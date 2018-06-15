<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
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
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code='adv.typeManage' /></li>
    <li>></li>
    <li>
     	<shiro:hasPermission name="adv:material:edit"><a href="${ctx}/adv/control/form?id=${adType.id}">
		<c:choose><c:when test="${not empty adType.id}"><spring:message code='type.update' /></c:when>
        			<c:otherwise><spring:message code='type.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/control/audit"><spring:message code='control.list' /></a></li>
		<shiro:hasPermission name="adv:material:claim"><li  class="active"><a href="${ctx}/adv/control/form?id=${adControll.id}">
			<spring:message code='control.audit' />
        			</shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adControll" action="${ctx}/adv/control/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	 
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.type' />:</label>
			<div class="controls">
				<form:select path="adType.id" class="select" readonly="readonly" >
					<option value=""> <spring:message code='userform.select' /></option>
					<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<label class="info-messages"><p></p></label>
			</div>
		</div>
			<form:hidden path="resourceType"/>
			<form:hidden path="path"/> 
			<form:hidden path="vedioImagePath"/> 	 
	    <div class="control-group control-group-auto" id="adv_image">
		     <label class="control-label"><spring:message code="adv.image"/>:</label>
		     <div class="controls">		     	
		         <div class="dropzoneStyle" id="upload_adv_image"></div>
		     </div>
		</div>
		<div class="control-group control-group-auto" id="adv_kaijiimage">
		     <label class="control-label"><spring:message code="adv.image"/>:</label>
		     <div class="controls">		     	
		         <div class="dropzoneStyle" id="upload_adv_kaijiimage"></div>
		     </div>
		</div>
		<div class="control-group control-group-auto" id="adv_vedio">
		     <label class="control-label"><spring:message code="adv.vedio"/>:</label>
		     <div class="controls">
		         <div class="dropzoneStyle" id="upload_adv_vedio"></div>
		     </div>
		</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='control.format' />:</label>
				<div class="controls">
					<label class="lbl">${adControll.fileFormat}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='control.width' />:</label>
				<div class="controls">
					<label class="lbl">${adControll.width}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='control.height' />:</label>
				<div class="controls">
					<label class="lbl">${adControll.height}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='control.status' />:</label>
				<div class="controls">
				      <form:select path="status" class="required">
				      	<option value=""> <spring:message code='userform.select' /></option>
					  	<form:options items="${fns:getDictList('adv_control_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					 </form:select>
				</div>
			</div>
		<div class="control-group control-group-remarks">
			<label class="control-label"><spring:message code='control.reason' />:</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		 
		<div class="form-actions">
			<shiro:hasPermission name=adv:material:claim"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div class="control_info">
		<div class="info_header_content">
		<p class="info_header"><spring:message code="adv.hintinfo"/></p>
		<div class="icon_control"></div>
		</div>
		<div class="info"><p><spring:message code="adv.fileNotnull"/></p></div>
		<div class="info_control_content">
		 <input type="button" class="btn_submit" value='<spring:message code="sure"/>'>
		 <input type="button" class="btn_clear" value="<spring:message code="cancle"/>">
		 </div>
	</div>
	<div class="file_loading">
	<div class="l_left"></div>
	<div class="l-right"><spring:message code="vedio.uploading"/></div>
	</div>
	<div class="remove_data"></div>
	<script src="${ctxStatic}/dropzone/dropzone.js"></script>
	<script src="${ctxStatic}/adv/adv_control.js"></script>
</body>
</html>