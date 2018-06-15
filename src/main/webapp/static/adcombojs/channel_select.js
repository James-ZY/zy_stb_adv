/**
 * Created by Administrator on 2016/6/16 0016.
 */
$(function(){
	var host=accipiter.getRootPath();
	var TypeId='';
	var rem=new Array();//批量选择
	//频道选择状态：name=0;没有选中，name=1,表示当前已被选中，name=2,表示不可操作;
	/*init();*/
	var isFlag=$("#isFlag").find('option:selected').val(); 
    if(isFlag == "1"){
    	validTime();    	
    }

  //与程序无关，是禁止鼠标选中文字。点选的时候容易选中文字 太讨厌 。
    document.onselectstart=function(event){
        event = window.event||event;
        event.returnValue = false;
    }
	/*
	 * 后台回去频道数据*/
    function get_channel(){
    	var isFlag = $("#isFlag").val();
        var sendMode = "";
        if(isFlag == "0"){
           sendMode = $("#sendModeWG").val();
        }else{
       	   sendMode = $("#sendModeYG").val();
        }
        var html='';
        var typeId=$("#adTypeSelect").find("option:selected").val();
        $('.channel_content_ul').html("");
        $.ajax({
            type:"post",
            async: false,
            url:host+"/adv/combo/find_network",
            dataType:"json",
            success:function(data){
                $.each(data,function(commentIndex,comment){
                     var operatorsName   =    comment["operatorsName"];
                      html+='<li class="fasongqi_type"><input class="fasongqi_type_btn" type="button" name="0"><label>'+operatorsName+'</label>';
                  $.each(comment["networkList"],function(commentIndex,comment){
                	  html+='<ul class="fasongqi_type_content">';
                    if(comment["networkId"]!=""){
                    	html+='<input name="1" type="button" class="fasongqi_type_btn" id="f_'+comment["networkId"]+'"><label for="f_'+comment["networkId"]+'">'+comment["networkName"]+'</label>';
                    }
                    var post_data1={"networkId":comment["networkId"],"typeId":typeId};
                    var str1 = JSON.stringify(post_data1);
                    var startDate=getPlayTime().startDate;
                    var endDate=getPlayTime().endDate;
                	var startTime=getPlayTime().startTime;
                	var endTime=getPlayTime().endTime;
                    var post_data2={"networkId":comment["networkId"],"typeId":typeId,startTime:startTime,endTime:endTime,startDate:startDate,endDate:endDate,sendMode:sendMode};
                    var str2 = JSON.stringify(post_data2);
                    var t_str=comment["networkId"];
             	   $.ajax({
                       type:"post",
                       async: false,
                       url:host+"/adv/combo/find_channel",
                       data:str2,
                       contentType:"application/json; charset=UTF-8",
                       dataType:"json",
                       success:function(data){
                    	   $.each(data,function(commentIndex,comment){
                    	        var tempHtml='';
                              if(comment["channelList"].length==0){
                            	/*  html+='<li class="channel_list">'+accipiter.getLang_(messageLang,"nochannel")+'';*/
                              }else{
                       		   html+='<li class="channel_type_list">' +
                               '<input  name="0" type="button" class="channel_type" id="t_'+commentIndex+''+t_str+'"><label for="t_'+commentIndex+''+t_str+'">'+comment["networkName"]+'</label><ul class="channel_type_list_content">';
                       		   $.each(comment["channelList"],function(commentIndex,comment){
                       			   var channelId= comment["channelId"];
                                 	if(comment["invalid"]){
                                 		html+='<li class="channel_list" style="height:25px;">' +
                                       '<input  name="0" class="channel_item" type="button" id="c_'+comment["channelId"]+'">' +
                                       '<label for="c_'+comment["channelId"]+'">'+comment["channelName"]+'</label></li>' ;
	                               	}else{
	                               		var content='';
	                               		$.each(comment["adcomboUsedList"],function(commentIndex,comment){
	                               			content+='<p><a>'+comment["comboName"]+'('+startDate+" "+comment["startTime"]+','+endDate+" "+comment["endTime"]+')</a></p>';
	                               		});
	                               		if(comment["adcomboUsedList"].length>0){
	                               			html+='<div onMouseOver="javascript:show(this,dv_'+channelId+');" onMouseOut="hide(this,dv_'+channelId+');"><li class="channel_list">' +
	                               			'<input  name="2" class="channel_item channel_disabled" type="button" disabled="disabled" id="c_'+comment["channelId"]+'">' +
	                               			'<label for="c_'+comment["channelId"]+'">'+comment["channelName"]+'</label>' ;                               			
	                               		}else{
	                               			html+='<div onMouseOver="javascript:show(this,dv_'+channelId+');" onMouseOut="hide(this,dv_'+channelId+');"><li class="channel_list">' +
	                               			'<input  name="2" class="channel_item channel_disabled" type="button" disabled="disabled" id="c_'+comment["channelId"]+'">' +
	                               			'<label for="c_'+comment["channelId"]+'">'+comment["channelName"]+'</label>' ;
	                               		}
	                               		tempHtml='<div id="dv_'+channelId+'" style="position:relative;display:none;width:220px;">'+content+'</div>';
	                               		html=html+tempHtml+'</li></div>';
	                               	}
                                 });
                               html+='</ul></li>';
                              }
                    	   });
                           channer_data=[];
                       },
                       error:function(){
                       }
                   });
             	   html+='</ul>';
                  });
            	   html+='</li>';
                });
                $('.channel_content_ul').append(html);
                var obj=$(".fasongqi_type_btn");
                $('.fasongqi_type_btn:eq(0)').attr("name","1");
                for(var i=0;i<obj.length;i++){
                	var name=$('.fasongqi_type_btn:eq('+i+')').attr("name");
                	if(name=="0"){
                		$('.fasongqi_type_btn:eq('+i+')').parent().find(".fasongqi_type_content").css("display","none");
                		$('.fasongqi_type_btn:eq('+i+')').css("background-image","url('../../static/images/icon/ic_open.png')");
                		$('.fasongqi_type_btn:eq('+i+')').find(".channel_type_list").css("display","none");
                	}else{
                		$('.fasongqi_type_btn:eq('+i+')').css("background-image","url('../../static/images/icon/ic_close.png')");
                		$('.fasongqi_type_btn:eq('+i+')').parent().find(".fasongqi_type_content").css("display","block");
                		$('.fasongqi_type_btn:eq('+i+')').find(".channel_type_list").css("display","block");
                	}
                }
            },
            error:function(){
            }
        });
    }

    
   /*
    * 如果发射器下没有数据，禁止改发射器下的操作
    * */
    function banClick(){
    	var len=$(".fasongqi").length;
    	for(var i=0;i<len;i++){
    		var list=$('.fasongqi:eq('+i+')').parent().find("li").length;
    		if(list==0 || list==undefined){
    			$('.fasongqi:eq('+i+')').attr("disabled",true);
    		}
    	}
    	
    }
    //时间验证
    function validTime(){
    	var validStartTime = $("#validStartTime").val();
    	var validEndTime = $("#validEndTime").val();
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	if(validStartTime == null || validStartTime == ""){
			$(".setPlayStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startDate"));
		}else{
			$(".setPlayStartTime").find(".info-messages p").text("");
		}	
    	if(validEndTime == null || validEndTime == ""){
	    	 $(".setPlayEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endDate"));
		}else{
	    	 $(".setPlayEndTime").find(".info-messages p").text("");
		}
    	if(startTime == null || startTime == ""){
			$(".setStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startTime"));
		}else{
			$(".setStartTime").find(".info-messages p").text("");
		}	
    	if(endTime == null || endTime == ""){
	    	 $(".setEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endTime"));
		}else{
	    	 $(".setEndTime").find(".info-messages p").text("");
		}
    	if(validStartTime != null && validStartTime != "" && validEndTime != null && validEndTime != ""
    		&& startTime != null && startTime != "" && endTime != null && endTime != ""){
    		$("#btnSubmit").attr({"disabled":false});
    	}else{
    		$("#btnSubmit").attr({"disabled":true});
    		return false;
    	}
    }
    
    function validStartTime(){
    	var startTime = $("#validStartTime").val();
		if(startTime == null || startTime == ""){
			$(".setPlayStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startDate"));
			$("#btnSubmit").attr({"disabled":true});
		}else{
			$(".setPlayStartTime").find(".info-messages p").text("");
			$("#btnSubmit").attr({"disabled":false});
		}	
    }
    
    function validEndTime(){
    	var validEndTime = $("#validEndTime").val();
		if(validEndTime == null || validEndTime == ""){
	    	 $(".setPlayEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endDate"));
	    	 $("#btnSubmit").attr({"disabled":true});
		}else{
	    	 $(".setPlayEndTime").find(".info-messages p").text("");
	    	 $("#btnSubmit").attr({"disabled":false});
		}
    }
    $('#validStartTime').blur(function() {
    	validTime();
	});

	$('#validEndTime').blur(function() {
		validTime();
	});
	$('#startTime').blur(function() {
	 	 var startTime=$(this).val();
    	 if(startTime!=null && startTime != ""){
    	   $("#startHour").val(startTime.split(":")[0]); 
    	   $("#startMinutes").val(startTime.split(":")[1]); 
    	   $("#startSecond").val(startTime.split(":")[2]); 
    	 }
		validTime();
	});
	$('#endTime').blur(function() {
    	 var endTime=$(this).val();
         if(endTime!=null && endTime != ""){
    	   $("#endHour").val(endTime.split(":")[0]); 
    	   $("#endMinutes").val(endTime.split(":")[1]); 
    	   $("#endSecond").val(endTime.split(":")[2]);  
    	 }
		validTime();
	});
	
    /*
     * 获取已选择频道id*/
    function get_selectData() {
        var selectData = [];
        var selectNameData=[];
        var fsqData = [];
        var channelData=[];
        var item = $('.channel_item[name="1"]');
        for (var i = 0; i < item.length; i++) {
            var channel_id = $('.channel_item[name="1"]:eq('+i+')').attr("id").split("_")[1];
            var channel_Name = $('.channel_item[name="1"]:eq('+i+')').parent().find("label").text();
            var tagName = $('.channel_item[name="1"]:eq('+i+')').parent().parent().prop("tagName");
            var fasongqi_id = "";
            if(tagName == "DIV"){
            	fasongqi_id =$('.channel_item[name="1"]:eq('+i+')').parent().parent().parent().parent().parent().find(".fasongqi_type_btn").attr("id").split("_")[1];
            }else{
            	fasongqi_id =$('.channel_item[name="1"]:eq('+i+')').parent().parent().parent().parent().find(".fasongqi_type_btn").attr("id").split("_")[1];
            }
             var data=channel_id;
            selectData.push(data);
            selectNameData.push(channel_Name);
            var index = fsqData.indexOf(fasongqi_id);
            if(index<0){
            	fsqData.push(fasongqi_id);
            	channelData.push("@"+channel_id);
            }else{
            	channelData.push(channel_id);
            }
        }
        
        $('.setNet').val(selectNameData);
        $('#channelIds').val(selectData);
        getTimeSet(fsqData);
        var netWorkType = $("#netWorkType").val();
        if(netWorkType == "quick"){
        	var typeId = $("#adTypeSelect").val();
        	var networkIds = fsqData.join(",");
        	var channelIds = channelData.join(",");
        	var startDate = $("#startDate").val();
        	var endDate = $("#endDate").val();
        	var startHour = $("#startHour").val();
        	var startMinutes = $("#startMinutes").val();
        	var startSecond = $("#startSecond").val();
        	var endHour = $("#endHour").val();
        	var endMinutes = $("#endMinutes").val();
        	var endSecond = $("#endSecond").val();
        	var post_data = {
        			"networkIds" : networkIds,
        			"channelIds" : channelIds,
        			"typeId" : typeId,
        			"startDate" : startDate,
        			"startHour" : startHour,
        			"startMinutes" : startMinutes,
        			"startSecond" : startSecond,
        			"endHour" : endHour,
        			"endMinutes" : endMinutes,
        			"endDate" : endDate,
        			"endSecond" : endSecond
        	}; 
        	var psData = JSON.stringify(post_data);
        	/*getNCLimit(psData);*/
        }
        return selectData;
    }
    
    function getTimeSet(fsqData){
    	var networkIds = fsqData.join(",");
    	var post_data = {"networkIds":networkIds}; 
	       var psData = JSON.stringify(post_data);
	    	$.ajax({
	    		 type:"post",
	             async: false,
	             url:host+"/adv/combo/getTimeSet",
	             data:psData,
	             contentType:"application/json; charset=UTF-8",
	             dataType:"json",
	             success:function(data){
	            	 if(data!=null && data != ""){
	            		$("#paramValue").val(data.paramValue);
	            	 }else{
	            		$("#paramValue").val(120); 
	            	 }
	             }
	    }); 
    }

    /*
     * 修改时回显已被选择频道*/
    function get_selectedChannel(ad_id){
    	$(".channel_content_ul").find(".fasongqi_type_content").css("display","none");
    	$(".channel_content_ul").find(".fasongqi_type_btn").attr("name","0");
    	$(".channel_content_ul").find(".fasongqi_type_btn").css("background-image","url('../../static/images/icon/ic_open.png')");
    	$(".channel_content_ul").find(".channel_type_list").attr("name","0");
    	 var startDate=getPlayTime().startDate;
         var endDate=getPlayTime().endDate;
         var startHour=getPlayTime().startTime.split(":")[0];
       	 var startMinute=getPlayTime().startTime.split(":")[1];
       	 var startSecond=getPlayTime().startTime.split(":")[2];
       	 var endHour=getPlayTime().endTime.split(":")[0];
       	 var endMinute=getPlayTime().endTime.split(":")[1];
       	 var endSecond=getPlayTime().endTime.split(":")[2];
       	 var typeId=$("#adTypeSelect").children('option:selected').val();
       	var o = {
       			"typeId":typeId,
    			"comboId" : ad_id,
    			"startDate":startDate,
    			"endDate":endDate,
    			"startHour":startHour,
    			"startMinute":startMinute,
    			"startSecond":startSecond,
    			"endHour":endHour,
    			"endMinute":endMinute,
    			"endSecond":endSecond
    		};
    	 
        var adId = JSON.stringify(o);
    	$.ajax({
    		 type:"post",
             async: false,
             url:host+"/adv/combo/find_networkby_comboId",
             data:adId,
             contentType:"application/json; charset=UTF-8",
             dataType:"json",
             success:function(data){
            	 var advTypeId=$("#adTypeSelect").children('option:selected').val();
            	 $.each(data,function(commentIndex,comment){	
            		 var selectedChannel_data=[];
            		 var networkId="f_"+comment["networkId"];
            		 var typeId=comment["typeId"];
            		 selectedChannel_data=comment["channelId"];
            		 $(".channel_content_ul").find('.fasongqi_type_btn[id='+networkId+']').attr("name","1");
            		 $(".channel_content_ul").find('.fasongqi_type_btn[id='+networkId+']').css("background-image","url('../../static/images/icon/ic_close.png')");
                	 $(".channel_content_ul").find('.fasongqi_type_btn[id='+networkId+']').parent().css("display","block");
                	 $.each(selectedChannel_data,function(commentIndex,comment){
                		 var channelId="c_"+comment["channelId"];
                		 if(advTypeId==typeId){
                    		 $(".channel_content_ul").find('.fasongqi_type_btn[id='+networkId+']').parent().find('.channel_item[id='+channelId+']').removeClass("channel_disabled");
                    		 $(".channel_content_ul").find('.fasongqi_type_btn[id='+networkId+']').parent().find('.channel_item[id='+channelId+']').attr({"name":"1","disabled":false});
                    		 $(".channel_content_ul").find('.fasongqi_type_btn[id='+networkId+']').parent().find('.channel_item[id='+channelId+']').addClass("input-checked");
                		 }
                	 });
            		 
            	 });
             },
             error:function(){
             }
    	});
    }
    
    /*
     * 快速发布返回时频道选中
     * */
    function  setQuickSelectedChannel(){
    	var channelIds = $("#channelIds").val();
    	var clds = channelIds.split(",");
    	for(var i=0;i<clds.length;i++){
    		var cdv = "c_"+clds[i];
    		var fdv = "f_"+clds[i];
    		//发送器选中
    		$("#"+cdv).removeClass("channel_disabled");
    		$("#"+cdv).attr({"name":"1","disabled":false});
    		$("#"+cdv).addClass("input-checked");
    		
    		//展开
    		$("#"+fdv).attr("name","1");
    		$("#"+fdv).css("background-image","url('../../static/images/icon/ic_close.png')");
    		$("#"+fdv).parent().css("display","block");	 		
    	}
    }
    
    /*
     * 修改时第一次加载给频道数据赋值，防止提交数据有可能为空
     * */
    function channelFirstLoad(){
      	var ad_id =$("#comboId").val();
      	if(ad_id!==""){
        	var post_data={"comboId":ad_id};
            var adId = JSON.stringify(post_data);
        	$.ajax({
       		 type:"post",
                async: false,
                url:host+"/adv/combo/find_networkby_comboId",
                data:adId,
                contentType:"application/json; charset=UTF-8",
                dataType:"json",
                success:function(data){
                     var selectedChannelData=[];
                     var selectedChannelNameData=[];
	               	 $.each(data,function(commentIndex,comment){
	               		var selectedChannel_data=comment["channelId"];
	               		 $.each(selectedChannel_data,function(commentIndex,comment){
	               			selectedChannelData.push(comment["channelId"]); 
	               			selectedChannelNameData.push(comment["channelName"]); 
	               		 });      		 
	            	 });
	               	 $('.setNet').val(selectedChannelNameData);
        	         $('#channelIds').val(selectedChannelData);
                	}
                });
      	}
    }
    channelFirstLoad();

    /*
     * 频道加载*/
    function get_channelData(){
    	var ad_id =$("#comboId").val();
        var startDate=getPlayTime().startDate;
        var endDate=getPlayTime().endDate;
    	var startTime=getPlayTime().startTime;
    	var endTime=getPlayTime().endTime;
    	var netWorkType = $("#netWorkType").val();
    	if(startDate!=""&&endDate!=""&&startTime!=""&&endTime!=""){
        	if(ad_id==""){
        		get_channel();
        		banClick();
        		if(netWorkType == "quick" ){
        			setQuickSelectedChannel();
        		}
        	}else{
        		get_channel();
        		get_selectedChannel(ad_id);
        		banClick();
        		judgeStatus(); 
        	}
    	}else{
    		return;
    	}
    }
    
    function init(){
    	var startHour = document.getElementById("startHour");  
    	var startMinute = document.getElementById("startMinute");  
    	var startSecond = document.getElementById("startSecond");  
    	var endHour = document.getElementById("endHour");  
    	var endMinute = document.getElementById("endMinute");  
    	var endSecond = document.getElementById("endSecond");  
 
    	for (var i= 0; i< 24; i++) {
    		var str="";
    		if(i<10)
    			str="0"+i;
    		else
    			str=i;
    			
    		startHour.options.add(new Option(str,i));
    		endHour.options.add(new Option(str,i));
		}
    	for (var i= 0; i< 60; i++) {
    		var str="";
    		if(i<10)
    			str="0"+i;
    		else
    			str=i;
    			
    		startMinute.options.add(new Option(str,i));
    		startSecond.options.add(new Option(str,i));
    		endMinute.options.add(new Option(str,i));
    		endSecond.options.add(new Option(str,i));
		}
    }
    
 
 
    
    $('.channel_content .btn_submit').click(function(){
        $('.channel_content').css("display","none");
		$(".setNet-errInfo").text("");
         get_selectData();
         $('.setNet').attr("name","1");
     });
     $('.channel_content .btn_close').click(function(){
         $('.channel_content').css("display","none");
         $('.setNet').attr("name","0");
     });
     //频道进行验证
     function IsValidated(group) {
         var isValid = true;
         group.find(':input').each(function (i, item) {
             if (!$(item).valid())
                 isValid = false;
         }); 
         group.find('label[class="error"]').css("display","none");
         return isValid;
     }
     //日期进行验证
     function setDataValidated(group) {
         var isValid = true;
         group.find(':input').each(function (i, item) {
             if ($(item).val()==""){
                 isValid = false; 
             }
         }); 
         return isValid;
     }
     //频道设置
     $('.setNet').click(function(){
    	 var sendMode = $("#sendModeYG").val();
         validTime();
         if(sendMode =="1"){
        	 if(IsValidated($(".adTypeSelect"))&&setDataValidated($(".setPlayStartTime"))&&setDataValidated($(".setPlayEndTime"))
        			 &&setDataValidated($(".setStartTime"))&&setDataValidated($(".setEndTime"))){
        		 $(".info-messages p").text("");
        		 if($(this).attr("name")=="1"){
                     $('.channel_content').css("display","block");
        		 }else{
                	 get_channelData();
                     $('.channel_content').css("display","block"); 
        		 }
                 return;
        	 }
             if(IsValidated($(".adTypeSelect"))==false){
        		 $(".adTypeSelect").find(".info-messages p").text(accipiter.getLang_(messageLang,"advTypeSelect"));
        	 }
             if(IsValidated($(".adTypeSelect"))==true){
        		 $(".adTypeSelect").find(".info-messages p").text("");
        	 }
             if(setDataValidated($(".setPlayStartTime"))==false){
            	 $(".setPlayStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startDate"));
        	 }
             if(setDataValidated($(".setPlayStartTime"))==true){
            	 $(".setPlayStartTime").find(".info-messages p").text("");
        	 }
             if(setDataValidated($(".setPlayEndTime"))==false){
            	 $(".setPlayEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endDate"));
        	 }
             if(setDataValidated($(".setEndTime"))==true){
            	 $(".setEndTime").find(".info-messages p").text("");
        	 }
             if(setDataValidated($(".setStartTime"))==false){
            	 $(".setStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startTime"));
        	 }
             if(setDataValidated($(".setStartTime"))==true){
            	 $(".setStartTime").find(".info-messages p").text("");
        	 }
             if(setDataValidated($(".setEndTime"))==false){
            	 $(".setEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endTime"));
        	 }
             if(setDataValidated($(".setEndTime"))==true){
            	 $(".setEndTime").find(".info-messages p").text("");
        	 }
             if(IsValidated($(".adTypeSelect"))==false&&setDataValidated($(".setPlayStartTime"))==false&&setDataValidated($(".setPlayEndTime"))==false
            		 &&setDataValidated($(".setStartTime"))==false&&setDataValidated($(".setEndTime"))==false){
        		 $(".info-messages p").text("");
            	 $(".adTypeSelect").find(".info-messages p").text(accipiter.getLang_(messageLang,"advTypeSelect"));
        		 $(".setPlayStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startDate"));
        		 $(".setPlayEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endDate"));
        		 $(".setStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startTime"));
        		 $(".setEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endTime"));
             } 
    	 }else{
             $('.channel_content').css("display","block");  
    	 }
     });
     
   /*-------data绑定事件------------------*/
     $('.channel_content_ul').on("click","input",function(e){
         var dom=$(this).attr("class");
         var control=$(this).attr("name");
         if($(this).hasClass("fasongqi")){
             if(control=="0"){
                 $(this).parent().find('input[name="0"]').addClass("input-checked");
                 $(this).parent().find('input[name="0"]').attr("name","1");
                 return;
             }
             if(control=="1"){
                 $(this).parent().find('input[name="1"]').removeClass("input-checked");
                 $(this).parent().find('input[name="1"]').attr("name","0");
                 return;
             }
         }
         if($(this).hasClass("channel_type")){
             if(control=="0"){
                 $(this).parent().find('input[name="0"]').addClass("input-checked");
                 $(this).parent().find('input[name="0"]').attr("name","1");
                 return;
             }
             if(control=="1"){
                 $(this).parent().find('input[name="1"]').removeClass("input-checked");;
                 $(this).parent().find('input[name="1"]').attr("name","0");
                 return;
             }
         }
         if($(this).hasClass("channel_item")){
        	 rem.push($(this).parent().parent().find(".channel_list").index($(this).parent()));
        	   if(e.shiftKey){
        		 var iMin =  Math.min(rem[rem.length-2],rem[rem.length-1]);
               var iMax =  Math.max(rem[rem.length-2],rem[rem.length-1]);
               for(var i=iMin;i<=iMax;i++){
            	 var node =  $(this).parent().parent().find(".channel_list:eq("+i+")").find("input[type=button]");
            	 if(node.attr("name")=="0"){
              	    $(this).parent().parent().find(".channel_list:eq("+i+")").find("input[type=button]").attr("name","1");
              	    $(this).parent().parent().find(".channel_list:eq("+i+")").find("input[type=button]").attr("class","channel_item input-checked");
            	 }
               }
    	   }else{
    		   if(control=="0"){
    			   $(this).attr("name","1");
    			   $(this).addClass("input-checked");
    			   return;
    		   }
    		   if(control=="1"){
    			   $(this).attr("name","0");
    			   $(this).removeClass("input-checked");
    			   return;
    		   }    		   
    	   } 
         }
         if($(this).hasClass("fasongqi_type_btn")){
             if($(this).attr("name")=="1"){
                 $(this).attr("name","0");
                 $(this).css("background-image","url('../../static/images/icon/ic_open.png')");
                 $(this).parent().find(".fasongqi_type_content").css("display","none");
                 $(this).parent().find(".channel_type_list").css("display","none");
                 return;
             }
             if($(this).attr("name")=="0"){
                 $(this).attr("name","1");
                 $(this).css("background-image","url('../../static/images/icon/ic_close.png')");
                 $(this).parent().find(".fasongqi_type_content").css("display","block");
                 $(this).parent().find(".channel_type_list").css("display","block");
                 return;
             }   
         }
     });
     function getPlayTime(){
    	 var startDate=$("#validStartTime").val();
    	 var endDate=$("#validEndTime").val();
    	 var startTime=$("#startTime").val();
    	 var endTime=$("#endTime").val();
 /*   	 var startHour=$("#startHour").val();
    	 var startMinute=$("#startMinute").val();
    	 var startSecond=$("#startSecond").val();
    	 var endHour=$("#endHour").val();
    	 var endMinute=$("#endMinute").val();
    	 var endSecond=$("#endSecond").val();
    	 var startTime="";
    	 var endTime="";
    	 if(startMinute==""||startMinute==""||startSecond==""){
    		 startTime="";
    	 }else{
    		 startTime=startHour+":"+startMinute+":"+startSecond;
    	 }
    	 if(endHour==""||endMinute==""||endSecond==""){
    		 endTime="";
    	 }else{
    		 endTime=endHour+":"+endMinute+":"+endSecond;
    	 }*/
    	 return {startDate:startDate,endDate:endDate,startTime:startTime,endTime:endTime};
     }
     /*-------------判断是否相关----------------*/
	   var a=document.getElementById("isFlag");
	   var b=a.options[a.selectedIndex].value; 
		if(b==1){
			if($("#comboId").val()!=""){
				
			}
			document.getElementById( "channel").style.display= "block";
			document.getElementById( "notchannel").style.display= "none";
			var sendModeYG = $("#sendModeYG").val();
			if( sendModeYG== "2"){
				 $("#YGArea").css("display","block");
				 $("#selDisResultYG").css("display","block");
			 }else{
				 $("#YGArea").css("display","none");
				 $("#selDisResultYG").css("display","none");
			 }
		}else{
			document.getElementById( "notchannel").style.display= "block";
			document.getElementById( "channel").style.display= "none";
			checkModeWG();
		}
		
		$('#isFlag').change(function(){
			var comboId = $("#id").val();
			 if(comboId == null || comboId == ""){				 					 
				 $("#area").val("");
				 $("#selArea").val("");
				 $("#wgarea").val("");
				 $("#ygarea").val("");
			 }
			clearNet();
		});
		$('#set_startDate,#set_stopDate').change(function(){
			var start_icon=parseInt($('#set_startDate').children('option:selected').val());
			var stop_icon=parseInt($('#set_stopDate').children('option:selected').val());
			if(stop_icon<start_icon){
				$('#setDate_error').css("visibility","visible");
				$('#setDate_error').html("截止日期要比开始日期大");
				return;				
			}else{
				$('#setDate_error').css("visibility","hidden");
			}
		});
