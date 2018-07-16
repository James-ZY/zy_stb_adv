var strVarCity = '';
strVarCity += '<div class="aui_state_box"><div class="aui_state_box_bg"></div>';
strVarCity += '  <div class="aui_alert_zn aui_outer">';
strVarCity += '    <table class="aui_border" style="border:2px solid #fff;">';
strVarCity += '      <tbody>';
strVarCity += '        <tr>';
strVarCity += '          <td class="aui_c">';
strVarCity += '            <div class="aui_inner">';
strVarCity += '              <table class="aui_dialog">';
strVarCity += '                <tbody>';
strVarCity += '                  <tr>';
strVarCity += '                    <td class="aui_header" colspan="2"><div class="aui_titleBar">';
strVarCity += '                      <div class="aui_title" style="cursor: move;">'+accipiter.getLang_(messageLang,"districtCategory.select")+'</div>';
strVarCity += '                        <a href="javascript:;" class="aui_close" onclick="Close()">×</a>';
strVarCity += '                      </div>';
strVarCity += '                    </td>';
strVarCity += '                  </tr>';
strVarCity += '                  <tr>';
strVarCity += '                  <tr>';
strVarCity += '                    <td class="aui_icon" style="display: none;">';
strVarCity += '                     <div class="aui_iconBg" style="background: transparent none repeat scroll 0% 0%;"></div></td>';
strVarCity += '                       <td class="aui_main" style="width: auto; height: auto;">';
strVarCity += '                        <div class="aui_content" style="padding: 0px; position:relative">';
strVarCity += '                          <div id="" style="width: 900px; position:relative;">';
//strVarCity += '                            <div class="data-result"><em>最多选择 <strong>2000</strong> 项</em></div>';
strVarCity += '                            <div class="data-result"></div>';
strVarCity += '                            <div class="data-error" style="display: none;">最多只能选择 3 项</div>';
strVarCity += '                            <div class="data-search" id="searchRun"><input class="run" name="searcharea"/><div class="searchList run"></div></div>';
strVarCity += '                            <div class="data-tabs">';
strVarCity += '                              <ul>';
strVarCity += '                                <li onclick="removenode_area(this)" data-selector="tab-all" class="active"><a href="javascript:;"><span>'+accipiter.getLang("all")+'</span><em></em></a></li>';
strVarCity += '                              </ul>';
strVarCity += '                            </div>';
strVarCity += '                            <div class="data-container data-container-city">';
strVarCity += '                            </div>';
strVarCity += '                          </div>';
strVarCity += '                        </div>';
strVarCity += '                      </div>';
strVarCity += '                    </td>';
strVarCity += '                  </tr>';
strVarCity += '                  <tr>';
strVarCity += '                    <td class="aui_footer" colspan="2">';
strVarCity += '                      <div class="aui_buttons">';
strVarCity += '                      <button class="aui-btn aui-btn-primary" onclick="save_City()" type="button">'+accipiter.getLang("t4")+'</button>';
strVarCity += '                        <button class="aui-btn aui-btn-light" onclick="Close()" type="button">'+accipiter.getLang("t5")+'</button>';
strVarCity += '                      </div>';
strVarCity += '                    </td>';
strVarCity += '                  </tr>';
strVarCity += '                </tbody>';
strVarCity += '              </table>';
strVarCity += '            </div></td>';
strVarCity += '        </tr>';
strVarCity += '      </tbody>';
strVarCity += '    </table>';
strVarCity += '  </div>';
strVarCity += '</div>';

// 全局变量
var datatype        = "";
var dataCityinput   = null;
var __LocalDataCities = {};
var dataarrary = {};

var searchValue = {};
var channelSearchValue = {};//频道搜索数据全局变量

$(document).on('click', '.area-duoxuan', function () {
	initDis();
    appendCity(this, 'duoxuan');
});

$(document).on('click', '.area-danxuan', function () {
	initDis();
    appendCity(this, 'danxuan');
});

function initDis(){
	var data = {};
    var districtMode = $("#districtMode").val();
    if(districtMode == "selNetDis"){
    	var operatorsId = $("#operatorsId").val();
    	data = {operatorsId:operatorsId};
    }
	var postData = JSON.stringify(data);
	$.ajax({
		url :accipiter.getRootPath() + "/adv/districtCategory/getDistrictCategory",
		type :"post",
		async:false,
		data :postData,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success:function(data){
			if(data != null){
				__LocalDataCities = data;
				dataarrary = __LocalDataCities.list;
			}			
		}
	});
	searchValue = searchdata();
}

