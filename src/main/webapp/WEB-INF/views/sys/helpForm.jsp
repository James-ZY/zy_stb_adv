
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='helpionary.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<link rel="stylesheet" href="${ctxStatic}/layer/skin/myskin/style.css">
	<style>
	.dropzoneStyle .dz-preview.dz-file-preview [data-dz-thumbnail], .dropzoneStyle-previews .dz-preview.dz-file-preview [data-dz-thumbnail]{display:block}
	</style>
    <script src="${ctxStatic}/dropzone/dropzone.min.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
    	<script type="text/javascript">
		$(document).ready(function() {
		    	$("#oldFilePath").val("${help.filePath}");
		    /******弹框样式全局配置*******/
		    layer.config({
		     extend: 'myskin/style.css',
		     skin: 'layui-layer-rim',
		     shadeClose: true, //点击遮罩关闭
		     type: 1,
		     title:accipiter.getLang_(lang,"t3"),
		     area: ['350px', '200px'],
		        btn: [accipiter.getLang_(messageLang,"import.confirm"),accipiter.getLang_(messageLang,"import.close")],
		        content: '\<\div style="padding:25px 20px;text-align: center;"><i></i><span>'+accipiter.getLang_(messageLang,"file.notNull")+'</span>\<\/div>',
		     });
			$("#value").focus();
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
		        /*文件上传*/
 		        Dropzone.autoDiscover = false;
		        $("#uploadHelp").dropzone({
		            url:"${ctx}/file/helpFileUpload",
		            maxFiles: 1,
		            maxFilesize: 30,
		            acceptedFiles:".doc,.docx,.pdf,.xls",
		            dictDefaultMessage: "",
		            dictRemoveFile: accipiter.getLang_(messageLang,"import.remove"),
		            dictFileTooBig: accipiter.getLang_(messageLang,"file.too.big"),
		            dictMaxFilesExceeded: accipiter.getLang_(messageLang,"image.maxsize"),
		            dictResponseError: accipiter.getLang_(messageLang,"image.upload.fail"),
		            dictCancelUpload: accipiter.getLang_(messageLang,"cancle.upload"),
		            addRemoveLinks:true,
		            autoProcessQueue:false,
		            /* uploadMultiple:true,*/
		            parallelUploads:100,
		            init: function() {
			      	    var fileDropzone=this;
			      		var path=$("#filePath").val();
			      		var fileName=$("#fileName").val();
			      		if(path!=""){
			      			var data=[{url:path,name:fileName}];
			  				var existingFileCount = data.length;
			      			$.each(data,function(infoIndex,info){
			      				var mockFile = { name:info["name"]};
			      				fileDropzone.emit("addedfile", mockFile);
			      				fileDropzone.emit("thumbnail", mockFile, info["url"]);
			      				$("#uploadHelp").find(".dz-preview").attr("class","dz-preview dz-image-preview dz-old dz-success");
			      				$("#uploadHelp").find(".dz-details").find("img").attr("src","${ctxStatic}/images/icon/doc.png");
			      			});
			      			fileDropzone.options.maxFiles = fileDropzone.options.maxFiles - existingFileCount;
			      		}
						this.on("addedfile", function (file){
							 $("#uploadHelp").find(".dz-details").find("img").attr("src","${ctxStatic}/images/icon/doc.png");
						});
		                this.on("success", function(file,data) {
		                	$("#uploadHelp").find(".dz-details").find("img").attr("src","${ctxStatic}/images/icon/doc.png");
		                	var control=data.split("`")[0];
		                	if(control==1){
			                	var filePath=data.split("`")[1];
			                	var fileName=data.split("`")[2];
			                	$("#filePath").val(filePath);
			                	$("#fileName").val(fileName);
			                 	$("#inputForm").submit(); 
		                	}else{
		                		var errorinfo=data.substring(2);
		                		$("#uploadHelp").find('.dz-preview').attr("class","dz-preview dz-image-preview dz-error");
		                		$("#uploadHelp").find('.dz-preview').find(".dz-error-message").text(errorinfo);
		                	}
		                });
				        this.on("removedfile",function(file){
				        	   var errorcount=$("#uploadHelp").find(".dz-error").length;
				        	   var successcount=$("#uploadHelp").find(".dz-success").length;
				        	   if((successcount==undefined&&errorcount==undefined)||(successcount==0&&errorcount==0)){
				        		   fileDropzone.options.maxFiles=1;
				        	   }
				            });
		                $("#btnSubmit").on("click",function(){
		                	var total=$("#uploadHelp .dz-preview").length;
		                	var success=$("#uploadHelp .dz-success").length;
		                	var error=$("#uploadHelp .dz-error").length;
		                	if((total>0&&error==undefined)||(total>0&&error==0)){
		                		if(success==0||success==undefined){
			                       	fileDropzone.processQueue();
			                       	loading(accipiter.getLang('loading'));
			                		return false;
		                		}
		                	}else{
		                		if(total==0||total==undefined){
			                        layer.open({
			                            yes:function (index, layero) {
			                                layer.close(index);
			                            },
			                            btn2:function (index, layero) {
			                                layer.close(index);
			                            }
			                        })
		                		}
		                		return false;
		                	}
		                });
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
	        <li><spring:message code="about.help"/></li>
	         
	       
	          <shiro:hasPermission name="sys:help:edit">
			  <li>></li>
				<li>
			    <a href="${ctx}/sys/help/form?id=${help.id}">
				  <c:choose>
				  <c:when test="${not empty help.id}"><spring:message code='help.update' />
				  </c:when>
				  <c:otherwise><spring:message code='help.add' /></c:otherwise>
				  </c:choose>
				 </a>
			  </li>
	          </shiro:hasPermission>
	        
	         
         </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/help/"><spring:message code='help.list' /></a></li>
		<li class="active">
			    <a href="${ctx}/sys/help/form?id=${help.id}">
				  <c:choose>
				  <c:when test="${not empty help.id}"><spring:message code='help.update' />
				  </c:when>
				  <c:otherwise><spring:message code='help.add' /></c:otherwise>
				  </c:choose>
				 </a>
	   </li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="help" action="${ctx}/sys/help/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		       
		<tags:message content="${message}"/>
		<form:hidden path="filePath"/>
		<form:hidden path="oldFilePath"/>
		<form:hidden path="fileName"/>
		<div class="control-group">
			
			<label class="control-label"><spring:message code='help.file.flag' />:</label>
			<div class="controls">
				 
				<form:select path="flag"  >
					<form:options items="${fns:getDictList('help_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			 </form:select>
			 <label class="info-messages"><spring:message code='help.file.max' /><p></p></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='language' />:</label>
			<div class="controls">
				 
				<form:select path="helpLocale"  >
					<form:options items="${adv_language}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			 </form:select>
			</div>
		</div>
	    <div class="control-group control-group-auto help_upload">
		     <label class="control-label"><spring:message code="help.upload"/>:</label>
		     <div class="controls">		     	
		         <div class="dropzoneStyle upload_image" id="uploadHelp"></div>
		     </div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:help:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>