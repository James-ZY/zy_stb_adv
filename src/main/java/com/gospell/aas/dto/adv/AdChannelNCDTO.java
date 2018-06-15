package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdChannelNCDTO {

	@JsonProperty("sdMaxNC")
	private Integer sdMaxNC;// 高清占用内存

	@JsonProperty("hdMaxNC")
	private Integer hdMaxNC;// 标清占用内存

	public Integer getSdMaxNC() {
		return sdMaxNC;
	}

	public void setSdMaxNC(Integer sdMaxNC) {
		this.sdMaxNC = sdMaxNC;
	}

	public Integer getHdMaxNC() {
		return hdMaxNC;
	}

	public void setHdMaxNC(Integer hdMaxNC) {
		this.hdMaxNC = hdMaxNC;
	}

}
