<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.ibtech.core.utilities.helper.ParseHelper"%>
<%
	int sideBarId = 0;
	if(session.getAttribute("sideBarId") != null && ParseHelper.isInteger(session.getAttribute("sideBarId").toString())){
		sideBarId = (int) session.getAttribute("sideBarId");
	}

%>

<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3 sidebar-sticky">
        <ul class="nav flex-column">
        <li class="nav-item">
                <a href="../main/AdminPage.jsp" class="nav-link <%=sideBarId == 0 ? "active" : ""%>" >
                    Home
                </a>
            </li>
            <li class="nav-item">
                <a href="../product/ProductSummary.jsp" class="nav-link <%=sideBarId == 1 ? "active" : ""%>"">
                    Products
                </a>
            </li>
            <li class="nav-item">
                <a href="../category/CategorySummary.jsp" class="nav-link <%=sideBarId == 2 ? "active" : ""%>"">
                    Categories
                </a>
            </li>
             <li class="nav-item">
                <a href="../cart/CartSummary.jsp" class="nav-link <%=sideBarId == 3 ? "active" : ""%>"">
                    Cart Summaries
                </a>
            </li>
            <li class="nav-item">
                <a href="../user/UserSummary.jsp" class="nav-link <%=sideBarId == 4 ? "active" : ""%>"">
                    Users
                </a>
            </li>
            <li class="nav-item">
                <a href="../order/OrderSummary.jsp" class="nav-link <%=sideBarId == 5 ? "active" : ""%>"">
                    Orders
                </a>
            </li>
        </ul>
    </div>
</nav>