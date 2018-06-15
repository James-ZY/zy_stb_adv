//package com.gospell.aas.common.security;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
// 
//import org.apache.shiro.web.filter.authc.LogoutFilter;
//import org.springframework.stereotype.Service;
//
//import com.gospell.aas.common.utils.UserUtils;
//
// 
//@Service
//public class SystemLogoutFilter extends LogoutFilter{
//
//	  @Override
//	    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//		  	UserUtils.getUser(true);
//	        return super.preHandle(request, response);
//	    }
//}
