package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdCategory;

/**
 * 广告分类数据操作层
 * 
 * @author Administrator
 * 
 */
@Repository
public class AdCategoryDao extends BaseDao<AdCategory> {

	public List<AdCategory> findAllByParent(String parentId) {
		return find("from AdCategory where delFlag = :p1 and parent.id= :p2 ",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL, parentId));
	}

	@Override
	public List<AdCategory> findAll() {
		return find(
				"from AdCategory where delFlag = :p1 order by categoryId asc",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}

	public List<AdCategory> findByParentIdsLike(String parentIds) {
		return find("from AdCategory where parentIds like :p1 and delFlag =:p2", new Parameter(
				parentIds,BaseEntity.DEL_FLAG_NORMAL));
	}

	/**
	 * 通过category查询数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AdCategory findAdCategoryByCateGoryId(String categoryId) {
		return getByHql("from AdCategory where delFlag=:p1 and categoryId = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL,categoryId));
	}

}
