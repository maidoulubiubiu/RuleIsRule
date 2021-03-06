<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>

</style>
<div class="navbar custom navbar-fixed-top">
	<div class="container-fluid" >
		<ul class="nav pull-left">
			<li ><div style="width: 50px;">&nbsp;</div></li>
			<!-- <li class="active" style="width: 90px;"><a href="/courses">主页</a></li> -->
			<li  ><a href="<c:url value="/"></c:url>" ><img  src='<c:url value="/resources/img/default/logo_new.png"></c:url>' ></a></li>
		</ul>
		<ul class="nav pull-right">
			<c:if test="${sessionUserInfo != null}">
				<li style="margin-right: -12px;"><a href='<c:url value="/jumpToPatents"></c:url>' class="navbar-link">${sessionUserInfo.user.email }</a></li>
				<li id="fat-menu" class="dropdown" style="margin-right: -10px"><a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown"> <img src="<c:url value='/resources/img/default/gear.png'></c:url>"
						style="margin-top: -3px;height:15px;"></img>
				</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
						<li><a href='<c:url value="/"></c:url>'>回到首页</a></li>
						<li class="divider"></li>
						<li><a href='<c:url value="/signout"></c:url>'>退出登录</a></li>
					</ul></li>
			</c:if>
			<c:if test="${sessionUserInfo == null}">
				<li class="top_margin"><a href='<c:url value="/jumpToPatents"></c:url>'><h6>登录</h6></a><br></li>
				<li class="top_margin"><a href='<c:url value="/jumpToPatents"></c:url>'><h6>注册</h6></a><br></li>
				<li class="top_margin"><a href='<c:url value="/jumpToDiplomat"></c:url>'><h5>外交官入口</h5></a><br></li>
			</c:if>
			<li ><div style="width: 10px;"></div></li>
		</ul>
		<br>
	</div>
</div>