package com.gospell.aas.webservice.netty.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
/**
 * 启动 netty client
 * @author Administrator
 *
 */
public class NettyServerRun  implements ApplicationListener<ContextRefreshedEvent>{

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
				  startNetty(); 
			}
		});
		t.start();
	}
	private void startNetty(){
		 String s=System.getenv("netty_home");
		 
		 String os = System.getProperty("os.name").toLowerCase();
		 String commcand = "";
		 if(os.startsWith("win")){
				commcand = "java -jar "+s+"\\gospell-netty-1.0.jar &";

		 }else{
				commcand = "java -jar "+s+"/gospell-netty-1.0.jar &";
 
		 }
			System.out.println(commcand);
			 try {
				 Runtime runtime = Runtime.getRuntime();
				 //执行命令    
				 Process process = runtime.exec(commcand);
	           //取得命令结果的输出流    
	            InputStream fis=process.getInputStream();    
	           //用一个读输出流类去读    
	            InputStreamReader isr=new InputStreamReader(fis);    
	           //用缓冲器读行    
	            BufferedReader br=new BufferedReader(isr);    
	            String line=null;    
	           //直到读完为止    
	           while((line=br.readLine())!=null)    
	            {    
	                System.out.println(line);    
	            }   
	           fis.close();
	           isr.close();
	           br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
