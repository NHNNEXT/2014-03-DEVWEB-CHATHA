<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/_css.jspf"%>
<link href="/css/register.css">
<title>Welcome - RealRank</title>
</head>
<body ng-app="signup">
<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4" ng-controller="SignUp">
				<h1 class="text-center login-title">RealRank SignUp</h1>
				<div class="account-wall">
					<input ng-model="user.id" type="text" class="form-control"
						placeholder="User ID" autofocus> <input
						ng-model="user.password" type="password" class="form-control"
						placeholder="Password"> <input type="password"
						class="form-control" placeholder="Password Confirm"> <br>
					<input ng-model="user.email" type="text" class="form-control"
						placeholder="Email"> <input ng-model="user.nickname"
						type="text" class="form-control" placeholder="NickName"> <input
						type="radio" ng-model="user.gender" value="m"> 남<br>
					<input type="radio" ng-model="user.gender" value="f"> 여<br>
					Birthday: <input ng-model="birthday" type="text" id="datepicker">
					<br> <br>
					<div ng-click="submit()" class="btn btn-lg btn-primary btn-block">Sign
						in</div>
					<h4>{{state.errorMessage}}</h4>
				</div>

			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/register.js"></script>
</body>
</html>