<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<%@ include file="/components/_imports.jspf"%>
	<link href="/support/signin/signin.css">
	<script src="/support/signin/signin.js"></script>
	<title>Welcome - RealRank</title>
</head>
<body ng-app="login">
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<form name="loginForm" ng-controller="LoginController as loginCtrl" ng-submit="check(loginForm) && submit()" novalidate>
					<h1 class="text-center login-title">RealRank Login</h1>
					<div class="account-wall">
						<fieldset class="form-group">
						<input ng-model="user.userId" type="text" class="form-control" placeholder="User ID" autofocus required />
						</fieldset>
						<fieldset class="form-group">
						<input ng-model="user.password" type="password" class="form-control" placeholder="Password" required /><br/>
						</fieldset>
						<fieldset class="form-group">
						<input type="submit" class="btn btn-lg btn-primary btn-block" value="Sign in" />
						</fieldset>
						<h4>{{state.errorMessage}}</h4>
					</div>
					<a href="/support/signup/" class="text-center new-account">Create an account </a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>