package com.gospell.aas.dto.adv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FfprobeVedioInfo {
	
	@JsonProperty(value="height")
	private Integer height;//视频高度
	
	@JsonProperty(value="width")
	private Integer width;//视频宽度
	
	@JsonProperty(value="fileSize")
	private Long fileSize;//视频大小
	
	@JsonProperty(value="duration")
	private Long duration;//视频时长(秒)
	
	@JsonProperty(value="format")
	private String format;//视频格式
	
	@JsonProperty(value="bitRate")
	private int bitRate;//码流
	
	@JsonProperty(value="vedioCodec")
	private String vedioCodec;//视频编码
	
	@JsonProperty(value="audioCodec")
	private String  audioCodec;//音频编码
	
	@JsonProperty(value="isVedio")
	private Boolean isVedio;//是否是视频流（只有是，上述所有情况才满足）
	
	public static final String H264= "h264";
	public static final String MPEG2TS= "mpegts";
	public static final String MPEG2VIDEO= "mpeg2video";
	
	public static final String AAC= "aac";
	public static final String AC3= "ac3";

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}


	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getBitRate() {
		return bitRate;
	}

	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}

	 
	public String getVedioCodec() {
		return vedioCodec;
	}

	public void setVedioCodec(String vedioCodec) {
		this.vedioCodec = vedioCodec;
	}

	public String getAudioCodec() {
		return audioCodec;
	}

	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	public Boolean getIsVedio() {
		return isVedio;
	}

	public void setIsVedio(Boolean isVedio) {
		this.isVedio = isVedio;
	}
	
	
	
	
	

}
