<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,
java.util.ArrayList"%>
<%@ page
	import="com.ibtech.business.concretes.CategoryManager,com.ibtech.repository.CategoryRepository,com.ibtech.entities.Category,com.ibtech.entities.Product,
	com.ibtech.core.utilities.result.DataResult"%>
<%
	List<Category> categories = new ArrayList<>();
	CategoryManager categoryService = new CategoryManager(new CategoryRepository());
	DataResult<List<Category>> result = categoryService.getAll();
	if(result.isSuccess()){
		categories = result.getData();
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
			<%if(!result.isSuccess()) {%>
				<h1 class="text-center text-danger mt-3"><%= result.getMessage() %></h1>
			<%} else{%>
				<div class="card">
					<div class="card-header text-center bg-dark bg-gradient text-light">
						<h3>Category Management</h3>
						<%=categories.size()%>
						categories listed.
					</div>
					<div class="card-body table-responsive">
						<table class="table align-middle">
							<thead>
								<tr class="bg-warning bg-gradient">
									<th scope="col">ID</th>
									<th scope="col">Name</th>
									<th scope="col"><button type="button"
                                		class="btn btn-success float-end px-3">Add Product</button>
                       				 </th>
								</tr>
							</thead>
							<tbody>
								<%for (int i = 0; i < categories.size(); i += 1) {%>
								<tr>
									<td><%=categories.get(i).getCategoryId()%></td>
									<td><%=categories.get(i).getCategoryName()%></td>
									<td>
	                            		<div class="btn-group float-end mr-2">
											<a href="CategoryDetail.jsp?categoryId=<%=categories.get(i).getCategoryId() %>" class="btn btn-dark px-2">Detail</a>
	                                		<a href="#" class="btn btn-danger px-2 ">Delete</a>
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