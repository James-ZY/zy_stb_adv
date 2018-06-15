<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='combo.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
 
		var roleName = ""; //add by pengr for close  归属机构  tree 
		 
		function page(n,s){
 
			var rule=$("#searchForm").valid();
			if(rule){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#searchForm").attr("action","${ctx}/adv/sell/comboReleaseCount");
				$("#searchForm").submit();
			}else{
				return false;
			}
	    }
		 
	</script>
</head>
<body>
	<!-- 某种广告类型的所有套餐总数，即广告商购买套餐点击表格中所有的数字按钮跳转页面 -->
   <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adcombo.statistic' /></li>
    <li>></li>
    <li><spring:message code='combo.sell.to.advtiser' /></li>
    <li>></li>
 
    <li>
    	<a href="${ctx}/adv/sell/comboReleaseCount"><spring:message code='combo.release.list' arguments="${typeName}"/></a> 
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/sell/comboReleaseList"><spring:message code='combo.release.count.list'/></a></li>
		<li class="active"><a href="${ctx}/adv/sell/comboReleaseCount?startDate=${beginDate}&endDate=${endDate}&typeId=${adComboPublishNumber.adType.id}&selectType=${selectType}">
		<c:if test="${selectType eq 'release'}">
		<spring:message code='combo.release.list' arguments="${typeName}"/>	
		</c:if>
		<c:if test="${selectType eq 'unrelease'}">
		<spring:message code='combo.unrelease.list' arguments="${typeName}"/>	
		</c:if>
		 </a> </li>	
	</ul>
	<form:form id="searchForm" modelAttribute="adComboPublishNumber" action="${ctx}/adv/sell/comboReleaseCount" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<input id="typeId" name="typeId" type="hidden" value="${adComboPublishNumber.adType.id}"/>
		<input id="selectType" name="selectType" type="hidden" value="${selectType}"/>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<%-- <th><spring:message code='combo.id' /></th> --%>
		<th><spring:message code='combo.name' /></th>
		<th><spring:message code='adv.type'/>
		<th><spring:message code='sell.advertiser.name' /></th>
		<th><spring:message code='type.isflag'/>
		<th><spring:message code='combo.startdate'/>
		<th><spring:message code='combo.enddate'/>
		<th><spring:message code='combo.playstart'/>
		<th><spring:message code='combo.playend'/>
		<th><spring:message code='combo.status' /></th>
	 
		 
	 
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="comboPublish">
		 
			<tr>
				 
				<td>${comboPublish.adCombo.comboName}</td>
				<c:choose>
					<c:when test="${!empty comboPublish.adType }">
						<td>${comboPublish.adType.typeName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<td>${comboPublish.advertiser.name}</td>
				<td>${fns:getDictLabel(comboPublish.adCombo.isFlag, 'adv_type_flag', "")}</td>
		 
				<td><fmt:formatDate value="${comboPublish.adCombo.validStartTime}" pattern="yyyy-MM-dd"/></td>
		 
				<td><fmt:formatDate value="${comboPublish.adCombo.validEndTime}" pattern="yyyy-MM-dd"/></td>
				<td>${comboPublish.adCombo.startTime}</td>
				<td>${comboPublish.adCombo.endTime}</td>
		
				<td>${fns:getDictLabel(comboPublish.adCombo.status, 'combo_status', "")}</td>
			
			</tr>
				 
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>