package com.gospell.aas.entity.adv;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

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

import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.excel.annotation.ExcelField;

/**
 * 电视运营商Entity
 * 
 * @author zhengdesheng
 * @version 2016-5-24
 */
@Entity
@Table(name = "ad_operators")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdOperators extends IdEntity<AdOperators> {

	private static final long serialVersionUID = 1L;

	private String operatorsId;// 电视运营商ID
	private String operatorsName;// 电视运营商名称
	private String password; // 密码
	private String contact; // 联系人
	private String mobilephone;// 手机
	private String telphone;// 电话
	private List<AdOperatorsDistrict> adDistrictCategorys;// 区域
	private String area;// 区域
	private String number;// 订户数
	private List<AdNetwork> networkList;// 广告发送器ID

	@Transient
	private String adNetworkId;
	
	@Transient
	private String logName;

	@Transient
	private String uploadMessage;// 上传信息
	
	@Transient
	private String selArea;//选择的区域信息 

	public AdOperators() {
		super();
	}

	public AdOperators(String id) {
		this();
		this.id = id;
	}

	@Column(name = "ad_operators_id", nullable = false)
	@ExcelField(title = "operators.id", align = 2, sort = 1, required = 1, max = 20)
	public String getOperatorsId() {
		return operatorsId;
	}

	public void setOperatorsId(String operatorsId) {
		this.operatorsId = operatorsId;
	}

	@Column(name = "ad_operators_name", nullable = false)
	@Length(min = 1, max = 100)
	@ExcelField(title = "operators.name", align = 2, sort = 2, required = 1, max = 100)
	public String getOperatorsName() {
		return operatorsName;
	}

	public void setOperatorsName(String operatorsName) {
		this.operatorsName = operatorsName;
	}

	@Column(name = "ad_password", nullable = false)
	@ExcelField(title = "operators.password", align = 2, sort = 3, required = 1, max = 20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ad_contact", nullable = false)
	@ExcelField(title = "operators.contact", align = 2, sort = 4, required = 1, max = 30)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

/*	@OneToOne
	@JoinColumn(name = "ad_net_work_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public AdNetwork getNetwork() {
		return network;
	}

	public void setNetwork(AdNetwork network) {
		this.network = network;
	}*/

	@OneToMany(mappedBy = "adOperators", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdNetwork> getNetworkList() {
		return networkList;
	}

	public void setNetworkList(List<AdNetwork> networkList) {
		this.networkList = networkList;
	}
	
	@Transient
	public String getAdNetworkId() {
		return adNetworkId;
	}

	@Transient
	public void setAdNetworkId(String adNetworkId) {
		this.adNetworkId = adNetworkId;
	}

	@Column(name = "ad_mobilephone")
	@Length(max = 15)
	@ExcelField(title = "operators.mobile", align = 2, sort = 5, required = 1, max = 15)
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name = "ad_telphone")
	@ExcelField(title = "operators.telphone", align = 2, sort = 6, required = 1, max = 15)
	@Length(max = 15)
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@OneToMany(mappedBy = "operators")
	@NotFound(action = NotFoundAction.IGNORE)
	@OrderBy(value="ad_district_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<AdOperatorsDistrict> getAdDistrictCategorys() {
		return adDistrictCategorys;
	}

	public void setAdDistrictCategorys(
			List<AdOperatorsDistrict> adDistrictCategorys) {
		this.adDistrictCategorys = adDistrictCategorys;
	}

	@Column(name = "ad_area")
	@ExcelField(title = "operators.area", align = 2, sort = 7, required = 0, max = 200)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "ad_number")
	@ExcelField(title = "operators.number", align = 2, sort = 8, required = 0, max = 11)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Transient
	@ExcelField(title = "network.name", align = 2, sort = 10, required = 1, max = 11, type = 1)
	public String getNetworkName() {
		String networkName = "";
		if(null != networkList && networkList.size() >0){
			for (AdNetwork network : networkList) {
				if (network != null) {
					networkName += network.getNetworkName()+",";
				}
			}
			networkName = networkName.substring(0, networkName.lastIndexOf(","));
		}
			return networkName;
	}

	@Transient
	@ExcelField(title = "network.id", align = 2, sort = 9, required = 1, max = 11, type = 1)
	public String getNetworkId() {
		String networkId = "";
		if(null != networkList && networkList.size() >0){
			for (AdNetwork network : networkList) {
				if (network != null) {
					networkId += network.getNetworkId();
				}
			}
			networkId = networkId.substring(0, networkId.lastIndexOf(","));
		}
		return networkId;
	}

	@Transient
	public String getUploadMessage() {
		return uploadMessage;
	}

	@Transient
	public void setUploadMessage(String uploadMessage) {
		this.uploadMessage = uploadMessage;
	}

	@Transient
	public String getLogName() {
		return "电视运营商ID" + operatorsId;
	}

	@Transient
	public void setLogName(String logName) {
		this.logName = logName;
	}
	
	@Transient
	public String getSelArea() {
		return selArea;
	}

	@Transient
	public void setSelArea(String selArea) {
		this.selArea = selArea;
	}

}