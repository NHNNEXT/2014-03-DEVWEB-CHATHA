/**
 * 
 */

var app = angular.module('module', []);

var controllers = {};

app.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});


app.directive(
	'spFlash',
	function() {
		return {
			restrict : 'A',
			replace : true,
			template : '<div class="flash row-fluid">'
					+ '<div class="flash-inner span4 offset4 alert alert-success" data-ng-repeat="msg in successMsg">{{msg}}</div>'
					+ '</div>',
			link : function($rootScope, scope, element, attrs) {
				$rootScope.$watch('successMsg', function(val) {
					if(val !== undefined){
						if (val.length) {
							update();
						}
					}
				}, true);

				function update() {
					$('.flash').fadeIn(500).delay(1000).fadeOut(
						500, function() {
							$rootScope.successMsg.splice(0);
							$rootScope.$apply();
						});
				}
			}
		};
	});

app
		.directive(
				'modal',
				function() {
					return {
						template : '<div class="modal fade">'
								+ '<div class="modal-dialog">'
								+ '<div class="modal-content">'
								+ '<div class="modal-header">'
								+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
								+ '<h4 class="modal-title">{{ title }}</h4>'
								+ '</div>'
								+ '<div class="modal-body" ng-transclude></div>'
								+ '</div>' + '</div>' + '</div>',
						restrict : 'E',
						transclude : true,
						replace : true,
						scope : true,
						link : function postLink(scope, element, attrs) {
							scope.title = attrs.title;
							scope.$watch(attrs.visible, function(value) {
								if (value === true)
									$(element).modal('show');
								else
									$(element).modal('hide');
							});

							$(element).on('shown.bs.modal', function() {
								scope.$apply(function() {
									scope.$parent[attrs.visible] = true;
								});
							});

							$(element).on('hidden.bs.modal', function() {
								scope.$apply(function() {
									scope.$parent[attrs.visivle] = false;
								});
							});
						}
					};
				});

function postRequest(url, data) {
	return {
		method : 'POST',
		url : url,
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		transformRequest : function(obj) {
			var str = [];
			for ( var p in obj)
				str.push(encodeURIComponent(p) + "="
						+ encodeURIComponent(obj[p]));
			return str.join("&");
		},
		data : data
	}
}

function warning(msg) {
	controllers.rootscope.successMsg.push(msg);
}

app.controller('TopMenuController', [ '$scope', function($scope) {
	$scope.alertLogin = function() {
		warning("로그인을 먼저 해주세요.");
	}
}]);

app.controller('LoginController', [ '$http', '$scope', '$rootScope',
	function($http, $scope, $rootScope) {

		controllers.LoginController = $scope;
		controllers.rootscope = $rootScope;
		$rootScope.successMsg = [];
		$scope.user = {};
		$scope.submit = function() {
			$http(postRequest('/users/login.rk', {
				user : JSON.stringify($scope.user)
			})).success(function(result) {
				if (result.success) {
					location.href = "/battle/battle_list.rk";
				} else {
					warning(result.errorMessage);
				}
			});
		}

	} ]);
