/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.common.utils;

import com.google.common.collect.Maps;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.security.SystemAuthorizingRealm.Principal;
import com.gospell.aas.dto.adv.AdRangeDTO;
import com.gospell.aas.dto.adv.AdTrackDTO;
import com.gospell.aas.entity.adv.*;
import com.gospell.aas.entity.sys.*;
import com.gospell.aas.repository.hibernate.adv.*;
import com.gospell.aas.repository.hibernate.sys.MenuDao;
import com.gospell.aas.repository.hibernate.sys.OfficeDao;
import com.gospell.aas.repository.hibernate.sys.RoleDao;
import com.gospell.aas.repository.hibernate.sys.UserDao;
import com.gospell.aas.repository.mybatis.adv.IAdRangeDao;
import com.gospell.aas.repository.mybatis.adv.IAdTrackDao;
import com.gospell.aas.repository.mybatis.adv.INetworkDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.sys.SysParamService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.assertj.core.util.Lists;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 用户工具类
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
public class UserUtils extends BaseService {

	private static UserDao userDao = ApplicationContextHelper
			.getBean(UserDao.class);
	private static RoleDao roleDao = ApplicationContextHelper
			.getBean(RoleDao.class);
	private static MenuDao menuDao = ApplicationContextHelper
			.getBean(MenuDao.class);
	private static OfficeDao officeDao = ApplicationContextHelper
			.getBean(OfficeDao.class);
	private static AdExternalProgramDao programDao = ApplicationContextHelper
			.getBean(AdExternalProgramDao.class);
	private static AdTypeDao typeDao = ApplicationContextHelper
			.getBean(AdTypeDao.class);
	private static IAdRangeDao rangeDao = ApplicationContextHelper
			.getBean(IAdRangeDao.class);
	private static IAdTrackDao trackDao = ApplicationContextHelper
			.getBean(IAdTrackDao.class);
	private static AdCategoryDao cateGoryDao = ApplicationContextHelper
			.getBean(AdCategoryDao.class);
	private static AdDistrictCategoryDao disCateGoryDao = ApplicationContextHelper
			.getBean(AdDistrictCategoryDao.class);
	private static AdProgramCategoryDao programCateGoryDao = ApplicationContextHelper
			.getBean(AdProgramCategoryDao.class);
	private static INetworkDao networkDao = ApplicationContextHelper
			.getBean(INetworkDao.class);
	private static SysParamService sysParamService = ApplicationContextHelper
			.getBean(SysParamService.class);

