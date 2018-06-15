package com.gospell.aas.webservice.netty.client;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
/**
 * 启动 netty client
 * @author Administrator
 *
 */
public class NettyClientRun  implements ApplicationListener<ContextRefreshedEvent>{

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
				 Client.getInstance();
			}
		});
		t.start();
	}
	

}
