package com.gospell.aas.repository.mybatis.adv;


import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.dto.adv.AdElementStatisticPlayDTO;
import com.gospell.aas.dto.adv.AdStatisticPlayDTO;
import com.gospell.aas.dto.adv.AdStatisticTypePlayDTO;
import com.gospell.aas.entity.adv.AdStatistic;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface IAdStatisticDao extends BaseMybatisDao<AdStatistic>{

	/**
	 * 查询一段时间以内，广告总的播放次数
	 * @param entity
	 * @return
	 */
	public List<AdStatisticPlayDTO> statisticPlayCount(AdStatistic entity);

	/**
	 * 统计一段时间内，不同广告类型点击次数
	 * @param entity
	 * @return
	 */
	public List<AdStatisticTypePlayDTO> statisticAdvTypeClickCount(AdStatistic entity);

	/**
	 * 查询详细点击次数
	 * @param entity
	 * @return
	 */
	public List<AdElementStatisticPlayDTO> statisticAdvElementClickCount(AdStatistic entity);

	/**
	 * 统计一段时间内，不同广告类型播放时长
	 * @param entity
	 * @return
	 */
	public List<AdStatisticTypePlayDTO> statisticAdvTypePlayCount(AdStatistic entity);

	/**
	 * 查询详细播放时长
	 * @param entity
	 * @return
	 */
	public List<AdElementStatisticPlayDTO> statisticAdvElementPlayCount(AdStatistic entity);

	public Integer statisticPlayAllCount(AdStatistic entity);
	
	/**
	 * 查询具体的某条广告的播放记录
	 * @param entity
	 * @return
	 */
	public List<AdStatistic> statisticPlayDetail(AdStatistic entity);
	
	/**
	 * 根据时间和机顶盒查询数据是否重复
	 * @param map
	 * @return
	 */
	public List<AdStatistic> selectAdStatisticByDateList(Map<String,Object> map);
	
}
