<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='advertiser.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<meta name="decorator" content="default"/>
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
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="adv.user"/></li>
		    <li>></li>
		    <li><spring:message code="adv.userManage"/></li>
		    <li>></li>
		    <li>
		    	<shiro:hasPermission name="sys:advertiser:edit"><a href="${ctx}/adv/advertiser/form?id=${advertiser.id}">
		        <c:choose><c:when test="${not empty advertiser.id}"><spring:message code='advertiser.update' /></c:when>
        		<c:otherwise><spring:message code='advertiser.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/advertiser"><spring:message code='advertiser.list' /></a></li>
		<shiro:hasPermission name="sys:advertiser:edit"><li  class="active"><a href="${ctx}/adv/advertiser/form?id=${advertiser.id}">
		<c:choose><c:when test="${not empty advertiser.id}"><spring:message code='advertiser.update' /></c:when>
        			<c:otherwise><spring:message code='advertiser.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="advertiser" action="${ctx}/adv/advertiser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<c:choose>
			<c:when test="${not empty advertiser.id}">
				<div class="control-group">
					<label class="control-label"><spring:message
							code='advertiser.id' />:</label>
					<div class="controls">
						<form:input path="advertiserId" htmlEscape="false" maxlength="30" readonly="true"/>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<form:hidden path="advertiserId" />
			</c:otherwise>
		</c:choose>

		<div class="control-group">
			<label class="control-label"><spring:message code='advertiser.name' />:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="required"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label"><spring:message code='advertiser.industry'/>:</label>
			<div class="controls">
				<form:input path="industry" htmlEscape="false" maxlength="64" class="required"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label"><spring:message code='advertiser.businessLicenseNumber'/>:</label>
			<div class="controls">
				<form:input path="businessLicenseNumber" htmlEscape="false" maxlength="30"/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label"><spring:message code='advertiser.address' />:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='advertiser.contact' />:</label>
			<div class="controls">
				<form:input path="contacts" htmlEscape="false" maxlength="40"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='advertiser.webname' />:</label>
			<div class="controls">
				<form:input path="webName" htmlEscape="false" maxlength="64"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='advertiser.phone'/>:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="25" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='mobilephone' />:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="25" class="required"/>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='advertiser.type' />:</label>
			<div class="controls">
				<form:select path="type">
					<option value=""><spring:message code='advertiser.select' /></option>
					<form:options items="${fns:getDictList('advertiser_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<!--  <div class="control-group">
			<label class="control-label"><spring:message code='audit.role' />:</label>
			<div class="controls controls_s">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
			</div>
		</div>-->
		 
		<div class="form-actions">
			<shiro:hasPermission name="sys:advertiser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>