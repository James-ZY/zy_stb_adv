$(function() {
    var host = accipiter.getRootPath();
    firstLoad();
    /*firstLoadPosition();*/

    var control_show;
    var selectedlimit=0;//用于限定选择文件的数目
    var selectedHdlimit=3;//用于限定选择高清文件的数目
    var selectedSdlimit=3;//用于限定选择标清文件的数目
    var adId="";//用于判断当前进来是广告添加还是修改
    var adClassId;//广告类型id
    var show_dom;//显示节点;
    var isPosition="";//用于判断是否进行坐标查询
    jQuery.validator.addMethod("domain", function (value, element) {
        var reg =/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
        return this.optional(element) || (reg.test(value));
    }, accipiter.getLang_(jsLang,"webName"));
    /*-------上传图片与视频的切换--------*/
    var change_id=parseInt($("#resourceType").attr("value"));
    if(change_id==0){
        $("#adv_image").css("display","block");
        $("#adv_vedio").css("display","none");
        $('.playTime').css("display", "none");
        show_dom=$("#upload_adv_image");
        control_show=0;
    }
    if(change_id==1){
        $("#adv_image").css("display","none");
        $("#adv_vedio").css("display","block");
        $('.playTime').css("display", "block");
        show_dom=$("#upload_adv_vedio");
        control_show=1;
    }
    //根据套餐判断选择文件数目
    function setlimit(id){
        var controlId=parseInt(id);
        if(controlId==1){
            var data = {typeId:controlId};
            var postData = JSON.stringify(data);
            $.ajax({
                url :host + "/sys/fileParam/getParams",
                type :"post",
                async:false,
                data :postData,
                contentType : "application/json; charset=utf-8",
                dataType : "json",
                success:function(data){
                    $.each(data,function(commentIndex,comment){
                        if(comment.flag ==0){
                            selectedSdlimit = comment.amount;
                        }else{
                            selectedHdlimit = comment.amount;
                        }
                    });
                }
            });
            selectedlimit=3;
        }else if(controlId==5){
            selectedlimit=100;
        }else if(controlId==8){
            selectedlimit=1;
        }else{
            selectedlimit=1;
        }
    }

    //根据广告类型获取开机画面广告显示时间
    function setBtP(id){
        var controlId=parseInt(id);
        var data = {typeId:controlId};
        var postData = JSON.stringify(data);
        $.ajax({
            url :host + "/sys/param/getParamByType",
            type :"post",
            async:false,
            data :postData,
            contentType : "application/json; charset=utf-8",
            dataType : "json",
            success:function(data){
                if(null != data){
                    if(controlId == 1 || controlId == 2){
                        $("#bpTT").val(data.bpTT);
                        $("#bpTS").val(data.bpTS);
                    }
                    if(controlId == 2 || controlId == 4 || controlId == 5){
                        $("#psTs").val(data.psTs);
                    }
                }
            }
        });
    }
    function firstLoad() {
        loginOnadvertiser();//当登录用户是广告商的时候，默认加载广告套餐
        var a = document.getElementById("adv_isFlag");
        var b = a.options[a.selectedIndex].value;
        if (b == 0) {
            document.getElementById("add_text").style.display = "none";
        } else{
            document.getElementById("add_text").style.display = "block";
            if(b==2){
                $(".linkText").find("a").css("display","inline-block");
                $(".linkText").find("a").attr("href",$("#add_text_area").val());
                $("#add_text_area").addClass("domain");
            }else{
                $("#add_text_area").removeClass("domain");
                $(".linkText").find("a").css("display","none");
            }
        }
    }
    function checkAddText() {
        var a = document.getElementById("adv_isFlag");
        var b = a.options[a.selectedIndex].value;
        if (b == 0) {
            document.getElementById("add_text").style.display = "none";
            document.getElementById("v_addText").innerText = "";
            $("#add_text_area").val('');
        } else {
            document.getElementById("add_text").style.display = "block";

            if(b==2){
                $("#add_text_area").addClass("domain");
            }else{
                $("#add_text_area").removeClass("domain");
                $(".linkText").find("a").css("display","none");
            }
            $("#add_text_area").val('');
            /*ajaxConnect(b);*/
        }
    }
    /*监听文本框正取信息*/
    $("#add_text_area").off("keyup").on("keyup",function(){
        if($(this).hasClass("domain")){
            if($(this).valid()){
                var href=$(this).val();
                $(".linkText").find("a").css("display","inline-block");
                $(".linkText").find("a").attr("href",href);
            }else{
                $(".linkText").find("a").removeAttr("href");
                $(".linkText").find("a").css("display","none");
            }
        }
    });
    function addTextFocus() {
        var a = document.getElementById("adv_isFlag");
        var b = a.options[a.selectedIndex].value;
        /*ajaxConnect(b);*/
    }
    function ajaxConnect(b) {
        document.getElementById("v_addText").innerText = "";
        /*if (b == 2) {
         var addText = eval(document.getElementById("add_text_area")).value;
         if (null != addText && "" != addText) {
         var o = {"addText" : addText};
         var data = JSON.stringify(o);

         $.ajax({
         url : host + "/adv/adelement/chekAddress",
         async : false,
         type : "POST",
         data : data,
         contentType : "application/json; charset=utf-8",
         dataType : "text",
         success : function(data) {
         if (data == "false") {
         document.getElementById("v_addText").innerText =  accipiter.getLang_(messageLang,"check.addtext.exception");
         $("#btnSubmit").attr("disabled",true);
         } else {
         document.getElementById("v_addText").innerText = "";
         $("#btnSubmit").attr("disabled",false);
         }
         },
         error : function(err) {

         }
         });
         }
         }else{*/
        $("#btnSubmit").attr("disabled",false);
        //}

    }
    function firstLoadPosition() {
        var a = document.getElementById("position_id");
        var b = a.options[a.selectedIndex].value;
        if (null != b && "" != b) {
            $('#adv_position').css("display", "block");
        }
    }
    /********判断时段类广告唯一性*********/
    function judage_advOne(){
        var advId="";
        var childAdType="";
        var a=document.getElementById("advertiser_id");
        var child=document.getElementById("adcombo_chlidType");
        if(a==undefined){
            advId="";
        }else{
            advId=a.options[a.selectedIndex].value;
        }
        if(child==undefined){
            childAdType="";
        }else{
            if(child.options[child.selectedIndex] != undefined){
                childAdType=child.options[child.selectedIndex].value;
            }

        }
        var startDate=$("#startDate").val();
        var endDate=$("#endDate").val();
        var id=$("#id").val();
        var data={"id":id,"startDate":startDate,"endDate":endDate,"advId":advId,"childAdType":childAdType};
        var postData=JSON.stringify(data);
        if(endDate==""||startDate==""){
            return;
        }else{
            $.ajax({
                url:host+"/adv/adelement/checkAdvIsRepeat",
                async: false,
                type:"POST",
                data:postData,
                contentType: "application/json; charset=utf-8",
                dataType : "json",
                success:function(data){
                    if(data==false){
                        document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.findone");
                        $("#btnSubmit").attr({"disabled":true});
                    }else{
                        $("#btnSubmit").attr({"disabled":false});
                        document.getElementById("enddate_span").innerText ="";
                    }

                }
            })
        }
    }

    function dateValid(status) {

        var start = document.getElementById("startDate").value.replace(/-/g,"/");
        var end = document.getElementById("endDate").value.replace(/-/g, "/");
        if (null == start || "" == start) {
            document.getElementById("startdate_span").innerText = accipiter.getLang_(messageLang,"adv.start.date");
            return;
        }
        document.getElementById("startdate_span").innerText = "";
        if (null == end || "" == end) {
            document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.end.date");
            return;
        }
        document.getElementById("enddate_span").innerText = "";
        var beginDate = new Date(start);
        var endDate = new Date(end);
        if (beginDate > endDate) {
            if (status == 0) {
                document.getElementById("startdate_span").innerText = accipiter.getLang_(messageLang,"end.compare.start");
            } else {
                document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"end.compare.start");
            }
            return;
        }else{
            judage_advOne();
        }
    }

    /**
     * 第一次加载的时候，新增广告时间不可读，修改广告时间可读
     */
    function setDateReadOnly() {
        var ad_id = $("#id").val();
        if (null == ad_id || "" == ad_id) {
            if ($('startDate').attr("readonly") != "readonly") {
                $('startDate').attr("readonly", "readonly");
            }
            if ($('endDate').attr("readonly") == false) {
                $('endDate').attr("readonly", true);
            }
        } else {
            if ($('startDate').attr("readonly") == true) {
                $('startDate').attr("readonly", false);
            }
            if ($('endDate').attr("readonly") == true) {
                $('endDate').attr("readonly", false);
            }
        }
    }
    /****根据广告商选择相应数据--->对应的广告商数据****/
    function onchangeByadvertiser(){
        var a=document.getElementById("advertiser_id");
        var b=a.options[a.selectedIndex].value;
        var a_id=document.getElementById("id").value;;
        var status=document.getElementById("status").value;;
        var o={"advId":b,"id":a_id,"status":status};
        var PostData = JSON.stringify(o);
        if(b!=""){
            $.ajax({
                url:host+"/adv/combo/find_combo_by_advId",
                async: false,
                type:"POST",
                data:PostData,
                contentType: "application/json; charset=utf-8",
                dataType : "json",
                success:function(data){
                    var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                    $.each(data,function(commentIndex,comment){
                        html+='<option value='+comment.id+'>'+comment.comboName+'</option>';
                    });
                    $('#adcombo_id_type').css("display","none");
                    $('#adcombo_id_chlidType').css("display","none");
                    $('#adv_position').css("display","none");
                    $('.list_content').html('');
                    $("#adcombo_id").html("");
                    $("#adcombo_id").append(html);
                    $("#adcombo_id").select2();
                }
            })
        }
    }
    /****根据广告商选择相应数据****/
    function loginOnadvertiser(){
        var a=document.getElementById("advertiser_id");
        if(a == undefined || a== null){
            var a_id=document.getElementById("id").value;
            var status=document.getElementById("status").value;;
            var o={"advId":"","id":a_id,"status":status};
            var PostData = JSON.stringify(o);
            $.ajax({
                url:host+"/adv/combo/find_combo_by_advId",
                async: false,
                type:"POST",
                data:PostData,
                contentType: "application/json; charset=utf-8",
                dataType : "json",
                success:function(data){
                    var id=$("#comboId").val();
                    var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                    $.each(data,function(commentIndex,comment){
                        if(id==comment.id){
                            html+='<option selected="selected" value='+comment.id+'>'+comment.comboName+'</option>';
                        }else{
                            html+='<option value='+comment.id+'>'+comment.comboName+'</option>';
                        }
                    });
                    $('#adcombo_id_type').css("display","none");
                    $('#adcombo_id_chlidType').css("display","none");
                    $('#adv_position').css("display","none");
                    $('.list_content').html('');
                    $("#adcombo_id").html("");
                    $("#adcombo_id").append(html);
                    $("#adcombo_id").select2();
                }
            })

        }
    }
    //修改时根据广告商获取相应广告套餐数据
    function getDataByAmend(){
        if($("#advertiser_id").find("option").length!==0){
            var a=document.getElementById("advertiser_id");
            var b=a.options[a.selectedIndex].value;
            var a_id=document.getElementById("id").value;
            var status=document.getElementById("status").value;
            var o={"advId":b,"id":a_id,"status":status};

            var PostData = JSON.stringify(o);

            if(b!=""){
                $.ajax({
                    url:host+"/adv/combo/find_combo_by_advId",
                    async: false,
                    type:"POST",
                    data:PostData,
                    contentType: "application/json; charset=utf-8",
                    dataType : "json",
                    success:function(data){
                        var id=$("#comboId").val();
                        var html='<option value="">'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        $.each(data,function(commentIndex,comment){
                            if(id==comment.id){
                                html+='<option selected="selected" value='+comment.id+'>'+comment.comboName+'</option>';
                            }else{
                                html+='<option value='+comment.id+'>'+comment.comboName+'</option>';
                            }
                        });
                        $('#adcombo_id_type').css("display","none");
                        $('#adcombo_id_chlidType').css("display","none");
                        $('#adv_position').css("display","none");
                        $('.list_content').html('');
                        $("#adcombo_id").html("");
                        $("#adcombo_id").append(html);
                        $("#adcombo_id").select2();
                    }
                })
            }
        }
    }
    //根据广告套餐获取相应资源文件数据
    /****相应广告位置数据****/
    function getadvPosition(data){
        var o=data;
        var postData = JSON.stringify(data);
        var positionId=$("#positionId").text();
        var hdPositionId=$("#hdPositionId").text();
        $.ajax({
            url:host+"/adv/adelement/getPositionByTypeId ",
            async: false,
            type:"POST",
            data:postData,
            contentType: "application/json; charset=utf-8",
            dataType : "json",
            success:function(data){
                var friends = $("#position_id");
                var hdselect =  $("#hd_position_id");
                if(data != null ){
                    if(o.resolution=="0"){
                        var sd = data.position;
                        var html='';
                        if(positionId==""){
                            html+='<option :selected value>'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        }else{
                            html+='<option value>'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        }
                        $.each(sd,function(commentIndex,comment){
                            var x  = comment["beginPointX"];
                            var y  = comment["beginPointY"];
                            var x1 = comment["endPointX"];
                            var y1 = comment["endPointY"];
                            var id =comment["id"];
                            var point = "";
                            if(null != x1 &&null != y1){
                                point= "start:"+"("+x+","+y+")"+"  end:"+"("+x1+","+y1+")"
                            }else{
                                point = "("+x+","+y+")";
                            }
                            if(id==positionId){
                                html+='<option selected="selected" value='+id+'>'+point+'</option>';
                            }else{
                                html+='<option value='+id+'>'+point+'</option>';
                            }
                        })
                        $("#position_id").html("");
                        $("#position_id").append(html);
                        $("#position_id").select2();
                        $('#adv_position').css("display","block");
                    }
                    if(o.resolution=="1"){
                        var hd = data.position;
                        var html='';
                        if(hdPositionId==""){
                            html+='<option :selected value>'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        }else{
                            html+='<option value>'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                        }
                        $.each(hd,function(commentIndex,comment){
                            var x  = comment["beginPointX"];
                            var y  = comment["beginPointY"];
                            var x1 = comment["endPointX"];
                            var y1 = comment["endPointY"];
                            var id =comment["id"];
                            var point = "";
                            if(null != x1 &&null != y1){
                                point= "start:"+"("+x+","+y+")"+"  end:"+"("+x1+","+y1+")"
                            }else{
                                point = "("+x+","+y+")";
                            }
                            if(id==hdPositionId){
                                html+='<option selected="selected" value='+id+'>'+point+'</option>';
                            }else{
                                html+='<option value='+id+'>'+point+'</option>';
                            }
                        })
                        hdselect.html("");
                        hdselect.append(html);
                        $('#adv_position').css("display","block");
                        hdselect.select2();
                    }
                }else{
                    var html='<option :selected value>'+accipiter.getLang_(messageLang,"userform.select")+'</option>';
                    if(o.resolution=="0"){
                        $("#position_id").html("");
                        $("#position_id").append(html);
                        $("#position_id").select2();
                    }
                    if(o.resolution=="1"){
                        hdselect.html("");
                        hdselect.append(html);
                        hdselect.select2();
                    }
                    $('#adv_position').css("display","block");
                    /*$('#adv_position').css("display","none");*/
                }

            }
        });
    }
    //获取子广告类型
    function getchlidType(e,child){
        $.ajax({
            url:host+"/adv/quickAdelement/getAdChlidType",
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
    //获取相应的广告资源数据
    function onchangeByCombo(){
        InitializeResolution();
        $(".resourcesHd").css("display","none");
        $('.advHd_list_Content').html('');
        showPointAndResources("advStandard","1");
    }

    /*//获取相应的广告资源数据
     function onchangeByCombo(){
     var comboId=$("#comboId").val();
     var Adv="";
     var a=document.getElementById("adcombo_id");
     Adv=a.options[a.selectedIndex].value;
     var o ={"id":Adv};
     var data = JSON.stringify(o);
     var resolution="0";
     if(comboId!=Adv){
     $("#startDate").val("");
     $("#endDate").val("");
     }
     getSellDate(getSellDateSoure());
     if(Adv!=""){
     getchlidType(data);
     InitializeResolution();
     $(".resourcesHd").css("display","none");
     $('.advHd_list_Content').html('');
     showPointAndResources("advStandard","1");
     }else{
     $('#adcombo_id_type').css("display","none");
     $('#adcombo_id_chlidType').css("display","none");
     $('.list_content').html('');
     }


     }*/
    /*********************获取图片或视频资源*************************/
    /*****修改时显示选中数据*****/
    function alertShowSelectedData(data,node){
        $.each(data,function(commentIndex,comment){
            var selectedId=comment;
            node.find('img[id='+comment+']').parent().addClass("selected");
            node.find('img[id='+comment+']').parent().attr("name","1");
            node.find('img[id='+comment+']').parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
        });
    }


    function uploadLayer(adType,advertiser,flag) {
        layer.open({
            type: 2,
            title: '',
            area: ['900px', '600px'], //宽高
            btn: [],
            shadeClose:true,
            content: host+'/adv/control/formLayer?adTypeId='+adType+"&advertiserId="+advertiser+"&flag="+flag,
            success: function(layero, index){
            },
            end: function () {
                //关闭刷新素材数据
                getSelectData(advertiser,adType,'',flag);
                if(adType == 5){
                    var cs = "sd";
                    if(flag == 1){
                        cs = "hd";
                    }
                    checkSet(cs);
                }
            }
        });
    }

    /*****资源获取*****/
    function getSelectData(id,ClassId,comboId,resolution){
        var isNotAdv=$("#isNotAdv").text();
        var advId=$("#advId").text();
        var advertiser_id="";
        var sdShowParam = $("#sdShowParam").val();
        var hdShowParam = $("#hdShowParam").val();
        if(isNotAdv=="true"){
            advertiser_id=$("#advertiser_id").find("option:selected").val();
        }else{
            if(advId!=""){
                advertiser_id=advId;
            }
        }
        var rollFlag = "1";
        if(ClassId!=""&&advertiser_id!=""){
            if(ClassId == "5"){
                if(resolution == "0"){
                    var sdFx = $("#sdFx").val();
                    if(sdFx == "2" || sdFx =="3"){
                        rollFlag = "2";
                    }
                }else{
                    var hdFx = $("#hdFx").val();
                    if(hdFx == "2" || hdFx =="3"){
                        rollFlag = "2";
                    }
                }
            }
            var version;
            if(resolution == "0"){
                version = $("#sdversion").val();
            }else{
                version = $("#hdversion").val();
            }
            if(null != version){
                version = version.join(",");
            }

            if(ClassId == "1" || ClassId == "2" || ClassId == "4" || ClassId == "5"){
                setBtP(ClassId);
            }
            var bpTS = $("#bpTS").val();
            var min = $("#psTs").val();
            var postdata={"id":ClassId,"advId":advertiser_id,"resolution":resolution,"sdShowParam":sdShowParam,"hdShowParam":hdShowParam,"rollFlag":rollFlag,"version":version};
            var data = JSON.stringify(postdata);
            $.ajax({
                url:host+"/adv/adelement/getResoure",
                async: false,
                type:"POST",
                data:data,
                contentType: "application/json; charset=utf-8",
                dataType : "json",
                success:function(data){
                    var html='';
                    var appendHtml='';
                    if(resolution=="0"){
                        $('.advStandard_list_Content').html('');
                    }else{
                        $('.advHd_list_Content').html('');
                    }
                    if(ClassId=="1" || ClassId=="2" || ClassId=="4" || ClassId=="5"){

                        if(resolution=="0"){
                            html+='<div class="jq22">';
                        }else{
                            html+='<div class="jq23">';
                        }
                    }
                    if(data!=null){
                        $.each(data,function(commentIndex,comment){
                            if(control_show==0){
                                imgpath=comment["path"];
                            }else{
                                imgpath=comment["vedioImagePath"];
                            }
                            var itemId=resolution+"_"+comment["id"];
                            var size = comment["size"];
                            if(ClassId=="1" || ClassId=="2" || ClassId=="4" || ClassId=="5"){
                                if(resolution=="0"){
                                    html+='<div class="jqitem2 item1">';
                                }else{
                                    html+='<div class="jqitem3 item1">';
                                }
                            }
                            if(ClassId == "5" && comment["rollText"] != ""){
                                html+='<li class="item" name="0" title="'+comment["rollText"]+'">';
                            }else{
                                html+='<li class="item" name="0">';
                            }

                            html+='<img class="action_img" id='+comment["id"]+' src='+imgpath+'>' +
                                '<div class="item_bottom">' +
                                '<input class="action_button" type="button" id='+itemId+' for='+size+'>';
                            if(ClassId=="1"){
                                if((comment["time"] !=null && comment["time"]!= "") || comment["time"]==0){
                                    html+='<input type="number" class="pcshowTime" min="1" style="display:block;" max="'+bpTS+'" value="'+comment["time"]+'"></div></li>';
                                }else{
                                    html+='<input type="number" class="pcshowTime" style="display:none;" min="1" max="'+bpTS+'" value="'+bpTS+'"></div></li>';
                                }
                            }else if(ClassId=="2" || ClassId=="4" || ClassId=="5"){
                                if((comment["time"] !=null && comment["time"]!= "") || comment["time"]==0){
                                    html+='<input type="number" class="pcshowTime" style="display:block;" min="0"  max="600" value="'+comment["time"]+'"></div></li>';
                                }else{
                                    html+='<input type="number" class="pcshowTime" style="display:none;" min="0" max="600" value="0"></div></li>';
                                }
                            }else{
                                html+='<label for='+itemId+'></label></div></li>';
                            }
                            if(ClassId=="1" || ClassId=="2" || ClassId=="4" || ClassId=="5"){
                                html+='</div>';
                            }
                        });
                    }
                    if(ClassId=="1" || ClassId=="2" || ClassId=="4" || ClassId=="5"){

                        if(resolution=="0"){
                            html+='<div class="jqitem2 item1"><li class="item add_item"  name="0"></li></div>';
                        }else{
                            html+='<div class="jqitem3 item1"><li class="item add_item"  name="0"></li></div>';
                        }
                        html+='</div>';
                    }else{
                        html+='<li class="item add_item"  name="0"></li>';
                    }

                    if(resolution=="0"){
                        appendHtml='<li class="title"><p>'+accipiter.getLang_(messageLang,"adv.standard")+'</p>';
                        if(ClassId =="5"){
                            appendHtml +='<label>'+accipiter.getLang("selectAll")+':</label><input id="sdSelAll" type="button" name="0" class="selAll"/>' +
                                '<label>'+accipiter.getLang_(messageLang,"batch.set")+':</label><input type="number" class="tiemSet" min="'+min+'"/>' +
                                '<label>'+accipiter.getLang_(messageLang,"selectd.time")+':</label><input id="sdSelTime" type="text" readonly="readonly"/>' +
                                '<label>'+accipiter.getLang_(messageLang,"remaining.time")+':</label><input id="sdRemTime" type="text" readonly="readonly"/>';
                        }
                        appendHtml +='</li>'+html;                        show_dom.find('.advStandard_list_Content').html('');
                        show_dom.find('.advStandard_list_Content').html(appendHtml);
                        if(ClassId=="1" || ClassId=="2" || ClassId=="4" || ClassId=="5"){
                            $(".list_content .item").css("margin","0px");
                            $(function(){
                                $('.jq22').dad({
                                    draggable: 'img'
                                });
                            });
                        }else{
                            $(".list_content .item").css("margin","10px");
                        }
                    }else{
                        appendHtml='<li class="title"><p>'+accipiter.getLang_(messageLang,"adv.hd")+'</p>';
                        if(ClassId =="5") {
                            appendHtml += '<label>'+accipiter.getLang("selectAll")+':</label><input id="hdSelAll" type="button" name="0" class="selAll"/>' +
                                '<label>' + accipiter.getLang_(messageLang, "batch.set") + ':</label><input type="number" class="tiemSet" min="0"/>' +
                                '<label>' + accipiter.getLang_(messageLang, "selectd.time") + ':</label><input id="hdSelTime" type="text" readonly="readonly"/>' +
                                '<label>' + accipiter.getLang_(messageLang, "remaining.time") + ':</label><input id="hdRemTime" type="text" readonly="readonly"/>';
                        }
                        appendHtml +='</li>'+html;                        show_dom.find('.advHd_list_Content').html('');
                        show_dom.find('.advHd_list_Content').html(appendHtml);
                        if(ClassId=="1" || ClassId=="2" || ClassId=="4" || ClassId=="5"){
                            $(".list_content .item").css("margin","0px");
                            $(function(){
                                $('.jq23').dad({
                                    draggable: 'img'
                                });
                            });
                        }else{
                            $(".list_content .item").css("margin","10px");
                        }
                    }
                    //绑定上传素材弹窗
                    $(".add_item").off("click").on("click",function(){
                        getSelectedResources();
                        var node = $(this).parent().parent().parent();
                        if(node.attr("class").indexOf("advStandard_list_Content")==-1){
                            resolution = "1";
                        }else{
                            resolution = "0";
                        }
                        var advId = $("#advertiser_id").val();
                        uploadLayer(ClassId,advId,resolution);
                    });
                    $(".pcshowTime ").off('keyup').on('keyup', function (event) {
                        var adTypeId = $("#adTypeId").val();
                        if(adTypeId == 5){
                            var node = $(this).parent().parent().parent().parent().parent();
                            if(node.attr("class").indexOf("advStandard_list_Content")==-1){
                                checkSet("hd");
                            }else{
                                checkSet("sd");
                            }
                        }
                    });
                    $(".tiemSet").off('keyup').on('keyup', function (event) {
                        var min = $("#psTs").val();
                        if($(this).val()<min){
                            return false;
                        }
                        plSet($(this));
                        var node = $(this).parent().parent();
                        if(node.attr("class").indexOf("advStandard_list_Content")==-1){
                            checkSet("hd");
                        }else{
                            checkSet("sd");
                        }
                    });

                    $(".tiemSet").change(function(){
                        var min = $("#psTs").val();
                        if($(this).val()<min){
                            return false;
                        }
                        plSet($(this));
                        var node = $(this).parent().parent();
                        if(node.attr("class").indexOf("advStandard_list_Content")==-1){
                            checkSet("hd");
                        }else{
                            checkSet("sd");
                        }
                    });

                    $("#pictureTimes,#pictureInterval").off('keyup').on('keyup', function (event) {
                        var adTypeId = $("#adTypeId").val();
                        if(adTypeId == 5){
                            $("#sdSelAll").attr("name","0");
                            selAllFun($("#sdSelAll"));
                            $("#hdSelAll").attr("name","0");
                            selAllFun($("#hdSelAll"));
                        }
                    });

                    $("#pictureTimes,#pictureInterval").change(function(){
                        var adTypeId = $("#adTypeId").val();
                        if(adTypeId == 5){
                            $("#sdSelAll").attr("name","0");
                            selAllFun($("#sdSelAll"));
                            $("#hdSelAll").attr("name","0");
                            selAllFun($("#hdSelAll"));
                        }
                    });
                },
                error: function (err) {
                }
            });
        }
        if(ClassId==adClassId){
            var sdpath=$("#path").attr("value");
            var hdPath=$("#hdPath").attr("value");
            var sdLen=sdpath.split(",").length;
            var hdLen=hdPath.split(",").length;
            var sdSelectedData=[];
            var hdSelectedData=[];
            if(sdpath!=""){
                for(var i=0;i<sdLen;i++){
                    sdSelectedData.push(sdpath.split(",")[i]);
                }
                alertShowSelectedData(sdSelectedData,show_dom.find('.advStandard_list_Content'));
            }
            if(hdPath!=""){
                for(var i=0;i<hdLen;i++){
                    hdSelectedData.push(hdPath.split(",")[i]);
                }
                alertShowSelectedData(hdSelectedData,show_dom.find('.advHd_list_Content'));
            }
        }
        setlimit(ClassId);
    }
    //获取销售时间段
    function getSellDateSoure(){
        var dataSoure=[];
        var o = {};
        var a=document.getElementById("adcombo_id");
        var b=a.options[a.selectedIndex].value;
        var c=$("#advertiser_id").find("option:selected").attr("value");
        if((null != b || ""!= b)&&(null != c || ""!= c)){
            var o ={"id":b,"advId":c};
            var data = JSON.stringify(o);
            $.ajax({
                url:host+"/adv/adelement/getSellDate",
                async: false,
                type:"POST",
                data:data,
                contentType: "application/json; charset=utf-8",
                dataType : "json",
                success:function(data){
                    if(null != data){
                        dataSoure=data;
                    }else{
                        dataSoure=[];
                        if($('startDate').attr("readonly")==false){
                            $('startDate').attr("readonly",true) ;
                        }
                    }

                },
                error: function (err) {
                    dataSoure=[];
                    if($('startDate').attr("readonly")==false){
                        $('startDate').attr("readonly",true) ;
                    }
                }
            });
        }else{
            dataSoure=[];
        }
        return dataSoure;
    }
    function getSellDate(data){
        var html="";
        if(data!=null){
            $.each(data,function(commentIndex,comment){
                html+='<option>'+comment.startDate+'~'+comment.endDate+'</option>';
            });
            $("#adCombo_SellData").html("");
            $("#adCombo_SellData").html(html);
            $("#adCombo_SellData").select2();
        }
    }
    function echoSellDate(data){
        var html="";
        var sellDate=$.parseJSON($("#sellTime").text());
        var sellTime=sellDate.startDate+'~'+sellDate.endDate;
        var control=0;//判断销售时间是否过期，如果过期接口就没有这条销售时间，自动添加上去
        if(data!=null){
            $.each(data,function(commentIndex,comment){
                if(comment.startDate==sellDate.startDate && comment.endDate==sellDate.endDate){
                    control=1;
                    html+='<option selected="selected">'+sellTime+'</option>';
                }else{
                    html+='<option>'+comment.startDate+'~'+comment.endDate+'</option>';
                }
            });
            if(control==0){
                html='<option selected="selected">'+sellTime+'</option>'+html;
            }
            $("#adCombo_SellData").html("");
            $("#adCombo_SellData").html(html);
            $("#adCombo_SellData").select2();
        }
    }
    /*****************判断套餐修改时状态***************/
    function judage(){
        var status=parseInt($("#judgeStatus").text());
        if(status==1 || status==3 || status==4){
            $(".control-group").find("input").attr("disabled",true);
            $(".control-group").find("select").attr("disabled",true);
            $(".control-group").find("textarea").attr("disabled",true);
        }
    }
    judage();
    /******************高标清资源切换和坐标展示**********/
    function showPointAndResources(node,show){
        var resolution="0";
        var comboId = $("#comboId").val();
        /***控制索材显示***/
        if(node=="advHd"){
            resolution="1";
            if(show=="1"){
                $(".advHdContent").css("display","block");
                $(".resourcesHd").css("display","block");
                getSelectData(adId,adClassId,comboId,resolution);
            }else{
                $(".advHdContent").css("display","none");
                $(".resourcesHd").css("display","none");
                $('.advHd_list_Content').html('');
            }
        }else{
            resolution="0";
            if(show=="1"){
                $(".resourcesSd").css("display","block");
                $(".advStandardContent").css("display","block");
                getSelectData(adId,adClassId,comboId,resolution);
            }else{
                $(".resourcesSd").css("display","none");
                $(".advStandardContent").css("display","none");
                $('.advStandard_list_Content').html('');
            }
        }
        /***控制坐标显示***/
        /*if(isPosition=="1"){
         if(show=="1"){
         var data ={"id":adClassId,"resolution":resolution};
         getadvPosition(data);
         }
         }else{
         $(".position_point").val("");
         $(".hdPosition_point").val("");
         }*/
    }
    /****获取选中的索材id****/
    function getSelectedResources(){
        var HDImg='';
        var SDImg='';
        var sdParam='';
        var hdParam='';
        var control=false;
        var info='';
        if(show_dom!=""){
            var SD=show_dom.find(".advStandard").attr("name");
            var HD=show_dom.find(".advHd").attr("name");
            if(SD=="1"){
                var ast = getSelectedID(show_dom.find(".advStandard_list_Content"));
                if(ast == false){
                    info='2';
                    var returnDate={"control":false,"info":info};
                    return returnDate;
                }else{
                    SDImg=ast.split("&&")[0];
                    sdParam=ast.split("&&")[1];
                }
            }else{
                SDImg='';
                sdParam='';
            }
            if(HD=="1"){
                var aht =getSelectedID(show_dom.find(".advHd_list_Content"));
                if(aht == false){
                    info='3';
                    var returnDate={"control":false,"info":info};
                    return returnDate;
                }else{
                    HDImg=aht.split("&&")[0];
                    hdParam=aht.split("&&")[1];
                }
            }else{
                HDImg='';
                hdParam='';
            }
            if((SD=="1"&&HD=="0")||(SD=="0"&&HD=="1")||(SD=="1"&&HD=="1")){
                if(SD=="1"&&HD=="0"){
                    $("#isSd").val("1");
                    $("#isHd").val("0");
                    if(SDImg==""){
                        control=false;
                        info='0';
                    }else{
                        info='';
                        control=true;
                    }
                }
                if(SD=="0"&&HD=="1"){
                    $("#isSd").val("0");
                    $("#isHd").val("1");
                    if(HDImg==""){
                        info='1';
                        control=false;
                    }else{
                        info='';
                        control=true;
                    }
                }
                if(SD=="1"&&HD=="1"){
                    $("#isSd").val("1");
                    $("#isHd").val("1");
                    if(HDImg==""||SDImg==""){
                        control=false;
                        if(SDImg==""){
                            info='0';
                        }
                        if(HDImg==""){
                            info='1';
                        }
                    }else{
                        info='';
                        control=true;
                    }
                }
            }else{
                info='-1';
                control=false;
            }
            $("#path").val(SDImg);
            $("#hdPath").val(HDImg);
            $("#sdShowParam").val(sdParam);
            $("#hdShowParam").val(hdParam);
        }else{
            info='-1';
            control=false;
        }
        var returnDate={"control":control,"info":info}
        return returnDate;
    }
    function getSelectedID(node){
        var dom=node.find(".selected").find("img");
        var dom1;
        if(adClassId==1 || adClassId==2 || adClassId==4  || adClassId==5){
            dom1=node.find(".selected").find("input[type='number']");
        }
        var count=node.find(".selected").find("img").length;
        var imgId='';
        var showParam='';
        var isShow=true;
        if(count==0){
            imgId='';
            /*			$(".control_info").css("display","block");*/
        }else{
            for(var i=0;i<count;i++){
                imgId+=dom[i].getAttribute("id")+',';
                if(adClassId==1 || adClassId==2 || adClassId==4  || adClassId==5){
                    if(dom1[i].value ==null || dom1[i].value ==""){
                        isShow = false;
                    }
                    showParam+=dom[i].getAttribute("id")+'@'+dom1[i].value+',';
                }
            }
        }
        if(isShow==true){
            $("#btnSubmit").css("disabled","false");
            node.parent().parent().find(".uploadfile_error").css("display","none");
            node.parent().parent().find(".uploadfile_error").text("");
        }else{
            node.parent().parent().find(".uploadfile_error").css("display","block");
            node.parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"showParam.notNull"));
            return false;
        }
        return imgId+"&&"+showParam;
    }
    /**点击广告套餐初始化分辨率**/
    function InitializeResolution(){
        $(".switchResolution").find("input").attr("name","0");
        $(".switchResolution").find(".advStandard").attr("name","1");
    }
    /**图片资源切换**/
    $("#upload_adv_image .switchResolution").on("click","input",function(){
        var adtype=$("#adTypeId").val();
        if($(this).attr("name")=="0"){
            $(this).attr("name","1");
            if($(this).attr("class")=="advStandard"){
                if(adtype ==5){
                    $("#sd_gd_fx").css("display","block");
                    $("#move").css("display","block");
                }
            }else{
                if(adtype ==5){
                    $("#hd_gd_fx").css("display","block");
                    $("#move").css("display","block");
                }
            }
        }else{
            if($(this).attr("class")=="advStandard"){
                /*        		$("#path").val("");
                 */        		if(adtype ==5){
                    $("#sd_gd_fx").css("display","none");
                    $("#move").css("display","block");
                }
            }else{
                /*        		$("#hdPath").val("");
                 */        		if(adtype ==5){
                    $("#hd_gd_fx").css("display","none");
                    $("#move").css("display","block");
                }
            }
            $(this).attr("name","0");
        }
        showPointAndResources($(this).attr("class"),$(this).attr("name"));

    });
    /*视频资源切换*/
    $("#upload_adv_vedio .switchResolution").on("click","input",function(){
        var typeId=$("#adcombo_id").find('option:selected').val();
        $("#upload_adv_vedio").find(".switchResolution input").attr("name","0");
        $("#upload_adv_vedio .advStandard_list_Content").html("");
        $("#upload_adv_vedio .advHd_list_Content").html("");
        $("#upload_adv_vedio .resourcesHd").css("display","none");
        $("#upload_adv_vedio .resourcesSd").css("display","none");
        if($(this).attr("name")=="0"){
            $(this).attr("name","1");
        }else{
            $(this).attr("name","0");
        }
        showPointAndResources($(this).attr("class"),$(this).attr("name"));
    })
    /***************************修改时数据显示*************************/
    function alertShowInfo(){
        var typeId = $("#adTypeId").val();
        var childAdTypeId = $("#childAdTypeId").val();
        if(typeId != null && typeId != ""){
            if(typeId==6){
                $('.playTime').css("display", "block");
                $('#adv_vedio').css("display", "block");
                $('#adv_image').css("display", "none");
                control_show=1;
                show_dom=$("#upload_adv_vedio");
            }else{
                $('.playTime').css("display", "none");
                $('#adv_vedio').css("display", "none");
                $('#adv_image').css("display", "block");
                control_show=0;
                show_dom=$("#upload_adv_image");
            }
            if(typeId == 7 || typeId == 8){
                var o ={"id":typeId};
                var data = JSON.stringify(o);
                getchlidType(data,childAdTypeId);
            }else{
                $('#adcombo_id_chlidType').css("display", "none");
            }

            var isFlag = $("#isFlag").val();
            $(".uploadfile_error").css("display","none");
            if(isFlag == "0"){
                if(null  != $("#typeId").val()){
                    adClassId = $("#typeId").val();
                    $(".uploadfile_error").css("display","none");
                    $(".advHdContent").css("display","none");
                    $(".advStandardContent").css("display","none");
                }
            }else{
                $("#oldcomboId").val("");
                $("#comboId").val("");
                if(null  != $("#adTypeSelect").val()){
                    adClassId = $("#adTypeSelect").val();
                    $(".uploadfile_error").css("display","none");
                    $(".advHdContent").css("display","none");
                    $(".advStandardContent").css("display","none");
                }
            }
            adClassId=typeId;
            setlimit(adClassId);
            var path=$("#path").val();
            var hdPath=$("#hdPath").val();
            $(".advHdContent").css("display","none");
            $(".resourcesSd").css("display","none");
            $(".advStandardContent").css("display","none");
            $(".switchResolution").find("input").attr("name","0");
            if(path==""&&hdPath!=""){
                showPointAndResources("advHd","1");
                show_dom.find(".advHd").attr("name","1");
                $(".resourcesHd").css("display","block");
                if(typeId == 5){
                    $("#sd_gd_fx").css("display","none");
                    $("#hd_gd_fx").css("display","block");
                    $("#move").css("display","block");
                }
            }
            if(path!=""&&hdPath==""){
                showPointAndResources("advStandard","1");
                show_dom.find(".advStandard").attr("name","1");
                $(".resourcesSd").css("display","block");
                if(typeId == 5){
                    $("#sd_gd_fx").css("display","block");
                    $("#hd_gd_fx").css("display","none");
                    $("#move").css("display","block");
                }
            }
            if(path!=""&&hdPath!=""){
                show_dom.find(".advHd").attr("name","1");
                show_dom.find(".advStandard").attr("name","1");
                showPointAndResources("advHd","1");
                showPointAndResources("advStandard","1");
                $(".resourcesHd").css("display","block");
                $(".resourcesSd").css("display","block");
                if(typeId == 5){
                    $("#sd_gd_fx").css("display","block");
                    $("#hd_gd_fx").css("display","block");
                    $("#move").css("display","block");
                }
            }

            alertNets();
            function alertNets(){
                if(IsValidated($(".adTypeSelect"))&&IsValidated($(".setPlayTime"))&&setDataValidated($(".setPlayStartTime"))&&setDataValidated($(".setPlayEndTime"))){
                    $(".info-messages p").text("");
                    if($(this).attr("name")=="1"){
                        $('.channel_content').css("display","block");
                    }else{
                        /*   	            	 get_channelData();
                         */   	                 $('.channel_content').css("display","none");
                    }
                    return;
                }
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
            }
        }
        if(typeId ==5){
            checkSet("sd");
            checkSet("hd");
        }
    }
    alertShowInfo();

    function clearNet(){
        /* 清空频道相关选项*/
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

    function getInvalidNetWork(){
        var typeId=$("#typeId").find("option:selected").val();
        var post_data={"typeId":typeId};
        var netWorkType = $("#netWorkType").val();
        if(netWorkType=="quick"){
            var startDate=$("#startDate").val();
            var endDate=$("#endDate").val();
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
            post_data={"typeId":typeId,"chlidType":chlidType,"startDate":startDate,"endDate":endDate,"advertiserId":advertiserId};
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
                var html='';
                $.each(data,function(commentIndex,comment){
                    var netWorkId=comment["id"];
                    if(comment["invalid"]){
                        html+='<li id='+comment["id"]+'><input type="button" class="option" name="0" id='+commentIndex+'><label for='+commentIndex+' title='+comment["networkName"]+'>'+comment["networkName"]+'</label>';
                    }else{
                        html+='<li id='+comment["id"]+'><input type="button" class="option noselected" name="2" disabled=true id='+commentIndex+'><label for='+commentIndex+' title='+comment["networkName"]+'>'+comment["networkName"]+'</label>';
                    }
                });
                $(".networkContent ul").html(html);
            }
        });
    }
    function showSelectedNetWork(data){
        var Data=$("#networkIds").val();
        var netdata=Data.split(",");
        $.each(netdata,function(commentIndex,comment){
            var netWorkId=comment;
            $(".networkContent ul").find('li[id='+netWorkId+']').find("input").css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
            $(".networkContent ul").find('li[id='+netWorkId+']').find("input").attr({"name":"1"});
            $(".networkContent ul").find('li[id='+netWorkId+']').find("input").removeClass("noselected");
            $(".networkContent ul").find('li[id='+netWorkId+']').find("input").removeAttr("disabled");
        });
    }
    /***************************点击事件****************************/
    $('#adv_isFlag').change(function() {
        checkAddText();
    });
    $('#advertiser_id').change(function() {
        $("#oldcomboId").val("");
        var advertiserId = $("#advertiser_id").val();
        var isFlag = $("#isFlag").val();
        $(".uploadfile_error").css("display","none");
        $("#advertiser_id").select2();
        /*		onchangeByadvertiser();
         */		$(".resourcesHd").css("display","none");
        $('.advHd_list_Content').html('');
        InitializeResolution();
        adId =advertiserId;
        if(isFlag == "0"){
            if(null  != $("#typeId").val()){
                adClassId = $("#typeId").val();
                $(".uploadfile_error").css("display","none");
                $(".advHdContent").css("display","none");
                $(".advStandardContent").css("display","none");
                onchangeByCombo();
            }
        }else{
            if(null  != $("#adTypeSelect").val()){
                adClassId = $("#adTypeSelect").val();
                $(".uploadfile_error").css("display","none");
                $(".advHdContent").css("display","none");
                $(".advStandardContent").css("display","none");
                onchangeByCombo();
            }
        }

        alertNets();
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
        function alertNets(){
            if(IsValidated($(".adTypeSelect"))&&IsValidated($(".setPlayTime"))&&setDataValidated($(".setPlayStartTime"))&&setDataValidated($(".setPlayEndTime"))){
                $(".info-messages p").text("");
                if($(this).attr("name")=="1"){
                    $('.channel_content').css("display","block");
                }else{
                    /*	            	 get_channelData();
                     */	                 $('.channel_content').css("display","none");
                }
                return;
            }
        }
    });

    $('#isFlag').change(function() {
        $("#oldcomboId").val("");
        document.getElementById("startdate_span").innerText="";
        document.getElementById("enddate_span").innerText="";
        var isFlag = $("#isFlag").val();
        if(isFlag ==1){
            $('.playTime').css("display", "none");
            $('#adv_vedio').css("display", "none");
            $('#adv_image').css("display", "block");
            control_show=0;
            show_dom=$("#upload_adv_image");
            $("#adcombo_chlidType").html("");
            $("#adcombo_chlidType").select2();
        }else{
            $("#move").css("display","none");
            $("#velocity").val("").select2();
            clearNet();
        }
    });
    $('#typeId').change(function() {
        $("#oldcomboId").val("");
        $("#adcombo_chlidType").html("");
        $("#adcombo_chlidType").select2();
        var advertiserId = $("#advertiser_id").val();
        var typeId = $("#typeId").val();
        $("#adTypeId").val(typeId);
        adClassId = typeId;
        if(typeId==6){
            $('.playTime').css("display", "block");
            $('#adv_vedio').css("display", "block");
            $('#adv_image').css("display", "none");
            control_show=1;
            show_dom=$("#upload_adv_vedio");
        }else{
            $('.playTime').css("display", "none");
            $('#adv_vedio').css("display", "none");
            $('#adv_image').css("display", "block");
            $("#playTime").val("");
            control_show=0;
            show_dom=$("#upload_adv_image");
        }
        if(typeId == 4 || typeId == 5){
            $("#showCount").val("");
        }
        if(typeId == 7 || typeId == 8){
            var o ={"id":typeId};
            var data = JSON.stringify(o);
            getchlidType(data,null);
        }else{
            $('#adcombo_id_chlidType').css("display", "none");
        }
        if(null  != advertiserId){
            adId =advertiserId;
            $(".uploadfile_error").css("display","none");
            $(".advHdContent").css("display","none");
            $(".advStandardContent").css("display","none");
            onchangeByCombo();
        }
    });

    $("#adTypeSelect").change(function(){
        /*$("#sdMaxNC").val("");
         $("#hdMaxNC").val("");*/
        $("#oldcomboId").val("");
        $("#adcombo_chlidType").html("");
        $("#adcombo_chlidType").select2();
        var advertiserId = $("#advertiser_id").val();
        var typeId = $("#adTypeSelect").val();
        $("#adTypeId").val(typeId);
        adClassId = typeId;
        if(null  != advertiserId){
            adId =advertiserId;
            $(".uploadfile_error").css("display","none");
            $(".advHdContent").css("display","none");
            $(".advStandardContent").css("display","none");
            onchangeByCombo();
        }
        $("#adv_image").css("display","block");
        $("#adv_vedio").css("display","none");
        $('.playTime').css("display", "none");
        show_dom=$("#upload_adv_image");
        control_show=0;
        if(typeId == 5){
            var SD=show_dom.find(".advStandard").attr("name");
            var HD=show_dom.find(".advHd").attr("name");
            $("#move").css("display","block");
            if(SD=="1"){
                $("#sd_gd_fx").css("display","block");
            }else{
                $("#sd_gd_fx").css("display","none");
            }
            if(HD=="1"){
                $("#hd_gd_fx").css("display","block");
            }else{
                $("#hd_gd_fx").css("display","none");
            }
        }else{
            $("#sd_gd_fx").css("display","none");
            $("#hd_gd_fx").css("display","none");
            $("#move").css("display","none");
        }
    });
    $('#adCombo_SellData').change(function(){
        $("#startDate").val("");
        $("#endDate").val("");
        $("#validStartTime").val("");
        $("#validEndTime").val("");
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

    $('#hdFx').change(function(){
        $("#hdPath").val("");
        $("#hdShowParam").val("");
        showPointAndResources("advHd","1");
    });

    $('#sdFx').change(function(){
        $("#path").val("");
        $("#sdShowParam").val("");
        showPointAndResources("advStandard","1");
    });
    $("#sdversion").change(function () {
        var sd =    $("#upload_adv_image").find(".advStandard").attr("name");
        if(sd == 1){
            getSelectedResources();
            showPointAndResources("advStandard","1");
            var adTypeId = $("#adTypeId").val();
            if(adTypeId == 5){
                checkSet("sd");
            }
        }
    });
    $("#hdversion").change(function () {
        var hd =    $("#upload_adv_image").find(".advHd").attr("name");
        if(hd == 1){
            getSelectedResources();
            showPointAndResources("advHd","1");
            var adTypeId = $("#adTypeId").val();
            if(adTypeId == 5){
                checkSet("hd");
            }
        }
    });

    function checkSet(standard) {
        var selTime = 0;
        var pictureTimes = $("#pictureTimes").val();//每轮每张图片显示次数
        var showTime = $("#showTime").val();//每轮显示总时间
        var pictureInterval = $("#pictureInterval").val();//每张图片显示间隔时间
        if(pictureTimes ==null || pictureTimes == "" || showTime ==null || showTime == "" || pictureInterval ==null || pictureInterval == ""){
            $("#erron_span").text(accipiter.getLang_(messageLang,"improveAdCombo"));
            return false;
        }else{
            $("#erron_span").text("");
        }
        var node;
        if(standard =="sd"){
            node = $('.advStandard_list_Content');
        }else{
            node = $('.advHd_list_Content');
        }
        node.find(".item.selected").find("input[type='number']").each(function() {
            var sss = parseInt($(this).val());
            var ml = (parseInt(sss)+parseInt(pictureInterval))*parseInt(pictureTimes);
            selTime = parseInt(selTime)+parseInt(ml);
        });
        if(selTime > 0){
            selTime = parseInt(selTime)-parseInt(pictureInterval);
        }
        var remTime = parseInt(showTime)-parseInt(selTime);
        if(standard == "sd"){
            $("#sdSelTime").val(selTime);
            $("#sdRemTime").val(remTime);
        }else{
            $("#hdSelTime").val(selTime);
            $("#hdRemTime").val(remTime);
        }
    }

    function selAllFun(e) {
        var type =  e.attr("id");
        var curv = e.attr("name");
        var def = 10;
        var parentNode;
        var standard = "sd";
        if(type == "sdSelAll"){
            parentNode = $('.advStandard_list_Content');
        }else{
            parentNode = $('.advHd_list_Content');
            standard = "hd";
        }
        //计算可选择图片最大数量
        var pictureTimes = $("#pictureTimes").val();//每轮每张图片显示次数
        var showTime = $("#showTime").val();//每轮显示总时间
        var pictureInterval = $("#pictureInterval").val();//每张图片显示间隔时间
        if(pictureTimes ==null || pictureTimes == "" || showTime ==null || showTime == "" || pictureInterval ==null || pictureInterval == ""){
            $("#erron_span").text(accipiter.getLang_(messageLang,"improveAdCombo"));
            return false;
        }else{
            $("#erron_span").text("");
        }
        if(curv == "0"){
            e.attr("name","1");
            e.css("background","url('../../static/images/icon/ic_checkbox_ture.png')");
        }else{
            e.attr("name","0");
            e.css("background","url('../../static/images/icon/ic_checkbox_false.png')");
        }
        var maxSel = Math.floor((parseInt(showTime)+parseInt(pictureInterval))/((parseInt(def)+parseInt(pictureInterval))*parseInt(pictureTimes)));
        parentNode.find(".item").find("input[class='action_button']").each(function () {
            $(this).parent().parent().parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
            $(this).parent().find("label[class='error']").remove();
            $(this).parent().parent().removeClass("selected");
            $(this).parent().parent().removeClass("valid");
            $(this).parent().parent().attr("name","0");
            $(this).parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_false.png")');
            $(this).parent().parent().find("input").get(1).setAttribute("class","pcshowTime");
            $(this).parent().parent().find("input[class='pcshowTime']").val("");
            $(this).parent().parent().find("input[class='pcshowTime']").css("display","none");
        });
        var min = $("#psTs").val();
        parentNode.find(".item :lt("+maxSel+")").find("input[class='action_button']").each(function() {
            if(curv == 0){
                $(this).parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                $(this).parent().parent().addClass("selected");
                $(this).parent().parent().attr("name","1");
                $(this).parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
                $(this).parent().parent().find("input[class='pcshowTime']").css("display","block");
                $(this).parent().parent().find("input[class='pcshowTime']").val(def);
                $(this).parent().parent().find("input[class='pcshowTime']").attr("min",min);
                $(this).parent().parent().find("input").get(1).setAttribute("class","pcshowTime selected");
            }else{
                $(this).parent().parent().parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                $(this).parent().find("label[class='error']").remove();
                $(this).parent().parent().removeClass("selected");
                $(this).parent().parent().removeClass("valid");
                $(this).parent().parent().attr("name","0");
                $(this).parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_false.png")');
                $(this).parent().parent().find("input").get(1).setAttribute("class","pcshowTime");
                $(this).parent().parent().find("input[class='pcshowTime']").val("");
                $(this).parent().parent().find("input[class='pcshowTime']").attr("min",0);
                $(this).parent().parent().find("input[class='pcshowTime']").css("display","none");
            }
        });
        checkSet(standard);
    }

    function plSet(e) {
        var curV = e.val();
        if(null == curV || curV == ""){
            return false;
        }
        curV = parseInt(curV);
        var pictureTimes = $("#pictureTimes").val();//每轮每张图片显示次数
        var pictureInterval = $("#pictureInterval").val();//每张图片显示间隔时间
        var showTime = $("#showTime").val();//每轮显示总时间
        if(pictureTimes ==null || pictureTimes == "" || showTime ==null || showTime == "" || pictureInterval ==null || pictureInterval == "") {
            $("#erron_span").text(accipiter.getLang_(messageLang,"improveAdCombo"));
            return false;
        }else{
            $("#erron_span").text("");
        }
        var sel = e.parent().parent().find(".item.selected").find("input[type='number']").length;
        var picSum = parseInt(sel)*parseInt(pictureTimes);//已选择图片占用时间
        var as =Math.floor((parseInt(showTime)-(parseInt(picSum)-1)*parseInt(pictureInterval))/picSum);
        if(curV>as){
            curV =as;
            e.val(as);
        }
        e.parent().parent().find(".item.selected").find("input[type='number']").each(function() {
            $(this).attr("max",curV);
            $(this).val(curV);
        });
    }

    /****索材点击事件******/
    function itemclick(parent,node,standard){
        var nodeClass = node.attr("class");

        if(nodeClass.indexOf("selAll") != -1){
            selAllFun(node);//全选
            return false;
        }
        if(nodeClass.indexOf("tiemSet") != -1){
            plSet(node);//批量设置时间
            return false;
        }

        var count=parent.find(".item.selected").length;
        var flag = $("#isFlag").val();
        var cur = 0;
        var sel = 0;
        var sum = 0;
        var selTime = 0;
        var maxset = 30;
        var max=10;
        var def=10;
        var min = $("#psTs").val();
        var adTypeId = $("#adTypeId").val();
        /* parent.find(".item.selected").find("input[class='action_button']").each(function() {
         var sss = parseInt($(this).attr("for"));
         sel = parseInt(sel)+parseInt(sss);
         });*/

        if(adTypeId == "1"){
            maxset =  $("#bpTS").val();
            var curId = node.parent().find("input[class='action_button']").attr("id");
            parent.find(".item.selected").find("input[type='number']").each(function() {
                var selId =  $(this).parent().find("input[class='action_button']").attr("id");
                if(selId != curId){
                    var sss = parseInt($(this).val());
                    selTime = parseInt(selTime)+parseInt(sss);
                }
            });
            var bpTT = $("#bpTT").val();
            if(parseInt(bpTT) - parseInt(selTime) > maxset){
                max = maxset;
            }else{
                max = parseInt(bpTT) - parseInt(selTime);
            }
            def = max;
            node.parent().find("input[type='number']").attr("max",max);
        }

        if(adTypeId == "5"){
            //要求：Σ（每张图片的显示时间+间隔时间）*显示次数<=每轮显示总时间-间隔时间
            max = 30;//每张图片最长显示时间
            maxset = $("#showTime").val();//每轮显示总时间
            var pictureTimes = $("#pictureTimes").val();//每轮每张图片显示次数
            var pictureInterval = $("#pictureInterval").val();//每张图片显示间隔时间
            if(pictureTimes ==null || pictureTimes == "" || maxset ==null || maxset == "" || pictureInterval ==null || pictureInterval == ""){
                $("#erron_span").text(accipiter.getLang_(messageLang,"improveAdCombo"));
                return false;
            }else{
                $("#erron_span").text("");
            }
            var curId = node.parent().find("input[class='action_button']").attr("id");
            parent.find(".item.selected").find("input[type='number']").each(function() {
                var selId =  $(this).parent().find("input[class='action_button']").attr("id");
                if(selId != curId){
                    var sss = parseInt($(this).val());
                    var ml = (parseInt(sss)+parseInt(pictureInterval))*parseInt(pictureTimes);
                    selTime = parseInt(selTime)+parseInt(ml);
                }
            });
            selTime = selTime -parseInt(pictureInterval);
            max = (maxset - parseInt(selTime))/parseInt(pictureTimes)-parseInt(pictureInterval);
            if( max> 0){
                if(def>max){
                    def=max;
                }
                $("#erron_span").text("");
            }else{
                $("#erron_span").text(accipiter.getLang_(messageLang,"outTimeSet"));
                return false;
            }
            node.parent().find("input[type='number']").attr("max",max);
        }


        var dom=node.attr("class");
        if(standard=="sd" && adTypeId != 6){
            sum = $("#sdMaxNC").val();
            if(null == sum || sum == ""){
                if(flag == 0){
                    document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.nonetwork");
                }else{
                    $(".setNet-errInfo").text(accipiter.getLang_(messageLang,"adv.nochannel"));
                }
                return;
            }
        }
        if(standard=="hd" && adTypeId != 6){
            sum = $("#hdMaxNC").val();
            if(null == sum || sum == ""){
                if(flag == 0){
                    document.getElementById("enddate_span").innerText = accipiter.getLang_(messageLang,"adv.nonetwork");
                }else{
                    $(".setNet-errInfo").text(accipiter.getLang_(messageLang,"adv.nochannel"));
                }
            }
        }
        if(adTypeId =="1" && standard=="sd"){
            selectedlimit = selectedSdlimit;
        }
        if(adTypeId =="1" && standard=="hd"){
            selectedlimit = selectedHdlimit;
        }
        if(dom=="action_img"){
            cur = node.parent().find("input[class='action_button']").attr("for");
            if(node.parent().find("input").attr("disabled")!="disabled" || node.parent().find("input").attr("disabled")==undefined){
                var control= node.parent().attr("name");
                if(control=="0"){
                    if(count<selectedlimit){
                        if(adTypeId =="6"){
                            node.parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                            node.parent().addClass("selected");
                            node.parent().attr("name","1");
                            node.parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
                        }else{
                            if(parseInt(cur)+parseInt(sel)<=parseInt(sum)){
                                node.parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                                node.parent().addClass("selected");
                                node.parent().attr("name","1");
                                node.parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
                            }else{
                                node.parent().parent().parent().parent().find(".uploadfile_error").css("display","block");
                                node.parent().parent().parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"outNCRange"));
                                return false;
                            }
                        }
                    }else{
                        node.parent().parent().parent().parent().find(".uploadfile_error").css("display","block");
                        node.parent().parent().parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"file.max"));
                    }
                }else{
                    node.parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                    node.parent().parent().parent().parent().find(".uploadfile_error").text("");
                    node.parent().removeClass("selected");
                    node.parent().attr("name","0");
                    node.parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_false.png")');
                }
            }
        }else if(dom=="action_button"){
            cur = node.attr("for");
            var control=node.parent().parent().attr("name");//判断当前是否选中，如果只选中一张图片:默认值0（长显）,最小值为0。如果选中多张图片，每张最小值是系统配置的最下时间（默认5）
            if( adTypeId == 5){
                if(count == 0 && control == 0){
                    min = 0;
                    def = 0;
                    node.parent().find("input[type='number']").attr("min",min);
                    node.parent().find("input[type='number']").val(def);
                }else if(count == 2 && control == 1){
                    min = 0;
                    def = 0;
                    parent.find(".item.selected").each(function () {
                        $(this).find("input[type='number']").attr("min",min);
                        $(this).find("input[type='number']").val(def);
                    });
                }else{
                    parent.find(".item.selected").each(function () {
                        $(this).find("input[type='number']").attr("min",min);
                        if(count == 1){
                            if($(this).find("input[type='number']").val() <= min ){
                                $(this).find("input[type='number']").val(def);
                            }
                        }
                    });
                    node.parent().find("input[type='number']").attr("min",min);
                }
            }

            if(control=="0"){
                if(count<selectedlimit){
                    if(adTypeId =="6"){
                        node.parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                        node.parent().parent().addClass("selected");
                        node.parent().parent().attr("name","1");
                        node.parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
                    }else{
                        if(parseInt(cur)+parseInt(sel)<=parseInt(sum)){
                            node.parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                            node.parent().parent().addClass("selected");
                            node.parent().parent().attr("name","1");
                            node.parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
                            node.parent().parent().parent().parent().find("input[class='pcshowTime']").attr("max",max);
                            if(adTypeId =="1"  || adTypeId == "5"){
                                node.parent().parent().find("input[class='pcshowTime']").css("display","block");
                                node.parent().parent().find("input[class='pcshowTime']").val(def);
                                node.parent().parent().find("input").get(1).setAttribute("class","pcshowTime selected");
                            }
                            if(adTypeId =="2" || adTypeId =="4"){
                                node.parent().parent().parent().parent().find("input").removeClass("selected");
                                node.parent().parent().parent().parent().find("input[class='pcshowTime']").val("");
                                node.parent().parent().find("input[class='pcshowTime']").css("display","block");
                                node.parent().parent().find("input[class='pcshowTime']").val(0);
                                node.parent().parent().find("input").get(1).setAttribute("class","pcshowTime selected");
                            }
                        }else{
                            node.parent().parent().parent().parent().find(".uploadfile_error").css("display","block");
                            node.parent().parent().parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"outNCRange"));
                            return false;
                        }
                    }
                }else{
                    if(adTypeId =="1"  || adTypeId == "5"){
                        node.parent().parent().parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","block");
                        node.parent().parent().parent().parent().parent().parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"file.max"));
                    }else if(adTypeId =="2" || adTypeId =="4"){
                        node.parent().parent().parent().parent().find("input[class='action_button']").parent().parent().removeClass("selected");
                        node.parent().parent().parent().parent().find("input[class='action_button']").parent().parent().attr("name","0");
                        node.parent().parent().parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_false.png")');
                        node.parent().parent().parent().parent().find("input").removeClass("selected");
                        node.parent().parent().parent().parent().find("input").removeClass("valid");
                        node.parent().parent().parent().parent().find("input[class='pcshowTime']").val("");
                        node.parent().parent().parent().parent().find("input[class='pcshowTime']").css("display","none");
                        var sel1 = 0;
                        parent.find(".item.selected").find("input[class='action_button']").each(function() {
                            var sss = parseInt($(this).attr("for"));
                            sel1 = parseInt(sel1)+parseInt(sss);
                        });
                        if(parseInt(cur)+parseInt(sel1)<=parseInt(sum)){
                            node.parent().parent().addClass("selected");
                            node.parent().parent().attr("name","1");
                            node.parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_ture.png")');
                            node.parent().parent().find("input[class='pcshowTime']").val(0);
                            node.parent().parent().find("input[class='pcshowTime']").css("display","block");
                            node.parent().parent().find("input[class='pcshowTime']").addClass("selected");
                        }else{
                            node.parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","block");
                            node.parent().parent().parent().parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"outNCRange"));
                        }
                    }else{
                        node.parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","block");
                        node.parent().parent().parent().parent().parent().find(".uploadfile_error").text(accipiter.getLang_(messageLang,"file.max"));
                    }
                }
            }else{
                if(adTypeId =="1" || adTypeId =="2" || adTypeId =="4" || adTypeId == "5"){
                    node.parent().parent().parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                    node.parent().find("label[class='error']").remove();
                }else{
                    node.parent().parent().parent().parent().parent().find(".uploadfile_error").css("display","none");
                }
                node.parent().parent().removeClass("selected");
                node.parent().parent().removeClass("valid");
                node.parent().parent().attr("name","0");
                node.parent().parent().find("input[class='action_button']").css("background",'url("../../static/images/icon/ic_checkbox_false.png")');
                if(adTypeId =="1" || adTypeId =="2" || adTypeId =="4" || adTypeId == "5"){
                    node.parent().parent().find("input").get(1).setAttribute("class","pcshowTime");
                    node.parent().parent().find("input[class='pcshowTime']").val("");
                    node.parent().parent().find("input[class='pcshowTime']").css("display","none");
                }
            }
        }else{
            if(adTypeId =="1" || adTypeId =="2" || adTypeId =="4" || adTypeId == "5"){
                var s1 = node.attr("value");
                var s2 = node.attr("max");
                if(parseInt(s1) > parseInt(s2) ){
                    node.attr("value",s2);
                    node.val(s2);
                    node.parent().find("label[class='error']").remove();
                }
            }
        }
        if(adTypeId == 5){
            checkSet(standard);
        }
    }
    $('.advStandard_list_Content').on("click","img,input",function(){
        itemclick($('.advStandard_list_Content'),$(this),'sd');
    });
    $('.advHd_list_Content').on("click","img,input",function(){
        itemclick($('.advHd_list_Content'),$(this),'hd');
    });
    /*****坐标隐藏标签赋值*****/
    $('#position_id').change(function() {
        var positionText=$('#position_id').find('option:selected').text();
        $(".position_point").val(positionText);

    });
    $('#hd_position_id').change(function() {
        var positionText=$('#hd_position_id').find('option:selected').text();
        $(".hdPosition_point").val(positionText);

    });
    /*****数据提交*****/
    $("#btnSubmit").on("click",function(){
        /*****坐标隐藏标签赋值*****/
        if(isPosition=="1"){
            if($('#position_id').find('option:selected').val()!=""){
                var positionText=$('#position_id').find('option:selected').text();
                $(".position_point").val(positionText);
            }
            if($('#hd_position_id').find('option:selected').val()!=""){
                var positionText=$('#hd_position_id').find('option:selected').text();
                $(".hdPosition_point").val(positionText);
            }
        }
        var juage=getSelectedResources().control;
        var alerInfo=getSelectedResources().info;
        if(juage==false){
            var messageInfo="";
            if(alerInfo=="-1"){
                messageInfo=accipiter.getLang_(messageLang,"resources.notNull");
            }
            if(alerInfo=="0"){
                messageInfo=accipiter.getLang_(messageLang,"SDresources.notNull");
            }
            if(alerInfo=="1"){
                messageInfo=accipiter.getLang_(messageLang,"HDresources.notNull");
            }
            if(alerInfo=="2" || alerInfo=="3"){
                return false;
            }
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
    });
    $(".icon_control,.btn_submit,.btn_clear").on("click",function(){
        $(".control_info").css("display","none");
    });

});
