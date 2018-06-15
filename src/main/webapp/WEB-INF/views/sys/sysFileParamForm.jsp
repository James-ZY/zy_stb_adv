<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var host = accipiter.getRootPath();
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
			
			$(".adTypeId").on("change",function(){
				var flag=$("#flag").find('option:selected').val();
				var typeId=$(".adTypeId").find('option:selected').val();
				if(flag!="" && typeId !=""){
					checkParamIsExist(flag,typeId);
				}					
			});
			
			$("#flag").on("change",function(){
				var flag=$("#flag").find('option:selected').val();
				var typeId=$(".adTypeId").find('option:selected').val();
				if(flag!="" && typeId !=""){
					checkParamIsExist(flag,typeId);
				}				
			});
			
			function checkParamIsExist(flag,typeId){
				var data = {flag:flag,typeId:typeId};
				var postData = JSON.stringify(data);
				$.ajax({
					url :host + "/sys/fileParam/checkParamIsExist",
					type :"post",
					async:false,
					data :postData,
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success:function(data){
						if(data!=null){
							$("#id").val(data.spId);
							$("#amount").val(data.amount);
						}						
					}
				});
			}
			
		});
		
	</script>

</head>
<body>
 <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='combo.setSystem' /></li>
    <li>></li>
    <li><spring:message code='sysFileParam.manage' /></li>
    <li>></li>
   
     	<shiro:hasPermission name="sys:fileParam:edit">
     	 <li>
     	<a href="${ctx}/sys/fileParam/form?id=${sysParam.id}">
		<c:choose><c:when test="${not empty sysParam.id}"><spring:message code='sysFileParam.update' /></c:when>
        			<c:otherwise><spring:message code='sysFileParam.add' /></c:otherwise></c:choose></a>
          </li>
        </shiro:hasPermission>
   
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/fileParam"><spring:message code='sysFileParam.list' /></a></li>
		<shiro:hasPermission name="sys:fileParam:edit"><li  class="active"><a href="${ctx}/sys/fileParam/form?id=${sysParam.id}">
		<c:choose><c:when test="${not empty sysParam.id}"><spring:message code='sysFileParam.update' /></c:when>
        			<c:otherwise><spring:message code='sysFileParam.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
		 
	<form:form id="inputForm" modelAttribute="sysFileParam" action="${ctx}/sys/fileParam/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label"><spring:message code='adv.type' />：</label>
			<div class="controls">
			<form:select path="adType.id" class="adTypeId required">
				<option value=""><spring:message code="userform.select"/></option>
				<form:options items="${fns:getAdTypeById('1')}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
			</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><spring:message code='range.resolution' />:</label>
			<div class="controls">
				<form:select path="flag" class="required">			 
						<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>	  
		 <div class="control-group">
				<label class="control-label"><spring:message code='sysFileParam.amount' />:</label>
				<div class="controls">
					<form:input path="amount" htmlEscape="false" maxlength="5" class="required number"/>
				</div>
		</div> 
		<div class="form-actions">
			<shiro:hasPermission name="sys:fileParam:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>