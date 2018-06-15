<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title><spring:message code='production.name' /> <spring:message
		code='login' /></title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/common/typica-login.css">
<style type="text/css">
html,body{height:100%;width:100%;font-family:'微软雅黑';overflow:hidden}
body{background-color:#1c77ac; background-image:url(${ctxStatic}/images/icon/loginBg.jpg); background-repeat:no-repeat; background-position:center top; overflow:hidden;padding-top:0}
#mainBody {width:100%;height:100%;position:absolute;z-index:-1;background:url(${ctxStatic}/images/loginBg.jpg/) no-repeat;}
.cloud {position:absolute;top:0px;left:0px;width:100%;height:100%;background:url(${ctxStatic}/images/icon/cloud.png) no-repeat;z-index:1;opacity:0.5;}
#cloud2 {z-index:2;}
.container{position: relative;}
/*login*/
.logintop{height:47px; position:absolute; top:-47px; background:url(${ctxStatic}/images/icon/loginbg1.png) repeat-x;z-index:100; width:100%;}
.logintop span{display:block;height:47px;line-height:47px;color:#fff; text-indent:44px; color:#afc5d2; float:left;background:url(${ctxStatic}/images/icon/loginsj.png) no-repeat 21px 17px;}
.logintop span logintop_close{display:inline-block;background:url(${ctxStatic}/images/icon/arrow_up_alt1.png) no-repeat;width:16px;height:16px;margin-right:5px;margin-top:15.5px;}
.logintop ul{float:right; padding-right:30px;list-style-type:none}
.logintop ul li{float:left; margin-left:20px; line-height:47px;}
.logintop ul li a{color:#afc5d2;}
.logintop ul li a:hover{color:#fff;text-decoration:none}
.loginbody{background:url(${ctxStatic}/images/icon/loginbg3.png) no-repeat center center; width:100%; height:585px; overflow:hidden; position:absolute; top:47px;}
.loginbm{height:50px; line-height:50px; text-align:center; background:url(${ctxStatic}/images/icon//loginbg2.png) repeat-x;position:absolute; bottom:0; width:100%; color:#0b3a58;}
.loginbm a{text-indent:10px}
.loginbm a:hover{text-decoration:none;}

#login-wraper{background-color:#fff;width:500px;text-align:left;margin-top:260px;top:0;margin-left:-275px;}
#login-wraper legend{text-align:center} 
#login-wraper .login-left{width:55%;height:100%;float:left}
#login-wraper .login-right{width:90%;margin-left:5%;padding-top: 40px;}
#login-wraper{*zoom:1;}
#login-wraper:after{clear:both;content:'';visbility:hidden;display:block;}
#login-wraper .login-info{width:448px;border: 1px solid gainsboro;-webkit-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px;}
#login-wraper .control-group {border-bottom: 0px;}
#login-wraper .control-group .controls{margin-bottom:10px;}
#login-wraper .control-group input{outline:none;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;border:0}
#login-wraper .control-group input[type=text]{margin-bottom:0;height:35px;outline:none!important;border:0;width:384px;line-height:35px;}
#login-wraper .control-group input[type=password]{margin-bottom:0;height:35px;outline:none!important;border:0;width:384px;line-height:35px;}
#login-wraper .control-group .lb_user{display:inline-block;margin-left:10px;width:30px;height:30px;background:url(${ctxStatic}/images/icon/user.png) no-repeat center center;vertical-align: middle;margin-right:10px;}
#login-wraper .control-group .lb_pwd{display:inline-block;margin-left:10px;width:30px;height:30px;background:url(${ctxStatic}/images/icon/password.png) no-repeat center center;vertical-align: middle;margin-right:10px;}
#login-wraper .control-group .rememberMe{margin-left:18px;} 

.login-form .footer{margin-top:0}
.login-form .footer .btn-primary{background:#20AED4;color:#fff;width:448px;height:43px;font-size: 25px;margin-left:0;padding:0;line-height: 43px;}
.login-form .footer .btn:hover{background:#1A7CBC;}
.login-form .validateCode label{display:line-block;width:30px;height:30px;margin-right:10px;font-size:16px;background:url(${ctxStatic}/images/icon/icon_yanzhenma.png) no-repeat;vertical-align:middle}
.login-form .validateCode input{}

/* logon */
.login-title {height:auto;width:940px;height:150px;top:100px;position: relative;}
.login-title p{text-align: center;font-size: 35px;height: 50px;line-height: 50px; color: #fff;margin-top: 10px;}
.login-title .login-title-logo {position: relative;left: 50%;background:url(${ctxStatic}/images/icon/new-logo.png) no-repeat ;width:323px;height:76px;margin-left:-161.5px;}
.validateCode{margin-bottom:10px;}
.validateCode #validateCode{width:100px!important}
.validateCode img{margin-bottom:0}
/* error info */
#keeponeMessage{position: absolute;width: 100%}
#messageBox{position: absolute;width: 100%}
.dropdown-menu{min-width:auto}
.tab-control{position: absolute;right: 10px;top: -2px;}
.tab-control .dropdown>a{display: inline-block;width: 85px;padding:0px;background:url(${ctxStatic}/images/icon/qiehuan1.png) no-repeat ;background-size: cover;}
/* #dropdownMenu1{padding: 0;height: 26px;border:0;} */
/* .browser-switch{display: inline-block;background:url(${ctxStatic}/images/icon/qiehuan.png) no-repeat ;width:24px;height:24px;}
.browser-switch:hover{background:url(${ctxStatic}/images/icon/qiehuan.png) no-repeat ;} */
#localeform{position: absolute;top: 0px;right:0;background-color:#ffffff;padding:0;}
.select2-container{display: none; }
.select2-search{display: none}
.china{background:url(${ctxStatic}/images/icon/china1.png);width:32px;height:32px;}
.english{background:url(${ctxStatic}/images/icon/english.png);width:32px;height:32px;}
.login-item{height:32px;width:120px;}
.login-item label{width:32px;height:32px;}
.login-item:hover{cursor: pointer;}
.login-item label{display:block;float:left}
.login-item span{display:block;float:left;height: 32px;line-height: 32px;margin-left: 10px;}
.login-item:after{visibility: hidden;content: "";clear: both;display: block;}
.select-item{display:none;margin-left: 10px;list-style-type: none;width:100px;border: 1px solid gainsboro;border-radius: 4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;}
.select-item li{padding: 2px}
.select-item li:hover{cursor: pointer;}
.select-item label{display:block;float:left;width:16px;height:16px;}
.select-item span{display:block;float:left;margin-left:10px;}
.select-item li:after{visibility: hidden;content: "";clear: both;display: block;}
.select-item .china-select{background:url(${ctxStatic}/images/icon/china.png);}
.select-item .us-select{background:url(${ctxStatic}/images/icon/us.png);}
.help{width:32px;height:32px;position: absolute;top: 20px;right:20px;}
.help a{display:block;background:url(${ctxStatic}/images/help.png);width:32px;height:32px;}
.help:hover{cursor: pointer;}
</style>
<script src="${ctxStatic}/common/backstretch.min.js"></script> 
<script src="${ctxStatic}/jquery-validation/jquery-html5Validate.js"></script>
 <script src="${ctx}/static/libraries/jquery-i18n/jquery.i18n.properties.js"></script>
<script src="${ctx}/static/scripts/i18n/i18n.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		/*设置cookies(有效期30天)*/
		function setCookie(name,value){
			var Days = 30;
			var exp = new Date();
			exp.setTime(exp.getTime() + Days*24*60*60*1000);
			var CookieVlue=encodeURIComponent(value);  
			document.cookie = name + "="+ CookieVlue + ";expires=" + exp.toGMTString();
		}
		//js删除cookies
		function delCookie(name){
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval=getCookie(name);
			if(cval!=null)
			document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		}
		//js读取cookies
		function getCookie(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg))
			return (decodeURIComponent(arr[2]));
			else
			return null;
		}
	    function getLoginIfoFromCookies(){
	    	var userNmae=getCookie("sysUserName");
	    	var userPwd=getCookie("sysUserPwd");
	    	if(userNmae!=null&&userPwd!=null){
	    		$("#username").val(userNmae);
	    		$("#password").val(userPwd);
	    		$("#rememberMe").prop("checked",true);
	    	}else{
	    		return;
	    	}    	
	    }
	    function operateCookies(){
	    	if($("#rememberMe").prop("checked")==true){
	    		setCookie("sysUserName",$("#username").val());
	    		setCookie("sysUserPwd",$("#password").val());
	    	}else{
	    		delCookie("sysUserName");
	    		delCookie("sysUserPwd");
	    	}
	    }
	    getLoginIfoFromCookies();
		$("#loginForm").validate(
				{
					rules : {
						validateCode : {remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
					},
					submitHandler: function(form){
						operateCookies();
				 
						form.submit();
					},
					messages : {
						username: {required:  accipiter.getLang_(messageLang,"input.username") },password: {required: accipiter.getLang_(messageLang,"input.password")},
						validateCode: {remote: accipiter.getLang_(messageLang,"verification.code.error"), required: accipiter.getLang_(messageLang,"input.verification.code") }
					},
					errorLabelContainer : "#messageBox",
					errorPlacement : function(error,element) {error.appendTo($("#loginError").parent());}
				});
		$(".dropdown-menu").on("click","a",function(){
			$(".browser-text").text($(this).text());
		})
 		$("#locale").change(function(){
 			var a = document.getElementById("locale");
 			var b = a.options[a.selectedIndex].value;
 			$("#localeform").attr("action","${ctx}/login?locale="+b);
 			$("#localeform").submit();
		}) 
		var SysType=$("#SysType").text();
		function getSysType(type){
			var SysType=$("#SysType").text();
			if(SysType!=""){
				if(SysType=="en_US"){
					$("#login-language").val("en_US");
					$(".login-item").find("label").removeClass("china");
					$(".login-item").find("label").addClass("english");
					$(".login-item").find("span").text("English");
				}else{
					$(".login-item").find("label").removeClass("english");
					$(".login-item").find("label").addClass("china");
					$(".login-item").find("span").text("中文");
					$("#login-language").val("zh_CN");
				}
			}
			$("#locale").find("option").removeAttr("selected");
			$("#locale").find('option[value='+SysType+']').attr("selected","selected");
			$("#locale").select2();
		}
		getSysType(SysType);
		$(".login-item").on("click",function(){
			if($(this).attr("name")=="0"){
				$(this).attr("name","1");
				$(".select-item").css("display","block");
			}else{
				$(this).attr("name","0");
				$(".select-item").css("display","none");
			}			
		})
		$(".select-item").on("click","li",function(){
			if($(this).hasClass("chinaSelect")){
				$(".select-item").css("display","none");
				$(".login-item").attr("name","0");
				$(".login-item").find("label").removeClass("english");
				$(".login-item").find("label").addClass("china");
				$(".login-item").find("span").text("中文");
				$("#locale").find('optin').removeAttr("selected");
				$("#locale").find('option[value="zh_CN"]').attr("selected","selected");
				$("#locale").select2();
				var b="zh_CN";
				$("#localeform").attr("action","${ctx}/login?locale="+b);
				$("#localeform").submit();
			}else{
				$(".select-item").css("display","none");
				$(".login-item").attr("name","0");
				$(".login-item").find("label").removeClass("china");
				$(".login-item").find("label").addClass("english");
				$(".login-item").find("span").text("English");
				$("#locale").find('optin').removeAttr("selected");
				$("#locale").find('option[value="en_US"]').attr("selected","selected");
				$("#locale").select2();
				var b="en_US";
				$("#localeform").attr("action","${ctx}/login?locale="+b);
				$("#localeform").submit();
			}
		})
/* 		if($(".error").text()!=""){
			$("#keeponeMessage").removeClass("hide");
		}
		$("#username,#password").on("keyup",function(){
			$(".error").text("");
			$("#keeponeMessage").addClass("hide");
		}) */
	});
		  

	// 如果在框架中，则跳转刷新上级页面
	if (self.frameElement && self.frameElement.tagName == "IFRAME") {
	/* 	parent.location.reload(); */
	}
	
	function checkIsChannel(b){
		$("#localeform").attr("action","${ctx}/login?locale="+b);
		$("#localeform").submit();
		return false;
    }
 
	 
</script>
</head>
<body>

	<div class="container">
		 <div style="" id="keeponeMessage" class="alert alert-error <c:if test='${empty param.kickout }'>hide</c:if>"><button data-dismiss="alert" class="close">×</button>
		 
		<label class="error">
			<spring:message code='repeat.login'/>
		</label>
		 
	</div>
 
		<%String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);%>
	<div style="" id="messageBox" class="alert alert-error <%=error==null?"hide":""%>"><button data-dismiss="alert" class="close">×</button>
		<label id="loginError" class="error">
			<spring:message code="${error!=null?\"\":(\"com.gospell.aas.common.security.CaptchaException\".equals(error)?\"verification.code.error\":\"username.or.password.error\")}"/>
		</label>
	</div>
	    <div class="login-title">
	    <div class="login-title-logo pngfix"></div>
	    <p><spring:message code='system.name' /></p>
	    </div>
		<div id="login-wraper">
		    <div class="login-right">
		    <p style="display:none" id="SysType">${locale}</p>
			  <form id="localeform" action="${ctx}/login" method="get" class="breadcrumb form-search">
 			 	   <select  id="locale"  name="locale" style="display:none">
						<option value="zh_CN">中文</option>
						<option value="en_US">english</option>
					</select>
					<div class="login-item" name="0"><label class="china"></label><span value="zh_CN">中文</span></div>
					<ul class="select-item">
					<li class=chinaSelect><label class="china-select"></label><span value="zh_CN">中文</span></li>
					<li class="usSelect"><label class="us-select"></label><span value="en_US">English</span></li>
					</ul>
			 </form>
			   <div id="login">
				<form id="loginForm" class="form login-form" action="${ctx}/login"
					method="post">
					<div class="body">
			 
						<div class="control-group">
							<div class="controls login-info">		
						 			
                                  <label class="lb_user pngfix"></label><input type="text" id="username" name="username"
									class="required" value="${username}" autocomplete= "off"  placeholder="<spring:message code='login.name' />">
							</div>
						</div>
						<div class="control-group">
							<div class="controls login-info">
								<label class="lb_pwd pngfix"></label><input type="password" id="password" name="password"
									class="required"  autocomplete= "off" placeholder="<spring:message code='password' />" />
							</div>
						</div>
						<c:if test="${isValidateCodeLogin}">
							<div class="validateCode">
							<!-- 	<label for="validateCode"></label> -->
								<tags:validateCode name="validateCode"
									inputCssStyle="margin-bottom:0;"/>
							</div>
						</c:if>
						<div class="control-group">
							<div class="controls rememberMe">
								<input type="checkbox" id="rememberMe" name="rememberMe"><span for="rememberMe"><spring:message code='remember.me' /></span>
							</div>
						</div>
					</div>
					<div class="control-group footer">
					  <div class="controls">
					  	   <input class="btn btn-primary" type="submit" value="<spring:message code='login' />"/>
					  	   
					  	   
					  </div>						 						
					</div>					 
				 </form>
			   </div>
		    </div> 
		 </div>

	</div>
    <div class="loginbm">Copyright &copy; 2017 &nbsp;Powered By <a href="http://cn.gospell.com/" target="_blank">GOSPELL</a>&nbsp;<spring:message code='version'/></div>
    <script type="text/javascript"> 
	    var minHeight = 950, minWidth = 1920;
 	    var screenSizeWidth=window.screen.availWidth;
	    var screenSizeHeight=window.screen.availHeight;
	    var seeHeight=document.body.clientHeight;
	    var seeWidth=document.body.clientWidth;
	    function getLoginIfoFromCookies(){
	    	var userNmae=document.cookie
	    	
	    	
	    }
	    

	</script>
</body>
</html>