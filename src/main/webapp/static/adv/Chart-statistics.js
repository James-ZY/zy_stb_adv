/**
 * Created by Administrator on 2016/10/25 0025.
 */
    /*图表切换*/
    $("#switchover").on("click",function(){
    	if($(this).hasClass("switchover")){
    		$(this).removeClass("switchover");
    		$(".tab_content").css({"display":"none"});
    		$(".pagination").css({"display":"none"});
    		$(".chartContent").css({"display":"block"});
    		return false;
    	}else{
    		$(this).addClass("switchover");
    		$(".tab_content").css({"display":"block"});
    		$(".pagination").css({"display":"block"});
    		$(".chartContent").css({"display":"none"});
    		return false;
    	}
    });
    $(function () {
    	var detailData=$.parseJSON($("#id_list").text());
    	console.log(detailData);
    	/*数据分类*/
    	function getData(){
    		
    	}
    /*时间计算*/
    	function getTime(data){
    		var start = new Date('2016-10-15').getTime() , end = new Date('2016-10-20').getTime();
    		var dateData=[];//将日期转换成2016-10-26格式并存储
    		var reg=new RegExp("/","g"); //创建正则RegExp对象  
    		for(var i = start ; i <= end ;i+=24*60*60*1000){
    			dateData.push(new Date(i).toLocaleDateString().replace(reg,"-"));
    		}
    		return dateData;
    	}
    /*广告商在单个广告类型的所有套餐中的投放量占比*/
    	var data1=[{"title":"挂角广告",
    	           "data":[
    	                   {"name":"百度","count":"20","id":"1","advList":[["套餐1","12"],["套餐2","5"],["套餐3","5"],["套餐4","4"]]},
    	                   {"name":"阿里巴巴","count":"30","id":"2","advList":[["套餐1","1"],["套餐2","3"],["套餐3","5"],["套餐4","4"]]},
    	                   {"name":"腾讯","count":"10","id":"3","advList":[["套餐1","2"],["套餐2","5"],["套餐3","5"],["套餐4","4"]]},
    	                   {"name":"搜狐","count":"20","id":"4","advList":[["套餐1","4"],["套餐2","5"],["套餐3","5"],["套餐4","4"]]},
    	                   {"name":"谷歌","count":"20","id":"5","advList":[["套餐1","6"],["套餐2","5"],["套餐3","5"],["套餐4","4"]]},
    	                   ]
    	}];
    	function BulidChart1(data){
            Highcharts.setOptions({
                lang: {
                    drillUpText: '返回'
                }
            });
            var title='广告商在'+data[0].title+'投放广告占比';
            var merchants=[];//广告商
            var merchantData=[];//一个广告商详细数据
            var len=data[0].data.length;
            for(var i=0;i<len;i++){
            	merchants.push({"name":data[0].data[i].name,"y":parseFloat(data[0].data[i].count),"drilldown":data[0].data[i].id});
            	(function(i){
                  	var Len=data[0].data[i].advList.length;
                	console.log(i);
                	var listData=[];
                	for(var j=0;j<Len;j++){
                		listData.push([data[0].data[i].advList[j][0],parseFloat(data[0].data[i].advList[j][1])]);
                	}
                	merchantData.push({"id":data[0].data[i].id,"data":listData,"name":"占比"});
            	})(i)
            }
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
                    text: title
                },
                xAxis: {
                    type: 'category'
                },
                yAxis:{
                	title:{
                    	text:'百分比(%)'
                	}
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                series: [{
                    name: '投放广告占比',
                    colorByPoint: true,
                    data:merchants
                }],
                drilldown: {
                    drillUpButton: {
                        relativeTo: 'spacingBox',
                        position: {
                            y: 50,
                            x: 0
                        },
                        theme: {
                            fill: 'white',
                            'stroke-width': 1,
                            stroke: 'silver',
                            r: 0,
                            states: {
                                hover: {
                                    fill: '#bada55'
                                },
                                select: {
                                    stroke: '#039',
                                    fill: '#bada55'
                                }
                            }
                        }
                    },
                    series:merchantData
                }
            });
    	}
