package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdType;

/**
 * 广告类型数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdTypeDao extends BaseDao<AdType> {

	 public List<AdType> findAdTypeByIds(List<String> adTypeIds,String parentId){
		 return find("from AdType where typeId in (:p1) and parent.id= :p2 and delFlag = :p3", new Parameter(adTypeIds,parentId,BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 public AdType findAdTypeByTypeId(String typeId,String parentId){
		 return getByHql("from AdType where parent.id = :p1 and typeId = :p2 and delFlag= :p3",new Parameter(parentId,typeId,BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 @Override
	public List<AdType> findAll(){
		 return find("from AdType where delFlag = :p1 order by typeId asc", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 public List<AdType> findAllByParent(String parentId){
		 return find("from AdType where delFlag = :p1 and parent.id= :p2 ", new Parameter(BaseEntity.DEL_FLAG_NORMAL,parentId));
	 }
	 
	 public List<AdType> findAdTypeByIsFlag(Integer isFlag){
		 return find("from AdType where isFlag = :p1 and delFlag= :p2",new Parameter(isFlag,BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 public List<AdType> findByParentIdsLike(String parentIds) {
	        return find("from AdType where parentIds like :p1", new Parameter(parentIds));
	 }
	 
	 public AdType findAdTypeByTypeIdAndNotChannel(String id,Integer isFlag){
		 return getByHql("from AdType where delFlag= :p1 and isFlag = :p2 and id = :p3 ",new Parameter(BaseEntity.DEL_FLAG_NORMAL,isFlag,id));
	 }
}
