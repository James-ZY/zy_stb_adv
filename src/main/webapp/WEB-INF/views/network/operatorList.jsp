<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='' /><spring:message code='user.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
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
	</style>
    <link rel="stylesheet" href="${ctx}/static/fileUpLoad/fileUpLoadcss/skinBase.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
 	<script src="${ctxStatic}/dropzone/dropzone.min.js"></script>
 	<script src="${ctx}/static/fileUpLoad/js/uploadFile.js"></script>
 	  <script src="${ctxStatic}/layer/layer.js"></script>
    	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
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
				var info = accipiter.getLang("export.adopertator.Data");
				 
				var systemPrompt =accipiter.getLang("systemPrompt");
				 
				top.$.jBox.confirm(info,systemPrompt,function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/adv/operators/export");
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
		    $("#upload_file").on("click",function(){
		    })
		    function getY(e){
				var e = event || window.event;
				return e.pageY ||e.clientY+document.body.scrollTop;
		    }
		    $("#btnImport").click(function(){
		    	var topHeight=getY($(this))+20;
		    	if($(this).attr("name")=="0"){
		    		$(this).attr("name","1");
		    		$(".FileImport").attr("name","1");
				    var upLoadUrl="${ctx}/adv/operators/import";
				    var modelUrl="${ctx}/adv/operators/import/template";
				    $(".FileImport").css("top",topHeight);
				    $(".FileImport").FileImport({"uploadUrl":upLoadUrl,"modelUrl":modelUrl});
		    	}else{
		    		if($(".FileImport").attr("name")=="0"){
					    var upLoadUrl="${ctx}/adv/operators/import";
					    var modelUrl="${ctx}/adv/operators/import/template";
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
		    //全选/全不选
		    $("#checkAll").click(function(){
		    		if(this.checked){   
		    			$("input[name='check']").attr("checked", true);  
		    	    }else{   
		    	    	$("input[name='check']").attr("checked", false);
		    	    }   
		    });
		    //单个点击事件
		    $("input[name='check']").click(function(){
		    var length = $("input[name='check']").length;
		    var checkLen = $("input[name='check']:checkbox:checked").length;
		    if(checkLen == length ){
		    	 $("#checkAll").attr("checked", true);  
		    }else{
		    	 $("#checkAll").attr("checked", false);  
		    }
		    });
	
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/operators");
			$("#searchForm").submit();
	    	return false;
	    }
		
		function checkDelete(){		  
			var aa="";
		    $("input[name='check']:checkbox:checked").each(function(){ 
		    	aa+=($(this).val()+","); 
		    });
		    if(aa==""){
		    	var messageInfo=accipiter.getLang_(messageLang,"operator.notNull");
		    	prompt(messageInfo);		    	
	    		return false;
		    }else{
		    	var info = accipiter.getLang_(messageLang,"delete_checked_operators");
				 
				var systemPrompt =accipiter.getLang("systemPrompt");
				 
				top.$.jBox.confirm(info,systemPrompt,function(v,h,f){
					if(v=="ok"){

				    	var data={"ids":aa.substring(0, aa.length-1)};
					    var postData = JSON.stringify(data);
				    	$.ajax({
				    		type:"post",
							async:false,
							data:postData,
				             url:accipiter.getRootPath()+"/adv/operators/delete_checked_operators",
				             contentType:"application/json; charset=UTF-8",
								dataType:"json",
				             success:function(data){
				            	 var messageInfo=data.msg;
				 		    	layer.config({
				 			        content: '\<\div style="padding:20px 20px;text-align: center;"><i></i><span>'+messageInfo+'</span>\<\/div>',
				 			     });
				 	            layer.open({
				 	                yes:function (index, layero) {
				 	                    layer.close(index);
						            	 page();
				 	                },
				 	                btn2:function (index, layero) {
				 	                    layer.close(index);
						            	 page();
				 	                }
				 	            });
				 		    	return false;
				             }
				    });
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
				
		    }		    
		};
	</script>
</head>
<body>
	<div class="top_position">
		<div class="top_position_lab"><spring:message code="position"/>:</div>
		<ul>
 
		<li><spring:message code="operators"/></li>
		<li>></li>
		<li><spring:message code="operayors.manage"/></li>
		<li>></li>
		<li>
		<a href="${ctx}/adv/operators"><spring:message code='operators' /><spring:message code='list' /></a>
		</li>
		</ul>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/operators"><spring:message code='operators' /><spring:message code="list"/></a></li>
		<shiro:hasPermission name="sys:operators:edit"><li><a href="${ctx}/adv/operators/form"><spring:message code='operators.add'/></a></li></shiro:hasPermission>
	</ul>
	<div class="hide appendHtml"></div>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/adv/operators/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading(accipiter.getLang('loading'));"><br/>
			<div class="uplodFile">
			    <span id="FlieName"><spring:message code='click.upload'/></span>
			      <input id="uploadFile" name="file" type="file"/>
			      <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="<spring:message code='import.operators'/>" />
			</div>
			<div class="down-model">
				<a href="${ctx}/adv/operators/import/template"><spring:message code='download.operators.template' /></a>
			</div>
		</form>
	</div>
	<div class="FileImport" name="0"></div>
	<form:form id="searchForm" modelAttribute="adOperators" action="${ctx}/adv/operators/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div class="query-item">
			   <label><spring:message code='operators.id' /> ：</label><form:input path="operatorsId" htmlEscape="false" maxlength="50" class="input-small"/>
			</div> 
			<div class="query-item">
				<label><spring:message code='operators.name' />：</label><form:input path="operatorsName" htmlEscape="false" maxlength="50" class="input-small"/>
			</div> 
			<div class="query-item">
				<label><spring:message code='adNetwork' />：</label>
				<form:select path="adNetworkId">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${ fns:findAdNetworkAll()}" itemLabel="networkName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div> 
			<div class="query-item">
	           <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div> 
			<div class="query-item">
	           <input id="btnDelete" class="btn btn-primary" type="button" value="<spring:message code='delete' />" onclick="return checkDelete();"/>
			</div> 
			<div class="query-item">
	           	<shiro:hasPermission name="sys:operators:export">
				<input id="btnExport" class="btn btn-primary" type="button" value="<spring:message code='export' />"/>
				</shiro:hasPermission>
			</div> 
			<div class="query-item">
				<shiro:hasPermission name="sys:operators:import">
				<input id="btnImport" class="btn btn-primary" name="0" type="button" value="<spring:message code='import' />"/>
				<form:hidden path="uploadMessage"/>
				</shiro:hasPermission>
			</div>
		</div> 
	</form:form>
 
	<tags:message content="${message}"/>
	<div class="tab_content">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><input id="checkAll" type="checkbox" class="option" style="margin-left: -8px"></th>
		<th><spring:message code='operators.id' /></th>
		<th><spring:message code='operators.name' /></th>
		<th><spring:message code='operators.password' /></th>
		<th><spring:message code='operators.service.area' /></th>
		<th><spring:message code='network.name' /></th>
		<th><spring:message code='operators.contact' /></th>
		<th><spring:message code='operators.telphone' /></th>
		<shiro:hasPermission name="sys:operators:edit">
		<th><spring:message code='operation' /></th>
		</shiro:hasPermission> 
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="adOperators">
			<tr>
			    <td><input name="check" type="checkbox" class="option" value="${adOperators.id}"></td>
				<td><a href="${ctx}/adv/operators/form?id=${adOperators.id}">${adOperators.operatorsId}</a></td>
				<td>${adOperators.operatorsName}</td>
				<td>${adOperators.password}</td>
				<td>${adOperators.area}</td>
<%-- 				<c:if test="${!empty adOperators.network}">
						<td>${adOperators.network.networkName}</td>
				</c:if>
				<c:if test="${empty adOperators.network}">
						<td></td>
				</c:if> --%>
				<td>${adOperators.networkName}</td>
				<td>${adOperators.contact}</td>
				<td>${adOperators.telphone}</td>
				<shiro:hasPermission name="sys:operators:edit">
					<td>
						 <a href="${ctx}/adv/operators/form?id=${adOperators.id}"><spring:message code='update' /></a>
						 <a href="${ctx}/adv/operators/delete?id=${adOperators.id}" onclick="return confirmx('<spring:message code='confirm.delete' />', this.href)"><spring:message code='delete' /></a>					 
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