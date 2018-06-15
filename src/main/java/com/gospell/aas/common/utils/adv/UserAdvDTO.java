package com.gospell.aas.common.utils.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

public class UserAdvDTO {
	
	@JsonProperty("typeId")
	private String typeId;
	
	@JsonProperty("typeName")
	private  String typeName;
	
	@JsonProperty("userIsAdv")
	private Boolean userIsAdv;
	
	@JsonProperty("isSetAdmin")
	private Boolean isSetAdmin;
	
	@JsonProperty("advertiserList")
	private List<AdvertiserDTO> advertiserList = Lists.newArrayList();
	
	@JsonProperty("roleList")
	private List<RoleDTO> roleList = Lists.newArrayList();

 

	public List<AdvertiserDTO> getAdvertiserList() {
		return advertiserList;
	}

	public void setAdvertiserList(List<AdvertiserDTO> advertiserList) {
		this.advertiserList = advertiserList;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Boolean getUserIsAdv() {
		return userIsAdv;
	}

	public void setUserIsAdv(Boolean userIsAdv) {
		this.userIsAdv = userIsAdv;
	}

	public Boolean getIsSetAdmin() {
		return isSetAdmin;
	}

	public void setIsSetAdmin(Boolean isSetAdmin) {
		this.isSetAdmin = isSetAdmin;
	}

	public List<RoleDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleDTO> roleList) {
		this.roleList = roleList;
	}
	
	

}
