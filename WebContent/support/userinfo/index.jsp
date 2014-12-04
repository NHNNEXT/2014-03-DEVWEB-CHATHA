<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<link rel='stylesheet' media="screen"
	href="/support/userinfo/userinfo.css">
<title>Real RANK!</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="jumbotron">
				<h1>RealRank</h1>
			</div>
		</div>
		<div class="row">
			<div id='myProfile' class="col-md-8">
				<center>
					<img
						src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRbezqZpEuwGSvitKy3wrwnth5kysKdRqBW54cAszm_wiutku3R"
						name="aboutme" width="140" height="140" border="0"
						class="img-circle"></a>
					<h3 class="media-heading">
						Joe Sixpack <small> KOR</small>
					</h3>
					<span><strong>Achievements: </strong></span> <span
						class="label label-warning">VETERAN</span> <span
						class="label label-info">D-1 TOP5</span> <span
						class="label label-info">LAST WEEK TOP5</span>
				</center>
			</div>
			<div class="col-md-4">
				<div id="myPosition">오늘 순위 ></div>

				<div id="verticalLine"></div>
			</div>
		</div>

	</div>
	<%@ include file="/components/_imports.jspf"%>
	<!-- <script src="/support/userinfo/userinfo.js"></script> -->
</body>
</html>