package com.gospell.aas.entity.adv;

import java.util.Date;

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
import com.gospell.aas.dto.adv.AdStatisticPlayDTO;
 
 
/**
 * 广告反馈实体
 * 
 * @author zhengdesheng
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_statistics")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdStatistic extends IdEntity<AdStatistic> {

    private static final long serialVersionUID = 1L;
 
 
    private String stdSerialNumber;// 机顶盒序列号
    private String smartcardId;// 智能卡ID
	private Date playStartDate; // 广告播放开始时间（当下面的isFlag为1的时候，表示当前广告跳转网址的时间）
	
	private Date playEndDate; // 广告播放结束时间（该字段暂时保留。无意义）
	
	private Integer duration;//持续时间
	
	private Adelement adElemet;//所属广告
	
	private Integer systemType;//扫描手机的类型（//0表示安卓 1表示IOS 2表示其他）
	
	private String bossUserCode;//扫描是做在boss的app里面的，所有可以上传对应的boss usercode，当用户未登录扫描的时候就为null
	
	private	Integer isScan;//0表示播放记录 1表示扫描记录
	
	@Transient
	private AdStatisticPlayDTO dto;
	@Transient
	private int history;
	
	public static final Integer SYSTEM_ANDROID = 0;
	public static final Integer SYSTEM_IOS = 1;
	public static final Integer SYSTEM_OTHER = 2; 
	
	public static final Integer SKIP_URL_YES = 1;
	public static final Integer SKIP_URL_NO = 0;
    public AdStatistic() {
        super();
    }

    public AdStatistic(String id) {
        this();
        this.id = id;
    }

    @Column(name="stb_serial_number")
	public String getStdSerialNumber() {
		return stdSerialNumber;
	}

	public void setStdSerialNumber(String stdSerialNumber) {
		this.stdSerialNumber = stdSerialNumber;
	}

	@Column(name="smartcard_id")
	public String getSmartcardId() {
		return smartcardId;
	}

	public void setSmartcardId(String smartcardId) {
		this.smartcardId = smartcardId;
	}
	@Column(name="play_start_date")
	public Date getPlayStartDate() {
		return playStartDate;
	}

	public void setPlayStartDate(Date playStartDate) {
		this.playStartDate = playStartDate;
	}

	@Column(name="play_end_date")
	public Date getPlayEndDate() {
		return playEndDate;
	}

	public void setPlayEndDate(Date playEndDate) {
		this.playEndDate = playEndDate;
	}

	@Column(name="duration")
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	@ManyToOne
	@JoinColumn(name="adv_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message="广告不能为空")
	public Adelement getAdElemet() {
		return adElemet;
	}

	public void setAdElemet(Adelement adElemet) {
		this.adElemet = adElemet;
	}

	@Column(name="system_type")
	public Integer getSystemType() {
		return systemType;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	@Column(name="boss_user_code")
	public String getBossUserCode() {
		return bossUserCode;
	}

	public void setBossUserCode(String bossUserCode) {
		this.bossUserCode = bossUserCode;
	}

	@Column(name="is_scan")
	public Integer getIsScan() {
		return isScan;
	}

	public void setIsScan(Integer isScan) {
		this.isScan = isScan;
	}

	@Transient
	public AdStatisticPlayDTO getDto() {
		return dto;
	}

	@Transient
	public void setDto(AdStatisticPlayDTO dto) {
		this.dto = dto;
	}

	@Transient
	public int getHistory() {
		return history;
	}

	@Transient
	public void setHistory(int history) {
		this.history = history;
	}

 

	
    
}