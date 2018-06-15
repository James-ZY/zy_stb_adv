Array.prototype.remove = function( item ){
  for( var i = 0 ; i < this.length ; i++ ){
   if( item == this[i] )
    break;
  }
  if( i == this.length )
   return;
  for( var j = i ; j < this.length - 1 ; j++ ){
   this[ j ] = this[ j + 1 ];
  }
  this.length--;
  } 
  
String.prototype.replaceAll = function (AFindText,ARepText){ raRegExp = new RegExp(AFindText,"g"); return this.replace(raRegExp,ARepText);}
 function getAllChildren(e) {
  return e.all ? e.all : e.getElementsByTagName('*');
}

document.getElementsBySelector = function(selector) {
  if (!document.getElementsByTagName) {
    return new Array();
  }
  var tokens = selector.split(' ');
  var currentContext = new Array(document);
  for (var i = 0; i < tokens.length; i++) {
    token = tokens[i].replace(/^\s+/,'').replace(/\s+$/,'');;
    if (token.indexOf('#') > -1) {
      var bits = token.split('#');
      var tagName = bits[0];
      var id = bits[1];
      var element = document.getElementById(id);
      if (tagName  &&  element.nodeName.toLowerCase() != tagName) {
        return new Array();
      }
      currentContext = new Array(element);
      continue; 
    }
    if (token.indexOf('.') > -1) {

      var bits = token.split('.');
      var tagName = bits[0];
      var className = bits[1];
      if (!tagName) {
        tagName = '*';
      }

      var found = new Array;
      var foundCount = 0;
      for (var h = 0; h < currentContext.length; h++) {
        var elements;
        if (tagName == '*') {
            elements = getAllChildren(currentContext[h]);
        } else {
            elements = currentContext[h].getElementsByTagName(tagName);
        }
        for (var j = 0; j < elements.length; j++) {
          found[foundCount++] = elements[j];
        }
      }
      currentContext = new Array;
      var currentContextIndex = 0;
      for (var k = 0; k < found.length; k++) {
        if (found[k].className  &&  found[k].className.match(new RegExp('\\b'+className+'\\b'))) {
          currentContext[currentContextIndex++] = found[k];
        }
      }
      continue;
    }
    if (token.match(/^(\w*)\[(\w+)([=~\|\^\$\*]?)=?"?([^\]"]*)"?\]$/)) {
      var tagName = RegExp.$1;
      var attrName = RegExp.$2;
      var attrOperator = RegExp.$3;
      var attrValue = RegExp.$4;
      if (!tagName) {
        tagName = '*';
      }
      var found = new Array;
      var foundCount = 0;
      for (var h = 0; h < currentContext.length; h++) {
        var elements;
        if (tagName == '*') {
            elements = getAllChildren(currentContext[h]);
        } else {
            elements = currentContext[h].getElementsByTagName(tagName);
        }
        for (var j = 0; j < elements.length; j++) {
          found[foundCount++] = elements[j];
        }
      }
      currentContext = new Array;
      var currentContextIndex = 0;
      var checkFunction; 
      switch (attrOperator) {
        case '=':
          checkFunction = function(e) { return (e.getAttribute(attrName) == attrValue); };
          break;
        case '~': 
          checkFunction = function(e) { return (e.getAttribute(attrName).match(new RegExp('\\b'+attrValue+'\\b'))); };
          break;
        case '|':
          checkFunction = function(e) { return (e.getAttribute(attrName).match(new RegExp('^'+attrValue+'-?'))); };
          break;
        case '^':
          checkFunction = function(e) { return (e.getAttribute(attrName).indexOf(attrValue) == 0); };
          break;
        case '$': 
          checkFunction = function(e) { return (e.getAttribute(attrName).lastIndexOf(attrValue) == e.getAttribute(attrName).length - attrValue.length); };
          break;
        case '*': 
          checkFunction = function(e) { return (e.getAttribute(attrName).indexOf(attrValue) > -1); };
          break;
        default :
          checkFunction = function(e) { return e.getAttribute(attrName); };
      }
      currentContext = new Array;
      var currentContextIndex = 0;
      for (var k = 0; k < found.length; k++) {
        if (checkFunction(found[k])) {
          currentContext[currentContextIndex++] = found[k];
        }
      }
      continue;
    }
    tagName = token;
    var found = new Array;
    var foundCount = 0;
    for (var h = 0; h < currentContext.length; h++) {
      var elements = currentContext[h].getElementsByTagName(tagName);
      for (var j = 0; j < elements.length; j++) {
        found[foundCount++] = elements[j];
      }
    }
    currentContext = found;
  }
  return currentContext;
}
  
 function addEvent(eventType,eventFunc,eventObj){
    eventObj = eventObj || document;
    if(window.attachEvent)  eventObj.attachEvent("on"+eventType,eventFunc);
     if(window.addEventListener) eventObj.addEventListener(eventType,eventFunc,false);
  }
