<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='combo.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style combo="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
		<script src="${ctxStatic}/common/language.js"></script>
	<script combo="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/sell/advtiserBuyComboList");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
 
 	<!-- 某个广告商购买某种广告类型的所有套餐总数，即广告商购买套餐点击表格中自己购买的的数字按钮跳转页面 -->
 <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adcombo.statistic' /></li>
    <li>></li>
    <li><spring:message code='combo.sell.to.advtiser' /></li>
    <li>></li>
 
    <li>
    	<a href="${ctx}/adv/sell/comboSellToAdvTiser"><spring:message code='combo.sell.to.advertiser' arguments="${advName},${typeName}" argumentSeparator=","/></a> 
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
	<li><a  onclick="history.go(${adSell.history});"><spring:message code='combo.sell.to.advtiser' /></a></li>
		<li class="active"><a href="${ctx}/adv/sell/advtiserBuyComboList"><spring:message code='combo.sell.to.advertiser' arguments="${advName},${typeName}" argumentSeparator=","/></a></li>
		 
	</ul>
	<form:form id="searchForm" modelAttribute="adSell" action="${ctx}/adv/sell/advtiserBuyComboList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		 <div>
		 <label><spring:message code='combo.name' />：</label><form:input path="adCombo.comboName" htmlEscape="false" maxlength="50" class="input-small"/>
			<form:hidden path="adCombo.adType.id"/>
			<form:hidden path="history"/>
			<form:hidden path="advertiser.advertiserId"/>
		<input id="startDate" type="hidden" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input id="endDate" type="hidden"  name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th><spring:message code='combo.name' /></th>
		<th><spring:message code='adv.type'/>
		<th><spring:message code='type.isflag'/>
		<th><spring:message code='combo.startdate'/>
		<th><spring:message code='combo.enddate'/>
		<th><spring:message code='combo.playstart'/>
		<th><spring:message code='combo.playend'/>
		<th><spring:message code='combo.status' /></th>
	 
		 
	 
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adSell">
		 
			<tr>
				 
				<td>${adSell.adCombo.comboName}</td>
				<c:choose>
					<c:when test="${!empty adSell.adCombo.adType }">
						<td>${adSell.adCombo.adType.typeName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<td>${fns:getDictLabel(adSell.adCombo.isFlag, 'adv_type_flag', "")}</td>
		 
				<td><fmt:formatDate value="${adSell.startDate}" pattern="yyyy-MM-dd"/></td>
		 
				<td><fmt:formatDate value="${adSell.endDate}" pattern="yyyy-MM-dd"/></td>
				<td>${adSell.adCombo.startTime}</td>
				<td>${adSell.adCombo.endTime}</td>
		
				<td>${fns:getDictLabel(adSell.adCombo.status, 'combo_status', "")}</td>
			
			</tr>
				 
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>