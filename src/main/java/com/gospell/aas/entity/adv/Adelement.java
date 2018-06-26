package com.gospell.aas.entity.adv;

import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.entity.sys.User;

/**
 * 广告Entity
 * 
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_element")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel(value="Adelement", discriminator = "adelement", subTypes = {Adelement.class})
public class Adelement extends IdEntity<Adelement> {

	private static final long serialVersionUID = 1L;

	private String adId;// 广告ID(分类ID + 分类自身下的顺序号)
	private String adName;// 广告名称
	private Advertiser advertiser;// 广告商
	
	private AdCategory adCategory;//广告分类
	
	protected User auditUser;	// 审核人
	/**
	 * 如果广告商购买了一个广告套餐，可以在该广告套餐下发布多条广告，只是每条广告的具体播放时间不冲突，比如广告套餐的播放时刻是每天的09:00-09:
	 * 30， 那么在同一天的09：00-09:30可以播该广告套餐的多条广告，只是第一条广告是09:00：50-09:01：34分播，
	 * 第二条09:02:45-09:03：56分播，第三条09:29:34-09:30:00播，以下两个字段目前作为保留字段，
	 * 因为目前对应的广告套餐在同一日期只对应一条广告
	 */
	private String playStart;// 广告播放开始时刻（套餐精确到分，广告精确到秒）
	private String playEnd;// 广告播放的结束时刻
	private String addText; // 附加文本信息
	private AdCombo adCombo;// 广告套餐
	private Integer isFlag;// 广告是否包含附加信息的标志(0未附件数据 1纯文本 2网站链接)
	private Integer status;// 0待审核 1 认领 2审核通过 -1 审核未通过 3投放中 4投放结束 
	private String text;// 广告文字
	private Date startDate;// 广告展示的开始时间
	private Date endDate;// 广告展示的结束时间
	private Date claimDate;// 广告认领时间
	private Date auditDate;// 广告审核时间
	private Integer resolution;//广告分辨率(0标清 1高清)
	private Integer isSd;//是否启用标清
	private Integer isHd;//是否启用高清
	private AdPosition position;// 广告位置坐标（标清）
	private AdPosition hdPosition;//广告位置坐标（高清）
	
	private Integer showWay;//广告展示方式（0纯文本 1二维码）
	
	private AdType childAdType;//子广告类型，套餐跟子广告类型不相关，所以这个地方要单独拿出来比较，比如菜单广告有四种子广告类型（ 主菜单, 音量条	,频道列表, EPG）
	
	private Date originalEndDate;//原计划结束时间
	/**
	 * 主要用于处理当用户修改正在投放的广告，修改正在投放的广告有以下几种情况：
	 * 1.只能修改广告名称、广告坐标、广告链接
	 * 2.修改广告后的状态为待审核，但是由于是播放过的广告，所以不允许删除
	 * 3.当修改正在投放的广告审核通过后，主动投放给客户端，审核不通过可以删除，删除时间就是广告投放结束时间
	 * 4.审核不通过的时候，如果用户没有删除广告，那么终端继续播放原来的广告，在定时任务的时候，如果该广告日期到了，应该结束该广告
	 */
	private Integer isPlay;//是否播放过（0未播放 1播放）
	
	
	private Integer playTime;//开机广告播放的时间（以秒为单位）
	
	private String sdShowParam;//开机画面广告展示参数
	
	private String hdShowParam;//开机画面广告展示参数
	
	private Integer velocity;// 滚动广告移动速度

	@Transient
	private String sdversion;

	@Transient
	private String hdversion;

	@Transient
	private Integer isValid;
	
	@Transient
	private String fileSize;
	
	@Transient
	private List<Integer> statusList;
	
	@Transient
	private List<String> officeList;//用于领导查看审核广告
	
	@Transient
	private Integer isPlayNow;//是否紧急插播(0否 1是)
	
	@Transient
	private String oldAdCategoryId;//旧广告分类
	
	@Transient
	private Integer temp;//旧广告分类
	
	@Transient
	private String adTypeId;//广告分类ID
	
	@Transient
	private Integer sdFx;//标清滚动方向
	
	@Transient
	private Integer hdFx;//高清滚动方向
	
	@Transient
	private Integer sdMaxNC;//标清内存
	
	@Transient
	private Integer hdMaxNC;//高清内存
	
	private Integer format;// 格式（1图片 2视频）

	@Transient
	private String isExpired;

	private List<AdControll> controllerList = Lists.newArrayList();// 广告包含的标清资源文件
	private List<AdControll> hdControllerList = Lists.newArrayList();// 广告包含的高清资源文件

	public static final Integer ADV_STATUS_WAIT = 0;// 待审核
	public static final Integer ADV_STATUS_CLAIM = 1;// 认领
	public static final Integer ADV_STATUS_PASS = 2;// 审核通过
	public static final Integer ADV_STATUS_FAIL = -1;// 审核未通过
	public static final Integer ADV_STATUS_SHOW = 3;// 投放中
	public static final Integer ADV_STATUS_END = 4;// 投放结束
	public static final Integer ADV_STATUS_PAUSE= 5;// 暂停中
	public static final Integer ADV_STATUS_FREEZE= 6;// 冻结中
	public static final Integer ADV_STATUS_STOP= 7;//合同终止
	
	public static final Integer ADV_STATUS_PLAY = 0;// 未播放
	public static final Integer ADV_STATUS_NOT_PLAY= 1;// 播放过
	
	public static final Integer ADV_SHOW_WAY_TEXT = 0;// 文本
	public static final Integer ADV_SHOW_WAY_QR_CODE= 1;// 二维码
	
	public static final Integer ADV_CLOSE_NOW_YES=1;//紧急插播
	public static final Integer ADV_CLOSE_NOW_NO=0;//不紧急插播
	
	public static final Integer ADV_DELETE_NOW_YES=1;//紧急停播
	public static final Integer ADV_DELETE_NOW_NO=0;//不紧急停播
	
	public static final Integer ADV_RESOLUTION_HD=1;//高清
	public static final Integer ADV_RESOLUTION_SD=0;//标清
	
	public static final Integer ADV_START_HD=1;//启用高清
	public static final Integer ADV_DISABLE_HD=0;//不启用高清
	
	public static final Integer ADV_START_SD=1;//启用标清
	public static final Integer ADV_DISABLE_SD=0;//不启用标清
	
	public static final Integer ADV_VALID_STATUS=1;//有效
	public static final Integer ADV_INVALID_STATUS=0;//无效

	public Adelement() {
		super();
	}

	public Adelement(String id) {
		this();
		this.id = id;
	}

	@Column(name = "ad_id")
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	@Column(name = "ad_name", nullable = false)
	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	@Column(name = "ad_add_text")
	public String getAddText() {
		return addText;
	}

	public void setAddText(String addText) {
		this.addText = addText;
	}

	@Column(name = "ad_text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@ManyToOne
	@JoinColumn(name = "advertiser_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	@ManyToOne
	@JoinColumn(name = "ad_combo_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "归属广告套餐不能为空")
	public AdCombo getAdCombo() {
		return adCombo;
	}

	public void setAdCombo(AdCombo adCombo) {
		this.adCombo = adCombo;
	}

	@Column(name = "ad_flag", nullable = false)
	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
	}

	@Column(name = "ad_status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_control_adelement", joinColumns = { @JoinColumn(name = "ad_adv_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_control_id") })
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
	public List<AdControll> getControllerList() {
		return controllerList;
	}

	public void setControllerList(List<AdControll> controllerList) {
		this.controllerList = controllerList;
	}
	
	
	 @ManyToMany(fetch = FetchType.LAZY)
	 @JoinTable(name = "ad_hd_control_adelement", joinColumns = { @JoinColumn(name = "ad_adv_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_control_id") })
	 @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	 @OrderBy("id")
	 @Fetch(FetchMode.SUBSELECT)
	 @NotFound(action = NotFoundAction.IGNORE)
	 @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	 @JsonIgnore
	public List<AdControll> getHdControllerList() {
		return hdControllerList;
	}

	public void setHdControllerList(List<AdControll> hdControllerList) {
		this.hdControllerList = hdControllerList;
	}

	@Column(name = "play_start")
	public String getPlayStart() {
		return playStart;
	}

	public void setPlayStart(String playStart) {
		this.playStart = playStart;
	}

	@Column(name = "play_end")
	public String getPlayEnd() {
		return playEnd;
	}

	public void setPlayEnd(String playEnd) {
		this.playEnd = playEnd;
	}

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@OneToOne
	@JoinColumn(name = "ad_position_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdPosition getPosition() {
		return position;
	}

	public void setPosition(AdPosition position) {
		this.position = position;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "ad_child_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdType getChildAdType() {
		return childAdType;
	}

	public void setChildAdType(AdType childAdType) {
		this.childAdType = childAdType;
	}

	@Column(name = "ad_format")
	public Integer getFormat() {
		return format;
	}

	public void setFormat(Integer format) {
		this.format = format;
	}
	@Column(name = "show_way")
	public Integer getShowWay() {
		return showWay;
	}

	public void setShowWay(Integer showWay) {
		this.showWay = showWay;
	}

	@Transient
	public String getPath() {
		List<String> nameIdList = Lists.newArrayList();
		if (null != controllerList && controllerList.size() > 0) {
			for (AdControll menu : controllerList) {
				nameIdList.add(menu.getId());
			}
		}
		return StringUtils.join(nameIdList, ",");
	}
	
	@Transient
	public String getFilePath() {
		List<String> nameIdList = Lists.newArrayList();
		if (null != controllerList && controllerList.size() > 0) {
			for (AdControll menu : controllerList) {
				nameIdList.add(menu.getFilePath());
			}
		}
		return StringUtils.join(nameIdList, ",");
	}

	@Transient
	public void setPath(String paths) {
		controllerList = Lists.newArrayList();
		if (null != paths) {
			String[] s = paths.split(",");
			for (int i = 0; i < s.length; i++) {
				AdControll a = new AdControll();
				a.setId(s[i]);
				controllerList.add(a);
			}
		}
	}
	
	@Transient
	public void setHdPath(String paths) {
		hdControllerList = Lists.newArrayList();
		if (null != paths) {
			String[] s = paths.split(",");
			for (int i = 0; i < s.length; i++) {
				AdControll a = new AdControll();
				a.setId(s[i]);
				hdControllerList.add(a);
			}
		}
	}
	
	@Transient
	public String getHdPath() {
		List<String> nameIdList = Lists.newArrayList();
		if (null != hdControllerList && hdControllerList.size() > 0) {
			for (AdControll menu : hdControllerList) {
				nameIdList.add(menu.getId());
			}
		}
		return StringUtils.join(nameIdList, ",");
	}

	@Transient
	public String getFileSize() {
		return fileSize;
	}

	@Transient
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	@Transient
	public String getComboId() {
		if(null != adCombo){
			return adCombo.getId();
		}else{
			return "";
		}
	}

	@OneToOne
	@JoinColumn(name="ad_hd_position_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdPosition getHdPosition() {
		return hdPosition;
	}

	public void setHdPosition(AdPosition hdPosition) {
		this.hdPosition = hdPosition;
	}

	@Transient
	public List<Integer> getStatusList() {
		return statusList;
	}

	@Transient
	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	@Column(name="is_play")
	public Integer getIsPlay() {
		return isPlay;
	}

	public void setIsPlay(Integer isPlay) {
		this.isPlay = isPlay;
	}

	@Column(name="original_end_date")
	public Date getOriginalEndDate() {
		return originalEndDate;
	}

	public void setOriginalEndDate(Date originalEndDate) {
		this.originalEndDate = originalEndDate;
	}

	@Transient
	public Integer getIsPlayNow() {
		return isPlayNow;
	}

	@Transient
	public void setIsPlayNow(Integer isPlayNow) {
		this.isPlayNow = isPlayNow;
	}

	@ManyToOne
	@JoinColumn(name="ad_category_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public AdCategory getAdCategory() {
		return adCategory;
	}

	
	public void setAdCategory(AdCategory adCategory) {
		this.adCategory = adCategory;
	}
	
	@ManyToOne
	@JoinColumn(name="audit_user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
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
	public Integer getTemp() {
		return temp;
	}

	@Transient
	public void setTemp(Integer temp) {
		this.temp = temp;
	}
	@Transient
	public String getAdTypeId() {
		return adTypeId;
	}
	@Transient
	public void setAdTypeId(String adTypeId) {
		this.adTypeId = adTypeId;
	}

	@Transient
	public Integer getSdFx() {
		return sdFx;
	}
	@Transient
	public void setSdFx(Integer sdFx) {
		this.sdFx = sdFx;
	}
	@Transient
	public Integer getHdFx() {
		return hdFx;
	}
	@Transient
	public void setHdFx(Integer hdFx) {
		this.hdFx = hdFx;
	}

	@Transient
	public Integer getSdMaxNC() {
		return sdMaxNC;
	}

	public void setSdMaxNC(Integer sdMaxNC) {
		this.sdMaxNC = sdMaxNC;
	}

	@Transient
	public Integer getHdMaxNC() {
		return hdMaxNC;
	}

	public void setHdMaxNC(Integer hdMaxNC) {
		this.hdMaxNC = hdMaxNC;
	}

	@Transient
	public String getSdversion() {
		return sdversion;
	}

	public void setSdversion(String sdversion) {
		this.sdversion = sdversion;
	}

	@Transient
	public String getHdversion() {
		return hdversion;
	}

	public void setHdversion(String hdversion) {
		this.hdversion = hdversion;
	}

	@Transient
	public List<String> getOfficeList() {
		return officeList;
	}

	@Transient
	public void setOfficeList(List<String> officeList) {
		this.officeList = officeList;
	}

	@Column(name="claim_date")
	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	@Column(name="audit_date")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name="ad_play_time")
	public Integer getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}
	
	@Column(name="sd_show_param")
	public String getSdShowParam() {
		return sdShowParam;
	}

	public void setSdShowParam(String sdShowParam) {
		this.sdShowParam = sdShowParam;
	}

	@Column(name="hd_show_param")
	public String getHdShowParam() {
		return hdShowParam;
	}

	public void setHdShowParam(String hdShowParam) {
		this.hdShowParam = hdShowParam;
	}
	
	@Column(name = "velocity")
	public Integer getVelocity() {
		return velocity;
	}

	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
	}

	@Transient
	public Integer getIsValid() {
		return isValid;
	}
	@Transient
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	@Column(name="ad_resolution")
	public Integer getResolution() {
		return resolution;
	}

	public void setResolution(Integer resolution) {
		this.resolution = resolution;
	}

	@Column(name="ad_is_sd")
	public Integer getIsSd() {
		return isSd;
	}

	public void setIsSd(Integer isSd) {
		this.isSd = isSd;
	}

	@Column(name="ad_is_hd")
	public Integer getIsHd() {
		return isHd;
	}

	public void setIsHd(Integer isHd) {
		this.isHd = isHd;
	}

	@Transient
	public String getIsExpired() {
		return isExpired;
	}

	@Transient
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}


}