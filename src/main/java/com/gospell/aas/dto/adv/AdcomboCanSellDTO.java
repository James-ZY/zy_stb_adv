package com.gospell.aas.dto.adv;

 
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdcomboCanSellDTO {
	
	@JsonProperty("id")
	private String id;//套餐ID
	
	@JsonProperty("comboName")
	private String comboName;//套餐名称
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	 
 
	

	
}
