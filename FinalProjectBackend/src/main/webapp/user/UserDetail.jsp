<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.business.concretes.UserManager,
	com.ibtech.repository.UserRepository,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.entities.User"%>
<%
	if(session.getAttribute("isAdmin") == null || !(ParseHelper.isBoolean(session.getAttribute("isAdmin").toString()))){
		response.sendRedirect("AdminLogin.jsp");
	}
	DataResult<User> result = null;
	if (request.getParameter("userId") != null && ParseHelper.isLong(request.getParameter("userId"))) {
		Long userId = Long.parseLong(request.getParameter("userId"));
		UserManager userService = new UserManager(new UserRepository());
		result = userService.getById(userId);
	} else {
		response.sendRedirect("UserSummary.jsp");
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
			<%if(result == null) {%>
			<% } else if(result.isSuccess()){ %>
				<div class="content w-50 mx-auto h-100">
				<div class="col-md-12 mt-4">
					<div class="card my-auto">
						<div class="card-header">
							<h5 class="title">User Detail</h5>
						</div>
						<div class="card-body">
							<form>
								<div class="mb-3">
									<label for="userId">User Id</label>
									<div class="form-group">
										<input type="text" value="<%=result.getData().getId()%>"
											class="form-control" placeholder="User Id" id="userId"
											disabled />
									</div>
								</div>
								<div class="mb-3">
									<label for="userName">User Name</label>
									<div class="form-group">
										<input type="text" value="<%=result.getData().getName()%>"
											class="form-control" placeholder="User Name" id="userName"
											disabled />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<%} else {%>
				<div class="text-center mt-3">
						<h1 class="text-center text-danger"><%=result.getMessage() != null ? result.getMessage() : ""%></h1>
						<img src="../../images/data_not_found.jpg">
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