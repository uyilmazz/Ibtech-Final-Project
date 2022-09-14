<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page
	import="com.ibtech.inventory.service.ProductService,
	com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.service.CartProductService,com.ibtech.inventory.entities.Product,
	com.ibtech.shopping.entities.CartProduct,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.DataResult,
	com.ibtech.shopping.entities.Cart"%>
<%
	ProductService productService = new ProductService();	
	Product product = null;
	DataResult<Product> result = null;
	if(request.getParameter("productId") != null && ParseHelper.isLong(request.getParameter("productId"))){
		long productId = Long.parseLong(request.getParameter("productId"));
		result = productService.getById(productId);
		if(result.isSuccess()){
			product = result.getData();
		}
	}else{
		response.sendRedirect("MainPage.jsp");
	}
	
	if(request.getParameter("addProduct") != null){
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		if(session.getAttribute("cartId") != null && ParseHelper.isLong(session.getAttribute("cartId").toString())){
			long cartId = (Long) session.getAttribute("cartId");
			DataResult<CartProduct> addCartProductResult = addProduct(product, cartId, quantity);
		}else{
			session.removeAttribute("cartId");
			CartService cartService = new CartService();
			Cart cart = new Cart(0,"",0);
			DataResult<Cart> createdCartResult = cartService.createCart(cart);
			if(createdCartResult.isSuccess()){
				session.setAttribute("cartId", createdCartResult.getData().getId());
				addProduct(product, createdCartResult.getData().getId(), quantity);
			}else{
				response.sendRedirect("ProductView.jsp");
			}
		}
		
	}
%>  

<%!
	public DataResult<CartProduct> addProduct(Product product,long cartId,int quantity) {
		double taxRate = 10;
		double lineAmount = (product.getSalesPrice() * quantity) + (product.getSalesPrice() * quantity * taxRate) / 100;
		CartProductService cartProductService = new CartProductService();
		CartProduct cartProduct = new CartProduct();
		cartProduct.setCartId(cartId);
		cartProduct.setProduct(product);
		cartProduct.setSalesPrice(product.getSalesPrice());
		cartProduct.setSalesQuantity(quantity);
		cartProduct.setLineAmount(lineAmount);
		cartProduct.setTaxRate(taxRate);
		return cartProductService.addCartProduct(cartProduct);
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
		<%if(result.isSuccess()) {%>
		<form action="" method="POST">
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
		    			<div style="width:8vh;">
			    			<input id="form1" min="1" max="5" name="quantity" value="1" type="number"
		                        class="form-control" />
	                        </div>
		    			 <button class="btn mt-3" name="addProduct"
   						 style="background-color:#ff6000"><span class="text-white">Add to Cart</span></button>
   					</div>
		    	</div>
		    </form>
		<%} else{%>
			<div class="d-flex justify-content-sm-center align-items-center" style="height: 60vh;">
			    <h3 class="text-danger"><%=result.getMessage() != null ?  result.getMessage() : ""%></h3>
			</div>
		<%} %>
	</div>	
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script>


</body>
</html>