	public static final String CACHE_USER = "user";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_ZH_CN_MENU_LIST = "zh_CN_menuList";
	public static final String CACHE_EN_US_MENU_LIST = "en_US_menuList";
	public static final String CACHE_ZH_CN_ROLE_LIST = "zh_CN_roleList";
	public static final String CACHE_EN_US_ROLE_LIST = "en_US_roleList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_ZH_CN_OFFICE_LIST = "zh_CN_officeList";
	public static final String CACHE_EN_US_OFFICE_LIST = "en_US_officeList";
	public static final String CACHE_FACILITY_LIST = "facilityList";
	public static final String CACHE_FFMPEG_PROGRAM_LIST = "programList";
	public static final String CACHE_NETTY_CLIENT_LIST = "nettyList";
	public static final String CACHE_ADTYPE_LIST = "adtypeList";
	public static final String CACHE_ADRANGE_LIST = "adrangeList";
	public static final String CACHE_ADTRACK_LIST = "adtrackList";
	public static final String CACHE_ZH_CN_ADTYPE_LIST = "zh_CN_adtypeList";
	public static final String CACHE_EN_US_ADTYPE_LIST = "en_US_adtypeList";
	public static final String CACHE_ADCATEGORY_LIST = "categoryList";
	public static final String CACHE_ZH_CN_ADCATEGORY_LIST = "zh_CN_categoryList";
	public static final String CACHE_EN_US_ADCATEGORY_LIST = "en_US_categoryList";
	public static final String CACHE_ADDISTRICTCATEGORY_TYPE = "districtCategoryType";
	public static final String CACHE_ADDISTRICTCATEGORY_LIST = "districtCategoryList";
	public static final String CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST = "zh_CN_districtCategoryList";
	public static final String CACHE_EN_US_ADDISTRICTCATEGORY_LIST = "en_US_districtCategoryList";
	public static final String CACHE_PROGRAM_CATEGORY_LIST = "programCategoryList";
	public static final String CACHE_ZH_CN_PROGRAM_CATEGORY_LIST = "zh_CN_program_categoryList";
	public static final String CACHE_EN_US_PROGRAM_CATEGORY_LIST = "en_US_program_categoryList";
	public static final String CACHE_VALID_DATE_NETWORK_MAP = "valid_date_network_map";
	public static User getUser() {
		User user = (User) getCache(CACHE_USER);
		if (user == null) {
			try {

				Subject subject = SecurityUtils.getSubject();

				Principal principal = (Principal) subject.getPrincipal();

				if (principal != null) {
					user = userDao.get(principal.getId());

					putCache(CACHE_USER, user);
				}
			} catch (UnavailableSecurityManagerException e) {
				e.printStackTrace();
			} catch (InvalidSessionException e) {
				e.printStackTrace();
			}
		}

		if (user == null) {
			user = new User();
			try {
				SecurityUtils.getSubject().logout();

			} catch (UnavailableSecurityManagerException e) {
				e.printStackTrace();
			} catch (InvalidSessionException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public static User getUser(boolean isRefresh) {
		if (isRefresh) {
			removeCache(CACHE_USER);
		}
		return getUser();
	}
	@SuppressWarnings("unchecked")
	public static List<Role> getRoleList() {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();

		List<Role> list = (List<Role>) getCache(CACHE_ROLE_LIST);
		if (list == null) {

			list = roleDao.findAll();
			for (int i = 0; i < list.size(); i++) {
				Office office = list.get(i).getOffice();
				Office o = getOffice(office.getId());
				list.get(i).setOffice(o);
			}
			putCache(CACHE_ROLE_LIST, list);

		}
		if ("en".equals(name) || "EN".equals(name)) {// 英文状态下的时候，
			List<Role> en_us_roleList = (List<Role>) getCache(CACHE_EN_US_ROLE_LIST);
			if (en_us_roleList == null) {
				en_us_roleList = getLocaleRoleList(list);
				putCache(CACHE_EN_US_ROLE_LIST, en_us_roleList);
			}
			return en_us_roleList;

		} else {
			List<Role> zh_cn_roleList = (List<Role>) getCache(CACHE_ZH_CN_ROLE_LIST);
			if (zh_cn_roleList == null) {
				zh_cn_roleList = getLocaleRoleList(list);
				putCache(CACHE_ZH_CN_ROLE_LIST, zh_cn_roleList);
			}
			return zh_cn_roleList;
		}

	}

	@SuppressWarnings("unchecked")
	public static List<Menu> getMenuList() {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList();

			} else {
				menuList = menuDao.findByUserId(user.getId());
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		if ("en".equals(name) || "EN".equals(name)) {// 英文状态下的时候，去国际化菜单，需要进行深复制菜单，因为不可以改变数据库查询出来的对象，中文同理
			List<Menu> en_us_menuList = (List<Menu>) getCache(CACHE_EN_US_MENU_LIST);
			if (en_us_menuList == null) {
				en_us_menuList = getLocaleMenuList(menuList);
				putCache(CACHE_EN_US_MENU_LIST, en_us_menuList);
			}
			return en_us_menuList;

		} else {
			List<Menu> zh_cn_menuList = (List<Menu>) getCache(CACHE_ZH_CN_MENU_LIST);
			if (zh_cn_menuList == null) {
				zh_cn_menuList = getLocaleMenuList(menuList);
				putCache(CACHE_ZH_CN_MENU_LIST, zh_cn_menuList);
			}
			return zh_cn_menuList;
		}

	}

	/**
	 * 获取数据库中真正的菜单
	 * @return
	 */
	public static List<Menu> getRealMenuList() {

		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			User user = getUser();
			if (user.isAdmin()) {
				menuList = menuDao.findAllList();

			} else {
				menuList = menuDao.findByUserId(user.getId());
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}


	@SuppressWarnings("unchecked")
	public static List<AdType> getAdTypeList() {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();

		List<AdType> typeList = (List<AdType>) getCache(CACHE_ADTYPE_LIST);
		if (typeList == null) {
			typeList = typeDao.findAll();
			putCache(CACHE_ADTYPE_LIST, typeList);
		}
		if ("en".equals(name) || "EN".equals(name)) {// 英文状态下的时候，去国际化菜单，需要进行深复制广告类型，因为不可以改变数据库查询出来的对象，中文同理
			List<AdType> en_us_type_List = (List<AdType>) getCache(CACHE_EN_US_ADTYPE_LIST);
			if (en_us_type_List == null) {
				en_us_type_List = getLocaleAdTypeList(typeList);
				putCache(CACHE_EN_US_ADTYPE_LIST, en_us_type_List);
			}
			return en_us_type_List;

		} else {
			List<AdType> zh_cn_typeList = (List<AdType>) getCache(CACHE_ZH_CN_ADTYPE_LIST);
			if (zh_cn_typeList == null) {
				zh_cn_typeList = getLocaleAdTypeList(typeList);
				putCache(CACHE_ZH_CN_ADTYPE_LIST, zh_cn_typeList);
			}
			return zh_cn_typeList;

		}

	}

	@SuppressWarnings({ "unchecked" })
	public static List<AdRangeDTO> getAdRangeList(String rangeType) {
		UserUtils.removeCache(UserUtils.CACHE_ADRANGE_LIST);
		List<AdRangeDTO> typeList = (List<AdRangeDTO>) getCache(CACHE_ADRANGE_LIST);

		if (typeList == null) {
			List<AdRangeDTO> list = Lists.newArrayList();
			AdRange a = new AdRange();
			a.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
			a.setStatus(AdRange.RANGE_START_STAUTS);
			a.setFlag(rangeType.equals("hd")?1:0);
			List<AdRange> range = rangeDao.findList(a);
			for (AdRange adRange : range) {
				AdRangeDTO dto = new AdRangeDTO();
				dto.setId(adRange.getId());
				dto.setRangeName(adRange.getRangeName());
				dto.setBeginX(adRange.getBeginX());
				dto.setBeginY(adRange.getBeginY());
				dto.setEndX(adRange.getEndX());
				dto.setEndY(adRange.getEndY());
				dto.setRange(adRange.getRangeName()+"("+adRange.getBeginX()+","+adRange.getBeginY()+")~("+adRange.getEndX()+","+adRange.getEndY()+")");
				list.add(dto);
			}
			putCache(CACHE_ADRANGE_LIST, list);
			typeList = (List<AdRangeDTO>) getCache(CACHE_ADRANGE_LIST);
		}
		return typeList;
	}

	public static List<AdTrackDTO> getAdTrackList(String trackType) {
		UserUtils.removeCache(UserUtils.CACHE_ADTRACK_LIST);
		List<AdTrackDTO> trackList = (List<AdTrackDTO>) getCache(CACHE_ADTRACK_LIST);

		if (trackList == null) {
			List<AdTrackDTO> list = Lists.newArrayList();
			AdTrack a = new AdTrack();
			a.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
			a.setStatus(AdTrack.TRACK_START_STAUTS);
			a.setFlag(trackType.equals("hd") ? 1 : 0);
			List<AdTrack> track = trackDao.findList(a);
			for (AdTrack adTrack : track) {
				AdTrackDTO dto = new AdTrackDTO();
				dto.setId(adTrack.getId());
				dto.setTrackName(adTrack.getTrackName());
				dto.setCoordinates(adTrack.getCoordinates());
				dto.setFlag(adTrack.getFlag());
				dto.setShowTime(adTrack.getShowTime());
				list.add(dto);
			}
			putCache(CACHE_ADTRACK_LIST, list);
			trackList = (List<AdTrackDTO>) getCache(CACHE_ADTRACK_LIST);
		}
		return trackList;
	}

	@SuppressWarnings("unchecked")
	public static List<AdCategory> getCateGoryList() {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();
		List<AdCategory> categoryList = (List<AdCategory>) getCache(CACHE_ADCATEGORY_LIST);
		if (categoryList == null) {

			categoryList = cateGoryDao.findAll();

			putCache(CACHE_ADCATEGORY_LIST, categoryList);
		}
		if ("en".equals(name) || "EN".equals(name)) {// 英文状态下的时候，去国际化菜单，需要进行深复制office，因为不可以改变数据库查询出来的对象，中文同理
			List<AdCategory> en_us_category_List = (List<AdCategory>) getCache(CACHE_EN_US_ADCATEGORY_LIST);
			if (en_us_category_List == null) {
				en_us_category_List = getLocaleAdCategoryList(categoryList);
				putCache(CACHE_EN_US_ADCATEGORY_LIST, en_us_category_List);
			}
			return en_us_category_List;

		} else {
			List<AdCategory> zh_cn_category_List = (List<AdCategory>) getCache(CACHE_ZH_CN_ADCATEGORY_LIST);
			if (zh_cn_category_List == null) {
				zh_cn_category_List = getLocaleAdCategoryList(categoryList);
				putCache(CACHE_ZH_CN_ADCATEGORY_LIST, zh_cn_category_List);
			}
			return zh_cn_category_List;

		}

	}

	@SuppressWarnings("unchecked")
	public static List<AdDistrictCategory> getDisCateGoryList(String... type) {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();
		String categoryType = (String)getCache(CACHE_ADDISTRICTCATEGORY_TYPE);
		List<AdDistrictCategory> categoryList = (List<AdDistrictCategory>) getCache(CACHE_ADDISTRICTCATEGORY_LIST);
		//系统区域参数变更标志  true变更 false 未变更
		boolean areaChangeFlag = !type[0].equals(categoryType);
		if (categoryList == null || categoryList.size() == 0 || areaChangeFlag) {

			//categoryList = disCateGoryDao.findAll();
			if(type[0]==null){
				categoryList = disCateGoryDao.findAllByType(AdDistrictCategory.TYPE_CHINA);
			}else{
				categoryList = disCateGoryDao.findAllByType(type[0]);
			}

			putCache(CACHE_ADDISTRICTCATEGORY_TYPE, type[0]);
			putCache(CACHE_ADDISTRICTCATEGORY_LIST, categoryList);
		}

		if(areaChangeFlag){
			putCache(CACHE_EN_US_ADDISTRICTCATEGORY_LIST,  getLocaleAdDisCategoryList(categoryList));
			putCache(CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST,  getLocaleAdDisCategoryList(categoryList));
		}

		// 英文状态下的时候，去国际化菜单，需要进行深复制office，因为不可以改变数据库查询出来的对象，中文同理
		if ("en".equals(name) || "EN".equals(name)) {
			List<AdDistrictCategory> en_us_category_List = (List<AdDistrictCategory>) getCache(CACHE_EN_US_ADDISTRICTCATEGORY_LIST);
			if (en_us_category_List == null) {
				en_us_category_List = getLocaleAdDisCategoryList(categoryList);
				putCache(CACHE_EN_US_ADDISTRICTCATEGORY_LIST, en_us_category_List);
			}
			return en_us_category_List;

		} else {
			List<AdDistrictCategory> zh_cn_category_List = (List<AdDistrictCategory>) getCache(CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST);
			if (zh_cn_category_List == null) {
				zh_cn_category_List = getLocaleAdDisCategoryList(categoryList);
				putCache(CACHE_ZH_CN_ADDISTRICTCATEGORY_LIST, zh_cn_category_List);
			}
			return zh_cn_category_List;

		}

	}

	@SuppressWarnings("unchecked")
	public static List<AdProgramCategory> getProgramCateGoryList() {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();
		List<AdProgramCategory> categoryList = (List<AdProgramCategory>) getCache(CACHE_PROGRAM_CATEGORY_LIST);
		if (categoryList == null) {

			categoryList = programCateGoryDao.findAll();

			putCache(CACHE_PROGRAM_CATEGORY_LIST, categoryList);
		}
		if ("en".equals(name) || "EN".equals(name)) {// 英文状态下的时候，去国际化菜单，需要进行深复制office，因为不可以改变数据库查询出来的对象，中文同理
			List<AdProgramCategory> en_us_category_List = (List<AdProgramCategory>) getCache(CACHE_EN_US_PROGRAM_CATEGORY_LIST);
			if (en_us_category_List == null) {
				en_us_category_List = getLocaleAdProgramCategoryList(categoryList);
				putCache(CACHE_EN_US_PROGRAM_CATEGORY_LIST, en_us_category_List);
			}
			return en_us_category_List;

		} else {
			List<AdProgramCategory> zh_cn_category_List = (List<AdProgramCategory>) getCache(CACHE_ZH_CN_PROGRAM_CATEGORY_LIST);
			if (zh_cn_category_List == null) {
				zh_cn_category_List = getLocaleAdProgramCategoryList(categoryList);
				putCache(CACHE_ZH_CN_PROGRAM_CATEGORY_LIST, zh_cn_category_List);
			}
			return zh_cn_category_List;

		}

	}

	public static Office getOffice(String id) {
		List<Office> list =getOfficeList();
		if(null != list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Office office = list.get(i);
				String office_id = office.getId();
				if(null != id && id.equals(office_id)){
					return office;
				}
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public static List<Office> getOfficeList() {
		Locale current_locale = LocaleContextHolder.getLocale();
		String name = current_locale.getLanguage();
		List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
		if (officeList == null) {
			User user = getUser();
			DetachedCriteria dc = officeDao.createDetachedCriteria();
			//dc.add(dataScopeFilter(user, dc.getAlias(), ""));
			dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG,
					BaseEntity.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("code"));
			officeList = officeDao.findAll();
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		if ("en".equals(name) || "EN".equals(name)) {// 英文状态下的时候，去国际化菜单，需要进行深复制office，因为不可以改变数据库查询出来的对象，中文同理
			List<Office> en_us_office_List = (List<Office>) getCache(CACHE_EN_US_OFFICE_LIST);
			if (en_us_office_List == null) {
				en_us_office_List = getLocaleOfficeList(officeList);
				putCache(CACHE_EN_US_OFFICE_LIST, en_us_office_List);
			}
			return en_us_office_List;

		} else {
			List<Office> zh_cn_officeList = (List<Office>) getCache(CACHE_ZH_CN_OFFICE_LIST);
			if (zh_cn_officeList == null) {
				zh_cn_officeList = getLocaleOfficeList(officeList);
				putCache(CACHE_ZH_CN_OFFICE_LIST, zh_cn_officeList);
			}
			return zh_cn_officeList;

		}
	}

	public static List<AdExternalProgram> getProgramList() {
		@SuppressWarnings("unchecked")
		List<AdExternalProgram> programList = (List<AdExternalProgram>) getCache(CACHE_FFMPEG_PROGRAM_LIST);
		if (programList == null) {

			programList = programDao
					.findProgramByProgramName(AdExternalProgram.PROGRAM_FFMPEG);
			putCache(CACHE_FFMPEG_PROGRAM_LIST, programList);
		}
		return programList;
	}

	/**
	 * get User By Id
	 *
	 * @param id
	 * @return
	 */
	public static User getUserById(String id) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(id)) {
			return userDao.get(id);
		} else {
			return null;
		}
	}

	/**
	 * 根据loginName取User
	 *
	 * @param loginName
	 * @return
	 */
	public static User getUserByLoginName(String loginName) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(loginName)) {
			return userDao.findByLoginName(loginName);
		} else {
			return null;
		}
	}

	/**
	 * 根据OfficeName取office
	 *
	 * @param name
	 * @return
	 */
	public static Office getOfficeByName(String name) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(name)) {
			List<Office> list= officeDao.findOfficeByName(name);
			if(null != list && list.size()>0){
				return list.get(0);
			}
		}
		return null;

	}

