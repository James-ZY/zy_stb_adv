package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdRange;

@MyBatisRepository
public interface IAdRangeDao extends BaseMybatisDao<AdRange>{

	public void updateRangeStatus(Map<String,Object> map);
	
	
	/**
	 * 获取当前广告类型启用的广告坐标
	 * @param map
	 * @return
	 */
	public List<AdRange> getUseInAdRange(Map<String,Object> map); 
	 
	
}
