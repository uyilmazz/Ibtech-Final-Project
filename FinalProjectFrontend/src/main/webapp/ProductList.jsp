<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page
	import="com.ibtech.inventory.service.ProductService,
	com.ibtech.inventory.entities.Product"%>
<%
	List<Product> products;
	ProductService productService = new ProductService();
	if(request.getParameter("categoryId") != null){
		int categoryId = Integer.parseInt( request.getParameter("categoryId"));
		products= productService.getByCategoryId(categoryId);
	}else{
		products = productService.getAll();
	}	
%>    

<div class="row mt-3">
	<%for (int i = 0; i < products.size(); i += 1) {%>
    	 <div class="card mx-2" style="width: 18rem;">
			  <img src="..." class="card-img-top">
			  <div class="card-body">
			    <h5 class="card-title"><a href="ProductView.jsp?productId=<%=products.get(i).getProductId() %>"><%= products.get(i).getProductName() %></a></h5>
			    <p class="card-text"><%= products.get(i).getSalesPrice()%> TL</p>
			    <a href="#" class="btn btn-primary">Add to Cart</a>
			  </div>
		</div>		
	<%}%>
</div>
