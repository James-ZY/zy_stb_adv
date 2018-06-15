package com.gospell.aas.repository.rest;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.mapper.JsonMapper;
import com.gospell.aas.common.utils.Constant;
import com.gospell.aas.vo.rest.RequestParam;
import com.gospell.aas.vo.rest.RestClient;

/**
 * 
 * <p> Title: </p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by IS </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author: free lance
 * @Email: free.lance@Gmail.com
 * @version: 2.0
 * @date: 2014-12-4 下午5:01:32
 * 
 */
@Repository
public class RestClientDao {
    private static Logger logger = LoggerFactory.getLogger(RestClientDao.class);

    private String requestUrl;

    @PostConstruct
    public void init() {
        // 使用公共方法读取项目属性值
        requestUrl = Global.getConfig("httpServer.url").trim();

        if (requestUrl == null || requestUrl.trim().equals("")) {
            logger.warn("未配置参数httpServer.url");
            // throw new NullPointerException("未配置参数jsonrpc.url");
        }
    }

    public Response request(String httpMethod, RestClient request, String httpUrl) {
        try {
            this.requestUrl = httpUrl;
            // 调用server接口名字
            String method = request.getMethod();
            // 调用Headers
            Set<RequestParam> headParams = request.getHeadParams();
            // 调用参数集合
            Set<RequestParam> queryParams = request.getQueryParams();
            // 调用参数体
            Object requestDatas = request.getRequestDatas();
            // 调用 MediaType
            String requestMediaType = request.getRequestMediaType();

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(requestUrl).path(method);
            if (!CollectionUtils.isEmpty(queryParams)) {
                for (final RequestParam requestParam : queryParams) {
                    webTarget = webTarget.queryParam(requestParam.getKey(), requestParam.getValue());
                }
            }

            final Invocation.Builder invocationBuilder = webTarget.request(requestMediaType);

            if (!CollectionUtils.isEmpty(headParams)) {
                for (final RequestParam requestParam : headParams) {
                    invocationBuilder.header(requestParam.getKey(), requestParam.getValue());
                }
            }

            Response response = null;
            String requestData = JsonMapper.buildNonNullBinder().toJson(requestDatas);

            Entity<String> entity = Entity.entity(requestData, requestMediaType);

            switch (httpMethod) {
            case Constant.GET:
                response = invocationBuilder.get();
                break;
            case Constant.DELETE:
                response = invocationBuilder.delete();
                break;
            case Constant.PUT:
                response = invocationBuilder.put(entity);
                break;
            case Constant.POST:
                response = invocationBuilder.post(entity);
                break;
            default:
                response = invocationBuilder.get();
            }
            if (response != null) {
                // String rr = response.readEntity(String.class);
                return response;
            } else {
                client.close();
                return null;
            }

        } catch (Throwable e) {
            logger.error("远程调用方法出错或者对象格式无法解析", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param httpMethod
     * @param 重载上面的方法
     *            ；
     * @return
     */
    public Response request(String httpMethod, RestClient request, String requestData, String httpUrl) {
        try {
            this.requestUrl = httpUrl;
            // 调用server接口名字
            String method = request.getMethod();
            // 调用Headers
            Set<RequestParam> headParams = request.getHeadParams();
            // 调用参数集合
            Set<RequestParam> queryParams = request.getQueryParams();
            // 调用参数体
            // 调用 MediaType
            String requestMediaType = request.getRequestMediaType();

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(requestUrl).path(method);
            if (!CollectionUtils.isEmpty(queryParams)) {
                for (final RequestParam requestParam : queryParams) {
                    webTarget = webTarget.queryParam(requestParam.getKey(), requestParam.getValue());
                }
            }

            final Invocation.Builder invocationBuilder = webTarget.request(requestMediaType);

            if (!CollectionUtils.isEmpty(headParams)) {
                for (final RequestParam requestParam : headParams) {
                    invocationBuilder.header(requestParam.getKey(), requestParam.getValue());
                }
            }

            Response response = null;
            // String requestData = JsonMapper.buildNonNullBinder().toJson(requestDatas);

            Entity<String> entity = Entity.entity(requestData, requestMediaType);
            switch (httpMethod) {
            case Constant.GET:
                response = invocationBuilder.get();
                break;
            case Constant.DELETE:
                response = invocationBuilder.delete();
                break;
            case Constant.PUT:
                response = invocationBuilder.put(entity);
                break;
            case Constant.POST:
                response = invocationBuilder.post(entity);
                break;
            default:
                response = invocationBuilder.get();
            }
            if (response != null) {
                // String rr = response.readEntity(String.class);
                return response;
            } else {
                client.close();
                return null;
            }

        } catch (Throwable e) {
            logger.error("远程调用方法出错或者对象格式无法解析", e);
            throw new RuntimeException(e);
        }
    }
}
