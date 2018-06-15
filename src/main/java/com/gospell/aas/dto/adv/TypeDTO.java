package com.gospell.aas.dto.adv;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

/**
 * 广告类型
 * @author Administrator
 *
 */
public class TypeDTO {
	
	@JsonProperty(value="adType")
	private String adType;//广告类型（0图片 1视频）
	
	@JsonProperty(value="comboId")
	private String comboId;//套餐ID

	@JsonProperty(value="comboFlag")
	private Integer comboFlag;// 是否跟频道相关（0跟频道无关，1跟频道相关）

	@JsonProperty(value="typeId")
	private String typeId;//广告类型Id
	
	@JsonProperty(value="isPosition")
	private String isPosition;//广告类型是否需要坐标（0需要 1不需要）
	
	@JsonProperty(value="typeName")
	private String typeName;//广告类型名称
	
	@JsonProperty(value="childList")
	private List<ChildAdTypeDTO> childList = Lists.newArrayList();

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public Integer getComboFlag() {
		return comboFlag;
	}

	public void setComboFlag(Integer comboFlag) {
		this.comboFlag = comboFlag;
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

	public List<ChildAdTypeDTO> getChildList() {
		return childList;
	}

	public void setChildList(List<ChildAdTypeDTO> childList) {
		this.childList = childList;
	}

	public String getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(String isPosition) {
		this.isPosition = isPosition;
	}
 
	
	

}
