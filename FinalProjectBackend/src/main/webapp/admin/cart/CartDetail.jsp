<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.business.concretes.CartManager,
	com.ibtech.business.concretes.CartProductManager,
	java.util.ArrayList,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.repository.CartRepository,
	com.ibtech.repository.CartProductRepository,
	java.util.List,
	com.ibtech.entities.Cart,
	com.ibtech.entities.CartProduct"%>
<%
	Cart cart = null;
	String message = "";
	DataResult<Cart> cartResult = null;
	DataResult<List<CartProduct>> cartProductResult = null;
	List<CartProduct> cartProductList = new ArrayList<>();
	if (request.getParameter("cartId") != null && ParseHelper.isLong(request.getParameter("cartId"))) {
		CartManager cartService = new CartManager(new CartRepository());
		long cartId = Long.parseLong(request.getParameter("cartId"));
		cartResult = cartService.getById(cartId);
		if(cartResult.isSuccess()){
			cart = cartResult.getData();
			CartProductManager cartProductService = new CartProductManager(new CartProductRepository());
			cartProductResult = cartProductService.getByCartId(cartId);
			if(cartProductResult.isSuccess()){
				cartProductList = cartProductResult.getData();
				message = cartProductList.size() > 0 ? "" : "No products have been added to the cart yet.";
			}
		}
	}else{
		response.sendRedirect("CartSummary.jsp");
	}
%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="../../css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="../../css/navbar.css" />
</head>
<body>
	
  <jsp:include page="../partials/Navbar.jsp" />
    <div class="row mt-2">
      <jsp:include page="../partials/SideBar.jsp" />
    	 <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4" >
		  <%if (cartResult == null) {%>
			<%} else if (!cartResult.isSuccess()) {%>
			<div class="text-center mt-3">
				<h1 class="text-center text-danger"><%=cartResult.getMessage()%></h1>
				<img src="../../images/data_not_found.jpg">
			</div>
			<%} else {%>
				<%if (cartProductResult == null) {%>
				<%} else if (!cartProductResult.isSuccess()) {%>
					<div class="text-center mt-3">
						<h1 class="text-center text-danger"><%=cartProductResult.getMessage()%></h1>
						<img src="../../images/data_not_found.jpg">
					</div>
				<%} else {%>
					<div class="content mx-auto h-100" >
    		<section class="h-100 h-custom">
				<div class="py-5 h-100">
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col">
					<h3>Cart Detail</h3>
						<div class="card">
							<div class="card-body p-4">
								<% if(cartProductList.size() > 0) { %>
									<div class="row">
									<div class="col-lg-8">
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
															<h5><a href="CartProductDetail.jsp?cartProductId=<%=cartProductList.get(i).getId() %>"><%= cartProductList.get(i).getProduct().getProductName() %></a></h5>
														</div>
														</div>
													</div>												
													<div class="d-flex flex-row align-items-center">
														<div style="width: 100px;">
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
							
												<div class="d-flex justify-content-between">
													<p class="mb-2">Customer</p>
													<p class="mb-2"><%= cart.getCustomerName()%></p>
												</div>
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
				
				<%} %>
			<%} %>
		  
	</main>
    </div>
  
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</html>