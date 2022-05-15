<%@page import="com.Meter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.6.0.min.js"></script>
        <script src="Components/meters.js"></script>
		<title>User Bill Details</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h1>User Bill Details</h1>
					<form id="formMeter" name="formMeter" method="POST" action="meters.jsp">
						Meter Code: 
						<input 
							id="meterCode" 
							name="meterCode" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						House Owner's Name: 
						<input 
							id="houseownerName"
							name="houseownerName" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Price of a Single Unit: 
						<input 
							id="singleUnitPrice" 
							name="singleUnitPrice" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Units Amount: 
						<input 
							id="unitsAmount" 
							name="unitsAmount" 
							type="text" 
							class="form-control form-control-sm"
						><br>
						
						<!-- Total Price: 
						<input 
							id="totalPrice" 
							name="totalPrice" 
							type="text" 
							class="form-control form-control-sm"
						><br> -->

						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						> 

						<input type="hidden" name="hidItemIDSave" id="hidItemIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divItemsGrid">
						<%
							Meter meter = new Meter(); 
							out.print(meter.readMeters());
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>