function appendCity(thiscon, Cityxz) {
    dataCityinput = thiscon;
    datatype = Cityxz;
    $('body').append(strVarCity);
    if (datatype == "danxuan") {
        $('.data-result').find('strong').text('1');
    } else {
        //$('.data-result').html('<em>可选择多项</em>');
    }
    var selArea =  $("#selArea").val();
    if (selArea != "") {
        var inputarry = selArea.split('-');
        var inputarryname = $(dataCityinput).val().split('-');
        if (inputarry.length > 0) {
            for (var i in inputarry) {
            	 if (inputarry.hasOwnProperty(i) === true){  
            		$('.data-result').append('<span class="save_box aui-titlespan" data-code="' + inputarry[i].split(':')[0] + '" data-name="' + inputarryname[i] + '">' + inputarryname[i] + '<i onclick="removespan_area(this)">×</i></span>');
            	 }
            	 }
        }
    }
    
    var minwid = document.documentElement.clientWidth;
    $('.aui_outer .aui_header').on("mousedown", function (e) {
        /*$(this)[0].onselectstart = function(e) { return false; }*///防止拖动窗口时，会有文字被选中的现象(事实证明不加上这段效果会更好)
        $(this)[0].oncontextmenu = function (e) { return false; } //防止右击弹出菜单
        var getStartX = e.pageX;
        var getStartY = e.pageY;
        var getPositionX = (minwid / 2) - $(this).offset().left,
            getPositionY = 60;
        $(document).on("mousemove", function (e) {
            var getEndX = e.pageX;
            var getEndY = e.pageY;
            $('.aui_outer').css({
                left: getEndX - getStartX - getPositionX,
                top: getEndY - getStartY + getPositionY
            });

        });
        $(document).on("mouseup", function () {
            $(document).unbind("mousemove");
        });
    });
    selectProvince('all', null, '');
    auto_area.run();
}

