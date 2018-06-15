<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='user.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
      /*  $(document).ready(function() {
    	   
    	   var id = $("#id").val();
    	   if(id == null || id == ""){
    	      var cron =  accipiter.getLang_(messageLang,"cron_info");   
    	      $("#cronInfo").text(cron);
    	   }
       }); */
       $(function(){ 
    	$("#attention").on("click",function(){
    		if($(".attention-info").hasClass("hidden")){
    			$(".attention-info").removeClass("hidden");
    			$(".attention-info").addClass("show");
    		}else{
    			$(".attention-info").removeClass("show");
    			$(".attention-info").addClass("hidden");
    		}
    	});
   	});
	</script>
	<style type="text/css">
	#attention{width:34px;height:34px;display:block;float:left;position: relative;left:10px;top:4px;background:url("${ctxStatic}/images/icon/attention.png") no-repeat;}
	#attention:hover{cursor: pointer;}
    .attention-info{padding: 10px 0;margin-bottom: 10px;border: 1px solid #FFCE42;border-radius: 4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;background-color: #DFF0D8;}
	.attention-info p{text-indent: 25px;margin-bottom:0;}
	.hidden{display:none;}
	.show{display:block;}
	</style>
</head>
<body>
<div class="top_position">
<div class="top_position_lab"><spring:message code="position"/>:</div>
<ul><li><spring:message code="database.manage"/></li>
<li>></li>
<li><spring:message code="task.manage"/></li>
<li>></li>
<li>
<shiro:hasPermission name="sys:task:edit"><a href="${ctx}/sys/task/form?id=${scheduleJob.id}">
		<c:choose><c:when test="${not empty scheduleJob.id}"><spring:message code='task.edit' /></c:when>
        			<c:otherwise><spring:message code='task.add' /></c:otherwise></c:choose></a></shiro:hasPermission></li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/task"><spring:message code='task.list' /></a></li>
		<shiro:hasPermission name="sys:task:edit"><li  class="active"><a href="${ctx}/sys/task/form?id=${scheduleJob.id}">
		<c:choose><c:when test="${not empty scheduleJob.id}"><spring:message code='task.edit' /></c:when>
        			<c:otherwise><spring:message code='task.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scheduleJob" action="${ctx}/sys/task/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><spring:message code='task.jobName' />:</label>
			<div class="controls">
				<form:input path="jobName" htmlEscape="false" maxlength="20" class="required"/>
			</div>
				<label></label>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='task.jobGroup' />:</label>
			<div class="controls">
				<form:input path="jobGroup" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='task.cronExpression' />:</label>
			<div class="controls">
				<form:input path="cronExpression" htmlEscape="false" maxlength="20" class="required" style="float:left;"/>			
		     <a id="attention"></a>
		    </div>
       </div>
	   <div class="attention-info hidden"><p><spring:message code="cron_info"/></p></div>
		
	   <div class="control-group">
			<label class="control-label"><spring:message code='task.beanClass' />:</label>
			<div class="controls">
				<label class="lbl">${scheduleJob.showClass }</label>			
			</div>
		</div>
			<div class="control-group">
			<label class="control-label"><spring:message code='task.executeMethod' />:</label>
			<div class="controls">
				<label class="lbl">${scheduleJob.executeMethod }</label>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label"><spring:message code='task.jobDesc' />:</label>
			<div class="controls">
				<form:input path="jobDesc" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label"><spring:message code='task.jobStatus' />:</label>
			<div class="controls">
			<form:select path="jobStatus">
				<form:options items="${fns:getDictList('sys_task_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			 </form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:task:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>