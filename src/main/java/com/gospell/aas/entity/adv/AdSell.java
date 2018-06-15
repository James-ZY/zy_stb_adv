package com.gospell.aas.entity.adv;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.dto.adv.AdSellNumberDTO;

/**
 * 广告销售Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_sell")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdSell extends IdEntity<AdSell> {

	private static final long serialVersionUID = 1L;

	private String contractNumber;//销售编号
	private AdCombo adCombo;// 广告套餐
	private Advertiser advertiser;// 广告商
	private Date startDate; // 销售开始时间(选择必须大于等于现在的日期)
	private Date endDate; // 销售结束时间
	private Integer status;//状态
	
	public static final Integer ADSELL_YES_VALID = 1;// 有效
	public static final Integer ADSELL_NO_VALID = 0;// 无效
	@Transient
	private Date createStartDate;
	@Transient
	private Date createEndDate;
 
	@Transient
	private AdSellNumberDTO dto;
 
	@Transient
	private int history;//用于统计页面返回值
	
	@Transient
	private Boolean isPast;//是否过期（过期表示套餐的销售结束时间小于当前时间）
	 

	public AdSell() {
		super();
	}

	public AdSell(String id) {
		this();
		this.id = id;
	}

	@Column(name="contract_number")
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@ManyToOne
	@JoinColumn(name="ad_combo_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message="归属广告套餐不能为空")
	public AdCombo getAdCombo() {
		return adCombo;
	}

	public void setAdCombo(AdCombo adCombo) {
		this.adCombo = adCombo;
	}

	@ManyToOne
	@JoinColumn(name="advertiser_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message="归属广告商不能为空")
	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	@Column(name="start_date",nullable=false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="end_date",nullable=false)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public AdSellNumberDTO getDto() {
		return dto;
	}

	@Transient
	public void setDto(AdSellNumberDTO dto) {
		this.dto = dto;
	}

	@Transient
	public Date getCreateStartDate() {
		return createStartDate;
	}

	@Transient
	public void setCreateStartDate(Date createStartDate) {
		this.createStartDate = createStartDate;
	}

	@Transient
	public Date getCreateEndDate() {
		return createEndDate;
	}

	@Transient
	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	@Transient
	public int getHistory() {
		return history;
	}

	@Transient
	public void setHistory(int history) {
		this.history = history;
	}

	@Transient
	public Boolean getIsPast() {
		return isPast;
	}

	@Transient
	public void setIsPast(Boolean isPast) {
		this.isPast = isPast;
	}

	 
  
	
	
}