<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<link href="/support/signup/signup.css">
<title>Welcome - RealRank</title>
</head>
<body ng-app="signup">
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4" ng-controller="SignUp">
				<h1 class="text-center login-title">RealRank SignUp</h1>
				<div class="account-wall">
					<input ng-model="user.userId" type="text" class="form-control"
						placeholder="User ID" autofocus> <input
						ng-model="user.password" type="password" class="form-control"
						placeholder="Password"> <input type="password"
						class="form-control" placeholder="Password Confirm"> <br>
					<input ng-model="user.email" type="text" class="form-control"
						placeholder="Email"> <input type="radio"
						ng-model="user.gender" value="m"> 남<br> <input
						type="radio" ng-model="user.gender" value="f"> 여<br>
			Birthday: <input ng-model="user.birthday" type="text" id="datepicker">
<br><br>
					<div ng-click="submit()" class="btn btn-lg btn-primary btn-block">Sign
						in</div>
					<h4>
						{{state.errorMessage}}
						</h5>
				</div>
				
			</div>
		</div>
	</div>
	<%@ include file="/components/_imports.jspf"%>
	<script src="/support/signup/signup.js"></script>
</body>
</html>