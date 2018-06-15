<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='adelement.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		$(document).ready(function() {
			window.parent.document.getElementById("menuFrame").contentWindow.document.getElementById("collapse66").getElementsByTagName("a")[0].parentElement.setAttribute("class","active");			
			window.parent.document.getElementById("menuFrame").contentWindow.document.getElementById("collapse66").getElementsByTagName("a")[1].parentElement.setAttribute("class","");			
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
				$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{"close":true}, 
					bottomText:accipiter.getLang("importFormat")});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/adelement");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
		    <li><spring:message code="adv.manage"/></li>
		    <li>></li>
		    <li><spring:message code="adv.issue"/></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/adelement"><spring:message code='adv.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/adelement"><spring:message code='adv.list' /></a></li>
		<shiro:hasPermission name="sys:adv:edit"><li><a href="${ctx}/adv/adelement/form"><spring:message code='adv.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adelement" action="${ctx}/adv/adelement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				 <div class="query-item">
				 	<label><spring:message code='start.date' />：</label>
				 	<input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				     value="${startDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				 </div>
				 <div class="query-item">
				    <label><spring:message code='end.date' />：</label>
				    <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				    value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> 
				 </div>
				 <div class="query-item">
					<c:if test="${fns:checkUserIsAdvertiser()}">
						&nbsp;&nbsp;<label><spring:message code='adv.user' />：</label>
						<form:select path="advertiser.id">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</c:if>
				 </div>
				 <div class="query-item">
					<label><spring:message code='adv.status' />:</label>
					<form:select path="status">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				 </div>
				<div class="query-item">
					<label><spring:message code='combo.name' />：</label><form:input path="adCombo.comboName"  htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
			</div> 
			<div>
				
				<div class="query-item">
				    <label><spring:message code='channel.id' />：</label><form:input path="adCombo.channelId" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
				    <label><spring:message code='channel.name' />：</label><form:input path="adCombo.channleName" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
			 
				<div class="query-item">
					<label><spring:message code='adNetwork' />：</label>
					<form:select path="adCombo.networkId">
								<option value=""><spring:message code="userform.select"/></option>
								<form:options items="${ fns:findAdNetworkAll()}" itemLabel="networkName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="query-item">
					<label><spring:message code='adv.type' />：</label>
					<form:select path="adCombo.adType.id">
								<option value=""><spring:message code="userform.select"/></option>
								<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="query-item">
					<label><spring:message code='adv.name' />：</label><form:input path="adName"  htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
				</div>
			</div>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content advList">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
	 
		<tr>
 		<th>ID</th>
		<th><spring:message code='adv.name' /></th>
		<c:if test="${isAdv ==false}">
					<th class="td-fore1"><spring:message code='adv.user'/></th>
		</c:if>

		 
		<th class="td-fore1"><spring:message code='adv.adcombo'/></th>
		<th class="td-fore1"><spring:message code='channel.adv.type'/></th>
		<th class="td-fore1"><spring:message code='adv.son.type'/></th>
		<th><spring:message code='adv.playstart' /></th>
		<th style="width:90px;" class="td-control"><spring:message code='adv.playend' /><i class="td-open"></i></th>
		<th class="td-fore2"><spring:message code='adv.isflag' /></th>
  
		<th class="td-fore2"><spring:message code='adv.status' /></th>

		<th class="td-fore2"><spring:message code='adv.audit.user' /></th>
		<th class="td-fore2"><spring:message code='adv.remarks' /></th>
		<shiro:hasPermission name="sys:adv:edit">
		<th style="width:200px;"><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adelement">
			<tr>
		 
	 
				<td>${adelement.adId}</td>
				<td>${adelement.adName}</td>
				<c:if test="${isAdv ==false}">
				<c:choose>
					<c:when test="${!empty adelement.advertiser}">
						<td class="td-fore1">${adelement.advertiser.name}</td>
					</c:when>
					<c:otherwise><td class="td-fore1"></td></c:otherwise>
				</c:choose>
				</c:if>
	 
					<c:choose>
					<c:when test="${!empty adelement.adCombo}">
						<td class="td-fore1">${adelement.adCombo.comboName}</td>
						<c:choose>
							<c:when test="${!empty adelement.adCombo.adType}">
									<td class="td-fore1">${adelement.adCombo.adType.typeName}</td>
							</c:when>
							<c:otherwise><td class="td-fore1"></td></c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise><td class="td-fore1"></td><td class="td-fore1"></td></c:otherwise>
				</c:choose>
				<c:choose>
						<c:when test="${!empty adelement.childAdType}">
							<td class="td-fore1">${adelement.childAdType.typeName}</td>
						</c:when>
						<c:otherwise><td class="td-fore1"></td></c:otherwise>
				</c:choose>
				<td>
				<fmt:formatDate value="${adelement.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td><fmt:formatDate value="${adelement.endDate}" pattern="yyyy-MM-dd"/></td>
				<td class="td-fore2">${fns:getDictLabel(adelement.isFlag,'adv_isflag',"")}</td>
 
				<td class="td-fore2">
				
				<c:choose>
					<c:when test="${1 == adelement.status}">
						<spring:message code="auditing"/>
					</c:when>
					<c:otherwise>
						${fns:getDictLabel(adelement.status, 'adv_status', "")}
					</c:otherwise>
				</c:choose>
				</td>
		
				<c:choose>
						<c:when test="${!empty adelement.auditUser}">
							<td class="td-fore2">${adelement.auditUser.name}</td>
						</c:when>
						<c:otherwise><td class="td-fore2"></td></c:otherwise>
				</c:choose>
				
			 
					<td class="td-fore2">${adelement.remarks}</td>
				<td>
				 
				<shiro:hasPermission name="sys:adv:edit">
				                <fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd" var="nowDate"/>
								<fmt:formatDate value="${adelement.endDate}" pattern="yyyy-MM-dd" var="endDate"/>     
						<c:choose>
							<c:when test="${1 == adelement.status || (4 == adelement.status && endDate lt nowDate )}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='adv.design' /></a>
							</c:when>
							<c:when test="${3 == adelement.status && endDate lt nowDate}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='adv.design' /></a>
							</c:when>
							<c:when test="${3 == adelement.status  && endDate ge nowDate}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='update' /></a>
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/closedown?id=${adelement.id}" onclick="return confirmx('<spring:message code='sure.close.down.current' />', this.href)"><spring:message code='close.down' /></a>
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/closedownNow/?id=${adelement.id}" onclick="return confirmx('<spring:message code='right.now.delete.sure' />', this.href)"><spring:message code='right.now.delete' /></a>						
							</c:when>
							<c:when test="${2 == adelement.status}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/put?id=${adelement.id}" onclick="return confirmx('<spring:message code='right.now.put.sure' />', this.href)"><spring:message code='right.now.put' /></a>
							</c:when>
							<c:when test="${4 == adelement.status && endDate ge nowDate}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='update' /></a>
<%-- 								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/freeze?id=${adelement.id}" onclick="return confirmx('<spring:message code='right.now.freeze.sure' />', this.href)"><spring:message code='adv.freeze' /></a>
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/restart?id=${adelement.id}" onclick="return confirmx('<spring:message code='right.now.restart.sure' />', this.href)"><spring:message code='adv.restart' /></a>
 --%>							</c:when>
						    <c:when test="${6 == adelement.status}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='adv.design' /></a>
							</c:when>
							 <c:when test="${7 == adelement.status}">
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='adv.design' /></a>
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/delete?status=${adelement.status}&id=${adelement.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
							</c:when>
							<c:otherwise>
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/form?id=${adelement.id}"><spring:message code='update' /></a>
								&nbsp;&nbsp;<a href="${ctx}/adv/adelement/delete?status=${adelement.status}&id=${adelement.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
							</c:otherwise>
						</c:choose>
						 
				</shiro:hasPermission>
				
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>