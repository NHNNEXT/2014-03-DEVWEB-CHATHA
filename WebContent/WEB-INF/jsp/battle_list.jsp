<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>BattleList - RealRank</title>

<link href="/css/battlelist.css">
</head>
<body ng-app="module">
<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				<%@ include file="/WEB-INF/include/_userheader.jspf"%>
				<div class="panel-group" id="accordion" ng-controller="BattleController">
				
					<!-- 내가 신청 -->
					<div class="panel panel-default battles-send">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#battles-send">보낸 도전</a>
								<span class="badge">{{battleList.sent.length}}</span>
							</h4>
						</div>
						<div id="battles-send" class="panel-collapse collapse in">
							<ul class="list-group">
							  <li class="list-group-item" ng-repeat="sentBattle in battleList.sent">
							  	To : <strong>{{sentBattle.champion}}</strong> 
							  	<span class="label label-danger">{{sentBattle.opponentScore}}</span>
							  	<span class="label label-warning">{{sentBattle.opponentReputation}}</span>
							  	<span class="date">{{dateFormatter(sentBattle.req_time)}}</span><span class="time">{{timeFormatter(sentBattle.req_time)}}</span>
							  	<button type="button" class="btn btn-default btn-xs pull-right btn-warning" ng-click="cancelChallenge(sentBattle)">
								  <span class="glyphicon glyphicon-remove"></span>cancel
								</button>
							  </li>
							</ul>
						</div>
					</div>
					<!-- battles-send -->
	
					<!-- 신청 받음 -->
					<div class="panel panel-default battles-recieve">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#battles-recieve">받은 도전</a> <span class="badge">{{battleList.received.length}}</span>
							</h4>
						</div>
						<div id="battles-recieve" class="panel-collapse collapse in">
							<ul class="list-group">
								<li class="list-group-item" ng-repeat="receivedBattle in battleList.received">
									From : <strong>{{receivedBattle.challenger}}</strong>
									<span class="label label-danger">{{receivedBattle.opponentScore}}</span>
									<span class="label label-warning">{{receivedBattle.opponentReputation}}</span>
									<span class="date">{{dateFormatter(receivedBattle.req_time)}}</span><span class="time">{{timeFormatter(receivedBattle.req_time)}}</span>
									<div class="btn-group pull-right" role="group">
										<button type="button" class="btn btn-default btn-xs btn-success" ng-click="acceptChallenge(receivedBattle)">
											<span class="glyphicon glyphicon-ok" ></span>accept
										</button>
										<button type="button" class="btn btn-default btn-xs btn-warning" btn-warning ng-click="denyChallenge(receivedBattle)">
											<span class="glyphicon glyphicon-remove" ></span>deny
										</button>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<!-- .battles-recieve -->
					
	
					<!-- 결투중 -->
					<div class="panel panel-default battles-in-progress">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#battles-in-progress">진행중인 도전</a> <span class="badge">{{battleList.progress.length}}</span>
							</h4>
						</div>
						<div id="battles-in-progress" class="panel-collapse collapse in">
							<ul class="list-group">
								<li class="list-group-item" ng-repeat="acceptedBattle in battleList.accepted">
								
									<strong>{{acceptedBattle.champion}}</strong>
									vs
									<strong>{{acceptedBattle.challenger}}</strong>
									
									<span class="date">{{dateFormatter(acceptedBattle.req_time)}}</span><span class="time">{{timeFormatter(acceptedBattle.req_time)}}</span>
									<button type="button" class="btn btn-default btn-xs pull-right btn-primary" ng-click="startChallenge(acceptedBattle)">
										<span class="glyphicon glyphicon-ok" ></span>GO!
									</button>
								</li>
							</ul>
						</div>
					</div>
					<!-- .battles-in-progress -->
				</div>
			</div>			
		</div>
	</div>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/battle_list.js"></script>
</body>
</html>
