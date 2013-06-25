<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.row-fluid.custom {
	margin-bottom: 20px;
	padding: 0px 0px 10px;
	/*background: #FAFAFB;*/
}

.row-fluid.custom .row {
	margin: 0px 10px 0px 10px;
	border: solid 1px #f77605; padding:10px 10px 10px 10px
}
.row-fluid.custom .row .content {
	width: 85%; margin: 15px auto;
}

.row-fluid.custom .row  .title{
		margin: 10px 30px 0px 30px;
}

.row-fluid.custom .row  .title > h4{
	color: #80b029;
	border-bottom: solid #f77605 1.5px;
	padding-bottom: 4px;
	margin: 0px 0px 0px 0px;
	padding:0px 10px 5px 10px;
}

.row-fluid.custom .row .bb{
	border-bottom: dashed #cccccc 1px;
}
.titlebg{
	background-color:#ccdfa8; 
	font-size: 14px;
	width: 100%;
}
</style>

<div class="row-fluid custom">
	<div class="row" style="">
		<div class="title">
			<h4>公告专栏</h4>
		</div>
		<table   class="content"  cellpadding="5" >
			<!-- <tr class="titlebg" ><th align="left"><b>标题</b></th><th width="25%" align="left"><b>发布时间</b></th></tr> -->
			<tbody>
				<c:forEach items="${page.content}" var="page" varStatus="i">
					<tr  class="bb">
					<td align="center">${i.index+1}</td>
					<td align="left" >
						<div style="width: 450px;" id="content"><a href="<c:url value="/enterprise/${userInfo.id}/announcement/view/${page.id}"></c:url>">
						${page.title}</a></div>
					</td>
					<td >${page.date}</td>
					</tr>
				</c:forEach>
			</tbody>		 
		</table>
	</div>
	<div style="margin: 0px 0px 0px 10px;"><jsp:include page="/WEB-INF/views/_shared/pagination.jsp"></jsp:include></div>	 
</div>
