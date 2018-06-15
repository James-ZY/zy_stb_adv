package com.gospell.aas.log.user;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

 


public class LoginClient {
	
   public static void main(String[] args) {
        try {
        	   Client client = ClientBuilder.newClient();
        	 WebTarget target = client.target("http://infosoul.oicp.net:8081/advs/rs/network/add");
        	 //addLogObj(target, JsonMapper.toJsonString("中华人民共和国"), "");
        	getAdv();
        	//test();
        	 //getCombo();
        }
        catch (Exception e) {
            throw new WebApplicationException();
        }
    }
   
  
   
   public static void getAdv(){
	   Client client = ClientBuilder.newClient();
	   WebTarget target = client.target("http://infosoul.oicp.net:8081/advs/rs/advelement/getAdvByComId1");
	   Response response = target
			   .queryParam("clientId","7c4ea714259a4b9f824ae6ac57e04235")
			   .request(MediaType.APPLICATION_JSON).get();
	   System.out.println("-----------response响应信息-------------" + response);
	   System.out.println("-----------客户端返回信息----------------" + response.readEntity(String.class));
	   response.close();
 
  }
   
   public static void getCombo(){
	   Client client = ClientBuilder.newClient();
	   WebTarget target = client.target("http://infosoul.oicp.net:8081/advs/rs/adcombo/getCombo1");
	   Response response = target
			   .queryParam("clientId","7c4ea714259a4b9f824ae6ac57e04235")
			   .request(MediaType.APPLICATION_JSON).get();
	   System.out.println("-----------response响应信息-------------" + response);
	   System.out.println("-----------客户端返回信息----------------" + response.readEntity(String.class));
	   response.close();
   }
   
   public static void addLogObj(WebTarget target, Object log, String authentication) throws Exception {
		Response response = target.request(MediaType.APPLICATION_JSON)
		        .post(Entity.entity(log, MediaType.APPLICATION_JSON));
		 
	}
  
	
}
