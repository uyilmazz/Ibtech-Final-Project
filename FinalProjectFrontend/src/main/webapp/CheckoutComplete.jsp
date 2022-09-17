<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page
	import="
	com.ibtech.shopping.service.OrderService,
	com.ibtech.shopping.service.AddressService,
	com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.entities.Order,
	com.ibtech.shopping.entities.Address,
	java.util.ArrayList,
	java.util.List,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.*,
	com.ibtech.shopping.entities.Cart"
%>

<%
	String message = "";
	Result createdOrderResult = null;
	AddressService addressService = new AddressService();
	OrderService orderService = new OrderService();
	CartService cartService = new CartService();
	
	if(session.getAttribute("userName") != null){
		if(session.getAttribute("cartId") != null && ParseHelper.isLong(session.getAttribute("cartId").toString())){
			long cartId =(long) session.getAttribute("cartId");
			DataResult<Cart> cartResult = cartService.getById(cartId);
			if(cartResult.isSuccess() && cartResult.getData().getTotalAmount() > 0){
				if(request.getParameter("addressId") != null && ParseHelper.isInteger(request.getParameter("addressId"))){
					long addressId = Long.parseLong(request.getParameter("addressId"));
					DataResult<Address> addressResult = addressService.getById(addressId);
					if(addressResult.isSuccess()){
						Address address = addressResult.getData();
						String userName = session.getAttribute("userName").toString();
						Order order = new Order(0,address.getAddressLine1(),address.getAddressLine2(),userName,0);
						createdOrderResult = orderService.createOrder(order);
						if(createdOrderResult.isSuccess()){
							
						}else{

						}
					}else{
						response.sendRedirect("CheckoutAddress.jsp");
					}
				}else{
					response.sendRedirect("CheckoutAddress.jsp");
				}	
			}else{
				response.sendRedirect("MainPage.jsp");	
			}
		}else{
			response.sendRedirect("CartView.jsp");
		}
	}else{
		session.setAttribute("backUrl", "CheckoutAddress.jsp");
 		response.sendRedirect("UserLogin.jsp");
	}
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
	crossorigin="anonymous">

<link href="css/cart.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container mt-3">
	<jsp:include page="partials/Navbar.jsp" />
    <div style="position: relative;">
        <div class="mt-5 mb-5 p-5 ">
            <div class="text-center text-success">
                <img class="img-responsive cc-img" src="./images/success.png" height="200">
                <h1><strong>Payment Successful</strong></h1>
                <h5>Thanks for Shopping!</h5>
            </div>
            <div class="container text-center">
                <div class="mt-3" style="width: 450px; margin:auto">
                    <ul class="list-group">
                    </ul>
                </div><br>
                <a href="MainPage.jsp" class="btn btn-success">Continue shopping</a>
            </div>
        </div>
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