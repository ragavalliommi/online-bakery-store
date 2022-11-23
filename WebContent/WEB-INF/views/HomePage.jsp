<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> OBS : Home</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
	<header>
		<nav class="navbar navbar-light bg-light justify-content-between">
			<a class="navbar-brand">OBS</a>
			<form class="form-inline" action="http://localhost:5120/obs/search"
				method="POST">
				<input class="form-control mr-sm-2" type="text" name="search"
					id="search" placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>
			<c:if test="${_userid!=null&&_name!=null}">
				<div>
					<a id="name" data-name="${_name}"
						class="navbar-brand p-1 border rounded bg-white"> <i
						class="bi bi-person-circle"></i> <c:out value="${_name}" />
					</a> <a id="collection" class="btn btn-dark"
						href="http://localhost:5120/obs/collection?_userid=${_userid}&_name=${_name}"> <i class="bi bi-collection-fill"></i>
					</a> <a id="cart" class="btn btn-dark"
						href="http://localhost:5120/obs/cart?_userid=${_userid}&_name=${_name}">
						<i class="bi bi-cart4"></i>
					</a>
					<button id="logout" class="btn btn-dark">
						<i class="bi bi-box-arrow-right"></i>
					</button>
				</div>

			</c:if>
		</nav>
	</header>

	<div class="row">
		<div class="container">
			<div class="card mt-3 mb-3">
				<div class="card-body">
					<h3 class="text-center">Available Bakery Items</h3>
						<c:forEach var="item" items="${_items_data}">
							<div class="card">
							  <a href="www.test.com"> 
							  	<img src= "${item.getImageURL()}" alt="Card image cap", style="width: 18rem; height: 20rem;">
							  </a>
  							  <p class="price">$ ${item.getPrice()}</p>							  
							  <a href="#" class="card-link">${item.getItemName()}</a>
							  <p>${item.getDescription()}</p>
<!-- 							  <p><button>Add to Cart</button></p>
 -->							</div>
						</c:forEach>
					</div>
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
					window.location.replace("/obs/");
				} else {
					window.location.replace("/obs/");
				}
			});
		}
	</script>
</body>
</html>