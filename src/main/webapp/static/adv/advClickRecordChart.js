/**
 *
 * Created by zhaohw on 2018/1/30.
 */
var option ={
    title1:$.i18n.prop('stats.advClickType'),
    title2:$.i18n.prop('stats.advClick'),
    sub_title1:$.i18n.prop('stats.onlyIncludeScan'),
    sub_title2:$.i18n.prop('stats.onlyIncludeScan'),
    tips1:$.i18n.prop('stats.advType'),
    tips2:$.i18n.prop('stats.advName'),
    error:$.i18n.prop('stats.noData'),
    detail_layer:$.i18n.prop('stats.advDetailInfo')
};

function parseData(data,index){

    //处理按类型统计列表
    if($("#type_box").length<=0){
        $("#box1").append('<div id=\"type_box\" style=\"width: 100%;height:100%\"></div>');
    }
    var types = new Array();
    var datas = new Array();
    var serise = new Array();
    for(var i =0;i < data.typeList.length ;i++){
        var obj = new Object();
        types.push(data.typeList[i].advTypeName);
        obj.name=data.typeList[i].advTypeName;
        obj.value= data.typeList[i].count;
        obj.id = data.typeList[i].advTypeId;
        datas.push(obj);
    }
    bar_series_item.data = datas;
    serise.push(bar_series_item);
    if(data.typeList.length>0){
        addBarChart(option.title1,option.sub_title1,types,types,serise,option.tips1,"","type_box",getTypeDetail);
    }else{
        $("#box1").html('<a class = "error_code">'+option.error+"</a>");
    }


    //各类型广告统计
    for(var i =0;i < data.elementList.length ;i++){
        //append container
        if($("#child"+i).length<=0){
            $("#box2").append('<div id=\"child'+i+'\" style=\"width: 400px;height:290px;\"></div>');
        }
        var child_types = new Array();
        var child_datas = new Array();
        for(var j=0;j<data.elementList[i].list.length;j++){
            child_types.push(data.elementList[i].list[j].advName);
            var child_obj = new Object();
            child_obj.name=data.elementList[i].list[j].advName;
            child_obj.value= data.elementList[i].list[j].count;
            child_obj.id = data.elementList[i].list[j].advId;
            child_datas.push(child_obj);
        }
        addPieChart(data.elementList[i].type+option.title2,option.sub_title2,child_types,option.tips2,child_datas,"child"+i,getElementDetail);
    }
    if(data.elementList.length==0){
        $("#box2").html('<a class = "error_code">'+option.error+"</a>");
    }
    setTimeout(function () {
        layer.close(index);
        console.log("loading close");
    },100);

}


/**
 * 广告统计点击事件
 * @param param
 */
function getTypeDetail(param) {
}
