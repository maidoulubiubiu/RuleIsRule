<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.row-fluid.centralize {
	text-align: center;
	margin-bottom: 20px;
	padding: 0px 0px 10px;
 	/*background-image:url('<c:url value='/resources/img/default/admin_left_bg.png'></c:url>');*/
 	background-color:#ccdfa8;
 	background-position:top center;
 	background-repeat:repeat-y;
	vertical-align: middle;
}
</style>
<c:url var="avatar_url" value="${sessionUserInfo.avatar}"></c:url>
<c:choose>
	<c:when test='${sessionUserInfo.avatar == "/resources/img/avatar/avatar90.png"}'>
		<div class="row-fluid centralize">
		    <div style="background-image:url(${avatar_url}); background-repeat:no-repeat;background-position:center;height:90px;width:90px;margin:15px auto;">
		    <div style="height: 35px;"></div>
		    <div style="height: 20px;background-color:gray;padding:2px 2px;">
		    	<a href='<c:url value="/admin/teacher/details"><c:param name="active" value="avatar" /></c:url>' >上传头像</a>
		    </div>
		    <div style="height: 35px;"></div>
		   </div>
		</div>	
	</c:when>
	<c:otherwise>
		<div class="row-fluid centralize">
			<img width="150px" height="150px" src="${avatar_url}" style="margin: 15px 0px;">
			<a href='<c:url value='/teacher/${teacherInfo.id}'></c:url>'><h4>${sessionUserInfo.name }</h4></a>
		</div>		
	</c:otherwise>
</c:choose>
<div class="left-menu-container">
	<ul class="nav nav-tabs nav-stacked">
		<!-- <li class="active"><a href='<c:url value="/admin"></c:url>' >个人中心</a></li> -->
		<li><a href='<c:url value="/teacher/${sessionUserInfo.id}"></c:url>' >个人主页</a></li>
		<li><a href='<c:url value="/admin/teacher/resume"><c:param name="active" value="personal" /></c:url>' >我的简历</a></li>
		<li><a href='<c:url value="/admin/teacher/announcement/list"></c:url>' >公告管理</a></li>
		<li><a href='<c:url value="/admin/teacher/course/list"></c:url>' >课程管理</a></li>
		<li><a href='<c:url value="/admin/teacher/resource/list"></c:url>' >资源管理</a></li>
		<!-- <li><a href='<c:url value="/admin/teacher/achievement/list"></c:url>' >科研成果</a></li> -->
		<li><a href='<c:url value="/admin/blog/list"></c:url>' >我的博文</a></li>
		<!-- <li><a href='<c:url value="/admin/teacher/friendsRelated/list"></c:url>' >好友互动</a></li> -->
		<li><a href='<c:url value="/admin/teacher/details"><c:param name="active" value="avatar" /></c:url>' >账号信息</a></li>
		<li><a href='<c:url value="/admin/teacher/message/list"></c:url>' >站内信</a></li>
		<!-- 
		<c:if test="${(sessionUserInfo!=null) && (sessionUserInfo.isEnterprise != null) }">
		       <li><a href='<c:url value="/admin/teacher/downgradeRole"></c:url>' >升级成为教师用户</a></li>
		</c:if>
		<c:if test="${(sessionUserInfo!=null) && (sessionUserInfo.isEnterprise == null) || (sessionUserInfo.isEnterprise == '')}">
		       <li><a href='<c:url value="/admin/teacher/upgradeRole"></c:url>' >升级成为企业用户</a></li>
		</c:if>
		 -->
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