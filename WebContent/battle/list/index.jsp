<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<link href="/battle/list/battlelist.css">
<title>BattleList - RealRank</title>
</head>
<body ng-app="BattleList">
	<div class="container">
		<div class="row">
		
		
			<div class="col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				<div class="page-header" ng-controller="UserController">
				  <h1>Champ <small>champ@gmail.cm</small></h1>
				</div>

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
							  	{{sentBattle.champion}} vs {{sentBattle.challenger}}
							  	<span class="date">{{dateFormatter(sentBattle.req_time)}}</span><span class="time">{{timeFormatter(sentBattle.req_time)}}</span>
							  	<button type="button" class="btn btn-default btn-xs pull-right">
								  <span class="glyphicon glyphicon-remove"></span>cancel
								</button>
							  </li>
							</ul>
						</div>
					</div>
	
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
									{{receivedBattle.champion}} vs {{receivedBattle.challenger}}
									<span class="date">{{dateFormatter(receivedBattle.req_time)}}</span><span class="time">{{timeFormatter(receivedBattle.req_time)}}</span>
									<button type="button" class="btn btn-default btn-xs pull-right">
										<span class="glyphicon glyphicon-ok"></span>accept
									</button>
								</li>
							</ul>
						</div>
					</div>
	
					
	
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
								<li class="list-group-item" ng-repeat="progressBattle in battleList.progress">
									{{progressBattle.champion}} vs {{progressBattle.challenger}}
									<span class="date">{{dateFormatter(progressBattle.req_time)}}</span><span class="time">{{timeFormatter(progressBattle.req_time)}}</span>
									<button type="button" class="btn btn-default btn-xs pull-right">
										<span class="glyphicon glyphicon-ok"></span>accept
									</button>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>			
		</div>
	</div>
	
	<%@ include file="/components/_imports.jspf"%>
	<script src="/battle/list/battlelist.js"></script>
</body>
</html>