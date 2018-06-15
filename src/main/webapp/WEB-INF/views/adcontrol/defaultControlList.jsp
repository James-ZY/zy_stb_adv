<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
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
						$("#searchForm").attr("action","${ctx}/adv/defaultControl/export");
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
			$("#searchForm").attr("action","${ctx}/adv/defaultControl");
			$("#searchForm").submit();
	    	return false;
	    }
		
	 
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='combo.setSystem' /></li>
    <li>></li>
    <li><spring:message code='adv.defaultControl' /></li>
    <li>></li>
    <li><a href="${ctx}/adv/defaultControl"><spring:message code='defaultControl.list' /></a></li></ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/defaultControl"><spring:message code='defaultControl.list' /></a></li>
		<shiro:hasPermission name="adv:adSet:edit"><li><a href="${ctx}/adv/defaultControl/form"><spring:message code='defaultControl.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adDefaultControll" action="${ctx}/adv/defaultControl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div class="query-item">
				<label><spring:message code='adv.type' />：</label>
				<form:select path="adType.id">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getAdTypeById('1,9')}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
			<div class="query-item">
						<label><spring:message code='network.name' />：</label>
						<form:select path="adNetwork.id">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:findAdNetworkAll()}" itemLabel="networkName" itemValue="id" htmlEscape="false"/>
						</form:select>
			</div>
			<div class="query-item">
			<label><spring:message code='control.flag' />：</label>
			<form:select path="flag">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<div class="query-item">
			<label><spring:message code='control.create.name' />：</label><form:input path="createBy.loginName" htmlEscape="false" maxlength="50" class="input-small"/>
			</div>
			<div class="query-item">
			<label><spring:message code='control.update.name' />：</label><form:input path="updateBy.loginName" htmlEscape="false" maxlength="50" class="input-small"/>
			</div>	
			<div class="query-item">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>						 
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th><spring:message code='adv.type' /></th>
			<th><spring:message code="network.name"/></th>
			<th><spring:message code='control.format'/></th>
			<th><spring:message code='control.width'/></th>
			<th><spring:message code='control.height'/></th>
			<th><spring:message code='control.flag'/></th>
			<th><spring:message code='create.user'/></th>
			<th><spring:message code='create.date'/></th>
			<th><spring:message code='update.user'/></th>
			<th><spring:message code='update.date'/></th>
			<th><spring:message code='control.preview'/></th>
			<shiro:hasPermission name="adv:adSet:edit">
			<th><spring:message code='operation'/></th>
			</shiro:hasPermission>
			</tr>
		</thead>
 		<tbody>
		<c:forEach items="${page.list}" var="adDefaultControll" varStatus="status">
			<tr>		
				<c:choose>
					<c:when test="${!empty adDefaultControll.adType}">
						<td>${adDefaultControll.adType.typeName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<td>
					<c:choose>
					<c:when test="${!empty adDefaultControll.adNetwork}">
							${adDefaultControll.adNetwork.networkName}
					</c:when>
					<c:otherwise>						
					</c:otherwise>
					</c:choose>
				</td>
				<td>${adDefaultControll.fileFormat}</td>
				<td>${adDefaultControll.width}</td>
				<td>${adDefaultControll.height}</td>
				<td>${fns:getDictLabel(adDefaultControll.flag, 'adv_range_flag', "")}</td>
				<td>
					<c:choose>
					<c:when test="${!empty adDefaultControll.createBy}">
						 ${adDefaultControll.createBy.name} 
					</c:when>
					<c:otherwise>						 
					</c:otherwise>
					</c:choose>				
				</td>
				 <td>
				 <c:choose>
					<c:when test="${!empty adDefaultControll.createDate}">
						<fmt:formatDate value="${adDefaultControll.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:when>
					<c:otherwise>
					</c:otherwise>
					</c:choose>								
				</td>				
				<td>
						<c:choose>
						<c:when test="${!empty adDefaultControll.updateBy}">
							 ${adDefaultControll.updateBy.name} 
						</c:when>
						<c:otherwise>							 
						</c:otherwise>
						</c:choose>				
				</td>
				 <td>
				 <c:choose>
					<c:when test="${!empty adDefaultControll.updateDate}">
						<fmt:formatDate value="${adDefaultControll.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:when>
<%-- 					<c:otherwise>
					<img alt="" src="${adDefaultControll.filePath }">
					</c:otherwise> --%>
					</c:choose>								
				</td>
				<td>
				<c:choose>
					<c:when test="${adDefaultControll.adType.typeId==1 ||adDefaultControll.adType.typeId==9 }">
				        <img alt="" src="${adDefaultControll.fileImagePath }" style="width:80px;height:80px;">
					</c:when>
				    <c:otherwise>
				         <img alt="" src="${adDefaultControll.filePath }" style="width:80px;height:80px;">
					</c:otherwise> 
					</c:choose>	
				</td>	
				<shiro:hasPermission name="adv:adSet:edit">
					<td>						 
					  <a href="${ctx}/adv/defaultControl/form?id=${adDefaultControll.id}"><spring:message code='update' /></a>				 						 
					  <a href="${ctx}/adv/defaultControl/delete?id=${adDefaultControll.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>						  
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