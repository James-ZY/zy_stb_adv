<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='dictionary.manage' /></title>
	<meta name="decorator" content="default"/>
		<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		// 表格排序
		 $("#btnExport").click(function(){
				top.$.jBox.confirm(accipiter.getLang("exportData"),accipiter.getLang("systemPrompt"),function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/dict/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		 
		$("#btnImport").click(function(){
			var close= accipiter.getLang('close');

			$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{close:true}, 
				bottomText:accipiter.getLang("importFormat")});
		});
	});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code="combo.setSystem"/></li>
    <li>></li>
    <li><spring:message code="dictionary.manage"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/dict/"><spring:message code='dictionary.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/dict/"><spring:message code='dictionary.list' /></a></li>
		<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10"><spring:message code='dictionary.add' /></a></li></shiro:hasPermission>
	</ul>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/dict/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading(accipiter.getLang('loading'));"><br/>
		<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="<spring:message code='import.advertiser'/>" />
			<a href="${ctx}/sys/dict/import/template"><spring:message code='download.advertiser.template' /></a>
			
		</form>
	</div>
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label><spring:message code='dictionary.key' /> ：</label>
		<form:input path="value" htmlEscape="false" maxlength="100"
			class="input-small" />
		&nbsp;&nbsp;<label><spring:message code='dictionary.label' />
			：</label>
		<form:input path="label" htmlEscape="false" maxlength="100"
			class="input-small" />
		&nbsp;&nbsp;<label><spring:message code='dictionary.type' />：</label>
		<form:select id="type" path="type" class="input-small"
			style="width:200px !important;">
			<option value="">
				<spring:message code='userform.select' /></option>
			<form:options items="${typeList}" htmlEscape="false" />
		</form:select>
		&nbsp;&nbsp;<label><spring:message
				code='dictionary.description' /> ：</label>
		<form:input path="description" htmlEscape="false" maxlength="50"
			class="input-small" />
		&nbsp;&nbsp;<label><spring:message code='language' /> ：</label>
		<form:select id="dictLocale" path="dictLocale" class="input-small"
			style="width:160px !important;">
			<option value="">
				<spring:message code='userform.select' /></option>
			<form:options items="${dictLocale}" itemLabel="label"
				itemValue="value" htmlEscape="false" />
		</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"/>
			&nbsp;&nbsp;  
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='dictionary.key' />
		</th><th><spring:message code='dictionary.label' /></th>
		<th><spring:message code='dictionary.type' /></th>
		<th><spring:message code='dictionary.description' /></th>
		<th><spring:message code='dictionary.order' /></th>
		<th><spring:message code='language'/></th>
		<shiro:hasPermission name="sys:dict:edit"><th><spring:message code='dictionary.operation' /></th>
		</shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td>${dict.value}</td>
				<td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
				<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
				<td>${dict.description}</td>
				<td>${dict.sort}</td>
				<td><c:choose>
					<c:when test="${dict.dictLocale == 1}">
					<spring:message code='English'/>
					</c:when>
					<c:otherwise><spring:message code='Chinese'/></c:otherwise>
				</c:choose></td>
				<shiro:hasPermission name="sys:dict:edit"><td>
    				<a href="${ctx}/sys/dict/form?id=${dict.id}"><spring:message code='update' /></a>
					<a href="${ctx}/sys/dict/delete?id=${dict.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
    				<a href="<c:url value='/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>"><spring:message code='add.dictionary.key' /></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>