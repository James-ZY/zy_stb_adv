<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<style>
	.typeIdError{color:red;}
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		function juadgeId(){
			var oldCategoryId=$("#oldCategoryId").val();
			var categoryId=$("#categoryId").val();
			var url="${ctx}/adv/category/checkProgramCategoryId?oldCategoryId="+encodeURIComponent(oldCategoryId)+"&programCategoryId="+encodeURIComponent(categoryId);
			if(categoryId!=""){
				$.get(url,function(data){
					if(data=="true"){
							$(".programCategoryIdErrInfo").text("");
							$("#btnSubmit").prop("disabled",false);
						}
						if(data=="false"){
							var text = accipiter.getLang("IdNotUnique");
							$(".programCategoryIdErrInfo").text(text);
							$("#btnSubmit").prop("disabled",true);
						}
					})
				}
			}
		$('#categoryId').blur(function(){
			 var categoryId=$(this).val();
			 var len=categoryId.length;
			 if(len==1){
				 $(this).val("000"+categoryId);
				 juadgeId();
				 return;
			 }
			 if(len==2){
				 $(this).val("00"+categoryId);
				 juadgeId();
				 return;
			 }
			 if(len==3){
				 $(this).val("0"+categoryId);
				 juadgeId();
				 return;
			 }
			 if(len==4){
				 juadgeId();
			 }
		 })
			 $('#categoryId').off('keyup').on('keyup', function(){
				 $(".programCategoryIdErrInfo").text("");
			 })
			$("#categoryId").focus();
			$("#inputForm").validate({
		/* 		rules: {
					programCategoryId: {remote: "${ctx}/adv/programCategory/checkprogramCategoryId?oldCategoryId=" +encodeURIComponent('${adProgramCategory.categoryId}')}
	
				 
				},
				messages: {
					programCategoryId: {remote:accipiter.getLang("programCategoryIdExist")}
		 
				}, */
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
    <li><spring:message code='programCategory' /></li>
    <li>></li>
    <li><spring:message code='programCategory.manage' /></li>
    <li>></li>
    <li>
     	<shiro:hasPermission name="sys:programCategory:edit"><a href="${ctx}/adv/programCategory/form?id=${adProgramCategory.id}">
		<c:choose><c:when test="${not empty adProgramCategory.id}"><spring:message code='programCategory.update' /></c:when>
        			<c:otherwise><spring:message code='programCategory.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/programCategory"><spring:message code='type.list' /></a></li>
		<shiro:hasPermission name="sys:programCategory:edit"><li  class="active"><a href="${ctx}/adv/programCategory/form?id=${adProgramCategory.id}">
		<c:choose><c:when test="${not empty adProgramCategory.id}"><spring:message code='programCategory.update' /></c:when>
        			<c:otherwise><spring:message code='programCategory.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adProgramCategory" action="${ctx}/adv/programCategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	<div class="control-group">
			<label class="control-label"><spring:message code='programCategory.grade' />:</label>
			<div class="controls">
                <tags:treeselect id="adProgramCategory" name="parent.id" value="${adProgramCategory.parent.id}" labelName="parent.programCategoryName" labelValue="${adProgramCategory.parent.categoryName}"
					title="programCategory" url="/adv/programCategory/treeData" extId="${adProgramCategory.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='programCategory.id' />:</label>
			<div class="controls">
				<input id="oldCategoryId" name="oldCategoryId" type="hidden" value="${adProgramCategory.categoryId}">
				<form:input path="categoryId" htmlEscape="false"  maxlength="4" class="required number"/>
				<!-- 后台判定id必须是正整数 -->
				<span style="color:red" class="programCategoryIdErrInfo">${programCategoryIdError}</span>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='programCategory.name' />:</label>
			<div class="controls">
				<form:input path="categoryName" htmlEscape="false" maxlength="75" class="required"/>
			</div>
		</div>
		 
	 
		 
	 <div class="control-group control-group-remarks">
			<label class="control-label"><spring:message code='programCategory.remarks' />:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:programCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
 
</body>
</html>