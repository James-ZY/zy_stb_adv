package com.gospell.aas.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.FileUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.entity.sys.Help;
import com.gospell.aas.service.sys.HelpService;

/**
 * 字典Controller
 * 
 * @author free lance
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "/sys/help")
public class HelpController extends BaseController {

	@Autowired
	private HelpService helpService;

	private String helpFileUploadMaxSize = Global.getConfig("help.file.max");
	@ModelAttribute
	public Help get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return helpService.get(id);
		} else {
			return new Help();
		}
	}

	@RequiresPermissions("sys:help:view")
	@RequestMapping(value = { "/list", "" })
	public String list(Help help, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<Help> page = helpService.find(new Page<Help>(request, response),
				help);
		model.addAttribute("page", page);
 
		return "/sys/helpList";
	}

	@RequiresPermissions("sys:help:view")
	@RequestMapping(value = "/form")
	public String form(Help help, Model model) {

		model.addAttribute("help", help);
		List<Dict> list = Lists.newArrayList();
		Dict dict1 = new Dict();
		dict1.setLabel(getMessage("Chinese"));
		dict1.setValue(String.valueOf(Dict.zh_CN));
		list.add(dict1);
		Dict dict2 = new Dict();
		dict2.setLabel(getMessage("English"));
		dict2.setValue(String.valueOf(Dict.en_US));
		list.add(dict2);
		model.addAttribute("adv_language", list);
		model.addAttribute("maxSize",helpFileUploadMaxSize);
		return "/sys/helpForm";
	}

	@RequiresPermissions("sys:help:edit")
	@RequestMapping(value = "/save")
	// @Valid
	public String save(Help help, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, help)) {
			return form(help, model);
		}
		String logInfo=logService.getLogInfo(help.getId(), 0, getMessage("hlep.doc"), help.getFileName());
		if(null == help.getStatus()){
			help.setStatus(Help.HELP_INVALID_STATUS);
		}
		try {
			String id = help.getId();
			String oldFilePath = help.getOldFilePath();
			helpService.save(help);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改帮助文档成功");
			//如果是修改文档，当文档变更了，需要删除原来的文档
			if(StringUtils.isNotBlank(id)){
				String filePath = help.getFilePath();
				if(null != filePath && oldFilePath != null){
					if(!filePath.equals(oldFilePath)){
						String fileRealPath = FileUtils.getUploadFileRealPath()+oldFilePath;
						FileUtils.deleteFile(fileRealPath);
					}
				}
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "msg.save.fail");
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改帮助文档失败",e.getMessage());
		}

		return "redirect:/sys/help/list";
	}

	@RequiresPermissions("sys:help:edit")
	@RequestMapping(value = "/startvalid")
	// @Valid
	public String startvalid(Help help, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, help)) {
			return form(help, model);
		}
	 
		String logInfo=getMessage("network.start.log",new Object[]{getMessage("hlep.doc"),help.getFileName()});
		help.setStatus(Help.HELP_EFFECTIVE_STAUTS);
		try {
			helpService.saveValid(help);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"启用帮助文档成功");
			addMessage(redirectAttributes, "msg.save.success");
		} catch (Exception e) {
			addMessage(redirectAttributes, "msg.save.fail");
			logService.save(request, logInfo, null);
			logger.error(UserUtils.getUser().getLoginName()+"启用帮助文档失败",e.getMessage());
		}
		
		return "redirect:/sys/help/list";
	}

	@RequiresPermissions("sys:help:edit")
	@RequestMapping(value = "/closevalid")
	// @Valid
	public String closevalid(Help help, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, help)) {
			return form(help, model);
		}
		help.setStatus(Help.HELP_INVALID_STATUS);
		String logInfo=getMessage("network.stop.log",new Object[]{getMessage("hlep.doc"),help.getFileName()});
 
		try {
			helpService.save(help);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"停用帮助文档成功");
			addMessage(redirectAttributes, "msg.save.success");
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"停用帮助文档失败",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
		}
	
		return "redirect:/sys/help/?repage";
	}

	@RequiresPermissions("sys:help:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		Help help = get(id);
		String logInfo=logService.getLogInfo(help.getId(), 1, getMessage("hlep.doc"), help.getFileName());
		try {
			helpService.delete(id);
			addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.error(UserUtils.getUser().getLoginName()+"删除帮助文档成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "msg.del.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除帮助文档失败",e.getMessage());
		}

		return "redirect:/sys/help/?repage";
	}

	/**
	 * 帮助文档下载路径
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_help_file", method = RequestMethod.GET)
	public String findhelpFile(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Help> list = helpService.findHelpValid();
			if (null != list && list.size() > 0) {
				Help h = list.get(0);
				return h.getFilePath();
			} else {
				return "";
			}
		} catch (Exception e) {
		 
			return "";
		}
	}

}
