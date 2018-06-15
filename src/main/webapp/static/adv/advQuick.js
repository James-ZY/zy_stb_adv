	var host = accipiter.getRootPath();
	var control;
	$(document).ready(function() {
		checkType();
		$("#comboName").focus();
		$("#inputForm").validate({
			rules: {
 
				comboName: {remote: "${ctx}/adv/combo/checkComboName?operate=${operate}&oldComboName=" +encodeURIComponent('${adCombo.comboName}')}
			 
			},
			messages: {
				comboName: {remote:accipiter.getLang("adComboNameExist")}
			},
			submitHandler: function(form){
 				    function getNetId(){
				        var option=$('.networkContent .option[name="1"]');
				        var len=option.length;
				        var postData="";
				        for(var i=0;i<len;i++){
				            var data=option[i].parentNode.getAttribute("id");
				        	if(i==len-1){
				        		postData+=data;
				        	}else{
					            postData+=data+",";
				        	}
				        }
				        $("#networkIds").val(postData);
				        return postData;
				    }
				    getNetId();
				    var isFlag=$("#isFlag").find('option:selected').val(); 
					if(($("networkIds").val()==""&&isFlag=="0")||($("#channelIds").val()==""&&isFlag=="1")){
                        layer.open({
                            yes:function (index, layero) {
                                layer.close(index);
                            },
                            btn2:function (index, layero) {
                                layer.close(index);
                            }
                        });
						return false;
					}
					loading(accipiter.getLang("loading"));
					form.submit();
					$("#btnSubmit").attr({"disabled":true});
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text(accipiter.getLang("inputError"));
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		
		checkIsChannel();
		$("#btnSubmit").on("click",function(){
			var control = $("#isFlag").val(); 
			if(control==1){
				var len=$("#weekStart option:selected").length;
				var weekStart="";
				for(var i=0;i<len-1;i++){
					weekStart+=$('#weekStart option:selected:eq('+i+')').attr("value")+","
				}
				weekStart+=$('#weekStart option:selected:eq('+(len-1)+')').attr("value");
				$("#week").attr("value",$("#weekStart").val());
				var ad_id =$("#id").val();
				
				/* 与频道相关广告频道不能为空 */
				if($('#channelIds').val()==""){
					$(".setNet-errInfo").text(accipiter.getLang_(messageLang,"adv.nochannel"));
					return false;
				}
			}
		})
	});

	function checkIsChannel(){
	
		  var a=document.getElementById("isFlag");
		   var b=a.options[a.selectedIndex].value; 
		   control=b;
			if(b==1){
				document.getElementById( "channel").style.display= "block";
				document.getElementById( "notchannel").style.display= "none";
			    $('.playTime').css("display", "none");
			}else{
				document.getElementById( "notchannel").style.display= "block";
				document.getElementById( "channel").style.display= "none";
			    $("#sd_gd_fx").hide();
			    $("#hd_gd_fx").hide();
				$("#startTime").val("");
				$("#endTime").val("");
				$("#weekStart").val("");
				$("#weekEnd").val("");
				$("#showTime").val("");
				$("#intervalTime").val("");
				$("#showCount").val("");
				$("#channelIds").val("");
			}
	}

    //判断与频道相关的广告类型  显示 隐藏
    function checkType(){
        var a=document.getElementById("adTypeSelect");
        var b=a.options[a.selectedIndex].value;
        if(b == 5){
            $("#trackDiv").hide();
            $("#showTimeSet").show();
            $("#rollSet").show();
            $("#trackDiv").hide();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            $("#sdRange").show();
            $("#hdRange").show();
            getRangeInfo(b);
        }else if(b==2 || b==10){
            $("#trackDiv").hide();
            $("#showTimeSet").hide();
            $("#rollSet").hide();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            $("#sdRange").show();
            $("#hdRange").show();
            $("#showTime").val("");
            $("#showCount").val("");
            $("#pictureTimes").val("");
            $("#pictureInterval").val("");
            getRangeInfo(b);
        }else if(b==3){
            $("#trackDiv").hide();
            $("#showTimeSet").hide();
            $("#rollSet").hide();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            $("#sdRange").hide();
            $("#hdRange").hide();
            $("#showTime").val("");
            $("#showCount").val("");
            $("#pictureTimes").val("");
            $("#pictureInterval").val("");
        }else if(b==4){
            $("#pictureTimes").val("");
            $("#pictureInterval").val("");
            $("#trackDiv").show();
            $("#showTimeSet").show();
            $("#rollSet").hide();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            $("#sdRange").hide();
            $("#hdRange").hide();
            var trackMode = $("#trackMode").val();
            if(trackMode == 1){
                $("#trackDiv").hide();
                $("#sdTrack").hide();
                $("#hdTrack").hide();
            }
            if(null != id && id != ""){
                var trackMode = $("#trackMode").val();
                if(trackMode == 1){
                    getRangeInfo(b);
                }else{
                    getTrackInfo(b);
                }
            }
        }else{
            $("#trackDiv").hide();
            $("#showTimeSet").hide();
            $("#rollSet").hide();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            $("#sdRange").hide();
            $("#hdRange").hide();
            $("#showTime").val("");
            $("#showCount").val("");
            $("#pictureTimes").val("");
            $("#pictureInterval").val("");
        }
    }

    //获取广告范围
    function getRangeInfo(b) {
        var typeId = {"adTypeId":b};
        var PostData = JSON.stringify(typeId);
        var sdId = $("#sdId").attr("value");
        var hdId = $("#hdId").attr("value");
        var a=document.getElementById("adTypeSelect");
        $.ajax({
            url:accipiter.getRootPath()+"/adv/combo/find_range_by_adTypeId",
            async: false,
            type:"POST",
            data:PostData,
            contentType: "application/json; charset=utf-8",
            dataType : "json",
            success:function(data){

                var sdhtml='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                var hdhtml='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                $.each(data,function(commentIndex,comment){
                    if(comment.flag ==0){
                        if(sdId == comment.id){
                            sdhtml+='<option selected="selected" value='+comment.id+'>'+comment.range+'</option>';
                        }else{
                            sdhtml+='<option value='+comment.id+'>'+comment.range+'</option>';
                        }
                    }else{
                        if(hdId == comment.id){
                            hdhtml+='<option selected="selected" value='+comment.id+'>'+comment.range+'</option>';
                        }else{
                            hdhtml+='<option value='+comment.id+'>'+comment.range+'</option>';
                        }
                    }
                });
                $("#sdRange_id").html("");
                $("#sdRange_id").append(sdhtml);
                $("#sdRange_id").select2();
                $("#sdRange").show();

                $("#hdRange_id").html("");
                $("#hdRange_id").append(hdhtml);
                $("#hdRange_id").select2();
                $("#hdRange").show();
            }
        });
    }
    //获取轨迹列表
    function getTrackInfo(b) {
        var typeId = {"adTypeId":b};
        var PostData = JSON.stringify(typeId);
        var sdId = $("#sdTrackId").attr("value");
        var hdId = $("#hdTrackId").attr("value");
        var a=document.getElementById("adTypeSelect");
        $.ajax({
            url:accipiter.getRootPath()+"/adv/combo/find_track_by_adTypeId",
            async: false,
            type:"POST",
            data:PostData,
            contentType: "application/json; charset=utf-8",
            dataType : "json",
            success:function(data){

                var sdhtml='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                var hdhtml='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                $.each(data,function(commentIndex,comment){
                    if(comment.flag ==0){
                        if(sdId == comment.id){
                            sdhtml+='<option selected="selected" value='+comment.id+'>'+comment.track+'</option>';
                        }else{
                            sdhtml+='<option value='+comment.id+'>'+comment.track+'</option>';
                        }
                    }else{
                        if(hdId == comment.id){
                            hdhtml+='<option selected="selected" value='+comment.id+'>'+comment.track+'</option>';
                        }else{
                            hdhtml+='<option value='+comment.id+'>'+comment.track+'</option>';
                        }
                    }
                });
                $("#sdTrack_id").html("");
                $("#sdTrack_id").append(sdhtml);
                $("#sdTrack_id").select2();
                $("#sdTrack").show();

                $("#hdTrack_id").html("");
                $("#hdTrack_id").append(hdhtml);
                $("#hdTrack_id").select2();
                $("#hdTrack").show();
            }
        });
    }

    //判断是否为轨迹模式
    function  checkTrackMode() {
        var adType = $("#adTypeSelect").val();
        var a=document.getElementById("trackMode");
        var b=a.options[a.selectedIndex].value;
        if(b == 1){
            $("#sdRange").show();
            $("#hdRange").show();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            getRangeInfo(adType);
        }else{
            $("#sdRange").hide();
            $("#hdRange").hide();
            $("#sdTrack").show();
            $("#hdTrack").show();
            getTrackInfo(adType);
        }
    }
	
	/*判断展示时间和播放时间段之间关系  */
	function judgeTime (){
		var startHour=parseInt($("#startTime").val().split(":")[0]);
		var startMinute=parseInt($("#startTime").val().split(":")[1]);
		var startSecond=parseInt($("#startTime").val().split(":")[2]);
		var endHour=parseInt($("#endTime").val().split(":")[0]);
		var endMinute=parseInt($("#endTime").val().split(":")[1]);
		var endSecond=parseInt($("#endTime").val().split(":")[2]);
		var isFlag=$("#isFlag").find('option:selected').val(); 		
        if(isFlag == "1"){
        	var adtype = $("#adTypeSelect").val();
        	if(adtype ==4 || adtype ==5){
        		var intervalTime=parseInt($('.intervalTime').val());
        	    var showTime=parseInt($('.showTime').val());
        		var totalSecond=(endHour-startHour)*3600+(endMinute-startMinute)*60+endSecond-startSecond;
        		var showCount = Math.floor( (totalSecond+intervalTime)/(showTime+intervalTime));
        		if(showCount==0){
        			showCount=1;
        		}
        		$('#showCount').val(showCount);
        		if(startHour!=NaN && startMinute!=NaN && startSecond!=NaN && endHour!=NaN && endMinute!=NaN && endSecond!=NaN && showCount!=NaN && showTime!=NaN){
        			if(showTime>totalSecond){
        				 $("#btnSubmit").attr({"disabled":true});
        				 var text = accipiter.getLang("diplayTimeOutPlayTime");
        				 $("#time-beyond").text(text);
        			}else{
        				 $("#btnSubmit").attr({"disabled":false});
        				 $("#time-beyond").text("");
        			}
        		}
        	}
		}
		
	}
	/* 日期设置变化，清空数据 */
	function clearChannelData(){
		 $('.setNet').attr("name","0");
		 $('.setNet').val(accipiter.getLang_(messageLang,"channel.select"));
		 $(".channel_content").css("display","none");
		 $('#channelIds').val("");
	}
	
	var timeoutId = 0;
	    $('.showTime').off('keyup').on('keyup', function (event) {
	    	if($(this).val()!=""){
	    		var paramValue = $("#paramValue").val();
                var showTime = $(this).val();
                var intervalTime = $(".intervalTime").val();
                if(intervalTime ==""){
                	intervalTime = 0;
                }
                $(".intervalTime").attr("min",parseInt(paramValue)-parseInt(showTime)>1?parseInt(paramValue)-parseInt(showTime):1);
                if(parseInt(paramValue)-parseInt(showTime)<=parseInt(intervalTime)){
                	$(".intervalTime").parent().find('label[class="error"]').remove();
                }else{
                	$(".showTime").parent().find('label[class="error"]').remove();
                	$(".showTime").attr("min",parseInt(paramValue)-parseInt(intervalTime));
                }
		        clearTimeout(timeoutId);
		        timeoutId = setTimeout($.proxy(judgeTime), 100, event); // 100ms
	    	}
	    });
	    $('.intervalTime').off('keyup').on('keyup', function (event) {
	    	if($(this).val()!=""){
	    		var paramValue = $("#paramValue").val();
                var intervalTime = $(this).val();
                var showTime = $(".showTime").val();
                if(showTime ==""){
                	showTime = 0;
                }
                $(".showTime").attr("min",parseInt(paramValue)-parseInt(intervalTime)>1?parseInt(paramValue)-parseInt(intervalTime):1);
                if(parseInt(paramValue)-parseInt(intervalTime)<=parseInt(showTime)){
                	$(".showTime").parent().find('label[class="error"]').remove();
                }else{
                	$(".intervalTime").parent().find('label[class="error"]').remove();
                	$(".intervalTime").attr("min",parseInt(paramValue)-parseInt(showTime));
                }
		        clearTimeout(timeoutId);
		        timeoutId = setTimeout($.proxy(judgeTime), 100, event); // 100ms
	    	}
	    });
    
    function show(obj,id) { 
    	 var objDiv =id.getAttribute("id");
    	$("#"+objDiv).css("display","block"); 
    	id.parentElement.style.height="60px";
    	} 
    	function hide(obj,id) { 
        var objDiv =id.getAttribute("id");
    	$("#"+objDiv).css("display", "none"); 
    	id.parentElement.style.height="25px";
    	} 
    	
    	function onClickByStartTime(){
    		var endTime = $("#endTime").val();
    		var data = {"endTime":endTime};
    		return data;
    	}
    	function onClickByEndTime(){
    		var startTime = $("#startTime").val();
    		var data = {"startTime":startTime};
    		return data;
    	}
    	$('#startTime').blur(function() {
			judgeTime();
		});
		$('#endTime').blur(function() {
			judgeTime();
		});