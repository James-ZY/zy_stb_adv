<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page language="java" import="java.text.SimpleDateFormat"%>
<%@page language="java" import="java.lang.String"%>
<%@page language="java" import="java.math.BigDecimal"%>
<%@page language="java" import="java.lang.Math"%>
<html>
<head>
<title><spring:message code='production.name' /></title>
<%@include file="/WEB-INF/views/include/dialog.jsp"%>
<meta name="decorator" content="default" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
<style type="text/css">
html,body,#main{height:100%;}
#main .comment{background-color:#20AED4;}
#main .container-fluid { padding: 10px 4px 10px 4px;background-color:#fff;margin:0 5px 0 5px}
#main .container-fluid>#content{background-color:#fff;}
#header { margin: 0 /* 0 10px */; position: static; }
#header li { font-size: 14px; }
#footer { margin: 8px 0 0 0; padding: 3px 0 0 0; font-size: 11px; text-align: center; border-top: 2px solid #0663A2; }
#footer, #footer a { color: #999; }
.navbar .brand{position: relative;margin:0px; margin-top:0; width:300px; height:68px;line-height:68px;text-shadow:none;color:#333;  }
.navbar .nav-right{margin:0px; margin-top:0; width:auto; height:88px;line-height:68px;/* background: url(${ctxStatic}/images/icon/topright.jpg) no-repeat; */text-shadow:none;float:right;background-position: right;position: relative;}
.logo{float:left; margin-top:-6px; margin-left:20px; position:relative; top:0px;display:inline-block; height:45px; width:180px;background:url("${ctxStatic}/libraries/global/styles/pictures/index/banner-logo-cn.png") no-repeat;background-size:168px 45px;}
.navbar-fixed-top .navbar-inner, .navbar-static-top .navbar-inner{-webkit-box-shadow: none !important;-moz-box-shadow: none !important;box-shadow: none !important;height:88px;border:0px;}
.navbar .nav-right li{margin-top:15px;}
#menuFrame{border-top:1px solid #B7D5DF;border-bottom:1px solid #B7D5DF;box-shadow: 0 0 0.5px #B7D5DF;background-color: #383E4C;} 
#mainFrame{border:1px solid #B7D5DF;box-shadow: 0 0 0.5px #B7D5DF;-moz-box-shadow:0 0 0.5px #B7D5DF;-webkit-box-shadow: 0 0 0.5px #B7D5DF;}
.navbar-inner{font-family: "微软雅黑";font-weight: normal;color:#fff;height:88px!important;/* background: none;background-image: none; */background-color: #20AED4;}
.navbar .nav{height:88px; position: relative;margin-top:0;}
.navbar .menu{height:88px;line-height:88px;}
.navbar .menu .menu{border-right:2px solid #35BBE1;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;font-size:16px;}

/*导航图片样式  */
.navbar .nav .icon_27>a{background:url("${ctxStatic}/images/nav/mianban.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_2>a{background:url("${ctxStatic}/images/nav/xitong.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_47>a{background:url("${ctxStatic}/images/nav/wangluo.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_44>a{background:url("${ctxStatic}/images/nav/yunying.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_43>a{background:url("${ctxStatic}/images/nav/guanggao.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_58>a{background:url("${ctxStatic}/images/nav/tongji.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_87>a{background:url("${ctxStatic}/images/nav/taocan.png") no-repeat;background-position: 15px;}  

.navbar .nav .icon_27[class="menu icon_27 active"]>a{background:url("${ctxStatic}/images/nav/mianban1.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar .nav .icon_2[class="menu icon_2 active"]>a{background:url("${ctxStatic}/images/nav/xitong1.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar .nav .icon_47[class="menu icon_47 active"]>a{background:url("${ctxStatic}/images/nav/wangluo1.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar .nav .icon_44[class="menu icon_44 active"]>a{background:url("${ctxStatic}/images/nav/yunying1.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar .nav .icon_43[class="menu icon_43 active"]>a{background:url("${ctxStatic}/images/nav/guanggao1.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar .nav .icon_58[class="menu icon_58 active"]>a{background:url("${ctxStatic}/images/nav/tongji1.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;} 
.navbar .nav .icon_87[class="menu icon_87 active"]>a{background:url("${ctxStatic}/images/nav/taocan2.png") no-repeat;background-position: 15px;background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;} 
/*悬停样式  */
.navbar .nav .icon_27>a:hover{background:url("${ctxStatic}/images/nav/mianban1.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_2>a:hover{background:url("${ctxStatic}/images/nav/xitong1.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_47>a:hover{background:url("${ctxStatic}/images/nav/wangluo1.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_44>a:hover{background:url("${ctxStatic}/images/nav/yunying1.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_43>a:hover{background:url("${ctxStatic}/images/nav/guanggao1.png") no-repeat;background-position: 15px;}
.navbar .nav .icon_58>a:hover{background:url("${ctxStatic}/images/nav/tongji1.png") no-repeat;background-position: 15px;} 
.navbar .nav .icon_87>a:hover{background:url("${ctxStatic}/images/nav/taocan2.png") no-repeat;background-position: 15px;} 
.navbar #menu .active>a{background-color: #fff;color:#23ADD2;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar #menu .active>a:hover{background-color: #fff;color:#23ADD2;}
.navbar #menu .menu>a:hover{background-color: #fff;-webkit-box-shadow:none;-moz-transition:none;box-shadow: none;}
.navbar .nav>li>a:hover{color:#23ADD2;}
.pull-right{height:58px;}
.pull-right>li{height:58px;line-height:58px;}
#themeSwitch{display:none;}
.adv-logo{/* position: absolute;top: 0; */width:100%;height:100%;background:url("${ctxStatic}/images/icon/logo1.png") no-repeat;background-position: 0 10px;}
.navbar .nav li.dropdown.open>.dropdown-toggle, .navbar .nav li.dropdown.active>.dropdown-toggle, .navbar .nav li.dropdown.open.active>.dropdown-toggle{color:#fff;background-color:transparent}
.navbar .pull-right>.dropdown>.dropdown-toggle{color:#fff}
.dropdown-menu>li>a:hover{background-color:#3EAFE0;background-image: none;}
.navbar .pull-right>.logout>a{color:#fff}
.navbar .pull-right>.logout>a:hover{color:#fff;}
.footer{width:100%;background-color:#20AED4;height:40px;text-align:center;line-height:40px;position: relative;}
.footer #dt{position: absolute;top: 0;right: 20px;font-size: 14px;}
.footer a{color:#fff}
.footer a:hover{text-decoration:none;} 
.navbar .nav>li>a{padding-top:0;padding-bottom:0;}
.help{width:32px;height:32px;}
.navbar .nav .help a{display:block;background:url(${ctxStatic}/images/help.png) no-repeat;width:32px;height:32px;margin-top: 12px;}
.help:hover{cursor: pointer;}
.sysTime{position: absolute;height: 30px;width: 100%;top: 58px;}
.sysTime label{inline-block}
.sysTime span{inline-block}

</style>
<script src="${ctxStatic}/common/respond.min.js"></script>
<script type="text/javascript">
	//从服务器上获取初始时间 
 
	function getSysTime(){
		var systime={};
		var url="${ctx}"+"/find_system_date";
		$.ajaxSetup({"async":false});
		$.post(url,function(data){
			systime=data;
		})
		return systime;
	}
	var systime=getSysTime();
	var zoneFormat=systime.zoneFormat;
	var zone = systime.timeZone;
	var d=new Date((getSysTime().time)); //创建一个Date对象 
	var localTime = d.getTime(); 
    var offset=d.getTimezoneOffset(); //获得当地时间偏移的毫秒数 
    var localOffset = offset*60000;
    var utc = localTime + localOffset;
    var serverOffetSet = 3600000*zone;//服务器的时间偏移毫秒数
    var bombay = utc + serverOffetSet;
	var currentDate = new Date(bombay);  
 
	function run() 
	{ 
		currentDate.setSeconds(currentDate.getSeconds()+1); 
		var monthEngish=new Array("January","February","March","April","May","June","July","August","September","October","November","December");
		var time = "";
		var year = currentDate.getFullYear();
		var month = currentDate.getMonth() + 1;
		var day = currentDate.getDate();
		var hour = currentDate.getHours();
		var minute = currentDate.getMinutes();
		var second = currentDate.getSeconds();
		var dateString=year+"-"+month+"-"+day;
		if(hour < 10){
			time += "0" + hour;
		}else{
			time += hour;
		}
		time += ":";
		if(minute < 10){
			time += "0" + minute;
		}else{
			time += minute;
		}
		time += ":";
		if(second < 10){
			time += "0" + second;
		}else{
			time += second;
		}
		document.getElementById("sysTime").setAttribute("name",dateString);
		document.getElementById("sysTimeLabel").innerHTML =accipiter.getLang_(messageLang,"System.time");
		var control=accipiter.getLocale();
		if(control==0){
			document.getElementById("sysTime").innerHTML = year+"年"+month+"月"+day+"日" + time+"&nbsp"+"("+zoneFormat+")";
		}else{
			document.getElementById("sysTime").innerHTML = monthEngish[currentDate.getMonth()]+"&nbsp;"+day+","+"&nbsp;"+year+"&nbsp;"+ time+"&nbsp"+"("+zoneFormat+")"; 
		}    
	} 
	window.setInterval("run();", 1000); 
	function loadJsAndCssFile(filename, filetype){
		if (filetype=="js"){
			var fileref=document.createElement('script');
			fileref.setAttribute("type","text/javascript");
			fileref.setAttribute("src", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
		else if (filetype=="css"){
			var fileref=document.createElement("link");
			fileref.setAttribute("rel", "stylesheet");
			fileref.setAttribute("type", "text/css");
			fileref.setAttribute("href", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
	}
	function loadFile(){
		var control=accipiter.getLocale();
		if(control==0){
			var headCss="/advs/static/common/cn-index.css";
			var cntabs="/advs/static/common/cn-nav-tabs.css";
				loadJsAndCssFile(headCss,"css");
				loadJsAndCssFile(cntabs,"css");
			
		}else{
			var headCss="/advs/static/common/us-index.css";
			var ustabs="/advs/static/common/us-nav-tabs.css";
			loadJsAndCssFile(headCss,"css");
			loadJsAndCssFile(ustabs,"css");
		}		
	}
	loadFile();
	$(document).ready(function() {
		var language=accipiter.getLocale();
		$("#menu a.menu").click(function() {
			if(language!=accipiter.getLocale()){
				location.reload();
			}
			$("#menu li.menu").removeClass("active");
			$(this).parent().addClass("active");
			if (!$("#openClose").hasClass("close")) {
				$("#openClose").click();
			}
		});
		function getHelpDocument(){
			$.ajaxSetup({cache:false});
			var helpUrl="${ctx}/sys/help/find_help_file";
			$.get(helpUrl,function(data){
				if(data!=""){
					$(".help a").attr("href",encodeURI(data));
				}else{
					$(".help a").attr("href",'');
				}
			});
		}
		getHelpDocument();
		$(".help a").on("click",function(){
			var href=$(".help a").attr("href");
			if(href==""){
				$.jBox.tip(accipiter.getLang_(messageLang,"help.alarm"));
				return false;
			}
		})
	});
	
</script>
</head>
<body id="adv-content">
	<%-- <link href="${ctxStatic}/common/header.css" rel="stylesheet"/> --%>
	<!--[if lt IE 9]>
	  <script src="${ctxStatic}/common/html5.js"></script>
	  <script src="${ctxStatic}/common/respond.min.js"></script>
	<![endif]-->
	<div id="main">
		<div id="header" class="navbar navbar-fixed-top">
	      <div class="navbar-inner">
	      	 <div class="brand"><%-- <spring:message code='production.name' /> --%><div class="adv-logo"></div></div>
	         <div class="nav-collapse">
	           <ul id="menu" class="nav">
				 <c:set var="firstMenu" value="true"/>
				 <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
					<c:if test="${menu.parent.id eq '1' && menu.isShow eq '1'}">
						<li class="menu icon_${menu.id} ${firstMenu ? 'active':''}"><a class="menu" href="${ctx}/sys/menu/tree?parentId=${menu.id}" target="menuFrame" >${menu.name}</a></li>
						<c:if test="${firstMenu}">
							<c:set var="firstMenuId" value="${menu.id}"/>
						</c:if>
						<c:set var="firstMenu" value="false"/>
					</c:if>
				 </c:forEach>
	           </ul>
	           <div class="nav-right">
	           <ul class="nav pull-right">
				 <li id="themeSwitch" class="dropdown">
			       	<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="<spring:message code="switch.theme"/>"><i class="icon-th-large"></i></a>
				    <ul class="dropdown-menu">
				      <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
				    </ul>
				    <!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
			     </li>
			  	 <li class="dropdown">
				    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="<shiro:principal property="name"/>"><spring:message code='hello'/>, <shiro:principal property="name"/></a>
				    <ul class="dropdown-menu">
				      <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; <spring:message code='user.information'/></a></li>
				      <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  <spring:message code='update.password'/></a></li>
				    </ul>
			  	 </li>
			  	 <li class="logout"><a href="${ctx}/logout" title="<spring:message code='logout'/>"><spring:message code='quit'/></a></li>
			  	  <li class="help"><a href="" title="<spring:message code='system.help' />"></a></li>
			  	 <li>&nbsp;</li>
	           </ul>
	          <!--  <div class="sysTime"><label>系统时间:</label><span class="time"></span></div> -->
	           </div>
	         </div><!--/.nav-collapse -->
	      </div>
	    </div>
	    <div class="comment">
	    <div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left">
					<iframe id="menuFrame" name="menuFrame" src="${ctx}/sys/menu/tree?parentId=${firstMenuId}" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
				<div id="openClose" class="close">&nbsp;</div>
				<div id="right">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;"
						scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
				</div>
			</div>
		</div>
		</div>
		<div class="footer">
		Copyright &copy; 2017 &nbsp;Powered By <a href="http://cn.gospell.com/" target="_blank">GOSPELL</a>&nbsp;<spring:message code='version'/>
		<div id="dt"><label id="sysTimeLabel"></label>&nbsp;<span id="sysTime"></span></div>
		</div>
	</div>
	<script type="text/javascript"> 
		var leftWidth = ""; // 左侧窗口大小
		var minHeight = 500, minWidth = 1220; 
		function getLeftWidth(){
			var control=accipiter.getLocale();
			if(control==0){
				leftWidth="180";
				minWidth = 1220;
			}else{
				var headCss="/advs/static/common/us-index.css";
				leftWidth = "220";
				minWidth = 1310;
			}		
		}
		getLeftWidth();
		function wSize(){
		  /*   var minHeight = 973, minWidth = 1920; */
		 	var strs=getWindowSize().toString().split(",");
			$("#menuFrame, #mainFrame, #openClose,.left,.right,.row-fluid,.container-fluid").height((strs[0]<minHeight?minHeight:strs[0])-$("#header").height()-$("#footer").height()-32-20);
			$("#openClose").height($("#openClose").height()-5);
			if(strs[1]<minWidth){
				$("#main").css("width",minWidth-10);
/* 				$("html,body").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"}); */
				 $("body").css({"overflow":"hidden","overflow-x":"auto","erflow-y":"hidden"});
			}else{
				$("#main").css("width","auto");
				$("html,body").css({"overflow":"hidden","overflow-x":"hidden","overflow-y":"hidden"});
			}
			$("#right").width($("#content").width()-$("#left").width()-$("#openClose").width()-5);
		}

	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>