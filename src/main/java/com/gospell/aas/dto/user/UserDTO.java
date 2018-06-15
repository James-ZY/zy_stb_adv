package com.gospell.aas.dto.user;

import java.util.Date;

 

public class UserDTO {

    private String id;  // 用户Id
    private String officeId;// 登录名
    private String companyId;// 登录名
    private String loginName;// 登录名=手机号码
    private String password;// 密码
    private String name; // 姓名
    private String email; // 邮箱
    private String mobile; // 手机
    private String userType;// 用户类型
    private String loginIp; // 最后登陆IP
    private String nick; // 昵称
    private String sex;
    private Date birthday;  // 出生日期
    private String income;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
     * @return the income
     */
    public String getIncome() {
        return income;
    }

    /**
     * @param income
     *            the income to set
     */
    public void setIncome(String income) {
        this.income = income;
    }
    
    public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

    @Override
    public String toString() {
        return "UserDTO [officeId=" + officeId + ", companyId=" + companyId + ", loginName=" + loginName
                + ", password=" + password + ", name=" + name + ", email=" + email + ", mobile=" + mobile
                + ", userType=" + userType + ", loginIp=" + loginIp + ", id=" + id + "]";
    }

}