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
						$("#searchForm").attr("action","${ctx}/adv/position/export");
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
			$("#searchForm").attr("action","${ctx}/adv/position");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adv.show' /></li>
    <li>></li>
    <li><spring:message code='adv.showStyleManage' /></li>
    <li>></li><li><a href="${ctx}/adv/position"><spring:message code='position.list' /></a></li></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/position"><spring:message code='position.list' /></a></li>
		<shiro:hasPermission name="adv:position:edit"><li><a href="${ctx}/adv/position/form"><spring:message code='position.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adPosition" action="${ctx}/adv/position/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div class="query-item">
				<label><spring:message code='position.id' /> ：</label><form:input path="pointId" htmlEscape="false" maxlength="50" class="input-small"/> 
			</div>
			<div class="query-item">
				<label><spring:message code='position.name' />：</label><form:input path="positionName" htmlEscape="false" maxlength="50" class="input-small"/>
			</div>			
		</div> 
		<div>
			<div class="query-item">
				<label><spring:message code='range.type' />：</label>
				<form:select path="adType.id">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getAdTypeByIsPosition(1)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
			    </form:select>
			</div>
			<div class="query-item">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='position.id' /></th>
		<th><spring:message code='position.name' /></th>
		<th><spring:message code='position.begin'/></th>
		<th><spring:message code='position.end'/></th>
		<th><spring:message code='position.velocity'/></th>
		<th><spring:message code='position.advtype'/></th>
		<shiro:hasPermission name="adv:position:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adPosition">
			<tr>
				<td><a href="${ctx}/adv/position/form?id=${adPosition.id}">${adPosition.pointId}</a></td>
				<td>${adPosition.positionName}</td>
				<td>${adPosition.begin}</td>
				<td>${adPosition.end}</td>
				<td>${adPosition.velocity}</td>
				<c:choose>
					<c:when test="${!empty adPosition.adType}">
						<td>${adPosition.adType.typeName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<shiro:hasPermission name="adv:position:edit">
					<td>
						 <a href="${ctx}/adv/position/form?id=${adPosition.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/position/delete?id=${adPosition.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
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