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
		console.log(1);
		function page(n,s){  
			var rule=$("#searchForm").valid();
			if(rule){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#searchForm").attr("action","${ctx}/adv/adStatistic/advPlayCount");
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
<body>
<!-- 广告点击统计一级页面 -->
      <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
	    <li><spring:message code='adv.statistics' /></li>
	    <li>></li>
	    <li><spring:message code='adv.click.statistics' /></li>
	    <li>></li>
	 
	    <li>
	    	<a href="${ctx}/adv/adStatistic/advPlayCount"><spring:message code='adv.click.record'/></a> 
	     </li>
	     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/adStatistic/advPlayCount"><spring:message code='adv.click.record' /></a></li>
	    
	</ul>
	<form:form id="searchForm" modelAttribute="adStatistic" action="${ctx}/adv/adStatistic/advPlayCount" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				 <div class="query-item">
					 <label><spring:message code='adv.id' />:</label>
					 <form:input path="adElemet.adId" htmlEscape="false" maxlength="8" class="input-small"/>
				 </div>
				 <div class="query-item">
				 	<label><spring:message code='adv.play.startdate' />：</label><input id="playStartDate" name="playStartDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${playStartDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 </div>
				 <div class="query-item">
				 	<label><spring:message code='adv.play.enddate' />：</label><input id="playEndDate" name="playEndDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${playEndDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
		<th><spring:message code='adv.id' /></th>
		<th style="color:#005580"><spring:message code='play.count'/></th>
		 
 
		</tr></thead>
		<tbody> 
			 
				<c:forEach items="${page.list}" var="adStatistic">
				<c:if test="${null != adStatistic.dto}"></c:if>
					<tr>
					<td>${adStatistic.dto.advId}</td>
					<td><a  href="${ctx}/adv/adStatistic/advClickDetail?advId=${adStatistic.dto.advId}&startDate=${playStartDate}&endDate=${playEndDate}">
						 ${adStatistic.dto.count}</a>
					</td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
 
</body>
</html>

 
