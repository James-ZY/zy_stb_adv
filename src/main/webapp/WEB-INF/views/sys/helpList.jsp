<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='dictionary.manage' /></title>
	<meta name="decorator" content="default"/>
		<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
    <script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		/* 刷新帮助文档 */
		window.onload = function() { 
			function getHelpDocument(){
				var helpUrl="${ctx}/sys/help/find_help_file";
				$.ajaxSetup({cache:false});
				$.get(helpUrl,function(data){
					if(data!=""){
						$(".help a",window.parent.document).attr("href",encodeURI(data));
					}else{
						$(".help a",window.parent.document).attr("href",'');
					}
				});
			}
			getHelpDocument();
		}; 
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="about.help"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/help/"><spring:message code='help.doc.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/help/"><spring:message code='help.list' /></a></li>
		<shiro:hasPermission name="sys:help:edit"><li><a href="${ctx}/sys/help/form"><spring:message code='help.add' /></a></li></shiro:hasPermission>
	</ul>
	 
	<form:form id="searchForm" modelAttribute="help" action="${ctx}/sys/help/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label><spring:message code='create.help.user' /> ：</label><form:input path="createBy.loginName" htmlEscape="false" maxlength="100" class="input-small"/>
		&nbsp;&nbsp;<label><spring:message code='update.user.id' /> ：</label><form:input path="updateBy.loginName" htmlEscape="false" maxlength="100" class="input-small"/>
		&nbsp;&nbsp;<label><spring:message code='help.file.flag' /> ：</label>
		<form:select   path="flag" class="input-small" style="width:160px !important;">
		<option value=""> <spring:message code='userform.select' /></option>
		<form:options items="${fns:getDictList('help_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		&nbsp;&nbsp;
		<label><spring:message code='help.file.isvalid' /> ：</label>
		<form:select   path="status" class="input-small" style="width:160px !important;">
		<option value=""> <spring:message code='userform.select' /></option>
		<form:options items="${fns:getDictList('help_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"/>
	 
		</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th><spring:message code='help.file.path' /></th>
		<th><spring:message code='help.file.flag' /></th>
		<th><spring:message code='help.file.create'/></th>
		<th><spring:message code='help.file.update' /></th>
		<th><spring:message code='help.file.isvalid'/></th>
		<th><spring:message code='language'/></th>
		<shiro:hasPermission name="sys:help:edit"><th><spring:message code='operation' /></th>
		</shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="help">
			<tr>
				<td>${help.filePath}</td>
				<td>${fns:getDictLabel(help.flag, 'help_flag', "")}</td>
				<c:choose>
					<c:when test="${!empty help.createBy}">
						<td>${help.createBy.loginName}</td>
					</c:when>
					<c:otherwise>
						 <td></td>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${!empty help.updateBy}">
						<td>${help.updateBy.loginName}</td>
					</c:when>
					<c:otherwise>
						 <td></td>
					</c:otherwise>
				</c:choose>
				<td>${fns:getDictLabel(help.status, 'help_status', "")}</td>
				 
					<c:choose>
					<c:when test="${help.helpLocale == 0}">
						<td><spring:message code='Chinese'/></td>
					</c:when>
					<c:otherwise>
						 <td><spring:message code='English'/></td>
					</c:otherwise>
				</c:choose>
				 
				<shiro:hasPermission name="sys:help:edit"><td>
    				<a href="${ctx}/sys/help/form?id=${help.id}"><spring:message code='update' /></a>
					
					 
    				 <c:choose>
					<c:when test="${help.status == 0}">
							<a href="${ctx}/sys/help/delete?id=${help.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
							<a href="${ctx}/sys/help/startvalid?id=${help.id}"><spring:message code='help.start' /></a>
					</c:when>
					<c:otherwise>
						  <a href="${ctx}/sys/help/closevalid?id=${help.id}"><spring:message code='help.close' /></a> 
					</c:otherwise>
				</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>