<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="background-color:#ccdfa8;">
	<ul class="nav nav-tabs nav-stacked">
		<li><a href='<c:url value="/admin/course/list"></c:url>'>返回课程</a></li>
		<li><a href='<c:url value="/admin/course/edit/${course.id}/modifycourse"></c:url>'>课程资料</a></li>
		<li><a href='<c:url value="/admin/course/edit/${course.id}/basicinfo"></c:url>'>基本信息</a></li>
		<li><a href='<c:url value="/admin/course/edit/${course.id}/detailinfo"></c:url>'>详细信息</a></li>
		<li><a href='<c:url value="/admin/course/edit/${course.id}/cover"></c:url>'>修改封面</a></li>
		<li><a href='<c:url value="/admin/course/edit/${course.id}/powerprice"></c:url>'>权限价格</a></li>
		<li><a href='<c:url value="/admin/course/edit/${course.id}/deletecourse"></c:url>'>取消课程</a></li>
	</ul>
</div>
