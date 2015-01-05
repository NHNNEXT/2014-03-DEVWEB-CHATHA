'use strict';

(function() {
	app.controller('UserController', ['$http','$scope' , function($http, $scope) {
		$scope.user = user;
	}]);
	
	app.controller('BattleController', ['$http','$scope' , function($http, $scope) {
		$scope.dateFormatter = function(dateString){
			var dateObject = new Date(dateString),
				days = "일월화수목금토",
				year = dateObject.getFullYear(),
				month = dateObject.getMonth()+1,
				date = dateObject.getDate(),
				day = days[dateObject.getDay()];
			
			return year+". "+month+". "+date+" ("+day+") ";
		};
		
		$scope.timeFormatter = function(dateString){
			var dateObject = new Date(dateString),
				hours = dateObject.getHours() % 12,
				amPm = (dateObject.getHours() > 11) ? 'AM' : 'PM',
				minutes = dateObject.getMinutes();
			
			if(hours == 0) hours = 12;
			if(minutes<10) minutes = "0"+minutes;
			return amPm +" "+hours+":"+minutes;
		};
		
		
		$scope.updateList = function(){
			$http({
				method: 'GET',
				url: '/battle/battle_list.json',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: { user : JSON.stringify($scope.user) }
			})
			.success( function(result) {
				$scope.battleList = result;
			})
			.error( function(result) {
				$scope.state = result;
			});
		}
		
		
		
		
		$http({
			method: 'GET',
			url: '/battle/battle_list.json',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			transformRequest: function(obj) {
				var str = [];
				for(var p in obj)
					str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
				return str.join("&");
			}
		})
		.success( function(result) {
			$scope.battleList = result;
		})
		.error( function(result) {
			$scope.state = result;
		});

		$scope.cancelChallenge=function(sentBattle){
			$http({
				method: 'POST',
				url: '/battle/battle_cancel.rk',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: { battleId : sentBattle.id }
			})
			.success( function(result) {
				if (result.success) {
					window.location.href = '/battle/battle_list.rk';
				} else {
					alert(result.errorMessage);
				}
			})
			.error( function(result) {
				alert(result.errorMessage);
			});
			
		};
		
		$scope.acceptChallenge=function(receivedBattle){
			$('html').addClass('loading');
			$http({
				method: 'POST',
				url: '/battle/battle_accept.rk',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: { battleId : receivedBattle.id, challengerId : receivedBattle.challenger}
			})
			.success( function(result) {
				if (result.success) {
					$('html').removeClass('loading');
					window.location.href = '/battle/battle_list.rk';
				} else {
					$('html').removeClass('loading');
					alert(result.errorMessage);
				}
			})
			.error( function(result) {
				$('html').removeClass('loading');
				alert(result.errorMessage);
			});
		};
		
		$scope.denyChallenge=function(receivedBattle){
			$('html').addClass('loading');
			$http({
				method: 'POST',
				url: '/battle/battle_deny.rk',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: { battleId : receivedBattle.id, challengerId : receivedBattle.challenger}
			})
			.success( function(result) {
				if (result.success) {
					$('html').removeClass('loading');
					window.location.href = '/battle/battle_list.rk';
				} else {
					$('html').removeClass('loading');
					alert(result.errorMessage);
				}
			})
			.error( function(result) {
				$('html').removeClass('loading');
				alert(result.errorMessage);
			});
		};
		
		$scope.startChallenge=function(battle){
			window.location.href = '/battle/battle_start.rk?bid=' + battle.id;

		}
		
	}]);
	
	
})();