function selectProvince(type, con, isremove) {
    //显示省级
	var districtMode = $("#districtMode").val();
    var strVarCity = "";
    if (type == "all") {
    	if(__LocalDataCities.category == null || __LocalDataCities.category == ""){
    		return false;
    	}
        var dataCityxz      = __LocalDataCities.category.provinces;
        var datahotcityxz   = __LocalDataCities.category.hotcities;
        // 加载热门城市和省份
        strVarCity += '<div class="view-all" id="">';
        strVarCity += '  <p class="data-title">'+accipiter.getLang_(messageLang,"hot.districtCategory")+'</p>';
        strVarCity += '    <div class="data-list data-list-hot">';
        strVarCity += '      <ul class="clearfix">';
        for (var i in datahotcityxz) {
        	 //只遍历对象自身的属性，而不包含继承于原型链上的属性。  
            if (datahotcityxz.hasOwnProperty(i) === true){  
            	strVarCity += '<li><a href="javascript:;" data-code="' + datahotcityxz[i] + '" data-selfcode="'+dataarrary[datahotcityxz[i]][2]+'" data-name="' + dataarrary[datahotcityxz[i]][0] + '" class="d-item"  onclick="selectProvince(\'sub\',this,\'\')">' + dataarrary[datahotcityxz[i]][0] + '<label>0</label></a></li>';                     
            }  
        }
        strVarCity += '      </ul>';
        strVarCity += '    </div>';
        strVarCity += '    <p class="data-title">'+accipiter.getLang_(messageLang,"all.districtCategory")+'</p>';
        strVarCity += '   <div class="data-list data-list-provinces">';
        strVarCity += '  <ul class="clearfix">';
        for (var i in dataCityxz) {
        	 if (dataCityxz.hasOwnProperty(i) === true){  
                strVarCity += '<li><a href="javascript:;" data-code="' + dataCityxz[i] + '" data-selfcode="'+dataarrary[dataCityxz[i]][2]+'" data-name="' + dataarrary[dataCityxz[i]][0] + '" class="d-item"  onclick="selectProvince(\'sub\',this,\'\')">' + dataarrary[dataCityxz[i]][0] + '<label>0</label></a></li>';
           }
        }
        strVarCity += ' </ul>';
        strVarCity += '</div>';
        $('.data-container-city').html(strVarCity);

        $('.data-result span').each(function (index) {
            if ($('a[data-code=' + $(this).data("code") + ']').length > 0) {
                $('a[data-code=' + $(this).data("code") + ']').addClass('d-item-active');
                if ($('a[data-code=' + $(this).data("code") + ']').attr("class").indexOf('data-all') > 0) {
                    $('a[data-code=' + $(this).data("code") + ']').parent('li').nextAll('li').find('a').css({ 'color': '#ccc', 'cursor': 'not-allowed' });
                    $('a[data-code=' + $(this).data("code") + ']').parent('li').nextAll("li").find('a').attr("onclick", "");
                } else {
                    if ($('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').length > 0) {
                        var numlabel = $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text();
                        $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text(parseInt(numlabel) + 1).show();
                    }
                }
            } else {
                var numlabel = $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text();
                $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text(parseInt(numlabel) + 1).show();
            }
        });
    }
    //显示下一级
    else {
        var dataCityxz = __LocalDataCities.category.provinces;
        var relations  = __LocalDataCities.relations;
        if (typeof (relations[$(con).data("code")]) != "undefined") {
            //添加标题
            if (isremove != "remove") {
                $('.data-tabs li').each(function () {
                    $(this).removeClass('active');
                });
                $('.data-tabs ul').append('<li data-code="' + $(con).data("code") + '" data-selfcode="'+$(con).data("selfcode")+'" data-name="' + $(con).data("name") + '" class="active" onclick="removenode_area(this)"><a href="javascript:;"><span>' + $(con).data("name") + '</span><em></em></a></li>');
            }
            //添加内容
            strVarCity += '<ul class="clearfix">';
            if (datatype == "danxuan") {
/*                strVarCity += '<li class="li-disabled" style="width:100%" ><a href="javascript:;" class="d-item data-all"  data-code="' + $(con).data("code") + '" data-selfcode="'+$(con).data("selfcode")+'" data-name="' + $(con).data("name") + '">' + $(con).data("name") + '<label>0</label></a></li>';
*/                strVarCity += '<li class="" style="width:100%"><a href="javascript:;" class="d-item data-all"  data-code="' + $(con).data("code") + '" data-selfcode="'+$(con).data("selfcode")+'" data-name="' + $(con).data("name") + '"  onclick="selectitem_area(this)">' + $(con).data("name") + '<label>0</label></a></li>';
            } else {
                strVarCity += '<li class="" style="width:100%"><a href="javascript:;" class="d-item data-all"  data-code="' + $(con).data("code") + '" data-selfcode="'+$(con).data("selfcode")+'" data-name="' + $(con).data("name") + '"  onclick="selectitem_area(this)">' + $(con).data("name") + '<label>0</label></a></li>';
            }
            for (var i in relations[$(con).data("code")]) {
            	if (relations[$(con).data("code")].hasOwnProperty(i) === true){  
                  strVarCity += '<li><a href="javascript:;" class="d-item" data-code="' + relations[$(con).data("code")][i] + '" data-selfcode="'+dataarrary[relations[$(con).data("code")][i]][2]+'"  data-name="' + dataarrary[relations[$(con).data("code")][i]][0] + '" onclick="selectProvince(\'sub\',this,\'\')">' + dataarrary[relations[$(con).data("code")][i]][0] + '<label>0</label></a></li>';
            	 }
            	}
            strVarCity += '</ul>';
            $('.data-container-city').html(strVarCity);
        } else {
            if (datatype == "duoxuan") {
                if (typeof $(con).data('flag') != 'undefined') {
                    if($('.data-result span[data-code="' + $(con).data("code") + '"]').length > 0) {
                        return false;
                    }
                }
                if ($(con).attr("class").indexOf('d-item-active') > 0) {
                    $('.data-result span[data-code="' + $(con).data("code") + '"]').remove();
                    $(con).removeClass('d-item-active');
                    // 省份显示城市数量减一,当为0时不显示
                    if ($('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').length > 0) {
                        var numlabel = $('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').find('label').text();
                        if (parseInt(numlabel) == 1) {
                            $('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').find('label').text(0).hide();
                        } else {
                            $('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').find('label').text(parseInt(numlabel) - 1);
                        }
                    }
                    return false;
                } else {
                    // 已全选省份,不可再选
                    if ($('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').hasClass('d-item-active')) {
                        $('.data-error').text('已全选省份,不可再选');
                        $('.data-error').slideDown();
                        setTimeout("$('.data-error').text('最多只能选择 3 项').hide()", 1000);
                        return false;
                    }
                }
                if ($('.data-result span').length > 2000) {
                    $('.data-error').slideDown();
                    setTimeout("$('.data-error').hide()", 1000);
                    return false;
                } else {
                		$('.data-result').append('<span class="save_box aui-titlespan" data-code="' + $(con).data("code") + '" data-name="' + $(con).data("name") + '">' + $(con).data("name") + '<i onclick="removespan_area(this)">×</i></span>');                		
                    $(con).addClass('d-item-active');
                }
            } else {
                //单选 
                $('.data-result span').remove();
                 // 消除搜索影响
                $('.data-list-hot li').siblings('li').find('a').removeClass('d-item-active');
                $('.data-container-city li').siblings('li').find('a').removeClass('d-item-active');

            	$('.data-result').append('<span class="save_box aui-titlespan" data-code="' + $(con).data("code") + '" data-name="' + $(con).data("name") + '">' + $(con).data("name") + '<i onclick="removespan_area(this)">×</i></span>');                		
                
                $(con).parent('li').siblings('li').find('a').removeClass('d-item-active');
                $(con).addClass('d-item-active');

                $('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').removeClass('d-item-active');
                $('.data-list-provinces a[data-code=' + $(con).data("code").toString().substring(0, 6) + ']').find('label').text(0).hide();
            }
        }
        $('.data-result span').each(function () {
            $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text(0).hide();
        });
        $('.data-result span').each(function () {
            if ($('a[data-code=' + $(this).data("code") + ']').length > 0) {
                $('a[data-code=' + $(this).data("code") + ']').addClass('d-item-active');
                if ($('a[data-code=' + $(this).data("code") + ']').attr("class").indexOf('data-all') > 0) {
                    if (datatype == "duoxuan") {
                        $('a[data-code=' + $(this).data("code") + ']').parent('li').nextAll('li').find('a').css({ 'color': '#ccc', 'cursor': 'not-allowed' });
                        $('a[data-code=' + $(this).data("code") + ']').parent('li').nextAll("li").find('a').attr("onclick", "");
                    }
                } else {
                    if (datatype == "danxuan") {
                        $('.data-list-provinces a').each(function () {
                            $(this).find('label').text(0).hide();
                        });
                    }
                    if ($('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').length > 0) {
                        var numlabel = $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text();
                        $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text(parseInt(numlabel) + 1).show();
                    }
                }
            } else {
                var numlabel = $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text();
                $('.data-list-provinces a[data-code=' + $(this).data("code").toString().substring(0, 6) + ']').find('label').text(parseInt(numlabel) + 1).show();
            }
        });
    }
}

