<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="text-align: center;">
	<form:form action="register/common" modelAttribute="commonRegisterForm" method="post">

		<input type="text" name="email" placeholder="邮箱地址">
		<span class="help-block"><form:errors path="email"></form:errors></span>
		<input type="password" name="psw" id="pwd1" placeholder="密码">
		<span class="help-block"><form:errors path="psw"></form:errors></span>
		<input type="password" name="confirmpsw" id="pwd2" placeholder="密码确认">
		<span class="help-block"><form:errors path="confirmpsw"></form:errors></span>
		<label style="clear: right;"></label>
		<button type="submit" class="btn">注册</button>

	</form:form>

</div>