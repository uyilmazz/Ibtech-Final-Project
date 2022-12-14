<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.business.concretes.CategoryManager,
	com.ibtech.repository.CategoryRepository,
	com.ibtech.entities.Category,com.ibtech.entities.Product,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.core.utilities.helper.ParseHelper"%>
<%
	if(session.getAttribute("isAdmin") == null || !(ParseHelper.isBoolean(session.getAttribute("isAdmin").toString()))){
		response.sendRedirect("AdminLogin.jsp");
	}
	Category category = null;
	DataResult<Category> result = null;
	if (request.getParameter("categoryId") != null && ParseHelper.isInteger(request.getParameter("categoryId"))) {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		CategoryManager categoryManager = new CategoryManager(new CategoryRepository());
		result = categoryManager.getById(categoryId);
		if (result.isSuccess()) {
			category = result.getData();
		}
	}else{
		response.sendRedirect("./CategorySummary.jsp");
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
			<%if (result == null) {%>
			<%} else if (!result.isSuccess()) {%>
			<div class="text-center mt-3">
				<h1 class="text-center text-danger"><%=result.getMessage()%></h1>
				<img src="../../images/data_not_found.jpg">
			</div>
			<%} else {%>
			<div class="content w-50 mx-auto h-100">
				<div class="col-md-12 mt-4">
					<div class="card my-auto">
						<div class="card-header">
							<h5 class="title">Category Detail</h5>
						</div>
						<div class="card-body">
							<form>
								<div class="mb-3">
									<label for="categoryId">Category Id</label>
									<div class="form-group">
										<input type="text" value="<%=category.getCategoryId()%>"
											class="form-control" placeholder="Category Id"
											id="categoryId" disabled />
									</div>
								</div>
								<div class="mb-3">
									<label for="categoryName">Category Name</label>
									<div class="form-group">
										<input type="text" value="<%=category.getCategoryName()%>"
											class="form-control" placeholder="Category Name"
											id="categoryName" disabled />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<%}%>
		</main>
	</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</html>