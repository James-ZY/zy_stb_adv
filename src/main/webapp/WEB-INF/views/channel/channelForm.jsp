<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='channel.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	 
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="operators"/></li>
		    <li>></li>
		    <li><spring:message code="channel.manage"/></li>
		    <li>></li>
		    <li>
			    <shiro:hasPermission name="sys:channel:view"><a href="${ctx}/adv/channel/form?id=${adChannel.id}">
			 	<spring:message code='channel.detail' /></a></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
	<li><a href="${ctx}/adv/channel"><spring:message code='channel.list' /></a></li>
		<shiro:hasPermission name="	sys:channel:view"><li  class="active"><a href="${ctx}/adv/channel/form?id=${adChannel.id}">
	 									<spring:message code='channel.detail' /></a></li></shiro:hasPermission>
	</ul>
 	<form:form id="inputForm" modelAttribute="adChannel" action="${ctx}/adv/channel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	 
		<div class="control-group">
			<label class="control-label"><spring:message code='channel.id' />:</label>
			<div class="controls">
				 
				<form:input path="channelId" htmlEscape="false" maxlength="50" class="required typeId" readonly="true" onfocus="this.blur();" />
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='channel.name' />:</label>
			<div class="controls">
				<form:input path="channelName" htmlEscape="false" maxlength="50" class="required" readonly="true" onfocus="this.blur();" />
			</div>
		</div>
		 
	   <div class="control-group control-group-remarks">
			<label class="control-label"><spring:message code='channel.type' />:</label>
			<div class="controls">
				<form:textarea path="channelType" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"  readonly="true" onfocus="this.blur();" />
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label"><spring:message code='programCategory' />:</label>
			<div class="controls">
				<form:hidden path="oldAdCategoryId"/>		 
                <tags:treeselectnotfirst id="adProgramCategoryLevel1" name="adCategory.id" value="${adChannel.adCategory.id}" labelName="adCategory.categoryName" labelValue="${adChannel.adCategory.categoryName}"
					title="programCategory" url="/adv/programCategory/treeDataNoFirst" extId="${adChannel.adCategory.id}" cssClass="required"/>
			</div>
		</div>
	   <div class="control-group">
			<label class="control-label"><spring:message code='channel.network' />:</label>
			
			<div class="controls">
				<form:input path="adNetWork.networkId" htmlEscape="false" maxlength="50" class="required" readonly="true" onfocus="this.blur();" />
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label"><spring:message code='network.name' />:</label>
			
			<div class="controls">
				<form:input path="adNetWork.networkName" htmlEscape="false" maxlength="50" class="required" readonly="true" onfocus="this.blur();" />
			</div>
		</div>
			<div class="control-group">
			<label class="control-label"><spring:message code='service.id' />:</label>
			<div class="controls">
				 
				<form:input path="serviceId" htmlEscape="false" maxlength="50" class="required typeId" readonly="true" onfocus="this.blur();" />
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='service.name' />:</label>
			<div class="controls">
				<form:input path="serviceName" htmlEscape="false" maxlength="50" class="required" readonly="true" onfocus="this.blur();" />
			</div>
		</div>
	  <div class="control-group">
			<label class="control-label"><spring:message code='channel.adv.type' />:</label>
			<div class="controls controls_s">
	 
				<form:checkboxes path="typeList" items="${allTypeList}" itemLabel="typeName" itemValue="id" htmlEscape="false" class="required" disabled="true" onfocus="this.blur();"/>
			</div>
		</div>
		 <div class="form-actions">
		    <shiro:hasPermission name="sys:channel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
		</form:form>
</body>
</html>