<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
		<h5>课程详细</h5>
	</div>
	<div class="row">
		<table class="table ">
			<thead><tr><th>课程标题</th><th>课程简述</th><th width=20%>发布时间</th></tr></thead>
			<tbody>
				<tr><td align="left">${course.courseName}</td>
				<td align="center">${course.courseDesc}</td>
				<td align="center">${course.courseDate}</td></tr>	
			</tbody>
		</table>
		<br>
		<div class="row">
			<h5>课程资源</h5>
		</div>
			
		<table class="table ">
			<c:choose>
				<c:when test="${resourceCount>0}">
					
					<tbody>
						<tr><td>资源名称</td><td>上传时间</td><td>下载</td></tr>
						<c:forEach items="${resourceList}" var="resource">
							<tr>
								<td align="left">${resource.fileName}</td>
								<td align="center">${resource.date}</td>
								<td align="center"><a href='<c:url value="/course/resource/download/${resource.id }"></c:url>'>下载</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</c:when>
				<c:otherwise><tr><td colspan="3">尚未添加资源！</td></tr></c:otherwise>
			</c:choose>
		</table>
		
	</div>

</div>

