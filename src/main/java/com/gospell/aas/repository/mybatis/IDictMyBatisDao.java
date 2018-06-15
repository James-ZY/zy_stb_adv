package com.gospell.aas.repository.mybatis;

import java.util.List;

import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.sys.Dict;

/**
 * MyBatis字典DAO接口
 * @author L.J
 * @version 2013-8-23
 */
@MyBatisRepository
public interface IDictMyBatisDao {
	
    Dict get(String id);
    
    List<Dict> find(Dict dict);
    
}
