package com.gospell.aas.entity.adv;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospell.aas.common.persistence.IdEntity;

 
 
/**
 * 默认图片配置
 * 
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_default_controller")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdDefaultControll extends IdEntity<AdDefaultControll> {

    private static final long serialVersionUID = 1L;
    private String filePath;//文件路径
    private String fileImagePath;//视频的截图路径
    private Integer width;// 文件宽
    private Integer height;//文件高
    private Integer playOrder;// 展示顺序
	private String fileSize;  //文件大小
	private Integer duration;//视频时长
	private String fileFormat;  // 文件格式
	private Integer resourceType;//资源类型(1图片 2视频)
	private Integer status;//资源状态（0待审核 3认领(审核中) 1审核通过 2审核未通过）
	private AdType adType;//广告类型
	private AdNetwork adNetwork;//所属发送器
    private Integer flag;//（0标清 1高清）
	
	@Transient
	private String path;
	@Transient
	private String vedioImagePath;
	@Transient
	private String controllId;

	public static final Integer RESOURCE_IMAGE=1;
	public static final Integer RESOURCE_VEDIO=2;
	
	public static final Integer STATUS_WAIT=0;
	public static final Integer STATUS_CLAIM=3;
	public static final Integer STATUS_PASS=1;
	public static final Integer STATUS_FALI=2;
	
	public static final Integer FLAG_SD = 0;//标清
	public static final Integer FLAG_HD=1;//高清
 
    public AdDefaultControll() {
        super();
    }

    public AdDefaultControll(String id) {
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

	@Column(name="play_order")
	public Integer getPlayOrder() {
		return playOrder;
	}

	public void setPlayOrder(Integer playOrder) {
		this.playOrder = playOrder;
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
	@Column(name="resource_type")
	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name="ad_status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

 
	@ManyToOne
	@JoinColumn(name="ad_network_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull
    public AdNetwork getAdNetwork() {
		return adNetwork;
	}

	public void setAdNetwork(AdNetwork adNetwork) {
		this.adNetwork = adNetwork;
	}

	@Column(name="ad_width")
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name="ad_height")
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name="file_image_path")
	public String getFileImagePath() {
		return fileImagePath;
	}

	public void setFileImagePath(String fileImagePath) {
		this.fileImagePath = fileImagePath;
	}

	@Transient
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Transient
	public String getVedioImagePath() {
		return vedioImagePath;
	}

	public void setVedioImagePath(String vedioImagePath) {
		this.vedioImagePath = vedioImagePath;
	}
	
	@Transient
	public String getControllId() {
		return controllId;
	}

	public void setControllId(String controllId) {
		this.controllId = controllId;
	}

	@Column(name="duration")
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name="flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

 
	
	
}