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
		<li><spring:message code='combo.setSystem' /></li>
		<li>></li>
		<li><spring:message code='districtCategory.manage' /></li>
		<li>></li><li><a href="${ctx}/adv/districtCategory"><spring:message code='districtCategory.list' /></a></li></ul></div>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/adv/districtCategory"><spring:message code='districtCategory.list' /></a></li>
	<shiro:hasPermission name="sys:districtCategory:edit"><li><a href="${ctx}/adv/districtCategory/form"><spring:message code='districtCategory.add'/></a></li></shiro:hasPermission>
</ul>

<tags:message content="${message}"/>
<div class="tab_content">
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<th><spring:message code='districtCategory.name' /></th>
		<th><spring:message code='districtCategory.id' /></th>

		<th><spring:message code='districtCategory.remarks' /></th>
		<shiro:hasPermission name="sys:districtCategory:edit">
			<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${list}" var="adCategory">
			<tr id="${adCategory.id}" pId="${adCategory.parent.id ne key ? adCategory.parent.id : '0'}">
				<td><a href="${ctx}/adv/districtCategory/form?id=${adCategory.id}">${adCategory.categoryName}</a></td>
				<td><a href="${ctx}/adv/districtCategory/form?id=${adCategory.id}">${adCategory.categoryId}</a></td>

				<td>${adCategory.remarks}</td>
				<shiro:hasPermission name="sys:districtCategory:edit">
					<td>
						<a href="${ctx}/adv/districtCategory/form?id=${adCategory.id}"><spring:message code='update' /></a>
						<a href="${ctx}/adv/districtCategory/delete?id=${adCategory.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
						<a href="${ctx}/adv/districtCategory/form?parent.id=${adCategory.id}"><spring:message code='add.son.category' /></a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>