'use strict';

(function() {
	
	app.controller('userController', ['$http','$scope' , function($http, $scope) {
		$scope.user = user;	
		$scope.score = score;
		$scope.dailyRanks = [];
		$scope.weeklyRanks = [];
		$scope.monthlyRanks = [];
		$scope.ranks = {};
		$scope.rankTitle = ["", "Daily Rank", "Weekly Rank", "Monthly Rank"];
		$scope.currentTitle = $scope.rankTitle[1];
		
		$scope.turnOnModal = function() {
			$('.modal').modal('show');
		}
		
		$scope.doAjax = function(rankType) {
			$http({
				method: 'POST',
				url: '/users/rank.rk',
				headers: {'Content-Type': 'application/x-www.form-urlencoded'},
				params: {rankType: rankType}
			}).success(function(result) {
				$scope.ranks = result;
				$scope.currentTitle = $scope.rankTitle[rankType];
			}).error(function(result) {
				warning(result.errorMessage);
			});
		}
		
		$scope.doAjax(1);
	}]);
})();
