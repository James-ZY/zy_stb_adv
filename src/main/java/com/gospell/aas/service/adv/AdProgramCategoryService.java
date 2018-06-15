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
import com.gospell.aas.entity.adv.AdProgramCategory;
import com.gospell.aas.repository.hibernate.adv.AdProgramCategoryDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdProgramCategoryService extends BaseService{
	
	@Autowired
	private AdProgramCategoryDao thisDao;
 

	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
 
	//private Logger logger = LoggerFactory.getLogger(AdProgramCategory.class);

	public AdProgramCategory get(String id) {
		List<AdProgramCategory> list= UserUtils.getProgramCateGoryList();
		if(null != list && list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				AdProgramCategory category = list.get(i);
				if(id != null && id.equals(category.getId())){
					return category;
				}
			}
		}
		return null;
	}

 
	public List<AdProgramCategory> findAll() {
		return thisDao.findAll();
	}
	
	public List<AdProgramCategory> findAllByParent() {
		return thisDao.findAllByParent(AdProgramCategory.getzeroAdProgramCategoryId());
	}

	public List<AdProgramCategory> findAllAdProgramCategory() {
		return UserUtils.getProgramCateGoryList();
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
	public Page<AdProgramCategory> find(Page<AdProgramCategory> page, AdProgramCategory entity) {
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
	public void save(AdProgramCategory entity) throws Exception {

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
		List<AdProgramCategory> list = thisDao.findByParentIdsLike("%," + entity.getId()
				+ ",%");
		for (AdProgramCategory e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds,
					entity.getParentIds()));
		}
		thisDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_PROGRAM_CATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_PROGRAM_CATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_PROGRAM_CATEGORY_LIST);
	}

	/**
	 * 删除广告分类
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdProgramCategory entity) throws  Exception {
		thisDao.deleteById(entity.getId(), "%," + entity.getId() + ",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_PROGRAM_CATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_PROGRAM_CATEGORY_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_PROGRAM_CATEGORY_LIST);

	}

	/**
	 * 根据分类ID查询广告分类
	 * @param categoryId
	 * @return
	 */
	public AdProgramCategory findAdProgramCategoryByCateGoryId(String categoryId){
		return thisDao.findAdProgramCategoryByCateGoryId(categoryId);
	}
 

}
