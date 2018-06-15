<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='type.manage' /></title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/common/style.css">
	<link rel="stylesheet" href="${ctxStatic}/common/nav-tabs.css">
	<link rel="stylesheet" href="${ctxStatic}/common/jquery-ui.css">
	<script src="${ctx}/static/scripts/common/common.js"></script>
	<script src="${ctx}/static/common/jquery-ui.js"></script>
	<script src="${ctxStatic}/adv/drag1.js"></script>
	<script type="text/javascript">
        $(document).ready(function() {
            var maxWidth = 714;
            var maxHeight = 570;
            $("#inputForm").validate({

                submitHandler: function(form){
                    if ($('.save_box').length > 0){
                        var selTrack = "";
                        $('.save_box').each(function () {
                            var x = $(this).data("x");
                            var y = $(this).data("y");
                            selTrack += x+','+y+'-';
                        });
                        if (selTrack != '') {
                            selTrack = selTrack.substring(0, selTrack.lastIndexOf('-'));
                        }
                        $("#coordinates").val(selTrack);
                        loading(accipiter.getLang("loading"));
                        form.submit();
                    }else{
                        $("#selDataResult").html("");
                        var html ='<span id="trackError">'+accipiter.getLang_(messageLang,"no.track")+'</span>';
                        $("#selDataResult").append(html);
                        $("#selTrackResult").css("display","block");
                        return false;
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

        $(function() {
            var a = document.getElementById("flag");
            var b = a.options[a.selectedIndex].value;
            flagChange(b);
            initRes();
            getRange();
            getRangeinfo($("#atRangeId").val());
            showSelTrack();

            //提示
            $("#attention").on("click",function(){
                if($(".attention-info").hasClass("hidden")){
                    $(".attention-info").removeClass("hidden");
                    $(".attention-info").addClass("show");
                }else{
                    $(".attention-info").removeClass("show");
                    $(".attention-info").addClass("hidden");
                }
            });

            //保存轨迹
            $("#saveTrack").on("click",function(){
                var flag = $("#flag").val();
                var rangeId = $("#rangeId").val();
                if(null == rangeId || rangeId == ""){
                    return false;
				}
                var rleft = $('.ad_cp').css("left").split("px")[0];
                var rtop = $('.ad_cp').css("top").split("px")[0];
                var dlleft = $('#drag1').css("left").split("px")[0];
                var dlrtop = $('#drag1').css("top").split("px")[0];
                var left = parseInt(rleft)+parseInt(dlleft);
                var top = parseInt(rtop)+parseInt(dlrtop);
                if(!isNaN(left) && !isNaN(top)){
                    $("#trackError").remove();
                    var ss = "("+left+","+top+")";
                    var html = '<span class="save_box aui-titlespan" data-x="'+left+'" data-y="'+top+'">'+ss+'<i onclick="removespan_area(this)">×</i></span>';
                    $("#selDataResult").append(html);
                    $("#selTrackResult").css("display","block");
                }
            });

            //轨迹预览
            $("#prevTrack").on("click",function(){
                if ($('.save_box').length > 0) {
                    var mvd = $("#drag1");
                    var first = $(mvd)[0];
                    var fx = parseInt($(first).data("x"))-parseInt(rleft);
                    var fy = parseInt($(first).data("y"))-parseInt(rtop);
                    $(mvd).css({"left":fx,"top":fy});//设置初始位置

                    var rleft = $('.ad_cp').css("left").split("px")[0];
                    var rtop = $('.ad_cp').css("top").split("px")[0];

                    $('.save_box').each(function () {
                        var x = parseInt($(this).data("x"))-parseInt(rleft);
                        var y = parseInt($(this).data("y"))-parseInt(rtop);
                        mvd.animate({left: x,top:y}, 1000);
                    });
                }
            });

            $("#typeId").change(function(){
                getRange();
                clearTrackData();
            })
            $("#flag").change(function(){
                getRange();
                $("#rangeId").select2();
                var vle = $(this).val();
                flagChange(vle);
                initRes();
                clearTrackData();
            });
            
            $("#bgWidth").change(function () {
                var flag = $("#flag").val();
                if(flag == null || flag == ""){
                    return false;
                }
				var val = $(this).val();
                var maxWidth = 360;
                if(flag==1){
                    maxWidth = 640;
                }
                var maxw = Math.min(maxWidth,val);
                $("#drag1").css("width",maxw);
                $("#bgWidth").val(maxw);
            });

            $("#bgHeight").change(function () {
                var flag = $("#flag").val();
                if(flag == null || flag == ""){
                    return false;
                }
                var val = $(this).val();
                var maxHeight = 288;
                if(b==1){
                    maxHeight = 360;
                }
                var maxh = Math.min(maxHeight,val);
                $("#drag1").css("height",maxh);
                $("#bgHeight").val(maxh);
            });

            $("#rangeId").change(function(){
                var vle = $(this).val();
                if(vle != null && vle !=""){
                    getRangeinfo(vle);
                    var a = document.getElementById("flag");
                    var b = a.options[a.selectedIndex].value;
                    flagChange(b);
                }else{

				}
                clearTrackData();
            });

            //缩放事件
            function resizable(b,w,h){
                var maxWidth = 360;
                var maxHeight = 288;
                if(b==1){
                    maxWidth = 640;
                    maxHeight = 360;
                }
                var maxw = Math.min(maxWidth,w);
                var maxh = Math.min(maxHeight,h);
                $("#drag1").resizable({
                    maxWidth: maxw,
                    maxHeight: maxh,
                    minHeight: 50,
                    minWidth: 50,
                    resize: function( event, ui ) {
                        $("#bgWidth").val(ui.size.width);
						$("#bgHeight").val(ui.size.height);
                    }
                });
            }

            //拖动事件
            function dragTrack(width,height){
                $('#drag1').each(function(){
                    $(this).dragging({
                        move : "both",
                        randomPosition : false,
                        rangeX : width,
                        rangeY : height,
                        hander: '.hander'
                    });
                });
            }

            //区分高清标清
            function flagChange(vle){
                var host = accipiter.getRootPath();
                if(vle == 0){
                    $(".ad_Preview").css("height","720px");
                    $(".ad_Preview .tv_icon").css({"width":"800px","height":"656px","background":"url("+host+"/static/images/icon/tv-icon.jpg)"});
                    $(".ad_Preview .Preview_content").css({"width":"726px","height":"582px"});
                }else if(vle ==1){
                    $(".ad_Preview").css("height","870px");
                    $(".ad_Preview .tv_icon").css({"width":"1360px","height":"800px","background":"url("+host+"/static/images/icon/tv-icon2.jpg)"});
                    $(".ad_Preview .Preview_content").css({"width":"1286px","height":"726px"});
                }
            }

            //获取广告坐标范围
            function getRange() {
                var adTypeId = $("#typeId").val();
                var flag = $("#flag").val();
                if(adTypeId == null || adTypeId == ""){
                    return false;
                }
                if(flag == null || flag == ""){
                    return false;
                }
                var data1 = {"adTypeId":adTypeId,"flag":flag};
                var PostData = JSON.stringify(data1);
                $.ajax({
                    url:accipiter.getRootPath()+"/adv/combo/find_range_by_adTypeId",
                    async: false,
                    type:"POST",
                    data:PostData,
                    contentType: "application/json; charset=utf-8",
                    dataType : "json",
                    success:function(data){
                        var atRangeId = $("#atRangeId").val();
                        $("#rangeId").html("");
                        $("#rangeInfo").html("");
                        var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        var sx,sy,width,height =0;
                        if(data != null) {
                            $.each(data,function(commentIndex,comment){
                                if(atRangeId == comment.id){
                                    html+='<option selected="selected" value='+comment.id+'>'+comment.range+'</option>';
                                }else{
                                    html+='<option value='+comment.id+'>'+comment.range+'</option>';
                                }
                                sx = comment.beginX;
                                sy = comment.beginY;
                                width = parseInt(comment.endX) - parseInt(comment.beginX);
                                height = parseInt(comment.endY) - parseInt(comment.beginY);
                                var rp = '<p id="rp'+comment.id+'" sx='+sx+' sy='+sy+' width='+width+' height='+height+'></p>';
                                $("#rangeInfo").append(rp);
                            });
                            $("#rangeId").append(html);
                            $("#rangeId").select2();
                        }
                    }
                });
            }

            //可视化背景拖动  缩放操作
            function  getRangeinfo(rid) {
                if(null == rid || rid == ""){
                    return false;
                }
                var rmd = $("#rp"+rid);
                var rgLeft = rmd.attr("sx")+"px";
                var rgTop = rmd.attr("sy")+"px";
                var width = rmd.attr("width")+"px";
                var height = rmd.attr("height")+"px";
                var flag = $("#flag").val();
                if(flag == 0){
                    $(".ad_cp").css("max-width","720px");
                    $(".ad_cp").css("max-height","576px");
                }else {
                    $(".ad_cp").css("max-width","1280px");
                    $(".ad_cp").css("max-height","720px");
                }
                $('.ad_cp').css({"left":rgLeft,"top":rgTop,"width":width,"height":height});
                $('.ad_cp').css("display","block");

                resizable(flag,rmd.attr("width"),rmd.attr("height"));
                /*dragTrack(width,height);*/
                $("#drag1").draggable({ containment: ".ad_cp" });
                $("#drag1").removeClass("ui-draggable");
/*
                $("#resizable2").resizable().draggable().removeClass("ui-draggable");
*/



            }

            //回显轨迹数据
            function  showSelTrack() {
                var coordinates = $("#coordinates").val();
                if(null != coordinates && coordinates != ""){
                    var html = "";
                    $("#selDataResult").html("");
                    $.each(coordinates.split("-"),function(commentIndex,comment){
                        var ss = "("+comment.split(",")[0]+","+comment.split(",")[1]+")";
                        html += '<span class="save_box aui-titlespan" data-x="'+comment.split(",")[0]+'" data-y="'+comment.split(",")[1]+'">'+ss+'<i onclick="removespan_area(this)">×</i></span>';
                    });
                    $("#selDataResult").append(html);
                    $("#selTrackResult").css("display","block");
                }

            }

            function initRes(){
				var id = $("#id").val();
				//坐标范围初始化
				var left = 0;
                var top = 0;
                var width = 300;
                var height = 300;

                //可拖动图片初始化
                var beginX = 0;
                var beginY = 0;
                var bgWidth = 150;
                var bgHeight = 150;

                if(null != id && id != ""){

                    left = $("#beginX").val();
                    top = $("#beginY").val();
                    width = parseInt($("#endX").val()) - parseInt($("#beginX").val());
                    height = parseInt($("#endY").val()) - parseInt($("#beginY").val());

                    var coordinates = $("#coordinates").val();
                    var ss = coordinates.split("-")[0].split(",");
                    beginX = ss[0];
                    beginY = ss[1];

                    bgWidth = $("#bgWidth").val();
                    bgHeight = $("#bgHeight").val();
                }
                $('.ad_cp').css({"left":left,"top":top,"width":width,"height":height});
                $("#drag1").css({"left":beginX,"top":beginY});
                $("#drag1").css("width",bgWidth);
                $("#drag1").css("height",bgHeight);
                $("#bgWidth").val(bgWidth);
                $("#bgHeight").val(bgHeight);
            }
            //清除轨迹数据
            function clearTrackData() {
                $("#selDataResult").html("");
            }
        });
		//删除轨迹功能
        function removespan_area(spanthis) {
            spanthis = spanthis.parentElement;
            $(spanthis).remove();
        }

	</script>
	<!-- 引用css -->
	<style type="text/css">
		#drag1 {
			border:0px;
			background-color: aliceblue;
			cursor: move;
		}

		#drag1 img {
			width:100%;
			height:100%;
		}
		span {
			font-size:100%;
		}

		i.hander {
			display:block;
			position: absolute;
			width:100%;
			height:100%;
			background:#ccc;
			filter:alpha(opacity=0);
			-moz-opacity:0;
			-khtml-opacity: 0;
			opacity: 0;
		}

		#attention{width:34px;height:34px;display:block;float:left;position: relative;left:10px;top:4px;background:url("${ctxStatic}/images/icon/attention.png") no-repeat;}
		#attention:hover{cursor: pointer;}
		.attention-info{padding: 10px 0;margin-bottom: 10px;border: 1px solid #FFCE42;border-radius: 4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;background-color: #DFF0D8;}
		.attention-info p{text-indent: 25px;margin-bottom:0;}
		.hidden{display:none;}
		.show{display:block;}
	</style>
</head>
<body>
<div class="top_position">
	<div class="top_position_lab"><spring:message code='combo.position' /></div>
	<ul>
		<li><spring:message code='adv.show' /></li>
		<li>></li>
		<li><spring:message code='adv.trackManage' /></li>
		<li>></li>
		<li>
			<shiro:hasPermission name="sys:track:edit"><a href="${ctx}/adv/track/form?id=${adTrack.id}">
				<c:choose><c:when test="${not empty adTrack.id}"><spring:message code='track.update' /></c:when>
					<c:otherwise><spring:message code='track.add' /></c:otherwise></c:choose></a></shiro:hasPermission>
		</li>
	</ul>
</div>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/adv/track"><spring:message code='track.list' /></a></li>
	<shiro:hasPermission name="sys:track:edit"><li  class="active"><a href="${ctx}/adv/track/form?id=${adTrack.id}">
		<c:choose><c:when test="${not empty adTrack.id}"><spring:message code='track.update' /></c:when>
			<c:otherwise><spring:message code='track.add' /></c:otherwise></c:choose></a></li></shiro:hasPermission>
</ul>
<form:form id="inputForm" modelAttribute="adTrack" action="${ctx}/adv/track/${empty adTrack.id ? 'save' : 'update' }" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="coordinates"/>
	<input id="atRangeId" type="hidden" value=${adTrack.range.id}>

	<input id="beginX" type="hidden" value=${adTrack.range.beginX}>
	<input id="beginY" type="hidden" value=${adTrack.range.beginY}>
	<input id="endX" type="hidden" value=${adTrack.range.endX}>
	<input id="endY" type="hidden" value=${adTrack.range.endY}>

	<tags:message content="${message}"/>

	<div class="control-group">
		<label class="control-label"><spring:message code='track.name' />:</label>
		<div class="controls">
			<form:input path="trackName" htmlEscape="false" maxlength="50" class="required"/>
		</div>
	</div>


	<div class="control-group">
		<label class="control-label"><spring:message code='track.type' />：</label>
		<div class="controls">
			<form:select path="type.id" id="typeId" class="required">
				<option value=""><spring:message code="userform.select"/></option>
				<form:options items="${fns:getAdTypeById('4')}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
			</form:select>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label"><spring:message code='track.resolution' />:</label>
		<div class="controls">
			<form:select path="flag" class="required">
				<option value=""> <spring:message code='userform.select' /></option>
				<form:options items="${fns:getDictList('adv_range_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
	</div>

	<div class="control-group" id="hdRange">
		<label class="control-label"><spring:message code='range' />:</label>
		<div class="controls">
			<form:select path="range.id" id="rangeId" class="required">
				<option value=""> <spring:message code='userform.select' /></option>
			</form:select>
			&nbsp;&nbsp;
			<input id="saveTrack" class="btn btn-primary" style="height: 34px" type="button" value="<spring:message code='track.save' />"/>
			&nbsp;&nbsp;
			<input id="prevTrack" class="btn btn-primary" style="height: 34px" type="button" value="<spring:message code='preview' />"/>
		</div>
	</div>

	<div class="control-group" id="selTrackResult" style="display:none;height: auto;">
		<label class="control-label"><spring:message code='track.coordinates' />:</label>
		<div class="controls">
			<div id="selDataResult" class="data-result" style="padding: 0"></div>
		</div>
	</div>

	<div class="attention-info hidden"><p><spring:message code="track_info"/></p></div>

	<div class="control-group">
		<label class="control-label"><spring:message code='control.width' />:</label>
		<div class="controls">
			<form:input path="bgWidth" htmlEscape="false" maxlength="50" type="number" class="required" style="float:left;"/>
			<a id="attention"></a>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label"><spring:message code='control.height' />:</label>
		<div class="controls">
			<form:input path="bgHeight" htmlEscape="false" maxlength="50" type="number" class="required"/>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label"><spring:message code='adv.time' />:</label>
		<div class="controls">

			<form:input path="showTime" htmlEscape="false" maxlength="50" type="number" class="required"/>
		</div>
	</div>

	<div class="data-result"></div>

	<div id="rangeInfo" style="display: none"></div>
	<div class="control-group ad_Preview">
		<div class="tv_icon" style="left:60px;top:30px;">
			<div class="Preview_content">
				<div class="ad_cp">
					<div id="drag1" class="ui-widget-content" style="width:150px;height:150px;"><i class='hander'></i>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="form-actions">
		<shiro:hasPermission name="sys:track:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>