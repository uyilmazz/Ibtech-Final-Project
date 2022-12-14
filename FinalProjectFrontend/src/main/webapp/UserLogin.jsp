<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.user.service.UserService,
	com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.service.CartProductService,
	com.ibtech.core.utilities.result.*,
	java.util.List,
	com.ibtech.shopping.entities.*,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.user.entities.User"%>
<%
String userName = "";
String password = "";
String message = "Please enter your Username and password!";
DataResult<User> loginResult = null;
	if(session.getAttribute("userName") != null){
		response.sendRedirect("MainPage.jsp");
	}
	if ((request.getParameter("login")) != null) {
		userName = request.getParameter("userName");
		password = request.getParameter("password");
		UserService userService = new UserService();
		User loginUser = new User(0,userName,password);
		loginResult = userService.login(loginUser);
		if ((loginResult.isSuccess())) {
			CartService cartService = new CartService();
			CartProductService cartProductService = new CartProductService();
			session.setAttribute("userName", loginResult.getData().getName());
			if(session.getAttribute("cartId") != null && ParseHelper.isLong(session.getAttribute("cartId").toString())){
				long cartId = (Long) session.getAttribute("cartId");
				DataResult<Cart> cartResult = cartService.getById(cartId);
				if(cartResult.isSuccess()){
					String customerName = loginResult.getData().getName();
					DataResult<Cart> cartByCustomerNameResult = cartService.getByCustomerName(customerName);
					if(cartByCustomerNameResult.isSuccess()){
						DataResult<List<CartProduct>> cartProductListResult = cartProductService.getByCartId(cartResult.getData().getId());
						if(cartProductListResult.isSuccess() && cartProductListResult.getData().size() > 0){
							for(CartProduct cartProduct : cartProductListResult.getData()){
								cartProduct.setCartId(cartByCustomerNameResult.getData().getId());
							}
							Result updateBulkResult = cartProductService.updateBulkCartProduct(cartProductListResult.getData());
							if(updateBulkResult.isSuccess()){
								cartService.deleteCart(cartId);
							}
						}
						session.setAttribute("cartId", cartByCustomerNameResult.getData().getId());
					}else{
						cartResult.getData().setCustomerName(customerName);
						DataResult<Cart> updatedCartResult = cartService.updateCart(cartResult.getData());
					}
				}
			}
			if(session.getAttribute("backUrl") != null){
				response.sendRedirect((String)session.getAttribute("backUrl"));
			}else{
				response.sendRedirect("MainPage.jsp");
			}	
		} 
	}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.gradient-custom {
	/* fallback for old browsers */
	background: #6a11cb;
	/* Chrome 10-25, Safari 5.1-6 */
	background: -webkit-linear-gradient(to right, rgba(106, 17, 203, 1),
		rgba(37, 117, 252, 1));
	/* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	background: linear-gradient(to right, rgba(106, 17, 203, 1),
		rgba(37, 117, 252, 1))
}
</style>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
	crossorigin="anonymous">
</head>

<body>
<form action="" method="POST">
	<section class="vh-100 gradient-custom">
		<div class="container py-5 h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-5">
					<div class="card bg-dark text-white" style="border-radius: 1rem;">
						<div class="card-body p-5 text-center">
							<div class="mb-md-5 mt-md-4 pb-5">
								<h2 class="fw-bold mb-2 text-uppercase">Login</h2>
								<% if(loginResult == null) {%>
									<p class="text-white-50 mb-5"><%= message %></p>
								<%}else if(!loginResult.isSuccess()){%>
									<p class="text-danger mb-5"><%= loginResult.getMessage() %></p>
								<%}else{ %><%} %>
								<div class="form-outline form-white mb-4">
									<input type="text"  id="userName" name="userName" value="<%= userName %>"  minlength="3" required
										class="form-control form-control-lg" /> <label
										class="form-label" for="userName">User Name</label>
								</div>
								<div class="form-outline form-white mb-4">
									<input type="password" id="password" name="password" value="<%= password %>" minlength="5" required
										class="form-control form-control-lg" /> <label
										class="form-label" for="password">Password</label>
								</div>
								<button class="btn btn-outline-light btn-lg px-5" type="submit"  name="login">Login</button>
							</div>
							<div>
								<p class="mb-0">
									Don't have an account? <a href="UserRegister.jsp"
										class="text-white-50 fw-bold">Sign Up</a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</form>
<script src="https://kit.fontawesome.com/dee818d880.js"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>