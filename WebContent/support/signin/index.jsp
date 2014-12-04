<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<link href="/support/signin/signin.css">
<title>Welcome - RealRank</title>
</head>
<body ng-app="login">
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4"
				ng-controller="LoginController">
				<h1 class="text-center login-title">RealRank Login</h1>
				<div class="account-wall">
					<input ng-model="user.userId" type="text" class="form-control" placeholder="User ID"
						autofocus> <input ng-model="user.password" type="password"
						class="form-control" placeholder="Password"> <br>
					<div ng-click="submit()" class="btn btn-lg btn-primary btn-block">Sign in</div>
					<h4>{{state.errorMessage}}</h5>
				</div>
				<a href="/support/signup/" class="text-center new-account">Create an account </a>
			</div>
		</div>
	</div>
	<%@ include file="/components/_imports.jspf"%>
	<script src="/support/signin/signin.js"></script>
</body>
</html>