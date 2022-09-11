<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
	import="com.ibtech.inventory.business.concretes.ProductManager,
	com.ibtech.inventory.repository.ProductRepository,
	com.ibtech.inventory.entities.Category,com.ibtech.inventory.entities.Product"%>
<%
	long productId = Long.parseLong(request.getParameter("productId"));
	ProductManager productManager = new ProductManager(new ProductRepository());
	Product product = productManager.getById(productId).getData();
%>  
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="../../css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="../../css/navbar.css" />
</head>
<body>
	
  <jsp:include page="../partials/Navbar.jsp" />
    <div class="row mt-2">
      <jsp:include page="../partials/SideBar.jsp" />
      <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
		  
		  <div class="content w-50 mx-auto h-100">
    <div class="col-md-12 mt-4">
        <div class="card my-auto">
            <div class="card-header">
                <h5 class="title">Product Detail</h5>
            </div>
            <div class="card-body">
                <form>

                    <div class="mb-3">
                        <label for="productId">Product Id</label>
                        <div class="form-group">
                            <input type="text" value="<%= product.getProductId()%>" class="form-control" placeholder="Product Id"
                                id="productId" disabled/>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="productName">Product Name</label>
                        <div class="form-group">
                            <input type="text" value="<%= product.getProductName()%>" class="form-control" placeholder="Product Name" 
                                id="productName" disabled />
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="salesPrice">Sales Price</label>
                        <div class="form-group">
                            <input type="text" value="<%= product.getSalesPrice()%>" class="form-control" placeholder="Sales Price" 
                                id="salesPrice" disabled />
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="imagePath">Image Path</label>
                        <div class="form-group">
                            <input type="text" value="<%= product.getImagePath()%>" class="form-control" placeholder="Image Path" 
                                id="imagePath" disabled />
                        </div>
                    </div>
                    
                      <div class="mb-3">
                        <label for="categoryName">Category</label>
                        <div class="form-group">
                            <input type="text" value="<%= product.getCategory().getCategoryName()%>" class="form-control" placeholder="Category" 
                                id="categoryName" disabled />
                        </div>
                    </div>
                    
                </form>
            </div>
        </div> 
    </div>
</div>
		  
		  
      </main>
    </div>
  </div>
  
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</html>