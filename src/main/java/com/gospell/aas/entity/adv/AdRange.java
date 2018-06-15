package com.gospell.aas.entity.adv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.IdEntity;

/**
 * 广告坐标范围Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_range")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdRange extends IdEntity<AdRange> {

	private static final long serialVersionUID = 1L;

	private String rangeName;//名称
	
	private Integer beginX;//最小x坐标
	
	private Integer beginY;//最小y坐标
	
	private Integer endX;//最大X坐标
	
	private Integer endY;//最大Y坐标
	
	private Integer status;//状态（0未启用 1启用）
	
	private Integer flag;//格式状态（0标清 1高清）
	
	private AdType type;//广告类型（只能选择跟频道无关的广告）
	
	public static final Integer RANGE_START_STAUTS=1;
	
	public static final Integer RANGE_END_STATUS=0;

	public AdRange() {
		super();
		this.status = RANGE_END_STATUS;
	}

	public AdRange(String id) {
		this();
		this.id = id;

	}

	@Column(name="range_name")
	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	@Column(name="begin_x")
	@NotNull
	public Integer getBeginX() {
		return beginX;
	}

	public void setBeginX(Integer beginX) {
		this.beginX = beginX;
	}

	@Column(name="begin_y")
	@NotNull
	public Integer getBeginY() {
		return beginY;
	}

	public void setBeginY(Integer beginY) {
		this.beginY = beginY;
	}

	@Column(name="end_x")
	@NotNull
	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	@Column(name="end_y")
	@NotNull
	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	@Column(name="status")
	@NotNull
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name="ad_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message="type is not null")
	public AdType getType() {
		return type;
	}

	public void setType(AdType type) {
		this.type = type;
	}

	@Column(name="flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Transient
	public String getX(){
		String x="";
		if(null != beginX && null != endX){
			x = beginX+"~"+endX;
		}
		return x;
	}
	
	@Transient
	public String getY(){
		String y="";
		if(null != beginY && null != endY){
			y= beginY+"~"+endY;
		}
		return y;
	}
	 
	 

}