'usr strict';



(function() {
	var app = angular.module('userinfo', []);
	app.controller('user', ['$http','$scope' , function($http, $scope) {
		$scope.user = user;
	}]);

})();
