package com.gospell.aas.controller.adv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.AdPosition;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.service.ServiceException;
import com.gospell.aas.service.adv.AdPositionService;
import com.gospell.aas.service.adv.AdTypeService;


@Controller
@RequestMapping(value = "/adv/position")
public class AdPositionController extends BaseController {

	@Autowired
	private AdPositionService thisService;
	@Autowired
	private AdTypeService typeService;
	 

	@ModelAttribute
	public AdPosition get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdPosition();
		}
	}

	@RequiresPermissions("adv:position:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdPosition entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdPosition> page = thisService.find(new Page<AdPosition>(request, response), entity);
		model.addAttribute("page", page);
		return "/adposition/positionList";
	}

	@RequiresPermissions("adv:position:view")
	@RequestMapping(value = "/form")
	public String form(AdPosition entity, Model model) {
		model.addAttribute("adPosition", entity);
		return "/adposition/positionForm";
	}
 

	@RequiresPermissions("adv:position:edit")
	@RequestMapping(value = "/save") 
	public String save(AdPosition entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("position.id"), entity.getPointId());
	 
		try {
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存或者修改广告坐标ID："+entity.getPointId()+"成功");
			return "redirect:/adv/position/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存或者修改广告坐标ID："+entity.getPointId()+"失败");
			addMessage(redirectAttributes,  "msg.save.fail");
			return "redirect:/adv/position/?repage";
		}
	}
	
	@RequiresPermissions("adv:position:edit")
	@RequestMapping(value = "/delete") 
	public String delete(AdPosition entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("position.id"), entity.getPointId());
		try {
			thisService.delete(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"删除广告坐标ID："+entity.getPointId()+"成功");
			addMessage(redirectAttributes, "msg.del.success");
			return "redirect:/adv/position/?repage";
		} catch (ServiceException e) {
			addMessage(redirectAttributes,  "msg.del.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告坐标ID："+entity.getPointId()+"失败");
			return "redirect:/adv/position/?repage";
		}
	}
    @ResponseBody
    @RequiresPermissions("adv:position:edit")
    @RequestMapping(value = "checkpointId")
    public String checkPointId(String oldPointId, String pointId) {
    	try{
        if (pointId != null && pointId.equals(oldPointId)) {
            return "true";
        } else if (pointId != null && thisService.findAdPositionById(pointId.trim()) == null) {
            return "true";
        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "false";
    }
    
    @ResponseBody
	@RequestMapping(value = "find_type_move",method=RequestMethod.POST)
	public String findChannelType(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) {
    	Boolean b = false;
    	try{
    	String id = (String)map.get("id");
    	 AdType type = typeService.findById(id);
    	 if(null != type){
    		 if(type.getIsMove() != null && AdType.TYPE_CAN_MOVE == type.getIsMove()){
    			 b = true;
    		 }
    	 }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	 return JsonMapper.toJsonString(String.valueOf(b));
    }
 
}
