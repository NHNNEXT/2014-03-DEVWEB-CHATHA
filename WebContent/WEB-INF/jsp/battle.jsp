<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/_css.jspf"%>

<link href="/css/login.css" rel="stylesheet">
<link href="/css/battle.css" rel="stylesheet">

<title>Battle - RealRank</title>
</head>
<body ng-app="module">
	<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container" ng-controller="battleController">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="page-header">
				  <h1>The BATTLE is ON! <small>be a winner</small></h1>
				</div>

				<div class="row">
				  <div class="col-sm-6 col-md-6 champion">
				    <div class="thumbnail">
				      <img src="http://placehold.it/300x300" alt="profile" class="img-thumbnail">
				      <div class="caption">
				        <h3>{{champion.nickname}}<br><small>{{champion.email}}</small></h3>
				        <table class="table">
							<tbody>
								<tr>
									<th>id</th>
									<td>{{champion.id}}</td>
								</tr>
								<tr>
									<th>score</th>
									<td>{{champion.score}}</td>
								</tr>
								<tr>
									<th>reputation</th>
									<td>{{champion.reputation}}</td>
								</tr>
							</tbody>
						</table>
				      </div>
				    </div>
				  </div>
				  <div class="vs">vs</div>
				  <div class="col-sm-6 col-md-6 challenger">
				    <div class="thumbnail">
				      <img src="http://placehold.it/300x300" alt="profile" class="img-thumbnail">
				      <div class="caption">
				        <h3>{{challenger.nickname}}<br><small>{{challenger.email}}</small></h3>
				        <table class="table">
							<tbody>
								<tr>
									<th>id</th>
									<td>{{challenger.id}}</td>
								</tr>
								<tr>
									<th>score</th>
									<td>{{challenger.score}}</td>
								</tr>
								<tr>
									<th>reputation</th>
									<td>{{challenger.reputation}}</td>
								</tr>
							</tbody>
						</table>
				      </div>
				    </div>
				  </div>
				</div>
				<div class="panel panel-default">
					<table class="table">
						<tbody>
							<tr>
								<th>결투장 번호</th>
								<td>{{battle.id}}</td>
							</tr>
							<tr>
								<th>결투 시작</th>
								<td>{{battle.acc_time}}</td>
							</tr>
							<tr>
								<th>경과 시간</th>
								<td>{{getElapsedTime()}}</td>
							</tr>
						</tbody>
					</table>
				</div>

				<input type="submit" class="btn btn-lg btn-primary btn-block" value="항복" ng-click="endBattle(battle)"/>
				<br>
				<div class="alert alert-danger" role="alert">먼저 버튼을 클릭한 사람은 패배를 인정하게 됩니다.</div>
				<div>
					<h4>{{state.errorMessage}}</h4>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/battle.js"></script>
		<script>
		var user = ${user};
		var score = ${score};
		var challenger = ${challenger};
		var challengerScore = ${challengerScore};
		var champion = ${champion};
		var championScore = ${championScore};
		var battle = ${battle};
	</script>
</body>
</html>