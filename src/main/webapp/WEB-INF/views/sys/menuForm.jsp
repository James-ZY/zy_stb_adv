<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='menu.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
    <div class="top_position"><div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="menu.manage"/></li>
    <li>></li><li>
    <a href="${ctx}/sys/menu/form?id=${menu.id}&parent.id=${menu.parent.id}"> <shiro:hasPermission name="sys:menu:edit"> 
    <c:choose><c:when test="${not empty menu.id}"><spring:message code='menu.update' /></c:when>
        			<c:otherwise><spring:message code='menu.add' /></c:otherwise></c:choose>
    </shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit"><spring:message code='menu.check' /></shiro:lacksPermission></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/menu/"><spring:message code='menu.list' /></a></li>
		<li class="active"><a href="${ctx}/sys/menu/form?id=${menu.id}&parent.id=${menu.parent.id}"><shiro:hasPermission name="sys:menu:edit">
    <c:choose><c:when test="${not empty menu.id}"><spring:message code='menu.update' /></c:when>
        			<c:otherwise><spring:message code='menu.add' /></c:otherwise></c:choose>
    </shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit"><spring:message code='menu.check' /></shiro:lacksPermission></a></li></ul><br/>
	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><spring:message code='parent.menu' />:</label>
			<div class="controls">
                <tags:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
					title="menu" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.name' />:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.link' />:</label>
			<div class="controls">
				<form:input path="href" htmlEscape="false" maxlength="200"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.target' />:</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="10"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.icon' />:</label>
			<div class="controls">
				<tags:iconselect id="icon" name="icon" value="${menu.icon}"></tags:iconselect>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.order' />:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.visibility' />:</label>
			<div class="controls controls_s">
				<form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='menu.auth' />:</label>
			<div class="controls">
				<form:input path="permission" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>