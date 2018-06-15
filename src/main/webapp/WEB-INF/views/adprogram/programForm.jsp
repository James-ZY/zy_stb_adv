<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	  <link rel="stylesheet" href="${ctxStatic}/dropzone/css/basic.css">
	  <link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	  <script src="${ctx}/static/scripts/common/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#programName").attr('placeholder',accipiter.getLang_(messageLang,"right.program"));
			$("#programId").focus();
			$("#inputForm").validate({
				rules: {
					programId: {remote: "${ctx}/adv/program/checkProgramId?oldProgramId=" + encodeURIComponent('${adExternalProgram.programId}')}
				},
				messages: {
					programId: {remote:accipiter.getLang("programIdExist")}
				 
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
   
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/program"><spring:message code='program.list' /></a></li>
		<shiro:hasPermission name="adv:program:edit"><li  class="active"><a href="${ctx}/adv/program/form?id=${adExternalProgram.id}">
		<c:choose><c:when test="${not empty adExternalProgram.id}"><spring:message code='program.update' /></c:when>
        			<c:otherwise><spring:message code='program.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adExternalProgram" action="${ctx}/adv/program/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	 
		<div class="control-group">
			<label class="control-label"><spring:message code='program.id' />:</label>
			<div class="controls">
				<input id="oldProgramId" name="oldProgramId" type="hidden" value="${adExternalProgram.programId}">
				<form:input path="programId" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='program.name' />:</label>
			<div class="controls">
				<form:input path="programName" htmlEscape="false" maxlength="50"  class="required"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label"><spring:message code='program.version' />:</label>
			<div class="controls">
				<form:input path="programVersion" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
 		<div class="control-group">
			<label class="control-label"><spring:message code="program.path"/>:</label>
			<div class="controls">
				<input id="photo" name="programPath" type='hidden'
					value="${adExternalProgram.programPath}" required="true"/>
				<div id="fileUpload" name="fileUpload" class="dropzone"
					style="height:200px;width:200px;" required="true"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='memo' />:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="adv:program:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
		<script type="text/javascript">
		Dropzone.autoDiscover = false;

		var myDropzone = new Dropzone("div#fileUpload", {
			url : "${ctx}/file/programUpload",
			addRemoveLinks : true,
			maxFiles : 1,
			maxFilesize: 1024,
 
			dictDefaultMessage : accipiter.getLang_(messageLang,"file.upload"),
			dictRemoveFile :accipiter.getLang_(messageLang,"file.remove"),
			dictMaxFilesExceeded : accipiter.getLang_(messageLang,"file.maxuploadFile")
		});

		var mockFile = {
			name : "Filename",
			size : 12345
		};
		myDropzone.emit("addedfile", mockFile);

		if (inputForm.photo.value.length != 0) {
			myDropzone.emit("thumbnail", mockFile,
					"${adExternalProgram.programPath}");
		}

		myDropzone.on("success", function(file, responseText) {
			if (responseText.substring(0, 1) == 0) { //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
				$("#photo").attr("value", responseText.substring(2));
				var success = accipiter.getLang_(messageLang,"file.upload.success");
				top.$.jBox.tip(success, "1", {
					persistent : true,
					opacity : 0
				});
			} else {
				var fail = accipiter.getLang_(messageLang,"file.upload.fail");
				top.$.jBox.tip(fail + responseText.substring(2), "1", {
					persistent : true,
					opacity : 0
				});
			}
		});
	</script>
</body>
</html>