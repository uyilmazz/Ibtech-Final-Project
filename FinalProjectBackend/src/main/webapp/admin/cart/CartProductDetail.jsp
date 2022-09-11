<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page
	import="com.ibtech.repository.CartProductRepository,
	com.ibtech.business.abstracts.CartProductService,
	com.ibtech.business.concretes.CartProductManager,
	com.ibtech.entities.CartProduct
	"
%>
<%
	String message ="";
	if(request.getParameter("cartProductId") == null){
		response.sendRedirect("CartSummary.jsp");
	}
	long cartProductId = Long.parseLong(request.getParameter("cartProductId"));
	CartProductService cartProductService = new CartProductManager(new CartProductRepository());
	CartProduct cartProduct = cartProductService.getById(cartProductId).getData();
	if(cartProduct == null){
		message = "CartProduct Not Found!";
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
		 <main class="col-md-7 ms-sm-auto col-lg-10 px-md-4" >
		
			<% if(cartProduct != null) {%>
				<div class="container">
				<div class="row mt-3">
					<div class="col-4 lg-5 sm-1">
		    			<img src="https://productimages.hepsiburada.net/s/268/550/110000253796405.jpg/format:webp">
		    		</div>
		    		<div class="col-5 lg-5 sm-1 mt-3 mx-auto">
						<div class="card bg-secondary p-2 text-white rounded-3 mt-4">
							<header class="title-wrapper px-2 ">
							    <h1><%= cartProduct.getProduct().getProductName() %></h1>
							 </header>
							 <div class="card-body">
							 	<div class="d-flex justify-content-between">
									<p class="mb-2">Cart Product Id</p>
									<p class="mb-2"><%= cartProduct.getId() %></p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Category</p>
									<p class="mb-2"><%= cartProduct.getProduct().getCategory().getCategoryName() %></p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Sales Price</p>
									<p class="mb-2"><%= cartProduct.getSalesPrice() %> TL</p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Sales Quantity</p>
									<p class="mb-2"><%= cartProduct.getSalesQuantity() %>x</p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Tax Rate</p>
									<p class="mb-2"><%= cartProduct.getTaxRate() %> %</p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Line Amount</p>
									<p class="mb-2"><%= cartProduct.getLineAmount() %> TL</p>
								</div>
							</div>
						</div>
					</div>
   				</div>
				</div>
				
				<%}else{%>
					<h1 class="text-center"><%= message %></h1>
				<%} %>	
		   </main>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</html>