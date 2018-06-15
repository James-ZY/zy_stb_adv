package com.gospell.aas.dto.adv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gospell.aas.common.mapper.JsonMapper;

/**
 * 区域分类选择插件json model
 * 
 * @author Administrator
 * 
 */
public class DistrictCategoryDTO {

	@JsonProperty(value = "list")
	private Map<String, Object> list; // 总区域信息表
	
	@JsonProperty(value = "relations")
	private Map<String, Object> relations; // 含下级分类信息表
	
	@JsonProperty(value = "category")
	private Map<String, Object> category; // 自定义显示信息表
	

	public Map<String, Object> getList() {
		return list;
	}

	public void setList(Map<String, Object> list) {
		this.list = list;
	}

	public Map<String, Object> getRelations() {
		return relations;
	}

	public void setRelations(Map<String, Object> relations) {
		this.relations = relations;
	}

	public Map<String, Object> getCategory() {
		return category;
	}

	public void setCategory(Map<String, Object> category) {
		this.category = category;
	}

	public static void main(String[] args) {
		DistrictCategoryDTO model = new DistrictCategoryDTO();
		List<Map<String, Object>> list = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		List<String> ls = new ArrayList<String>();
		ls.add("北京");
		ls.add("BEIJING");
		ls.add("BJ");
		map.put("010", ls.toArray());

		List<String> ls1 = new ArrayList<String>();
		ls1.add("北京2");
		ls1.add("BEIJING2");
		ls1.add("BJ2");
		map.put("010010010", ls1.toArray());
		list.add(map);
		model.setList(map);
		
		List<String> ls2 = new ArrayList<String>();
		ls2.add("280020");
		ls2.add("280030");
		ls2.add("280040");
		Map<String, Object> relations = Maps.newHashMap();
		List<Map<String, Object>> relationslist = Lists.newArrayList();
		relations.put("280",ls2.toArray());
		relationslist.add(relations);
		model.setRelations(relations);
		
		Map<String, Object> category = Maps.newHashMap();
		List<Map<String, Object>> categorylist = Lists.newArrayList();
		category.put("district",ls2.toArray());
		category.put("provinces",ls2.toArray());
		category.put("gangaotai",ls2.toArray());
		categorylist.add(category);
		model.setCategory(category);
				
		
		String json = JsonMapper.getInstance().toJson(model);
		System.out.println(json);
	}

}
