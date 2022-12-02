<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OBS : Order History</title>
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
	
	<div class="container">
	<p></p>
	</div>
	
	<div class="site-wrap">
    <div class="site-section" data-aos="fade-in">
        <div class="container">
            <div class="row justify-content-md-center">
            
                <div class="col-md-10">
                <h2 class="h3 mb-3 text-white">Order History</h2>
                    <div class="site-blocks-table">
                        <table class="table table-bordered  bg-light rounded">
                        
                            <thead class="thead-dark">
                            <tr>
                                <th style="text-align: center">ID</th>
                                <th style="text-align: center">Total</th>
                                <th style="text-align: center">Date</th>
                                <th style="text-align: center">Delivery Mode</th>
                                <th style="min-width: 100px; text-align: center">Detail</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order_history}" var="o">
                                <tr>
                                    <td style="text-align: center">${o.getOrderId()}</td>

                                    <td style="text-align: center">$${o.getAmount()}</td>

                                    <td style="text-align: center">${o.getOrderDate()}</td>
                                    
                                    <td style="text-align: center">${o.getDeliveryMode()}</td>
								
                                    <td style="text-align: center">
                                    
                                    <div class="row justify-content-md-center">
                                    <form id="form" class="form-inline" action="http://localhost:8080/obs/orderHistory?userID=${userID}&userName=${userName}&value=${cart_value}&orderID=${o.getOrderId()}" method="POST" >
	                                    <table ><tr style="border: none"><td style="border: none">
	                                    
						                   <button type="submit" class="btn btn-primary btn-md" style="background-color: green ; border-color: green">
						                       <span id="boot-icon" class="bi bi-arrow-right" style="font-size:1rem"></span>
						                   </button>
              						 
	                                    </td></tr></table>
	                                    </form>
                                     </div>
                                     
                                    </td>
                                    
                                </tr>
                            </c:forEach>
                            </tbody>
                            
                        </table>
                        <a class="btn btn-success" href="http://localhost:8080/obs/home?userID=${userID}&userName=${userName}">
						Go Home </a>
                    </div>
                </div>
            
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