<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="com.gospell.aas.entity.sys.ScheduleJob" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='task.manage' /></title>
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
						$("#searchForm").attr("action","${ctx}/sys/task/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
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
    <li><spring:message code="task.manage"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/task"><spring:message code='task.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/task/"><spring:message code='task.list' /></a></li>
		<shiro:hasPermission name="sys:task:edit"><li><a href="${ctx}/sys/task/form"><spring:message code='task.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scheduleJob" action="${ctx}/sys/task/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<%-- <div class="query-item">
				<label><spring:message code='task.id' />：</label><form:input path="id" type="text" maxlength="50" class="input-small"/>
			</div> --%>
			<div class="query-item">
			    <label><spring:message code='task.jobName' />：</label><form:input path="jobName" type="text" maxlength="50" class="input-small"/>
			</div>
			<div class="query-item">
					<label><spring:message code='task.jobStatus' />：</label>
					<form:select path="jobStatus" class="selectlength">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getDictList('sys_task_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
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
			</div>
			<div class="query-item">
			   	<shiro:hasPermission name="sys:task:export">
				<input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>
				</shiro:hasPermission>
			</div> 
			 
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
				<th><spring:message code='task.jobName' /></th>
				<th><spring:message code='task.jobGroup' /></th>
				<th><spring:message code='task.cronExpression' /></th>
				<th><spring:message code='task.beanClass' /></th>
				<th><spring:message code='task.executeMethod' /></th>
				<th><spring:message code='task.jobDesc' /></th>
				<th><spring:message code='task.jobStatus' /></th>
				<th><spring:message code='create.user' /></th>
				<th><spring:message code='create.date' /></th>
				<shiro:hasPermission name="sys:task:edit">
					<th><spring:message code='operation' /></th>
				</shiro:hasPermission></thead>
		
		<tbody>
		<c:forEach items="${page.list}" var="scheduleJob">
			<tr>
    		 	<td>${scheduleJob.jobName}</td>
				<td>${scheduleJob.jobGroup}</td>
			    <td>${scheduleJob.cronExpression}</td>
				<td>com.gospell.***.PutEndTask</td>
				<td>${scheduleJob.executeMethod}</td>
				<td>${scheduleJob.jobDesc}</td>
				<c:choose>
						<c:when test="${scheduleJob.jobStatus eq '1'}">
						<td style="color:#08c"><spring:message code='task.start'/> </td>
						</c:when>
							<c:otherwise>
						<td style="color:red"><spring:message code='task.forbid'/> </td>
						</c:otherwise>
					</c:choose>
			   
				<td>${scheduleJob.createBy.name}</td>
				<td><fmt:formatDate value="${scheduleJob.createDate}" type="both"/></td>
				<shiro:hasPermission name="sys:task:edit">
					<td>
				       <a href="${ctx}/sys/task/form?id=${scheduleJob.id}"><spring:message code='update' /></a>
					 <c:if test="${scheduleJob.jobStatus eq '0'}">
						 <a href="${ctx}/sys/task/operate?jobStatus=1&id=${scheduleJob.id}" onclick="return confirmx('<spring:message code='task.start' />', this.href)"><spring:message code='task.start' /></a>
					</c:if>		
					 <c:if test="${scheduleJob.jobStatus eq '1'}">
						 <a href="${ctx}/sys/task/operate?&jobStatus=0&id=${scheduleJob.id}" onclick="return confirmx('<spring:message code='task.forbid' />', this.href)"><spring:message code='task.forbid' /></a>
					</c:if>				 
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