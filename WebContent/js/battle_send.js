'usr strict';


(function() {
	var searchTimer;
	
	app.controller('UserController', ['$http','$scope' , function($http, $scope) {
		$scope.user = user;
	}])
	.controller('battleSendFormController', ['$http','$scope','$location', function($http, $scope,$location) {
		$scope.state = {};
		$scope.selected;

		$scope.search = function(query) {
			clearTimeout(searchTimer);
			searchTimer = setTimeout(function(){
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
					params: {keyword : query}
				})
				.success( function(result) {
					$scope.searchedUserList = result.userList;
					if(!$('#search-result').is('.open') && query !== undefined){ //open
						$('#search-result .dropdown-Toggle').dropdown('toggle');
						$scope.selected = 0;
					}else if(query === undefined && $('#search-result').is('.open')){ //close
						$('#search-result .dropdown-Toggle').dropdown('toggle');
					} 
				})
				.error( function(result) {
					$scope.state = result;
				});
			},100)
			
		};
		
		$scope.searchKeyPress=function(e){
			if($scope.searchedUserList !== undefined){
				var activeClass = 'btn-primary active';
				switch(e.keyCode){
				case 38:
					$scope.selected = Math.max(0,$scope.selected-1);
					break;
				case 40:
					$scope.selected = Math.min($scope.searchedUserList.length-1,$scope.selected+1);
					break;
				default:
					return;
				};
				$scope.setQuery($scope.selected);
				$('#search-result .dropdown-menu li a')
					.removeClass(activeClass)
					.eq($scope.selected)
					.addClass(activeClass);
			}
		};
		
		$scope.setQuery=function(index){
			var champion = $scope.searchedUserList[index];
			$scope.keyword = champion.id;
			$scope.setChampId(champion.id);
		}
		
		$scope.setChampId=function(user){
			$scope.champId = user.id;
		}
		
		
		$scope.sendChallenge=function(cid){
			$http({
				method: 'POST',
				url: '/battle/battle_send.rk',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj) {
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					return str.join("&");
				},
				data: { champId : $scope.champId}
			})
			.success( function(result) {
				
				if (result.success) {
					var redirectPath = $location.search().redirect;
					alert(cid+"님에게 결투를 신청했습니다.")
					location.href = (redirectPath ? redirectPath : "/battle/battle_list.rk");
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
