package com.gospell.aas.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.JobMethod;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.ScheduleJob;
import com.gospell.aas.service.sys.QuartzJobService;



/**
 * 定时任务Controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/sys/task")
public class QuartzTaskController extends BaseController {
	@Autowired
	private QuartzJobService quartzJobService;
	@Resource(name = "JobMethod")
	private JobMethod jobMethod;

	@ModelAttribute
	public ScheduleJob get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return quartzJobService.get(id);
		} else {
			return new ScheduleJob();
		}
	}

	@RequiresPermissions("sys:task:view")
	@RequestMapping(value = { "list", "" })
	public String list(ScheduleJob scheduleJob, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ScheduleJob> page = quartzJobService.find(new Page<ScheduleJob>(
				request, response), scheduleJob);
		if (null != scheduleJob.getCreateDateStart()) {
			model.addAttribute("createDateStart", DateUtils.formatDate(
					scheduleJob.getCreateDateStart(), "yyyy-MM-dd"));
		}
		if (null != scheduleJob.getCreateDateEnd()) {
			model.addAttribute("createDateEnd", DateUtils.formatDate(
					scheduleJob.getCreateDateEnd(), "yyyy-MM-dd"));
		}
		model.addAttribute("page", page);
		return "/sys/quartzTaskList";
	}

	@RequiresPermissions("sys:task:edit")
	@RequestMapping(value = "/form")
	public String form(ScheduleJob scheduleJob,HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		scheduleJob.setJobGroup("QuartzTask");
		scheduleJob.setBeanClass("com.gospell.aas.service.quartz.PutEndTask");
		scheduleJob.setExecuteMethod("dataBaseManage");
		scheduleJob.setShowClass("com.gospell.***.PutEndTask");
		model.addAttribute("scheduleJob", scheduleJob);
		return "/sys/quartzTaskForm";
	}

	@RequiresPermissions("sys:task:edit")
	@RequestMapping(value = "/save")
	public String save(ScheduleJob entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, request,model,redirectAttributes);
		}
		entity.setBeanClass("com.gospell.aas.service.quartz.PutEndTask");
		entity.setExecuteMethod("dataBaseManage");
		String name= entity.getJobName();
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("task.save"), name);
		try {
			quartzJobService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存或者修改任务调度："+entity.getJobName()+"成功");
			addMessage(redirectAttributes, "msg.save.success");
			jobMethod.testInit(entity);
			return "redirect:/sys/task/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"保存或者修改任务调度："+entity.getJobName()+"失败");
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/sys/task/?repage";
		}
	}
	
	
	@RequiresPermissions("sys:task:edit")
	@RequestMapping(value = "/operate")
	public String operate(ScheduleJob scheduleJob, HttpServletRequest request, Model model) {
		String name= scheduleJob.getJobName();
		String logInfo=logService.getLogInfo(scheduleJob.getId(), 0, getMessage("task.save"), name);
		if(scheduleJob.getJobStatus().equals("0")){
			try {
				jobMethod.pauseJob(scheduleJob);
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"暂停任务："+scheduleJob.getJobName()+"成功");
				quartzJobService.save(scheduleJob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
			jobMethod.resumeJob(scheduleJob);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"启动任务："+scheduleJob.getJobName()+"成功");
			quartzJobService.save(scheduleJob);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("scheduleJob", scheduleJob);
		return "redirect:/sys/task/?repage";
	}
}
