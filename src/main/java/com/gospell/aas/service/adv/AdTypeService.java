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

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.security.SystemAuthorizingRealm;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.repository.hibernate.adv.AdTypeDao;
import com.gospell.aas.repository.mybatis.adv.IAdTypeDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;

@Service
@Transactional(readOnly = true)
public class AdTypeService extends BaseService {

	@Autowired
	private AdTypeDao thisDao;

	@Autowired
	private IAdTypeDao mybatisDao;

	@Autowired
	private SystemAuthorizingRealm systemRealm;

	public AdType get(String id) {
		return thisDao.get(id);
	}

	public AdType findById(String id) {
		AdType type = new AdType();
		type.setId(id);
		return mybatisDao.find(type);
	}

	public AdType getAdType(String id) {
		List<AdType> list = findAllAdType();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdType type = list.get(i);
				if (type.getId().equals(id)) {
					return type;
				}
			}
		}
		return null;
	}

	public List<AdType> findAll() {
		return thisDao.findAll();
	}

	public List<AdType> findAllByParent() {
 
 
		List<AdType> list = thisDao.findAllByParent(AdType.getzeroAdTypeId());
		thisDao.clear();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).getId();
				AdType type = AdTypeUtils.get(id);
				list.get(i).setTypeName(type.getTypeName());
			}
		}
		thisDao.clear();
		return list;
	}
	
 

	public List<AdType> findAllAdType() {
		return UserUtils.getAdTypeList();
	}
	/**
	 * 根据广告类型ID查询广告类型
	 * 
	 * @param page
	 *            前台分页参数
	 * @param entity
	 *            广告发送器
	 * @return
	 */
	public List<AdType> findTypeByIdList(List<String> typeIdList) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		dc.add(Restrictions.in("typeId", typeIdList));
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return thisDao.find(dc);
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
	public Page<AdType> find(Page<AdType> page, AdType entity) {
		DetachedCriteria dc = thisDao.createDetachedCriteria();
		if (StringUtils.isNotBlank(entity.getTypeId())) {
			dc.add(Restrictions.like("typeId", "%" + entity.getTypeId() + "%"));
		}
		if (StringUtils.isNotBlank(entity.getTypeName())) {
			dc.add(Restrictions.like("typeName", "%" + entity.getTypeName()
					+ "%"));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("typeId"));
		return thisDao.find(page, dc);
	}

	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void save(AdType entity) throws Exception {

		// thisDao.save(entity);

		entity.setParent(this.getAdType(entity.getParent().getId()));
		if (StringUtils.isBlank(entity.getId())) {
			if (entity.getParent() != null
					&& !entity.getParent().getId()
							.equals(AdType.getzeroAdTypeId())) {
				entity.setId(entity.getParent().getId() + "-"
						+ entity.getTypeId());
			} else {
				entity.setId(entity.getTypeId());
			}

		}
		String oldParentIds = entity.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		entity.setParentIds(entity.getParent().getParentIds()
				+ entity.getParent().getId() + ",");
		thisDao.clear();
		thisDao.save(entity);
		// 更新子节点 parentIds
		List<AdType> list = thisDao.findByParentIdsLike("%," + entity.getId()
				+ ",%");
		for (AdType e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds,
					entity.getParentIds()));
		}
		thisDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ADTYPE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADTYPE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADTYPE_LIST);
	}

	/**
	 * 保存广告类型
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public void delete(AdType entity) throws ServiceException {
		// entity.setDelFlag(AdType.DEL_FLAG_DELETE);
		// thisDao.save(entity);
		thisDao.deleteById(entity.getId(), "%," + entity.getId() + ",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ADTYPE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ADTYPE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ADTYPE_LIST);

	}

	/**
	 * 获取广告类型
	 * 
	 * @param adTypeIds
	 *            广告类型ID
	 * @return
	 */
	public List<AdType> findAdTypeByIds(List<String> adTypeIds) {
		return thisDao.findAdTypeByIds(adTypeIds, AdType.getzeroAdTypeId());
	}

	public AdType findAdTypeByTypeId(String typeId, String parentId) {
		return thisDao.findAdTypeByTypeId(typeId, parentId);
	}
	
	public AdType findAdTypeByTypeIdAndNotChannel(String typeId,Integer isFlag) {
		return thisDao.findAdTypeByTypeIdAndNotChannel(typeId,isFlag);
	}

	/**
	 * 判断同一级是否重复
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean checkTypeIdIsRepeat(Map<String, Object> map)
			throws Exception {
		String id = (String) map.get("id");
		String oldParentId = (String) map.get("oldParentId");
		String newParentId = (String) map.get("newParentId");
		String oldTypeId = (String) map.get("oldTypeId");
		String newTypeId = (String) map.get("newTypeId");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		queryMap.put("parentId", newParentId.trim());
		queryMap.put("typeId", newTypeId.trim());
		if (StringUtils.isBlank(id)) {// 表示新增广告类型
			if (null != newParentId && null != newTypeId) {
				AdType type = mybatisDao.findByParentIdAndTypeId(queryMap);
				if (null != type) {
					return false;
				} else {
					return true;
				}
			}
		} else {// 表示修改广告类型
			if (newParentId != null && newParentId.equals(oldParentId)
					&& newTypeId != null && newTypeId.equals(oldTypeId)) {
				return true;
			} else {
				if (null != newParentId && null != newTypeId) {
					AdType type = mybatisDao.findByParentIdAndTypeId(queryMap);
					if (null != type) {
						return false;
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}

}
