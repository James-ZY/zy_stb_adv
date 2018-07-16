<%@ page contentType="text/html;charset=UTF-8" deferredSyntaxAllowedAsLiteral="true" %>
<%@ page import="com.gospell.aas.entity.adv.AdNetwork" %>
<%@ page import="java.util.List" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/common/style.css">
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<link rel="stylesheet" href="${ctxStatic}/layer/skin/myskin/style.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctxStatic}/layer/layer.js"></script>
    <script src="${ctxStatic}/common/language.js"></script>
    <script src="${ctxStatic}/common/validDate.js"></script>
    <script type="text/javascript" src="${ctxStatic}/common/areadata.js"></script>
    <script type="text/javascript" src="${ctxStatic}/common/auto_area.js"></script>
	<script type="text/javascript" src="${ctxStatic}/adcombojs/auto_channel.js"></script>


	<script type="text/javascript">
	var control;
	$(document).ready(function() {
        showChildType();
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
 				    
 				    function getSelArea(){
 				    	var val = '';
 						if ($('.sel_save_box').length > 0) {
 					        $('.sel_save_box').each(function () {
 					                val += $(this).data("code") + '-';
 					        });
 					    }
 					    if (val != '') {
 					        val = val.substring(0, val.lastIndexOf('-'));
 					    }
 					    console.log(val);
 					    $("#selAllArea").val(val);
 				    }
				    getNetId();
				    //getSelArea();
				    var isFlag=$("#isFlag").find('option:selected').val(); 
				    var messageInfo="";
					if($("#networkIds").val()==""&&isFlag=="0"){
		    			messageInfo=accipiter.getLang_(messageLang,"adv.nonetwork");
		    			layer.config({
		    		        content: '\<\div style="padding:25px 20px;text-align: center;"><i></i><span>'+messageInfo+'</span>\<\/div>',
		    		     });
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
					if($("#channelIds").val()==""&&isFlag=="1"){
						messageInfo=accipiter.getLang_(messageLang,"adv.nochannel");
						layer.config({
					        content: '\<\div style="padding:25px 20px;text-align: center;"><i></i><span>'+messageInfo+'</span>\<\/div>',
					     });
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
			}else{
				document.getElementById( "notchannel").style.display= "block";
				document.getElementById( "channel").style.display= "none";
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
        var id=$("#id").val();
        if(null == id || id == ""){
            /*$("#trackMode").find('option:selected').attr("selected",false);
            $("#trackMode").select2();*/
            $("#trackDiv").hide();
            $("#showTimeSet").hide();
            $("#sdTrack").hide();
            $("#hdTrack").hide();
            $("#sdRange").hide();
            $("#hdRange").hide();
        }
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
                $("#sdRange").show();
                $("#hdRange").show();
                getRangeInfo(b);
            }
            if(null != id && id != ""){
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
        		var Second=showCount*showTime;
        		var showCount = Math.floor( (totalSecond+intervalTime)/(showTime+intervalTime));
        		if(showTime==0){
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
	
    
    function show(obj,id) { 
    	 var objDiv =id.getAttribute("id");
    	$("#"+objDiv).css("display","block"); 
    	id.parentElement.style.height="45px";
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
    	function showChildType() {
            var typeId = $("#typeId").val();
            var childAdTypeId = $("#childAdTypeId").val();

            if(typeId == 8){
                var o ={"id":typeId};
                var data = JSON.stringify(o);
                getchlidType(data,childAdTypeId);
            }else{
                $('#adcombo_id_chlidType').css("display", "none");
            }
        }

    //获取子广告类型
    function getchlidType(e,child){
        $.ajax({
            url:accipiter.getRootPath()+"/adv/quickAdelement/getAdChlidType",
            async: false,
            type:"POST",
            data:e,
            contentType: "application/json; charset=utf-8",
            dataType : "json",
            success:function(data){
                if(data.childList==undefined){
                    $("#adcombo_chlidType").html("");
                    $('#adcombo_id_chlidType').css("display","none");
                }else{
                    if(data !=null && data.childList.length!=0){
                        var len=data.childList.length;
                        var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        for(var i=0;i<len;i++){
                            if(data.childList[i].id == child){
                                html+='<option value='+data.childList[i].id+' selected="selected">'+data.childList[i].typeName+'</option>';
                            }else{
                                html+='<option value='+data.childList[i].id+'>'+data.childList[i].typeName+'</option>';
                            }
                        }
                        $("#adcombo_chlidType").html("");
                        $("#adcombo_chlidType").html(html);
                        $("#adcombo_chlidType").select2();
                        $('#adcombo_id_chlidType').css("display","block");
                    }else{
                        $("#adcombo_chlidType").html("");
                        $('#adcombo_id_chlidType').css("display","none");
                    }
                }
                adClassId=data.typeId;

            },
            error: function (err) {

            }
        });
    }
	</script>
	<style type="text/css">
		.timeSelect{
			width:110px;
		}
		*{margin:0;padding:0;}
		.channel_content{position:fixed;width: 800px;height: 450px;top:15%;left:50%;margin-left: -400px;border:1px solid gainsboro;border-radius: 6px; font-family: "微软雅黑";background: ghostwhite;display:none}
        .channel_content ul{list-style-type: none;overflow-y:auto;}
        .channel_content .channel_content_ul{width: 90%;padding-left: 5%;padding-right: 5%;padding-top: 10px;height: 340px;margin:0;}
        .channel_content_ul .fasongqi_type{list-style-type: none;border-bottom: 1px solid gainsboro;margin-top: 5px;padding-bottom: 5px;}
        .channel_content_ul .fasongqi_type .fasongqi_type_content{padding-left: 15px;margin-top: 5px;margin-left:0}
        .channel_type_list_content{padding-left: 15px;overflow: hidden;margin:0;}
        .channel_type_list{list-style-type: none;padding-left: 15px;margin-top: 10px}
        .channel_list{list-style-type: none;float: left;width: 33.333%;margin-top: 5px;height: 25px;}
        .channel_content label{margin-left: 5px;height: 25px}
        .channel_content input{margin-top: 4.5px}
        .channel_content .fasongqi_type_content input{margin-top: -4.5px}
        .channel_content .channel_list label{margin-left: 10px;height: 25px;line-height: 25px}
        .channel_content .ad_action {position: absolute;bottom: 10px;width: 100%;height: 30px;border-top: 1px solid gainsboro}
        .channel_content .ad_action input{display: inline-block;width: 60px;height: 30px;outline: none;float: right;margin-right: 25px;border-radius: 6px;border: 0;background: gainsboro}
        .channel_content .ad_action input:hover{cursor: pointer;background:#228D9F;color: #fff }
        .channel_content_ul input[type="button"]{width: 14px;height: 14px;padding: 0;border: 0;background-image: url("../../static/images/icon/ic_close.png");outline: none;background-size: 100%;border-radius: 4px;margin-top:-1.5px}
        .channel_content_ul .fasongqi_type_content input { -webkit-appearance: none;-moz-appearance: none;appearance: none; background:url("../../static/images/icon/ic_checkbox_false.png");  height: 22px;  vertical-align: middle;  width: 22px;width: 16px;height: 16px;outline: none}
        .channel_content_ul .fasongqi_type_content .input-checked {background-image:url("../../static/images/icon/ic_checkbox_ture.png"); -moz-background-image:url("./././static/images/icon/check_icon.png");background-size: 100% }
        .channel_content_ul input[type="button"]:hover{cursor: pointer}
        .channel_content .channel_content_ul:last-child{border-bottom: 0}
        .setNet-icon{position: relative; display: block;height: 32px;width: 18px;top: -34px;left: 200px;border: 1px solid #aaa;}
        .selOptCheck{border: 0; width: 16px; height: 16px;margin: 5px;background-size: 100%;  background: url(../../static/images/icon/ic_checkbox_ture.png);}
        .selOpt{border: 0; width: 16px; height: 16px;margin: 5px;background-size: 100%;background: url("../../static/images/icon/ic_checkbox_false.png");}
	    -webkit-border-radius: 0 4px 4px 0;                   
	    -moz-border-radius: 0 4px 4px 0;
	    border-radius: 0 4px 4px 0;
	    -webkit-background-clip: padding-box;
	    -moz-background-clip: padding;
	    background-clip: padding-box;
	    background: #ccc;
	    background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, #ccc), color-stop(0.6, #eee));
	    background-image: -webkit-linear-gradient(center bottom, #ccc 0%, #eee 60%);
	    background-image: -moz-linear-gradient(center bottom, #ccc 0%, #eee 60%);
	    background-image: -o-linear-gradient(bottom, #ccc 0%, #eee 60%);
	    background-image: -ms-linear-gradient(top, #cccccc 0%, #eeeeee 60%);
	    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#eeeeee', endColorstr = '#cccccc', GradientType = 0);
	    background-image: linear-gradient(top, #cccccc 0%, #eeeeee 60%);
        }
        .controls-setNet{position: relative;}
        .controls-setNet .setNet-icon i{margin-left: 2px; margin-top: 7px;}
        .setNet{display: inline-block;width: 200px !important; border-radius: 4px;border: 1px solid gainsboro;background-color: #FFFFFF;height: 26px;line-height:26px;}
	    .setNetwork{display: inline-block;width: 100px !important; border-radius: 4px;border: 1px solid gainsboro;background-color: #FFFFFF;height: 26px;line-height:26px;}
	    .setDate{width:100px!important}
	    #setDate_error{visibility:hidden}
	    .channel_content_ul .channel_list .channel_disabled{-webkit-appearance: none;-moz-appearance: none;appearance: none; background:url("../../static/images/icon/noSelected.png"); }
	    /**/
	   /*  .setDate .select2-search{display:none!important;} */
       .control-network{display:none}
       .control-network .networkContent{float:left}
       .control-network .error{display: inline-block;float: left;}
       .control-network>div:after{clear:both;content:"";display:block;overflow:hidden}
       .controls .networkContent{width:660px;min-height:100px;max-height:200px;height:auto;border: 1px solid #ccc;-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);-moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);-webkit-transition: border linear .2s,box-shadow linear .2s;-moz-transition: border linear .2s,box-shadow linear .2s;-o-transition: border linear .2s,box-shadow linear .2s;transition: border linear .2s,box-shadow linear .2s;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;overflow: hidden;overflow-y: auto;}
	   .networkContent ul{list-style-type: none;overflow-y:auto;margin-left:30px;}
	   .networkContent ul li{min-width: 200px;width:auto!important;height: 30px;position: relative;line-height: 30px;float:left}
	   .networkContent .network_type{margin-left: 20px; margin-top: -1px; border: 0px;background: url(../../static/images/icon/ic_checkbox_false.png);outline: none; }
	  
	   .networkContent ul li .option{display:block;position: absolute;width: 16px;height: 16px;left: 10px;top: 7px; }
       .networkContent ul li label{display: block;position: absolute;left: 35px;width: 170px;height: 30px;line-height: 30px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;}
       
       .networkContent ul li .option{background:url("../../static/images/icon/ic_checkbox_false.png");outline: none;border: 0}
       .networkContent ul li .noselected{background:url("../../static/images/icon/noSelected.png");}
       .networkContent ul li input:hover{cursor: pointer}
       .networkContent ul li label:hover{cursor: pointer}
       .networkContent ul:after{clear:both;content:"";display:block;overflow:hidden}
       .networkContent .ad_action {position: relative;bottom: 0px;width: 100%;height: 40px;border-top: 1px solid gainsboro}
       .network_content .ad_action input{display: inline-block;width: 60px;height: 30px;outline: none;float: right;margin-right: 25px;margin-top:5px;border-radius: 6px;border: 0;background: gainsboro}
       .network_content .ad_action input:hover{cursor: pointer;background:#228D9F;color: #fff }
       #validStartTime{width:200px;}
       #validEndTime{width:200px;}
       #startTime{width:200px;}
       #endTime{width:200px;}
        #startDate{width:200px;}
       #endDate{width:200px;}
/*        .select2-container.select2-container-disabled .select2-choice{margin-bottom: 5px;}
       .select2-container .select2-choice {margin-bottom: 5px;} */
	</style>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' /></div>
	    <ul>
		    <li><spring:message code='sell.adcombo' /></li>
		    <li>></li>
		    <li><spring:message code='combo.manage' /></li>
		    <li>></li>
		    <li>
		    	<shiro:hasPermission name="sys:combo:edit"><a href="${ctx}/adv/combo/form?id=${adCombo.id}">
		<c:choose><c:when test="${not empty adCombo.id}"><spring:message code='combo.update' /></c:when>
        			<c:otherwise><spring:message code='combo.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/combo"><spring:message code='combo.list' /></a></li>
		<shiro:hasPermission name="sys:combo:edit"><li  class="active"><a href="${ctx}/adv/combo/form?id=${adCombo.id}">
		<c:choose><c:when test="${not empty adCombo.id}"><spring:message code='combo.update' /></c:when>
        			<c:otherwise><spring:message code='combo.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adCombo" action="${ctx}/adv/combo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="comboId" name="comboId" type="hidden" value=${comboId}>
		<input id="childAdTypeId" name="childAdTypeId" type="hidden" value="${adCombo.childAdType.id}">
		<input id="oldStatus" name="oldStatus" type="hidden" value=${adCombo.status}>
		<input id="sdId" name="sdId" type="hidden" value=${adCombo.sdRange.id}>
		<input id="hdId" name="hdId" type="hidden" value=${adCombo.hdRange.id}>
		<input id="sdTrackId" name="sdTrackId" type="hidden" value=${adCombo.sdTrack.id}>
		<input id="hdTrackId" name="hdTrackId" type="hidden" value=${adCombo.hdTrack.id}>
		<form:hidden path="startHour"/>
		<form:hidden path="startMinutes"/>
		<form:hidden path="startSecond"/>
		<form:hidden path="endHour"/>
		<form:hidden path="endMinutes"/>
		<form:hidden path="endSecond"/>
		<input id="paramValue"  type="hidden" value="${paramValue}">
		<input id="ops"  type="hidden" value="${ops}">
		<input id="netIds"  type="hidden" value="${netIds}">
		<input id="netWorkType" type="hidden" value="common">
		<input type="hidden" id="area" name="area" value="${adCombo.area}">
		<input type="hidden" id="selArea" name="selArea" value="${adCombo.selArea}">
		<input type="hidden" id="selAllArea" name="selAllArea" value="${selAllArea}">
		<input type="hidden" id="districtMode" value="setComboDis">
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label"><spring:message code='combo.name' />:</label>
			<div class="controls">
				<input id="oldComboName" name="oldComboName" type="hidden" value="${adCombo.comboName}">
				<form:input path="comboName" htmlEscape="false" maxlength="50" class="required"/>
										<label class="info-messages"><p></p></label>
				
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label"><spring:message code='type.isflag' />:</label>
			<div class="controls">
				<form:select path="isFlag" id="isFlag" class="required" onchange="checkIsChannel();">
						<form:options items="${fns:getDictList('adv_type_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
	
		 <!-- 与频道无关 -->
		<div id="notchannel">
		 	<div class="control-group NetWorkContent">
				<label class="control-label"><spring:message code='adv.type'/>:</label>
				<div class="controls" >
					<form:select path="typeId"  class="required" onchange="showChildType();">
							<option value=""><spring:message code='userform.select'/></option>
							<form:options items="${fns:getAdTypeByIsFlag(0)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="adcombo_id_chlidType" style="display:none">
				<label class="control-label"><spring:message code='adv.son.type' />:</label>
				<div class="controls">
					<p id="chlidType" style="display:none"></p>
					<form:select path="childAdType.id" class="required" id="adcombo_chlidType">
						<option value=""> <spring:message code='userform.select' /></option>
					</form:select>
					<label class="info-messages"><p></p></label>
				</div>
			</div>
			<div class="control-group" style="display: block;">
				<label class="control-label"><spring:message code='send.mode' />:</label>
				<div class="controls">
					<form:select path="sendMode" id="sendModeWG" class="required">
					        <option value=""><spring:message code='userform.select'/></option>
							<form:options items="${fns:getDictList('ad_send_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
		    </div>		
				<div class="control-group">
			 	<label class="control-label"><spring:message code='combo.startdate' />：</label>
			 	<div class="controls">
			 		<input id="startDate" name="startDate" type="text"  maxlength="20" class="input-small Wdate"
						value="${startDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:today,maxDate:'#F{$dp.$D(\'endDate\',{d:0})}'})"/>
						<span id="startdate_span" style="color:red"></span>
			 	</div>
			 </div>
			 
			<div class="control-group">
			 	<label class="control-label"><spring:message code='combo.enddate' />：</label>
			 	<div class="controls">
			 		<input id="endDate" name="endDate" type="text"  maxlength="20" class="input-small Wdate"
						value="${endDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startDate\',{d:0})||\'+today+\'}'})" class="required"/>
						<span id="enddate_span" style="color:red"></span>
			 	</div>
			</div>
			<div class="control-group" id="WGArea">
				<label class="control-label"><spring:message code='operators.area' />:</label>
				<div class="controls">
					<input type="text" id="wgarea" name="wgarea" class="area-duoxuan" value="${adCombo.area}" data-value="" readonly="readonly">
				</div>
			</div>
			<div class="control-group" id="selDisResultWG" style="display:block;height: auto;">
				<label class="control-label"><spring:message code='operators.list' />:</label>
				<div class="controls">
			          <div id="selDataResultWG" class="sel-data-result" style="padding: 5px;"></div>
				</div>
			</div>
			<div class="control-group control-group-auto control-network">
			<label class="control-label"><spring:message code='network.tree' />:</label>
			<div class="controls">
			    <!-- <input type="button" class="setNetwork" value="请选择网络"/> -->
				<!-- <div id="networkTree" class="ztree" style="margin-top:3px;float:left;" class="required"></div> -->
				<div class="networkContent">
<!-- 					   <ul class="network_content_ul"></ul>
 -->			    </div>
				<form:hidden path="networkIds"/> 
			</div>
			</div>
		</div>
		<!-- 与频道有关 -->
		<div id="channel" style="display:none;">
			<div class="control-group">
				<label class="control-label"><spring:message code='adv.type'/>:</label>
					 
				<div class="controls adTypeSelect" >
					<form:select path="adType.id" class="required" onchange="checkType();" id="adTypeSelect" >
							<option value=""><spring:message code='userform.select'/></option>
							<form:options items="${fns:getAdTypeByIsFlag(1)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
					<label class="info-messages"><p></p></label>
				</div>
			</div>
		<div class="control-group" style="display: block;">
			<label class="control-label"><spring:message code='send.mode' />:</label>
			<div class="controls">
				<form:select path="sendModeYG" id="sendModeYG" class="required">
						<option value=""><spring:message code='userform.select'/></option>
						<form:options items="${fns:getDictList('ad_send_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
	    </div>
			<div class="control-group" id="trackDiv">
				<label class="control-label"><spring:message code='track.mode' />:</label>
				<div class="controls">
					<form:select path="trackMode" onchange="checkTrackMode();" class="required">
						<option value=""><spring:message code='userform.select'/></option>
						<form:options items="${fns:getDictList('ad_track_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="sdRange">
				<label class="control-label"><spring:message code='adv.sdRange' />:</label>
				<div class="controls">
					<form:select path="sdRange.id" id="sdRange_id">
						<option value=""> <spring:message code='userform.select' /></option>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="hdRange">
				<label class="control-label"><spring:message code='adv.hdRange' />:</label>
				<div class="controls">
						<form:select path="hdRange.id" id="hdRange_id">
							<option value=""> <spring:message code='userform.select' /></option>
						</form:select>
					</div>
			</div>
			<div class="control-group" id="sdTrack">
				<label class="control-label"><spring:message code='adv.sdTrack' />:</label>
				<div class="controls">
					<form:select path="sdTrack.id" id="sdTrack_id">
						<option value=""> <spring:message code='userform.select' /></option>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="hdTrack">
				<label class="control-label"><spring:message code='adv.hdTrack' />:</label>
				<div class="controls">
					<form:select path="hdTrack.id" id="hdTrack_id">
						<option value=""> <spring:message code='userform.select' /></option>
					</form:select>
				</div>
			</div>
			  <div class="control-group setPlayStartTime">
			 	<label class="control-label"><spring:message code='combo.startdate' />：</label>
			 	<div class="controls">
			 		<input id="validStartTime" name="validStartTime" type="text"  maxlength="20" class="input-small Wdate"
						value="${startDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:today,maxDate:'#F{$dp.$D(\'validEndTime\',{d:0})}'})"/>
						<label class="info-messages"><p></p></label>
			 	</div>
			 </div>
			 
			<div class="control-group setPlayEndTime">
			 	<label class="control-label"><spring:message code='combo.enddate' />：</label>
			 	<div class="controls">
			 		<input id="validEndTime" name="validEndTime" type="text"  maxlength="20" class="input-small Wdate"
						value="${endDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'validStartTime\',{d:0})||\'+today+\'}'})" class="required"/>
						<label class="info-messages"><p></p></label>
			 	</div>
			</div>
			<div class="control-group setStartTime">
			 	<label class="control-label"><spring:message code='combo.playstart' />：</label>
			 	<div class="controls">
				 	<input id="startTime" name="startTime" type="text" maxlength="20" class="input-small Wdate"
				     value="${startTime}" onclick="var date=onClickByStartTime();clearChannelData();WdatePicker({dateFmt:'HH:mm:ss',isShowClear:true,isShowToday:false,minDate:'00:00:00',maxDate:date.endTime});"/>
					 <label class="info-messages"><p></p></label>
				</div>
			</div>
			<div class="control-group setEndTime">
			 	<label class="control-label"><spring:message code='combo.playend' />：</label>
			 	<div class="controls">
			 	<input id="endTime" name="endTime" type="text" maxlength="20" class="input-small Wdate"
				     value="${endTime}" onclick="var date=onClickByEndTime();clearChannelData();WdatePicker({dateFmt:'HH:mm:ss',isShowClear:true,isShowToday:false,minDate:date.startTime,maxDate:'23:59:59'});"/>
				 <label class="info-messages"><p></p></label>
				 </div>
			</div>
		   <div class="control-group" id="YGArea">
				<label class="control-label"><spring:message code='operators.area' />:</label>
				<div class="controls">
					<input type="text" id="ygarea" name="ygarea" class="area-duoxuan" value="${adCombo.area}" data-value="" readonly="readonly">
				</div>
			</div>
		 <div class="control-group" id="selDisResultYG" style="display:none;height: auto;">
				<label class="control-label"><spring:message code='operators.list' />:</label>
				<div class="controls">
			          <div id="selDataResultYG" class="sel-data-result" style="padding: 5px;"></div>
				</div>
			</div>
		   <div class="control-group">
		        <label class="control-label"><spring:message code='channel.select' />:</label>
		        <div class="controls controls-setNet"><input class="setNet" type="button" name="0" id="setNetChannel" value="<spring:message code='channel.select'/>"><span class="setNet-errInfo" style="color:red;margin-left:40px;"></span><label class="setNet-icon" for="setNetChannel"><i class="icon-search"></i></label></div>
		        <form:hidden path="channelIds"/> 
		    </div>
			<div class="control-group control-group-auto" style="height:37px">
				<label class="control-label"><spring:message code='combo.dateplan' />:</label>
				<div class="controls">
 				<form:hidden path="week"/>
				<div class="selectContent">
				    <div class="selectData"><input class="weekSelect required" id="weekStart" name="0" placeholder="<spring:message code='input.date'/>"readonly></div>
				    <input class="control_icon" type="button" for="weekStart" name="0">
				    <ul class="multiselect">
				        <li value="1" id="option-1"><input type="button" class="option" name="0" id="1"><label for="1"><spring:message code="Mondy"/></label></li>
				        <li value="2" id="option-2"><input type="button" class="option" name="0" id="2"><label for="2"><spring:message code="Tuesday"/></label></li>
				        <li value="3" id="option-3"><input type="button" class="option" name="0" id="3"><label for="3"><spring:message code="Wednesday"/></label></li>
				        <li value="4" id="option-4"><input type="button" class="option" name="0" id="4"><label for="4" ><spring:message code="Thursday"/></label></li>
				        <li value="5" id="option-5"><input type="button" class="option" name="0" id="5"><label for="5"><spring:message code="Friday"/></label></li>
				        <li value="6" id="option-6"><input type="button" class="option" name="0" id="6"><label for="6"><spring:message code="Saturday"/></label></li>
				        <li value="7" id="option-7"><input type="button" class="option" name="0" id="7"><label for="7"><spring:message code="Sunday"/></label></li>
				        <li class="btnAction"><input type="button" class="selectAll" id="selectAll" name="0" ><label for="selectAll"><spring:message code='all.select'/></label><input type="button" class="submit" value="<spring:message code='sure'/>"><input type="button" class="btnClear" value="<spring:message code='cancle'/>"></li>
				    </ul>
				</div>
				<label class="error" id="setDate_error" for="weekStart"></label>
				</div>
			</div>
			<div id="showTimeSet" style="display:none;">
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.showtime' />:</label>
					<div class="controls">
						<form:input path="showTime"  htmlEscape="false" maxlength="50" min="1" class="required digits showTime"/>
					    <span id="time-prompt " style="color:green;"><spring:message code='adv.prompt' /></span>
					</div>
				</div>
					<div class="control-group">
					<label class="control-label"><spring:message code='combo.intervaltime' />:</label>
					<div class="controls">
						<form:input path="intervalTime" type="number" htmlEscape="false" maxlength="50" min="0" class="required digits intervalTime"/>
					    <span id="time-prompt1 " style="color:green;"><spring:message code='adv.prompt1' /></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.cycle' />:</label>
					<div class="controls">
						<form:input path="showCount" htmlEscape="false" maxlength="50" class="required digits" readonly="true"/>
						<span id="time-beyond" style="color:red;"></span>
					</div>
				</div>
			</div>
			<div id="rollSet" style="display:none;">
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.pictureTimes' />:</label>
					<div class="controls">
						<form:input path="pictureTimes" type="number" htmlEscape="false" maxlength="50" min="1" class="required digits"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.pictureInterval' />:</label>
					<div class="controls">
						<form:input path="pictureInterval" type="number" htmlEscape="false" maxlength="50" min="0" class="required digits"/>
						<span id="time-prompt2 " style="color:green;"><spring:message code='adv.prompt2' /></span>
					</div>
				</div>

			</div>
		</div>
 
		
		
		<!-- 套餐状态 -->
		<div class="control-group">
			<label class="control-label"><spring:message code='combo.status' />:</label>
			<div class="controls">
				<form:select path="status" class="required">
						<form:options items="${fns:getDictList('ad_combo_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
	    </div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:combo:edit"><input id="btnSubmit" class="btn btn-primary"   type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div class="channel_content">
		<div class="data-search" id="searchRun"><input class="run" name="searchchannel"><div class="searchList run" style="display: none;"></div></div>
	    <ul class="channel_content_ul">
	    </ul>
	    <div class="ad_action">
	        <input class="btn_close" type="button" value="<spring:message code='cancle' />">
	       
	        	        <input class="btn_submit" type="button" value="<spring:message code='sure' />">
	       

	    </div>
   </div>
<%--    <div class="network_content">
       <div class="network_title"><label>请选择网络</label></div>
	   <ul class="network_content_ul">
	   </ul>
	   <div class="ad_action">
	        <input class="btn_close" type="button" value="<spring:message code='cancle' />">
	        <input class="btn_submit" type="button" value="<spring:message code='sure' />">
	    </div>
   </div> --%>
   <script src="../../static/adcombojs/channel_select.js"></script>
   <script>
   $(function(){
		//时间回显
		function findTime(m,e){
			var len=e.find("option").length;
			e.find('.select2-choice span').text(m);
			for(var i=0;i<len;i++){
				if(e.find('option:eq('+i+')').attr("value")==m){
					e.find('option:eq('+i+')').attr("selected",true);
					e.select2();
				}
			} 
		}
		function showTimeData(){
			if($("#adcomboId").attr("value")!=""){
				var startTime=$("#startTime").val().split(":");
				var endTime=$("#endTime").val().split(":");
				var startHour=startTime[0];
				var startMinute=startTime[1];
				var startSecond=startTime[2];
				var endHour=endTime[0];
				var endMinute=endTime[1];
				var endSecond=endTime[2];
				findTime(startHour,$("#startHour"));
				findTime(startMinute,$("#startMinute"));
				findTime(startSecond,$("#startSecond"));
				findTime(endHour,$("#endHour"));
				findTime(endMinute,$("#endMinute"));
				findTime(endSecond,$("#endSecond"));
				var startWeek=$("#week").attr("value");
				var len=startWeek.split(",").length;
/* 				var optionLen=$("#weekStart option").length;
 				for(var i=0;i<len;i++){
					var selectStr=startWeek.split(",")[i];
					for(var m=0;m<optionLen;m++){
						if($("#weekStart").find('option:eq('+m+')').attr("value")==selectStr){
							$("#weekStart").find('option:eq('+m+')').attr("selected",true);
						}
					}
				} 
	
				/* $("#weekStart option").removeAttr("selected"); */
				/* $("#weekStart option").attr("selected",false); */
				/* $("#weekStart").select2(); */ 
				
			}
		}
		showTimeData();
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
                    if(isNaN(startHour) || isNaN(startMinute) || isNaN(startSecond) || isNaN(endHour) || isNaN(endMinute) || isNaN(endSecond) || isNaN(intervalTime) || isNaN(showTime)){
                        return false;
                    }
	        		var showCount = Math.floor( (totalSecond+intervalTime)/(showTime+intervalTime));
	        		if(showCount==0){
	        			showCount=1;
	        		}
	        		if(startHour!=NaN && startMinute!=NaN && startSecond!=NaN && endHour!=NaN && endMinute!=NaN && endSecond!=NaN && showCount!=NaN && showTime!=NaN){
                        $('#showCount').val(showCount);
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
	    var timeoutId = 0;
	/*     $('#showCount').off('keyup').on('keyup', function (event) {
	        clearTimeout(timeoutId);
	        timeoutId = setTimeout($.proxy(judgeTime), 100, event); // 100ms
	    }); */
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
		$('#startTime').blur(function() {
			judgeTime();
		});
		$('#endTime').blur(function() {
			judgeTime();
		});
    	$('#startDate').blur(function() {
    		$('#validStartTime').val($(this).val());
    		$(".setPlayStartTime").find(".info-messages p").text("");
    		var startDate1 = $(this).val();
    		if(null == startDate1 || "" == startDate1){
    			document.getElementById("startdate_span").innerText = accipiter.getLang_(messageLang,"adv.start.date");
    			return;
        	}else{
        		document.getElementById("startdate_span").innerText="";
        	}
    	});

    	$('#endDate').blur(function() {
    		$('#validEndTime').val($(this).val());
    		$(".setPlayEndTime").find(".info-messages p").text("");
    		var endDate1 = $(this).val();	
    		if(null == endDate1 || "" == endDate1){
    			document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.end.date");
    			return;
        	}else{
        		document.getElementById("enddate_span").innerText="";
        	}
    	});
    	
       $('#validStartTime').blur(function() {
    	   $('#startDate').val($(this).val());
    	   document.getElementById("startdate_span").innerText="";
    	   var validStartTime = $(this).val();
    	   if(null == validStartTime || "" == validStartTime){
    			$(".setPlayStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startDate"));
    			return;
    	   	}else{
    			$(".setPlayStartTime").find(".info-messages p").text("");
    	   	}
       });

    	$('#validEndTime').blur(function() {
    		$('#endDate').val($(this).val());
    		document.getElementById("enddate_span").innerText="";
    		var validEndTime = $(this).val();
    		if(null == validEndTime || "" == validEndTime){
    	    	 $(".setPlayEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endDate"));
    			return;
        	}else{
    	    	 $(".setPlayEndTime").find(".info-messages p").text("");
        	}
         });
   });
   </script>
</body>
</html>