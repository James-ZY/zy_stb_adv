/**
 * Created by Administrator on 2016/7/18 0018.
 */
$(function(){
	var host = accipiter.getRootPath();
    var time1;//控制器
    var time2;
    var positionData=[];//记录不同类型广告坐标
    /*-----------------------------------换台广告---------------------------------*/
    $(function(){
        /*------ht_right-------*/
        var setW=$('.ad_ht').width()-$('.left').width();
        var setH=$('.ad_ht').height();
        $('.right').css({"width":setW,"height":setH});
    })
    function ad_htPlay(pathData){
    	var languageControl=accipiter.getLocale();
    	if(languageControl==0){
    		$('.ad_ht .left img').attr("src","../../static/images/icon/adv_ht_cn.png");
    	}else{
    		$('.ad_ht .left img').attr("src","../../static/images/icon/adv_ht_us.png");
    	}
        var showTime=5;
        var limit=pathData.length;
        var i=0;
        $('.ad_ht').css("display","block");
        $('.ad_ht .right img').attr("src",pathData[0]);
        i++;
        function playStyle(){
            if(i<limit){
                $('.ad_ht .right img').attr("src",pathData[i]);
                i++;
            }else{
                i=0;
            }
        }
        clearInterval(time1);
        time1= setInterval(function(){playStyle()},showTime*1000);
    }
    
    /*--------------------------------挂角广告----------------------------------------*/
    function ad_gjPlay(pathData,position,range,node){
        var showTime=10;
        var limit=pathData.length;
        clearInterval(time1);
        var i=0;
        var psLeft = position.setLeft;
        var psTop = position.setTop;
        
        var rgLeft = range.rangeX;
        var rgTop = range.rangeY;
        if(node=="Sd"){
          	$(".ad_gj").css("max-width","720px");
          	$(".ad_gj").css("max-height","576px");
          }else{
          	$(".ad_gj").css("max-width","1280px");
          	$(".ad_gj").css("max-height","720px");
        }
        $('.ad_gj').css({"left":rgLeft,"top":rgTop,"width":range.width,"height":range.height});
        $('.ad_gj').attr("class","ad_gj ad_gj_style");        
        $('.ad_gj dl').css({"left":psLeft,"top":psTop,"margin":"auto"});
        $('.ad_gj .flipper').attr("src",pathData[0]);
        
        i++;
        function playStyle(){
            if(i<limit){
            	$('.ad_gj .flipper').attr("src",pathData[i]);
                i++;
            }else{
                i=0;
            	  /*clearInterval(time1);*/
            }
        }
        time1= setInterval(function(){playStyle()},showTime*1000);
        var imgWidth = $('.ad_gj .flipper').width();
        var imgHeight = $('.ad_gj .flipper').height();
        var rangeWidth = range.width;
        var rangeHeight = range.height;
        var style = "both";
        if(imgHeight>=rangeHeight){
        	style = "x";
        }     
        if(imgWidth>=rangeWidth){
        	style = "y";
        }
    	$('.ad_gj dl').each(function(){
			$(this).dragging({
				move : style,
				randomPosition : false,
				rangeX : range.rangeX,
				rangeY : range.rangeY
			});
		});
    }
    
    /*--------------------------------插屏广告----------------------------------------*/
    function ad_cpPlay(data){
    	clearInterval(time1);
    	var showTime=parseInt(data[0].showtime);
    	var limit=data[0].list.length;
    	var i=0;
    	$('.ad_cp').css("background-image",'url('+data[0].list[0].url+')');
    	i++;
    	function playStyle(){
    		if(i<limit){
       		    $('.ad_cp').css("background-image",'url('+data[0].list[i].url+')');
    		}else{
    			clearInterval(time1);
    		}
    	}
    	time1= setInterval(function(){playStyle()},intervalTime*1000);
    }
    /*------------------------------滚动广告--------------------------------------*/
    function setHeight(){
        var limit=$('.ad_gd img').length;
        for(var i=0;i<limit;i++){
            var setHeight=$('.ad_gd img:eq('+i+')').width();
            $('.ad_gd img:eq('+i+')').parent().css("height",setHeight+"px");           
        }
    }
    function ad_gdPlay(pathData,showtime,showCount,roolposition,position,range,Velocity,gdFx,node){
         $("#gd_content ul li :gt(0)").remove(); 
         if(node=="Sd"){
           	$(".ad_gd").css("max-width","720px");
           	$(".ad_gd").css("max-height","576px");
           }else{
           	$(".ad_gd").css("max-width","1280px");
           	$(".ad_gd").css("max-height","720px");
         }
    	 var moveStyle = "left";
         var psLeft = position.setLeft;
         var psTop = position.setTop;
         
         var rgLeft = range.rangeX;
         var rgTop = range.rangeY;
         
         $('.ad_gd').css({"left":rgLeft,"top":rgTop,"width":range.width,"height":range.height});
         $('.ad_gd').css("display","block");      
         $('.ad_gd .flipper').attr("src",pathData[0]); 
         var imgWidth = $('.ad_gd .flipper').width();
         var imgheight = $('.ad_gd .flipper').height();
         console.log(imgWidth);
         console.log(imgheight);
         if(gdFx == 1){
         	moveStyle ="right";
         }else if(gdFx ==2){
         	moveStyle = "up";
         }else if(gdFx ==3){
         	moveStyle = "down";
         }else{
         	moveStyle = "left";
         }
         if(gdFx == 1 || gdFx==0){
        	 if(node=="Sd"){
        		 $('#gd_content').css({"left":psLeft,"top":psTop,"margin":"auto","width":range.width,"height":imgheight});        	 
        	 }else{
        		 if(imgWidth == 720){
        			 range.width = 720;
        			 $('#gd_content').css({"left":psLeft,"top":psTop,"margin":"auto","width":"720px","height":imgheight});  
        		 }else{
        			 $('#gd_content').css({"left":psLeft,"top":psTop,"margin":"auto","width":range.width,"height":imgheight});    
        		 }
        	 } 
        	 $('#gd_content ul').css("height",imgheight);
         }else{
        	 if(node=="Sd"){
        		 $('#gd_content').css({"left":psLeft,"top":psTop,"margin":"auto","width":imgWidth,"height":range.height});        	   
        	 }else{
        		 if(imgheight == 720){
        			 range.height = 720;
            		 $('#gd_content').css({"left":psLeft,"top":psTop,"margin":"auto","width":imgWidth,"height":range.height});        	   
        		 }else{
            		 $('#gd_content').css({"left":psLeft,"top":psTop,"margin":"auto","width":imgWidth,"height":range.height});        	   
        		 }
        	 }  
        	$('#gd_content ul').css("width",imgWidth);
         }

         var a = $("#gd_content").kxbdMarquee({
      		isEqual:false,
      		loop:0,
      		direction:moveStyle,
             scrollAmount:Velocity,		//步长(px)
             scrollDelay:50		//时长(ms)
      	});
     	clearInterval(a[0].moveId);
     	$("#gd_content").kxbdMarquee({
      		isEqual:false,
      		loop:0,
      		direction:moveStyle,
             scrollAmount:Velocity,		//步长(px)
             scrollDelay:50		//时长(ms)
      	});
     	
     	if(gdFx == 1 || gdFx==0){
     		$('.ad_gd ul').css("height",imgheight);
     		$('.gd_content').each(function(){
     			$(this).dragging({
     				move : 'both',
     				randomPosition : false,
     				rangeX : range.rangeX,
     				rangeY : range.rangeY,
     				rangeWidth : range.width,
     				rangeHeight : range.height,
     				moveStyle : moveStyle
     			});
     		});	
     	}else{
     		$('.ad_gd ul').css("width",imgWidth);
     		$('.gd_content').each(function(){
     			$(this).dragging({
     				move : 'x',
     				randomPosition : false,
     				rangeX : range.rangeX,
     				rangeY : range.rangeY,
     				rangeWidth : range.width,
     				rangeHeight : range.height,
     				moveStyle : moveStyle
     				
     			});
     		});

     	}
    }
    /*------------------------------插屏广告--------------------------------------*/
    function ad_cpPlay(pathData,showtime,showCount,position,range,node,trackMode,trackData){
        var psLeft = position.setLeft;
        var psTop = position.setTop;

        var rgLeft = range.rangeX;
        var rgTop = range.rangeY;
        if(node=="Sd"){
            $(".ad_cp").css("max-width","720px");
            $(".ad_cp").css("max-height","576px");
        }else{
            $(".ad_cp").css("max-width","1280px");
            $(".ad_cp").css("max-height","720px");
        }
        if(trackMode == 1) {
            clearInterval(time1);
            var imglimit=pathData.length;/*图片数*/
            var c_limit=showCount;/*显示次数*/
            if(showCount==0){
                c_limit=1;
                showtime=20;
            }
            var showTime=showtime/imglimit;/*每张图片显示时间*/
            var intervalTime=showtime+5;/*当前一轮图片显示到下一轮图片出现的时间*/
            var i=0;
            var i_contolShow=0;

            function playStyle(){
                $('.ad_cp').css({"left":rgLeft,"top":rgTop,"width":range.width,"height":range.height});
                $('.ad_cp').css("display","block");
                $('.ad_cp dl').css({"left":psLeft,"top":psTop,"margin":"auto"});
                $('.ad_cp .flipper').attr("src",pathData[0]);
                i++;
                time2= setInterval(function(){
                    if(i<imglimit){
                        $('.ad_cp .flipper').attr("src",pathData[0]);
                        i++;
                    }else{
                        i_contolShow++;
                        i=0;
                        $('.ad_cp').css("display","none");
                        clearInterval(time2);
                    }
                },showTime*1000);
            }
            playStyle();
            time1= setInterval(function(){
                if(i_contolShow<c_limit){
                    playStyle();
                }else{
                    clearInterval(time1);
                }
            },intervalTime*1000);

            var imgWidth = $('.ad_cp .flipper').width();
            var imgHeight = $('.ad_cp .flipper').height();
            var rangeWidth = range.width;
            var rangeHeight = range.height;
            var style = "both";
            if(imgHeight>=rangeHeight){
                style = "x";
            }
            if(imgWidth>=rangeWidth){
                style = "y";
            }
            $('.ad_cp dl').each(function () {
                $(this).dragging({
                    move: style,
                    randomPosition: false,
                    rangeX: range.rangeX,
                    rangeY: range.rangeY
                });
            });
        }else {
            $('.ad_cp').css({"left":rgLeft,"top":rgTop,"width":range.width,"height":range.height});
            $('.ad_cp').css("display","block");
            var coordinatesc = trackData.coordinates;
            var first = coordinatesc.split("-")[0];
            var frx = parseInt(first.split(",")[0]) - parseInt(rgLeft);
            var fry = parseInt(first.split(",")[1]) - parseInt(rgTop);
            $('.ad_cp dl').css({"left":frx,"top":fry,"margin":"auto"});
            $('.ad_cp .flipper').attr("src",pathData[0]);
            var dl = $('.ad_cp .flipper');
            dl.stop(true,true);
            $.each(coordinatesc.split("-"),function (commentIndex,comment) {
                var x = parseInt(comment.split(",")[0])-parseInt(rgLeft);
                var y = parseInt(comment.split(",")[1])-parseInt(rgTop);
                dl.animate({left: x,top:y} ,1000).delay(1500);
            });
        }
    }
    /*----------------------------------视频广告----------------------------------*/
    function vedioPlay(path,fileImagePath){
    	$(".ad_video .vjs-poster").css("background-image","none");
    	$(".ad_video").css("display","block");   	
        $('.ad_video video').attr({
        	"poster":fileImagePath,
            "src":path
        });
        $('.ad_video source').attr({
            "src":path
        });
    	var player = videojs('my-video');   	   
    	player.load();
    	player.pause();
    	$(".vjs-big-play-button").on("click",function(){
    		player.play();
    	});
    }
    /*----------------------------------背景广告---------------------------------------*/
    function adBgPlay(pathData){
    	$(".ad_bg").css("background",'url('+pathData[0]+')');
    	$(".ad_bg").css("display","block");
   }
    /*----------------------------------菜单广告---------------------------------------*/
    function adMenuPlay(pathData){
    	var languageControl=accipiter.getLocale();
    	if($(".childType").text()!==""){
        	adMenuChildPlay($(".childType").text().getAdvType(),languageControl,pathData[0]);
    	}
    	$(".ad_menu").css("display","block");
    }
    function adMenuChildPlay(i,language,src){
    	switch(i) 
    	{ 
    	    case "8-0"://主菜单 	    	
    	    	if(language==0){ 
    	    		$(".main_adv").css("background","url('../../static/images/icon/main_cn.jpg')");
    	    	}else{
    	    		$(".main_adv").css("background","url('../../static/images/icon/main_us.jpg')");
    	    	}
    	    	$(".main_adv").find("img").attr("src",src);
    	    	$(".main_adv").css("display","block");
    	    break; 
    	    case "8-1"://音量条
    	    	if(language==0){ 
    	    		$(".volume_adv").find(".menu_left img").attr("src","../../static/images/icon/volume_cn.png");
    	    	}else{
    	    		$(".volume_adv").find(".menu_left img").attr("src","../../static/images/icon/volume_us.png");
    	    	}
    	    	$(".volume_adv").find(".menu_right img").attr("src",src);
    	    	$(".volume_adv").css("display","block");
    	    	$(".ad_menu").css("background","url('../../static/images/icon/adv_bg.jpg')");
    	    break; 
    	    case "8-2": //频道列表
    	    	if(language==0){ 
    	    		$(".channelList_adv .bg").attr("src","../../static/images/icon/channelList_cn.jpg");
    	    	}else{
    	    		$(".channelList_adv .bg").attr("src","../../static/images/icon/channelList_us.jpg");
    	    	}
    	    	$(".channelList_adv").find(".adv").attr("src",src);
    	    	$(".channelList_adv").css("display","block");
    	    	
    	    break; 
    	    case "8-3": //EPG列表
    	    	if(language==0){ 
    	    		$(".EPG_adv .bg").attr("src","../../static/images/icon/EPG_cn.jpg");
    	    	}else{
    	    		$(".EPG_adv .bg").attr("src","../../static/images/icon/EPG_us.jpg");
    	    	}
    	    	$(".EPG_adv").find(".adv").attr("src",src);
    	    	$(".EPG_adv").css("display","block");
    	    	
        	break; 
    	}
    	
    }
    /*----------------------------------提示广告---------------------------------------*/
    function adPromptPlay(pathData){
    	var languageControl=accipiter.getLocale();
    	$(".ad_prompt").find(".adImg img").attr("src",pathData[0]);
    	if($(".childType").text()!=""){
        	adPromptChildPlay($(".childType").text().getAdvType(),languageControl);
    	}
    	$(".ad_prompt").css("display","block");
    }
    function adPromptChildPlay(i,language){
    	switch(i) 
    	{ 
    	    case "7-0"://无信号
    	    	$(".ad_promptInfo").find("span").text(accipiter.getLang_(messageLang,"adv.noSignal")+"!");
    	    break; 
    	    case "7-1":  //无频道
    	    	if(language==0){ 
    	    		$(".adv_noChannel").css("background","url('../../static/images/icon/noChannel_cn.png')");
    	    	}else{
    	    		$(".adv_noChannel").css("background","url('../../static/images/icon/noChannel_us.png')");
    	    	}
    	    	$(".ad_prompt .adv_noChannel").css("display","block");
    	    	$(".ad_promptInfo").css("display","none");
    	    break; 
    	    case "7-2": //无授权
    	    	$(".ad_promptInfo").find("span").text(accipiter.getLang_(messageLang,"adv.noAuthorization")+"!");
    	    break; 
    	    case "7-3": //其他类型
    	    	$(".ad_promptInfo").find("span").text(accipiter.getLang_(messageLang,"adv.Ather")+"!");
        	break; 
    	}
    }
    /*----------------------------------开机广告---------------------------------------*/
    function adKjPlay(pathData){
    	clearInterval(time1);
    	var limit=pathData.length;
    	var kj_control=1;
    	$(".ad_kj").css("background",'url('+pathData[0]+')');
    	$(".ad_kj").css("display","block");
        time1= setInterval(function(){
        	if(kj_control<limit){
        		$(".ad_kj").css("background",'url('+pathData[kj_control]+')');
        		kj_control++;
        	}else{
        		clearInterval(time1);
        	}
        	},4000);
    }
    /*----------------------------------获取数据---------------------------------*/
    function getPreviewData(data){
    	var typeId=data.typeId;
		if(typeId==1){
			//开机
			$(".ad_Preview .Preview_content").css({"top":"40px","left":"41px"});
			adKjPlay(data.pathData,data.position);		
		}
		if(typeId==2 || typeId ==10){
			//挂角
			ad_gjPlay(data.pathData,data.position,data.range,data.node);			
		}
		if(typeId==4){
			//插屏
			var showtime=parseInt(data.totalData.showTime);
			var showCount=parseInt(data.totalData.showCount);
            ad_cpPlay(data.pathData,showtime,showCount,data.position,data.range,data.node,data.trackMode,data.trackData);
		}
		if(typeId==3){
			//换台
			$(".ad_Preview .Preview_content").css({"top":"40px","left":"41px"});
			ad_htPlay(data.pathData);
		}
		if(typeId==5){
			//滚动
			var showtime=parseInt(data.totalData.showTime);
			var showCount=parseInt(data.totalData.showCount);
			ad_gdPlay(data.pathData,showtime,showCount,data.rollPosition,data.position,data.range,data.Velocity,data.gdFx,data.node);
		}
		if(typeId==6){
			//视频广告
			$(".ad_Preview .Preview_content").css({"top":"40px","left":"41px"});
			vedioPlay(data.pathData,data.fileImagePath);
		}
		if(typeId==7){
			//提示框广告
			$(".ad_Preview .Preview_content").css({"top":"40px","left":"41px"});
			adPromptPlay(data.pathData);
		}
		if(typeId==8){
			//菜单广告
			$(".ad_Preview .Preview_content").css({"top":"40px","left":"41px"});
			adMenuPlay(data.pathData);
		}
		if(typeId==9){
			//背景广告
			$(".ad_Preview .Preview_content").css({"top":"40px","left":"41px"});
			adBgPlay(data.pathData);
		}
		
    }
 /********高标清预览切换*********/
    function InitializeShowStyle(){
        var isSd=$("#isSd").attr("value");
        var isHd=$("#isHd").attr("value");
        if(isSd=="0"&&isHd=="1"){
        	$("#hdShow").attr("name","1");
        	$("#standardShow").attr({"disabled":true,"name":"-1"});
        	var  hdData = ResolutionShowStyle("Hd");
			var typeId = hdData.typeId;
            if(typeId ==2 ||typeId ==4 || typeId ==5 || typeId ==10){
            	$(".ad_Preview").css("height","870px");
				$(".ad_Preview .tv_icon").css({"width":"1360px","height":"800px","background":"url("+host+"/static/images/icon/tv-icon2.jpg)"});
				$(".ad_Preview .Preview_content").css({"width":"1286px","height":"726px"});
			}
			getPreviewData(hdData);
			getPreviewData(hdData);
        }
        if(isSd=="1"&&isHd=="0"){
        	$("#hdShow").attr({"disabled":true,"name":"-1"});
        	$("#standardShow").attr("name","1");
        	var sdData = ResolutionShowStyle("Sd");
			var typeId=sdData.typeId; 
			if(typeId ==2 ||typeId ==4 || typeId ==5 || typeId ==10){
				$(".ad_Preview").css("height","720px");
				$(".ad_Preview .tv_icon").css({"width":"800px","height":"656px","background":"url("+host+"/static/images/icon/tv-icon.jpg)"});
				$(".ad_Preview .Preview_content").css({"width":"726px","height":"582px"});
			}
			getPreviewData(sdData);
			getPreviewData(sdData);
        }
        if(isSd=="1"&&isHd=="1"){
        	
        	$("#hdShow").attr("name","1");
        	$("#standardShow").attr("name","0");
        	var  hdData = ResolutionShowStyle("Hd");
			var typeId = hdData.typeId;
		     if(typeId ==2 ||typeId ==4 || typeId ==5 || typeId ==10){
	            	$(".ad_Preview").css("height","870px");
					$(".ad_Preview .tv_icon").css({"width":"1360px","height":"800px","background":"url("+host+"/static/images/icon/tv-icon2.jpg)"});
					$(".ad_Preview .Preview_content").css({"width":"1286px","height":"726px"});
				}
        	getPreviewData(ResolutionShowStyle("Hd"));
        	
        	$("#standardShow").attr("name","1");
        	$("#hdShow").attr("name","0");
        	var sdData = ResolutionShowStyle("Sd");
			var typeId=sdData.typeId; 
			if(typeId ==2 ||typeId ==4 || typeId ==5 || typeId ==10){
				$(".ad_Preview").css("height","720px");
				$(".ad_Preview .tv_icon").css({"width":"800px","height":"656px","background":"url("+host+"/static/images/icon/tv-icon.jpg)"});
				$(".ad_Preview .Preview_content").css({"width":"726px","height":"582px"});
			}
        	getPreviewData(ResolutionShowStyle("Sd"));
        	getPreviewData(ResolutionShowStyle("Sd"));
        }
    }
    function ResolutionShowStyle(node){
    	if($('#adelementDto').text()!=""){
        	var pathData=[];
        	var position={};
        	var rollPosition={};
        	var range={};
      		var data=JSON.parse($('#adelementDto').text());
      	   	var typeId=parseInt(data.typeId.getAdvType());
      	    var fileImagePath='';
      	    var Velocity ;
      	    var gdFx;
            var trackData = {};
            var trackMode = data.showMode;
        	if(node=="Hd"){
         	   	var path=data.hdPath;
          	   	var len=path.split(".").length;
          	    var HdPathData=[];
          	   	if(len==2){
          	   	HdPathData.push(path);
          	   	}else{
          			var path=data.hdPath.split(",");
          			for(var i=0,len=path.length;i<len;i++){
          				HdPathData.push(path[i]);
          			}
          	   	}
          	  position={"setLeft":parseInt($("#hdLeft").val()),"setTop":parseInt($("#hdTop").val())};          		  
          	  fileImagePath=data.hdFileImagePath;
          	  pathData=HdPathData;
          	  Velocity=parseInt(data.hdVelocity);
          	  rollPosition={"startX":data.hdX,"startY":data.hdY,"endX":data.endHdX,"endY":data.endHdY};
          	  range={"rangeX":data.hdStartX,"rangeY":data.hdStartY,"width":data.widthHd,"height":data.heightHd};
          	  gdFx=data.hdFx;
          	  trackData = {"coordinates":data.hdCoordinates,"showTime":data.hdCpShowTime,"bgWidth":data.hdBgWidth,"bgHeight":data.hdBgHeight};
            }
        	if(node=="Sd"){
         	   	var path=data.path;
          	   	var len=path.split(".").length;
          	    var SdPathData=[];
          	   	if(len==2){
          	    SdPathData.push(path);
          	   	}else{
          			var path=data.path.split(",");
          			for(var i=0,len=path.length;i<len;i++){
          				SdPathData.push(path[i]);
          			}
          	   	}
          	  position={"setLeft":parseInt($("#sdLeft").val()),"setTop":parseInt($("#sdTop").val())};          		  
          	  fileImagePath=data.fileImagePath;
          	  pathData=SdPathData;
          	  Velocity=parseInt(data.sdVelocity);
          	  rollPosition={"startX":data.sdX,"startY":data.sdY,"endX":data.endSdX,"endY":data.endSdY};
           	  range={"rangeX":data.sdStartX,"rangeY":data.sdStartY,"width":data.width,"height":data.height};
              gdFx=data.sdFx;
              trackData = {"coordinates":data.sdCoordinates,"showTime":data.sdCpShowTime,"bgWidth":data.sdBgWidth,"bgHeight":data.sdBgHeight};
            }
      	   	var reData={"typeId":typeId,"totalData":data,"pathData":pathData,"position":position,"rollPosition":rollPosition,"fileImagePath":fileImagePath,"Velocity":Velocity,"range":range,"gdFx":gdFx,"node":node,"trackMode":trackMode,"trackData":trackData};
        	return reData;
    	}
    }
    InitializeShowStyle();
    $(".switchResolution").on("click","input",function(){
    	$(".switchResolution").find("input").attr("name","0");
    	if($(this).attr("name")=="0"){
    		$(this).attr("name","1");
    		if($(this).attr("id")=="standardShow"){
    			var sdData = ResolutionShowStyle("Sd");
    			var typeId=sdData.typeId; 
    			if(typeId ==2 ||typeId ==4 || typeId ==5 || typeId ==10){
    				$(".ad_Preview").css("height","720px");
					$(".ad_Preview .tv_icon").css({"width":"800px","height":"656px","background":"url("+host+"/static/images/icon/tv-icon.jpg)"});
					$(".ad_Preview .Preview_content").css({"width":"726px","height":"582px"});
				}
    			getPreviewData(sdData);
    		}else{
    			var  hdData = ResolutionShowStyle("Hd");
    			var typeId = hdData.typeId;
                if(typeId ==2 ||typeId ==4 || typeId ==5 || typeId ==10){
                	$(".ad_Preview").css("height","870px");
					$(".ad_Preview .tv_icon").css({"width":"1360px","height":"800px","background":"url("+host+"/static/images/icon/tv-icon2.jpg)"});
					$(".ad_Preview .Preview_content").css({"width":"1286px","height":"726px"});
				}
    			getPreviewData(hdData);
    		}
    	}
    });
})