var app = angular.module("app", []);

app.controller("loginCtrl", function($scope, $http) {
	
	$scope.dbConnection = "You are not connected to DB Server";
	$scope.connected = false;
	$scope.username={};
	$scope.username.text="";
	$scope.password={};
	$scope.password.text="";
	$scope.msg="";
	$scope.ifloggedin=1;
	
		 $http.get("Connection")
		    .then(function(response) {
		    	
		    	
		    	if (response.data == "true") {
		    	
		    		$scope.connected = true;
		    		$scope.dbConnection = "You are to DB Server";
		    	} else {
		    		$scope.connected = false;
		    		$scope.dbConnection = "You are not connected to DB Server";
		    		console.log("false");
		    	}
		    		
		    });
		
		 	$scope.login=function() {
		 		console.log("inside");
		 		$http({
		 			  method: 'GET',
		 			  url: 'LoginServlet',
		 			 params: {"username": $scope.username.text, "password": $scope.password.text}
		 			}).then(function successCallback(response) {
		 			   $scope.msg=response.data;
		 			   $scope.ifloggedin=0;
		 			  }, function errorCallback(response) {
		 			    // called asynchronously if an error occurs
		 			    // or server returns response with an error status.
		 			  });
		 	}
		 	
		 	
});