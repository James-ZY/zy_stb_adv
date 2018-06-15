/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.gospell.aas.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.excel.annotation.ExcelField;



/**
 * 字典Entity
 * @author free lance
 * @version 2013-05-15
 */
@Entity
@Table(name = "sys_dict")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dict extends IdEntity<Dict> {

	private static final long serialVersionUID = 1L;
	private String label;	// 标签名
	private String value;	// 数据值
	private String type;	// 类型
	private String description;// 描述
	private Integer sort;	// 排序
	
	private Integer dictLocale;//0中文 1英文
	
	
	public static final Integer zh_CN = 0;//中文
	public static final Integer en_US = 1;//英文

	public Dict() {
		super();
	}
	
	public Dict(String id) {
		this();
		this.id = id;
	}

	@Length(min=1, max=100)
	@ExcelField(title="字典标签", align=2, sort=2,required=1,max=100)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Length(min=1, max=100)
	@ExcelField(title="字典键值", align=2, sort=1,required=1,max=100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Length(min=1, max=100)
	@ExcelField(title="字典类型", align=2, sort=3,required=1,max=100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=400)
	@ExcelField(title="字典描述", align=2, sort=4,required=1,max=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@ExcelField(title="字典排序", align=2, sort=5,required=1,max=100)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@NotNull
	@ExcelField(title="字典语言", align=2, sort=5,required=1,max=100)
	public Integer getDictLocale() {
		return dictLocale;
	}

	public void setDictLocale(Integer dictLocale) {
		this.dictLocale = dictLocale;
	}

 
	
	
	
}