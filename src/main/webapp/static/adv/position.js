$(function() {
	var host = accipiter.getRootPath();
	firstLoad();
	function typeChange() {
		var a = document.getElementById("position_type");
		var b = a.options[a.selectedIndex].value;
		var o = {
			"id" : b
		};
		var data = JSON.stringify(o);
		 
		$.ajax({
			url : host + "/adv/position/find_type_move",
			async : false,
			type : "POST",
			data : data,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				 if(data == "true"){
					 $("#move").css('display','block'); 
				 }else{
					 $("#move").css('display','none'); 
				 }

			},
			error : function(err) {
				console.error(err);

			}
		});
	}

	function firstLoad() {
		var a = document.getElementById("position_type");
		var b = a.options[a.selectedIndex].value;
		if (b != null && "" != b) {
			typeChange();
		}  
	}
	$('#position_type').change(function() {
		typeChange();
	});
})