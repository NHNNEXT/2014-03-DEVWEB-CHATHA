<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/include/_css.jspf"%>
<link href="/battle/send/battlesend.css">
<title>BattleList - RealRank</title>
</head>
<body ng-app="BattleSend">
	<div class="container">
		<div class="row">
		
			<div class="col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				<%@ include file="/WEB-INF/include/_userheader.jspf"%>
				<div class="panel-group" id="accordion" ng-controller="BattleController">
				
					<!-- 내가 신청 -->
					<div class="panel panel-default battles-send">
						<div class="panel-heading">
							<h4 class="panel-title">도전 보내기</h4>
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
					<!--div.send-battle-->
				</div>
				<!--div.panel-group-->
			</div>			
		</div>
		<!--div.row-->
	</div>
	
	<%@ include file="/WEB-INF/include/_imports.jspf"%>
	<script src="/js/battlesend.js"></script>
</body>
</html>