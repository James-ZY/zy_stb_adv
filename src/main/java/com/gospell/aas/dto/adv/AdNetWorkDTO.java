package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdNetWorkDTO {
	
	@JsonProperty("networkId")
	private String networkId;// 广告发送器ID

	@JsonProperty("networkName")
	private String networkName;// 广告发送器名称

	@JsonIgnore
	private String cpu;// cpu状态

	@JsonIgnore
	private String memory; // 内存状态

	@JsonProperty("adOperatorsId")
	private String adOperatorsId;// 所属电视运营商id

	@JsonProperty("password")
	private String password;

	@JsonProperty("port")
	private String port;// 端口号

	@JsonProperty("wayEncryption")
	private String wayEncryption;// 传输加密方式

	@JsonProperty("secretKey")
	private String secretKey;// 密钥

	@JsonProperty("ip")
	private String ip;// IP地址
	
	@JsonProperty("validDate")
	private String validDate;// 有效时间，需要自己转换成Date，格式默认是yyyy-MM-dd
	
	@JsonProperty("onlineStatus")
	private String onlineStatus;//发送器在线状态
	
	
	@JsonProperty("isControlAllAD")
	private String isControlAllAD;//发送器是否控制本级复用器所有频道的广告
 
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

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getAdOperatorsId() {
		return adOperatorsId;
	}

	public void setAdOperatorsId(String adOperatorsId) {
		this.adOperatorsId = adOperatorsId;
	}

 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getWayEncryption() {
		return wayEncryption;
	}

	public void setWayEncryption(String wayEncryption) {
		this.wayEncryption = wayEncryption;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getIsControlAllAD() {
		return isControlAllAD;
	}

	public void setIsControlAllAD(String isControlAllAD) {
		this.isControlAllAD = isControlAllAD;
	}


	 
}
