package com.gospell.aas.repository.hibernate.adv;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdComboDistrict;

/**
 * 频道数据操作层
 * 
 * @author Administrator
 *
 */
@Repository
public class AdComboDistrictDao extends BaseDao<AdComboDistrict> {
	
	public void deleteComDis(String comboId){
		update("delete AdComboDistrict where adcombo.id = :p1", new Parameter(comboId));
	}

}
