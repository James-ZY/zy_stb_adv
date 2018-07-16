<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='adelement.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style adelement="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script adelement="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
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
				$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{"关闭":true}, 
					bottomText:accipiter.getLang("importFormat")});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/adelement/audit/query");
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
		    <li><spring:message code='adv.audit' /></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/adelement"><spring:message code='audit.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/adelement/audit/query"><spring:message code='audit.list' /></a></li>
		 
	</ul>
	<form:form id="searchForm" modelAttribute="adelement" action="${ctx}/adv/adelement/audit/query" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
			<div>
				<div class="query-item">
					<label><spring:message code='adv.id' />：</label><form:input path="adId" htmlEscape="false" maxlength="8" class="input-small"/> 
				</div> 
				<div class="query-item">
					<label><spring:message code='adv.name' />：</label><form:input path="adName" htmlEscape="false" maxlength="50" class="input-small"/> 
				</div>
				<div class="query-item">
					<label><spring:message code='combo.name' />：</label><form:input path="adCombo.comboName"  htmlEscape="false" maxlength="50" class="input-small"/>
				</div> 
				<div class="query-item">
					<c:if test="${fns:checkUserIsAdvertiser()}">
						<label><spring:message code='adv.user' />：</label>
						<form:select path="advertiser.id">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</c:if>
				</div>				
			<div class="query-item">
				<label><spring:message code='adv.status' />：</label>
				<form:select path="status">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${statusSelect}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			</div> 
			 
			<div class="query-item">
			     <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>
			</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content aduitList">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
		<th><spring:message code='adv.id' /></th>
		<th><spring:message code='adv.name' /></th>
		<th><spring:message code='adv.user'/>
		<th class="td-fore1"><spring:message code='adv.adcombo'/>
		<th class="td-fore1"><spring:message code='channel.adv.type'/></th>
		<th class="td-fore1"><spring:message code='adv.son.type'/></th>
		<th><spring:message code='adv.playstart' /></th>
		<th class="td-control"><spring:message code='adv.playend' /><i class="td-open"></i></th>
		<th class="td-fore2"><spring:message code='claim.time' /></th>
		<th class="td-fore2"><spring:message code='audit.status' /></th>
	
		<th class="td-fore2"><spring:message code='adv.audit.user' /></th>
		<th class="td-fore2"><spring:message code='adv.remarks' /></th>
		<shiro:hasPermission name="sys:adv:audit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="adelement">
			<tr>
				<td>${adelement.adId}</td>
				<td>${adelement.adName}</td>
				<c:choose>
					<c:when test="${!empty adelement.advertiser}">
						<td>${adelement.advertiser.name}</td>
					</c:when>
					<c:otherwise><td></td></c:otherwise>
				</c:choose>
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
							<td class="td-fore1">${adelement.adCombo.childAdType.typeName}</td>
						</c:when>
						<c:otherwise><td class="td-fore1"></td></c:otherwise>
				</c:choose>
				<td>
				<fmt:formatDate value="${adelement.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td><fmt:formatDate value="${adelement.endDate}" pattern="yyyy-MM-dd"/></td>
			 	<td class="td-fore2"><fmt:formatDate value="${adelement.claimDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="td-fore2">
					<c:choose>
						<c:when test="${adelement.status == 0 || adelement.status == 1 || adelement.status == -1}">
							${fns:getDictLabel(adelement.status, 'adv_status', "")}
						</c:when>
						<c:otherwise>
							<spring:message code='adv.status.pass'/>
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
				<shiro:hasPermission name="sys:adv:audit">
					<td>
						<c:choose>
							<c:when test="${0==adelement.status}">
									<a href="${ctx}/adv/adelement/saveClaim?id=${adelement.id}"><spring:message code='claim' /></a>
							</c:when>
							
							<c:otherwise>
							
								  <a href="${ctx}/adv/adelement/auditForm?id=${adelement.id}"><spring:message code='audit' /></a>
							</c:otherwise>
						</c:choose>

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