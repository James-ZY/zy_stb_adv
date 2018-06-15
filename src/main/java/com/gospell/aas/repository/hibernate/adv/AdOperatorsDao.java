package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdOperators;

/**
 * 运营商数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdOperatorsDao extends BaseDao<AdOperators> {

	public AdOperators findByOperatorsId(String operatorId){
        return getByHql("from AdOperators where operatorsId = :p1 and delFlag = :p2", new Parameter(operatorId, BaseEntity.DEL_FLAG_NORMAL));
    }
	
	public AdOperators findByOperators(String operatorsId,String password){
		  return getByHql("from AdOperators where operatorsId = :p1 and password= :p2 and delFlag = :p3", new Parameter(operatorsId, password,BaseEntity.DEL_FLAG_NORMAL));
	}
	
	@Override
	public List<AdOperators> findAll(){
		return find("from AdOperators where delFlag = :p1",new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}
	
	public List<AdOperators> findByOperatorsIds(String[] ids){
		return find("from AdOperators where delFlag = :p1 and id in (:p2)", new Parameter(BaseEntity.DEL_FLAG_NORMAL,ids));
	}
}
