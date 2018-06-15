package com.gospell.aas.common.utils.ffmpeg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConvertDTO {
	
	@JsonProperty(value="input")
	private String input;//输入文件
	
	@JsonProperty(value="output")
	private String output;//输出源文件
	
	@JsonProperty(value="imagePath")
	private String imagePath;//视频截图
	
	@JsonProperty(value="status")
	private Integer status;//状态(0为成功，1为失败)

	@JsonProperty(value="message")
	private String message;//失败原因
	
	
	public static final Integer CONVERT_SUCCESS_STATUS=0;
	public static final Integer CONVERT_FAIL_STATUS=1;
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	

}
