var app = angular.module("app", []);

app.factory("LoginService", function() {
	  var loggedIn = 0;

	  return {
	    setLoginStatus: function(status) {
	      loggedIn = status;
	    },
	    getLoginStatus: function() {
	      return loggedIn;
	    }
	  };
	});