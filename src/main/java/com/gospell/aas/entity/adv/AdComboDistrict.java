package com.gospell.aas.entity.adv;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 运营商区域中间表
 * 
 * @author zhengdesheng
 * @version 2016-5-24
 */
@Entity
@Table(name = "ad_combo_district")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdComboDistrict implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;		// 编号
	private AdCombo adcombo;// 套餐ID
	private AdDistrictCategory district;// 区域ID

	private String selfDistrictId;// 自定义区域ID

	public AdComboDistrict() {
		super();
	}

	public AdComboDistrict(String id) {
		this();
		this.id = id;
	}

	@Id
	@JsonIgnore
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne  
	@JoinColumn(name = "ad_combo_id")
	public AdCombo getAdcombo() {
		return adcombo;
	}

	public void setAdcombo(AdCombo adcombo) {
		this.adcombo = adcombo;
	}

	@ManyToOne  
	@JoinColumn(name = "ad_district_id")
	public AdDistrictCategory getDistrict() {
		return district;
	}

	public void setDistrict(AdDistrictCategory district) {
		this.district = district;
	}

	@Column(name="ad_self_district_id")
	public String getSelfDistrictId() {
		return selfDistrictId;
	}

	public void setSelfDistrictId(String selfDistrictId) {
		this.selfDistrictId = selfDistrictId;
	}

}