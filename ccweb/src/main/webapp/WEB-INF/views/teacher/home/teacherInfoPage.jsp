<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="<c:url value="/resources/jquery/emptyCheck-ajax.js" />"></script>
<h3>账号信息</h3>
<hr />
<div class="tabbable">
	<ul class="nav nav-tabs">
		<li <c:if test='${active == "psw"}'>class="active"</c:if>><a href="#security_tab" data-toggle="tab">账号安全</a></li>
		<li <c:if test='${active == "url"}'>class="active"</c:if>><a href="#p_url_tab" data-toggle="tab">个性域名</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane <c:if test='${active == "psw"}'>active</c:if>" id="security_tab">
			<form class="form-horizontal" action="changePsw" method="post" id="chanePsw_info_form">
				<div class="control-group" id="ori_psw">
					<label class="control-label" for="ori_psw">当前密码</label>
					<div class="controls">
						<input type="password"  id="oriPsw" name="ori_psw" placeholder="请输入您的当前密码" >
						<span class="help-inline" id="oriError"></span>
					</div>
				</div>
				<div class="control-group" id="new_psw">
					<label class="control-label" for="new_psw">新密码</label>
					<div class="controls">
						<input type="password" id="alter_new_psw" name="new_psw" placeholder="请输入您的新密码">
					    <span class="help-inline" id="newPsw"></span>
					</div>
				</div>
				<div class="control-group" id="confirm_new_psw">
					<label class="control-label" for="confirm_new_psw">确认密码</label>
					<div class="controls">
						<input type="password" id="alter_confirm_new_psw" name="confirm_new_psw" placeholder="再次输入一遍您的新密码">
						<span class="help-inline" id="errorPs"></span>
						<div id="errorPsw"></div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<button type="submit" onclick="changPwdOnclick();"  class="btn btn-large btn-success">修改密码</button>
					</div>
				</div>
			</form>
		</div>
		<div class="tab-pane <c:if test='${active == "url"}'>active</c:if>" id="p_url_tab">
			<form class="form-horizontal" action="selfurl" method="post" id="selfurl_info_form">
				<div class="control-group" id="url">
					<label class="control-label" for="p_url">个性域名</label>
					<div class="controls">
						<input type="text" name="url" id="p_url" value="${sessionScope.userInfo.user.self_url }" placeholder="请输入您的个性域名">
					    <span class="help-inline"></span>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<button type="submit" onclick="selfurlOnclick();" class="btn btn-large btn-success">保存</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
function selfurlOnclick(){
	checkEmptyAjax("selfurl_info_form","selfurlInfoAJAX");
	return false;
};
function changPwdOnclick(){
	checkEmptyAjax("chanePsw_info_form","pswInfoAJAX");
	return false;
};
$(document).ready(function(){
	$("#alter_confirm_new_psw").focus(function(){
		$("#errorPs").html("");
		$("#errorPsw").html("");
	});
	$("#alter_new_psw").focus(function(){
		$("#newPsw").html();
	});
	$("#oriPsw").focus(function(){
		$("#oriError").html("");
	});
	$("#oriPsw").blur(function(){
		var password=$("#oriPsw").val();
		$.ajax({
			type:"post",
			url:"<c:url value='/admin/teacher/pswInfoCheck'/>",
			data:"oriPsw="+password,
			dataType:"text",
			success:function(num){
				alert(num);
				if(num=='0'){
					$("#oriError").html("输入的密码不正确");
					return false;
				}
			}
		});
		
	});
	$("#alter_confirm_new_psw").blur(function(){
		var newPsw=$("#alter_new_psw").val().trim();
		var confirmNewPsw=$("#alter_confirm_new_psw").val().trim();
		if(newPsw!=confirmNewPsw){
			$("#errorPsw").html("两次输入的密码不一致,请重新输入!");
			return false;
		}
		
	});
	
});
</script>
		
