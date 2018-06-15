package com.gospell.aas.common.security.shiro;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authz.UnauthorizedException;

import com.gospell.aas.webservice.rest.RsResponse;

 

@Provider
public class ShiroExceptionMapper implements ExceptionMapper<UnauthorizedException> {
	@Override
	public Response toResponse(UnauthorizedException ex) {
		return RsResponse.buildTextResponse(Status.UNAUTHORIZED, ex.getMessage());
	}

}