function clearEventBubble(evt){
   evt = evt || window.event;
    if (evt.stopPropagation) evt.stopPropagation(); else evt.cancelBubble = true; 
     if (evt.preventDefault)  evt.preventDefault();  else evt.returnValue = false;
}
 
function posXY(event){
 event = event || window.event;
 var posX = event.pageX || (event.clientX +
         (document.documentElement.scrollLeft || document.body.scrollLeft));
 var posY = event.pageY || (event.clientY +
         (document.documentElement.scrollTop || document.body.scrollTop));
 return {x:posX, y:posY};
}

 var _selectedRegions = [];
 function RegionSelect(selRegionProp){
   this.regions =[];
   var _regions = $(".fasongqi_type_btn[name='1']").parent().find('div[class="channel_list123"]').find('li[class="channel_list"]');
   if(_regions  &&  _regions.length > 0){
    var _self = this;
     for(var i=0; i< _regions.length;i++){
       _regions[i].onmousedown = function(){
         var evt = window.event || arguments[0];
         if(!evt.shiftKey  &&  !evt.ctrlKey){
          // 清空所有select样式
          _self.clearSelections(_regions);
          this.className += " "+_self.selectedClass;
          // 清空selected数组，并加入当前select中的元素
          _selectedRegions = [];
          _selectedRegions.push(this);
         }else{
          if(this.className.indexOf(_self.selectedClass) == -1){
            this.className += " "+_self.selectedClass;
            _selectedRegions.push(this);
          }else{
            this.className = this.className.replaceAll(_self.selectedClass,"");
            _selectedRegions.remove(this);
          }
         }
         clearEventBubble(evt);
       }
       this.regions.push(_regions[i]);
     }
   }
   this.selectedClass = selRegionProp["selectedClass"];
   this.selectedRegion = [];
   this.selectDiv = null;
   this.startX = null;
   this.startY = null;
 }
 
 function getRegions(){
	 this.regions =[];
	 var _regions = $(".fasongqi_type_btn[name='1']").parent().find('div[class="channel_list123"]');
	   if(_regions  &&  _regions.length > 0){
	    var _self = this;
	     for(var i=0; i< _regions.length;i++){
	       _regions[i].onmousedown = function(){
	         var evt = window.event || arguments[0];
	         if(!evt.shiftKey  &&  !evt.ctrlKey){
	          // 清空所有select样式
	          _self.clearSelections(_regions);
	          this.className += " "+_self.selectedClass;
	          // 清空selected数组，并加入当前select中的元素
	          _selectedRegions = [];
	          _selectedRegions.push(this);
	         }else{
	          if(this.className.indexOf(_self.selectedClass) == -1){
	            this.className += " "+_self.selectedClass;
	            _selectedRegions.push(this);
	          }else{
	            this.className = this.className.replaceAll(_self.selectedClass,"");
	            _selectedRegions.remove(this);
	          }
	         }
	         clearEventBubble(evt);
	       }
	       this.regions.push(_regions[i]);
	     }
	   }
	   return this.regions;
 }
 
 RegionSelect.prototype.select = function(){
	 var _self = this;
  
  addEvent("mousedown",function(){
    var evt = window.event || arguments[0];
    _self.onBeforeSelect(evt);
    clearEventBubble(evt);
  },document);
  
  addEvent("mousemove",function(){
    var evt = window.event || arguments[0];
    _self.onSelect(evt);
    clearEventBubble(evt);
  },document);
  
  addEvent("mouseup",function(){
    _self.onEnd();
  },document);
 }
 
 RegionSelect.prototype.onBeforeSelect = function(evt){
  if(!document.getElementById("selContainer")){
     this.selectDiv = document.createElement("div");
     this.selectDiv.style.cssText = "position:absolute;width:0px;height:0px;font-size:0px;margin:0px;padding:0px;border:1px dashed #0099FF;background-color:#C3D5ED;z-index:1000;filter:alpha(opacity:60);opacity:0.6;display:none;";
     this.selectDiv.id = "selContainer";
     document.body.appendChild(this.selectDiv);
   }else{
     this.selectDiv = document.getElementById("selContainer");
   }
   
   this.startX = posXY(evt).x;
   this.startY = posXY(evt).y;
   this.isSelect = true;
   
 }
 
 RegionSelect.prototype.onSelect = function(evt){
    var _self = this;
    if(_self.isSelect){
      if(_self.selectDiv.style.display == "none") _self.selectDiv.style.display = "";
      
      var posX = posXY(evt).x;
      var poxY = posXY(evt).y;
      
      _self.selectDiv.style.left   = Math.min(posX, this.startX);
     _self.selectDiv.style.top    = Math.min(poxY, this.startY);
     _self.selectDiv.style.width  = Math.abs(posX - this.startX);
     _self.selectDiv.style.height = Math.abs(poxY - this.startY);  
     
     $("#selContainer").css({"width":Math.abs(posX - this.startX),"height":Math.abs(poxY - this.startY),"top":Math.min(poxY, this.startY),"left":Math.min(posX, this.startX)});

     var regionList = _self.regions;
     for(var i=0; i< regionList.length; i++){
       var r = regionList[i], sr = _self.innerRegion(_self.selectDiv,r);
       if(sr  &&  r.className.indexOf(_self.selectedClass) == -1){
          r.className = r.className + " "+_self.selectedClass; 
          _selectedRegions.push(r);
       }else if(!sr  &&  r.className.indexOf(_self.selectedClass) != -1){
          r.className = r.className.replaceAll(_self.selectedClass,"");
         _selectedRegions.remove(r);
        }
       
     }
   }
 }
 
 RegionSelect.prototype.onEnd = function(){
  if(this.selectDiv){
    this.selectDiv.style.display = "none"; 
  }
  this.isSelect = false;
  //_selectedRegions = this.selectedRegion;
  showSelDiv();
 }
 
 // 判断一个区域是否在选择区内
 RegionSelect.prototype.innerRegion = function(selDiv, region){
   var s_top = parseInt(selDiv.style.top);
   var s_left = parseInt(selDiv.style.left);
   var s_right = s_left + parseInt(selDiv.offsetWidth);
   var s_bottom = s_top + parseInt(selDiv.offsetHeight);
   
   var r_top = parseInt(region.offsetTop);
   var r_left = parseInt(region.offsetLeft);
   var r_right = r_left + parseInt(region.offsetWidth);
   var r_bottom = r_top + parseInt(region.offsetHeight);
// 蓝色div位置
   console.warn(s_top,s_left,s_right,s_bottom,"上左右下");
   // 所有li元素的位置
   console.log(r_top,r_left,r_right,r_bottom,"shangzuoyouxia");
    var t = Math.max(s_top, r_top);
     var r = Math.min(s_right, r_right);
     var b = Math.min(s_bottom, r_bottom);
     var l = Math.max(s_left, r_left);
 
      if (b > t+5  &&  r > l+5) {
          return region;
      } else {
          return null;
      }
   
 }
 
 RegionSelect.prototype.clearSelections = function(regions){
  for(var i=0; i<regions.length;i++){
   regions[i].className = regions[i].className.replaceAll(this.selectedClass,"");
  }
 }
 
 function getSelectedRegions(){
  return  _selectedRegions;
 }

 function showSelDiv(){
	  var count = 0; 
   var selInfo = "";
   var arr = getSelectedRegions();
   for(var i=0; i<arr.length;i++){
	   if (arr[i].className.indexOf("seled") != -1) { 
	    	arr[i].checked = true;
	  	  count++; 
		  selInfo += arr[i].lastChild.innerText + "\n"; 
	      var control = arr[i].firstChild.getAttribute("name");
	          if(control=="0"){
	        	  arr[i].firstChild.setAttribute("name","1");
	        	  arr[i].firstChild.setAttribute("class","channel_item input-checked");;
	          }
	    }
   }

   console.log("共选择 "+arr.length+" 个文件，分别是：\n"+selInfo);
   
 }