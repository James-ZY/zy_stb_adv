/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.service.sys;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.FileUtils;
import com.gospell.aas.entity.sys.Help;
import com.gospell.aas.repository.hibernate.sys.HelpDao;
import com.gospell.aas.repository.mybatis.sys.IHelpDao;
import com.gospell.aas.service.BaseService;

/**
 * 帮助Service
 * 
 * @author free lance
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class HelpService extends BaseService {

    @Autowired
    private HelpDao helpDao;
    
    @Autowired
    private IHelpDao mybatisDao;

    public Help get(String id) {
      
        return helpDao.get(id);
    }

    public Page<Help> find(Page<Help> page, Help entity) { 
		page.setOrderBy("h.flag asc,h.status desc,h.locale,u.login_name");
		entity.setPage(page);
		List<Help> list = mybatisDao.findAll(entity);
	 
		page.setList(list);
	

		return page;
    }
    
    /**
     * 获取当前有效的使用说明书
     * @return
     */
    public List<Help> findHelpValid(){
    	Help h = new Help();
    	h.setStatus(Help.HELP_EFFECTIVE_STAUTS);
    	h.setFlag(Help.HELP_INSTRUCTIONS_FLAG);
    	Locale current_locale = LocaleContextHolder.getLocale();
    	String name = current_locale.getLanguage();
		if ("en".equals(name) || "EN".equals(name)) {
			int locale = Help.en_US;
			h.setHelpLocale(locale);
		}else{
			h.setHelpLocale(Help.zh_CN);
		}
    	return mybatisDao.findHelpValid(h);
    }
    
   

    @Transactional(readOnly = false)
    public void save(Help help) throws Exception{
    	helpDao.save(help);
    }
    
    @Transactional(readOnly = false)
    public void saveValid(Help help) throws Exception{
    	helpDao.clear();
    	Integer flag = help.getFlag();
    	List<Help> list = helpDao.getHelpByFlag(Help.HELP_EFFECTIVE_STAUTS, flag,help.getHelpLocale());
    	if(null != list&& list.size()>0){
    		for (int i = 0; i < list.size(); i++) {
				Help h = list.get(i);
				h.setStatus(Help.HELP_INVALID_STATUS);
			}
    	}
    	helpDao.save(help);
    }

    @Transactional(readOnly = false)
    public void delete(String id) throws Exception{
    	Help entity = get(id);
    	String path = entity.getFilePath();
    	
        helpDao.deleteById(id);
        //删除文档在服务器的数据
    	if(StringUtils.isNotBlank(path)){
    		String real = FileUtils.getUploadFileRealPath();
    		String allPath = real + path;
   		 FileUtils.delFile(allPath);
    	}
     }

}
