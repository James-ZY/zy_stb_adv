package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdDefaultControll;

@MyBatisRepository
public interface IAdDefaultControlDao  extends BaseMybatisDao<AdDefaultControll>{

	
	/**
	 * 
	 * @param typeID广告类型ID
	 * @param advId 广告商Id
	 * @return
	 */
	List<AdDefaultControll> getControlByTypeId(Map<String,Object> map);
 
	
	/**
	 * 
	 * @param 根据广告Id获取值
	 * @return
	 */
	List<AdDefaultControll> getControlByIds(List<String> ids);
	
	/**
	 * 查询某个广告素材是否被广告引用，为了效率只查询一条 
	 * @param c
	 * @return
	 */
	String getAdelementByDefaultControllId(Map<String,Object> map);
	
	/**
	 * 查询某个广告素材是否被广告引用，为了效率只查询一条 
	 * @param c
	 * @return
	 */
	Integer getAdelementCountById(Map<String,Object> map);

	 
}
