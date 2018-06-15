package com.gospell.aas.repository.mybatis.adv;


 

import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdComboPushFailRecord;

@MyBatisRepository
public interface IAdComboPushFailRecordDao  extends BaseMybatisDao<AdComboPushFailRecord>{

	
	public void insertEntity(List<AdComboPushFailRecord> list) throws Exception;
	
	public void deleteList(Map<String,Object> map) throws Exception;
	
	public List<AdComboPushFailRecord> selectData(Map<String,Object> map);
	
	
 
	
}
