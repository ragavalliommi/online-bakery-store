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

	<div class="site-section">
        <div class="container">
            <form class="row" method="post" action="http://localhost:8080/obs/order?userID=${userID}&userName=${userName}">
                <div class="col-md-6 mb-5 mb-md-0">
                    <h2 class="h3 mb-3 text-black">Billing Details</h2>

                    <div class="p-3 p-lg-5 border">
                        <div class="form-group row">
                            <div class="col-md-6">
                                <label for="first-name" class="text-black">
                                    First Name <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="first-name" name="first-name"
                                       value="${account.firstName}" required>
                            </div>

                            <div class="col-md-6">
                                <label for="last-name" class="text-black">
                                    Last Name <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="last-name" name="last-name"
                                       value="${account.lastName}" required>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-12">
                                <label for="address" class="text-black">
                                    Address <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="address" name="address"
                                       value="${account.address}" required>
                            </div>
                        </div>

                        <div class="form-group row mb-5">
                            <div class="col-md-6">
                                <label for="email" class="text-black">
                                    Email Address <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="email" name="email"
                                       value="${account.email}" required>
                            </div>
                            <div class="col-md-6">
                                <label for="phone" class="text-black">
                                    Phone <span class="text-danger">*</span>
                                </label>

                                <input type="text" class="form-control" id="phone" name="phone"
                                       value="${account.phone}" required>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="row mb-5">
                        <div class="col-md-12">
                            <h2 class="h3 mb-3 text-black">Your Order</h2>

                            <div class="p-3 p-lg-5 border">
                                <table class="table site-block-order-table mb-5">
                                    <thead>
                                    <tr>
                                        <th style="text-align: center">Product</th>
                                        <th style="text-align: center">Price</th>
                                        <th style="text-align: center">Quantity</th>
                                        <th style="text-align: center">Total</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${order.cartProducts}" var="o">
                                        <tr>
                                            <td>
                                                <input name="product-name" class="form-control-plaintext h5 text-black"
                                                       value="${o.product.name}" style="text-align: center" readonly>
                                            </td>

                                            <td>
                                                <input name="product-price" class="form-control-plaintext h5 text-black"
                                                       value="${o.price}" style="text-align: center" readonly>
                                            </td>

                                            <td>
                                                <input name="product-quantity"
                                                       class="form-control-plaintext h5 text-black"
                                                       value="${o.quantity}" style="text-align: center" readonly>
                                            </td>

                                            <td>
                                                <input name="product-total" class="form-control-plaintext h5 text-black"
                                                       value="${o.price * o.quantity}" style="text-align: center"
                                                       readonly>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td class="text-black font-weight-bold"><strong>Order Total</strong></td>
                                        <td class="text-black font-weight-bold">
                                            <input name="order-total-price" class="form-control-plaintext h5 text-black"
                                                   value="${total_price}" style="text-align: center" readonly>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>

                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-lg py-3 btn-block">
                                        Place Order
                                    </button>
                                </div>
                            </div>
                        </div>
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
	</script>
</body>
</html>