function selectitem_area(con) {
	var districtMode = $("#districtMode").val();
    if (datatype == "duoxuan") {
        //多选
        if ($('.data-result span').length > 2000) {
            $('.data-error').slideDown();
            setTimeout("$('.data-error').hide()", 1000);
            return false;
        } else {
            $('.data-result span').each(function () {
                if ($(this).data("code").toString().substring(0, $(con).data("code").toString().length) == $(con).data("code").toString()) {
                    $(this).remove();
                }
            })
            $(con).parent('li').siblings('li').find("a").removeClass("d-item-active");

            if ($(con).attr("class").indexOf("d-item-active") == -1) {
                $(con).parent('li').nextAll("li").find('a').css({ 'color': '#ccc', 'cursor': 'not-allowed' })
                $(con).parent('li').nextAll("li").find('a').attr("onclick", "");
            } else {
                $(con).parent('li').nextAll("li").find('a').css({ 'color': '#0077b3', 'a.d-item-active:hover': '#fff', 'cursor': 'pointer' })
                $(con).parent('li').nextAll("li").find('a').attr("onclick", 'selectProvince("sub",this,"")');
            }
            if ($(con).attr("class").indexOf('d-item-active') > 0) {
                $('.data-result span[data-code="' + $(con).data("code") + '"]').remove();
                $(con).removeClass('d-item-active');
                return false;
            }
        	$('.data-result').append('<span class="save_box aui-titlespan" data-code="' + $(con).data("code") + '" data-name="' + $(con).data("name") + '">' + $(con).data("name") + '<i onclick="removespan_area(this)">×</i></span>');                		
            $(con).addClass('d-item-active');
        }
    } else {
        //单选
        $('.data-result span').remove();
    	$('.data-result').append('<span class="save_box aui-titlespan" data-code="' + $(con).data("code") + '" data-name="' + $(con).data("name") + '">' + $(con).data("name") + '<i onclick="removespan_area(this)">×</i></span>');                		
        $(con).parent('li').siblings('li').find('a').removeClass('d-item-active');
        $(con).addClass('d-item-active');
    }
}

function removenode_area(lithis) {
    $(lithis).siblings().removeClass('active');
    $(lithis).addClass('active');
    if ($(lithis).nextAll('li').length == 0) {
        return false;
    }
    $(lithis).nextAll('li').remove();
    if ($(lithis).data("selector") == "tab-all") {
        selectProvince('all', null, '');
    } else {
        selectProvince('sub', lithis, 'remove');
    }
}

