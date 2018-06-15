<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="com.gospell.aas.entity.sys.ScheduleJob" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='database.manage' /></title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
    <script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function(){
			$("#btnExport").click(function(){
				var info = accipiter.getLang("export.adopertator.Data");
				 
				var systemPrompt =accipiter.getLang("systemPrompt");
				 
				top.$.jBox.confirm(info,systemPrompt,function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/database/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#runJobNow").off("click").on("click",function(){
                $("#searchForm").attr("action","${ctx}/sys/database/runJobNow");
                $("#searchForm").submit();
            });
		});
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code ='database.manage'/></li>
    <li>></li>
    <li><spring:message code="database.BackUpRestore"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/database"><spring:message code='database.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/database/"><spring:message code='database.list' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dataBaseRecord" action="${ctx}/sys/database/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<div class="query-item">
			    <label><spring:message code='database.recordName' />：</label><form:input path="recordName" type="text" maxlength="50" class="input-small"/>
			</div>
			<div class="query-item">
				<label><spring:message code='start.date' />：</label><input id="createDateStart" name="createDateStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${createDateStart}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'createDateEnd\')}'});"/>
			</div>
			<div class="query-item">
				<label><spring:message code='end.date' />：</label><input id="createDateEnd" name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${createDateEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'createDateStart\')}'});"/>
			</div>
			<div class="query-item">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"/>
			<input id="runJobNow" class="btn btn-primary" type="button" value="<spring:message code='runJobNow' />"/>
			</div>
			<div class="query-item">
			   	<shiro:hasPermission name="sys:database:export">
				<input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>
				</shiro:hasPermission>
			</div> 
			 
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
				<th><spring:message code='database.recordName' /></th>
				<th><spring:message code='database.recordPath' /></th>
				<th><spring:message code='create.date' /></th>
				<shiro:hasPermission name="sys:database:edit">
					<th><spring:message code='operation' /></th>
				</shiro:hasPermission></thead>
		
		<tbody>
		<c:forEach items="${page.list}" var="database">
			<tr>
			 	<td>${database.recordName}</td>
				<td><a href="${database.recordPath}">${database.recordPath}</a></td>
				<td><fmt:formatDate value="${database.createDate}" type="both"/></td>
				<shiro:hasPermission name="sys:database:edit">
					<td>
						 <a href="${database.recordPath}"><spring:message code='database.backup' /></a>
						 <a href="${ctx}/sys/database/restore?id=${database.id}" onclick="return confirmx('<spring:message code='database.restore' />', this.href)"><spring:message code='database.restore' /></a>
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