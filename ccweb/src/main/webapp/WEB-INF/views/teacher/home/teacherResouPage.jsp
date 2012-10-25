<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Welcome to teacher resource page.</h1>
<div style="text-align: center;">

	${user.user.email }<br>
	${user.user.id }<br>
	${user.user.name}<br>
	<a href='<c:url value="/admin/teacher/resource/add"></c:url>'>添加资源</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='<c:url value="/admin/teacher/resource/type"></c:url>'>类别管理</a><br>
	<table width="500" border="1" cellspacing="0" cellpadding="5">
		<tr><td>文件名称</td><td>发布时间</td><td>文件描述</td><td>文件类型</td><td>详细操作</td></tr>
		<c:forEach items="${page.content}" var="page">
			<tr><td align="left">${page.name}</td>
			<td align="center">${page.date}</td>
			<td>${page.description}</td>
			<td>${page.resourceType.typeName}</td>
			<td><a href='<c:url value="/admin/teacher/resource/dele?id=${page.id }"></c:url>'> 删除</a> | <a href="${page.savePath}">下载</a></td></tr>
		</c:forEach>
	</table>
	<br/>
			 <div class="pagination">
				<c:if test="${page.totalPages > 0}">
					<c:set var="prev" value="${page.number-1}" scope="page"></c:set>
					<c:set var="next" value="${page.number+1}" scope="page"></c:set>
					<ul>
						<c:choose>
							<c:when test="${page.hasPreviousPage()}">
								<li><a href='<c:url value="/admin/teacher/resource/list?pageNumber=${prev}" />'>Prev</a></li></c:when>
							<c:otherwise>
								<li><a href="#" class="disabled">Prev</a></li></c:otherwise>
						</c:choose>
					    <li><a href="#" class="active">${page.number+1}/${page.totalPages}</a></li>
					    <c:choose>
							<c:when test="${page.hasNextPage()}">
								<li><a href='<c:url value="/admin/teacher/resource/list?pageNumber=${next}" />'>Next</a></li></c:when>
							<c:otherwise>
								<li><a href="#" class="disabled">Next</a></li></c:otherwise>
						</c:choose>
					    
					  </ul>
				</c:if> 
			</div>
</div>