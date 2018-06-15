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
                var url="${ctx}/adv/districtCategory/checkcategoryId?oldCategoryId="+encodeURIComponent(oldCategoryId)+"&categoryId="+encodeURIComponent(categoryId);
                if(categoryId!=""){
                    $.get(url,function(data){
                        if(data=="true"){
                            $(".categoryIdErrInfo").text("");
                            $("#btnSubmit").prop("disabled",false);
                        }
                        if(data=="false"){
                            var text = accipiter.getLang("IdNotUnique");
                            $(".categoryIdErrInfo").text(text);
                            $("#btnSubmit").prop("disabled",true);
                        }
                    })
                }
            }
			/* 		 $('#categoryId').blur(function(){
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
			 $(".categoryIdErrInfo").text("");
			 })
			 $("#categoryId").focus(); */
            $("#inputForm").validate({
				/* 		rules: {
				 categoryId: {remote: "${ctx}/adv/districtCategory/checkcategoryId?oldCategoryId=" +encodeURIComponent('${adCategory.categoryId}')}


				 },
				 messages: {
				 categoryId: {remote:accipiter.getLang("categoryIdExist")}

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
		<li><spring:message code='combo.setSystem' /></li>
		<li>></li>
		<li><spring:message code='districtCategory.manage' /></li>
		<li>></li>
		<li>
			<shiro:hasPermission name="sys:districtCategory:edit"><a href="${ctx}/adv/districtCategory/form?id=${adCategory.id}">
				<c:choose><c:when test="${not empty adCategory.id}"><spring:message code='districtCategory.update' /></c:when>
					<c:otherwise><spring:message code='districtCategory.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		</li>
	</ul>
</div>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/adv/districtCategory"><spring:message code='districtCategory.list' /></a></li>
	<shiro:hasPermission name="sys:districtCategory:edit"><li  class="active"><a href="${ctx}/adv/districtCategory/form?id=${adCategory.id}">
		<c:choose><c:when test="${not empty adCategory.id}"><spring:message code='districtCategory.update' /></c:when>
			<c:otherwise><spring:message code='districtCategory.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
</ul>
<form:form id="inputForm" modelAttribute="adDistrictCategory" action="${ctx}/adv/districtCategory/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<tags:message content="${message}"/>
	<div class="control-group">
		<label class="control-label"><spring:message code='districtCategory.grade' />:</label>
		<div class="controls">
			<tags:treeselect id="adCategory" name="parent.id" value="${adCategory.parent.id}" labelName="parent.categoryName" labelValue="${adCategory.parent.categoryName}"
							 title="category" url="/adv/districtCategory/treeData" extId="${adCategory.id}" cssClass="required"/>
		</div>
	</div>
	<c:choose><c:when test="${not empty adCategory.id}">
		<div class="control-group">
			<label class="control-label"><spring:message code='districtCategory.id' />:</label>
			<div class="controls">
				<form:input path="categoryId" htmlEscape="false"  maxlength="50"  readonly="true"/>
			</div>
		</div>
	</c:when>
		<c:otherwise>
			<form:hidden path="categoryId" />
		</c:otherwise>
	</c:choose>
	<div class="control-group">
		<label class="control-label"><spring:message code='districtCategory.name' />:</label>
		<div class="controls">
			<form:input path="categoryName" htmlEscape="false" maxlength="75" class="required"/>
		</div>
	</div>



	<div class="control-group control-group-remarks">
		<label class="control-label"><spring:message code='districtCategory.remarks' />:</label>
		<div class="controls">
			<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="sys:districtCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
	</div>
</form:form>

</body>
</html>