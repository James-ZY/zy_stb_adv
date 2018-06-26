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
				$("#searchForm").attr("action","${ctx}/adv/sell/comboSellStats");
				$("#searchForm").submit();
			}else{
				return false;
			}
		}
	</script>
	<style type="text/css">
		.chartContent{width: 100%;display: -webkit-flex;display:flex;flex-wrap: wrap;flex-direction: column;}
		#chart{width: 100%;height:290px;display: -webkit-flex;display:flex;flex-wrap: wrap;}
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
			<a href="${ctx}/adv/sell/comboSellStats"><spring:message code='adv.comboSell.chart'/></a>
		</li>
	</ul>
</div>
<ul class="nav nav-tabs">
	<li class="active"> <a href="${ctx}/adv/sell/comboSellStats"><spring:message code='adv.comboSell.chart' /></a> </li>

</ul>


<form:form id="searchForm" modelAttribute="adSell" action="${ctx}/adv/sell/comboSellStats" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
	<div>
		<div>
			<div class="query-item">
				<label><spring:message code='sell.date' />：</label><input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="required input-small Wdate"
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
	<p style="font-size:  24px;text-align:  center;font-weight:  bold;"><spring:message code='advtiser.rate.analyze' /></p>
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
				console.log(chart_data);
				for(var i = 0;i<chart_data.length;i++){
					if($("#child"+i).length<=0){
						$("#chart").append('<div id=\"child'+i+'\" style=\"width: 400px;height:290px;padding-bottom: 10px;\"></div>');
					}
					var child_types = new Array();
					var child_datas = new Array();
					for(var j=0;j<chart_data[i].list.length;j++){
						child_types.push(chart_data[i].type);
						var child_obj = new Object();
						child_obj.name = chart_data[i].list[j].advName;
						child_obj.value = chart_data[i].list[j].count;
						child_obj.adTypeId = chart_data[i].list[j].adTypeId;
						child_obj.advId = chart_data[i].list[j].advId;
						child_datas.push(child_obj);
					}
					addPieChart(chart_data[i].type,accipiter.getLang_(messageLang,"unit.day"),
							child_types,accipiter.getLang_(messageLang,"adv.type"),child_datas,"child"+i,callback);
				}
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
			function callback(param) {
				layer.open({
					type: 2,
					title: accipiter.getLang_(messageLang,"adv.sellInfo"),
					area: ['1500px', '600px'], //宽高
                    shadeClose:true,
					content: '${ctx}/adv/sell/comboSellStatsDetail?typeId='+param.data.adTypeId+'&advId='+param.data.advId+'&startDate='+$("#startDate").val()+'&endDate='+$("#endDate").val(),
					success: function(layero, index){
						//清空frame nav及搜索框
						//top.window.frames["mainFrame"].frames[0].trimLayout();
					}
				});
			}
		})

	}
</script>

</body>
</html>

 
