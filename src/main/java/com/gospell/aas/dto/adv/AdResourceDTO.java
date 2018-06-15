package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdResourceDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value="path")
	private String path;//文件路径
	
	@JsonProperty(value="vedioImagePath")
	private String vedioImagePath;//如果是视频，返回视频的截图
		
	@JsonProperty(value="id")
	private String id;//资源文件ID
	
	@JsonProperty(value="order")
	private Integer order;//开机画面展示顺序
	
	@JsonProperty(value="time")
	private Integer time;//开机画面展示时间
	
	@JsonProperty(value="size")
	private Integer size;//素材大小

	@JsonProperty(value="rollText")
	private String rollText;//滚动素材文字

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVedioImagePath() {
		return vedioImagePath;
	}

	public void setVedioImagePath(String vedioImagePath) {
		this.vedioImagePath = vedioImagePath;
	}

	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getRollText() {
		return rollText;
	}

	public void setRollText(String rollText) {
		this.rollText = rollText;
	}

}
