package com.gospell.aas.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.IdEntity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统参数
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_param")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysParam extends IdEntity<SysParam> {

	private static final long serialVersionUID = 1L;

	private String paramType;//参数类型
	private String paramKey;//参数键
	private String paramValue;//参数值
	private Integer enable;//是否启用（0启用 1不启用）
	private Integer canUpdate;//是否能修改（0不能 1能）


	@Transient
	private String districtValue;//区域设置值

	public static final Integer ENABLE_NO = 0;//不启用
	public static final Integer ENABLE_YES=1;//启用

	public static final Integer CANUPDATE_NO = 0;//不能
	public static final Integer CANUPDATE_YES=1;//能
	//CU开头表示能够自行添加修改的参数
	public static String NETWORK_VIDEO_RATE = "NVR";//视频码率前缀
	public static String NETWORK_PICTURE_RATE = "NPR";//图片码率前缀
	public static String ADELEMENT_TIME_SET = "ATS";//发送器推送广告间隔时间控制
	public static String BOOT_PICTURE_TOTAL_TIME = "CU_BPTT";//开机图片总时长
	public static String BOOT_PICTURE_TIME_SET = "CU_BPTS";//开机单图片时长
	public static String CORNER_PICTURE_TOTAL_TIME = "CU_CPTT";//挂角图片总时长
	public static String CORNER_PICTURE_TIME_SET = "CU_CPTS";//挂角单图片时长
	public static String PICTURE_SHOW_TIME_SET = "CU_PSTS";//图片最少展示时长
	public static String ROLL_PICTURE_MAX_LENGTH_SET = "CU_RPMLS";//滚动图片最大长度
	public static String SYS_AREA_TYPE = "CU_SYS_AREA";
	public static String SYS_AREA_KEY = "SYS_AREA";

	public SysParam() {
		super();
	}

	public SysParam(String id) {
		this();
		this.id = id;

	}

	@Column(name = "param_type")
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	@Column(name = "param_key")
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	@Column(name = "param_value")
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}


	@Column(name = "can_update")
	public Integer getCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(Integer canUpdate) {
		this.canUpdate = canUpdate;
	}

	@Transient
	public String getDistrictValue() {
		if(StringUtils.isNotBlank(this.paramKey) && this.paramKey.endsWith(SYS_AREA_KEY)){
			return this.paramValue;
		}else{
			return "";
		}
	}

	@Transient
	public void setDistrictValue(String districtValue) {
		this.districtValue = districtValue;
	}

}