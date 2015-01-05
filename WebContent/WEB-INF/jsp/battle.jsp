<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="/css/login.css">

<title>Battle - RealRank</title>
</head>
<body ng-app="module">
<%@ include file="/WEB-INF/include/_header.jspf"%>

	<div class="container" ng-controller="battleController">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				
				<h1>BATTLE MODE</h1>
				
				<h4>Opponent : {{opponent()}}</h4>

				<input type="submit" class="btn btn-lg btn-primary btn-block" value="LOSE" ng-click="endBattle(battle)"/>
					
				<div ng-click="gotoMypage()">
					<h4>{{state.errorMessage}}</h4>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/battle.js"></script>
		<script>
		var user = ${user};
		var battle = ${battle};
	</script>
</body>
</html>