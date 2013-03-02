<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
!function ($) {
  $(function(){
    // carousel demo
    $('#myCarousel').carousel();
    $('#myUniversity').carousel();
    $('#myTeacher').carousel();
  });
}(window.jQuery);
</script>

<jsp:include page="/WEB-INF/views/_shared/index/_user_course.jsp"></jsp:include>
  	
<div class="container marketing">
  <h2>教学资源（${courseCount }）</h2>
  <!-- Three columns of text below the carousel -->
  <div class="row" >
    <!-- <div class="span4">
      <a href="#"><img src="resources/img/topics/240x135_1.png" width="310px"></a> 
      <h4>宇宙的形成</h4>
      <h5>杜克大学</h5>
      <p><span>更新至第二集</span></p>
    </div>
    <div class="span4">
      <a href="#"><img src="resources/img/topics/240x135_2.png" width="324px"></a> 
      <h4>药物和大脑111</h4>
      <h5>加州科技大学</h5>
      <p><span>更新至第四集</span></p>
    </div>
    <div class="span4">
      <a href="#"><img src="resources/img/topics/240x135_3.png" width="324px"></a>
      <h4>如何解释和争论</h4>
      <h5>杜克大学</h5>
      <p><span>更新至第八集</span></p>
    </div>
     -->
    <c:forEach items="${courseList}" var="c"  begin="0" end="2">
    	<div class="span4">
    		<div>
   				<c:choose>
					<c:when test="${c.teacherCourse.courseCover != null && c.teacherCourse.courseCover != ''}">
						<a href='<c:url value="/course/view/${c.teacherCourse.id}"></c:url>'> <img src='<c:url value="${url }${c.teacherCourse.courseCover }"></c:url>' style="width: 310px; height: 100px;" />
						</a>
					</c:when>
					<c:otherwise>
						<a href='<c:url value="/course/view/${c.teacherCourse.id}"></c:url>'> <img src='<c:url value="/resources/img/logo.png"></c:url>' style="width: 310px; height: 100px;" />
						</a>
					</c:otherwise>
				</c:choose>
    		</div>
    		<div style="padding: 3px;"><b>${c.teacherCourse.courseName}</b> — ${c.teacherCourse.courseType }</div>
    		<div style="padding: 3px;">
    			教师名称：<a href='<c:url value="/teacher/${c.teacherCourse.teacher.id}"></c:url>'>${c.teacherCourse.teacher.user.name}</a>
    		</div>
    		<div style="padding: 3px;">
    			学习人数：${c.userCount}人&nbsp;&nbsp;&nbsp;课程评分：
    			<c:choose>
    				<c:when test="${c.courseMark !=null && c.courseMark>0 }">
    					<fmt:formatNumber type="number" value="${c.courseMark }" maxFractionDigits="0" />分
    				</c:when>
    				<c:otherwise>
    					0分
    				</c:otherwise>
    			</c:choose>
    			&nbsp;&nbsp;&nbsp;<span style="color: red;"></span>
    		</div>
    	</div>
    </c:forEach>
  </div><!-- /.row -->
  <div class="row" >
  		<a class="view-all-courses-link btn" href='<c:url value="/course/list/type?detail=all"></c:url>' >全部课程</a>
  </div>
</div>
<div class="container teacher" >
  <h2>热门教师（Top 50）</h2>
  <div id="myTeacher" class="carousel slide">
  <div class="carousel-inner" style="margin-bottom: -50px;">
    <div class="item active">
	      <div class="row">
					<c:forEach items="${teacherLists}" var="t" begin="0" end="4">
						<div class="span2" style="width: 175px;height: 200px;">
						<c:if test="${!t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>${t.user.name }
							</a>
						</c:if>
						<c:if test="${t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>无名氏
						</a>
						</c:if>
						<c:choose>
						<c:when test="${t.user.photo_url!=null||t.user.photo_url!=''}">
						<img src='<c:url value="${url }${t.user.photo_url }"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:when>
						<c:otherwise>
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>${t.user.name }<img src='<c:url value="/resources/img/avatar/avatar40.png"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" /></a>
						</c:otherwise>
						</c:choose>
						</div>
					</c:forEach>
				</div>
    </div>
    <c:if test="${teacherLists.size()-5>0}">
    <div class="item">
       <div class="row">
			<c:forEach items="${teacherLists}" var="t" begin="5" end="10">
				<div class="span2" style="width: 175px;height: 200px;">
					<c:if test="${!t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>${t.user.name }
							</a>
						</c:if>
						<c:if test="${t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>无名氏
						</a>
						</c:if>
						<c:choose>
						<c:when test="${t.user.photo_url!=null||t.user.photo_url!=''}">
						<img src='<c:url value="${url }${t.user.photo_url }"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:when>
						<c:otherwise>
						<img src='<c:url value="/resources/img/avatar/avatar40.png"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:otherwise>
						</c:choose>
				</div>
			</c:forEach>
		</div>
    </div>
    </c:if>
  </div>
  <div class="row">
  		<a class="view-all-teachers-link btn" href='<c:url value="/teacher/list?isEnterPrise=null"></c:url>' >全部教师</a>  
  </div>
  <div style="display: none;">
	  <a class="left carousel-control" href="#myTeacher" data-slide="prev">‹</a>
	  <a class="right carousel-control" href="#myTeacher" data-slide="next">›</a>
  </div>
</div>

</div>
<div class="container teacher" >
  <h2>热门企业（Top 50）</h2>
  <div id="myUniversity" class="carousel slide">
  <div class="carousel-inner" style="margin-bottom: -50px;">
    <div class="item active">
	      <div class="row">
					<c:forEach items="${enterPriseList}" var="t" begin="0" end="4">
						<div class="span2" style="width: 175px;height: 200px;">
						<c:if test="${!t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>${t.user.name }
							</a>
						</c:if>
						<c:if test="${t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>无名氏
						</a>
						</c:if>
						<c:choose>
						<c:when test="${t.user.photo_url!=null||t.user.photo_url!=''}">
						<img src='<c:url value="${url }${t.user.photo_url }"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:when>
						<c:otherwise>
						<img src='<c:url value="/resources/img/avatar/avatar40.png"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:otherwise>
						</c:choose>
						</div>
					</c:forEach>
				</div>
    </div>
    <c:if test="${enterPriseList.size()-5>0}">
    <div class="item">
       <div class="row">
			<c:forEach items="${enterPriseList}" var="t" begin="5" end="10">
				<div class="span2" style="width: 175px;height: 200px;">
				<c:if test="${!t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>${t.user.name }
							</a>
						</c:if>
						<c:if test="${t.user.name.equals('')}">
						<a href='<c:url value="${url}/teacher/${t.id}"></c:url>'>无名氏
						</a>
						</c:if>
					<c:choose>
						<c:when test="${t.user.photo_url!=null||t.user.photo_url!=''}">
						<img src='<c:url value="${url }${t.user.photo_url }"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:when>
						<c:otherwise>
						<img src='<c:url value="/resources/img/avatar/avatar40.png"></c:url>' style="width: 175px; height:180px;margin-bottom: 10px;" />
						</c:otherwise>
						</c:choose>
				</div>
			</c:forEach>
		</div>
    </div>
    </c:if>
  </div>
  <div class="row">
  		<a class="view-all-teachers-link btn" href='<c:url value="/teacher/list?isEnterPrise=1"></c:url>' >全部企业</a>  
  </div>
  <div style="display: none;">
	  <a class="left carousel-control" href="#myTeacher" data-slide="prev">‹</a>
	  <a class="right carousel-control" href="#myTeacher" data-slide="next">›</a>
  </div>
</div>

</div>