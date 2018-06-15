package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdSell;

/**
 * 广告类型数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdSellDao extends BaseDao<AdSell> {

	public AdSell findSellByContractNumber(String number){
		return getByHql("from AdSell where delFlag = :p1 and contractNumber = :p2",new Parameter(BaseEntity.DEL_FLAG_NORMAL,number));
	}
	
	public List<AdSell> findSellByCombo(AdCombo combo){
		return find("from AdSell where adCombo.id= :p1  and delFlag = :p2",new Parameter(combo.getId(),BaseEntity.DEL_FLAG_NORMAL));
	}
	
	public List<Object> findMaxEndDateByCombo(String comboId){
		  return find("select max(endDate) from AdSell where adCombo.id= :p1  and delFlag = :p2",new Parameter(comboId,BaseEntity.DEL_FLAG_NORMAL));
	}
	
	public List<AdSell> findSellByComboAndUser(String comboId,String advtiserId){
		return find("from AdSell where adCombo.id= :p1  and advertiser.id = :p2 and delFlag = :p3",new Parameter(comboId,advtiserId,BaseEntity.DEL_FLAG_NORMAL));
	}
	 
}
