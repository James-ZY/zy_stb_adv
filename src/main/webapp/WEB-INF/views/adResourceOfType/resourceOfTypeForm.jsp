<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">
	.videoItems{display:none}
	.imgItems{display:none}
	.gifItems{display:none}
	</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var host = accipiter.getRootPath();
			jQuery.validator.addMethod("widthMin",function(value, element) {
				var status=true;
				if($(".widthMax").val()==""){
					status=true;
				}else{
					status=(parseInt(value) <= parseInt($(".widthMax").val()));
				}
				return status;
			},accipiter.getLang_(messageLang,"parameter.widthMin"));
			jQuery.validator.addMethod("widthMax",function(value, element) {
				var status=true;
				if($(".widthMin").val()==""){
					status=true;
				}else{
					status=(parseInt(value) >=parseInt($(".widthMin").val()));
				}
				return status;
			},accipiter.getLang_(messageLang,"parameter.widthMax"));
			jQuery.validator.addMethod("highMin",function(value, element) {
				var status=true;
				if($(".highMax").val()==""){
					status=true
				}else{
					status=(parseInt(value) <= parseInt($(".highMax").val()));
				}
				return status;
			},accipiter.getLang_(messageLang,"parameter.heightMin"));
			jQuery.validator.addMethod("highMax",function(value, element) {
				var status=true;
				if($(".highMin").val()==""){
					status=true;
				}else{
					status=(parseInt(value) >= parseInt($(".highMin").val()));
				}
				return status;
			},accipiter.getLang_(messageLang,"parameter.heightMax"));
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
			/*item展示  */
			function showItems(id){
				if(id=="5"){
					$(".rollType").css("display","block");
				}else{
					$(".rollType").css("display","none");
					$("#rollFlag").val("");
				}
				var data={id:id};
				var postData = JSON.stringify(data);
				$.ajax({
					url :host + "/adv/type/find_type_status",
					type :"post",
					async:false,
					data :postData,
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success:function(data){
						if(data!=null){
							if(data=="0"){
								$(".imgItems").css("display","block");
							}else{
								$(".imgItems").css("display","none");
							}
							getFormat(data);
						}						
					}
				})
			}
			/*获取索材格式 */
			function getFormat(m){
				var imageformat=$("#imageformat").text();
				var vedioformat=$("#vedioformat").text();
				var imgFormatData=JSON.parse(imageformat);
				var vFormatData=JSON.parse(vedioformat);
				var html='';
				if(m==0){
					$.each(imgFormatData,function(commentIndex,comment){
						var id="formatList"+commentIndex;
						html+='<span>'+
						'<input id='+id+' name="formatList" class="required" type="checkbox" value='+comment["value"]+'>'+
						'<label for='+id+'>'+comment["label"]+'</label>'+
						'</span>';
					})
				}else{
					$.each(vFormatData,function(commentIndex,comment){
						var id="formatList"+commentIndex;
						html+='<span>'+
						'<input id='+id+' name="formatList" class="required" type="checkbox" value='+comment["value"]+'>'+
						'<label for='+id+'>'+comment["label"]+'</label>'+
						'</span>';
					})
				}
				$(".formatContent").html("");
				$(".formatContent").html(html);
			}
			/* 获取选中的 */
			function getFormatSelected(){
				var len=$(".formatContent").find('input[type="checkbox"]:checked').length; 
				var format="";
				if(len>0){
					if(len==0){
						format=$(".formatContent").find('input[type="checkbox"]:checked').val();
					}else{
						for(var i=0;i<len;i++){
							if(i==len-1){
								format+=$(".formatContent").find('input[type="checkbox"]:checked:eq('+i+')').val();
							}else{
								format+=$(".formatContent").find('input[type="checkbox"]:checked:eq('+i+')').val()+",";
							}
						}
					}					
					$("#format").val(format);
				}else{
					format="";
				}
			}
			$(".adTypeId").on("change",function(){
				var id=$(".adTypeId").find('option:selected').val();
				if(id!=""){
					showItems(id);
					/* 清除之前预设数据 */
					$("#format").val("");
					$(".gifItems").css("display","none");
					$("#frameMin").val("");
					$("#frameMax").val("");
					if(id=="5"){
						$(".rollType").css("display","block");
					}else{
						$(".rollType").css("display","none");
						$("#rollFlag").val("");
					}
				}				
			})
			/*索材格式 选取监听 */
			$(".formatContent").on("click","input",function(){
				var gifSelected=$(".formatContent").find('input[value="gif"]').attr("checked");
				if($(this).val()=="gif"){
					if(gifSelected){
						$(".gifItems").css("display","block");
					}else{
						$(".gifItems").css("display","none");
						$("#frameMin").val("");
						$("#frameMax").val("");
					}
				}else{
					if(gifSelected){
						$(".gifItems").css("display","block");
					}else{
						$(".gifItems").css("display","none");
						$("#frameMin").val("");
						$("#frameMax").val("");
					}
				}
			})
			$("#btnSubmit").on("click",function(){
				getFormatSelected();
			});
			/*修改时回显  索材格式 */
			if($("#id").val()!=""){
				showItems($(".adTypeId").find('option:selected').val());
				var format=$("#format").val().split(",");
				$.each(format,function(commentIndex,comment){
					$(".formatContent").find('input[value='+comment+']').attr("checked",true);
					if(comment=="gif"){
						$(".gifItems").css("display","block");
					}					
				})
				
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
    <li><spring:message code='resource.manage' /></li>
    <li>></li>
   
     	<shiro:hasPermission name="adv:adResourceOfType:edit">
     	 <li>
     	<a href="${ctx}/adv/resource/form?id=${adResourceOfType.id}">
		<c:choose><c:when test="${not empty adResourceOfType.id}"><spring:message code='resource.update' /></c:when>
        			<c:otherwise><spring:message code='resource.add' /></c:otherwise></c:choose></a>
          </li>
        </shiro:hasPermission>
   
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/resource"><spring:message code='resource.list' /></a></li>
		<shiro:hasPermission name="adv:adResourceOfType:edit"><li  class="active"><a href="${ctx}/adv/resource/form?id=${adResourceOfType.id}">
		<c:choose><c:when test="${not empty adResourceOfType.id}"><spring:message code='resource.update' /></c:when>
        			<c:otherwise><spring:message code='resource.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
		 
	<form:form id="inputForm" modelAttribute="adResourceOfType" action="${ctx}/adv/resource/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="format"/>
		<tags:message content="${message}"/>
		<p id="imageformat" style="display:none">${imageformat}</p>
		<p id="vedioformat" style="display:none">${vedioFormat}</p>
	

 
		<div class="control-group">
			<label class="control-label"><spring:message code='range.type' />：</label>
			<div class="controls">
			<form:select path="adType.id" class="adTypeId required">
				<option value=""><spring:message code="userform.select"/></option>
				<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
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
		
	<div class="control-group rollType" style="display:none">
			<label class="control-label"><spring:message code='adv.rollType' />:</label>
			<div class="controls">
				<form:select path="rollFlag" class="required">
						<form:options items="${fns:getDictList('ad_gd_sc_fx')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		  
		 <div class="control-group">
				<label class="control-label"><spring:message code='file.max.size' />:</label>
				<div class="controls">
					<form:input path="fileMaxSize" htmlEscape="false" maxlength="5" class="required number"/>
				</div>
		</div>
		<div class="imgItems">
			<div class="control-group">
					<label class="control-label"><spring:message code='width.min.size' />:</label>
					<div class="controls">
						<form:input path="widthMin" htmlEscape="false" maxlength="50" class="required digits widthMin " min="1" max="10000"/>
					</div>
			</div>
			 <div class="control-group">
					<label class="control-label"><spring:message code='width.max.size' />:</label>
					<div class="controls">
						<form:input path="widthMax" htmlEscape="false" maxlength="50" class="required digits widthMax" min="1" max="10000" />
					</div>
			</div>
			
				 <div class="control-group">
					<label class="control-label"><spring:message code='height.min.size' />:</label>
					<div class="controls">
						<form:input path="highMin" htmlEscape="false" maxlength="50" class="required digits highMin" min="1" max="10000"/>
					</div>
			</div>
			 <div class="control-group">
					<label class="control-label"><spring:message code='height.max.size' />:</label>
					<div class="controls">
						<form:input path="highMax" htmlEscape="false" maxlength="4" class="required digits highMax" min="1" max="10000"/>
					</div>
			</div>
			<div class="gifItems">
				 
				 <div class="control-group">
						<label class="control-label"><spring:message code='frame.max.size' />:</label>
						<div class="controls">
							<form:input path="frameMax" htmlEscape="false" maxlength="4" class="required digits" min="1" max="256" />
						</div>
				</div>
			</div>
		</div>
		<div class="videoItems">
			<div class="control-group">
					<label class="control-label"><spring:message code='rate.min.size' />:</label>
					<div class="controls">
						<form:input path="rateMin" htmlEscape="false" maxlength="4" class="required" min="0"/>
					</div>
			</div>
			 <div class="control-group">
					<label class="control-label"><spring:message code='rate.max.size' />:</label>
					<div class="controls">
						<form:input path="rateMax" htmlEscape="false" maxlength="4" class="required" min="1" />
					</div>
			</div>
		</div>	 
		 <div class="control-group">
			<label class="control-label"><spring:message code='material.format' />:</label>
			<div class="controls controls_s formatContent">
				<form:checkboxes path="formatList" items="${fns:getDictList('adv_resource_image_format')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
			</div>
		</div>
		 
	
		<div class="form-actions">
			<shiro:hasPermission name="adv:adResourceOfType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>