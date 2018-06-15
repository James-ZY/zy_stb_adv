package com.gospell.aas.common.utils.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gospell.aas.entity.adv.AdPosition;
 


/**
 * 位置工具类
 * @author free lance
 * @version 2013-5-29
 */
public class AdPostionUtils {
	
 
	 
	public static Map<String,AdPosition> getListToMap(List<AdPosition> list){
		 if(null == list || list.size() ==0)
			 return null;
		 Map<String,AdPosition> map = new HashMap<String,AdPosition>();
		 for (AdPosition t : list) {
			map.put(t.getId(), t);
		}
		return map;
	}
	
	 
	
	 
	
}
