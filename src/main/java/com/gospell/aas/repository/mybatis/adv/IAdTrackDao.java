package com.gospell.aas.repository.mybatis.adv;


import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdTrack;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface IAdTrackDao extends BaseMybatisDao<AdTrack> {

	public void updateTrackStatus(Map<String, Object> map);


	/**
	 * 获取当前广告类型启用的轨迹模板
	 * @param map
	 * @return
	 */
	public List<AdTrack> getUseInAdTrack(Map<String, Object> map);
	 
	
}
