package com.gospell.aas.entity.adv;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.Collections3;
import com.gospell.aas.common.utils.excel.annotation.ExcelField;
import com.gospell.aas.entity.sys.Role;
import com.gospell.aas.entity.sys.User;

/**
 * 广告Entity
 * 
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_advertiser")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Advertiser extends IdEntity<Advertiser> {
	private static final long serialVersionUID = 1L;
	private String businessLicenseNumber; // 营业执照注册号
	private String businessLicense; // 营业执照存放路径图片
	private String industry; // 行业
	private String industryAptitude; // 行业资质
	private String advertiserId;// 广告商Id
	private String name;// 广告商名称
	private Integer type;// 0普通用户，1 vip
	private String contacts;// 联系人
	private String webName; // 公司网站
	private String address; // 地址
	private String phone; // 固定电话
	private String mobile; // 手机
	private String email; // 邮箱
	private List<User> userList = Lists.newArrayList();
	private List<AdCombo> comboList = Lists.newArrayList();
	private List<Role> roleList = Lists.newArrayList(); //当前广告商的广告由哪些用户审核
	
	@Transient
	private String uploadMessage;//上传信息
	
	public final static Integer ADVERTISER_TYPE_NORMAL=0;
	public final static Integer ADVERTISER_TYPE_VIP=1;

	public Advertiser() {
		super();
	}

	public Advertiser(String id) {
		this();
		this.id = id;
		this.type = Advertiser.ADVERTISER_TYPE_NORMAL;
	}

	@Column(name = "business_license_number")
	@ExcelField(title="advertiser.businessLicenseNumber", align=2, sort=4,required=1,max=30)
	@Length(max=30)
	@NotNull
	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}

	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}

	@Column(name = "business_license")
	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	@Column(name = "industry")
	@ExcelField(title="advertiser.industry", align=2,sort=3,max=64,required=1)
	@NotNull
	@Length(max=64)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "industry_aptitude")
	public String getIndustryAptitude() {
		return industryAptitude;
	}

	public void setIndustryAptitude(String industryAptitude) {
		this.industryAptitude = industryAptitude;
	}

	@Column(name = "advertiser_id")
	@ExcelField(title="advertiser.id", align=2,sort=1,max=30,required=1)
	@NotNull
	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	 @Column(name="name")
	@ExcelField(title="advertiser.name", align=2,sort=2,max=64,required=1)
	@NotNull
	@Length(max=64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 @Column(name="advertiser_type")
	@ExcelField(title="advertiser.type##(value)",align=2,sort=11,dictType="advertiser_type",max=1,required=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	 @Column(name="contacts")
	@ExcelField(title="advertiser.contact", align=2 ,sort=10,max=40,required=1)
	@NotNull
    @Length(max=40)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	 @Column(name="web_name")
	@ExcelField(title="advertiser.webname", align=2,sort=8)
	 @Length(max=64)
	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	 @Column(name="address")
	@ExcelField(title="advertiser.address", align=2,sort=5,max=100)
	 @Length(max=100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	 @Column(name="phone")
	 @Length(max=15)
	@ExcelField(title="advertiser.phone", align=2,sort=6,max=15,required=1)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	 @Column(name="mobile")
	 @Length(max=15)
	@ExcelField(title="mobilephone", align=2,sort=7,max=15,required=1)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	 @Column(name="email")
	//@ExcelField(title="email", align=2,sort=9)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(mappedBy = "advertiser", fetch=FetchType.LAZY)
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@OrderBy(value="id") @Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ad_advertiser_combo", joinColumns = { @JoinColumn(name = "ad_advertiser_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_combo_id") })
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



	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ad_advertiser_role", joinColumns = { @JoinColumn(name = "ad_advertiser_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_role_id") })
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
    @Transient
    @JsonIgnore
    public List<String> getRoleIdList() {
        List<String> roleIdList = Lists.newArrayList();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    @Transient
    public void setRoleIdList(List<String> roleIdList) {
        roleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roleList.add(role);
        }
    }
    
    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    @Transient
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ", ");
    }

    @Transient
    @JsonIgnore
    public String getRoleListNames() {
        return Collections3.extractToString(roleList, "id", ", ");
    }

    @Transient
	public String getUploadMessage() {
		return uploadMessage;
	}

	@Transient
	public void setUploadMessage(String uploadMessage) {
		this.uploadMessage = uploadMessage;
	}

}
