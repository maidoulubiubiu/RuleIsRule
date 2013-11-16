<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.row-fluid.custom {
	margin-bottom: 20px;
	padding: 0px 0px 10px;
	
}
.round {
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.row-fluid .custom .row {
	margin: 10px 40px;
	color: #80b029;
	border-bottom: solid #cccccc 1.5px;
}
.row-fluid.custom .content {
	margin: 20px 40px;
}


</style>
<script type="text/javascript">

</script>
<div class="row-fluid custom round">
	<div class="row">
		<h4>专利详细</h4>
	</div>
	<div class="content">
			<table width="100%" height="" border="1" cellpadding="5" cellspacing="0"  class="blue">
			<tr>
			    <td width="122" align="center" bgcolor="#f3f3f3">专利号码</td>
			    <td width="190">${patent.patentNum}</td>
			    <td width="103" align="center" bgcolor="#f3f3f3">申请日期</td>
			    <td width="169">${patent.applicationDate}</td>
			  </tr>
			  <tr>
			    <td width="122" align="center" bgcolor="#f3f3f3">适用领域</td>
			    <td width="190">${patent.patentField.fieldName}</td>
			    <td width="103" align="center" bgcolor="#f3f3f3">专利类型</td>
			    <td width="169">${patent.patentType}</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">专利名称</td>
			    <td colspan="3">${patent.patentName }</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">公开号码</td>
			    <td>${patent.publishNum}</td>
			    <td align="center" bgcolor="#f3f3f3">公开日期</td>
			    <td>${patent.publishDate}</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">主分类号</td>
			    <td colspan="3">${patent.mainClassNum}</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">分类号码</td>
			    <td colspan="3">${patent.classNum }</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">申请人</td>
			    <td>${patent.applicant }</td>
			    <td align="center" bgcolor="#f3f3f3">发明人</td>
			    <td>${patent.inventer }</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">联系地址</td>
			    <td colspan="3">${patent.address }</td>
			  </tr>
			  <tr>
			    <td align="center" bgcolor="#f3f3f3">代理人</td>
			    <td>${patent.agency }</td>
			    <td align="center" bgcolor="#f3f3f3">代理机构</td>
			    <td>${patent.agent }</td>
			  </tr>
			</table>
			<br>
			<div style="width: 100%; background-color: #f3f3f3;text-align: left;border:1px solid #b1cf75;"><span style="margin: 5px 5px;">专利摘要</span></div>
			<div style="width: 100%; text-align: left; margin:5px 5px;">
				${patent.summary }
			</div>
	</div>
	<div class="content" >
		<a href='<c:url value="/admin/patent/list"></c:url>'  class="btn btn-success">返回</a>&nbsp;&nbsp;
	</div>
</div>