angular.module('app').controller("dataCtrl", 
		['$scope', '$http', 'LoginService', function($scope, $http, LoginService) {
	
	$scope.selectedView = 'homeView';		
			
	$scope.logInStatus = 0;
	$scope.loggedInUser = "";
	
	$scope.selectedFilter = "";
	$scope.filteredInstrumentData = {};
	
	$scope.counterpartyDeals = {};
	$scope.filterOptions = [];
	
	
	 
	 $http.get("CounterPartyNames")
	    .then(function(response) {
	    	
	    	
	    	if (response.data == null) {
	    		console.log("counterpartynames null: ",response);
	    		
	    	} else {
	    		$scope.filterOptions = response.data;
	    		console.log("counterpartyname: ",response);
	    	}
	    		
	    });
	 
	
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
	    
	    $http({
			  method: 'GET',
			  url: 'BAServlet2',
			 params: {"selected_id": $scope.selectedFilter}
			}).then(function successCallback(response) {
				console.log("selected_id", $scope.selectedFilter);
				console.log(response);
				$scope.filteredInstrumentData = response.data;
			  }, function errorCallback(response) {
				  console.log(response);
				  
			  });
	  };
	  
	$scope.setFocus = function(view) {
		if (view == 'filterView') {
			$scope.filteredInstrumentData = {};
		}
			$scope.selectedView = view;
			
		};
		
}]);