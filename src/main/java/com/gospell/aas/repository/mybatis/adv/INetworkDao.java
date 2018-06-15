package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.entity.adv.AdOperators;

@MyBatisRepository
public interface INetworkDao  extends BaseMybatisDao<AdNetwork>{

	 
	/**
	 * 查询全部未删除的广告发送器
	 * @param comboId 广告套餐ID
	 * @return
	 */
	 List<AdNetwork> findAllNetwork(Map<String,Object> map);
	
 
	/**
	 * 查询当前广告类型（与频道无关）的在哪些发送器中已经添加
	 * @return
	 */
	public List<AdNetwork> findNetWorkByTypeAndCombo(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询当前所有的发送器，并且查询对应的与频道无关的广告类型
	 * @return
	 */
	public List<AdNetwork> findAllPage(AdNetwork network) throws Exception;
	
	/**
	 * 查询当前没有停用的
	 * @param network
	 * @return
	 */
	public List<AdNetwork> findAllValid(AdNetwork network);
	
    public AdNetwork getAdNetWorkByOpreators(AdOperators operators);
    
    /**
     * 根据套餐
     * @param map
     * @return
     */
    public List<AdNetwork> getNetworkByComboAndChannel(Map<String,Object> map);
    
    public List<AdNetwork> getNetworkByComboId(Map<String,Object> map);
    
    public AdNetwork getNetworkByOperatorId(Map<String,Object> map);
    
	public List<AdNetwork> findAllNetworkByTypeId(Map<String,Object> map);
 
}

