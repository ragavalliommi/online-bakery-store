<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OBS : Place Order</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
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
	
	<div class="container">
	<p></p>
	</div>

	<div class="site-section">
        <div class="container">
            <form class="row" method="post" action="http://localhost:8080/obs/order?userID=${userID}&userName=${userName}">
                
                <div class="col-md-6">
                    <div class="row mb-5">
                        <div class="col-md-12">
                            <h2 class="h3 mb-3 text-black">Order Summary</h2>

                            <div class="p-2 p-lg-3 border">
                                <table class="table site-block-order-table mb-6">
                                    <thead>
                                    <tr>
                                        <th style="text-align: center">Name</th>
                                        <th style="text-align: center">Quantity</th>
                                        <th style="text-align: center">Price</th>
                                        <th style="text-align: center">Image</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${cart_data}" var="item">
                                        <tr>
                                            <td>
                                                <input name="product-name" class="form-control-plaintext h6 text-black"
                                                       value="${item.getBakeryItem().getItemName()}" style="text-align: center" readonly>
                                            </td>


                                            <td>
                                                <input name="product-quantity"
                                                       class="form-control-plaintext h6 text-black"
                                                       value="${item.getItemQty()}" style="text-align: center" readonly>
                                            </td>
                                           

                                            <td>
                                                <input name="product-total" class="form-control-plaintext h6 text-black"
                                                       value=$${item.getBakeryItem().getPrice() * item.getItemQty()} style="text-align: center"
                                                       readonly>
                                            </td>
                                            
                                             <td>
                                                <img class="img-fluid mx-auto img-thumbnail mb-2" style="max-height: 75px;" src="${item.getBakeryItem().getImageURL() }"></img>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td class="text-black font-weight-bold"><strong>Order Total</strong></td>
                                        <td class="text-black font-weight-bold">
                                            <input name="order-total-price" class="form-control-plaintext h5 text-black"
                                                   value=$${cart_value} style="text-align: center" readonly>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>

                                
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6 mb-5 mb-md-0">
                    <h2 class="h3 mb-3 text-black">Billing Details</h2>

                    <div class="p-2 p-lg-4 border">
                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="name" class="text-black">
                                    Name <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="name" name="name"
                                       value="${userName}" required readonly>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="address" class="text-black">
                                    Delivery/ Billing Address <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="address" name="address"
                                       value="${user.getDeliveryAddress()}" required readonly>
                            </div>
                        </div>

                        <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="email" class="text-black">
                                    Email Address <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="email" name="email"
                                       value="${user.getEmail()}" required readonly>
                            </div>
                            <div class="col-md-6">
                                <label for="phone" class="text-black">
                                    Phone <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="phone" name="phone"
                                       value="${user.getPhone()}" required readonly>
                            </div>
                        </div>
                        	<div class="form-check">
							  <input class="form-check-input" type="radio" name="flexRadioDefault" id="pickup" value="pickup" onclick="ShowHideDiv()">
							  <label class="form-check-label" for="pickup">
							    Pick up
							  </label>
							</div>
							<div id="dvtext" style="display: none">
	    					<span class="text-info">Please pick up the order at Dallas</span>
							</div>
							<div class="form-check">
							  <input class="form-check-input" type="radio" name="flexRadioDefault" id="delivery" value="delivery" checked onclick="ShowHideDiv()">
							  <label class="form-check-label" for="delivery">
							    Delivery
							  </label>
							</div>
							<hr>
							<h4 class="h4 mb-3 text-black">Payment info</h4>
                         <div class="form-group row">
                            <div class="col-md-12">
                                <label for="card_name" class="text-black">
                                    Name on Card
                                </label>

                                <input type="text" class="form-control" id="card_name" name="card_name"
                                       placeholder="Name" required>
                            </div>
                        </div>
                        
                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="card_no" class="text-black">
                                    Card Number
                                </label>

                                <input type="text" class="form-control" id="card_no" name="card_n0"
                                       placeholder="Card Number" required>
                            </div>
                        </div>
                        
                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="card_expiry" class="text-black">
                                    Expiry
                                </label>

                                <input type="text" class="form-control" id="card_expiry" name="card_expiry"
                                       placeholder="MM/YY" required>
                            </div>
                        </div>
                        
                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="card_cvv" class="text-black">
                                    CVV
                                </label>

                                <input type="text" class="form-control" id="card_cvv" name="card_cvv"
                                       placeholder="CVV" required>
                            </div>
                        </div>
                  
					</div>
                        
                    </div>
                    <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-lg py-2 btn-block">
                                        Place Order
                                    </button>
                       	</div>
                </div>
                
               
                
            </form>
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
		
		function ShowHideDiv() {
	        var chkYes = document.getElementById("pickup");
	        var dvtext = document.getElementById("dvtext");
	        dvtext.style.display = chkYes.checked ? "block" : "none";
	    }
	</script>
</body>
</html>