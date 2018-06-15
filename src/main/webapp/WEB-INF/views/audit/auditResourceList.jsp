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
			// 表格排序
			var orderBy = $("#orderBy").val().split(" ");		
			$("#contentTable th.sort").each(function(){
				if ($(this).hasClass(orderBy[0])){
					orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
					$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
				}
			});
			$("#contentTable th.sort").click(function(){
				var order = $(this).attr("class").split(" ");
				var sort = $("#orderBy").val().split(" ");
				for(var i=0; i<order.length; i++){
					if (order[i] == "sort"){order = order[i+1]; break;}
				}
				if (order == sort[0]){
					sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
					$("#orderBy").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
				}else{
					$("#orderBy").val(order+" ASC");
				}
				page();
			});
			$("#btnExport").click(function(){
				top.$.jBox.confirm(accipiter.getLang("exportData"),accipiter.getLang("systemPrompt"),function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/adv/control/export");
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
			$("#searchForm").attr("action","${ctx}/adv/control");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul><li><spring:message code="combo.setSystem"/></li><li>></li>
    <li><spring:message code='adv.typeManage' /></li>
    <li>></li><li><a href="${ctx}/adv/control"><spring:message code='type.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/control/audit"><spring:message code='control.list' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adControll" action="${ctx}/adv/control/audit" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<label><spring:message code='adv.type' /> ：</label><form:input path="adType.id" htmlEscape="false" maxlength="50" class="input-small"/> 
			<label><spring:message code='control.status' />：</label>
			<form:select path="status">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_control_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
			&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='serialNumber' /></th>
		<th><spring:message code='type' /></th>
		<th><spring:message code='control.format'/>
		<th><spring:message code='control.width'/>
		<th><spring:message code='control.height' /></th>
		<th><spring:message code='control.createpeople' /></th>
		<th><spring:message code='control.status' /></th>
		<th><spring:message code='control.auditpeople' /></th>
		<th><spring:message code='control.reason' /></th>
		<shiro:hasPermission name="adv:material:claim">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adControll" varStatus="status">
			<tr>
				<td><a href="${ctx}/adv/control/form?id=${adControll.id}">${status.index}</a></td>
				<c:choose>
					<c:when test="${!empty adControll.adType}">
						<td>${adControll.adType.typeName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<td>${adControll.fileFormat}</td>
				<td>${adControll.width}</td>
				<td>${adControll.height}</td>
					<td>
					<c:choose>
						<c:when test="${not empty adControll.createBy}">
							${adControll.createBy.name}
						</c:when>
					 
					</c:choose>
				</td>
				<td>${fns:getDictLabel(adControll.status, 'adv_control_status', "")}</td>
				<td>
					<c:choose>
						<c:when test="${not empty adControll.updateBy}">
							${adControll.updateBy.name}
						</c:when>
					 
					</c:choose>
				</td>
				<td>${adControll.reason}</td>
				 
					
						 <shiro:hasPermission name="adv:material:claim">
						 <td>
						 	 <c:choose>
						 	 	<c:when test="${0 == adControll.status && !empty adControll.id}">
						 	 		 <a href="${ctx}/adv/control/claim?id=${adControll.id}"><spring:message code='claim' /></a>
						 	 	</c:when>
						 	 	
						 	 	<c:when test="${1 == adControll.status && !empty adControll.id}">
						 	 		 <a href="${ctx}/adv/control/auditform?id=${adControll.id}"><spring:message code='audit' /></a>
						 	 	</c:when>
						 	 	 
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