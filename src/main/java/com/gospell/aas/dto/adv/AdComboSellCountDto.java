package com.gospell.aas.dto.adv;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Advertiser;

/**
 * 用于统计一段时间内售出的广告套餐个数
 * 
 * @author Administrator
 * 
 */
public class AdComboSellCountDto {

	@JsonProperty("sellDate")
	private String sellDate;// 销售时间

	@JsonProperty("sellCount")
	private Integer sellCount;// 销售个数

	@JsonIgnore
	private Advertiser advertiser;// 广告商
	
	@JsonIgnore
	private AdType adType;// 套餐广告类型
	
	@JsonIgnore
	private Date startDate;// 查询的开始时间
	
	@JsonIgnore
	private Date endDate;// 查询的结束时间

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
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
}