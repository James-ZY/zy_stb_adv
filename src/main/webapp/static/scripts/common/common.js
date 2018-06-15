(function($) {
	accipiter=window.accipiter=$(document);//定义全局变量
	$.fn.extend({//插件扩展
		/**
		 * 获取根目录
		 * @returns
		 */
		 getRootPath:function(){
			 
			//获取当前网址
			var curPath=window.document.location.href;
			//获取主机地址之后的目录
			var pathName=window.document.location.pathname;
			var pos=curPath.indexOf(pathName);
			//获取主机地址，
			var localhostPath=curPath.substring(0,pos);
			var reg=/.*(advs).*/;
			var result=reg.test(curPath);
			var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);//获取带"/"的项目名
			if(result==true){
				return localhostPath+"/advs";
			}else{				return localhostPath;
			}
		},
        getProjectName:function() {
            var pathName = window.document.location.pathname;
            var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);//获取带"/"的项目名
            if(projectName=="/advs"){
            	return projectName;
            }else{
            	return "";
            }
        },
		getIp:function(){//获取ip
			return accipiter.getLocaleParam("ip");
		},
		getPage:function(){//获取页码
			return accipiter.getLocaleParam("page");
		},
		/**
		 * 获取地址栏传入的参数
		 * parm : 传入需要得到的参数的值
		 * @return
		 */
		getLocaleParam:function(parm){
			var val="";
			var url=window.location.search;
			if(url.indexOf("?")!=-1){
				var str=url.slice(1);
				var strs=str.split("&");
				for(var i=0;i<strs.length;i++){
					if([strs[i].split("=")[0]]==parm){
						val=decodeURI(strs[i].split("=")[1]);
					} 
				}
			}
			return val;
		},
		/**
		 * 数字微调框
		 * 参数：
		 * 	sel:选择符
		 *  min:最小限制
		 *  max:最大限制
		 *  loop:间隔
		 * @return
		 */
		  numberPicker:function(sel,min,max,loop){
			  if(sel==null){throw new Error("参数错误");}
			  if(loop==null){loop=1;}
			  if(min==null){min=0;}
			  if(max==null){max=100;}
			var up=$(sel).siblings().eq(0).find(".up");
			var down=$(sel).siblings().eq(0).find(".down");
			up.click(function(){
				var v=$(sel).find("input").val(); 
				if(v!="" && v!=undefined && v!=null && !isNaN(v)){
					v=Number(v)+loop;
					if(v>=max){
						v=max;
					}
				}
				$(sel).find("input").val(v);
			});
			down.click(function(){
				var v=$(sel).find("input").val(); 
				if(v!="" && v!=undefined && v!=null && !isNaN(v)){
					v=Number(v)-loop;
					if(v<=min){
						v=min;
					}
				}
				$(sel).find("input").val(v);
			});
		},
		/**
		 * post方式请求数据（Json）
		 * 参数：url:请求地址，
		 * 		param:所传参数
         * 	    alwaysLoading:总是显示加载状态
         * 	    faultHandler:错误处理方法
         * 	    async:是否是异步模型 默认为异步
         **/
		  doPost:function(url,param,alwaysLoading,faultHandler, async){
              if(!alwaysLoading)
                  alwaysLoading = false; //设置参数alwaysLoading默认值为false
              async = (undefined == async ? true :async);
			  var reg=alwaysLoading ? alwaysLoading : /get|find/.test(url);   //alwaysLoading时无需正则计算
			  if(alwaysLoading || reg){accipiter.loadding(true);}
				var jq=$.ajax({
						type:'post',
						data:JSON.stringify(param),
                        async:async,
						url:url,
						contentType:"application/json; charset=UTF-8"/*,
						dataType:'json'*/});

				jq.fail(function(jqXHR, textStatus, errorThrown){
                    if(faultHandler) {
                        faultHandler(jqXHR, textStatus, errorThrown);
                    } else {
                        accipiter.back({tip:accipiter.getLang("t6"),type:1});
                    }
				});
				jq.always(function(){
					if(alwaysLoading || reg){accipiter.loadding(false);}
				});
				return jq;
		},
		/**
		 * ip地址正则验证
		 * 	ip:需要验证的ip
		 */
		ipReg:function(ip){
			var reg=/^((\d{1}|[1-9][0-9]|2[0-5][0-5]|1\d\d)\.){3}(\d{1}|[1-9][0-9]|2[0-5][0-5]|1\d\d)$/;
			var t= reg.test(ip);
			return t;
		},
		/**
		 * 获取值，如果是默认值，返回''
		 * @param sel：选择符
		 * @return
		 */
		getVal:function (sel){
			var v=$(sel).val();
		},
		/**
		 * 下拉列表框触发事件
		 */
		selChange:function (){
			$(".sel select").change(function(obj){
				var target=obj.target;
				var o=$(this);
				$.each(target,function(i){
					var selected=target[i].selected;
					if(selected){
						var text=target[i].text;
						$(o).parent().find(".title").text(text);
					}
				});
			});	
		},
		/**
		 * 只能输入数字正则表达
		 * val:需要验证的值
		 */
		numReg:function(val){
			var num=/^(\d)*$/g;
			return num.test(val);
		},
		/**
		 * 设置后返回
		 */
		setBack:function(status,oid,value,callback){
			if(status=="000000"){
				if(oid=="0.0.0.1"){
					alert(value);
				}else{
					accipiter.back({tip:accipiter.getLang("success")}); 
				}
				if(callback && (callback  instanceof Function)){
		              callback();
		        }
			}else{
				accipiter.back({tip:accipiter.getLang("fail"),type:1}); 
			}
		},
		/**
		 * checkbox全选
		 * sel:   例如： $("#vpnTable input[type=checkbox]")
		 */
		chk:function(sel){
			$(sel).eq(0).click(function(){
				sel.slice(1).prop("checked",this.checked);
			});
			$(sel).slice(1).click(function(){
				if($(sel).slice(1).filter(":checked").length==0){
					$(sel).eq(0).prop("checked",false);
				}else{
					$(sel).eq(0).prop("checked",true);
				}
			});
			$(sel).eq(0).prop("checked",false);
		},
		
		/**
		 * 分页
		 * @param pageNo:当前页码
		 * @param pageSize:每页显示的条数
		 * @param total :总共页数
		 * @param sel：选择符
		 * @param callback:调用的函数
		 * @return
		 */
		 doPage:function(pageNo,pageSize,total,sel){
			  $(sel).smartpaginator({
			  		 totalrecords: Number(total), //总的条数
			  		 recordsperpage: Number(pageSize), // 每页显示的记录条数
			  		 length: 10,  //上下第一和最后一页的显示依赖这个参数。当length的值大于totalrecords/recordsperpage时，不显示。
			  		 initval:Number(pageNo),
			  		 next: 'Next', 
			  		 prev: 'Prev', 
			  		 first: 'First', 
			  		 last: 'Last', 
			  		 theme: 'gray', 
			  		 controlsalways: false, 
			  		 onchange: function (page) {
			  			 if($("#page_index")){
			  				$("#page_index").val(page);
			  			 }
		          	 	changePage(page);//必须调用的函数
		      		}
		      });
		},
		/**
		 * 设置文本框默认值，及状态
		 * @param sel(元素选择符)
		 */
	    textState:function(sel){
			if($(sel).val()==this.defaultValue){
				$(sel).css({color:'#4D4D4D',fontStyle:'italic'});
			}
			$(sel).focus(function(){
				if($(sel).val()==this.defaultValue){
					$(sel).val('').css({color:'',fontStyle:'normal'});
				}else{
					$(sel).select();
				}
			});
			$(sel).blur(function(){
				if($(sel).val()==''){
					$(sel).val(this.defaultValue).css({color:'#4D4D4D',fontStyle:'italic'});
				}
			});
		},
		/**
		 * 文本框自动聚焦
		 * @param ints:文本框数组对象
		 * @param mode：号码类型。如PDT,MPT，其他等.  
		 */
		autoFocus:function(ints,mode,type){
				var len=ints.length;
				if(mode==ssi.PDT && ssi.pdt_mode==1 && type==ssi.USR){//目前浙江拨号方式
					if( $(ints[0]).is(":focus") && (accipiter.getVal(ints[0])).length==3){
						$(ints[0]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
					}
				}else{//单插入
					if(mode==ssi.PDT || (mode==ssi.MPT && Number(ssi.mpt_mode)==0) ){
						if($(ints[0]).is(":focus") && (accipiter.getVal(ints[0])).length==3){
							$(ints[0]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if($(ints[1]).is(":focus") && (accipiter.getVal(ints[1])).length==2){
							$(ints[1]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if(len==5){
							if($(ints[2]).is(":focus") && (accipiter.getVal(ints[2])).length==3){
								$(ints[2]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
							if($(ints[3]).is(":focus") && (accipiter.getVal(ints[3])).length==2){
								$(ints[3]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
						}
						
					}else if(mode==ssi.MPT && Number(ssi.mpt_mode)==1){
						if($(ints[0]).is(":focus") && (accipiter.getVal(ints[0])).length==3){
							$(ints[0]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if($(ints[1]).is(":focus") && (accipiter.getVal(ints[1])).length==4){
							$(ints[1]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if(len==5){
							if($(ints[2]).is(":focus") && (accipiter.getVal(ints[2])).length==ssi.unlen){
								$(ints[2]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
							if($(ints[3]).is(":focus") && (accipiter.getVal(ints[3])).length==4){
								$(ints[3]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
						}
					}
				}
		},
		/** 
		 * js截取字符串，中英文
		 * @param str：需要截取的字符串 
		 * @param len: 需要截取的长度 
		 */  
		 cutstr:function(str,len) {   
		   var str_length = 0;  
		   var str_len = 0;  
		   str_cut = new String();  
		   str_len = str.length;  
		   for(var i = 0;i<str_len;i++) { 
		       var a = str.charAt(i);  
		        str_length++;  
		        if(decodeURI(a).length > 4)  { //中文字符的长度经编码之后大于4 
		        	str_length++;  
		         }  
		         str_cut = str_cut.concat(a);  
		         if(str_length>=len) {  
		        	 str_cut = str_cut.concat("..."); 
		        	 return str_cut;  
		         }  
		    }  
		    //如果给定字符串小于指定长度，则返回源字符串；  
		    if(str_length<len){  return  str;  } 
		},
		phoneRange:function(options){//号码范围
			var defaultOptions={
					mode:ssi.PDT,//PDT:0,MPT:1
					type:ssi.USR,//USR:0,GRP:1
					selector:"",//号码范围选择器（必填）
					emptyAddress:"",//隐藏空口地址Id（必填）
					errTip:"",
					btn:"",
					funcs:null,//新添加的函数
					callback_flag:0,//是否使用默认的函数（0：使用，1，不使用，调用funcs）
					flag:0,//0：查询，1：添加，
					selflag:0,//默认  0：只有pdt和mpt 1：有其他情况
					selopt:{title:"",value:"",callback:null}//添加的值
			};
			var options_=$.extend({},defaultOptions,options);
			var emptyAddress="";//空口地址值
			accipiter.phoneOptions(options_);
			var mode=options_.mode;
			var ints=$(options_.selector+" .ssi_input input");
				ints.keyup(function(){
					ints=$(options_.selector+" .ssi_input .ipt").filter(":visible").find("input");
					var selVal=$(options_.selector).find("select:first").val();
					switch (Number(selVal)) {
						case 1://pdt
							mode=ssi.PDT;
							break;
						case 2://mpt
						case 3:
							mode=ssi.MPT;
							break;
					}
					accipiter.autoFocus(ints,mode,options_.type);//自动聚焦
					var vals=[],totalEmpty=0;
					$.each(ints,function(index,value){
						vals[index]=accipiter.getVal(value);
						if(vals[index]!=""){totalEmpty++;}
					});
					var ai=[],start=[-1],end=[-1],err=0,
						inpLen=ints.length;
					//以下代码变动大（先分情况处理）
					switch(inpLen){
						case 2://单插入(区号-个号)
							start=accipiter.getssi(ints.slice(0,2),mode,options_.type);
							if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
								err=0;
								emptyAddress=start[2];
							}else{
								err=1;
							}
							break;
						case 3:
							if(ssi.pdt_mode==1 && options_.type==ssi.USR){//(多插入)目前浙江拨号方式
								start=accipiter.getssi(ints.slice(0,2),mode,options_.type);
								end=accipiter.getssi([ints[0],ints[2]],mode,options_.type);
								if(vals[2]=="" || (vals[2]!="" && vals[2]>=vals[1])){
									err=0;
									if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
										err=0;
										emptyAddress=start[2];
										if(vals[2]!=""){
											if(end[0]!=-1  && end[1]==options_.type && end[0]==mode){
												err=0;
												emptyAddress+="-"+end[2];
											}else{
												err=1;
											}
										}
									}else{err=1;}
								}else{
									err=1;
								}
							}else{//单插入
								start=accipiter.getssi(ints.slice(0,3),mode,options_.type);
								if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
									err=0;
									emptyAddress=start[2];
								}else{
									err=1;
								}
							}
							break;
						case 5://支持多插入
							start=accipiter.getssi(ints.slice(0,3),mode,options_.type);
							end=accipiter.getssi([ints[0],ints[3],ints[4]],mode,options_.type);
							if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
								err=0;
								emptyAddress=start[2];
								if(vals[3]!="" || vals[4]!=""){
									if(vals[3]>=vals[1] && vals[4]>=vals[2]){
										if(end[0]!=-1  && end[1]==options_.type && end[0]==mode){
											err=0;
											emptyAddress+="-"+end[2];
										}else{
											err=1;
										}
									}else{err=1;}
								}
							}else {
								err=1;
							}
							break;
					}
					if(totalEmpty==0){//当输入框全部为空
						err=2;
						if(options_.flag==0){//查询时
							if(options_.mode==0){
								emptyAddress="1048577-16777216";//pdt范围值
							}else if(options_.mode==1){
								emptyAddress="1-1048576";//mpt范围值
							}
						}else{
							emptyAddress="";
						}
					}
					if(options_["callback_flag"]==0){
						switch(err){
						case 1://错误时
							if($(options.emptyAddress).is("td")){
								$(options.emptyAddress).text("");
							}else if($(options.emptyAddress).is("input")){
								$(options.emptyAddress).val("");
							}
							$(options.errTip).text(accipiter.getLang("invalidno"));
							$(options.btn).hide();
							break;
						case 0://正确时
							if($(options.emptyAddress).is("td")){
								$(options.emptyAddress).text(emptyAddress);
							}else if($(options.emptyAddress).is("input")){
								$(options.emptyAddress).val(emptyAddress);
							}
							$(options.errTip).text("");
							$(options.btn).show();
							break;
						case 2://全部为空时
							if($(options.emptyAddress).is("td")){
								$(options.emptyAddress).text(emptyAddress);
							}else if($(options.emptyAddress).is("input")){
								$(options.emptyAddress).val(emptyAddress);
							}
							$(options.errTip).text("");
							if(options_.flag==0){//查询时
								$(options.btn).show();
							}else{
								$(options.btn).hide();
							}
						}
					}else{
						options_["funcs"].call(this,err,emptyAddress); //调用函数
					}
				});
			},
			getssi:function(sel,mode,type){
				var val=[],ai=[-1];
				$(sel).each(function(i){
					var v=accipiter.getVal(this);
					if(v!=''){val.push(v);}else{val.push(0);}
				});
				switch (mode) {
				case ssi.PDT:
					if(ssi.pdt_mode==1 && type==ssi.USR){
						ai=ssi.pdt_ai(val[0],val[1],null);
					}else{
						ai=ssi.pdt_ai(val[0],val[1],val[2]);
					}
					break;
				case ssi.MPT:
					ai=ssi.mpt_ai(val[0],val[1],val[2]);
					break;
				}
				return ai;
			},
			getType:function(mode,type){
				var str="";
				if(mode==ssi.UNKNW){
					str=accipiter.getLang("invaliduser");
				}else{
					if(mode==ssi.PDT){
						if(type==ssi.USR && ssi.pdt_mode==1){
							str=accipiter.getLang("zjdial");
						}else{
							str="PDT";
						}
					}else if(mode==ssi.MPT){
						str="MPT";
					}
				}
				return str;
			},
			phoneOptions:function(options){
				var html="",ini=ssi.mode[0];
				if(options["flag"]==0){//查询
					$(options.selector).find(".ssi_input").hide();
					html+="<option value=0>"+accipiter.getLang("all")+"</option>";
					ini=accipiter.getLang("all");
				}else if(options["flag"]==1){//插入
					$(options.selector).find(".ssi_input").show();
					if(ssi.pdt_mode==1 && options.type==ssi.USR){
						$(options.selector).find(".ssi_input .zj").hide();
					}
					if(ssi.pdt_mode==1 && options.type==ssi.USR){
						ini=ssi.mode[4];
					}
				}
				if(ssi.pdt_mode==1 && options.type==ssi.USR){
					 html+="<option value=1>"+ssi.mode[4]+"</option>";
				}else{
					 html+="<option value=1>"+ssi.mode[0]+"</option>";
				}
				if(ssi.mpt_mode==0){
					 html+="<option value=2>"+ssi.mode[1]+"</option>";
				}else if(ssi.mpt_mode==1){
					html+="<option value=3>"+ssi.mode[2]+"</option>";
				}
				if(options.selflag==1){
					html+="<option value="+options["selopt"]["value"]+">"+options["selopt"]["title"]+"</option>";
				}
				$(options.selector).find("select:first").append(html);
				if($.trim($(options.selector).find(".title:first").text())==""){
					$(options.selector).find(".title:first").text(ini);
				}
				$(options.selector+" .ssi_input input ").each(function(){
					$(this).val(this.defaultValue).css({color:'#4D4D4D',fontStyle:'italic'});
				});
				//初始化数据
				$(options.selector).find("select:first").change(function(){
					$(options.selector).find(".ssi_input input").each(function(){
						$(this).val(this.defaultValue).css({color:'#4D4D4D',fontStyle:'italic'});
					});
					var v=$(this).val(),emptyAddress="";
					switch (Number(v)) {
					case 0://全部
						$(options.selector).find(".ssi_input").hide();
						emptyAddress="";
						break;
					case 1:
						$(options.selector).find(".ssi_input").show();
						if(ssi.pdt_mode==1 && options.type==ssi.USR){
							$(options.selector).find(".ssi_input .zj").hide();
						}else{
							$(options.selector).find(".ssi_input .zj").show();
						}
						if(options.flag==0){
							emptyAddress="1048577-16777216";//pdt范围值
						}
						if(options.selflag==1){
							options["selopt"]["callback"].call(this,1);
						}	
						break;
					case 2:
					case 3:
						$(options.selector).find(".ssi_input").show();
						$(options.selector).find(".ssi_input .zj").show();
						if(options.flag==0){
							emptyAddress="1-1048576";//mpt范围值
						}
						if(options.selflag==1){
							options["selopt"]["callback"].call(this,1);
						}
						break;
					case 4://其他
						$(options.selector).find(".ssi_input").hide();
						emptyAddress="";
						if(options.selflag==1){
							options["selopt"]["callback"].call(this,0);
						}	
					}
					if($(options.emptyAddress).is("td")){
						$(options.emptyAddress).text(emptyAddress);
					}else if($(options.emptyAddress).is("input")){
						$(options.emptyAddress).val(emptyAddress);
					}
					$(options.errTip).text("");
					if(options.flag==1){
						$(options.btn).hide();
					}else{
						$(options.btn).show();
					}
				});
			},
			userAddress:function(v,type){//通过空口地址获取用户号码和用户类型
				var phone=ssi.ai_ssi(Number(v),type),addr=[];
				if(phone[0]!=ssi.UNKNW){
						if(ssi.pdt_mode==1 && type==ssi.USR && phone[0]==ssi.PDT){
							addr[0]=(phone[0]==ssi.UNKNW?accipiter.getLang("invalidno"):(phone[2]+'-'+phone[3]));
						}else{
							addr[0]=(phone[0]==ssi.UNKNW?accipiter.getLang("invalidno"):(phone[2]+'-'+phone[3]+'-'+phone[4]));
						}
						addr[1]=accipiter.getType(phone[0],type);
				}else{
					addr[0]=accipiter.getLang("invalidno");
					addr[1]=accipiter.getLang("invaliduser");
				}
				return addr;
			},
			back:function(options){//请求后返回提示
				var defaultOptions={
						tip:"",//提示内容
						funcs:null,
						flag:0,//0：使用默认的，1：创建新的
						type:0,//0:提示框，1：confirm框
						top:0,
						left:0,
						record:0,
						confirm_callback:null,//确定后的操作。当type结果为1，2时需要设置的
						cancel_callback:null//取消后的操作
				};
				var options_=$.extend({},defaultOptions,options);
				if(options_["flag"]==1){
					options_["funcs"].call(this); //调用函数(具体情况以后扩展)
				}else{
					var top=(options_["top"]==0?220:options_["top"]);
					var left=(options_["left"]==0?$("body").width()/2-$("body").offset().left-140:options_["left"]);
					if(options_["type"]==0){//简单提示
						var load='<div class="load_tip" style="z-index:100;width:280px;height:160px; border:1px solid #3E7FC3; background:#FFF; position:absolute; top:'+top+'px;left:'+left+'px;">' +
								'<p style="border-bottom:1px solid #3E7FC3; background:#78B8E4; height:25px; line-height: 25px; padding-left:5px; font-weight:bold;font-size:14px; color:#535252;">'+accipiter.getLang("t3")+'</p>' +
								'<div style=" text-align:center; line-height:130px; font-size:14px; color:#535252; " >' +
								''+options_["tip"]+''+
								'</div>'+
								'</div>';
						var shadow='<div class="shadow" style="position:fixed;width:100%; height:100%;filter:alpha(opacity=50);opacity:.5; '+
						'background-color:#CCC;z-index:20;left:0; top:0;"></div>	';
						$("body").append(load).append(shadow);
						setTimeout(function(){$("body").find(".load_tip,.shadow").remove();},1000);
					}else if(options_["type"]==1){//确认框（confirm）
						var load='<div class="load_tip" style="z-index:100;width:280px;height:160px; border:1px solid #3E7FC3; background:#FFF; position:fixed; top:'+top+'px;left:'+left+'px;">' +
						'<p style="border-bottom:1px solid #3E7FC3; background:#78B8E4; height:25px; line-height: 25px; padding-left:5px; font-weight:bold;font-size:14px; color:#535252;">'+accipiter.getLang("t3")+'</p>' +
						'<div style=" text-align:center; margin:28px auto; font-size:14px; height:34px;  padding:0 15px; color:#535252;" >' +
						''+options_["tip"]+''+
						'</div>'+
						'<div style="text-align:center; "><button>'+accipiter.getLang("t4")+'</button> <button>'+accipiter.getLang("t5")+'</button> </div>'+
						'</div>';
						var shadow='<div class="shadow" style="position:fixed;width:100%; height:100%;filter:alpha(opacity=50);opacity:.5; '+
							'background-color:#CCC;z-index:20;left:0; top:0;"></div>	';
						$("body").append(load).append(shadow);
						$("body").find(".load_tip button:eq(0)").click(function(){//确认
							$(".load_tip,.shadow").remove();
							if(options_["confirm_callback"]){
								options_["confirm_callback"].call(this);
							}
						});
						$("body").find(".load_tip button:eq(1)").click(function(){//取消
							$(".load_tip,.shadow").remove();
						});
					}else if(options_["type"]==2){//重复添加覆盖提示框
						var load='<div class="load_tip" style="z-index:100;width:280px;height:185px; border:1px solid #3E7FC3; background:#FFF; position:fixed; top:'+top+'px;left:'+left+'px;">' +
						'<p style="border-bottom:1px solid #3E7FC3; background:#78B8E4; height:25px; line-height: 25px; padding-left:5px; font-weight:bold;font-size:14px; color:#535252;">'+accipiter.getLang("t3")+'</p>' +
						'<div style=" text-align:center; margin:28px auto; font-size:14px; height:34px;  padding:0 15px; color:#535252;" >' +
						''+options_["tip"]+''+
						'</div>'+
						'<div style="text-align:center"; ><button>'+accipiter.getLang("yes")+'</button> <button>'+accipiter.getLang("no")+'</button> </div>'+
						'<div style="padding-top:20px"; ><input type="checkbox"/>'+accipiter.getLang("after")+'<b> '+options_["record"]+'</b> '+accipiter.getLang("check")+'</div>'+
						'</div>';
						var shadow='<div class="shadow" style="position:fixed;width:100%; height:100%;filter:alpha(opacity=50);opacity:.5; '+
							'background-color:#CCC;z-index:20;left:0; top:0;"></div>	';
						$("body").append(load).append(shadow);
						$("body").find(".load_tip button:eq(0)").click(function(){//确认
							if(options_["confirm_callback"]){
								options_["confirm_callback"].call(this,$(".load_tip :checkbox").is(":checked"));
							}
							$(".load_tip,.shadow").remove();
						});
						$("body").find(".load_tip button:eq(1)").click(function(){//取消
							if(options_["cancel_callback"]){
								options_["cancel_callback"].call(this,$(".load_tip :checkbox").is(":checked"));
							}
							$(".load_tip,.shadow").remove();
						});
					}
				}
			},
			progress:function(flag){//进度
				var load='<div class="progress" ><span><span></div>';
				if(flag){
					$("body").append(load);
				}else{
					if($(".progress")){
						$(".progress").remove();
					}
				}
			},
			loadding:function(flag){//进度
				var load='<div class="loadding" ></div>';
				if(flag){
					$("body").append(load);
				}else{
					if($(".loadding")){
						$(".loadding").remove();
					}
				}
			},
            resultMessage:function(message, duration) {
                if(!duration) duration = 1000;  //设置参数duration默认值为1000
                //duration = arguments[1] ? arguments[1] : 1000;
                new jBox('Notice', {
                    content: message,
                    autoClose:duration,
                    attributes: {
                        x: 'left',
                        y: 'top'
                    },
                    position: {  // The position attribute defines the distance to the window edges
                        x: $(document).width()/2,
                        y: $(document).height()/2
                    }
                });
            },
			getCookie:function(name){//获取cookie值
				var c=document.cookie;
				if(c){
					var s=new RegExp(name+"=([^;]*)");
					var cc=c.match(s);
					if(cc){return cc[1];}else{return null;}
				}
			},
			getLocale:function(){//获取本地话设置的语言，值en_US/zh_CN
				var c=document.cookie;
				var rtn=1;//English。
				if(c){
					var s=/myLocaleCookie=([^\;]*)/;
					var cc=c.match(s);
					if(cc){
					 
						return cc[1]=="en_US"?1:0;
					}else{	//语言设置为默认时(c不存在时)，默认为中文	
			 
						return rtn;
					}
				}
				return rtn;
			},
			getLang:function(tip){//公共语言对象切换
		 
				if(accipiter.getLocale()==0){
					return lang[tip][0];
				}else{
					return lang[tip][1];
				}
			},
			getLang_:function(la,tip){//自定义语言对象切换
				if(accipiter.getLocale()==0){
					return la[tip][0];
				}else{
					return la[tip][1];
				}
			},
			gc:function(key){
				if(accipiter.getCookie("pdt")){
					return JSON.parse(accipiter.getCookie("pdt"))[key];
				}
			},
			getNowTime:function(){//默认获取当前时间，格式yyyy-m-d h:m:s
					var d=new Date();
					var year=d.getFullYear();
					var month=Number(d.getMonth())+1;month=(month<10?'0'+month:month);
					var day=d.getDate();day=(day<10?'0'+day:day);
					var hour=d.getHours();hour=(hour<10?'0'+hour:hour);
					var min=d.getMinutes();min=(min<10?'0'+min:min);
					var sec=d.getSeconds();sec=(sec<10?'0'+sec:sec);
					return year+'-'+month+'-'+day+" "+hour+':'+min+':'+sec;
			},
			getGroup_type_select:function(selector){//初始加载组属性
				var selector=selector || "#group_type_select",html="";
				html='<option value=0  selected >'+accipiter.getLang('partin')+'</option>'+
				     '<option value=1   >'+accipiter.getLang('respin')+'</option>' + 
				     '<option value=2   >'+accipiter.getLang('backin')+'</option>' ;
				$(selector).find("select").html(html);
				$(selector).find(".title").text(accipiter.getLang('partin'));
			},
			getGroup_type:function(val){//组属性
				var val=val||0;
				switch(Number(val)){
					case 0: return accipiter.getLang('partin');break;
					case 1: return accipiter.getLang('respin');break;
					case 2: return accipiter.getLang('backin');break;
				}
			},
			getVpnList:function(selector,flag){//加载vpn
				parm={
						"ip":accipiter.getIp(),
						"vpn_index":"-1"
					};
				 var jq=accipiter.doPost(accipiter.getRootPath()+"/rest/ajax/configuration/vpn/findList",parm);
				jq.done(function(data){
					if(data.status=="000000" && data.content.length>0){
							var html="";
							flag=flag||0;//默认添加修改
							if(flag==1){
								html+="<option value=-1>"+accipiter.getLang("unlimited")+"</option>";
								$(selector).find(".title").text(accipiter.getLang("unlimited"));
							}else{
								$(selector).find(".title").text(data.content[0].name);
							}
							$(data.content).each(function(i){
								html+="<option value="+data.content[i].vpn_index+">"+data.content[i].name+"</option>";
							});
							$(selector).find("select").append(html);
					}
				}); 	
			},
			getPriority_select:function(selector,flag){//初始优先等级//0-15
				var html="";
				flag=flag||0;
				if(flag==1){//查询。
					html+="<option value=-1>"+accipiter.getLang("unlimited")+"</option>";
					$(selector).find(".title").text(accipiter.getLang("unlimited"));
				}else{
					$(selector).find(".title").text(0+accipiter.getLang("level"));
				}
				for(var i=0;i<16;i++){
					html+="<option value="+i+">"+i+accipiter.getLang("level")+"</option>";
				}
				$(selector).find("select").append(html);
				
			},
			getGrpPriority_select:function(selector,flag){//组初始优先等级//
				var html="";
				flag=flag||0;
				if(flag==1){//查询。
					html+="<option value=-1>"+accipiter.getLang("unlimited")+"</option>";
					$(selector).find(".title").text(accipiter.getLang("unlimited"));
				}else{
					$(selector).find(".title").text(accipiter.getLang("normal"));
				}
				html+='<option value=0 >'+accipiter.getLang('normal')+'</option>' + 
			          '<option value=1 >'+accipiter.getLang('priority')+'</option>' ;
				$(selector).find("select").append(html);
			}
	});
})(jQuery);

$(function(){
	$("button,a,input[type=radio]").focus(function(){//去除虚线框
		$(this).blur();
	});
	$(".sel select").change(function(obj){//下拉列表框触发事件
		var target=obj.target;
		var o=$(this);
		$.each(target,function(i){
			var selected=target[i].selected;
			if(selected){
				var text=target[i].text;
				$(o).parent().find(".title").text(text);
			}
		});
	});
});
lang={
		 
		"t1":["请求失败","Failed to request"],
		"t2":["网络异常","Network exception"],
		"t3":["提示","Prompt"],
		"t4":["确认","Confirm"],
		"t5":["取消","Cancle"],
		"t6":["服务器连接错误","Server connection error"],
		"t7":["服务器内部错误","Internal Error Code 500"],
		"success":["操作成功!","Operation successfully!"],
		"fail":["操作失败!","Operation failed!"],
		"dowloadFail":["下载失败！","Failed to download	"],
		"dowloadAndReset":["下载成功，是否立即复位！","Downloaded successfully, reset now or not!"],
		"deleTip":["请选择要删除的记录.","Please selected record to be deleted."],
		"verifyDelete":["确定删除？","Confirm to delete ?"],
		"verifyAdd":["确定添加？","Confirm to add?"],
		"verifyUpdate":["确认更改？","Confirm to update?"],
		"noData":["无数据","No data"],
		"delete":["删除","Delete"],
		"detail":["详情","Details"],
		"update":["更新","Update"],
		"invalidno":["无效号码","Invalid No"],
		"invaliduser":["无效用户","Invalid user"],
		"all":["全部","All"],
		"other":["其他","Others"],
		"exist":[" 已经存在","Existed"],
		"unlimited":["不限","Unlimited"],
		"level":["级"," Level"],
		"replace":["是否替换？","Replace or Not"],
		"yes":["是","Yes"],
		"no":["否","No"],
		"after":["之后","After"],
		"check":["记录执行相同操作","Record same operation"],
		"partin":["参与组","Participate "],
		"respin":["响应组","Response "],
		"backin":["背景组","Backgroup "],
		"enable":["启用","Enable"],
		"disable":["禁用","Disable"],
		"normal":["普通","Normal"],
		"priority":["优先","Priority"],
		//====================================================
		"next":["下一页","Next"],
		"prev":["上一页","Prev"],
		"first":["首页","First"],
		"last":["尾页","Last"],
		"go":["跳转","Go"],
        "selectAll":["全选","Select All"],
		"loading":["正在提交，请稍等...","In submitting, pleas wait..."],
		"inputError":["输入有误，请先更正。","Wrong input, please correct."],
		"deleteArea":["要删除该用户级别及所有子用户级别项吗？","Are you sure to delete the area and all child items?"],
	 
		"deleteMenu":["要删除该菜单及所有子菜单项吗？","Confirm to delete menu and all sub menus?"],
		"deleteUser":["确认要删除该用户吗？","Confirm to delete user?"],
		"deleteRole":["确认要删除该角色吗？","Confirm to delete role?"],
		"deleteDict":["确认要删除该字典吗？","Confirm to delete dictionary?"],
		"userExist":["用户登录名已存在","User login name existed"],
		"operatoridExist":["运营商ID已存在","Operator ID existed"],
		"adTypetIdExist":["广告类型ID已存在","Display Style ID existed"],
		"adPositiontIdExist":["广告坐标ID已存在","Position ID existed"],
		"adComboIdExist":["广告套餐ID已存在","Package ID existed"],
		"adComboNameExist":["广告套餐名称已存在","Package Name existed"],
		"adNameExist":["广告名称已存在","Advertisement Name existed"],
		"categoryIdExist":["广告分类Id已存在","Category Id existed"],
		"advertiserIdExist":["广告商ID已存在","Advertiser ID existed"],
		"advIdExist":["广告ID已存在","Advertisement ID existed"],
		"programIdExist":["外部程序ID已存在","External Program ID existed"],
		"officeExist":["用户级别名称已存在","User level name existed"],
		"adSelltIdExist":["该流水号已存在","SN. existed"],
		"passwordConfirm":["两次输入密码不一致","Two input passwords are not consistent"],
		"close":["关闭","Close"],
		"importData":["导入数据","Import data"],
		"systemPrompt":["系统提示","System Prompt"],
		"export.adopertator.Data":["确认要导出电视运营商数据吗？","Confirm to export operator data?"],
		"export.combo.Data":["确认要导出套餐数据吗？","Confirm to export combo data?"],
		"exportData":["确认要导出用户数据吗？","Confirm to export user data?"],
		"exportadvertisserData":["确认要导出广告商数据吗？","Confirm to export advertiser data?"],
		"exportSysLogData":["确认要导出系统日志数据吗？","Confirm to export log data?"],
		"importFormat":["导入文件不能超过1M，仅允许导入“xls”或“xlsx”格式文件！","File size to be imported can't exceed 1M, only xls and xlsx are permitted!"]	,
		"nameExists":["角色名已存在","Role name existed"],
		"assignRoles":["分配角色","Role Assignment"],
		"rolesToUsers":["通过选择用户级别，然后为列出的人员分配角色。","Select user level, then assign role for users in the list."],
		"notRole":["未给角色","Role not assigned"],
		"newMember":["分配新成员！","Assign new member"],
		"sure":["确定要将用户<b>","Confirm to remove user <b>"],
		"from":["</b>从 <b>","</b> from <b>"],
		"roleRemoved":["</b>角色中移除吗？","</b> role is removed？"],
		"total":["共","Total"],
		"onlyDeleteNew":["只能删除新添加人员！","Only new user permitted to be deleted"],
		"clearSuccess":["已选人员清除成功！","Selected users deleted successfully"],
		"cancelDelete":["取消清除操作！","Cancel delete"],
		"clearSure":["清除确认","Confirm to delete"],
		"sureClearRole":["确定清除角色【","Determine the selected persons under the ["],
		"NoRole":["未给角色【","No ["],
		"assignMember":["】分配新成员！","]for role Assign new members!"],
		"selectPeople":["】已选人员？","]role?"],
		"diplayTimeOutPlayTime":["展示总时间超出播放时间","Display total time out of play time"],
		"inputValueRange":["输入值的范围：","Range of input values:"],
		"changeTypeData":["该类型坐标数据为空，请设置改类型数据。","This type of coordinate data is empty, please change the type data."],
		"xminlessmax":["横坐标最小值不能大于最大值","Min of X can't be greater than max of X"],
		"yminlessmax":["横坐标最小值不能大于最大值","Min of Y can't be greater than max of Y"],
		"IdNotUnique":["id不唯一","ID is not unique"],
		"authList":["权限列表","Right List"],
		"message":["消息","message"],
		"clear":["清除","Clear"],
		"leveCodeExists":["用户级别编号已存在","User level code existed"],
		"leveNameExists":["用户级别名称已存在","User level name existed"]
};
jsLang={
		"required":["必须填写.","This field is required."],
		"remote":["请修复此项.","Please fix this entry."],
		"email":["请输入合法的Email地址.","Please enter a valid email address."],
		"url":["请输入合法的URL地址.","Please enter a valid URL."],
		"date":["请输入合法的日期.","Please enter a valid date."],
		"dateISO":["请输入合法的国际标准日期.","Please input valid UTC date."],
		"number":["请填写合法的数字.","Please enter a valid number."],
		"digits":["只允许输入数字.","Please enter only digits."],
		"creditcard":["请输入合法的信用卡号码.","Please enter a valid credit card number."],
		"equalTo":["请再次输入相同的值.","Please enter the same value again."],
		"maxlength":["请输入最多 {0} 个字符.","Please enter no more than {0} characters."],
		"minlength":["请输入最少 {0} 个字符.","Please enter at least {0} characters."],
		"rangelength":["请输入一个在 {0} 和 {1} 长度的字符.","Please enter a value between {0} and {1} characters long."],
		"range":["请输入 {0} 至 {1} 之间的值.","Please enter a value between {0} and {1}."],
		"max":["请输入小于或等于 {0} 的值.","Please enter a value less than or equal to {0}."],
		"min":["请输入大于或等于 {0} 的值.","Please enter a value greater than or equal to {0}."],
		"total":["共","in total"],
		"accpet":["请输入拥有合法后缀名的字符串","Please enter a string with a valid suffix"],
		"ip":["请输入合法的IP地址","Please input valid IP address"],
		"abc":["请输入字母数字或者下划线","Please input character, number or underline"],
		"username":["3-20位字母或数字开头，允许字母数字下划线","33~20, start with char or number, permitted to input char, number and underline"],
		"noEqualTo":["请再次输入不同的值","Please enter a different value again"],
		"gt":["请输入更大的值","Please input a greater value"],
		"lt":["请输入更小的值","Please input a smaller value"],
		"realName":["姓名只能为2-30个汉字","Name can only be 2-30 Chinese characters"],
		"userName":["登录名只能包括中文字、英文字母、数字和下划线","Login names can only include Chinese characters, English letters, numbers, and underscores"],
		"mobile":["请正确填写您的手机号码","Please fill in your phone number"],
		"simplePhone":["请正确填写您的电话号码","Please fill in your phone number"],
		"phone":["格式为:长度不超过15位电话号码","Format: Length is not more than 15 phone number"],
		"zipCode":["请正确填写您的邮政编码","Please fill in your zip code"],
		"qq":["请正确填写您的QQ号码","Please fill in your QQ number correctly"],
		"card":["请输入正确的身份证号码(15-18位)","Please enter the correct ID number (15-18)"],
		"formatNoMatches":["没有找到匹配项","No item matched"],
		"formatInputTooShort":["请再输入","Please enter another"],
		"formatInputTooLong":["请删掉","Please delete"],
		"unitchar":["个字符","characters"],
		"formatSelectionTooBig":["你只能选择最多","You can select up to"],
		"term":["项","items"],
		"formatLoadMore":["加载结果中...","Loading results ..."],
		"formatSearching":["搜索中...","Searching..."],
		"webName":["请输入合法的网址","Please enter a valid URL"]
		
};
messageLang={
		"verification.code.error":["验证码不正确.","Wrong CAPTCHA."],
		"set.upload":["点击上传","Click upload"],
		"adv.Ather":["没有信号","null"],
		"help.alarm":["当前没有帮助文档，请联系管理员","Currently there is no help document, please contact your administrator"],
		"set.picture":["图片","Picture"],
		"channelNoSelected":["（该频道不可用）","This channel is not available"],
		"file.max":["已达到文件最大选择数！","Maximum number of files selected"],
		"import.confirm":["确定","confirm"],
		"image.upload.fail":["图片文件上传失败!","Image file upload failed!"],
		"click.add.vedio":["请点击添加广告视频","Click to add video"],
		"sell.startDate":["生效开始时间必选","Valid start date must be selected"],
		"adv.type":["广告套餐:","Advertisement Package:"],
		"right.program":["请正确输入程序名称。例：ffmpeg","null"],
		"vedio.maxsize":["已达到最大视频上传数目","Max number of videos to upload is reached"],
		"add.image":["请拖拽或点击上传图片","Drag or click to upload picture"],
		"end.compare.start":["结束时间必须大于开始时间","End Time must be greater than start time"],
		"import.preview":["上传文件预览","Upload file preview"],
		"import.limit":["必须以导入模板的格式导入,导入文件不能超过1M，仅允许导入xls或xlsx格式文件","Have to import the template format to import, import file cannot be more than 1 m, only allowed to import the XLS or XLSX format file"],
		"image.maxsize":["已达到最大图片上传数目","Max number of pictures to upload is reached"],
		"import.close":["关闭","close"],
		"channel.select":["频道选择","Select Channel"],
		"remove.vedio":["移除视频","Remove video"],
		"select.endDate":["请选择结束日期","Please select end date"],
		"select.endTime":["请选择结束时间","Please select end time"],
		"adv.nochannel":["频道设置不能为空","Channel settings cannot be empty"],
		"adv.noChannelInfo":["没有频道","No Channel"],
		"add.vedio":["请拖拽或点击上传视频","Drag or click to upload video"],
		"adv.PositionX":["起点和终点x坐标必须相等","Start and end coordinates x value must be equal"],
		"adv.PositionY":["起点和终点y坐标必须相等","Start and end coordinates y value must be equal"],
		"adv.count":["套餐投放个数","Set number of packages"],
		"adcombo_select":["请先选择广告套餐","Please select package firstly"],
		"combo.sell.count":["套餐销售总数量","Total number of advertising package sales"],
		"combo.sell.analyze":["套餐销售统计分析","Advertising package sales statistics analysis"],
		"selectedRole":["没有找到相关角色","No related role found"],
		"select.complete.start":["请先选择完整的开始时间","Please select complete start time"],
		"sell.endDate":["生效结束时间必选","Valid end date must be selected"],
		"import.model":["下载数据导入模板","Download data import template"],
		"please_select_start":["请先选择开始时间","Please select start date"],
		"import.attention":["注意","Attention"],
		"cancle.upload":["取消上传","Cancel upload"],
		"set.fontStyel":["字体格式","Font Style"],
		"set.fontSize":["字体大小","Font Size"],
		"set.isBold":["是否加粗","Font Bold"],
		"set.isItalic":["是否倾斜","Font Italic"],
		"import.remove":["移除文件","Remove the file"],
		"set.fontColor":["字体颜色","Font color"],
		"remove.image":["移除图片","Remove picture"],
		"select.endsecond":["请选择结束时间点","Please select end time"],
		"adv.start.date":["广告开始时间不能为空","Start date can't be empty"],
		"select.endminute":["请先选择结束时刻","Please select start minute"],
		"network.list":["发送器列表","Broadcasting system List"],
		"networkConfig":["所选发送器与已有套餐冲突","The selected NetWork conflicts with existing packages"],
		"file.upload.fail":["文件上传失败","Failed to upload file"],
		"set.colorValue":["颜色值","Color value"],
		"vedio.too.big":["广告视频过大","Video is too large"],
		"import.data":["导入数据","Import Data"],
		"file.videoUpLoad":["支持MPEG2、H.264编码的ts,mp4,avi文件上传","Support MPEG2, H.264 encoding ts, MP4, AVI file upload"],
		"file.remove":["移除文件","Remove file"],
		"middle.one":["中的一种",""],
		"select.endhour":["请先选择结束时间点","Please select end hour"],
		"endDate.compare.start":["生效结束时间必须大于生效开始时间","Valid end date must be greater than valid start date"],
		"exception":["程序出现异常","Program exception"],
		"adv.findone":["该时间段内已经存在广告，请重新选择播放时间段","The time period has been advertising, please select the playback time"],
		"network.notdeploy":["暂未部署广告发送器","Broadcasting system not deployed"],
		"adv.nonetwork":["发送器设置不能为空","Network settings cannot be empty"],
		"adv.nosell":["未销售","Not sell"],
		"upload.picture.preview":["上传图片预览：","Upload picture preview:"],
		"adv.Scale":["百分比(%)","Percentage(%)"],
		"adv.end.date":["广告结束时间不能为空","End date can't be empty"],
		"input.password":["请填写密码.","Please input password."],
		"input.username":["请填写用户名.","Please input user name."],
		"advTypeSelect":["请选择广告类型","Please select the type of advertisement"],
		"advChildTypeSelect":["请选择子广告类型","Please select the subtype of advertisement"],
		"adv.sell":["已销售","Already sold"],
		"uploaderrInfo":["图片尺寸不符合要求","Picture size does not meet the requirements"],
		"adv.putScale":["投放广告占比","Advertising accounted for"],
		"adv.size":["尺寸:","size:"],
		"set.uploadPicture":["上传图片","Upload pictures"],
		"select.starthour":["请选择开始时间点","Please select start hour"],
		"adv.adtype":["广告类型:","Display Styles :"],
		"adv.noSignal":["没有信号","No Signal"],
		"userform.select":["--请选择--","--Select--"],
		"wait.network":["请等待网络部署完成","Please wait for deployment"],
		"set.bgStyle":["背景样式","Background"],
		"file.upload":["拖拽或点击上传","Drag or click to upload"],
		"other":["其它","Others"],
		"comid.already.select":["当前广告套餐在你所选择的时间范围内已经销售","Your selected time range of this package has been sold out"],
		"vedio.upload.fail":["视频文件上传失败!","Failed to upload video"],
		"file.maxuploadFile":["你不能上传更多的文件","More files can’t be uploaded"],
		"systemPrompt":["系统提示","System Message"],
		"file.text":["文字信息不能为空","Text messages can't be empty"],
		"set.color":["纯色","Solid color"],
		"set.transparent":["透明","Transparent "],
		"select.startDate":["请选择开始日期","Please select start date"],
		"select.startTime":["请选择开始时间","Please select start time"],
		"image.too.big":["广告图片过大","Picture is too large"],
		"nochannel":["当前没有频道数据","No channel data"],
		"file.upload.success":["文件上传成功","File uploaded successfully"],
		"image.must.be":["选择文件错误,图片类型必须是","Select file error, image type must be one of the "],
		"playtime":["播放时间：","Play Time:"],
		"adv.type.id.repeat":["同一级的广告类型ID已存在","The same level of advertising type ID already exists"],
		"file.too.big":["上传文件过大","upload the file is too large"],
		"program.name":["节目名称：","Program name:"],
		"adv.analyze":["销售时段分析","Time Range Analysis of Sales"],
		"set.bgHeight":["背景图高度","Background image height"],
		"set.bgWidth":["背景图宽度","Background image width"],
		"uplpad.error":["上传出错，请仔细核实上传信息!","Upload error, please check information carefully"],
		"adv.sellInfo":["销售详情","Sales details"],
		"adv.comboInfo":["套餐详情","AdCombo details"],
		"adv.advInfo":["广告详情","Adelement details"],
		"check.addtext.exception":["请输入有效的链接网址","Please input valid web link"],
		"input.verification.code":["请填写验证码.","Please input CAPTCHA."],
		"advTimePlay":["请选择完整播放时间段","Please select complete display time range"],
		"adv.noAuthorization":["没有授权","No Authorization"],
		"uplpad.empty":["图片不能为空","Pictures can't be empty"],
		"adv.hd":["高清","High definition"],
		"adv.standard":["标清","Standard definition"],
		"file.parameter":["请设置上传索材参数","Please set up the upload SuoCai parameters"],
		"file.notNull":["上传文档不能为空","Upload document can not be empty"],
		"parameter.widthMin":["请输比最大宽度值小或等于的值","Please lose than the maximum width smaller or equal to the value"],
		"parameter.widthMax":["请输比最小宽度值大或等于的值","Please lose value is equal to or greater than the minimum width values"],
		"parameter.heightMin":["请输比最大高度值小或等于的值","Please lose than smaller or equal to the value of the maximum height value"],
		"parameter.heightMax":["请输比最小高度值大或等于的值","Please lose value is equal to or greater than the minimum height value"],
		"parameter.or":["或","or"],
		"parameter.format":["文件支持格式:","File format support:"],
		"System.time":["系统时间:","System Time:"],
		"showParam.notNull":["显示时间设置不能为空:","Display time settings cannot be empty"],
		"resources.notNull":["图片或视频选择不能为空","Pictures or video selection cannot be empty"],
		"SDresources.notNull":["标清图片或视频选择不能为空","The clear picture or video option cannot be empty"],
		"HDresources.notNull":["高清图片或视频选择不能为空","High-definition pictures or video selection cannot be empty"],
		"operator.notNull":["至少选择一个运营商","Select at least one operator"],
		"delete_checked_operators":["确认删除所选的运营商","Confirm delete the selected operators"],
		"outNCRange":["所选素材超出通道内存的范围","Beyond the range of channel memory"],
		"cron_info":["0 59 23 * * ? 表示在每天的23点59分执行一次数据库备份操作,0 */20 11 * * ? 表示在每天的11点每隔20分钟分执行一次数据库备份操作","0 59 23 * * ?: Perform a database backup operation at 23:59 every day,  0 */20 11 * * ?: Perform a database backup operation at 11:00 am every 20 minutes"],
	    "combo.sell.types.advtiser":["各广告类型广告商统计","Advertisers in different types of advertising statistics"],
	    "advtiser.rate.analyze":["广告商占比","advertisers rate analysis"],
	    "combo.sell.valid.time.analyze":["各类型广告可运营时间与售出时间统计","The valid time and sales time statistics of types of advertisements"],
	    "time.hour":["时间 (小时)","time (h)"],
	    "combo.valid.time":["可运营时间","operational(valid) time"],
	    "combo.sell.time":["已销售时间","sales time"],
	    "districtCategory.select":["选择区域","Select District"],
	    "all.districtCategory":["所有区域","All District"],
	    "hot.districtCategory":["热门区域","Hot District"],
	    "search":["请搜索...","Please search..."],
		"unit.day":["单位 (天)","unit (day)"],
		"no.operatirs":["所选区域无运营商，请重新选择","There is no operator in the selected district, please choose again."],
        "no.networks":["该运营商不支持所选区域","This operator does not support the selected district."],
        "outTimeSet":["超出每轮时间设置上限","Exceeds the time limit set for each round."],
        "improveAdCombo":["请完善套餐相关参数","Please improve the relevant parameters of the package."],
        "batch.set":["批量设置时间","Batch setting time"],
        "selectd.time":["已选择时间","Selected time."],
        "remaining.time":["剩余时间","Remaining time"]
};

 
