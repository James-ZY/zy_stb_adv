package com.gospell.aas.webservice.rest;

/**
 * 
 * <p> Title: RESTful REST返回结果</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by ACTEC </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author: free lance
 * @version: 1.0
 * @date: 2014年10月29日 下午4:17:27
 * 
 */
public class Result<T> {
    /**
     * 状态码
     */
    private String status;
    /**
     * 状态信息,错误描述.
     */
    private String message;
    /**
     * 数据.
     */
    private T content;

    private Result(String status, String message, T content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }

    private Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private Result(String message) {
        this.message = message;
    }

    
    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     */
    public static <T> Result<T> buildResult(Status status, String message, T content) {
        return new Result<T>(status.getCode(), message, content);
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     */
    public static <T> Result<T> buildResult(Status status, T content) {
        return new Result<T>(status.getCode(), status.getReason(), content);
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     */
    protected static <T> Result<T> buildResult(Status status) {
        return new Result<T>(status.getCode(), status.getReason());
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     * 
     * @param content
     *            业务层处理结果
     */
    public static <T> Result<T> buildSuccessResult() {
        return buildResult(Status.OK);
    }

    /**
     * 创建一个带有<b>成功状态</b>的结果对象.
     * 
     * @param content
     *            业务层处理结果
     */
    public static <T> Result<T> buildSuccessResult(T content) {
        return buildResult(Status.OK, content);
    }

    public static <T> Result<T> buildErrorResult() {
        return buildResult(Status.ERROR);
    }

    /**
     * 成功后不含有message:OK
     * 
     * @param <T>
     * @param content
     * @return
     */
    public static <T> Result<T> buildSuccessOkResult(String content) {
        return new Result<T>(Status.OK.getCode(), content);
    }

    /**
     * 返回成功代码，成功描述和自定义类型
     * 
     * @param <T>
     * 
     * @return
     */
    public static <T> Result<T> buildSuccessOkResult(T content) {
        return new Result<T>(Status.OK.getCode(), Status.OK.getReason(), content);
    }

    /**
     * 创建一个带有<b>错误状态</b>的结果对象.
     * 
     * @param status
     *            错误状态
     */
    public static <T> Result<T> buildErrorResult(Status status) {
        return buildResult(status);
    }

    /**
     * 创建一个带有<b>错误状态</b>的结果对象.
     * 
     * @param status
     *            错误状态
     * @param message
     *            错误信息
     */
    public static <T> Result<T> buildErrorResult(Status status, String message) {
        return new Result<T>(status.getCode(), message);
    }

    /**
     * 获取返回信息
     * 
     * @param status
     * @return
     */
    public static <T> Result<T> buildReturnResult(Status status) {
        return new Result<T>(status.getCode(), status.getReason());
    }

    public static <T> Result<T> buildErrorResult(String message) {
        return new Result<T>(message);
    }

    // -- getters --//

    public String getMessage() {
        return message;
    }

    public T getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public enum Status {
        /**
         * 成功状态,创建成功结果的时候自动设置.
         */
        OK("200", "OK"),

        /**
         * 错误的请求,参数不正确,如果没有更精确的状态表示,使用此状态.
         */
        BAD_REQUEST("400", "Bad Request"),

        /**
         * 错误状态
         */
        ERROR("404", "NOT FOUND"),

        /**
         * 服务器内部错误,如果没有更精确的状态表示,使用此状态.
         */
        INTERNAL_SERVER_ERROR("500", "Internal Server Error"),

        UNKOWN_ERROR("600", "未知错误"),

        NOT_EXIST_ERROR("700", "数据不存在"),

        EXIST_ERROR("800", "数据已经存在"),
        
        BLANK_PARAMETER("900", "参数值为空"),

        NETWORK_CONFLICT("1000", "发送器冲突");

        /**
         * 状态码,长度固定为6位的字符串.
         */
        private String code;
        /**
         * 错误信息.
         */
        private String reason;

        Status(final String code, final String reason) {
            this.code = code;
            this.reason = reason;
        }

        public String getCode() {
            return code;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return code + ": " + reason;
        }

    }

}
