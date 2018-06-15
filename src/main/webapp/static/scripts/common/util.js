/**
 * 日期格式化
 * 
 * @returns
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

function getLastDay(year,month)        
{        
	var new_year = year;    //取当前的年份        
	var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）        
	if(month>12)            //如果当前大于12月，则年份转到下一年        
	{        
		new_month -=12;        //月份减        
		new_year++;            //年份增        
	}        
	var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天     
	return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期        
}  