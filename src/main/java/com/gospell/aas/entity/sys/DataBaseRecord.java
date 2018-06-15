package com.gospell.aas.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gospell.aas.common.persistence.IdEntity;

/**
 * 数据库备份记录表
 * 
 * @author zjh
 */
@Entity
@Table(name = "sys_database_record")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataBaseRecord extends IdEntity<DataBaseRecord> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 记录名称 */	
	private String recordName;

	/** 记录路径 */
	private String recordPath;

	public DataBaseRecord() {
		super();
	}

	public DataBaseRecord(String id) {
		this();
		this.id = id;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}
}
