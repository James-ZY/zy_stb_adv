package com.gospell.aas.entity.adv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gospell.aas.common.persistence.IdEntity;

/**
 * 
 * 外部程序管理表（比如ffmpeg管理）
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_external_program")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdExternalProgram extends IdEntity<AdExternalProgram> {

	private static final long serialVersionUID = 1L;

	private String programId;// 程序ID
	
	private String programName;// 程序名称
	
	private String programVersion;//程序版本
	
	private String programPath;//程序存放路径
	
	
	public static final String PROGRAM_FFMPEG="ffmpeg";

	public AdExternalProgram() {
		super();
	}

	public AdExternalProgram(String id) {
		this();
		this.id = id;
	}
	@Column(name="ad_program_id")
	@NotNull(message="programId is not null")
	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	@Column(name="ad_program_name")
	@NotNull(message="program name is not null")
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	@Column(name="ad_program_version")
	@NotNull(message="program version is not null")
	public String getProgramVersion() {
		return programVersion;
	}

	public void setProgramVersion(String programVersion) {
		this.programVersion = programVersion;
	}

	@Column(name="ad_program_path")
	@NotNull(message="program path is not null")
	public String getProgramPath() {
		return programPath;
	}

	public void setProgramPath(String programPath) {
		this.programPath = programPath;
	}
 
 
	 
	 

}