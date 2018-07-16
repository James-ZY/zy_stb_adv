<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
    <link rel="stylesheet" href="${ctxStatic}/common/jquery-ui.css">
    <link rel="stylesheet" href="${ctxStatic}/common/video-js.css">
    <script src="${ctxStatic}/common/language.js"></script>
    <script src="${ctx}/static/common/jquery-ui.js"></script>
    <style type="text/css">
        .switchResolution{position: absolute;top: 25px;height:30px;left: 180px;}
        .switchResolution .switchItem{float:left;margin-right: 20px;position: relative;height:20px;line-height:20px;}
        .switchResolution:after{content: '';visibility: hidden;clear: both;display: block}
        .switchResolution input{background: url(../../static/images/icon/ic_checkbox_false.png);display: inline-block;width:16px;height:16px;outline: none;border: 0;margin-right:10px;margin-top: -2px;}
    	.switchResolution input[name="1"]{background: url(../../static/images/icon/ic_checkbox_ture.png);}
    	.switchResolution input[name="-1"]{background: url(../../static/images/icon/noSelected.png);}
    	.switchResolution label{display: inline-block;}
	}

    </style>
	<script type="text/javascript"> 
	var b = true;
	$(document).ready(function() {
 		$("#inputForm").validate({
		 
			submitHandler: function(form){
				 if(b){
					 
					loading(accipiter.getLang("loading"));
					form.submit();
				 }else{
					  
				 }
				 
			}
		}); 
		var aToStr=JSON.stringify(${adelementDto}); 
		$("#adelementDto").text(aToStr);
	});
	
	function advReturn(){
		 
		$("#inputForm").attr("action","${ctx}/adv/adelement/addreturn");
		$("#inputForm").submit();
		b = false;
    	return false;
    }
	function returnList(){
		 
		$("#inputForm").attr("action","${ctx}/adv/adelement/returnList");
		$("#inputForm").submit();
		b = false;
    	return false;
    }
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="adv.manage"/></li>
		    <li>></li>
		    <li><spring:message code="adv.issue"/></li>
		    <li>></li>
		    <li>
		<shiro:hasPermission name="sys:adv:edit"><li  class="active"><a href="${ctx}/adv/adelement/preview?id=${adelement.id}">
		 <spring:message code='adv.preview' />
        		 </a></li></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
   
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/adelement"><spring:message code='adv.list' /></a></li>
		<shiro:hasPermission name="sys:adv:edit"><li  class="active"><a href="${ctx}/adv/adelement/preview?id=${adelement.id}">
		 <spring:message code='adv.preview' />
        		 </a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adelement" action="${ctx}/adv/adelement/save" method="post" class="form-horizontal">
		 <form:hidden path="id"/>
		 <form:hidden path="adId"/>
		 <div class="control-group ad_Preview">
			 <div class="Preview_tip"><p><spring:message code='adv.preview' /></p></div>
			 <div class="switchResolution">
			     <div class="switchItem"><input type="button" id="standardShow"/><label for="standardShow"><spring:message code='adv.standard' /></label></div>
				 <div class="switchItem"><input type="button" id="hdShow"/><label for="hdShow"><spring:message code='adv.hd' /></label></div>			
			 </div>
			<div class="tv_icon">
				<div class="Preview_content">
					<div class="ad_gj">
						<dl>
							<img class="flipper">
						</dl>
					</div>
					<div class="ad_gd" id="ad_gd">
					<div class="gd_content" id="gd_content">
						<ul>
						   <li><img class="flipper"></li>					
						</ul>					
					</div>
				</div>
					<div class="ad_kj"></div>
					<div class="ad_cp">
						<dl>
							<img class="flipper">
						</dl>
					</div>
					<div class="ad_ht">
						<div class="left">
							<img>
						</div>
						<div class="right">
							<img>
						</div>
					</div>
					<div class="ad_bg"></div>
					<div class="ad_prompt">
						<div class="ad_promptInfo">
							<img> <span>${childTypeName}!</span>
						</div>
						<div class="adImg">
							<img>
						</div>
						<div class="adv_noChannel"></div>
					</div>
					<div class="ad_menu">
						<div class="main_adv">
							<img>
						</div>
						<div class="volume_adv">
							<div class="menu_left">
								<img>
							</div>
							<div class="menu_right">
								<img>
							</div>
						</div>
						<div class="channelList_adv">
							<img class="bg"><img class="adv">
						</div>
						<div class="EPG_adv">
							<img class="bg"><img class="adv">
						</div>
					</div>
					<div class="ad_video">
						<video class="video-js vjs-default-skin vjs-big-play-centered"
							controls id="my-video" preload="none" poster webkit-playsinline>
							<source src="" type="video/mp4" class="video_source">
						</video>
					</div>
				</div>
			</div>					
		</div>
		<div class="adinfo_content">
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.name' />:</label>
			<div class="controls">
				 <label class="label_info">
				 	${adelement.adName}
				</label>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.adcombo.name' />:</label>
			<div class="controls">
				 <label class="label_info">
				 	${comboName}
				</label>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.type' />:</label>
			<div class="controls">
				 <label class="label_info">
				 	${typeName}
				</label>
			</div>
		</div>
		<c:if test="${!empty childTypeName}">
		<p class="childType" style="display:none">${childTypeId}</p>
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.son.type' />:</label>
			<div class="controls">
				 <label class="label_info">
				 	${childTypeName}
				</label>
			</div>
		</div>
		 </c:if>
		 <p id="adelementDto" name="adelementDto" style="display:none"></p>
		<form:hidden path="position.id" class="required" id="position_id"/>
				<form:hidden path="hdPosition.id" class="required" />
		<form:hidden path="adCombo.id" class="required"/>
		<form:hidden path="adName" class="required" htmlEscape="false"/>
		<form:hidden path="adTypeId"/>
		<form:hidden path="status"/>
		<form:hidden path="path"/>
		<form:hidden path="hdPath"/> 
		<form:hidden path="sdShowParam"/>
		<form:hidden path="hdShowParam"/> 
		<%-- <form:hidden path="vedioImagePath"/>  --%>
		<form:hidden path="isFlag"/> 
		<form:hidden path="addText" htmlEscape="false"/> 
	    <form:hidden path="advertiser.id"/> 
	    <form:hidden path="showWay"/>
	    <form:hidden path="adCombo.childAdType.id"/>
		<form:hidden path="oldAdCategoryId"/>
	    <form:hidden path="adCategory.id"/>
	    <form:hidden path="playTime"/>
	    <form:hidden path="isSd"/>
	   	<form:hidden path="isHd"/>
	   	<form:hidden path="velocity"/>
	   	<form:hidden path="sdFx"/>
	   	<form:hidden path="hdFx"/>
	   	<form:hidden path="position.beginPointX"/>
	   	<form:hidden path="position.beginPointY"/>
	   	<form:hidden path="hdPosition.beginPointX"/>
	   	<form:hidden path="hdPosition.beginPointY"/>
	   	<form:hidden path="position.endPointX"/>
	   	<form:hidden path="position.endPointY"/>
	   	<form:hidden path="hdPosition.endPointX"/>
	   	<form:hidden path="hdPosition.endPointY"/>
		<form:hidden path="adCombo.startTime"/>
		<form:hidden path="adCombo.endTime"/>
		<form:hidden path="adCombo.showTime"/>
		<form:hidden path="adCombo.intervalTime"/>
		<form:hidden path="adCombo.showCount"/>
		<form:hidden path="adCombo.pictureTimes"/>
		<form:hidden path="adCombo.pictureInterval"/>
		<input id="sdLeft" name="adCombo.childAdType.id" type="hidden" value="${adeDto.sdX}">
		<input id="sdLeft" name="sdLeft" type="hidden" value="${adeDto.sdX}">
	   	<input id="sdTop" name="sdTop" type="hidden" value="${adeDto.sdY}">
	   	<input id="hdLeft" name="hdLeft" type="hidden" value="${adeDto.hdX}">
	   	<input id="hdTop" name="hdTop" type="hidden" value="${adeDto.hdY}">
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.time' />：</label>
			<div class="controls">
				 <label class="label_info" >${startDate}</label>&nbsp;&nbsp;
				 <spring:message code="to"/>&nbsp;&nbsp;<label>${endDate}</label>
			</div>
		</div>
		<c:if test="${!empty playTime}">
			<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.playtime' />：</label>
			<div class="controls">
				 <label class="label_info" >${playTime} </label>
			</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='is.sd' />:</label>
			<div class="controls">
				 <label class="label_info">
				 	${isSd}
				</label>
			</div>
		</div>
		<c:if test="${adType.typeId == 2 || adType.typeId == 4 || adType.typeId == 5 || adType.typeId == 10}">
			<c:if test="${sdShow == 'yes'}">
				<c:choose>
					<c:when test="${trackMode == 2}">
						<label class="control-label label_weight"><spring:message code='adv.sdTrack' />：</label>
						<div class="controls">
							<label id="sdTrack" class="label_info" >${sdTrack}</label>
						</div>
						</div>
					</c:when>
					<c:otherwise>
						<label class="control-label label_weight"><spring:message code='adv.sd.position' />：</label>
						<div class="controls">
							<label id="position" class="label_info" >${sdPoint}</label>
						</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='is.hd' />:</label>
			<div class="controls">
				 <label class="label_info">
				 	${isHd}
				</label>
			</div>
		</div>
		<c:if test="${adType.typeId == 2 || adType.typeId == 4 || adType.typeId == 5 || adType.typeId == 10}">
			<c:if test="${hdShow == 'yes'}">
				<c:choose>
					<c:when test="${trackMode == 2}">
						<label class="control-label label_weight"><spring:message code='adv.hdTrack' />：</label>
						<div class="controls">
							<label id="hdTrack" class="label_info" >${hdTrack}</label>
						</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="control-group">
							<label class="control-label label_weight"><spring:message code='adv.hd.position' />：</label>
							<div class="controls">
								<label id="hdposition" class="label_info" >${hdPoint}</label>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.isflag' />：</label>
			<div class="controls">
				 <label class="label_info" >${isFlag} </label>
			</div>
		</div>
		 
		<c:if test="${adelement.isFlag == 1 || adelement.isFlag ==2}">
		<div class="control-group control-group-remarks" id="add_text">
			<label class="control-label label_weight"><spring:message code='adv.adtext' />:</label>
			<div class="controls">
				<form:textarea path="addText" id="add_text_area" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge required" disabled="true"/>
			 
			</div>
			
		</div>
		 </c:if>
		<div style="display:none;">
	 	<div class="control-group">
			<label class="control-label"><spring:message code='adv.playstart' />：</label>
			<div class="controls">
				<input id="startDate" name="startDate" type="text"    maxlength="200" class="input-small Wdate data_content"
				value="${startDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.playend' />：</label>
			<div class="controls" >
				<input id="endDate" name="endDate" type="text"    maxlength="200" class="input-small Wdate data_content"
				value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"/>
			</div>
		</div>
				
		</div>
		
		</div>
	
		<div class="form-actions">
		
			<shiro:hasPermission name="sys:adv:edit">
                                <fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd" var="nowDate"/>
								<fmt:formatDate value="${adelement.endDate}" pattern="yyyy-MM-dd" var="endDate"/>
						<c:choose>
						<c:when test="${0 == adelement.status ||  -1 == adelement.status}">
                                 <input id="btnReturnSubmit" class="btn btn-primary " type="button" value='<spring:message code='return'/>' onclick="return advReturn();"/>
							     <input id="btnSubmit" class="btn btn-primary " type="submit" value='<spring:message code='save'/>'/>							
							</c:when>
							<c:when test="${3 == adelement.status && endDate ge nowDate}">
								<input id="btnReturnSubmit" class="btn btn-primary " type="button" value='<spring:message code='return'/>' onclick="return advReturn();"/>
							     <input id="btnSubmit" class="btn btn-primary " type="submit" value='<spring:message code='save'/>'/>
							</c:when>
							<c:when test="${4 == adelement.status  && endDate ge nowDate}">
								<input id="btnReturnSubmit" class="btn btn-primary " type="button" value='<spring:message code='return'/>' onclick="return advReturn();"/>
							    <input id="btnSubmit" class="btn btn-primary " type="submit" value='<spring:message code='save'/>'/>	
							</c:when>
							<c:otherwise>
								<input id="btnReturnSubmit" class="btn btn-primary " type="button" value='<spring:message code='return'/>' onclick="return advReturn();"/>
								<input id="btnReturnSubmit" class="btn btn-primary " type="button" value='<spring:message code='close'/>' onclick="return returnList();"/>
							</c:otherwise>
					</c:choose>		
			</shiro:hasPermission>
		</div>
	</form:form>
 
		<script src="${ctxStatic}/adv/html5media.min.js"></script>
	    <script src="${ctxStatic}/adv/video.js"></script>
	    <script src="${ctxStatic}/adv/jquery.kxbdmarquee.js"></script>
	 	<script src="${ctxStatic}/adv/adplay.js"></script>
	 	<script src="${ctxStatic}/adv/drag.js"></script>
</body>
</html>