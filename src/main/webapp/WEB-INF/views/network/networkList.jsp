<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='adelement.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style adelement="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
 
	<script type="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		$(document).ready(function() {
			 
		 
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/network");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	 <div class="top_position">
	 <div class="top_position_lab"><spring:message code="position"/>:</div>
	 <ul>
 
	 <li><spring:message code="network.manage"/></li>
	 <li>></li>
	 <li><a href="${ctx}/adv/network"><spring:message code='network.list' /></a></li>
	 </ul>
	 </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/network"><spring:message code='network.list' /></a></li>
		<!--<shiro:hasPermission name="sys:operators:edit"><li><a href="${ctx}/adv/operators/form"><spring:message code='network.add'/></a></li></shiro:hasPermission>-->
	</ul>
	<form:form id="searchForm" modelAttribute="adNetwork" action="${ctx}/adv/network/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div class="query-item">
				<label><spring:message code='network.id' /> ：</label><form:input path="networkId" htmlEscape="false" maxlength="50" class="input-small"/> 
			</div>
			<div class="query-item">
				<label><spring:message code='network.name' />：</label><form:input path="networkName" htmlEscape="false" maxlength="50" class="input-small"/>
			</div>
			<div class="query-item">
				<label><spring:message code='network.operators' />：</label><form:input path="adOperators.operatorsName" htmlEscape="false" maxlength="50" class="input-small"/>
			</div>
			<div class="query-item">
	             <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>
		</div>
	</form:form>
	
	<tags:message content="${message}"/>
	<div class="tab_content">
 
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='network.id' /></th>
		<th><spring:message code='network.name' /></th>
		<th><spring:message code='network.ip' /></th>
		<th><spring:message code='network.port' /></th>
		<th><spring:message code='network.operators' /></th>
		<th><spring:message code='operators.area' /></th>
		<th><spring:message code='not.channel.type' /></th>
		 <th><spring:message code='valid.time' /></th>
		<shiro:hasPermission name="sys:network:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adNetwork">
			<tr>
				<td>${adNetwork.networkId}</td>
				<td>${adNetwork.networkName}</td>
				<td>${adNetwork.ip}</td>
				<td>${adNetwork.port}</td>
				<c:if test="${empty adNetwork.adOperators}">
				<td></td>
				</c:if>
				<c:if test="${!empty adNetwork.adOperators}">
						<td>${adNetwork.adOperators.operatorsName}</td>
				</c:if>
				<td>${adNetwork.area}</td>
				<td>${adNetwork.typeName}</td>
	 			<td><fmt:formatDate value="${adNetwork.validDate}" pattern="yyyy-MM-dd"/></td>
			 	<shiro:hasPermission name="sys:network:edit">
					<td>
						<a href="${ctx}/adv/network/form?id=${adNetwork.id}"><spring:message code='update' /></a>
						<c:choose>
							<c:when test="${adNetwork.status == 1}">
							 <a href="${ctx}/adv/network/stop?id=${adNetwork.id}"><spring:message code='stop.network' /></a>
							</c:when>
							<c:otherwise>
								 <a href="${ctx}/adv/network/start?id=${adNetwork.id}"><spring:message code='network.start' /></a>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${adNetwork.playStatus == 1}">
							 <a href="${ctx}/adv/network/shutdown?id=${adNetwork.id}"><spring:message code='shutdown.network' /></a>
							</c:when>
							<c:otherwise>
								 <a href="${ctx}/adv/network/restart?id=${adNetwork.id}"><spring:message code='restart.network' /></a>
							</c:otherwise>
						</c:choose>
	    			</td>
	    		</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>