package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.dto.adv.AdelementNCDTO;
import com.gospell.aas.entity.adv.Adelement;

@MyBatisRepository
public interface IAdelementDao extends BaseMybatisDao<Adelement>{

	
	/**
	 * 根据广告发送器ID获取与频道无关的广告
	 * @param map
	 * @return
	 */
	public List<Adelement> getAdelementNotChannel(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据广告发送器ID获取与频道有关的广告
	 * @param map
	 * @return
	 */
	public List<Adelement> getAdelementChannel(Map<String,Object> map) throws Exception;
	
	/**
	 * 批量更新数据的状态为已下传
	 * @param map
	 */
	public void updateIsPut(Map<String,Object> map) throws Exception;
	
	 
	public void updatePutEnd(Map<String,Object> map) throws Exception;
	
	public void closeDown(Map<String,Object> map) throws Exception;
	
	public void updateStatus(Adelement adelement) throws Exception;
	
	public List<Adelement> findAdelementByAdName(Adelement entity);
	
	/**
	 * 根据广告发送器ID获取与频道无关的推送失败的广告
	 * @param map
	 * @return
	 */
	public List<Adelement> getPutFailToClientNotChannel(Map<String,Object> map);
	
	/**
	 * 根据广告发送器ID获取与频道有关的未推送的广告
	 * @param map
	 * @return
	 */
	public List<Adelement> getPutFailToClientChannel(Map<String,Object> map);
	/**
	 * 求单个与频道无关的广告的详细信息
	 * @param map
	 * @return
	 */
	public Adelement getSingelAdelementNotChannel(Map<String,Object> map);
	
	/**
	 * 求单个与频道有关的广告的详细信息
	 * @param map
	 * @return
	 */
	public Adelement getSingelAdelementChannel(Map<String,Object> map);
	
	/**
	 * 在发布广告的时候判断同一个套餐同一个广告类型的广告在用户输入的时间范围内是否有其他广告
	 * @param map
	 * @return
	 */
	public Integer checkIsAdvRepeat(Map<String,Object> map);
	
	/**
	 * 根据广告ID的集合查询广告
	 * @param map
	 * @return
	 */
	public List<Adelement> findAdelementByIdList(Map<String,Object> map);
	
	/**
	 * 目前删除广告之后，广告ID按顺序往下延伸，所以需要查询已经删除的广告ID
	 * @param map
	 * @return
	 */
	public String findAdelementByAdCategory(Map<String,Object> map);
	
	public List<Adelement> findAuditList(Adelement entity);
	
	public List<Adelement> findAuditStatisticList(Adelement entity);
	
	/**
	 * 获取当前时间段使用的与频道相关广告
	 * @param map
	 * @return
	 */
	public List<AdelementNCDTO> findChannelAdelements(Map<String,Object> map);
	
	/**
	 * 获取当前时间段使用的与频道无关广告
	 * @param map
	 * @return
	 */
	public List<AdelementNCDTO> findNetworkAdelements(Map<String,Object> map);

	public Integer findStatusById(Adelement entity);
}
