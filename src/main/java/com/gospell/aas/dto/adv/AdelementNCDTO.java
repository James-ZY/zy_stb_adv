package com.gospell.aas.dto.adv;

import java.util.Date;

/**
 * 获取查询时间段已发布的广告
 * 
 * @author Administrator
 * 
 */
public class AdelementNCDTO {

	private String adId;// 广告ID

	private String adName;// 广告名称
	
	private Integer status;// 0待审核 1 认领 2审核通过 -1 审核未通过 3投放中 4投放结束 
	private Date startDate;// 广告展示的开始时间
	private Date endDate;// 广告展示的结束时间
	
	private Integer startHour;// 频道相关的套餐的开始的小时，比如早上1点

	private Integer startMinutes;// 频道相关的套餐的开始的分钟，比如早上5分

	private Integer startSecond;// 频道相关的套餐的开始的秒，比如早上5秒

	private Integer endHour;// 频道相关的套餐的结束小时，比如早上1点

	private Integer endMinutes;// 频道相关的套餐结束分钟，比如早上5分

	private Integer endSecond;// 频道相关的套餐的结束的秒，比如早上5秒
	
	private Integer startTime;// 频道相关的套餐结束分钟，比如早上5分

	private Integer endTime;// 频道相关的套餐的结束的秒，比如早上5秒
	
	private Integer serviceId;// 广告所占用通道id
	
	private String isMainChannel;// 是否是主通道
	
	private String isVideoChannel;// 是否是视频通道
	
	private String fileFormat;//素材类型
	
	private Integer fileSize;// 广告所占用素材大小
	
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(Integer startMinutes) {
		this.startMinutes = startMinutes;
	}

	public Integer getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(Integer startSecond) {
		this.startSecond = startSecond;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinutes() {
		return endMinutes;
	}

	public void setEndMinutes(Integer endMinutes) {
		this.endMinutes = endMinutes;
	}

	public Integer getEndSecond() {
		return endSecond;
	}

	public void setEndSecond(Integer endSecond) {
		this.endSecond = endSecond;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getIsMainChannel() {
		return isMainChannel;
	}

	public void setIsMainChannel(String isMainChannel) {
		this.isMainChannel = isMainChannel;
	}

	public String getIsVideoChannel() {
		return isVideoChannel;
	}

	public void setIsVideoChannel(String isVideoChannel) {
		this.isVideoChannel = isVideoChannel;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}


}
