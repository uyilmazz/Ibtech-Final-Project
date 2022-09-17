<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page
	import="com.ibtech.business.concretes.CategoryManager,
	com.ibtech.repository.CategoryRepository,
	com.ibtech.business.concretes.UserManager,
	com.ibtech.repository.UserRepository,
	com.ibtech.business.concretes.ProductManager,
	com.ibtech.repository.ProductRepository,
	com.ibtech.business.concretes.OrderManager,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.repository.OrderRepository,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.entities.Cart"%>
<%
if(request.getParameter("signOut") != null){
	System.out.println("navbar");}

	if(session.getAttribute("isAdmin") == null || !(ParseHelper.isBoolean(session.getAttribute("isAdmin").toString()))){
		response.sendRedirect("AdminLogin.jsp");
	}

	int productCount = 0;
	int categoryCount = 0;
	int orderCount = 0;
	int userCount = 0;
	session.setAttribute("sideBarId", 0);
	CategoryManager cartService = new CategoryManager(new CategoryRepository());
	UserManager userService = new UserManager(new UserRepository());
	ProductManager productService = new ProductManager(new ProductRepository());
	OrderManager orderService = new OrderManager(new OrderRepository());
	DataResult<Integer> productResult = productService.getCount();
	DataResult<Integer> categoryResult = cartService.getCount();
	DataResult<Integer> orderResult = orderService.getCount();
	DataResult<Integer> userResult = userService.getCount();
	productCount = productResult.isSuccess() ? productResult.getData() : 0;
	categoryCount = categoryResult.isSuccess() ? categoryResult.getData() : 0;
	orderCount = orderResult.isSuccess() ? orderResult.getData() : 0;
	userCount = userResult.isSuccess() ? userResult.getData() : 0;
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
<link rel="stylesheet" type="text/css" href="../css/admin.css" />
</head>
<body>
	
	<jsp:include page="../partials/Navbar.jsp" />
	<div class="container-fluid">
		<div class="row mt-2">
			<jsp:include page="../partials/SideBar.jsp" />
			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div class="container">
    
				    <div class="row">
				
					<div class="four col-md-3">
						<div class="counter-box colored">
							<i class="fa-solid fa-tag"></i>
							<span class="counter"><%= productCount %></span>
							<p>Products</p>
						</div>
					</div>
					<div class="four col-md-3">
						<div class="counter-box colored">
							<i class="fa fa-group"></i>
							<span class="counter"><%= categoryCount %></span>
							<p>Categories</p>
						</div>
					</div>
					<div class="four col-md-3">
						<div class="counter-box colored">
							<i class="fa  fa-shopping-cart"></i>
							<span class="counter"><%= orderCount %></span>
							<p>Orders</p>
						</div>
					</div>
					<div class="four col-md-3">
						<div class="counter-box colored">
							<i class="fa  fa-user"></i>
							<span class="counter"><%= userCount %></span>
							<p>Customer</p>
						</div>
					</div>
				  </div>	
			</div>
			</main>
		</div>
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