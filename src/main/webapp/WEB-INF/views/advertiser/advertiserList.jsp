<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='advertiser.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">
	.sort{color:#0663A2;cursor:pointer;}
	.uplodFile{margin:10px 0;position: relative;height: 30px;margin-left: 25px;}
	.down-model{position: relative;height: 30px;}
	.uplodFile span{display: block;position: absolute;height: 100%;width:40%;top: 0;left: 2px;border: 1px solid gainsboro;border-radius: 4px;line-height: 30px;font-size: 16px;overflow: hidden;}
	.uplodFile #uploadFile{display: block;position: absolute;height: 100%;width:40%;top: 0;left: 2px;opacity: 0;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);}
	.uplodFile #btnImportSubmit{position: absolute;left:50%;}
	.upload_file{width:350px;height:100px;margin-bottom:10px;}
    .down-model a{position: absolute;line-height:30px;width: 100%;left: 0;text-align: left;text-indent: 25px;}
	</style>
	<link rel="stylesheet" href="${ctx}/static/fileUpLoad/fileUpLoadcss/skinBase.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script src="${ctxStatic}/dropzone/dropzone.min.js"></script>
 	<script src="${ctx}/static/fileUpLoad/js/uploadFile.js"></script>
	<script type="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		$(document).ready(function() {
			// 表格排序
			 
			$("#btnExport").click(function(){
				top.$.jBox.confirm(accipiter.getLang("exportadvertisserData"),accipiter.getLang("systemPrompt"),function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/adv/advertiser/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
/* 			$("#btnImport").click(function(){
				var close= accipiter.getLang('close');
 
				$.jBox($("#importBox").html(), {title:accipiter.getLang('importData'), buttons:{close:true}, 
					bottomText:accipiter.getLang("importFormat")});
			}); */
		    function getY(e){
				var e = event || window.event;
				return e.pageY ||e.clientY+document.body.scrollTop;
		    }
		    $("#btnImport").click(function(){
		    	var topHeight=getY($(this))+20;
		    	if($(this).attr("name")=="0"){
		    		$(this).attr("name","1");
		    		$(".FileImport").attr("name","1");
				    var upLoadUrl="${ctx}/adv/advertiser/import";
				    var modelUrl="${ctx}/adv/advertiser/import/template";
				    $(".FileImport").css("top",topHeight);
				    $(".FileImport").FileImport({"uploadUrl":upLoadUrl,"modelUrl":modelUrl});
		    	}else{
		    		if($(".FileImport").attr("name")=="0"){
					    var upLoadUrl="${ctx}/adv/advertiser/import";
					    var modelUrl="${ctx}/adv/advertiser/import/template";
					    $(".FileImport").css("top",topHeight);
					    $(".FileImport").FileImport({"uploadUrl":upLoadUrl,"modelUrl":modelUrl});
		    			$(".FileImport").attr("name","1");
		    		}else{
			    		$(this).attr("name","0");
			    		$(".FileImport").html("");
			    		 $(".FileImport").css("top","-1000px");
			    		$(".FileImport").attr("name")=="0";
		    		}
		    	}	    	
			});		
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/advertiser");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="adv.user"/></li>
		    <li>></li>
		    <li><spring:message code="adv.userManage"/></li>
		    <li>></li>
		    <li>
		    <a href="${ctx}/adv/advertiser"><spring:message code='advertiser.list' /></a>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/advertiser"><spring:message code='advertiser.list' /></a></li>
		<shiro:hasPermission name="sys:advertiser:edit"><li><a href="${ctx}/adv/advertiser/form"><spring:message code='advertiser.add'/></a></li></shiro:hasPermission>
	</ul>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/adv/advertiser/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading(accipiter.getLang('loading'));">
			<div class="uplodFile">
			    <span id="FlieName"><spring:message code='click.upload'/></span>
			      <input id="uploadFile" name="file" type="file"/>
			     <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="<spring:message code='import.advertiser'/>" />
			</div>
		<div class="down-model">
		   <a href="${ctx}/adv/advertiser/import/template"><spring:message code='download.advertiser.template' /></a>
	    </div>
		</form>
	</div>
	<div>
	
	</div>
	<div class="FileImport" name="0"></div>
	<form:form id="searchForm" modelAttribute="advertiser" action="${ctx}/adv/advertiser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			 <div class="query-item">
			 	<label><spring:message code='advertiser.id' /> ：</label><form:input path="advertiserId" htmlEscape="false" maxlength="50" class="input-small"/> 
			 </div>
			 <div class="query-item">
			    <label><spring:message code='advertiser.name' />：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
			 </div>
			 <div class="query-item">
			 	<label><spring:message code='advertiser.type' />：</label>
			 	<form:select path="type">
					<option value=""> <spring:message code='userform.select' /></option>
					<form:options items="${fns:getDictList('advertiser_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			 </div>
			 <div class="query-item">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			 </div>
			 <div class="query-item">
				<shiro:hasPermission name="sys:advertiser:export">
				<input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>
				</shiro:hasPermission>
			 </div>
			 <div class="query-item">
				<shiro:hasPermission name="sys:advertiser:import">
				<input id="btnImport" class="btn btn-primary" type="button" value="<spring:message code='import' />"/>			
				</shiro:hasPermission>
				<form:hidden path="uploadMessage"/>
			 </div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='advertiser.id' /></th>
		<th><spring:message code='advertiser.name' /></th>
		<th><spring:message code='advertiser.industry' /></th>
		<th><spring:message code='advertiser.businessLicenseNumber' /></th>
		<th><spring:message code='advertiser.phone' /></th>
		<th><spring:message code='advertiser.mobile' /></th>
		<!--  <th><spring:message code='audit.role' /></th>-->
		<th><spring:message code='advertiser.type' /></th>
		<shiro:hasPermission name="sys:advertiser:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="advertiser">
			<tr>
				<td><a href="${ctx}/adv/advertiser/form?id=${advertiser.id}">${advertiser.advertiserId}</a></td>
				<td>${advertiser.name}</td>
				<td>${advertiser.industry}</td>
				<td>${advertiser.businessLicenseNumber}</td>
				<td>${advertiser.phone}</td>
				<td>${advertiser.mobile}</td>
				<!-- <td>${advertiser.roleNames}</td> -->
				<td>${fns:getDictLabel(advertiser.type, 'advertiser_type', "")}</td>
				
				<shiro:hasPermission name="sys:advertiser:edit">
					<td>
						 <a href="${ctx}/adv/advertiser/form?id=${advertiser.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/advertiser/delete?id=${advertiser.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
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