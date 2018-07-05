package com.gospell.aas.dto.push;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushAdelementDTO {
	
	@JsonProperty("advName")
	private String advName;//广告名称
	
	@JsonProperty("id") 
	private String id;//广告唯一Id

	@JsonProperty("adId")
	private String adId;//广告Id

	@JsonProperty("advId")
	private String advId;//广告商ID

	@JsonProperty("startDate")
	private String startDate;//广告开始播放的日期

	@JsonProperty("endDate") 
	private String endDate;//广告结束播放的日期

	@JsonProperty("addText") 
	private String addText;//附加文本信息

	@JsonProperty("isFlag") 
	private Integer isFlag;//广告是否包含附加信息的标志(0未附件数据 1纯文本 2网站链接)

	@JsonProperty("sd_beginPointX") 
	private String sd_beginPointX;//标清开始x

	@JsonProperty("sd_beginPointY")
	private String sd_beginPointY;//标清开始y

	@JsonProperty("sd_endPointX") 
	private String sd_endPointX;//标清结束x（针对滚动和插屏广告）

	@JsonProperty("sd_endPointY") 
	private String sd_endPointY;;//标清结束y（针对滚动和插屏广告）

	@JsonProperty("hd_beginPointX")
	private String hd_beginPointX;//高清开始x

	@JsonProperty("hd_beginPointY")
	private String hd_beginPointY;//高清开始y

	@JsonProperty("hd_endPointX") 
	private String hd_endPointX;//高清结束x（针对滚动和插屏广告）

	@JsonProperty("hd_endPointY") 
	private String hd_endPointY;;//高清结束y（针对滚动和插屏广告）
	
	@JsonProperty("sd_velocity")
	private String sd_velocity ;//标清滚动广告移动速度
	
	@JsonProperty("hd_velocity")
	private String hd_velocity ;//高清滚动广告移动速度

	@JsonProperty("file_path") 
	private String file_path;//资源路径
	
	@JsonProperty("hd_file_path") 
	private String hd_file_path;//资源路径

	@JsonProperty("adcomboId") 
	private String adcomboId;//套餐Id

	@JsonProperty("desType")
	private String desType ;//显示方式 0纯文本 1二维码
	
	@JsonProperty("sonAdvType")
	private String sonAdvType;//子广告类型，现在默认为0
	
	@JsonProperty("playTime")
	private Integer playTime;//开机视频播放秒数
	
	@JsonProperty("advType")
	private String advType;//广告大类型，需要从套餐中读取
	
	@JsonProperty("sdShowParam")
	private String sdShowParam;//标清开机画面广告展示参数
	
	@JsonProperty("hdShowParam")
	private String hdShowParam;//高清开机画面广告展示参数
 
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdvId() {
		return advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAddText() {
		return addText;
	}

	public void setAddText(String addText) {
		this.addText = addText;
	}

	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
	}

	public String getSd_beginPointX() {
		return sd_beginPointX;
	}

	public void setSd_beginPointX(String sd_beginPointX) {
		this.sd_beginPointX = sd_beginPointX;
	}

	public String getSd_beginPointY() {
		return sd_beginPointY;
	}

	public void setSd_beginPointY(String sd_beginPointY) {
		this.sd_beginPointY = sd_beginPointY;
	}

	public String getSd_endPointX() {
		return sd_endPointX;
	}

	public void setSd_endPointX(String sd_endPointX) {
		this.sd_endPointX = sd_endPointX;
	}

	public String getSd_endPointY() {
		return sd_endPointY;
	}

	public void setSd_endPointY(String sd_endPointY) {
		this.sd_endPointY = sd_endPointY;
	}

	public String getHd_beginPointX() {
		return hd_beginPointX;
	}

	public void setHd_beginPointX(String hd_beginPointX) {
		this.hd_beginPointX = hd_beginPointX;
	}

	public String getHd_beginPointY() {
		return hd_beginPointY;
	}

	public void setHd_beginPointY(String hd_beginPointY) {
		this.hd_beginPointY = hd_beginPointY;
	}

	public String getHd_endPointX() {
		return hd_endPointX;
	}

	public void setHd_endPointX(String hd_endPointX) {
		this.hd_endPointX = hd_endPointX;
	}

	public String getHd_endPointY() {
		return hd_endPointY;
	}

	public void setHd_endPointY(String hd_endPointY) {
		this.hd_endPointY = hd_endPointY;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	 
	public String getAdcomboId() {
		return adcomboId;
	}

	public void setAdcomboId(String adcomboId) {
		this.adcomboId = adcomboId;
	}
	
	
	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}


	public String getSd_velocity() {
		return sd_velocity;
	}

	public void setSd_velocity(String sd_velocity) {
		this.sd_velocity = sd_velocity;
	}

	public String getHd_velocity() {
		return hd_velocity;
	}

	public void setHd_velocity(String hd_velocity) {
		this.hd_velocity = hd_velocity;
	}

	public String getDesType() {
		return desType;
	}

	public void setDesType(String desType) {
		this.desType = desType;
	}

	public String getSonAdvType() {
		return sonAdvType;
	}

	public void setSonAdvType(String sonAdvType) {
		this.sonAdvType = sonAdvType;
	}

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public Integer getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}

	public String getHd_file_path() {
		return hd_file_path;
	}

	public void setHd_file_path(String hd_file_path) {
		this.hd_file_path = hd_file_path;
	}

	public String getSdShowParam() {
		return sdShowParam;
	}

	public void setSdShowParam(String sdShowParam) {
		this.sdShowParam = sdShowParam;
	}

	public String getHdShowParam() {
		return hdShowParam;
	}
	
	public void setHdShowParam(String hdShowParam) {
		this.hdShowParam = hdShowParam;
	}
}
