<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="/support/signin" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<link rel='stylesheet' media="screen"
	href="/support/userinfo/userinfo.css">
<title>Real RANK!</title>
</head>
<body ng-app="userinfo">
	<div class="container">
		<div class="row">
			<div class="jumbotron">
				<h1>RealRank</h1>
			</div>
		</div>
		<div class="row" ng-controller="user">
			<a href="/users/logout">logout</a>
			<div id='myProfile' class="col-md-8">
				<center>
					<img src="http://www.efmaefm.org/0CONTACTUS/default.jpg"
						width="140" height="140" border="0" class="img-circle"></a>
					<h3 class="media-heading">
						{{user.nickname}} <small> {{user.userId}}</small>
					</h3>
					My WinnerLink : <a href="#">http://localhost:8080/winner/{{user.userId}}</a>
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
					<h2>Point : {{user.score}}</h2>
					내 위치 >
				</div>

				<div id="verticalLine"></div>
			</div>
		</div>

	</div>
	<%@ include file="/components/_imports.jspf"%>
	<script>
		var user = ${sessionScope.user.json};
	</script>
	<script src="/support/userinfo/userinfo.js"></script>
</body>
</html>