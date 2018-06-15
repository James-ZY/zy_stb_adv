package com.gospell.aas.entity.adv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.adv.AdTypeUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 广告发送器Entity
 * 
 * @author 郑德生
 * @version 2015-05-24
 */
@Entity
@Table(name = "ad_version")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdVersion extends IdEntity<AdVersion> {

	private static final long serialVersionUID = 1L;

	private String versionID;//版本号

	private String companyName;//公司名称

	private String companyLogo;//公司logo

	private String companyUrl;//公司url

	@Column(name = "version_id")
	public String getVersionID() {
		return versionID;
	}

	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}

	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "company_logo")
	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	@Column(name = "company_url")
	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
}