
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='dictionary.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
	         <li><spring:message code="combo.setSystem"/></li>
	         <li>></li>
	         <li><spring:message code="dictionary.manage"/></li>
	         <li>></li>
	         <li>
	         <a href="${ctx}/sys/dict/form?id=${dict.id}">
	         <spring:message code='dictionary' /><shiro:hasPermission name="sys:dict:edit"><c:choose><c:when test="${not empty dict.id}"><spring:message code='update' /></c:when><c:otherwise><spring:message code='add' /></c:otherwise></c:choose></shiro:hasPermission><shiro:lacksPermission name="sys:dict:edit"><spring:message code='check' /></shiro:lacksPermission>
	         </a>
	         </li>
         </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/dict/"><spring:message code='dictionary.list' /></a></li>
		<li class="active"><a href="${ctx}/sys/dict/form?id=${dict.id}"><shiro:hasPermission name="sys:dict:edit">
			<c:choose><c:when test="${not empty dict.id}"><spring:message code='update' /></c:when><c:otherwise>
		<spring:message code='add' /></c:otherwise></c:choose>&nbsp;<spring:message code='dictionary' />
		</shiro:hasPermission><shiro:lacksPermission name="sys:dict:edit"><spring:message code='check' /></shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><spring:message code='dictionary.key' />:</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='dictionary.label' />:</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='dictionary.type' />:</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="50" class="required abc"/>
			</div>
		</div>
 
		<div class="control-group control-group-remarks" >
			<label class="control-label"><spring:message code='dictionary.description' />:</label>
			<div class="controls">
				<form:textarea path="description"   htmlEscape="false" rows="6" maxlength="400" class="input-xlarge required"/>
				 
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='dictionary.order' />:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><spring:message code='language' />:</label>
			<div class="controls">
				 
				<form:select path="dictLocale"  >
					<form:options items="${adv_language}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			 </form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>