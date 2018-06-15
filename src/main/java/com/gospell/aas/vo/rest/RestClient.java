package com.gospell.aas.vo.rest;

import java.util.Map;
import java.util.Set;

/**
 * 
 * <p> Title: 调用HTTP接口的方法和输入参数值</p>
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
 * @date: 2014-12-4 下午5:00:51
 * 
 */
public class RestClient {

    private String method;

    private Set<RequestParam> queryParams;

    private Set<RequestParam> headParams;

    private Object requestDatas;

    private String requestMediaType;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the queryParams
     */
    public Set<RequestParam> getQueryParams() {
        return queryParams;
    }

    /**
     * @param queryParams
     *            the queryParams to set
     */
    public void setQueryParams(Set<RequestParam> queryParams) {
        this.queryParams = queryParams;
    }

    /**
     * @return the headParams
     */
    public Set<RequestParam> getHeadParams() {
        return headParams;
    }

    /**
     * @param headParams
     *            the headParams to set
     */
    public void setHeadParams(Set<RequestParam> headParams) {
        this.headParams = headParams;
    }

	public Object getRequestDatas() {
		return requestDatas;
	}

	public void setRequestDatas(Object requestDatas) {
		this.requestDatas = requestDatas;
	}

	/**
     * @param requestDatas
     *            the requestDatas to set
     */
    public void setRequestDatas(Map<String, Object> requestDatas) {
        this.requestDatas = requestDatas;
    }

    /**
     * @return the requestMediaType
     */
    public String getRequestMediaType() {
        return requestMediaType;
    }

    /**
     * @param requestMediaType
     *            the requestMediaType to set
     */
    public void setRequestMediaType(String requestMediaType) {
        this.requestMediaType = requestMediaType;
    }

}