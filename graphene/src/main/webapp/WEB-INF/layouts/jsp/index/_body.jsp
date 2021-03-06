<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
body {
	max-width: 1150px;
	width: 100%;
	font-family: 'Microsoft YaHei';
}

.container-fluid {
	padding-right: 0px;
	padding-left: 0px;
}

.container-fluid .row-fluid .row.bg {
	margin: 0px auto;
	height: 600px;
	background: url(<c:url value='/resources/img/default/index/home_bg.png' ></c:url>) center no-repeat;
}

.span9.left {
	width: 100%;
	text-align: left;
	margin-top: -20px;
}

.span9.right {
	width: 55%;
	margin-top: -5px;
}

.span4.login-panel {
	height: 78%;
	width: 305px;
	margin: 60px -50px auto;
	text-align: center;
}

.span4.register-panel {
	height: 78%;
	width: 305px;
	margin: 60px -50px auto;
	text-align: center;
}

.span4.forgotPsw-panel {
	height: 78%;
	width: 305px;
	margin: 60px -50px auto;
	text-align: center;
}

.login-context {
	width: 80%;
	height: 100%;
	margin:0 auto;
}

.login-logo {
	height: 80px;
	margin:5px auto;
	text-align: center;
}

.form-horizontal {
	margin: 0 auto;
}

.form-horizontal .controls {
	margin-left: 0px;
}

.form-horizontal-register {
	margin: 0 auto;
}
.hr-bg {
	height: 5px;
	background: url(<c:url value='/resources/img/default/index/hr.png' ></c:url>) center no-repeat;
}
.login-hr-bg {
	height: 30px;
	background: url(<c:url value='/resources/img/default/index/hr.png' ></c:url>) center no-repeat;
}
.register-hr-bg {
	height: 30px;
	background: url(<c:url value='/resources/img/default/index/hr.png' ></c:url>) center no-repeat;
}
.radio.inline{
	width: 20%;
}
.help-inline{
	margin-top: -5px;
}
.help-inline-third{
	margin-left:100px;
	margin-top: -15px;
}
.title-index{
	color:#fff;
	text-align: left;
}
function login(o) {
	alert(o.screen_name);
	window.location.href='/enterThirdParty?acc='+o.screen_name;
}
function logout() {
	window.location.href="/signout";
}
</style>
<style type="text/css" media="screen">@import url("<c:url value="/resources/js/jq-mail/jquery.autoMailSuggest.css"/>");</style>
<script type="text/javascript" charset="utf-8" src="<c:url value="/resources/js/jq-mail/jquery.autoMailSuggest.js"/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/resources/jquery/loginCheck.js"/>"></script>
<script type="text/javascript">
	$(function(){
	    $("#em").autoMailSuggest(defaultMailSuffix);
	  });
	$(function(){
	    $("#es").autoMailSuggest(defaultMailSuffix);
	  });
	$(function(){
	    $("#emailForPsw").autoMailSuggest(defaultMailSuffix);
	  });
	function checkEmailAndPwd(){
		return checkEmailAndPass("sigin_info_form",'checkEmailAndPassword');
	}
	function registerOnclick(){
		return reginsterLogin("register_info_form",'registerEmailAndPwd');
	}
	function registerThirdPartyOnclick(){
		return thirdPartyRegister('register_thirdParty_form','registerThirdParty');
	}
	function forgetPwdOnclick(){
		return forgetPwd("forgotPsw_info_form",'forgetPwdCheck');
	}
	$(document).ready(function() { 
		var thirdPartyName=$("#thirdPartyName").val();
		if(thirdPartyName!=""){
			$('#myModal').modal('show');
		}
	}); 
</script>

