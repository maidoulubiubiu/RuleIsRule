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
<div class="row-fluid custom round">
	<div class="row">
		<h4>站内信</h4>
	</div>
	<div class="content">	
		<div style="text-align: center; margin-bottom: 10px;">收到的站内信数量：${msgCount}件 &nbsp;&nbsp;&nbsp;：
			 	<a href='<c:url value="/admin/message/list"></c:url>'>未读信件数量${unReadCount}件</a>&nbsp;&nbsp;&nbsp;
			 	<a href='<c:url value="/admin/message/isRead"></c:url>'>已读信件数量：${isReadCount}件</a>&nbsp;&nbsp;&nbsp;
			 	<a href='<c:url value="/admin/message/isDele"></c:url>'>回收站：${isDeleCount}件</a>
		</div>
		<div style="text-align: right;">
			 <table class="blue" id="mytab" cellpadding="7" width=100%  border=0>
				 <thead><tr><th width="25%" align="center">标题</th><th width="20%" align="center">是否已读</th><th width="25%" align="center">发送时间</th><th width="20%" align="center">发件人</th></tr></thead>
				 <tbody>
			  		<c:forEach items="${page.content}" var = "page" >
			  			<tr>
			  				<td align="center"><a href='<c:url value="/admin/message/detailOne?mid=${page.sendMsg.id}&urmid=${page.id}"></c:url>'>
			  					<c:if test="${page.sendMsg.title == null}"><div style="width: 150px;" id="contentlimit">${page.sendMsg.content}</div></c:if>${page.sendMsg.title}
			  					<c:if test="${page.types == 'focus'}"><a href='<c:url value="/id/${page.commenter }" ></c:url>'>点击查看新粉丝</a></c:if>
			  					<c:if test="${page.types == 'comment'}"><a href='<c:url value="/id/${sessionUserInfo.id }" ></c:url>'>点击查看新回复</a></c:if>
							</a></td>
			  				<td align="center">
			  					<c:if test="${page.readed==1 }">
			  						未读
			  					</c:if>
			  				</td>
			  				<td align="center">${page.sendMsg.date}</td>
			  				<td align="center">${page.sendMsg.user.name}</td>
			  				<!--<td align="center"> 
			  					<div class="btn-group"> 
									<button class="btn">更多</button>  
									<button class="btn dropdown-toggle" data-toggle="dropdown">   
									<span class="caret"></span> </button>
									<ul class="dropdown-menu">
										<li>详细</li>
										<li><a href='<c:url value="/admin/message/deleOne?mid=${page.sendMsg.id}"></c:url>'>删除</a> </li>
									</ul>
								 </div> 
								 	 <!--   <a class="deleteMsgPostBtn" href="#deleteMsgPostModal" role="button" data-toggle="modal" data-target="#deleteMsgPostModal">删除</a><input type="hidden"  value="${page.sendMsg.id}" >
			  				</td>-->
			  			</tr>
			  		</c:forEach>
				  </tbody>
			</table>
			<div class="content"><jsp:include page="/WEB-INF/views/_shared/pagination.jsp"></jsp:include></div>
		</div>
		
	</div>
</div>

<!-- delete  msg Form -->
<div class="modal hide fade" id="deleteMsgPostModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">请注意</h3>
	  </div>
	  <div class="modal-body">
	    <p>你确定将该信件放入回收站吗？</p>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
	    <form action='<c:url value="/admin/message/deleOne"></c:url>' method="post" style="display: inline-block;" >
	    	<input id="meId" type="hidden" name="mId" />
	    	<button class="btn btn-success">确定</button>
	    </form>
	  </div>
</div>
<script type="text/javascript">
$(document).ready(function() {	
	$('.deleteMsgPostBtn').on('click', function() {
		var m_id =$(this).next().val();
		$('#deleteMsgPostModal #meId').val(m_id);	
	});
});
</script>		