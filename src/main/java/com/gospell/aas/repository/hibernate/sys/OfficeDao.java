/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.repository.hibernate.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gospell.aas.common.persistence.BaseDao;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Parameter;
import com.gospell.aas.entity.sys.Office;

 

/**
 * 机构DAO接口
 * 
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class OfficeDao extends BaseDao<Office> {

    public List<Office> findByParentIdsLike(String parentIds) {
        return find("from Office where parentIds like :p1", new Parameter(parentIds));
    }
    
    @Override
    public List<Office> findAll() {
        return find("from Office where delFlag = :p1 order by code", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
    }

 
    public List<Office> findOfficeByCode(String code) {
        return find("from Office where delFlag = :p1 and code = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL,code));
    }
    public List<Office> findOfficeByName(String name) {
        return find("from Office where delFlag = :p1 and name = :p2", new Parameter(BaseEntity.DEL_FLAG_NORMAL,name));
    }
     
}
