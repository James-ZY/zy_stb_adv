package com.gospell.aas.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gospell.aas.common.utils.JobMethod;
import com.gospell.aas.service.quartz.PutEndTask;
import com.gospell.aas.service.sys.QuartzJobService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DataBaseManage;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.DataBaseRecord;
import com.gospell.aas.service.sys.DataBaseRecordService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 数据库备份还原Controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/sys/database")
public class DataBaseController extends BaseController {
	@Autowired
	private DataBaseRecordService dataBaseService;
	@Autowired
	private QuartzJobService quartzJobService;
	@Resource(name = "JobMethod")
	private JobMethod jobMethod;

	@ModelAttribute
	public DataBaseRecord get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return dataBaseService.get(id);
		} else {
			return new DataBaseRecord();
		}
	}

	@RequiresPermissions("sys:database:view")
	@RequestMapping(value = { "list", "" })
	public String list(DataBaseRecord database, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<DataBaseRecord> page = dataBaseService.find(new Page<DataBaseRecord>(
				request, response), database);
		if (null != database.getCreateDateStart()) {
			model.addAttribute("createDateStart", DateUtils.formatDate(
					database.getCreateDateStart(), "yyyy-MM-dd"));
		}
		if (null != database.getCreateDateEnd()) {
			model.addAttribute("createDateEnd", DateUtils.formatDate(
					database.getCreateDateEnd(), "yyyy-MM-dd"));
		}
		model.addAttribute("page", page);
		return "/sys/dataBaseList";
	}

	
	@RequiresPermissions("sys:database:edit")
	@RequestMapping(value = "/backup")
	public String backup(DataBaseRecord database, HttpServletRequest request, Model model) {
		
		model.addAttribute("database", database);
		return "redirect:/sys/database/?repage";
	}
	
	@RequiresPermissions("sys:database:edit")
	@RequestMapping(value = "/restore")
	public String restore(DataBaseRecord database, HttpServletRequest request, Model model) {
		if (StringUtils.isNotBlank(database.getId())) {
			database =  dataBaseService.get(database.getId());
		}
		String path = request.getSession().getServletContext().getRealPath("");
		String rPath;
		if(path.split("/").length>1){
			rPath = path.substring(0, path.lastIndexOf("/"));			
		}else{
			rPath = path.substring(0, path.lastIndexOf("\\"));		
		}
		DataBaseManage.restore(rPath+database.getRecordPath());
	   model.addAttribute("database", database);
		return "redirect:/sys/database/?repage";
	}

	@RequiresPermissions("sys:database:edit")
	@RequestMapping(value = "/runJobNow")
	public String runJobNow(DataBaseRecord database, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try {
			PutEndTask ta = new PutEndTask();
			ta.dataBaseManage();
			addMessage(redirectAttributes, "msg.backup.success");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "msg.backup.fail");
		}
		return "redirect:/sys/database/?repage";
	}

}
