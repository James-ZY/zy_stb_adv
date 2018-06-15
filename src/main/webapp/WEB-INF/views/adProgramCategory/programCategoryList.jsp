<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<style type="text/css">.table td i{margin:0 2px;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 2});
		});
		 
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='programCategory' /></li>
    <li>></li>
    <li><spring:message code='programCategory.manage' /></li>
    <li>></li><li><a href="${ctx}/adv/programCategory"><spring:message code='programCategory.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/programCategory"><spring:message code='programCategory.list' /></a></li>
		<shiro:hasPermission name="sys:programCategory:edit"><li><a href="${ctx}/adv/programCategory/form"><spring:message code='programCategory.add'/></a></li></shiro:hasPermission>
	</ul>
 
	<tags:message content="${message}"/>
 	<div class="tab_content">
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<th><spring:message code='programCategory.name' /></th>
		<th><spring:message code='programCategory.id' /></th>

		<th><spring:message code='programCategory.remarks' /></th>
		<shiro:hasPermission name="sys:programCategory:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
			<c:forEach items="${list}" var="adProgramCategory">
				<tr id="${adProgramCategory.id}" pId="${adProgramCategory.parent.id ne '1' ? adProgramCategory.parent.id : '0'}">
				<td><a href="${ctx}/adv/programCategory/form?id=${adProgramCategory.id}">${adProgramCategory.categoryName}</a></td>
				<td><a href="${ctx}/adv/programCategory/form?id=${adProgramCategory.id}">${adProgramCategory.categoryId}</a></td>
		
				<td>${adProgramCategory.remarks}</td>
				<shiro:hasPermission name="sys:programCategory:edit">
					<td>
						 <a href="${ctx}/adv/programCategory/form?id=${adProgramCategory.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/programCategory/delete?id=${adProgramCategory.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
						 <a href="${ctx}/adv/programCategory/form?parent.id=${adProgramCategory.id}"><spring:message code='add.son.programCategory' /></a> 					 
	    			</td>
				</shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
	</table>
 </div>
</body>
</html>