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
	
		$(document).ready(function() {
			getIndex();
			$("#loginName").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote:accipiter.getLang("userExist")},
					confirmNewPassword: {equalTo:  accipiter.getLang("passwordConfirm")}
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
			
			$("#companyId").change(function(){
				alert(1);
			});
			
			function getUserRole(){
				
				var id=$("#companyId").val();
				var html='';
				var post={"officeId":id};
				var postData = JSON.stringify(post);
					$.ajax({
					type:"POST",
					url:"${ctx}/sys/user/getUserType",
					data:postData,
					contentType : "application/json; charset=utf-8",
					dataType:"json",
					success:function(data){
						 var friends = $("#userType");
						 var advertiser = $("#advertiser");
						friends.empty();
						advertiser.empty();
						if(data != null){
							 var type = data.userType;
							
							var option = $("<option>").text(data.typeName).val(data.typeId);
							friends.append(option);
							var list = data.advertiserList;
				 
						 
							 console.log("list:"+list);
							if (list != null && list.length > 0) {
								var userIsAdv = data.userIsAdv;
								if(userIsAdv == "false"){
									var option1 = $("<option :selected>").text(
											accipiter.getLang_(messageLang,"userform.select")).val("");
									advertiser.append(option1);
								}
								for (var i = 0; i < list.length; i++) {
									var option3 = $("<option>").text(list[i].name)
											.val(list[i].id);
									advertiser.append(option3);
									
								}
								$('#userAdv').css("display", "block");
						
							} else {
								$('#userAdv').css("display", "none");
							}
						}else{
							$('#userAdv').css("display", "none");
						}
						friends.select2();
						advertiser.select2();
					 
					},
					error:function(data){
						$('#userAdv').css("display", "block");
			 
					}
				});
					 
			}
			//默认角色
			getUserRole();
/* 			//监听动态添加角色
			$("body",parent.document).on("click",function(){
				console.log($("body",parent.document).find(".jbox-button").attr("class"));
			})
			$("body",parent.document).on("click",".jbox-button",function(){
				alert("haha");
				if($(this).val()=="ok"){
					getUserRole();
					console.log(1);
					return;
				}
				if($(this).val()=="true"){
					console.log(2);
					return;
				}
			}) */
			$("#btnSubmit").on("click",function(){
				if($(".selectRole").children("span").length==0){
					return false;
				}
			})
		});
		function getIndex()

		{
		   /*var a=document.getElementById("userType");
		   var b=a.options[a.selectedIndex].value; 
		   var c=document.getElementById("userAdv");
		   var d=document.getElementById("abc");
		   if( c!=null){
	 			if(b==1){
	 				 
	 				document.getElementById("userAdv").style.display= "block";
	 				document.getElementById("isadmin").style.display= "block";
	 			}else{
	 				document.getElementById("userAdv").style.display= "none";
	 				document.getElementById("isadmin").style.display= "none";
	 			}
		   }else{
			   return;
		   }
		   var id = "${currentUserId}";
		   console.log("id:"+id);
			if(id != null && id == 'admin'){
				document.getElementById("isadmin").style.display= "block";
			} */
		} 		 
	</script>
</head>
<body>
<div class="top_position">
<div class="top_position_lab"><spring:message code="position"/>:</div>
<ul><li><spring:message code="sys.user"/></li>
<li>></li>
<li><spring:message code="user.manage"/></li>
<li>></li>
<li><a href="${ctx}/sys/user/form?id=${user.id}"><spring:message code='user'/><spring:message code="btn.add" /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/?role="><spring:message code='user.list' /></a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}"> <shiro:hasPermission name="sys:user:edit"><c:choose><c:when test="${not empty user.id}"><spring:message code='user.update' /></c:when>
        			<c:otherwise><spring:message code='user.add' /></c:otherwise></c:choose></shiro:hasPermission><shiro:lacksPermission name="sys:user:edit"><spring:message code='check' /></shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
	 	<div class="control-group">
			<label class="control-label"><spring:message code='grade' />:</label>
			<div class="controls">
                <tags:treeselectoffice id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
					title="officeform.grade" url="/sys/office/treeData?type=1" cssClass="required" />
			</div>
			 
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='login.name' />:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label"><spring:message code='user.name' />:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='password' />:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/>
				<c:if test="${not empty user.id}"><span class="help-inline"><spring:message code='userform.empty.it' /></span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='confirm.password' />:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='email' />:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='telephone' />:</label>
			<div class="controls">
				<form:input path="phone" name="phone" class="phone" htmlEscape="false" maxlength="15"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='mobilephone' />:</label>
			<div class="controls">
				<form:input path="mobile" name="mobile" class="mobile" htmlEscape="false" maxlength="15"/>
			</div>
		</div>
		<div class="control-group control-group-auto">
			<label class="control-label"><spring:message code='memo' />:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
 
		 <div class="control-group">
						<label class="control-label"><spring:message code='user.type' />:</label>
						<div class="controls">
							<form:select path="userType" id="userType" class="required">
								
							</form:select>
						</div>
		</div>
		<div id="userAdv" style="display:none;">
						<div class="control-group" >
							
							<label class="control-label"><spring:message code='user.adv' />:</label>
							<div class="controls">
								<form:select path="advertiser.id" id="advertiser" class="required">
									<option value=""> <spring:message code='userform.select' /></option>
						 
								</form:select>
							</div>
						</div>
		</div>
	 
	 
		<div class="control-group control-group-auto">
			<label class="control-label"><spring:message code='user.role' />:</label>
			<div class="controls controls_s selectRole">
				 <form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
			</div>
		</div>
		<c:if test="${not empty user.id}">
			<!--  <div class="control-group">
				<label class="control-label"><spring:message code='user.status' />:</label>
				<div class="controls">
					<input type="hidden" name="delFlag" value="${user.delFlag}" />
					<label class="lbl">${fns:getDictList(user.delFlag)}</label>
				</div>
			</div>-->
			<div class="control-group">
				<label class="control-label"><spring:message code='create.time' />:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='last.login' />:</label>
				<div class="controls">
					<label class="lbl lbl_s">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code='time' />：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>