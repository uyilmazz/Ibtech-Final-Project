<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.service.CartProductService,
	com.ibtech.shopping.entities.CartProduct,
	com.ibtech.shopping.entities.Cart,
	java.util.List,
	com.ibtech.shopping.entities.Cart"%>
<%
long id = 1;
session.setAttribute("cartId", id);
String message = "";
Cart cart = null;
List<CartProduct> cartProductList = null;
CartService cartService = new CartService();
CartProductService cartProductService = new CartProductService();
if (session.getAttribute("cartId") != null) {
	long cartId = (long) session.getAttribute("cartId");
	cart = cartService.getById(cartId);
	if(cart != null){
		cartProductList = cartProductService.getByCartId(cartId);
		message = cartProductList.size() > 0 ? "" : "You haven't added any products to the cart yet.";
	}else{
		message = cart != null ? "" : "You haven't added any products to the cart yet.";
	}
}else{
	Cart newCart = new Cart(0,"new user",0);
	Cart addedCart = cartService.createCart(newCart);
	session.setAttribute("cartId", addedCart.getId());
	response.sendRedirect("CartView.jsp");
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
	crossorigin="anonymous">

<link href="css/cart.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container mt-3">
		<jsp:include page="partials/Navbar.jsp" />
		<section class="h-100 h-custom" style="background-color: #eee;">
			<div class="py-5 h-100">
				<div
					class="row d-flex justify-content-center align-items-center h-100">
					<div class="col">
						<div class="card">
							<div class="card-body p-4">
								<% if(cartProductList.size() > 0) { %>
									<div class="row">
									<div class="col-lg-8">
										<h5 class="mb-3">
											<a href="#!" class="text-body"><i
												class="fas fa-long-arrow-alt-left me-2"></i>Continue
												shopping</a>
										</h5>
										<hr>
										<div
											class="d-flex justify-content-between align-items-center mb-4">
											<div>
												<p class="mb-1">Shopping cart</p>
												<p class="mb-0">You have <%= cartProductList.size() %> items in your cart</p>
											</div>
										</div>

									<% for(int i = 0; i < cartProductList.size() ; i++) {%>
										<div class="card mb-3">
											<div class="card-body">
												<div class="d-flex justify-content-between">
													<div class="d-flex flex-row align-items-center">
														<div>
															<img
																src="<%= cartProductList.get(i).getProduct().getImagePath() %>"
																class="img-fluid rounded-3" alt="Shopping item"
																style="width: 65px;">
														</div>
														<div class="ms-3">
															<div style="width: 250px;">
															<h5><%= cartProductList.get(i).getProduct().getProductName() %></h5>
														</div>
														</div>
													</div>												
													<div class="d-flex flex-row align-items-center">
														<div style="width: 80px;">
															<h5 class="mb-0"><%= cartProductList.get(i).getSalesPrice() %> TL</h5>
														</div>
													</div>
													<div class="d-flex flex-row align-items-center">
														<div style="width: 50px;">
															<h5 class="fw-normal mb-0"><%= cartProductList.get(i).getSalesQuantity() %>x</h5>
														</div>
														<div style="width: 120px;">
															<h5 class="mb-0"><%= cartProductList.get(i).getLineAmount() %> TL</h5>
														</div>
														<a href="#!" style="color: #cecece;"><i
															class="fas fa-trash-alt text-danger"></i></a>
													</div>
												</div>
											</div>
										</div>
									
									<%}%>
	
									</div>
									<div class="col-lg-4">
										<div class="card bg-primary text-white rounded-3">
											<div class="card-body">
												<div
													class="d-flex justify-content-between align-items-center mb-4">
													<h5 class="mb-0">Card details</h5>
													<img
														src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/avatar-6.webp"
														class="img-fluid rounded-3" style="width: 45px;"
														alt="Avatar">
												</div>
												<hr class="my-4">
												<div class="d-flex justify-content-between">
													<p class="mb-2">Subtotal</p>
													<p class="mb-2"><%= cart.getTotalAmount() %> TL</p>
												</div>
												<div class="d-flex justify-content-between">
													<p class="mb-2">Shipping</p>
													<p class="mb-2">0</p>
												</div>
												<div class="d-flex justify-content-between mb-4">
													<p class="mb-2">Total(Incl. taxes)</p>
													<p class="mb-2"><%= cart.getTotalAmount() %> TL</p>
												</div>

												<button type="button" class="btn btn-info btn-block btn-lg">
													<div class="d-flex justify-content-between">
														<span>Checkout <i
															class="fas fa-long-arrow-alt-right ms-2"></i></span>
													</div>
												</button>
											</div>
										</div>
									</div>
								</div>
								<%}else{%>
									<h1 class="text-center"><%= message %></h1>
								<%} %>						
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

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