package com.gospell.aas.controller.adv;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.gospell.aas.common.beanvalidator.BeanValidators;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.excel.ExportExcel;
import com.gospell.aas.common.utils.excel.ImportExcel;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.Advertiser;
import com.gospell.aas.service.adv.AdvertiserService;
import com.gospell.aas.service.sys.SystemService;

@Controller
@RequestMapping(value = "/adv/advertiser")
public class AdvertiserController extends BaseController {

	@Autowired
	private AdvertiserService thisService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public Advertiser get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return thisService.get(id);
		} else {
			return new Advertiser();
		}
	}

	@RequiresPermissions("sys:advertiser:view")
	@RequestMapping(value = { "/list", "" })
	public String list(Advertiser entity, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<Advertiser> page = thisService.find(new Page<Advertiser>(request,
				response), entity);
		if(StringUtils.isNotBlank(entity.getUploadMessage())){
			model.addAttribute("message", entity.getUploadMessage());
			entity.setUploadMessage(null);
		}
		model.addAttribute("page", page);
		return "/advertiser/advertiserList";
	}

	@RequiresPermissions("sys:advertiser:view")
	@RequestMapping(value = "/form")
	public String form(Advertiser entity, Model model) {
		model.addAttribute("advertiser", entity);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "/advertiser/advertiserForm";
	}

	@RequiresPermissions("sys:advertiser:edit")
	@RequestMapping(value = "/save")
	public String save(Advertiser entity, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entity)) {
			return form(entity, model);
		}
		String logInfo=logService.getLogInfo(entity.getId(), 0, getMessage("advertiser"), entity.getAdvertiserId());
		try {
			if(StringUtils.isBlank(entity.getAdvertiserId())){
				Integer advId = thisService.findMaxAdvertiserId();
				if(advId ==null){
					advId = 1;
				}
				entity.setAdvertiserId(String.valueOf(advId));
			}
			thisService.save(entity);
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存或者修改广告商："+entity.getAdvertiserId()+"成功！");
			return "redirect:/adv/advertiser/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存或者修改广告商："+entity.getAdvertiserId()+"失败！",e.getMessage());
			addMessage(redirectAttributes, "msg.save.fail");
			return "redirect:/adv/advertiser/?repage";
		}
	}

	@RequiresPermissions("sys:advertiser:edit")
	@RequestMapping(value = "/delete")
	public String delete(String id, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		Advertiser a = get(id);
		String logInfo=logService.getLogInfo(a.getId(), 1, getMessage("advertiser"), a.getAdvertiserId());
		try {
			int i = thisService.isCanDelete(id);
			if (i == 0) {
		 
				a.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
				thisService.delete(a);
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"删除广告商："+a.getAdvertiserId()+"成功！");
				addMessage(redirectAttributes, "msg.del.success");
			}else if(i==1){
				logService.save(request, logInfo, new Exception("del.advertiser.exist.user"));
				logger.error(UserUtils.getUser().getLoginName()+"删除广告商："+a.getAdvertiserId()+"失败！",getMessage("del.advertiser.exist.user"));
				addMessage(redirectAttributes, "del.advertiser.exist.user");
			}else {
				logService.save(request, logInfo, new Exception(getMessage("del.advertiser.exist.combo")));
				logger.error(UserUtils.getUser().getLoginName()+"删除广告商："+a.getAdvertiserId()+"失败！",getMessage("del.advertiser.exist.combo"));
				addMessage(redirectAttributes, "del.advertiser.exist.combo");
			}
			return "redirect:/adv/advertiser/?repage";
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"删除广告商："+a.getAdvertiserId()+"失败！",e.getMessage());
			addMessage(redirectAttributes, "msg.del.fail");
			return "redirect:/adv/advertiser/?repage";
		}
	}

	@ResponseBody
	@RequiresPermissions("sys:advertiser:edit")
	@RequestMapping(value = "checkAdvertiserId")
	public String checktypeId(String oldAdvertiserId, String advertiserId) {

		try {
			if (advertiserId != null && advertiserId.equals(oldAdvertiserId)) {
				return "true";
			} else if (advertiserId != null
					&& thisService.findAdvertiserById(advertiserId.trim()) == null) {
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	
	@RequiresPermissions("sys:advertiser:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Advertiser entity, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String logInfo=logService.getLogInfo(null, 3, getMessage("advertiser"), "");
		try {
			String name  =getMessage("advertiser.data.export") ;
			String fileName = name + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<Advertiser> page = thisService.find(new Page<Advertiser>(request,
					response,-1), entity);
			new ExportExcel(name, Advertiser.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"导出电视运营商的信息成功");
			return null;
		} catch (Exception e) {
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"导出电视运营商的信息失败",e.getMessage());
			addMessage(redirectAttributes, "export.fail");
		}
		return "redirect:/adv/advertiser/?repage";
	}
	
	@RequiresPermissions("sys:advertiser:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(@RequestParam("file")MultipartFile file,HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			if(file == null){
				failureMsg.append("<br/>" +getMessage("excel.is.null"));
				failureMsg.insert(0, getMessage("import.advertiser.fail"));
				 
				addMessage(redirectAttributes, failureMsg.toString());
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",new Exception(failureMsg.toString()));
				return "redirect:/adv/advertiser/?repage";
			}
		
			byte[] b1 = file.getBytes();
			 
			if(b1.length > filesize){
				failureMsg.append("<br/>" +getMessage("importFormat"));
				long max = 1024*1024;
				long size = filesize/max;
				failureMsg.append("<br/>" +getMessage("importFormat",new Object[]{size}));
				failureMsg.insert(0, getMessage("import.advertiser.fail"));
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",new Exception(failureMsg.toString()));
				addMessage(redirectAttributes, failureMsg.toString());
				return "redirect:/adv/advertiser/?repage";
			} 
			
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Advertiser> list = ei.getDataList(Advertiser.class);
			if(list.size() >excelNumber){

				failureMsg.append("<br/>" +getMessage("import.number",new Object[]{excelNumber})
					 );
		 
				failureMsg.insert(0, getMessage("import.advertiser.fail"));
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",new Exception(failureMsg.toString()));
				addMessage(redirectAttributes, failureMsg.toString());
				return "redirect:/adv/advertiser/?repage";
			}
			

			//String commonFailStr = getMessage("import.data")+getMessage("middle")+getMessage("have");
			boolean b = thisService.checkImportAdvertiserIsNotData(list);//验证excel表格不为null
			if(!b){
		 
				failureMsg.append("<br/>" +getMessage("excel.change.advertiser.data")
						 );
				failureMsg.insert(0, getMessage("import.advertiser.fail"));
				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",new Exception(failureMsg.toString()));
				addMessage(redirectAttributes, failureMsg.toString());
				return "redirect:/adv/advertiser/?repage";
			}
			//验证每一条数据是否完整
			List<Advertiser> availableList = thisService.checkImportAdvertiserIsNul(list);
			int error_size = thisService.compareListSize(list, availableList);
			if(error_size >0){
				failureMsg.append("<br/>" +getMessage("import.data.advertiser.incomplete.info",new Object[]{error_size}) );
				failureNum += error_size;
			}
			//验证数据长度是否正确
			int checkFieldLength = 0;
			if(availableList != null && availableList.size() >0){
				List<Advertiser> length_list = Lists.newArrayList();
				for (Advertiser adv : availableList) {
					try {
	 						BeanValidators.validateWithException(validator, adv); 
	 						length_list.add(adv);
					} catch (ConstraintViolationException ex) {
						checkFieldLength ++;
					 
					}  
				}
				if(checkFieldLength >0){
					failureMsg.append("<br/>"
							+ getMessage("import.data.length.error",new Object[]{checkFieldLength}));
					failureNum+=checkFieldLength;
				}
				//验证手机，电话格式是否正确
				if(length_list != null && length_list.size() >0){
					List<Advertiser> fomartList = thisService.checkImportAdvertiserDataFormat(length_list);
					int compare = thisService.compareListSize(length_list, fomartList);
					if(compare >0){
						failureMsg.append("<br/>"+ getMessage("import.advertiser.phone.mobie.type.error",new Object[]{compare}));
						failureNum += compare;
					}
					
					if(fomartList != null && fomartList.size() >0){
						List<Advertiser> distinctList = thisService.distinctAdvtiserId(fomartList);//验证导入的广告商ID自身是否重复
						int repeatCount = thisService.compareListSize(fomartList, distinctList);
						if(repeatCount >0){
							failureMsg.append("<br/>" +getMessage("import.data.repeat.advertiser.id",new Object[]{repeatCount}));
							failureNum+=repeatCount;
						}
						
						if (distinctList != null && distinctList.size() >0) {
							List<Advertiser> norepeatList = thisService.databaseAlreadyExist(distinctList);;//验证导入的广告商ID在数据库中是否重复
							int databaseRepeatCount = thisService.compareListSize(distinctList, norepeatList);
							if(databaseRepeatCount >0){
								failureMsg.append("<br/>" + getMessage("import.data.advertiser.id.exist.database",new Object[]{databaseRepeatCount}));
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
 				failureMsg.insert(0,  getMessage("import.advertiser.fail.info",o));
 				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",new Exception(failureMsg.toString()));
			}
			if (failureNum > 0 && successNum >0) { 
				Object[] o ={list.size(),successNum,failureNum};
 			    failureMsg.insert(0,  getMessage("import.advertiser.fail.success.info",o));
 				logService.save(request, failureMsg.toString(), new Exception(failureMsg.toString()));
				logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",new Exception(failureMsg.toString()));
			
			}
			if (failureNum == 0 && successNum >0) {
				Object[] o ={list.size(),successNum};
      		    failureMsg.insert(0,  getMessage("import.advertiser.success.info",o));
      			logService.save(request, failureMsg.toString(), null);
				logger.info(UserUtils.getUser().getLoginName()+"导入广告商的信息成功");
			}
		 
			addMessage(redirectAttributes, failureMsg.toString());
		} catch (Exception e) {
			String logInfo=logService.getLogInfo(null, 4, getMessage("advertiser"), "");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"导入广告商的信息失败",e);
			addMessage(redirectAttributes, "import.fail");
		}
		return "redirect:/adv/advertiser/?repage";
	}
	
	@RequiresPermissions("sys:advertiser:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
 
	
		try {
			String fileName = getMessage("advertiser.import.template")+".xlsx";
			List<Advertiser> list = Lists.newArrayList();
			list.add(new Advertiser());
			new ExportExcel(getMessage("advertiser.import.template")+getMessage("import.required"), Advertiser.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			 
			logger.info(UserUtils.getUser().getLoginName()+"下载电视运营商的模板成功");
			return null;
		} catch (Exception e) {
			logger.error(UserUtils.getUser().getLoginName()+"下载广告商的模板失败",e.getMessage());
			addMessage(redirectAttributes, getMessage("download.advertiser.template.fail"));
		}
		return "redirect:/adv/advertiser/?repage";
	}
	
	
 
 
}
