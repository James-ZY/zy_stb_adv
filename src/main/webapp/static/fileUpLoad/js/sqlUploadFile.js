/**
 * Created by XiaWei on 2016/9/30 0030.
 *
 */
(function($){
    var host = accipiter.getRootPath();
    $.fn.FileImport=function(options){
        var options= $.extend({},$.fn.FileImport.defaults,options);
        return $(this).each(function(){
            var $FileImport=$(this);                                              //获取容器
            var $Import=$(this).find(".filePreview");                             //获取上传容器
            var innerTitle=options.title;                                         //插入标题
            var innerUrl=options.uploadUrl;                                       //插入文件上传路径
            var innerModelUrl=options.modelUrl;                                   //插入数据模板下载路径
            var innerHtml=$.fn.FileImport.addHtml(innerTitle,innerModelUrl); //插入动态元素结构
            $FileImport.html(innerHtml);                                          //DOM结构插入
            /*关闭弹框事件监听*/
            $FileImport.on("click",".closeBtn",function(){
            	$FileImport.attr("name","0");
                $FileImport.html("");
            });
            $.fn.FileImport.fileUpLoad($(".filePreview"),innerUrl);
        });
    };
    //动态插入DOM结构
    $.fn.FileImport.addHtml=function(title,Modelurl){
        var html='<div class="FileImport-Bg"><form id="FileImportForm" ></div>' +
            '<div class="FileImport-Content">' +
            '<div class="FileImport-Title"><p>'+title+'</p><a class="closeBtn"></a></div>' +
            '<div class="FileImport-Comment">';
        html+='<div class="uploadFile">' +
            '<P>'+accipiter.getLang_(messageLang,"import.preview")+'：</P>' +
            '<div class="filePreview dropzoneStyle" style="background-color: rgba(0, 0, 0, 0.02);"></div>' +
            '</div>' +
            '<div class="uploadModel">' +
/*
            '<P><a href='+Modelurl+'>'+accipiter.getLang_(messageLang,"import.model")+'</P></a>' +
*/
            '</div>' +
            '<div class="alert-info"><p><b>'+accipiter.getLang_(messageLang,"import.attention")+':</b>&nbsp;'+accipiter.getLang_(messageLang,"import.sqllimit")+'</p></div>';
             html+='</div><div class="FileImport-footBtn">' +
            '<input type="submit" value='+accipiter.getLang_(messageLang,"import.confirm")+' class="submitBtn" id="ImportSubmit">' +
            '<input type="button" value='+accipiter.getLang_(messageLang,"import.close")+' class="closeBtn">' +
            '</div></form></div>';
        return html;
    };
    //数据上传
    $.fn.FileImport.fileUpLoad=function(e,uploadUrl){
        /*文件上传*/
        Dropzone.autoDiscover = false;
        e.dropzone({
            url:uploadUrl,
            maxFiles: 1,
            maxFilesize: 5,
            acceptedFiles: ".sql",
            dictDefaultMessage : "1",
            dictRemoveFile :accipiter.getLang_(messageLang,"import.remove"),
            dictFileTooBig:accipiter.getLang_(messageLang,"file.too.big"),
            dictMaxFilesExceeded:accipiter.getLang_(messageLang,"image.maxsize"),
            dictResponseError: accipiter.getLang_(messageLang,"image.upload.fail"),
            dictCancelUpload: accipiter.getLang_(messageLang,"cancle.upload"),
            dictInvalidFileType:accipiter.getLang_(messageLang,"support.filetype"),
            addRemoveLinks:true,
            autoProcessQueue: false,
            /* uploadMultiple:true,*/
            parallelUploads:100,
            init: function() {
            	var fileDropzone=this;
                this.on("thumbnail",function(file){
                    e.find(".dz-details").find("img").attr("src",host+"/static/fileUpLoad/fileUpLoadcss/images/sqlicon.png");
                });
                this.on("addedfile", function (file){
                    e.find(".dz-details").find("img").attr("src",host+"/static/fileUpLoad/fileUpLoadcss/images/sqlicon.png");
                });
                this.on("success", function(file,data) {
                    e.find(".dz-details").find("img").attr("src",host+"/static/fileUpLoad/fileUpLoadcss/images/sqlicon.png");
/*
                    var messageinfo=$('<span>'+data+'</span>').find("#messageBox").html().toString().split("</button>")[1];
*/
                    $("#uploadMessage").val(data);
                    $("#searchForm").submit();
                });
                $("#ImportSubmit").on("click",function(){
                	var total=$(".filePreview .dz-preview ").length;
                	if(total>0){
                       	fileDropzone.processQueue();
                       	loading(accipiter.getLang('loading'));
                	}else{
                		return false;
                	}
                });
            }
        });
    };
    //默认数据
    $.fn.FileImport.defaults={
        title:accipiter.getLang_(messageLang,"import.data"),
        uploadUrl:"",
        modelUrl:""
    };

})(jQuery);