	/**
	 * 根据查询出来的菜单根据cookie的语言进行国际化
	 *
	 * @param menuList
	 *            数据库本身的菜单集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Menu> getLocaleMenuList(List<Menu> menuList) {
		try {
			List<Menu> localMenuList = (List<Menu>) DeepCopy.deepCopy(menuList);

			if (localMenuList != null && localMenuList.size() > 0) {
				for (int i = 0; i < localMenuList.size(); i++) {
					Menu menu = localMenuList.get(i);
					List<Dict> list = DictUtils.getDictList(menu.getName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						menu.setName(dict.getValue());
					}
				}
			}
			return localMenuList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据查询出来的角色名称根据cookie的语言进行国际化
	 *
	 * @param roleList
	 *            数据库本身的菜单集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Role> getLocaleRoleList(List<Role> roleList) {
		try {
			List<Role> localRoleList = (List<Role>) DeepCopy.deepCopy(roleList);

			if (localRoleList != null && localRoleList.size() > 0) {
				for (int i = 0; i < localRoleList.size(); i++) {
					Role role = localRoleList.get(i);
					List<Dict> list = DictUtils.getDictList(role.getName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						role.setName(dict.getValue());
					}
				}
			}
			return localRoleList;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * 根据查询出来的广告类型名称在字典中根据当前语言找到对应的国际化名称
	 *
	 * @param typeList
	 *            数据库中本身的广告类型集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<AdType> getLocaleAdTypeList(List<AdType> typeList) {
		try {
			List<AdType> localeTypeList = (List<AdType>) DeepCopy
					.deepCopy(typeList);
			if (localeTypeList != null && localeTypeList.size() > 0) {
				for (int i = 0; i < localeTypeList.size(); i++) {
					AdType type = localeTypeList.get(i);
					List<Dict> list = DictUtils.getDictList(type.getTypeName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						type.setTypeName(dict.getValue());
						type.setTypeDescription(dict.getDescription());
					}
				}
			}
			return localeTypeList;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据查询出来的office名称在字典中根据当前语言找到对应的国际化名称
	 *
	 * @param officeList
	 *            数据库中本身的用户级别集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Office> getLocaleOfficeList(List<Office> officeList) {
		try {
			List<Office> localeOfficeList = (List<Office>) DeepCopy
					.deepCopy(officeList);

			if (localeOfficeList != null && localeOfficeList.size() > 0) {
				for (int i = 0; i < localeOfficeList.size(); i++) {
					Office office = localeOfficeList.get(i);
					List<Dict> list = DictUtils.getDictList(office.getName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						office.setName(dict.getValue());
						office.setRemarks(dict.getDescription());
					}
				}
			}
			return localeOfficeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 获取系统全部的广告发送器，用于在给发送器发送数据时候判断改发送时是否过期
	 * @return
	 */

