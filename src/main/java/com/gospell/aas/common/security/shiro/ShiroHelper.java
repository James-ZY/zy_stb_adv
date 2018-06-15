package com.gospell.aas.common.security.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;

/**
 * 
 * <p> Title: 绑定SecurityManager</p>
 * 
 * <p> Description: 多线程中访问shiro报错：org.apache.shiro.UnavailableSecurityManagerException: 
 * No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.ThreadContext 
 * or as a vm static singleton. This is an invalid application configuration.</p>
 * 
 * <p> Copyright: Copyright (c) 2015 by Free Lancer </p>
 * 
 * <p> Company: Free Lance </p>
 * 
 * @author:  L.J (free lance)
 * @Email:   free.lance@Gmail.com
 * @version: 1.0
 * @date:    2015年12月14日  下午4:38:08
 *
 */
public class ShiroHelper {

    private static ThreadState threadState;

    private ShiroHelper() {
    }

    /**
     * 綁定Subject到當前線程.
     */
    public static void bindSubject(Subject subject) {
        clearSubject();
        threadState = new SubjectThreadState(subject);
        threadState.bind();
    }

    /**
     * 清除當前線程中的Subject.
     */
    public static void clearSubject() {
        if (threadState != null) {
            threadState.clear();
            threadState = null;
        }
    }
}
