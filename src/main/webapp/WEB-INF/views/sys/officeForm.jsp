<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='organization.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					code: {remote: "${ctx}/sys/office/checkCode?oldCode=" + encodeURIComponent('${office.code}')} ,
				    name: {remote: "${ctx}/sys/office/checkName?oldName=" + encodeURIComponent('${office.name}')} 
				},
				messages: {
					code: {remote:accipiter.getLang("leveCodeExists")},
					name: {remote:accipiter.getLang("leveNameExists")}
					 
				},
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
    <li><spring:message code="sys.user"/></li>
    <li>></li>
    <li><spring:message code="grade.manage"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">
   <shiro:hasPermission name="sys:office:edit"><c:choose><c:when test="${not empty office.id}"><spring:message code='grade.update' /></c:when><c:otherwise><spring:message code='grade.add' /></c:otherwise></c:choose>
		</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit"><spring:message code='officeform.check' /></shiro:lacksPermission>
    </a></li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/office/"><spring:message code='grade.list' /></a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}"><shiro:hasPermission name="sys:office:edit"><c:choose><c:when test="${not empty office.id}"><spring:message code='grade.update' /></c:when><c:otherwise><spring:message code='grade.add' /></c:otherwise></c:choose>
		</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit"><spring:message code='officeform.check' /></shiro:lacksPermission></a></li>
	</ul><br/>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><spring:message code='parent.grade' />:</label>
			<div class="controls">
                <tags:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="officeform.grade" url="/sys/office/treeData" extId="${office.id}" cssClass="required"/>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='grade.name' />:</label>
			<div class="controls">
				<input id="oldName" name="oldName" type="hidden" value="${office.name}">
				<form:input path="name" htmlEscape="false" maxlength="60" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='grade.number' />:</label>
			<div class="controls">
				<input id="oldCode" name="oldCode" type="hidden" value="${office.code}">
				<form:input path="code" htmlEscape="false" maxlength="15" class="required"/>
			</div>
		</div>
	 
		<div class="control-group">
			<label class="control-label"><spring:message code='grade.level' />:</label>
			<div class="controls">
				<form:select path="grade">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</form:select>
			</div>
		</div>
	 
		<div class="control-group control-group-remarks">
			<label class="control-label"><spring:message code='memo' />:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>