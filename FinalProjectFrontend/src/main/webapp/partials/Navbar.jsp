<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page
	import="com.ibtech.inventory.service.CategoryService,
	com.ibtech.inventory.entities.Category"%>
<%
	boolean isLogin = session.getAttribute("userName") != null ? true : false;
	CategoryService categoryService = new CategoryService();
	List<Category> categories = categoryService.getAll();
%>    

<nav class="navbar bg-light">
  <div class="container-fluid">
    <a class="navbar-brand">Shop Project</a>
    <form class="d-flex" role="search">
      <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success me-2" type="submit">Search</button>
		<% if(isLogin) {%>
			<a href=".../../UserLogout.jsp" class="btn btn-outline-danger me-2">Logout</a>
		<%}else{ %>
			<a href=".../../UserLogin.jsp" class="btn btn-outline-danger me-2">Login</a>
       		<a href=".../../UserRegister.jsp" class="btn btn-outline-danger me-2">Register</a>
		<%} %>  
    </form>
  </div>
</nav>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary  mt-3">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">       
        <%for (int i = 0; i < categories.size(); i += 1) {%>
    	 <li class="nav-item">
          	<a class="nav-link" onclick="" aria-current="page" href="MainPage.jsp?categoryId=<%=categories.get(i).getCategoryId()%>"><%= categories.get(i).getCategoryName() %></a>
        </li>			
	<%}%>
      </ul>
    </div>
  </div>
</nav>