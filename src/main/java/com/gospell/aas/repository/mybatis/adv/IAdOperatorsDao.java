package com.gospell.aas.repository.mybatis.adv;


 

import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdOperators;

@MyBatisRepository
public interface IAdOperatorsDao  extends BaseMybatisDao<AdOperators>{

	
	 /**
	  * 通过电视运营商ID查询电视运营商
	  * @param idList
	  * @return
	  */
	 public List<AdOperators> findAdOperatorsByOpId(Map<String,Object> map);
	 
	 public Integer findMaxAdOperatorId();
 
	
}
