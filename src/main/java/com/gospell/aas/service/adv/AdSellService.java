package com.gospell.aas.service.adv;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.CalculateUtil;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.common.utils.sms.DateUtil;
import com.gospell.aas.dto.adv.*;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdSell;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.hibernate.adv.AdSellDao;
import com.gospell.aas.repository.hibernate.adv.AdTypeDao;
import com.gospell.aas.repository.hibernate.adv.AdvertiserDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.repository.mybatis.adv.IAdSellDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.tools.internal.xjc.reader.Ring.add;

@Service
@Transactional(readOnly = true)
public class AdSellService extends BaseService {

	@Autowired
	private AdSellDao thisDao;
	@Autowired
	private AdComboService comboService;
	@Autowired
	private IAdSellDao mybatisDao;
	@Autowired
	private IAdComboDao comboDao;
	@Autowired
	private AdTypeDao typeDao;	
	@Autowired
	private AdvertiserDao advertiserDao;

	public AdSell get(String id) {
		return thisDao.get(id);
	}

	public List<AdSell> findAll() {
		return thisDao.findAll();
	}

	public void clear() {
		thisDao.clear();
	}

	/**
	 * 根据条件查询销售表
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            销售表
	 * @return
	 */
	public Page<AdSell> find(Page<AdSell> page, AdSell entity) {
		// DetachedCriteria dc = thisDao.createDetachedCriteria();
		// dc.createAlias("adCombo", "adCombo");
		// dc.createAlias("advertiser", "advertiser");
		// if (null != entity.getAdCombo()) {
		// if (StringUtils.isNotBlank(entity.getAdCombo().getId())) {
		//
		// dc.add(Restrictions.eq("adCombo.id", entity.getAdCombo()
		// .getId()));
		// }
		//
		// }
		// if (null != entity.getAdvertiser()) {
		// if (StringUtils.isNotBlank(entity.getAdvertiser().getId())) {
		// dc.add(Restrictions.eq("advertiser.id", entity.getAdvertiser()
		// .getId()));
		// }
		// }
		//
		// if (StringUtils.isNotBlank(entity.getContractNumber())) {
		// dc.add(Restrictions.like("contractNumber",
		// "%" + entity.getContractNumber() + "%"));
		// }
		//
		// if (null != entity.getStartDate()) {
		// dc.add(Restrictions.ge("startDate", entity.getStartDate()));
		// }
		// if (null != entity.getEndDate()) {
		// dc.add(Restrictions.le("endDate", entity.getEndDate()));
		// }
		//
		// dc.add(Restrictions.eq(AdSell.FIELD_DEL_FLAG,
		// AdSell.DEL_FLAG_NORMAL));
		// dc.addOrder(Order.asc("adCombo.adcomboId"));
		User user = UserUtils.getUser();// 保证某个广告商只能查看自己的广告
		if (user.getAdvertiser() != null) {
			entity.setAdvertiser(user.getAdvertiser());
		}else if(user.isAdmin()){
			entity.setAdvertiser(null);
		}else{
			return page;
		}
		page.setOrderBy("t.id asc,s.end_date asc,u.login_name");
		entity.setPage(page);
		List<AdSell> list = mybatisDao.findAll(entity);
		
	 
		if(list != null &&list.size() >0){
			List<AdSell> noPastlist = Lists.newArrayList();
			List<AdSell> pastlist = Lists.newArrayList();
		//判断查询出的套餐销售记录是否过期
			for (int i = 0; i < list.size(); i++) {
				AdSell sell = list.get(i);
				Date date = sell.getEndDate();
				
			 
				AdType t = sell.getAdCombo().getAdType();
				if(null != t.getId()){
					AdType localeType= AdTypeUtils.get(t.getId());
					sell.getAdCombo().setAdType(localeType);
					
				}
				int isEqual = DateUtils.judgeDateSize(new Date(), date);
				if(isEqual == -1){
					sell.setIsPast(true);
					pastlist.add(sell);
				}else{
					sell.setIsPast(false);
					noPastlist.add(sell);
				}
				
			}
			if(noPastlist != null && pastlist.size() >0){
				for (int i = 0; i < pastlist.size(); i++) {
					noPastlist.add(pastlist.get(i));
				}
			}
			page.setList(noPastlist);
		}
	

		return page;
	}

	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdSell entity) throws Exception {

		thisDao.save(entity);

	}

	/**
	 * 删除套餐销售数据
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(String id) throws ServiceException {

		thisDao.deleteById(id);

	}

	public AdSell findSellByContractNumber(String number) {
		return thisDao.findSellByContractNumber(number);
	}

	/**
	 * 判断用户选择的广告套餐的时间是否是已经销售了的
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean checkTime(String comboId, Date startDate,Date endDate) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("comboId", comboId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("status", AdSell.ADSELL_YES_VALID);
		int count = mybatisDao.findSellCountByComboId(map);
		if(count ==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断用户选择的广告套餐的时间是否是已经销售了的（除了自身id的销售记录）
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean checkTime(String comboId, Date startDate,Date endDate,String sellId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("comboId", comboId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("sellId", sellId);
		int count = mybatisDao.findSellCountByComboId(map);
		if(count ==0){
			return true;
		}
		return false;
	}

	/**
	 * 根据套餐Id获取当前登录用户的广告套餐销售时间, 最多查询当前时间之后一年的数据,防止数据过多
	 * 
	 * @param entity
	 * @return
	 */
	public List<AdSellDateDTO> getSellDate(String comboId, String advId) {
		AdSellDateDTO dto = null;
		// List<AdSell> list = thisDao.findSellByComboAndUser(comboId, advId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboId", comboId);
		map.put("advId", advId);
		map.put("startDate", new Date());
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int nextYear = year + 1;
		c.set(Calendar.YEAR, nextYear);
		map.put("endDate", c.getTime());
		List<AdSell> list = mybatisDao.findSellByComboIdAndAdvId(map);
		List<AdSellDateDTO> date_list = Lists.newArrayList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				AdSell sell = list.get(i);
				dto = new AdSellDateDTO();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
				if (null != sell.getStartDate()) {
					Date dto_startDate = null;
					Date nowDate = new Date();
					Date beginDate = sell.getStartDate();
					int compare = DateUtil.compareDate(beginDate, nowDate);
					if (compare == -1) {
						dto_startDate = beginDate;
					} else {
						dto_startDate = nowDate;
					}
					dto.setStartDate(DateUtils.formatDate(dto_startDate,
							"yyyy-MM-dd"));
				}
				if (null != sell.getEndDate()) {
					dto.setEndDate(DateUtils.formatDate(sell.getEndDate(),
							"yyyy-MM-dd"));
				}
				date_list.add(dto);
			}
		}

		return date_list;
	}

	/**
	 * 获取当前广告的销售记录
	 * 
	 * @param adelement
	 * @return
	 */
	public AdSellDateDTO getSellDateByAdvPlayDate(Adelement adelement) {
		if (null == adelement.getAdCombo() || null == adelement.getAdvertiser()) {
			return null;
		}
		String combo = adelement.getAdCombo().getId();
		String advId = adelement.getAdvertiser().getId();
		Date startDate = adelement.getStartDate();
		Date endDate = adelement.getEndDate();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comboId", combo);
		map.put("advId", advId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		List<AdSell> list = mybatisDao.findSellByComboIdAndAdvPlayDate(map);
		if (null != list && list.size() > 0) {
			AdSell sell = list.get(0);
			AdSellDateDTO dto = new AdSellDateDTO();
			if (null != sell.getStartDate()) {
				dto.setStartDate(DateUtils.formatDate(sell.getStartDate(),
						"yyyy-MM-dd"));
			}
			if (null != sell.getEndDate()) {
				dto.setEndDate(DateUtils.formatDate(sell.getEndDate(),
						"yyyy-MM-dd"));
			}

			return dto;
		}
		return null;
	}

	/**
	 * 判断广告商同一天是重复购买了广告类型（只查询与频道无关的，因为频道相关在同一天套餐是相斥的，是不可能重复的）
	 * @param sell
	 * @return
	 */
	public Boolean judgeAdTypeIsRepeat(AdSell sell) {
		Boolean b = false;
		AdCombo combo = sell.getAdCombo();
		if (null != combo) {
			AdCombo adCombo = comboService.get(combo.getId());
			if (null != adCombo && adCombo.getAdType() != null) {
				String typeId = adCombo.getAdType().getId();
				int isFlag = adCombo.getAdType().getIsFlag();
				if (AdType.TYPE_NOT_CHANNEL == isFlag) {

				 			List<String> list = 
							mybatisDao.getIsRepeatAcombo(typeId,
							sell.getAdvertiser().getId(), sell.getStartDate(),
							sell.getEndDate());
					if (null == list || list.size() == 0) {
						b = true;
					}
				} else {
					return true;
				}

			}
		}
		return b;
	}

	/**
	 * 判断是否可以删除销售记录，只要该销售记录下有广告数据数据就不允许删除
	 * 
	 * @return
	 */
	public Boolean isCanDeleteSell(String id) {
		AdSell sell = get(id);
		int count = mybatisDao.fingAdvInSellTime(sell);
		if (count == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public List<Adelement> fingAdvInSell(String id){
		AdSell sell = get(id);
		return mybatisDao.fingAdvInSell(sell);
	}

	/**
	 * 根据条件查询销售表
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            销售表
	 * @return
	 */
	public Page<AdSell> findAdvertiserBuyNumber(Page<AdSell> page, AdSell entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delFlag", entity.getDelFlag());
		map.put("startDate", entity.getStartDate());
		map.put("endDate", entity.getEndDate());
		map.put("typeId", entity.getAdCombo().getAdType().getId());
		Integer total = mybatisDao.getAdcomboCountByType(map);
		if (total == 0)// 没有卖出任何一个套餐
			return page;
		entity.setPage(page);
		List<AdvtiserSellNumber> list = mybatisDao
				.getAdcomboSellAdvtiserCount(entity);
		List<AdSell> result_list = Lists.newArrayList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdvtiserSellNumber query = list.get(i);
				AdSell sell = new AdSell();
				AdCombo combo = new AdCombo();
				combo.setAdType(entity.getAdCombo().getAdType());
				sell.setAdCombo(combo);
				AdSellNumberDTO dto = new AdSellNumberDTO();
				dto.setTotal(total);
				dto.setAdvId(query.getAdvId());
				dto.setAdvName(query.getAdvName());
				dto.setBuyNumber(query.getCount());
				sell.setStartDate(entity.getStartDate());
				sell.setEndDate(entity.getEndDate());
				if (entity.getAdvertiser() != null) {
					sell.setAdvertiser(entity.getAdvertiser());
				}
				BigDecimal percent = CalculateUtil.getPercent(query.getCount(),
						total);
				String p = CalculateUtil.decimalFormat(percent);// 保证输出的数据小数点后只有两位
				dto.setScale(p + "%");
				sell.setDto(dto);
				result_list.add(sell);
			}
		}
		page.setList(result_list);
		return page;

	}

	/**
	 * 查询广告商购买时长
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<AdSell> findAdvertiserBuyTime(Page<AdSell> page, AdSell entity) {
		entity.setPage(page);
		List<AdvtiserSellNumber> list = mybatisDao
				.getAdcomboSellAdvtiserTime(entity);
		List<AdSell> resultList = Lists.newArrayList();
		Integer total = 0;
		for(AdvtiserSellNumber timeItem : list){
			total+=timeItem.getCount();
		}
		for(AdvtiserSellNumber timeItem : list){
			if(timeItem.getCount().equals(0)){
				continue;
			}
			AdSell sell = new AdSell();
			AdCombo combo = new AdCombo();
			combo.setAdType(entity.getAdCombo().getAdType());
			sell.setAdCombo(combo);
			AdSellNumberDTO dto = new AdSellNumberDTO();
			dto.setTotal(total);
			dto.setAdvId(timeItem.getAdvId());
			dto.setAdvName(timeItem.getAdvName());
			dto.setBuyNumber(timeItem.getCount());
			sell.setStartDate(entity.getStartDate());
			sell.setEndDate(entity.getEndDate());
			if (entity.getAdvertiser() != null) {
				sell.setAdvertiser(entity.getAdvertiser());
			}
			BigDecimal percent = CalculateUtil.getPercent(timeItem.getCount(),
					total);
			String p = CalculateUtil.decimalFormat(percent);
			dto.setScale(p + "%");
			sell.setDto(dto);
			resultList.add(sell);
		}
		page.setList(resultList);
		return page;
	}

	/**
	 * 根据条件查询卖给广告商的所有套餐的详细信息
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            销售表
	 * @return
	 * @throws ParseException
	 */
	public Page<AdSell> findAdvertiserBuyCombo(Page<AdSell> page, AdSell sell)
			throws Exception {

		List<AdCombo> list = mybatisDao.getAdcomboCountDetail(sell);
		if (null != list && list.size() > 0) {
			List<AdSell> sell_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				AdSell s = new AdSell();
				AdCombo combo = list.get(i);
				if(combo.getAdType() != null){
					AdType type = combo.getAdType();
					String typeId = type.getId();
					if(StringUtils.isNotEmpty(typeId)){
						AdType localetype = AdTypeUtils.get(typeId);
						combo.setAdType(localetype);
					}
				}
				s.setAdCombo(list.get(i));
				sell_list.add(s);
			}
			page.setList(sell_list);

		}
		return page;

	}

	/**
	 * 获取一定时间范围内广告商购买套餐的详细信息
	 * 
	 * @param page
	 * @param sell
	 * @return
	 * @throws Exception
	 */

	public Page<AdSell> getSellAdvTiserComboDetail(Page<AdSell> page,
			AdSell sell) throws Exception {
		List<AdCombo> list = mybatisDao.getSellAdvTiserComboDetail(sell);
		if (null != list && list.size() > 0) {
			List<AdSell> sell_list = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				AdSell s = new AdSell();
				AdCombo combo = list.get(i);
				if(combo.getAdType() != null){
					AdType type = combo.getAdType();
					String typeId = type.getId();
					if(StringUtils.isNotEmpty(typeId)){
						AdType localetype = AdTypeUtils.get(typeId);
						combo.setAdType(localetype);
					}
				}
				s.setAdCombo(list.get(i));
				sell_list.add(s);
			}
			page.setList(sell_list);

		}
		return page;
	}

	//
	/**
	 * 某个广告类型的一天的24小时的销售情况，这个地方考虑了数据量过大的问题，但是只能查询一天
	 * 
	 * @return
	 */
	public String getSellDayTimeNumber(AdSell sell) {
		// 这个直接是查询一天的情况
		List<ChannelAdComboSellNumberDTO> list = Lists.newArrayList();
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < 24; i++) {
			map.put(i, 0);// 初始化
		}
		Date startDate = sell.getStartDate();
		Date endDate = sell.getEndDate();
		int days = DateUtils.dateBetweenDay(startDate, endDate);
		if (days > 30) {
			endDate = DateUtils.getDateAfter(startDate, 30);
		}
		AdCombo combo = null;
		if (sell.getAdCombo() == null) {
			combo = new AdCombo();
		} else {
			combo = sell.getAdCombo();
		}
		for (int i = 0; i < days; i++) {
			int j = i + 1;
			Date end_Date = DateUtils.getDateAfter(startDate, j);
			sell.setEndDate(end_Date);
			combo.setIsFlag(AdCombo.ADCOMOBO_CHANNEL_ISFLAG);
			sell.setAdCombo(combo);
			List<ChannelAdComboSellNumberDTO> query_list = mybatisDao
					.getSellDayTimeNumber(sell);
			if (null != query_list && query_list.size() > 0) {
				for (int k = 0; k < query_list.size(); k++) {
					ChannelAdComboSellNumberDTO dto = query_list.get(k);
					int time = dto.getTime();
					if (map.containsKey(time)) {
						int count = map.get(time);
						count += dto.getCount();
						map.put(time, count);
					}
				}
			}
		}
		for (int key : map.keySet()) {
			ChannelAdComboSellNumberDTO c = new ChannelAdComboSellNumberDTO();
			c.setTime(key);
			c.setCount(map.get(key));
			list.add(c);
		}
		return JsonMapper.toJsonString(list);

	}

	/**
	 * 某个广告类型的一段时间（最多7天）的24小时的销售情况,这个地方未考虑7天数据量过大的问题
	 * 
	 * @return
	 */
	public String getSellDayTimeNumberBymemory(AdSell sell) {
		// 这个直接是查询一天的情况
		List<ChannelAdComboSellNumberDTO> list = Lists.newArrayList();
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < 24; i++) {
			ChannelAdComboSellNumberDTO c = new ChannelAdComboSellNumberDTO();
			c.setTime(i);
			c.setCount(0);
			list.add(c);
			map.put(i, 0);// 初始化
		}
		Date startDate = sell.getStartDate();
		Date endDate = sell.getEndDate();
		int days = DateUtils.dateBetweenDay(startDate, endDate);
		if (days > 7) {
			endDate = DateUtils.getDateAfter(startDate, 6);
			sell.setEndDate(endDate);
		}
		List<AdCombo> combo_list = mybatisDao.getAdcomboCountDetail(sell);
		if (null != combo_list && combo_list.size() > 0) {
			for (int i = 0; i < combo_list.size(); i++) {
				AdCombo combo = combo_list.get(i);
				int start_hour = combo.getStartHour();
				int end_hour = combo.getEndHour();
				for (int j = start_hour; j <= end_hour; j++) {
					if (map.containsKey(j)) {
						int count = map.get(j);
						count += 1;
						map.put(j, count);
					}
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			ChannelAdComboSellNumberDTO c = list.get(i);
			if (map.containsKey(i)) {
				c.setCount(map.get(i));
			}
		}
		return JsonMapper.toJsonString(list);
	}


    /**
     * 查询与频道相关的所有类型广告播放时间段数据
     * @param sell
     * @return
     */
    public String getSellDayTimeNumberByTypes(AdSell sell) {
        List<AdType> adTypes = AdTypeUtils.getAdTypeByIsFlag(1);
        List<AdComboSellTypesNumberDTO> typesNumberList = new ArrayList<>(adTypes.size());
        Date startDate = sell.getStartDate();
        Date endDate = sell.getEndDate();
		if(startDate!=null&&endDate!=null){
			int days = DateUtils.dateBetweenDay(startDate, endDate);
			if (days > 7) {
				endDate = DateUtils.getDateAfter(startDate, 6);
				sell.setEndDate(endDate);
			}
		}
        List<AdCombo> combo_list = mybatisDao.getAdcomboCountDetail(sell);

            //按类型分类
            Multimap<String, AdCombo> multiMap = ArrayListMultimap.create();
            if (null != combo_list && combo_list.size() > 0) {
                for (AdCombo adCombo : combo_list) {
                    multiMap.put(adCombo.getAdType().getId(), adCombo);
                }
            }
            //分类型计数
            for(AdType adType : adTypes){
                //存放一个广告类型计数list
                List<ChannelAdComboSellNumberDTO> list = Lists.newArrayList();
                //创建一个全0数据map
                Map<Integer, Integer> map = new HashMap<>();
                for (int i = 0; i < 24; i++) {
                    ChannelAdComboSellNumberDTO c = new ChannelAdComboSellNumberDTO();
                    c.setTime(i);
                    c.setCount(0);
                    list.add(c);
                    map.put(i, 0);
                }
                Collection<AdCombo> sameTypeAdCombos = multiMap.get(adType.getId());

                for(AdCombo sameTypeAdCombo : sameTypeAdCombos){
                    int startHour = 0;
                    int endHour = 0;
                    //若时间段为null，作全天播放处理
                    if(null==sameTypeAdCombo.getStartHour()||null==sameTypeAdCombo.getEndHour()){
                        startHour =0;
                        endHour =24;
                    }else {
                        startHour = sameTypeAdCombo.getStartHour();
                        endHour = sameTypeAdCombo.getEndHour();
                    }
                    for (int j = startHour; j <= endHour; j++) {
                        if (map.containsKey(j)) {
                            int count = map.get(j);
                            count += 1;
                            map.put(j, count);
                        }
                    }
                }
                //写入该类型统计计数数据
                for (int i = 0; i < list.size(); i++) {
                    ChannelAdComboSellNumberDTO c = list.get(i);
                    if (map.containsKey(i)) {
                        c.setCount(map.get(i));
                    }
                }
                AdComboSellTypesNumberDTO typesNumberDTO = new AdComboSellTypesNumberDTO(adType.getId(),list);
                typesNumberDTO.setTypeName(adType.getTypeName());
                typesNumberList.add(typesNumberDTO);
            }

        return JsonMapper.toJsonString(typesNumberList);
    }

	/**
	 * 获取时间段统计数据
	 * @param sellParam
	 * @return
	 */
	public String getAdSellTimeSlot(AdSell sellParam){
		List<AdType> adTypes = AdTypeUtils.getAdTypeByIsFlag(1);
		List<AdComboSellTypesNumberDTO> resultList = new ArrayList<>(adTypes.size());
		//查出所有在查询时间范围内的销售记录
		List<AdSell> sellList = mybatisDao.findAdSellByAdType(sellParam);
		//按类型对销售记录分类
		Multimap<String, AdSell> multiMap = ArrayListMultimap.create();
		for(AdSell adSell : sellList){
			multiMap.put(adSell.getAdCombo().getAdType().getId(),adSell);
		}
		//分类型记录时间
		for(AdType adType : adTypes){
			List<ChannelAdComboSellNumberDTO> list = Lists.newArrayList();
			//创建一个全0数据map
			Map<Integer, Integer> map = new HashMap<>();
			//某类型广告套餐在查询时间段内的总售出时长
			Integer totalTime = 0;
			for (int i = 0; i < 24; i++) {
				ChannelAdComboSellNumberDTO c = new ChannelAdComboSellNumberDTO();
				c.setCount(0);
				c.setTime(i);
				list.add(c);
				map.put(i, 0);
			}
			Collection<AdSell> sameTypeAdAdSells = multiMap.get(adType.getId());
			int days = 0;
			int startHour = 0,endHour = 0,startMin = 0,endMin = 0,startSecond = 0,endSecond = 0;
			int totalSeconds = 0;
			for(AdSell adSell : sameTypeAdAdSells){
				startHour = adSell.getAdCombo().getStartHour();
				endHour = adSell.getAdCombo().getEndHour();
				startMin = adSell.getAdCombo().getStartMinutes();
				endMin = adSell.getAdCombo().getEndMinutes();
				startSecond = adSell.getAdCombo().getStartSecond();
				endSecond = adSell.getAdCombo().getEndSecond();
				 //以秒计算
				 days = getAdComboDays(sellParam.getStartDate(),sellParam.getEndDate(),adSell.getStartDate(),adSell.getEndDate());
				for (int j = startHour; j <= endHour; j++) {
					int seconds = 0;
					if(startHour==endHour){
						seconds = (endSecond-startSecond)+(endMin-startMin)*60;
					}else if(j==endHour){
						seconds = endSecond+endMin*60;
					}else{
						seconds = (0-startSecond)+(0-startMin)*60+3600;
					}

					if (map.containsKey(j)) {
						int count = map.get(j);
						count += (days+1)*seconds;
						map.put(j, count);
					}
				}
				totalSeconds+=(days+1)*((endHour-startHour)*3600+(endMin-startMin)*60+(endSecond-startSecond));
			}
			//写入该类型统计计数数据
			for (int i = 0; i < list.size(); i++) {
				ChannelAdComboSellNumberDTO c = list.get(i);
				if (map.containsKey(i)) {
					c.setCount(map.get(i));
					if(totalSeconds!=0){
						BigDecimal rate = CalculateUtil.getPercent(map.get(i),totalSeconds);
						c.setRate(rate.doubleValue());
					}else{
						c.setRate(0.00);
					}
				}
			}
			AdComboSellTypesNumberDTO typesNumberDTO = new AdComboSellTypesNumberDTO(adType.getId(),list);
			typesNumberDTO.setTypeName(adType.getTypeName());
			resultList.add(typesNumberDTO);
		}
		return JsonMapper.toJsonString(resultList);
	}

	/**
	 * 查询广告发送器在当前时间范围下有没有正在销售的广告
	 * 
	 * @param networkId
	 * @return
	 */
	public Integer getSellCountInNetwork(String networkId) {
		Map<String, Object> map = new HashMap<>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("nowDate", new Date());
		map.put("networkId", networkId);
		int count = mybatisDao.sellNotChannelComboInNetworkNow(map);// 先查询与频道无关的正在销售的广告有木有。没有的话再次查询与频道相关的广告有没有正在销售的
		if (count == 0) {
			count = mybatisDao.sellChannelComboInNetworkNow(map);
		}
		return count;
	}
	
	/**
	 * 查询广告发送器在当前时间范围下有没有正在销售的广告
	 * 
	 * @param networkId
	 * @return
	 */
	public Date getAdvMaxDateInSell(String sellId) {
		Map<String, Object> map = new HashMap<>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("sellId", sellId);
		return  mybatisDao.getAdvMaxDateInSell(map);// 查询当前销售记录管理的广告的最大的播放结束时间
		 
	}
	/**
	 * 修改销售记录，分为两种情况：
	 * 1.销售记录对应的有广告
	 * 2.销售记录未对应广告
	 * @param dto
	 * @return
	 */
	public Boolean checkAlterSell(AdSelAlterlDTO dto) throws Exception{
		//销售记录对应的广告
		boolean b = dto.getIsHaveAdv();
		
		if(!b){
			String n_EndDate = dto.getNewEndDate();
			String o_EndDate = dto.getOldEndDate();
			Date newEndDate = DateUtils.getDateFromString(n_EndDate,"yyyy-MM-dd");
			Date oldEndDate = DateUtils.getDateFromString(o_EndDate,"yyyy-MM-dd");
			int compare = DateUtils.judgeDateSize(newEndDate, oldEndDate);
			if(compare == -1){//销售记录的时间增加了，必须查询原来的结束时间到现在的结束时间这段时间以内有没有销售记录
				Date oldEndDateAdd = DateUtils.getDateAfter(oldEndDate, 1);
				return checkTime(dto.getNewComboId(), oldEndDateAdd, newEndDate);
			}else{
				return true;
			}
		}else{
 
		  String id =dto.getId();
			 
		  String newComboId  = dto.getNewComboId();
			
	      String newAdvertiserId  =dto.getNewAdvertiserId();
			
          String newStartDate =dto.getNewStartDate();
			
	 	  String newEndDate =dto.getNewEndDate();
			 
	     String oldComboId =dto.getOldComboId();	
		 
		 String oldAdvertiserId = dto.getOldAdvertiserId();

		 String oldStartDate  = dto.getOldStartDate();
		 
		 String oldEndDate  = dto.getOldEndDate();
		 Date n_EndDate = DateUtils.getDateFromString(newEndDate,"yyyy-MM-dd");
		 Date o_EndDate = DateUtils.getDateFromString(oldEndDate,"yyyy-MM-dd");
		 Date n_StartDate = DateUtils.getDateFromString(newStartDate,"yyyy-MM-dd");
		 Date o_StartDate = DateUtils.getDateFromString(oldStartDate,"yyyy-MM-dd");
		 if(newComboId.equals(oldComboId) && newAdvertiserId.equals(oldAdvertiserId)
				 &&newStartDate.equals(oldStartDate) &&newEndDate.equals(oldEndDate)){
			 return true;
		 }else{
			 if(!newComboId.equals(oldComboId)){//套餐变化了，去判断有没有被销售（相当于添加）
				 return checkTime(newComboId, n_StartDate, n_EndDate);
			 }else{
				 if(!newAdvertiserId.equals(oldAdvertiserId)){//套餐相同，广告商不同
					 return checkTime(newComboId,  n_StartDate, n_EndDate, id);
				 }else{
					 int compareStart = DateUtils.judgeDateSize(n_StartDate, o_StartDate);
					 int compareEnd = DateUtils.judgeDateSize(n_EndDate, o_EndDate);
					 if(compareStart == -1 && compareEnd ==1){//新的时间在原来的时间范围以内
						 return true;
					 }else{
						 return checkTime(newComboId,  n_StartDate, n_EndDate, id);
					 }
				 }
			 }
		 }

		}
	}
	
	public List<AdComboPublishNumberDto> getAdComboPublishNumber(AdComboPublishNumberDto dto){
		Map<String,Object> map = new HashMap<String,Object>();
		if(dto.getAdType()!=null){
			map.put("typeId", dto.getAdType().getId());			
		}else{
			map.put("typeId",null);
		}
		map.put("startDate", dto.getStartDate());
		map.put("endDate", dto.getEndDate());
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		Map<String, AdComboPublishNumberDto> comboMap = new TreeMap<String, AdComboPublishNumberDto>();
		List<AdComboPublishNumberDto> adComboList =Lists.newArrayList();
		List<AdComboPublishNumber> list = mybatisDao.getAdComboPublishNumber(map);
		for (AdComboPublishNumber entity : list) {
			AdComboPublishNumberDto adComboPublishNumberDto = new AdComboPublishNumberDto();
			entity.setAdCombo(comboService.get(entity.getAdCombo().getId()));
			entity.setAdvertiser(advertiserDao.get(entity.getAdvertiser().getId()));
			entity.setAdType(typeDao.get(entity.getAdType().getId()));
			if(comboMap.containsKey(entity.getAdType().getId())){
				List<AdComboPublishNumber> publishList =null;
				List<AdComboPublishNumber> unPublishList =null;
				if(comboMap.get(entity.getAdType().getId()).getPublishList() ==null){
					publishList = Lists.newArrayList();
				}else{
					publishList = comboMap.get(entity.getAdType().getId()).getPublishList();
				}
				
				if(comboMap.get(entity.getAdType().getId()).getUnPublishList() ==null){
					unPublishList = Lists.newArrayList();
				}else{
					unPublishList = comboMap.get(entity.getAdType().getId()).getUnPublishList();
				}
				 if(entity.getPublishCount()>0){
					 adComboPublishNumberDto.setPublishCount(comboMap.get(entity.getAdType().getId()).getPublishCount()+1);
					 adComboPublishNumberDto.setUnPublishCount(comboMap.get(entity.getAdType().getId()).getUnPublishCount());
					 publishList.add(entity);
					 adComboPublishNumberDto.setPublishList(publishList);
					 adComboPublishNumberDto.setUnPublishList(unPublishList);
					 comboMap.put(entity.getAdType().getId(), adComboPublishNumberDto);					 
				 }else{
					 adComboPublishNumberDto.setPublishCount(comboMap.get(entity.getAdType().getId()).getPublishCount());
					 adComboPublishNumberDto.setUnPublishCount(comboMap.get(entity.getAdType().getId()).getUnPublishCount()+1);
					 unPublishList.add(entity);
					 adComboPublishNumberDto.setPublishList(publishList);
					 adComboPublishNumberDto.setUnPublishList(unPublishList);
					 comboMap.put(entity.getAdType().getId(), adComboPublishNumberDto);					 
				 }
				 
			}else{
				List<AdComboPublishNumber> cpList = Lists.newArrayList();
				cpList.add(entity);
				if(entity.getAllCount()>0){
					adComboPublishNumberDto.setPublishCount(1);
					adComboPublishNumberDto.setUnPublishCount(0);
					adComboPublishNumberDto.setPublishList(cpList);
				}else{
					adComboPublishNumberDto.setPublishCount(0);
					adComboPublishNumberDto.setUnPublishCount(1);
					adComboPublishNumberDto.setUnPublishList(cpList);
				}
			   comboMap.put(entity.getAdType().getId(), adComboPublishNumberDto);
			}
		}
		for (String key : comboMap.keySet()) {
			AdComboPublishNumberDto adComboPublishNumberDto1 = new AdComboPublishNumberDto();
			AdType adtype = typeDao.get(key);
			AdTypeUtils.getLocaleAdType(adtype);
			adComboPublishNumberDto1.setAdType(adtype);
			adComboPublishNumberDto1.setPublishCount(comboMap.get(key).getPublishCount());
			adComboPublishNumberDto1.setUnPublishCount(comboMap.get(key).getUnPublishCount());
			adComboPublishNumberDto1.setPublishList(comboMap.get(key).getPublishList());
			adComboPublishNumberDto1.setUnPublishList(comboMap.get(key).getUnPublishList());
			adComboList.add(adComboPublishNumberDto1);
		}
		return adComboList;
	}

	public Page<AdComboPublishNumberDto> findComboRelease(
			Page<AdComboPublishNumberDto> page, AdComboPublishNumberDto entity) {
		List<AdComboPublishNumberDto> list = this.getAdComboPublishNumber(entity);
		int count = list.size();
		page.setList(list);
		page.setCount(count);
		return page;
	}
	
	public Page<AdComboPublishNumber> findComboReleaseList(
			Page<AdComboPublishNumber> page, AdComboPublishNumberDto dto,String type) {
		Integer startNum = (page.getPageNo()-1)*page.getPageSize();
		Map<String,Object> map = new HashMap<String,Object>();
		if(dto.getAdType()!=null){
			map.put("typeId", dto.getAdType().getId());			
		}else{
			map.put("typeId",null);
		}
		map.put("startDate", dto.getStartDate());
		map.put("endDate", dto.getEndDate());
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboRelease",type);
		map.put("startNum",startNum);
		map.put("pageSize",page.getPageSize());
		List<AdComboPublishNumber> list = mybatisDao.getAdComboPublishNumber(map);     
		for (AdComboPublishNumber entity : list) {
			entity.setAdCombo(comboService.get(entity.getAdCombo().getId()));
			entity.setAdvertiser(advertiserDao.get(entity.getAdvertiser().getId()));
			AdType adtype = typeDao.get(entity.getAdType().getId());
			AdTypeUtils.getLocaleAdType(adtype);
			entity.setAdType(adtype);
		}
		int count = mybatisDao.getAdComboPublishCount(map);
		page.setList(list);
		page.setCount(count);
		return page;
	}
	public Page<AdComboPublishNumber> findComboUnReleaseList(
			Page<AdComboPublishNumber> page, AdComboPublishNumberDto dto) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(dto.getAdType()!=null){
			map.put("typeId", dto.getAdType().getId());			
		}else{
			map.put("typeId",null);
		}
		map.put("startDate", dto.getStartDate());
		map.put("endDate", dto.getEndDate());
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("comboRelease","=");
		List<AdComboPublishNumber> list = mybatisDao.getAdComboPublishNumber(map);       
		page.setList(list);
		page.setCount(list.size());
		return page;
	}
	
	public String getAdComboSellCount(Map<String,Object> map){
		List<AdComboSellCountDto> list =  mybatisDao.getAdComboSellCount(map);		
		List<AdComboSellCountDto> newList = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			AdComboSellCountDto adComboSellCountDto = list.get(i);
			AdComboSellCountDto dto = new AdComboSellCountDto();
			dto.setSellDate(adComboSellCountDto.getSellDate());
			if(i==0){
				dto.setSellCount(adComboSellCountDto.getSellCount());				
			}else{
				dto.setSellCount(newList.get(i-1).getSellCount()+adComboSellCountDto.getSellCount());			
			}
			newList.add(dto);
		}
		return JsonMapper.toJsonString(newList);
	}
	
	public AdSell fingAdSellByCombo(Map<String,Object> map){
		List<AdSell> list = mybatisDao.fingAdSellByCombo(map);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询各类型广告中广告商占比
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AdvtiserRateDTO> findAdvtiserRateInAdTypes(Date startDate,Date endDate){
			List<AdvtiserRateDTO> resultList = new ArrayList<AdvtiserRateDTO>();
			//查出与查询时间段相关的所有销售记录
			AdSell adSellParam = new AdSell();
			adSellParam.setStartDate(startDate);
			adSellParam.setEndDate(endDate);
			List<AdSell> sellList = mybatisDao.findAdSellByAdType(adSellParam);
		    //按广告类型分类 每个类型含多个广告商多条销售记录
			Multimap<String,AdSell> adTypeMultimap = ArrayListMultimap.create();
			Map<String,String> adTypeMap = Maps.newHashMap();
			Map<String,String> advMap = Maps.newHashMap();
			Set<String> adTypeKeySet = new HashSet<>();
			for(AdSell adSell : sellList){
				adTypeMultimap.put(adSell.getAdCombo().getAdType().getId(),adSell);
				adTypeKeySet.add(adSell.getAdCombo().getAdType().getId());
				adTypeMap.put(adSell.getAdCombo().getAdType().getId(),adSell.getAdCombo().getAdType().getTypeName());
			}
			//分广告商统计各类型中 广告商购买的时长信息
			for(String key : adTypeKeySet){
				//该类型 各广告商购买时长map
				Map<String,Integer> advertiserTimeMap = Maps.newHashMap();
				Collection<AdSell> sameTypeAdSells = adTypeMultimap.get(key);
				//该广告类型所有广告商
				for(AdSell sameTypeAdSell : sameTypeAdSells){
					int days = 0;
					if(null == advertiserTimeMap.get(sameTypeAdSell.getAdvertiser().getId())){
						 days = getAdComboDays(startDate,endDate,
								sameTypeAdSell.getStartDate(),sameTypeAdSell.getEndDate());
					}else{
						int oldDays =  advertiserTimeMap.get(sameTypeAdSell.getAdvertiser().getId());
						 days = oldDays+getAdComboDays(startDate,endDate,
								 sameTypeAdSell.getStartDate(),sameTypeAdSell.getEndDate());
					}
					advertiserTimeMap.put(sameTypeAdSell.getAdvertiser().getId(),days);
					advMap.put(sameTypeAdSell.getAdvertiser().getId(),sameTypeAdSell.getAdvertiser().getName());
				}
				for(Map.Entry<String,Integer> entry:advertiserTimeMap.entrySet()){
					AdvtiserRateDTO dto = new AdvtiserRateDTO();
					dto.setAdTypeId(key);
					dto.setAdTypeName(adTypeMap.get(key));
					dto.setAdvId(entry.getKey());
					dto.setAdvName(advMap.get(entry.getKey()));
					dto.setCount(entry.getValue());
					resultList.add(dto);
				}
			}
		System.out.println(JSONObject.toJSONString(resultList));
			return resultList;
	}

    /**
     * 查询某类型广告销售记录
     * @param page
     * @param sell
     * @return
     * @throws Exception
     */

    public Page<AdSell> findAdSellByAdType(Page<AdSell> page,
                                                   AdSell sell) throws Exception {
        List<AdSell> list = mybatisDao.findAdSellByAdType(sell);
		if(list.size()==0&&!sell.getAdCombo().getStartHour().equals(null)){
			sell.setHistory(1);
			list = mybatisDao.findAdSellByAdType(sell);
		}
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
            	try{
            		String typeId = list.get(i).getAdCombo().getAdType().getId();
					if(StringUtils.isNotEmpty(typeId)){
						AdType localetype = AdTypeUtils.get(typeId);
						list.get(i).getAdCombo().setAdType(localetype);
					}
				}catch (Exception e){
					System.out.println("销售记录"+list.get(i).getId()+"更新AdType异常");
				}
            }
            page.setList(list);
        }
        return page;
    }

	/**
	 * 查询一段时间内各类型广告可运营时间和售出的时间
	 * 时间单位： 小时
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    public List<AdComboSellTimeDTO> findAdComboSellAndValidTime(Date startDate,Date endDate){
    	List<AdComboSellTimeDTO> list = Lists.newArrayList();
		AdSell adSellParam = new AdSell();
		adSellParam.setStartDate(startDate);
		adSellParam.setEndDate(endDate);
    	//查询出时间段内存在的可运营套餐
		List<AdCombo> adComboList = comboService.findListByTimeIntersection(startDate,endDate);
		//查询出时间段内已售出的套餐
		List<AdSell> adSellList = mybatisDao.findAdSellByAdType(adSellParam);
		Multimap<String, AdCombo> validMultiMap = ArrayListMultimap.create() ;
		for(AdCombo validAdCombo : adComboList) {
			validMultiMap.put( validAdCombo.getAdType().getId(), validAdCombo) ;
		}
		Multimap<String, AdSell> sellMultiMap = ArrayListMultimap.create() ;
		for(AdSell adSell : adSellList) {
			if(adSell.getAdCombo()!=null){
				sellMultiMap.put( adSell.getAdCombo().getAdType().getId(), adSell) ;
			}
		}
		Set<String> keys = validMultiMap.keySet();
		for(String key:keys){
			if(key!=null&&!"null".equals(key)){
				Integer totalValidHours = 0;
				Integer totalSellHours = 0;
				Collection<AdCombo> sameTypeValidAdCombos = validMultiMap.get(key);
				for(AdCombo combo : sameTypeValidAdCombos){
					Integer days = getAdComboDays(startDate,endDate,combo.getValidStartTime(),combo.getValidEndTime());
					Integer hours = getAdComboHours(combo);
					totalValidHours += (days+1)* hours;
				}
				Collection<AdSell> sameTypeSellAdSells = sellMultiMap.get(key);
				for(AdSell adSell : sameTypeSellAdSells){
					Integer days = getAdComboDays(startDate,endDate,adSell.getStartDate(),adSell.getEndDate());
					Integer hours = getAdComboHours(adSell.getAdCombo());
					totalSellHours += (days+1)* hours;
				}
				AdComboSellTimeDTO sellTimeDTO = new AdComboSellTimeDTO(key,totalValidHours,totalSellHours);
				list.add(sellTimeDTO);
			}
		}

		//计算每条数据时间
		//return mybatisDao.findAdComboSellAndValidTime(startDate,endDate);
		return list;
	}

	/**
	 * 计算实际天数
	 * @param queryStartDate
	 * @param queryEndDate
	 * @param dbStartDate
	 * @param dbEndDate
	 * @return
	 */
	private Integer getAdComboDays(Date queryStartDate,Date queryEndDate,Date dbStartDate,Date dbEndDate){
		Integer daysDiff = 0;
		//查询开始时间或者结束时间为空 以套餐开始结束时间计算
		if(queryStartDate==null||queryEndDate==null){
			return DateUtils.daysBetween(dbStartDate,dbEndDate);
		}
		//查询开始时间小于或等于套餐开始时间 查询结束时间大于或等于套餐结束时间  以套餐开始结束时间计算
		if(queryStartDate.getTime()<=dbStartDate.getTime()&&queryEndDate.getTime()>=dbEndDate.getTime()){
			return DateUtils.daysBetween(dbStartDate,dbEndDate);
		}
		//查询开始时间大于或等于套餐开始时间 查询结束时间小于或等于套餐结束时间  以查询开始结束时间计算
		if(queryStartDate.getTime()>=dbStartDate.getTime()&&queryEndDate.getTime()<=dbEndDate.getTime()){
			return DateUtils.daysBetween(queryStartDate,queryEndDate);
		}
		//查询开始时间小于等于套餐开始时间 查询结束时间大于等于套餐开始时间且小于等于套餐结束时间  开始时间以套餐开始时间计算，结束时间以查询结束时间计算
		if(queryStartDate.getTime()<=dbStartDate.getTime()&&queryEndDate.getTime()>=dbStartDate.getTime()&&queryEndDate.getTime()<=dbEndDate.getTime()){
			return DateUtils.daysBetween(dbStartDate,queryEndDate);
		}
		//查询开始时间大于等于套餐开始时间且小于等于套餐结束时间 查询结束时间大于等于套餐结束时间  开始时间以查询开始时间计算，结束时间以套餐结束时间计算
		if(queryStartDate.getTime()>dbStartDate.getTime()&&queryEndDate.getTime()<dbEndDate.getTime()){
			return DateUtils.daysBetween(queryStartDate,dbEndDate);
		}
		return daysDiff;
	}

	/**
	 * 获取套餐每天播放的时间
	 * 单位： 小时
	 * @param adCombo
	 * @return
	 */
	private Integer getAdComboHours(AdCombo adCombo){
		Integer hourDiff = 24;
		Integer minutesDiff = 0;
		Integer secondDiff = 0;
		if(adCombo.getStartHour()!=null&&adCombo.getEndHour()!=null){
			hourDiff = adCombo.getEndHour() - adCombo.getStartHour();
		}
		if(adCombo.getStartMinutes()!=null&&adCombo.getEndMinutes()!=null){
			minutesDiff = adCombo.getEndMinutes() - adCombo.getStartMinutes();
		}
		if(adCombo.getStartSecond()!=null&&adCombo.getEndSecond()!=null){
			secondDiff = adCombo.getEndSecond() - adCombo.getStartSecond();
		}
		return (hourDiff*3600+minutesDiff*60+secondDiff)/3600;
	}
}
