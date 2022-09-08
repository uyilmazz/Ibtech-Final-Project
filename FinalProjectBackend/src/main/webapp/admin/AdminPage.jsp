<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page
	import="com.ibtech.inventory.business.concretes.CategoryManager,
	com.ibtech.inventory.repository.CategoryRepository,
	com.ibtech.inventory.entities.Category,com.ibtech.inventory.entities.Product"%>
<%
	CategoryManager categoryService = new CategoryManager(new CategoryRepository());
	List<Category> categories = categoryService.getAll().getData();
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

	<div class="container-fluid mt-5 mx-3">
		<div class="row">
			<div class="col-3">
				<div class="list-group">
				  <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
				    The current link item
				  </a>
				  <a href="#" class="list-group-item list-group-item-action">A second link item</a>
				  <a href="#" class="list-group-item list-group-item-action">A third link item</a>
				  <a href="#" class="list-group-item list-group-item-action">A fourth link item</a>
				  <a class="list-group-item list-group-item-action disabled">A disabled link item</a>
				</div>
			</div>
			<div class="col-9">Center</div>
		</div>
	</div>



	<!--   <table border="1">
        <% for(int i = 0; i < categories.size(); i+=1) { %>
            <tr>      
                <td><%=categories.get(i).getCategoryName()%></td> 
            </tr>
        <% } %>
    </table> -->

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</body>
</html>