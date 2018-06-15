<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code="menu.manage"/></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<style type="text/css">.table td i{margin:0 2px;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3});
		});
    	function updateSort() {
			loading(accipiter.getLang("loading"));
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="menu.manage"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/menu/"><spring:message code="menu.list" /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/menu/"><spring:message code='menu.list' /></a></li>
		<shiro:hasPermission name="sys:menu:edit"><li><a href="${ctx}/sys/menu/form"><spring:message code='menu.add' /></a></li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead><tr><th><spring:message code='menu.name' /></th><th><spring:message code='menu.link' /></th><th style="text-align:center;"><spring:message code='menu.order' /></th><th><spring:message code='menu.visibility' /></th><th><spring:message code='menu.auth' /></th><shiro:hasPermission name="sys:menu:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr></thead>
			<tbody>
			<c:forEach items="${list}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '1' ? menu.parent.id : '0'}">
					<td><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a></td>
					<td>${menu.href}</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="sys:menu:edit">
							<input type="hidden" name="ids" value="${menu.id}"/>
							<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
							${menu.sort}
						</shiro:lacksPermission>
					</td>
					<td> 
						<c:choose>
							<c:when test="${menu.isShow eq '1'}">
							<spring:message code='show'/>
							</c:when>
							<c:otherwise>
							<spring:message code='hidden'/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${menu.permission}</td>
					<shiro:hasPermission name="sys:menu:edit"><td>
						<a href="${ctx}/sys/menu/form?id=${menu.id}"><spring:message code='update' /></a>
						<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('<spring:message code='deleteMenu'/>', this.href)"><spring:message code='delete' /></a>
						<a href="${ctx}/sys/menu/form?parent.id=${menu.id}"><spring:message code='add.child.menu' /></a> 
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<shiro:hasPermission name="sys:menu:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="<spring:message code='save.menu.order' />" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>
</body>
</html>
