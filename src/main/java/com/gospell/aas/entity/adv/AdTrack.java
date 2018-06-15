package com.gospell.aas.entity.adv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 广告轨迹模板管理Entity
 * 
 * @author 左健宏
 * @version 2018-04-19
 */
@Entity
@Table(name = "ad_track")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdTrack extends IdEntity<AdTrack> {

	private static final long serialVersionUID = 1L;

	private String trackName;//名称

	private String coordinates;//坐标集合

	private Integer status;//状态（0未启用 1启用）

	private Integer flag;//格式状态（0标清 1高清）

	private Integer showTime;//总的显示时间

	private Integer bgWidth;//背景图片宽度

	private Integer bgHeight;//背景图片高度

	private AdType type;//广告类型（只能选择跟频道无关的广告）

	private AdRange range;//广告范围

	public static final Integer TRACK_START_STAUTS=1;

	public static final Integer TRACK_END_STATUS=0;


	public AdTrack() {
		super();
		this.status = TRACK_END_STATUS;
	}

	public AdTrack(String id) {
		this();
		this.id = id;

	}

	@Column(name="track_name")
	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	@Column(name="coordinates")
	@NotNull
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	@Column(name="status")
	@NotNull
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name="show_time")
	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}


	@Column(name="bg_width")
	public Integer getBgWidth() {
		return bgWidth;
	}

	public void setBgWidth(Integer bgWidth) {
		this.bgWidth = bgWidth;
	}

	@Column(name="bg_height")
	public Integer getBgHeight() {
		return bgHeight;
	}

	public void setBgHeight(Integer bgHeight) {
		this.bgHeight = bgHeight;
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

	@ManyToOne
	@JoinColumn(name="ad_range_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message="range is not null")
	public AdRange getRange() {
		return range;
	}

	public void setRange(AdRange range) {
		this.range = range;
	}


}