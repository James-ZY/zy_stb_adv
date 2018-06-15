package com.gospell.aas.repository.mybatis.sys;


import java.util.List;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.sys.Help;

@MyBatisRepository
public interface IHelpDao extends BaseMybatisDao<Help> {

 
	public List<Help> findHelpValid(Help help);
}
