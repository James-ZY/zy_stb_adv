package com.gospell.aas.dto.adv;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.entity.adv.AdType;

/**
 * 用于统计发布和未发布广告的套餐个数
 * @author Administrator
 *
 */
public class AdComboPublishNumberDto {

	@JsonProperty("adType")
	private AdType adType;// 广告类型

	@JsonProperty("publishCount")
	private Integer publishCount;// 发布广告的套餐的个数

	@JsonProperty("unPublishCount")
	private Integer unPublishCount;// 未发布广告的套餐的个数

	@JsonProperty("publishList")
	private List<AdComboPublishNumber> publishList;// 发布广告的套餐集合

	@JsonProperty("unPublishList")
	private List<AdComboPublishNumber> unPublishList;// 未发布广告的套餐集合
	
	@JsonProperty("startDate")
	private Date startDate;//查询的开始时间
	
	@JsonProperty("endDate")
	private Date endDate;//查询的结束时间

	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	public Integer getPublishCount() {
		return publishCount;
	}

	public void setPublishCount(Integer publishCount) {
		this.publishCount = publishCount;
	}

	public Integer getUnPublishCount() {
		return unPublishCount;
	}

	public void setUnPublishCount(Integer unPublishCount) {
		this.unPublishCount = unPublishCount;
	}

	public List<AdComboPublishNumber> getPublishList() {
		return publishList;
	}

	public void setPublishList(List<AdComboPublishNumber> publishList) {
		this.publishList = publishList;
	}

	public List<AdComboPublishNumber> getUnPublishList() {
		return unPublishList;
	}

	public void setUnPublishList(List<AdComboPublishNumber> unPublishList) {
		this.unPublishList = unPublishList;
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
