package com.gospell.aas.dto.adv;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

/**
 * 套餐销售页面传的参数
 * @author Administrator
 *
 */
public class AdComboCanSellQueryDTO {
	
	@JsonProperty("typeId")
	private  String typeId;
	
	@JsonProperty("networkIdList")
	private  List<String> networkIdList;
	
	@JsonProperty("netWork")
	private  String netWork;
	
	@JsonProperty("channel")
	private  String channel;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<String> getNetworkIdList() {
		return networkIdList;
	}

	public void setNetworkIdList(List<String> networkIdList) {
		this.networkIdList = networkIdList;
	}
	
	
	public String getNetWork() {
		return netWork;
	}

	public void setNetWork(String netWork) {
		this.netWork = netWork;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public static void main(String[] args) {
		AdComboCanSellQueryDTO d = new AdComboCanSellQueryDTO();
		d.setTypeId("1");
		List<String> list = new ArrayList<String>();
		list.add("12346");
		list.add("123465");
		d.setNetworkIdList(list);
		System.out.println(JsonMapper.toJsonString(d));
	}

}
