package com.gospell.aas.repository.mybatis.adv;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gospell.aas.dto.adv.*;
import org.apache.ibatis.annotations.Param;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdSell;
import com.gospell.aas.entity.adv.Adelement;

@MyBatisRepository
public interface IAdSellDao extends BaseMybatisDao<AdSell> {

	
	/**
	 * 
	 * @param 判断当前选择的广告商是否在所选时间内已经购买的该广告类型的广告（只针对与频道无关的广告）
	 * @return
	 */
	List<String> getIsRepeatAcombo(@Param(value="typeId")String typeId,
			@Param(value="advtersier_id")String advtersier_id,@Param("startDate")Date startDate,
			@Param(value="endDate")Date endDate);
 
	/**
	 * 查询广告套餐在当前系统时间内是否还处于销售当中
	 * @param map
	 * @return
	 */
	List<String> findComboInSell(Map<String,Object> map) throws Exception;
	
	/**
	 * 查询是否广告
	 * @param sell
	 * @return
	 */
	public Integer fingAdvInSellTime(AdSell sell);
	
	/**
	 * 查询该记录下的广告
	 * @param sell
	 * @return
	 */
	public List<Adelement> fingAdvInSell(AdSell sell);
	
	/**
	 * 判断套餐有没有被销售，考虑到效率问题，只查询一条数据，所以返回的是对应的ID
	 * @param map
	 * @return
	 */
	String findSellByComboId(Map<String,Object> map);
	
	List<AdSell> findSellByComboIdAndAdvId(Map<String,Object> map);
	
	/**
	 * 获取一段时间以内，销售的套餐的总个数
	 * @param map
	 * @return
	 */
	Integer getAdcomboCountByType(Map<String,Object> map);
	
	/**
	 * 获取一定时间段内，广告商购买的套餐个数
	 * @param map
	 * @return
	 */
	List<AdvtiserSellNumber> getAdcomboSellAdvtiserCount(AdSell sell);

	/**
	 *  查询一段时间内，广告商购买套餐时长
	 *  @author zhw
	 * @param sell
	 * @return
	 */
	List<AdvtiserSellNumber> getAdcomboSellAdvtiserTime(AdSell sell);

	/**
	 * 获取一段时间以内，销售的套餐的销售详细
	 * @param sell
	 * @return
	 */
	List<AdCombo> getAdcomboCountDetail(AdSell sell);
	
	/**
	 * 获取当前广告商购买的广告套餐的详细信息
	 * @param sell
	 * @return
	 */
	List<AdCombo> getSellAdvTiserComboDetail(AdSell sell) throws Exception;
	
	/**
	 * 计算某天的24个时段范围内与频道相关的套餐的销售个数                                  
	 * @param map
	 * @return
	 */
	public List<ChannelAdComboSellNumberDTO> getSellDayTimeNumber(AdSell sell);
	
 
	public List<AdSell> findSellByComboIdAndAdvPlayDate(Map<String,Object> map);
	
	/**
	 *	<!-- 查询当前时间下是否还有正在销售与频道无关的套餐 的个数-->
	 * @param map
	 * @return
	 */
	Integer sellNotChannelComboInNetworkNow(Map<String,Object> map);
	
	
	/**
	 * <!-- 查询当前时间下是否还有正在销售与频道相关的套餐 的个数-->
	 * @param map
	 * @return
	 */
	public Integer sellChannelComboInNetworkNow(Map<String,Object> map);
	
	/**
	 * 获取某个销售记录下的广告的最迟的播放时间
	 * @param map
	 * @return
	 */
	public Date getAdvMaxDateInSell(Map<String,Object> map);
	
	/**
	 * 在用户添加销售记录的时候查询该套餐在用户所选的销售时间范围以内是否销售
	 * @param map
	 * @return
	 */
	public Integer findSellCountByComboId(Map<String,Object> map);
	
	
	/**
	 * 获取套餐下发布和未发布广告的数量
	 * @param map
	 * @return
	 */
	public List<AdComboPublishNumber>  getAdComboPublishNumber(Map<String,Object> map);
	
	/**
	 * 获取套餐下发布和未发布广告的数量
	 * @param map
	 * @return
	 */
	public Integer getAdComboPublishCount(Map<String,Object> map);
	
	/**
	 * 统计一段时间内售出的广告套餐个数
	 * @param map
	 * @return
	 */
	public List<AdComboSellCountDto>  getAdComboSellCount(Map<String,Object> map);
	
	/**
	 * 获取销售记录
	 * @param map
	 * @return
	 */
	List<AdSell> fingAdSellByCombo(Map<String,Object> map);

	/**
	 * 各广告类型中广告商占比
	 * zhw
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<AdvtiserRateDTO> findAdvtiserRateInAdTypes(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	/**
	 * 查找某类型广告销售记录
	 * zhw
	 * @param sell
	 * @return
	 */
	List<AdSell> findAdSellByAdType(AdSell sell);

	/**
	 * 查询一段时间内各类型广告可运营时间和售出的时间
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<AdComboSellTimeDTO> findAdComboSellAndValidTime(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}
