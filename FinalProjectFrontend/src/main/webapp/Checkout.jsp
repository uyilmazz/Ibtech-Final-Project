<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.shopping.service.CartService,
	com.ibtech.shopping.service.CartProductService,
	com.ibtech.shopping.entities.*,
	java.util.List,
	java.util.ArrayList,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.*,
	com.ibtech.shopping.entities.Cart"%>
<%
	CartService cartService = new CartService();
	CartProductService cartProductService = new CartProductService();
	DataResult<List<CartProduct>> cartProductListResult = null;
	if(session.getAttribute("userName") == null){
		session.setAttribute("backUrl", "./CartView.jsp");
		response.sendRedirect("./UserLogin.jsp");
	}else{
		response.sendRedirect("MainPage.jsp");
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

</body>
</html>