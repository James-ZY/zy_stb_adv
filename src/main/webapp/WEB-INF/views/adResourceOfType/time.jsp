<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@page language="java" import="java.text.SimpleDateFormat"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta name="decorator" content="default"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <%
      Calendar rightNow = Calendar.getInstance();
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>
<script language="javascript"> 
//从服务器上获取初始时间 
var currentDate = new Date(<%=new java.util.Date().getTime()%>); 


function run() 
{ 
currentDate.setSeconds(currentDate.getSeconds()+1); 
var time = "";
var year = currentDate.getFullYear();
var month = currentDate.getMonth() + 1;
var day = currentDate.getDate();
var hour = currentDate.getHours();
var minute = currentDate.getMinutes();
var second = currentDate.getSeconds();
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
document.getElementById("dt").innerHTML = year+"年"+month+"月"+day+"日" + time;     
} 
window.setInterval("run();", 1000); 
</script> 
<div id="dt">自动显示时间。。。。</div>
  </body>
</html>