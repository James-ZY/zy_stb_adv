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
                    $("#orderBy").val(order+" "+sort);
                }else{
                    $("#orderBy").val(order+" DESC");
                }
                page();
            });
        });
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/sell");
			$("#searchForm").submit();
	    	return false;
	    }
	 
	</script>
</head>
<body>
     <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
		    <li><spring:message code='sell.adcombo' /></li>
		    <li>></li>
		    <li><spring:message code='adv.sell' /></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/sell"><spring:message code='sell.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/sell"><spring:message code='sell.list' /></a></li>
		<shiro:hasPermission name="sys:sell:edit"><li><a href="${ctx}/adv/sell/form"><spring:message code='sell.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adSell" action="${ctx}/adv/sell/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				<div class="query-item">
					<c:choose>
					
					 	<c:when test="${isAdvtiser == false}">
					 		<label><spring:message code='combo.sell.time' />:</label>
					 	</c:when>
					 	<c:otherwise>
					 		<label><spring:message code='advitser.buy.time' />:</label>
					 	</c:otherwise>
					 
					 </c:choose>
					 <input id="createStartDate" name="createStartDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
						value="${beginCreateDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'createEndDate\')}'});"/>&nbsp;&nbsp;
					 <spring:message code='to' />&nbsp;&nbsp;<input id="createEndDate" name="createEndDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
						value="${endCreateDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'createStartDate\')}'});"/>
				</div>
				<div class="query-item">
				   <label><spring:message code='sell.valid.time' />：</label><input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
						value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'endDate\')}'});"/>
						&nbsp;&nbsp;
						<spring:message code='to' />&nbsp;&nbsp;
			       <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
						value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startDate\')}'});"/>
				</div>
				<div class="query-item">
					<label><spring:message code='combo.name' />：</label><form:input path="adCombo.comboName" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>				
			</div> 
			<div>
				<div class="query-item">
					<label><spring:message code='sell.user' />：</label><form:input path="createBy.loginName" htmlEscape="false" maxlength="50" class="input-small"/>		
				</div>
				<div class="query-item">
				    <label><spring:message code='sell.update.user' />：</label><form:input path="updateBy.loginName" htmlEscape="false" maxlength="50" class="input-small"/>		
				</div>
				<div class="query-item">
				  	<label><spring:message code='adv.type' />：</label>
					<form:select path="adCombo.adType.id">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				    </form:select>
				</div>
				<shiro:hasPermission name="sys:sell:edit">
				<div class="query-item">
				
				       <label><spring:message code='sell.advertiser' />：</label>
					   <form:select path="advertiser.id">
						  <option value=""><spring:message code="userform.select"/></option>
					      <form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				       </form:select>
				  
				</div>
				  </shiro:hasPermission>
				<div class="query-item">
				   <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
				</div>			
			</div>
			
	
		</div>
	</form:form>

	<tags:message content="${message}"/>
	<div class="tab_content sellList">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>

		<th><spring:message code='sell.comboname'/></th>
		<th class="sort t.id"><spring:message code='type.name'/></th>
		<shiro:hasPermission name="sys:sell:edit">
			<th><spring:message code='sell.advertiser.id' /></th>
			<th class="sort a.id"><spring:message code='sell.advertiser.name' /></th>
		</shiro:hasPermission>
		<th class="sort s.start_date"><spring:message code='sell.startDate' /></th>
		<th class="sort s.end_date td-fore1"><spring:message code='sell.endDate'/>
		<th class="td-fore1">
		<c:choose>
					<c:when test="${isAdvtiser == false}">
				 		 <spring:message code='combo.sell.time'/>
				 	</c:when>
				 	<c:otherwise>
				 	 <spring:message code='advitser.buy.time' />
				 	</c:otherwise>
		</c:choose>
		</th>
	
		<th class="td-control">
			<spring:message code='sell.user'/><i class="td-open"></i>
		</th>
		<th class="td-fore2">
			<spring:message code='sell.update.date'/>
		</th>
		<th class="td-fore2">
			<spring:message code='sell.update.user'/>
		</th>
		<shiro:hasPermission name="sys:sell:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adSell">
			<tr>
				
				
				<c:choose>
					<c:when test="${ !empty adSell.adCombo}">
						<td>${adSell.adCombo.comboName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
						<td></td>
					</c:otherwise>
				</c:choose>
			 	<td>${adSell.adCombo.adType.typeName}</td>
			 	<shiro:hasPermission name="sys:sell:edit">
				<c:choose>
					<c:when test="${ !empty adSell.advertiser}">
						<td>${adSell.advertiser.advertiserId}</td>
						<td>${adSell.advertiser.name}</td>
					</c:when>
					<c:otherwise>
						<td></td>
						<td></td>
					</c:otherwise>
				</c:choose>
				</shiro:hasPermission>
				 <td> <fmt:formatDate value="${adSell.startDate}" pattern="yyyy-MM-dd"/></td>
				<td class="td-fore1"><fmt:formatDate value="${adSell.endDate}" pattern="yyyy-MM-dd"/></td>
				<td class="td-fore1"><fmt:formatDate value="${adSell.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></a></td>
				<td> 
					<c:if test="${!empty adSell.createBy}">
					
						${adSell.createBy.loginName}
					</c:if>
				 </td>
				 <td class="td-fore2">
				 	<c:if test="${!empty adSell.updateDate}">
				 				 <fmt:formatDate value="${adSell.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></a> 
				 	</c:if>
				 
				 </td>
				 
				 <td class="td-fore2"> 
					<c:if test="${!empty adSell.updateBy}">					
						${adSell.updateBy.loginName}
					</c:if>
				 </td>
				<shiro:hasPermission name="sys:sell:edit">
					<td>
					<!-- 只有没过期的销售记录才可以进行修改和删除 -->
					     <c:if test="${adSell.isPast == false && adSell.status == '1'}">
						 <a href="${ctx}/adv/sell/form?id=${adSell.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/sell/stop?id=${adSell.id}" onclick="return confirmx('<spring:message code='confirm.stop' />', this.href)"><spring:message code='stop' /></a>
						 <a href="${ctx}/adv/sell/delete?id=${adSell.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
						 </c:if>
						 <c:if test="${adSell.isPast == false && adSell.status == '0'}">
<%-- 						 <a href="${ctx}/adv/sell/form?id=${adSell.id}"><spring:message code='update' /></a>
 --%>						 <a href="${ctx}/adv/sell/delete?id=${adSell.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
						 </c:if>
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