<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true" %>
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
	<script src="${ctx}/static/Highcharts-5.0.0/js/highcharts.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/data.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/exporting.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/drilldown.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/highcharts-more.js"></script>
    <script src="${ctxStatic}/common/language.js"></script>
	<script combo="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		 
		function page(n,s){  
			var rule=$("#searchForm").valid();
			if(rule){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#searchForm").attr("action","${ctx}/adv/combo/comboOperation");
				$("#searchForm").submit();
			}else{
		    	return false;
			}
	    }
	</script>
	<style type="text/css">
	.chartContent{height:500px;background-color: #f5f5f5;padding: 8px 15px;margin: 20px 20px;    position: relative;display:none;}
	#chart{width:900px;height:450px;position: absolute;left: 50%;top: 50%;margin-left:-450px;margin-top:-225px;}
	#switchover{margin-left:15px;background-color: #3B95C8;background-image: none;}
	#switchover:hover {background-color: #3EAFE0;}
	#attention{width:24px;height:24px;display:block;float:right;background:url("${ctxStatic}/images/icon/attention.png") no-repeat;}
	#attention:hover{cursor: pointer;}
    .attention-info{padding: 10px 0;margin-bottom: 10px;border: 1px solid #FFCE42;border-radius: 4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;background-color: #DFF0D8;}
	.attention-info p{text-indent: 25px;margin-bottom:0;}
	.hidden{display:none};
	.show{display:block}
	</style>
</head>
<body>
<!-- 套餐销售统计一级页面 -->
 <div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adcombo.statistic' /></li>
    <li>></li>
    <li><spring:message code='combo.sell.statistics' /></li>
    <li>></li>
    <li>
    <li>
    	<a href="${ctx}/adv/combo/comboOperation"><spring:message code='combo.operation.statistics' /></a> 
     </li>
     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/combo/comboOperation"><spring:message code='combo.operation.statistics' /></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adCombo" action="${ctx}/adv/combo/comboOperation" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
			<div class="query-item">
				<label><spring:message code='combo.name' />：</label><form:input path="comboName" htmlEscape="false" maxlength="50" class="input-small"/>		
			</div>
			<div class="query-item">
				<label><spring:message code='adv.type' />：</label>
				<form:select path="adType.id" class="required">
						<option value=""><spring:message code="userform.select"/></option>
						<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>		
			</div>
			<div class="query-item">
				<label><spring:message code='start.date' />：</label><input id="validStartTime" name="validStartTime" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
				value="${startDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'validEndTime\',{d:0})}',minDate:'#F{$dp.$D(\'validEndTime\',{d:-365})}'})"/>		
			</div>
			<div class="query-item">
				<label><spring:message code='end.date' />：</label><input id="validEndTime" name="validEndTime" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
				value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'validStartTime\',{d:0})||\'%y-%M-#{%d+1}\'}',maxDate:'#F{$dp.$D(\'validStartTime\',{d:365})||\'%y-%M-#{%d+366}\'}'})"/>
			</div>
			<div class="query-item">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div> 
				<a id="attention"></a>
			</div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div class="tab_content">
