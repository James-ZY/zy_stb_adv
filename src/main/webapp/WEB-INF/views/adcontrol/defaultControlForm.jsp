<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
 	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/common/evol-colorpicker.css">
	<style type="text/css">
	#createpicture{display:none}
    .createContent{width: 473px;background-color:#F5F5F5 ;border:1px solid #CCCCCC}
    .createContent .title-table{background-color: #F5F5F5}
    .createContent .title-table ul{padding: 0;list-style-type: none;margin-left: 0;}
    .createContent .title-table .set-ul{margin-bottom:0;}
    .createContent .title-table .set-chose{/*display: none*/}
    .createContent .title-table ul li{float: left;height: 30px;width: 30px;}
    .createContent .title-table .set-chose>ul li{width:47%;font-size: 13px;line-height: 30px;padding-left: 7px;position: relative;}
    .createContent .title-table .set-chose li .set-chose-content{width: 72px;max-height:200px;height: auto;/*overflow: hidden;*/position: absolute;display: inline-block;float: left;margin-left: 5px;}
    .createContent .title-table .set-chose li .set-bg{width: 210px;max-height: 200px;height: auto;}
    .createContent .title-table .set-chose li>label{display: inline-block;float: left;height:30px;line-height: 30px;}
    .createContent .title-table .set-chose li .set-chose-content input{width: 70px;padding: 0;height: 20px;text-indent: 7px;outline:none;background-color: #fff;}
    .createContent .title-table .set-chose li .set-chose-content ul{position:absolute;top:28px;height:auto;max-height: 173px;width: 70px;overflow: hidden;overflow-y: auto;border: 1px solid gainsboro;z-index: 2;background-color: #fff;display: none;margin-left:0;}
    .createContent .title-table .set-chose li .set-chose-content ul li{width: 100%;height: 20px;line-height: 20px;padding-left: 0;text-indent: 7px;}
    .createContent .title-table .set-chose li .set-chose-content ul li:hover{background-color: #11cd6e;cursor: pointer}
    .createContent .title-table .set-chose li select{width: 60px;}
    .createContent .title-table .set-chose li .set-font-style{width:130px;}
    .createContent .title-table .set-chose li .set-font-style input{width:130px;}
    .createContent .title-table .set-chose li .set-font-style ul{width:130px;}
    .createContent .title-table ul:after{visibility: hidden;content: "";display: block;clear: both}
    .createContent .title-table .set-ul a{width: 16px;height: 16px; display: inline-block;margin: 7px;}
    .createContent .title-table .set-ul .set-action {float:right;width: 100px;}
    .createContent .title-table .set-ul .set-action a{width:100px;height:16px;}
    .createContent .title-table ul a:hover{cursor: pointer}
    .createContent .title-table ul .set-font-size a{background:url("../../static/images/icon/set-font-size.png")}
    .createContent .title-table ul .set-font-size .active{background:url("../../static/images/icon/set-font-size-hover.png")}
    .createContent .title-table ul .set-font-size a:hover{background:url("../../static/images/icon/set-font-size-hover.png")}
    .createContent .title-table ul .set-font-color a{background:url("../../static/images/icon/set-font-color.png")}
    .createContent .title-table ul .set-font-color .active{background:url("../../static/images/icon/set-font-color-hover.png")}
    .createContent .title-table ul .set-font-color a:hover{background:url("../../static/images/icon/set-font-color-hover.png")}
    .createContent .title-table ul .set-font-style a{background:url("../../static/images/icon/yuyan.png")}
    .createContent .title-table ul .set-font-style .active{background:url("../../static/images/icon/yuyanhover.png")}
    .createContent .title-table ul .set-font-style a:hover{background:url("../../static/images/icon/yuyanhover.png")}
    .createContent .title-table ul .set-bg-size a{background:url("../../static/images/icon/set-bg-size.png")}
    .createContent .title-table ul .set-bg-size .active{background:url("../../static/images/icon/set-bg-size-hover.png")}
    .createContent .title-table ul .set-bg-size a:hover{background:url("../../static/images/icon/set-bg-size-hover.png")}
    .createContent .title-table ul .set-bg a{background:url("../../static/images/icon/set-bg.png")}
    .createContent .title-table ul .set-bg .active{background:url("../../static/images/icon/set-bg-hover.png")}
    .createContent .title-table ul .set-bg a:hover{background:url("../../static/images/icon/set-bg-hover.png")}
    .createContent .title-table .set-chose .set-bg-style a{width:100px;height: 20px;text-indent: 7px;background-color: #f3f3f3;border: 1px solid #ccc;}
    .createContent .title-table .set-chose .set-bg-style input[type="file"]{opacity: 0;}
    .createContent .title-table .set-chose .uploadPicture{width:100%;height: auto;padding-left:0}
    .createContent .title-table .set-chose .uploadPicture div{overflow: hidden;overflow-x: auto;}
    .createContent .title-table .set-chose .uploadPicture p{padding-left: 7px;}
    .createContent .title-table .set-chose .uploadPicture p .uploadErrInfo{color:#ea5200;margin-left: 10px;}
    .createContent .title-table .set-chose li .uploadBgPicture{height:20px;border: 0;padding: 0;margin-top: 4px;line-height: 20px;white-sapce:nowrap;overflow:hidden;text-overflow:ellipsis;margin-left: -70px;display: inline-block}
    .createContent .title-table .set-chose li .uploadTitle{height:20px;border: 1px solid #ccc;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;padding: 0;margin-top: 4px;line-height: 20px;white-sapce:nowrap;overflow:hidden;text-overflow:ellipsis;display: inline-block;width:70px;background-color: #fff;margin-left: 5px;}
    .createContent .set-text{width: 100%;z-index: 3;}
    .createContent .set-text textarea{width: 473px;max-width:473px;height:100px;padding: 20px 0 0 0;background-color: #fff;margin: 0 0 7px 0;text-indent: 20px;border:0;border-top:1px solid #cccccc;border-radius: 0;-webkit-border-radius: 0;-moz-border-radius: 0;outline:none}
    .createContent .set-text .imgContent{width: 473px;height:90px;margin-bottom:10px;border-top: 1px solid #cccccc;background-color: #fff;overflow: hidden;overflow-x:auto}
    .createContent .set-text .imgContent img{max-width:auto}
    .createContent .set-text  p{text-indent:7px;margin-bottom: 0;}
    .createContent .set-text .imgContent p{text-indent:20px;}
    .createContent .title-table .set-chose li .set-bg-style input{width:100px;}
    .createContent .title-table .set-chose li .set-bg-style ul{width:100px;}
    .createContent .title-table .set-chose li .set-bg-style{width:100px;position: relative;}
    .createContent .title-table .set-chose li #uploadBgPicture{height:20px;border: 0;padding: 0;margin-top: 4px;line-height: 20px;white-sapce:nowrap;overflow:hidden;text-overflow:ellipsis;margin-left: -70px;display: inline-block;position: absolute;}
    .set-chose-content i{display: block;position: absolute;top: 8px;right: 0px;}
    .set-chose-content .icon-down{background:url("../../static/images/icon/xiangxia.png")no-repeat;background-position: center;}
    
    /*选色样板样式*/
    .evo-cp-wrap{margin: 0}
    .evo-colorind, .evo-colorind-ie, .evo-colorind-ff{ margin-top: 6px;display: inline-block;top: 0px;border: solid 1px #c3c3c3; width: 18px;height: 18px;float: right;}
    .evo-pop{border: 1px solid gainsboro}
    .pictureType{display:none;}
	</style>
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
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='combo.setSystem' /></li>
    <li>></li>
    <li><spring:message code='adv.defaultControl' /></li>
    <li>></li>
    <li>
     	<shiro:hasPermission name="adv:adSet:edit"><a href="${ctx}/adv/defaultControl/form?id=${adType.id}">
		<c:choose><c:when test="${not empty adType.id}"><spring:message code='defaultControl.update' /></c:when>
        			<c:otherwise><spring:message code='defaultControl.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/defaultControl"><spring:message code='defaultControl.list' /></a></li>
		<shiro:hasPermission name="adv:adSet:edit"><li  class="active"><a href="${ctx}/adv/defaultControl/form?id=${adDefaultControll.id}">
		<c:choose><c:when test="${not empty adDefaultControll.id}"><spring:message code='defaultControl.update' /></c:when>
        			<c:otherwise><spring:message code='defaultControl.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adDefaultControll" action="${ctx}/adv/defaultControl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	 <c:if test="${isNotAdv}">
	 	 	<div class="control-group">
	 	 	<label class="control-label"><spring:message code='network.name' />：</label>
	 	 	<div class="controls">
					<form:select path="adNetwork.id" class="required" id="adNetwork">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:findAdNetworkAll()}" itemLabel="networkName" itemValue="id" htmlEscape="false"/>
					</form:select>
			</div>
			</div>
	 	 </c:if>
	 	<div class="control-group">
			<label class="control-label"><spring:message code='control.flag' />:</label>
			<div class="controls">
				<form:select path="flag" class="required">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.type' />:</label>
			<div class="controls">
				<form:select path="adType.id" class="select required" id="adType">
					<option value=""> <spring:message code='userform.select' /></option>
					<form:options items="${fns:getAdTypeById('1,3,9')}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<label class="info-messages"><p></p></label>
			</div>
		</div>
		<div class="control-group pictureType">
		    <label class="control-label"><spring:message code='adv.pictureType' />:</label>
			<div class="controls">
				<select class="selectPictureType">
					<option value=""> <spring:message code='userform.select' /></option>
					 	<c:forEach items="${fns:getDictList('roll_image_select')}" var="dict">
					 		<option value="${dict.value}">${dict.label}</option>
					 	</c:forEach>
			
 
				</select>
			</div>
		</div>
			<form:hidden path="resourceType"/>
			<form:hidden path="path"/> 
			<form:hidden path="vedioImagePath"/> 
			<form:hidden path="fileSize"/> 	
	    <div class="control-group control-group-auto" id="adv_image">
		     <label class="control-label"><spring:message code="adv.image"/>:</label>
		     <div class="controls">		     	
		         <div class="dropzoneStyle upload_image" id="upload_adv_image"></div>
		     </div>
		</div>
		<div class="control-group control-group-auto" id="adv_kaijiimage">
		     <label class="control-label"><spring:message code="adv.image"/>:</label>
		     <div class="controls">		     	
		         <div class="dropzoneStyle upload_kaijiimage" id="upload_adv_kaijiimage"></div>
		     </div>
		</div>
		<div class="control-group control-group-auto" id="adv_vedio">
		     <label class="control-label"><spring:message code="adv.vedio"/>:</label>
		     <div class="controls">
		         <div class="dropzoneStyle upload_vedio" id="upload_adv_vedio"></div>
		     </div>
		</div>
		

		<c:if test="${not empty adDefaultControll.id}">
			<div class="control-group">
				<label class="control-label"><spring:message code='control.format' />:</label>
				<div class="controls">
					<label class="lbl" id="pictureType">${adDefaultControll.fileFormat}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='control.width' />:</label>
				<div class="controls">
					<label class="lbl">${adDefaultControll.width}</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='control.height' />:</label>
				<div class="controls">
					<label class="lbl">${adDefaultControll.height}</label>
				</div>
			</div>
 
		</c:if>
		<div class="control-group control-group-auto" id="createpicture">
			   <label class="control-label"><spring:message code="create.picture"/>:</label>
				<div class="controls">
					<div class="createContent">
					     <div class="title-table">
					         <ul class="set-ul">
					             <li class="set-font-style" style="display:none"><a></a></li>
					             <li class="set-font-size"><a></a></li>
					             <li class="set-font-color"><a></a></li>
					             <li class="set-bg-size"><a></a></li>
					             <li class="set-bg"><a></a></li>
					             <li class="set-action"><a name="0"><spring:message code='Generate.picture'/></a></li>
					         </ul>
					         <div class="set-chose">
					             <ul>
					             </ul>
					         </div>
					     </div>
					     <div class="set-text">
					         <textarea class="required" maxlength="400" placeholder="<spring:message code="add.newtext"/>"></textarea>
					         <p><spring:message code="picture.Preview"/>:</p>
					         <div class="imgContent"></div>
					     </div>
					 </div>
				</div>
		</div>
		 
		<div class="form-actions">
			<shiro:hasPermission name="adv:adSet:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
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
	<script src="${ctxStatic}/common/jquery-ui.min.js"></script>
	<script src="${ctxStatic}/dropzone/dropzone.min.js"></script>
	<script src="${ctxStatic}/common/evol-colorpicker.min.js"></script>
	<script src="${ctxStatic}/adv/adv_default_control.js"></script>
</body>
</html>