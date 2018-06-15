package com.gospell.aas.webservice.rest;

import org.springframework.http.HttpStatus;

/**
 * 专用于Restful Service的异常.
 * 
 * @author free lance
 */
public class RestException extends RuntimeException {

	/**
     * 
     */
    private static final long serialVersionUID = -6631410042123950961L;
    
    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	public RestException() {
	}

	public RestException(HttpStatus status) {
		this.status = status;
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
