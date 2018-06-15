<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		$(document).ready(function() {
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm(accipiter.getLang("exportData"),accipiter.getLang("systemPrompt"),function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/adv/type/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{"close":true}, 
					bottomText:accipiter.getLang("importFormat")});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/channel");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div class="top_position">
	<div class="top_position_lab"><spring:message code="position"/>:</div>
	<ul>
	<li><spring:message code="operators"/></li>
	<li>></li>
	<li><spring:message code="channel.manage"/></li>
	<li>></li><li><a href="${ctx}/adv/channel"><spring:message code='channel.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/channel"><spring:message code='channel.list' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adChannel" action="${ctx}/adv/channel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				<div class="query-item">
				    <label><spring:message code='channel.id' /> ：</label><form:input path="channelId" htmlEscape="false" maxlength="50" class="input-small"/> 
				</div>
				<div class="query-item">
					<label><spring:message code='channel.name' />：</label><form:input path="channelName" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
					<label><spring:message code='network.name' />：</label>
					<form:select path="adNetWork.id">
								<option value=""><spring:message code="userform.select"/></option>
								<form:options items="${ fns:findAdNetworkAll()}" itemLabel="networkName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="query-item">
					<label><spring:message code='service.id' />：</label><form:input path="serviceId" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
					<label><spring:message code='service.name' />：</label><form:input path="serviceName" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
					<label><spring:message code='channel.adv.type' />：</label>
					<form:select path="type">
						<option value=""><spring:message code="userform.select"/></option>
						<form:options items="${fns:getAdTypeByIsFlag(1)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="query-item">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
				</div>				 
		   </div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='channel.id' /></th>
		<th><spring:message code='channel.name' /></th>
		<th><spring:message code='channel.network' /></th>
		<th><spring:message code='network.name' /></th>
		<th><spring:message code='service.id' /></th>
		<th><spring:message code='service.name' /></th>
		<th><spring:message code='channel.type' /></th>
		<shiro:hasPermission name="sys:channel:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adChannel">
			<tr>
				<td><a href="${ctx}/adv/channel/form?id=${adChannel.id}">${adChannel.channelId}</a></td>
				<td>${adChannel.channelName}</td>
				<c:if test="${empty adChannel.adNetWork}">
					<td></td>
					<td></td>
				</c:if>
				<c:if test="${!empty adChannel.adNetWork}">
					<td>${adChannel.adNetWork.networkId}</td>
					<td>${adChannel.adNetWork.networkName}</td>
				</c:if>
				<td>${adChannel.serviceId}</td>
				<td>${adChannel.serviceName}</td>
				<td>${adChannel.channelType}</td>
				<shiro:hasPermission name="sys:channel:edit">
					<td>
						 <a href="${ctx}/adv/channel/form?id=${adChannel.id}"><spring:message code='adv.design' /></a>
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