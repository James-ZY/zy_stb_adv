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
	<script src="${ctxStatic}/common/language.js"></script>
    <script src="${ctx}/static/echart/echarts.common.min.js"></script>
	<script src="${ctx}/static/echart/customEcharts.js"></script>
	<script src="${ctx}/static/layer/layer.js"></script>
	<script src="${ctx}/static/adv/advClickRecordChart.js"></script>
	<script combo="text/javascript">

		createChart('','');

		function refresh() {
			$("#box1").html('');
			$("#box2").html('');
			createChart($("#playStartDate").val(),$("#playEndDate").val());
		}

		function createChart(startDate,endDate) {
			var index = layer.load(1, {
				shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			$.ajax({
				url:"${ctx}/adv/adStatistic/clickChartData?startDate="+startDate+"&endDate="+endDate,
				success:function(result){
					parseData(result,index);
				}});
		}
		/**
		 * 类型统计点击事件
		 * @param param
		 */
		function getElementDetail(param) {
			layer.open({
				type: 2,
				title: option.detail_layer,
				area: ['1500px', '600px'], //宽高
				shadeClose:true,
				content: '${ctx}/adv/adStatistic/advClickChartDetail?advId='+param.data.id+'&startDate='+$("#playStartDate").val()+'&endDate='+$("#playEndDate").val(),
				success: function(layero, index){
					//清空frame nav及搜索框
					//top.window.frames["mainFrame"].frames[0].trimLayout();
				}
			});



		}
	</script>
	<style type="text/css">
	.chartContent{height:500px;background-color: #f5f5f5;padding: 8px 15px;margin: 20px 20px;    position: relative;display:none;}
	#chart{width:900px;height:450px;position: absolute;left: 50%;top: 50%;margin-left:-450px;margin-top:-225px;}
	#switchover{margin-left:15px;background-color: #3B95C8;background-image: none;}
	#switchover:hover {background-color: #3EAFE0;}
	#contentTable tbody td a{    display: inline-block; width: 100%;}
	.error_code{margin-left: 20px;text-decoration:none;}
	.detail_frame{width: 1100px;height: 600px;}
	</style>
</head>
<body>
<!-- 广告点击统计一级页面 -->
      <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
	    <li><spring:message code='adv.chart' /></li>
	    <li>></li>
	    <li>
	    	<a href="${ctx}/adv/adStatistic/advClickRecordChart"><spring:message code='adv.click.chart'/></a>
	     </li>
	     </ul>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/adv/adStatistic/advClickRecordChart"><spring:message code='adv.click.chart' /></a></li>
	    
	</ul>
	<form:form id="searchForm" modelAttribute="adStatistic" class="breadcrumb form-search">
		<div>
			<div>
				 <div class="query-item">
				 	<label><spring:message code='adv.play.startdate' />：</label><input id="playStartDate" name="playStartDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${playStartDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				 </div>
				 <div class="query-item">
				 	<label><spring:message code='adv.play.enddate' />：</label><input id="playEndDate" name="playEndDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${playEndDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				 </div>
				 <div class="query-item">
				    <input id="btnSubmit" class="btn btn-primary" type="button" value="<spring:message code='query' />" onclick="return refresh();"/>
				 </div>
			</div>
		</div> 
	</form:form>
	<tags:message content="${message}"/>
	<div style="width: 100%;display: -webkit-flex;display:flex;flex-wrap: wrap;flex-direction: column">
	<div id="box1" style="width: 80%;height:330px;display: -webkit-flex;display:flex;flex-wrap: wrap;">
	</div>
	<br/>
	<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" margin='0' width="100%" color=#b7d5df SIZE=2><br/>
	<div id="box2" style="width: 100%;display: -webkit-flex;display:flex;flex-wrap: wrap;">
	</div>
	</div>
</body>
<script type="text/javascript">

</script>
</html>

 
