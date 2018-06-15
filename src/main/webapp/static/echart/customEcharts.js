/**
 * 通用echarts方法
 * author: zhw
 */

//柱状图通用序列item
var bar_series_item = {
    data: [],//array
    type: 'bar',
    barWidth : 60,//柱图宽度
    ext1 : '',
    ext2 : '',
    ext3 : ''
};

/**
 * 添加饼状图
 * @param title 主标题
 * @param subtitle 副标题
 * @param legend_data 图例
 * @param tips 数据标题
 * @param series_data 数据序列
 * @param container_id div容器id
 * @param click 点击回调
 */
function addPieChart(title,subtitle,legend_data,tips,series_data,container_id,click) {

    var myChart = echarts.init(document.getElementById(container_id));

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: title,
            subtext: subtitle,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        // legend: {
        //     orient: 'vertical',
        //     left: 'left',
        //     data: legend_data
        // },
        series : [
            {
                name: tips,
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:series_data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart.on("click", click);
}

/**
 * 添加基础柱状图
 * @param title
 * @param subtitle
 * @param legend_data
 * @param category_data
 * @param series_data
 * @param x_name
 * @param y_name
 * @param container_id
 * @param click
 */
function addBarChart(title,subtitle,legend_data,category_data,series_data,x_name,y_name,container_id,click) {
    var myChart = echarts.init(document.getElementById(container_id));
    var option = {
        title : {
            text: title,
            subtext: subtitle,
            x:'center'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        // legend: {
        //     orient: 'vertical',
        //     left: 'left',
        //     data: legend_data
        // },
        color:
            ['#27ae60','#7f8c8d'],
        xAxis: {
            name: x_name,
            type: 'category',
            data: category_data
            //data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            name: y_name,
            type: 'value'
        },
        series: series_data
    };
  /*  [{
        data: series_data,
        //data: [120, 200, 150, 80, 70, 110, 130],
        type: 'bar',
        barWidth : 60//柱图宽度
    }]*/
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart.on("click", click);
}

/**
 * 添加自定义tooltip柱状图
 * @param title
 * @param subtitle
 * @param legend_data
 * @param category_data
 * @param series_data
 * @param x_name
 * @param y_name
 * @param container_id
 * @param click
 */
function addCustomToolTipBarChart(title,subtitle,legend_data,category_data,series_data,x_name,y_name,container_id,click) {
    var myChart = echarts.init(document.getElementById(container_id));
    var option = {
        title : {
            text: title,
            subtext: subtitle,
            x:'center'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params,ticket, callback) {
                var html = params[0].name+'<br/>';
                html+=accipiter.getLang_(messageLang,"combo.valid.time")+': '+params[0].value+'<br/>';
                html+=accipiter.getLang_(messageLang,"combo.sell.time")+': '+params[1].value+' ('+((params[1].data.percent)*100).toFixed(2)+'%'+')';
                return html;
            }
        },
        legend: {
            data: legend_data,
            right: '10%'
        },
        color:
            ['#27ae60','#7f8c8d'],
        xAxis: {
            name: x_name,
            type: 'category',
            data: category_data
            //data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            name: y_name,
            type: 'value'
        },
        series: series_data
    };
    /*  [{
     data: series_data,
     //data: [120, 200, 150, 80, 70, 110, 130],
     type: 'bar',
     barWidth : 60//柱图宽度
     }]*/
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart.on("click", click);
}