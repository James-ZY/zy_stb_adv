package com.gospell.aas.entity.adv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.gospell.aas.common.persistence.IdEntity;

/**
 * 机顶盒ID与上传字符串Entity
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_stb")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdStb extends IdEntity<AdStb> {

	private static final long serialVersionUID = 1L;
	
	private String smartcardId;//智能卡ID
	
	private String uploadPlayRecord;//上传的播放记录
	
	public AdStb() {
		super();
	}

	public AdStb(String id) {
		this();
		this.id = id;
	}

	@Column(name="smartcard_id")
	public String getSmartcardId() {
		return smartcardId;
	}

	public void setSmartcardId(String smartcardId) {
		this.smartcardId = smartcardId;
	}

	@Column(name="upload_play_record")
	public String getUploadPlayRecord() {
		return uploadPlayRecord;
	}

	public void setUploadPlayRecord(String uploadPlayRecord) {
		this.uploadPlayRecord = uploadPlayRecord;
	}

	 
	 
	
	

}
