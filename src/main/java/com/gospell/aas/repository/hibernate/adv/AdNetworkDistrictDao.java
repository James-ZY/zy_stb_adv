package com.gospell.aas.repository.hibernate.adv;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdNetworkDistrict;

/**
 * 频道数据操作层
 * 
 * @author Administrator
 *
 */
@Repository
public class AdNetworkDistrictDao extends BaseDao<AdNetworkDistrict> {
	
	public void deleteNtwDis(String networkId){
		update("delete AdNetworkDistrict where adNetwork.id = :p1", new Parameter(networkId));
	}

}
