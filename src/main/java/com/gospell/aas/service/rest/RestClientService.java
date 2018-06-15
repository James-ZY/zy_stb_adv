package com.gospell.aas.service.rest;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gospell.aas.repository.rest.RestClientDao;
import com.gospell.aas.vo.rest.RestClient;

/**
 * 
 * <p> Title: </p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by IS </p>
 * 
 * <p> Company: IS </p>
 * 
 * @author:  free lance
 * @Email:   free.lance@Gmail.com
 * @version: 2.0
 * @date:    2014-12-4  下午4:38:36
 *
 */
@Service
public class RestClientService {

    @Autowired
    private RestClientDao restClientDao;

    public Response request(String httpUrl, String httpMethod, RestClient request) {
    	 RestClientDao restClientDao=new  RestClientDao();
        return restClientDao.request(httpMethod, request, httpUrl);
    }
    
    /**
     * 重载request方法
     * @param httpMethod  请求方法
     * @param request	  session相关的信息
     * @param reuestData  需要传入的写入的json字符串
     * @return
     */
    public Response request(String httpUrl, String httpMethod, RestClient request,String reuestData) {
    	return restClientDao.request(httpMethod, request,reuestData, httpUrl);
    }
}
