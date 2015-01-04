/**
 * 
 */

var app = angular.module('module', []);

var controllers = {};

app.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});


function postRequest(url, data) {
	return {
		method: 'POST',
		url: url,
		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		transformRequest: function (obj){
			var str = [];
			for(var p in obj)
				str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			return str.join("&");
		},
		data: data
	}
}

app.controller('LoginController', ['$http','$scope' , function($http, $scope) {
	
	controllers.LoginController = $scope;
	
	$scope.user = {};
	$scope.submit = function() {
		$http(postRequest('/users/login.my', { user : JSON.stringify($scope.user) }))
		.success( function(result) {
			if (result.success) {
				location.reload();
			} else {
				warring(result.error);
			}
		});
	}
	
}]);
