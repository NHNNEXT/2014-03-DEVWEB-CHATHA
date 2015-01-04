'use strict';

(function() {
	
	app.controller('battleController', ['$http','$scope' , function($http, $scope) {
		$scope.battle = battle;
		$scope.user = user;
		
		
		$scope.opponent=function(){
			var opponent = battle.challenger;
			if(opponent==user.id)
				opponent=battle.champion;
			return opponent
		}
		
		$scope.endBattle=function(battle){
			var opponent = battle.challenger;
			if(opponent==user.id)
				opponent=battle.champion;
			opponent
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
				data: { cid : opponent}
			})
		}

		
	}]);
})();
