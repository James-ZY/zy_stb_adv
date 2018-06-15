var validDate=window.parent.document.getElementById('sysTime').getAttribute("name");
Date.prototype.getValidDate=function(){
    return validDate;
};
