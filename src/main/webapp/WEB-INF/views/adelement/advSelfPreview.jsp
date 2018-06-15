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
	 
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="adelement" action="${ctx}/adv/adelement/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<p id="isSd" stye="display:none" value="${adelement.isSd}"></p>
		<p id="isHd" stye="display:none" value="${adelement.isHd}"></p>
		<tags:message content="${message}"/>
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
		    <div class="adinfo_tip"><p><spring:message code='adv.baseInfo' /></p></div>
		    <div class="adinfo_comment">
			    <div class="control-group list">
					<label class="control-label"><spring:message code='adv.name' />:</label>
					<div class="controls">
					 
						<label class="lbl"> ${adelement.adName}</label>
					</div>
				</div>
				 
				<div class="control-group list">
					<label class="control-label"><spring:message code='channel.adv.type' />:</label>
					<div class="controls">
						<label class="lbl"> ${typeName}</label>
					
					</div>
				</div>
				<c:if test="${!empty adelement.childAdType}">
				<p class="childType" style="display:none">${childTypeId}</p>
					<div class="control-group list">
						<label class="control-label"><spring:message code='adv.son.type' />:</label>
						<div class="controls">
				
							<label class="lbl"> ${childTypeName}</label>
						</div>
					</div>
				</c:if>
				<div class="control-group list">
					<label class="control-label"><spring:message code='adv.adcombo' />:</label>
					<div class="controls">
						<label class="lbl"> ${adelement.adCombo.comboName}</label>
					</div>
				</div>
				
				<div class="control-group list">
					<label class="control-label"><spring:message code='category.name.id' />:</label>
					<div class="controls">
						<label class="lbl"> ${adelement.adCategory.categoryName}(${adelement.adCategory.categoryId})</label>
					</div>
				</div>
				<div class="control-group list">
						<label class="control-label"><spring:message code='is.sd' />:</label>
						<div class="controls">
							 
								 
							<label class="lbl">${isSd}</label>

						</div>
				</div>
				
				<c:if test="${!empty adelement.position}">
					<div class="control-group list">
						<label class="control-label"><spring:message code='adv.sd.position' />:</label>
						<div class="controls">
							 
								 
							<label class="lbl"> ${ adelement.position.point}</label>

						</div>
					</div>
					 
					<c:if test="${!empty adelement.position.velocity}">
						<div class="control-group list" >
							<label class="control-label"><spring:message code='position.sd.velocity' />:</label>
							<div class="controls">
								 
									 
								<label class="lbl"> ${adelement.position.velocity}</label>
	
							</div>
						</div>
					</c:if>
					</c:if> 
				
				<div class="control-group list">
						<label class="control-label"><spring:message code='is.hd' />:</label>
						<div class="controls">
							 
								 
							<label class="lbl">${isHd}</label>

						</div>
				</div>
				
				<c:if test="${!empty adelement.hdPosition}">
					<div class="control-group list">
						<label class="control-label"><spring:message code='adv.hd.position' />:</label>
						<div class="controls">
							 
								 
							<label class="lbl"> ${ adelement.hdPosition.point} </label>

						</div>
					</div>
					 
					<c:if test="${!empty adelement.hdPosition.velocity}">
						<div class="control-group list" >
							<label class="control-label"><spring:message code='position.hd.velocity' />:</label>
							<div class="controls">
								 
									 
								<label class="lbl"> ${adelement.hdPosition.velocity}</label>
	
							</div>
						</div>
					</c:if>
					</c:if> 
				 
			 	<div class="control-group list">
					<label class="control-label"><spring:message code='adv.playstart' />:</label>
					<div class="controls">
						<form:hidden path="startDate"/>
						<label class="lbl"><fmt:formatDate value="${adelement.startDate}" pattern="yyyy-MM-dd" /></label>
					</div>
				</div>
				<div class="control-group list">
					<label class="control-label"><spring:message code='adv.playend' />:</label>
					<div class="controls">
							<form:hidden path="endDate"/>
							<label class="lbl"><fmt:formatDate value="${adelement.endDate}" pattern="yyyy-MM-dd" /></label>
					</div>
				</div>
				<c:if test="${!empty playTime}">
				 <div class="control-group list">
					<label class="control-label"><spring:message code='adv.playtime' />:</label>
					<div class="controls">
		 
						<label class="lbl">${playTime}</label>
					</div>
				</div>
				</c:if>
				 <div class="control-group list">
					<label class="control-label"><spring:message code='play.week' />:</label>
					<div class="controls">
		 
						<label class="lbl">${adelement.adCombo.week}</label>
					</div>
				</div>
				<c:if test="${!empty adelement.adCombo.startTime}">
				<div class="control-group list">
					<label class="control-label"><spring:message code='playtime' />:</label>
					<div class="controls">
					 
					<label class="lbl">${adelement.adCombo.startTime} &nbsp;  ~  &nbsp;${adelement.adCombo.endTime} </label>
					</div>
				</div>
				</c:if>
					<input id="resourceType" name="resourceType" type="hidden" value="${resourceType}"/>
					<p id="adelementDto" name="adelementDto" style="display:none">${adelementDto}</p>
					<form:hidden path="path"/> 
					<form:hidden path="fileSize"/> 
				   	<input id="sdLeft" name="sdLeft" type="hidden" value="${adeDto.sdX}">
				   	<input id="sdTop" name="sdTop" type="hidden" value="${adeDto.sdY}">
				   	<input id="hdLeft" name="hdLeft" type="hidden" value="${adeDto.hdX}">
				   	<input id="hdTop" name="hdTop" type="hidden" value="${adeDto.hdY}">
				 
				<div class="control-group list">
					<label class="control-label"><spring:message code='adv.isflag' />:</label>
					 <form:hidden path="isFlag" />
					 <div class="controls">
						<label class="lbl">${fns:getDictLabel(adelement.isFlag,'adv_isflag',"")}</label>
					</div>
				</div>
				
				<div class="control-group control-group-remarks list_s" id="add_text">
					<label class="control-label"><spring:message code='adv.adtext' />:</label>
					<div class="controls">
						<form:textarea path="addText" id="add_text_area" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge required" disabled="true"/>
				 
					</div>
				</div>
		    </div>
		</div>
	</form:form>
 		<script src="${ctxStatic}/adv/html5media.min.js"></script>
	    <script src="${ctxStatic}/adv/video.js"></script>
	    <script src="${ctxStatic}/adv/jquery.kxbdmarquee.js"></script>
	 	<script src="${ctxStatic}/adv/adplayaudit.js"></script>
</body>
</html>