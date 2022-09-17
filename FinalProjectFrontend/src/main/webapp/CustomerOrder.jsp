<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.shopping.service.OrderService,
	com.ibtech.core.utilities.result.*,
	java.util.List,
	java.util.ArrayList,
	com.ibtech.shopping.entities.Order"%>
<%
	List<Order> orders = new ArrayList<>();
	DataResult<List<Order>> result = null;
	if(session.getAttribute("userName") != null){
		String customerName = session.getAttribute("userName").toString();
		OrderService orderService = new OrderService();
		result = orderService.getByCustomerName(customerName);
		if(result.isSuccess()){
			orders = result.getData();
		}
	}else{
		session.setAttribute("backUrl", "CustomerOrder.jsp");
		response.sendRedirect("UserLogin.jsp");
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
		<%if(result == null) {%>
		<%}else if(result.isSuccess()) {%>
			<section class="h-100 h-custom" style="background-color: #eee;">
			<div class="py-5 h-100">
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col">
						<div class="card">
							<div class="card-body p-4">
									<div class="row">
									<div class="col-lg-12">
										<div
											class="d-flex justify-content-between align-items-center mb-4">
											<div>
												<h3 class="mb-0" style="color:#FF6000">You have <%= orders.size() %> orders</h3>
											</div>
										</div>
									<% for(int i = 0; i < orders.size() ; i++) {%>
										<div class="card mb-3">
											<div class="card-body">
												<div class="d-flex justify-content-between">
													<div class="d-flex flex-row align-items-center">
														<div class="ms-3">
															<div style="width: 250px;">
															<h5><%= orders.get(i).getCustomerName() %></h5>
														</div>
														</div>
													</div>												
													<div class="d-flex flex-row align-items-center">
														<div style="width: 100px;">
															<h5 class="mb-0"><%= orders.get(i).getTotalAmount() %> TL</h5>
														</div>
													</div>
													<div class="d-flex flex-row align-items-center">
														<div style="width: 130px;">
															<a href="OrderDetail.jsp?orderId=<%=orders.get(i).getId()%>" class="btn btn-outline-success me-2" type="submit">Order Detail</a>
														</div>
													</div>												
												</div>
											</div>
										</div>
									<%}%>
									</div>
								</div>					
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<%}else{%>
			<div class="d-flex justify-content-sm-center align-items-center" style="height: 60vh;">
			    <h3 class="text-danger"><%=result.getMessage() != null ?  result.getMessage() : "You do not have an order yet"%></h3>
			</div>
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