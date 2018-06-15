package com.gospell.aas.entity.adv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gospell.aas.common.persistence.IdEntity;

/**
 * 广告位置Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_postion")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdPosition extends IdEntity<AdPosition> {

	private static final long serialVersionUID = 1L;

	private String pointId;// 位置ID
	private String positionName;// 类型名称
	private Integer isFlag;// 位置形式(0坐标 1轨迹)
	private Integer status;//（0标清 1高清）
	private Integer beginPointX;
	private Integer beginPointY;
	private AdType adType;// 广告类型
	/**
	 * 以下三个字段目前用于支持轨迹
	 */
	private Integer endPointX;
	private Integer endPointY;
	private Integer velocity;// 移动速度
	
	@Transient
	private String point;
	
 

	public static final Integer POSITION_SD=0;//标清
	
	public static final Integer POSITION_HD=1;//高清

	public AdPosition() {
		super();
	}

	public AdPosition(String id) {
		this();
		this.id = id;
	}

	@Column(name = "position_id", nullable = false)
	@JsonIgnore
	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	@Column(name = "position_name", nullable = false)
	@JsonIgnore
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name = "is_flag", nullable = false)
	@JsonIgnore
	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
	}

	@Column(name = "begin_point_x", nullable = false)
	@JsonIgnore
	public Integer getBeginPointX() {
		return beginPointX;
	}

	public void setBeginPointX(Integer beginPointX) {
		this.beginPointX = beginPointX;
	}

	@Column(name = "begin_point_y", nullable = false)
	@JsonIgnore
	public Integer getBeginPointY() {
		return beginPointY;
	}

	public void setBeginPointY(Integer beginPointY) {
		this.beginPointY = beginPointY;
	}

	@Column(name = "end_point_x", nullable = false)
	@JsonIgnore
	public Integer getEndPointX() {
		return endPointX;
	}

	public void setEndPointX(Integer endPointX) {
		this.endPointX = endPointX;
	}

	@Column(name = "end_point_y", nullable = false)
	@JsonIgnore
	public Integer getEndPointY() {
		return endPointY;
	}

	public void setEndPointY(Integer endPointY) {
		this.endPointY = endPointY;
	}

	@ManyToOne
	@JoinColumn(name = "ad_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	@Column(name = "velocity")
	@JsonIgnore
	public Integer getVelocity() {
		return velocity;
	}

	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
	}

	@Transient
	@JsonSerialize
	public String getBegin() {
		if (null != beginPointX && null != beginPointY) {
			return "(" + beginPointX + "," + beginPointY + ")";
		}
		return null;
	}

	@Transient
	public String getEnd() {
		if (null != endPointX && null != endPointY) {
			return "(" + endPointX + "," + endPointY + ")";
		}
		return null;
	}
	
	@Transient
	public String getBeginEnd() {
		 if(null != getBegin() && null != getEnd()){
			 return getBegin()+","+getEnd();
		 }
		return null;
	}

	@Column(name="status")
	@JsonIgnore
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getPoint() {
		String s = "";
		if(adType.getTypeId().equals("2") || adType.getTypeId().equals("4") || adType.getTypeId().equals("5") || adType.getTypeId().equals("10")){
			s = "start("+(beginPointX==null?0:beginPointX)+","+(beginPointY==null?0:beginPointY)+")";
			if(endPointX != null && endPointY != null){
				s = s + ",end("+endPointX+","+endPointY+")";
			}
		}else{
			s = "start("+(beginPointX==null?0:beginPointX)+","+(beginPointY==null?0:beginPointY)+")";
		}
		return s;
	}

	@Transient
	public void setPoint(String point) {
		this.point = point;
	}

 
	
 
	
	

}