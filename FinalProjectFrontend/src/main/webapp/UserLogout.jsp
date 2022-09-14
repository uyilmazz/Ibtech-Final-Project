<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	session.removeAttribute("userName");
 	session.removeAttribute("cartId");
 	response.sendRedirect("MainPage.jsp");
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

</body>
</html>