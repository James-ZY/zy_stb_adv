var auto_channel = {
    run: function () {// 运行应用
        var run = $('.data-search input[name=searchchannel]'), runList = $('.searchList'), ac_menu = $('.searchList .channel_menu');
        var def_text = accipiter.getLang_(messageLang,"search");
        run.val(def_text);
        run.focus(function () {
            if (this.value == def_text) this.value = '';
        }).blur(function () {
            if (this.value == '') this.value = def_text;
            auto_channel.delay(function () { runList.hide() }, 300);//延时，等待选择事件执行完成
        }).bind('keyup', function () {
            auto_channel.appRunList(runList, run.val());
        }).keydown(function (e) {
            if (e.keyCode == 13) setTimeout(auto_channel.appRunExec, 200);
        });
    },
    delay: function (f, t) {
        { if (typeof f != "function") return; var o = setTimeout(f, t); this.clear = function () { clearTimeout(o) } }
    },
    appRunList: function (runList, v) {//自动搜索应用
        if (v == '') {
            runList.hide();
            return;
        }
        var i, temp = '', n = 0, loaded = {};
        //搜索以关键词开头的应用
        for (i in channelSearchValue) {
            if (isNaN(i) || loaded[i] || !channelSearchValue[i].name) {
                continue;
            }
            runSearchID = channelSearchValue[i].id;
            runSearchCode = channelSearchValue[i].code;
            runSearchName = channelSearchValue[i].name;
            //if (runSearchName.indexOf(v) >= 0 || runSearchPinyin.indexOf(v) >= 0 || runSearchPy.indexOf(v) >= 0 || runSearchPinyin.toLowerCase().indexOf(v) >= 0 || runSearchPy.toLowerCase().indexOf(v) >= 0) {
            if (runSearchCode.indexOf(v.toLowerCase()) >= 0 || runSearchName.indexOf(v.toLowerCase()) >= 0 || runSearchName.toLowerCase().indexOf(v.toLowerCase()) >= 0 ) {
                loaded[i] = 1;
                temp += '<a href="#c_' + runSearchID+'" data-ID="' + runSearchID + '" class="channel_menu" onclick="changeColor(this)"><em>' + runSearchName.replace(v, "<b>" + v + "</b>") + '</em>' + runSearchCode.replace(v, "<b>" + v + "</b>") + '</a>';
                if (++n > 10) break;
            }
        }
        if (temp) {// 搜索到应用则显示
            runList.show().html(temp);
        } else {
            runList.hide().html('');
        }
    },

    appRunExec: function () {// 运行按纽点击
        ac_menu = $('.searchList .channel_menu');
        if (ac_menu.length > 0) {
            ac_menu.eq(0).trigger('click');
        }
    },
};

function changeColor(ts) {
    var code = $(ts).data("id");
    $(".channel_type_list").find("li[name='changeColor']").each(function () {
         $(this).attr("name","");
         $(this).css("background-color","");
    });
    $("#c_"+code).parent().attr("name","changeColor");
    $("#c_"+code).parent().css("background-color","cornflowerblue");
}