package com.gospell.aas.entity.adv;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.IdEntity;
import com.gospell.aas.common.utils.DictUtils;

/**
 * =设计广告类型的素材的大小
 * 
 * @author 郑德生
 * @version 2016-05-24
 */
@Entity
@Table(name = "ad_resoure_type")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdResourceOfType extends IdEntity<AdResourceOfType> {

	private static final long serialVersionUID = 1L;

	private Float fileMinSize;//文件最小大小
	private Float fileMaxSize;//文件最大大小
	private Integer widthMin;//最小宽
	private Integer widthMax;//最大高
	private Integer highMin;//最小高
	private Integer highMax;//最大高
	
	private Integer frameMin;//最小帧
	private Integer frameMax;//最大帧
	private Integer rateMin;//最小码流
	private Integer rateMax;//最大码流
	private Integer flag;//（0标清 1高清）
	private String format;//图片格式或者视频格式
	private Integer rollFlag;  //滚动方向 1表示横向 2表示竖向  只针对滚动广告
	private Integer enable;//是否启用（0启用 1不启用） 
	private AdType adType;//广告类型
	
	
	public static final Integer FLAG_SD = 0;//标清
	public static final Integer FLAG_HD=1;//高清
	
	public static final Integer ENABLE_NO = 0;//不启用
	public static final Integer ENABLE_YES=1;//启用
	
	public static final Integer ROLL_SC_HX = 0;//横向
	public static final Integer ROLL_SC_SX=1;//竖向

	public AdResourceOfType() {
		super();
		this.enable = AdResourceOfType.ENABLE_NO;
		this.fileMinSize = 0f;
		this.rateMin = 0;
		this.rateMax =0;
		this.frameMin=0;
	}

	public AdResourceOfType(String id) {
		this();
		this.id = id;
		
	}

	@Column(name = "file_min_size")
	public Float getFileMinSize() {
		return fileMinSize;
	}

	public void setFileMinSize(Float fileMinSize) {
		this.fileMinSize = fileMinSize;
	}

	@Column(name = "file_max_size")
	public Float getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(Float fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	@Column(name = "width_min")
	public Integer getWidthMin() {
		return widthMin;
	}

	public void setWidthMin(Integer widthMin) {
		this.widthMin = widthMin;
	}

	@Column(name = "width_max")
	public Integer getWidthMax() {
		return widthMax;
	}

	public void setWidthMax(Integer widthMax) {
		this.widthMax = widthMax;
	}

	@Column(name = "high_min")
	public Integer getHighMin() {
		return highMin;
	}

	public void setHighMin(Integer highMin) {
		this.highMin = highMin;
	}

	@Column(name = "high_max")
	public Integer getHighMax() {
		return highMax;
	}

	public void setHighMax(Integer highMax) {
		this.highMax = highMax;
	}

	@Column(name = "frame_min")
	public Integer getFrameMin() {
		return frameMin;
	}

	public void setFrameMin(Integer frameMin) {
		this.frameMin = frameMin;
	}

	@Column(name = "frame_max")
	public Integer getFrameMax() {
		return frameMax;
	}

	public void setFrameMax(Integer frameMax) {
		this.frameMax = frameMax;
	}

	@Column(name = "rate_min")
	public Integer getRateMin() {
		return rateMin;
	}

	public void setRateMin(Integer rateMin) {
		this.rateMin = rateMin;
	}

	@Column(name = "rate_max")
	public Integer getRateMax() {
		return rateMax;
	}

	public void setRateMax(Integer rateMax) {
		this.rateMax = rateMax;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "format")
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	@Column(name="roll_flag")
	public Integer getRollFlag() {
		return rollFlag;
	}

	public void setRollFlag(Integer rollFlag) {
		this.rollFlag = rollFlag;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@ManyToOne
	@JoinColumn(name="ad_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull
	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

 
	@Transient
	public String getFileSize(){
		if(null != fileMaxSize){
			if(null != fileMinSize&& 0<fileMinSize){
				return "["+String.valueOf(fileMinSize)+","+String.valueOf(fileMaxSize)+"]";
			}else{
				return "("+0+","+String.valueOf(fileMaxSize)+"]";
			}
		}
		return "";
	}
	
	@Transient
	public String getHighSize(){
		if(null != highMax){
			if(null != highMin && 0<highMin){
				return "["+String.valueOf(highMin)+","+String.valueOf(highMax)+"]";
			}else{
				return "("+0+","+String.valueOf(highMax)+"]";
			}
		}
		return "";
	}
	
	@Transient
	public String getFrameSize(){
		if(null != frameMax){
			if(null != frameMin && 0<frameMin){
				return "["+String.valueOf(frameMin)+","+String.valueOf(frameMax)+"]";
			}else{
				return "("+0+","+String.valueOf(frameMax)+"]";
			}
		}
		return "";
	}
	
	@Transient
	public String getRateSize(){
		if(null != rateMax){
			if(null != rateMin && 0<rateMin){
				return "["+String.valueOf(rateMin)+","+String.valueOf(rateMax)+"]";
			}else{
				return "["+0+","+String.valueOf(rateMax)+"]";
			}
		}
		return "";
	}
	
	@Transient
	public String getWidthSize(){
		if(null != widthMax){
			if(null != widthMin && 0<widthMin){
				return "["+String.valueOf(widthMin)+","+String.valueOf(widthMax)+"]";
			}else{
				return "("+0+","+String.valueOf(widthMax)+"]";
			}
		}
		return "";
	}
	
	@Transient
	public String getFormatList(){
		if(null != format){
			String image ="adv_resource_image_format";
			if(adType != null && adType.getStatus() != null){
				image = "adv_resource_vedio_format";
			}
			List<String> buffer = Lists.newArrayList();
			 String[] s = format.split(",");
			 for(int i=0;i<s.length;i++){
				 
				 String label = DictUtils.getDictLabel(s[i], image, "");
				 buffer.add(label);
			 }
			 
			 return StringUtils.join(buffer, ",");
		}
		return "";
	}
	
	

}