/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.DateUtils;
import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.adv.AdvertiserDTO;
import com.gospell.aas.common.utils.adv.AdvertiserUtils;
import com.gospell.aas.common.utils.adv.RoleDTO;
import com.gospell.aas.common.utils.adv.UserAdvDTO;
import com.gospell.aas.common.utils.excel.ExportExcel;
import com.gospell.aas.common.utils.excel.ImportExcel;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.adv.Advertiser;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.Role;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.sys.OfficeService;
import com.gospell.aas.service.sys.SystemService;

/**
 * 用户Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "list", "" })
	public String list(User user, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request,
				response), user);
		model.addAttribute("page", page);
		model.addAttribute("currentUser", UserUtils.getUser());
		return "/sys/userList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			Office company = UserUtils.getOffice(UserUtils.getUser().getCompany().getId());
			user.setCompany(company);
		}else{//国际化需要转换，把office表格里面数据在字典表里面查看
			Office company = UserUtils.getOffice(user.getCompany().getId());
			user.setCompany(company);
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			Office company = UserUtils.getOffice(UserUtils.getUser().getCompany().getId());
			user.setOffice(company);
		}else{
			Office company = UserUtils.getOffice(user.getCompany().getId());
			user.setOffice(company);
		}

		// 判断显示的用户是否在授权范围内
		String companyId = user.getCompany().getId();
		User currentUser = UserUtils.getUser();
		if (!currentUser.isAdmin()) {
			String dataScope = systemService.getDataScope(currentUser);
			// System.out.println(dataScope);
			if (dataScope.indexOf("office.id=") != -1) {
				String AuthorizedOfficeId = dataScope.substring(
						dataScope.indexOf("office.id=") + 10,
						dataScope.indexOf(" or"));
				if (!AuthorizedOfficeId.equalsIgnoreCase(companyId)) {
					Office parentUser = user.getCompany().getParent();
					if (!AuthorizedOfficeId
							.equalsIgnoreCase(parentUser.getId())) {
						if (!parentUser.getParentIds().contains(
								AuthorizedOfficeId)) { // add
														// by
														// pengr
														// 解决省级对区县的权限判断（垮2级的权限判断）
							return "error/403";
						}

					}
				}
			}
		}

		model.addAttribute("user", user);
 
		model.addAttribute("currentUserId", UserUtils.getUser().getId());
		model.addAttribute("allRoles", systemService.findAllRoleBySelectOffice(user.getCompany().getId()));
		return "/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, String oldLoginName, String newPassword,
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("company.id"))); // 部门和机构相同
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}
		if (!beanValidator(model, user)) {
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(oldLoginName, user.getLoginName()))) {
			addMessage(model, "msg.save.fail");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
	      String logInfo=logService.getLogInfo(user.getId(),0, getMessage("user.information"), user.getLoginName());
	 
			systemService.saveUser(user);
			// 清除当前用户缓存
			if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
				UserUtils.getCacheMap().clear();
			}
			addMessage(redirectAttributes, "msg.save.success");
			logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存用户信息："+user.getLoginName()+"成功！");
	

		return "redirect:/sys/user/?repage";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		User user = get(id);
		  String logInfo=logService.getLogInfo(user.getId(),1, getMessage("user.information"), user.getLoginName());
		if (UserUtils.getUser().getId().equals(id)) {
			addMessage(redirectAttributes, "del.cuser.fail");
			logService.save(request, logInfo, new Exception(getMessage("del.cuser.fail")));
			logger.error(UserUtils.getUser().getLoginName()+"删除用户信息："+user.getLoginName()+"失败！",new Exception(getMessage("del.cuser.fail")));
		} else if (User.isAdmin(id)) {
			addMessage(redirectAttributes, "del.admin.fail");
			logService.save(request, logInfo, new Exception(getMessage("del.admin.fail")));
			logger.error(UserUtils.getUser().getLoginName()+"删除用户信息："+user.getLoginName()+"失败！",new Exception(getMessage("del.admin.fail")));
		} else {
		
			systemService.deleteUser(id);
			addMessage(redirectAttributes, "msg.del.success");
			logService.save(request, logInfo, null);
			logger.error(UserUtils.getUser().getLoginName()+"删除用户："+user.getLoginName()+"成功");
		}
		return "redirect:/sys/user/?repage";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<User> page = systemService.findUser(new Page<User>(request,
					response, -1), user);
			new ExportExcel("用户数据", User.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "export.fail");
		}
		return "redirect:/sys/user/?repage";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list) {
				try {
					if ("true".equals(checkLoginName("", user.getLoginName()))) {
						user.setPassword(SystemService
								.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					} else {
						failureMsg.append("<br/>登录名 " + user.getLoginName()
								+ " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName()
							+ " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName()
							+ " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "import.success");
		} catch (Exception e) {
			addMessage(redirectAttributes, "import.fail");
		}
		return "redirect:/sys/user/?repage";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "import.fail");
		}
		return "redirect:/sys/user/?repage";
	}

	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {

		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null
				&& UserUtils.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	@RequiresUser
	@RequestMapping(value = "info")
	public String info(User user, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			currentUser = UserUtils.getUser(true);
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			systemService.saveUser(currentUser);
			model.addAttribute("message", "msg.save.success");
		}
		model.addAttribute("user", currentUser);
		return "/sys/userInfo";
	}

	@RequiresUser
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, HttpServletRequest request,String newPassword, Model model) {
		User user = UserUtils.getUser();
		String logInfo=getMessage("modifyPwd.log.info",new Object[]{getMessage("success")});
		if (StringUtils.isNotBlank(oldPassword)
				&& StringUtils.isNotBlank(newPassword)) {
			if (SystemService.validatePassword(oldPassword, user.getPassword())) {
				systemService.updatePasswordById(user.getId(),
						user.getLoginName(), newPassword);
				model.addAttribute("message", "update.pwd.success");
				logService.save(request, logInfo, null);
				logger.info(UserUtils.getUser().getLoginName()+"修改自身密码成功");
			} else {
				model.addAttribute("message", "update.pwd.fail");
				logService.save(request, logInfo, new Exception(getMessage("update.pwd.fail")));
				logger.error(UserUtils.getUser().getLoginName()+"修改自身密码失败");
			}
		}
		model.addAttribute("user", user);
		return "/sys/userModifyPwd";
	}

//	@ResponseBody
//	@RequiresPermissions("sys:user:edit")
//	@RequestMapping(value = "getRoles")
//	public List<UserRoleDTO> findChannelType(
//			@RequestBody Map<String, Object> map, HttpServletRequest request,
//			HttpServletResponse response) {
//		List<UserRoleDTO> dtoList = Lists.newArrayList();
//		String officeId = (String) map.get("officeId");
//		List<Role> list;
//		try {
//			list = systemService.getRoleByOfficeId(officeId);
//			if (null != list && list.size() > 0) {
//				for (int i = 0; i < list.size(); i++) {
//					UserRoleDTO d = new UserRoleDTO();
//					d.setId(list.get(i).getId());
//					d.setName(list.get(i).getName());
//					dtoList.add(d);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return dtoList;
//
//	}

	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "getUserType")
	public UserAdvDTO findUserType(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
try{

		String officeId = (String) map.get("officeId");
		Office office = officeService.get(officeId);
		UserAdvDTO dto = new UserAdvDTO();
		  dto.setIsSetAdmin(false);
		  dto.setUserIsAdv(false);
		if (office != null) {
			String type = office.getGrade();
			if (StringUtils.isNotBlank(type)) {
				if(office.getId().equals("1")){
					type = Office.GRADE_OPERATOR;
				}
				dto.setTypeId(type);
				dto.setTypeName(DictUtils.getDictLabel(type, "sys_office_grade", null));
				if (type.equals(Office.GRADE_ADVERTISER)) {
					User user = UserUtils.getUser();
					List<Advertiser> list = Lists.newArrayList();
					if(null == user.getAdvertiser()){
						  list = AdvertiserUtils.getAdvertiserList();
						 
						  dto.setIsSetAdmin(true);
					}else{
						list.add(user.getAdvertiser());
						dto.setUserIsAdv(true);
					 
					}
				 
					if (null != list && list.size() > 0) {

						List<AdvertiserDTO> dtoList = Lists.newArrayList();
						for (int i = 0; i < list.size(); i++) {
							Advertiser a = list.get(i);
							AdvertiserDTO d = new AdvertiserDTO();
							d.setId(a.getId());
							d.setName(a.getName());
							dtoList.add(d);
						}
						dto.setAdvertiserList(dtoList);
					}

				}

			}
			List<Role> roleList = systemService.findAllRoleByOffice(officeId);
			if(roleList != null && roleList.size() >0){
				List<RoleDTO> roleDtoList = Lists.newArrayList();
				for (int i = 0; i < roleList.size(); i++) {
					RoleDTO d = new RoleDTO();
					d.setRoleId(roleList.get(i).getId());
					d.setRoleName(roleList.get(i).getName());
					roleDtoList.add(d);
				}
				dto.setRoleList(roleDtoList);
			}
		}
		return dto;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	// @InitBinder
	// public void initBinder(WebDataBinder b) {
	// b.registerCustomEditor(List.class, "roleList", new
	// PropertyEditorSupport(){
	// @Autowired
	// private SystemService systemService;
	// @Override
	// public void setAsText(String text) throws IllegalArgumentException {
	// String[] ids = StringUtils.split(text, ",");
	// List<Role> roles = new ArrayList<Role>();
	// for (String id : ids) {
	// Role role = systemService.getRole(Long.valueOf(id));
	// roles.add(role);
	// }
	// setValue(roles);
	// }
	// @Override
	// public String getAsText() {
	// return Collections3.extractToString((List) getValue(), "id", ",");
	// }
	// });
	// }
}
