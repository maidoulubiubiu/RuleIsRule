<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.row-fluid.centralize.left {
	text-align: center;
	margin-bottom: 20px;
 /*	background-image:url('<c:url value='/resources/img/default/item-lefttop-bg.png'></c:url>');*/
 	background-color:#718495; 
 	background-position:top center;
 	background-repeat:repeat;
	vertical-align: middle;
	height: 160px;
}
.left-menu-container {
background-color: #718495;

height:350px;
background-image: url("<c:url value='/resources/img/default/item_bg.png'></c:url>");
background-position: top right;
background-repeat: repeat-y;
margin-bottom: 10px;

}
.nav-stacked  > li{
	border-bottom: 0px solid #6d7b88;
	width: 183px;
	background-image: url("<c:url value='/resources/img/default/item_second_bLing.png'></c:url>");
	background-position: bottom left;
	background-repeat: repeat-x;
}

/*.nav-stacked > li > a {
text-align: left;
font-size: 15px;
color: #adc877;
font-weight: bold;
border:0px;
padding:13px 25px;
text-decoration: none;
} */

.nav-stacked > li > a {
border: 0px solid #DDD;
/*-webkit-border-radius: 0;
-moz-border-radius: 0;
border-radius: 0;*/
text-align: left;
padding:8px 30px;
font-size: 15px;
font-family: 'Microsoft YaHei',Arial;
color: #FFF;
font-weight: bold;
}

.nav-stacked > li > a:hover {
color: #3d4f67;
text-decoration: none;
/*background-color: #80B029;*/
background-color:#b1b6ba;
font-weight: bold;

}
.nav-stacked > .active > a , .nav-stacked > .active > a:hover {
color: #3d4f67;
cursor: default;
text-decoration: none;
/*background-color: #80B029;*/
background-color:#b1b6ba;
border: 0px solid #DDD;
border-bottom-color: transparent;
}



</style>
<c:url var="avatar_url" value="${sessionUserInfo.avatar}"></c:url>
<div class="row-fluid centralize left ">
	<div class="row-fluid " >
		<c:choose>
			<c:when test='${sessionUserInfo.avatar == "/resources/img/avatar/avatar90.png" || sessionUserInfo.avatar == "/resources/img/avatar/avatar91.png" }'>
				 <div style="background-image:url(${avatar_url}); background-repeat:no-repeat;background-position:center;width:60px;margin:10px auto;">
				    <div style="height: 35px;"></div>
				    <div style="height: 20px;background-color:gray;  padding:10px 10px;">
				    	<a href='<c:url value="/pcenter/details"><c:param name="active" value="photo" /></c:url>' >上传头像</a> 
				    </div>
			   </div>
			<div style="width: 180px;  color: #fff;" id="content"><h4>${sessionUserInfo.name }</h4>	</div>
			</c:when>
			<c:otherwise>
				<img src="${avatar_url}" style="margin: 10px 10px; width: 100px; " >
				<div style="width: 180px; text-align: center; color: #fff;" id="content"><h4>${sessionUserInfo.name }</h4>	</div>	
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="left-menu-container ">
	<ul class="nav  nav-stacked">
		<li><a href='<c:url value="/admin/incubator/about"></c:url>' >孵化园介绍</a></li>
		<li><a href='<c:url value="/admin/incubator/announcement/list"></c:url>' >新闻动态</a></li>
		<li><a href='<c:url value="/incubator/${sessionUserInfo.id }"></c:url>' >孵化园前台页面</a></li>
		<li><a href='<c:url value="/admin"></c:url>' >返回上级菜单</a></li>
	</ul>
</div>

<html>
<script type="text/javascript">
$(document).ready(function() {
	$('ul > li > a').each(function(index){
		if ($(this).attr('href') == (window.location.pathname+window.location.search) ||$(this).attr('href') == (window.location.pathname) ){
			$(this).parent().addClass('active');
		}
	});
});
</script>
</html>