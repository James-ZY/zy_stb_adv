/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.service.sys;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.StringUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.entity.sys.Log;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.repository.hibernate.sys.LogDao;
import com.gospell.aas.service.BaseService;

/**
 * 日志Service
 * 
 * @author free lance
 * @version 2013-6-2
 */
@Service("sysLogService")
@Transactional(readOnly = true)
public class SysLogService extends BaseService {

	@Autowired
	private LogDao logDao;

	public Log get(String id) {
		return logDao.get(id);
	}

	public Page<Log> find(Page<Log> page, Map<String, Object> paramMap) {
		DetachedCriteria dc = logDao.createDetachedCriteria();
		String createById = (String) paramMap.get("createById");
		dc.createAlias("createBy", "createBy");
		String name = (String) paramMap.get("createByName");
		//默认查询自己的
		if(org.apache.commons.lang3.StringUtils.isBlank(name) && org.apache.commons.lang3.StringUtils.isBlank(createById)){
			dc.add(Restrictions.eq("createBy.loginName", UserUtils.getUser().getLoginName()));
			dc.add(Restrictions.eq("createBy.name", UserUtils.getUser().getName()));
		}else{
			if (org.apache.commons.lang3.StringUtils.isNotBlank(createById)) {
				dc.add(Restrictions.eq("createBy.loginName", createById));
			} 
			if (org.apache.commons.lang3.StringUtils.isNotBlank(name)) {
				dc.add(Restrictions.eq("createBy.name", name));
			}	
		}
        //判断日志类型		 
		String logType= (String) paramMap.get("type");
		if (logType!=null && !logType.equals("")) {
			String logInfo = null;
			Integer type=Integer.parseInt(logType);
			if(type==3){
				logInfo = ApplicationContextHelper.getMessage("save.log.info");
			}else if(type==4){
				logInfo = ApplicationContextHelper.getMessage("update.log.info");
			}else if(type==5){
				logInfo = ApplicationContextHelper.getMessage("delete.log.info");
			}else if(type==6){
				logInfo = ApplicationContextHelper.getMessage("import.log.info");
			}else if(type==7){
				logInfo = ApplicationContextHelper.getMessage("export.log.info");
			}else if(type==8){
				logInfo = ApplicationContextHelper.getMessage("audit.log.info");
			}else if(type==9){
				logInfo = ApplicationContextHelper.getMessage("claim.log.info");
			}else if(type==10){
				logInfo = ApplicationContextHelper.getMessage("other.log.info");
			}
			if(logInfo!=null){
				dc.add(Restrictions.like("logInfo", logInfo.substring(0, logInfo.indexOf("{"))+"%"));				
			}
		}
		
		String exception = (String) (paramMap.get("exception"));
		if (org.apache.commons.lang3.StringUtils.isNotBlank(exception)) {
			dc.add(Restrictions.eq("type", Log.TYPE_EXCEPTION));
		}

		Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
		if (beginDate == null) {
			beginDate = org.apache.commons.lang3.time.DateUtils.setDays(new Date(), 1);
			paramMap.put("beginDate",
					DateUtils.formatDate(beginDate, "yyyy-MM-dd"));
		}
		Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
		if (endDate == null) {
			endDate = org.apache.commons.lang3.time.DateUtils.addDays(org.apache.commons.lang3.time.DateUtils.addMonths(beginDate, 1), -1);
			paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd"));
		}
		dc.add(Restrictions.between("createDate", beginDate, endDate));

		dc.addOrder(Order.desc("createDate"));
		return logDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(HttpServletRequest request, String logInfo, Exception ex) {
		User user = UserUtils.getUser();
		if (user != null && user.getId() != null) {

			StringBuilder params = new StringBuilder();
			int index = 0;
			for (Object param : request.getParameterMap().keySet()) {
				params.append((index++ == 0 ? "" : "&") + param + "=");
				params.append(StringUtils.abbr(
						org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((String) param,
								"password") ? "" : request
								.getParameter((String) param), 100));
			}

			Log log = new Log();
			log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
			log.setCreateBy(user);
			log.setCreateDate(new Date());
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setMethod(request.getMethod());
			log.setParams(params.toString());
			log.setException(ex != null ? ex.toString() : "");
			log.setLogInfo(logInfo);
			logDao.save(log);
		}
	}

	/**
	 * 
	 * @param id
	 *            实体Id用于判断是修改还是新增
	 * @param level
	 *            0表示修改或者添加 1表示删除  2查询 3导出 4导入 5表示审核 6认领 7其他
	 * @param businessName
	 *            业务的名称，比如添加用户的时候，businessName为用户ID
	 * @param businessId
	 *            业务的ID，比如添加用户的时候，businessID为用户的登录名
	 */
	public String getLogInfo(String id, int level, String businessName,
			String businessId) {
		Object[] object = new Object[2];
		object[0] = businessName;
		object[1] = businessId;
	 
		if (level == 0) {
			if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
				return ApplicationContextHelper.getMessage("save.log.info",
						object);
			} else {
				return ApplicationContextHelper.getMessage("update.log.info",
						object);
			}
		}else if(level == 1){
			return ApplicationContextHelper.getMessage("delete.log.info",
					object);
		}else if(level ==3){
			return ApplicationContextHelper.getMessage("export.log.info",
					object);
		}else if(level ==4){
			return ApplicationContextHelper.getMessage("import.log.info",
					object);
		}else if(level ==5){
			return ApplicationContextHelper.getMessage("audit.log.info",
					object);
		}else if(level ==6){
			return ApplicationContextHelper.getMessage("claim.log.info",
					object);
		} 
		return "";
	}
	
 

}
