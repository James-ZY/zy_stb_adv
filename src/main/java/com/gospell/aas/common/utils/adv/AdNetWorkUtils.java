package com.gospell.aas.common.utils.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.entity.adv.AdNetwork;
import com.gospell.aas.service.adv.AdNetworkService;

/**
 * 
 * @author free lance
 * @version 2013-5-29
 */
public class AdNetWorkUtils {
	private static AdNetworkService thisService = ApplicationContextHelper
			.getBean(AdNetworkService.class);

	public static Map<String, String> networkListToMap(List<AdNetwork> list) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				AdNetwork c = list.get(i);
				map.put(c.getId(), c.getNetworkName());
			}
		}
		return map;
	}

	public static List<AdNetwork> findAdNetworkAll() {
		return thisService.findAllByMabatis();
	}
//
//	/**
//	 * 检查发送器是否过期，也就是比较当前时间与广告发送器的有效时间的大小
//	 * 
//	 * @return
//	 */
//	public static List<String> checkClientIdIsValid(List<String> list) {
//		List<String> valid_list = Lists.newArrayList();
//		if (null != list && list.size() > 0) {
//			Map<String, AdNetwork> map = UserUtils.getNetworkMap();
//			Date date = new Date();
//			for (int i = 0; i < list.size(); i++) {
//				String clientId = list.get(i);
//				if (map.containsKey(clientId)) {
//					AdNetwork network = map.get(clientId);
//					if (null != network && network.getValidDate() != null) {
//						int compare = DateUtil.compareDate(date,
//								network.getValidDate());
//						if (compare != -1) {
//							valid_list.add(clientId);
//						}
//					}
//				}
//			}
//		}
//		return valid_list;
//	}
//
//	/**
//	 * 检查单个发送器是否过期，也就是比较当前时间与广告发送器的有效时间的大小
//	 * 
//	 * @return
//	 */
//	public static boolean checkClientIdIsValid(String clientId) {
//		boolean flag = true;// 默认过期
//		if (StringUtils.isNotBlank(clientId)) {
//			Map<String, AdNetwork> map = UserUtils.getNetworkMap();
//			Date date = new Date();
//
//			if (map.containsKey(clientId)) {
//				AdNetwork network = map.get(clientId);
//				if (null != network && network.getValidDate() != null) {
//					int compare = DateUtil.compareDate(date,
//							network.getValidDate());
//					if (compare != -1) {
//						flag = false;
//					}
//				}
//			}
//
//		}
//		return flag;
//	}
//	
//	
}
