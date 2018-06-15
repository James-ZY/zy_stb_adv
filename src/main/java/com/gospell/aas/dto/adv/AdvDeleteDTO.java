package com.gospell.aas.dto.adv;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class AdvDeleteDTO {

	@JsonProperty(value = "adID")
	private String adId;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public static void main(String[] args) {
		List<AdvDeleteDTO> list = new ArrayList<AdvDeleteDTO>();
		list.add(new AdvDeleteDTO());
		list.add(new AdvDeleteDTO());
		System.out.println(JsonMapper.toJsonString(list));
	}
}
