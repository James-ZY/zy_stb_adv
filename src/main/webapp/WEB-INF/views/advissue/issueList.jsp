<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='adelement.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style adelement="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script adelement="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		$(document).ready(function() {
			// 表格排序
			var orderBy = $("#orderBy").val().split(" ");		
			$("#contentTable th.sort").each(function(){
				if ($(this).hasClass(orderBy[0])){
					orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
					$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
				}
			});
			$("#contentTable th.sort").click(function(){
				var order = $(this).attr("class").split(" ");
				var sort = $("#orderBy").val().split(" ");
				for(var i=0; i<order.length; i++){
					if (order[i] == "sort"){order = order[i+1]; break;}
				}
				if (order == sort[0]){
					sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
					$("#orderBy").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
				}else{
					$("#orderBy").val(order+" ASC");
				}
				page();
			});
			$("#btnExport").click(function(){
				top.$.jBox.confirm(accipiter.getLang("exportData"),accipiter.getLang("systemPrompt"),function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/adv/adelement/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{"关闭":true}, 
					bottomText:accipiter.getLang("importFormat")});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/adelement/issue");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="adv.userManage"/></li>
		    <li>></li>
		    <li><spring:message code="adv.put"/></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/adelement/issue"><spring:message code='issue.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/adelement/issue"><spring:message code='issue.list' /></a></li>
		 
	</ul>
	<form:form id="searchForm" modelAttribute="adelement" action="${ctx}/adv/adelement/issue" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				<label><spring:message code='adv.id' /> ：</label><form:input path="adId" htmlEscape="false" maxlength="50" class="input-small"/> 
				<label><spring:message code='adv.name' />：</label><form:input path="adName" htmlEscape="false" maxlength="50" class="input-small"/> 
				<c:if test="${fns:checkUserIsAdvertiser()}">
					<label><spring:message code='adv.user' />：
					<form:select path="advertiser.id">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</c:if>
				
			</div> 
			<div style="margin-top:8px;">
			<label><spring:message code='adv.adcombo' />：
			<form:select path="adCombo.id">
					<option value=""> <spring:message code='userform.select' /></option>
					<form:options items="${fns:getAdComboList()}" itemLabel="comboName" itemValue="id" htmlEscape="false"/>
			</form:select>&nbsp;&nbsp;
			<label><spring:message code='adv.status' />：
				<form:select path="status">
					<option value=""> <spring:message code='userform.select' /></option>
					<form:options items="${statusSelect}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			 </form:select>
				&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='adv.id' /></th>
		<th><spring:message code='adv.name' /></th>
		<th><spring:message code='adv.user'/>
		<th><spring:message code='adv.adcombo'/>
		<th><spring:message code='adv.playstart' /></th>
		<th><spring:message code='adv.playend' /></th>
		<th><spring:message code='adv.status' /></th>
		<th><spring:message code='adv.remarks' /></th>
		<shiro:hasPermission name="sys:adv:issue">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adelement">
			<tr>
				<td>${adelement.adId}</td>
				<td>${adelement.adName}</td>
				<c:choose>
					<c:when test="${!empty adelement.advertiser}">
						<td>${adelement.advertiser.name}</td>
					</c:when>
					<c:otherwise><td></td></c:otherwise>
				</c:choose>
					<c:choose>
					<c:when test="${!empty adelement.adCombo}">
						<td>${adelement.adCombo.comboName}</td>
					</c:when>
					<c:otherwise><td></td></c:otherwise>
				</c:choose>
				<td>
				<fmt:formatDate value="${adelement.startDate}" type="date"/>
				</td>
				<td><fmt:formatDate value="${adelement.endDate}" type="date"/></td>
				<td>${fns:getDictLabel(adelement.status, 'adv_status', "")}</td>
				<td>${adelement.remarks}</td>
				<shiro:hasPermission name="sys:adv:issue">
					<td>
						<c:choose>
							<c:when test="${2==adelement.status}">
									<a href="${ctx}/adv/adelement/saveIssue?id=${adelement.id}"><spring:message code='adv.issue.right.now' /></a>
							</c:when>
							<c:otherwise>
							
							<spring:message code='adv.already.issue' />
							</c:otherwise>
						</c:choose>

	    			</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>