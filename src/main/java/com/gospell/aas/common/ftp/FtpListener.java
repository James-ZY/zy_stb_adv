package com.gospell.aas.common.ftp;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FtpListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		 FutureTask<String> task = new FutureTask<>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				 start();
				 System.out.println("初始化ftp完成");
				 return "初始化ftp完成";
			}
			 	
		});

	 
	    
	    new Thread(task).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		 
	}
	
	 // 希望Tomcat启动结束后执行的方法
	  private void start() {
		  new MobFTPServer().startWork();
	  }

}
