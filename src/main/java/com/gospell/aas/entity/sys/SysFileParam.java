package com.gospell.aas.entity.sys;

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
import com.gospell.aas.entity.adv.AdType;

/**
 * 系统参数（用于设置上传文件数量）
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_file_param")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysFileParam extends IdEntity<SysFileParam> {

	private static final long serialVersionUID = 1L;
	
	private Integer amount;//数量
	private Integer flag;//（0标清 1高清）
	private Integer enable;//是否启用（0启用 1不启用） 
	private AdType adType;//广告类型
	@Transient
	private String spId;
	
	
	public static final Integer FLAG_SD = 0;//标清
	public static final Integer FLAG_HD=1;//高清
	
	public static final Integer ENABLE_NO = 0;//不启用
	public static final Integer ENABLE_YES=1;//启用

	public SysFileParam() {
		super();
		this.enable = SysFileParam.ENABLE_NO;
		this.setAmount(3);
	}

	public SysFileParam(String id) {
		this();
		this.id = id;
		
	}

	@Column(name = "amount")
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@ManyToOne
	@JoinColumn(name="ad_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull
	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	@Transient
	public String getSpId() {
		return spId;
	}
	@Transient
	public void setSpId(String spId) {
		this.spId = spId;
	}

}