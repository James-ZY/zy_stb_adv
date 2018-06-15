package com.gospell.aas.common.ftp;
 
 

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class FtpService implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		  if(event.getApplicationContext().getParent() == null)//root application context 没有parent，他就是老大.
	        {  
	            
			  run();	
	    		 
	       
	        }  
	        
	       
	}
	
	public void run(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//需要执行的逻辑代码，当spr初始化哇初始化完成后就会执行该方法。  
				System.out.println("初始化开始。。。");
			     new MobFTPServer().startWork();
				 System.out.println("初始化ftp完成");
			}
		});
		t.start();
	}

 

}
