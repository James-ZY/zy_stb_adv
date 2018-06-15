<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框"%>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="不允许选择根节点"%>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="不允许选择父节点"%>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）"%>
<%@ attribute name="selectScopeModule" type="java.lang.Boolean" required="false" description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="nodesLevel" type="java.lang.String" required="false" description="菜单展开层数"%>
<%@ attribute name="nameLevel" type="java.lang.String" required="false" description="返回名称关联级别"%>
<div class="input-append">
	<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"${disabled eq 'true' ? ' disabled=\'disabled\'' : ''}/>
	<input id="${id}Name" name="${labelName}" readonly="readonly" type="text" value="${labelValue}" maxlength="50"${disabled eq "true"? " disabled=\"disabled\"":""}"
		class="${cssClass}" style="${cssStyle}"/><a id="${id}Button" href="javascript:" class="btn${disabled eq 'true' ? ' disabled' : ''}"><i class="icon-search"></i></a>&nbsp;&nbsp;
</div>
<script type="text/javascript">
			function getUserRole(){
				
				var id=$("#companyId").val();
				var html='';
				var post={"officeId":id};
				var postData = JSON.stringify(post);
					$.ajax({
					type:"POST",
					url:"${ctx}/sys/user/getUserType",
					data:postData,
					contentType : "application/json; charset=utf-8",
					dataType:"json",
					success:function(data){
						 var friends = $("#userType");
						 var advertiser = $("#advertiser");
						friends.empty();
						advertiser.empty();
						if(data != null){
							var type = data.userType;						
							var option = $("<option>").text(data.typeName).val(data.typeId);
							friends.append(option);
							var list = data.advertiserList;
							var a=document.getElementById("isadmin");						 
							if(a != null){						 
								if( data.isSetAdmin != null && data.isSetAdmin == true){							 
									$('#isadmin').css("display", "block");
								}else{								 
									$('#isadmin').css("display", "none");
								}
							} 
							if (list != null && list.length > 0) {
								var userIsAdv = data.userIsAdv;
								if(userIsAdv == "false"){
									var option1 = $("<option :selected>").text(
											$.i18n.prop("userform.select")).val("");
									advertiser.append(option1);
								}
								for (var i = 0; i < list.length; i++) {
									var option3 = $("<option>").text(list[i].name)
											.val(list[i].id);
									advertiser.append(option3);
									
								}
								$('#userAdv').css("display", "block");
						
							} else {
								$('#userAdv').css("display", "none");
							}
							var listHtml="";
							if(null == data.roleList || data.roleList.length==0 || data.roleList==undefined){
							listHtml='<label class="error">角色信息为空</label>';
							}else{
							$.each(data.roleList,function(commentIndex,comment){
							var roleIdList='roleIdList'+commentIndex;
							    listHtml+='<span><input class="required" id='+roleIdList+' type="checkbox" name="roleIdList" value='+comment["roleId"]+'><label for="roleIdList">'+comment["roleName"]+'</label></span>';					
							})
							}
							$(".selectRole").html("");
							$(".selectRole").html(listHtml);
						}else{
							$('#userAdv').css("display", "none");
						}
						friends.select2();
						advertiser.select2();
					 
					},
					error:function(data){
						$('#userAdv').css("display", "block");
						console.log("error");
						console.log(data);
					}
				});
					 
			}
	$("#${id}Button").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Id").attr("disabled")){
			return true;
		}
        var nameLevel = ${nameLevel eq null ? "1" : nameLevel};
		// 正常打开	
		top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("${url}")+"&module=${module}&checked=${checked}&extId=${extId}&nodesLevel=${nodesLevel}&selectIds="+$("#${id}Id").val(), "<spring:message code='treeselect.selection' />"+" "+"<spring:message code='${title}' />", 300, 420, {
			buttons:{"<spring:message code='treeselect.confirm' />":"ok", ${allowClear?"\"<spring:message code='treeselect.clear' />\":\"clear\", ":""}"<spring:message code='treeselect.colse' />":true}, submit:function(v, h, f){
				if (v=="ok"){
					var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
					var ids = [], names = [], nodes = [];
					if ("${checked}" == "true"){
						nodes = tree.getCheckedNodes(true);
					}else{
						nodes = tree.getSelectedNodes();
					}
					for(var i=0; i<nodes.length; i++) {//<c:if test="${checked}">
						if (nodes[i].isParent){
							continue; // 如果为复选框选择，则过滤掉父节点
						}//</c:if><c:if test="${notAllowSelectRoot}">
						if (nodes[i].level == 0){
							top.$.jBox.tip($.i18n.prop("choose.rootnode.fail")+nodes[i].name+$.i18n.prop("choose.reselect"));
							return false;
						}//</c:if><c:if test="${notAllowSelectParent}">
						if (nodes[i].isParent){
							top.$.jBox.tip($.i18n.prop("choose.fnode.fail")+nodes[i].name+$.i18n.prop("choose.reselect"));
							return false;
						}//</c:if><c:if test="${not empty module && selectScopeModule}">
						if (nodes[i].module == ""){
							top.$.jBox.tip($.i18n.prop("choose.common.fail")+nodes[i].name+$.i18n.prop("choose.reselect"));
							return false;
						}else if (nodes[i].module != "${module}"){
							top.$.jBox.tip($.i18n.prop("choose.model.fail")+$.i18n.prop("choose.reselect"));
							return false;
						}//</c:if>
						ids.push(nodes[i].id);
                        var t_node = nodes[i];
                        var t_name = "";
                        var name_l = 0;
                        do{
                            name_l++;
                            t_name = t_node.name + " " + t_name;
                            t_node = t_node.getParentNode();
                        }while(name_l < nameLevel);
						names.push(t_name);//<c:if test="${!checked}">
						break; // 如果为非复选框选择，则返回第一个选择  </c:if>
					}
					$("#${id}Id").val(ids);
					$("#${id}Name").val(names);
/* 					$("#userType").html("");
			 		var html='<option "selected=selected" value='+ids+'>'+names+'</option>';
			 		 $("#userType").html(html);	
			 		 $("#userType").select2(); */
			 		 getUserRole();	
					if("${id}"=="company"){
						//getRole();//  add by pengr , function find role by officeid 14.10.17
					}
					
				}//<c:if test="${allowClear}">
				else if (v=="clear"){
					$("#${id}Id").val("");
					$("#${id}Name").val("");
                }//</c:if>
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
</script>