<div class="row bg">
	<div class="span8 left"></div>
	<div class="span4 login-panel">
		<div class="login-context">
			<form:form class="form-horizontal" action="signin" id="sigin_info_form" modelAttribute="loginForm" method="post">
				<h4 class="title-index">用户登录</h4>
				<div class="hr-bg"></div>
				<div class="controls" id="email" style="margin-top: 20px;">
					<h5 style="text-align: left;" class="title-index">邮箱地址</h5>
					<div class="controls" style="text-align: right;">
						<input type="text" id="em" name="email" placeholder="请输入您的邮箱地址" style="width: 96.17%;">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="controls" id="password" style="margin-top: -10px;">
					<h5 style="text-align: left;" class="title-index">登录密码</h5>
					<div class="controls" style="text-align: right;">
						<input type="password" id="ps" name="password" placeholder="请输入您的密码" style="width: 96.17%;">
						<span class="help-inline"  id="passwordErr"><form:errors path="psw"></form:errors></span>
					</div>
				</div>
				<div class="span9 left">
					<h5 class="controls">
						<a id="fogotPswbtn" href="<c:url value='#'></c:url>"><font color="#fff">忘记密码？</font></a>
					</h5>
				</div>
				<button class="btn btn-large btn-block btn-primary" type="submit" onclick="return checkEmailAndPwd();" style="font-family: 'Microsoft YaHei'; margin-top: 40px;">登录</button>
			</form:form>
			<div class="login-hr-bg"></div>
			<a id="registerbtn" class="btn btn-large btn-block btn-success"  style="font-family: 'Microsoft YaHei';">快速注册</a>
		</div>
	</div>
	<div class="span4 register-panel" style="display: none;">
		<div class="login-context">
			<form:form class="form-horizontal-register" style="margin-top: 10px;" id="register_info_form" action="register/common" modelAttribute="commonRegisterForm" method="post">
				<h4 class="title-index">快速注册</h4>
				<div class="hr-bg"></div>
				<div class="control" id="emails" >
					<h5 style="text-align: left; line-height: 5px; margin-top: 10px;" class="title-index">邮箱地址</h5>
					<div class="controls" style="text-align: right;">
						<input type="text" id="es" name="emails" placeholder="请输入您的邮箱地址" style="width: 96.17%;">
						<span class="help-inline" ></span>
					</div>
				</div>
				<div class="control" id="psw" style="margin-top: -20px;">
					<h5 style="text-align: left; line-height: 5px; margin-top: 10px;" class="title-index">密码</h5>
					<div class="controls" style="text-align: right;">
						<input type="password"  id="p" name="psw" placeholder="请设置您的密码" style="width: 96.17%;">
						<span class="help-inline" ></span>
					</div>
				</div>
				<div class="control" id="confirmpsw" style="margin-top: -20px;">
					<h5 style="text-align: left; line-height: 5px; margin-top: 10px;" class="title-index">密码确认</h5>
					<div class="controls" style="text-align: right;">
						<input type="password" id="conf" name="confirmpsw" placeholder="请再次输入您的密码" style="width: 96.17%;">
						<span class="help-inline" id="passwordError"><form:errors path="psw"></form:errors></span>
					</div>
				</div>
				
				<div class="control" style="margin-top: -20px;">
					<h5 style="text-align: left; line-height: 5px; margin-top: 10px;" class="title-index">角色选择</h5>
					<div class="controls" style="text-align: left; margin-top: -5px;">
						<label class="radio inline title-index" style="font-family: 'Microsoft YaHei'; font-size: 8pt; width: 20%;"> <input type="radio" name="userType" id="user" value="user" checked>普通用户</label>
						<label class="radio inline title-index" style="font-family: 'Microsoft YaHei'; font-size: 8pt; width: 20%;"> <input type="radio" name="userType" id="teacher" value="teacher" >专家用户</label>
					</div>
				</div> 
				<button type="submit" class="btn btn-large btn-block btn-primary" onclick="return registerOnclick();" style="font-family: 'Microsoft YaHei'; margin-top: 10px;">确认注册</button>
			</form:form>
			<div class="register-hr-bg"></div>
			<a id="loginbtn" class="btn btn-large btn-block btn-success" style="font-family: 'Microsoft YaHei';">用户登录</a>
		</div>
	</div>
	<div class="span4 forgotPsw-panel" style="display: none;">
		<div class="login-context">
			<form:form class="form-horizontal" action="forgotPsw" id="forgotPsw_info_form" modelAttribute="forgotPswForm" method="post">
				<h4 class="title-index">密码找回</h4>
				<div class="hr-bg"></div>
				<div style="height: 10px"></div>
				<div class="controls" id="forgotemail">
					<h5 style="text-align: left;" class="title-index">您的注册邮箱地址</h5>
					<div class="controls" style="text-align: right;">
						<input type="text" id="emailForPsw" name="forgotemail" placeholder="请输入您已注册的邮箱地址" style="width: 96.17%;">
						<span class="help-inline" id="emailError"><form:errors path="emailForPsw"></form:errors></span>
					</div>
				</div>
				<label style="clear: right;"></label>
				<div style="height: 30px;"></div>
				<button class="btn btn-large btn-block btn-primary" type="submit" onclick="return forgetPwdOnclick();" style="font-family: 'Microsoft YaHei';">发送邮件，找回密码！</button>
			</form:form>
			<div class="login-hr-bg"></div>
			<a id="tologinbtn" class="btn btn-large btn-block btn-success" style="font-family: 'Microsoft YaHei';">返回登录</a>
		</div>
	</div>
