package com.gospell.aas.common.utils.adv.rollimage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gospell.aas.common.mapper.JsonMapper;

public class ImageDTO {

	@JsonProperty("backImagePath")
	private String backImagePath;//背景图片路径

	@JsonProperty("background")
	private String background;//背景颜色

	@JsonProperty("isPurity")
	private int isPurity;//是否是纯色，0表示是 1反之。当等于0的时候background不为null，等于1的时候backImagePath不为空

	@JsonProperty("rollFlag")
	private Integer rollFlag;//滚动方向 1表示横向 2表示竖向

	@JsonProperty("isBold")
	private int isBold;//是否加粗

	@JsonProperty("isItalic")
	private int isItalic;//是否斜体

	@JsonProperty("textColor")
	private String textColor;//文字颜色

	@JsonProperty("textSize")
	private int textSize;;//文字大小

	@JsonProperty("text")
	private String text;//文字

	@JsonProperty("imageHeight")
	private int imageHeight;//图片整体高度

	@JsonProperty("testBy")
	private byte[] testBy;//图片整体高度

	@JsonProperty("font")
	private String font;//字体*/

	@JsonProperty("flag")
	private String flag;//图片类型  高清/标清


	public static final int IMAGE_PURITY_YES=0;//背景为纯色

	public static final int IMAGE_PURITY_NO=1;//背景为图片

	public static final int IMAGE_PURITY_TSP=2;//背景为透明
	public String getBackImagePath() {
		return backImagePath;
	}

	public void setBackImagePath(String backImagePath) {
		this.backImagePath = backImagePath;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getIsPurity() {
		return isPurity;
	}

	public void setIsPurity(int isPurity) {
		this.isPurity = isPurity;
	}

	public Integer getRollFlag() {
		return rollFlag;
	}

	public void setRollFlag(Integer rollFlag) {
		this.rollFlag = rollFlag;
	}

	public int getIsBold() {
		return isBold;
	}

	public void setIsBold(int isBold) {
		this.isBold = isBold;
	}

	public int getIsItalic() {
		return isItalic;
	}

	public void setIsItalic(int isItalic) {
		this.isItalic = isItalic;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}


	public byte[] getTestBy() {
		String d1 ="郑德生";
		byte[] b = d1.getBytes();
		return b;
	}

	public void setTestBy(byte[] testBy) {
		this.testBy = testBy;
	}



	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static void main(String[] args) {
		System.out.println(JsonMapper.toJsonString(new ImageDTO()));
	}


}
