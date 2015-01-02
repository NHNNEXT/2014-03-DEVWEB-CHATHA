'usr strict';

(function() {
	var app = angular.module('BattleList', []);
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
			
			return year+"년 "+month+"월 "+date+"일 "+day+"요일";
		};
		
		$scope.timeFormatter = function(dateString){
			var dateObject = new Date(dateString),
				hours = dateObject.getHours() % 12,
				amPm = (dateObject.getHours() > 11) ? '오전' : '오후',
				minutes = dateObject.getMinutes();
			
			return amPm +" "+hours+"시"+minutes+"분";
		};
		
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
	}]);
	
	
})();
