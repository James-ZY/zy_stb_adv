package com.gospell.aas.entity.adv;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.dto.adv.AdcomboUsedDTO;

/**
 * 频道Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_channel")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdChannel extends IdEntity<AdChannel> {

	private static final long serialVersionUID = 1L;

	
	private String channelId;// 频道Id
	private String channelName;// 频道名称
	private String channelType;// 频道类型
	private String serviceId; //通道ID
	private String serviceName;//通道名称
	private String isMainChannel;//是否是主通道（0：否；1：是）
	private String isVideoChannel;//是否是视频通道
	private AdNetwork adNetWork; // 频道所属广告发送器
	private AdProgramCategory adCategory;//节目分类
	
	private String type;//广告类型
	@Transient
	private String oldAdCategoryId;//旧广告分类
	
	private List<AdCombo> comboList = Lists.newArrayList();
	private List<AdType> typeList = Lists.newArrayList();
	@Transient
	private List<AdcomboUsedDTO> adcomboUsedList;// 占用的套餐列表
	 

	public AdChannel() {
		super();
	}

	public AdChannel(String id) {
		this();
		this.id = id;
	}

	@Column(name = "ad_channel_id", nullable = false)
	@JsonProperty("id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "ad_channel_name", nullable = false)
	@JsonIgnore
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Column(name = "ad_channel_type", nullable = false)
	@JsonIgnore
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "ad_service_id", nullable = false)
	@JsonIgnore
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name = "ad_service_name", nullable = false)
	@JsonIgnore
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Column(name = "is_main_channel", nullable = false)
	@JsonIgnore
	public String getIsMainChannel() {
		return isMainChannel;
	}

	public void setIsMainChannel(String isMainChannel) {
		this.isMainChannel = isMainChannel;
	}

	@Column(name = "is_video_channel", nullable = false)
	@JsonIgnore
	public String getIsVideoChannel() {
		return isVideoChannel;
	}

	public void setIsVideoChannel(String isVideoChannel) {
		this.isVideoChannel = isVideoChannel;
	}

	@ManyToOne
	@JoinColumn(name = "ad_network_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "所属发送器不能为空")
	public AdNetwork getAdNetWork() {
		return adNetWork;
	}

	public void setAdNetWork(AdNetwork adNetWork) {
		this.adNetWork = adNetWork;
	}
	

	@ManyToOne
	@JoinColumn(name="category_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public AdProgramCategory getAdCategory() {
		return adCategory;
	}

	public void setAdCategory(AdProgramCategory adCategory) {
		this.adCategory = adCategory;
	}
 
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_combo_channel", joinColumns = { @JoinColumn(name = "ad_channel_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ad_combo_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdCombo> getComboList() {
		return comboList;
	}

	public void setComboList(List<AdCombo> comboList) {
		this.comboList = comboList;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_type_channel", joinColumns = { @JoinColumn(name = "ad_channel_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ad_type_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<AdType> typeList) {
		this.typeList = typeList;
	}

	@Transient
	@JsonIgnore
	public String getType() {
		return type;
	}
	@Transient
	@JsonIgnore
	public void setType(String type) {
		this.type = type;
	}
	
	@Transient
	public String getOldAdCategoryId() {
		return oldAdCategoryId;
	}

	@Transient
	public void setOldAdCategoryId(String oldAdCategoryId) {
		this.oldAdCategoryId = oldAdCategoryId;
	}
	
	@Transient
	public List<AdcomboUsedDTO> getAdcomboUsedList() {
		return adcomboUsedList;
	}

	@Transient
	public void setAdcomboUsedList(List<AdcomboUsedDTO> adcomboUsedList) {
		this.adcomboUsedList = adcomboUsedList;
	}

}