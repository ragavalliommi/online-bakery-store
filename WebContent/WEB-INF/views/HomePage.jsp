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
	
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body class="bg-secondary">
	<header>
		<nav class="navbar navbar-dark bg-dark justify-content-between">
			<a class="navbar-brand text-white font-italic font-weight-bold" href="http://localhost:8080/obs/home?userID=${userID}&userName=${userName}">Online Bakery Store</a>
			<form id="searchForm" class="form-inline" action=""
				method="POST">
				<input class="form-control mr-sm-2" type="text" name="searchString"
					id="searchString" placeholder="Search" aria-label="searchString">
				<button class="btn btn-outline-warning my-2 my-sm-0" type="submit">Search</button>
			</form>
			<c:if test="${userID!=null&&userName!=null}">
				<div>
					<a id="name" data-name="${userName}"
						class="navbar-brand text-white rounded p-2">Welcome, <c:out value="${userName}" /> !
					</a> <a id="collection" class="btn btn-dark" href="http://localhost:8080/obs/orderHistory?userID=${userID}&userName=${userName}"> <i class="fa fa-history" aria-hidden="true"></i>
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
	
    <h4 class="mt-4 mb-5" style='color: #F2E0F7'><center><strong>Available Bakery Item</strong></center></h4>
	
	<div class="row">
	
	<c:forEach var="item" items="${_items_data}">
	
		<div class="col-lg-4 col-md-12 mb-4">
			<div class="card card-cascade card-ecommerce wider shadow mb-5 ">
	
				<div class="view view-cascade overlay text-center">
					<a href="http://localhost:8080/obs/viewItem?bakeryItemID=<c:out value="${item.getBakeryItemId()}" />&userID=<c:out value="${userID}" />&userName=<c:out value="${userName}" />">
						    <img class="card-img-top" src="${item.getImageURL()}" alt="Card image cap" style="width: 28.5rem; height: 24rem;"> 
					</a>
					<a>
						<div class="mask rgba-white-slight"></div>
					</a>
				</div>
	
				<div class="card-body card-body-cascade text-center">
	
					<!--Card Title-->
					<h4 class="card-title"><strong><a href="http://localhost:8080/obs/viewItem?bakeryItemID=<c:out value="${item.getBakeryItemId()}" />&userID=<c:out value="${userID}" />&userName=<c:out value="${userName}" />">${item.getItemName()}</a></strong></h4>
	
					<!-- Card Description-->
<!-- 					<p class="card-text">This is a Mobile phone with all the advance features and at best price.
					</p> -->
	
	
					<p class="price">$ ${item.getPrice()}</p>
	
	
				</div>
			</div>
		</div>
	</c:forEach>
    </div>
	

	<div id="userID" hidden=true data-userID="${userID}"></div>

	<script>
		var userID = document.getElementById("userID").getAttribute(
				"data-userID");
		//console.log(userID);

		if (userID) {
			localStorage.setItem("userID", userID);

			var _logoutBtn = document.getElementById("logout");
			
			const urlParams = new URLSearchParams(window.location.search);
			document.getElementById("searchString").value="";
			var searchString = document.getElementById("searchString").value;
			document.getElementById("searchForm").action="http://localhost:8080/obs/search?userID="+urlParams.get('userID')+"&userName="+urlParams.get('userName')+"&searchString="+searchString;
			
			_logoutBtn.addEventListener('click', function(event) {
				if (localStorage.getItem("userID")) {
					console.log("User was logged in");
					localStorage.removeItem("userID");
					window.location.replace("/obs/");
				} else {
					window.location.replace("/obs/");
				}
			});
			
			
			
			
			document.getElementById('searchString').addEventListener('change', function(event){
				var searchString = document.getElementById("searchString").value;
				console.log(window.location.search);
				const urlParams = new URLSearchParams(window.location.search);
				document.getElementById("searchForm").action="http://localhost:8080/obs/search?userID="+urlParams.get('userID')+"&userName="+urlParams.get('userName')+"&searchString="+searchString;
			});
		}
	</script>
</body>
</html>