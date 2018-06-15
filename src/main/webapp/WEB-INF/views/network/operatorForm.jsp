<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='user.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/style.css">
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	
	<script src="${ctxStatic}/common/language.js"></script>
 	<script type="text/javascript" src="${ctxStatic}/common/City_data.js"></script>
    <script src="${ctxStatic}/common/auto_area.js"></script>
    <script src="${ctxStatic}/common/areadata.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var val = '';
					if ($('.sel_save_box').length > 0) {
				        $('.sel_save_box').each(function () {
				                val += $(this).data("code") + ':' + $(this).find("input").val() + '-';
				        });
				    }
				    if (val != '') {
				        val = val.substring(0, val.lastIndexOf('-'));
				    }
				    console.log(val);
				    $("#selArea").val(val);
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
			var selAllArea = $("#selAllArea").text();
			if(selAllArea != null &&selAllArea != ""){
	      		var data=JSON.parse(selAllArea);
				appendSelfArea(data.adDistrictCategorys);				
			}
		});
	</script>
</head>
<body>
    <div class="top_position">
		<div class="top_position_lab"><spring:message code="position"/>:</div>
		<ul>
		<li><spring:message code="operators"/></li>
		<li>></li>
		<li><spring:message code="operayors.manage"/></li>
		<li>></li>
		<li>
		<shiro:hasPermission name="sys:operators:edit"><li  class="active"><a href="${ctx}/adv/operators/form?id=${adOperators.id}">
		<c:choose><c:when test="${not empty adOperators.id}"><spring:message code='operators.update' /></c:when>
        			<c:otherwise><spring:message code='operators.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
		</li>
		</ul>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/operators"><spring:message code='operators' /><spring:message code="list"/></a></li>
		<shiro:hasPermission name="sys:operators:edit"><li  class="active"><a href="${ctx}/adv/operators/form?id=${adOperators.id}">
		<c:choose><c:when test="${not empty adOperators.id}"><spring:message code='operators.update' /></c:when>
        			<c:otherwise><spring:message code='operators.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adOperators" action="${ctx}/adv/operators/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="selArea" name="selArea" value="${selArea}">
		<p id="selAllArea" style="display:none">${selAllArea}</p>
		<input type="hidden" id="districtMode" value="setDis">
		<tags:message content="${message}"/>
	 			<c:choose>
			<c:when test="${not empty adOperators.id}">
				<div class="control-group">
					<label class="control-label"><spring:message
							code='operators.id' />:</label>
					<div class="controls">
						<form:input path="operatorsId" htmlEscape="false" maxlength="20" readonly="true"/>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<form:hidden path="operatorsId" />
			</c:otherwise>
		</c:choose>
		<div class="control-group">
			<label class="control-label"><spring:message code='operators.name' />:</label>
			<div class="controls">
				<form:input path="operatorsName" htmlEscape="false" maxlength="75" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='operators.password' />:</label>
			<div class="controls">
				<form:input path="password" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label"><spring:message code='operators.contact' />:</label>
			<div class="controls">
				<form:input path="contact" htmlEscape="false" maxlength="30" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='operators.telphone' />:</label>
			<div class="controls">
				<form:input path="telphone" htmlEscape="false" maxlength="15" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='operators.mobile' />:</label>
			<div class="controls">
				<form:input path="mobilephone" htmlEscape="false" maxlength="15" class="required"/>
			</div>
		</div>
		 
	 	<div class="control-group" style="display: block">
			<label class="control-label"><spring:message code='operators.service.area' />:</label>
			<div class="controls">
			<input type="text" id="area" name="area"  class="area-danxuan" value="${adOperators.area}" data-value="" readonly="readonly">
			</div>
		</div>
		<div class="control-group" id="selDisResult" style="display:none;height: auto;">
			<label class="control-label"><spring:message code='operators.self.number' />:</label>
			<div class="controls">
		          <div id="selDataResult" class="sel-data-result" style="padding: 5px;"></div>
			</div>
		</div>
				
		<div class="control-group">
			<label class="control-label"><spring:message code='operators.number' />:</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" class="digits" maxlength="11"/>
			</div>
		</div>
			 
		<c:if test="${not empty adOperators.networkName}">
		<div class="control-group">
			 <label class="control-label"><spring:message code='network.name' />:</label>
			<div class="controls">
				<form:input path="networkName" htmlEscape="false" maxlength="200" readonly="true"/>
			</div>
			</div>
		</c:if>
		<c:if test="${empty adOperators.networkName}">
		<div class="control-group">
			 <label class="control-label"><spring:message code='network.name' />:</label>
			<div class="controls">
				<label class="lbl"><spring:message code='network.not.deploy' /></label>
			</div>
			</div> 
		</c:if>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:operators:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>