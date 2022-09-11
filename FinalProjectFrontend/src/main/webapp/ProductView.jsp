<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page
	import="com.ibtech.inventory.service.ProductService,
	com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.service.CartProductService,
	com.ibtech.inventory.entities.Product,
	com.ibtech.shopping.entities.CartProduct,
	com.ibtech.shopping.entities.Cart"%>
<%
	ProductService productService = new ProductService();	
	CartService cartService = new CartService();
	if(session.getAttribute("cartId") == null){
		Cart cart = new Cart(0,"new customer",0);
		Cart newCart = cartService.createCart(cart);
		session.setAttribute("cartId", newCart.getId());
	}
	
	long cartId = (long) session.getAttribute("cartId");
	Product product = null;
	String message ="";
	if(request.getParameter("productId") != null){
		long productId = Long.parseLong(request.getParameter("productId"));
		product = productService.getById(productId);
	}else{
		message = "Product Not Found!";
	}
%>  

<%!
	public boolean addProduct(javax.servlet.jsp.JspWriter out,Product product,long cartId) {
		int productQuantity = 2;
		double taxRate = 10;
		double lineAmount = product.getSalesPrice() + (product.getSalesPrice() * 2 * taxRate) / 100;
		CartProductService cartProductService = new CartProductService();
		CartProduct cartProduct = new CartProduct();
		cartProduct.setCartId(cartId);
		cartProduct.setProduct(product);
		cartProduct.setSalesPrice(product.getSalesPrice());
		cartProduct.setSalesQuantity(2);
		cartProduct.setLineAmount(lineAmount);
		cartProduct.setTaxRate(taxRate);
		cartProductService.addCartProduct(cartProduct);
		return true;
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>
<body>
	<div class="container mt-3">
		<jsp:include page="partials/Navbar.jsp" />
		<c:choose>
		    <c:when test="${product != null}">
		    	<div class="row mt-3">
		    		<div class="col lg-5 sm-1">
		    			<img src="https://productimages.hepsiburada.net/s/268/550/110000253796405.jpg/format:webp">
		    		</div>
		    		<div class="col lg-5 sm-1">
		    			<header class="title-wrapper">
		    				<h1><%= product.getProductName() %></h1>
		    			</header>
		    			<div>
		    				<h2><span class="text-success"><%= product.getSalesPrice() %> TL</span></h2>
		    			</div>
		    			 <button class="btn mt-3" onclick="javascript:addProduct(Product product,long cartId)"
   						 style="background-color:#ff6000"><span class="text-white">Add to Cart</span></button>
   					</div>
		    	</div>
		    </c:when>    
		    <c:otherwise>
				<%= message %>
		    </c:otherwise>
		</c:choose>
	</div>
	
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script>
function addProduct(Product product,long cartId) {
	int productQuantity = 2;
	double taxRate = 10;
	double lineAmount = product.getSalesPrice() + (product.getSalesPrice() * 2 * taxRate) / 100;
	CartProductService cartProductService = new CartProductService();
	CartProduct cartProduct = new CartProduct();
	cartProduct.setCartId(cartId);
	cartProduct.setProduct(product);
	cartProduct.setSalesPrice(product.getSalesPrice());
	cartProduct.setSalesQuantity(2);
	cartProduct.setLineAmount(lineAmount);
	cartProduct.setTaxRate(taxRate);
	cartProductService.addCartProduct(cartProduct);
}
    </script>

</body>
</html>