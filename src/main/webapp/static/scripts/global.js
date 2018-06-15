(function(){
	/*Defined a global variable for entire window scope.*/
	this.GLOBAL= {};
	
	/*  Predefind some infomation about url and adress. */
	(function(){
		var url = GLOBAL.url = {};
		url.host_address = window.location.host;
		url.project_name = window.location.pathname.split("/")[1];
		url.base = "http://" + url.host_address + "/" + url.project_name + "/" ;
		url.socket = "ws://" + url.host_address + "/" + url.project_name + "/" + "websocket/" ;
	})();
	
	/* Encapsulate basic opration for http cookies.  */
	(function(){
		var cookie = GLOBAL.cooke = {};
		/*Return a target cookie's value*/
		cookie.getCookie = function (target_cookie_name) {
			if (target_cookie_name != null && target_cookie_name.trim() != "") {
				var cookies = document.cookie;
				if (cookies) {
					var map = cookies.split(";");
					var key_value = [];
					var key;
					var value;
					//Traverse target value
					for (var i = 0; i < map.length; i++) {
						//Get  every cookie in the array
						key_value = map[i].split("=");
						//Get Cookie's key
						key = key_value[0].trim();
						//Get Cookie's value
						value = key_value[1].trim();
						//Return cookie's value if target cookie exist. If not return false.
						if (key == target_cookie_name.trim()) {
							return value;
						}
						else{
							return "NO_TARGET_COOKIE";
						}
					}
				} else {
					return "NO_ANY_COOKIE";
				}
			} else {
				alert("COOKIE_NAME_ERROR");
			}
		}
	})()
	
	
	/* Predefined debuging function, make debug process easly and simply.  */
	this.log=function( message ){
		console.log( message )
	}
	this.info=function( message ){
		console.info( message );
	}
	this.error=function( message ){
		console.error( message );
	}
	this.debug=function( message ){
		console.debug( message );
	}
	this.warn=function( message ){
		console.warn( message );
	}
	
})()