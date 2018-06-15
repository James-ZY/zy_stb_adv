package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdControll;

@MyBatisRepository
public interface IAdControlDao  extends BaseMybatisDao<AdControll>{

	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<AdControll> getControlByTypeId(Map<String,Object> map) throws Exception;
 
	
	/**
	 * 根据广告Id获取值
	 * @param ids
	 * @return
	 */
	List<AdControll> getControlByIds(List<String> ids);
	
	/**
	 * 查询某个广告素材是否被广告引用，为了效率只查询一条 
	 * @param map
	 * @return
	 */
	String getAdelementByControllId(Map<String,Object> map);
	
	/**
	 * 查询某个广告素材是否被广告引用，为了效率只查询一条 
	 * @param map
	 * @return
	 */
	Integer getAdelementCountById(Map<String,Object> map);

	List<String> getControllVersionList(Map<String,String> map);
	 
}
