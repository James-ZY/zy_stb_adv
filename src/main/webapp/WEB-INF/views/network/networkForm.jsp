<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='user.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/style.css">
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	
	<script src="${ctxStatic}/common/language.js"></script>
  <script type="text/javascript" src="${ctxStatic}/common/areadata.js"></script>
    <script type="text/javascript" src="${ctxStatic}/common/auto_area.js"></script>
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
		<li><spring:message code="network.manage"/></li>
		<li>></li>
		<li>
		<shiro:hasPermission name="sys:network:edit"><li  class="active"><a href="${ctx}/adv/network/form?id=${adNetwork.id}">
		<c:choose><c:when test="${not empty adNetwork.id}"><spring:message code='network.update' /></c:when>
        			<c:otherwise><spring:message code='network.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
		</li>
		</ul>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/network"><spring:message code='network' /><spring:message code="list"/></a></li>
		<shiro:hasPermission name="sys:network:edit"><li  class="active"><a href="${ctx}/adv/network/form?id=${adNetwork.id}">
		<c:choose><c:when test="${not empty adNetwork.id}"><spring:message code='network.update' /></c:when>
        			<c:otherwise><spring:message code='network.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adNetwork" action="${ctx}/adv/network/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="selArea" name="selArea" value="${selArea}">
		<input type="hidden" id="operatorsId" value="${adNetwork.adOperators.id}">
		<p id="selAllArea" style="display:none">${selAllArea}</p>
		<input type="hidden" id="districtMode" value="selNetDis">
		<div class="control-group">
			<label class="control-label"><spring:message code='network.id' />:</label>
			<div class="controls">
				<form:input path="networkId" htmlEscape="false" maxlength="75" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='network.name' />:</label>
			<div class="controls">
				<form:input path="networkName" htmlEscape="false" maxlength="75" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='network.ip' />:</label>
			<div class="controls">
				<form:input path="ip" htmlEscape="false" maxlength="20" class="required" disabled="true"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label"><spring:message code='network.port' />:</label>
			<div class="controls">
				<form:input path="port" htmlEscape="false" maxlength="30" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='network.operators' />:</label>
			<div class="controls">
				<form:input path="adOperators.operatorsName" htmlEscape="false" maxlength="15" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='not.channel.type' />:</label>
			<div class="controls">
				<form:input path="typeName" htmlEscape="false" maxlength="500" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='valid.time' />:</label>
			<div class="controls">
             <input id="validDate" name="validDate" disabled="disabled" type="text" value="<fmt:formatDate value="${adNetwork.validDate}" pattern="yyyy-MM-dd" />" maxlength="11">
			</div>
		</div>
		 
	 	<div class="control-group" style="display: block">
			<label class="control-label"><spring:message code='operators.area' />:</label>
			<div class="controls">
			<input type="text" id="area" name="area" class="area-danxuan" value="${adNetwork.area}" data-value="" readonly="readonly">
			</div>
		</div>
		<div class="control-group" id="selDisResult" style="display:none;height: auto;">
			<label class="control-label"><spring:message code='operators.self.number' />:</label>
			<div class="controls">
		          <div id="selDataResult" class="sel-data-result" style="padding: 5px;"></div>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:network:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>