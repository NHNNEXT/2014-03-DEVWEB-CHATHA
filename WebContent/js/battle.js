'use strict';

(function() {
	
	app.controller('battleController', ['$http','$scope' , function($http, $scope) {
		$scope.battle = battle;
		$scope.user = user;
		
		$scope.endBattle=function(battle){
			var winner=battle.challenger;
			if(winner==user.id){
				winner=battle.champion;
			}
			console.log(winner)
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
				data: { cid : winner}
			})
		}

		
	}]);
})();
