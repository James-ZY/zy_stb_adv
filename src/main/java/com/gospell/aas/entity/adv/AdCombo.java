package com.gospell.aas.entity.adv;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.ApplicationContextHelper;
import com.gospell.aas.common.utils.DictUtils;
import com.gospell.aas.common.utils.excel.annotation.ExcelField;

/**
 * 用户Entity
 * 
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_combo")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdCombo extends IdEntity<AdCombo> {

	private static final long serialVersionUID = 1L;

	private String adcomboId;// 套餐ID
	private String comboName;// 套餐名称
	private Integer isFlag;// 是否跟频道相关（0跟频道无关，1跟频道相关）

	private Integer status;// 套餐状态（0未生效，1可运营，2已运营）
	// private Integer weekStart;//日期规划,区别每周或者其他（比如法定假日）的不同的天的播出开始时间
	// private Integer weekEnd;//日期规划,区别每周或者其他（比如法定假日）的不同的天的播出的结束时间
	private AdType adType;// 广告类型
	private Integer showTime;// 单次展示时间（插屏图片和滚动广告）
	private Integer intervalTime ;// 间隔时间（插屏图片和滚动广告）
	private Integer showCount;// 展示次数（插屏图片和滚动广告）
	private Integer pictureInterval ;//每轮图片显示间隔时间（滚动广告）
	private Integer pictureTimes;// 每轮图片显示次数（滚动广告）

	private Integer startHour;// 频道相关的套餐的开始的小时，比如早上1点

	private Integer startMinutes;// 频道相关的套餐的开始的分钟，比如早上5分

	private Integer startSecond;// 频道相关的套餐的开始的秒，比如早上5秒

	private Integer endHour;// 频道相关的套餐的结束小时，比如早上1点

	private Integer endMinutes;// 频道相关的套餐结束分钟，比如早上5分

	private Integer endSecond;// 频道相关的套餐的结束的秒，比如早上5秒

	private String week;

	private Integer isPut;// 是否投放（0）

	private Date validStartTime;// 套餐生效开始时间

	private Date validEndTime;// 套餐生效结束时间
	
	private Integer isValid;//是否失效（0失效 1生效）

	private AdRange hdRange;//高清坐标范围

	private AdRange sdRange;//标清坐标范围

	private AdTrack hdTrack;//高清轨迹模板

	private AdTrack sdTrack;//标清轨迹模板

	private Integer sendMode; //广告发送模式（1普通 2区域）

	private Integer trackMode; //广告轨迹模式（1普通 2普通）

	private List<AdComboDistrict> adComboCategorys;// 区域
	private String area;// 区域
	private String selArea;//选择的区域信息 Id

	@Transient
	private Integer sendModeYG;
	
	@Transient
	private String selAllArea;//包含子类的区域
	
	@Transient
	private Integer oldStatus;// 套餐状态（0未生效，1可运营，2已运营）
	@Transient
	private String adTypeName; //广告类型名称
	@Transient
	private String startTime; // 广告在每天播放的开始时刻（比如周一早上8点8分8秒)，数据库保存格式为08:00:08
	@Transient
	private String endTime; // 广告在每天播放的结束时刻（比如周一早上8点8分10秒）

	@Transient
	private String timeScale;

	@Transient
	private Integer validDay;

	@Transient
	private Integer sellDay;

	@Transient
	private Date queryStartDate;

	@Transient
	private Date queryEndDate;
	
	@Transient
	private String channleName;
	
	@Transient
	private String channelId;
	
	@Transient
	private String networkId;
	
	@Transient
	private String isExpired;
	
	@Transient
	private String isFlagName; //导出是否与频道相关

	@Transient
	private String statusName; //导出广告套餐状态
	
	@Transient
	private String isValidName; //导出是否有效

	private List<AdNetwork> networkList = Lists.newArrayList();// 包含的广告发送器
	private List<Adelement> adelemetList = Lists.newArrayList();// 包含的广告
	private List<AdChannel> channelList = Lists.newArrayList();// 包含的频道
	private List<Advertiser> advertiserList = Lists.newArrayList();// 包含的广告商

	public static final Integer ADCOMOBO_DENY_STATUS = 0;// 未生效
	public static final Integer ADCOMOBO_CAN_STATUS = 1;// 可运营
	public static final Integer ADCOMOBO_ALREADY_STATUS = 2;// 已运营

	public static final Integer ADCOMOBO_CHANNEL_ISFLAG = 1;// 跟频道有关
	public static final Integer ADCOMOBO_NETWORK_ISFLAG = 0;// 跟频道无关

	public static final Integer ADCOMOBO_NO_PUT = 0;// 没有投放
	public static final Integer ADCOMOBO_YES_PUT = 1;// 已经投放
	
	public static final Integer ADCOMOBO_YES_VALID = 1;// 有效
	public static final Integer ADCOMOBO_NO_VALID = 0;// 生效
 
	public AdCombo() {
		super();
	}

	public AdCombo(String id) {
		this();
		this.id = id;
	}

	@Column(name = "ad_combo_id")
	public String getAdcomboId() {
		return adcomboId;
	}

	public void setAdcomboId(String adcomboId) {
		this.adcomboId = adcomboId;
	}

	@Column(name = "ad_combo_name", nullable = false)
    @ExcelField(title="combo.name", align=2, sort=1,required=1,max=20)
	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	@Column(name = "ad_is_flag", nullable = false)
	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
	}

	@Transient
	@ExcelField(title="combo.playstart", align=2, sort=6,required=1,max=20)
	public String getStartTime() {
		if (null != startHour && null != startMinutes && null != startSecond) {

			return com.gospell.aas.common.utils.StringUtils.getTime(startHour,
					startMinutes, startSecond);
		} else {
			return "";
		}
	}
 

	@Transient
	@ExcelField(title="combo.playend", align=2, sort=7,required=1,max=20)
	public String getEndTime() {
		if (null != endHour && null != endMinutes && null != endSecond) {
			return com.gospell.aas.common.utils.StringUtils.getTime(endHour,
					endMinutes, endSecond);
		} else {
			return "";
		}
	}

 

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	//
	//
	// @Column(name="week_start")
	// public Integer getWeekStart() {
	// return weekStart;
	// }
	//
	// public void setWeekStart(Integer weekStart) {
	// this.weekStart = weekStart;
	// }
	//
	// @Column(name="week_end")
	// public Integer getWeekEnd() {
	// return weekEnd;
	// }
	//
	// public void setWeekEnd(Integer weekEnd) {
	// this.weekEnd = weekEnd;
	// }

	@ManyToOne
	@JoinColumn(name = "ad_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "广告类型不能为空")
	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_combo_network", joinColumns = { @JoinColumn(name = "ad_combo_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_network_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdNetwork> getNetworkList() {
		return networkList;
	}

	public void setNetworkList(List<AdNetwork> networkList) {
		this.networkList = networkList;
	}

	@OneToMany(mappedBy = "adCombo", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Adelement> getAdelemetList() {
		return adelemetList;
	}

	public void setAdelemetList(List<Adelement> adelemetList) {
		this.adelemetList = adelemetList;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_combo_channel", joinColumns = { @JoinColumn(name = "ad_combo_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_channel_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdChannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<AdChannel> channelList) {
		this.channelList = channelList;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_advertiser_combo", joinColumns = { @JoinColumn(name = "ad_combo_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_advertiser_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<Advertiser> getAdvertiserList() {
		return advertiserList;
	}

	public void setAdvertiserList(List<Advertiser> advertiserList) {
		this.advertiserList = advertiserList;
	}

	@Column(name = "show_time")
	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	@Column(name = "interval_time")
	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Column(name = "show_count")
	public Integer getShowCount() {
		return showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	@Column(name = "picture_interval")
	public Integer getPictureInterval() {
		return pictureInterval;
	}

	public void setPictureInterval(Integer pictureInterval) {
		this.pictureInterval = pictureInterval;
	}

	@Column(name = "picture_times")
	public Integer getPictureTimes() {
		return pictureTimes;
	}

	public void setPictureTimes(Integer pictureTimes) {
		this.pictureTimes = pictureTimes;
	}

	@Column(name = "week")
	public String getWeek() {
		if (null == week || week.length() == 0) {
			List<String> list = Lists.newArrayList();
			for (int i = 0; i < 7; i++) {
				list.add(String.valueOf(i + 1));
			}
			week = StringUtils.join(list, ",");
		}
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Transient
	public List<String> getNetworkIdList() {
		List<String> networkIdList = Lists.newArrayList();
		for (AdNetwork network : networkList) {
			networkIdList.add(network.getId());
		}
		return networkIdList;
	}

	@Transient
	public void setNetworkIdList(List<String> networkIdList) {
		networkList = Lists.newArrayList();
		for (String networkId : networkIdList) {
			AdNetwork network = new AdNetwork();
			network.setId(networkId);
			networkList.add(network);
		}
	}

	@Transient
	public String getNetworkIds() {
		List<String> networkIdList = Lists.newArrayList();
		for (AdNetwork network : networkList) {
			networkIdList.add(network.getId());
		}
		return StringUtils.join(networkIdList, ",");
	}

	@Transient
	public void setNetworkIds(String networkIds) {
		networkList = Lists.newArrayList();
		if (networkIds != null) {
			String[] ids = StringUtils.split(networkIds, ",");
			for (String networkId : ids) {
				AdNetwork network = new AdNetwork();
				network.setId(networkId);
				networkList.add(network);
			}
		}
	}

	@Transient
	public String getChannelIds() {
		List<String> adchannelList = Lists.newArrayList();
		for (AdChannel channel : channelList) {
			adchannelList.add(channel.getId());
		}
		return StringUtils.join(adchannelList, ",");
	}
	

	@Transient
	public void setChannelIds(String channelIds) {
		channelList = Lists.newArrayList();
		if (channelIds != null) {
			String[] ids = StringUtils.split(channelIds, ",");
			for (String channelId : ids) {
				AdChannel channel = new AdChannel();
				channel.setId(channelId);
				channelList.add(channel);
			}
		}
	}

	@Transient
	public String getTypeId() {
		if (null != adType && null != isFlag
				&& AdCombo.ADCOMOBO_NETWORK_ISFLAG.equals(isFlag)) {
			return adType.getId();
		} else {
			return null;
		}
	}

	@Transient
	public void setTypeId(String typeId) {
		if (StringUtils.isNotEmpty(typeId)) {
			if (null == adType) {
				adType = new AdType();
			}
			adType.setId(typeId);
		}
	}
	
	@Transient
	@ExcelField(title="adv.type", align=2, sort=2,required=1,max=20)
	public String getAdTypeName(){
		return this.adType.getTypeName();
	}
	
	@Transient
	public void setAdTypeName(String adTypeName){
		this.adTypeName = adTypeName;
	}

	@Column(name = "is_put")
	public Integer getIsPut() {
		return isPut;
	}

	public void setIsPut(Integer isPut) {
		this.isPut = isPut;
	}

	@Column(name = "valid_start_time")
	@Temporal(TemporalType.DATE)
	@ExcelField(title="combo.startdate", align=2, sort=4,required=1,max=20)
	public Date getValidStartTime() {
		return validStartTime;
	}

	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}

	@Column(name = "valid_end_time")
	@Temporal(TemporalType.TIMESTAMP)
	@ExcelField(title="combo.enddate", align=2, sort=5,required=1,max=20)
	public Date getValidEndTime() {
		return validEndTime;
	}

	@Column(name = "start_hour")
	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	@Column(name = "start_minutes")
	public Integer getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(Integer startMinutes) {
		this.startMinutes = startMinutes;
	}

	@Column(name = "start_second")
	public Integer getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(Integer startSecond) {
		this.startSecond = startSecond;
	}

	@Column(name = "end_hour")
	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	@Column(name = "end_minutes")
	public Integer getEndMinutes() {
		return endMinutes;
	}

	public void setEndMinutes(Integer endMinutes) {
		this.endMinutes = endMinutes;
	}

	@Column(name = "end_second")
	public Integer getEndSecond() {
		return endSecond;
	}
	
	
	@Column(name="is_valid")
	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	@ManyToOne
	@JoinColumn(name = "hdrange_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdRange getHdRange() {
		return hdRange;
	}

	public void setHdRange(AdRange hdRange) {
		this.hdRange = hdRange;
	}
	
	@ManyToOne
	@JoinColumn(name = "sdrange_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdRange getSdRange() {
		return sdRange;
	}

	public void setSdRange(AdRange sdRange) {
		this.sdRange = sdRange;
	}

	@ManyToOne
	@JoinColumn(name = "hdtrack_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdTrack getHdTrack() {
		return hdTrack;
	}

	public void setHdTrack(AdTrack hdTrack) {
		this.hdTrack = hdTrack;
	}

	@ManyToOne
	@JoinColumn(name = "sdtrack_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdTrack getSdTrack() {
		return sdTrack;
	}

	public void setSdTrack(AdTrack sdTrack) {
		this.sdTrack = sdTrack;
	}

	@Column(name = "send_mode")
	public Integer getSendMode() {
		return sendMode;
	}

	public void setSendMode(Integer sendMode) {
		this.sendMode = sendMode;
	}

	@Column(name = "track_mode")
	public Integer getTrackMode() {
		return trackMode;
	}

	public void setTrackMode(Integer trackMode) {
		this.trackMode = trackMode;
	}

	@Transient
	public Integer getSendModeYG() {
		return sendMode;
	}

	@Transient
	public void setSendModeYG(Integer sendModeYG) {
		if (null != sendModeYG && null != isFlag
				&& AdCombo.ADCOMOBO_CHANNEL_ISFLAG.equals(isFlag)) {
			sendMode= sendModeYG;
		}
	}
	
	@OneToMany(mappedBy = "adcombo")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdComboDistrict> getAdComboCategorys() {
		return adComboCategorys;
	}

	public void setAdComboCategorys(List<AdComboDistrict> adComboCategorys) {
		this.adComboCategorys = adComboCategorys;
	}

	@Column(name = "ad_area")
	@ExcelField(title = "operators.area", align = 2, sort = 7, required = 0, max = 200)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "ad_sel_area")
	public String getSelArea() {
		return selArea;
	}

	public void setSelArea(String selArea) {
		this.selArea = selArea;
	}
	
	@Transient
	public String getSelAllArea() {
		return selAllArea;
	}

	@Transient
	public void setSelAllArea(String selAllArea) {
		this.selAllArea = selAllArea;
	}
	
	@Transient
	public Integer getOldStatus() {
		return oldStatus;
	}
	@Transient
	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public void setEndSecond(Integer endSecond) {
		this.endSecond = endSecond;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	@Transient
	public String getTimeScale() {
		return timeScale;
	}

	@Transient
	public void setTimeScale(String timeScale) {
		this.timeScale = timeScale;
	}

	@Transient
	public Integer getValidDay() {
		return validDay;
	}

	@Transient
	public void setValidDay(Integer validDay) {
		this.validDay = validDay;
	}

	@Transient
	public Integer getSellDay() {
		return sellDay;
	}

	@Transient
	public void setSellDay(Integer sellDay) {
		this.sellDay = sellDay;
	}

	@Transient
	public Date getQueryStartDate() {
		return queryStartDate;
	}

	@Transient
	public void setQueryStartDate(Date queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	@Transient
	public Date getQueryEndDate() {
		return queryEndDate;
	}

	@Transient
	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	@Transient
	public String getChannleName() {
		return channleName;
	}

	@Transient
	public void setChannleName(String channleName) {
		this.channleName = channleName;
	}

	@Transient
	public String getChannelId() {
		return channelId;
	}

	@Transient
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Transient
	public String getNetworkId() {
		return networkId;
	}

	@Transient
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	@Transient
	public String getIsExpired() {
		return isExpired;
	}
	
	@Transient
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}
	@Transient
	@ExcelField(title="type.isflag", align=2, sort=3,required=1,max=20)
	public String getIsFlagName() {
		return DictUtils.getDictLabel(String.valueOf(this.isFlag), "adv_type_flag", "");
	}
	@Transient
	public void setIsFlagName(String isFlagName) {
		this.isFlagName = isFlagName;
	}
	@Transient
	@ExcelField(title="combo.status", align=2, sort=8,required=1,max=20)
	public String getStatusName() {
		return DictUtils.getDictLabel(String.valueOf(this.status), "combo_status", "");
	}
	@Transient
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Transient
	@ExcelField(title="combo.isvalid", align=2, sort=9,required=1,max=20)
	public String getIsValidName() {
		return ApplicationContextHelper.getMessage(this.isValid==1?"yes":"no");
	}
	@Transient
	public void setIsValidName(String isValidName) {
		this.isValidName = isValidName;
	}
	
	

}