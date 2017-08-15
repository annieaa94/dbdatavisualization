angular.module('app').controller("dataCtrl", 
		['$scope', '$http', 'LoginService', function($scope, $http, LoginService) {
	
	$scope.selectedView = 'homeView';		
			
	$scope.logInStatus = 0;
	$scope.loggedInUser = "";
	
	$scope.filterOptions = ['counterparty_ID', 'instrument_ID'];
	$scope.selectedFilter = "";
	
	$scope.$on('loginFail', function(event,data) {
		  // you could inspect the data to see if what you care about changed, or just update your own scope
		  $scope.logInStatus = LoginService.getLoginStatus();
	});

		   // different event names let you group your code and logic by what happened
	$scope.$on('loginSuccess', function(event,data) {
		$scope.logInStatus = LoginService.getLoginStatus();
		$scope.loggedInUser = data;
	});
	
	$scope.changedSelectedFilter = function(newFilter) {
	    $scope.selectedFilter = newFilter;
	  };
	  
	$scope.setFocus = function(view) {
			$scope.selectedView = view;
		  
		}; 
}]);