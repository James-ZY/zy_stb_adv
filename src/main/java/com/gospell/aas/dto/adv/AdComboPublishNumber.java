package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.entity.adv.AdCombo;
import com.gospell.aas.entity.adv.AdType;
import com.gospell.aas.entity.adv.Advertiser;

/**
 * 用于统计套餐下广告发布和未发布的个数
 * @author Administrator
 *
 */
public class AdComboPublishNumber extends BaseEntity<AdComboPublishNumber> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("adCombo")
	private AdCombo adCombo;// 广告套餐ID

	@JsonProperty("advertiser")
	private Advertiser advertiser;// 广告商
	
	@JsonProperty("adType")
	private AdType adType;// 套餐广告类型

	@JsonProperty("allCount")
	private Integer allCount;// 该套餐总的广告个数

	@JsonProperty("publishCount")
	private Integer publishCount;// 发布广告的个数

	@JsonProperty("unPublishCount")
	private Integer unPublishCount;// 未发布广告的个数


	public AdCombo getAdCombo() {
		return adCombo;
	}

	public void setAdCombo(AdCombo adCombo) {
		this.adCombo = adCombo;
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

	public Integer getAllCount() {
		return allCount;
	}

	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
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

}
