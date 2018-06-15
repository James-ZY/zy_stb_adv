<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='role.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
		<script src="${ctxStatic}/common/language.js"></script>
	<script src="${ctx}/static/scripts/i18n/i18n.js"></script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="role.manage"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/role/"><spring:message code='role.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/role/"><spring:message code='role.list' /></a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form"><spring:message code='role.add' /></a></li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='role.name' /></th><th><spring:message code='grade' /></th>  <shiro:hasPermission name="sys:role:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${list}" var="role">
			<tr>
				<td><a href="form?id=${role.id}">${role.name}</a></td>
				<td>${role.office.name}</td>
 
				<shiro:hasPermission name="sys:role:edit">
				<td>
					<c:if test="${role.id == \"admin\" and role.id != \"1\"}">
						<a href="${ctx}/sys/role/assign?id=${role.id}"><spring:message code='assign' /></a>
					</c:if> 
					<c:if test="${role.id != \"admin\" and role.id == \"1\" }">
						<a href="${ctx}/sys/role/assign?id=${role.id}"><spring:message code='assign' /></a>
						<a href="${ctx}/sys/role/form?id=${role.id}"><spring:message code='update' /></a>
					</c:if> 
					<c:if test="${role.id != \"admin\"  and role.id !=\"1\"}">
						<a href="${ctx}/sys/role/assign?id=${role.id}"><spring:message code='assign' /></a>
						<a href="${ctx}/sys/role/form?id=${role.id}"><spring:message code='update' /></a>
						<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('<spring:message code='deleteRole'/>', this.href)"><spring:message code='delete' /></a>
					</c:if>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>