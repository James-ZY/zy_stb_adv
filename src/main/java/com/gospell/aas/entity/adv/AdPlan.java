package com.gospell.aas.entity.adv;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 
 
/**
 * 广告规划Entity
 * 
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_plan")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdPlan extends IdEntity<AdPlan> {

    private static final long serialVersionUID = 1L;
 
    private AdCombo adCombo;//广告套餐
    private Adelement adelemet;//广告
    private Date startDate;//开始时间
    private Integer duringDate;//持续时间（以天来记）
	
	
    public AdPlan() {
        super();
    }

    public AdPlan(String id) {
        this();
        this.id = id;
    }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ad_combo_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	@JsonIgnore
	public AdCombo getAdCombo() {
		return adCombo;
	}

	public void setAdCombo(AdCombo adCombo) {
		this.adCombo = adCombo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ad_adv_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	@JsonIgnore
	public Adelement getAdelemet() {
		return adelemet;
	}

	public void setAdelemet(Adelement adelemet) {
		this.adelemet = adelemet;
	}

	@Column(name="ad_start_time",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name="duration",nullable=false)
	public Integer getDuringDate() {
		return duringDate;
	}

	public void setDuringDate(Integer duringDate) {
		this.duringDate = duringDate;
	}

    
}