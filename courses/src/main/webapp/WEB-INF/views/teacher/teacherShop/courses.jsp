<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	function selectType(id) {
		var sel = document.getElementById("type");
		var opt = sel.options;
		for ( var i = 0; i < opt.length; i++) {
			if (opt[i].selected) {
				var typeName = opt[i].innerHTML;
				//alert(typeName);
				window.location.href = '<c:url value="/teacher/'+id+'/course/type?detail='
						+ typeName + '"></c:url>';
			}
		}
	}
</script>
<style>
	.detail{
		padding-top: 30px;
		padding-left: 40px;
		padding-bottom: 20px;
	}
	
	.detail .content{
		margin-left: 30px;
	}
</style>
<div class="container teacher">
	<div class="detail" >
	   	<c:if test="${teacher.isEnterprise == null}">
	   		<c:choose>
				<c:when test="${teacher.user.photo_url != null && teacher.user.photo_url != ''}">
				<a href='<c:url value="${url}/teacher/${teacher.id}"></c:url>'><img src='<c:url value="${url}${teacher.user.photo_url }"></c:url>' 
						style="width: 112px;height:112px; float:left; margin-left:30px" /></a>
				</c:when>
				<c:otherwise>
				<a href='<c:url value="${url}/teacher/${teacher.id}"></c:url>'> <img src='<c:url value="/resources/img/avatar/avatar256.png"></c:url>' 
						style="width: 112px;height: 112px; float:left; margin-left:30px" /></a>
				</c:otherwise>
			 </c:choose>
	   	  <span class="content" style="font-size: 18px;"><a href='<c:url value="${url}/teacher/${teacher.id}"></c:url>'> <b>${teacher.user.name }</b></a></span>
	   	<br/><br/>
	   	<div style="width:200px;" id="contentlimit">
		   	<span class="content">学校名称：${teacher.school }</span><br/> 
		   	<span class="content">学院名称：${teacher.college }</span><br/>
		    <span class="content">专业技术：${teacher.major }</span>
		  </div>
	    </c:if>
	    <c:if  test="${teacher.isEnterprise != null}">
	    	<c:choose>
				<c:when test="${teacher.user.photo_url != null && teacher.user.photo_url != ''}">
					<img src='<c:url value="${url}${teacher.user.photo_url }"></c:url>' style="width: 112px;height:112px; float:left; margin-left:30px" />
				</c:when>
				<c:otherwise>
					<img src='<c:url value="/resources/img/avatar/avatar256.png"></c:url>' style="width: 112px;height: 112px; float:left; margin-left:30px" />
				</c:otherwise>
			 </c:choose>
	    	 <span class="content" style="font-size: 18px;"><b>${teacher.user.name }</b></span>
	    </c:if>
   </div>
</div>
<br>
<div class="container title"  >
 	 <table >
 	 	<tr>
 	 		<td width="20%" align="center"><h4>所有课程（${courseCount}）</h4></td>
 	 		<td></td>
 	 	</tr>
 	 </table>
 </div>
<div class="container user-course" >
	<c:if test="${courseCount <= 0}">
		<h4 style="margin-left: 70px;">尚未添加课程</h4>
	</c:if>
 	<c:forEach items="${teacherCourseList}" var="course">
			<table cellpadding="10" style="width: 430px;   margin-bottom: 10px;  float: left; margin-left: 50px;"  >
				<tr >
				<td   align="right"  valign="top">
						<c:choose>
							<c:when test="${course.courseCover != null && course.courseCover != ''}">
								<a href='<c:url value="/course/view/${course.id}"></c:url>'> <img src='<c:url value="${url }${course.courseCover }"></c:url>' style="width: 160px; height: 120px;float: right;" />
								</a>
							</c:when>
							<c:otherwise>
								<a href='<c:url value="/course/view/${course.id}"></c:url>'> <img src='<c:url value="/resources/img/logo.png"></c:url>' style="width: 160px; height: 120px; float: right;" />
								</a>
							</c:otherwise>
						</c:choose>
					</td>
					<td valign="top">
						<div style="width:230px;" id="contentlimit">
						<b style="font-size: 17px;">${course.courseName}</b><br><br>
						类别：${course.cType.typeName }<br>
						描述：${course.courseDesc }<br>
						日期：${course.courseDate }</div>
					</td>
				</tr>
			</table>
	</c:forEach>
 </div>