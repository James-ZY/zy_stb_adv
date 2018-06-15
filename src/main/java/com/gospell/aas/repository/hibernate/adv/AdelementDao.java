package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.Adelement;

/**
 * 广告类型数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdelementDao extends BaseDao<Adelement> {

	 public List<Adelement> findAdElemetByIds(List<String> adElemetIds){
		 return find("from Adelement where typeId in (:p1) and delFlag = :p2", new Parameter(adElemetIds,BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 public Adelement findAdElemetByAdId(String adId){
		 return getByHql("from Adelement where adId in (:p1) and delFlag = :p2", new Parameter(adId,BaseEntity.DEL_FLAG_NORMAL));
	 }
}