/*  	BulidChart1(data1);*/
    	/*对于频道相关的广告类型的广告套餐，可以按日期段查看各广告商自己在各个时间段的投放情况
    	 * 可以选择单个广告类型的所有套餐或者所有频道相关广告套餐进行查看，即在该广告商所有该类型
    	 * （或所有频道类型套餐）投放量中的占比(广告商和其他广告商进行比较).
    	 * 
    	 * */
    	var time=["00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00",
    	          "11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00",
    	          "22:00","23:00"];
    	var scaleData=[{"name":"投放量",data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}];
    	function BulidChart2(time,scaleData){
            Highcharts.setOptions({
                lang: {
                    drillUpText: '返回'
                }
            });
            // Create the chart
            $('#chart').highcharts({
            	/*去除默然链接  */
                credits: {
                    enabled: false
                },
                chart: {
                    type: 'line'
                },
                title: {
                    text: "广告投放量占比"
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
                    	text:'百分比(%)'
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
                                alert(event.point.category);  
             /*                   location.href='';  */
                            }  
                        }
                	}
                },
                series: scaleData
            });
    	}
	   BulidChart2(time,scaleData);
    	/*对于频道相关的广告类型的广告套餐，可以按日期段查看各广告商在单个广告类型的所有套餐中的，
    	 * 在各个时间段的投放情况，即该广告商在所有广告商在该类型所有套餐投放量中的占比。
    	 * 并据此对广告商排序(按日期查询(XX天),x为24小时)广告商自己和自己比较
    	 * */
    	var data3=[
    	           {"advtype":"插屏广告",
    	        	   "dataList":[
    	                           {"name":"百度","count":"30","id":"1","advListByDate":["5","5","10","2","3","5","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},
    	                           {"name":"阿里巴巴","count":"30","id":"2","advListByDate":["5","5","10","2","3","5","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},
    	                           {"name":"腾讯","count":"15","id":"3","advListByDate":["5","5","10","2","3","5","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},
    	                           {"name":"搜狐","count":"20","id":"4","advListByDate":["5","5","10","2","3","5","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]},
    	                           {"name":"谷歌","count":"10","id":"5","advListByDate":["4","2","1","1","1","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"]}
    	                          ]
    	           }
    	           ];
    	function BulidChart3(time,data){
            Highcharts.setOptions({
                lang: {
                    drillUpText: '返回'
                }
            });
    		   var title='广告商在'+data[0].advtype+'投放广告详情';
    	       var merchants=[];//广告商
    	       var merchantData=[];//一个广告商详细数据
    		   var len=data[0].dataList.length;
               for(var i=0;i<len;i++){
               	merchants.push({"name":data[0].dataList[i].name,"y":parseFloat(data[0].dataList[i].count),"drilldown":data[0].dataList[i].id});
               	(function(i){
                     	var Len=data[0].dataList[i].advListByDate.length;
                   	console.log(i);
                   	var listData=[];
                   	for(var j=0;j<Len;j++){
                   		listData.push([time[j],parseFloat(data[0].dataList[i].advListByDate[j])]);
                   	}
                   	merchantData.push({"id":data[0].dataList[i].id,"data":listData,"type":"line","name":data[0].dataList[i].name});
               	})(i)
               }
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
                    text: title
                },
                xAxis: {
                    type: 'category',
                    labels:{
                    	rotation:90
                    }
                },
                yAxis:{
                	title:{
                    	text:'百分比(%)'
                	}
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                series: [{
                    name: '投放广告占比',
                    colorByPoint: true,
                    data:merchants
                }],
                drilldown: {
                    drillUpButton: {
                        relativeTo: 'spacingBox',
                        position: {
                            y: 50,
                            x: 0
                        },
                        theme: {
                            fill: 'white',
                            'stroke-width': 1,
                            stroke: 'silver',
                            r: 0,
                            states: {
                                hover: {
                                    fill: '#bada55'
                                },
                                select: {
                                    stroke: '#039',
                                    fill: '#bada55'
                                }
                            }
                        }
                    },
                    series:merchantData
                }
            });
    	}
/*      BulidChart3(time,data3);*/
    	/*
    	 * 单个广告类型所有时段，所有广告商的分布
    	 * 
    	 * */
    	var data4=[{"advtype":"滚动广告",
    		"advlist":[
    		           {"advname":"套餐1","merchantCount":"8","advid":"1","merchantList":[["广告商1","2"],["广告商2","3"],["广告商3","1"],["广告商4","1"],["广告商5","1"],["广告商6","1"],["广告商7","3"],["广告商8","2"]]},
    		           {"advname":"套餐2","merchantCount":"10","advid":"2","merchantList":[["广告商1","2"],["广告商2","3"],["广告商3","1"],["广告商4","1"],["广告商5","1"],["广告商6","1"],["广告商7","3"],["广告商8","2"],["广告商9","3"],["广告商10","2"]]},
    		           {"advname":"套餐3","merchantCount":"6","advid":"3","merchantList":[["广告商1","2"],["广告商2","3"],["广告商3","1"],["广告商4","1"],["广告商5","1"],["广告商6","1"]]},
    		           {"advname":"套餐4","merchantCount":"4","advid":"4","merchantList":[["广告商1","2"],["广告商2","3"],["广告商3","1"],["广告商4","1"]]},
    		           {"advname":"套餐5","merchantCount":"5","advid":"5","merchantList":[["广告商1","2"],["广告商2","3"],["广告商3","1"],["广告商4","1"],["广告商5","1"]]}
    	                                 ]
    	}];
    	function BulidChart4(data){
            Highcharts.setOptions({
                lang: {
                    drillUpText: '返回'
                }
            });
            var title='广告商在'+data[0].advtype+'分布';
            var merchants=[];//广告商
            var merchantData=[];//一个广告商详细数据
            var len=data[0].advlist.length;
            for(var i=0;i<len;i++){
            	merchants.push({"name":data[0].advlist[i].advname,"y":parseFloat(data[0].advlist[i].merchantCount),"drilldown":data[0].advlist[i].advid});
            	(function(i){
                  	var Len=data[0].advlist[i].merchantList.length;
                	var listData=[];
                	for(var j=0;j<Len;j++){
                		listData.push([data[0].advlist[i].merchantList[j][0],parseFloat(data[0].advlist[i].merchantList[j][1])]);
                	}
                	merchantData.push({"id":data[0].advlist[i].advid,"data":listData,"name":data[0].advlist[i].advname+"投放次数","yAxis":1});
            	})(i)
            }
        	console.log(merchantData)
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
                    text: title
                },
                xAxis: {
                    type: 'category'
                },
                yAxis:[
                       {
		                	title:{
		                    	text:'广告商数'
		                	}
                       }, 
                       {
                           title: {
                               text: '套餐投放次数'
                           },
                           opposite: true
                       }
                ],
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                series: [{
                    name: '广告商数',
                    colorByPoint: true,
                    data:merchants
                }],
                drilldown: {
                    drillUpButton: {
                        relativeTo: 'spacingBox',
                        position: {
                            y: 50,
                            x: 0
                        },
                        theme: {
                            fill: 'white',
                            'stroke-width': 1,
                            stroke: 'silver',
                            r: 0,
                            states: {
                                hover: {
                                    fill: '#bada55'
                                },
                                select: {
                                    stroke: '#039',
                                    fill: '#bada55'
                                }
                            }
                        }
                    },
                    series:merchantData
                }
            });
    	}
    	/*BulidChart4(data4);*/
    	/*
    	 * 根据用户查询条件判断图表显示的类型
    	 * */
    	function getChart(){

    	}
    });