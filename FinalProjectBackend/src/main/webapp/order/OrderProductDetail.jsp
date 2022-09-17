<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page
	import="com.ibtech.repository.OrderProductRepository,
	com.ibtech.business.concretes.OrderProductManager,
	com.ibtech.entities.OrderProduct,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.core.utilities.helper.ParseHelper"
%>
<%
	if(session.getAttribute("isAdmin") == null || !(ParseHelper.isBoolean(session.getAttribute("isAdmin").toString()))){
		response.sendRedirect("AdminLogin.jsp");
	}
	OrderProduct orderProduct = null;
	DataResult<OrderProduct> result = null;
	if(request.getParameter("orderProductId") != null && ParseHelper.isLong(request.getParameter("orderProductId"))){
		long orderProductId = Long.parseLong(request.getParameter("orderProductId"));
		OrderProductManager orderProductManager = new OrderProductManager(new OrderProductRepository());
		result = orderProductManager.getById(orderProductId);
		if(result.isSuccess()){
			orderProduct = result.getData();
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
		 <main class="col-md-7 ms-sm-auto col-lg-10 px-md-4" >
			<%if (result == null) {%>
			<%} else if (!result.isSuccess()) {%>
			<div class="text-center mt-3">
				<h1 class="text-center text-danger"><%=result.getMessage() != null ? result.getMessage() :  ""%></h1>
				<img src="../../images/data_not_found.jpg">
			</div>
			<%} else {%>
				<div class="container">
				<div class="row mt-3">
					<div class="col-4 lg-5 sm-1" align="end">
		    			<img height="500" src="<%= orderProduct.getImagePath() %>">
		    		</div>
		    		<div class="col-5 lg-5 sm-1 mt-3 mx-auto">
						<div class="card bg-secondary p-2 text-white rounded-3 mt-4">
							<header class="title-wrapper px-2 ">
							    <h1><%= orderProduct.getProductName() %></h1>
							 </header>
							 <div class="card-body">
							 	<div class="d-flex justify-content-between">
									<p class="mb-2">Order Product Id</p>
									<p class="mb-2"><%= orderProduct.getId() %></p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Sales Price</p>
									<p class="mb-2"><%= orderProduct.getSalesPrice() %> TL</p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Sales Quantity</p>
									<p class="mb-2"><%= orderProduct.getSalesQuantity() %>x</p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Tax Rate</p>
									<p class="mb-2"><%= orderProduct.getTaxRate() %> %</p>
								</div>
								<div class="d-flex justify-content-between">
									<p class="mb-2">Line Amount</p>
									<p class="mb-2"><%= orderProduct.getLineAmount() %> TL</p>
								</div>
							</div>
						</div>
					</div>
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