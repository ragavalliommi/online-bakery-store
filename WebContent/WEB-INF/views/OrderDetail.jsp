<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> OBS : Order Detail</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body class="bg-secondary">
	<header>
		<nav class="navbar navbar-dark bg-dark justify-content-between">
			<a class="navbar-brand text-white" href="http://localhost:8080/obs/home?userID=${userID}&userName=${userName}">OBS</a>
			<c:if test="${userID!=null&&userName!=null}">
				<div>
					<a id="name" data-name="${userName}"
						class="navbar-brand text-white rounded p-2"> <i
						class="bi bi-person-circle"></i> <c:out value="${userName}" />
					</a> <a id="collection" class="btn btn-dark" href="http://localhost:8080/obs/orderHistory?userID=${userID}&userName=${userName}"> <i
						class="bi bi-collection-fill"></i>
					</a> <a id="cart" class="btn btn-dark"
						href="http://localhost:8080/obs/cart?userID=${userID}&userName=${userName}">
						<i class="bi bi-cart4"></i>
					</a>
					<button id="logout" class="btn btn-dark">
						<i class="bi bi-box-arrow-right"></i>
					</button>
				</div>

			</c:if>
		</nav>
	</header>
	
	<div class = "container">
		<div class = "row">
			<div class= "row mt-3 mb-3">
				<div class="list-group">
					<table class = "table table-light table-striped table-bordered table-hover">
						<thead class="thead-dark">
							<tr style="border: 1px solid black;"> 
								<th> Name </th>
								<th> Quantity </th>
							 	<th> Price </th>
								<th>Image</th>
							 	
							 </tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${cart_data}">
								<tr style="border: 1px solid black;">
									<td><c:out value="${item.getBakeryItem().getItemName()}" /></td>
									<td><c:out value="${item.getItemQty()}" /></td>
									<td>$ <c:out value="${item.getBakeryItem().getPrice() * item.getItemQty()}" /></td>
									<td><img class="img-fluid img-thumbnail mb-3" style="max-height: 75px;" src="${item.getBakeryItem().getImageURL() }"></img></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<a class="btn btn-success" href="http://localhost:8080/obs/orderHistory?userID=${userID}&userName=${userName}">
					Go Back </a>
				</div>
			</div>
		</div>
	</div>
	
	<div id="userID" hidden=true data-userID="${userID}"></div>

	<script>
		var userID = document.getElementById("userID").getAttribute(
				"data-userID");
		//console.log(userID);

		if (userID) {
			localStorage.setItem("userID", userID);

			var _logoutBtn = document.getElementById("logout");

			_logoutBtn.addEventListener('click', function(event) {
				if (localStorage.getItem("userID")) {
					console.log("User was logged in");
					localStorage.removeItem("userID");
					window.location.replace("/obs/");
				} else {
					window.location.replace("/obs/");
				}
			});
		}
	</script>

</body>
</html>