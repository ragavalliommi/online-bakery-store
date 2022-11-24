<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Online Bakery Store : Login</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<div class="row w-100">
		<div class="container w-100">
			<div class="card w-50 mt-3 mb-3 mr-auto ml-auto">
				<div class="card-body">
					<div class="h1">Login</div>
					<form action="<%= request.getContextPath() %>/" method="POST">
						<div class="form-group">
							<label for="email">Email address:</label> <input type="email"
								class="form-control" name="email" id="email">
						</div>
						<div class="form-group">
							<label for="password">Password:</label> <input type="password"
								class="form-control" name="password" id="password">
						</div>
						<button type="submit" class="btn btn-primary">Login</button>
					</form>
					<a class="btn btn-link mt-2"
						href="<%= request.getContextPath() %>/register">Create New Account</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>