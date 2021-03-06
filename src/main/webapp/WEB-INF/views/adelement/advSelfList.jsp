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
	<script src="${ctx}/static/layer/layer.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
			/**
			 * 套餐名称点击事件
			 * @param param
			 */
			function alertAdCombo(param) {
				layer.open({
					type: 2,
					title: accipiter.getLang_(messageLang,"adv.comboInfo"),
					area: ['850px', '650px'], //宽高
                    shadeClose:true,
					content: '${ctx}/adv/selfAdelement/getAdCombo?id='+param,
					success: function(layero, index){
						//清空frame nav及搜索框
						//top.window.frames["mainFrame"].frames[0].trimLayout();
					}
				});
			}
			
			/**
			 * 广告详情点击事件
			 * @param param
			 */
			function alertAdelement(comboId,startDate,endDate) {
				layer.open({
					type: 2,
					title: accipiter.getLang_(messageLang,"adv.advInfo"),
					area: ['1600px', '700px'], //宽高
                    shadeClose:true,
					content: '${ctx}/adv/selfAdelement/alertAdelement?comboId='+comboId+'&startDate='+startDate+'&endDate='+endDate,
					success: function(layero, index){
						//清空frame nav及搜索框
						//top.window.frames["mainFrame"].frames[0].trimLayout();
					}
				});
			}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/selfAdelement");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
     <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
		    <li><spring:message code='adv.manage' /></li>
		    <li>></li>
		    <li><spring:message code='adv.selfMan' /></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/selfAdelement"><spring:message code='sell.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/selfAdelement"><spring:message code='sell.list' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adSell" action="${ctx}/adv/selfAdelement/" method="post" class="breadcrumb form-search">
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

		<th><spring:message code='sell.comboname'/>
		<th><spring:message code='type.name'/>
		<th><spring:message code='sell.advertiser.id' /></th>
		<th><spring:message code='sell.advertiser.name' /></th>
		<th><spring:message code='sell.startDate' /></th>
		<th class="td-fore1"><spring:message code='sell.endDate'/>
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
		<th><spring:message code='operation' /></th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adSell">
			<tr>
				
				
				<c:choose>
					<c:when test="${ !empty adSell.adCombo}">
						<td><a href="javascript:void(0)" onclick="alertAdCombo('${adSell.adCombo.id}')">${adSell.adCombo.comboName}</a></td>
					</c:when>
					<c:otherwise>
						<td></td>
						<td></td>
					</c:otherwise>
				</c:choose>
			 	<td>${adSell.adCombo.adType.typeName}</td>
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
					<td>
                      <a href="javascript:void(0);" onclick="alertAdelement('${adSell.adCombo.id}','<fmt:formatDate value="${adSell.startDate}" pattern="yyyy-MM-dd"/>','<fmt:formatDate value="${adSell.endDate}" pattern="yyyy-MM-dd"/>')"><spring:message code='adv.list' /></a>
	    			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
 
</body>
</html>