<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
	.ctype{
		background: #fff5e0;
		text-align: left;
	}
	.ctype .content{
		padding:10px 20px; 
		border-bottom: 1px solid #f77605;
		border-left: 1px solid #f77605;
		border-right: 1px solid #f77605;
	}
	.ctype .content >a{
		text-decoration: none;
	}
	.container-fluid .navbg{
		background-image: url('<c:url value='/resources/img/default/index/enavibg.png' ></c:url>');
		background-position: left top;
		background-repeat: repeat-x;
		height: 50px;
	}
	h5{	font-family:'Microsoft YaHei',Arial; font-size: 14px; letter-spacing: 1px; font-weight: normal;}
	ul#simple-menu{list-style-type:none;width:100%;position:relative;height:38px;margin:0;padding:0px 0 0 0;}
	ul#simple-menu li{display:block;float:left;margin:0 0 0 1px;height:38px;}
	ul#simple-menu li a{display:block;float:left;color:#fdf2ee; text-decoration:none; padding:0 20px 0 20px;height:38px;}
	ul#simple-menu li a:hover{color:#f77605;background: #fff5e0; border-top: 1px solid #f77605;}
	ul#simple-menu li a.current{color:#f77605;background: #fff5e0; border-top: 1px solid #f77605;}
	ul#simple-menu li a.current:hover{color:#f77605;border-top: 1px solid #f77605;}
	
</style>
<!-- 
<div class="navbar" >
	<div class="navbar navbar-bg">
	<div class="navbar-inner">
		<ul class="nav">
			<li><a href='<c:url value='/enterprise/${teacherInfo.id}'></c:url>' class="active">首页</a></li>
			<li><a href='<c:url value='/enterprise/${teacherInfo.id}/course/list'></c:url>'>学习资源</a></li>
			<li><a href='<c:url value='/enterprise/${teacherInfo.id}/teacher/list'></c:url>'>知名老师</a></li>
			<li><a href='<c:url value='/enterprise/${teacherInfo.id}/resume'></c:url>'>学校介绍</a></li>
			<li><a href='<c:url value='/enterprise/${teacherInfo.id}/announcement/list'></c:url>'>公告专栏</a></li>
			<%-- <c:if test="${teacherInfo.isEnterprise == null}">
				<li><a href='<c:url value='/teacher/${teacherInfo.id}/achievement/list'></c:url>'>科研成果</a></li>
			</c:if> 
			<li><a href='<c:url value='/enterprise/${teacherInfo.id}/blog/list'></c:url>'>博文</a></li>--%>
		</ul>
	</div>
	</div>
</div>
 -->
 <div class="container-fluid navbg" >
	<div class="dropdown " style="margin-left: 21px; height:38px; 	background: #fff5e0;  text-align: center;  width: 100px;  float: left;  border-top: 1px solid #f77605;  " >
		<div class="dropdown-toggle"  data-toggle="dropdown"><a href="#" style="text-decoration: none;"><h5 style="color: #f77605;">课程类别</h5></a></div>
		<div class="dropdown-menu" style="text-align: left;background: #fff5e0; width: 205px;" role="menu" aria-labelledby="dropdownMenu">
			<div class="ctype">
				<div class="content">
					<c:forEach items="${cTypeList }" var="cType" begin="0" end="2">
						<a href='<c:url value='/enterprise/${teacher_id}/course/type/${cType.id}'></c:url>'><span style="color:#80b029; margin-right: 5px;">${cType.typeName}</span></a>
					</c:forEach>
				</div>	
				<div class="content">
					<c:forEach items="${cTypeList }" var="cType" begin="3" end="5">
						<a href='<c:url value='/enterprise/${teacher_id}/course/type/${cType.id}'></c:url>'><span style="color:#80b029; margin-right: 5px;">${cType.typeName}</span></a>
					</c:forEach>
				</div>	
				<div class="content">
					<c:forEach items="${cTypeList }" var="cType" begin="6" end="8">
						<a href='<c:url value='/enterprise/${teacher_id}/course/type/${cType.id}'></c:url>'><span style="color:#80b029; margin-right: 5px;">${cType.typeName}</span></a>
					</c:forEach>
				</div>	
				<div class="content">
					<c:forEach items="${cTypeList }" var="cType" begin="9" end="12">
						<a href='<c:url value='/enterprise/${teacher_id}/course/type/${cType.id}'></c:url>'><span style="color:#80b029; margin-right: 5px;">${cType.typeName}</span></a>
					</c:forEach>
				</div>	
				<div class="content">
					<c:forEach items="${cTypeList }" var="cType" begin="13" end="16">
						<a href='<c:url value='/enterprise/${teacher_id}/course/type/${cType.id}'></c:url>'><span style="color:#80b029; margin-right: 5px;">${cType.typeName}</span></a>
					</c:forEach>
				</div>	
				<div class="content">
					<c:forEach items="${cTypeList }" var="cType" begin="17" end="20">
						<a href='<c:url value='/enterprise/${teacher_id}/course/type/${cType.id}'></c:url>'><span style="color:#80b029; margin-right: 5px;">${cType.typeName}</span></a>
					</c:forEach>
				</div>	
			</div>
		</div>
	</div>
	<ul style="margin-left: 270px;" id="simple-menu">
		<li><a href='<c:url value='/enterprise/${teacherInfo.id}'></c:url>' > <h5>首页</h5></a></li>
		<li><a href='<c:url value='/enterprise/${teacherInfo.id}/course/list'></c:url>'><h5>学习资源</h5></a></li>
		<li><a href='<c:url value='/enterprise/${teacherInfo.id}/teacher/list'></c:url>'><h5>知名老师</h5></a></li>
		<li><a href='<c:url value='/enterprise/${teacherInfo.id}/resume'></c:url>'><h5>学校介绍</h5></a></li>
		<li><a href='<c:url value='/enterprise/${teacherInfo.id}/announcement/list'></c:url>'><h5>公告专栏</h5></a></li>
	</ul>
 </div>
 <script type="text/javascript">
 $(document).ready(function() {
		$('ul > li > a').each(function(index){
			if ($(this).attr('href') == (window.location.pathname+window.location.search)) {
				//alert(window.location.pathname+window.location.search+"====");
				$(this).addClass('current');
			}
		});
	});
 </script>