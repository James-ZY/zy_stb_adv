package com.gospell.aas.log.user;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gospell.aas.common.web.Servlets;
import com.gospell.aas.dto.user.UserDTO;

class UserClient {

	public static void main(String[] args) throws Exception {
		Client client = ClientBuilder.newClient();
		WebTarget resourceBase = client
				.target("http://localhost:8080/mserver/rs/user");
		// WebTarget getById = resourceBase.path("/getById");
		// WebTarget addObj = resourceBase.path("/addObj");

		String authentication = Servlets
				.encodeHttpBasic("superadmin", "123456");
		// ========save==Begin====================================================
		// WebTarget save = resourceBase.path("/save");
		// UserDTO user = new UserDTO();
		//
		// List<RoleDTO> list = new ArrayList<>();
		// RoleDTO r1 = new RoleDTO();
		// r1.setName("212");
		// list.add(r1);
		//
		// RoleDTO r2 = new RoleDTO();
		// r2.setName("313");
		// list.add(r2);
		//
		// user.setRoleList(list);
		// user.setCompanyId("2");
		// user.setOfficeId("2");
		// user.setLoginName("test");
		// user.setPassword("123456");
		// user.setName("test");
		//
		// save(save, user, authentication);
		// ========save==END====================================================

		// ========delete==Begin====================================================
		// WebTarget deleteId = resourceBase.path("/delete");
		// deleteId(deleteId, "8",authentication);
		// ========delete==END====================================================

		// ========updateAndGetById==Begin====================================================
		// WebTarget save = resourceBase.path("/save");
		// WebTarget getById = resourceBase.path("/getById");
		// getById(getById, 8, authentication);
		// save(save, user, authentication);
		// ========updateAndGetById==END====================================================

		// ========getAlls==Begin====================================================
		WebTarget getAlls = resourceBase.path("/getAlls");

		getAlls(getAlls, "5","2", authentication);

		// ========getAlls==END====================================================

		// ========checkByLoginName===Begin=============================================
//		 WebTarget checkByLoginName = resourceBase.path("/checkByLoginName");
//		 checkByLoginName(checkByLoginName, "admin", authentication);
		
		// ========checkByLoginName===END=============================================
		// ========================================================
		// office1.setId("2");
		// dto1.setOffice(office1);
		// dto1.setCompanyId("2");
		// dto1.setEmail("22");

	}

	public static void save(WebTarget target, UserDTO id, String authentication)
			throws Exception {
		Builder builder = target.request(MediaType.APPLICATION_JSON);
		Builder header = builder.header("", "");
		Response response = header.post(Entity.entity(id,
				MediaType.APPLICATION_JSON));
		System.out.println("response.getDate():" + response.getDate());
		System.out.println("response.getHeaders():" + response.getHeaders());
		System.out.println("response.getCookies():" + response.getCookies());
		System.out.println(response);

		// .post(Entity.entity(log, MediaType.APPLICATION_JSON));
		// logger.info("addLogObj response code=" + response.getStatus());
	}

	public static void getById(WebTarget target, Integer id,
			String authentication) throws Exception {
		Response response = target.queryParam("id", id)
				.request(MediaType.APPLICATION_JSON)
				.header(Servlets.AUTHENTICATION_HEADER, authentication).get();
		System.out.println(response);
	}

	public static void deleteId(WebTarget target, String id,
			String authentication) throws Exception {
		Response response = target.queryParam("id", id)
				.request(MediaType.APPLICATION_JSON)
				.header(Servlets.AUTHENTICATION_HEADER, authentication).get();
		System.out.println(response);
	}
	public static void getAlls(WebTarget target, String pageNo,String id,
			String authentication) throws Exception {
		Response response = target.queryParam("id", id).queryParam("pageNo", pageNo)
				.request(MediaType.APPLICATION_JSON)
				.header(Servlets.AUTHENTICATION_HEADER, authentication).get();
		System.out.println(response);
	}
	
	public static void checkByLoginName(WebTarget target, String id,
			String authentication) throws Exception {
		Response response = target.queryParam("loginName", id)
				.request(MediaType.APPLICATION_JSON)
				.header(Servlets.AUTHENTICATION_HEADER, authentication).get();
		System.out.println(response);
	}


}
