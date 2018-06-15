package com.gospell.aas.common.utils.adv;

import java.util.List;

import org.assertj.core.util.Lists;

import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.sys.Dict;

/**
 * 字典工具类
 * 
 * @author free lance
 * @version 2013-5-29
 */
public class AdTypeUtils {

	public static List<AdType> getAdTypeList() {
		List<AdType> allList = UserUtils.getAdTypeList();
		List<AdType> list = Lists.newArrayList();
		if (null != allList) {
			for (int i = 0; i < allList.size(); i++) {
				AdType type = allList.get(i);
				// 必须是一级节点
				if (type.getParent() != null
						&& AdType.getzeroAdTypeId().equals(
								type.getParent().getId())) {
					list.add(type);
				}
			}
		}
		return list;

	}

	public static List<AdType> getAdTypeByIsFlag(Integer isFlag) {
		List<AdType> allList = UserUtils.getAdTypeList();
		List<AdType> list = Lists.newArrayList();
		if (null != allList) {
			for (int i = 0; i < allList.size(); i++) {
				AdType type = allList.get(i);
				// 是否频道相关(0不相关 1相关)（0不需要 1需要）, 必须是一级节点
				if (type.getIsFlag() != null) {
					int t_isFlag = type.getIsFlag();

					if (t_isFlag == isFlag&& type.getParent() != null
							&& AdType.getzeroAdTypeId().equals(
									type.getParent().getId())) {
						list.add(type);
					}
				}
			}
		}
		return list;
	}

	public static AdType get(String id) {
		List<AdType> allList = UserUtils.getAdTypeList();
		if (null != allList) {
			for (int i = 0; i < allList.size(); i++) {
				AdType type = allList.get(i);
				if (id.equals(type.getId())) {
					return type;
				}
			}
		}
		return null;

	}
	
	public static List<AdType> getAdTypeById(String id){
		List<AdType> allList = UserUtils.getAdTypeList();
		List<AdType> list = Lists.newArrayList();
		if (null != allList) {
			for (int i = 0; i < allList.size(); i++) {
				AdType type = allList.get(i);
				if (org.apache.commons.lang3.StringUtils.contains(id,type.getId())) {
					list.add(type);
				}
			}
		}
		return list;
	}

	public static List<AdType> getAdTypeByIsPosition(Integer position) {
		List<AdType> allList = UserUtils.getAdTypeList();
		List<AdType> list = Lists.newArrayList();
		if (null != allList) {
			for (int i = 0; i < allList.size(); i++) {
				AdType type = allList.get(i);
				// 显示是否需要详细坐标（0不需要 1需要）, 必须是一级节点
				if (type.getIsPosition() != null) {
					int t_isFlag = type.getIsPosition();

					if (t_isFlag == position
							&& type.getParent() != null
							&& AdType.getzeroAdTypeId().equals(
									type.getParent().getId())) {
						list.add(type);
					}
				}
				 
			}
		}
		return list;

	}

	/**
	 * 获取国际化的广告类型
	 * 
	 * @param type
	 * @return
	 */
	public static void getLocaleAdType(AdType adtype) {
		String type = adtype.getTypeName();
		List<Dict> dictList = DictUtils.getDictList(type);
		if (null != dictList && dictList.size() > 0) {
			Dict d = dictList.get(0);
			String typeName = d.getValue();
			adtype.setTypeName(typeName);
		}

	}

	/**
	 * 根据广告类型的名称 获取国际化的广告类型
	 * 
	 * @param type
	 * @return
	 */
	public static String getLocaleAdTypeName(String name) {
		if (org.apache.commons.lang3.StringUtils.isBlank(name)) {
			return name;
		}
		List<Dict> dictList = DictUtils.getDictList(name);
		if (null != dictList && dictList.size() > 0) {
			Dict d = dictList.get(0);
			String typeName = d.getValue();
			return typeName;
		}
		return name;
	}

}
