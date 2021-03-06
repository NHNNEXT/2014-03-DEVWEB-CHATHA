<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Register - Mytoday</title>
<link href="/css/register.css" rel="stylesheet" media="screen">
</head>
<body ng-app='module'>

	<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container" ng-controller="registerController">
		<div class="row">
			<div class="register text-center col-md-6 col-md-offset-3">
				<h1>회원 정보 수정</h1>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">UserID</span><input
						value="{{user.id}}" type="text" class="form-control"
						maxlength="12" disabled>
				</div>
				
				<span class='red' ng-show="!check.oldPassword()">이전 패스워드를 정확하게 입력해 주세요.</span>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">password</span><input type="password"
						class="form-control" placeholder="password" maxlength="30"
						ng-model="oldPassword">
				</div>
				<span ng-show="!check.password()">패스워드는 6~20자여야 합니다.</span> <span
					class="green" ng-show="!check.passwordConfirm()">패스워드가 일치하지
					않습니다.</span>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">new Password</span> <input
						type="password" class="form-control" placeholder="Password"
						maxlength="20" ng-model="user.password"> <input
						type="password" class="form-control"
						placeholder="Password Confirm" maxlength="20"
						ng-model="user.passwordConfirm">
				</div>
				<span ng-show="!check.email()">이메일형식 : id@domain.com</span>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Email</span><input type="text"
						class="form-control" placeholder="Email" maxlength="30"
						ng-model="user.email">
				</div>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Name</span><input type="text"
						class="form-control" placeholder="UserName" maxlength="30"
						ng-model="user.name">
				</div>
				<div class="input-group input-group-lg margin-auto">
					<span class="radio"> <label><input type="radio"
							value="M" ng-model="user.gender" name="gender" ng-checked="'M'=='{{user.gender}}'">남</label> <label>
							<input type="radio" value="F" ng-model="user.gender"
							name="gender" ng-checked="'F'=='{{user.gender}}'">여
					</label>
					</span>
				</div>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">NickName</span><input type="text"
						class="form-control" placeholder="UserName" maxlength="30"
						ng-model="user.nickname">
				</div>

			
				<div class="btn btn-info btn-lg" ng-click="submit()">정보수정</div>

			</div>
		</div>
	</div>

	<script>
		var oldUser = {};
	
	</script>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/modify.js"></script>
</body>
</html>