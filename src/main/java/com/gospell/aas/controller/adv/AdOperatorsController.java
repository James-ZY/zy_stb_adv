package com.gospell.aas.controller.adv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.beanvalidator.BeanValidators;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.excel.ExportExcel;
import com.gospell.aas.common.utils.excel.ImportExcel;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.DistrictCategoryModel;
import com.gospell.aas.dto.adv.SelectDistrictDTO;
import com.gospell.aas.entity.adv.AdOperators;
import com.gospell.aas.entity.adv.AdOperatorsDistrict;
import com.gospell.aas.service.adv.AdDistrictCategoryService;
import com.gospell.aas.service.adv.AdOperatorsService;
import com.gospell.aas.service.sys.SysLogService;

@Controller
@RequestMapping(value = "/adv/operators")
public class AdOperatorsController extends BaseController {

	@Autowired
	private AdOperatorsService thisService;
	@Autowired
	private AdDistrictCategoryService adDistrictCategoryService;
	@Autowired
	private SysLogService logService;

	@ModelAttribute
	public AdOperators get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new AdOperators();
		}
	}

	@RequiresPermissions("sys:operators:view")
	@RequestMapping(value = { "/list", "" })
	public String list(AdOperators entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdOperators> page = thisService.find(new Page<AdOperators>(request, response), entity);
		model.addAttribute("page", page);
		if(StringUtils.isNotBlank(entity.getUploadMessage())){
			model.addAttribute("message", entity.getUploadMessage());
			entity.setUploadMessage(null);
		}

		return "/network/operatorList";
	}

	@RequiresPermissions("sys:operators:view")
	@RequestMapping(value = "/form")
	public String form(AdOperators entity, Model model) {
		List<AdOperatorsDistrict> adDistrictCategorys = entity.getAdDistrictCategorys();
		List<DistrictCategoryModel> districtCategoryModels = Lists.newArrayList();
		SelectDistrictDTO dto = new SelectDistrictDTO();
		if(adDistrictCategorys != null &&adDistrictCategorys.size() > 0 ){
			Map<String,String> selMap = new HashMap<String, String>();
			String selArea = "";
			String firId = "";
			for (AdOperatorsDistrict adOperatorsDistrict : adDistrictCategorys) {
				firId = adOperatorsDistrict.getDistrict().getId().substring(0, 3);
				DistrictCategoryModel dcModel = new DistrictCategoryModel();
				dcModel.setCategoryId(adOperatorsDistrict.getDistrict().getId());
				dcModel.setCategoryName(adOperatorsDistrict.getDistrict().getCategoryName());
				if(StringUtils.isNotBlank(adOperatorsDistrict.getSelfDistrictId())){
					dcModel.setSelfCategoryId(adOperatorsDistrict.getSelfDistrictId());
					selMap.put(adOperatorsDistrict.getDistrict().getId(), adOperatorsDistrict.getSelfDistrictId());
				}else{
					dcModel.setSelfCategoryId("");	
					selMap.put(adOperatorsDistrict.getDistrict().getId(), "");
				}
				districtCategoryModels.add(dcModel);
			}
			selArea = firId + ":" + selMap.get(firId);
			dto.setAdDistrictCategorys(districtCategoryModels);
			model.addAttribute("selArea", selArea);
			model.addAttribute("selAllArea", JsonMapper.toJsonString(dto));
		}
		model.addAttribute("adOperators", entity);
		return "/network/operatorForm";
	}

	@RequiresPermissions("sys:operators:edit")
	@RequestMapping(value = "/save") 
	public String save(AdOperators entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("operators"), entity.getOperatorsId());
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
 
	
		try {
			if(StringUtils.isBlank(entity.getOperatorsId())){
				Integer operatorsId = thisService.findMaxAdOperatorId();
				if(operatorsId ==null){
					operatorsId = 1;
				}
				entity.setOperatorsId(String.valueOf(operatorsId));
			}
			thisService.save(entity);
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存或者修改电视运营商："+entity.getOperatorsId()+"成功！");
			addMessage(redirectAttributes, "msg.save.success");
			return "redirect:/adv/operators/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存或者修改电视运营商："+entity.getOperatorsId()+"失败！",e);
			addMessage(redirectAttributes,  "msg.save.fail");
			return "redirect:/adv/operators/?repage";
		}
	}
	
	@RequiresPermissions("sys:operators:edit")
	@RequestMapping(value = "/delete") 
	public String delete(AdOperators entity, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("operators"), entity.getOperatorsId());
		try {
			boolean b = thisService.isCanDeleteOperator(entity);
		
			if(!b){
				
				thisService.clear();
				addMessage(redirectAttributes, "operators.have.network");
				logService.save(request, logInfo, new Exception(getMessage("operators.have.network")));
			}else{
				thisService.delete(entity);
				addMessage(redirectAttributes, "msg.del.success");
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"删除电视运营商："+entity.getOperatorsId()+"成功！");
			}
			
			return "redirect:/adv/operators/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除电视运营商："+entity.getOperatorsId()+"失败！");
			addMessage(redirectAttributes,  "msg.del.fail");
			return "redirect:/adv/operators/?repage";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "delete_checked_operators", method = RequestMethod.POST)
	public String deleteCheckedOperators(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String[] ids = ((String) map.get("ids")).split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		int j=0;int k = 0;
		for (String string : ids) {
			AdOperators entity = thisService.get(string);
			String logInfo=logService.getLogInfo(entity.getId(), 1, getMessage("operators"), entity.getOperatorsId());
			try {
				boolean b = thisService.isCanDeleteOperator(entity);		
				if(!b){				
					thisService.clear();
					logService.save(request, logInfo, new Exception(getMessage("operators.have.network")));
					j++;
				}else{
					thisService.delete(entity);
					logService.save(request, logInfo, null);
					logger.info(UserUtils.getUser().getLoginName()+"删除电视运营商："+entity.getOperatorsId()+"成功！");
				}			
			} catch (Exception e) {
				logService.save(request, logInfo, e);
				logger.error(UserUtils.getUser().getLoginName()+"删除电视运营商："+entity.getOperatorsId()+"失败！");
				k++;
			}
		}
		if(k>0){
			result.put("state", false);
			result.put("msg", getMessage("msg.del.fail"));
		}else if(j>0){
			result.put("state", true);
			result.put("msg", getMessage("operators.have.network1"));
		}else{
			result.put("state", true);
			result.put("msg", getMessage("msg.del.success"));
		}
		return JsonMapper.toJsonString(result);
	}
	
    @ResponseBody
    @RequiresPermissions("sys:operators:edit")
    @RequestMapping(value = "checkOperatorsId")
    public String checkOperatorsId(String oldOperatorsId, String operatorsId) {
    	try{
        if (operatorsId != null && operatorsId.equals(oldOperatorsId)) {
            return "true";
        } else if (operatorsId != null && thisService.findByOperatorsId(operatorsId) == null) {
            return "true";
        }
    	}catch(Exception e){
    		logger.error(UserUtils.getUser().getLoginName()+"检查电视运营商Id："+operatorsId+"是否重复失败！");
    	}
        return "false";
    }
    /**
     * 导出电视运营商
     * @param entity
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("sys:operators:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(AdOperators entity, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(null, 3, getMessage("operators"), "");
		try {
			String name  =getMessage("operator.data.export") ;
			String fileName = name + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<AdOperators> page = thisService.find(new Page<AdOperators>(request,
					response,-1), entity);
			new ExportExcel(name, AdOperators.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"导出电视运营商的信息成功");
			return null;
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"导出电视运营商的信息失败",e.getMessage());
			addMessage(redirectAttributes, "export.fail");
		}
		return "redirect:/adv/operators/?repage";
	}
	
	@RequiresPermissions("sys:operators:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		 
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			if(file == null){
				failureMsg.append("<br/>" +getMessage("excel.is.null"));
				failureMsg.insert(0, getMessage("import.operator.fail"));
				 
				addMessage(redirectAttributes, failureMsg.toString());
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入电视运营商的信息失败",new Exception(failureMsg.toString()));
				return "redirect:/adv/operators/?repagee";
			}
		
			byte[] b1 = file.getBytes();
			 
			if(b1.length > filesize){
				long max = 1024*1024;
				long size = filesize/max;
				failureMsg.append("<br/>" +getMessage("importFormat",new Object[]{size}));
				failureMsg.insert(0, getMessage("import.operator.fail"));
				 
				addMessage(redirectAttributes, failureMsg.toString());
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入电视运营商的信息失败",new Exception(failureMsg.toString()));
				return "redirect:/adv/operators/?repagee";
			} 
			
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AdOperators> list = ei.getDataList(AdOperators.class);
			if(list.size() >excelNumber){

				failureMsg.append("<br/>" +getMessage("import.number",new Object[]{excelNumber})
					 );
		 
				failureMsg.insert(0, getMessage("import.operator.fail"));
				 
				addMessage(redirectAttributes, failureMsg.toString());
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入电视运营商的信息失败",new Exception(failureMsg.toString()));
				return "redirect:/adv/operators/?repagee";
			}
			
 
			//String commonFailStr = getMessage("import.data")+getMessage("middle")+getMessage("have");
			boolean b = thisService.checkImportAdOperatorsIsNotData(list);//验证excel表格不为null
			if(!b){
		 
				failureMsg.append("<br/>" +getMessage("excel.change.data"));
				 
		 		failureMsg.insert(0, getMessage("import.operator.fail"));
				 
				addMessage(redirectAttributes, failureMsg.toString());
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				return "redirect:/adv/operators/?repagee";
			}
			//验证每一条数据是否完整
			List<AdOperators> availableList = thisService.checkImportAdOperatorsIsNul(list);
			int error_size = thisService.compareListSize(list, availableList);
			if(error_size >0){
				failureMsg.append("<br/>" +getMessage("import.data.incomplete.info", new Object[]{error_size}));
				failureNum += error_size;
			}
			//验证数据长度是否正确
			int checkFieldLength = 0;
			if(availableList != null && availableList.size() >0){
				List<AdOperators> length_list = Lists.newArrayList();
				for (AdOperators adv : availableList) {
					try {
	 						BeanValidators.validateWithException(validator, adv); 
	 						length_list.add(adv);
					} catch (ConstraintViolationException ex) {
						checkFieldLength ++;
					 
					}  
				}
				if(checkFieldLength >0){
					failureMsg.append("<br/>"+getMessage("import.data.length.error", new Object[]{checkFieldLength}));
					
					failureNum+=checkFieldLength;
				}
				//验证手机，电话格式是否正确
				if(length_list != null && length_list.size() >0){
					List<AdOperators> fomartList = thisService.checkImportAdOperatorsDataFormat(length_list);
					int compare = thisService.compareListSize(length_list, fomartList);
					if(compare >0){
						failureMsg.append("<br/>" + getMessage("import.data.phone.mobie.number.error",new Object[]{compare}));
						failureNum += compare;
					}
					
					if(fomartList != null && fomartList.size() >0){
						List<AdOperators> distinctList = thisService.distinctAdvtiserId(fomartList);//验证导入的广告商ID自身是否重复
						int repeatCount = thisService.compareListSize(fomartList, distinctList);
						if(repeatCount >0){
							failureMsg.append("<br/>" + getMessage("import.data.repeat.adOperators.id",new Object[]{repeatCount}));
							failureNum+=repeatCount;
						}
						
						if (distinctList != null && distinctList.size() >0) {
							List<AdOperators> norepeatList = thisService.databaseAlreadyExist(distinctList);;//验证导入的广告商ID在数据库中是否重复
							int databaseRepeatCount = thisService.compareListSize(distinctList, norepeatList);
							if(databaseRepeatCount >0){
								failureMsg.append("<br/>" + getMessage("import.data.adOperators.id.exist.database",new Object[]{databaseRepeatCount}));
								failureNum+=databaseRepeatCount;
							}
							if(null != norepeatList && norepeatList.size() >0){
								thisService.saveList(norepeatList);
								successNum = norepeatList.size();
							}
						}
					}
				}
			}
			if (failureNum > 0 && successNum ==0) {
				 
				Object[] o ={list.size(),failureNum};
 				failureMsg.insert(0,  getMessage("import.operator.fail.info",o));
 				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
 				logger.error(UserUtils.getUser().getLoginName()+"导入电视运营商的信息失败",new Exception(failureMsg.toString()));
			}
			
			if (failureNum > 0 && successNum >0) {
				 
				Object[] o ={list.size(),successNum,failureNum};
				
 			failureMsg.insert(0,  getMessage("import.operator.fail.success.info",o));
 			logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
 			logger.error(UserUtils.getUser().getLoginName()+"导入电视运营商的信息失败",new Exception(failureMsg.toString()));
			}
			if (failureNum == 0 && successNum >0) {
				
				Object[] o ={list.size(),successNum};
				failureMsg.insert(0,  getMessage("import.operator.success.info",o));
				logService.save(request, failureMsg.toString(),null);
				logger.info(UserUtils.getUser().getLoginName()+"导入电视运营商的信息成功");
			}
			
			addMessage(redirectAttributes, failureMsg.toString());
		} catch (Exception e) {
			String logInfo=logService.getLogInfo(null, 4, getMessage("operators"), "");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"导入电视运营商的信息失败",e);
			addMessage(redirectAttributes, "import.fail");
		}
		return "redirect:/adv/operators/?repagee";
	}
	
	@RequiresPermissions("sys:operators:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = getMessage("adOperators.import.template")+".xlsx";
			List<AdOperators> list = Lists.newArrayList();
			list.add(new AdOperators());
			new ExportExcel(getMessage("adOperators.import.template")+getMessage("import.required"), AdOperators.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			logger.info(UserUtils.getUser().getLoginName()+"下载电视运营商的模板成功");
			return null;
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"下载电视运营商模板失败",e);
		 
			addMessage(redirectAttributes, getMessage("download.adOperators.template.fail"));
		}
		return "redirect:/adv/operators/?repagee";
	}

 
    
    

}
