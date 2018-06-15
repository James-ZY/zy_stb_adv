package com.gospell.aas.common.ftp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.impl.DefaultConnectionConfig;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.utils.ApplicationContextHelper;

public class MobFTPServer {
	private PropertiesUserManagerFactory userManagerFactory = null;
	private UserManager userManager = null;
	private BaseUser user = null;
	private ListenerFactory listenerFactory = null;
	private Logger logger = null;

	public MobFTPServer() {
		if (null == userManagerFactory)
			userManagerFactory = new PropertiesUserManagerFactory();

		if (null == userManager)
			userManager = userManagerFactory.createUserManager();

		if (null == user)
			user = new BaseUser();

		if (null == listenerFactory)
			listenerFactory = new ListenerFactory();

		if (null == logger)
			logger = LoggerFactory.getLogger(MobFTPServer.class);
	}

	public void startWork() {
		String username = Global.getConfig("ftp_username");
		String userpass = Global.getConfig("ftp_user_pass");
		String ftpHome = Global.getConfig("ftp_home");
		int ftpport = Integer.valueOf(Global.getConfig("ftp_port"));
		int max_logins = Integer.valueOf(Global.getConfig("ftp_max_logins"));
		Boolean anon_enabled = Boolean.valueOf(Global.getConfig("ftp_anon_enabled"));
		int max_anon_logins = Integer.valueOf(Global.getConfig("ftp_max_anon_logins"));
		String name = ApplicationContextHelper.getRootRealPath();
		if(name != null && name.contains("\\")){
			name = name.replace("\\", "/");
		}
		String[] s = name.split("/");
    	String[] real = new String[s.length];
    	for (int i = 0; i < s.length-1; i++) {
			real[i] = s[i];
		}
    	real[s.length-1] = "upload"; 
    	
    	String path = StringUtils.join(real, "/");
    	
		if (username != null && userpass != null && ftpHome != null && ftpport != 0) {
			try {
				user.setName(username);
				user.setPassword(userpass);
				user.setHomeDirectory(path);
				userManager.save(user);
				listenerFactory.setPort(ftpport);
				
				List<Authority> authorities = new ArrayList<Authority>();  
			    authorities.add(new WritePermission());  
			    user.setAuthorities(authorities);  
		  
				FtpServerFactory factory = new FtpServerFactory();
				userManager.save(user);
				factory.setUserManager(userManager);
				factory.addListener("default", listenerFactory.createListener());
				//设置ftp server的并发登录数，是否可以匿名登录等
				DefaultConnectionConfig c = new DefaultConnectionConfig(anon_enabled, 30000, max_logins, max_anon_logins, 3, 0);
				
				factory.setConnectionConfig(c);
				FtpServer server = factory.createServer();
				
				logger.info("MobFTPServer start ...");
 
				server.start();

			} catch (FtpException e) {
				e.printStackTrace();
				logger.info("MobFTPServer start Failed!");
			}
		}
	}

}
