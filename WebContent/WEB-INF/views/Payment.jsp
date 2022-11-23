<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OBS : Payment</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
	<header>
		Payment page
	</header>
	<div class="row mt-3 mb-3">
		<div class="container">
			<div class="list-group">
				<div>Cart Value: "${value}"</div>
				<hr>
				<form class="w-25" action="http://localhost:5120/ibdb/order"
					method="POST">
					<div class="form-group">
						<label for="card_name">Name on Card</label> <input type="text"
							class="form-control" name="card_name" id="card_name"
							placeholder="John Doe">
					</div>
					<div class="form-group">
						<label for="card_no">Card Number</label> <input type="text"
							class="form-control" name="card_no" id="card_no">
					</div>
					<div class="form-group">
						<label for="card_expiry">Expiry</label> <input type="text"
							class="form-control" name="card_expiry" placeholder="MM/YY"
							id="card_expiry"> <label for="card_cvv">CVV</label> <input
							type="text" class="form-control" name="card_cvv" id="card_cvv">
					</div>
					<input hidden type="text" name="cartid" value="${cartid}">
					<input hidden type="text" name="_userid" value="${_userid}">
					<input hidden type="text" name="_name" value="${_name}">
					<button type="submit" class="btn btn-primary">Make Payment</button>
				</form>
			</div>
		</div>
	</div>

	<div id="userid" hidden=true data-userid="${_userid}"></div>

	<script>
		var _userid = document.getElementById("userid").getAttribute(
				"data-userid");
		//console.log(_userid);

		if (_userid) {
			localStorage.setItem("_userid", _userid);

			var _logoutBtn = document.getElementById("logout");

			_logoutBtn.addEventListener('click', function(event) {
				if (localStorage.getItem("_userid")) {
					console.log("User was logged in");
					localStorage.removeItem("_userid");
					window.location.replace("/ibdb/");
				} else {
					window.location.replace("/ibdb/");
				}
			});
		}
	</script>
	
</body>
</html>