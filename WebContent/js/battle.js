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
	    $scope.tickInterval = 1000;

	    function tick() {
	        $scope.clock = new Date().getTime()
	        $timeout(tick, $scope.tickInterval);
	    }
	    $timeout(tick, $scope.tickInterval);
		
	    $scope.getElapsedTime=function () {
		    var msPerMinute = 60 * 1000;
		    var msPerHour = msPerMinute * 60;
		    var msPerDay = msPerHour * 24;
		    var msPerMonth = msPerDay * 30;
		    var msPerYear = msPerDay * 365;

		    var elapsed = $scope.clock - $scope.battle.startTimestamp;

		    if (elapsed < msPerMinute) {
		         return Math.round(elapsed/1000) + ' 초';   
		    }else if (elapsed < msPerHour) {
		         return Math.round(elapsed/msPerMinute) + ' 분';   
		    }else if (elapsed < msPerDay ) {
		         return Math.round(elapsed/msPerHour ) + ' 시';   
		    }else if (elapsed < msPerMonth) {
		        return Math.round(elapsed/msPerDay) + ' 일';   
		    }else if (elapsed < msPerYear) {
		        return Math.round(elapsed/msPerMonth) + ' 달';   
		    }else {
		        return Math.round(elapsed/msPerYear ) + ' 년';   
		    }
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
			window.location.href = '/users/userinfo.rk';
		}

		
	}]);
})();
