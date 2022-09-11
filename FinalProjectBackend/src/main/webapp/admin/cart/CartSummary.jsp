<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page
	import="com.ibtech.business.concretes.CartManager,
	com.ibtech.repository.CartRepository,
	com.ibtech.entities.Cart"%>
<%
CartManager cartService = new CartManager(new CartRepository());
	List<Cart> carts = cartService.getAll().getData();
	if(carts == null){
		carts = new ArrayList<>();
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
	<div class="container-fluid">
		<div class="row mt-2">
			<jsp:include page="../partials/SideBar.jsp" />
			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div class="card">
					<div class="card-header text-center bg-dark bg-gradient text-light">
						<h3>Cart Management</h3>
						<%= carts.size()%>
						carts listed.
					</div>
					<div class="card-body table-responsive">
						<table class="table align-middle">
							<thead>
								<tr class="bg-warning bg-gradient">
									<th scope="col">ID</th>
									<th scope="col">Customer Name</th>
									<th scope="col">Total Amount</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<%for (int i = 0; i < carts.size(); i += 1) {%>
								<tr>
									<td><%=carts.get(i).getId()%></td>
									<td><%=carts.get(i).getCustomerName()%></td>
									<td><%=carts.get(i).getTotalAmount()%></td>
									<td>
	                            		<div class="btn-group float-end mr-4">
											<a href="CartDetail.jsp?cartId=<%=carts.get(i).getId()%>" class="btn btn-dark px-2">Detail</a>
	                            		</div>
				           			</td>
								</tr>
								<%}%>
							</tbody>
						</table>
					</div>
				</div>
			</main>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>

</body>
</html>