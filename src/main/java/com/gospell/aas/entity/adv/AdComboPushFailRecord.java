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
@Table(name = "ad_combo_push_record")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdComboPushFailRecord extends IdEntity<AdComboPushFailRecord> {

	private static final long serialVersionUID = 1L;

	
	private String comboId;// 频道Id
	private String clientId;// 网络发送器ID
	
	private Integer status;
	
	//0表示通知客户端删除该套餐失败，1表示已运营的广告套餐主动推送广告到客户端失败
	public final static Integer DELETE_CLIENT_ADCOMBO_FAIL=0;
	
	public final static Integer PUSH_CLIENT_ADCOMBO_FAIL=1;
	
	 
	@Column(name = "ad_combo_id", nullable = false)
	@JsonProperty("comboId")
	public String getComboId() {
		return comboId;
	}
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	@Column(name = "client_id", nullable = false)
	@JsonProperty("clientId")
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	 
	
	public AdComboPushFailRecord() {
		super();
	}

	public AdComboPushFailRecord(String id) {
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