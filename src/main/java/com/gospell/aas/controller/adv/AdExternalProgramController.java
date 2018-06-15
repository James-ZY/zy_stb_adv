package com.gospell.aas.controller.adv;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdExternalProgram;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.service.adv.AdExternalProgramService;

@Controller
@RequestMapping(value = "/adv/program")
public class AdExternalProgramController extends BaseController {

	@Autowired
	private AdExternalProgramService thisService;
	 

	@ModelAttribute
	public AdExternalProgram get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdExternalProgram();
		}
	}

	@RequiresPermissions("adv:program:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdExternalProgram entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdExternalProgram> page = thisService.find(new Page<AdExternalProgram>(request, response), entity);
		model.addAttribute("page", page);
		return "/adprogram/programList";
	}

	@RequiresPermissions("adv:program:view")
	@RequestMapping(value = "/form")
	public String form(AdExternalProgram entity, Model model) {
		model.addAttribute("adExternalProgram", entity);
		return "/adprogram/programForm";
	}

	@RequiresPermissions("adv:program:edit")
	@RequestMapping(value = "/save") 
	public String save(AdExternalProgram entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		try {
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/program/?repage";
		} catch (ServiceException e) {
			e.printStackTrace();
			addMessage(redirectAttributes,  "msg.save.fail");
			return "redirect:/adv/program/?repage";
		}
	}
	
	@RequiresPermissions("adv:program:edit")
	@RequestMapping(value = "/delete") 
	public String delete(AdExternalProgram entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		 
		try {
			thisService.delete(entity);
			addMessage(redirectAttributes, "msg.del.success");
			return "redirect:/adv/program/?repage";
		} catch (ServiceException e) {
			addMessage(redirectAttributes,  "msg.del.fail");
			return "redirect:/adv/program/?repage";
		}
	}
	
    @ResponseBody
    @RequiresPermissions("adv:program:edit")
    @RequestMapping(value = "checkProgramId")
    public String checktypeId(String oldProgramId, String programId) {
 
    	try{
        if (programId != null && programId.equals(oldProgramId)) {
            return "true";
        } else if (programId != null && thisService.findProgramByProgramId(programId.trim()) == null) {
            return "true";
        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
       return "false";
    }

}
