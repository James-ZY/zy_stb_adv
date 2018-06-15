package com.gospell.aas.repository.mybatis.sys;

import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.sys.SysParam;

@MyBatisRepository
public interface ISysParamDao extends BaseMybatisDao<SysParam>{
 
	public List<SysParam> getParamList(Map<String,Object> map);
	
	public SysParam getParam(Map<String,Object> map); 
	
	public SysParam getMinParam(Map<String,Object> map); 
}
