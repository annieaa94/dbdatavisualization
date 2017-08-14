<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,java.lang.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<script src="lib/angular.js"></script>
		<script src="app.js"></script>
		<script src="login.js"></script>
		<script src="dataview.js"></script>
	</head>
	
	<body ng-app="app">
		<div ng-controller="loginCtrl">
			
			<h1 ng-if="loggedIn == 0"> {{dbConnection}} </h1>
			<div ng-if="connected && loggedIn == 0">
				<form>
  					Username:<br>
  					<input type="text" name="username" ng-model="username.text"><br>
  					Password:<br>
  					<input type="text" name="password" ng-model="password.text">
  					<button type="button" value="submit" ng-click="login()">Submit</button>
				</form>
				
			</div>
			<h3>{{msg}}</h3>
		</div>
		<%
		
		%>
	</body>
</html>