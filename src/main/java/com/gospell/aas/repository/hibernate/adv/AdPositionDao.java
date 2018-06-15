package com.gospell.aas.repository.hibernate.adv;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdPosition;

/**
 * 广告类型数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdPositionDao extends BaseDao<AdPosition> {

	public AdPosition findAdPositionById(String positionId){
		return getByHql("from AdPosition where delFlag = :p1 and pointId = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL,
				positionId));
	}
	 
}
