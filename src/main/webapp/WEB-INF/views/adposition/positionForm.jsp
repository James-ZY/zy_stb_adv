<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<style type="text/css">
	.attention-info {position: relative;}
	.attention-info p{margin-bottom:0}
	.attention-info .close{position: absolute;right:20px;top:50%;margin-top:-10px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			 var host=accipiter.getRootPath();
			 function JudgePositionY(value){
				 var errorInfo=[true,false];
				 var beginPointX=$("#beginPointX").val();
				 var beginPointY=$("#beginPointY").val();
				 var endPointX=$("#endPointX").val();
				 var endPointY=$("#endPointY").val();
				 if(beginPointX!=""&&beginPointY!=""&&endPointX!=""&&endPointY!=""){
					 if(parseInt(beginPointX)!=parseInt(endPointX)){
						 if(parseInt(endPointY)!=parseInt(beginPointY)){
							 return errorInfo[1];
						 }else{
							 return errorInfo[0];
						 }
					 }else{
						 return errorInfo[0];
					 }
				 }else{
					 return errorInfo[0];
				 }
			 }
			 function JudgePositionX(value){
				 var errorInfo=[true,false];
				 var beginPointX=$("#beginPointX").val();
				 var beginPointY=$("#beginPointY").val();
				 var endPointX=$("#endPointX").val();
				 var endPointY=$("#endPointY").val();
				 if(beginPointX!=""&&beginPointY!=""&&endPointX!=""&&endPointY!=""){
					 if(parseInt(endPointY)!=parseInt(beginPointY)){
						 if(parseInt(endPointX)!=parseInt(beginPointX)){
							 return errorInfo[1];
						 }else{
							 return errorInfo[0];
						 }
					 }else{
						 return errorInfo[0];
					 }
				 }else{
					 return errorInfo[0];
				 }
			 }
			$("#pointId").focus();
			jQuery.validator.addMethod("JudgePositionY",function(value, element) {
				return this.optional(element) || (JudgePositionY(value));
			},accipiter.getLang_(messageLang,"adv.PositionY"));
			jQuery.validator.addMethod("JudgePositionX",function(value, element) {
				return this.optional(element) || (JudgePositionX(value));
			},accipiter.getLang_(messageLang,"adv.PositionX"));
			$(".position").off("keyup").on("keyup",function(){
				 var beginPointX=$("#beginPointX").val();
				 var beginPointY=$("#beginPointY").val();
				 var endPointX=$("#endPointX").val();
				 var endPointY=$("#endPointY").val();
				 if(beginPointX!=""&&beginPointY!=""&&endPointX!=""&&endPointY!=""){
					if($(this).valid()==true){
						$(".setPosition").find('label[class="error"]').css("display","none");
						$(".setPosition").find("input").removeClass("error");
						$(".setPosition").find("input").addClass("valid");
					}					 
				 }
				if($(this).valid()==true){
					$(".position")
				}
			})
			$("#inputForm").validate({
				rules: {
					pointId: {remote: "${ctx}/adv/position/checkpointId?oldPointId=" + encodeURIComponent('${adPosition.pointId}')}
					
				},
				messages: {
					pointId: {remote:accipiter.getLang("adPositiontIdExist")}
				 
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
			function getPositionData(){
				var advType=$("#position_type").children('option:selected').val();
				var Status=$("#status").children('option:selected').val()
				var status=parseInt(Status);
				if(advType!=""&&Status!=""){
					var data={"typeId":advType,"flag":status};
				    var postData = JSON.stringify(data);
					$.ajax({
						type:"post",
						async:false,
						data:postData,
						url:host+"/adv/range/find_range_type_id",
						contentType:"application/json; charset=UTF-8",
						dataType:"json",
						success:function(data){
							if(data==""){
								$(".positionX").attr({"placeholder":""});
								$(".positionY").attr({"placeholder":""});
								if($("#position_type").next()!=undefined){
									$("#position_type").next().remove();
								}
								$("#position_type").after('<label for="position_type" class="error errorInfo">'+accipiter.getLang("changeTypeData")+'</label>');
								$(".position").attr("disabled",true);
								$("#btnSubmit").attr("disabled",true);
							}else{
								if($("#position_type").next()!=undefined){
									$("#position_type").next().remove();
								}
								$(".position").attr("disabled",false);
								$("#btnSubmit").attr("disabled",false);
								var xRange=[parseInt(data.beginX),parseInt(data.endX)];
								var yRange=[parseInt(data.beginY),parseInt(data.endY)];
								var xRangeinfo=accipiter.getLang("inputValueRange")+data.beginX+"~"+data.endX;
								var yRangeinfo=accipiter.getLang("inputValueRange")+data.beginY+"~"+data.endY;
								$(".positionX").attr({"range":xRange,"placeholder":xRangeinfo});
								$(".positionY").attr({"range":yRange,"placeholder":yRangeinfo});
							}
						}
					})
				}
			}
			$("#position_type,#status").change(function(){
				getPositionData();
			});
			if($("#id").val()!=""){
				getPositionData();
			}
			
		});
		
		 
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adv.show' /></li><li>></li>
    <li><spring:message code='adv.showStyleManage' /></li>
    <li>></li>
    <li>
    	<shiro:hasPermission name="adv:position:edit"><a href="${ctx}/adv/position/form?id=${adPosition.id}">
		<c:choose><c:when test="${not empty adPosition.id}"><spring:message code='position.update' /></c:when>
        			<c:otherwise><spring:message code='position.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
    </li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/position"><spring:message code='position.list' /></a></li>
		<shiro:hasPermission name="adv:position:edit"><li  class="active"><a href="${ctx}/adv/position/form?id=${adPosition.id}">
		<c:choose><c:when test="${not empty adPosition.id}"><spring:message code='position.update' /></c:when>
        			<c:otherwise><spring:message code='position.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adPosition" action="${ctx}/adv/position/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="attention-info show alert alert-success">
		<p><strong><spring:message code='adv.PositionAttention' />:</strong>&nbsp;&nbsp;<spring:message code='adv.PositionDescription' /></p>
		<button data-dismiss="alert" class="close">×</button>
		</div>
	 	 
		<div class="control-group">
			<label class="control-label"><spring:message code='position.id' />:</label>
			<div class="controls">
				<input id="oldPointId" name="oldPointId" type="hidden" value="${adPosition.pointId}">
				<form:input path="pointId" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='position.name' />:</label>
			<div class="controls">
				<form:input path="positionName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		
		<!--  <div class="control-group">
			<label class="control-label"><spring:message code='position.isflag' />:</label>
			<div class="controls">
				<form:select path="isFlag" class="required" id="position_flag" onchange="checkType();">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_position_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>-->
		
		<div class="control-group">
			<label class="control-label"><spring:message code='range.type' />:</label>
			<div class="controls">
				<form:select path="adType.id" id="position_type" class="required">
					<option value=""> <spring:message code='userform.select' /></option>
					<form:options items="${fns:getAdTypeByIsPosition(1)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='range.resolution' />:</label>
			<div class="controls">
				<form:select path="status" class="required">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
	   <div class="control-group">
	   		 
			<div id="beginx" >
				<label class="control-label"><spring:message code='position.beginx' />:</label>
			</div>
			<div class="controls setPosition">
				<form:input path="beginPointX" htmlEscape="false" maxlength="50" class="position positionX required digits JudgePositionX"/>
			</div>
	  </div>
	   <div class="control-group">
	   
			<div id="endy" style="display:block;">
				<label class="control-label"><spring:message code='position.beginy' />:</label>
			</div>
			<div class="controls setPosition">
				<form:input path="beginPointY" htmlEscape="false" maxlength="50" class="position positionX required digits JudgePositionY"/>
			</div>
	  </div>
	  
	  <div class="control-group">
	   		 
			<div id="beginx" >
				<label class="control-label"><spring:message code='position.endx' />:</label>
			</div>
			<div class="controls setPosition">
				<form:input path="endPointX" htmlEscape="false" maxlength="50" class="position positionY required digits JudgePositionX"/>
			</div>
	  </div>
	   <div class="control-group">
	   
			<div id="endy" style="display:block;">
				<label class="control-label"><spring:message code='position.endy' />:</label>
			</div>
			<div class="controls setPosition">
				<form:input path="endPointY" htmlEscape="false" maxlength="50" class="position positionY required digits JudgePositionY"/>
			</div>
	  </div>
	  <div id="move" style="display:none">
	  	
	   
		<div class="control-group">
				<label class="control-label"><spring:message code='position.velocity' />:</label>
				<div class="controls">
					<form:input path="velocity" htmlEscape="false" maxlength="50" />
				</div>
		</div>
	  </div>
	  
		<div class="form-actions">
			<shiro:hasPermission name="adv:position:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/adv/position.js"></script>
</body>
</html>