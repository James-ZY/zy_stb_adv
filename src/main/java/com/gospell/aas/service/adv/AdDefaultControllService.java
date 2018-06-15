package com.gospell.aas.service.adv;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.FileUtils;
import com.gospell.aas.common.utils.ImageUtil;
import com.gospell.aas.common.utils.PropertiesReadUtil;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.common.utils.adv.rollimage.CharUtil;
import com.gospell.aas.common.utils.adv.rollimage.ImageDTO;
import com.gospell.aas.common.utils.adv.rollimage.PictureUtil;
import com.gospell.aas.common.utils.ffmpeg.FfmpegUtil;
import com.gospell.aas.dto.adv.AdResourceDTO;
import com.gospell.aas.dto.adv.FfprobeVedioInfo;
import com.gospell.aas.dto.adv.ImageInfo;
import com.gospell.aas.entity.adv.AdDefaultControll;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.hibernate.adv.AdDefaultControllDao;
import com.gospell.aas.repository.hibernate.adv.AdelementDao;
import com.gospell.aas.repository.mybatis.adv.IAdDefaultControlDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdDefaultControllService extends BaseService {

	@Autowired
	private AdDefaultControllDao thisDao;
	@Autowired
	private IAdDefaultControlDao mybatisDao;
	@Autowired
	private AdelementDao adelementDao;

	public AdDefaultControll get(String id) {
		return thisDao.get(id);
	}

	public List<AdDefaultControll> findAll() {
		return thisDao.findAll();
	}

	/**
	 * 根据条件查询广告素材
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<AdDefaultControll> find(Page<AdDefaultControll> page, AdDefaultControll entity) {
 
		if (StringUtils.isEmpty(page.getOrderBy())) {
			page.setOrderBy("t.ad_type_id ASC,c.flag asc,c.create_date DESC");
		}
		entity.setPage(page);
        List<AdDefaultControll> list = mybatisDao.findList(entity);
        if(null != list && list.size() >0){
			for (int i = 0; i < list.size(); i++) {
				AdType adtype = list.get(i).getAdType();
				AdTypeUtils.getLocaleAdType(adtype);
				
			 
			}
		}
        page.setList(list);
        return page;
	}
	

	/**
	 * 根据条件查询需要审核的广告素材
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<AdDefaultControll> findAudit(Page<AdDefaultControll> page, AdDefaultControll entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		dc.createAlias("adType", "adType");
		dc.createAlias("updateBy", "user");
		User user = UserUtils.getUser();
		if (null != entity.getAdType()) {
			dc.add(Restrictions.eq("adType.id", entity.getAdType().getId()));
		}
		if (null != entity.getStatus()) {
	 
				dc.add(Restrictions.eq("status", entity.getStatus()));
		 

		}else{
			dc.add(Restrictions.eq("updateBy.id", user.getId()));
			List<Integer> list = Lists.newArrayList();
			list.add(AdDefaultControll.STATUS_WAIT);
			list.add(AdDefaultControll.STATUS_PASS);
			list.add(AdDefaultControll.STATUS_FALI);
			dc.add(Restrictions.or(Restrictions.in("status", list)));
		}
		/**
		 * 这个地方还需要添加当前用户只能审查自己旗下的广告商
		 */
		//只能查看自己下级
		if (!user.isAdmin()) {
			Office office = user.getOffice();
			dc.createAlias("updateBy.office", "office");
			if (office != null && StringUtils.isNotBlank(office.getId())) {
				dc.add(Restrictions.or(Restrictions.eq("office.id", user.getOffice().getId()),
						Restrictions.like("office.parentIds", "%," + user.getOffice().getId() + ",%")));
			}
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("status"));

		return thisDao.find(page, dc);
	}

	/**
	 * 当素材关联的广告有处于审核中的，审核通过的，投放中的，或者是投放结束的都不可以修改
	 */
	public Integer checkIsUpdate(String id){
		int count = 0;
		if(StringUtils.isNotBlank(id)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			map.put("id", id);
			List<Integer> statusList = Lists.newArrayList();
			statusList.add(Adelement.ADV_STATUS_CLAIM);
			statusList.add(Adelement.ADV_STATUS_PASS);
			statusList.add(Adelement.ADV_STATUS_SHOW);
			statusList.add(Adelement.ADV_STATUS_END);
			map.put("list", statusList);
			
			  count = mybatisDao.getAdelementCountById(map);
		}
		return count;
	}
	
	/**
	 * 保存素材
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save1(AdDefaultControll entity) throws Exception {
		thisDao.clear();
		thisDao.save(entity);
	}
	/**
	 * 保存素材
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdDefaultControll entity) throws Exception {
		 
		thisDao.clear();
		String path = entity.getPath();
		if (StringUtils.isBlank(entity.getId())) {// 新增的情况
			List<AdDefaultControll> list = null;
			if (StringUtils.isNotBlank(path)) {
				list = Lists.newArrayList();
				String[] filePaths = path.split(",");
				String imangePaths = entity.getVedioImagePath();
				String[] vedioImages = null;
				if (StringUtils.isNotBlank(imangePaths)) {
					vedioImages = imangePaths.split(",");
				}
				for (int i = 0; i < filePaths.length; i++) {
					AdDefaultControll control = new AdDefaultControll();
					String filePath = filePaths[i];

					if (AdDefaultControll.RESOURCE_VEDIO.equals(entity.getResourceType())) {
						if (null != vedioImages && vedioImages.length == filePaths.length) {
							String fileImagePath = vedioImages[i];
							control.setFileImagePath(fileImagePath);
						}
						
						vedioResource(filePath, control);
					} else if(AdDefaultControll.RESOURCE_IMAGE.equals(entity.getResourceType())){
						imageResource(filePath, control);
					}else{
						throw new Exception();
					}
					control.setAdType(entity.getAdType());
					control.setAdNetwork(entity.getAdNetwork());
					control.setResourceType(entity.getResourceType());
					control.setFlag(entity.getFlag());
					list.add(control);
				}
				thisDao.save(list);
			}
		} else {// 修改

			if (StringUtils.isNotBlank(path)) {// 广告商修改素材的时候调用
				String[] filePaths = path.split(",");
				String imangePaths = entity.getVedioImagePath();
				String[] vedioImages = null;
				if (StringUtils.isNotBlank(imangePaths)) {
					vedioImages = imangePaths.split(",");
				}
				for (int i = 0; i < 1; i++) {
					String filePath = filePaths[i];

					if (AdDefaultControll.RESOURCE_VEDIO.equals(entity.getResourceType())) {
						if (null != vedioImages && vedioImages.length == filePaths.length) {
							String fileImagePath = vedioImages[i];
							entity.setFileImagePath(fileImagePath);
						}
						vedioResource(filePath, entity);
					} else {
						imageResource(filePath, entity);
					}
				}

				entity.setStatus(AdDefaultControll.STATUS_WAIT);
//				String preViewVedioPath = entity.getPreviewVedioPath();//把预览的时候ts转换的MP4删除，因为一旦修改素材预览的时候必须重新把ts转换成新的MP4进行预览
//				if(com.gospell.aas.common.utils.StringUtils.isNotBlank(preViewVedioPath)){
//		   			String deletePreviewVedioPath = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath()+preViewVedioPath;
//		   			FileUtils.deleteFile(deletePreviewVedioPath);
//					entity.setPreviewVedioPath(null);//修改的素材的视频预览转换的MP4设置为null
//				}
			
				thisDao.save(entity);
			} else {// 审核时调用
				thisDao.save(entity);
			}
		}

	}

	/**
	 * 通过视频的相关信息封装资源素材
	 * 
	 * @param filePath
	 * @param control
	 */
	public void vedioResource(String filePath, AdDefaultControll control) {
		String filename = FileUtils.getUploadFileRealPath() + filePath;
		FfprobeVedioInfo info = FfmpegUtil.getFfprobeVedioInfo("",filename,"picture");
		if (null != info) {
			control.setFilePath(filePath);
			control.setFileFormat(info.getFormat());
			control.setFileSize(String.valueOf(info.getFileSize()));
			if(null != info.getDuration()){
				control.setDuration(Integer.parseInt(String.valueOf(info.getDuration())));				
			}else{
				control.setDuration(0);	
			}
			control.setWidth(info.getWidth());
			control.setHeight(info.getHeight());
		}

	}

	/**
	 * 通过图片的相关信息封装资源素材
	 * 
	 * @param filePath
	 * @param control
	 */
	public void imageResource(String filePath, AdDefaultControll control) {
		String filename = FileUtils.getUploadFileRealPath() + filePath;
		ImageInfo info = ImageUtil.getImageInfoByBufferedImage(filename);
		if (null != info) {
			control.setFilePath(filePath);
			control.setFileFormat(info.getFileFormat());
			control.setFileSize(String.valueOf(info.getFileSize()));
			control.setWidth(info.getWidth());
			control.setHeight(info.getHeight());
		}

	}

	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdDefaultControll entity) throws Exception {
		entity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		thisDao.save(entity);

	}
	

 
	public List<AdResourceDTO> getControlByTypeId(Map<String,Object> map) throws Exception{
	 
 		List<AdDefaultControll>  list = mybatisDao.getControlByTypeId(map);
		Adelement adem =null;
		String param = null;
		List<AdResourceDTO> dtoList = null;
		if (map.get("typeId").toString().equals("1") || map.get("typeId").toString().equals("2") 
						||map.get("typeId").toString().equals("4") ) {		
			if(map.get("adelementId") != null && map.get("adelementId") != ""){
				adem = adelementDao.get((String) map.get("adelementId"));
				if(adem != null){
					if(map.get("resolution").toString().equals("1")){
						param = adem.getHdShowParam();
						dtoList = convertResourceDto(param, list);
					}else{
						param = adem.getSdShowParam();
						dtoList = convertResourceDto(param, list);
					}				
			     }
			}else if(map.get("resolution").toString().equals("1") && map.get("hdShowParam") != null && map.get("hdShowParam") != ""){
				param = (String) map.get("hdShowParam");
				dtoList = convertResourceDto(param, list);
			}else if(map.get("resolution").toString().equals("0") && map.get("sdShowParam") != null && map.get("sdShowParam") != ""){
				param = (String) map.get("sdShowParam");
				dtoList = convertResourceDto(param, list);
			}else{
				if(null != list && list.size() >0){
					dtoList = Lists.newArrayList();
					for (AdDefaultControll adDefaultControll : list) {
						AdResourceDTO dto = new AdResourceDTO();
						if(StringUtils.isNotBlank(adDefaultControll.getFileImagePath())){
							dto.setPath(adDefaultControll.getFileImagePath());
						}else{
							dto.setPath(adDefaultControll.getFilePath());
						}
						dto.setVedioImagePath(adDefaultControll.getFileImagePath());
						dto.setId(adDefaultControll.getId());
						dtoList.add(dto);
					}
				}
			}
		}else{
			if(null != list && list.size() >0){
				dtoList = Lists.newArrayList();
				for (AdDefaultControll adDefaultControll : list) {
					AdResourceDTO dto = new AdResourceDTO();
					if(StringUtils.isNotBlank(adDefaultControll.getFileImagePath())){
						dto.setPath(adDefaultControll.getFileImagePath());
					}else{
						dto.setPath(adDefaultControll.getFilePath());
					}
					
					dto.setVedioImagePath(adDefaultControll.getFileImagePath());
					dto.setId(adDefaultControll.getId());
					dtoList.add(dto);
				}
			}
			
		}		
		return dtoList;
	}

	private List<AdResourceDTO>  convertResourceDto(String param,List<AdDefaultControll>  list){
		Map<String,Integer> orderMap = new HashMap<String,Integer>();
		Map<String,Integer> timeMap = new HashMap<String,Integer>();
		List<AdResourceDTO> dtoList = null;
		if(StringUtils.isNotBlank(param)){
			String[]	hdParam1	= param.split(",");
			for(int i=0;i<hdParam1.length;i++){
				orderMap.put(hdParam1[i].split("@")[0], i+1);
				timeMap.put(hdParam1[i].split("@")[0], Integer.parseInt(hdParam1[i].split("@")[1]));
			}
		}else{
			if(null != list && list.size() >0){
				dtoList = Lists.newArrayList();
				for (AdDefaultControll adDefaultControll : list) {
					AdResourceDTO dto = new AdResourceDTO();
					if(StringUtils.isNotBlank(adDefaultControll.getFileImagePath())){
						dto.setPath(adDefaultControll.getFileImagePath());
					}else{
						dto.setPath(adDefaultControll.getFilePath());
					}
					
					dto.setVedioImagePath(adDefaultControll.getFileImagePath());
					dto.setId(adDefaultControll.getId());
					dtoList.add(dto);
				}
			}
			return dtoList;
		}
		if(null != list && list.size() >0){
			dtoList = Lists.newArrayList();
			for (AdDefaultControll adDefaultControll : list) {
				AdResourceDTO dto = new AdResourceDTO();
				if(StringUtils.isNotBlank(adDefaultControll.getFileImagePath())){
					dto.setPath(adDefaultControll.getFileImagePath());
				}else{
					dto.setPath(adDefaultControll.getFilePath());
				}
				dto.setVedioImagePath(adDefaultControll.getFileImagePath());
				dto.setId(adDefaultControll.getId());
				if(orderMap.containsKey(adDefaultControll.getId())){
					dto.setOrder(orderMap.get(adDefaultControll.getId()));
					dto.setTime(timeMap.get(adDefaultControll.getId()));
				}
				dtoList.add(dto);
			}
		}
        Collections.sort(dtoList,new Comparator<AdResourceDTO>() {

			@Override
			public int compare(AdResourceDTO o1, AdResourceDTO o2) {
				return (o1.getOrder()==null?999:o1.getOrder().compareTo(o2.getOrder()==null?999:o2.getOrder()));
			}
		});
		return dtoList;
	}
	
	public List<AdDefaultControll> getAdDefaultControllListByids(String ids){
		if(StringUtils.isNotBlank(ids)){
			String[] list_id = ids.split(",");
			return thisDao.getAdDefaultControllByIds(list_id);
		}
		return null;
	}
	
	/**
	 * 根据Id获取文件路径
	 * @param ids
	 * @return
	 */
	public List<AdDefaultControll> getMybatisAdDefaultControllByids(List<String> ids) throws Exception{
		if(null != ids && ids.size()>0){
			return  mybatisDao.getControlByIds(ids);
			 
		}
		return null;
	}
	/**
	 * 判断素材是否被广告引用，被引用则不能删除，为了效率，值查询一条数据即可
	 * @param id
	 * @return
	 */
	public Boolean isCanDelete(String id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("id", id);
		String s = mybatisDao.getAdelementByDefaultControllId(map);
		if(StringUtils.isBlank(s)){
				return true;
		}
		return false;
	}
	/**
	 * 滚动广告生成
	 * @param image
	 * @param path
	 * @throws Exception
	 */
	public void rollImageUpload(ImageDTO image,String path) throws Exception{
		if(image == null || StringUtils.isBlank(image.getText())){
			throw new Exception();
		}
		String text = image.getText();
		if(CharUtil.isChinese(text)){
			image.setFont(PropertiesReadUtil.ROLL_CHINESE_FONT);
		}else{
			image.setFont(PropertiesReadUtil.ROLL_OTHER_LANAGUAGE_FONT);
		}
		if(image.getIsPurity() == ImageDTO.IMAGE_PURITY_YES || image.getIsPurity() == ImageDTO.IMAGE_PURITY_TSP){
			PictureUtil.graphicsImage(path, image);
		}else{
			if(StringUtils.isNotBlank(image.getBackImagePath())){
				PictureUtil.imageAddText(path, image);
			}else{
				throw new Exception();
			}
		}
		
	}

	public AdDefaultControll checkIsRepeat(Map<String, Object> map) {
		try {
			map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
			List<AdDefaultControll> list = mybatisDao.getControlByTypeId(map);
			if(list != null && list.size()>0){
				list.get(0).setControllId(list.get(0).getId());
				return list.get(0);			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
