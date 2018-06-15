<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='user.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctxStatic}/common/language.js"></script>
	<script src="${ctx}/static/scripts/common/common.js"></script>
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
						$("#searchForm").attr("action","${ctx}/sys/user/export");
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
			$("#searchForm").attr("action","${ctx}/sys/user/");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="sys.user"/></li>
    <li>></li>
    <li><spring:message code="user.manage"/></li>
    <li>></li><li><a href="${ctx}/sys/user/"><spring:message code="user.list" /></a></li></ul></div>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading(accipiter.getLang('loading'));"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="<spring:message code='user.import'/>" />
			<a href="${ctx}/sys/user/import/template"><spring:message code='download.template' /></a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/"><spring:message code='user.list' /></a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form"><spring:message code='user.add' /></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<label><spring:message code='grade' />：</label><tags:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="officeform.grade" url="/sys/office/treeData?type=1" cssClass="input-small"/>
			<label><spring:message code='login.name' />：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-small"/>
			<%-- <label><spring:message code='department' />：</label><tags:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="company" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"/> --%>
			<label style="margin-left:23px;"><spring:message code='user.name' />：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small" style="margin-left:4px;"/>
			&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			&nbsp; 
			<!--   <input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>-->
			<!--&nbsp;<input id="btnImport" class="btn btn-primary" type="button" value="<spring:message code='import' />"/>-->
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='grade' /><%-- </th><th><spring:message code='department' /> --%></th><th class="sort loginName"><spring:message code='login.name' /></th><th class="sort name"><spring:message code='user.name' /></th><th><spring:message code='telephone' /></th><th><spring:message code='mobilephone' /></th><th><spring:message code='role' /></th><shiro:hasPermission name="sys:user:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.company.name}</td>
			 
				<td>
					 
				 
						${user.loginName}
				 
						
				</td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.roleNames}</td>
				<shiro:hasPermission name="sys:user:edit">
					<td>
						<c:if test="${user.id == \"admin\" }">
						</c:if> 
						<c:if test="${user.id != \"admin\" }">
					 
							<a href="${ctx}/sys/user/form?id=${user.id}"><spring:message code='update' /></a>
							<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
				 		</c:if>
	    			</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
	<%-- <script src="${ctx}/static/scripts/sys/role.js?t=<%=new java.util.Date()%>"></script> --%>
</body>
</html>