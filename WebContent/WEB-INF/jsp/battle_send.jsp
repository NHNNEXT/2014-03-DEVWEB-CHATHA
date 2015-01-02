<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/_css.jspf"%>
<%@ include file="/WEB-INF/include/_imports.jspf"%>
<link href="/battle/send/battlesend.css">
<title>BattleList - RealRank</title>
</head>
<body ng-app="BattleSend">
	<div class="container">
		<div class="row">
		
			<div class="col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				<%@ include file="/WEB-INF/include/_userheader.jspf"%>
				<div class="panel-group" id="accordion">
				
					<!-- 내가 신청 -->
					<div class="panel panel-default battles-send">
						<div class="panel-heading">
							<h4 class="panel-title">도전 보내기</h4>
						</div>
						<div class="panel-body" ng-controller="battleSendFormController">
							<form name="battleSendFrom" class="battleSendFrom">
								<div class="input-group" ng-class="{'has-error has-feedback' : battleSendFrom.champId.$error.required}">
									<input type="text" class="form-control" ng-model="champId" ng-change="search(champId)" name ="champId" placeholder="champ@example.com" required> 
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">Go!</button>
									</span>
								</div>
							</form>
							<ul class="list-group">
								<li class="list-group-item" ng-repeat="user in searchedUserList">
									{{user.nickname}} <small> {{user.email}}</small>
								</li>
							</ul>
						</div>
					</div>
					<!--div.send-battle-->
				</div>
				<!--div.panel-group-->
			</div>			
		</div>
		<!--div.row-->
	</div>
	<script>
		var user = ${user};
	</script>
	<script src="/js/battle_send.js"></script>
</body>
</html>