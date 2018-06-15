package com.gospell.aas.service.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.repository.mybatis.sys.IRoleDao;

 

 

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class UserRoleService {
	
	@Autowired
	private  IRoleDao userRoleDao;
	
	public int getUserRoleNumByUserId(String userId){
		
		return userRoleDao.getUserRoleNum(userId);
	}

}
