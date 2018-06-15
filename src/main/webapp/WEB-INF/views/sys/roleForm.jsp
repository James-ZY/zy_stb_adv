<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='role.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<style type="text/css">
	.treeInfo{display:none;font-weight: bold;color: #ea5200;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")}
				},
				messages: {
					name: {remote: accipiter.getLang("nameExists")}
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					if(ids.length==0){
						$(".treeInfo").css({"display":"block"});
						return;
					}else{
						$(".treeInfo").css({"display":"none"});
						$("#menuIds").val(ids);
						loading(accipiter.getLang("loading"));
						form.submit();
					}
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

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			var authList = accipiter.getLang("authList");
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:'${menu.id}', pId:'${not empty menu.parent.id?menu.parent.id:0}', name:"${not empty menu.parent.id?menu.name:'"+authList+"'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
			
			 
		});
		function refreshOfficeTree(){
			if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			}
		}
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="role.manage"/></li>
    <li>></li>
    <li>
    <a href="${ctx}/sys/role/form?id=${role.id}">
  <shiro:hasPermission name="sys:role:edit"><c:choose><c:when test="${not empty role.id}">
  <spring:message code='role.update' /></c:when><c:otherwise><spring:message code='role.add' /></c:otherwise></c:choose></shiro:hasPermission><shiro:lacksPermission name="sys:role:edit"><spring:message code='check' /></shiro:lacksPermission>
     </a></li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/"><spring:message code='role.list' /></a></li>
		<li class="active"><a href="${ctx}/sys/role/form?id=${role.id}"><shiro:hasPermission name="sys:role:edit">
		<c:choose><c:when test="${not empty role.id}"><spring:message code='role.update' /></c:when>
		<c:otherwise><spring:message code='role.add' /></c:otherwise></c:choose></shiro:hasPermission>
		<shiro:lacksPermission name="sys:role:edit"><spring:message code='check' /></shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		 <div class="control-group">
			<label class="control-label"><spring:message code='grade' />:</label>
			<div class="controls">
                <tags:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
					title="officeform.grade" url="/sys/office/roletreeData?type=1" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='role.name' />:</label>
			<div class="controls">
				<input id="oldName" name="oldName" type="hidden" value="${role.name}">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		 
		<div class="control-group control-group-tree">
			<label class="control-label"><spring:message code='role.permission' />:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="treeInfo"></span>
				<form:hidden path="menuIds"/>
				 
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>