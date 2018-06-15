//js国际化，加载资浏览器语言对应的资源文件
(function(){
	 $.i18n.properties({ 
		 // 资源文件名称
		 //根据语言类型加载文件
		 language : (function(){
			 //获取本地话设置的语言，值en_US/zh_CN
			 var c=document.cookie;
				if(c){
					var s=/myLocaleCookie=([^\;]*)/;
					var cc=c.match(s);
					if(cc){
						return cc[1];
					}else{	//语言设置为默认时(c不存在时)，默认为中文	
						return "en_US";
				   }
				}
				return "en_US";
		 })(), 
		 // 资源文件路径
		 path: GLOBAL.url.base+"static/scripts/i18n/",
		 // 用 Map 的方式使用资源文件中的值
		 mode:'map',
		 cache:true,
		 // 加载成功后设置显示内容	
		 callback: function() {}	 
	 }); 
})();
