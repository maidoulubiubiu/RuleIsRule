<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html>
<head>
	<head>
	<jsp:include page="/WEB-INF/views/_shared/common/_html_head.jsp"></jsp:include>
	<style>
	body {
		width: 1024px;
		background-image: url(/courses/resources/img/default/faqbg.png);
		background-position: center top;
		background-repeat: repeat-y;
		background-color: #e8e8e8;
		margin: 0 auto;
	}
	
	.container-fluid {
		padding-right: 0px;
		padding-left: 0px;
	}

		.bg_white{
		background-color: #fff;
	}
	</style>
</head>
<body>
	<tiles:insertAttribute name="top-navbar" />
	<br><br>
	<div class="container-fluid wrapper bg_white">
		<div class="container-fluid custom ">
			<div class="row-fluid" style="padding-top: 20px;">
				<tiles:insertAttribute name="banner" />
				<tiles:insertAttribute name="navbar" />
			</div>
			<div class="row-fluid content-panel" style="padding-top: 25px;" >
				<div class="span3 margin_top_10"  style="width: 190px;  ">
					<tiles:insertAttribute name="left" />
				</div>
				
				<div class="span9 margin_top_10" style="width: 780px;" >
					<tiles:insertAttribute name="right" />
				</div>
				<!--/span-->
			</div>
			<hr>
			<footer>
				<tiles:insertAttribute name="footer" />
			</footer>
		</div>
	</div>
	<!--/.fluid-container-->
</body>
</html>
