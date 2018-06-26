<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/dropzone/css/basic.css">
    	<link rel="stylesheet" href="${ctxStatic}/common/style.css">
    
    	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
    	<link rel="stylesheet" href="${ctxStatic}/layer/skin/myskin/style.css">
    	<script src="${ctx}/static/scripts/common/common.js"></script>
    	<script src="${ctxStatic}/common/language.js"></script>
    	<script src="${ctxStatic}/layer/layer.js"></script>
    	<script src="${ctxStatic}/common/validDate.js"></script>
    	<script type="text/javascript" src="${ctxStatic}/common/areadata.js"></script>
        <script type="text/javascript" src="${ctxStatic}/common/auto_area.js"></script>
    	
    	
    	
    	<style type="text/css">
    	#upload_adv_image,#upload_adv_vedio{width: 100%; min-height:260px;height: auto;/* overflow: hidden; */padding:0;border-bottom: 0px;}
    	#upload_adv_image .switchResolution,#upload_adv_vedio .switchResolution{width:100%;height:39px;border-bottom:1px solid gainsboro;}

    	.switchResolution ul{list-style-type: none;padding: 0;margin: 0;height:100%;}
        .switchResolution ul li{float:left;margin-left:20px;height:100%;line-height:38px;position: relative;}
    	.switchResolution ul:after{content: '';visibility: hidden;clear: both;display: block}
    	.controls .switchResolution ul li label{}
    	.controls .switchResolution ul li input{display: inline-block;width:16px;height:16px;outline: none;border: 0;margin-top: -1px;margin-left: 10px;}
    	.controls .switchResolution ul li input{background: url(../../static/images/icon/ic_checkbox_false.png);}
    	.controls .switchResolution ul li input[name="1"]{background: url(../../static/images/icon/ic_checkbox_ture.png);}
    	
        #upload_adv_image .resourcesContent,#upload_adv_vedio .resourcesContent{width: 100%; height: 220px;position: relative;}
        #upload_adv_image .resourcesContent>div,#upload_adv_vedio .resourcesContent>div{width: 100%; height: 220px;overflow: auto; position: relative;}
    	#upload_adv_image>p{width:100%;color:red;text-align:center;position: absolute;bottom: 0;z-index: 1000;}
    	#upload_adv_vedio>p{width:100%;color:red;text-align:center;position: absolute;bottom: 0;z-index: 1000;}
		.list_content{list-style-type: none;padding: 0;margin: 0}
		.list_content .title{width:100%;height:30px;/*line-height:30px;*/margin-top: 10px;padding: 0 10px;border-bottom: 1px solid gainsboro;}
		.list_content .title p{float: left;    width: 130px;}
		.list_content .title label{ width: auto; margin-left: 10px;/*margin-top: 5px;*/}
		.list_content .title .selAll{display: inline-block;width: 16px;height: 16px;  outline: none;  border: 0;  margin-top: -1px;  margin-left: 10px;  background: url("../../static/images/icon/ic_checkbox_false.png");  }
		.list_content .title input{margin-top: -4px;width: 60px;    margin-left: 10px;}
		.list_content .item{float:left;width: 100px;height: 100px;border: 1px solid grey;position: relative;margin: 10px;border-radius: 4px}
		.list_content:after{content: '';visibility: hidden;clear: both;display: block}
		.item img{display: block;width: 100%;height: 75px;}
		.item .item_bottom{position: absolute;left: 0;top: 75px;height: 25px;}
		.item .action_button{display:block;width: 16px;height: 16px !important;padding: 0;margin: 0;border: 0;outline: none;position: absolute;top:4.5px;background: url("${ctxStatic}/images/icon/ic_checkbox_false.png");background-repeat: no-repeat;}
		.item label{display:block;height: 16px;overflow: hidden;position: relative;left: 0px;top: 5.5px;text-indent: 5px;line-height: 16px;font-size: 13px;white-space: nowrap;text-overflow: ellipsis;dispaly:none;}
		.item img:hover{cursor: pointer;}
		.item input:hover{cursor: pointer}
		.item label:hover{cursor: pointer}
		.playTime{display:none;}
        .advHdContent{display:none;}
        .advStandardContent{display:none;}
        .resourcesSd{border-bottom: 1px solid gainsboro;}
        .resourcesHd{display:none;border-bottom: 1px solid gainsboro;}
        .linkText{position: relative;}
        .linkText a{display: none;color:blue;position: absolute;top: 0px;padding: 2px 20px 2px 2px;border-bottom: 1px solid gainsboro;}
    	.linkText a:hover{text-decoration: none;}
    	 #upload_adv_image .resourcesContent .uploadfile_error,#upload_adv_vedio .resourcesContent.uploadfile_error{display: none; width: 100%;position: absolute;bottom: 0;text-align: center;color: red;}
    	
		.timeSelect{
			width:110px;
		}
		*{margin:0;padding:0;}
        .channel_content{position:fixed;width: 800px;height: 400px;top:15%;left:50%;margin-left: -400px;border:1px solid gainsboro;border-radius: 6px; font-family: "微软雅黑";background: ghostwhite;display:none}
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
        .add_btn{background: url("../../static/images/icon/add.png");width: 39px;height: 39px;    background-position: center;  background-size: cover;cursor: pointer;}
		.add_item{	position: absolute;
			right: 0;
			bottom: 0;background: url("../../static/images/icon/add.png");width: 39px;height: 39px;  background-position: center;  background-size: cover;cursor: pointer;}
		.add_btn:hover{background: url("../../static/images/icon/add_fill.png");width: 39px;height: 39px;    background-position: center;  background-size: cover;cursor: pointer;}
        
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
       #startDate{width:200px;}
       #endDate{width:200px;}
       #startTime{width:200px;}
       #endTime{width:200px;}
		.add_item{	position: absolute;
			right: 0;
			bottom: 0;background: url("../../static/images/icon/add.png");
			width: 39px;height: 39px;
			background-position: center;  background-size: cover;cursor: pointer;}
		.jq22 { float: left;width: 100%;height:200px; margin: 0 auto; font-family: arial,SimSun; font-size: 0;}
		.jq22 .jqitem2 { display: inline-block; width: 100px; height: 100px; margin: 20px 10px 0px 20px;}
		.jq23 { float: left;width: 100%;height:200px; margin: 0 auto; font-family: arial,SimSun; font-size: 0;}
		.jq23 .jqitem3 { display: inline-block; width: 100px; height: 100px; margin: 20px 10px 0px 20px;}
		.item .pcshowTime {left: 30px; width: 55px; background-color: #f3f3f3;position: relative; border: 1px solid #9E9E9E;}
		.special-contrals .select2-container{
			width: auto!important;
			min-width: 230px!important;
		}
		.dad-noSelect,.dad-noSelect *{
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    cursor: -webkit-grabbing !important;
    cursor: -moz-grabbing !important;
}

.dad-container{
    position: relative;
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}
.dad-container::after{
    content: '';
    clear: both !important;
    display: block;
}
.dad-active .dad-draggable-area{
    cursor: -webkit-grab;
    cursor: -moz-grab;
}
.dads-children-clone{
    opacity: 0.8;
    z-index: 9999;
    pointer-events: none;
}
.dads-children-placeholder{
    overflow: hidden;
    position: absolute !important;
    box-sizing: border-box;
    border:4px dashed #639BF6;
    margin:5px;
    text-align: center;
    color: #639BF6;
    font-weight: bold;
}
       
       
/*        .select2-container.select2-container-disabled .select2-choice{margin-bottom: 5px;}
       .select2-container .select2-choice {margin-bottom: 5px;} */
    	</style>
	<script type="text/javascript">
		$(document).ready(function() {
		    $("#adName").focus();
			$("#inputForm").validate({
				rules: {
					 
					adName: {remote: "${ctx}/adv/quickAdelement/checkAdvName?operate=${operate}&oldAdName=" +encodeURIComponent('${adelement.adName}')}
				 
				},
				messages: {
					adName: {remote:accipiter.getLang("adNameExist")}
				}, 
				submitHandler: function(form){
				    var isFlag=$("#isFlag").find('option:selected').val(); 
                    if(isFlag == "0"){
                    var startDate1 = $("#startDate").val();
                    var endDate1 = $("#endDate").val();
                    	if(null == startDate1 || "" == startDate1){
                			document.getElementById("startdate_span").innerText = accipiter.getLang_(messageLang,"adv.start.date");
                			return;
                    	}else{
                    		document.getElementById("startdate_span").innerText="";
                    	}
                    	if(null == endDate1 || "" == endDate1){
                			document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.end.date");
                			return;
                    	}else{
                    		document.getElementById("enddate_span").innerText="";
                    	}
                    }else{
                    	var validStartTime = $("#validStartTime").val();
                    	var validEndTime = $("#validEndTime").val();
                    	if(validStartTime == null || validStartTime == ""){
                			$(".setPlayStartTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.startDate"));
                			return;
                    	}else{
                			$(".setPlayStartTime").find(".info-messages p").text("");
                		}	
                    	if(validEndTime == null || validEndTime == ""){
                	    	 $(".setPlayEndTime").find(".info-messages p").text(accipiter.getLang_(messageLang,"select.endDate"));
                	    	 return;
                    	}else{
                	    	 $(".setPlayEndTime").find(".info-messages p").text("");
                		}
                    }
					var addText = eval(document.getElementById("v_addText")).value;
					var startDate =document.getElementById("startdate_span").innerText;
					var endDate = document.getElementById("enddate_span").innerText;
					if((null == addText || "" == addText ) &&(null == startDate || "" == startDate)
							&&(null == endDate || "" == endDate)){
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
							form.submit();
							$("#btnSubmit").attr({"disabled":true});
					}
					
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
		});
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="adv.manage"/></li>
		    <li>></li>
		    <li><spring:message code="adv.quick.issue"/></li>
		    <li>></li>
		    <li>
		 	<shiro:hasPermission name="sys:adv:edit"><a href="${ctx}/adv/quickAdelement/form?id=${adelement.id}">
		<c:choose><c:when test="${not empty adelement.id}"><spring:message code='adv.update' /></c:when>
        			<c:otherwise><spring:message code='adv.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="sys:adv:edit"><li  class="active"><a href="${ctx}/adv/quickAdelement/form?id=${adelement.id}">
		<c:choose><c:when test="${not empty adelement.id}"><spring:message code='adv.update' /></c:when>
        			<c:otherwise><spring:message code='adv.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adelement" action="${ctx}/adv/quickAdelement/preview" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<!--   <div class="control-group">
			<label class="control-label"><spring:message code='adv.id' />:</label>
			<div class="controls">
				<input id="oldAdId" name="oldAdId" type="hidden" value="${adelement.adId}">
				<form:input path="adId" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>-->
		<form:hidden path="path"/> 
		<form:hidden path="status"/>
		<form:hidden path="hdPath"/>
	    <form:hidden path="isPlay"/>
	    <form:hidden path="isSd"/>
	    <form:hidden path="isHd"/>
	    <form:hidden path="adTypeId"/>
	    <form:hidden path="sdShowParam"/> 
	    <form:hidden path="hdShowParam"/> 
	    <p id="judgeStatus" style="display:none">${adelement.status}</p>
	    <p id="isNotAdv" style="display:none">${isNotAdv}</p>
	    <p id="advId" style="display:none">${advId}</p>
	    <p id="sellTime" style="display:none">${sellTime}</p>
	    <p id="isPosition" style="display:none">${isPosition}</p>
	    <p id="adelementDto" style="display:none">${adelementDto}</p>
	    <input id="oldcomboId" name="adCombo.id" type="hidden" value="${adelement.adCombo.id}">
	    <input id="comboId" name="adCombo.comboId" type="hidden" value="${adelement.comboId}">
		<input id="oldStatus" name="adCombo.oldStatus" type="hidden" value="${adelement.adCombo.status}">
		<input id="sdId" name="adCombo.sdId" type="hidden" value="${adelement.adCombo.sdRange.id}">
		<input id="hdId" name="adCombo.hdId" type="hidden" value="${adelement.adCombo.hdRange.id}">
		<input id="sdTrackId" type="hidden" value="${adelement.adCombo.sdTrack.id}">
		<input id="hdTrackId" type="hidden" value="${adelement.adCombo.hdTrack.id}">
		<input id="childAdTypeId" name="childAdTypeId" type="hidden" value="${adelement.childAdType.id}">
		<input id="startHour" name="adCombo.startHour" type="hidden" value="${adelement.adCombo.startHour}">
		<input id="startMinutes" name="adCombo.startMinutes" type="hidden" value="${adelement.adCombo.startMinutes}">
		<input id="startSecond" name="adCombo.startSecond" type="hidden" value="${adelement.adCombo.startSecond}">
		<input id="endHour" name="adCombo.endHour" type="hidden" value="${adelement.adCombo.endHour}">
		<input id="endMinutes" name="adCombo.endMinutes" type="hidden" value="${adelement.adCombo.endMinutes}">
		<input id="endSecond" name="adCombo.endSecond" type="hidden" value="${adelement.adCombo.endSecond}">
		<input id="sd_Fx" type="hidden" value="${adelement.sdFx}">
		<input id="hd_Fx" type="hidden" value="${adelement.hdFx}">
		<input id="setNet1" type="hidden" value="${setNet}">
		<input id="channelIds1" type="hidden" value="${channelIds}">
		<input id="acCount" type="hidden" value="${adelement.adCombo.showCount}">
		<input id="paramValue"  type="hidden" value="120">
		<input id="sdMaxNC" name="sdMaxNC" type="hidden" value="${adelement.sdMaxNC}">
		<input id="hdMaxNC" name="hdMaxNC" type="hidden" value="${adelement.hdMaxNC}">
		<input id="bpTT"  type="hidden" value="10">
		<input id="bpTS"  type="hidden" value="30">
		<input id="psTs"  type="hidden" value="5">
		<input id="netWorkType" type="hidden" value="quick">
		<input id="ops"  type="hidden" value="${ops}">
		<input id="netIds"  type="hidden" value="${netIds}">
		<input type="hidden" id="area" name="adCombo.area" value="${adCombo.area}">
		<input type="hidden" id="selArea" name="adCombo.selArea" value="${adCombo.selArea}">
		<input type="hidden" id="selAllArea" name="adCombo.selAllArea" value="${selAllArea}">
		<input type="hidden" id="districtMode" value="setComboDis">
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.name' />:</label>
			<div class="controls">
				<input id="oldAdName" name="oldAdName" type="hidden" value="${adelement.adName}">
				<form:input path="adName" htmlEscape="false" maxlength="50" class="required"/>
				<label class="info-messages"><p></p></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='category' />:</label>
			<div class="controls">
				<form:hidden path="oldAdCategoryId"/>
			 
                <tags:treeselectnotfirst id="adCategory" name="adCategory.id" value="${adelement.adCategory.id}" labelName="adCategory.categoryName" labelValue="${adelement.adCategory.categoryName}"
					title="category" url="/adv/category/treeDataNoFirst" extId="${adelement.adCategory.id}" cssClass="required"/>
			</div>
		</div>
		<c:choose>
			<c:when test="${isNotAdv}">
				<div class="control-group">
		 	 		<label class="control-label"><spring:message code='adv.user' />：</label>
		 	 		<div class="controls">
						<form:select path="advertiser.id" class="required" id="advertiser_id">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><spring:message code='type.isflag' />:</label>
					<div class="controls">
						<form:select path="adCombo.isFlag" id="isFlag" class="required" onchange="checkIsChannel();">
								<form:options items="${fns:getDictList('adv_type_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				
				 <!-- 与频道无关 -->
		<div id="notchannel">
		 	<div class="control-group NetWorkContent">
				<label class="control-label"><spring:message code='adv.type'/>:</label>
					 
				<div class="controls" >
					<form:select path="adCombo.typeId" id="typeId"  class="required">
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
					<form:select path="adCombo.sendMode" id="sendModeWG" class="required">
					        <option value=""><spring:message code='userform.select'/></option>
							<form:options items="${fns:getDictList('ad_send_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
		    </div>
			<div class="control-group">
			 	<label class="control-label"><spring:message code='adv.playstart' />：</label>
			 	<div class="controls">
			 		<input id="startDate" name="startDate" type="text"  maxlength="20" class="input-small Wdate"
						value="${startDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:today,maxDate:'#F{$dp.$D(\'endDate\',{d:0})}'})"/>
						<span id="startdate_span" style="color:red"></span>
			 	</div>
			 </div>
			 
			<div class="control-group">
			 	<label class="control-label"><spring:message code='adv.playend' />：</label>
			 	<div class="controls">
			 		<input id="endDate" name="endDate" type="text"  maxlength="20" class="input-small Wdate"
						value="${endDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startDate\',{d:0})||\'+today+\'}'})" class="required"/>
						<span id="enddate_span" style="color:red"></span>
			 	</div>
			</div>
			<div class="control-group" id="WGArea">
				<label class="control-label"><spring:message code='operators.area' />:</label>
				<div class="controls">
					<input type="text" id="wgarea" name="adCombo.wgarea" class="area-duoxuan" value="${adCombo.area}" data-value="" readonly="readonly">
				</div>
			</div>
			<div class="control-group" id="selDisResultWG" style="display:none;height: auto;">
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
				<form:hidden path="adCombo.networkIds" id="networkIds"/> 
			</div>
			</div>
		</div>
		
		<!-- 与频道有关 -->
		<div id="channel" style="display:none;">
			<div class="control-group">
				<label class="control-label"><spring:message code='adv.type'/>:</label>
					 
				<div class="controls adTypeSelect" >
					<form:select path="adCombo.adType.id" class="required" onchange="checkType();" id="adTypeSelect" >
							<option value=""><spring:message code='userform.select'/></option>
							<form:options items="${fns:getAdTypeByIsFlag(1)}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
					</form:select>
					<label class="info-messages"><p></p></label>
				</div>
			</div>
			<div class="control-group" style="display: block;">
			<label class="control-label"><spring:message code='send.mode' />:</label>
			<div class="controls">
				<form:select path="adCombo.sendModeYG" id="sendModeYG" class="required">
						<option value=""><spring:message code='userform.select'/></option>
						<form:options items="${fns:getDictList('ad_send_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
	        </div>
			<div class="control-group" id="trackDiv">
				<label class="control-label"><spring:message code='track.mode' />:</label>
				<div class="controls">
					<form:select path="adCombo.trackMode" id="trackMode" onchange="checkTrackMode();" class="required">
						<option value=""><spring:message code='userform.select'/></option>
						<form:options items="${fns:getDictList('ad_track_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="sdRange">
					<label class="control-label"><spring:message code='adv.sdRange' />:</label>
					<div class="controls">
						<form:select path="adCombo.sdRange.id" id="sdRange_id">
								<option value=""> <spring:message code='userform.select' /></option>	
						</form:select>
					</div>
			</div>
			<div class="control-group" id="hdRange">
					<label class="control-label"><spring:message code='adv.hdRange' />:</label>
					<div class="controls">
						<form:select path="adCombo.hdRange.id" id="hdRange_id">
								<option value=""> <spring:message code='userform.select' /></option>	
						</form:select>
					</div>
			</div>
			<div class="control-group" id="sdTrack">
				<label class="control-label"><spring:message code='adv.sdTrack' />:</label>
				<div class="controls">
					<form:select path="adCombo.sdTrack.id" id="sdTrack_id">
						<option value=""> <spring:message code='userform.select' /></option>
					</form:select>
				</div>
			</div>
			<div class="control-group" id="hdTrack">
				<label class="control-label"><spring:message code='adv.hdTrack' />:</label>
				<div class="controls">
					<form:select path="adCombo.hdTrack.id" id="hdTrack_id">
						<option value=""> <spring:message code='userform.select' /></option>
					</form:select>
				</div>
			</div>
			 <div class="control-group">
			 	<label class="control-label"><spring:message code='adv.playstart' />：</label>
			 	<div class="controls setPlayStartTime">
			 		<input id="validStartTime" name="adCombo.validStartTime" type="text"  maxlength="20" class="input-small Wdate"
						value="${startDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:today,maxDate:'#F{$dp.$D(\'validEndTime\',{d:0})}'})"/>
						<label class="info-messages"><p></p></label>
			 	</div>
			 </div>
			 
			<div class="control-group">
			 	<label class="control-label"><spring:message code='adv.playend' />：</label>
			 	<div class="controls setPlayStartTime">
			 		<input id="validEndTime" name="adCombo.validEndTime" type="text"  maxlength="20" class="input-small Wdate"
						value="${endDate}" onclick="var date=new Date(),today=date.getValidDate();clearChannelData();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'validStartTime\',{d:0})||\'+today+\'}'})" class="required"/>
						<label class="info-messages"><p></p></label>
			 	</div>
			</div>
			<div class="control-group setStartTime">
			 	<label class="control-label"><spring:message code='combo.playstart' />：</label>
			 	<div class="controls">
				 	<input id="startTime" name="adCombo.startTime" type="text" maxlength="20" class="input-small Wdate"
				     value="${startTime}" onclick="var date=onClickByStartTime();clearChannelData();WdatePicker({dateFmt:'HH:mm:ss',isShowClear:true,isShowToday:false,minDate:'00:00:00',maxDate:date.endTime});"/>
					 <label class="info-messages"><p></p></label>
				</div>
			</div>
			<div class="control-group setEndTime">
			 	<label class="control-label"><spring:message code='combo.playend' />：</label>
			 	<div class="controls">
			 	<input id="endTime" name="adCombo.endTime" type="text" maxlength="20" class="input-small Wdate"
				     value="${endTime}" onclick="var date=onClickByEndTime();clearChannelData();WdatePicker({dateFmt:'HH:mm:ss',isShowClear:true,isShowToday:false,minDate:date.startTime,maxDate:'23:59:59'});"/>
				 <label class="info-messages"><p></p></label>
				 </div>
			</div>
		 <div class="control-group" id="YGArea">
				<label class="control-label"><spring:message code='operators.area' />:</label>
				<div class="controls">
					<input type="text" id="ygarea" name="adCombo.ygarea" class="area-duoxuan" value="${adCombo.area}" data-value="" readonly="readonly">
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
		        <form:hidden path="adCombo.channelIds" id="channelIds"/> 
		    </div>
			<div class="control-group control-group-auto" style="height:37px">
				<label class="control-label"><spring:message code='combo.dateplan' />:</label>
				<div class="controls">
 				<form:hidden path="adCombo.week" id="week"/>
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
						<form:input path="adCombo.showTime" id="showTime" htmlEscape="false" maxlength="50" class="required digits showTime" min="1"/>
					    <span id="time-prompt " style="color:green;"><spring:message code='adv.prompt' /></span>
					</div>
				</div>
					<div class="control-group">
					<label class="control-label"><spring:message code='combo.intervaltime' />:</label>
					<div class="controls">
						<form:input path="adCombo.intervalTime" id="intervalTime" htmlEscape="false" maxlength="50" class="required digits intervalTime" />
					    <span id="time-prompt1 " style="color:green;"><spring:message code='adv.prompt1' /></span>
					</div>			
				</div>
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.cycle' />:</label>
					<div class="controls">
						<form:input path="adCombo.showCount" id="showCount" htmlEscape="false" maxlength="50" class="required digits" readonly="true"/>
						<span id="time-beyond" style="color:red;"></span>
					</div>
				</div>
            </div>
			<div id="rollSet" style="display:none;">
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.pictureTimes' />:</label>
					<div class="controls">
						<form:input path="adCombo.pictureTimes" id="pictureTimes" htmlEscape="false" type="number" maxlength="50" min="1" class="required digits"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><spring:message code='combo.pictureInterval' />:</label>
					<div class="controls">
						<form:input path="adCombo.pictureInterval" id="pictureInterval" htmlEscape="false" type="number" maxlength="50" min="0" class="required digits"/>
						<span id="time-prompt2 " style="color:green;"><spring:message code='adv.prompt2' /></span>
					</div>
				</div>

			</div>
            </div>
				
			</c:when>
			
			<c:otherwise>
			<div class="control-group">
			<label class="control-label"><spring:message code='adv.adcombo' />:</label>
			<div class="controls">
				<form:select path="adCombo.id" class="required" id="adcombo_id">
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getAdComboListByCurrentUser()}" itemLabel="comboName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
			
			</div>
			
		<%-- 	<div class="control-group">
				<label class="control-label"><spring:message code='adv.selldate' />:</label>
				<div class="controls">
					<select class="required" id="SellData">																							
					</select>
				</div>
			</div> --%>
				
			</c:otherwise>
		
		</c:choose>
	
		<div class="control-group" id="adcombo_id_type" style="display:none">
			<label class="control-label"><spring:message code='adv.type' />:</label>
			<div class="controls">
				<label class="lbl"></label>
			</div>
		</div>
		<div class="control-group" id="move" style="display:none">
				<label class="control-label"><spring:message code='position.velocity' />:</label>
				<div class="controls">
					<form:select path="velocity" class="required">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:getDictList('roll_speed_set')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
				</div>
		</div>
		<div class="control-group" id="sd_gd_fx" style="display:none">
		 	 		<label class="control-label"><spring:message code='sd_gd_fx' />：</label>
		 	 		<div class="controls">
						<form:select path="sdFx" class="required">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:getDictList('ad_gd_fx')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
		<div class="control-group" id="hd_gd_fx" style="display:none">
		 	 		<label class="control-label"><spring:message code='hd_gd_fx' />：</label>
		 	 		<div class="controls">
						<form:select path="hdFx" class="required">
							<option value=""> <spring:message code='userform.select' /></option>
							<form:options items="${fns:getDictList('ad_gd_fx')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
		</div>
		<div class="control-group playTime">
			<label class="control-label"><spring:message code='adv.playtime' />：</label>
			<div class="controls">
				<form:input path="playTime" htmlEscape="false" maxlength="6" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='control.sdversion' />：</label>
			<div class="controls special-contrals">
				<form:select path="sdversion" multiple="multiple" style="width:auto;min-width:220px;">
					<form:options items="${fns:getControllVersionList()}" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='control.hdversion' />：</label>
			<div class="controls special-contrals">
				<form:select path="hdversion" multiple="multiple" style="width:auto;min-width:220px;">
					<form:options items="${fns:getControllVersionList()}" htmlEscape="false"/>
				</form:select>
				<span id="erron_span" style="color:red"></span>
			</div>
		</div>

			<input id="resourceType" name="resourceType" type="hidden" value="${resourceType}"/>
			<form:hidden path="fileSize"/> 
		   <%-- <form:hidden path="vedioImagePath"/> --%>
		<div class="control-group control-group-auto" id="adv_image">
		     <label class="control-label"><spring:message code="adv.image"/>：</label>
		     <div class="controls">		     	
		         <div class="dropzoneStyle" id="upload_adv_image">
		          <!--    <p class="uploadfile_error"></p> -->
		             <div class="switchResolution">
			             <ul>
							 <%--<li><div class="add_btn" id="control_add" title="<spring:message code='control.add'/>"></div></li>--%>
								 <li><label for="advStandard"><spring:message code="adv.standard"/>:</label><input type="button" class="advStandard" name="1" id="advStandard"/></li>
								 <li><label for="advHd"><spring:message code="adv.hd"/>:</label><input type="button" class="advHd" id="advHd" name="0"/></li>
						 </ul>
		             </div>
		             <div class="resourcesContent resourcesSd"><div><ul class="list_content advStandard_list_Content"></ul></div><p class="uploadfile_error"></p></div>
		             <div class="resourcesContent resourcesHd"><div><ul class="list_content advHd_list_Content"></ul></div><p class="uploadfile_error"></p></div>
		         </div>

		     </div>
		</div>
		<div class="control-group control-group-auto" id="adv_vedio">
		     <label class="control-label"><spring:message code="adv.vedio"/>：</label>
		     <div class="controls">
		         <div class="dropzoneStyle" id="upload_adv_vedio">
		         	<div class="switchResolution">
			             <ul>
			             	 <li><label for="advHd_vedio"><spring:message code="adv.hd"/>:</label><input type="button" class="advHd" id="advHd_vedio" name="0"/></li>
				             <li><label for="advStandard_vedio"><spring:message code="adv.standard"/>:</label><input type="button" name="1" class="advStandard"  id="advStandard_vedio"/></li>
			             </ul>
		             </div>
		             <div class="resourcesContent resourcesSd"><div><ul class="list_content advStandard_list_Content"></ul></div><p class="uploadfile_error"></p></div>
		             <div class="resourcesContent resourcesHd"><div><ul class="list_content advHd_list_Content"></ul></div><p class="uploadfile_error"></p></div>
		         </div>
		     </div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.isflag' />:</label>
			<div class="controls">
				<form:select path="isFlag" id="adv_isFlag" >
					<form:options items="${fns:getDictList('adv_isflag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			 </form:select>
			</div>
		</div>
		<div class="control-group control-group-remarks" id="add_text" style="display:none;">
			<label class="control-label"><spring:message code='adv.adtext' />:</label>
			<div class="controls linkText">
				<form:textarea path="addText" id="add_text_area" htmlEscape="false" rows="3" maxlength="60" style="resize:none" class="input-xlarge required "/>
				&nbsp;&nbsp;<span style="color:red" id="v_addText"></span><a target="_blank"><spring:message code='adv.link' />...</a>
			</div>
			
		</div>
		 
		<div class="form-actions">
		
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
			&nbsp;
			<shiro:hasPermission name="sys:adv:edit">
				<input id="btnSubmit" class="btn btn-primary " type="submit" value='<spring:message code='adv.preview'/>'/>&nbsp;
		 
			</shiro:hasPermission>
			<!-- <input id="btn-go" type="button"> -->
		</div>
	</form:form>
	
	<div class="channel_content">
	    <ul class="channel_content_ul">
	    </ul>
	    <div class="ad_action">
	        <input class="btn_close" type="button" value="<spring:message code='cancle' />">       
	        	        <input class="btn_submit" type="button" value="<spring:message code='sure' />">
	    </div>
   </div>
	<div class="control_info">
		<div class="info_header_content">
		<p class="info_header"><spring:message code="adv.hintinfo"/></p>
		<div class="icon_control"></div>
		</div>
		<div class="info"><p><spring:message code="adv.fileNotnull"/></p></div>
		<div class="info_control_content">
		 <input type="button" class="btn_submit" value='<spring:message code="sure"/>'>
		 <input type="button" class="btn_clear" value="<spring:message code="cancle"/>">
		 </div>
	</div>
	<div class="file_loading">
	<div class="l_left"></div>
	<div class="l-right"><spring:message code="vedio.uploading"/></div>
	</div>
	<div class="remove_data"></div>
	    <script src="${ctxStatic}/dropzone/dropzone.js"></script>
		<script src="${ctxStatic}/adv/adv1.js"></script>
		<script src="${ctxStatic}/adv/jquery.dad.js"></script>
		<script src="${ctxStatic}/adv/advQuick.js"></script>
		<script src="${ctxStatic}/adcombojs/channel_select.js"></script>
		<script src="${ctxStatic}/adcombojs/channel_selectMany.js"></script>
</body>
</html>