'usr strict';

(function() {
	var app = angular.module('login', []);

	app.config(function($locationProvider) {
		$locationProvider.html5Mode(true);
	});

	app.controller('LoginController', ['$http', '$scope', '$location' , function($http, $scope, $location) {
		$scope.user = {};
		$scope.state = {};
		$scope.check = function(target) {
			if (! $scope.user.id)
				$scope.state.errorMessage = "아이디를 입력하세요";
			else if (! $scope.user.password)
				$scope.state.errorMessage = "비밀번호를 입력하세요";
			return target.$valid;
		};
		$scope.submit = function() {
			$http({
				method: 'POST',
				url: '/users/login.rk',
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
				if (result.success) {
					var redirectPath = $location.search().redirect;
					location.href = (redirectPath ? redirectPath : "/users/userinfo.rk");
				} else {
					$scope.state = result;
				}
			})
			.error( function(result) {
				$scope.state = result;
			});
		}
	}]);

})();
