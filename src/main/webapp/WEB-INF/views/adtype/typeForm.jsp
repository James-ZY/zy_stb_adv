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
			$("#typeId").focus();
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
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='combo.setSystem' /></li>
    <li>></li>
    <li><spring:message code='adv.typeManage' /></li>
    <li>></li>
    <li>
     	<shiro:hasPermission name="sys:type:edit"><a href="${ctx}/adv/type/form?id=${adType.id}">
		<c:choose><c:when test="${not empty adType.id}"><spring:message code='type.update' /></c:when>
        			<c:otherwise><spring:message code='type.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/type"><spring:message code='type.list' /></a></li>
		<shiro:hasPermission name="sys:type:edit"><li  class="active"><a href="${ctx}/adv/type/form?id=${adType.id}">
		<c:choose><c:when test="${not empty adType.id}"><spring:message code='type.update' /></c:when>
        			<c:otherwise><spring:message code='type.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adType" action="${ctx}/adv/type/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	<div class="control-group">
			<label class="control-label"><spring:message code='super.adv.type' />:</label>
			<div class="controls">
                <tags:treeselect id="adType" name="parent.id" value="${adType.parent.id}" labelName="parent.typeName" labelValue="${adType.parent.typeName}"
					title="diplay.style" url="/adv/type/treeData" extId="${adType.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='type.id' />:</label>
			<div class="controls">
				<input id="oldTypeId" name="oldTypeId" type="hidden" value="${adType.typeId}">
				<form:input path="typeId" htmlEscape="false" maxlength="50" class="required typeId"/>
				<span class="typeIdError"></span>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='type.name' />:</label>
			<div class="controls">
				<form:input path="typeName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		 
		<c:choose>
			<c:when test="${adType.parent.id ne '-1'}">
			<div style="display:none;">
			<form:select path="isFlag" class="required">
						<option value="${adType.parent.isFlag}"></option>
		 	 </form:select>
			<form:select path="status" class="required">
					<option value="${adType.parent.status}"></option>
			</form:select>
	
			<form:select path="isPosition" class="required">
				<option value="${adType.parent.isPosition}"> </option>
			</form:select>
			<form:select path="isMove" class="required">
						<option value="${adType.parent.isMove}"> </option>
			</form:select>
		
			</div>
			</c:when>
			<c:otherwise>
			<div class="control-group">
			<label class="control-label"><spring:message code='type.isflag' />:</label>
			<div class="controls">
				<form:select path="isFlag" class="required">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_type_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><spring:message code='type.status' />:</label>
			<div class="controls">
				<form:select path="status" class="required">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_type_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='type.isposition' />:</label>
			<div class="controls">
				<form:select path="isPosition" class="required">
						<form:options items="${fns:getDictList('adv_type_is_position')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='type.ismove' />:</label>
			<div class="controls">
				<form:select path="isMove" class="required">
						<form:options items="${fns:getDictList('adv_type_is_move')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
			</c:otherwise>
		</c:choose>
		
		 
	<!--  <div class="control-group control-group-remarks">
			<label class="control-label"><spring:message code='type.description' />:</label>
			<div class="controls">
				<form:textarea path="typeDescription" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>--> 
		<div class="form-actions">
			<shiro:hasPermission name="sys:type:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
	$(function(){
		var host = accipiter.getRootPath();
		var oldParentId=$("#adTypeId").val();
		var oldTypeId=$("#oldTypeId").val();
		var newParentId="";
		var newTypeId="";
		var id=$("#id").val();
		function judgeAdvType(){
			var validate=true;
 			if(id==null || id==""){
				/* 广告类型添加 */
				oldParentId="";
				oldTypeId="";
			    newParentId=$("#adTypeId").val();
				newTypeId=$("#typeId").val();
			}else{
				/* 广告类型修改 */
				newParentId=$("#adTypeId").val();
				newTypeId=$("#typeId").val();
				
			}
			var Data={"oldParentId":oldParentId,"oldTypeId":oldTypeId,"newParentId":newParentId,"newTypeId":newTypeId,"id":id};
			var postData=JSON.stringify(Data);
			if(newParentId !="" && newTypeId !=""){
				$.ajax({
					url : host + "/adv/type/checktypeId",
					async : false,
					type : "POST",
					data : postData,
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success:function(data){
						if(data==false){
							$(".typeIdError").text(accipiter.getLang_(messageLang,"adv.type.id.repeat"));
							validate=false;
						}else{
							$(".typeIdError").text("");
							validate=true;
						}
					}
				});
			}else{
				validate=false;
			}
			return validate;
		}
		$("#btnSubmit").on("click",function(){
			var validate=judgeAdvType();
			if(validate==false){
				return false;
			}				
		});
		var timeoutId = 0;
	    $('#typeId').off('keyup').on('keyup', function (event) {
	    	$(".typeIdError").text("");
	    });
	})
	</script>
</body>
</html>