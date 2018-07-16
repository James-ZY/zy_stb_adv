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
			// 表格排序
            var orderBy = $("#orderBy").val().split(",")[0].split(" ");
			$("#contentTable th.sort").each(function(){
				if ($(this).hasClass(orderBy[0])){
					orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
					$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
				}
			});
			$("#contentTable th.sort").click(function(){
                var order = $(this).attr("class").split(" ")[1];
                var sort = $("#orderBy").val().split(",")[0].split(" ");
                sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
                $("#orderBy").val(order+" "+sort);
				/*for(var i=0; i<order.length; i++){
				 if (order[i] == "sort"){order = order[i+1]; break;}
				 }
				 if (order == sort[0]){
				 sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
				 $("#orderBy").val(order+" "+sort);
				 }else{
				 $("#orderBy").val(order+" DESC");
				 }*/
                page();
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/plan/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
		    <li><spring:message code="adv.plan"/></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/plan/list"><spring:message code='adv.plan.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/plan/list"><spring:message code='adv.plan.list' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adelement" action="${ctx}/adv/plan/list" method="post" class="breadcrumb form-search">
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
					<label><spring:message code='adv.type' />：</label>
					<form:select path="adCombo.adType.id">
						<option value=""><spring:message code="userform.select"/></option>
						<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
				<%--<div class="query-item">
					<label><spring:message code='adv.display.type' />:</label>
					<form:select path="status">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getAdDisplayType('adv_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>--%>
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
					<label><spring:message code='adv.status' />:</label>
					<form:select path="status">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getDictList('adv_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
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
		<th class="sort a.ad_id">ID</th>
		<th class="sort a.ad_name"><spring:message code='adv.name' /></th>
		<c:if test="${isAdv ==false}">
					<th class="sort k.id td-fore1"><spring:message code='adv.user'/></th>
		</c:if>
		<th class="sort c.ad_combo_name td-fore1"><spring:message code='adv.adcombo'/></th>
		<th class="sort t.id td-fore1"><spring:message code='channel.adv.type'/></th>
		<th style="width:90px;" class="td-control"><spring:message code='adv.son.type'/><i class="td-open"></i></th>
<%--
		<th class="td-fore1"><spring:message code='adv.display.type'/></th>
--%>
		<th class="sort a.start_date"><spring:message code='adv.playstart' /></th>
		<th class="sort a.end_date"><spring:message code='adv.playend' /></th>
		<th><spring:message code='combo.playstart'/></th>
		<th><spring:message code='combo.playend'/></th>
		<th class="sort a.ad_status"><spring:message code='adv.status'/></th>
		<th class="td-fore2"><spring:message code='adv.isflag' /></th>
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
						<c:when test="${!empty adelement.adCombo.childAdType}">
							<td>${adelement.adCombo.childAdType.typeName}</td>
						</c:when>
						<c:otherwise><td></td></c:otherwise>
				</c:choose>
<%--
				<td class="td-fore1"></td><!-- TODO 广告显示模式 -->
--%>
				<td>
				<fmt:formatDate value="${adelement.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td><fmt:formatDate value="${adelement.endDate}" pattern="yyyy-MM-dd"/></td>
				<td>${adelement.adCombo.startTime}</td>
				<td>${adelement.adCombo.endTime}</td>
				<td>
					<c:choose>
						<c:when test="${1 == adelement.status}">
							<spring:message code="auditing"/>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(adelement.status, 'adv_status', "")}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="td-fore2">${fns:getDictLabel(adelement.isFlag,'adv_isflag',"")}</td>
				<td>
					<shiro:hasPermission name="sys:adv:edit">
					<a href="${ctx}/adv/selfAdelement/adelementPreview?id=${adelement.id}"><spring:message code='adv.preview' /></a>
					</shiro:hasPermission>
				</td>


				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>