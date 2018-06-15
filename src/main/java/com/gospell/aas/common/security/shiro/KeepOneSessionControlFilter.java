package com.gospell.aas.common.security.shiro;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gospell.aas.common.utils.CacheUtils;
import com.gospell.aas.common.utils.Constant;


/**
 * 保存最新的用户在线，踢出上一个用户
 * 
 * @author caomei
 * 
 */

@Component("keepone")
public class KeepOneSessionControlFilter extends AccessControlFilter {
    
    protected Logger logger = LoggerFactory.getLogger(KeepOneSessionControlFilter.class);
            
    @Resource
    private SessionManager sessionManager;
    
    @Resource
    private CacheManager shiroCacheManager;

    private Cache<String, String> cache = null;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String userId = SessionUser.getUserId();
        if (StringUtils.isBlank(userId)) {// 没有登录
            return true;
        }
 
 
        // 当前session 的Id
        Serializable sessionId = SessionUser.getSession().getId();

        // 当前用户缓存中的sessionId
        if (cache == null) {
            this.cache = shiroCacheManager.getCache(Constant.keeponeCacheName);
        }

        String deleteSessionId = cache.get(userId);

        if (sessionId.toString().equalsIgnoreCase(deleteSessionId)) {
            return true;
        } else if (StringUtils.isBlank(deleteSessionId)) {
            cache.put(userId, sessionId.toString());
            return true;
        } else {
        	try {
        		cache.put(userId, sessionId.toString());
                Session deletetSession = sessionManager.getSession(new DefaultSessionKey(deleteSessionId));
                if (deletetSession == null) {
                    return true;
                }
                // 根据 需要删除的 sessionId,生成subject
                Subject deleteSubject = new Subject.Builder().sessionId(deleteSessionId).buildSubject();
                // 退出
                logger.info("KeepOneSessionControlFilter logout!!!");
                deleteSubject.logout();
                 
                CacheUtils.put(Constant.keeponeError, "您的账号已经在其他设备登录");
                // 在此可以自定义json格式的回复
                return true;
			} catch (UnknownSessionException e) {  // session 失效
				 
				//if(cache != null && cache.keys().contains(userId)){
					cache.remove(userId);
				//}
				
				return true;
			}
        }
    }

}
