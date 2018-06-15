<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='grade.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
 <div class="top_position">
 <div class="top_position_lab"><spring:message code="position"/>:</div>
 <ul>
 <li><spring:message code="sys.user"/></li>
 <li>></li>
 <li><spring:message code="grade.manage"/></li>
 <li>></li>
 <li><a href="${ctx}/sys/office/"><spring:message code="grade.list" /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/"><spring:message code='grade.list' /></a></li>
		<shiro:hasPermission name="sys:office:edit"><li>
	 
					<a href="${ctx}/sys/office/form"><spring:message code='grade.add' /></a>
		 
		</li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
	    <thead>
		<tr><th><spring:message code='grade.name' /></th>
		<th><spring:message code='grade.number' /></th>
		<th><spring:message code='grade.level' /></th>
		<th><spring:message code='memo' /></th>
		<shiro:hasPermission name="sys:office:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission></tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="office">
			<tr id="${office.id}" pId="${office.parent.id ne requestScope.office.id?office.parent.id:'0'}">
				<td>
				<c:if test="${user.userType != 1 }"> 
						${office.name}
				</c:if> 
				<c:if test="${user.userType == 1 }">
						<a href="${ctx}/sys/office/form?id=${office.id}">${office.name}</a>
				</c:if>
				</td>
				<td>${office.code}</td>
				<td>${fns:getDictLabel(office.grade, 'sys_office_grade', office.grade)}</td>
				<%-- <td>${fns:getDictLabel(office.type, 'sys_office_type', '')}</td> --%>
				<td>${office.remarks}</td>
				<td class="officetype" style="display:none;">${office.type}</td>
				<shiro:hasPermission name="sys:office:edit"><td>
						<a href="${ctx}/sys/office/form?id=${office.id}"><spring:message code='update' /></a>
						<a href="${ctx}/sys/office/delete?id=${office.id}" onclick="return confirmx('<spring:message code='deleteOffice' />', this.href)"><spring:message code='delete' /></a>
						<a href="${ctx}/sys/office/form?parent.id=${office.id}"><spring:message code='add.child.grade' /></a> 
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<script type="text/javascript">
		//log
		function log(des, value) {
			try {
				console.info(new Date() + "%c" + des, "color:blue; font-weight:bold", value);
			} catch (e) {
			}
		}
	
		$(document).ready(function() {
			$(".officetype").each(function(){
			if($(this).text()=="2"){
				log("rowdata", $(this).prev().children().next().next());
				$(this).prev().children().next().next().css('display','none');
			}
			});
		});
	</script>
</body>
</html>