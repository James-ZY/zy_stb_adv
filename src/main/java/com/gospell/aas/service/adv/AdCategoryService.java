package com.gospell.aas.service.adv;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.security.SystemAuthorizingRealm;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.adv.AdCategory;
import com.gospell.aas.repository.hibernate.adv.AdCategoryDao;
import com.gospell.aas.repository.mybatis.adv.IAdCategoryDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdCategoryService extends BaseService{
	
	@Autowired
	private AdCategoryDao thisDao;
 
	@Autowired
	private IAdCategoryDao categoryDao;
	
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
 
	//private Logger logger = LoggerFactory.getLogger(AdCategory.class);

	public AdCategory get(String id) {
		List<AdCategory> list= UserUtils.getCateGoryList();
		if(null != list && list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				AdCategory category = list.get(i);
				if(id != null && id.equals(category.getId())){
					return category;
				}
			}
		}
		return null;
	}

 
	public List<AdCategory> findAll() {
		return thisDao.findAll();
	}
	
	public List<AdCategory> findAllByParent() {
		return thisDao.findAllByParent(AdCategory.getzeroAdCategoryId());
	}

	public List<AdCategory> findAllAdCategory() {
		return UserUtils.getCateGoryList();
	}

	/**
	 * 根据条件查询广告类型
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public Page<AdCategory> find(Page<AdCategory> page, AdCategory entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		if (StringUtils.isNotBlank(entity.getCategoryId())) {
			dc.add(Restrictions.like("categoryId", "%" + entity.getCategoryId() + "%"));
		}
		if (StringUtils.isNotBlank(entity.getCategoryName())) {
			dc.add(Restrictions.like("categoryName", "%" + entity.getCategoryName()
					+ "%"));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("categoryId"));
		return thisDao.find(page, dc);
	}

	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdCategory entity) throws Exception {

		// thisDao.save(entity);

		entity.setParent(this.get(entity.getParent().getId()));
		if (StringUtils.isBlank(entity.getId())) {
		 
				entity.setId(entity.getCategoryId());
		 
			entity.setCreateBy(UserUtils.getUser());
			entity.setCreateDate(new Date());
		}else{
			String id =entity.getId();
			if(!id.equals(entity.getCategoryId())){
				entity.setId(entity.getCategoryId());
			}
		}
		String oldParentIds = entity.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		entity.setParentIds(entity.getParent().getParentIds()
				+ entity.getParent().getId() + ",");
		thisDao.clear();
		thisDao.save(entity);
		// 更新子节点 parentIds
		List<AdCategory> list = thisDao.findByParentIdsLike("%," + entity.getId()
				+ ",%");
		for (AdCategory e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds,
					entity.getParentIds()));
		}
		thisDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADCATEGORY_LIST);
	}

	/**
	 * 删除广告分类
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdCategory entity) throws  Exception {
		thisDao.deleteById(entity.getId(), "%," + entity.getId() + ",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADCATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADCATEGORY_LIST);

	}

	/**
	 * 根据分类ID查询广告分类
	 * @param categoryId
	 * @return
	 */
	public AdCategory findAdCategoryByCateGoryId(String categoryId){
		return thisDao.findAdCategoryByCateGoryId(categoryId);
	}
 
	public synchronized String getNewCategoryId(){
	   Integer categoryId = categoryDao.findMaxAdCategoryId();
	   String newCategoryId = "";
	   int len = String.valueOf(categoryId).length();
	   if(len==1){
		   newCategoryId = "000"+categoryId;  
	   }else if(len==2){
		   newCategoryId = "00"+categoryId; 
	   }else if(len==3){
		   newCategoryId = "00"+categoryId; 
	   }
	   return newCategoryId;
	}

}
