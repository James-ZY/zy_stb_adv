/**
 * 
 */
package com.gospell.aas.service.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.DataBaseManage;
import com.gospell.aas.entity.adv.Adelement;
import com.gospell.aas.entity.sys.DataBaseRecord;
import com.gospell.aas.service.adv.AdelementService;
import com.gospell.aas.service.sys.DataBaseRecordService;


/**
 * 广告投放自动结束Services
 * 
 */
 
public class PutEndTask{

	private Logger logger = LoggerFactory.getLogger(PutEndTask.class);
	
	private AdelementService adelementService = ApplicationContextHelper.getBean(AdelementService.class);
	
	private DataBaseRecordService dataBaseRecordService = ApplicationContextHelper.getBean(DataBaseRecordService.class);
	public void task(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("showStatus", Adelement.ADV_STATUS_SHOW);
		map.put("endStatus", Adelement.ADV_STATUS_END);
		map.put("endDate", new Date());
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		try {
			adelementService.updatePutEnd(map);
			System.out.println("调用广告投放结束成功");
			logger.info("调用广告投放结束成功");
		} catch (Exception e) {
			logger.error("调用广告投放结束任务失败", e);
			e.printStackTrace();
		}
	}
 
    public void dataBaseManage(){
    	System.out.println("Database backup start======================");
    	String path = DataBaseManage.backup();
    	DataBaseRecord model = new DataBaseRecord();
    	model.setRecordPath(path);
    	model.setRecordName(path.split("/")[path.split("/").length-1]);
    	org.apache.shiro.mgt.SecurityManager securityManager =ApplicationContextHelper.getBean("securityManager");
    	SecurityUtils.setSecurityManager(securityManager);
    /*	User user = UserUtils.getUser();
    	System.out.println(user.getName());*/
    	model.setRemarks("Database backup");
    	
    	try {
			dataBaseRecordService.save(model);
			logger.info("Database backup success");
		} catch (Exception e) {
			logger.error("Database backup failure");
			e.printStackTrace();
		}
    }
  
}
