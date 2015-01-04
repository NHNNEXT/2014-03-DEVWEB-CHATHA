<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/_css.jspf"%>
<link rel='stylesheet' media="screen"
	href="/css/userinfo.css">
<title>Real RANK!</title>
</head>
<body ng-app="userinfo">
<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container">
		<div class="row">
			<div class="jumbotron">
				<h1>RealRank</h1>
			</div>
		</div>
		<div class="row" ng-controller="user">
			<div id='myProfile' class="col-md-8">
				<center>
					<img src="http://www.efmaefm.org/0CONTACTUS/default.jpg"
						width="140" height="140" border="0" class="img-circle"></a>
					<h3 class="media-heading">
						{{user.nickname}} <small> {{user.id}}</small>
					</h3>
					My WinnerLink : <a href="#">http://localhost:8080/winner/{{user.id}}</a>
					<div>
						<span><strong>Achievements: </strong></span> <span
							class="label label-warning">VETERAN</span> <span
							class="label label-info">D-1 TOP5</span> <span
							class="label label-info">LAST WEEK TOP5</span>
					</div>
				</center>
			</div>
			<div class="col-md-4">
				<div id="myPosition">
					<h2>Point : {{score.score}}</h2>
					내 위치 >
				</div>

				<div id="verticalLine"></div>
			</div>
		</div>

	</div>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script>
		var user = ${user};
		var score = ${score};
	</script>
	<script src="/js/userinfo.js"></script>
</body>
</html>