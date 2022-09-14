<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page
	import="com.ibtech.inventory.service.ProductService,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.inventory.entities.Product"%>
<%
	int limit = 20;
	List<Product> products = new ArrayList<>();
	DataResult<List<Product>> result = null;
	ProductService productService = new ProductService();
	if(request.getParameter("categoryId") != null && ParseHelper.isInteger(request.getParameter("categoryId"))){
		int categoryId = Integer.parseInt( request.getParameter("categoryId"));
		result = productService.getByCategoryId(categoryId);
		if(result.isSuccess()){
			products = result.getData();
		}
	}else{
		result = productService.getLimitedData(limit);
		if(result.isSuccess()){
			products = result.getData();
		}
	}	
%>    

	<%if(result.isSuccess()) {%>
		<div class="row mt-3">
		<%for (int i = 0; i < products.size(); i += 1) {%>
	    	 <div class="card mx-3" style="width: 18rem;">
				  <img src="..." class="card-img-top">
				  <div class="card-body">
				    <h5 class="card-title"><a href="ProductView.jsp?productId=<%=products.get(i).getProductId() %>"><%= products.get(i).getProductName() %></a></h5>
				    <p class="card-text"><%= products.get(i).getSalesPrice()%> TL</p>
				    <a href="#" class="btn btn-primary">Add to Cart</a>
				  </div>
			</div>		
		<%}%>
	</div>
	<%} else{%>
		<div class="d-flex justify-content-sm-center align-items-center" style="height: 60vh;">
		    <h3 class="text-danger"><%=result.getMessage() != null ?  result.getMessage() : ""%></h3>
		</div>
	<%} %>

