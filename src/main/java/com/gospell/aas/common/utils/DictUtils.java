package com.gospell.aas.common.utils;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.repository.hibernate.sys.DictDao;


/**
 * 字典工具类
 * @author free lance
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = ApplicationContextHelper.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static final String CACHE_ZH_CN_DICT_MAP = "zh_CN_dictMap";
	public static final String CACHE_EN_US_DICT_MAP = "en_US_dictMap";
	
 
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Dict> getDictList(String type){
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();
		Map<String, List<Dict>> dictMap = null;
		if("en".equals(name) || "EN".equals(name)){
		    dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_EN_US_DICT_MAP);
			if (dictMap==null){
				dictMap = Maps.newHashMap();
				for (Dict dict : dictDao.findAllList()){
					int locale = dict.getDictLocale();
					if(locale == Dict.en_US){
						List<Dict> dictList = dictMap.get(dict.getType());
						
						if (dictList != null){
							dictList.add(dict);
						}else{
							dictMap.put(dict.getType(), Lists.newArrayList(dict));
						}
					}
				}
				CacheUtils.put(CACHE_EN_US_DICT_MAP, dictMap);
			}
		}else{
			 dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_ZH_CN_DICT_MAP);
			if (dictMap==null){
				dictMap = Maps.newHashMap();
				for (Dict dict : dictDao.findAllList()){
					int locale = dict.getDictLocale();
					if(locale == Dict.zh_CN){
						List<Dict> dictList = dictMap.get(dict.getType());
						
						if (dictList != null){
							dictList.add(dict);
						}else{
							dictMap.put(dict.getType(), Lists.newArrayList(dict));
						}
					}
				}
				CacheUtils.put(CACHE_ZH_CN_DICT_MAP, dictMap);
			}
		}
		 
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	public static List<Dict> getDictList1(String type,String canUpdate){
		List<Dict> uplist = Lists.newArrayList();
		List<Dict> list = getDictList(type);
		if(canUpdate.equals("1")){
			for (Dict dict : list) {
				if(dict.getValue().startsWith("CU_")){
					uplist.add(dict);
				}
			}			
		}else{
			return list;
		}
		return uplist;
		
	}
	
	
}
