package com.gospell.aas.entity.adv;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;

/**
 * 广告类型Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_type")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdType extends IdEntity<AdType> {

	private static final long serialVersionUID = 1L;

	private AdType parent;// 父级子类型
	private String parentIds; // 所有父级编号
	private String typeId;// 类型ID
	private String typeName;// 类型名称
	private String typeDescription;// 类型描述
	private Integer isFlag;// 是否频道相关(0不相关 1相关)
	private Integer status;// 0图片类型，1视频类型

	private Integer isPosition;// 显示是否需要详细坐标（0不需要 1需要）
	private Integer isMove;// 是否可移动（0 不可移动 1可移动）

	private List<AdType> childList = Lists.newArrayList();// 拥有的子类型列表
	private List<AdChannel> channelList = Lists.newArrayList();
	private List<AdNetwork> networkList = Lists.newArrayList();
	private List<AdPosition> positionList = Lists.newArrayList();

	public static final String TYPE_INSERT_SCREEN_ID = "4";
	public static final String TYPE_ROLL_ADV_ID = "5";
	public static final String TYPE_CORNER_ID = "2";
	public static final String Type_CHANGE_CHANNEL = "3";
	public static final String Type_SWITCH_ON_VEDIO = "6";
	public static final String Type_PROMPT_WINDOW = "7";
	public static final String Type_MENUE_ADV = "8";
	public static final String Type_BROCAST = "9";
	public static final String Type_OPEN_IMGAE = "1";

	/**
	 * 类型（图片 or 视频）
	 */
	public static final Integer TYPE_STATUS_IMAGE = 0;
	public static final Integer TYPE_STATUS_VEDIO = 1;

	/**
	 * 是否跟频道相关
	 */
	public static final Integer TYPE_NOT_CHANNEL = 0;
	public static final Integer TYPE_CHANNEL = 1;

	/**
	 * 是否可移动
	 */
	public static final Integer TYPE_NOT_MOVE = 0;
	public static final Integer TYPE_CAN_MOVE = 1;

	public AdType() {
		super();
		this.isMove = AdType.TYPE_CAN_MOVE;
	}

	public AdType(String id) {
		this();
		this.id = id;
	}

	/**
	 * 获取树的顶点值的id，默认添加的
	 */
	@Transient
	public static String getzeroAdTypeId() {
		return "-1";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public AdType getParent() {
		return parent;
	}

	public void setParent(AdType parent) {
		this.parent = parent;
	}

	@Column(name = "parent_ids")
	@NotFound(action = NotFoundAction.IGNORE)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Column(name = "ad_type_id")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "ad_type_name", nullable = false)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "ad_type_description")
	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_type_channel", joinColumns = { @JoinColumn(name = "ad_type_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_channel_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdChannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<AdChannel> channelList) {
		this.channelList = channelList;
	}
	
	

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_type_network", joinColumns = { @JoinColumn(name = "ad_type_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_network_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdNetwork> getNetworkList() {
		return networkList;
	}

	public void setNetworkList(List<AdNetwork> networkList) {
		this.networkList = networkList;
	}

	@Column(name = "is_flag")
	public Integer getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(Integer isFlag) {
		this.isFlag = isFlag;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "adType", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<AdPosition> positionList) {
		this.positionList = positionList;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdType> getChildList() {
		return childList;
	}

	public void setChildList(List<AdType> childList) {
		this.childList = childList;
	}

	@Column(name = "is_move")
	public Integer getIsMove() {
		return isMove;
	}

	public void setIsMove(Integer isMove) {
		this.isMove = isMove;
	}

	@Column(name = "is_position")
	public Integer getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(Integer isPosition) {
		this.isPosition = isPosition;
	}

	@Transient
	public static void sortList(List<AdType> list, List<AdType> sourcelist,
			String parentId) {

		for (int i = 0; i < sourcelist.size(); i++) {
			AdType e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null
					&& e.getParent().getId().equals(parentId)) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					AdType child = sourcelist.get(j);
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