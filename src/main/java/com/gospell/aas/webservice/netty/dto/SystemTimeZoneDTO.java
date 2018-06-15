package com.gospell.aas.webservice.netty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class SystemTimeZoneDTO {
	
	@JsonProperty(value="timeZone")
	private String timeZone;//时区格式是8.00，5.45，-3.50，-3.45
	
	@JsonProperty(value="date")
	private String date;//当前时间，格式yyyy-MM-dd

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
 public static void main(String[] args) {
	System.out.println(JsonMapper.toJsonString(new SystemTimeZoneDTO()));
}

}
