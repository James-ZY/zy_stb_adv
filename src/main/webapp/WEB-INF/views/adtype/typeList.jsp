<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<style type="text/css">.table td i{margin:0 2px;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 2});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/type");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='combo.setSystem' /></li>
    <li>></li>
    <li><spring:message code='adv.typeManage' /></li>
    <li>></li><li><a href="${ctx}/adv/type"><spring:message code='type.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/type"><spring:message code='type.list' /></a></li>
		<shiro:hasPermission name="sys:type:edit"><li><a href="${ctx}/adv/type/form"><spring:message code='type.add'/></a></li></shiro:hasPermission>
	</ul>
 
	<tags:message content="${message}"/>
  	<div class="tab_content typeList">
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<th class="td-fore1" align="center"><spring:message code='type.name' /></th>
		 <th class="td-fore1" align="center"><spring:message code='type.id' /></th>
		<th align="center"><spring:message code='type.isflag'/></th>
		<th align="center"><spring:message code='type.status'/></th>
		<th class="td-control" align="center"><spring:message code='type.isposition'/><i class="td-open"></i></th>
		<th class="td-fore2"><spring:message code='type.ismove'/></th>
		<th style="width:400px;" class="td-fore2"><spring:message code='type.description' /></th>
		<shiro:hasPermission name="sys:type:edit">
		<th align="center"><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
			<c:forEach items="${list}" var="adType">
				<tr id="${adType.id}" pId="${adType.parent.id ne '-1' ? adType.parent.id : '0'}">
				<td class="td-fore1"><a href="${ctx}/adv/type/form?id=${adType.id}">${adType.typeName}</a></td>
				<td class="td-fore1"><a href="${ctx}/adv/type/form?id=${adType.id}">${adType.typeId}</a></td>
				<td>${fns:getDictLabel(adType.isFlag, 'adv_type_flag', "")}</td>
				<td>${fns:getDictLabel(adType.status, 'adv_type_status', "")}</td>
				<td>${fns:getDictLabel(adType.isPosition, 'adv_type_is_position', "")}</td>
				<td class="td-fore2">${fns:getDictLabel(adType.isMove, 'adv_type_is_move', "")}</td>
				<td class="td-fore2">${adType.typeDescription}</td>
				<shiro:hasPermission name="sys:type:edit">
					<td  style="min-width:140px;">
						 <a href="${ctx}/adv/type/form?id=${adType.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/type/delete?id=${adType.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
						 <a href="${ctx}/adv/type/form?parent.id=${adType.id}"><spring:message code='add.son.adtype' /></a> 					 
	    			</td>
				</shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
 
</body>
</html>