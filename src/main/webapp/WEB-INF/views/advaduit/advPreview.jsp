<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
     <link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
     <link rel="stylesheet" href="${ctxStatic}/common/video-js.css">
     <style type="text/css">
        .switchResolution{position: absolute;top: 25px;height:30px;left: 180px;}
        .switchResolution .switchItem{float:left;margin-right: 20px;position: relative;height:20px;line-height:20px;}
        .switchResolution:after{content: '';visibility: hidden;clear: both;display: block}
        .switchResolution input{background: url(../../static/images/icon/ic_checkbox_false.png);display: inline-block;width:16px;height:16px;outline: none;border: 0;margin-right:10px;margin-top: -2px;}
    	.switchResolution input[name="1"]{background: url(../../static/images/icon/ic_checkbox_ture.png);}
    	.switchResolution input[name="-1"]{background: url(../../static/images/icon/noSelected.png);}
    	.switchResolution label{display: inline-block;}
    </style>
     <script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
 		$("#inputForm").validate({
		 
			submitHandler: function(form){
				 
					loading(accipiter.getLang("loading"));
					form.submit();
				 
			}
		}); 
		var aToStr=JSON.stringify(${adelementDto}); 
		$("#adelementDto").text(aToStr);
	});
	 
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
			            <img class="flipper">
			        </div>
			        <div class="ad_gd" id="ad_gd">
			            <ul class="ad_gd_content"></ul>		              
			        </div>
			        <div class="ad_kj"></div>
			        <div class="ad_cp"></div>
			        <div class="ad_ht">
			            <div class="left"><img></div>
			            <div class="right"><img></div>
			        </div>
			        <div class="ad_bg"></div>
			        <div class="ad_prompt">
			             <div class="ad_promptInfo">
			                <img>
			                <span>${childTypeName}!</span>
			             </div>
			             <div class="adImg">
			                 <img>
			             </div>
			             <div class="adv_noChannel"></div>
			        </div>
			        <div class="ad_menu">
			            <div class="main_adv"><img></div>
			            <div class="volume_adv">
				            <div class="menu_left"><img></div>
				            <div class="menu_right"><img></div>
			            </div>
			            <div class="channelList_adv"> <img class="bg"><img class="adv"></div>
			            <div class="EPG_adv"><img class="bg"><img class="adv"></div>
			        </div>
			      <div class="ad_video">
					<video class="video-js vjs-default-skin vjs-big-play-centered" controls id="my-video"
			               preload="none" poster webkit-playsinline>
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
		 
		 <p id="adelementDto" name="adelementDto" style="display:none"></p>
		<form:hidden path="position.id" class="required" id="position_id"/>
		<form:hidden path="adCombo.id" class="required"/>
		<form:hidden path="adName" class="required"/>
		<form:hidden path="path"/> 
		<form:hidden path="vedioImagePath"/> 
		<form:hidden path="isFlag"/> 
		<form:hidden path="addText"/> 
	 
	
		<div class="control-group">
			<label class="control-label label_weight"><spring:message code='adv.time' />：</label>
			<div class="controls">
				 <label class="label_info" >${startDate}</label>&nbsp;&nbsp;
				 <spring:message code="to"/>&nbsp;&nbsp;<label>${endDate}</label>
			</div>
		</div>
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
		
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
			&nbsp;
			<shiro:hasPermission name="sys:adv:edit">
				<input id="btnSubmit" class="btn btn-primary " type="submit" value='<spring:message code='save'/>'/>&nbsp;
		 
			</shiro:hasPermission>
			<!-- <input id="btn-go" type="button"> -->
		</div>
	</form:form>
	    <script src="${ctxStatic}/adv/video.js"></script>
	    <script src="${ctxStatic}/adv/jquery.kxbdmarquee.js"></script>
	 	<script src="${ctxStatic}/adv/adplay.js"></script>
</body>
</html>