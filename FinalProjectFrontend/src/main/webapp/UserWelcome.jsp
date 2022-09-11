<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userName = "";
	if(session.getAttribute("userName") != null){
		userName = (String) session.getAttribute("userName");
	}else{
		response.sendRedirect("UserLogin.jsp");
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Welcome <%= userName %><br><br>
	<a href="LogoutWithDB.jsp">Çık</a>
</body>
</html>