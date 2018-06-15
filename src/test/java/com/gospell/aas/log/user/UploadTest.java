package com.gospell.aas.log.user;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

public class UploadTest {
	public static void main(String[] args) {
		
		String TARGET_URL = "http://localhost:8080/mserver/rs/cdcy/uploadFile";
		 Client client = ClientBuilder.newBuilder()
		            .register(MultiPartFeature.class).build();
		 
		 
		    WebTarget webTarget = client.target(TARGET_URL);
		    MultiPart multiPart = new MultiPart();

		    FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("pic",
		            new File("C:/Users/Administrator/Desktop/x.png"), MediaType.MULTIPART_FORM_DATA_TYPE);
		    multiPart.bodyPart(fileDataBodyPart);
		    Response response = webTarget.request(
            MediaType.MULTIPART_FORM_DATA).post(
            Entity.entity(multiPart, multiPart.getMediaType()));
		    
		    System.out.println(response.getStatus()+" =="+response.getStatusInfo()+" "+response);
		    System.out.println(response.getEntity());
	}
	
	
}
