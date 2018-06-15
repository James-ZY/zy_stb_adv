package com.gospell.aas.common.ftp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtpUser {

	@JsonProperty(value="userName")
	private String userName;//用户名

	@JsonProperty(value="password")
	private String password;//密码

	@JsonProperty(value="homeDirectory")
	private String homeDirectory;//下载路径

}
