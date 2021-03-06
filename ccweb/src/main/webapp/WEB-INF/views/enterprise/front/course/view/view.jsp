<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.row-fluid.custom {
	margin-bottom: 20px;
	padding: 0px 0px 10px;
	/*background: #FAFAFB;*/

}
.row-fluid.custom .row > h4 {
	color: #80b029;
	border-bottom: solid #f77605 1.5px;
	padding-bottom: 4px;
	margin: 0px 0px 0px 0px;
	padding:0px 10px 5px 10px;
}

.row-fluid.custom .row {
	margin: 0px 10px 0px 10px;
	text-align: center;
}

.row-fluid.custom .row .content {
	width: 80%;
	padding:0px 0px 0px 100px;
}
.row-fluid.custom .row .content .detail{
	width: 45%;
	float: left;
	margin: 20px 0px 20px 0px;
	text-align: left;
	line-height: 24px;
}
.row-fluid.custom .content .bb{
	border-bottom: dashed #cccccc 1px;
}


.tabbable .tab-content {
	border: solid 1px #f77605;
	margin-top: -20px;
}

.tabbable .tab-content .tab-pane .title{
	padding:0px 20px 0px 20px;
}
.tabbable .tab-content .tab-pane .content{
	padding:0px 20px 0px 20px;
	text-align: left;
	margin:10px 10px;
	width: 700px;
}
.tabbable .tab-content .tab-pane .title >h4{
	color: #80b029;
	border-bottom: solid #f77605 1.5px;
	padding: 0px 0px 4px 10px;
	text-align: left;
	font-size: 15px;
	letter-spacing: 0px;
}
</style>

<div  class="row-fluid custom ">
	<div class="row" style="border: solid 1px #f77605;" >
		<div class="content" >
			<div class="detail" style="text-align: center;">
				<c:choose>
					<c:when test="${course.courseCover != null && course.courseCover != ''}">
						<a href='#'> <img src="<c:url value='${course.courseCover }'></c:url>" style="width: 200px; height: 150px;" />
						</a>
					</c:when>
					<c:otherwise>
						<a href='#'> <img src='<c:url value="/resources/img/logo.png"></c:url>' style="width: 200px; height: 150px;" />
						</a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="detail" >
				<div style="width: 200px; font-size: 18px; color: #80b029;" id="content">${course.courseName}</div>
				${course.user.name}<br>
				 课程类别：${course.cType.typeName }<br>
				发布时间：${course.courseDate }<br>
				课程售价：${course.price } 元 <br>
				<!-- <a href="#" style="margin-right: 5px;"><img src='<c:url value="/resources/img/default/front/epre.png"></c:url>'  /></a> -->
				<c:if test="${buyFlag == 'bought'}">
					<img src='<c:url value="/resources/img/default/front/bought.png"></c:url>'  />
				</c:if>
				<c:if test="${buyFlag == 'buy'}">
					<a  href='<c:url value="/course/cart/view/${course.id}"></c:url>'><img src='<c:url value="/resources/img/default/front/ebuy.png"></c:url>'  /></a>
				</c:if>
			</div>
		</div>
	</div>
	<div class="row" style="margin-top: 30px;">
		<div class="tabbable">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#course_info_tab" data-toggle="tab">课程介绍</a></li>
				<li><a href="#purchase_info_tab" data-toggle="tab">购买记录</a></li>
				<li><a href="#comment_tab" data-toggle="tab">评价详情</a></li>
			</ul>
			<div class="tab-content" >
				<div class="tab-pane active " id="course_info_tab">
					<div class="title">
						<h4>课程详情</h4>
					</div>
					<div class="content">
						<c:choose>
							<c:when test="${course.courseDesc!=null}">
								${course.courseDesc}
							</c:when>
							<c:otherwise>
								暂无内容课程介绍
							</c:otherwise>
						</c:choose>
					</div>
						<div class="title">
						<h4>目标人群</h4>
					</div>
					<div class="content">
						<c:choose>
							<c:when test="${course.targetPerson!=null}">
								${course.targetPerson}
							</c:when>
							<c:otherwise>
								暂无内容
							</c:otherwise>
						</c:choose>
					</div>
						<div class="title">
						<h4>课程看点</h4>
					</div>
					<div class="content">
						<c:choose>
							<c:when test="${course.courseCharacter!=null }">
								${course.courseCharacter}
							</c:when>
							<c:otherwise>
								暂无内容
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="tab-pane " id="purchase_info_tab">
					<div class="content">尚未有人购买</div>
				</div>
				<div class="tab-pane " id="comment_tab">
					<div class="content">
						<c:choose>
							<c:when test="${userCourseCount>0 }">
								<table cellpadding="5" style="width: 100%;" >
									<thead>
										<tr style="border-bottom: solid #f77605 1.5px;"><th>评价人</th><th>评论内容</th><th>时间</th></tr>
									</thead>
									<tbody>
										<c:forEach items="${CourseComment }" var="comment" varStatus="i" >
											<tr style="border-bottom: solid #ccc 1px;">
												<!-- <td  width="10%" align="center">${i.index+1 }</td> -->
												<td align="center" width="10%">${comment.userName }</td>
												<td align="left" width="65%"> ${comment.userCourse.commentDesc }</td>
												<td width="25%" align="center"><fmt:formatDate value="${comment.userCourse.commentDate }" pattern="yyyy-MM-dd HH:mm"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
							<c:otherwise>
								暂无内容
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
