<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/battle/send/battlesend.css">
<title>BattleList - RealRank</title>
</head>
<body ng-app="module">
<%@ include file="/WEB-INF/include/_header.jspf"%>
	<div class="container">
		<div class="row">
		
			<div class="col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				<%@ include file="/WEB-INF/include/_userheader.jspf"%>
				<div class="panel-group" id="accordion">
				
					<div class="panel panel-default battles-send">
						<div class="panel-heading">
							<h4 class="panel-title">도전 보내기</h4>
						</div>
						<div class="panel-body" ng-controller="battleSendFormController">
							<form name="battleSendFrom" class="battleSendFrom" >
								<div id="search-result" class="dropdown">
									<div class="input-group" ng-class="{'has-error has-feedback' : battleSendFrom.keyword.$error.required}">
										<input type="text" class="form-control" ng-model="keyword" ng-change="search(keyword)" ng-keyup="searchKeyPress($event)" name ="keyword" placeholder="champ@example.com" required>
										<input type="hidden" ng-model="champId" name ="champId">
										<span class="input-group-btn">
											<input class="btn btn-default" type="submit" ng-click="sendChallenge()">Go!</input>
										</span>
									</div>
									<a class="dropdown-Toggle" data-toggle="dropdown"></a>
									<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
										<li role="presentation" ng-repeat-start="user in searchedUserList" ng-if="$first">
											<a role="menuitem" href="#" tabindex="-1" class="btn-primary active" ng-click="SetQueryDo($event,$index)">
										  		<strong>{{user.id}}</strong> <small>{{user.nickname}} <u>{{user.email}}</u></small>
									  		</a>
									  	</li>
									  	<li role="presentation" ng-repeat-end ng-if="!$first">
									  		<a role="menuitem" href="#" tabindex="-1" ng-click="setQuery($event,$index)">
										  		<strong>{{user.id}}</strong> <small>{{user.nickname}} <u>{{user.email}}</u></small>
									  		</a>
									  	</li>
									</ul>
								</div>
								<br><span class="bg-warning" role="alert">{{state.errorMessage}}</span>
							</form>
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
	<script src="/js/battle_send.js"></script>
</body>
</html>