	public static Map<String,AdNetwork> getNetworkMap() {
		try {
			@SuppressWarnings("unchecked")
			Map<String,AdNetwork> map = (Map<String,AdNetwork>) getCache(CACHE_VALID_DATE_NETWORK_MAP);
			if(map == null || map.isEmpty()){
				map = new HashMap<String,AdNetwork>();
				Map<String,Object> querymap = new HashMap<String,Object>();
				querymap.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
				List<AdNetwork> list = networkDao.findAllNetwork(querymap);

				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						AdNetwork adNetwork = list.get(i);
						map.put(adNetwork.getNetworkId(),adNetwork);
					}
					putCache(CACHE_VALID_DATE_NETWORK_MAP, map);
				}
			}
			return map;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * 根据查询出来的广告分类名称在字典中根据当前语言找到对应的国际化名称
	 *
	 * @param categoryList
	 *            数据库中本身的用户级别集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<AdCategory> getLocaleAdCategoryList(
			List<AdCategory> categoryList) {
		try {
			List<AdCategory> localeCategoryList = (List<AdCategory>) DeepCopy
					.deepCopy(categoryList);

			if (localeCategoryList != null && localeCategoryList.size() > 0) {
				for (int i = 0; i < localeCategoryList.size(); i++) {
					AdCategory category = localeCategoryList.get(i);
					List<Dict> list = DictUtils.getDictList(category
							.getCategoryName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						category.setCategoryName(dict.getValue());
					}
				}
			}
			return localeCategoryList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据查询出来的地区分类名称在字典中根据当前语言找到对应的国际化名称
	 *
	 * @param categoryList
	 *            数据库中本身的用户级别集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<AdDistrictCategory> getLocaleAdDisCategoryList(
			List<AdDistrictCategory> categoryList) {
		try {
			List<AdDistrictCategory> localeCategoryList = (List<AdDistrictCategory>) DeepCopy
					.deepCopy(categoryList);

			if (localeCategoryList != null && localeCategoryList.size() > 0) {
				for (int i = 0; i < localeCategoryList.size(); i++) {
					AdDistrictCategory category = localeCategoryList.get(i);
					List<Dict> list = DictUtils.getDictList(category
							.getCategoryName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						category.setCategoryName(dict.getValue());
					}
				}
			}
			return localeCategoryList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据查询出来的 节目分类名称在字典中根据当前语言找到对应的国际化名称
	 *
	 * @param categoryList
	 *            数据库中本身的用户级别集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<AdProgramCategory> getLocaleAdProgramCategoryList(
			List<AdProgramCategory> categoryList) {
		try {
			List<AdProgramCategory> localeCategoryList = (List<AdProgramCategory>) DeepCopy
					.deepCopy(categoryList);

			if (localeCategoryList != null && localeCategoryList.size() > 0) {
				for (int i = 0; i < localeCategoryList.size(); i++) {
					AdProgramCategory category = localeCategoryList.get(i);
					List<Dict> list = DictUtils.getDictList(category
							.getCategoryName());
					if (null != list && list.size() > 0) {
						Dict dict = list.get(0);
						category.setCategoryName(dict.getValue());
					}
				}
			}
			return localeCategoryList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		getCacheMap().put(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
	}

	public static Map<String, Object> getCacheMap() {
		Map<String, Object> map = Maps.newHashMap();
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			return principal != null ? principal.getCacheMap() : map;
		} catch (UnavailableSecurityManagerException e) {
			e.printStackTrace();
		} catch (InvalidSessionException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Boolean checkUserIsAdvertiser() {
		User user = getUser();
		if (null == user.getAdvertiser())
			return true;
		return false;
	}

	public static Boolean checkuserIsCurrentUser(String id) {
		User currentUser = UserUtils.getUser();
		if (currentUser.isAdmin()) {
			return true;
		} else {
			if (null != id && id.equals(currentUser.getId())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static Boolean isAdmin() {
		User currentUser = UserUtils.getUser();
		if (currentUser.isAdmin()) {
			return true;
		} else {
			return false;
		}
	}
}
