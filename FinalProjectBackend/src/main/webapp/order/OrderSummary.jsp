<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page
	import="com.ibtech.business.concretes.OrderManager,
	com.ibtech.repository.OrderRepository,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.*,
	com.ibtech.entities.Order"%>
<%
	if(session.getAttribute("isAdmin") == null || !(ParseHelper.isBoolean(session.getAttribute("isAdmin").toString()))){
		response.sendRedirect("AdminLogin.jsp");
	}
	session.setAttribute("sideBarId", 5);
	List<Order> orders = new ArrayList<>();
	OrderManager cartService = new OrderManager(new OrderRepository());
	DataResult<List<Order>> result = cartService.getAll();
	if(result.isSuccess()){
		orders = result.getData();
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
			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
			<%if(!result.isSuccess()) {%>
				<h1 class="text-center text-danger mt-3"><%= result.getMessage() %></h1>
			<%} else{%>
				<div class="card">
					<div class="card-header text-center bg-dark bg-gradient text-light">
						<h3>Order Management</h3>
						<%= orders.size()%>
						orders listed.
					</div>
					<div class="card-body table-responsive">
						<table class="table align-middle">
							<thead>
								<tr class="bg-warning bg-gradient">
									<th scope="col">ID</th>
									<th scope="col">Customer Name</th>
									<th scope="col">Address Line 1</th>
									<th scope="col">Total Amount</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<%for (int i = 0; i < orders.size(); i += 1) {%>
								<tr>
									<td><%=orders.get(i).getId()%></td>
									<td><%=orders.get(i).getCustomerName()%></td>
									<td><%=orders.get(i).getAddressLine1()%></td>
									<td><%=orders.get(i).getTotalAmount()%></td>
									<td>
	                            		<div class="btn-group float-end mr-4">
											<a href="OrderDetail.jsp?orderId=<%=orders.get(i).getId()%>" class="btn btn-dark px-2">Detail</a>
	                            		</div>
				           			</td>
								</tr>
								<%}%>
							</tbody>
						</table>
					</div>
				</div>
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