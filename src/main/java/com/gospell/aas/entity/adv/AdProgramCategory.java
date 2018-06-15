package com.gospell.aas.entity.adv;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.IdEntity;

/**
 * 广告分类Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_program_category")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdProgramCategory extends IdEntity<AdProgramCategory> {

	private static final long serialVersionUID = 1L;
	
	private AdProgramCategory parent;
	private String parentIds;
	private String categoryId;//分类ID
	private String categoryName;//分类名称
	
	
	private List<AdProgramCategory> childList;
	 
	public AdProgramCategory() {
		super();
	}

	public AdProgramCategory(String id) {
		this();
		this.id = id;
	}
	
	/**
	 * 获取树的顶点值的id，默认添加的
	 */
	@Transient
	public static String getzeroAdProgramCategoryId() {
		return "1";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	@JsonIgnore
	public AdProgramCategory getParent() {
		return parent;
	}

	public void setParent(AdProgramCategory parent) {
		this.parent = parent;
	}

	@Column(name="parent_ids")
	@Length(min=1,max=1000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Column(name="category_id")
	@NotNull
	@Length(min=4,max=4)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="category_name")
	@Length(min=1,max=100)
	@NotNull
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
 

	@OneToMany(mappedBy = "parent", fetch=FetchType.LAZY)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@OrderBy(value="id") @Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdProgramCategory> getChildList() {
		return childList;
	}

	public void setChildList(List<AdProgramCategory> childList) {
		this.childList = childList;
	}
	
	@Transient
	public static void sortList(List<AdProgramCategory> list, List<AdProgramCategory> sourcelist,
			String parentId) {

		for (int i = 0; i < sourcelist.size(); i++) {
			AdProgramCategory e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null
					&& e.getParent().getId().equals(parentId)) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					AdProgramCategory child = sourcelist.get(j);
					if (child.getParent() != null
							&& child.getParent().getId() != null
							&& child.getParent().getId().equals(e.getId())) {
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
	
}
