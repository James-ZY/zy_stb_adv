$(function() {
 
	function loadJsAndCssFile(filename, filetype){
		if (filetype=="js"){
			var fileref=document.createElement('script');
			fileref.setAttribute("type","text/javascript");
			fileref.setAttribute("src", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
		else if (filetype=="css"){
			var fileref=document.createElement("link");
			fileref.setAttribute("rel", "stylesheet");
			fileref.setAttribute("type", "text/css");
			fileref.setAttribute("href", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
	}
	function loadFile(){
 
		var control=accipiter.getLocale();
		var headCss ="";
		if(control==0){
	 
			headCss="/advs/static/My97DatePicker/WdatePicker-zh-cn.js";
		}else{
	 
			headCss="/advs/static/My97DatePicker/WdatePicker-en.js";
		}	
		loadJsAndCssFile(headCss,"js");
 
	}
	loadFile();
	 
})