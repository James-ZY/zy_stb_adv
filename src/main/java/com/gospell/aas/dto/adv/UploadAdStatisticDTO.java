package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class UploadAdStatisticDTO {

	@JsonProperty("uploadData")
	private String uploadData;
	
	@JsonProperty("system")
	private Integer system;//0表示安卓 1表示IOS 2表示其他
	
	@JsonProperty("userCode")
	private String userCode;//如果传了数据，表示boss那边的机顶盒用户，以后通过这个usercode可以查询到对应的信息，没有就为null

	public String getUploadData() {
		return uploadData;
	}

	public void setUploadData(String uploadData) {
		this.uploadData = uploadData;
	}
	
	
	
	public Integer getSystem() {
		return system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public static void main(String[] args) {
		System.out.println(JsonMapper.toJsonString(new UploadAdStatisticDTO()));
	}
	
}
