<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code="role.assign"/></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
    <script src="${ctx}/static/scripts/global.js"></script>
    <script src="${ctx}/static/libraries/jquery-i18n/jquery.i18n.properties.js"></script>
    <script src="${ctx}/static/scripts/i18n/i18n.js"></script>
    <script src="${ctxStatic}/common/language.js"></script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="role.manage"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/role/assign?id=${role.id}">
    <shiro:hasPermission name="sys:role:edit"><spring:message code='role.assign' /></shiro:hasPermission><shiro:lacksPermission name="sys:role:edit"><spring:message code='people.list' /></shiro:lacksPermission>
    </a></li></ul></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/"><spring:message code='role.list' /></a></li>
		<li class="active"><a href="${ctx}/sys/role/assign?id=${role.id}"><shiro:hasPermission name="sys:role:edit"><spring:message code='role.assign' /></shiro:hasPermission><shiro:lacksPermission name="sys:role:edit"><spring:message code='people.list' /></shiro:lacksPermission></a></li>
	</ul>
	<div class="container-fluid breadcrumb">
		<div class="row-fluid span12">
			<span class="span4"><spring:message code='role.name' />: <b>${role.name}</b></span>
			<span class="span4"><spring:message code='grade' />: ${role.office.name}</span>
			<c:set var="dictvalue" value="${role.dataScope}" scope="page" />
			<span class="span4"><spring:message code='data.scope' />: ${fns:getDictLabel(dictvalue, 'sys_data_scope', '')}</span>
		</div>
	</div>
	<tags:message content="${message}"/>
	<div class="breadcrumb">
		<form id="assignRoleForm" action="" method="post" class="hide"></form>
		<a id="assignButton" href="javascript:" class="btn btn-primary"><spring:message code='role.assign' /></a>
		<script type="text/javascript">
			$("#assignButton").click(function(){
				top.$.jBox.open("iframe:${ctx}/sys/role/usertorole?id=${role.id}",
                                accipiter.getLang("assignRoles"),810,$(top.document).height()-240,
                                {
                                buttons:function() {
                                    var ret = {};
                                    ret[ accipiter.getLang("t4")] = "ok";
                                    ret[ accipiter.getLang("clear")] = "clear";
                                    ret[ accipiter.getLang("close")] = true;
                                    return ret;
                                }(),
                                bottomText:accipiter.getLang("rolesToUsers"),
                                submit:function(v, h, f){
                                    var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
                                    var ids = h.find("iframe")[0].contentWindow.ids;
                                    //nodes = selectedTree.getSelectedNodes();
                                    if (v=="ok"){
                                        // 删除''的元素
                                        if(ids[0]==''){
                                            ids.shift();
                                            pre_ids.shift();
                                        }
                                        if(pre_ids.sort().toString() == ids.sort().toString()){
                                            top.$.jBox.tip(accipiter.getLang("notRole")+"【${role.name}】"+accipiter.getLang("newMember"), 'info');
                                            return false;
                                        };
                                        // 执行保存
                                        loading(accipiter.getLang("loading"));
                                        var idsArr = "";
                                        for (var i = 0; i<ids.length; i++) {
                                            idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
                                        }
                                        $('#assignRoleForm').attr('action','${ctx}/sys/role/assignrole?id=${role.id}&idsArr='+idsArr).submit();
                                        return true;
                                    } else if (v=="clear"){
                                        h.find("iframe")[0].contentWindow.clearAssign();
                                        return false;
                                    }
                                },
                                loaded:function(h){
                                    $(".jbox-content", top.document).css("overflow-y","hidden");
                                }
				                });
			});
		</script>
	</div>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='grade' /></th><th><spring:message code='login.name' /></th><th><spring:message code='user.name' /></th><th><spring:message code='telephone' /></th><th><spring:message code='mobilephone' /></th><shiro:hasPermission name="sys:user:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.company.name}</td>
				<td><a href="${ctx}/sys/user/info">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<shiro:hasPermission name="sys:role:edit"><td>
					<a href="${ctx}/sys/role/outrole?userId=${user.id}&roleId=${role.id}" 
						onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='remove' /></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>
