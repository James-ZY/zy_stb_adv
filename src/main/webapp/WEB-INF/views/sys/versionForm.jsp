<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='version.manage'/></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
</head>
<body>
<input type="hidden" id="input01" value="<spring:message code='error'/>"/>
<div class="top_position">
	<div class="top_position_lab"><spring:message code="position"/>:</div>
	<ul>
		<li><spring:message code="combo.setSystem"/></li>
		<li>></li>
		<li><a href="${ctx}/sys/version/form"><spring:message code="version.manage" /></a></li></ul></div>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/sys/version/form"><spring:message code="version.manage" /></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="adVersion" action="${ctx}/sys/version/form" method="post" class="form-horizontal">
	<tags:message content="${message}"/>

	<div class="control-group">
		<label class="control-label"><spring:message code="version.id" />:</label>
		<div class="controls">
			<form:input path="versionID" htmlEscape="false" maxlength="50" class="required" readonly="true" type="hidden"/><label class="lbl">${adVersion.versionID}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><spring:message code="version.companyName" />:</label>
		<div class="controls">
			<form:input path="companyName" htmlEscape="false" maxlength="50" class="email" readonly="true" type="hidden"/><label class="lbl">${adVersion.companyName}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><spring:message code="version.createDate" />:</label>
		<div class="controls">
			<form:input path="createDate" htmlEscape="false" maxlength="50" readonly="true" type="hidden"/><label class="lbl"><fmt:formatDate value="${adVersion.createDate}" pattern="yyyy-MM-dd"/></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><spring:message code="version.companyUrl" />:</label>
		<div class="controls">
			<form:input path="companyUrl" htmlEscape="false" maxlength="50" readonly="true" type="hidden"/><label class="lbl">${adVersion.companyUrl}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><spring:message code="version.companyLogo" />:</label>
		<div class="controls" style="width: 323px;height: 76px;background:url(${adVersion.companyLogo}) no-repeat;background-color: #1c77ac;">
		</div>
	</div>
</form:form>
</body>
</html>