<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="/css/login.css">

<title>Welcome - RealRank</title>
</head>
<body ng-app="module">
<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<form name="loginForm" ng-controller="loginCtrl"
					ng-submit="check(loginForm) && submit()" novalidate>
					<h1 class="text-center login-title">RealRank Login</h1>
					<div class="account-wall">
						<fieldset class="form-group">
							<input ng-model="user.id" type="text" class="form-control"
								placeholder="User ID" autofocus required />
						</fieldset>
						<fieldset class="form-group">
							<input ng-model="user.password" type="password"
								class="form-control" placeholder="Password" required /><br />
						</fieldset>
						<fieldset class="form-group">
							<input type="submit" class="btn btn-lg btn-primary btn-block"
								value="Sign in" />
						</fieldset>
						<h4 ng-if="state != {}">{{state.errorMessage}}</h4>
					</div>
				</form>
					<a href="/users/register.rk" target="_self" class="text-center new-account">Create
						an account </a>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/login.js"></script>
</body>
</html>