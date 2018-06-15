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
    <li><spring:message code='sysFileParam.manage' /></li>
    <li>></li><li><a href="${ctx}/sys/fileParam"><spring:message code='sysFileParam.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/fileParam"><spring:message code='sysFileParam.list' /></a></li>
		<shiro:hasPermission name="sys:fileParam:edit"><li><a href="${ctx}/sys/fileParam/form"><spring:message code='sysFileParam.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysFileParam" action="${ctx}/sys/fileParam" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div class="query-item">
					<label><spring:message code='adv.type' />：</label>
					<form:select path="adType.id" class="selectlength1">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
			</div>
			 <div class="query-item">
					<label><spring:message code='resource.resolution' />:</label>
					<form:select path="flag">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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

		<thead><tr><th><spring:message code='adv.type' /></th>
		<th><spring:message code='resource.resolution'/>	
		<th><spring:message code='sysFileParam.amount'/>	
		<shiro:hasPermission name="sys:fileParam:edit">
			<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysParam">
			<tr>
				<td>${sysParam.adType.typeName}</td>
				<td>${fns:getDictLabel(sysParam.flag, 'adv_range_flag', "")}</td>
				<td>${sysParam.amount}</td>	 
				<shiro:hasPermission name="sys:fileParam:edit">
					<td>
						 
						   <a href="${ctx}/sys/fileParam/form?id=${sysParam.id}"><spring:message code='update' /></a>
							 
						 	<a href="${ctx}/sys/fileParam/delete?id=${sysParam.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
						 		
						 
						
						 
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