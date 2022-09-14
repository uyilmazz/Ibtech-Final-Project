<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.service.CartProductService,
	com.ibtech.shopping.entities.Cart,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.DataResult,
	java.util.List,
	com.ibtech.shopping.entities.Cart"%>
<%
	if(session.getAttribute("cartId") != null && ParseHelper.isLong(session.getAttribute("cartId").toString())){
		if(request.getParameter("cartProductId") != null && ParseHelper.isLong(request.getParameter("cartProductId"))){
			long cartProductId = Long.parseLong(request.getParameter("cartProductId"));
			CartProductService cartProductService = new CartProductService();
			cartProductService.removeCartProduct(cartProductId);
		}
	}
	response.sendRedirect("./CartView.jsp");
%>      
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body>

</body>
</html>