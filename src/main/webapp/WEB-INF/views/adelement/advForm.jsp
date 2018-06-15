<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/dropzone/css/basic.css">
    	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
    	<link rel="stylesheet" href="${ctxStatic}/layer/skin/myskin/style.css">
        <script src="${ctxStatic}/layer/layer.js"></script>
    	<script src="${ctxStatic}/common/language.js"></script>

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
</style>
	<script type="text/javascript">
		$(document).ready(function() {
		 
			$("#inputForm").validate({
				 
				submitHandler: function(form){
					var addText = eval(document.getElementById("v_addText")).value;
					var startDate =document.getElementById("startdate_span").innerText;
					var endDate = document.getElementById("enddate_span").innerText;
					if((null == addText || "" == addText ) &&(null == startDate || "" == startDate)
							&&(null == endDate || "" == endDate)){
			 
						form.submit();
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
		/**
		 * 设置时间控件的值随着广告套餐改变而改变
		 */
		function onClickByStartDate(){
			 var o = {}; 
 			 var a=document.getElementById("adcombo_id");
			 var b=a.options[a.selectedIndex].value; 
			 var c=$("#advertiser_id").find("option:selected").attr("value");
	 
			 if(null == b || ""== b ){
				 document.getElementById("startdate_span").innerText=accipiter.getLang_(messageLang,"adcombo_select");
				 if($('startDate').attr("readonly")==false){
					 $('startDate').attr("readonly",true) ;
				 }
			 }else{
				 document.getElementById("startdate_span").innerText="";
				 if($('startDate').attr("readonly")==true){
					 $('startDate').attr("readonly",false) ;
				 }
				 var  sellDate=$("#adCombo_SellData").find("option:selected").val();
				 var startDate=sellDate.split("~")[0];
				 var endDate=sellDate.split("~")[1];
				 var data={"startDate":startDate,"endDate":endDate};
				 /*  var o ={"id":b,"advId":c};
				 var data = JSON.stringify(o);
				 $.ajax({
			          url:"${ctx}/adv/adelement/getSellDate",
			          async: false,
			          type:"POST",
			   		  data:data,
			          contentType: "application/json; charset=utf-8",  
			          dataType : "json",
			          success:function(data){
			        	  if(null != data){
			        		  o = data;
			        		  console.log(data);
			        	  }else{
			        		  if($('startDate').attr("readonly")==false){
			 					 $('startDate').attr("readonly",true) ;
			 				 }
			        	  }
			       
			          },
					 error: function (err) {     
						 console.error(err);
						 if($('startDate').attr("readonly")==false){
							 $('startDate').attr("readonly",true) ;
						 }
				   	}  
			   });  */
				 
			 } 
			 return data;

		}
		
		/**
		 * 设置时间控件的值随着广告套餐改变而改变
		 */
		function onClickByEndDate(){
			 var o = {}; 
 			 var a=document.getElementById("adcombo_id");
			 var b=a.options[a.selectedIndex].value; 
			 var c=$("#advertiser_id").find("option:selected").attr("value");;
			 if(null == b || ""== b){
				 document.getElementById("enddate_span").innerText=accipiter.getLang_(messageLang,"adcombo_select");
				 if($('endDate').attr("readonly")==false){
					 $('endDate').attr("readonly",true) ;
				 }
				 return;
			 }else{
				var startDate = eval(document.getElementById("startDate")).value;
				if(null == startDate || "" ==startDate ){
					 document.getElementById("enddate_span").innerText=accipiter.getLang_(messageLang,"please_select_start");
					 if($('endDate').attr("readonly")==false){
						 $('endDate').attr("readonly",true) ;
					 }
					 return;
				}
				 document.getElementById("enddate_span").innerText="";
				 if($('endDate').attr("readonly")==true){
					 $('endDate').attr("readonly",false) ;
				 }
				 var  sellDate=$("#adCombo_SellData").find("option:selected").val();
				 var startDate=sellDate.split("~")[0];
				 var endDate=sellDate.split("~")[1];
				 var data={"startDate":startDate,"endDate":endDate};
				 
			 }
			 return data;
		}
	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code="position"/>:</div>
	    <ul>
		    <li><spring:message code="adv.manage"/></li>
		    <li>></li>
		    <li><spring:message code="adv.issue"/></li>
		    <li>></li>
		    <li>
		 	<shiro:hasPermission name="sys:adv:edit"><a href="${ctx}/adv/adelement/form?id=${adelement.id}">
		<c:choose><c:when test="${not empty adelement.id}"><spring:message code='adv.update' /></c:when>
        			<c:otherwise><spring:message code='adv.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/adelement"><spring:message code='adv.list' /></a></li>
		<shiro:hasPermission name="sys:adv:edit"><li  class="active"><a href="${ctx}/adv/adelement/form?id=${adelement.id}">
		<c:choose><c:when test="${not empty adelement.id}"><spring:message code='adv.update' /></c:when>
        			<c:otherwise><spring:message code='adv.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adelement" action="${ctx}/adv/adelement/preview" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<form:hidden path="path"/> 
		<form:hidden path="status"/>
		<form:hidden path="hdPath"/>
	    <form:hidden path="isPlay"/>
	    <form:hidden path="comboId"/>
	    <form:hidden path="isSd"/>
	    <form:hidden path="isHd"/>
	    <form:hidden path="adTypeId"/>
	    <form:hidden path="sdShowParam"/> 
	    <form:hidden path="hdShowParam"/>
		<input id="comboFlag"  type="hidden" value="">
	    <input id="sdMaxNC" name="sdMaxNC" type="hidden" value="${sdMaxNC}">
		<input id="hdMaxNC" name="hdMaxNC" type="hidden" value="${hdMaxNC}">
		<input id="bpTT"  type="hidden" value="10">
		<input id="bpTS"  type="hidden" value="30">
		<input id="psTs"  type="hidden" value="5">
	    <p id="judgeStatus" style="display:none">${adelement.status}</p>
	    <p id="isNotAdv" style="display:none">${isNotAdv}</p>
	    <p id="advId" style="display:none">${advId}</p>
	    <p id="sellTime" style="display:none">${sellTime}</p>
	    <p id="isPosition" style="display:none">${isPosition}</p>
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.name' />:</label>
			<div class="controls">
				<form:input path="adName" htmlEscape="false" maxlength="50" class="required"/>
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
					<label class="control-label"><spring:message code='adv.adcombo' />:</label>
					<div class="controls">
						<form:select path="adCombo.id" class="required" id="adcombo_id">
								<option value=""> <spring:message code='userform.select' /></option>
																							
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><spring:message code='adv.selldate' />:</label>
					<div class="controls">
						<select class="required" id="adCombo_SellData">	
						<option value=""> <spring:message code='userform.select' /></option>																					
						</select>
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
			
			<div class="control-group">
				<label class="control-label"><spring:message code='adv.selldate' />:</label>
				<div class="controls">
					<select class="required" id="adCombo_SellData">																							
					</select>
				</div>
			</div>
				
			</c:otherwise>
		
		</c:choose>
	
		<div class="control-group" id="adcombo_id_type" style="display:none">
			<label class="control-label"><spring:message code='adv.type' />:</label>
			<div class="controls">
				<label class="lbl"></label>
			</div>
		</div>
		<div class="control-group" id="adcombo_id_chlidType" style="display:none">
			<label class="control-label"><spring:message code='adv.son.type' />:</label>
			<div class="controls">
			    <p id="chlidType" style="display:none">${adelement.childAdType.id}</p>
				<form:select path="childAdType.id" class="required" id="adcombo_chlidType">
				  <option value=""> <spring:message code='userform.select' /></option>																			
				</form:select>
			</div>
		</div>	 
	 	<div class="control-group">
			<label class="control-label"><spring:message code='adv.playstart' />：</label>
			<div class="controls">
				<input id="startDate" name="startDate" type="text"  readonly="readonly" maxlength="200" class="input-small Wdate data_content"
				value="${startDate}" onclick="var date=onClickByStartDate();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:date.endDate,minDate:date.startDate})"/>
				<span id="startdate_span" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='adv.playend' />：</label>
			<div class="controls">
				<input id="endDate" name="endDate" type="text"  readonly="readonly" maxlength="200" class="input-small Wdate data_content"
				value="${endDate}" onclick="var date=onClickByEndDate();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:date.startDate,maxDate:date.endDate})"/>
				<span id="enddate_span" style="color:red"></span>
			</div>
		</div>
		<div id="timeShowSet" style="display:none;">
		<div class="control-group setStartTime">
			<label class="control-label"><spring:message code='combo.playstart' />：</label>
			<div class="controls">
				<input id="startTime" name="adCombo.startTime" type="text" maxlength="20" class="input-small Wdate" value="${startTime}" readonly="true"/>
				<label class="info-messages"><p></p></label>
			</div>
		</div>
		<div class="control-group setEndTime">
			<label class="control-label"><spring:message code='combo.playend' />：</label>
			<div class="controls">
				<input id="endTime" name="adComboendTime" type="text" maxlength="20" class="input-small Wdate" value="${endTime}" readonly="true"/>
				<label class="info-messages"><p></p></label>
			</div>
		</div>
		</div>
		<div id="showTimeSet" style="display:none;">
			<div class="control-group">
				<label class="control-label"><spring:message code='combo.showtime' />:</label>
				<div class="controls">
					<form:input path="adCombo.showTime" id="showTime" htmlEscape="false" readonly="true"/>
					<span id="time-prompt " style="color:green;"><spring:message code='adv.prompt' /></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='combo.intervaltime' />:</label>
				<div class="controls">
					<form:input path="adCombo.intervalTime" id="intervalTime" htmlEscape="false" readonly="true"/>
					<span id="time-prompt1 " style="color:green;"><spring:message code='adv.prompt1' /></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='combo.cycle' />:</label>
				<div class="controls">
					<form:input path="adCombo.showCount" id="showCount" htmlEscape="false" readonly="true"/>
					<span id="time-beyond" style="color:red;"></span>
				</div>
			</div>
		</div>
		<div id="rollSet" style="display:none;">
			<div class="control-group">
				<label class="control-label"><spring:message code='combo.pictureTimes' />:</label>
				<div class="controls">
					<form:input path="adCombo.pictureTimes" id="pictureTimes" type="number" htmlEscape="false" readonly="true"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><spring:message code='combo.pictureInterval' />:</label>
				<div class="controls">
					<form:input path="adCombo.pictureInterval" id="pictureInterval" type="number" htmlEscape="false" readonly="true"/>
					<span id="time-prompt2 " style="color:green;"><spring:message code='adv.prompt2' /></span>
				</div>
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
		          <!--    <p class="uploadfile_error"></p> -->
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
		<script src="${ctxStatic}/adv/adv.js"></script>
		<script src="${ctxStatic}/adv/jquery.dad.js"></script>
</body>
</html>