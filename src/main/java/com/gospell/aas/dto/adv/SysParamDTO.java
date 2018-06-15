package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SysParamDTO {

	@JsonProperty("bpTT")
	private String bpTT;// 图片总时长

	@JsonProperty("bpTS")
	private String bpTS;// 单图片时长

	@JsonProperty("psTs")
	private String psTs;// 图片最少展示时长

	public String getBpTT() {
		return bpTT;
	}

	public void setBpTT(String bpTT) {
		this.bpTT = bpTT;
	}

	public String getBpTS() {
		return bpTS;
	}

	public void setBpTS(String bpTS) {
		this.bpTS = bpTS;
	}

	public String getPsTs() {
		return psTs;
	}

	public void setPsTs(String psTs) {
		this.psTs = psTs;
	}
}
