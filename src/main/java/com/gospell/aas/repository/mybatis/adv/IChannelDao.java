package com.gospell.aas.repository.mybatis.adv;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gospell.aas.common.persistence.BaseMybatisDao;
import com.gospell.aas.common.persistence.anotation.MyBatisRepository;
import com.gospell.aas.dto.adv.AdChannelDTO;
import com.gospell.aas.dto.adv.AdChannelForComboDTO;
import com.gospell.aas.dto.adv.SelectChannelDTO;
import com.gospell.aas.entity.adv.AdChannel;

@MyBatisRepository
public interface IChannelDao  extends BaseMybatisDao<AdChannel>{

	List<String> getChannelType(@Param(value ="id") String networkId);
	List<AdChannelDTO> getChannel(@Param(value ="id") String networkId);
	
	/**
	 * 通过广告套餐ID查询与频道有关的
	 * @param comboId
	 * @return
	 */
	List<AdChannelForComboDTO> getChannelByComboId(Map<String,Object> map) throws Exception;
	
	/**
	 * 
	 * @param comboId
	 * @return
	 */
	List<SelectChannelDTO> getSelectChByComboId(@Param(value ="id") String comboId);
	
	List<AdChannelDTO> getChannelByNetworkIdAndTypeId(Map<String,Object> map);
	
	List<AdChannel> getAdChannelByAdComboId(Map<String,Object> map) ;
	
	List<AdChannel> getAdChannelByNetWorkId(Map<String,Object> map) ;
	
	List<AdChannel> getCanDeleteAdChannel(Map<String,Object> map) ;
	
	List<AdChannel> getChannelNotRepeat(Map<String,Object> map)throws Exception;
	
	public void updateChannel(Map<String,Object> map) ;

	public void deleteChannelList(Map<String,Object> map);

	public void deleteByChannelIds(Map<String,Object> map);

	public void deleteChannel(Map<String,Object> map);

	List<AdChannel> getAdChannels(Map<String,Object> map) ;

	public void checkCombo();
	
	
}
