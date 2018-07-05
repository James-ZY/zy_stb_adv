package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 广告javabean
 * 
 * @author Administrator
 * 
 */
public class AdelementDTO {

	@JsonProperty("adId")
	private String adId;// 广告ID

	@JsonProperty("adName")
	private String adName;// 广告名称

	@JsonProperty("path")
	private String path;// 资源路径

	@JsonProperty("hdPath")
	private String hdPath;// 高清资源路径

	@JsonProperty("fileImagePath")
	private String fileImagePath;// 视频截图路径

	@JsonProperty("hdFileImagePath")
	private String hdFileImagePath;// 高清视频截图路径

	@JsonProperty("typeId")
	private String typeId;// 广告类型Id（1 开机画面广告 2挂角广告 3换台广告 4 插屏广告 5 滚动广告,6开机视频广告
							// 7提示窗广告8菜单图片广告9广告背景图片）

	@JsonProperty("status")
	private Integer status;// 0图片类型，1视频类型

	@JsonProperty("isPosition")
	private Integer isPosition;// 显示是否需要详细坐标（0不需要 1需要）

	@JsonProperty("isMove")
	private Integer isMove;// 是否可移动（0 不可移动 1可移动）

	@JsonProperty("sdX")
	private Integer sdX;// 横坐标(标清)

	@JsonProperty("sdY")
	private Integer sdY;// 纵坐标（标清）

	@JsonProperty("hdX")
	private Integer hdX;// 横坐标(高清)

	@JsonProperty("hdY")
	private Integer hdY;// 纵坐标（高清）

	@JsonProperty("endSdX")
	private Integer endSdX;// 横坐标(标清)

	@JsonProperty("endSdY")
	private Integer endSdY;// 纵坐标（标清）

	@JsonProperty("endHdX")
	private Integer endHdX;// 横坐标(高清)

	@JsonProperty("endHdY")
	private Integer endHdY;// 纵坐标（高清）

	@JsonProperty("sdStartX")
	private Integer sdStartX;// 广告范围起始横坐标(标清)

	@JsonProperty("sdStartY")
	private Integer sdStartY;// 广告范围起始纵坐标（标清）

	@JsonProperty("hdStartX")
	private Integer hdStartX;// 广告范围起始横坐标(高清)

	@JsonProperty("hdStartY")
	private Integer hdStartY;// 广告范围起始纵坐标（高清）

	@JsonProperty("height")
	private Integer height;// 广告范围高度（标清）

	@JsonProperty("heightHd")
	private Integer heightHd;// 广告范围高度（高清）

	@JsonProperty("width")
	private Integer width;// 广告范围宽度（标清）

	@JsonProperty("widthHd")
	private Integer widthHd;// 广告范围宽度（高清）

	@JsonProperty("sdVelocity")
	private Integer sdVelocity;// 移动速度(标清)

	@JsonProperty("hdVelocity")
	private Integer hdVelocity;// 移动速度（高清）

	@JsonProperty("showTime")
	private Integer showTime;// 单次展示时间（插屏图片和滚动广告）

	@JsonProperty("showCount")
	private Integer showCount;// 展示次数（插屏图片和滚动广告）
	
	@JsonProperty("sdFx")
	private Integer sdFx;//标清滚动方向
	
	@JsonProperty("hdFx")
	private Integer hdFx;//高清滚动方向

	//插屏广告轨迹模式

	@JsonProperty("showMode")
	private Integer showMode;//插屏广告展示模式

	@JsonProperty("sdTrackName")
	private String sdTrackName;//标清轨迹模板名称

	@JsonProperty("sdCoordinates")
	private String sdCoordinates;//标清坐标集合

	@JsonProperty("sdCpShowTime")
	private Integer sdCpShowTime;//标清插屏总的显示时间

	@JsonProperty("sdBgWidth")
	private Integer sdBgWidth;//标清背景图片宽度

	@JsonProperty("sdBgHeight")
	private Integer sdBgHeight;//标清背景图片高度

	@JsonProperty("hdTrackName")
	private String hdTrackName;//高清轨迹模板名称

	@JsonProperty("hdCoordinates")
	private String hdCoordinates;//高清坐标集合

	@JsonProperty("hdCpShowTime")
	private Integer hdCpShowTime;//高清插屏总的显示时间

	@JsonProperty("hdBgWidth")
	private Integer hdBgWidth;//高清背景图片宽度

	@JsonProperty("hdBgHeight")
	private Integer hdBgHeight;//高清背景图片高度


	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(Integer isPosition) {
		this.isPosition = isPosition;
	}

	public Integer getIsMove() {
		return isMove;
	}

	public void setIsMove(Integer isMove) {
		this.isMove = isMove;
	}

	public Integer getSdX() {
		return sdX;
	}

	public void setSdX(Integer sdX) {
		this.sdX = sdX;
	}

	public Integer getSdY() {
		return sdY;
	}

	public void setSdY(Integer sdY) {
		this.sdY = sdY;
	}

	public Integer getHdX() {
		return hdX;
	}

	public void setHdX(Integer hdX) {
		this.hdX = hdX;
	}

