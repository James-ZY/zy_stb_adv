<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="com.gospell.aas.entity.sys.ScheduleJob" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='database.manage' /></title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}
	.uplodFile{margin:10px 0;position: relative;height: 30px;margin-left: 25px;}
	.down-model{position: relative;height: 30px;}
	.uplodFile span{display: block;position: absolute;height: 100%;width:40%;top: 0;left: 2px;border: 1px solid gainsboro;border-radius: 4px;line-height: 30px;font-size: 16px;overflow: hidden;}
	.uplodFile #uploadFile{display: block;position: absolute;height: 100%;width:40%;top: 0;left: 2px;opacity: 0;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);}
	.uplodFile #btnImportSubmit{position: absolute;left:50%;}
	.upload_file{width:350px;height:100px;margin-bottom:10px;}
	.down-model a{    position: absolute;line-height:30px;width: 100%;left: 0;text-align: left;text-indent: 25px;}
	.appendHtml{display:none;top:-100000px;}
	#attention{width:24px;height:24px;display:block;float:right;background:url("${ctxStatic}/images/icon/attention.png") no-repeat;}
	#attention:hover{cursor: pointer;}
	.attention-info{padding: 10px 0;margin-bottom: 10px;border: 1px solid #FFCE42;border-radius: 4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;background-color: #DFF0D8;}
	.attention-info p{text-indent: 25px;margin-bottom:0;}
	.hidden{display:none};
	.show{display:block};
	</style>
	<link rel="stylesheet" href="${ctx}/static/fileUpLoad/fileUpLoadcss/skinBase.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/dropzone/dropzone.min.js"></script>
	<script src="${ctx}/static/fileUpLoad/js/sqlUploadFile.js"></script>
	<script src="${ctxStatic}/layer/layer.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function(){
			$("#btnExport").click(function(){
				var info = accipiter.getLang("export.adopertator.Data");
				 
				var systemPrompt =accipiter.getLang("systemPrompt");
				 
				top.$.jBox.confirm(info,systemPrompt,function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/database/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#runJobNow").off("click").on("click",function(){
                $("#searchForm").attr("action","${ctx}/sys/database/runJobNow");
                $("#searchForm").submit();
            });
            function getY(e){
                var e = event || window.event;
                return e.pageY ||e.clientY+document.body.scrollTop;
            }
            $("#btnImport").click(function(){
                var topHeight=getY($(this))+20;
                if($(this).attr("name")=="0"){
                    $(this).attr("name","1");
                    $(".FileImport").attr("name","1");
                    var upLoadUrl="${ctx}/sys/database/import";
                    var modelUrl="${ctx}/sys/database/import/template";
                    $(".FileImport").css("top",topHeight);
                    $(".FileImport").FileImport({"uploadUrl":upLoadUrl,"modelUrl":modelUrl});
                }else{
                    if($(".FileImport").attr("name")=="0"){
                        var upLoadUrl="${ctx}/sys/database/import";
                        var modelUrl="${ctx}/sys/database/import/template";
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
            $("#attention").on("click",function(){
                if($(".attention-info").hasClass("hidden")){
                    $(".attention-info").removeClass("hidden");
                    $(".attention-info").addClass("show");
                    setTimeout(function(){
                        $(".attention-info").removeClass("show");
                        $(".attention-info").addClass("hidden");
                    },10000)
                }else{
                    $(".attention-info").removeClass("show");
                    $(".attention-info").addClass("hidden");
                }
            })
            setTimeout(function(){
                $(".attention-info").removeClass("show");
                $(".attention-info").addClass("hidden");
            },10000);
		});
	</script>
</head>
<body>
    <div class="top_position">
    <div class="top_position_lab"><spring:message code="position"/>:</div>
    <ul>
    <li><spring:message code ='database.manage'/></li>
    <li>></li>
    <li><spring:message code="database.BackUpRestore"/></li>
    <li>></li>
    <li><a href="${ctx}/sys/database"><spring:message code='database.list' /></a></li></ul></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/database/"><spring:message code='database.list' /></a></li>
	</ul>
	<div class="hide appendHtml"></div>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/database/import" method="post" enctype="multipart/form-data"
			  style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading(accipiter.getLang('loading'));"><br/>
			<div class="uplodFile">
				<span id="FlieName"><spring:message code='click.upload'/></span>
				<input id="uploadFile" name="file" type="file"/>
				<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="<spring:message code='import.operators'/>" />
			</div>
			<div class="down-model">
				<a href="${ctx}/sys/database/import/template"><spring:message code='download.operators.template' /></a>
			</div>
		</form>
	</div>
	<div class="FileImport" name="0"></div>
	<form:form id="searchForm" modelAttribute="dataBaseRecord" action="${ctx}/sys/database/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<div class="query-item">
			    <label><spring:message code='database.recordName' />：</label><form:input path="recordName" type="text" maxlength="50" class="input-small"/>
			</div>
			<div class="query-item">
				<label><spring:message code='start.date' />：</label><input id="createDateStart" name="createDateStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${createDateStart}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'createDateEnd\')}'});"/>
			</div>
			<div class="query-item">
				<label><spring:message code='end.date' />：</label><input id="createDateEnd" name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${createDateEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'createDateStart\')}'});"/>
			</div>
			<div class="query-item">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"/>
			</div>
			<div class="query-item">
				<input id="runJobNow" class="btn btn-primary" type="button" value="<spring:message code='runJobNow' />"/>
			</div>
			<div class="query-item">
				<shiro:hasPermission name="sys:database:import">
					<input id="btnImport" class="btn btn-primary" name="0" type="button" value="<spring:message code='import' />"/>
					<form:hidden path="uploadMessage"/>
				</shiro:hasPermission>
			</div>
			<a id="attention"></a>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
		<div class="attention-info show"><p><spring:message code="database.backup.detail"/>${realPath}</p></div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
				<th><spring:message code='database.recordName' /></th>
				<th><spring:message code='database.recordPath' /></th>
				<th><spring:message code='create.date' /></th>
				<shiro:hasPermission name="sys:database:edit">
					<th><spring:message code='operation' /></th>
				</shiro:hasPermission></thead>
		
		<tbody>
		<c:forEach items="${page.list}" var="database">
			<tr>
			 	<td>${database.recordName}</td>
				<td><a href="${database.recordPath}">${database.recordPath}</a></td>
				<td><fmt:formatDate value="${database.createDate}" type="both"/></td>
				<shiro:hasPermission name="sys:database:edit">
					<td>
						 <a href="${database.recordPath}"><spring:message code='database.backup' /></a>
						 <a href="${ctx}/sys/database/restore?id=${database.id}" onclick="return confirmx('<spring:message code='database.restore' />', this.href)"><spring:message code='database.restore' /></a>
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