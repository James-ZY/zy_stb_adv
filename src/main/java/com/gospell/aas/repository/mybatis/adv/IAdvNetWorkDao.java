package com.gospell.aas.repository.mybatis.adv;


 

import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdvNetwork;

@MyBatisRepository
public interface IAdvNetWorkDao  extends BaseMybatisDao<AdvNetwork>{

	
	public void insertEntity(List<AdvNetwork> list) throws Exception;
	
	public void deleteList(Map<String,Object> map) throws Exception;
	public void deleteList1(Map<String,Object> map) throws Exception;
	
	public List<AdvNetwork> selectData(Map<String,Object> map);
	
	
 
	
}
