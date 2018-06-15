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

	public List<AdDistrictCategory> findAllByParent(String parentId,String type) {
		return find("from AdDistrictCategory where delFlag = :p1 and parent.id= :p2 and type= :p3 ",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL, parentId,type));
	}

	@Override
	public List<AdDistrictCategory> findAll() {
		return find(
				"from AdDistrictCategory where delFlag = :p1 and type= :p2 order by id asc",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}

	public List<AdDistrictCategory> findAllByType(String type) {
		return find(
				"from AdDistrictCategory where delFlag = :p1 and ( type = :p2 or type = 'default') order by id asc",
				new Parameter(BaseEntity.DEL_FLAG_NORMAL,type));
	}

	public List<AdDistrictCategory> findByParentIdsLike(String parentIds,String type) {
		return find("from AdDistrictCategory where parentIds like :p1 and delFlag =:p2 and type= :p3", new Parameter(
				parentIds,BaseEntity.DEL_FLAG_NORMAL,type));
	}

	/**
	 * 通过category查询数据
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AdDistrictCategory findAdDistrictCategoryByCateGoryId(String categoryId,String type) {
		return getByHql("from AdDistrictCategory where delFlag=:p1 and id = :p2 and type= :p3", new Parameter(BaseEntity.DEL_FLAG_NORMAL,categoryId,type));
	}

	public List<AdDistrictCategory> findByRange(List<String> ls,String type) {
		return find("from AdDistrictCategory where id in (:p1) and delFlag =:p2 and type= :p3 order by id asc", new Parameter(
				ls,BaseEntity.DEL_FLAG_NORMAL,type));
	}
}
