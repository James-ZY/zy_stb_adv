package com.gospell.aas.common.utils.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;

import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.dto.adv.AdRangeDTO;
import com.gospell.aas.entity.adv.AdRange;

/**
 * 范围工具类
 * 
 * @author free lance
 * @version 2013-5-29
 */
public class AdRangeUtils {

	public static Map<String, AdRange> getListToMap(List<AdRange> list) {
		if (null == list || list.size() == 0)
			return null;
		Map<String, AdRange> map = new HashMap<String, AdRange>();
		for (AdRange t : list) {
			map.put(t.getId(), t);
		}
		return map;
	}

	public static List<AdRangeDTO> getAdRanges(String rangeType) {
		List<AdRangeDTO> allList = UserUtils.getAdRangeList(rangeType);
		List<AdRangeDTO> list = Lists.newArrayList();
		if (null != allList) {
			for (int i = 0; i < allList.size(); i++) {
				AdRangeDTO type = allList.get(i);
				list.add(type);
			}
		}
		return allList;

	}

}
