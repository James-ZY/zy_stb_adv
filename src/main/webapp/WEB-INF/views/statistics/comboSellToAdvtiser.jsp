<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true" %>
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
	<script src="${ctx}/static/Highcharts-5.0.0/js/highcharts.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/data.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/exporting.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/drilldown.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/highcharts-more.js"></script>
    <script src="${ctxStatic}/common/language.js"></script>
	<script combo="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		function page(n,s){  
			var rule=$("#searchForm").valid();
			if(rule){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#searchForm").attr("action","${ctx}/adv/sell/comboSellNumber");
				$("#searchForm").submit();
			}else{
		    	return false;
			}
	    }
	</script>
	<style type="text/css">
	.chartContent{height:500px;background-color: #f5f5f5;padding: 8px 15px;margin: 20px 20px;    position: relative;display:none;}
	#chart{width:900px;height:450px;position: absolute;left: 50%;top: 50%;margin-left:-450px;margin-top:-225px;}
	#switchover{margin-left:15px;background-color: #3B95C8;background-image: none;}
	#switchover:hover {background-color: #3EAFE0;}
	#contentTable tbody td a{    display: inline-block;
    width: 100%;}
	</style>
</head>
<!-- 广告商购买套餐统计一级页面 -->

<body>
<div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adcombo.statistic' /></li>
    <li>></li>
    <li><spring:message code='combo.sell.to.advtiser' /></li>
    <li>></li>
    <li>
    <li>
    	<a href="${ctx}/adv/sell/comboSellNumber"><spring:message code='combo.sell.to.advtiser.list' /></a> 
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/sell/comboSellNumber"><spring:message code='combo.sell.to.advtiser.list' /></a></li>
	    
	</ul>
	<form:form id="searchForm" modelAttribute="adSell" action="${ctx}/adv/sell/comboSellNumber" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				 <div class="query-item">
					<label><spring:message code='adv.type' />:</label>
					<form:select path="adCombo.adType.id" class="required">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				 </div>
				 <div class="query-item">
				 	<label><spring:message code='sell.startDate' />：</label><input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
					value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'endDate\',{d:0})}',minDate:'#F{$dp.$D(\'endDate\',{d:-365})}'});"/>
				 </div>
				 <div class="query-item">
				 	<label><spring:message code='sell.endDate' />：</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
					value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startDate\',{d:0})||\'%y-%M-#{%d+1}\'}',maxDate:'#F{$dp.$D(\'startDate\',{d:365})||\'%y-%M-#{%d+366}\'}'});"/>
				 </div>
				 <div class="query-item">
					<label><spring:message code='sell.advertiser' />：</label>
					<form:select path="advertiser.id">
						<option value=""><spring:message code="userform.select"/></option>
						<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				    </form:select>	 
				 </div>
				 <div class="query-item">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
				 </div>
			</div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th><spring:message code='advertiser.id' /></th>
		<th><spring:message code='advertiser.name'/></th>
		<th style="color:#005580"><spring:message code='combo.sell.total'/></th>
		<th style="color:#005580"><spring:message code='advtiser.buy.total'/></th>
		<th><spring:message code='advtiser.buy.scale'/></th>
 
		</tr></thead>
		<tbody> 
		
		<%-- 		<input style="display: none;" type="text" id="id_list" name="swfID" value="${json_list}"/> --%>
				<p style="display: none;" id="id_list">${json_list}</p>
				<c:forEach items="${page.list}" var="adSell">
				<c:if test="${null != adSell.dto}"></c:if>
					<tr>
					<td>${adSell.dto.advId}</td>
					<td>${adSell.dto.advName}</td>
				
					<td> <a href="${ctx}/adv/sell/comboSellCount?startDate=${adSell.startDate}&endDate=${adSell.endDate}&typeId=${adSell.adCombo.adType.id}">
						 ${adSell.dto.total}<spring:message code='day'/></a>
					</td>
		 
					<td> <a href="${ctx}/adv/sell/advtiserBuyCombo?startDate=${adSell.startDate}&endDate=${adSell.endDate}&typeId=${adSell.adCombo.adType.id}&advId=${adSell.dto.advId}&advName=${adSell.dto.advName}">
						 ${adSell.dto.buyNumber}<spring:message code='day'/></a>
					</td>
					<td>${adSell.dto.scale}</td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
 
</body>
</html>

 
