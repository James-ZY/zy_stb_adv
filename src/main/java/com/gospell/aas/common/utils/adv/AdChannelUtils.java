package com.gospell.aas.common.utils.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jersey.repackaged.com.google.common.collect.Lists;

import com.gospell.aas.dto.adv.AdChannelDTO;
import com.gospell.aas.entity.adv.AdChannel;

/**
 *  
 * @author free lance
 * @version 2013-5-29
 */
public class AdChannelUtils {
	
	public static Map<String,List<AdChannelDTO>> listToMap(List<AdChannelDTO> list){
		Map<String,List<AdChannelDTO>> map = new HashMap<String,List<AdChannelDTO>>();
		if(null != list && list.size() >0){
			for (int i = 0; i < list.size(); i++) {
				AdChannelDTO dto = list.get(i);
				String typeName = dto.getChannelType();
				List<AdChannelDTO> channelList = null;
				if(map.containsKey(typeName)){
					channelList = map.get(typeName);
				}else{
					channelList = Lists.newArrayList();
				}
				channelList.add(dto);
				map.put(typeName, channelList);
			}
		}
		return map;
	}
	
	public static Map<String,String> channeListToMap(List<AdChannel> list){
		Map<String,String> map = new HashMap<String,String>();
		if(null != list && list.size() >0){
			for (int i = 0; i < list.size(); i++) {
				AdChannel c = list.get(i);
				map.put(c.getId(), c.getChannelName());
			}
		}
		return map;
	}
	
 
	
 
	
}
