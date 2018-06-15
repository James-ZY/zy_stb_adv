<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 套餐时段分析 -->
<head>
	<title><spring:message code='' /><spring:message code='combo.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctx}/static/Highcharts-5.0.0/js/highcharts.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/data.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/exporting.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/modules/drilldown.js"></script>
    <script src="${ctx}/static/Highcharts-5.0.0/js/highcharts-more.js"></script>
    <script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
	    var roleName = ""; //add by pengr for close  归属机构  tree 
		function page(n,s){  
			var rule=$("#searchForm").valid();
			if(rule){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#searchForm").attr("action","${ctx}/adv/sell/getAdComboSellCount");
				$("#searchForm").submit();
			}else{
		    	return false;
			}
	    }
	</script>
	<style type="text/css">
	.chartContent{height:500px;background-color: #f5f5f5;padding: 8px 15px;margin: 20px 20px;    position: relative;display:none}
	#chart{width:900px;height:450px;position: absolute;left: 50%;top: 50%;margin-left:-450px;margin-top:-225px;}
	#switchover{margin-left:15px;background-color: #3B95C8;background-image: none;}
	#switchover:hover {background-color: #3EAFE0;}
	</style>
</head>
<body>
<!-- 套餐销售统计 -->
	<div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
	    <li><spring:message code='adcombo.statistic' /></li>
	    <li>></li>
	    <li><spring:message code='combo.sell.count1' /></li>
	    <li>></li>
	 
	    <li>
	    	<a href="${ctx}/adv/sell/getAdComboSellCount"><spring:message code='combo.sell.analyze'/></a> 
	     </li>
	     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"> <a href="${ctx}/adv/sell/getAdComboSellCount"><spring:message code='combo.sell.analyze' /></a> </li>
	    
	</ul>
	
	
	<form:form id="searchForm" modelAttribute="adComboSellCountDto" action="${ctx}/adv/sell/getAdComboSellCount" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div>
			<div>
				 <div class="query-item">
					<label><spring:message code='adv.type' />：</label>
					<form:select path="adType.id" class="required" id="adType.id">
							<option value=""><spring:message code="userform.select"/></option>
							<form:options items="${fns:getAdTypeList()}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				 </div>
				 <div class="query-item">
				 	<label><spring:message code='sell.date' />：</label><input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
					value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'endDate\',{d:-30})||\'2016-12-01\'}',maxDate:'#F{$dp.$D(\'endDate\',{d:0})||\'2025-12-31\'}'});"/>
				 </div>
				 <div class="query-item">
				 	<spring:message code='to' />&nbsp;&nbsp; <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
					value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startDate\',{d:0})||\'2016-12-01\'}',maxDate:'#F{$dp.$D(\'startDate\',{d:30})||\'2025-12-31\'}'});"/>
				 </div> 
				 <div class="query-item">
					<label><spring:message code='sell.advertiser' />：</label>
					<form:select path="advertiser.id">
						<option value=""><spring:message code="userform.select"/></option>
						<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				    </form:select>	 
				 </div>
				 <div class="query-item">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
				 </div>
			</div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
    <div class="chartContent">
	<div id="chart"></div>
	</div>
 	<p style="display:none;" id="id_list">${sellCount}</p>
 	<p style="display:none;" id="start_date">${beginDate}</p>
 	<p style="display:none;" id="end_date">${endDate}</p>
 	<p style="display:none;" id="typeName">${tyeName}</p>
 	 <script>
 	window.onload=function(){ 
 	  	  $(function(){
 	  		    if($("#id_list").text()!=""){
 	 		    		$(".tab_content").css({"display":"none"});
 	  		    		$(".pagination").css({"display":"none"});
 	  		    		$(".chartContent").css({"display":"block"});
 	  		    		BulidChart(time,getData());
 	  		    }else{
 	 		    		$(".tab_content").css({"display":"block"});
 	  		    		$(".pagination").css({"display":"block"});
 	  		    		$(".chartContent").css({"display":"none"});
 	  		    }
    			var time = new Array();
 	  	    	var defaultData=[{"name":accipiter.getLang_(messageLang,"combo.sell.count"),data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}];
 	  	    	var title="";
 	  	    	function getData(){
 	  		    	var detailData=$.parseJSON($("#id_list").text());
 	  		    	title="${tyeName}"+accipiter.getLang_(messageLang,"combo.sell.analyze")+"("+$("#start_date").text()+"-"+$("#end_date").text()+")";
 	  	    		if(detailData==null){
 	  	    			return;
 	  	    		}else{
 	  	    			return detailData;
 	  	    		}
 	  	    	}
 	   	    	function BulidChart(time,data){
 	   	    	    time = new Array();
 	  	    		var countData=[{"name":accipiter.getLang_(messageLang,"combo.sell.count"),data:[]}];
 	  	    		if(data==null){
 	  	    			return;
 	  	    		}
 	  	    		if(data.length==0){
 	  	    			countData=defaultData;
 	  	    		}else{
 	  	    			var len=data.length;
 	  	    			for(var i=0;i<len;i++){
 	  	    				countData[0].data.push(data[i].sellCount);
 	  	    				time.push(data[i].sellDate);
 	  	    			}
 	  	    		}
 	  	            // Create the chart
 	  	            $('#chart').highcharts({
 	  	                credits: {
 	  	                    enabled: false
 	  	                },
 	  	                chart: {
 	  	                    type: 'line'
 	  	                },
 	  	                title: {
 	  	                    text: title
 	  	                },
 	  	                xAxis: {
 	  	                    type: 'category',
 	  	                    labels:{
 	  	                    	rotation:90
 	  	                    },
 	  	                    categories:time
 	  	                    
 	  	                },
 	  	                yAxis:{
 	  	                	title:{
 	  	                    	text:accipiter.getLang_(messageLang,"combo.sell.count")
 	  	                	}
 	  	                },
 	  	                legend: {
 	  	                    enabled: false
 	  	                },
 	  	                plotOptions: {
 	  	                	series:{
 	  	                		cursor:"pointer",
 	  	                        events: {  
 	  	                            click: function(event) {  
 	  	                                console.log(event.point.category);  
 	  	                            }  
 	  	                        }
 	  	                	}
 	  	                },
 	  	                series: countData
 	  	            });
 	  	    	} 

 	  	  })
 		
 		}  
 	</script>
 
</body>
</html>

 
