package com.gospell.aas.entity.sys;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.Collections3;
import com.gospell.aas.common.utils.excel.annotation.ExcelField;
import com.gospell.aas.common.utils.excel.fieldtype.RoleListType;
import com.gospell.aas.entity.adv.Advertiser;
 
 
/**
 * 用户Entity
 * 
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity<User> {

    private static final long serialVersionUID = 1L;
    private Advertiser advertiser;
	private Office company;	// 归属机构
	private Office office;	// 归属部门(预留)
    private String loginName;// 登录名
    private String password;// 密码
    private String email;  //邮箱
    private String name;   //名称
	private String phone;  //固定电话
	private String mobile;  // 手机
    private String userType;// 用户类型：2广告运营商 3广告商
    private String isAdvSuper;//是否是广告商本身的超级管理员
    private String loginIp; // 最后登陆IP
    private Date loginDate; // 最后登陆日期
    private String address; //地址
	private String contacts;//联系人
	private String webName; //公司网站
	private String icpIcon;
	@Transient
	private String locale;//国际化
	
	 // 用户类型1-超级系统管理员；2-运营管理员 3网络管理员 4广告商管理员 5内容审核管理员 6广告商；
    public static final String TYPE_SUPER = "1";
    public static final String TYPE_OPERATION= "2";
    public static final String TYPE_NETWORK = "3";
    public static final String TYPE_ADV_SYSTEM= "4";
    public static final String TYPE_ANDIT = "5";
    public static final String TYPE_ADV= "6";
 

 
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
 

     
    public User() {
        super();
    }

    public User(String id) {
        this();
        this.id = id;
    }


	@ManyToOne
	@JoinColumn(name="company_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}
	
	@Transient
	@ExcelField(title="user.company.export", align=2, sort=25)
	public String getCompanyName() {
		if(null != company){
			return company.getName();
		}else{
			return "";
		}
	}
	
	@ManyToOne
	@JoinColumn(name="office_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

    @ExcelField(title = "登录名", align = 2, sort = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonIgnore
    @Length(min = 1, max = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 1, max = 100)
    @ExcelField(title = "姓名", align = 2, sort = 40)
    public String getName() {
        return name;
    }

  

    public void setName(String name) {
        this.name = name;
    }

    @Email
    @Length(min = 0, max = 200)
    @ExcelField(title = "邮箱", align = 1, sort = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ExcelField(title = "电话", align = 2, sort = 60)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 0, max = 200)
    @ExcelField(title = "手机", align = 2, sort = 70)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
	@Transient
    @ExcelField(title = "备注", align = 1, sort = 900)
    public String getRemarks() {
        return remarks;
    }

    @Length(min = 0, max = 100)
    @ExcelField(title = "用户类型", align = 2, sort = 80, dictType = "sys_user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
	@Transient
    @ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
    public Date getCreateDate() {
        return createDate;
    }

    @ExcelField(title = "最后登录IP", type = 1, align = 1, sort = 100)
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "最后登录日期", type = 1, align = 1, sort = 110)
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    @ExcelField(title = "拥有角色", align = 1, sort = 800, fieldType = RoleListType.class)
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
    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    @Transient
    public static boolean isAdmin(String id) {
        return id != null && id.equals("admin");
    }
 
 
    /**
     * @return the address
     */
    @Column(name = "address", length = 255)
    @ExcelField(title = "地址", align = 2, sort = 150)
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
  

	@Column(name = "contacts", nullable = false)
	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name = "web_name", nullable = false)
	public String getWebName() {
		return this.webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	 

	@Column(name = "icp_icon", nullable = false)
	public String getIcpIcon() {
		return this.icpIcon;
	}

	public void setIcpIcon(String icpIcon) {
		this.icpIcon = icpIcon;
	}

	@ManyToOne
	@JoinColumn(name="advertiser_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	@Column(name="is_adv_super")
	public String getIsAdvSuper() {
		return isAdvSuper;
	}

	public void setIsAdvSuper(String isAdvSuper) {
		this.isAdvSuper = isAdvSuper;
	}

	@Transient
	public String getLocale() {
		return locale;
	}

	@Transient
	public void setLocale(String locale) {
		this.locale = locale;
	}
 
 

   
}