package com.gospell.aas.service.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.adv.RegexUtil;
import com.gospell.aas.entity.adv.Advertiser;
import com.gospell.aas.repository.hibernate.adv.AdvertiserDao;
import com.gospell.aas.repository.mybatis.adv.IAdvertiserDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdvertiserService extends BaseService {

	@Autowired
	private AdvertiserDao thisDao;
	@Autowired
	private IAdvertiserDao mybatisDao;

	public Advertiser get(String id) {
		return thisDao.get(id);
	}

	public List<Advertiser> findAll() {
		return thisDao.findAll();
	}

	/**
	 * 根据条件查询广告商
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告商
	 * @return
	 */
	public Page<Advertiser> find(Page<Advertiser> page, Advertiser entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		if (StringUtils.isNotBlank(entity.getAdvertiserId())) {
			dc.add(Restrictions.like("advertiserId", entity.getAdvertiserId()
					+ "%"));
		}
		if (StringUtils.isNotBlank(entity.getName())) {
			dc.add(Restrictions.like("name", "%" + entity.getName() + "%"));
		}
		if (null != entity.getType()) {
			dc.add(Restrictions.eq("type", entity.getType()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG,
				BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("advertiserId"));
		return thisDao.find(page, dc);
	}

	/**
	 * 保存广告商
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(Advertiser entity) throws Exception {
		thisDao.save(entity);

	}
	
	/**
	 * 保存广告商
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void saveList(List<Advertiser> entityList) throws Exception {
		thisDao.save(entityList);

	}

	/**
	 * 删除广告商
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(Advertiser entity) throws Exception {
		thisDao.save(entity);

	}

	public Advertiser findAdvertiserById(String advertiserId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("advId", advertiserId);
		return mybatisDao.findSingelAdvertiserByAdvId(map);
	}
	
	 

	/**
	 * 判断广告商在系统中有没有对应的用户，如果有，则不允许删除广告商
	 * 
	 * @param id
	 * @return 返回1 表示 该广告商在我们系统还有登录存在 返回2 表示 该广告商已经存在具有广告套餐的销售记录 返回0 可以删除
	 * @throws Exception
	 */
	public Integer isCanDelete(String id) throws Exception {
		Advertiser a = new Advertiser();
		a.setId(id);
		int count = mybatisDao.findUserIdByAdvertiserId(a);

		if (count == 0) {
			String comboList = mybatisDao.findComboIdByAdvertiserId(a);
			if (StringUtils.isBlank(comboList)) {
				return 0;
			} else {
				return 2;
			}
		} else {
			return 1;
		}
	}

	/**
	 * 检查导入的数据字段是否为null 或者是类型是否是0和1
	 * 
	 * @param list
	 * @return
	 */
	public List<Advertiser> checkImportAdvertiserIsNul(List<Advertiser> list) {
		  
		List<Advertiser> availableList = Lists.newArrayList();
		for (Advertiser advertiser : list) {
			
			String advertiserId = advertiser.getAdvertiserId();// 广告商Id
			String businessLicenseNumber = advertiser.getBusinessLicenseNumber(); // 营业执照注册号
			String industry = advertiser.getIndustry(); // 行业
			String name = advertiser.getName();// 广告商名称
			Integer type = advertiser.getType();// 0普通用户，1 vip
			String contacts = advertiser.getContacts();// 联系人
			String phone = advertiser.getPhone(); // 固定电话
			String mobile = advertiser.getMobile(); // 手机
			
			if(StringUtils.isBlank(advertiserId) || StringUtils.isBlank(businessLicenseNumber) ||
					StringUtils.isBlank(industry) || StringUtils.isBlank(name) ||type == null || (type <Advertiser.ADVERTISER_TYPE_NORMAL && type >Advertiser.ADVERTISER_TYPE_VIP) 
					||StringUtils.isBlank(contacts) || StringUtils.isBlank(phone) || StringUtils.isBlank(mobile)){
				 
			}else{
				availableList.add(advertiser);
			}
		}
		return availableList;

	}
	/**
	 * 导入的是空白数据
	 * @param list
	 * @return
	 */
	public boolean checkImportAdvertiserIsNotData(List<Advertiser> list) {
		int i =0;
		for (Advertiser advertiser : list) {
			
			String advertiserId = advertiser.getAdvertiserId();// 广告商Id
			String businessLicenseNumber = advertiser.getBusinessLicenseNumber(); // 营业执照注册号
			String industry = advertiser.getIndustry(); // 行业
			String name = advertiser.getName();// 广告商名称
			Integer type = advertiser.getType();// 0普通用户，1 vip
			String contacts = advertiser.getContacts();// 联系人
			String phone = advertiser.getPhone(); // 固定电话
			String mobile = advertiser.getMobile(); // 手机
			
			if(StringUtils.isBlank(advertiserId) && StringUtils.isBlank(businessLicenseNumber) &&
					StringUtils.isBlank(industry) && StringUtils.isBlank(name) ||type == null  
							&&StringUtils.isBlank(contacts) && StringUtils.isBlank(phone) && StringUtils.isBlank(mobile)){
				 i++;
			} 
		}
		if(i == list.size()){
			return false;
		}
		return true;

	}
	
	/**
	 * 导入的广告商数据的电话，手机是否正确，验证广告商类型
	 * @param list
	 * @return
	 */
	public List<Advertiser>  checkImportAdvertiserDataFormat(List<Advertiser> list) {
		List<Advertiser> formatList = Lists.newArrayList();
		for (Advertiser advertiser : list) {
			
			 Integer type = advertiser.getType();
			 if(type !=null){//验证类型
				 int typevalue = advertiser.getType();
				 if(typevalue !=Advertiser.ADVERTISER_TYPE_NORMAL && typevalue !=Advertiser.ADVERTISER_TYPE_VIP){
					 continue;
				 }
			 }
			 String phone= advertiser.getPhone();
			 String mobile = advertiser.getMobile();
			 boolean b = RegexUtil.isTelPhone(phone);
			 boolean b1 = RegexUtil.isMobilePhone(mobile);
			 if(b && b1){
				 formatList.add(advertiser);
			 }
			 
		}
		 
 	 return formatList;

	}
	
	/**
	 * 如果excel中有重复的广告商ID，默认只取第一条
	 * @param list
	 * @return
	 */
	public List<Advertiser>   distinctAdvtiserId(List<Advertiser> list) {
	 
		Map<String,Advertiser> map = new HashMap<String,Advertiser>();
		List<Advertiser> distinctList = Lists.newArrayList();
		for (Advertiser advertiser : list) {
			 String advId= advertiser.getAdvertiserId();
			 if(!map.containsKey(advId)){
				 map.put(advId, advertiser);
			 }
		}
		
		for (String key:map.keySet()) {
			distinctList.add(map.get(key));
		}
 	  return distinctList;

	}
	/**
	 * 数据库中已经存在的广告商ID，需要排除
	 * @return
	 */
	public List<Advertiser> databaseAlreadyExist(List<Advertiser> list){
		Map<String,Advertiser> map = new HashMap<String,Advertiser>();
		List<Advertiser> norepeatList = Lists.newArrayList();
		List<String> id_list = Lists.newArrayList();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		for (Advertiser advertiser : list) {
			 String advId= advertiser.getAdvertiserId();
			 map.put(advId, advertiser);
			 id_list.add(advId);
		}
		queryMap.put("idList", id_list);
		List<Advertiser> queryList = mybatisDao.findAdvertiserByAdvId(queryMap);
		if (queryList !=null && queryList.size() >0) {
			for (int i = 0; i < queryList.size(); i++) {
				String id = queryList.get(i).getAdvertiserId();
				if(map.containsKey(id)){
					map.remove(id);
				}
			}
		}
		if(!map.isEmpty()){
			for (String key : map.keySet()) {
				norepeatList.add(map.get(key));
			}
		}
		return norepeatList;
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public int compareListSize(List<Advertiser> sourceList,List<Advertiser> newList) {
		 int size = sourceList.size();
		 int newSize = newList.size();
		 int compare = size-newSize;
		 return compare;

	}
	
	 public synchronized Integer findMaxAdvertiserId(){
		return mybatisDao.findMaxAdvertiserId();
		 
	 }
	 

}
