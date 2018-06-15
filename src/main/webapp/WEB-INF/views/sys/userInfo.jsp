<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<!-- 富文本编辑器引入文件 -->  
<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>    
<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.js"></script> 
	<script type="text/javascript">
	var input01 = $("#input01").attr("value");
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading(accipiter.getLang("loading"));
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text(accipiter.getLang("inputError"));
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
<input type="hidden" id="input01" value="<spring:message code='error'/>"/>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="personal.info"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/user/info"><spring:message code="user.information" /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info"><spring:message code="user.information" /></a></li>
		<li><a href="${ctx}/sys/user/modifyPwd"><spring:message code="update.password" /></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal">
		<tags:message content="${message}"/>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code="user.name" />:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true" type="hidden"/><label class="lbl">${user.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="email" />:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="email" readonly="true" type="hidden"/><label class="lbl">${user.email}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="telephone" />:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50" readonly="true" type="hidden"/><label class="lbl">${user.phone}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="mobilephone" />:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50" readonly="true" type="hidden"/><label class="lbl">${user.mobile}</label>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label"><spring:message code="user.type" />:</label>
			<div class="controls">
				<label class="lbl">${fns:getDictLabel(user.userType, 'sys_user_type', '')}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="user.role" />:</label>
			<div class="controls">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="last.login" />:</label>
			<div class="controls">
				<label class="lbl lbl_s">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="last.login.time" />：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="control-group control-group-remarks">
			<label class="control-label"><spring:message code="memo" />:</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" readonly="true" type="hidden" /><label class="lbl lbl_remarks">${user.remarks}</label>
			</div>
		</div>
		<div class="form-actions actions_hidden">
		</div>
	</form:form>
</body>
</html>