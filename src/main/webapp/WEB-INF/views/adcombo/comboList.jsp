<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='combo.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}
		.selectlength {
			width:110px;
		}
		.selectlength1 {
			width:150px;
		}
		
	</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		 
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/combo");
			$("#searchForm").submit();
	    	return false;
	    }
		
		$(document).ready(function(){
			$("#btnExport").click(function(){
				var info = accipiter.getLang("export.combo.Data");
				 
				var systemPrompt =accipiter.getLang("systemPrompt");
				 
				top.$.jBox.confirm(info,systemPrompt,function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/adv/combo/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
		    <li><spring:message code='sell.adcombo' /></li>
		    <li>></li>
		    <li><spring:message code='combo.manage' /></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/combo"><spring:message code='combo.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/combo"><spring:message code='combo.list' /></a></li>
		<shiro:hasPermission name="sys:combo:edit"><li><a href="${ctx}/adv/combo/form"><spring:message code='combo.add'/></a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adCombo" action="${ctx}/adv/combo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				<div class="query-item">
					<label><spring:message code='combo.name' />：&nbsp;&nbsp; </label><form:input path="comboName"  htmlEscape="false" maxlength="50" class="input-small selectlength1"/>
				</div>
				<div class="query-item">
					<label><spring:message code='channel.id' />：</label><form:input path="channelId" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
					<label><spring:message code='channel.name' />：</label><form:input path="channleName" htmlEscape="false" maxlength="50" class="input-small"/>
				</div>
				<div class="query-item">
					<label><spring:message code='combo.status' />：</label>
					<form:select path="status" class="selectlength">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getDictList('combo_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>											
				<div class="query-item">
					<label><spring:message code='adNetwork' />：</label>
					<form:select path="networkId" class="selectlength1">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${ fns:findAdNetworkAll()}" itemLabel="networkName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div>
				<div class="query-item">
					<label><spring:message code='adv.type' />：</label>
					<form:select path="adType.id" class="selectlength1">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
				 <div class="query-item">
				 	<label><spring:message code='combo.startdate' />：</label>
				 	<input id="validStartTime" name="validStartTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				     value="${validStartTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				 </div>
				 <div class="query-item">
				    <label><spring:message code='combo.enddate' />：</label>
				    <input id="validEndTime" name="validEndTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				    value="${validEndTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> 
				 </div>
				
				
				<div class="query-item">
					<label><spring:message code='type.isflag' />：</label>
					<form:select path="isFlag" class="selectlength">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getDictList('adv_type_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
					<div class="query-item">
					<label><spring:message code='type.isExpired' />：</label>
					<form:select path="isExpired" class="selectlength">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getDictList('adv_type_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
				<div class="query-item">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
				</div>
				<div class="query-item">
					<shiro:hasPermission name="sys:combo:export">
					&nbsp;&nbsp;  <input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>
					</shiro:hasPermission>
				</div>			
			</div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content combolist">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<%-- <th><spring:message code='combo.id' /></th> --%>
		<th><spring:message code='combo.name' /></th>
		<th><spring:message code='adv.type'/></th>
		<th class="td-fore1"><spring:message code='type.isflag'/></th>
		<th class="td-fore1"><spring:message code='operators.area'/></th>
		<th class="td-fore1"><spring:message code='combo.startdate'/></th>
		<th class="td-fore1"><spring:message code='combo.enddate'/></th>
		<th class="td-control"><spring:message code='combo.playstart'/><i class="td-open"></i></th>
		<th class="td-fore2"><spring:message code='combo.playend'/></th>
		<th class="td-fore2"><spring:message code='combo.status' /></th>
		<th class="td-fore2"><spring:message code='combo.isvalid' /></th>
		<th class="td-fore2"><spring:message code='combo.isvalid.reason' /></th>
		<shiro:hasPermission name="sys:combo:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adCombo">
			<tr>
				<!--  <td><a href="${ctx}/adv/combo/form?id=${adCombo.id}">${adCombo.adcomboId}</a></td>-->
				<td>${adCombo.comboName}</td>
				<c:choose>
					<c:when test="${!empty adCombo.adType }">
						<td>${adCombo.adType.typeName}</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
				<td class="td-fore1">${fns:getDictLabel(adCombo.isFlag, 'adv_type_flag', "")}</td>
				<td class="td-fore1">${adCombo.area}</td>
		 
				<td class="td-fore1"><fmt:formatDate value="${adCombo.validStartTime}" pattern="yyyy-MM-dd"/></td>
		 
				<td class="td-fore1"><fmt:formatDate value="${adCombo.validEndTime}" pattern="yyyy-MM-dd"/></td>
				<td>${adCombo.startTime}</td>
				<td class="td-fore2">${adCombo.endTime}</td>
		
				<td class="td-fore2">${fns:getDictLabel(adCombo.status, 'combo_status', "")}</td>
				 
					<c:choose>
					 <c:when test="${adCombo.isValid == 1}">
					 	<td class="td-fore2"><spring:message code='yes'/></td>
					 	<td class="td-fore2"></td>
					 </c:when>
					 <c:otherwise>
					 <td class="td-fore2"><spring:message code='no'/></td>
					 <td class="td-fore2"><spring:message code='combo.isvalid.reason.message'/></td>
					 </c:otherwise>
					</c:choose>
			 
				<shiro:hasPermission name="sys:combo:edit">
					<td>	
					 <c:if test="${adCombo.isValid == 1}">
					     <a href="${ctx}/adv/combo/form?id=${adCombo.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/combo/copy?id=${adCombo.id}"><spring:message code='copy' /></a>
						 <a href="${ctx}/adv/combo/delete?id=${adCombo.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>
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