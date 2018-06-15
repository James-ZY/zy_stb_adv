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
		if(control==0){
			var cntabs="/advs/static/common/cn-nav-tabs.css";
				loadJsAndCssFile(cntabs,"css");
			
		}else{
			var ustabs="/advs/static/common/us-nav-tabs.css";
			loadJsAndCssFile(ustabs,"css");
		}		
	}
	loadFile();
	$(function(){
		$(".td-control").on("click","i",function(){
			if($(this).hasClass("td-open")){
				var control=accipiter.getLocale();
				$(this).removeClass("td-open");
				$(this).addClass("td-close");
				$("table").find(".td-fore2").show();
				$("table").find(".td-fore1").hide();
			}else{
				$(this).addClass("td-open");
				$(this).removeClass("td-close");
				$("table").find(".td-fore2").hide();
				$("table").find(".td-fore1").show();
			}
		})
	})
