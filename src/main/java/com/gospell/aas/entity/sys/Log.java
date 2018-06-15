package com.gospell.aas.entity.sys;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.excel.annotation.ExcelField;

/**
 * 日志Entity
 * @author free lance
 * @version 2013-05-30
 */
@Entity
@Table(name = "sys_log")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Log extends BaseEntity<Log> {

	private static final long serialVersionUID = 1L;
	private String id;			// 日志编号
	private String type; 		// 日志类型（1：接入日志；2：错误日志）
	private User createBy;		// 创建者
	private Date createDate;	// 日志创建时间
	private String remoteAddr; 	// 操作用户的IP地址
	private String requestUri; 	// 操作的URI
	private String method; 		// 操作的方式
	private String params; 		// 操作提交的数据
	private String userAgent;	// 操作用户代理信息
	private String exception; 	// 异常信息
	private String logInfo;//日志信息
	@Transient
	private String loginName;
	@Transient
	private String userName;
	@Transient
	private String result;
	
	public static final String TYPE_ACCESS = "1";
	public static final String TYPE_EXCEPTION = "2";
	
	public Log(){
		super();
	}
	
	public Log(String id){
		this();
		this.id = id;
	}

	@PrePersist
	public void prePersist(){
		this.id = IdGen.uuid();
	}
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="creation.time", align=2, sort=5,required=1,max=20)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@ExcelField(title="operatives.IP", align=2, sort=3,required=1,max=20)
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
    @ExcelField(title="operation.destination", align=2, sort=4,required=1,max=20)
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	@Transient
    @ExcelField(title="login.name", align=2, sort=1,required=1,max=20)
	public String getLoginName() {
		return this.createBy.getLoginName();
	}
    @Transient
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@Transient
	@ExcelField(title="user.name", align=2, sort=2,required=1,max=20)
	public String getUserName() {
		return this.createBy.getName();
	}
	@Transient
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Transient
	@ExcelField(title="operation.result", align=6, sort=6,required=1,max=20)
	public String getResult() {
		return ApplicationContextHelper.getMessage(this.exception==null || this.exception == "" ?"success":"fail");
	}
	@Transient
	public void setResult(String result) {
		this.result = result;
	}
	
}