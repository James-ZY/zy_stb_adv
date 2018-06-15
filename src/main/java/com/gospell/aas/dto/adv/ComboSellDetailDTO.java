package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 套餐销售的详细情况
 * @author Administrator
 *
 */
public class ComboSellDetailDTO {
	
	@JsonProperty("totalDay")
	private  Integer totalDay;//总时间
	
	@JsonProperty("sellDay")
	private  Integer sellDay;//销售总时间
	
	@JsonProperty("comboId")
	private String comboId;//套餐ID
	
	@JsonProperty("comboName")
	private String comboName;//套餐名称
	
	@JsonProperty("merchantList")
	private List<ComboAdvtiserSellDTO> merchantList;//广告商具体的销售参数

	public Integer getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(Integer totalDay) {
		this.totalDay = totalDay;
	}

	public Integer getSellDay() {
		return sellDay;
	}

	public void setSellDay(Integer sellDay) {
		this.sellDay = sellDay;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public List<ComboAdvtiserSellDTO> getMerchantList() {
		return merchantList;
	}

	public void setMerchantList(List<ComboAdvtiserSellDTO> merchantList) {
		this.merchantList = merchantList;
	}
	
	

}
