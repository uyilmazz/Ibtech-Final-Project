<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page
	import="com.ibtech.shopping.service.OrderService,
	com.ibtech.shopping.service.OrderProductService,
	java.util.ArrayList,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.core.utilities.helper.ParseHelper,
	java.util.List,
	com.ibtech.shopping.entities.Order,
	com.ibtech.shopping.entities.OrderProduct"%>
<%
	Order order = null;
	DataResult<Order> orderResult = null;
	DataResult<List<OrderProduct>> orderProductResult = null;
	List<OrderProduct> orderProductList = new ArrayList<>();
	if (request.getParameter("orderId") != null && ParseHelper.isLong(request.getParameter("orderId"))) {
		OrderService orderService = new OrderService();
		long orderId = Long.parseLong(request.getParameter("orderId"));
		orderResult = orderService.getById(orderId);
		if(orderResult.isSuccess()){
			order = orderResult.getData();
			OrderProductService orderProductService = new OrderProductService();
			orderProductResult = orderProductService.getByOrderId(orderId);
			if(orderProductResult.isSuccess()){
				orderProductList = orderProductResult.getData();
			}
		}
	}else{
		response.sendRedirect("CustomerOrder.jsp");
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
</head>
<body>
	<div class="container mt-3">
		<jsp:include page="partials/Navbar.jsp" />
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
									<h5 class="mb-3">
											<a href="CustomerOrder.jsp" class="text-body"><i
												class="fas fa-long-arrow-alt-left me-2"></i>Back to My orders</a>
										</h5>
										<hr>
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
															<h5><%= orderProductList.get(i).getProductName() %></h5>
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