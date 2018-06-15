/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.service.sys;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.CacheUtils;
import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.repository.hibernate.sys.DictDao;
import com.gospell.aas.service.BaseService;

/**
 * 字典Service
 * 
 * @author free lance
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class DictService extends BaseService {

    @Autowired
    private DictDao dictDao;

    public Dict get(String id) {
        // Hibernate 查询
        return dictDao.get(id);
    }

    public Page<Dict> find(Page<Dict> page, Dict dict) {
 
        
        // Hibernate 查询
        DetachedCriteria dc = dictDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(dict.getType())) {
            dc.add(Restrictions.eq("type", dict.getType()));
        }
        if (StringUtils.isNotEmpty(dict.getDescription())) {
            dc.add(Restrictions.like("description", "%" + dict.getDescription() + "%"));
        }
        if(null != dict.getDictLocale()){
        	int locale = dict.getDictLocale();
        	if(locale == Dict.zh_CN){
        		   dc.add(Restrictions.eq("dictLocale", locale));
        	}else{
        		dc.add(Restrictions.eq("dictLocale", Dict.en_US));
        	}
        }
        String value = dict.getValue();
        if(StringUtils.isNotBlank(value) ){
        	  dc.add(Restrictions.like("value", value.trim() + "%"));
        }
        String label = dict.getLabel();
        if(StringUtils.isNotBlank(label) ){
      	  dc.add(Restrictions.like("label", label.trim() + "%"));
        }
        dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("type")).addOrder(Order.asc("dictLocale")).addOrder(Order.asc("sort"));
     
        
        
        return dictDao.find(page, dc);
    }
    
    public Page<Dict> findAll() {
    	Page<Dict> page = new Page<Dict>();
        DetachedCriteria dc = dictDao.createDetachedCriteria();
        
        dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("dictLocale")).addOrder(Order.asc("type")).addOrder(Order.asc("sort"));
        
     
        
        
        return dictDao.find(page, dc);
    }
    
 

    public List<String> findTypeList() {
        return dictDao.findTypeList();
    }

    @Transactional(readOnly = false)
    public void save(Dict dict) throws Exception {
        dictDao.save(dict);
        CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
        CacheUtils.remove(DictUtils.CACHE_ZH_CN_DICT_MAP);
        CacheUtils.remove(DictUtils.CACHE_EN_US_DICT_MAP);
    }

    @Transactional(readOnly = false)
    public void delete(String id) throws Exception {
        dictDao.deleteById(id);
        CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
        CacheUtils.remove(DictUtils.CACHE_ZH_CN_DICT_MAP);
        CacheUtils.remove(DictUtils.CACHE_EN_US_DICT_MAP);
    }

}
