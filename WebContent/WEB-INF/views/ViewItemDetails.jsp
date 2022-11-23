<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<title>Online Bakery Store: View Item"</title>
</head>
<body class="bg-secondary">
<header>
		<nav class="navbar navbar-dark bg-dark justify-content-between">
			<a class="navbar-brand text-white">OBS</a>
			
			<c:if test="${userID!=null&&userName!=null}">
				<div>
					<a id="name" data-name="${userName}"
						class="navbar-brand text-white rounded bg-dark"> <i
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

	<div class="row mt-3 mb-3 bg-secondary">
		<div class="container">
			<div class="card">
				<div class="card-header bg-warning text-black">
					<span>Product Details</span>
				</div>
				<div class="card-body bg-white">
					<h5 class="card-title">Product Name: ${item.getItemName()}</h5>
					<p class="card-text">Size: ${item.getItemSize()}</p>
					<p class="card-text">Price: $${item.getPrice()}</p>
					<p class="card-text">Description: ${item.getDescription()}</p>
					<img class="img-fluid img-thumbnail mb-3" style="max-height: 250px;" src="${item.getImageURL() }"></img>
					<form class="form-inline" action="http://localhost:8080/obs/cart"
						method="POST">
						<input hidden class="form-control mr-sm-2" type="text"
							name="bookid" value="${item.getBakeryItemId()}" aria-label="bakeryItemId">
						<input hidden class="form-control mr-sm-2" type="text"
							name="userID" value="${userID}" aria-label="userID"> <input
							hidden class="form-control mr-sm-2" type="text" name="userName"
							value="${userName}" aria-label="userName">
						<label for="quantity" class="mr-sm-2">Quantity:</label>
						  <select id="quantity" name="quantity" class="form-control mr-sm-2">
						    <option value=1 >1</option>
						    <option value=2>2</option>
						    <option value=3>3</option>
						    <option value=4>4</option>
						    <option value=5>5</option>
						  </select>
						<button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Add
							to Cart</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="userid" hidden=true data-userid="${userID}"></div>

	<script>
		var userID = document.getElementById("userid").getAttribute(
				"data-userid");

		if (userID) {
			localStorage.setItem("userID", userID);

			var _logoutBtn = document.getElementById("logout");

			_logoutBtn.addEventListener('click', function(event) {
				if (localStorage.getItem("userID")) {
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