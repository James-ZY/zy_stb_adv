package com.gospell.aas.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.entity.sys.Office;
import com.gospell.aas.entity.sys.Role;
import com.gospell.aas.entity.sys.User;
import com.gospell.aas.service.sys.OfficeService;
import com.gospell.aas.service.sys.SystemService;
import com.gospell.aas.service.sys.UserRoleService;

 

/**
 * 角色Controller
 * 
 * @author ThinkGem
 * @version 2013-5-15 update 2013-06-08
 */
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private UserRoleService userRoleService;

    // @Autowired
    // private CategoryService categoryService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute("role")
    public Role get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getRole(id);
        } else {
            return new Role();
        }
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = { "list", "" })
    public String list(Role role, Model model) {
   
    List<Role>  list = systemService.queryAllRole();
   
    	
      
        model.addAttribute("list", list);
    	 
        return "/sys/roleList";
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = "form")
    public String form(Role role, Model model, HttpServletRequest request) {
    	Office office  = null;
        if (role.getOffice() == null || role.getOffice().getId() == null) {
        	office = UserUtils.getUser().getCompany();
        	
        }else{
        	office = role.getOffice();
        }
        Office o = UserUtils.getOffice(office.getId());
        role.setOffice(o);
        model.addAttribute("role", role);
        model.addAttribute("menuList", systemService.findAllMenu());
        //model.addAttribute("auth.list", /*LocaleContextHolder.getLocale().equals(Locale.US) ? "Auth List": "权限列表"*/"auth.list");

        // model.addAttribute("categoryList", categoryService.findByUser(false, null));
        model.addAttribute("officeList", officeService.findAll());
        return "/sys/roleForm";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "save")
    public String save(Role role, Model model, HttpServletRequest request, String oldName, RedirectAttributes redirectAttributes) {
    	role.setDataScope(Role.DATA_SCOPE_COMPANY_AND_CHILD);
        if (!beanValidator(model, role)) {
            return form(role, model, request);
        }
        if (!"true".equals(checkName(oldName, role.getName()))) {
//            addMessage(model, "保存角色'" + role.getName() + "'失败, 角色名已存在");
            addMessage(model, "msg.save.fail");
            return form(role, model, request);
        }
        String logInfo=logService.getLogInfo(role.getId(),0, getMessage("role.name"), role.getName());
        try {
			systemService.saveRole(role);
		    addMessage(redirectAttributes, "msg.save.success");
		    logService.save(request, logInfo, null);
			logger.info(UserUtils.getUser().getLoginName()+"保存角色："+role.getName()+"成功！");
		} catch (Exception e) {    
			addMessage(redirectAttributes, "msg.save.fail");
			logService.save(request, logInfo, e);
			logger.error(UserUtils.getUser().getLoginName()+"保存角色："+role.getName()+"失败！",e);
		}
//        addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
   
        return "redirect:/sys/role/?repage";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "delete")
    public String delete(@RequestParam String id, HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	Role role = get(id);
        String logInfo=logService.getLogInfo(role.getId(),1, getMessage("role.name"), role.getName());
        if (Role.isAdmin(id)) {
//            addMessage(redirectAttributes, "删除角色失败, 不允许内置角色或编号空");
        		addMessage(redirectAttributes, "role.msg.del.admin");
        		logService.save(request, logInfo, new Exception("role.msg.del.admin"));
    			logger.error(UserUtils.getUser().getLoginName()+"删除角色："+role.getName()+"失败！",new Exception("role.msg.del.admin"));
             }else if (UserUtils.getUser().getRoleIdList().contains(id)){
//             addMessage(redirectAttributes, "删除角色失败, 不能删除当前用户所在角色");
            	addMessage(redirectAttributes, "role.msg.del.own");
            	logService.save(request, logInfo, new Exception("role.msg.del.own"));
    			logger.error(UserUtils.getUser().getLoginName()+"删除角色："+role.getName()+"失败！",new Exception("role.msg.del.own"));
        } else {
            try {
				systemService.deleteRole(id);
				logService.save(request, logInfo,null);
    			logger.error(UserUtils.getUser().getLoginName()+"删除角色："+role.getName()+"成功！");
	            addMessage(redirectAttributes, "role.delete.success");
			} catch (Exception e) {
				logService.save(request, logInfo, e);
    			logger.error(UserUtils.getUser().getLoginName()+"删除角色："+role.getName()+"失败！",e);
	            addMessage(redirectAttributes, "role.delete.fail");
				 
			}

        }
        return "redirect:/sys/role/?repage";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assign")
    public String assign(Role role, Model model) {
    	Office off = role.getOffice();
    	if(off != null){
    		Office o = UserUtils.getOffice(off.getId());
    		role.setOffice(o);
    	}
        List<User> users = role.getUserList();
        if(users!= null && users.size()>0){
        	 for (int i = 0; i < users.size(); i++) {
     			Office office = users.get(i).getCompany();
     			Office o = UserUtils.getOffice(office.getId());
     			users.get(i).setCompany(o);
     		}
        }
       
        model.addAttribute("users", users);
        return "/sys/roleAssign";
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = "usertorole")
    public String selectUserToRole(Role role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("selectIds", role.getUserIds());
        model.addAttribute("officeList", officeService.findAll());
        return "/sys/selectUserToRole";
    }

    @RequiresPermissions("sys:role:view")
    @ResponseBody
    @RequestMapping(value = "users")
    public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Office office = officeService.get(officeId);
        List<User> userList = office.getUserList();
        for (User user : userList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", user.getId());
            map.put("pId", 0);
            map.put("name", user.getName());
            if(userRoleService.getUserRoleNumByUserId(user.getId())==0){
            	mapList.add(map);
            }
        }
        return mapList;
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "outrole")
    public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
    	
        Role role = systemService.getRole(roleId);
        User user = systemService.getUser(userId);
        try{
        if (user.equals(UserUtils.getUser())) {
//            addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
            addMessage(redirectAttributes, "role.msg.del.own");
        } else {
            Boolean flag = systemService.outUserInRole(role, userId);
            if (!flag) {
//                addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
                addMessage(redirectAttributes, "role.msg.del.fail");
            } else {
//            	addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
            	addMessage(redirectAttributes, "role.msg.del.success");
            }
        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "redirect:/sys/role/assign?id=" + role.getId();
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assignrole")
    public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
        StringBuilder msg = new StringBuilder();
        int newNum = 0;
        for (int i = 0; i < idsArr.length; i++) {
            User user = systemService.assignUserToRole(role, idsArr[i]);
            if (null != user) {
           
                msg.append(getMessage("add.user.to.role",new Object[]{user.getName(),role.getName()}));
                newNum++;
            }
        }
//        addMessage(redirectAttributes, "已成功分配 " + newNum + " 个用户" + msg);
        addMessage(redirectAttributes, "role.msg.assign.user.success");
        return "redirect:/sys/role/assign?id=" + role.getId();
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && systemService.findRoleByName(name) == null) {
            return "true";
        }
        return "false";
    }

}
