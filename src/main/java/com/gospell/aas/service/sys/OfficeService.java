/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.gospell.aas.common.security.SystemAuthorizingRealm;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.repository.hibernate.sys.OfficeDao;
import com.gospell.aas.service.BaseService;

 

/**
 * 机构Service
 * @author ThinkGem
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends BaseService {

	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public Office get(String id) {
		return officeDao.get(id);
	}
	
	public List<Office> findAll(){
		List<Office> officeList =UserUtils.getOfficeList();
		List<Office> returnOfficelList = Lists.newArrayList();
		if(null != officeList && officeList.size()>0){
			String id = UserUtils.getUser().getOffice().getId();
			 
			 List<Office> currentOfficeChild = getChildOffice(id);
			 if(null == currentOfficeChild || currentOfficeChild.size()==0){
				 currentOfficeChild = Lists.newArrayList();
			 }
			 currentOfficeChild.add(UserUtils.getUser().getOffice());
			 for (int i = 0; i < officeList.size(); i++) {
				 Office office = officeList.get(i);
				for (int j = 0; j < currentOfficeChild.size(); j++) {
					 Office childoffice = currentOfficeChild.get(j);
					 if(office.getId().equals(childoffice.getId())){
						 returnOfficelList.add(office);
					 }
				}
			}
			 
			
			
		}
		return returnOfficelList;
	}
	
	public Office getOfficeByCode(String code){
		List<Office> list = officeDao.findOfficeByCode(code);
		if(null != list && list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	public Office getOfficeByName(String name){
		List<Office> list = officeDao.findOfficeByName(name);
		if(null != list && list.size() >0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 角色添加页面的用户级别除了运营商可以选择其他机构，其他只能选择自己本身的机构，不能选择其子机构
	 * @return
	 */
	public List<Office> findOfficeInRoleFrom(){
		List<Office> officeList =UserUtils.getOfficeList();
		List<Office> returnOfficelList = Lists.newArrayList();
		if(null != officeList && officeList.size()>0){
			String id = UserUtils.getUser().getOffice().getId();
			List<Office> currentOfficeChild  = null;
			if(UserUtils.getUser().getOffice().getParent() == null){
				currentOfficeChild = getChildOffice(id);
			}
			 if(null == currentOfficeChild || currentOfficeChild.size()==0){
				 currentOfficeChild = Lists.newArrayList();
			 }
			 currentOfficeChild.add(UserUtils.getUser().getOffice());
			 for (int i = 0; i < officeList.size(); i++) {
				 Office office = officeList.get(i);
				for (int j = 0; j < currentOfficeChild.size(); j++) {
					 Office childoffice = currentOfficeChild.get(j);
					 if(office.getId().equals(childoffice.getId())){
						 returnOfficelList.add(office);
					 }
				}
			}
			 
			
			
		}
		return returnOfficelList;
	}
	/**
	 * 获取下级机构，请注意最顶层没有父类节点
	 * @param id
	 */
	public List<Office> getChildOffice(String id){
		List<Office> childOffice = Lists.newArrayList();
		List<Office> officeList =UserUtils.getOfficeList();
		for (int i = 0; i < officeList.size(); i++) {
			Office office = officeList.get(i);
			if(office.getParent() != null ){
				String parentIds = office.getParentIds();
				String[] ids = parentIds.split(",");
				 for (int j = 0; j < ids.length; j++) {
					if(id.equals(ids[j])){
						childOffice.add(office);
					}
				}
			}
		}
		return childOffice;
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) throws Exception {
		 office.setType("1");
		office.setParent(this.get(office.getParent().getId()));
		String oldParentIds = office.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		office.setParentIds(office.getParent().getParentIds()+office.getParent().getId()+",");
		officeDao.clear();
		officeDao.save(office);
		// 更新子节点 parentIds
		List<Office> list = officeDao.findByParentIdsLike("%,"+office.getId()+",%");
		for (Office e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, office.getParentIds()));
		}
		officeDao.save(list);
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) throws Exception{
		officeDao.deleteById(id, "%,"+id+",%");
		systemRealm.clearAllCachedAuthorizationInfo();
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_ZH_CN_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_EN_US_OFFICE_LIST);
	}
	
}
