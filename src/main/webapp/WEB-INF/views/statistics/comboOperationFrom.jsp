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
    <script src="${ctx}/static/echart/echarts.js"></script>
    	<script src="${ctxStatic}/common/language.js"></script>
	<script combo="text/javascript">
		var roleName = ""; //add by pengr for close  归属机构  tree 
		 
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/adv/combo");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
	<style type="text/css">
	html,body{height:100%;overflow: hidden;}
	.mapContent{width:100%;height:100%;position: relative;}
    .chartComment{width:900px;height:500px;margin: 0 auto;border: 1px solid gainsboro;
    left: 50%;
    top: 50%;margin-left:-450px;margin-top:-300px;position: absolute;}
    #Echart{width:100%;height:100%}
    .detailData{display:none}
    .mapContent .btn{position: absolute;bottom:-60px;right:50%;display:none}
	</style>
</head>
<body>
<!-- 套餐销售统计详细页面 -->
<div class="top_position">
    <div class="top_position_lab"><spring:message code='combo.position' /></div>
    <ul>
    <li><spring:message code='adcombo.statistic' /></li>
    <li>></li>
    <li><spring:message code='combo.sell.statistics' /></li>
    <li>></li>
    <li>
    <li>
    	<spring:message code='combo.operation.detail' />
     </li>
     </ul>
    </div>
 	<p class="detailData">${detailData}</p>
	<ul class="nav nav-tabs">
		<li><a onclick="history.go(-1)"><spring:message code='combo.operation.statistics' /></a></li>
	    <li class="active" ><a><spring:message code='combo.operation.detail'/></a></li>
	</ul>
	<div class="mapContent">
     <div class="chartComment">
     <div id="Echart"></div>
     <input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
     </div>	
	</div>
     <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var detailData= $.parseJSON($(".detailData").text());
        function getChart(data){
        	var merchantList=[];
        	var merchantListData=[];
        	var unsellDay=parseInt(data.totalDay)-parseInt(data.sellDay);
        	for(var i=0;i<data.merchantList.length;i++){
        		merchantList.push(data.merchantList[i].name);
        		merchantListData.push({"value":data.merchantList[i].count,"name":data.merchantList[i].name})
        	}
        	merchantListData.push({"value":unsellDay,"name":accipiter.getLang_(messageLang,"adv.nosell")});
            var myChart = echarts.init(document.getElementById('Echart'));

            option = {
                title: {
                        text: accipiter.getLang_(messageLang,"adv.type")+data.comboName+accipiter.getLang_(messageLang,"adv.sellInfo"),
                        left:'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    top:'40px',
                    data:merchantList
                },
                series: [
                    {
                        name:data.comboName+accipiter.getLang_(messageLang,"adv.sellInfo"),
                        type:'pie',
                        selectedMode: 'single',
                        radius: [0, '30%'],

                        label: {
                            normal: {
                                position: 'inner'
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:[
                            {value:parseInt(data.sellDay), name:accipiter.getLang_(messageLang,"adv.sell"), selected:true},
                            {value:unsellDay, name:accipiter.getLang_(messageLang,"adv.nosell")}
                        ]
                    },
                    {
                        name:data.comboName+accipiter.getLang_(messageLang,"adv.sellInfo"),
                        type:'pie',
                        radius: ['40%', '55%'],

                        data:merchantListData
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
        getChart(detailData);
    </script>
</body>
</html>