<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">
		.form-actions {
			margin-top: 60px;
		}
	</style>
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
            checkCU();
            $("#paramType").on("change",function(){
                checkCU();
            });

            $("#districtValue").on("change",function(){
                $("#paramValue").val($(this).val());
            });



            function checkCU(){
                var paramType=$("#paramType").find('option:selected').val();
                var index = paramType.indexOf("CU_");
                if(index != -1){
                    $("#paramKey").val("DEFAULT");
                    $("#paramKey").attr("readonly","true");
                }
                if(paramType == "CU_SYS_AREA"){
                    $("#paramKey").val("SYS_AREA");
                    $("#paramKey").attr("readonly","false");
                    $("#defVal").css("display","none");
                    $("#disArea").css("display","block");
                }else{
                    var id = $("#id").val();
                    if(null == id || id == ""){
                        $("#paramValue").val("");
                    }
                    $("#defVal").css("display","block");
                    $("#disArea").css("display","none");
                }
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
		<li><spring:message code='sysParam.manage' /></li>
		<li>></li>

		<shiro:hasPermission name="sys:param:edit">
			<li>
				<a href="${ctx}/sys/param/form?id=${sysParam.id}">
					<c:choose><c:when test="${not empty sysParam.id}"><spring:message code='sysParam.update' /></c:when>
						<c:otherwise><spring:message code='sysParam.add' /></c:otherwise></c:choose></a>
			</li>
		</shiro:hasPermission>

	</ul>
</div>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/sys/param"><spring:message code='sysParam.list' /></a></li>
	<shiro:hasPermission name="sys:param:edit"><li  class="active"><a href="${ctx}/sys/param/form?id=${sysParam.id}">
		<c:choose><c:when test="${not empty sysParam.id}"><spring:message code='sysParam.update' /></c:when>
			<c:otherwise><spring:message code='sysParam.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
</ul>

<form:form id="inputForm" modelAttribute="sysParam" action="${ctx}/sys/param/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<tags:message content="${message}"/>

	<div class="control-group">
		<label class="control-label"><spring:message code='sysParam.type' />:</label>
		<div class="controls">
			<form:select path="paramType" class="required">
				<option value=""><spring:message code="userform.select"/></option>
				<form:options items="${fns:getDictList1('sys_param_manage','1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><spring:message code='sysParam.key' />:</label>
		<div class="controls">
			<form:input path="paramKey" htmlEscape="false" maxlength="20" class="required"/>
		</div>
	</div>
	<div class="control-group" id="defVal">
		<label class="control-label"><spring:message code='sysParam.value' />:</label>
		<div class="controls">
			<form:input path="paramValue" htmlEscape="false" maxlength="10" class="required"/>
		</div>
	</div>
	<div class="control-group" id="disArea" style="display:none;">
		<label class="control-label"><spring:message code='sysParam.value' />:</label>
		<div class="controls">
			<form:select path="districtValue" class="required">
				<option value=""><spring:message code="userform.select"/></option>
				<form:options items="${fns:getDictList('ad_district_country')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><spring:message code='sysParam.remark' />:</label>
		<div class="controls">
			<form:textarea path="remarks" htmlEscape="false" maxlength="50" class="required"/>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="sys:param:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>