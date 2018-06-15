package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

 

/**
 * 修改销售记录javabean
 * @author Administrator
 *
 */
public class AdSelAlterlDTO {

	@JsonProperty("id")
	private String id;//销售记录ID
	 
	@JsonProperty("newComboId")
	private String newComboId;// 新广告套餐ID（如果没变，保存原来的值）
	
	@JsonProperty("newAdvertiserId")
	private String newAdvertiserId;// 广告商如果没变，保存原来的值）
	
	@JsonProperty("newStartDate")
	private String newStartDate; // 销售开始时间（如果没变，保存原来的值）
	
	@JsonProperty("newEndDate")
	private String newEndDate; // 销售结束时间（如果没变，保存原来的值）
	
	@JsonProperty("oldComboId")
	private String oldComboId;// 广告套餐Id(未修改之前的值)
	
	@JsonProperty("oldAdvertiserId")
	private String oldAdvertiserId;// 广告商ID(未修改之前的值)
	
	@JsonProperty("oldStartDate")
	private String oldStartDate; // 销售开始时间(未修改之前的值)
	
	@JsonProperty("oldEndDate")
	private String oldEndDate; // 销售结束时间(未修改之前的值)
	
	@JsonProperty("isHaveAdv")
	private Boolean isHaveAdv;//原来下面是否有广告(false为有广告 true为没有广告)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewComboId() {
		return newComboId;
	}

	public void setNewComboId(String newComboId) {
		this.newComboId = newComboId;
	}

	public String getNewAdvertiserId() {
		return newAdvertiserId;
	}

	public void setNewAdvertiserId(String newAdvertiserId) {
		this.newAdvertiserId = newAdvertiserId;
	}

	public String getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(String newStartDate) {
		this.newStartDate = newStartDate;
	}

	public String getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(String newEndDate) {
		this.newEndDate = newEndDate;
	}

	public String getOldComboId() {
		return oldComboId;
	}

	public void setOldComboId(String oldComboId) {
		this.oldComboId = oldComboId;
	}

	public String getOldAdvertiserId() {
		return oldAdvertiserId;
	}

	public void setOldAdvertiserId(String oldAdvertiserId) {
		this.oldAdvertiserId = oldAdvertiserId;
	}

	public String getOldStartDate() {
		return oldStartDate;
	}

	public void setOldStartDate(String oldStartDate) {
		this.oldStartDate = oldStartDate;
	}

	public String getOldEndDate() {
		return oldEndDate;
	}

	public void setOldEndDate(String oldEndDate) {
		this.oldEndDate = oldEndDate;
	}

	public Boolean getIsHaveAdv() {
		return isHaveAdv;
	}

	public void setIsHaveAdv(Boolean isHaveAdv) {
		this.isHaveAdv = isHaveAdv;
	}
	
	
	
}
