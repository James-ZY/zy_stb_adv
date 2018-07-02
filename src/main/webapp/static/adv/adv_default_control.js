/**
 * Created by XiaWei on 2016/7/26 0026.
 */
/*
 * 16进制颜色转为RGB格式
 * */
//十六进制颜色值的正则表达式
var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
String.prototype.colorRgb = function(){
    var sColor = this.toLowerCase();
    if(sColor && reg.test(sColor)){
        if(sColor.length === 4){
            var sColorNew = "#";
            for(var i=1; i<4; i+=1){
                sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));
            }
            sColor = sColorNew;
        }
        //处理六位的颜色值
        var sColorChange = [];
        for(var i=1; i<7; i+=2){
            sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));
        }
        return sColorChange.join(",");
    }else{
        return sColor;
    }
};

$(function(){
	var host = accipiter.getRootPath();
	var control_show=0;/*------控制提交按钮，避免发生冲突----*/
	var DropzoneControl=0;/*------控制上传控件，避免发生多次触发----*/
	var adSize=[{"gjsize":"(220-250)*(96-180)","kjsize":"720*576","htsize":"(100-300)*(100-300)","cpsize":"(220-250)*(96-180)","gdsize":"(720-7200)*(36-40)","tssize":"(220-250)*(96-180)","menusize":"(220-250)*(96-180)","bgsize":"720*576"}];
	var sizeStyle=[];
	if($('#id').attr("value")!=""){
		var control=parseInt($('.select').children("option:selected").val().getAdvType());
		var info=$('.select').children("option:selected").text();
		$('.select').attr("disabled",true);
		$('#flag').attr("disabled",true);
		$('#adNetwork').attr("disabled",true);
		$('.selectPictureType').attr("disabled",true);
		var data={"id":$(".select").children("option:selected").val(),"flag":$("#flag").children("option:selected").val()};
		fileLimit(control,info,getSize(data));
	}
    $(".icon_control,.btn_submit,.btn_clear").on("click",function(){
    	$(".control_info").css("display","none");
    })
    
    /*-------图片上传和视频上传切换显示------*/
    /*-------去除重复格式------*/
    function unique(arr) {
	    var result = [], hash = {};
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	    }
	    return result;
    }
    function getSize(data){
    	var typeId = data.id;
		var postData = JSON.stringify(data);
		var sizeStyles=[];
		var fileSize,format,highMax,highMin,widthMax,widthMin,description="";
		$.ajax({
			url :host+"/adv/resource/find_resource_type",
			type :"post",
			async:false,
			data :postData,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success:function(data){
				if(data.length!=0){
					$.each(data,function(commentIndex,comment){
						fileSize=comment["fileMaxSize"];
						var formatC=[];
						$.each(comment["format"].split(","),function(commentIndex,comment){
							formatC.push("."+comment);
						});
						format=formatC.join(",") ;
						if(comment["status"]==0){
							if(typeId == "3"){
                                highMax=300;
                                highMin=100;
                                widthMax=300;
                                widthMin=100;
                                fileSize = 0.1;
							}else{
                                highMax=comment["highMax"];
                                highMin=comment["highMin"];
                                widthMax=comment["widthMax"];
                                widthMin=comment["widthMin"];
                            }
							if(comment["isFixed"]==0){
								description="("+widthMin+"-"+widthMax+")*("+highMin+"-"+highMax+")";
							}else{
								description=widthMin+"*"+highMin;
							}
							sizeStyles.push({"fileSize":fileSize,"format":format,"highMax":highMax,"highMin":highMin,"widthMax":widthMax,"widthMin":widthMin,"description":description});
						}else{
							sizeStyles.push({"fileSize":fileSize,"format":format});
						}
					})
				}else{
					sizeStyles=[];
					$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
				}						
			}
		})
		return sizeStyles;
    }
	/*对size数据进行限制*/
    function limitSizeData(size){
    	var sizeData=[];
    	var sizeDescription={"description":"","format":"","sizeRange":""}
    	if(size.length==1){
    		sizeData=size;
    		sizeDescription.format=size[0].format;
    		sizeDescription.sizeRange=size[0].description;
    		sizeDescription.description=sizeDescription.sizeRange+";"+accipiter.getLang_(messageLang,"parameter.format")+sizeDescription.format;
    	}else{
	    	if(size.length>1){
				var format1=size[0].format.split(",");
				var format2=size[1].format.split(",");
				sizeDescription.format=unique(format1.concat(format2)).join(",");
				sizeDescription.sizeRange=size[0].description+accipiter.getLang_(messageLang,"parameter.or")+size[1].description;
				sizeDescription.description=sizeDescription.sizeRange+";"+accipiter.getLang_(messageLang,"parameter.format")+sizeDescription.format;
				if(size.length<=2){
					sizeData=size;
				}else{
					for(var i=0;i<2;i++){
						sizeData.push(size[i]);
					}
				}
			}
	    	if(size.length==0){
	    		sizeData=[];
	    	}
    	}
    	return {"sizeDescription":sizeDescription,"sizeData":sizeData}
    }
	/*剔除gif格式*/
    function eliminateGIF(size){
    	var size=limitSizeData(size);
    	var formatdata=size.sizeDescription.format.split(",");
    	var newFormatdata=[];
    	var newFormat="";
    	for(var i=0;i<formatdata.length;i++){
    		if(formatdata[i]!=".gif"){
    			newFormatdata.push(formatdata[i]);
    		}
    	}
    	newFormat=newFormatdata.join(",");
    	return newFormat;
    }
    $("#flag").change(function(){
		var control=$(this).children("option:selected").val();
		var typeAdv=$(".select").children("option:selected").val();
		var info=$(".select").children("option:selected").text();
		if(control!=""&&typeAdv!=""){
			var data={"id":typeAdv,"flag":control};
			var advcontrol= parseInt($(".select").children("option:selected").val().getAdvType());
			fileLimit(advcontrol,info,getSize(data));
		}else{
			
		}
		 $("#id").val("");
		var flag = $(this).val();
		var adTypeId = $("#adType").val();
		var adNetworkId = $("#adNetwork").val();
		showPicture(flag,adTypeId,adNetworkId);
	});
	$(".select").change(function(){
		if($("#flag").children("option:selected").val()!=""){
			var control= parseInt($(this).children("option:selected").val().getAdvType());
			var info=$(this).children("option:selected").text();
			var data={"id":$(this).children("option:selected").val(),"flag":$("#flag").children("option:selected").val()};
			fileLimit(control,info,getSize(data));
		}else{
			$(this).children('option').attr("selected",false);
			$(this).children('option:eq(0)').attr("selected",true);
			$(this).select2();
		}
	});
	$("#adType").change(function(){
		 $("#id").val("");
		var adTypeId = $(this).val();
		var flag = $("#flag").val();
		var adNetworkId = $("#adNetwork").val();
		showPicture(flag,adTypeId,adNetworkId);
	});
    $("#adNetwork").change(function(){
    	 $("#id").val("");
    	var adNetworkId = $(this).val();
    	var adTypeId = $("#adType").val();
		var flag = $("#flag").val();
		showPicture(flag,adTypeId,adNetworkId);
    });
    function showPicture(flag,adTypeId,adNetworkId){
    	$("#path").val("");
		$("#fileSize").val("");
		$("#vedioImagePath").val("");
    	if((flag!=null && flag!= "") && (adTypeId!= null && adTypeId!="") && (adNetworkId != null  && adNetworkId !="")){
            var post_data2={"flag":flag,"adTypeId":adTypeId,adNetworkId:adNetworkId};
            var str2 = JSON.stringify(post_data2);
            $.ajax({
                type:"post",
                async: false,
                url:host+"/adv/defaultControl/checkIsRepeat",
                data:str2,
                contentType:"application/json; charset=UTF-8",
                dataType:"json",
                success:function(data){
                	if(data != null && data != ""){
                		$("#id").val(data.controllId);
                		$("#path").val(data.filePath+",");
                		$("#fileSize").val(data.fileSize);
                		$("#vedioImagePath").val(data.fileImagePath+",");; 
                	}else{
                		$("#id").val("");
                		$("#path").val("");
                		$("#fileSize").val("");
                		$("#vedioImagePath").val("");
                	}
                	var control=parseInt($('.select').children("option:selected").val().getAdvType());
                	var info=$('.select').children("option:selected").text();
                	var data={"id":$(".select").children("option:selected").val(),"flag":$("#flag").children("option:selected").val()};
                	fileLimit(control,info,getSize(data));                		
                }
            });
    	}
    }
    /*-------GIF与feiGIF切换*/
	$(".selectPictureType").change(function(){
		switchRollPictureClearData();
		var control= parseInt($(this).children("option:selected").val());
		if(control==0){
			control_show=0;
			$("#adv_image").css("display","block");
			$("#adv_kaijiimage").css("display","none");
			$("#createpicture").css("display","none");
			var control= parseInt($(".select").children("option:selected").val().getAdvType());
			var flag=$("#flag").children("option:selected").val()
			var info=$(".select").children("option:selected").text();
			if(control!=""&&flag!=""){
				var data={"id":$(".select").children("option:selected").val(),"flag":flag};
				fileLimit(control,info,getSize(data));	
			}
			return;
		}else{
			control_show=4;
			$("#adv_image").css("display","none");
			$("#adv_kaijiimage").css("display","none");
			clickSwitch($(".set-bg a"));//合成图片默认显示
			$("#createpicture").css("display","block");
			var control= parseInt($(".select").children("option:selected").val().getAdvType());
			var flag=$("#flag").children("option:selected").val()
			var info=$(".select").children("option:selected").text();
			if(control!=""&&flag!=""){
				var data={"id":$(".select").children("option:selected").val(),"flag":flag};
				var size=getSize(data);
				if(size.length==0){
					$("#createpicture .createContent").attr("disabled",true);
					$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
				}else{
					$("#createpicture .createContent").removeAttr("disabled");
					$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+limitSizeData(size).sizeDescription.sizeRange+";"+accipiter.getLang_(messageLang,"parameter.format")+eliminateGIF(size));
				}
			}
			return;
		}
	});
    function fileLimit(control,info,size){
    	var sizeData=limitSizeData(size);
		DropzoneControl++;
		if(control==6){
			control_show=1;
			$(".info-messages p").text(accipiter.getLang_(messageLang,"file.videoUpLoad"));
			$("#adv_image").css("display","none");
			$("#adv_kaijiimage").css("display","none");
			$("#adv_vedio").css("display","block");
			$("#createpicture").css("display","none");
			$(".pictureType").css("display","none");
			$("#resourceType").attr({"value":"2","readonly":"readonly"});
			var DropzoneId="upload_adv_vedio"+DropzoneControl;
			$("#adv_vedio").find(".controls").html("");
			$("#adv_vedio").find(".controls").html('<div class="dropzoneStyle upload_vedio" id='+DropzoneId+'></div>');	
			fileupload($("#"+DropzoneId),sizeData);
		}else{
		if(control==1){
			control_show=3;
			control_show=3;
			if(size.length==0){
				$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
			}else{
				$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
			}
			$("#adv_image").css("display","none");
			$("#adv_kaijiimage").css("display","block");
			$("#adv_vedio").css("display","none");
			$("#createpicture").css("display","none");
			$(".pictureType").css("display","none");
			$("#resourceType").attr({"value":"2","readonly":"readonly"});
			var DropzoneId="upload_adv_kaijiimage"+DropzoneControl;
			$("#adv_kaijiimage").find(".controls").html("");
			$("#adv_kaijiimage").find(".controls").html('<div class="dropzoneStyle upload_kaijiimage" id='+DropzoneId+'></div>');	
			fileupload($("#"+DropzoneId),sizeData);
		}else{
		control_show=0;
			$("#adv_vedio").css("display","none");
			$("#resourceType").attr({"value":"1","readonly":"readonly"});
			if(control==5){
				/*control_show=4;*/
				$(".pictureType").css("display","block");
				var rollText=$("#rollText").val();
				var id=$("#id").val();
				if(rollText!=undefined&&rollText!=""&&id!=""){
					control_show=4;
					$(".pictureType").find('option[value="1"]').attr("selected",true);
					$(".pictureType").find("select").select2();
					$("#adv_image").css("display","none");
					$("#adv_kaijiimage").css("display","none");
					clickSwitch($(".set-bg a"));//合成图片默认显示
					$("#createpicture").css("display","block");
					if(size.length==0){
						$("#createpicture .createContent").attr("disabled",true);
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$("#createpicture .createContent").removeAttr("disabled");
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.sizeRange+";"+accipiter.getLang_(messageLang,"parameter.format")+eliminateGIF(size));
					}
				}else{
					control_show=0;
					$(".pictureType").find('option').removeAttr("selected");
					$(".pictureType").find('option[value="0"]').attr("selected",true);
					$(".pictureType").find("select").select2();
					$("#adv_image").css("display","block");
					$("#adv_kaijiimage").css("display","none");
					$("#createpicture").css("display","none");
					var DropzoneId="upload_adv_image"+DropzoneControl;
					$("#adv_image").find(".controls").html("");
					$("#adv_image").find(".controls").html('<div class="dropzoneStyle upload_image" id='+DropzoneId+'></div>');
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					fileupload($("#"+DropzoneId),sizeData);
				}
				return;
			}else{
				$("#createpicture").css("display","none");
				$(".pictureType").css("display","none");
				$("#adv_image").css("display","block");
				$("#adv_kaijiimage").css("display","none");
				var DropzoneId="upload_adv_image"+DropzoneControl;
				$("#adv_image").find(".controls").html("");
				$("#adv_image").find(".controls").html('<div class="dropzoneStyle upload_image" id='+DropzoneId+'></div>');		
				if(control==2){
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					fileupload($("#"+DropzoneId),sizeData);
					return;
				}
				if(control==3){
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					fileupload($("#"+DropzoneId),sizeData);
					return;
				}
				if(control==4){
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					fileupload($("#"+DropzoneId),sizeData);
					return;
				}
				if(control==7){
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					fileupload($("#"+DropzoneId),sizeData);
					return;
				}
				if(control==8){
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					fileupload($("#"+DropzoneId),sizeData);
					return;
				}
				if(control==9){
					control_show=3;
					if(size.length==0){
						$(".info-messages p").text(accipiter.getLang_(messageLang,"file.parameter"));
					}else{
						$(".info-messages p").text(info+accipiter.getLang_(messageLang,"adv.size")+sizeData.sizeDescription.description);
					}
					$("#adv_image").css("display","none");
					$("#adv_kaijiimage").css("display","block");
					$("#adv_vedio").css("display","none");
					$("#resourceType").attr({"value":"2","readonly":"readonly"});
					DropzoneId="upload_adv_kaijiimage"+DropzoneControl;
					$("#adv_kaijiimage").find(".controls").html("");
					$("#adv_kaijiimage").find(".controls").html('<div class="dropzoneStyle upload_kaijiimage" id='+DropzoneId+'></div>');	
					fileupload($("#"+DropzoneId),sizeData);
					return;
				}else{
					$(".info-messages p").text('');
					return;
					}
			}
			}
		}
    }
    /*  文件上传*/
	function fileupload(e,size){
		if(size.sizeData.length!=0){
			Dropzone.autoDiscover = false;
			var imgData=[];
			var vedioUrlData=[];	
			var vedioSourceData=[];
			var vedioPreviewUrlData=[];
			if(control_show==0){
				/*------非开机广告图片上传-------------------*/
				e.html("");
			    e.dropzone({
			        url: host+"/file/fileUpload",
			        maxFiles: 1,
			        maxFilesize:size.sizeData[0].fileSize,
			        acceptedFiles:size.sizeDescription.format,
			        dictDefaultMessage : accipiter.getLang_(messageLang,"add.image"),
					dictRemoveFile :accipiter.getLang_(messageLang,"remove.image"),
			        dictFileTooBig:accipiter.getLang_(messageLang,"image.too.big"),
			        dictMaxFilesExceeded:accipiter.getLang_(messageLang,"image.maxsize"),
			        dictResponseError: accipiter.getLang_(messageLang,"image.upload.fail"),
			        dictCancelUpload: accipiter.getLang_(messageLang,"cancle.upload"),
			        addRemoveLinks:true,
			        autoProcessQueue: false,
			       /* uploadMultiple:true,*/
			        parallelUploads:100,
			        init: function() {
			      		var imgDropzone=this;
			      		var i_c=0;
			      		var i_c_style=0;
			      		var i_add=0;
			      		var i_post=1;
			      		var limit=0;
			      		var data_old="";
			      		var i_existing=0;
			      		var img_maxFiles=0;
			      		imgDropzone.options.dictDefaultMessage=accipiter.getLang_(messageLang,"add.image");
			      		var path=$("#path").attr("value");
			      		var filesize=$("#fileSize").attr("value");     		
			      		/*回显*/
			      		if(path!="null,"){
			      			var pathdata=path.split(",");
			      			var sizedata=filesize.split(",");
			      			var data=[];
			      			for(var i=0,len=pathdata.length;i<len-1;i++){
			      				var info={size:sizedata[i],url:pathdata[i]};
			      				data.push(info);
			      			}
			  				var existingFileCount = data.length;
			      			$.each(data,function(infoIndex,info){
			      				var mockFile = { name:info["name"], size:info["size"] };
			      				imgDropzone.emit("addedfile", mockFile);
			      				imgDropzone.emit("thumbnail", mockFile, info["url"]);
			      				e.find(".dz-preview").attr("class","dz-preview dz-image-preview dz-old dz-success");
			      			})
			      			i_existing=existingFileCount;
			      			img_maxFiles=imgDropzone.options.maxFiles - existingFileCount;
			      			imgDropzone.options.maxFiles = imgDropzone.options.maxFiles - existingFileCount;
			      		}
			     		this.on("thumbnail",function(file){
							var highMax=size.sizeData[0].highMax;
							var highMin=size.sizeData[0].highMin;
							var widthMax=size.sizeData[0].widthMax;
							var widthMin=size.sizeData[0].widthMin; 
			            	if(file.width<widthMin||file.width>widthMax||file.height<highMin||file.height>highMax){
			            		if(i_add<imgDropzone.options.maxFiles){
			                		e.find('.dz-preview:eq('+i_add+')').attr("class","dz-preview dz-image-preview dz-error");
			                		e.find('.dz-preview:eq('+i_add+')').find(".dz-error-message span").text(accipiter.getLang_(messageLang,"uploaderrInfo"));
			            		}
			            	}
			            	i_add++;
			      		});
			            this.on("success", function(file,data) {
			            	var imgDropzone=this;
			            	var control=data.split("`")[0];
			            	if(control=="0"){
			            		i_post=0;
			            		var errorinfo=data.substring(2);
			            		e.find('.dz-preview:eq('+i_c_style+')').attr("class","dz-preview dz-image-preview dz-error");
			            		e.find('.dz-preview:eq('+i_c_style+')').find(".dz-error-message").text(errorinfo);
			            	}
			            	if(control=="1"){
			                    imgData+=data.substring(2)+",";
			                    $("#adv_image").attr("name",imgData);
			                    e.find('.dz-preview:eq('+i_c_style+')').find("img").attr("src",data.substring(2));
			                    if(i_c==(limit-1)&&i_post==1&&limit!=0){
			                    	imgData=data_old+imgData;
			                        $("#path").attr("value",imgData);
				            		$("#btnSubmit").attr({"disabled":false});
			                       $("#inputForm").submit();
			                    } 
			                    i_c++;
			            	}
			            	if(control=="-1"){
			            		i_post=0;
			            		var errorinfo=data.substring(2);
			            		e.find('.dz-preview:eq('+i_c_style+')').attr("class","dz-preview dz-image-preview dz-error");
			            		e.find('.dz-preview:eq('+i_c_style+')').find(".dz-error-message").text(errorinfo);
			            	}
			        		i_c_style++;
			            });
			           this.on("removedfile",function(file){
			        	   var errorcount=e.find(".dz-error").length;
			        	   i_add=errorcount;
			        	   $('.remove_data').html(file.previewElement);
			        	   if($('.remove_data').find(".dz-preview").hasClass("dz-old")){
			              	 var change_old=$("#adv_image .dz-old").find("img").length;
			            	 if(change_old!=undefined){
			                	 imgDropzone.options.maxFiles=img_maxFiles + i_existing-change_old;
			            	 }
			        	   }
			            });
			            $("#btnSubmit").off("click").on("click",function(){
			                    if(control_show==0){
				            		data_old="";
				            		i_c=0;
				            		i_add=0;
				            		i_post=1;
				            		i_c_style=0;
				            		imgData=[];
				            		var old=$("#adv_image .dz-success").find("img");
				            		var total=$("#adv_image .dz-preview ").length;
				            		var new_count=total-old.length;      		
				            		var id=$("#id").attr("value");
				            		var error_count=e.find(".dz-error").length;
				            		limit=imgDropzone.getQueuedFiles().length;
				            		i_c_style=total-limit;
				            		for(var i=0;i<old.length;i++){        			
				            			data_old+=old[i].getAttribute("src")+',';
				            		}
				            		if(error_count==0){
						            	if(id!=""&&limit==0){
						            		if(old.length==0){
						            			$(".control_info").css("display","block");
						            			return false;
						            		}else{
							                    $("#path").attr("value",data_old);
						            		}
						            	}else{
						                	if(limit==0&&old.length==0){
						                		$(".control_info").css("display","block");
						                		return false;
						                	}else{
						                		if(limit!=0){
								              		imgDropzone.processQueue();
								               		return false;
						                		}
						                	}
						            	}
				            		}else{
				            			return false;
				            		}
			                    }
			           	   });
			        }
			    });
			    return;
			}
			if(control_show==1){
              console.log(size.sizeData[0].fileSize);
			    /*------------视频上传----------*/
				e.html("");
			    e.dropzone({
			        url: host+"/file/vedioUpload",
			        maxFiles: 1,
			        maxFilesize:size.sizeData[0].fileSize,
			        acceptedFiles:size.sizeDescription.format,
			        dictDefaultMessage:accipiter.getLang_(messageLang,"add.vedio"),
			    	dictRemoveFile :accipiter.getLang_(messageLang,"remove.vedio"),
			        dictFileTooBig:accipiter.getLang_(messageLang,"vedio.too.big"),
			        dictMaxFilesExceeded: accipiter.getLang_(messageLang,"vedio.maxsize"),
			        dictResponseError: accipiter.getLang_(messageLang,"vedio.upload.fail"),
			        dictCancelUpload: accipiter.getLang_(messageLang,"cancle.upload"),
			        addRemoveLinks:true,
			        autoProcessQueue: false,
			         parallelUploads:100,
			        init: function() {
			      		var vedioDropzone=this;
			      		var i_c=0;
			      		var i_c_style=0;
			      		var i_post=1;
			      		var limit=0;
			      		var data_source_old="";
			      		var data_url_old="";
			      		var data_previewurl_old="";
			      		var i_existing=0;
			      		var vedio_maxFiles=0;
			      		vedioDropzone.options.dictDefaultMessage=accipiter.getLang_(messageLang,"click.add.vedio");
			      		var path=$("#path").attr("value");
			      		var filesize=$("#fileSize").attr("value"); 
			      		var imgurl=$("#vedioImagePath").attr("value");
			      		var previewUrl=$("#previewVedioPath").attr("value");
			      		//回显
			      		if(path!="null,"){
			      			var pathdata=path.split(",");
			      			var sizedata=filesize.split(",");
			      			var imgurlData=imgurl.split(",");
			      			var previewUrlData=previewUrl.split(",");
			      			var data=[];
			      			for(var i=0,len=pathdata.length;i<len-1;i++){
			      				var info={size:sizedata[i],url:imgurlData[i],source:pathdata[i],preview:previewUrlData[i]};
			      				data.push(info);
			      			}
			  				var existingFileCount = data.length;
			      			$.each(data,function(infoIndex,info){
			      				var mockFile = { name:info["name"], size:info["size"] };
			      				vedioDropzone.emit("addedfile", mockFile);
			      				vedioDropzone.emit("thumbnail", mockFile, info["url"]);
			      				e.find('.dz-preview :eq('+infoIndex+')').attr("class","dz-preview dz-image-preview dz-old dz-success");
			      				e.find('.dz-preview :eq('+infoIndex+')').find("img").attr("name",info["source"]);
			      				e.find('.dz-preview :eq('+infoIndex+')').find("img").attr("title",info["preview"]);
			      				/*$("#upload_adv_vedio").find(".dz-preview").find(".dz-size").css("display","none");*/
			      			})
			      			i_existing=existingFileCount;
			      			vedio_maxFiles=vedioDropzone.options.maxFiles - existingFileCount;
			      			vedioDropzone.options.maxFiles = vedioDropzone.options.maxFiles - existingFileCount;
			      		}
			            this.on("success", function(file,data) {
			            	var vedioDropzone=this;
			            	var control=data.split("`")[0];
			            	if(control=="0"){
			            		i_post=0;
			            		var errorinfo=data.substring(2);
			            		e.find('.dz-preview:eq('+i_c_style+')').attr("class","dz-preview dz-image-preview dz-error");
			            		e.find('.dz-preview:eq('+i_c_style+')').find(".dz-error-message").text(errorinfo);
			            		$(".file_loading").css("display","none");
			            	}
			            	if(control=="1"){
			                   	vedioSourceData+=data.substring(2).split("`")[0]+",";
			                   	vedioUrlData+=data.substring(2).split("`")[1]+",";
			                   	vedioPreviewUrlData+=data.substring(2).split("`")[2]+",";
			                   	e.find('.dz-preview:eq('+i_c_style+')').find("img").attr("src",data.substring(2).split("`")[1]);
			                   	e.find('.dz-preview:eq('+i_c_style+')').find("img").attr("name",data.substring(2).split("`")[0]);
			                   	e.find('.dz-preview:eq('+i_c_style+')').find("img").attr("title",data.substring(2).split("`")[2]);
			                    if(i_c==(limit-1)&&i_post==1&&limit!=0){
			                    	vedioSourceData=data_source_old+vedioSourceData;
			                    	vedioUrlData=data_url_old+vedioUrlData;
			                    	vedioPreviewUrlData=data_url_old+vedioPreviewUrlData;
			                        $("#path").attr("value",vedioSourceData);
			                        $("#previewVedioPath").attr("value",vedioPreviewUrlData);
			                        $("#vedioImagePath").attr("value",vedioUrlData);
			                        $(".file_loading").css("display","none");
			                        $("#btnSubmit").attr({"disabled":false});
			                        $("#inputForm").submit();
			                    } 
			                    i_c++;
			            	}
			           		i_c_style++;
			            });
			            this.on("removedfile",function(file){
			            	var total=$("#adv_vedio .dz-preview ").length;
			            	if(total==0){
		                        $("#btnSubmit").attr({"disabled":false});
			            	}		            	
			         	   $('.remove_data').html(file.previewElement);
			         	   if($('.remove_data').find(".dz-preview").hasClass("dz-old")){
			               	 var change_old=$("#adv_vedio .dz-old").find("img").length;
			             	 if(change_old!=undefined){
			                 	 vedioDropzone.options.maxFiles=vedio_maxFiles + i_existing-change_old;
			             	 }
			         	   }
			             });
			            	$("#btnSubmit").off("click").on("click",function(){
			                    if(control_show==1){
			                  		data_source_old="";
			                  		data_url_old="";
			                  		data_previewurl_old="";
				            		i_c=0;
				            		i_post=1;
				            		i_c_style=0;
				            		vedioUrlData=[];	
				            		vedioSourceData=[];
				            		vedioPreviewUrlData=[];
				            		var old=$("#adv_vedio .dz-success").find("img");
				            		var id=$("#id").attr("value");
				            		var total=$("#adv_vedio .dz-preview ").length;
				            		var new_count=total-old.length; 
				            		var error_count=e.find(".dz-error").length;
				            		limit=vedioDropzone.getQueuedFiles().length;
				            		i_c_style=total-limit;
				            		for(var i=0;i<old.length;i++){        			
				            			data_url_old+=old[i].getAttribute("src")+',';
				            			data_source_old+=old[i].getAttribute("name")+',';
				            			data_previewurl_old+=old[i].getAttribute("title")+',';
				            		}
				            		if(error_count==0){
					            		if(id!=""&&limit==0){
					            			if(old.length==0){
					            				$(".control_info").css("display","block");
					                			return false;
					            			}else{
						                        $("#path").attr("value",data_source_old);
						                        $("#vedioImagePath").attr("value",data_url_old);
						                        $("#previewVedioPath").attr("value",data_previewurl_old);
						                        
					            			}
					            		}else{
					                		if(limit==0&&old.length==0){
					                			$(".control_info").css("display","block");
					                			return false;
					                		}
					                		if(limit==0){
						                		vedioDropzone.processQueue();
					                			return ;
					                		}
					                		$("#btnSubmit").attr({"disabled":true});
					                		$(".file_loading").css("display","block");
					                		vedioDropzone.processQueue();
					               		    return false;
					            		}
				            		}else{
				            			  return false;
				            		}
			                    }
			           	   });
			        }
			    });
			    return;
			}
			if(control_show==3){
			    /*--------开机广告上传--------*/
				var fileSize=0;
				$.each(size.sizeData,function(commentIndex,comment){
					if(comment["fileSize"]>=fileSize){
						fileSize=comment["fileSize"];
					}
				});
				e.html("");
			    e.dropzone({
			        url: host+"/file/openImageFile",
			        maxFiles: 1,
			        maxFilesize:fileSize,
			        acceptedFiles:size.sizeDescription.format,
			        dictDefaultMessage : accipiter.getLang_(messageLang,"add.image"),
					dictRemoveFile :accipiter.getLang_(messageLang,"remove.image"),
			        dictFileTooBig:accipiter.getLang_(messageLang,"image.too.big"),
			        dictMaxFilesExceeded:accipiter.getLang_(messageLang,"image.maxsize"),
			        dictResponseError: accipiter.getLang_(messageLang,"image.upload.fail"),
			        dictCancelUpload: accipiter.getLang_(messageLang,"cancle.upload"),
			        addRemoveLinks:true,
			        autoProcessQueue: false,
			       /* uploadMultiple:true,*/
			        parallelUploads:100,
			        init: function() {
			      		var imgDropzone=this;
			      		var i_c=0;
			      		var i_add=0;
			      		var i_c_style=0;
			      		var i_post=1;
			      		var limit=0;
			      		var data_source_old="";
			      		var data_url_old="";
			      		var i_existing=0;
			      		var img_maxFiles=0;
			      		imgDropzone.options.dictDefaultMessage=accipiter.getLang_(messageLang,"add.image");
			      		var path=$("#path").attr("value");
			      		var filesize=$("#fileSize").attr("value"); 
			      		var imgurl=$("#vedioImagePath").attr("value");  		
			      		/*回显*/
			      		if(path!="null,"){
			      			var pathdata=path.split(",");
			      			var sizedata=filesize.split(",");
			      			var imgurlData=imgurl.split(",");
			      			var data=[];
			      			for(var i=0,len=pathdata.length;i<len-1;i++){
			      				var info={size:sizedata[i],url:imgurlData[i],source:pathdata[i]};
			      				data.push(info);
			      			}
			  				var existingFileCount = data.length;
			      			$.each(data,function(infoIndex,info){
			      				var mockFile = { name:info["name"], size:info["size"] };
			      				imgDropzone.emit("addedfile", mockFile);
			      				imgDropzone.emit("thumbnail", mockFile, info["url"]);
			      				e.find('.dz-preview :eq('+infoIndex+')').attr("class","dz-preview dz-image-preview dz-old dz-success");
			      				e.find('.dz-preview :eq('+infoIndex+')').find("img").attr("name",info["source"]);
			      				/*$("#upload_adv_vedio").find(".dz-preview").find(".dz-size").css("display","none");*/
			      			})
			      			i_existing=existingFileCount;
			      			img_maxFiles=imgDropzone.options.maxFiles - existingFileCount;
			      			imgDropzone.options.maxFiles = imgDropzone.options.maxFiles - existingFileCount;
			      		}
			     		this.on("thumbnail",function(file){
			     			if(size.sizeData.length==1){
								var highMax=size.sizeData[0].highMax;
								var highMin=size.sizeData[0].highMin;
								var widthMax=size.sizeData[0].widthMax;
								var widthMin=size.sizeData[0].widthMin; 
				            	if(file.width<widthMin||file.width>widthMax||file.height<highMin||file.height>highMax){
				            		if(i_add<imgDropzone.options.maxFiles){
				                		e.find('.dz-preview:eq('+i_add+')').attr("class","dz-preview dz-image-preview dz-error");
				                		e.find('.dz-preview:eq('+i_add+')').find(".dz-error-message span").text(accipiter.getLang_(messageLang,"uploaderrInfo"));
				            		}
				            	}
			     			}else{
				            	if(file.width<size.sizeData[0].widthMin||file.width>size.sizeData[0].widthMax||file.height<size.sizeData[0].highMin||file.height>size.sizeData[0].highMax){
				            		if(file.width<size.sizeData[1].widthMin||file.width>size.sizeData[1].widthMax||file.height<size.sizeData[1].highMin||file.height>size.sizeData[1].highMax){
					            		if(i_add<imgDropzone.options.maxFiles){
					                		e.find('.dz-preview:eq('+i_add+')').attr("class","dz-preview dz-image-preview dz-error");
					                		e.find('.dz-preview:eq('+i_add+')').find(".dz-error-message span").text(accipiter.getLang_(messageLang,"uploaderrInfo"));
					            		}
				            		}
				            	}
			     			}
			            	i_add++;
			      		});
			            this.on("success", function(file,data) {		            	
			            	var imgDropzone=this;
			            	var control=data.split("`")[0];
			            	if(control=="0"){
			            		i_post=0;
			            		var errorinfo=data.substring(2);
			            		e.find('.dz-preview:eq('+i_c_style+')').attr("class","dz-preview dz-image-preview dz-error");
			            		e.find('.dz-preview:eq('+i_c_style+')').find(".dz-error-message").text(errorinfo);
			            		 $(".file_loading").css("display","none");
			                     $("#btnSubmit").attr({"disabled":false});
			            	}
			            	if(control=="1"){
			                   	vedioSourceData+=data.substring(2).split("`")[0]+",";
			                   	vedioUrlData+=data.substring(2).split("`")[1]+",";
			                   	e.find('.dz-preview:eq('+i_c_style+')').find("img").attr("src",data.substring(2).split("`")[1]);
			                   	e.find('.dz-preview:eq('+i_c_style+')').find("img").attr("name",data.substring(2).split("`")[0]);
			                    if(i_c==(limit-1)&&i_post==1&&limit!=0){
			                    	vedioSourceData=data_source_old+vedioSourceData;
			                    	vedioUrlData=data_url_old+vedioUrlData;
			                        $("#path").attr("value",vedioSourceData);
			                        $("#vedioImagePath").attr("value",vedioUrlData);
			                        $(".file_loading").css("display","none");
			                        $("#btnSubmit").attr({"disabled":false});
			                        $("#inputForm").submit();
			                    } 
			                    i_c++;
			            	}
			            	if(control=="-1"){
			            		i_post=0;
			            		var errorinfo=data.substring(2);
			            		e.find('.dz-preview:eq('+i_c_style+')').attr("class","dz-preview dz-image-preview dz-error");
			            		e.find('.dz-preview:eq('+i_c_style+')').find(".dz-error-message").text(errorinfo);
			            		$(".file_loading").css("display","none");
			            		$("#btnSubmit").attr({"disabled":false});
			            	}
			        		i_c_style++;
			            });
			           this.on("removedfile",function(file){
			        	   $('.remove_data').html(file.previewElement);
			        	   if($('.remove_data').find(".dz-preview").hasClass("dz-old")){
			              	 var change_old=$("#adv_kaijiimage .dz-old").find("img").length;
			            	 if(change_old!=undefined){
			                	 imgDropzone.options.maxFiles=img_maxFiles + i_existing-change_old;
			            	 }
			        	   }
			            });
			            $("#btnSubmit").off("click").on("click",function(){
			                    if(control_show==3){
			                  		data_source_old="";
			                  		data_url_old="";
				            		i_c=0;
				            		i_add=0;
				            		i_post=1;
				            		i_c_style=0;
				            		imgData=[];
				            		vedioUrlData=[];	
				            		vedioSourceData=[];
				            		var old=$("#adv_kaijiimage .dz-success").find("img");
				            		var total=$("#adv_kaijiimage .dz-preview ").length;
				            		var new_count=total-old.length;      		
				            		var id=$("#id").attr("value");
				            		var error_count=e.find(".dz-error").length;
				            		limit=imgDropzone.getQueuedFiles().length;
				            		i_c_style=total-limit;
				            		for(var i=0;i<old.length;i++){        			
				            			data_url_old+=old[i].getAttribute("src")+',';
				            			data_source_old+=old[i].getAttribute("name")+',';
				            		}
				            		if(error_count==0){
					            		if(id!=""&&limit==0){
					            			if(old.length==0){
					            				$(".control_info").css("display","block");
					                			return false;
					            			}else{
						                        $("#path").attr("value",data_source_old);
						                        $("#vedioImagePath").attr("value",data_url_old);			                        
					            			}
					            		}else{
					                		if(limit==0&&old.length==0){
					                			$(".control_info").css("display","block");
					                			return false;
					                		}
					                		if(limit==0){
					                			imgDropzone.processQueue();
					                			return ;
					                		}
					                		$("#btnSubmit").attr({"disabled":true});
					                		$(".file_loading").css("display","block")
					                		imgDropzone.processQueue();
					               		    return false;
					            		}
				            		}else{
				            			  return false;
				            		}
			                    }
			           	   });
			        }
			    });
			    return;
			}
		}

	}
    /*滚动图片自制*/
    /*
     * 默认值
     * */
    var heightSize=30;    //图片高度范围（30-50），默认值30,
    var heightRange={min:30,max:50};
    var fontSize=18;
    var fontStyle=0;//0表示宋体,1表示乌尔都(Noori Nastalleeq);
    var bgStyle=0;//0表示纯色，1表示背景图,2表示透明，
    var fontColor="#000000";
    var bgColor="#ffffff";
    var backImagePath="";
    function setDefault(e){
        if(e.hasClass("set-font-style")){
        	var fontStleText=$('.set-font-style li[value='+fontStyle+']').text();
            $(".set-chose").find(".set-font-style input").attr("value",fontStleText);
            return;
        }
        if(e.hasClass("set-font-size")){
            $(".set-chose").find(".set-font-size input").attr("value",fontSize);
            return;
        }
        if(e.hasClass("set-font-color")){
            $(".set-chose").find(".FontColor").attr("value",fontColor);
            return;
        }
        if(e.hasClass("set-bg-size")){
            $(".set-chose").find("input").attr("value",heightSize);
            return;
        }
        if(e.hasClass("set-bg")){
            var bg="";
            if(bgStyle==0){
                bg=accipiter.getLang_(messageLang,"set.color");
            }else if(bgStyle==1){
                bg=accipiter.getLang_(messageLang,"set.picture");
            }else{
            	bg=accipiter.getLang_(messageLang,"set.transparent");	
            }
            $(".set-chose").find(".chose-btn").attr("value",bg);
            $(".set-chose").find(".BgColor").attr("value",bgColor);
            return;
        }
    }
    /*
     * 获取颜色选择后的值设为默认值
     * */
     function setColorDefault(){
        var FontColor= $(".set-chose").find(".FontColor").val();
        var BgColor= $(".set-chose").find(".BgColor").val();
         if(FontColor!=undefined){
             fontColor=FontColor;
             return;
         }
         if(BgColor!=undefined){
             bgColor=BgColor;
             return;
         }

     }
     /*
      * 根据图片高度计算字体大小
      * */
     function calculateFontSize(heightSize){
         var fontSize={"min":parseInt(heightSize/2),"max":parseInt(heightSize/2+15)};
         return fontSize;
     }
     /*
      * 生成字体语言
      * */
     function createFontLanguage(e){
         html='<li><label>'+accipiter.getLang_(messageLang,"set.fontStyel")+':</label><div class="set-chose-content set-font-style"><input type="text" class="chose-btn chose-fontStyle-btn up" readonly><ul><li value="0" class="chose-item">宋体</li><li value="1" class="chose-item">Noori Nastalleeq</li></ul></div></li>';
         $(".set-chose ul").html("");
         $(".set-chose ul").html(html);
     }
     /*
      * 生成字体大小
      * */
     function createFontStyle(e){
         var sizeMin=e.min;
         var sizeMax=e.max;
         var html='<li><label>'+accipiter.getLang_(messageLang,"set.fontSize")+':</label><div class="set-chose-content set-font-size"><input type="text" class="chose-btn up" readonly><ul>';
         for(var i=sizeMin;i<=sizeMax;i++){
             html+='<li class="chose-item">'+i+'</li>';
         }
         html+='</ul></div></li>';
         $(".set-chose ul").html("");
         $(".set-chose ul").html(html);
     }
     /*
      *生成字体颜色值
      * */
      function createFontColor(){
          var html='<li><label>'+accipiter.getLang_(messageLang,"set.fontColor")+':</label><div class="set-chose-content set-bg-color ui-widget ui-widget-content ui-corner-all"><input type="text" id="FontColor" class="chose-btn FontColor up" readonly value="#000000"></div></li>';
          $(".set-chose ul").html("");
          $(".set-chose ul").html(html);
      }
      /*
       * 生成图片高度
       * */
       function createBgHeight(e){
           var min = e.min;
           var max = e.max;
           var html='<li><label>'+accipiter.getLang_(messageLang,"set.bgHeight")+':</label><div class="set-chose-content set-bg"><input type="text" class="chose-btn up" readonly><ul>';
           for(var i=min;i<=max;i++){
               html+='<li class="chose-item">'+i+'</li>';
           }
           html+='</ul></div></li>';
           $(".set-chose ul").html("");
           $(".set-chose ul").html(html);
       }
       /*
        * 生成图片背景和颜色
        * */
        function createBgColorAndImg(isPurity){
            var html='';
            html+='<li><label>'+accipiter.getLang_(messageLang,"set.bgStyle")+':</label>' +
                '<div class="set-chose-content set-bg-style">' +
                '<input type="text" class="chose-btn bg-style up" readonly><i class="icon-down"></i>'+
                '<ul>' +
                '<li class="chose-item" value="0">'+accipiter.getLang_(messageLang,"set.color")+'</li>' +
                '<li class="chose-item" value="1">'+accipiter.getLang_(messageLang,"set.picture")+'</option>' +
                '<li class="chose-item" value="2">'+accipiter.getLang_(messageLang,"set.transparent")+'</option>' +
                '</ul>' +
                '</div>';
            if(isPurity==0){
                html+='<li><label>'+accipiter.getLang_(messageLang,"set.colorValue")+':</label><div class="set-chose-content set-bg-color ui-widget ui-widget-content ui-corner-all"><input type="text" id="BgColor" class="BgColor up" readonly value="#e3e3e3"></div></li>'

            }else if(isPurity==2){
                html+='<li><label>'+accipiter.getLang_(messageLang,"set.colorValue")+':</label><div class="set-chose-content set-bg-color ui-widget ui-widget-content ui-corner-all"><input type="text" id="BgColor" class="BgColor up" readonly value="#e3e3e3"></div></li>'

            }else{
                html+='<li><label>'+accipiter.getLang_(messageLang,"set.uploadPicture")+':</label><span class="uploadTitle">'+accipiter.getLang_(messageLang,"set.upload")+'</span><div class="set-chose-content set-bg-style dropzoneStyle uploadBgPicture" id="uploadBgPicture" ></div></li><li class="uploadPicture"><div><p>'+accipiter.getLang_(messageLang,"upload.picture.preview")+'<span class="uploadErrInfo"></span></p><div><img></div></div></li>'
            }
            $(".set-chose ul").html("");
            $(".set-chose ul").html(html);

        }
 
        /*
         * 获取所需要传入后台参数并且生成自制图片
         * */
        function CreatePicture(){
        	var textSize=fontSize;
        	var textColor=fontColor.colorRgb()+",1";
        	var background=bgColor.colorRgb()+",1";
        	var imageHeight=heightSize;
        	var isPurity=bgStyle;
        	var flag = $("#flag").val();
        	
        	var text=$(".set-text").find("textarea").val();
         //var text = encodeURI(encodeURI(text11));
        	var data={};
        	if(isPurity==0 || isPurity==2){
            	data = {"textSize":textSize,"textColor":textColor,"background":background,"imageHeight":imageHeight,"isPurity":isPurity,"text":text,"flag":flag};
        	}else{
        		data={"textSize":textSize,"textColor":textColor,"isPurity":isPurity,"text":text,"backImagePath":backImagePath,"flag":flag};
        		if(backImagePath==""){
        			$(".imgContent").html("");
        			$(".imgContent").html('<p>'+accipiter.getLang_(messageLang,"uplpad.empty")+'</p>');
        			return;
        		}
        	}
        	var postData=JSON.stringify(data);;
        	$.ajax({
        		type:"post",
        		url:host+"/adv/control/get_roll_backgroud",
        		data:postData,
        		contentType : "application/json;charset=utf-8",
        		dataType:"text",
        		success:function(data){
        			var control=data.split("`")[0];
        			var imgurl=data.split("`")[1];
        			if(control=="0"){
        				$(".imgContent").html("");
        				$(".imgContent").html('<img src='+imgurl+'>');
            			$("#path").attr("value",imgurl);
            			$("#rollText").attr("value",text);
            			$("#rollTextSize").attr("value",fontSize);
            			$("#rollTextColor").attr("value",fontColor);
            			$("#isPurity").attr("value",isPurity);
            			if(isPurity==0 || isPurity==2){
                			$("#rollBackground").attr("value",bgColor);
            			}else{
                			$("#rollBackground").attr("value",backImagePath);
            			}
            			return;
        			}else{
        				var errorUpload = accipiter.getLang_(messageLang,"uplpad.error");
            			$(".imgContent").html("");
            			$(".imgContent").html('<p>'+errorUpload+'</p>');
            			$("#path").attr("value","");
        			}
        			
        		},
        		error:function(){
        			var errorUpload = accipiter.getLang_(messageLang,"uplpad.error");
        			$(".imgContent").html("");
          			$(".imgContent").html('<p>'+errorUpload+'</p>');
        			$("#path").attr("value","");
        		}
        	})
        	
        	
        }
        /*
         * 滚动图片上传
         * */
        function rollPictureUpload(e,control,size){
        	if(size.length!=0){
            	/*剔除gif格式*/
            	var size=limitSizeData(size);
            	var formatdata=size.sizeDescription.format.split(",");
            	var newFormatdata=[];
            	var newFormat="";
            	for(var i=0;i<formatdata.length;i++){
            		if(formatdata[i]!=".gif"){
            			newFormatdata.push(formatdata[i]);
            		}
            	}
            	newFormat=newFormatdata.join(",");
            	if(control==1){      		
                	Dropzone.autoDiscover = false;
        		    e.dropzone({
        		        url: host+"/file/uploadImage",
        		        maxFiles: 1,
        		        maxFilesize:size.sizeData[0].fileSize,
        		        acceptedFiles:newFormat,
        		        dictDefaultMessage : accipiter.getLang_(messageLang,"add.image"),
        				dictRemoveFile :accipiter.getLang_(messageLang,"remove.image"),
        		        dictFileTooBig:accipiter.getLang_(messageLang,"image.too.big"),
        		        dictMaxFilesExceeded:accipiter.getLang_(messageLang,"image.maxsize"),
        		        dictResponseError: accipiter.getLang_(messageLang,"image.upload.fail"),
        		        dictCancelUpload: accipiter.getLang_(messageLang,"cancle.upload"),
        		        addRemoveLinks:true,
        		        autoProcessQueue: false,
        		       /* uploadMultiple:true,*/
        		        parallelUploads:100,
        		        init: function() { 
        		        	var imgDropzone=this;
        		     		this.on("thumbnail",function(file){
        		     			$(".uploadPicture").find(".uploadTitle").text(file.name);
    	            			e.find('.dz-preview').css("display","none");
    							var highMax=size.sizeData[0].highMax;
    							var highMin=size.sizeData[0].highMin;
    							var widthMax=size.sizeData[0].widthMax;
    							var widthMin=size.sizeData[0].widthMin; 
        		            	if(file.width<widthMin||file.width>widthMax||file.height<highMin||file.height>highMax){
    		                		e.find('.dz-preview').attr("class","dz-preview dz-image-preview dz-error");	
    		                		e.find('.dz-preview').find(".dz-error-message span").text(accipiter.getLang_(messageLang,"uploaderrInfo"));
    		                		$(".uploadPicture").find(".uploadErrInfo").text(accipiter.getLang_(messageLang,"uploaderrInfo"));
    	    		            	imgDropzone.removeFile(file);
        		            	}else{
        		            		$(".uploadPicture").find(".uploadErrInfo").text("");
        		            		imgDropzone.processQueue();
        		            	}
        		      		});
        		            this.on("success", function(file,data) {
        		            	imgDropzone.removeFile(file);
        		            	var control=data.split("`")[0];
        		            	if(control=="0"){
        		            		var url=data.split("`")[1];
        		            		backImagePath=url;
        		            		$(".uploadPicture").find("img").attr("src",url);
        		            		$(".set-action").find("a").attr("name","1");       		            		
        		            	}
        		            	if(control=="1"){
        		            		var errorinfo=data.substring(2);
        		            		e.find('.dz-preview').attr("class","dz-preview dz-image-preview dz-error");
        		            		e.find('.dz-preview').find(".dz-error-message").text(errorinfo);
        		            		$(".set-action").find("a").attr("name","0");
        		            	}
        		            	if(control=="-1"){
        		            		var errorinfo=data.substring(2);
        		            		e.find('.dz-preview').attr("class","dz-preview dz-image-preview dz-error");
        		            		e.find('.dz-preview').find(".dz-error-message").text(errorinfo);
        		            		$(".set-action").find("a").attr("name","0");
        		            	}
        		            });
        		        }
        		    });
            	}
        	}
        }
       /*
        * 和成图片与非图片点击切换时，清除之前的历史数据
        * */
        function switchRollPictureClearData(){
    		$(".imgContent").html('');	
    		$(".set-text").find("textarea").val("");
    		$(".uploadPicture").find("img").removeAttr("src");
    		backImagePath="";
        }
        /*
         * 修改回显自制图片数据
         * */
        function getRollPicture(){
        	var id=$("#id").val();
        	var rolltext=$("rollText").val();
        	var url =$("#path").val().split(",")[0];
        	isPurity =parseInt($("#isPurity").val());
        	var text=$("#rollText").attr("value");
        	var rollBackground=$("#rollBackground").val();
        	if(id!=""){
                fontColor=$("#rollTextColor").val();
                bgStyle=$("#isPurity").val();
                fontSize=$("#rollTextSize").val();
            	if(id!=""&&rolltext!=""){
            		$(".set-action").find("a").attr("name","1");
            		$(".imgContent").html('<img src='+url+'>');	
            		$(".set-text").find("textarea").val(text);
            		if(bgStyle==0 || bgStyle==2){
            			bgColor=rollBackground;
            		}
            		createBgColorAndImg(isPurity);
                    setDefault($(".set-bg"));
            		if(isPurity==1){
            			backImagePath=rollBackground;
            			$(".uploadPicture").find("img").attr("src",rollBackground);
            			var control= parseInt($(".select").children("option:selected").val().getAdvType());
            			var flag=$("#flag").children("option:selected").val()
            			var info=$(".select").children("option:selected").text();
            			if(control!=""&&flag!=""){
            				var data={"id":$(".select").children("option:selected").val(),"flag":flag};
                			rollPictureUpload($(".set-chose").find(".dropzoneStyle"),bgStyle,getSize(data));
            			}
            		}else{
                        $(".set-chose").find(".BgColor").colorpicker({
                            displayIndicator: false
                        });
            		}
            	}
        	}
        }
        getRollPicture();
       /* 
        * 类容切换
        * */
        function clickSwitch(node){
            if(node.parent().hasClass("set-font-style")){
                /*字体语言设置*/
                $(".set-ul a").removeClass("active");
                node.addClass("active");
                setColorDefault();
           	    createFontLanguage();
                setDefault(node.parent());
                return;
            }
            if(node.parent().hasClass("set-font-size")){
                /*字体大小设置*/
                $(".set-ul a").removeClass("active");
                node.addClass("active");
                setColorDefault();
                createFontStyle(calculateFontSize(heightSize));
                setDefault(node.parent());
                return;
            }
            if(node.parent().hasClass("set-font-color")){
                /*字体颜色设置*/
                $(".set-ul a").removeClass("active");
                node.addClass("active");
                setColorDefault();
                createFontColor();
                setDefault(node.parent());
                $(".set-chose").find(".FontColor").colorpicker({
                    displayIndicator: false
                });
                return;
            }
            if(node.parent().hasClass("set-bg-size")){
                /*背景高度设置*/
                $(".set-ul a").removeClass("active");
                node.addClass("active");
                setColorDefault();
                createBgHeight(heightRange);
                setDefault(node.parent());
                return;
            }
            if(node.parent().hasClass("set-bg")){
                /*背景样式设置*/
                $(".set-ul a").removeClass("active");
                node.addClass("active");
                setColorDefault();
                createBgColorAndImg(bgStyle);
                if(bgStyle==1){
        			var control= parseInt($(".select").children("option:selected").val().getAdvType());
        			var flag=$("#flag").children("option:selected").val()
        			var info=$(".select").children("option:selected").text();
        			if(control!=""&&flag!=""){
        				var data={"id":$(".select").children("option:selected").val(),"flag":flag};
            			rollPictureUpload($(".set-chose").find(".dropzoneStyle"),bgStyle,getSize(data));
        			}
               	 if(backImagePath.indexOf(".")!=-1){
                     	 $(".uploadPicture").find("img").attr("src",backImagePath); 
               	 }
                }
                setDefault(node.parent());
                $(".set-chose").find(".BgColor").colorpicker({
                    displayIndicator: false
                });
                return;
            }
            if(node.parent().hasClass("set-action")){
           	 /*图片生成*/
           	 var text=$(".set-text textarea").val();
           	 if(text==""){
           		 var errinfo=accipiter.getLang_(messageLang,"file.text");
         			$(".imgContent").html('<p>'+errinfo+'</p>');
           	 }else{
           		 $(".imgContent").html('');
               	 CreatePicture();
           	 }
                return;
            }
        }
        clickSwitch($(".set-bg a"));//默认显示
        /*
         * 监听改变事件
         * */
         $('.set-ul').on("click","a",function(){
        	 clickSwitch($(this));
         })
         
        /*
         * 监听取色改变事件
         * */
             $(".set-chose").on("change.color",".BgColor", function(event, color){
            	 if(color!=undefined){
            		 bgColor=color;
            	 }
             });
             $(".set-chose").on("change.color",".FontColor", function(event, color){
            	 if(color!=undefined){
            		 fontColor=color;
            	 }
             })
         /*
          * 监听改变事件选项事件
          * */
          function changeStyle(e){
              if(e.hasClass("up")){
                  e.removeClass("up");
                  e.addClass("down");
                  $(".set-chose").find(".set-chose-content ul").css("display","block");
              }else{
                  e.removeClass("down");
                  e.addClass("up");
                  $(".set-chose").find(".set-chose-content ul").css("display","none");
              }
          }
          $(".set-chose").on("click","input,li,td",function(){
              /*改变条件选项状态*/
              if($(this).hasClass("chose-btn")){
                  var e=$(this);
                  changeStyle(e);
                  return;
              }
              /*条件选取*/
              if($(this).hasClass("chose-item")){
                  var chose=$(this).text();
                  var e=$(".set-chose").find(".chose-btn");
                  e.attr("value",chose);
                  changeStyle(e);
                  if($(this).parent().parent().hasClass("set-font-style")){
                	  fontStyle=$(this).attr("value");
                      return;
                  }
                  if($(this).parent().parent().hasClass("set-bg")){
                      heightSize=chose;
                      return;
                  }
                  if($(this).parent().parent().hasClass("set-font-size")){
                      fontSize=chose;
                      return;
                  }
                  if($(this).parent().parent().hasClass("set-bg-style")){
                      var choseValue=parseInt($(this).attr("value"));
                      if(choseValue==0){
                          bgStyle=0;
                      }else if(choseValue==2){
                          bgStyle=2;
                      } else{
                          bgStyle=1;
                      }
                      createBgColorAndImg(bgStyle);
                      if(bgStyle==1){
                     	 if(backImagePath.indexOf(".")!=-1){
                           	 $(".uploadPicture").find("img").attr("src",backImagePath); 
                     	 }
                      }else{
                      	switchRollPictureClearData();
                      }
                      var dom=$(".title-table").find("#uploadBgPictur");
                      if(dom!='underfind'){
              			var control= parseInt($(".select").children("option:selected").val().getAdvType());
            			var flag=$("#flag").children("option:selected").val()
            			var info=$(".select").children("option:selected").text();
            			if(control!=""&&flag!=""){
            				var data={"id":$(".select").children("option:selected").val(),"flag":flag};
                			rollPictureUpload($(".set-chose").find(".dropzoneStyle"),bgStyle,getSize(data));
            			}
                      }
                      setDefault($(".set-bg"));
                      $(".set-chose").find(".BgColor").colorpicker({
                          displayIndicator: false
                      });
                      return;
                  }
              }
          });
          $("#btnSubmit").on("click",function(){
		        if(control_show==4){
		        	var path=$("#path").attr("value").split(",")[0];
		        	if(path=="null"){
		        		return false
		        	}		                    	
		      }
		   })
		        
})