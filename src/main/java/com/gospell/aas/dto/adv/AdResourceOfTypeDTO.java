package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdResourceOfTypeDTO {
	

	@JsonProperty(value="fileMinSize")
	private Float fileMinSize;//文件最小大小
	
	@JsonProperty(value="fileMaxSize")
	private Float fileMaxSize;//文件最大大小
	
	@JsonProperty(value="widthMin")
	private Integer widthMin;//最小宽
	
	@JsonProperty(value="widthMax")
	private Integer widthMax;//最大高
	
	@JsonProperty(value="highMin")
	private Integer highMin;//最小高
	
	@JsonProperty(value="highMax")
	private Integer highMax;//最大高
	
	@JsonProperty(value="frameMin")
	private Integer frameMin;//最小帧
	
	@JsonProperty(value="frameMax")
	private Integer frameMax;//最大帧
	
	@JsonProperty(value="rateMin")
	private Integer rateMin;//最小码流
	
	@JsonProperty(value="rateMax")
	private Integer rateMax;//最大码流
	
	@JsonProperty(value="flag")
	private Integer flag;//（0标清 1高清）
	
	@JsonProperty(value="format")
	private String format;//图片格式或者视频格式
	
	@JsonProperty(value="isFixed")
	private Integer isFixed;//是否固定值(0表示否 1表示是)
	
	@JsonProperty(value="status")
	private Integer status;//是否是视频还是图片(0表示图片 1表示视频)

	public Float getFileMinSize() {
		return fileMinSize;
	}

	public void setFileMinSize(Float fileMinSize) {
		this.fileMinSize = fileMinSize;
	}

	public Float getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(Float fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	public Integer getWidthMin() {
		return widthMin;
	}

	public void setWidthMin(Integer widthMin) {
		this.widthMin = widthMin;
	}

	public Integer getWidthMax() {
		return widthMax;
	}

	public void setWidthMax(Integer widthMax) {
		this.widthMax = widthMax;
	}

	public Integer getHighMin() {
		return highMin;
	}

	public void setHighMin(Integer highMin) {
		this.highMin = highMin;
	}

	public Integer getHighMax() {
		return highMax;
	}

	public void setHighMax(Integer highMax) {
		this.highMax = highMax;
	}

	public Integer getFrameMin() {
		return frameMin;
	}

	public void setFrameMin(Integer frameMin) {
		this.frameMin = frameMin;
	}

	public Integer getFrameMax() {
		return frameMax;
	}

	public void setFrameMax(Integer frameMax) {
		this.frameMax = frameMax;
	}

	public Integer getRateMin() {
		return rateMin;
	}

	public void setRateMin(Integer rateMin) {
		this.rateMin = rateMin;
	}

	public Integer getRateMax() {
		return rateMax;
	}

	public void setRateMax(Integer rateMax) {
		this.rateMax = rateMax;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Integer isFixed) {
		this.isFixed = isFixed;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
