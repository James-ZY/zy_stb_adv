package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdDistrictCategory;

/**
 * 广告分类数据操作层
 * 
 * @author Administrator
 * 
 */
@Repository
public class AdDistrictCategoryDao extends BaseDao<AdDistrictCategory> {

	public List<AdDistrictCategory> findAllByParent(String parentId) {
		return find("from AdDistrictCategory where delFlag = :p1 and parent.id= :p2 ",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL, parentId));
	}

	@Override
	public List<AdDistrictCategory> findAll() {
		return find(
				"from AdDistrictCategory where delFlag = :p1 order by categoryId asc",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}

	public List<AdDistrictCategory> findByParentIdsLike(String parentIds) {
		return find("from AdDistrictCategory where parentIds like :p1 and delFlag =:p2", new Parameter(
				parentIds,BaseEntity.DEL_FLAG_NORMAL));
	}

	/**
	 * 通过category查询数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AdDistrictCategory findAdDistrictCategoryByCateGoryId(String categoryId) {
		return getByHql("from AdDistrictCategory where delFlag=:p1 and categoryId = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL,categoryId));
	}

	public List<AdDistrictCategory> findByRange(List<String> ls) {
		return find("from AdDistrictCategory where categoryId in (:p1) and delFlag =:p2 order by categoryId asc", new Parameter(
				ls,BaseEntity.DEL_FLAG_NORMAL));
	}
}
