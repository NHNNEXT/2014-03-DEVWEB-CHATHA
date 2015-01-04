<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>BattleList - RealRank</title>
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<%@ include file="/WEB-INF/include/_css.jspf"%>
<link href="/css/battlelist.css">
</head>
<body ng-app="BattleList">
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
							  	대전 상대 : {{sentBattle.champion}} (Score : {{sentBattle.opponentScore}}, Reputation : {{sentBattle.opponentScore}})
							  	<span class="date">{{dateFormatter(sentBattle.req_time)}}</span><span class="time">{{timeFormatter(sentBattle.req_time)}}</span>
							  	<button type="button" class="btn btn-default btn-xs pull-right" ng-click="cancelChallenge(sentBattle)">
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
									대전 상대 : {{receivedBattle.challenger}} (Score : {{receivedBattle.opponentScore}}, Reputation : {{receivedBattle.opponentScore}})
									<span class="date">{{dateFormatter(receivedBattle.req_time)}}</span><span class="time">{{timeFormatter(receivedBattle.req_time)}}</span>
									
									<button type="button" class="btn btn-default btn-xs pull-right" ng-click="acceptChallenge(receivedBattle)">
										<span class="glyphicon glyphicon-ok" ></span>accept
									</button>
									<button type="button" class="btn btn-default btn-xs pull-right" ng-click="denyChallenge(receivedBattle)">
										<span class="glyphicon glyphicon-remove" ></span>deny
									</button>
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
									{{acceptedBattle.champion}} vs {{acceptedBattle.challenger}}
									<span class="date">{{dateFormatter(acceptedBattle.req_time)}}</span><span class="time">{{timeFormatter(acceptedBattle.req_time)}}</span>
									<button type="button" class="btn btn-default btn-xs pull-right">
										<span class="glyphicon glyphicon-ok"></span>GO
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
	<script>
		var user = ${user};
	</script>
	<script src="/js/battle_list.js"></script>	
</body>
</html>