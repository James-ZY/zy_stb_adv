package com.gospell.aas.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gospell.aas.common.persistence.IdEntity;

/**
 * 系统帮助文档Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "sys_help")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Help extends IdEntity<Help> {

	private static final long serialVersionUID = 1L;
	
	private String fileName;//文件名
	
	private String filePath;//文件路径
	
 	private String fileSize;  //文件大小
 	
	private String fileFormat;  // 文件格式
	
	private Integer status;//状态（0失效 1有效）
	
	private Integer flag;//文档属于类别（0使用说明（暂时只有这一种状态））
	
	private Integer helpLocale;//0中文 1英文
	
	@Transient
	private String oldFilePath;//修改的时候调用
	
	public static final Integer zh_CN = 0;//中文
	public static final Integer en_US = 1;//英文
	
  
	
	public static final Integer HELP_EFFECTIVE_STAUTS=1;//有效
	
	public static final Integer HELP_INVALID_STATUS=0;//失效
	
 
	
	public static final Integer HELP_INSTRUCTIONS_FLAG=0;//使用说明

	public Help() {
		super();
		
	}

	public Help(String id) {
		this();
		this.id = id;
	}

	@Column(name="file_path")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="file_size")
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_format")
	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	@Column(name="status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name="file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="locale")
	public Integer getHelpLocale() {
		return helpLocale;
	}

	public void setHelpLocale(Integer helpLocale) {
		this.helpLocale = helpLocale;
	}

	@Transient
	public String getOldFilePath() {
		 return oldFilePath;
	}

	@Transient
	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}
 
	
	 

}