function removespan_area(spanthis) {
	spanthis = spanthis.parentElement;
    $('a[data-code=' + $(spanthis).data("code") + ']').removeClass('d-item-active');
    if ($('a[data-code=' + $(spanthis).data("code") + ']').length > 0) {
        if ($('a[data-code=' + $(spanthis).data("code") + ']').attr("class").indexOf('data-all') > 0) {
            $('a[data-code=' + $(spanthis).data("code") + ']').parent('li').nextAll('li').find('a').css({ 'color': '#0077b3', 'a.d-item-active:hover': '#fff', 'cursor': 'pointer' });
            $('a[data-code=' + $(spanthis).data("code") + ']').parent('li').nextAll("li").find('a').attr("onclick", 'selectProvince("sub",this,"")');
        }
    }
    if ($('.data-list-provinces a[data-code=' + $(spanthis).data("code").toString().substring(0, 6) + ']').length > 0) {
        var numlabel = $('.data-list-provinces a[data-code=' + $(spanthis).data("code").toString().substring(0, 6) + ']').find('label').text();
        if (parseInt(numlabel) == 1) {
            $('.data-list-provinces a[data-code=' + $(spanthis).data("code").toString().substring(0, 6) + ']').find('label').text(0).hide();
        } else {
            $('.data-list-provinces a[data-code=' + $(spanthis).data("code").toString().substring(0, 6) + ']').find('label').text(parseInt(numlabel) - 1);
        }
    }
    $(spanthis).remove();
}

//确定选择
function save_City() {
    var districtMode = $("#districtMode").val();
    var val = '';
    var Cityname = '';
    if ($('.save_box').length > 0) {
        $('.save_box').each(function () {
            Cityname += $(this).data("name") + '-';
                val += $(this).data("code") + '-';
        });
    }
    if (val != '') {
        val = val.substring(0, val.lastIndexOf('-'));
    }
    if (Cityname != '') {
        Cityname = Cityname.substring(0, Cityname.lastIndexOf('-'));
    }
    console.log(val);
    console.log(Cityname);
    $(dataCityinput).data("value", val);
    $("#selArea").val(val);
    if(districtMode == "setComboDis"){
    	var flag = $("#isFlag").val();
    	if(flag == "0"){
    		$("#wgarea").val(Cityname);
    	}else{
    		$("#ygarea").val(Cityname);
    	}
    }
    $(dataCityinput).val(Cityname);
    $("#area").val(Cityname);

    Close();
    if(districtMode == "setDis"){
    	var operatorsId = $("#id").val();
    	getDistrict(val,operatorsId);
    }
    
    if(districtMode == "selNetDis"){
    	var operatorsId = $("#operatorsId").val();
    	getDistrict(val,operatorsId);
    }
    
    if(districtMode == "setComboDis"){
    	getOperatorsByDis(val);
    	var comboId = $("#comboId").val();
        var isFlag = $("#isFlag").val();
    	if(comboId != null && comboId != "" && isFlag =="1"){
    		get_selectedChannel(comboId);    
    	}
    }
   
}
    function getDistrict(districts,operatorsId){
    	var data = {districts:districts,operatorsId:operatorsId};
    	var postData = JSON.stringify(data);
    	$.ajax({
    		url :accipiter.getRootPath() + "/adv/districtCategory/getOperatorDistrict",
    		type :"post",
    		async:false,
    		data :postData,
    		contentType : "application/json; charset=utf-8",
    		dataType : "json",
    		success:function(data){
    			if(data != null){
    				var adDistrictCategorys = data.adDistrictCategorys;
    				appendSelfArea(adDistrictCategorys);
    			}			
    		}
    	});
    }
    
    function getOperatorsByDis(districts){
    	$(".networkContent").html("");
    	var data = {districts:districts};
    	var postData = JSON.stringify(data);
    	var flag = $("#isFlag").val();
    	$.ajax({
    		url :accipiter.getRootPath() + "/adv/districtCategory/getOperatorsByDis",
    		type :"post",
    		async:false,
    		data :postData,
    		contentType : "application/json; charset=utf-8",
    		dataType : "json",
    		success:function(data){
    			$("#selDataResultWG").html("");
    			$("#selDataResultYG").html("");
    			
    			var html = "";
    			if(data != null){
    				var selArea = $("#selArea").val();
    				var ops = $("#ops").val();
    				var opsAr = ops.split("-");
    				var comboId = $("#id").val();
    				$.each(data,function(commentIndex,comment){	
    					html += '<span class="sel_save_box aui-titlespan" data-code="'+comment["operatorsId"]+'" data-name="'+comment["operatorsName"]+'">';
    					if(comboId != null && comboId != "" && $.inArray(comment["operatorsId"], opsAr) == -1){
    						html += '<input name="0" class="selOpt"';    
    					}else{
    						html += '<input name="1" class="selOptCheck"';    						    						
    					}
    					html +=	'style="border: 0;width: 16px; height: 16px;margin: 5px;" type="button" onclick="changeOpt(this)" id="'+comment["operatorsId"]+'">'+comment["operatorsName"]+'</span>';				
    					if(comboId != null && comboId != "" && ($.inArray(comment["operatorsId"], opsAr) == -1)){
    							console.log(1);		
    							if(flag==1){
        							getChannelByDis(selArea,comment["operatorsId"],comment["operatorsName"]);    	    							
        						}
    					}else{
    						if(flag==1){
    							getChannelByDis(selArea,comment["operatorsId"],comment["operatorsName"]);    	    							
    						}else{
    							getNetworkByDis(selArea,comment["operatorsId"],comment["operatorsName"]);    	    							    							
    						}
    					}
    				});
    				if(flag ==1){
    					$("#selDataResultYG").append(html); 
        				$("#selDisResultYG").css("display","block"); 
        				$("#selDisResultWG").css("display","none");
        			}else{
        				$("#selDataResultWG").append(html); 
        				$("#selDisResultWG").css("display","block");   
        				$("#selDisResultYG").css("display","none");
        			}
    			}else{
    				html +='<span>'+accipiter.getLang_(messageLang,"no.operatirs")+'</span>';
    				if(flag ==1){
    					$("#selDataResultYG").append(html); 
        				$("#selDisResultYG").css("display","block"); 
        				$("#selDisResultWG").css("display","none");
        			}else{
        				$("#selDataResultWG").append(html); 
        				$("#selDisResultWG").css("display","block");   
        				$("#selDisResultYG").css("display","none");
        			}
    			}			
    		}
    	});
    }