	public Integer getHdY() {
		return hdY;
	}

	public void setHdY(Integer hdY) {
		this.hdY = hdY;
	}

	public Integer getSdStartX() {
		return sdStartX;
	}

	public void setSdStartX(Integer sdStartX) {
		this.sdStartX = sdStartX;
	}

	public Integer getSdStartY() {
		return sdStartY;
	}

	public void setSdStartY(Integer sdStartY) {
		this.sdStartY = sdStartY;
	}

	public Integer getHdStartX() {
		return hdStartX;
	}

	public void setHdStartX(Integer hdStartX) {
		this.hdStartX = hdStartX;
	}

	public Integer getHdStartY() {
		return hdStartY;
	}

	public void setHdStartY(Integer hdStartY) {
		this.hdStartY = hdStartY;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHeightHd() {
		return heightHd;
	}

	public void setHeightHd(Integer heightHd) {
		this.heightHd = heightHd;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getWidthHd() {
		return widthHd;
	}

	public void setWidthHd(Integer widthHd) {
		this.widthHd = widthHd;
	}

	public Integer getSdVelocity() {
		return sdVelocity;
	}

	public void setSdVelocity(Integer sdVelocity) {
		this.sdVelocity = sdVelocity;
	}

	public Integer getHdVelocity() {
		return hdVelocity;
	}

	public void setHdVelocity(Integer hdVelocity) {
		this.hdVelocity = hdVelocity;
	}

	public Integer getShowTime() {
		return showTime;
	}

	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public Integer getShowCount() {
		return showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	public Integer getEndSdX() {
		return endSdX;
	}

	public void setEndSdX(Integer endSdX) {
		this.endSdX = endSdX;
	}

	public Integer getEndSdY() {
		return endSdY;
	}

	public void setEndSdY(Integer endSdY) {
		this.endSdY = endSdY;
	}

	public Integer getEndHdX() {
		return endHdX;
	}

	public void setEndHdX(Integer endHdX) {
		this.endHdX = endHdX;
	}

	public Integer getEndHdY() {
		return endHdY;
	}

	public void setEndHdY(Integer endHdY) {
		this.endHdY = endHdY;
	}

	public String getFileImagePath() {
		return fileImagePath;
	}

	public void setFileImagePath(String fileImagePath) {
		this.fileImagePath = fileImagePath;
	}

	public String getHdPath() {
		return hdPath;
	}

	public void setHdPath(String hdPath) {
		this.hdPath = hdPath;
	}

	public String getHdFileImagePath() {
		return hdFileImagePath;
	}

	public void setHdFileImagePath(String hdFileImagePath) {
		this.hdFileImagePath = hdFileImagePath;
	}

	public Integer getSdFx() {
		return sdFx;
	}
	public void setSdFx(Integer sdFx) {
		this.sdFx = sdFx;
	}
	public Integer getHdFx() {
		return hdFx;
	}
	public void setHdFx(Integer hdFx) {
		this.hdFx = hdFx;
	}

	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}

	public String getSdTrackName() {
		return sdTrackName;
	}

	public void setSdTrackName(String sdTrackName) {
		this.sdTrackName = sdTrackName;
	}

	public String getSdCoordinates() {
		return sdCoordinates;
	}

	public void setSdCoordinates(String sdCoordinates) {
		this.sdCoordinates = sdCoordinates;
	}

	public Integer getSdCpShowTime() {
		return sdCpShowTime;
	}

	public void setSdCpShowTime(Integer sdCpShowTime) {
		this.sdCpShowTime = sdCpShowTime;
	}

	public Integer getSdBgWidth() {
		return sdBgWidth;
	}

	public void setSdBgWidth(Integer sdBgWidth) {
		this.sdBgWidth = sdBgWidth;
	}

	public Integer getSdBgHeight() {
		return sdBgHeight;
	}

	public void setSdBgHeight(Integer sdBgHeight) {
		this.sdBgHeight = sdBgHeight;
	}

	public String getHdTrackName() {
		return hdTrackName;
	}

	public void setHdTrackName(String hdTrackName) {
		this.hdTrackName = hdTrackName;
	}

	public String getHdCoordinates() {
		return hdCoordinates;
	}

	public void setHdCoordinates(String hdCoordinates) {
		this.hdCoordinates = hdCoordinates;
	}

	public Integer getHdCpShowTime() {
		return hdCpShowTime;
	}

	public void setHdCpShowTime(Integer hdCpShowTime) {
		this.hdCpShowTime = hdCpShowTime;
	}

	public Integer getHdBgWidth() {
		return hdBgWidth;
	}

	public void setHdBgWidth(Integer hdBgWidth) {
		this.hdBgWidth = hdBgWidth;
	}

	public Integer getHdBgHeight() {
		return hdBgHeight;
	}

	public void setHdBgHeight(Integer hdBgHeight) {
		this.hdBgHeight = hdBgHeight;
	}

	public static void test(StringBuffer file) {
		file.append("123");
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		StringBuffer file = new StringBuffer();
		test(file);
		System.out.println("file:" + file);
		System.out.println(isBlank(file));
	}
}
