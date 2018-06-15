package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.dto.adv.AdComboByUserDTO;
import com.gospell.aas.dto.adv.AdComboStatisticSellDTO;
import com.gospell.aas.dto.adv.ComboAdvtiserSellDTO;
import com.gospell.aas.dto.adv.ComboCountDTO;
import com.gospell.aas.entity.adv.AdChannel;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdNetwork;

@MyBatisRepository
public interface IAdComboDao extends BaseMybatisDao<AdCombo>{

	
	/**
	 * 
	 * @param comboId 广告套餐Id
	 * @return
	 */
	public List<AdComboByUserDTO> getComboByUserId(@Param(value="date")String date,@Param(value="status")Integer status,@Param(value ="id") String userId);
	
	/**
	 * 
	 * @param comboId 广告套餐Id
	 * @return
	 */
	public List<AdComboByUserDTO> getComboByAdvId(@Param(value="date")String date,@Param(value="status")Integer status,@Param(value ="id") String advId);
	
	
	/**
	 * 获取广告套餐的广告类型及其子类型
	 * @param comboId 套餐ID
	 * @return
	 */
	public List<AdCombo> getAdTypeByComboId(AdCombo combo);
	
	/**
	 * 根据发送器ID获取对应的广告套餐
	 * @param comboId 套餐ID
	 * @return
	 */
	public List<AdCombo> getAdComboByNetworkId(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据发送器ID获取对应的与频道无关的广告套餐
	 * @param comboId 套餐ID
	 * @return
	 */
	public List<AdCombo> getAdComboNotByNetworkId(Map<String,Object> map)throws Exception;
	
	
	/**
	 * <!-- 获取频道无关的单个广告套餐详细 -->
	 */
	public AdCombo getSingleAdComboNotChannel(Map<String,Object> map);
	
	/**
	 * <!-- 获取频道有关的单个广告套餐详细信息 -->
	 */
	public AdCombo getSingleAdComboChannel(Map<String,Object> map);
	
	/**
	 * 获取已经投放的播控删除的广告套餐
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AdCombo> getAdComboByDelete(Map<String,Object> map)throws Exception;
	
	/**
	 * 批量更新数据的状态为已下传
	 * @param list
	 */
	public void updateIsPut(List<String> list) throws Exception;
	
	public List<AdCombo> findAdComboByComboName(AdCombo adcombo) throws Exception;
	
 
	/**
	 * 同一个广告类型的同一个频道是否有广告套餐
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AdCombo> getComboByNetworkIdAndTypeId(Map<String,Object> map) throws Exception;
	
	 /**
	  * 通过广告类型或者套餐名称加上生效日期来查询广告套餐
	  * @param map
	  * @return
	  */
	public List<AdCombo> getAdComboByAType(List<String> ids) ;
	
	 /**
	  * 计算在时间查询范围内套餐的销售天数
	  * @param map
	  * @return
	  */
	public List<AdComboStatisticSellDTO> getAdComboStatisticSellDTO(AdCombo entity) throws Exception;
	
 
	/**
	 * 根据广告ID查询该广告选择的广告套餐
	 * @param map
	 * @return
	 */
	public AdCombo findComboByAdv(Map<String,Object> map) throws Exception;
	
	 /**
	  * 	<!-- 根据中间表与频道无关广告套餐查询 -->
	  * @param map
	  * @return
	  */
	public List<AdCombo> getPutFailNotChannelAdCombo(Map<String,Object> map);
	
	 /**
	  * 	<!-- 根据中间表与频道有关广告套餐查询 -->
	  * @param map
	  * @return
	  */
	public List<AdCombo> getPutFailChannelAdCombo(Map<String,Object> map);
	
	/**
	 * 查询当前广告套餐在查询时间范围以内，卖了每个广告商的天数
	 * @param entity
	 * @return
	 */
	public List<ComboAdvtiserSellDTO> getAdcomboSellAdvtiserDay(AdCombo entity);
	

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<AdChannel> getChannelNotRepeat(Map<String,Object> map);
	
	/**
	 * 获取当前与频道无关的广告套餐（参数树广告类型ID和发送器ID）,用于销售
	 * @param map
	 * @return
	 */
	public List<AdCombo> selectNotChannelAdComboByType(Map<String,Object> map);
	
	/**
	 * 查询当前有效的与频道有关的广告套餐，用于销售 --
	 * @param map
	 * @return
	 */
	public List<AdCombo> selectChannelAdComboByType(Map<String,Object> map);
	
	/**
	 * 通过套餐Id（改套餐与频道无关）查询对应网络发送器
	 * @param map
	 * @return
	 */
	public List<AdNetwork> selectNotChannelNetworkByCombo(Map<String,Object> map);
	
	/**
	 * 通过套餐Id（改套餐与频道相关）查询对应网络发送器
	 * @param map
	 * @return
	 */
	public List<AdNetwork> selectChannelNetworkByCombo(Map<String,Object> map);
	
	/**
	 * 	<!-- 当广告发送器失效的时候，必须让对应的与频道无关的套餐失效 -->
	 * @param map
	 * @throws Exception
	 */
	public void updateAdNotChannlecombovalid(Map<String,Object> map) throws Exception;
	
	/**
	 * 	<!-- 当广告发送器失效的时候，必须让对应的与频道有关的套餐失效 -->
	 * @param map
	 * @throws Exception
	 */
	public void updateAdChannlecombovalid(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据广告类型ID与发送器ID查询是否有套餐
	 * @param map
	 * @return
	 */
	public Integer getComboCountByTypeIdAndNetwork(Map<String,Object> map);
	
	/**
	 * 根据频道ID和广告发送器ID查询当前频道下是否有套餐
	 * @param map
	 * @return
	 */
	
	public Integer getComboCountChannelIdAndNetwork(Map<String,Object> map);
	
	/**
	 * 判断发送器组合套餐是否冲突
	 * @param map
	 * @return
	 */
	public List<ComboCountDTO>     checkComboIsConflict(Map<String,Object> map);
	
	/**
	 * 判断发送器组合套餐是否存在
	 * @param map
	 * @return
	 */
	public List<ComboCountDTO>     checkComboIsExist(Map<String,Object> map);
	
	public Integer getAdChannelCombo(Map<String,Object> map);
	
	public Integer getAdNetworkCombo(Map<String,Object> map);

	List<AdCombo> findByTimeIntersection(AdCombo adCombo);
}
