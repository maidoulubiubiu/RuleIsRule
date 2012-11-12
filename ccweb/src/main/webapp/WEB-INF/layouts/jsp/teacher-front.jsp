<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/layouts/jsp/common/_html_head.jsp"></jsp:include>
	<style>
	body {
		max-width: 1600px;
		width: 100%;
	}
	
	.container-fluid {
		padding-right: 0px;
		padding-left: 0px;
	}
	
	.container-fluid.wrapper {
		background: url(/ccweb/resources/img/teacher_front_bg.jpg) repeat;
	}
	.container-fluid.custom {
		min-width: 1024px;
		max-width: 1024px;
		width: 1024px;
		margin: 0 auto;
	}
	</style>
</head>
<body>
	<tiles:insertAttribute name="top-navbar" />
	<div class="container-fluid wrapper">
		<div class="container-fluid custom">
			<div class="row-fluid">
				<tiles:insertAttribute name="banner" />
			</div>
			<div class="row-fluid">
				<tiles:insertAttribute name="navbar" />
			</div>
			<div class="row-fluid content-panel">
				<div class="span3">
					<tiles:insertAttribute name="left" />
				</div>
				<!--/span-->
				<div class="span9">
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

	<script type="text/javascript">
		$(document).ready(function() {
			/*button apply: show input fields when click apply*/
			$('#button_apply').bind('click', function() {
				$(this).css('display', 'none');
				$('#payment_details').css('display', 'block');
			});

		});
	</script>
</body>
</html>