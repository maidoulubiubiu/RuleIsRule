<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>

.photos{
	width: 100px;
}
.expert{
	float: left;
	text-align: center;
	margin: 10px 15px;
}
</style>

<div class="cont_block">
 	<div class="_titles">
 		<img  src="<c:url value='/resources/img/default/title7.png' ></c:url>">
 	</div>
	<table style=" width: 95%; "  cellpadding="5" align="center">
		<tbody>
			<tr>
				<td>
					<c:forEach items="${page.content}" var="allies">
						<div class="expert" >
							<c:choose>
								<c:when test="${allies.photo_url!=null||allies.photo_url!=''}">
									<img src='<c:url value="${allies.photo_url }"></c:url>'  class="img-circle photos" />
								</c:when>
								<c:otherwise>
									<img src='<c:url value="/resources/img/avatar/avatar40.png"></c:url>'  class="photos"/>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${allies.name==null||allies.name==''}">
									<div id="contentlimite" style="width: 100px; "><a href='<c:url value="/allies/${allies.id}"></c:url>'>尚未添加</a></div>
								</c:when>
								<c:otherwise>
									<div id="contentlimite" style="width: 100px; "><a href='<c:url value="/allies/${allies.id}"></c:url>'>${allies.name }</a></div>
								</c:otherwise>
							</c:choose>
						</div>
					</c:forEach>
				</td>
			</tr>
		</tbody>
		<tfoot>
	    	<tr><td>
					<jsp:include page="/WEB-INF/views/_shared/pagination.jsp"></jsp:include>
	   		 </td></tr>
		</tfoot>
	</table>
 </div>
