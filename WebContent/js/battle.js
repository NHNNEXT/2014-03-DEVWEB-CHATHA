'use strict';

(function() {
	
	
	app.controller('battleController', ['$http','$scope','$timeout', function($http, $scope, $timeout) {
		$scope.battle = battle;
		$scope.battle.startTimestamp = new Date(battle.acc_time).getTime(); 
		
		$scope.user = user;
		$scope.user.score = score;
		
		$scope.champion = champion;
		$scope.champion.score = championScore.score;
		$scope.champion.reputation = championScore.reputation;
		
		$scope.challenger = challenger;
		$scope.challenger.score = challengerScore.score;
		$scope.challenger.reputation = challengerScore.reputation;
		
		$scope.state ={};
		
		//realtime clock
	    $scope.clock = new Date().getTime();
	    $scope.tickInterval = 77;

	    function tick() {
	        $scope.clock = new Date().getTime()
	        $timeout(tick, $scope.tickInterval);
	    }
	    $timeout(tick, $scope.tickInterval);
		
	    $scope.getLeftTime=function () {
	    	var timeLeftString = "";
	    	
	    	var msPerSecond = 1000;
	    	var msPerMinute = msPerSecond * 60;
		    var msPerHour = msPerMinute * 60;
		    var msPerDay = msPerHour * 24;

		    var timeLeft = ($scope.battle.startTimestamp+msPerDay) - $scope.clock;
		    
		    var centiSeconds = Math.floor((timeLeft%msPerSecond)/10);
		    var seconds = Math.floor((timeLeft%msPerMinute)/msPerSecond);
		    var minutes = Math.floor((timeLeft%msPerHour)/msPerMinute);
		    var hours = Math.floor(timeLeft/msPerHour);

		    function twoDigit(num){
		    	if(num == 0) return "00";
		    	if(10 > num && num > 0) return "0"+num;
		    	return num;
		    }

		    timeLeftString += twoDigit(hours)+" : ";
		    timeLeftString += twoDigit(hours)+" : ";
		    timeLeftString += twoDigit(seconds)+"' ";
		    timeLeftString += twoDigit(centiSeconds)+"\"";
		    
		    return timeLeftString;
		}
	    	    		
		$scope.endBattle=function(battle){
			var opponent = battle.challenger;
			if(opponent==user.id)
				opponent=battle.champion;
			$('html').addClass('loading');
			$http({
				method: 'POST',
				url: '/battle_end.rk',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: {winner_id : opponent, battle_id : battle.id}
			})
			.success( function(result) {	
				if (result.success) {
					alert("당신은 패배했습니다.");
					$scope.gotoMypage();			
				}
				$('html').removeClass('loading');
			})
			.error( function(result) {
				$scope.state = result;
				$('html').removeClass('loading');
			});
		};
		
		$scope.gotoMypage=function(){
			window.location.href = '/battle/battle_list.rk';
		}

		
	}]);
})();
