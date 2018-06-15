package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageInfo {

	@JsonProperty("filePath")
    private String filePath;//文件路径
	
	@JsonProperty("width")
    private Integer width;// 文件高度
	
	@JsonProperty("height")
    private Integer height;// 文件高度
	
	@JsonProperty("fileSize")
	private String fileSize;  //文件大小
	
	@JsonProperty("fileFormat")
	private String fileFormat;  // 文件格式

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	
 
	
	 
	
}
