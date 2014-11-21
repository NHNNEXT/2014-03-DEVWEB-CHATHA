'usr strict';

(function() {
	var app = angular.module('login', []);

	app.controller('LoginController', ['$http', function($http) {
		this.data = data;
		this.submit = function() {
			$http({
				method: 'POST',
				url: 'login',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: data
			})
			.success( function(result, status) {
				if (status === 200 && result.success == true) {
					data.login = true;
					data.nickname = result.user.nickname;
					alert(result.errmsg);
				} else {
					alert(result.errmsg);
				}
			})
			.error( function(result, status) {
				alert("Connection error");
			});
		}
	}]);

	var data = {
		login: false,
		id: '',
		nickname: '',
		password: ''
	};
})();
