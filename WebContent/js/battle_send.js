'usr strict';


(function() {
	var app = angular.module('BattleSend', []);
	var searchTimer;
	
	app.controller('UserController', ['$http','$scope' , function($http, $scope) {
		$scope.user = user;
	}]);
	
	app.controller('battleSendFormController', ['$http','$scope' , function($http, $scope) {
		$scope.search = function(query) {
			clearTimeout(searchTimer);
			searchTimer = setTimeout(function(){
				console.log(query);
				$http({
					method: 'GET',
					url: '/users/user_search.json',
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					transformRequest: function(obj) {
						var str = [];
						for(var p in obj)
							str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
						return str.join("&");
					},
					params: {champId : query}
				})
				.success( function(result) {
					$scope.searchedUserList = result.userList;
				})
				.error( function(result) {
					$scope.state = result;
				});
			},500)
			
		};
	}]);
	
	
})();
