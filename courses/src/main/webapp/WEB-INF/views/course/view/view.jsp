<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.row-fluid.custom {
	margin-bottom: 20px;
	padding: 0px 0px 10px;
	background: #FAFAFB;
}
.round {
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.row-fluid.custom .row {
	margin: 0px 5px;
}
</style>

<div class="row-fluid custom round">
	<div class="row">
		<h5>课程资料</h5>
	</div>
	<div class="row">
		<div>
		<table class="table">
		<c:forEach var="course" items="${courseMap}">
			<tr><td >${course.key} &nbsp;&nbsp;</td></tr>
			<c:forEach var="fileNames" items="${course.value}">
			<tr><td>${fileNames.fileName}&nbsp;&nbsp;</td></tr>
			</c:forEach>
		</c:forEach>
		</table>
		</div>
	</div>
	<div>
	<jsp:include page="/WEB-INF/views/course/view/comment.jsp"></jsp:include>
	</div>
	<div class="row">
		<div id="comment">
		<span>总评论人数:&nbsp;&nbsp;&nbsp;${sumPerson}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总评论分数:&nbsp;&nbsp;&nbsp;${sumMark}&nbsp;&nbsp;&nbsp;</span>
			<c:forEach var="comment" items="${listcomment}">
				<tr><td>&nbsp;&nbsp;&nbsp;昵称:${comment.user.name}:&nbsp;&nbsp;&nbsp;本人评分数:${mark}</td></tr>
				<tr><td>&nbsp;&nbsp;&nbsp;标题:${comment.commentTitle}&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;评论时间:${comment.commentDate }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>					
				<tr><td>&nbsp;&nbsp;&nbsp;评论信息:${comment.commentDesc}</td></tr>
			</c:forEach>
			</div>
	</div>
</div>