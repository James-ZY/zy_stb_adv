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
						$("#searchForm").attr("action","${ctx}/adv/program/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{"关闭":true}, 
					bottomText:accipiter.getLang("importFormat")});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/program");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/program"><spring:message code='program.list' /></a></li>
		<shiro:hasPermission name="adv:program:edit"><li><a href="${ctx}/adv/program/form"><spring:message code='program.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adExternalProgram" action="${ctx}/adv/program/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<label><spring:message code='program.id' /> ：</label><form:input path="programId" htmlEscape="false" maxlength="50" class="input-small"/> 
			<label><spring:message code='program.name' />：</label><form:input path="programName" htmlEscape="false" maxlength="50" class="input-small"/>
			<label><spring:message code='program.version' />：</label><form:input path="programVersion" htmlEscape="false" maxlength="50" class="input-small"/>
			&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='program.id' /></th>
		<th><spring:message code='program.name' /></th>
		<th><spring:message code='program.version'/>
		<th><spring:message code='program.createtime'/>
		<th><spring:message code='program.createuser'/>
		<th><spring:message code='program.path'/>
		<th><spring:message code='program.remarks' /></th>
		<shiro:hasPermission name="adv:program:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adExternalProgram">
			<tr>
				<td><a href="${ctx}/adv/program/form?id=${adExternalProgram.id}">${adExternalProgram.programId}</a></td>
				<td>${adExternalProgram.programName}</td>
				<td>${adExternalProgram.programVersion}</td>
				<td>${adExternalProgram.createDate}</td>
				<c:choose>
						<c:when test="${!empty adExternalProgram.createBy}">
							<td>${adExternalProgram.createBy.name}</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
				<td>${adExternalProgram.programPath}</td>
				<td>${adExternalProgram.remarks}</td>
				<shiro:hasPermission name="adv:program:edit">
					<td>
						 <a href="${ctx}/adv/program/form?id=${adExternalProgram.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/program/delete?id=${adExternalProgram.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
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