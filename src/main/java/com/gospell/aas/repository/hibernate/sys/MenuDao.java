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
import com.gospell.aas.entity.sys.Menu;

/**
 * 菜单DAO接口
 * 
 * @author free lance
 * @version 2013-8-23
 */
@Repository
public class MenuDao extends BaseDao<Menu> {

    public List<Menu> findByParentIdsLike(String parentIds) {
        return find("from Menu where parentIds like :p1", new Parameter(parentIds));
    }

    public List<Menu> findAllList() {
        return find("from Menu where delFlag=:p1 order by sort", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
    }

    public List<Menu> findByUserId(String userId){
    	 return find(
                 "select distinct m from Menu m, Role r, User u where m in elements (r.menuList) and r in elements (u.roleList)"
                         + " and m.delFlag=:p1 and r.delFlag=:p1 and u.delFlag=:p1 and u.id=:p2" + // or (m.user.id=:p2
                                                                                                   // and m.delFlag=:p1)"
                                                                                                   // +
                         " order by m.sort", new Parameter(BaseEntity.DEL_FLAG_NORMAL, userId));
	}

    public List<Menu> findByRoleId(String roleId) {
        // select * from sys_menu m where m.id in (SELECT menu_id FROM sys_role_menu WHERE role_id='retailer')
        // String sqlString =
        // "select distinct m from Menu m, Role r where m in elements (m.roleList) and r.id=?1 or m.role.id=?1  order by m.name";
        // Parameter parameter = new Parameter(roleId);
        // List<Menu> list = findBySql(sqlString, parameter);

        // return list;

        return find("select distinct m from Menu m, Role r where m in elements (r.menuList) " + " and r.id=:p1"
                + " order by m.id", new Parameter(roleId));
    }

    /**
     * 根据菜单type取menu
     * 
     * @param type
     * @return
     */
    public List<Menu> findByType(String type) {
        // select distinct m.* from sys_menu m where m.type='1' order by m.id,m.sort
        // String sqlString = "select distinct m from Menu m where m.type=?1  order by m.id,m.sort";
        // Parameter parameter = new Parameter(type);
        // List<Menu> list = findBySql(sqlString, parameter);

        // return list;

        return find("from Menu where type=:p1 order by id,sort", new Parameter(type));
    }
    
    
    public List<Menu> findByRoleIdAndType(String roleId, String type) {
        return find("select distinct m from Menu m, Role r where m in elements (r.menuList) " + " and r.id=:p1 and type=:p2"
                + " order by m.id", new Parameter(roleId, type));
    }

}
