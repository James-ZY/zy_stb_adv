
var advType=[
    {"typeId":"1","typeName":"开机画面广告","id":"1"},
    {"typeId":"2","typeName":"挂角广告","id":"2"},
    {"typeId":"3","typeName":"换台广告","id":"3"},
    {"typeId":"4","typeName":"插屏广告","id":"4"},
    {"typeId":"5","typeName":"滚动广告","id":"5"},
    {"typeId":"6","typeName":"开机视频","id":"6"},
    {"typeId":"7","typeName":"提示窗广告","id":"7"},
    {"typeId":"8","typeName":"菜单图片广告","id":"8"},
    {"typeId":"9","typeName":"广播背景图片广告","id":"9"}
];
String.prototype.getAdvType = function(){
    var typeId = this;
    var len=advType.length;
    for(var i=0;i<len;i++){
        if(advType[i].typeId==typeId){
            return advType[i].id
        }
    }
};
