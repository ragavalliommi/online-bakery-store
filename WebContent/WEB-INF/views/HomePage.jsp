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
			<form class="form-inline" action="http://localhost:8080/obs/search"
				method="POST">
				<input class="form-control mr-sm-2" type="text" name="search"
					id="search" placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>
			<c:if test="${userID!=null&&userName!=null}">
				<div>
					<a id="name" data-name="${userName}"
						class="navbar-brand p-1 border rounded bg-white"> <i
						class="bi bi-person-circle"></i> <c:out value="${userName}" />
					</a> <a id="collection" class="btn btn-dark"
						href="http://localhost:8080/obs/collection?userID=${userID}&userName=${userName}"> <i class="bi bi-collection-fill"></i>
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

	<div class="row">
		<div class="container">
			<div class="card mt-3 mb-3">
				<div class="card-body">
					<h3 class="text-center">Available Bakery Items</h3>
						<c:forEach var="item" items="${_items_data}">
						
						<div class="card-deck">
						  <div class="card">
						  <a href="http://localhost:8080/obs/viewItem?bakeryItemID=<c:out value="${item.getBakeryItemId()}" />&userID=<c:out value="${userID}" />&userName=<c:out value="${userName}" />">
						    <img class="card-img-top" src="${item.getImageURL()}" alt="Card image cap" style="width: 18rem; height: 20rem;"> 
						    </a>
						    <div class="card-body">
    							<p class="price">$ ${item.getPrice()}</p>							  
						     	 <h5 class="card-title">
     	 						  <a href="http://localhost:8080/obs/viewItem?bakeryItemID=<c:out value="${item.getBakeryItemId()}" />&userID=<c:out value="${userID}" />&userName=<c:out value="${userName}" />" class="card-link">						     	 
						     	 	${item.getItemName()}  
						     	 </a>
						     	 </h5>
						    </div>
						  </div>
						  </div>
						</c:forEach>
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