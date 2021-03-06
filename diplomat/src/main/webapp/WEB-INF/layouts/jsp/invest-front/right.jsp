<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<style>
.row-fluid.custom {
	margin-bottom: 20px;
	padding: 0px 0px 10px;
	background: #FAFAFB;

}
.row-fluid.custom .row > h4 {
	color: #80b029;
	border-bottom: solid #cccccc 1.5px;
	padding-bottom: 4px;
	margin: 20px 0px 0px 0px;
}
.row-fluid.custom .row {
	margin: 0px 40px 0px 40px;
}

.row-fluid.custom .row .bb{
	border-bottom: dashed  1px;
}

.infor-block{
	max-width: 670px;
	max-height: 350px;
	overflow: hidden;
	margin-top: 10px;
}
</style>

<!-- investcompany resource -->
<div class="row-fluid custom round">
		<div class="row">
			<h4>投资机构介绍</h4>
		</div>
		<div class="row">
			<div class="infor-block">
				${investComInfo.companyinfo}
			</div>
			<div style="text-align: right;"><br><a href="<c:url value="/investcompany/${investComUserInfo.id}/about"></c:url>"> 查看全部>></a></div>
		</div>


	<div class="row"><h4>新闻动态 </h4></div>
	
	<div class="row">
		<table cellpadding="4" width="100%" style="margin-top: 10px;">
			<tbody>
				<c:forEach var="anno" items="${annoPage.content}" begin="0" end="4">
					<tr>
						<td  width="80%" class="bb"><a href="<c:url value="/investcompany/${investComUserInfo.id}/announcement/view/${anno.id}"></c:url>">${anno.title}</a>
						</td>
						<td class="bb">
							${anno.date}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div style="text-align: right;">
			<br><a href="<c:url value="/investcompany/${investComUserInfo.id}/announcement/list"></c:url>"> 查看全部>></a>
		</div>
	</div>



	<div class="row"><h4>成功案例 </h4></div>
	
	<div class="row">
		<table cellpadding="4" width="100%" style="margin-top: 10px;">
			<tbody>
				<c:forEach var="successcase" items="${casePage.content}" begin="0" end="4">
					<tr>
						<td  width="80%" class="bb"><a href="<c:url value="/investcompany/${investComUserInfo.id}/successcase/view/${successcase.id}"></c:url>">${successcase.name}</a>
						</td>
						<td class="bb">
							${successcase.date}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div style="text-align: right;">
			<br><a href="<c:url value="/investcompany/${investComUserInfo.id}/successcase/list"></c:url>"> 查看全部>></a>
		</div>
	</div>
</div>