function appendSelfArea(adDistrictCategorys){
		var html = "";
		$.each(adDistrictCategorys,function(commentIndex,comment){	
			$("#selDataResult").html("");
			var districtMode = $("#districtMode").val();
			if(districtMode == "setDis"){
				html += '<span class="sel_save_box aui-titlespan" data-code="'+comment["categoryId"]+'" data-name="'+comment["categoryName"]+'">'+comment["categoryName"]+'<input value="'+comment["selfCategoryId"]+'" class="selInp"></span>';				
			}else if(districtMode == "selNetDis"){
				html += '<span class="sel_save_box aui-titlespan" data-code="'+comment["categoryId"]+'" data-name="'+comment["categoryName"]+'">'+comment["categoryName"]+'<input value="'+comment["selfCategoryId"]+'" class="selInp" readonly="readonly"></span>';				
			}
		});
		$("#selDataResult").append(html); 
		$("#selDisResult").css("display","block");
}

function  checkComboIsConflict(psData) {
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
                   console.log(0);
                }else if(count == -1){
                    $("#oldcomboId").val("");
                    getNCLimit(psData);
                }else{
                    $("#oldcomboId").val(data.id);
                    getNCLimit(psData);
                }
            }
        }
    });
}

function getNCLimit(psData){
    $.ajax({
        type:"post",
        async: false,
        url:accipiter.getRootPath()+"/adv/adelement/getNCLimit",
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

function changeOpt(opthis){
	var operatorsId = $(opthis).attr("id");
	var namev =  $(opthis).attr("name");
	var flag = $("#isFlag").val();
	if(flag == "0"){
		if(namev == "1"){
			$(opthis).attr("name","0");
			$(opthis).removeClass("selOptCheck");
			$(opthis).addClass("selOpt");
			var dv = "dv_"+operatorsId;
			$("#"+dv).remove();
		}else{
			$(opthis).attr("name","1");
			$(opthis).removeClass("selOpt");
			$(opthis).addClass("selOptCheck");
			var selArea = $("#selArea").val();
			var netName = $(opthis).parent().text();
			getNetworkByDis(selArea,operatorsId,netName);
		}
	}else{
		var fsq = "fsq_"+operatorsId;
		$("#"+fsq).remove();
		if(namev == "1"){
			$(opthis).attr("name","0");
			$(opthis).removeClass("selOptCheck");
			$(opthis).addClass("selOpt");
			var fsq = "fsq_"+operatorsId;
			$("#"+fsq).remove();
			$('.setNet').val("");
	        $('#channelIds').val("");
		}else{
			$(opthis).attr("name","1");
			$(opthis).removeClass("selOpt");
			$(opthis).addClass("selOptCheck");
			var selArea = $("#selArea").val();
			var netName = $(opthis).parent().text();
			getChannelByDis(selArea,operatorsId,netName);
			var comboId = $("#comboId").val();
	    	if(comboId != null && comboId != ""){
	    		get_selectedChannel(comboId);    
	    	}
		}
	}
}

function getNetworkByDis(districts,operatorsId,netName){
    var netWorkType = $("#netWorkType").val();
    var typeId=$("#typeId").find("option:selected").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var isFlag = $("#isFlag").val();
    var sendMode = "";
    if(isFlag == "0"){
       sendMode = $("#sendModeWG").val();
    }else{
   	   sendMode = $("#sendModeYG").val();
    }
    var advertiserId = $("#advertiser_id").val();
    var chlidType = $("#adcombo_chlidType").val();
    var data = {districts:districts,operatorsId:operatorsId,"typeId":typeId,"chlidType":chlidType,"startDate":startDate,"endDate":endDate,"sendMode":sendMode,"advertiserId":advertiserId};
	var postData = JSON.stringify(data);

	$.ajax({
		url :accipiter.getRootPath() + "/adv/districtCategory/getNetworkByDis",
		type :"post",
		async:false,
		data :postData,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success:function(data){
			var html = "";
			var netIds = $("#netIds").val();
			var netArr = netIds.split("-");
			var comboId = $("#id").val();
			if(data != null){
				html += '<div id="dv_'+operatorsId+'"><input name="0" type="button" class="network_type noselected" id="nt_'+operatorsId+'" style="width: 16px; height: 16px;background:url(../../static/images/icon/ic_checkbox_ture.png);"><label for="nt_'+operatorsId+'" style="margin-top: 10px;margin-left: 10px;">'+netName+'</label><ul class="network_content_ul">';
				$.each(data,function(commentIndex,comment){
					if(comboId != null && comboId != "" && $.inArray(comment["id"],netArr) != -1){
          			     html+='<li id='+comment["id"]+'><input type="button" class="option noselected" name="1" style="background:url(../../static/images/icon/ic_checkbox_ture.png);" id='+commentIndex+'><label for='+commentIndex+' title='+comment["networkName"]+'('+comment["area"]+')>'+comment["networkName"]+'('+comment["area"]+')</label>';
					}else{
						if(comment["invalid"]){
							html+='<li id='+comment["id"]+'><input type="button" class="option noselected" name="1" id='+commentIndex+' style="background:url(../../static/images/icon/ic_checkbox_ture.png);"><label for='+commentIndex+' title='+comment["networkName"]+'('+comment["area"]+')>'+comment["networkName"]+'('+comment["area"]+')</label>';
						}else{
							html+='<li id='+comment["id"]+'><input type="button" class="option noselected" name="2" disabled=true id='+commentIndex+'><label for='+commentIndex+' title='+comment["networkName"]+'('+comment["area"]+')>'+comment["networkName"]+'('+comment["area"]+')</label>';
						}
					}
					});
				html += '</ul></div>';
				$(".networkContent").append(html);
				$(".networkContent").css("display","block");

                if(netWorkType == "quick") {
                    var option = $('.networkContent .option[name="1"]');
                    var len = option.length;
                    var networkIds = "";
                    for (var i = 0; i < len; i++) {
                        var data = option[i].parentNode.getAttribute("id");
                        networkIds += data + ",";
                    }
                    if (null == networkIds || networkIds == "") {
                        return false;
                    }
                    networkIds = networkIds.substring(0,networkIds.lastIndexOf(","));
                    var post_data = {
                        "networkIds": networkIds,
                        "typeId": typeId,
                        "chlidType":chlidType,
                        "startDate": startDate,
                        "endDate": endDate,
                        "sendMode": sendMode,
                        "districts":districts
                    };
                    var psData = JSON.stringify(post_data);
                    checkComboIsConflict(psData);
                }
			}else{
                html += '<div id="dv_'+operatorsId+'"><input name="0" type="button" class="network_type noselected" id="nt_'+operatorsId+'" style="width: 16px; height: 16px;background:url(../../static/images/icon/noSelected.png);"><label for="nt_'+operatorsId+'" style="margin-top: 10px;margin-left: 10px;">'+netName+'</label>';
                html +='<span style="color:red;margin-left: 30px;">'+accipiter.getLang_(messageLang,"no.networks")+'</span><ul class="network_content_ul"></ul></div>';
                var dv = "dv_"+operatorsId;
                $("#"+dv).remove();
                $(".networkContent").append(html);
                $(".networkContent").css("display","block");
            }
		}
	});
}

function getChannelByDis(districts,operatorsId,netName){
    var dataArr = [];
	var fsq = "fsq_"+operatorsId;
	$("#"+fsq).remove();
	var typeId=$("#adTypeSelect").find("option:selected").val();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var isFlag = $("#isFlag").val();
    var sendMode = "";
    if(isFlag == "0"){
       sendMode = $("#sendModeWG").val();
    }else{
   	   sendMode = $("#sendModeYG").val();
    }
	var data = {districts:districts,operatorsId:operatorsId,"typeId":typeId,"startDate":startDate,"endDate":endDate,"sendMode":sendMode};
	var postData = JSON.stringify(data);
	$.ajax({
		url :accipiter.getRootPath() + "/adv/districtCategory/getNetworkChannelByDis",
		type :"post",
		async:false,
		data :postData,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success:function(data){
			var html = "";
			if(data != null){
                var operatorsName = data.operatorsName;
                var operatorsId = data.operatorsId;
                html+='<li class="fasongqi_type" id="fsq_'+operatorsId+'"><input class="fasongqi_type_btn" type="button" name="0"><label>'+operatorsName+'</label>';
                  $.each(data.networkList,function(commentIndex,comment){
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
                   var channelIds = $("#channelIds").val();

                      var post_data2={"networkId":comment["networkId"],"typeId":typeId,startTime:startTime,endTime:endTime,startDate:startDate,endDate:endDate,sendMode:sendMode,channelIds:channelIds};
                   var str2 = JSON.stringify(post_data2);
                   var t_str=comment["networkId"];
            	   $.ajax({
                      type:"post",
                      async: false,
                      url:accipiter.getRootPath()+"/adv/combo/find_channel",
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
                                   var temp = {};
                                   temp.id = comment["channelId"];
                                   temp.code = comment["adchannelId"];
                                   temp.name = comment["channelName"];
                                   dataArr.push(temp);
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
                channelSearchValue = dataArr;
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
               $('.channel_content').css("display","block");
			}
		}
	});
    auto_channel.run();
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
         url:accipiter.getRootPath()+"/adv/combo/find_networkby_comboId",
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

function getPlayTime(){
	 var startDate=$("#validStartTime").val();
	 var endDate=$("#validEndTime").val();
	 var startTime=$("#startTime").val();
	 var endTime=$("#endTime").val();
	 return {startDate:startDate,endDate:endDate,startTime:startTime,endTime:endTime};
}

function Close() {
    $('.aui_state_box').remove();
}

function searchdata() {
    var list    = __LocalDataCities.list;
    var dataArr = [];
    for (var i in list) {
        /*if (i.length == 3 && i != '010' && i != '020' && i != '030' && i != '040') {
            continue;
        }
        if (i.length > 6 || i == 'hwgat') {
            continue;
        }
        if (parseInt(i.toString().substring(0, 2)) >= 32) {
            continue;
        }*/
        var temp = {};
        temp.code   = i;
        temp.name   = list[i][0];
        temp.pinyin = list[i][1];
        //temp.py     = list[i][2];
        temp.selfcode     = list[i][2];
        dataArr.push(temp);
    }
    return dataArr;
}

$(function(){
	var districtMode = $("#districtMode").val();
	var netWorkType = $("#netWorkType").val();
    var isFlag = $("#isFlag").val();
	if(districtMode == "setComboDis"){
		var dv = $("#selArea").val();
    	getOperatorsByDis(dv);
    	var comboId = $("#comboId").val();
    	if(comboId != null && comboId != "" && isFlag == "1"){
    		get_selectedChannel(comboId);
    		$(".channel_content").css("display","none");
    	}
    	if(netWorkType == "quick" ){
			setQuickSelectedChannel();
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
		}
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

    //频道设置
    $('.setNet').click(function(){
        var netWorkType = $("#netWorkType").val();
        var districtMode = $("#districtMode").val();
        var isFlag = $("#isFlag").val();
        var sendMode = "";
        if(isFlag == "0"){
            sendMode = $("#sendModeWG").val();
        }else{
            sendMode = $("#sendModeYG").val();
        }
        if(sendMode == 2 && districtMode == "setComboDis"){
            var status=$("#status").find("option:selected").val();
            if(status==2){
                $('.channel_content').css("display","block");
            }else{
                var dv = $("#selArea").val();
                getOperatorsByDis(dv);
                setQuickSelectedChannel();
            }
        }
    });
});