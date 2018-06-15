/*
 * typeId对应数据库中广告类型id字段，如果id字段发生变化，修改 对应数据的typeId；id对应系统中用到的全局变量，修改需谨慎。
 * */
var advType=[
    {"typeId":"1","typeName":"开机画面广告","id":"1"},
    {"typeId":"2","typeName":"挂角广告","id":"2"},
    {"typeId":"3","typeName":"换台广告","id":"3"},
    {"typeId":"4","typeName":"插屏广告","id":"4"},
    {"typeId":"5","typeName":"滚动广告","id":"5"},
    {"typeId":"6","typeName":"开机视频","id":"6"},
    {"typeId":"7","typeName":"提示窗广告","id":"7"},
    {"typeId":"8","typeName":"菜单图片广告","id":"8"},
    {"typeId":"9","typeName":"广播背景图片广告","id":"9"},
    {"typeId":"10","typeName":"长条广告","id":"10"},
    {"typeId":"7-0","typeName":"无信号","id":"7-0"},
    {"typeId":"7-1","typeName":"无频道","id":"7-1"},
    {"typeId":"7-2","typeName":"无授权","id":"7-2"},
    {"typeId":"7-3","typeName":"其他类型","id":"7-3"},
    {"typeId":"8-0","typeName":"主菜单","id":"8-0"},
    {"typeId":"8-1","typeName":"音量条","id":"8-1"},
    {"typeId":"8-2","typeName":"频道列表","id":"8-2"},
    {"typeId":"8-3","typeName":"EPG列表","id":"8-3"}
];
String.prototype.getAdvType = function(){
    var typeId = this;
    var len=advType.length;
    for(var i=0;i<len;i++){
        if(advType[i].typeId==typeId){
            return advType[i].id;
        }
    }
};