</div>

<!-- quick register modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<form:form style="margin-top: 10px; margin:auto;" id="register_thirdParty_form" action="register/thirdParty" modelAttribute="thirdPartyRegisterForm" method="post">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">绑定账号</h3>
		</div>
		<div class="modal-body">
			<div class="control" id="emails">
				<h5 style="text-align: left; line-height: 5px; margin-top: 10px;">邮箱地址</h5>
				<div class="controls" style="text-align: left;">
					<input type="text" id="es" name="emails" placeholder="请输入您的邮箱地址" style="width: 96.17%;"> <span class="help-inline-third"></span>
				</div>
			</div>
			<div class="control" id="psw" style="margin-top: -20px;">
				<h5 style="text-align: left; line-height: 5px; margin-top: 10px;">密码</h5>
				<div class="controls" style="text-align: left;">
					<input type="password" id="p" name="psw" placeholder="请设置您的密码" style="width: 96.17%;"> <span class="help-inline-third"></span>
				</div>
			</div>
			<div class="control" id="confirmpsw" style="margin-top: -20px;">
				<h5 style="text-align: left; line-height: 5px; margin-top: 10px;">密码确认</h5>
				<div class="controls" style="text-align: left;">
					<input type="password" id="conf" name="confirmpsw" placeholder="请再次输入您的密码" style="width: 96.17%;"> <span class="help-inline-third" id="passwordError"><form:errors path="psw"></form:errors></span>
				</div>
			</div>
			<div class="control" style="margin-top: -20px;">
				<h5 style="text-align: left; line-height: 5px; margin-top: 10px;">角色选择</h5>
				<div class="controls" style="text-align: left; margin-top: -5px;">
					<label class="radio inline" style="font-size: 8pt; width: 20%;"> <input type="radio" name="userType" id="user" value="user" checked>普通用户
					</label> <label class="radio inline" style="font-size: 8pt; width: 20%;"> <input type="radio" name="userType" id="teacher" value="teacher">教师用户
					</label> <label class="radio inline" style=" font-size: 8pt; width: 20%;"> <input type="radio" name="userType" id="enterprise" value="enterprise">企业用户
					</label>
				</div>
			</div>
			<input id="thirdParty" name="thirdParty" value="${thirdParty}" style="display:none"/>
			<input id="thirdPartyName" name="thirdPartyName" value="${thirdPartyName}" style="display:none"/>
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary" onclick="return registerThirdPartyOnclick();">确定</button>
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		</div>
	</form:form>
</div>
