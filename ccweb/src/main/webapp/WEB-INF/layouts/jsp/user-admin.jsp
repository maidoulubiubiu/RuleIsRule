<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/layouts/jsp/common/_html_head.jsp"></jsp:include>
	<style type="text/css" media="screen">
		@import url("<c:url value="/resources/css/admin-layout.css"/>");
		body{
			background-color: #edf1e0;
		}
	</style>
</head>
<body class="admin" >
	<tiles:insertAttribute name="top-navbar" />
	<!-- /top-navbar -->
	<div class="container-fluid"  >
		<div class="container-fluid content-centered" >
			<div class="row-fluid">
				<tiles:insertAttribute name="banner" />
			</div>
			<!--/row-->
			<tiles:insertAttribute name="breadcrumbs" />
			<div class="row-fluid content-panel">
				<div class="span3">
					<div class="left-panel">
						<tiles:insertAttribute name="left" />
					</div>
				</div>
				<!--/span-->
				<div class="span9" style="background-color: #fff; border: 1.5px solid #eed593;">
					<div class="right-panel">
						<tiles:insertAttribute name="flash" />
						<tiles:insertAttribute name="right" />
					</div>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<hr>

			<footer><tiles:insertAttribute name="footer" /></footer>

		</div>
	</div>
	<!--/.fluid-container-->
</body>
</html>