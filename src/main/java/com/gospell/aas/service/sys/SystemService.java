/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.security.SystemAuthorizingRealm;
import com.gospell.aas.common.security.utils.Digests;
import com.gospell.aas.common.utils.Encodes;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.sys.Menu;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.Role;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.hibernate.sys.MenuDao;
import com.gospell.aas.repository.hibernate.sys.OfficeDao;
import com.gospell.aas.repository.hibernate.sys.RoleDao;
import com.gospell.aas.repository.hibernate.sys.UserDao;
import com.gospell.aas.repository.mybatis.sys.IRoleDao;
import com.gospell.aas.service.BaseService;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Service
@Transactional(readOnly = true)
// 报错：Connection is read-only. Queries leading to data modification are not
// allowed
// @Transactional
public class SystemService extends BaseService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;

	@Autowired
	private SystemAuthorizingRealm systemRealm;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private IRoleDao roleMybatisDao;

	public User getUser(String id) {
		return userDao.get(id);
	}

	public Office getOffice(String id) {
		return officeDao.get(id);
	}

	public Page<User> findUser(Page<User> page, User user) {
		DetachedCriteria dc = userDao.createDetachedCriteria();
		User currentUser = UserUtils.getUser();
		dc.createAlias("company", "company");
		if (user.getCompany() != null
				&& StringUtils.isNotBlank(user.getCompany().getId())) {
			dc.add(Restrictions.or(
					Restrictions.eq("company.id", user.getCompany().getId()),
					Restrictions.like("company.parentIds", "%,"
							+ user.getCompany().getId() + ",%")));
		}
		dc.createAlias("office", "office");
		if (user.getOffice() != null
				&& StringUtils.isNotBlank(user.getOffice().getId())) {
			dc.add(Restrictions.or(
					Restrictions.eq("office.id", user.getOffice().getId()),
					Restrictions.like("office.parentIds", "%,"
							+ user.getOffice().getId() + ",%")));
		}
		// 如果不是超级管理员，则不显示超级管理员用户
		if (!currentUser.isAdmin()) {
			dc.add(Restrictions.ne("id", "admin"));
		}
		dc.add(dataScopeFilter(currentUser, "office", ""));
		// System.out.println(dataScopeFilterString(currentUser, "office", ""));
		if (StringUtils.isNotEmpty(user.getLoginName())) {
			dc.add(Restrictions.like("loginName", "%" + user.getLoginName()
					+ "%"));
		}
		if (StringUtils.isNotEmpty(user.getName())) {
			dc.add(Restrictions.like("name", "%" + user.getName() + "%"));
		}
		User loginUser = UserUtils.getUser();
		if (null != loginUser.getAdvertiser()) {
			dc.createAlias("advertiser", "advertiser");
			dc.add(Restrictions.eq("advertiser.id", loginUser.getAdvertiser()
					.getId()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		if (!StringUtils.isNotEmpty(page.getOrderBy())) {
			dc.addOrder(Order.desc("company.name")).addOrder(Order.asc("name"));
		}
		page = userDao.find(page, dc);
		List<User> list = page.getList();
		if (list != null && list.size() > 0) {
		
			for (int i = 0; i < list.size(); i++) {
				User page_user = list.get(i);
				Office office = UserUtils.getOffice(page_user.getOffice()
						.getId());
				page_user.setCompany(office);
			}
			List<Role> cacheRoleList = UserUtils.getRoleList();
			Map<String, Role> map = new HashMap<>();
			for (int i = 0; i < cacheRoleList.size(); i++) {
				Role role = cacheRoleList.get(i);
				map.put(role.getId(), role);
			}
			if (cacheRoleList != null) {
				for (int i = 0; i < list.size(); i++) {
					User user1 = list.get(i);
					List<Role> roleList = user1.getRoleList();
					for (int j = 0; j < roleList.size(); j++) {
						Role r = roleList.get(j);
						if (map.containsKey(r.getId())) {
							r.setName(map.get(r.getId()).getName());
						}
					}
				}
			}
		}
		return page;
	}

	// 取用户的数据范围
	public String getDataScope(User user) {
		return dataScopeFilterString(user, "office", "");
	}

	public User getUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	public User getUserById(String id) {
		return userDao.findById(id);
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {

		userDao.clear();
		userDao.save(user);

		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteUser(String id) {
		userDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName,
			String newPassword) {
		userDao.updatePasswordById(entryptPassword(newPassword), id);
		systemRealm.clearCachedAuthorizationInfo(loginName);
	}

	@Transactional(readOnly = false)
	public void updateUserLoginInfo(String id) {
		userDao.updateLoginInfo(SecurityUtils.getSubject().getSession()
				.getHost(), new Date(), id);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt,
				HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)
				+ Encodes.encodeHex(hashPassword));
	}

	// -- Role Service --//

	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role findRoleByName(String name) {
		return roleDao.findByName(name);
	}

	public List<Role> findAllRole() {
		return UserUtils.getRoleList();
	}

	public List<Role> queryAllRole() {
		User user = UserUtils.getUser();
		List<Role> roleAll = UserUtils.getRoleList();
		if (user.isAdmin()) {
			return roleAll;
		} else {
			List<Role> roleList = Lists.newArrayList();
			Office office = user.getOffice();
			List<Office> currentOfficeChild = Lists.newArrayList();
			if (office.getParent() == null) {
				currentOfficeChild = officeService.getChildOffice(office
						.getId());
			}

			if (null == currentOfficeChild || currentOfficeChild.size() == 0) {
				currentOfficeChild = Lists.newArrayList();
			}
			currentOfficeChild.add(office);
			for (int i = 0; i < roleAll.size(); i++) {
				Office roleoffice = roleAll.get(i).getOffice();
				if (null != roleoffice) {
					String id = roleoffice.getId();
					for (int j = 0; j < currentOfficeChild.size(); j++) {
						String o_id = currentOfficeChild.get(j).getId();
						if (o_id.equals(id)) {
							roleList.add(roleAll.get(i));
						}
					}
				}
			}
			return roleList;
		}
	}

	/**
	 * 当用户在用户管理页面选择机构的时候，用户根据级别进行变更
	 * 
	 * @param officeId
	 * @return
	 */
	public List<Role> findAllRoleByOffice(String officeId) {
		List<Role> list = Lists.newArrayList();
		List<Role> roleList = UserUtils.getRoleList();
		if (roleList != null && roleList.size() > 0) {
			for (int i = 0; i < roleList.size(); i++) {
				Office office = roleList.get(i).getOffice();
				if (null != office) {
					String oId = office.getId();
					if (oId.equals(officeId)) {
						list.add(roleList.get(i));
					}
				}
			}
		}
		return list;
	}

	/**
	 * 当用户在用户管理页面选择机构的时候，用户根据级别进行变更
	 * 
	 * @param officeId
	 * @return
	 */
	public List<Role> findAllRoleBySelectOffice(String officeId) {
		List<Role> list = Lists.newArrayList();
		List<Role> roleList = UserUtils.getRoleList();
		if (roleList != null && roleList.size() > 0) {
			for (int i = 0; i < roleList.size(); i++) {
				Office office = roleList.get(i).getOffice();
				if (null != office) {
					String oId = office.getId();
					if (oId.equals(officeId)) {
						list.add(roleList.get(i));
					}
				}
			}
		}

		List<Role> cacheRoleList = UserUtils.getRoleList();
		if (cacheRoleList != null && list != null) {
			Map<String, Role> map = new HashMap<>();
			for (int i = 0; i < cacheRoleList.size(); i++) {
				Role role = cacheRoleList.get(i);
				map.put(role.getId(), role);
			}

			for (int i = 0; i < list.size(); i++) {
				Role r = list.get(i);
				if (map.containsKey(r.getId())) {
					r.setName(map.get(r.getId()).getName());
				}
			}
		}
		return list;
	}

	@Transactional(readOnly = false)
	public void saveRole(Role role) throws Exception {
		roleDao.clear();
		roleDao.save(role);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ROLE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ROLE_LIST);
	}

	@Transactional(readOnly = false)
	public void deleteRole(String id) throws Exception {
		roleDao.deleteById(id);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_ROLE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_ROLE_LIST);
	}

	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, String userId) {
		User user = userDao.get(userId);
		List<String> roleIds = user.getRoleIdList();
		List<Role> roles = user.getRoleList();
		//
		if (roleIds.contains(role.getId())) {
			roles.remove(role);
			saveUser(user);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, String userId) {
		User user = userDao.get(userId);
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	// -- Menu Service --//

	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public Menu getMenuByCache(String id) {
		List<Menu> list = findAllMenu();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (id != null && id.equals(list.get(i).getId())) {
					return list.get(i);
				}
			}
		}
		return null;
	}

	public List<Menu> findAllMenu() {
		return UserUtils.getMenuList();
	}

	public List<Menu> findRealAllMenu() {
		return UserUtils.getRealMenuList();
	}

	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) throws Exception {
		menu.setParent(this.getMenu(menu.getParent().getId()));
		String oldParentIds = menu.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		menu.setParentIds(menu.getParent().getParentIds()
				+ menu.getParent().getId() + ",");
		menuDao.clear();
		menuDao.save(menu);
		// 更新子节点 parentIds
		List<Menu> list = menuDao.findByParentIdsLike("%," + menu.getId()
				+ ",%");
		for (Menu e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds,
					menu.getParentIds()));
		}
		menuDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_MENU_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_MENU_LIST);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(String id) throws Exception {
		menuDao.deleteById(id, "%," + id + ",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_MENU_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_MENU_LIST);
	}

	// @Cacheable(value = GlobalStatic.qxCacheKey, key =
	// "'getRolesAsString_'+#userId")
	public Set<String> getRoleAsString(String userId) throws Exception {
		List<Role> list = roleDao.findByUserId(userId);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		for (Role r : list) {
			set.add(r.getId());
		}
		return set;
	}

	// @Cacheable(value = GlobalStatic.qxCacheKey, key =
	// "'getPermissionsAsString_'+#userId")
	public Set<String> getPermissionsAsString(String userId) throws Exception {
		List<Menu> setMenu = findAllMenuByUserId(userId);
		if (CollectionUtils.isEmpty(setMenu)) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		for (Menu m : setMenu) {
			if (StringUtils.isBlank(m.getHref())) {
				continue;
			}
			set.add(m.getHref());
		}
		return set;
	}

	private List<Menu> findAllMenuByUserId(String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return null;
		}

		List<Role> roles = roleDao.findByUserId(userId);
		if (CollectionUtils.isEmpty(roles)) {
			return null;
		}
		List<Menu> list = new ArrayList<Menu>();
		for (Role role : roles) {
			List<Menu> menus = menuDao.findByRoleId(role.getId());
			if (CollectionUtils.isEmpty(menus)) {
				continue;
			}
			list.addAll(menus);
		}

		return list;

	}

	/**
	 * 根据菜单type取menu
	 * 
	 * @param type
	 * @return
	 */
	public List<Menu> findMenuByType(String type) {
		return menuDao.findByType(type);
	}

	public List<User> findAuditUser() {
		Role role = roleDao.get(Role.AUDIT_ID);
		return role.getUserList();
	}

}
