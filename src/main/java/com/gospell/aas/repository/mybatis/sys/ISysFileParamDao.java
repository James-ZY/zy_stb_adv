package com.gospell.aas.repository.mybatis.sys;

import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.sys.SysFileParam;

@MyBatisRepository
public interface ISysFileParamDao extends BaseMybatisDao<SysFileParam>{
 
	public List<SysFileParam> getParamList(Map<String,Object> map);
}
