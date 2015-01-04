'use strict';

(function() {
	app.controller('user', ['$http','$scope' , function($http, $scope) {
		$scope.user = user;	
		$scope.score = score;
	}]);
})();
