<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.left-menu-container {
background-color: #3c4f65;
width: 230px;
height:450px;
background-image: url("<c:url value='/resources/img/default/item_bg.png'></c:url>");
background-position: top right;
background-repeat: repeat-y;
margin-bottom: 10px;
}

.nav-tabs {
	width: 225px;
}
.nav-tabs  > li{
	border-bottom: 0px solid #6d7b88;
	width: 223px;
	background-image: url("<c:url value='/resources/img/default/item_second_bLing.png'></c:url>");
	background-position: bottom left;
	background-repeat: repeat-x;
}
.nav-tabs.nav-stacked > li > a {
border: 0px solid #DDD;
text-align: left;
padding:12px 20px;
font-size: 15px;
font-family: 'Microsoft YaHei',Arial;
color: #FFF;
font-weight: bold;
}

.nav-tabs.nav-stacked > li >a>span{
	padding-left: 10px;
	font-size: 13px;
	font-weight: normal;
}
.nav-tabs  > li > a:hover {

color: #3d4f67;
text-decoration: none;
/*background-color: #80B029;*/
background-color:#a1a6ac;
}
.nav-tabs > .active > a, .nav-tabs > .active > a:hover {
color: #3d4f67;
cursor: default;
text-decoration: none;
/*background-color: #80B029;*/
background-color:#a1a6ac;
border: 0px solid #DDD;
border-bottom-color: transparent;
}
</style>
<div class="left-menu-container">
	<ul class="nav nav-tabs nav-stacked">
		<li><a href='<c:url value="/about"></c:url>' >平台简介</a></li>
		<li><a href='<c:url value="/contact"></c:url>' >联系我们</a></li>
		<!-- <li><a href='<c:url value="/help"></c:url>' >服务帮助</a></li>-->
		<li><a href='<c:url value="/legal"></c:url>' >法律声明</a></li> 
		<li><a href='legal' >服务帮助</a></li>
	</ul>
</div>

<html>
<script type="text/javascript">
$(document).ready(function() {
	$('ul > li > a').each(function(index){
		if ($(this).attr('href') == (window.location.pathname+window.location.search)) {
			$(this).parent().addClass('active');
		}
	});
});
</script>
</html>


