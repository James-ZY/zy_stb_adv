package com.gospell.aas.repository.mybatis.adv;

import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdResourceOfType;

@MyBatisRepository
public interface IAdResourceOfTypeDao extends BaseMybatisDao<AdResourceOfType>{
 
	public List<AdResourceOfType> findByTypeId(Map<String,Object> map);
}
