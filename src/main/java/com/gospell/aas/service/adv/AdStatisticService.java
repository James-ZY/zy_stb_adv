package com.gospell.aas.service.adv;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.StringUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.dto.adv.*;
import com.gospell.aas.entity.adv.AdCategory;
import com.gospell.aas.entity.adv.AdStatistic;
import com.gospell.aas.entity.adv.AdStb;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.repository.hibernate.adv.AdStatisticDao;
import com.gospell.aas.repository.hibernate.adv.AdStbDao;
import com.gospell.aas.repository.mybatis.adv.IAdStatisticDao;
import com.gospell.aas.repository.mybatis.adv.IAdStbDao;
import com.gospell.aas.repository.mybatis.adv.IAdelementDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.webservice.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AdStatisticService extends BaseService {

	@Autowired
	private AdStatisticDao thisDao;
	@Autowired
	private IAdelementDao adelementDao;

	@Autowired
	private IAdStatisticDao mybatisDao;

	@Autowired
	private IAdStbDao stbDao;
	@Autowired
	private AdStbDao sDao;
	private Logger logger = LoggerFactory.getLogger(AdStatisticService.class);

	public AdStatistic get(String id) {
		return thisDao.get(id);
	}

	/**
	 * 分页查询广告的播放次数
	 * 
	 * @param page
	 * @param entity
	 */
	public Page<AdStatistic> findAdvPlayCount(Page<AdStatistic> page,
			AdStatistic entity) {
		entity.setIsScan(AdStatistic.SKIP_URL_YES);
		int count = mybatisDao.statisticPlayAllCount(entity);
		List<AdStatisticPlayDTO> list = mybatisDao.statisticPlayCount(entity);

		if (null != list && list.size() > 0) {
			List<AdStatistic> page_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				AdStatistic a = new AdStatistic();
				a.setDto(list.get(i));
				a.setPlayStartDate(entity.getPlayStartDate());
				a.setPlayEndDate(entity.getPlayEndDate());
				page_list.add(a);
			}
			page.setList(page_list);
			page.setCount(count);
		}
		return page;
	}

	/**
	 *  按类型统计广告点击次数
	 *
	 * @param entity
	 */
	public List<AdStatisticTypePlayDTO> findAdvTypeClickCount(AdStatistic entity) {
		entity.setIsScan(AdStatistic.SKIP_URL_YES);
		return mybatisDao.statisticAdvTypeClickCount(entity);
	}

	/**
	 * 统计所有广告点击次数
	 * @param entity
	 * @return
	 */
	public List<AdElementStatisticPlayDTO> statisticAdvElementClickCount(AdStatistic entity) {
		entity.setIsScan(AdStatistic.SKIP_URL_YES);
		return mybatisDao.statisticAdvElementClickCount(entity);
	}

	/**
	 * 按类型统计广告播放时长
	 * @param entity
	 * @return
	 */
	public List<AdStatisticTypePlayDTO> findAdvTypePlayCount(AdStatistic entity){
		entity.setIsScan(AdStatistic.SKIP_URL_NO);
		return mybatisDao.statisticAdvTypePlayCount(entity);
	}

	/**
	 * 统计各广告播放时长
	 * @param entity
	 * @return
	 */
	public List<AdElementStatisticPlayDTO> 	statisticAdvElementPlayCount(AdStatistic entity) {
		entity.setIsScan(AdStatistic.SKIP_URL_NO);
		return mybatisDao.statisticAdvElementPlayCount(entity);
	}

	/**
	 * 查询广告的播放次数
	 * 
	 * @param page
	 * @param entity
	 */
	public Page<AdStatistic> findAdvPlayDetail(Page<AdStatistic> page,
			AdStatistic entity) {


		List<AdStatistic> list = mybatisDao.statisticPlayDetail(entity);
		page.setList(list);

		return page;
	}

	/**
	 * 验证广告详细信息以前的需要跳转的广告,以及机顶盒ID和基准时间
	 * 
	 * @return
	 */
	public List<AdStatistic> checkString(UploadAdStatisticDTO upload,
			RestResult rest) {
		String uploadString = upload.getUploadData();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(uploadString)
				&& uploadString.startsWith("@") && uploadString.contains("#")) {
			String[] split = uploadString.split("#");
			if (split.length < 2) {// 判断至少解析出来有两组数据,一组是前面包含的广告ID，机顶盒ID，智能卡ID，后面广告的播放数据
				return null;
			}
			String first = split[0];
			String[] first_split = first.split("@");
			if (first_split.length != 4 && !first_split[0].equals("")) {// 判断第一组数据至少包含三种，广告ID，机顶盒ID，智能卡ID
				return null;
			}
			String advId = first_split[1];
			String stdSerialNumber = first_split[2];

			String standardTime = first_split[3];// 基准时间
			// 广告id，机顶盒ID，智能卡ID不能为null，并且广告ID必须为8位
			if (org.apache.commons.lang3.StringUtils.isBlank(advId) || advId.length() != 8
					|| org.apache.commons.lang3.StringUtils.isBlank(stdSerialNumber)
					|| org.apache.commons.lang3.StringUtils.isBlank(standardTime)) {
			 
				rest.setMessage("广告id，机顶盒ID，广告基准时间不能为null，并且广告ID必须为8位");
				logger.error("广告id，机顶盒ID，广告基准时间不能为null，并且广告ID必须为8位");
				return null;
			}
			long time = 0l;
			try {
				time = Long.parseLong(standardTime, 16);// 转换之后是秒数
				time = time * 1000;// 毫秒数
			} catch (NumberFormatException e) {// 不是正确的时间格式
			 
				rest.setMessage("广告的起始时间不是正确的格式");
				logger.error("广告的起始时间不是正确的格式");
				return null;
			}
			// 查询需要跳转的广告的信息，为null表示脏数据
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			List<String> list = Lists.newArrayList();
			list.add(advId.toLowerCase());
			queryMap.put("ids", list);
			List<Adelement> adelement = adelementDao
					.findAdelementByIdList(queryMap);
			Adelement scanAdelement = null;
			if (adelement == null || adelement.size() == 0) {
				 
				rest.setMessage("上传的需要返回url的广告信息不存在");
				logger.error("上传的需要返回url的广告信息不存在");
				return null;
			} else {
				scanAdelement = adelement.get(0);
				if (scanAdelement.getIsFlag() != null
						&& scanAdelement.getIsFlag() == 2) {// 表示是有网站连接的
					rest.setMessage(stdSerialNumber);// 临时存储智能卡Id
					rest.setContent(scanAdelement.getAddText());
				} else {
			 
					rest.setMessage("上传的需要返回url的广告信息不存在");
					logger.error("上传的需要返回url的广告信息不存在");
					return null;
				}
			}
			List<String> classifyIdList = Lists.newArrayList();// 查询分类是否在数据库中有，没有数据不合法
			for (int i = 1; i < split.length; i++) {
				String detail = split[i];
				if (detail.length() < 6 || !detail.substring(4, 5).equals("{")
						|| !detail.endsWith("}")) {
				 
					rest.setMessage("广告播放记录的信息格式错误");
					logger.error("广告播放记录的信息格式错误");
					return null;
				}
				String classifyId = detail.substring(0, 4);
				classifyIdList.add(classifyId);
			}
			// 验证广告的类型(需要补充)
			boolean checkCategoryId = checkCategoryId(classifyIdList);
			if (!checkCategoryId) {
			 
				rest.setMessage("广告分类信息ID不存在");
				logger.error("广告分类信息ID不存在");
				return null;
			}
			Map<String, String> adv_map = new HashMap<String, String>();
			List<AdStatisticDTO> insert_list = Lists.newArrayList();
			for (int i = 1; i < split.length; i++) {
				String detail = split[i];
				String info = detail.substring(5, detail.length() - 1);
				String[] all_info = info.split("&");
				if (all_info.length == 0) {
					 
					rest.setMessage("广告播放记录的信息格式错误");
					logger.error("广告播放记录的信息格式错误");
					return null;
				}
				String classifyId = detail.substring(0, 4);// 分组值
				for (int j = 0; j < all_info.length; j++) {

					String[] info_array = all_info[j].split(",");
					if (info_array.length != 3) {
						return null;
					}

					if (info_array[0].length() != 4) {
						return null;
					}
					int offset = 0;
					int durtion = 0;
					try {
						offset = Integer.parseInt(info_array[1]);
						durtion = Integer.parseInt(info_array[2]);
					} catch (NumberFormatException e) {
						rest.setMessage("广告播放记录的偏移时间或者是播放时间信息格式错误");
						logger.error("广告播放记录的偏移时间或者是播放时间信息格式错误");
						return null;
					}

					long dateLong = time + offset * 600 * 1000l;
					Date date = new Date(dateLong);

					String id = classifyId + info_array[0];
					adv_map.put(id, id);
					AdStatisticDTO dto = new AdStatisticDTO();
					dto.setAdId(id.toLowerCase());
					dto.setDuration(durtion * 10);
					dto.setPlayStartDate(DateUtils.formatDate(date,
							"yyyy-MM-dd HH:mm:ss"));
					dto.setSmartcardId(stdSerialNumber);
					insert_list.add(dto);
				}
			}
			List<Adelement> adv_list = queryAdelement(adv_map);
			if (null == adv_list) {
				rest.setMessage("广告播放Id不存在");
				logger.error("广告播放Id不存在");
				return null;
			}
			try {
				return insertList(scanAdelement, insert_list, adv_list, upload);

			} catch (ParseException e) {
				logger.error("时间格式转换出错！");
				e.printStackTrace();
				return null;
			}

		}
		return null;
	}

	/**
	 * 是否是重复扫描
	 * 
	 * @return 0不重复 1重复 2数据格式错误
	 */
	public Integer checkIsUploadCheck(String uploadSource, String uploadString) {
		if (uploadString.startsWith("@") && uploadString.contains("#")) {
			String[] split = uploadString.split("#");
			if (split.length < 2) {// 判断至少解析出来有两组数据,一组是前面包含的广告ID，机顶盒ID，智能卡ID，后面广告的播放数据
				return 2;
			}
			String first = split[0];
			String[] first_split = first.split("@");
			if (first_split.length != 4 && !first_split[0].equals("")) {// 判断第一组数据至少包含三种，广告ID，机顶盒ID，智能卡ID
				return 2;
			}
			String stdSerialNumber = first_split[2];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("uploadString", uploadSource);
			map.put("smartcardId", stdSerialNumber);
			int number = stbDao.findUploadStringByAdStb(map);
			if (number == 0) {
				return 0;
			} else {
				return 1;
			}
		}
		return 2;
	}

	/**
	 * 获取返回的url
	 * 
	 * @param uploadString
	 * @return
	 */
	public String getUrl(String uploadString) {
		String advId = uploadString.substring(1, 9);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<String> list = Lists.newArrayList();
		list.add(advId.toLowerCase());
		queryMap.put("ids", list);
		List<Adelement> adelement = adelementDao
				.findAdelementByIdList(queryMap);
		if(adelement != null && adelement.size()>0){
			Adelement scanAdelement = adelement.get(0);
			return scanAdelement.getAddText();
		}
	
		return "";
	}

	public List<AdStatistic> insertList(Adelement scanAdelement,
			List<AdStatisticDTO> list, List<Adelement> adv_list,
			UploadAdStatisticDTO dto) throws ParseException {
		Map<String, Adelement> map = new HashMap<String, Adelement>();
		for (int i = 0; i < adv_list.size(); i++) {
			map.put(adv_list.get(i).getAdId(), adv_list.get(i));
		}
		List<AdStatistic> insertList = Lists.newArrayList();
		Integer system = dto.getSystem();

		if (system == null
				|| (system != AdStatistic.SYSTEM_ANDROID && system != AdStatistic.SYSTEM_IOS)) {
			system = AdStatistic.SYSTEM_OTHER;
		}
		String usercode = dto.getUserCode();
		for (int i = 0; i < list.size(); i++) {
			AdStatisticDTO pushDto = list.get(i);
			String advId = pushDto.getAdId();
			AdStatistic entity = addAdvStatisticRecord(map.get(advId), pushDto,
					usercode, system, AdStatistic.SKIP_URL_NO);
			insertList.add(entity);
		}
		AdStatistic entity = addAdvStatisticRecord(scanAdelement, list.get(0),
				usercode, system, AdStatistic.SKIP_URL_YES);
		insertList.add(entity);
		return insertList;
	}

	public AdStatistic addAdvStatisticRecord(Adelement adelement,
			AdStatisticDTO pushDto, String usercode, Integer system,
			Integer isScan) throws ParseException {
		AdStatistic entity = new AdStatistic();
		entity.setAdElemet(adelement);
		entity.setSmartcardId(pushDto.getSmartcardId());
		entity.setStdSerialNumber(pushDto.getStdSerialNumber());
		Date playStartDate = null;
		if (isScan != null && AdStatistic.SKIP_URL_YES == isScan) {
			playStartDate = new Date();
		} else {
			playStartDate = format().parse(pushDto.getPlayStartDate());
			entity.setDuration(pushDto.getDuration());
		}
		entity.setPlayStartDate(playStartDate);
		entity.setBossUserCode(usercode);
		entity.setSystemType(system);
		entity.setIsScan(isScan);
		return entity;
	}

	public List<Adelement> queryAdelement(Map<String, String> map) {
		List<String> query_list = Lists.newArrayList();
		for (String key : map.keySet()) {
			query_list.add(key);
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<String> query_lower_list = StringUtils.stringListToLower(query_list);
		for (String string : query_lower_list) {
			System.out.println("ids="+string);
		}
		queryMap.put("ids", query_lower_list);
		List<Adelement> adelement = adelementDao
				.findAdelementByIdList(queryMap);
		for (Adelement adelement2 : adelement) {
			System.out.println("ids"+adelement2.getAdId());
		}
		if (null != adelement) {
			int size = adelement.size();
			int mapSize = map.size();
			if (size == mapSize) {// 表示后面传输的详细信息的分类加上顺序的ID全部在系统中可以查询到对应的广告
				return adelement;
			} else {
				return adelement;
			}
		}
		return null;
	}

	/**
	 * 验证字符串中的广告分类ID是否全部在播控存在，只要有一个不存在，则判断当前的数据不合法
	 * 
	 * @param categoryList
	 * @return
	 */
	public boolean checkCategoryId(List<String> categoryIdList) {
		List<AdCategory> allCateGoryList = UserUtils.getCateGoryList();
		if (null != allCateGoryList && allCateGoryList.size() > 0) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < allCateGoryList.size(); i++) {
				map.put(allCateGoryList.get(i).getCategoryId(), allCateGoryList
						.get(i).getCategoryId());
			}
			for (int i = 0; i < categoryIdList.size(); i++) {
				String id = categoryIdList.get(i);
				if (!map.containsKey(id)) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 保存广告统计数据
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(List<AdStatistic> entityList, String uploadData)
			throws Exception {

		if (null != entityList && entityList.size() > 0) {
			thisDao.clear();
			deleteRepeatData(entityList);
			String std_number = entityList.get(0).getSmartcardId();
			deleteDataBaseRepeatData(entityList, std_number);//
			if (null != entityList && entityList.size() > 0) {
				AdStb stb = new AdStb();
				stb.setSmartcardId(std_number);
				stb.setUploadPlayRecord(uploadData);
				sDao.save(stb);
				thisDao.save(entityList);
			}
		}
	}
	/**
	 * 防止用户在上传的过程中上传多条相同的记录，因为假如数据库中原本不存在数据，无法过滤
	 * @param sourceList
	 */
	public void deleteRepeatData(List<AdStatistic> sourceList){
		Map<String, AdStatistic> sourceMap = new HashMap<String, AdStatistic>();
		for (int i = 0; i < sourceList.size(); i++) {
			String key = getUniqKey(sourceList.get(i));
			sourceMap.put(key, sourceList.get(i));
		}
		sourceList.clear();
		for (String key:sourceMap.keySet()) {
			sourceList.add(sourceMap.get(key));
		}
	}

	/**
	 * 数据库中已经存在的数据不予保存
	 * @param list
	 * @param std_number
	 */
	public void deleteDataBaseRepeatData(List<AdStatistic> list, String std_number) {
		List<String> date_list = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			AdStatistic entity = list.get(i);
			if (entity.getIsScan() == AdStatistic.SKIP_URL_NO) {
				Date date = entity.getPlayStartDate();
				date_list.add(format().format(date));
			}
		}
		List<AdStatistic> repeat_list = selectAdStatisticByDateList(date_list,
				std_number);
		if (null != repeat_list && repeat_list.size() > 0) {
			listUniq(list, repeat_list);
		}
	}

	/**
	 * 最开始的做法：去掉重复的数据（一个广告ID在某一时刻只可能播放一次）
	 * 更新的做法：现在是累加时长
	 * 
	 * @param sourceList
	 *            扫描的二维码的数据
	 * @param repeatList
	 *            数据库中存在的数据
	 */
	public void listUniq(List<AdStatistic> sourceList,
			List<AdStatistic> repeatList) {
		Map<String, AdStatistic> sourceMap = new HashMap<String, AdStatistic>();
		for (int i = 0; i < sourceList.size(); i++) {
			String key = getUniqKey(sourceList.get(i));
			sourceMap.put(key, sourceList.get(i));
		}
		for (int i = 0; i < repeatList.size(); i++) {
			AdStatistic a = repeatList.get(i);
			String key = getUniqKey(a);
			if (sourceMap.containsKey(key)) {
				AdStatistic mapObject = sourceMap.get(key);
				int time = mapObject.getDuration();
				a.setDuration(time);
				a.setIsScan(AdStatistic.SKIP_URL_NO);
				sourceMap.put(key, a);
			}
		}
		sourceList.clear();
		if (!sourceMap.isEmpty() && sourceMap.size() > 0) {
			for (String key : sourceMap.keySet()) {
				sourceList.add(sourceMap.get(key));
			}
		}

	}

	/**
	 * 广告ID+播放时刻值是唯一的
	 * 
	 * @param entity
	 * @return
	 */
	public String getUniqKey(AdStatistic entity) {
		Adelement e = entity.getAdElemet();
		return e.getAdId() + format().format(entity.getPlayStartDate());// 这个值是唯一的，因为同一个广告在同一个时间只可能播放一次
	}

	/**
	 * 根据时间和机顶盒ID查询是否有冲虎的广告
	 * 
	 * @param dateList
	 * @param std_number
	 * @return
	 */
	public List<AdStatistic> selectAdStatisticByDateList(List<String> dateList,
			String std_number) {
		Map<String, Object> map = new HashMap<>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("dateList", dateList);
		map.put("smartcardId", std_number);
		map.put("isScan", AdStatistic.SKIP_URL_NO);
		return mybatisDao.selectAdStatisticByDateList(map);
	}

	/**
	 * 保证格式特意添加
	 * 
	 * @return
	 */
	public SimpleDateFormat format() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format;
	}
}
