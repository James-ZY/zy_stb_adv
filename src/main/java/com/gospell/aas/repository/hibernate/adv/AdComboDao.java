package com.gospell.aas.repository.hibernate.adv;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.adv.AdCombo;

/**
 * 广告类型数据操作层
 * @author Administrator
 *
 */
@Repository
public class AdComboDao extends BaseDao<AdCombo> {

	 @Override
	public List<AdCombo> findAll(){
		 return find("from AdCombo where delFlag = :p1 order by adcomboId asc", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 public AdCombo findAdComboByComboId(String comboId){
		 return getByHql("from AdCombo where delFlag = :p1 and adcomboId = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL,comboId));
	 }
	 
	 /**
	  * 根据运营状态获取广告套餐
	  * @return
	  */
	 public List<AdCombo> findAlreayCombo(Integer status){
		 return find("from AdCombo where status= :p1 and delFlag = :p2 order by adcomboId asc", new Parameter(status,BaseEntity.DEL_FLAG_NORMAL));
	 }
	 
	 
	 
}
