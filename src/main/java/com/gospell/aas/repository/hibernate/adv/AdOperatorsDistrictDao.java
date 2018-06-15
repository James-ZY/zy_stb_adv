package com.gospell.aas.repository.hibernate.adv;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdOperatorsDistrict;

/**
 * 频道数据操作层
 * 
 * @author Administrator
 *
 */
@Repository
public class AdOperatorsDistrictDao extends BaseDao<AdOperatorsDistrict> {
	
	public void deleteOpDis(String operatorsId){
		update("delete AdOperatorsDistrict where operators.id = :p1", new Parameter(operatorsId));
	}

}
