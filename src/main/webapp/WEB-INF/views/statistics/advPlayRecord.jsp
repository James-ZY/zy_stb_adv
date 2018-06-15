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
			$("#searchForm").attr("action","${ctx}/adv/adStatistic/advPlayDetailQuery");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
<!-- 广告播放记录统计 -->
      <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
	    <li><spring:message code='adv.statistics' /></li>
	    <li>></li>
	    <li><spring:message code='adStatistic' /></li>
	    <li>></li>
	 
	    <li>
	    	<a href="${ctx}/adv/adStatistic/advPlayDetailQuery"><spring:message code='adv.play.statistic.detail'/></a> 
	     </li>
	     </ul>
    </div>
	<ul class="nav nav-tabs">
	 
		<li class="active"><a href="${ctx}/adv/adStatistic/advPlayDetailQuery"><spring:message code='adv.play.statistic.detail' /></a></li>
		 
	</ul>
	<form:form id="searchForm" modelAttribute="adStatistic" action="${ctx}/adv/adStatistic/advPlayDetailQuery" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		 <div>
			 <div class="query-item">
				 <label><spring:message code='adv.id' />:</label>
				 <form:input path="adElemet.adId" htmlEscape="false" maxlength="8" class="input-small"/>
			 </div>
			 <div class="query-item">
			 	<label><spring:message code='adv.play.startdate' />：</label>
			 	<input id="playStartDate" name="playStartDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
						value="${playStartDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			 </div>
			 <div class="query-item">
			 	<label><spring:message code='adv.play.enddate' />：</label>
			 	<input id="playEndDate" name="playEndDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
						value="${playEndDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			 </div>
			 <div class="query-item">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			 </div>				 
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th><spring:message code='adv.id' /></th>
		<th><spring:message code='adv.name' /></th>
		<th><spring:message code='stb.number' /></th>
		<th><spring:message code='first.scan.time'/></th>
		<th><spring:message code='adv.play.startdate'/>
		<th><spring:message code='adv.durtion'/>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adStatistic">
		 
			<tr>
				 
				<td>${adStatistic.adElemet.adId}</td>
				<td>${adStatistic.adElemet.adName}</td>
				<td>${adStatistic.smartcardId}</td>
				<td><fmt:formatDate value="${adStatistic.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${adStatistic.playStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
				<td>${adStatistic.duration}</td>
			
			</tr>
				 
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>