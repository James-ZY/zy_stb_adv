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
import com.gospell.aas.entity.sys.Help;



/**
 * 帮助DAO接口
 * @author free lance
 * @version 2013-8-23
 */
@Repository
public class HelpDao extends BaseDao<Help> {

	 public List<Help> getHelpByFlag(Integer status,Integer flag,Integer locale){
		 return find("from Help where delFlag= :p1 and status =:p2 and flag = :p3 and locale= :p4",new Parameter(BaseEntity.DEL_FLAG_NORMAL,status,flag,locale));
	 }
}
