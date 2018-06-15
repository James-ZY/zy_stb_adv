<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title><spring:message code="menu.function"/></title>
<meta name="decorator" content="default" />
<head>
<%-- <link rel="stylesheet" href="${ctxStatic}/common/menuTree.css"> --%>
<script type="text/javascript">
	function loadJsAndCssFile(filename, filetype){
		if (filetype=="js"){
			var fileref=document.createElement('script');
			fileref.setAttribute("type","text/javascript");
			fileref.setAttribute("src", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
		else if (filetype=="css"){
			var fileref=document.createElement("link");
			fileref.setAttribute("rel", "stylesheet");
			fileref.setAttribute("type", "text/css");
			fileref.setAttribute("href", filename);
			document.getElementsByTagName("head")[0].appendChild(fileref);
		}
	}
	function loadFile(){
		var control=accipiter.getLocale();
		if(control==0){
			var headCss="/advs/static/common/cn-menuTree.css";
				loadJsAndCssFile(headCss,"css");
			
		}else{
			var headCss="/advs/static/common/us-menuTree.css";
			loadJsAndCssFile(headCss,"css");
		}		
	}
	loadFile();
	$(document).ready(function() {
		$(".accordion-heading a").click(function() {
			$('.accordion-toggle i').removeClass('icon-chevron-down');
			$('.accordion-toggle i').addClass('icon-chevron-right');
			if (!$($(this).attr('href')).hasClass('in')) {
				$(this).children('i').removeClass('icon-chevron-right');
				$(this).children('i').addClass('icon-chevron-down');
			}
		});
		$(".accordion-body a").click(function() {
			$("#menu li").removeClass("active");
			$("#menu li i").removeClass("icon-white");
			$(this).parent().addClass("active");
			$(this).children("i").addClass("icon-white");
		});
		$(".accordion-body .nav li a").mouseover(function(){
			if($(this).parent().hasClass("active")){
				return;
			}else{
				$(".accordion-body .nav li a").children("i").removeClass("icon-white");
				$(this).children("i").addClass("icon-white");
			}
		}).mouseleave(function(){
			$(this).children("i").removeClass("icon-white");
		});
		$(".accordion-body a:first i").click();
		var Height=$("body").height();
		var menuH=Height-$(".menu-top-title").height()-1;
	/* 	$(".menutree-content").css("height",menuH); */
	});
</script>
</head>
<body>
	<div class="accordion" id="menu">
	    <div class="menu-top-title"><span><spring:message code="Navigation.function"/></span><div class="top-totle-icon"></div></div>
	    <div class="menutree-content">
		<c:set var="menuList" value="${fns:getMenuList()}" />
		<c:set var="firstMenu" value="true" />
		<c:set var="continue" value="true"/>
		<c:forEach items="${menuList}"  var="menu" varStatus="idxStatus">
		 
			<c:if test="${menu.parent.id eq (not empty param.parentId?param.parentId:'1')&&menu.isShow eq '1'}">
				 
				<div class="accordion-group">
					<div class="accordion-heading">
						<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse${menu.id}"
							title="${menu.remarks}"><i class="icon-chevron-${firstMenu?'down':'right'}"></i>&nbsp;${menu.name}</a>
					</div>
					<div id="collapse${menu.id}" class="accordion-body collapse ${firstMenu?'in':''}">
						<div class="accordion-inner">
							<ul class="nav nav-list">
								<c:forEach items="${menuList}" var="menuChild">
									<c:if test="${menuChild.parent.id eq menu.id&&menuChild.isShow eq '1'}">
										<li>
										<a
											href="${fn:indexOf(menuChild.href, '://') eq -1?ctx:''}${not empty menuChild.href?menuChild.href:'/404'}"
											target="${not empty menuChild.target?menuChild.target:'mainFrame'}"> <i
												class="icon-${not empty menuChild.icon?menuChild.icon:'circle-arrow-right'}"></i>&nbsp;${menuChild.name}
										</a>
										<i class="active-icon"></i>
										</li>
										<c:set var="firstMenu" value="false" />
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
		</div>
	</div>
</body>
</html>
