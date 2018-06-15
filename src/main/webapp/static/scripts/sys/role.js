function log(des, value) {
	try {
		console.info(new Date() + "%c" + des, "color:blue; font-weight:bold",
				value);
	} catch (e) {
	}
}

$(document).ready(function(){
	 
	 if($("#companyId").val() != null){
		 getRole();
	 }

});

function getRole() {
	$.ajaxSetup({ cache: false }); 
	var ctx = "/" + window.location.pathname.split("/")[1];
	$.ajax({
		url : ctx + "/rest/role/getRoles",
		data : {
			id : $("#companyId").val(),
		},
		type : "GET",
		async : false,
		success : function(data) {
			$("#roleDiv").empty();
			$.each(data, function(i) {
				$("#roleDiv").append("<span><input id=\"roleIdList"+i+"\" name=\"roleIdList\" class=\"required\" value=\""+data[i].id+"\" type=\"radio\">" +
						"<label for=\"roleIdList"+i+"\">"+data[i].name+"</label></span>");
				if(roleName == data[i].name ){
					$("#roleIdList"+i).attr("checked","checked");
				}
			});
		}
	});
}