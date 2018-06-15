package com.gospell.aas.common.utils.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDTO {

	@JsonProperty("roleId")
	private String roleId;
	
	@JsonProperty("roleName")
	private String roleName;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
}
