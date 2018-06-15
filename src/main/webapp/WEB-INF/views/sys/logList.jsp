<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="com.gospell.aas.entity.sys.Log" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='log.manage' /></title>
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
						$("#searchForm").attr("action","${ctx}/sys/log/export");
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
    <li><spring:message code ='log.manage'/></li>
    <li>></li>
    <li><spring:message code="log.query"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/log"><spring:message code='log.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/log/"><spring:message code='log.list' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="log" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<div class="query-item">
				<label><spring:message code='login.name' />：</label><input id="createById" name="createById" type="text" maxlength="50" class="input-small" value="${createById}"/>
			</div>
			<div class="query-item">
			    <label><spring:message code='user.name' />：</label><input id="createByName" name="createByName" type="text" maxlength="50" class="input-small" value="${createByName}"/>
			</div>
			<div class="query-item">
					<label><spring:message code='log.type' />：</label>
					<form:select path="type" class="selectlength">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getDictList('sys_log_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			<div class="query-item">
				<label><spring:message code='start.date' />：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
			<div class="query-item">
				<label><spring:message code='end.date' />：</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
			<div class="query-item">
				<label for="exception"><input id="exception" name="exception" type="checkbox"${exception eq '2'?' checked':''} value="2"/><spring:message code='exception.info' /></label>
			</div>
			<div class="query-item">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"/>
			</div>
			<div class="query-item">
			   	<shiro:hasPermission name="sys:log:export">
				<input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>
				</shiro:hasPermission>
			</div> 
			 
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr> 
		 <th><spring:message code='login.name' /></th>
		 <th><spring:message code='user.name' /></th>
		<th><spring:message code='operation.destination' /></th>
		 <!--  <th><spring:message code='submit.type' /></th>-->
		<th><spring:message code='operatives.IP' /></th>
		<th><spring:message code='creation.time' /></th>
		<th><spring:message code='operation.result' /></th>
		</thead>
		
		<tbody>
		<c:forEach items="${page.list}" var="log">
			<tr>
	 
			 	<td>${log.createBy.loginName}</td>
				<td>${log.createBy.name}</td>
				   <td><strong>
                	${log.logInfo}
                 </strong></td>
				<!--  <td>${log.method}</td>-->
				<td>${log.remoteAddr}</td>
				<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
				 <td><spring:message code="${log.exception==null||log.exception==''?'success':'fail'}" /></td>
			</tr>
			<!-- <tr>
				<td colspan="8"><spring:message code='user.proxy' />: ${log.userAgent}<br/><spring:message code='submit.parameter' />: ${fns:escapeHtml(log.params)}
				<c:if test="${not empty log.exception}"><br/><spring:message code='exception.info' />: <br/><%request.setAttribute("strEnter", "\n"); %>
				${fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>')}</c:if></td>
			</tr> -->
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>