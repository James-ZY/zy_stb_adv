/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.repository.hibernate.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.sys.Role;

/**
 * 角色DAO接口
 * @author free lance
 * @version 2013-8-23
 */
@Repository
public class RoleDao extends BaseDao<Role> {

	public Role findByName(String name){
		return getByHql("from Role where delFlag = :p1 and name = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL, name));
	}
	
	@Override
	public List<Role> findAll(){
		return find("from Role r where delFlag = :p1 order by r.office.id", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}

//	@Query("from Role where delFlag='" + Role.DEL_FLAG_NORMAL + "' order by name")
//	public List<Role> findAllList();
//
	//@Query("select distinct r from Role r, User u where r in elements (u.roleList) and r.delFlag='" + Role.DEL_FLAG_NORMAL +
	//	"' and u.delFlag='" + User.DEL_FLAG_NORMAL + "' and u.id=?1 or (r.user.id=?1 and r.delFlag='" + Role.DEL_FLAG_NORMAL +
	//		"') order by r.name")
	public List<Role> findByUserId(String userId){  
	    String sqlString = "select distinct r from Role r, User u where r in elements (u.roleList) and u.id=:p1 order by r.name";
	    //sqlString = "";
	    Parameter parameter = new Parameter(userId);
	    List<Role> list = find(sqlString, parameter);
	    return list;
	}

}
