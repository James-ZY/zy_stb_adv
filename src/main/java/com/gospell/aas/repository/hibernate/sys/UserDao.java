/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.repository.hibernate.sys;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.sys.User;

/**
 * 用户DAO接口
 * @author free lance
 * @version 2013-8-23
 */
@Repository
public class UserDao extends BaseDao<User> {
	
	public List<User> findAllList() {
		return find("from User where delFlag=:p1 order by id", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}
	
	public User findById(String id){
        return getByHql("from User where id = :p1 and delFlag = :p2", new Parameter(id, BaseEntity.DEL_FLAG_NORMAL));
    }
	
	public User findByLoginName(String loginName){
		return getByHql("from User where loginName = :p1 and delFlag = :p2", new Parameter(loginName, BaseEntity.DEL_FLAG_NORMAL));
	}
	
	public User findByNick(String nick){
		return getByHql("from User where delFlag = '0' and nick = :p1  ", new Parameter(nick));
	}
	
	public User findByMobile(String mobile){
		return getByHql("from User where delFlag = '0' and mobile = :p1  ", new Parameter(mobile));
	    //return getByHql("from User where phone = :p1  ", new Parameter(phone));
	}
	
	public int findCountByPhone(String phone){
	    List<User> list = find(" from  User where  phone=:p1 and delFlag<>1",new Parameter(phone));
	    if(list==null){
	    	return 0;
	    }
		return list.size();
	}
	
	public User getUserByIdWithoutFlag(String userId) {
	    return getByHql("from User where id = :p1", new Parameter(userId));
	}

	public int updateDelFlagById(String delFlag, String id){
		return update("update User set delFlag=:p1 where id = :p2", new Parameter(delFlag, id));
	}
	
	public int updatePasswordById(String newPassword, String id){
		return update("update User set password=:p1 where id = :p2", new Parameter(newPassword, id));
	}
	
	public int updateLoginInfo(String loginIp, Date loginDate, String id){
		return update("update User set loginIp=:p1, loginDate=:p2 where id = :p3", new Parameter(loginIp, loginDate, id));
	}
	
	public int updatePhotoById(String photo, String loginName){
        return update("update User set photo=:p1 where loginName = :p2", new Parameter(photo, loginName));
    }

	public int updatePhoneById(String userPhone, String id) {
		return update("update User set phone=:p1 where id = :p2", new Parameter(userPhone, id));
	}
	
	public int updateLoginNameById(String userPhone, String id) {
		return update("update User set loginName=:p1 where id = :p2", new Parameter(userPhone, id));
	}

	public int updateNameById(String userName, String id) {
		return update("update User set name=:p1 where id = :p2", new Parameter(userName, id));	
	}
	
	public int updateUserState(String state,String id){
		return update("update User set state =:p1 where id = :p2",new Parameter(state,id));
	}
}
