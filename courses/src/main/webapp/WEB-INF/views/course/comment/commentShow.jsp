<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="comment">
		<div  align="left" style="background-color:#F7F7F7;height:40px; line-height:40px;clear:both"><h4 style=" float:left; margin-left: 40px;">用户评价</h4></div> 
		<div style="margin-left: 40px;">
			<span style="line-height:40px">总评论人数:&nbsp;&nbsp;&nbsp;${sumPerson}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			评论分数:
			<c:choose>
    				<c:when test="${courseMark !=null && courseMark>0 }">
    					<fmt:formatNumber type="number" value="${courseMark }" maxFractionDigits="0" />&nbsp;&nbsp;分
    				</c:when>
    				<c:otherwise>
    					0分
    				</c:otherwise>
    			</c:choose>
    			</span>
    			<hr style="margin-right: 40px">
			<c:forEach var="comment" items="${listcomment}" begin="0" end="3">
				<table style="width: 100%">
				  <tr>
				    <td width="20%" rowspan="3" align="center">
				    	 <c:choose >
							<c:when test="${comment.photoUrl != null && comment.photoUrl != ''}">
								<img src='<c:url value="${comment.photoUrl }"></c:url>'   style="width: 40px;height: 50px;" />
							</c:when>
							<c:otherwise>
								<img src='<c:url value="/resources/img/avatar/avatar256.png"></c:url>' style="width: 40px;height: 50px;" />
							</c:otherwise>
						</c:choose>
				    </td>
				    <td align="left" style="width:40px">${comment.userName}</td>
				    <td align="left" style="">${comment.comment.mark}</td>
				  </tr>
				  <tr>
				    <td colspan="2" align="left">${comment.comment.commentDesc}</td>
				  </tr>
				  <tr>
				    <td colspan="2" align="left">${comment.comment.commentDate }</td>
				  </tr>
				</table>
				<hr>				
			</c:forEach>
		</div>
</div>
