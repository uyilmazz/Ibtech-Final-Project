<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page
	import="com.ibtech.inventory.service.CategoryService,com.ibtech.inventory.entities.Category,com.ibtech.core.utilities.result.DataResult"%>
<%
	int categoryId = -2;
	if(request.getParameter("categoryId") != null){
		categoryId = Integer.parseInt(request.getParameter("categoryId"));
	}
	List<Category> categories = new ArrayList<>();
	boolean isLogin = session.getAttribute("userName") != null ? true : false;
	CategoryService categoryService = new CategoryService();
	DataResult<List<Category>> result = categoryService.getAll();
	if(result.isSuccess()){
		categories = result.getData();
	}
%>    

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a href=".../../MainPage.jsp?categoryId=-1" class="navbar-brand">Shop Project</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0"></ul>
            <form class="d-flex" >
		      <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
		       <button class="btn btn-outline-success me-2" type="submit" disabled>Search</button>
		      <div class="text-nowrap d-flex">
				  <a href=".../../CartView.jsp" class="btn me-2 align-middle text-white" style="background-color:#FF6000"><b><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
				  <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
				</svg> My Shopping Cart</b></a>
			  </div>
		    </form>
				<% if(isLogin) {%>
				<ul class="navbar-nav">
	                <li class="nav-item dropdown">
	                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
	                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                        <img src=".../../images/profile.png" width="40" height="40" class="rounded-circle">
	                    </a>
	                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
	                        <a class="dropdown-item" href="#">Profile</a>
	                        <a class="dropdown-item" href=".../../CustomerOrder.jsp">Orders</a>
	                        <a class="dropdown-item" href=".../../UserLogout.jsp">Log Out</a>
	                    </div>
	                </li>
	            </ul>
				<%}else{ %>
					<a href=".../../UserLogin.jsp" class="btn btn-outline-danger me-2">Login</a>
		       		<a href=".../../UserRegister.jsp" class="btn btn-outline-danger me-2">Register</a>
				<%} %>     
        </div>
    </div>
</nav>
    
<%if(result.isSuccess()){ %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary  mt-3">
  <div class="container-fluid">
  	 <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
     		<li class="nav-item">
	          	<a class="nav-link" style="color:<%=categoryId == -1 ? "#FF6000" : "#FFFFFF" %>" aria-current="page" href="MainPage.jsp?categoryId=-1">Home</a>
	        </li>	  
        <%for (int i = 0; i < categories.size(); i += 1) {%>
	    	 <li class="nav-item">
	          	<a class="nav-link" style="color:<%=categoryId == categories.get(i).getCategoryId() ? "#FF6000" : "#FFFFFF" %>" aria-current="page" href="MainPage.jsp?categoryId=<%=categories.get(i).getCategoryId()%>"><%= categories.get(i).getCategoryName() %></a>
	        </li>			
		<%}%>
      </ul>
    </div>
  </div>
</nav>
 <%} %>