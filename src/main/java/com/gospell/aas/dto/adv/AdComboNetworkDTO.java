package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdComboNetworkDTO {

	@JsonProperty(value="comboId")
	private String comboId;
	
	@JsonProperty(value="comboName")
	private String comboName;
	
	@JsonProperty(value="comboArea")
	private String comboArea;

	@JsonProperty(value="networkId")
	private String networkId;

	@JsonProperty(value="networkName")
	private String networkName;

	@JsonProperty(value="networkArea")
	private String networkArea;

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

	public String getComboArea() {
		return comboArea;
	}

	public void setComboArea(String comboArea) {
		this.comboArea = comboArea;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getNetworkArea() {
		return networkArea;
	}

	public void setNetworkArea(String networkArea) {
		this.networkArea = networkArea;
	}
}
