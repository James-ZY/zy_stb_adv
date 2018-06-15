package com.gospell.aas.entity.adv;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.persistence.IdEntity;

/**
 * 当netty服务器不能联通客户端的时候，需要把数据保存起来，用于下次服务器连接上来后主动推送删除的广告
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_adv_network")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvNetwork extends IdEntity<AdvNetwork> {

	private static final long serialVersionUID = 1L;

	
	private String advId;// 频道Id
	private String clientId;// 网络发送器ID
	
	private Integer status;//0表示通知客户端删除该广告但没有成功，1表示广告通过审核推送广告到客户端失败 2表示紧急插播失败 3表示紧急停播失败
	
	public final static Integer DELETE_CLIENT_ADV_FAIL=0;
	
	public final static Integer PUSH_CLIENT_ADV_FAIL=1;
	
	public final static Integer PUSH_PLAY_NOW_CLIENT_ADV_FAIL=2;
	public final static Integer DELETE_NOW_CLIENT_ADV_FAIL=3;
	
	@Column(name = "adv_id", nullable = false)
	@JsonProperty("advId")
	public String getAdvId() {
		return advId;
	}
	public void setAdvId(String advId) {
		this.advId = advId;
	}
	
	@Column(name = "client_id", nullable = false)
	@JsonProperty("clientId")
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	 
	
	public AdvNetwork() {
		super();
	}

	public AdvNetwork(String id) {
		this();
		this.id = id;
	}
	@Column(name = "ad_status", nullable = false)
	@JsonProperty("status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	 

 
	
	

}