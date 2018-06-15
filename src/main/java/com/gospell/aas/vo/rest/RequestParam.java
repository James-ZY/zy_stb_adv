package com.gospell.aas.vo.rest;

/**
 * 
 * <p> Title: 调用HTTP 接口输入参数值</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by IS </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author:  free lance
 * @Email:   free.lance@Gmail.com
 * @version: 2.0
 * @date:    2014-12-4  下午5:36:38
 *
 */
public class RequestParam {
    private String key;
    private Object value;

    public RequestParam(final String key, final Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

}
