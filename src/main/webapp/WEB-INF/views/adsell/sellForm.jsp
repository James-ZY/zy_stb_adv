<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='sell.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<script src="${ctxStatic}/common/language.js"></script>
	<script src="${ctxStatic}/common/validDate.js"></script>
	<style type="text/css">
       .controls .networkContent{width:475px;min-height:100px;max-height:200px;height:auto;border: 1px solid #ccc;-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);-moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);-webkit-transition: border linear .2s,box-shadow linear .2s;-moz-transition: border linear .2s,box-shadow linear .2s;-o-transition: border linear .2s,box-shadow linear .2s;transition: border linear .2s,box-shadow linear .2s;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;overflow: hidden;overflow-y: auto;}
	   .networkContent ul{list-style-type: none;overflow-y:auto;margin-left:0px;}
	   .networkContent ul li{min-width: 150px;width:auto!important;height: 30px;position: relative;line-height: 30px;float:left}
	   .networkContent ul li .option{display:block;position: absolute;width: 16px;height: 16px;left: 10px;top: 7px; }
       .networkContent ul li label{display: block;position: absolute;left: 35px;width: 115px;height: 30px;line-height: 30px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;}
       .networkContent ul li .option[name="0"]{background:url("../../static/images/icon/ic_checkbox_false.png");outline: none;border: 0}
        .networkContent ul li .option[name="1"]{background:url("../../static/images/icon/ic_checkbox_ture.png");outline: none;border: 0}
       .networkContent ul li .noselected{background:url("../../static/images/icon/noSelected.png");}
       .networkContent ul li input:hover{cursor: pointer}
       .networkContent ul li label:hover{cursor: pointer}
       .networkContent ul:after{clear:both;content:"";display:block;overflow:hidden}
       .networkContent .ad_action {position: relative;bottom: 0px;width: 100%;height: 40px;border-top: 1px solid gainsboro}
       .network_content .ad_action input{display: inline-block;width: 60px;height: 30px;outline: none;float: right;margin-right: 25px;margin-top:5px;border-radius: 6px;border: 0;background: gainsboro}
       .network_content .ad_action input:hover{cursor: pointer;background:#228D9F;color: #fff }
       input[readonly]{background-color: #fff}
       input[disabled]{background-color: rgb(243, 243, 243)}
       .attention-info{display:none;position: relative;}
	   .attention-info p{margin-bottom:0}
	   .attention-info .close{position: absolute;right:20px;top:50%;margin-top:-10px;}
	</style>
	<script sell="text/javascript">
		$(document).ready(function() {
			 var host=accipiter.getRootPath();
			$("#inputForm").validate({
				rules: {
					 
				},
				messages: {
				 
				 
				},
				submitHandler: function(form){
				
					 var id=$("#id").val();
					 if(id!=""){
						 alertJudge(form);
					 }else{
						timeCheck(form); 
						var end = document.getElementById("end").innerText;
						if(end != null && end != ""){
							return false;
						}else{
				  		 loading(accipiter.getLang("loading"));
	        	    	 form.submit();							
						}
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
			
			$("#startDate").change(function(){
				var endDate = $("#endDate").val();
				if(endDate == null || endDate==""){
					return false;
				}
				timeCheck(null);
			});
	        $("#endDate").change(function(){
	        	var startDate = $("#startDate").val();
				if(startDate == null || startDate==""){
					return false;
				}
				timeCheck(null);
			});
		   /* 获取相应条件下套餐数据 */
		   getadvInfo();
		   function getadvInfo(){
			   var advType=$(".typeId").children('option:selected').val();
/* 			   var netIdList=getSelectedData();
 */			   if(advType==""){
				   var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
		           $("#adcomboId").html("");
		           $("#adcomboId").append(html);
		           $("#adcomboId").select2();
		           return;
			   }
			   if(advType!=""){
				   var data={"typeId":advType};
				   var postData = JSON.stringify(data);
				   var OldadComboId = $("#OldadComboId").text();
				   var OldadComboName = $("#OldadComboName").text();
				   $.ajax({
					     type:"post",
			             url:host+"/adv/combo/find_combo_by_type_net",
			             data:postData,
			             async:false,
			             contentType:"application/json; charset=UTF-8",
			             dataType:"json",
			             success:function(data){
			            	 var i =0;
			            	 var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
				        	 $.each(data,function(commentIndex,comment){
				        		 if(OldadComboId == comment.id){
					        		  html+='<option selected="selected" value='+comment.id+'>'+comment.comboName+'</option>'; 
					        		  i++;					        		  
					        		 }else{
						        	  html+='<option value='+comment.id+'>'+comment.comboName+'</option>';  
					        		 }
				        		 });
                              if(i==0 && OldadComboId != null  &&OldadComboId != ""){
				        		  html+='<option selected="selected" value='+OldadComboId+'>'+OldadComboName+'</option>'; 
                              }
				        	  $("#adcomboId").html("");
				        	  $("#adcomboId").append(html);
				        	  $("#adcomboId").select2();
			             }
				   })
			   }
		   };
		   /* 修改时显示选中数据 */
		    function showSelectedNetWork(data){
		    	$.each(data,function(commentIndex,comment){
		    	var netWorkId=comment;
	   			 $(".networkContent ul").find('li[id='+netWorkId+']').find("input").css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
	    		 $(".networkContent ul").find('li[id='+netWorkId+']').find("input").attr({"name":"1"});
		    	});
		    }
		   function showSlectedData(){
			   var SelectedInfo=$("#SelectedInfo").text();
			   var advId=$("#id").val();
			   if(SelectedInfo!=""&&advId!==""){
				   var info=$.parseJSON(SelectedInfo);
				   var typeId=info.typeId;
				   var len=$("#type_id").find("option").length;
/* 				   showSelectedNetWork(info.networkIdList);
 */				   for(var i=0;i<len;i++){
					   var id=$(".typeId").find('option:eq('+i+')').val();
					   if(id==typeId){
						   $(".typeId").find('option:eq('+i+')').prop('selected', true);
					   }
				   } 
				   $("#type_id").select2();
				   getadvInfo();
				   var adComboId=$("#OldadComboId").text();
				   $("#adcomboId").select2("val",adComboId);
			   }
		   }
		   showSlectedData();
			$(".typeId").change(function(){
				getadvInfo();
			});
			
			$("#adcomboId").change(function(){
				$("#startDate").val("");
				$("#endDate").val("");
				var newComBoId=$("#adcomboId").find('option:selected').val();	
				if(newComBoId == undefined || newComBoId ==null || newComBoId ==""){
					return false;
				}
				 var data={"id":newComBoId};
				   var postData = JSON.stringify(data);
				   $.ajax({
					     type:"post",
			             url:host+"/adv/combo/findNetWorkByCombo",
			             data:postData,
			             async:false,
			             contentType:"application/json; charset=UTF-8",
			             dataType:"json",
			             success:function(data){
			            	 var html = "";
				        	 $.each(data,function(commentIndex,comment){
				        		 html= html+"," +comment.networkName; 
					         });
				        	 $("#netWork").val(html.substring(1, html.length));
			             }
				   })
				
			});
			/* 修改套餐销售时，限定判断 */
			var oldAdvertiserId=$("#advertiserId").find('option:selected').val();
			function alterStatus(){
				 var exit=$("#adSell_id").text();
				 var StartDate=$("#oldStartDate").text();
				 var maxDate=$("#maxDate").text();
				 var isHaveAdv=$("#isHaveAdv").text();
 
				 if(exit!=""){
				 
					 $(".attention-info").css("display","block");
					 if(isHaveAdv!="true"){
						 $(".disable").find("input").attr("disabled",true);
						 $(".disable").find("select").attr("disabled",true);
						 $("#netWork").attr("disabled",true);
						 $("#startDate").attr("disabled",true);
					 }
 				}
			}
			alterStatus();
			/*修改时提交验证  */
			function alertJudge(form){
				var oldAdvertiser=oldAdvertiserId;
				var newAdvertiser=$("#advertiserId").find('option:selected').val();
				var oldComBoId=$("#OldadComboId").text();
				var newComBoId=$("#adcomboId").find('option:selected').val();
				var oldStartData=$("#oldStartDate").text();
				var newStartData=$("#startDate").val();
				var oldEndData=$("#oldEndDate").text();
				var newEndData=$("#endDate").val();
				var isHaveAdv=$("#isHaveAdv").text();
				var id=$("#adSell_id").text();
				var data ={"oldAdvertiserId":oldAdvertiser,"newAdvertiserId":newAdvertiser,"oldComboId":oldComBoId,"newComboId":newComBoId,
						"oldStartDate":oldStartData,"newStartDate":newStartData,"oldEndDate":oldEndData,"newEndDate":newEndData,
						"isHaveAdv":isHaveAdv,"id":id};
				var postData = JSON.stringify(data);
				 $.ajax({
			          url:"${ctx}/adv/sell/check_alter_sell",
			          type:"POST",
			   		  data:postData,
			   	      async:false,
			          contentType: "application/json; charset=utf-8",  
			          dataType : "json",
			          success:function(data){
			        	  if(data==true){
				        	document.getElementById("end").innerText="";
			        	  	loading(accipiter.getLang("loading"));
			        	    form.submit();
			        	  }else{
			        	  	document.getElementById("end").innerText=accipiter.getLang_(messageLang,"comid.already.select");
			        	  	return false;
			        	  }
			          }
				 })
			}
		});

		function timeIsNotNUll(id){
			document.getElementById("end").innerText="";
			document.getElementById("start").innerText="";
			var flag = true;
			var start = eval(document.getElementById("startDate")).value;
			var end = eval(document.getElementById("endDate")).value;
			if(null == end || "" == end ){
				document.getElementById("end").innerText=accipiter.getLang_(messageLang,"sell.endDate");
				flag = false;
			}
			if(null == start || "" == start ){
				document.getElementById("start").innerText=accipiter.getLang_(messageLang,"sell.startDate");
				flag = false;
			}
			var startDate = new Date(start);
			var endDate = new Date(end);
			if(startDate > endDate){
				if(id==1){
					document.getElementById("end").innerText=accipiter.getLang_(messageLang,"endDate.compare.start");
				}else{
					document.getElementById("start").innerText=accipiter.getLang_(messageLang,"endDate.compare.start");
				}
				flag= false;
			}
			return flag;
		}
		function timeCheck(form){
			var start = document.getElementById("end").innerText;
			var end  = document.getElementById("start").innerText;
			if((null == start || "" == start) && (null == end || "" == end)){
			var combo =document.getElementById("adcomboId");
			var comboId =combo.options[combo.selectedIndex].value; 
			
			 
			var startDate = eval(document.getElementById("startDate")).value;
			var endDate = eval(document.getElementById("endDate")).value;
			var o ={"comboId":comboId,"startDate":startDate,"endDate":endDate};
			//var o = {"comboId":comboId};
			var data = JSON.stringify(o);
			 $.ajax({
		          url:"${ctx}/adv/combo/chekSellTime",
		          type:"POST",
		   		  data:data,
		          contentType: "application/json; charset=utf-8",  
		          dataType : "text",
		          success:function(data){
                      $("#btnSubmit").attr("disabled",true);
		        	  	if(data == "false"){
		        	  		document.getElementById("end").innerText=accipiter.getLang_(messageLang,"comid.already.select");
		        	  		return false;
		        	  	}else if(data == "date"){
		        	  		document.getElementById("end").innerText=accipiter.getLang_(messageLang,"endDate.compare.start");
		        	  		return false;
		        	  	}else if(data == "exception"){
		        	  		document.getElementById("end").innerText=accipiter.getLang_(messageLang,"exception");
		        	  		return false;
		        	  	}else if(data=="noStart"){
		        	  		document.getElementById("start").innerText=accipiter.getLang_(messageLang,"sell.startDate");
		        	  		return false;
		        	  	}else if(data=="noEnd"){
		        	  		document.getElementById("end").innerText=accipiter.getLang_(messageLang,"sell.endDate");
		        	  		return false;
		        	  	}/* else{ 
		        	  		loading(accipiter.getLang("loading"));
		        	    	 form.submit();
		        	  	} */
                      $("#btnSubmit").attr("disabled",false);

		          },
				 error: function (err) {     
			 
					 console.error(err);
					// document.getElementById("test").innerText=err;
			   	}  
        });
			}
		}
		//销售时间必须是在套餐生效的时间范围以内
		/**
		 * 设置时间控件的值随着广告套餐改变而改变
		 */
		 function getValidTime(data){
			 var oldStartDate=data.startDate.split(" ")[0];
			 var validDate= new Date()
			 var myDate = validDate.getValidDate();
			 var currentYear=parseInt(myDate.split("-")[0]); 
			 var currentMonth=parseInt(myDate.split("-")[1]);
			 var currentDate=parseInt(myDate.split("-")[2]);
			 var oldYear=parseInt(oldStartDate.split("-")[0]);
			 var oldMonth=parseInt(oldStartDate.split("-")[1]);
			 var oldDate=parseInt(oldStartDate.split("-")[2]);
			 var newStartDate='';
			 var data={"newStartDate":'',"istrue":true};
			 var newYear='';
			 var newMonth='';
			 var newDate='';
			 if(oldYear==currentYear){
				 newYear=currentYear;
				 if(currentMonth==oldMonth){
					 newMonth=currentMonth;
					if(oldDate<=currentDate){
						newDate=currentDate;
					    data.istrue=true;
					}else{
						newDate=oldDate;
					    data.istrue=false;
					}
					newStartDate=newYear+"-"+newMonth+"-"+newDate;
					data.newStartDate=newStartDate;
					return data;
				 }
				 if(currentMonth<oldMonth){
					 newMonth=oldMonth;
					 newDate=oldDate;
				     newStartDate=newYear+"-"+newMonth+"-"+newDate;
				     data.newStartDate=newStartDate;
					 data.istrue=false;
					 return data;
				 }
				 if(currentMonth>oldMonth){
					 newMonth=currentMonth;
					 newDate=currentDate;
				     newStartDate=newYear+"-"+newMonth+"-"+newDate;
				     data.newStartDate=newStartDate;
					 data.istrue=true;
					 return data;
				 }
			 }
			 if(currentYear>oldYear){
				 newYear=currentYear;
				 newMonth=currentMonth;
				 newDate=currentDate;
			     newStartDate=newYear+"-"+newMonth+"-"+newDate;
			     data.newStartDate=newStartDate;
				 data.istrue=true;
				 return data;
			 }
			 if(currentYear<oldYear){
				 newYear=oldYear;
				 newMonth=oldMonth;
				 newDate=oldDate;
			     newStartDate=newYear+"-"+newMonth+"-"+newDate;
			     data.newStartDate=newStartDate;
				 data.istrue=false;
				 return data;
			 }
			 
		}
		function onClickByStartDate(){
			 var o = {}; 
 			 var a=document.getElementById("adcomboId");
			 var b=a.options[a.selectedIndex].value; 
			 if(null == b || ""== b){
				 document.getElementById("start").innerText=accipiter.getLang_(messageLang,"adcombo_select");
				 if($('startDate').attr("readonly")==false){
					 $('startDate').attr("readonly",true) ;
				 }
				 return;
			 }else{
				 document.getElementById("start").innerText="";
				 if($('startDate').attr("readonly")==true){
					 $('startDate').attr("readonly",false) ;
				 }
				var o ={"id":b};
				var data = JSON.stringify(o);
				 $.ajax({
			          url:"${ctx}/adv/combo/find_adcombo_by_id",
			          async: false,
			          type:"POST",
			   		  data:data,
			          contentType: "application/json; charset=utf-8",  
			          dataType : "json",
			          success:function(data){
			        	  if(null != data){
			        		  o = data;
			        		  console.log(data);
			        		  o.startDate=getValidTime(data).newStartDate;
			        		  var exit=$("#adSell_id").text();
					    	  var isHaveAdv=$("#isHaveAdv").text();
			        		  if(o.isChannel==false){
					    		if(exit!=""&&isHaveAdv=="true"){
						        		o.startDate=$("#oldStartDate").text();
					    		}
			        		  }
			        		  else{
				        		  if(getValidTime(data).istrue){
				        			  if(exit!=""){
						        		  o.startDate=$("#oldStartDate").text(); 
				        			  }		        		   
				        		  }		        		  
			        		  }
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
			   });
				 
			 }
			 return o;

		}
		
		/**
		 * 设置时间控件的值随着广告套餐改变而改变
		 */
		function onClickByEndDate(){
			 var rData = {}; 
 			 var a=document.getElementById("adcomboId");
			 var b=a.options[a.selectedIndex].value; 
			 if(null == b || ""== b){
				 document.getElementById("end").innerText=accipiter.getLang_(messageLang,"adcombo_select");
				 if($('endDate').attr("readonly")==false){
					 $('endDate').attr("readonly",true) ;
				 }
				 return;
			 }else{
				var startDate = eval(document.getElementById("startDate")).value;
				if(null == startDate || "" ==startDate ){
					 document.getElementById("end").innerText=accipiter.getLang_(messageLang,"please_select_start");
					 if($('endDate').attr("readonly")==false){
						 $('endDate').attr("readonly",true) ;
					 }
					 return;
				}
				 document.getElementById("end").innerText="";
				 if($('endDate').attr("readonly")==true){
					 $('endDate').attr("readonly",false) ;
				 }
				var o ={"id":b};
				var data = JSON.stringify(o);
				 $.ajax({
			          url:"${ctx}/adv/combo/find_adcombo_by_id",
			          async: false,
			          type:"POST",
			   		  data:data,
			          contentType: "application/json; charset=utf-8",  
			          dataType : "json",
			          success:function(data){
			        	  if(null != data){
			        			  rData = data;
			        			  rData.startDate=getValidTime(data).newStartDate;
			        	  }else{
			        		  if($('endDate').attr("readonly")==false){
			 					 $('endDate').attr("readonly",true) ;
			 				 }
			        	  }
			       
			          },
					 error: function (err) {     
						 console.error(err);
						 if($('endDate').attr("readonly")==false){
							 $('endDate').attr("readonly",true) ;
						 }
				   	}  
			   });
				 
			 }
			 var exit=$("#adSell_id").text();
			 var StartDate=$("#oldStartDate").text();
			 var maxDate=$("#maxDate").text();
			 var isHaveAdv=$("#isHaveAdv").text();
			 if(exit!=""&&isHaveAdv=="false"){
				 rData.startDate=maxDate;
			 }
			 return rData;
		}

	</script>
</head>
<body>
    <div class="top_position">
	    <div class="top_position_lab"><spring:message code='combo.position' ><spring:message code="sell.update" /></spring:message></div>
	    <ul>
		    <li><spring:message code='sell.adcombo' /></li>
		    <li>></li>
		    <li><spring:message code='combo.manage' /></li>
		    <li>></li>
		    <li>
		    	<shiro:hasPermission name="sys:sell:edit"><a href="${ctx}/adv/sell/form?id=${adSell.id}">
		        <c:choose><c:when test="${not empty adSell.id}"></c:when>
        	    <c:otherwise><spring:message code='sell.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		    </li>
	    </ul>
    </div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/adv/sell"><spring:message code='sell.list' /></a></li>
		<shiro:hasPermission name="sys:sell:edit"><li  class="active"><a href="${ctx}/adv/sell/form?id=${adSell.id}">
		<c:choose><c:when test="${not empty adSell.id}"><spring:message code='sell.update' /></c:when>
        			<c:otherwise><spring:message code='sell.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
	</ul>
	<form:form id="inputForm" modelAttribute="adSell" action="${ctx}/adv/sell/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
			<p id="adSell_id" style="display:none">${adSell.id}</p>
			<p id="isHaveAdv"style="display:none">${isHaveAdv}</p>
			<p id="oldStartDate"style="display:none">${startDate}</p>
			<p id="oldEndDate"style="display:none">${endDate}</p>
		    <p id="maxDate" style="display:none">${maxDate}</p>
		<tags:message content="${message}"/>
		<p id="SelectedInfo" style="display:none">${typeAndNetwork}</p>
		<p id="OldadComboId" style="display:none">${adSell.adCombo.id}</p>
		<p id="OldadComboName" style="display:none">${adSell.adCombo.comboName}</p>
	 	 
		<div class="attention-info show alert alert-success">
		<p><strong><spring:message code='adv.PositionAttention' />:</strong>&nbsp;&nbsp;<spring:message code='adv.alertAttention' /></p>
		<button data-dismiss="alert" class="close">×</button>
		</div>
		 <div class="control-group">
			<label class="control-label"><spring:message code='adv.type' />:</label>
			<div class="controls disable">
				<select id="type_id" class="typeId required">
						<option value=""> <spring:message code='userform.select' /></option>
						<c:forEach items="${fns:getAdTypeList()}" var="adType">
						<option value="${adType.id}"> ${adType.typeName}</option>
						</c:forEach>					 
				</select>
			</div>
		</div>		
<%-- 		<div class="control-group control-group-auto control-network">
			<label class="control-label"><spring:message code='network.tree' />:</label>
			<div class="controls disable">
				<div class="networkContent">
					   <ul class="network_content_ul"></ul>
			    </div>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label"><spring:message code='sell.adcombo' />:</label>
			<div class="controls disable">
				<form:select path="adCombo.id" class="required" id="adcomboId">
						<option value=""> <spring:message code='userform.select' /></option>					 
				</form:select>
			</div>
		</div>
	   <div class="control-group">
			<label class="control-label"><spring:message code='network.tree' />:</label>
			<div class="controls">
                <input id="netWork" name="netWork" readonly="readonly" type="text" value="${netWorkStr}" maxlength="200">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"><spring:message code='sell.advertiser' />:</label>
			<div class="controls disable">
				<form:select path="advertiser.id" class="required" id="advertiserId" >
						<option value=""> <spring:message code='userform.select' /></option>
						<form:options items="${fns:getAdvertiserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		 
	  <div class="control-group">
		 	<label class="control-label"><spring:message code='sell.startDate' />：</label>
		 	<div class="controls">
		 		<input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${startDate}" onclick="var date=onClickByStartDate();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,startDate:date.startDate,maxDate:date.endDate,minDate:date.startDate})" class="required" onchange="timeIsNotNUll(0)"/>
				<span id="start" style="color:red"></span>
		 	</div>
		 </div>
		 
		  <div class="control-group">
		 	<label class="control-label"><spring:message code='sell.endDate' />：</label>
		 	<div class="controls">
		 		<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="${endDate}" onclick="var date=onClickByEndDate();WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,startDate:date.endDate,minDate:date.startDate,maxDate:date.endDate})" class="required" onchange="timeIsNotNUll(1)"/>
					<span id="end" style="color:red"></span>
		 	</div>
		 	
		 </div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sell:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>