/*-------------------multiselect(下来匡多选)-----------------*/
	    $(".selectData input,.control_icon").on("click",function(){
	    	if($(".selectData input").attr("disabled")==undefined){
		    	if($(this).attr("name")=="0"){
		    		$(".selectData input,.control_icon").attr("name","1");
			        $(".multiselect").css("display","block");
			        $(".selectContent .control_icon").css("background-position"," -18px 1px");
		    	}else{
		    		$(".selectData input,.control_icon").attr("name","0");
			        $(".multiselect").css("display","none");
			        $(".selectContent .control_icon").css("background-position"," 0px 1px");
		    	}
	    	}else{
	    		return;
	    	}
	    });
	    $(".multiselect").on("click","input",function(){
	        var dom=$(this).attr("class");
	        if(dom=="option"){
	            if($(this).attr("name")=="0"){
	                $(this).attr("name","1");
	                $(this).css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
	            }else{
	                $(this).attr("name","0");
	                $(this).css("background","url('../../static/images/icon/ic_checkbox_false.png')");
	            }
	            judgeSelectAll();
	            return;
	        }
	        if(dom=="submit"){
	            getSelectedData();
	            $(".multiselect").css("display","none");
	            return;
	        }
	        if(dom=="btnClear"){
	            $(".multiselect").css("display","none");
	            return;
	        }
	        if(dom=="selectAll"){
	            if($(this).attr("name")=="0"){
	                $(this).attr("name","1");
	                $(this).css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
	                $(".multiselect .option").attr("name","1");
	                $(".multiselect .option").css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
	            }else{
	                $(this).attr("name","0");
	                $(this).css("background","url('../../static/images/icon/ic_checkbox_false.png')");
	                $(".multiselect .option").attr("name","0");
	                $(".multiselect .option").css("background","url('../../static/images/icon/ic_checkbox_false.png')");
	            }
	            return;
	        }
	    });
	    function getSelectedData(){
	        var option=$('.multiselect .option[name="1"]');
	        var len=option.length;
	        var postData="";
	        for(var i=0;i<len;i++){
	            var data=option[i].parentNode.getAttribute("value");
	        	if(i==len-1){
	        		postData+=data;
	        	}else{
		            postData+=data+",";
	        	}
	        }
	        $("#weekStart").val(postData);
	        $("#weekStart").attr("readonly",postData);
	    }
	    function judgeSelectAll(){
	        var option=$('.option[name="1"]');
	        if(option.length!=$(".option").length){
	            $(".multiselect #selectAll").attr("name","0");
	            $(".multiselect #selectAll").css("background","url('../../static/images/icon/ic_checkbox_false.png')");
	        }else{
	            $(".multiselect #selectAll").attr("name","1");
	            $(".multiselect #selectAll").css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
	        }
	    }
	    function multiselectFirstLoad(data){
			var startWeek=$("#week").attr("value");
			var data=startWeek.split(",");
	        if(data.length!=0){
	            $.each(data,function(commentIndex,comment){
	                var Id="option-"+comment;
	                $(".multiselect").find('li[id='+Id+']').find("input").attr("name","1");
	                $(".multiselect").find('li[id='+Id+']').find("input").css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
	            });
	            judgeSelectAll();
	        }
	        if(data.length==0){
	            return;
	        }
	        $("#weekStart").val(startWeek);
	        $("#weekStart").attr("readonly",startWeek);
	        
	    };
	    multiselectFirstLoad();
	/*-------------------网络设置-----------------*/
	    function judgeStatus(){
	    	var status=$("#status").find("option:selected").val();
	    	if(status==2){
	    		$(".control-group").find("input").attr("disabled",true);
	    		$(".control-group").find("select").attr("disabled",true);
	    		$(".control-group .setNet").attr("disabled",false);
	    		$(".fasongqi_type_content").find("input").attr("disabled",true);
	    		$("#status").attr("disabled",false);
	    	}
	    }
	    function getInvalidNetWork(){
	    	var isFlag = $("#isFlag").val();
	    	var sendMode = "";
	    	if(isFlag == "0"){
	    		sendMode = $("#sendModeWG").val();
	    	}else{
	    		sendMode = $("#sendModeYG").val();
	    	}
	    	if(sendMode == "1"){
	    		var typeId=$("#typeId").find("option:selected").val();
		        var netWorkType = $("#netWorkType").val();
		        var startDate=$("#startDate").val();
		        var endDate=$("#endDate").val();

		        var post_data;
		        if(netWorkType=="quick"){
		        	var advertiserId = $("#advertiser_id").val();
		        	var chlidType = $("#adcombo_chlidType").val();
		        	if(typeId == 7 || typeId ==8){
		        		if(chlidType == null || chlidType ==""){
		        		  $("#adcombo_id_chlidType").find('label[class="info-messages"] p').text(accipiter.getLang_(messageLang,"advChildTypeSelect"));	
		        		  return false;
		        		}else{
		        		  $("#adcombo_id_chlidType").find('label[class="info-messages"] p').text("");
		        		}
		        	}
		        	if(startDate==null || startDate ==""){
		    			document.getElementById("startdate_span").innerText = accipiter.getLang_(messageLang,"adv.start.date");
		    			return false;
		        	}else{
		        		document.getElementById("startdate_span").innerText="";
		        	}
		        	if(endDate==null || endDate==""){
		    			document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.end.date");
		        		return false;
		        	}else{
		        		document.getElementById("enddate_span").innerText="";
		        	}       
		        	post_data={"typeId":typeId,"chlidType":chlidType,"startDate":startDate,"endDate":endDate,"sendMode":sendMode,"advertiserId":advertiserId};
		        }else{
			        if(startDate==null || startDate =="" || endDate==null || endDate=="" || sendMode == null || sendMode == ""){
			        	return false;
			        }
			    	post_data={"typeId":typeId,"startDate":startDate,"endDate":endDate,"sendMode":sendMode};
		        }
		        var psData = JSON.stringify(post_data);
		    	$.ajax({
		    		 type:"post",
		             async: false,
		             url:host+"/adv/combo/find_network_by_typeId",
		             data:psData,
		             contentType:"application/json; charset=UTF-8",
		             dataType:"json",
		             success:function(data){
		            	 $(".networkContent").html("");
		            	 var html='<ul class="network_content_ul">';
		            	 $.each(data,function(commentIndex,comment){
		            		 var netWorkId=comment["id"];	
		            		 if(comment["invalid"]){
		            			 html+='<li id='+comment["id"]+'><input type="button" class="option" name="0" id='+commentIndex+'><label for='+commentIndex+' title='+comment["networkName"]+'>'+comment["networkName"]+'('+comment["area"]+')</label>'; 
		            		 }else{
		            			 html+='<li id='+comment["id"]+'><input type="button" class="option noselected" name="2" disabled=true id='+commentIndex+'><label for='+commentIndex+' title='+comment["networkName"]+'>'+comment["networkName"]+'('+comment["area"]+')</label>'; 
		            		 }            		            	
		            	 });
		            	 html +='</ul>';
		            	 $(".networkContent").append(html); 
		 				$(".networkContent").css("display","block");
		             }
		    });
	    	}
	    	
	   }
	    function showSelectedNetWork(data){
	    	var Data=$("#networkIds").val();
	    	var netdata=Data.split(",");
            var isFlag = $("#isFlag").val();
            var sendMode = "";
            if(isFlag == "0"){
                sendMode = $("#sendModeWG").val();
            }else{
                sendMode = $("#sendModeYG").val();
            }
                $.each(netdata,function(commentIndex,comment){
                    var netWorkId=comment;
                    $(".networkContent ul").find('li[id='+netWorkId+']').find("input").css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
                    $(".networkContent ul").find('li[id='+netWorkId+']').find("input").attr({"name":"1"});
                    if(sendMode == "1"){
                    $(".networkContent ul").find('li[id='+netWorkId+']').find("input").removeClass("noselected");
                    $(".networkContent ul").find('li[id='+netWorkId+']').find("input").removeAttr("disabled");
                    }
                });
	    }
		 $(".networkContent").on("click","input",function(){
		        var dom=$(this).attr("class");
		        var type = $("#netWorkType").val();
		        var this1 = $(this);
		    	var isFlag = $("#isFlag").val();
				 var startDate = $("#startDate").val();
				 var endDate = $("#endDate").val();
				 var typeId = $("#typeId").val();
		        var sendMode = "";
		        if(isFlag == "0"){
		           sendMode = $("#sendModeWG").val();
		        }else{
		       	   sendMode = $("#sendModeYG").val();
		        }
		        if(dom=="option"){
		            if(this1.attr("name")=="0"){
				        if(type=="quick"){
				        	var curnetId = this1.parent().attr("id");
				        	var typeId = $("#typeId").val();
				        	var networkIds=curnetId;

				        	var option=$('.networkContent .option[name="1"]');
					        var len=option.length;
					        for(var i=0;i<len;i++){
					            var data=option[i].parentNode.getAttribute("id");
					        		networkIds+=","+data;
					        }
					       var post_data = {"networkIds":networkIds,"typeId":typeId,"startDate":startDate,"endDate":endDate,"sendMode":sendMode}; 
					       var psData = JSON.stringify(post_data);
					    	$.ajax({
					    		 type:"post",
					             async: false,
					             url:host+"/adv/combo/checkComboIsConflict",
					             data:psData,
					             contentType:"application/json; charset=UTF-8",
					             dataType:"json",
					             success:function(data){
					            	 if(data!=null){
					            		 var count = data.count;					            		 
					            		 if(count==0){
					     	    			document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"networkConfig");
					            		 }else if(count ==-1){
					            			 $("#oldcomboId").val("");
					            			 document.getElementById("enddate_span").innerText ="";
					            			 this1.attr("name","1");
					            			 this1.css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
											 /*getNCLimit(psData);*/
					            		 }else{
					            			 $("#oldcomboId").val(data.id);
					            			 document.getElementById("enddate_span").innerText ="";
					            			 this1.attr("name","1");
					            			 this1.css("background","url('../../static/images/icon/ic_checkbox_ture.png')");					            			 
					            			 /*getNCLimit(psData);*/
					            		 }
					            	 }
					             }
					    }); 
					        
				        }else{
				        	 this1.attr("name","1");
	            			 this1.css("background","url('../../static/images/icon/ic_checkbox_ture.png')");					            			 	
				        }
		            }else{
		            	this1.attr("name","0");
		            	this1.css("background","url('../../static/images/icon/ic_checkbox_false.png')");
		            	this1.parent().parent().parent().find('input[class="network_type"]').attr("name","0");
		            	this1.parent().parent().parent().find('input[class="network_type"]').css("background","url('../../static/images/icon/ic_checkbox_false.png')");
                        if(type=="quick") {
                        	var networkIds = "";
                            this1.parent().parent().find("li input[name='1']").each(function () {
                                networkIds += $(this).parent().attr("id")+",";
                            });
                            if(null != networkIds && networkIds != ""){
                                networkIds = networkIds.substring(0,networkIds.lastIndexOf(",")-1);
                                var post_data = {"networkIds":networkIds,"typeId":typeId,"startDate":startDate,"endDate":endDate,"sendMode":sendMode};
                                var psData = JSON.stringify(post_data);
                                /*getNCLimit(psData);*/
                                document.getElementById("enddate_span").innerText="";
                            }
                        }
		            }
		            return;
		        }else if(dom=="network_type"){
		        	if(this1.attr("name")=="0"){
		        		this1.attr("name","1");
		        		this1.css("background","url('../../static/images/icon/ic_checkbox_ture.png')");	
		        		this1.parent().find("li input[name='0']").each(function(){
		        			$(this).attr("name","1");
		        			$(this).css("background","url('../../static/images/icon/ic_checkbox_ture.png')");	
		        		});
                        if(type=="quick") {
                            var networkIds = "";
                            this1.parent().find("li input[name='1']").each(function () {
                                networkIds += $(this).parent().attr("id") + ",";
                            });
                            if (null != networkIds && networkIds != "") {
                                networkIds = networkIds.substring(0, networkIds.lastIndexOf(",") - 1);
                                var post_data = {
                                    "networkIds": networkIds,
                                    "typeId": typeId,
                                    "startDate": startDate,
                                    "endDate": endDate,
                                    "sendMode": sendMode
                                };
                                var psData = JSON.stringify(post_data);
                                /*getNCLimit(psData);*/
                                document.getElementById("enddate_span").innerText = "";
                            }
                        }
		        	}else{
		        		this1.attr("name","0");
		        		this1.css("background","url('../../static/images/icon/ic_checkbox_false.png')");	
		        		this1.parent().find("li input[name='1']").each(function(){
		        			$(this).attr("name","0");
		        			$(this).css("background","url('../../static/images/icon/ic_checkbox_false.png')");	
		        		});
                        /*$("#sdMaxNC").val("");
                        $("#hdMaxNC").val("");*/
		        	}
		        }
		    });
		 
		 function getNCLimit(psData){
		    	$.ajax({
		    		 type:"post",
		             async: false,
		             url:host+"/adv/adelement/getNCLimit",
		             data:psData,
		             contentType:"application/json; charset=UTF-8",
		             dataType:"json",
		             success:function(data){
		            	 if(data!=null){
		            		 $("#sdMaxNC").val(data.sdMaxNC);
		            		 $("#hdMaxNC").val(data.hdMaxNC);
		            	 }
		             }
		    	});
		 }
		 
			$('#startDate').blur(function() {
				clearNet();
			});

			$('#endDate').blur(function() {
				clearNet();
			});
			$('#advertiser_id').change(function(){
				clearNet1();
			});
			
		function clearNet(){
				/* 清空频道相关选项*/
			checkModeWG();
			checkModeYG();
			 $(".networkContent").html("");
			/*$("#sdMaxNC").val("");
			 $("#hdMaxNC").val("");*/
				 $("#adTypeSelect").find('option:selected').attr("selected",false);
				 $("#adTypeSelect").select2();		 
				 var typeId=$(this).children('option:selected').val();
				 if(typeId!==""){
					 $('.control-network').css("display","block");
		           	    getInvalidNetWork();
			    		var ad_id =$("#comboId").val();
			    		if(ad_id!=""){
			    			if(typeId==TypeId){
				    			showSelectedNetWork();
			    			}
			    		}
				 }else{
					 $('.control-network').css("display","none");
				 }
			}
		function clearNet1(){
			/* 清空频道相关选项*/	 
			 var typeId=$(this).children('option:selected').val();
			 $(".networkContent").html("");
			 /*$("#sdMaxNC").val("");
			 $("#hdMaxNC").val("");*/
			 if(typeId!==""){
				 $('.control-network').css("display","block");
	           	    getInvalidNetWork();
		    		var ad_id =$("#comboId").val();
		    		if(ad_id!=""){
		    			if(typeId==TypeId){
			    			showSelectedNetWork();
		    			}
		    		}
			 }else{
				 $('.control-network').css("display","none");
			 }
		}
		
		$("#sendModeWG").change(function(){
			$(".networkContent").html("");
			checkModeWG();
		 });
		
		$("#sendModeYG").change(function(){
			$(".networkContent").html("");
			checkModeYG();
		 });
		
		function checkModeWG(){
			var sendModeWG = $("#sendModeWG").val();
			var netWorkType = $("#netWorkType").val();
			if( sendModeWG== "2"){
				 $("#WGArea").css("display","block");
				 var comboId = $("#id").val();
				 if((netWorkType != "quick") && (comboId == null || comboId == "")){
					 $("#selDataResultWG").html("");
					 $("#selDisResultWG").css("display","block");					 					 
				 }
			 }else{
                 $("#area").val("");
				 $("#selArea").val("");
				 $("#wgarea").val("");
				 $("#WGArea").css("display","none");
				 $("#selDataResultWG").html("");
				 $("#selDisResultWG").css("display","none");
				 getInvalidNetWork();
			 }
		}
		
		function checkModeYG(){
			$('.setNet').val("");
			$('#channelIds').val("");
			$('.channel_content_ul').html("");
			var sendModeYG = $("#sendModeYG").val();
			if( sendModeYG== "2"){
					$("#YGArea").css("display","block");
					$("#selDataResultYG").html("");
					$("#selDisResultYG").css("display","block");					
			 }else{
                 $("#area").val("");
                 $("#selArea").val("");
				 $("#YGArea").css("display","none");
				 $("#selDataResultYG").html("");
				 $("#selDisResultYG").css("display","none");
			 }
		}
		
		 $("#typeId").change(function(){
			 clearNet();
		 });
		 $("#adcombo_chlidType").change(function(){
			 clearNet();
		 });
		 $("#adTypeSelect").change(function(){
			    var v = $(this).val();
			    if(v!=null && v!=""){
			    	$(this).find("label[class='info-messages'] p").text("");
			    }
				/* 清空频道不相关选项*/
				 $("#typeId").find('option:selected').attr("selected",false);
				 $("#typeId").select2();
				/* 广告类型发生变化要清空频道数据*/
				 $('.setNet').val(accipiter.getLang_(messageLang,"channel.select"));
				 $('.setNet').attr("name","0");
				 $('#channelIds').val("");
				 $(".channel_content").css("display","none");
				 /*get_channelData();*/
		 });
		 //判断添加还是修改
 		var ad_id =$("#comboId").val();
		if(ad_id!=""){
			TypeId=$("#typeId").children('option:selected').val();
			getInvalidNetWork();
			showSelectedNetWork();
			judgeStatus();
			$('.control-network').css("display","block");
		}
		var netWorkType = $("#netWorkType").val();
		if(netWorkType == "quick"){
			var isFlag = $("#isFlag").val();
			if(isFlag == "1"){
    			$('.setNet').val($("#setNet1").val());
   	            $('#channelIds').val($("#channelIds1").val());
			}
			var typeId1 = $("#adTypeId").val();
			if(typeId1 == 4 || typeId1 == 5){
   	            $("#showCount").val($("#acCount").val());
			}else{
				$("#showCount").val("");
			}
			
			if(typeId1==5){
				var sd_Fx = $("#sd_Fx").val();
				var hd_Fx = $("#hd_Fx").val();
				if(sd_Fx ==0){
					$("#sdFx").val(0).select2();
				}else if(sd_Fx ==1){
					$("#sdFx").val(1).select2();
				}else if(sd_Fx ==2){
					$("#sdFx").val(2).select2();
				}else if(sd_Fx ==3){
					$("#sdFx").val(3).select2();
				}
				if(hd_Fx ==0){
					$("#hdFx").val(0).select2();
				}else if(hd_Fx ==1){
					$("#hdFx").val(1).select2();
				}else if(hd_Fx ==2){
					$("#hdFx").val(2).select2();
				}else if(hd_Fx ==3){
					$("#hdFx").val(3).select2();
				}
			}
			if(isFlag=="0"){
				TypeId=$("#typeId").children('option:selected').val();
				getInvalidNetWork();
				showSelectedNetWork();
				judgeStatus();
				$('.control-network').css("display","block");
			}
			
		}
});
