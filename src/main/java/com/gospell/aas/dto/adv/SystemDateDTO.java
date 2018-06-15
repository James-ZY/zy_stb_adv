package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemDateDTO {
	
	@JsonProperty("time")
	private long time;
	
	@JsonProperty("zoneFormat")
	private String zoneFormat;//格式化时区，比如UTC+08:00
	
	@JsonProperty("timeZone")
	private String timeZone;//时区的值，比如8、5.5

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

 

	public String getZoneFormat() {
		return zoneFormat;
	}

	public void setZoneFormat(String zoneFormat) {
		this.zoneFormat = zoneFormat;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	

}
