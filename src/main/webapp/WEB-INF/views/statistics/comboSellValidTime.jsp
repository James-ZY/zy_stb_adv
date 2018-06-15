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
	<script src="${ctx}/static/layer/layer.js"></script>
	<script src="${ctx}/static/echart/echarts.common.min.js"></script>
	<script src="${ctx}/static/echart/customEcharts.js"></script>
	<script src="${ctxStatic}/common/language.js"></script>
	<script type="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree
		function page(n,s){
			var rule=$("#searchForm").valid();
			if(rule){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#sellSearchForm").attr("action","${ctx}/adv/sell/comboSellValidTime");
				$("#sellSearchForm").submit();
			}else{
				return false;
			}
		}
	</script>
	<style type="text/css">
		.chartContent{width: 100%;display: -webkit-flex;display:flex;flex-wrap: wrap;flex-direction: column;}
		#chart{width: 100%;height:620px;display: -webkit-flex;display:flex;flex-wrap: wrap;}
		.error_code{margin-left: 20px;text-decoration:none;}
		#switchover{margin-left:15px;background-color: #3B95C8;background-image: none;}
		#switchover:hover {background-color: #3EAFE0;}
	</style>
</head>
<body>
<!-- 套餐销售统计 -->
<div class="top_position">
	<div class="top_position_lab"><spring:message code='combo.position' /></div>
	<ul>
		<li><spring:message code='adv.chart' /></li>
		<li>></li>
		<li>
			<a href="${ctx}/adv/sell/comboSellStats"><spring:message code='adv.sellTime.chart'/></a>
		</li>
	</ul>
</div>
<ul class="nav nav-tabs">
	<li class="active"> <a href="${ctx}/adv/sell/comboSellStats"><spring:message code='adv.sellTime.chart' /></a> </li>

</ul>

<form:form id="sellSearchForm" modelAttribute="adSell" action="${ctx}/adv/sell/comboSellValidTime" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
	<form:hidden path="adCombo.adType.id"/>
	<form:hidden path="history"/>
	<div>
		<div>
			<div class="query-item">
				<label><spring:message code='sell.valid.time' />：</label><input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
																		  value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
			<div class="query-item">
				<spring:message code='to' />&nbsp;&nbsp; <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
																value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
			<div class="query-item">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />" onclick="return page();"/>
			</div>
		</div>
	</div>
</form:form>
<tags:message content="${message}"/>
<div class="chartContent">
	<div id="chart">
	</div>
</div>
<p style="display:none;" id="rate_chart">${rateChartJson}</p>
<p style="display:none;" id="start_date">${beginDate}</p>
<p style="display:none;" id="end_date">${endDate}</p>
<script>
	window.onload=function(){
		$(function(){
			if($("#rate_chart").text()!=""){
				parseData();
			}
			function parseData(){
				var index = layer.load(1, {
					shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				var chart_data=$.parseJSON($("#rate_chart").text());
				var series = [];
				var type_names = [];
				var sells_time_datas = [];
				var valid_time_datas = [];
				for(var i = 0;i<chart_data.length;i++){
					type_names.push(chart_data[i].typeName);
					var sell_data_item = {};
					sell_data_item.value = chart_data[i].sellTime;
					sell_data_item.typeId= chart_data[i].typeId;
					sell_data_item.percent= chart_data[i].percent;
					sell_data_item.cat = 'sell';
					sells_time_datas.push(sell_data_item);
					var valid_data_item = {};
					valid_data_item.value = chart_data[i].validTime;
					valid_data_item.typeId= chart_data[i].typeId;
					valid_data_item.percent= chart_data[i].percent;
					valid_data_item.cat = 'valid';
					valid_time_datas.push(valid_data_item);
				}
				var item1 = {};
				item1.type = 'bar';
				item1.data = valid_time_datas;
				item1.name = accipiter.getLang_(messageLang,"combo.valid.time");
				series.push(item1);
				var item2 = {};
				item2.type = 'bar';
				item2.data = sells_time_datas;
				item2.name = accipiter.getLang_(messageLang,"combo.sell.time");
				item2.label={normal: {show: true,position: 'top',formatter:function(params){return ((params.data.percent)*100).toFixed(2)+'%'}}};
				series.push(item2);
				var legend = [accipiter.getLang_(messageLang,"combo.valid.time"),accipiter.getLang_(messageLang,"combo.sell.time")];
				addCustomToolTipBarChart(
						accipiter.getLang_(messageLang,"combo.sell.valid.time.analyze"),"",legend,type_names,series,accipiter.getLang_(messageLang,"adv.adtype"),accipiter.getLang_(messageLang,"time.hour"),"chart",callback);
				if(chart_data.length==0){
					$("#chart").html('<a class = "error_code">'+accipiter.getLang_(lang,"noData")+'</a>');
				}
				setTimeout(function () {
					layer.close(index);
				},100);
			}

			/**
			 * 类型统计点击事件
			 * @param param
			 */
			function callback(params) {
				var url = '';
				var title = '';
				if(params.data.cat=='sell'){
					url = '${ctx}/adv/sell/comboSellStatsDetail?typeId='+params.data.typeId+'&startDate='+$("#startDate").val()+'&endDate='+$("#endDate").val();
					title = accipiter.getLang_(messageLang,"adv.sellInfo");
				}else{
					url = '${ctx}/adv/sell/comboSellValidTimeDetail?typeId='+params.data.typeId+'&startDate='+$("#startDate").val()+'&endDate='+$("#endDate").val();
					title = accipiter.getLang_(messageLang,"adv.type");
				}
				layer.open({
					type: 2,
					title: title,
					area: ['1500px', '600px'], //宽高
					shadeClose:true,
					content: url,
					success: function(layero, index){
					}
				});

			}
		})

	}
</script>

</body>
</html>

 
