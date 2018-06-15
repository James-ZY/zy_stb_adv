package com.gospell.aas.entity.adv;



import java.util.List;
import java.util.Objects;

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



/**
 * 图片控制Entity
 *
 * @author free lance
 * @version 2013-5-15
 */
@Entity
@Table(name = "ad_controller")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdControll extends IdEntity<AdControll> {

    private static final long serialVersionUID = 1L;
    private String filePath;//文件路径
    private String fileImagePath;//视频的截图路径
	private String previewVedioPath;//预览视频地址
    private Integer width;// 文件宽
    private Integer height;//文件高
    private Integer playOrder;// 展示顺序
	private String fileSize;  //文件大小
	private Integer duration;//视频时长
	private String fileFormat;  // 文件格式
	private Integer resourceType;//资源类型(1图片 2视频)
	private Integer status;//资源状态（0待审核 3认领(审核中) 1审核通过 2审核未通过）
	private String reason;//原因
	private AdType adType;//广告类型
	private List<Adelement> adElemetList = Lists.newArrayList();//广告
	private List<Adelement> hdAdElemetList = Lists.newArrayList();//高清广告
	private Advertiser advertiser;//所属广告商
    private Integer flag;//（0标清 1高清）
	private String version;//素材版本 方便筛选

	/**
	 * 以下字段仅仅作用于滚动广告
	 */
	private String rollText;//滚动广告的文字

	private String rollBackground;//isPurity=0表示RGB的值，=1表示背景图片的源文件

	private Integer isPurity;//0表示纯色1表示图片2表示透明

	private String rollTextColor;//文字颜色

	private Integer rollTextSize;//文字大小

	private Integer rollFlag;  //滚动方向 1表示横向 2表示竖向

	private String font;//字体

	private Integer isBold;//是否加粗

	private Integer isItalic;//是否斜体

	@Transient
	private String path;
	@Transient
	private String vedioImagePath;

	public static final Integer RESOURCE_IMAGE=1;
	public static final Integer RESOURCE_VEDIO=2;

	public static final Integer STATUS_WAIT=0;
	public static final Integer STATUS_CLAIM=3;
	public static final Integer STATUS_PASS=1;
	public static final Integer STATUS_FALI=2;

	public static final int IMAGE_PURITY_YES=0;//背景为纯色

	public static final int IMAGE_PURITY_NO=1;//背景为图片

	public static final int IMAGE_PURITY_TSP=2;//背景为透明

	public static final Integer FLAG_SD = 0;//标清
	public static final Integer FLAG_HD=1;//高清

	public static final Integer ROLL_SC_HX = 0;//横向
	public static final Integer ROLL_SC_SX=1;//竖向

    public AdControll() {
        super();
    }

    public AdControll(String id) {
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

	@Column(name="ad_reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_control_adelement", joinColumns = { @JoinColumn(name = "ad_control_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_adv_id") })
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
	public List<Adelement> getAdElemetList() {
		return adElemetList;
	}

	public void setAdElemetList(List<Adelement> adElemetList) {
		this.adElemetList = adElemetList;
	}



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_hd_control_adelement", joinColumns = { @JoinColumn(name = "ad_control_id") }, inverseJoinColumns = { @JoinColumn(name = "ad_adv_id") })
    @Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
    @OrderBy("id")
    @Fetch(FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
	public List<Adelement> getHdAdElemetList() {
		return hdAdElemetList;
	}

	public void setHdAdElemetList(List<Adelement> hdAdElemetList) {
		this.hdAdElemetList = hdAdElemetList;
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

	@Column(name="duration")
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@ManyToOne
	@JoinColumn(name="ad_advertiser_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnore
	@NotNull
	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	@Column(name="ad_preview_vedio_path")
	public String getPreviewVedioPath() {
		return previewVedioPath;
	}

	public void setPreviewVedioPath(String previewVedioPath) {
		this.previewVedioPath = previewVedioPath;
	}

	@Column(name="ad_roll_text")
	@Length(max=400)
	public String getRollText() {
		return rollText;
	}

	public void setRollText(String rollText) {
		this.rollText = rollText;
	}

	@Column(name="ad_roll_background")
	public String getRollBackground() {
		return rollBackground;
	}

	public void setRollBackground(String rollBackground) {
		this.rollBackground = rollBackground;
	}

	@Column(name="ad_is_purity")
	public Integer getIsPurity() {
		return isPurity;
	}

	public void setIsPurity(Integer isPurity) {
		this.isPurity = isPurity;
	}

	@Column(name="ad_roll_text_color")
	public String getRollTextColor() {
		return rollTextColor;
	}

	public void setRollTextColor(String rollTextColor) {
		this.rollTextColor = rollTextColor;
	}

	@Column(name="ad_roll_text_size")
	public Integer getRollTextSize() {
		return rollTextSize;
	}

	public void setRollTextSize(Integer rollTextSize) {
		this.rollTextSize = rollTextSize;
	}

	@Column(name="ad_roll_flag")
	public Integer getRollFlag() {
		return rollFlag;
	}

	public void setRollFlag(Integer rollFlag) {
		this.rollFlag = rollFlag;
	}

	@Column(name="ad_roll_text_font")
	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	@Column(name="ad_roll_text_bold")
	public Integer getIsBold() {
		return isBold;
	}

	public void setIsBold(Integer isBold) {
		this.isBold = isBold;
	}

	@Column(name="ad_roll_text_italic")
	public Integer getIsItalic() {
		return isItalic;
	}

	public void setIsItalic(Integer isItalic) {
		this.isItalic = isItalic;
	}

	@Column(name="flag")
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name="ad_version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public  boolean equals(Object obj) {
		if(obj == null) return false;
		if(this == obj) return true;
		if(obj instanceof AdControll){
			AdControll net =(AdControll)obj;
			if(net.id.equals(this.id )) return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}