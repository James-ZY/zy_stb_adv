package com.gospell.aas.common.utils.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gospell.aas.repository.mybatis.adv.IAdControlDao;
import org.assertj.core.util.Lists;

import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.adv.Advertiser;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.mybatis.adv.IAdvertiserDao;


/**
 * 广告商工具类
 * @author free lance
 * @version 2013-5-29
 */
public class AdvertiserUtils {
	
	private static IAdvertiserDao typeDao = ApplicationContextHelper.getBean(IAdvertiserDao.class);
	private static IAdControlDao controlDao = ApplicationContextHelper.getBean(IAdControlDao.class);
	 
	public static List<Advertiser> getAdvertiserList(){
		List<Advertiser> list = Lists.newArrayList();
		list =  typeDao.findAllAdvertiser(new Advertiser());
		return list;
 
	}
	
	public static boolean checkIdAdv(){
		User user = UserUtils.getUser();
		if(null != user.getAdvertiser())
			return true;
		else
			return false;
	}
	
	/**
	 * 判断是否是广告商或者是管理员
	 * @return
	 */
	public static boolean checkIdAdvOrAdmin(){
		User user = UserUtils.getUser();
		if(null != user.getAdvertiser() || user.isAdmin())
			return true;
		else
			return false;
	}
	
	public static List<Advertiser> getCurrentAdv(){
		List<Advertiser> list = Lists.newArrayList();
		User user = UserUtils.getUser();
		if(null != user.getAdvertiser())
			list.add(user.getAdvertiser());
		return list;
	}

	public static List<String> getControllVersionList(){
		List<String> list = Lists.newArrayList();
		User user = UserUtils.getUser();
		Map<String,String> map = new HashMap<String, String>();
		if(!user.isAdmin() && null != user.getAdvertiser()){
			map.put("advertiserId",user.getAdvertiser().getId());
		}
		list = controlDao.getControllVersionList(map);
		return list;
	}
	
}
