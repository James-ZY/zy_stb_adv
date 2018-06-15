package com.gospell.aas.common.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.gospell.aas.common.security.SystemAuthorizingRealm;

 

/**
 * 
 * <p> Title: Shiro User</p>
 * 
 * <p> Description: 当前登录用户信息,可以在bean中调用获取当前登录用户信息,例如 SessionUser.getUserId()获取当前登录人的userId</p>
 * 
 * <p> Copyright: Copyright (c) 2015 by Free Lancer </p>
 * 
 * <p> Company: Free Lance </p>
 * 
 * @author: L.J (free lance)
 * @Email: free.lance@Gmail.com
 * @version: 1.0
 * @date: 2015年12月10日 上午11:06:32
 * 
 */
public class SessionUser {

    public SessionUser() {
    }

    public static SystemAuthorizingRealm.Principal getShiroUser() {
        Subject user = SecurityUtils.getSubject();
        if (user == null) {
            return null;
        }

        SystemAuthorizingRealm.Principal shiroUser = (SystemAuthorizingRealm.Principal) user.getPrincipal();
        if (shiroUser == null) {
            return null;
        }
        return shiroUser;
    }

    public static Session getSession() {
        Subject user = SecurityUtils.getSubject();
        if (user == null) {
            return null;
        }
        Session session = user.getSession();
        return session;
    }

    public static String getUserId() {
        SystemAuthorizingRealm.Principal shiroUser = getShiroUser();
        if (shiroUser == null) {
            return null;
        }
        return shiroUser.getId();
    }

    public static String getUserName() {
        SystemAuthorizingRealm.Principal shiroUser = getShiroUser();
        if (shiroUser == null) {
            return null;
        }
        return shiroUser.getName();
    }

    public static String getLoginName() {
        SystemAuthorizingRealm.Principal shiroUser = getShiroUser();
        if (shiroUser == null) {
            return null;
        }
        return shiroUser.getLoginName();

    }

}
