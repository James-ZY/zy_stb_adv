package com.gospell.aas.repository.hibernate.adv;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdNetwork;

/**
 * 运营商数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdNetworkDao extends BaseDao<AdNetwork> {

	public AdNetwork findByNetworkId(String networkId){
        return getByHql("from AdNetwork where delFlag = :p1 and networkId = :p2 ", new Parameter( BaseEntity.DEL_FLAG_NORMAL,networkId));
    }
	
	public AdNetwork findByNetworkIdAndOperatorId(String networkId,String adOperatorId){
        return getByHql("from AdNetwork where delFlag = :p1 and networkId = :p2 and adOperators.operatorsId = :p3 ", new Parameter( BaseEntity.DEL_FLAG_NORMAL,networkId,adOperatorId));
    }
}
