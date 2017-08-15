<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,java.lang.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="lib/angular.js"></script>
<script src="app.js"></script>
<script src="login.js"></script>
<script src="dataview.js"></script>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
	crossorigin="anonymous"></script>
</head>

<body ng-app="app">
	<div ng-controller="loginCtrl as vm">

		<h1 ng-if="loggedIn == 0">{{dbConnection}}</h1>
		<div ng-if="connected && loggedIn == 0">
			<form>
				Username:<br> <input type="text" name="username"
					ng-model="username.text"><br> Password:<br> <input
					type="text" name="password" ng-model="password.text">
				<button type="button" value="submit" ng-click="login()">Submit</button>
			</form>

		</div>
		

		<div ng-controller="dataCtrl">
			<nav class="navbar navbar-expand-lg navbar-light bg-light"
				ng-if="logInStatus == 1"> <a class="navbar-brand" ng-click="setFocus('homeView')">{{loggedInUser}}</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNav" aria-controls="navbarNav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" ng-click="setFocus('jsonView')">JSON <span
							class="sr-only">(current)</span></a></li>
					<li class="nav-item"><a class="nav-link" ng-click="setFocus('filterView')">Filter</a></li>
					<li class="nav-item"><a class="nav-link" ng-click="setFocus('pricingView')">Pricing</a></li>
					<li class="nav-item"><a class="nav-link" ng-click="setFocus('chartView')">Chart</a></li>
				</ul>
			</div>
			</nav>
			<div ng-if="logInStatus == 1 && selectedView == 'homeView'">
				<h5>Welcome {{loggedInUser}}!</h5>
			</div>
			<div ng-if="logInStatus == 1 && selectedView == 'jsonView'">
				<h6>{{msg}}</h6>
			</div>
			<div ng-if="logInStatus == 1 && selectedView == 'filterView'">
				Filter Data by: <select ng-model="selectedFilter"
					ng-change="changedSelectedFilter(selectedFilter)"
					ng-options="filter for filter in filterOptions"></select>

			</div>
		</div>

	</div>




</body>
</html>