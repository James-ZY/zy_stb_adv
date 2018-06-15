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
    <li><spring:message code='resource.list' /></li>
    <li>></li><li><a href="${ctx}/adv/resource"><spring:message code='resource.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/resource"><spring:message code='resource.list' /></a></li>
		<shiro:hasPermission name="adv:adResourceOfType:edit"><li><a href="${ctx}/adv/resource/form"><spring:message code='resource.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adResourceOfType" action="${ctx}/adv/resource" method="post" class="breadcrumb form-search">
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
			<!--  <div class="query-item">
					<label><spring:message code='resource.enable' />:</label>
					<form:select path="enable">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_resource_enable')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
			</div>-->
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
		<th><spring:message code='file.size.range' /></th>
		<th><spring:message code='width.range'/>
		<th><spring:message code='high.range'/>
		<th><spring:message code='frame.range'/>	
		<!--  <th><spring:message code='rate.range'/>-->	
		<th><spring:message code='format'/>	
		<th><spring:message code='resource.resolution'/>	
		<th><spring:message code='adv.rollType'/>
		<!--  <th><spring:message code='resource.enable'/>-->	
		<shiro:hasPermission name="adv:adResourceOfType:edit">
			<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adResourceOfType">
			<tr>
				<td>${adResourceOfType.adType.typeName}</td>
				<td>${adResourceOfType.fileSize}</td>
				<td>${adResourceOfType.widthSize}</td>
					<td>${adResourceOfType.highSize}</td>
				<td>${adResourceOfType.frameSize}</td>
				<!--  <td>${adResourceOfType.rateSize}</td>-->
				<td>${adResourceOfType.format}</td>
				<td>${fns:getDictLabel(adResourceOfType.flag, 'adv_range_flag', "")}</td>
				<td>${fns:getDictLabel(adResourceOfType.rollFlag, 'ad_gd_sc_fx', "")}</td>
				<!--  <td>${fns:getDictLabel(adResourceOfType.enable, 'adv_resource_enable', "")}</td>-->
	 
				<shiro:hasPermission name="adv:adResourceOfType:edit">
					<td>
						 
						   <a href="${ctx}/adv/resource/form?id=${adResourceOfType.id}"><spring:message code='update' /></a>
							 
						 	<a href="${ctx}/adv/resource/delete?id=${adResourceOfType.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
						 		
						 
						
						 
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