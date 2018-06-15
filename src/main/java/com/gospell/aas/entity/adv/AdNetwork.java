package com.gospell.aas.entity.adv;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
import com.gospell.aas.common.utils.adv.AdTypeUtils;

/**
 * 广告发送器Entity
 * 
 * @author 郑德生
 * @version 2015-05-24
 */
@Entity
@Table(name = "ad_network")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdNetwork extends IdEntity<AdNetwork> {

	private static final long serialVersionUID = 1L;

	private String networkId;// 广告发送器ID
	private String networkName;// 广告发送器名称
	private String cpu;// cpu状态
	private String memory; // 内存状态
	private String port;//端口号
	private String wayEncryption;//传输加密方式
	private String secretKey;//密钥
	private String ip;//IP地址
	
	private Date validDate;//发送器的有效时间 （发送器上传上来的）
	
	private Integer status;//当前是否有效（0失效 1有效）当电视运营商与广告运营商签约失效的时候
	
	private Integer playStatus;//发送器广告播放状态
	
	private String onlineStatus;//发送器在线状态
	
	private String isControlAllAD;//发送器是否控制本级复用器所有频道的广告

	private AdOperators adOperators;// 所属电视运营商
	
	private List<AdNetworkDistrict> adNetworkCategorys;// 区域
	private String area;// 区域
	
	private String selArea;//选择的区域信息 
	
	@Transient
	private String selAllArea;//包含子类的区域

	private List<AdCombo> comboList = Lists.newArrayList();// 包含的广告套餐
	private List<AdChannel> channelList = Lists.newArrayList();// 包含的电视频道
	private List<AdType> typeList = Lists.newArrayList();//与频道无关的广告类型
	
	public static final Integer NETWORK_YES_STATUS = 1;//有效
	public static final Integer NETWORK_NO_STATUS=0;//失效
	
	public static final Integer NETWORK_PLAY_STATUS = 1;//播放
	public static final Integer NETWORK_CLOSEDOWN_STATUS=0;//停播

	public AdNetwork() {
		super();
	}

	public AdNetwork(String id) {
		this();
		this.id = id;
	}

	@Column(name = "ad_network_id", nullable = false)
	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	@Column(name = "ad_netwrok_name", nullable = false)
	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	@Column(name = "ad_cpu")
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	@Column(name = "ad_mermory")
	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	@OneToOne
	@JoinColumn(name = "ad_operators_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull(message = "电视运营商不能为空")
	public AdOperators getAdOperators() {
		return adOperators;
	}

	public void setAdOperators(AdOperators adOperators) {
		this.adOperators = adOperators;
	}
	
	@OneToMany(mappedBy = "adNetwork")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdNetworkDistrict> getAdNetworkCategorys() {
		return adNetworkCategorys;
	}

	public void setAdNetworkCategorys(List<AdNetworkDistrict> adNetworkCategorys) {
		this.adNetworkCategorys = adNetworkCategorys;
	}

	@Column(name = "ad_area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "ad_sel_area")
	public String getSelArea() {
		return selArea;
	}

	public void setSelArea(String selArea) {
		this.selArea = selArea;
	}

	@Column(name="ad_port")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column(name="ad_way_encryption")
	public String getWayEncryption() {
		return wayEncryption;
	}

	public void setWayEncryption(String wayEncryption) {
		this.wayEncryption = wayEncryption;
	}

	@Column(name="ad_secret_key")
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Column(name="ad_ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@OneToMany(mappedBy = "adNetWork", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdChannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<AdChannel> channelList) {
		this.channelList = channelList;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_combo_network", joinColumns = { @JoinColumn(name = "ad_network_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ad_combo_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdCombo> getComboList() {
		return comboList;
	}

	public void setComboList(List<AdCombo> comboList) {
		this.comboList = comboList;
	}

	@Column(name="ad_status")
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="ad_play_status")
	public Integer getPlayStatus() {
		return playStatus;
	}

	public void setPlayStatus(Integer playStatus) {
		this.playStatus = playStatus;
	}

	@Column(name="online_status")
	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Column(name="is_control_all")
	public String getIsControlAllAD() {
		return isControlAllAD;
	}

	public void setIsControlAllAD(String isControlAllAD) {
		this.isControlAllAD = isControlAllAD;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ad_type_network", joinColumns = { @JoinColumn(name = "ad_network_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_type_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<AdType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<AdType> typeList) {
		this.typeList = typeList;
	}
	
	
	@Column(name="valid_date")
	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Transient
	public String getSelAllArea() {
		return selAllArea;
	}

	@Transient
	public void setSelAllArea(String selAllArea) {
		this.selAllArea = selAllArea;
	}
	
	@Transient
	public String getTypeName(){
		String s = "";
		if(null != typeList && typeList.size()>0){
			for (int i = 0; i < typeList.size(); i++) {
				String name = typeList.get(i).getTypeName();
				String local_name = AdTypeUtils.getLocaleAdTypeName(name);
				if(i==0){
					s += local_name;
				}else{
					s +=","+local_name;
				}
			}
		}
		return s;
	}

	@Override
	public  boolean equals(Object obj) {
		if(obj == null) return false;
		if(this == obj) return true;
		if(obj instanceof AdNetwork){
			AdNetwork net =(AdNetwork)obj;
			if(net.id.equals(this.id )) return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public static void main(String[] args) {
         AdNetwork a1 = new AdNetwork();
         a1.setId("f1398a19315041b89911e82f1af2c452");
         a1.setNetworkId("123");

		AdNetwork a2 = new AdNetwork();
		a2.setId("f1398a19315041b89911e82f1af2c452");
		a2.setNetworkId("456");
		Set<AdNetwork> set = new HashSet<AdNetwork>();
		set.add(a1);
		set.add(a2);
		System.out.println(set.size());
	}
}