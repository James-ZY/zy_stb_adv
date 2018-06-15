<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<style>
	.typeIdError{color:red;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
 
			$("#inputForm").validate({
				 
				submitHandler: function(form){
					loading(accipiter.getLang("loading"));
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text(accipiter.getLang("inputError"));
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
   <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul><li><spring:message code='combo.setSystem' /></li>
    <li>></li>
    <li><spring:message code='sysParam.manage' /></li>
    <li>></li><li><a href="${ctx}/sys/param"><spring:message code='sysParam.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/param"><spring:message code='sysParam.list' /></a></li>
		<shiro:hasPermission name="sys:param:edit"><li><a href="${ctx}/sys/param/form"><spring:message code='sysParam.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysParam" action="${ctx}/sys/param" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			 <div class="query-item">
					<label><spring:message code='sysParam.type' />:</label>
					<form:select path="paramType">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('sys_param_manage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
			</div>
			<div class="query-item">
				<label class="control-label"><spring:message code='sysParam.key' />:</label>
				<form:input path="paramKey" htmlEscape="false" maxlength="50" class="required"/>
	    	</div>
			 <div class="query-item">
			 	<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			 </div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">

		<thead><tr>
		<th><spring:message code='sysParam.type' /></th>
		<th><spring:message code='sysParam.key'/>	
		<th><spring:message code='sysParam.value'/>	
		<shiro:hasPermission name="sys:param:edit">
			<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysParam">
			<tr>
				<td>${fns:getDictLabel(sysParam.paramType, 'sys_param_manage', "")}</td>
				<td>${sysParam.paramKey}</td>	 
				<td>${sysParam.paramValue}</td>	 
				<shiro:hasPermission name="sys:param:edit">
					<td>
					   <c:if test="${sysParam.canUpdate == '1' }">
								   <a href="${ctx}/sys/param/form?id=${sysParam.id}"><spring:message code='update' /></a>
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