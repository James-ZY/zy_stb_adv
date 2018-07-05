<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<link rel="stylesheet" href="${ctxStatic}/common/jquery-ui.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctx}/static/common/jquery-ui.js"></script>
	<script src="${ctxStatic}/adv/drag1.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var maxWidth = 714; 
			var maxHeight = 570; 
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
			$("#btnSubmit").on("click",function(){
				var beginX=parseInt($("#beginX").val());
				var endX=parseInt($("#endX").val());
				var beginY=parseInt($("#beginY").val());
				var endY=parseInt($("#endY").val());
				if(beginX!=NaN&&endX!=NaN){
					if(beginX>endX){
						if($("#endX").next()!=undefined){
							$("#endX").next().remove();
						}
						$("#endX").after('<label for="beginX" class="error errorInfo">'+accipiter.getLang("xminlessmax")+'</label>');
						return false;
					}
				}
				if(beginY!=NaN&&endY!=NaN){
					if(beginY>endY){
						if($("#endY").next()!=undefined){
							$("#endY").next().remove();
						}
						$("#endY").after('<label for="beginX" class="error errorInfo">'+accipiter.getLang("yminlessmax")+'</label>');
						return false;
					}
				}
			})
			$("#endX,#endY").off("keyup").on("keyup",function(){
				if($(this).parent().find(".errorInfo")!=undefined){
					$(this).parent().find(".errorInfo").html("");
				}
			});

            $("#beginX,#beginY,#endX,#endY").change(function() {
                var beginX =parseInt($("#beginX").val());
                var beginY = parseInt($("#beginY").val());
                var endX = parseInt($("#endX").val());
                var endY = parseInt($("#endY").val());
                $("#drag1").css({"left":beginX,"top":beginY});
                $("#resizable2").css("width",endX-beginX);
                $("#resizable2").css("height",endY-beginY);
            });

		$(function() {
			var a = document.getElementById("flag");
			var b = a.options[a.selectedIndex].value;
			flagChange(b);
		});
			$("#flag").change(function(){
				var vle = $(this).val();
				flagChange(vle);
				resizable(vle);
			});
			
			function flagChange(vle){
				var host = accipiter.getRootPath();
				if(vle == 0){
					$(".ad_Preview").css("height","720px");
					$(".ad_Preview .tv_icon").css({"width":"800px","height":"656px","background":"url("+host+"/static/images/icon/tv-icon.jpg)"});
					$(".ad_Preview .Preview_content").css({"width":"726px","height":"582px"});
				}else if(vle ==1){
					$(".ad_Preview").css("height","870px");
					$(".ad_Preview .tv_icon").css({"width":"1360px","height":"800px","background":"url("+host+"/static/images/icon/tv-icon2.jpg)"});
					$(".ad_Preview .Preview_content").css({"width":"1286px","height":"726px"});
				}
				initRes();
			}
		});
		$(function() {
			$('.drag1').each(function() {
				$(this).dragging({
					move: 'both',
					randomPosition: false,
					hander: '.hander'
				});
			});
		});
		
		function resizable(b){
			var maxWidth = 720; 
			var maxHeight = 576; 
			if(b==1){
				maxWidth = 1280;
				maxHeight = 720;
			}
			$("#resizable2").resizable({
				  maxHeight: maxHeight,
			      maxWidth: maxWidth,
			      minHeight: 30,
			      minWidth: 30
			});
		}
		$(function() {
			var a = document.getElementById("flag");
			var b = a.options[a.selectedIndex].value;
			resizable(b);
			initRes();
		});
		function initRes(){
			var beginX = ${adRange.beginX==null?0:adRange.beginX};
			var beginY = ${adRange.beginY==null?0:adRange.beginY};
			var endX = ${adRange.endX==null?150:adRange.endX};
			var endY = ${adRange.endY==null?150:adRange.endY};
			$("#beginX").val(beginX);
            $("#beginY").val(beginY);
            $("#endX").val(endX);
            $("#endY").val(endY);
			$("#drag1").css({"left":beginX,"top":beginY});
			$("#resizable2").css("width",endX-beginX);
			$("#resizable2").css("height",endY-beginY);
		}

	</script>
		<!-- 引用css -->
    <style type="text/css">
     #resizable2 img {
		width:100%;
		height:100%;
	}
	span {
		font-size:100%;
	}

	i.hander {
		display:block;
		position: absolute;
		width:100%;
		height:100%;
		background:#ccc;
		filter:alpha(opacity=0);  
		-moz-opacity:0;  
		-khtml-opacity: 0;  
		opacity: 0;
	}
    </style>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adv.show' /></li>
    <li>></li>
    <li><spring:message code='adv.rangeManage' /></li>
    <li>></li>
    <li>
     	<shiro:hasPermission name="sys:range:edit"><a href="${ctx}/adv/range/form?id=${adRange.id}">
		<c:choose><c:when test="${not empty adRange.id}"><spring:message code='range.update' /></c:when>
        			<c:otherwise><spring:message code='range.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/range"><spring:message code='range.list' /></a></li>
		<shiro:hasPermission name="sys:range:edit"><li  class="active"><a href="${ctx}/adv/range/form?id=${adRange.id}">
		<c:choose><c:when test="${not empty adRange.id}"><spring:message code='range.update' /></c:when>
        			<c:otherwise><spring:message code='range.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adRange" action="${ctx}/adv/range/${empty adRange.id ? 'save' : 'update' }" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	 
		<div class="control-group">
			<label class="control-label"><spring:message code='range.name' />:</label>
			<div class="controls">
				 
				<form:input path="rangeName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>

 
		<div class="control-group">
			<label class="control-label"><spring:message code='range.type' />：</label>
			<div class="controls">
			<form:select path="type.id" class="required">
				<option value=""><spring:message code="userform.select"/></option>
				<form:options items="${fns:getAdTypeByIsPosition(1)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
			</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><spring:message code='range.resolution' />:</label>
			<div class="controls">
				<form:select path="flag" class="required">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='range.beginx' />:</label>
			<div class="controls">
				<form:input path="beginX" htmlEscape="false" type="number" maxlength="50" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='range.endx' />:</label>
			<div class="controls">
				<form:input path="endX" htmlEscape="false" type="number" maxlength="50" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='range.beginy' />:</label>
			<div class="controls">
				<form:input path="beginY" htmlEscape="false" type="number" maxlength="50" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='range.endy' />:</label>
			<div class="controls">
				<form:input path="endY" htmlEscape="false" type="number" maxlength="50" class="required digits" />
			</div>
		</div>
		 <div class="control-group ad_Preview">
			<div class="tv_icon" style="left:60px;top:30px;">
				<div class="Preview_content">
				  <div id="drag1" class="drag1">
				  		<div id="resizable2" class="ui-widget-content" style="width:150px;height:150px;"><i class='hander'></i></div>			  
				  </div> 
				</div>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="sys:range:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>