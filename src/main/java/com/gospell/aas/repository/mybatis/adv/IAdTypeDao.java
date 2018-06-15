package com.gospell.aas.repository.mybatis.adv;


import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdType;

@MyBatisRepository
public interface IAdTypeDao extends BaseMybatisDao<AdType>{

	/**
	 * 通过typeId和父类ID查询Adtype
	 * @param map
	 * @return
	 * @throws Exception
	 */
	 public AdType findByParentIdAndTypeId(Map<String,Object> map) throws Exception;
	
}
