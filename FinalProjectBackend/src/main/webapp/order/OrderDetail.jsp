<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.business.concretes.OrderProductManager,
	com.ibtech.business.concretes.OrderManager,
	java.util.ArrayList,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.repository.OrderRepository,
	com.ibtech.repository.OrderProductRepository,
	java.util.List,
	com.ibtech.entities.Order,
	com.ibtech.entities.OrderProduct"%>
<%
	if(session.getAttribute("isAdmin") == null || !(ParseHelper.isBoolean(session.getAttribute("isAdmin").toString()))){
		response.sendRedirect("AdminLogin.jsp");
	}
	Order order = null;
	String message = "";
	DataResult<Order> orderResult = null;
	DataResult<List<OrderProduct>> orderProductResult = null;
	List<OrderProduct> orderProductList = new ArrayList<>();
	if (request.getParameter("orderId") != null && ParseHelper.isLong(request.getParameter("orderId"))) {
		OrderManager orderService = new OrderManager(new OrderRepository());
		long orderId = Long.parseLong(request.getParameter("orderId"));
		orderResult = orderService.getById(orderId);
		if(orderResult.isSuccess()){
			order = orderResult.getData();
			OrderProductManager cartProductService = new OrderProductManager(new OrderProductRepository());
			orderProductResult = cartProductService.getByOrderId(orderId);
			if(orderProductResult.isSuccess()){
				orderProductList = orderProductResult.getData();
				message = orderProductList.size() > 0 ? "" : "No products have been added to the cart yet.";
			}
		}
	}else{
		response.sendRedirect("OrderSummary.jsp");
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
<link rel="stylesheet" type="text/css" href="../css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="../css/navbar.css" />
</head>
<body>
	
  <jsp:include page="../partials/Navbar.jsp" />
   <div class="container-fluid">
    <div class="row mt-2">
      <jsp:include page="../partials/SideBar.jsp" />
    	 <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4" >
		  <%if (orderResult == null) {%>
			<%} else if (!orderResult.isSuccess()) {%>
			<div class="text-center mt-3">
				<h1 class="text-center text-danger"><%=orderResult.getMessage() != null ? orderResult.getMessage() : ""%></h1>
				<img src="../../images/data_not_found.jpg">
			</div>
			<%} else {%>
				<%if (orderProductResult == null) {%>
				<%} else if (!orderProductResult.isSuccess()) {%>
					<div class="text-center mt-3">
						<h1 class="text-center text-danger"><%=orderProductResult.getMessage() != null ? orderProductResult.getMessage() : ""%></h1>
						<img src="../../images/data_not_found.jpg">
					</div>
				<%} else {%>
					<div class="content mx-auto h-100" >
    		<section class="h-100 h-custom">
				<div class="py-5 h-100">
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col">
					<h3>Order Detail</h3>
						<div class="card">
							<div class="card-body p-4">
								<% if(orderProductList.size() > 0) { %>
									<div class="row">
									<div class="col-lg-8">
									<% for(int i = 0; i < orderProductList.size() ; i++) {%>
										<div class="card mb-3">
											<div class="card-body">
												<div class="d-flex justify-content-between">
													<div class="d-flex flex-row align-items-center">
														<div>
															<img
																src="<%= orderProductList.get(i).getImagePath() %>"
																class="img-fluid rounded-3" alt="Shopping item"
																style="width: 65px;">
														</div>
														<div class="ms-3">
															<div style="width: 250px;">
															<h5><a href="OrderProductDetail.jsp?orderProductId=<%=orderProductList.get(i).getId() %>"><%= orderProductList.get(i).getProductName() %></a></h5>
														</div>
														</div>
													</div>												
													<div class="d-flex flex-row align-items-center">
														<div style="width: 100px;">
															<h5 class="mb-0"><%= orderProductList.get(i).getSalesPrice() %> TL</h5>
														</div>
													</div>
													<div class="d-flex flex-row align-items-center">
														<div style="width: 110px;">
															<h5 class="mb-0">Tax: <%= orderProductList.get(i).getTaxRate() %>%</h5>
														</div>
													</div>
													
													<div class="d-flex flex-row align-items-center">
														<div style="width: 50px;">
															<h5 class="fw-normal mb-0"><%= orderProductList.get(i).getSalesQuantity() %>x</h5>
														</div>
														
														
														<div style="width: 120px;">
															<h5 class="mb-0"><%= orderProductList.get(i).getLineAmount() %> TL</h5>
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
													<p class="mb-2"><%= order.getCustomerName()%></p>
												</div>
												<div class="d-flex justify-content-between">
													<p class="mb-2">Address Line 1</p>
													<p class="mb-2"><%= order.getAddressLine1() %></p>
												</div>
												<div class="d-flex justify-content-between">
													<p class="mb-2">Address Line 2</p>
													<p class="mb-2"><%= order.getAddressLine2() %></p>
												</div>
												<div class="d-flex justify-content-between">
													<p class="mb-2">Shipping</p>
													<p class="mb-2">0</p>
												</div>
												<div class="d-flex justify-content-between mb-4">
													<p class="mb-2">Total(Incl. taxes)</p>
													<p class="mb-2"><%= order.getTotalAmount() %> TL</p>
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
    </div>
  
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</html>