<%-- 	<c:if test="${!empty page.list}">
		<p class="attention-info hidden"><spring:message code="combo.date.detail"/></p>
	</c:if> --%>
	<div class="attention-info show"><p><spring:message code="combo.date.detail"/></p></div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th><spring:message code='combo.name' /></th>
		<th><spring:message code='adv.type'/></th>
		<th><spring:message code='combo.startdate'/></th>
		<th><spring:message code='combo.enddate'/></th>
		<th><spring:message code='combo.valid.time'/></th>
		<th><spring:message code='combo.sold.time'/></th>
 		<th><spring:message code="combo.time.scale"/></th>
		<th><spring:message code='detail' /></th>
 
		</tr></thead>
		<tbody> 
		
		<%-- 		<input style="display: none;" type="text" id="id_list" name="swfID" value="${json_list}"/> --%>
				<p style="display: none;" id="id_list">${json_list}</p>
				<c:forEach items="${page.list}" var="adCombo">
					<tr>
					
						<td>${adCombo.comboName}</td>
						<c:choose>
							<c:when test="${!empty adCombo.adType }">
									<td>${adCombo.adType.typeName}</td>
							</c:when>
							<c:otherwise>
									<td></td>
							</c:otherwise>
						</c:choose>
						<td>
						<c:choose>
							<c:when test="${!empty adCombo.validStartTime}">
							<fmt:formatDate value="${adCombo.validStartTime}" pattern="yyyy-MM-dd"/> 
							</c:when>
							<c:otherwise>
							<fmt:formatDate value="${adCombo.queryStartDate}" pattern="yyyy-MM-dd"/>
							</c:otherwise>
						</c:choose>
						</td>
						
						<td>
						<c:choose>
							<c:when test="${!empty adCombo.validEndTime}">
							<fmt:formatDate value="${adCombo.validEndTime}" pattern="yyyy-MM-dd"/> 
							</c:when>
							<c:otherwise>
							<fmt:formatDate value="${adCombo.queryEndDate}" pattern="yyyy-MM-dd"/>
							</c:otherwise>
						</c:choose>
						</td>
					 
						<td>${adCombo.validDay}<spring:message code='day'/></td>
						<td>${adCombo.sellDay}<spring:message code='day'/></td>
						<td>${adCombo.timeScale}</td>
						
						<td> <a href="${ctx}/adv/combo/comboOperationFrom?id=${adCombo.id}&startDate=${adCombo.queryStartDate}&endDate=${adCombo.queryEndDate}&sellDay=${adCombo.sellDay}"><spring:message code='detail' /></a></td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
	<div class="chartContent">
	<div id="chart"></div>
	</div>
	<script>
	$(function(){
	    /*图表切换*/
	    $("#switchover").on("click",function(){
	    	if($(this).hasClass("switchover")){
	    		$(this).removeClass("switchover");
	    		$("#btnSubmit").attr({"disabled":true});
	    		$(".tab_content").css({"display":"none"});
	    		$(".pagination").css({"display":"none"});
	    		$(".chartContent").css({"display":"block"});
	    		return false;
	    	}else{
	    		$(this).addClass("switchover");
	    		$("#btnSubmit").attr({"disabled":false});
	    		$(".tab_content").css({"display":"block"});
	    		$(".pagination").css({"display":"block"});
	    		$(".chartContent").css({"display":"none"});
	    		return false;
	    	}
	    });
	    function getData(){
	    	var detailData=$.parseJSON($("#id_list").text());
	    	if(detailData==null){
	    		return;
	    	}else{
		    	console.log(detailData);
			    /*广告商在单个广告类型的所有套餐中的投放量占比*/
			    var len=detailData.length;
			    var title=accipiter.getLang_(messageLang,"adv.adtype")+detailData[0].typeName+accipiter.getLang_(messageLang,"adv.sellInfo");
			    var dataList=[];
			    var Data=[{"title":title,"dataList":dataList}];
			    for(var i=0;i<len ;i++){
				    dataList.push([detailData[i].comboName,parseFloat(detailData[i].timeScale)]);
			    	
			    }
			    return Data;
	    	}
	    	
	    }
    	function BulidChart(data){
    		if(data==null){
    			return;
    		}else{
                // Create the chart
                $('#chart').highcharts({
                	/*去除默然链接  */
                    credits: {
                        enabled: false
                    },
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: data[0].title
                    },
                    xAxis: {
                        labels:{
                        	rotation:45
                        },
                        type: 'category'
                    },
                    yAxis:{
                    	title:{
                        	text:accipiter.getLang_(messageLang,"adv.Scale")
                    	}
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                    	column:{
                    		pointWidth:25
                    	},
                        series: {
                            borderWidth: 0,
                            dataLabels: {
                                enabled: true
                            }
                        }
                    },
                    series: [{
                        name: accipiter.getLang_(messageLang,"adv.putScale"),
                        colorByPoint: true,
                        data:data[0].dataList
                    }]
                });
    		}
    	}
    	BulidChart(getData());
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
	})
	
	</script>
    
<%-- 	<script src="${ctx}/static/adv/Chart-statistics.js"></script> --%>
</body>
</html>

 
