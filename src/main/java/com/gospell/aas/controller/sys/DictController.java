package com.gospell.aas.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.beanvalidator.BeanValidators;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.excel.ExportExcel;
import com.gospell.aas.common.utils.excel.ImportExcel;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.Dict;
import com.gospell.aas.service.sys.DictService;


/**
 * 字典Controller
 * @author free lance
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public Dict get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return dictService.get(id);
		}else{
			return new Dict();
		}
	}
	
    @RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"/list", ""})
	public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = dictService.findTypeList();
		model.addAttribute("typeList", typeList);
		List<Dict> dictLocale = Lists.newArrayList();
		Dict d = new Dict();
		d.setValue(String.valueOf(Dict.zh_CN));
		d.setLabel(getMessage("Chinese"));
		Dict d1 = new Dict();
		d1.setValue(String.valueOf(Dict.en_US));
		d1.setLabel(getMessage("English"));
		dictLocale.add(d);
		dictLocale.add(d1);
		model.addAttribute("dictLocale", dictLocale);
        Page<Dict> page = dictService.find(new Page<Dict>(request, response), dict); 
        model.addAttribute("page", page);
		return "/sys/dictList";
	}

    @RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "/form")
	public String form(Dict dict, Model model) {
    	List<Dict> list =Lists.newArrayList();
    	Dict dict1 = new Dict();
    	dict1.setLabel(getMessage("Chinese"));
    	dict1.setValue(String.valueOf(Dict.zh_CN));
    	list.add(dict1);
    	Dict dict2 = new Dict();
    	dict2.setLabel(getMessage("English"));
    	dict2.setValue(String.valueOf(Dict.en_US));
    	list.add(dict2);
    	model.addAttribute("dict", dict);
		model.addAttribute("adv_language", list);
		return "/sys/dictForm";
	}

    @RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "/save")//@Valid 
	public String save(Dict dict, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
 
		if (!beanValidator(model, dict)){
			return form(dict, model);
		}
		String logInfo=logService.getLogInfo(dict.getId(), 0, getMessage("dictionary"), dict.getType());
	
		try {
			dictService.save(dict);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"添加或者修改字典成功");
		} catch (Exception e) {
		 
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"添加或者修改字典失败",e.getMessage());
		}
		addMessage(redirectAttributes,  "msg.save.success");
		return "redirect:/sys/dict/?repage&type="+dict.getType();
	}
	
    @RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id,HttpServletRequest request, RedirectAttributes redirectAttributes) {
    	Dict d = get(id);
    	String logInfo=logService.getLogInfo(id, 1, getMessage("dictionary"), d.getType());
		
		try {
			dictService.delete(id);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除字典成功");
			addMessage(redirectAttributes, "msg.del.success");
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.info(UserUtils.getUser().getLoginName()+"删除字典失败",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
		}

		return "redirect:/sys/dict/?repage";
	}
    
	@RequiresPermissions("sys:advertiser:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = getMessage("advertiser.import.template")+".xlsx";
			List<Dict> list = Lists.newArrayList();
			list.add(new Dict());
			new ExportExcel(getMessage("advertiser.import.template")+getMessage("import.required"), Dict.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, getMessage("download.advertiser.template.fail"));
		}
		return "redirect:/sys/dict/?repage";
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Dict> list = ei.getDataList(Dict.class);
			for (Dict user : list) {
				try {
				 
						 
						BeanValidators.validateWithException(validator, user);
						dictService.save(user);
						successNum++;
					 
				} catch (ConstraintViolationException ex) {
					 
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					 
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			System.out.println("成功"+successNum+"条数据");
			addMessage(redirectAttributes, "import.success");
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "import.fail");
		}
		return "redirect:/sys/dict/?repage";
	}

	@RequiresPermissions("sys:advertiser:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Dict entity, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String name  ="字典数据";
			String fileName = name + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<Dict> page = dictService.findAll();
			new ExportExcel(name, Dict.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "export.fail");
		}
		return "redirect:/adv/advertiser/?repage";
	}
	

}
