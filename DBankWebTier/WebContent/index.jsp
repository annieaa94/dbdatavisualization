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
<script src="lib/d3.js"></script>

<script src="barChartDirective.js"></script>
<script src="app.js"></script>
<script src="login.js"></script>
<script src="dataview.js"></script>


<link rel="stylesheet" href="style.css">

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

<script src="lib/d3.js"></script>

</head>

<body ng-app="app">
	
	<div ng-controller="loginCtrl as vm">
		<div class="overlay" ng-if="loggedIn == 0">
			<h1 ng-if="loggedIn == 0">{{dbConnection}}</h1>
		<div ng-if="connected && loggedIn == 0">
			<div class="container loginContainer">
			
			<form class="form-signin">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="inputUsername" class="sr-only">UserName</label> <input
					type="username" id="inputUsername" class="form-control"
					placeholder="Username" required autofocus ng-model="username.text"> <label
					for="inputPassword" class="sr-only">Password</label> <input
					type="password" id="inputPassword" class="form-control"
					placeholder="Password" ng-model="password.text" required>
				<button class="btn btn-lg btn-primary btn-block" type="submit" ng-click="login()">Sign
					in</button>
			</form>

		</div>

		</div>

		</div>

		<div ng-if="connected">
			<div ng-controller="dataCtrl">
				<nav class="navbar navbar-expand-lg navbar-dark bg-dark"
					ng-if="logInStatus == 1"> <a class="navbar-brand"
					ng-click="setFocus('homeView')">Home</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarNav" aria-controls="navbarNav"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse justify-content-end"
					id="navbarNav">
					<ul class="navbar-nav navbar-right">
						<li class="nav-item"><a class="nav-link"
							ng-click="setFocus('jsonView')">JSON <span class="sr-only">(current)</span></a></li>
						<li class="nav-item"><a class="nav-link"
							ng-click="setFocus('filterView')">Trades</a></li>
						<li class="nav-item"><a class="nav-link"
							ng-click="setFocus('chartView')">Price/Time</a></li>
						<li class="nav-item"><a class="nav-link"
							ng-click="setFocus('priceView')">Avg Price</a></li>
						<li class="nav-item"><a class="nav-link"
							ng-click="setFocus('tradesView')">Net Trades</a></li>
						<li class="nav-item"><a class="nav-link"
							ng-click="setFocus('profit_loss_view')">Profit/Loss</a></li>
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
					Filter Data by counterparty: <select ng-model="selectedFilter"
						ng-change="changedSelectedFilter(selectedFilter.Counterparty_name)"
						ng-options="filter.Counterparty_name for filter in filterOptions"></select>

					<table class="table table-striped">
						<thead>
							<tr>
								<th>Instrument Name</th>
								<th>Buys</th>
								<th>Sells</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="item in filteredInstrumentData">
								<td>{{item.instrument_name}}</td>
								<td>{{item.buy_quantity}}</td>
								<td>{{item.sell_quantity}}</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div ng-if="logInStatus == 1 && selectedView == 'chartView'">
					Filter Data by instrument: <select ng-model="selectedInstrument"
						ng-change="changedSelectedInstrument(selectedInstrument.Instrument_name)"
						ng-options="instrument.Instrument_name for instrument in instrumentNames"></select>

					<table class="table table-striped">
						<thead>
							<tr>
								<th>Time</th>
								<th>Buy Price</th>
								<th>Sell Price</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="item in filteredPriceData">
								<td>{{item.Time}}</td>
								<td>{{item.buy_price}}</td>
								<td>{{item.sell_price}}</td>
							</tr>
						</tbody>
					</table>
				</div>


				<div ng-if="logInStatus == 1 && selectedView == 'priceView'">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Instrument Name</th>
								<th>Avg Buy Price</th>
								<th>Avg Sell Price</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="item in avgPriceData">
								<td>{{item.instrument_name}}</td>
								<td>{{item.buy_quantity}}</td>
								<td>{{item.sell_quantity}}</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div ng-if="logInStatus == 1 && selectedView == 'tradesView'">
					Filter Data by counterparty: <select ng-model="selectedFilter"
						ng-change="changedSelectedFilterforTradeData(selectedFilter.Counterparty_name)"
						ng-options="filter.Counterparty_name for filter in filterOptions"></select>

					<table class="table table-striped">
						<thead>
							<tr>
								<th>Instrument</th>
								<th>Net Trades</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="item in netTradeData">
								<td>{{item.instrument_name}}</td>
								<td>{{item.net_trades}}</td>
							</tr>
						</tbody>
					</table>

					<bars-chart></bars-chart>
				</div>

				<div ng-if="logInStatus == 1 && selectedView == 'profit_loss_view'">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Counterparty Name</th>
								<th>Realised Profit/Loss</th>
								<th>Effective Profit/Loss</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="item in netProfitLossData">
								<td>{{item.counterparty_name}}</td>
								<td>{{item.realised}}</td>
								<td>{{item.effective}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>




</body>
</html>