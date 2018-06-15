
$.fn.extend({
		//---元素拖动插件
    dragging:function(data){   
		var $this = $(this);
		var xPage;
		var yPage;
		var X;//
		var Y;//
		var xRand = 0;//
		var yRand = 0;//
        var rangeX = data.rangeX;
        var rangeY = data.rangeY;
        var rangeWidth = data.rangeWidth;
        var rangeHeight = data.rangeHeight;
        var moveStyle = data.moveStyle;
        
		var father = $this.parent();
		var defaults = {
			move : 'both',
			randomPosition : true ,
			rangeX : 0,
			rangeY : 0,
			hander:1
		}
		var opt = $.extend({},defaults,data);
		var movePosition = opt.move;
		var random = opt.randomPosition;
		
		var hander = opt.hander;
		if(hander == 1){
			hander = $this; 
		}else{
			hander = $this.find(opt.hander);
		}
		
			
		//---初始化
		father.css({"position":"relative","overflow":"hidden"});
		$this.css({"position":"absolute"});
		hander.css({"cursor":"move"});
		/*$this.find('*').not('img').mousedown(function(e) {
			e.stopPropagation();
		});*/

		var faWidth = father.width();
		var faHeight = father.height();
		var thisWidth = $this.width()+parseInt($this.css('padding-left'))+parseInt($this.css('padding-right'))+parseInt($this.css('border-left-width'))+parseInt($this.css('border-right-width'));
		var thisHeight = $this.height()+parseInt($this.css('padding-top'))+parseInt($this.css('padding-bottom'))+parseInt($this.css('border-top-width'))+parseInt($this.css('border-bottom-width'));
		var type = father.attr("class");
		if(type !=null && type== "ad_gd"){
			setGdPoint(moveStyle);//设置滚动坐标信息
		}else{
			setPoint();	//设置挂角插屏坐标信息				
		}
		
		var mDown = false;//
		var positionX;
		var positionY;
		var moveX ;
		var moveY ;
		
		if(random){
			$thisRandom();
		}
		function $thisRandom(){ //随机函数
			$this.each(function(index){
				var randY = parseInt(Math.random()*(faHeight-thisHeight));///
				var randX = parseInt(Math.random()*(faWidth-thisWidth));///
				if(movePosition.toLowerCase() == 'x'){
					$(this).css({
						left:randX
					});
				}else if(movePosition.toLowerCase() == 'y'){
					$(this).css({
						top:randY
					});
				}else if(movePosition.toLowerCase() == 'both'){
					$(this).css({
						top:randY,
						left:randX
					});
				}
				
			});	
		}
		
		hander.mousedown(function(e){
			father.children().css({"zIndex":"0"});
			$this.css({"zIndex":"1"});
			mDown = true;
			X = e.pageX;
			Y = e.pageY;
			positionX = $this.position().left;
			positionY = $this.position().top;
			return false;
		});
			
		$(document).mouseup(function(e){
			mDown = false;
		});
		
		$(document).mousemove(function(e){
			faWidth = father.width();
			faHeight = father.height();
			thisWidth = $this.width()+parseInt($this.css('padding-left'))+parseInt($this.css('padding-right'))+parseInt($this.css('border-left-width'))+parseInt($this.css('border-right-width'));
			thisHeight = $this.height()+parseInt($this.css('padding-top'))+parseInt($this.css('padding-bottom'))+parseInt($this.css('border-top-width'))+parseInt($this.css('border-bottom-width'));
			xPage = e.pageX;//--
			moveX = positionX+xPage-X;
			
			yPage = e.pageY;//--
			moveY = positionY+yPage-Y;
			
			function thisXMove(){ //x轴移动
				if(mDown == true){
					$this.css({"left":moveX});
				}else{
					return;
				}
				if(moveX < 0){
					$this.css({"left":"0"});
				}
				if(moveX > (faWidth-thisWidth)){
					$this.css({"left":faWidth-thisWidth});
				}
				if(type !=null && type== "ad_gd"){
					setGdPoint(moveStyle);//设置滚动坐标信息
				}else{
					setPoint();	//设置挂角插屏坐标信息				
				}
				return moveX;
			}
			
			function thisYMove(){ //y轴移动
				if(mDown == true){
					$this.css({"top":moveY});
				}else{
					return;
				}
				if(moveY < 0){
					$this.css({"top":"0"});
				}
				if(moveY > (faHeight-thisHeight)){
					$this.css({"top":faHeight-thisHeight});
				}
				if(type !=null && type== "ad_gd"){
					setGdPoint(moveStyle);//设置滚动坐标信息
				}else{
					setPoint();	//设置挂角插屏坐标信息				
				}
				return moveY;
			}

			function thisAllMove(){ //全部移动
				if(mDown == true){
					$this.css({"left":moveX,"top":moveY});
				}else{
					return;
				}
				if(moveX < 0){
					$this.css({"left":"0"});
				}
				if(moveX > (faWidth-thisWidth)){
					$this.css({"left":faWidth-thisWidth});
				}

				if(moveY < 0){
					$this.css({"top":"0"});
				}
				if(moveY > (faHeight-thisHeight)){
					$this.css({"top":faHeight-thisHeight});
				}
				if(type !=null && type== "ad_gd"){
					setGdPoint(moveStyle);//设置滚动坐标信息
				}else{
					setPoint();	//设置挂角插屏坐标信息				
				}
				
			}
			if(movePosition.toLowerCase() == "x"){
				thisXMove();
			}else if(movePosition.toLowerCase() == "y"){
				thisYMove();
			}else if(movePosition.toLowerCase() == 'both'){
				thisAllMove();
			}
		});
		
		function setPoint(){
			var left = $this.css("left").split("px")[0];
			if(left<0){
				left=0;
			}
			var top = $this.css("top").split("px")[0];
			if(top<0){
				top=0;
			}
			var rLeft = parseInt(left)+parseInt(rangeX);
			var rTop = parseInt(top)+parseInt(rangeY);
			var endx = parseInt(rLeft)+parseInt(thisWidth);
			var endy = parseInt(rTop)+parseInt(thisHeight);
			var start = "start:("+rLeft+","+rTop+")";
			var end = "end:("+endx+","+endy+")";
			var show = $("#standardShow").attr("name");
			var dto=JSON.parse($('#adelementDto').text());
			var px;
			var py;

			if(show == 1){
				$("#position").html(start+","+end);
				px = dto.sdStartX; py = dto.sdStartY;
				px = parseInt(px) + parseInt(left);
				py = parseInt(py) + parseInt(top);
				$("[id='position.beginPointX']").val(px);
				$("[id='position.beginPointY']").val(py);
				$("[id='position.endPointX']").val(endx);
				$("[id='position.endPointY']").val(endy);
				$("#sdLeft").val(left);
				$("#sdTop").val(top);				
			}else{
				$("#hdposition").html(start+","+end);
				px = dto.hdStartX; py = dto.hdStartY;
				px = parseInt(px) + parseInt(left);
				py = parseInt(py) + parseInt(top);
				$("[id='hdPosition.beginPointX']").val(px);
				$("[id='hdPosition.beginPointY']").val(py);
				$("[id='hdPosition.endPointX']").val(endx);
				$("[id='hdPosition.endPointY']").val(endy);
				$("#hdLeft").val(left);
				$("#hdTop").val(top);
			}
		}			
			function setGdPoint(moveStyle){
				var left = $this.css("left").split("px")[0];
				if(left<0){
					left=0;
				}
				var top = $this.css("top").split("px")[0];
				if(top<0){
					top=0;
				}
				var rLeft = parseInt(left)+parseInt(rangeX);
				var rTop = parseInt(top)+parseInt(rangeY);		
				var endx =0;
				var endy =0;
				var start;
				var end;
                if(moveStyle == "left"){
                	endx = parseInt(rLeft)+parseInt(rangeWidth);
    				endy = rTop;
    				start = "start:("+endx+","+endy+")";
    				end = "end:("+rLeft+","+rTop+")";
				}else if(moveStyle == "right"){
					endx = parseInt(rLeft)+parseInt(rangeWidth);
					endy = rTop;
					start = "start:("+rLeft+","+rTop+")";
					end = "end:("+endx+","+endy+")";
				}else if(moveStyle == "up"){
					endx = rLeft;
					endy = rTop+parseInt(rangeHeight);
					start = "start:("+endx+","+endy+")";
					end = "end:("+rLeft+","+rTop+")";
				}else if(moveStyle == "down"){
					endx = rLeft;
					endy = rTop+parseInt(rangeHeight);
					start = "start:("+rLeft+","+rTop+")";
					end = "end:("+endx+","+endy+")";
				}

				var show = $("#standardShow").attr("name");
				var dto=JSON.parse($('#adelementDto').text());
				var px;
				var py;

				if(show == 1){
					$("#position").html(start+","+end);
					px = dto.sdStartX; py = dto.sdStartY;
					px = parseInt(px) + parseInt(left);
					py = parseInt(py) + parseInt(top);
					  if(moveStyle == "left"){
							$("[id='position.beginPointX']").val(endx);
							$("[id='position.beginPointY']").val(endy);
							$("[id='position.endPointX']").val(px);
							$("[id='position.endPointY']").val(py);
						}else if(moveStyle == "right"){
							$("[id='position.beginPointX']").val(px);
							$("[id='position.beginPointY']").val(py);
							$("[id='position.endPointX']").val(endx);
							$("[id='position.endPointY']").val(endy);
						}else if(moveStyle == "up"){
							$("[id='position.beginPointX']").val(endx);
							$("[id='position.beginPointY']").val(endy);
							$("[id='position.endPointX']").val(rLeft);
							$("[id='position.endPointY']").val(rTop);
						}else if(moveStyle == "down"){
							$("[id='position.beginPointX']").val(rLeft);
							$("[id='position.beginPointY']").val(rTop);
							$("[id='position.endPointX']").val(endx);
							$("[id='position.endPointY']").val(endy);
						}
					$("#sdLeft").val(left);
					$("#sdTop").val(top);				
				}else{
					$("#hdposition").html(start+","+end);
					px = dto.hdStartX; py = dto.hdStartY;
					px = parseInt(px) + parseInt(left);
					py = parseInt(py) + parseInt(top);
					  if(moveStyle == "left"){
							$("[id='hdPosition.beginPointX']").val(endx);
							$("[id='hdPosition.beginPointY']").val(endy);
							$("[id='hdPosition.endPointX']").val(px);
							$("[id='hdPosition.endPointY']").val(py);
						}else if(moveStyle == "right"){
							$("[id='hdPosition.beginPointX']").val(px);
							$("[id='hdPosition.beginPointY']").val(py);
							$("[id='hdPosition.endPointX']").val(endx);
							$("[id='hdPosition.endPointY']").val(endy);
						}else if(moveStyle == "up"){
							$("[id='hdPosition.beginPointX']").val(endx);
							$("[id='hdPosition.beginPointY']").val(endy);
							$("[id='hdPosition.endPointX']").val(rLeft);
							$("[id='hdPosition.endPointY']").val(rTop);
						}else if(moveStyle == "down"){
							$("[id='hdPosition.beginPointX']").val(rLeft);
							$("[id='hdPosition.beginPointY']").val(rTop);
							$("[id='hdPosition.endPointX']").val(endx);
							$("[id='hdPosition.endPointY']").val(endy);
						}
					  
					$("#hdLeft").val(left);
					$("#hdTop").val(top);
				}
			}
    }
}); 