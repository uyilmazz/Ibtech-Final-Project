<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page
	import="
	com.ibtech.shopping.service.ProvinceService,
	com.ibtech.shopping.service.AddressService,
	com.ibtech.shopping.entities.Province,
	com.ibtech.shopping.entities.Address,
	java.util.ArrayList,
	java.util.List,
	com.ibtech.core.utilities.helper.ParseHelper,
	com.ibtech.core.utilities.result.*,
	com.ibtech.shopping.entities.Cart"
%>

<%
	ProvinceService provinceService = new ProvinceService();
	AddressService addressService = new AddressService();
	List<Province> provinceList = new ArrayList<>();
	List<Address> addressList = new ArrayList<>();
	DataResult<List<Address>> addressResult = null;
 	DataResult<List<Province>> provinceResult = provinceService.getAll();
 	Result addAddressResult = null;	
 	if(session.getAttribute("userName") == null){
 		session.setAttribute("backUrl", "CheckoutAddress.jsp");
 		response.sendRedirect("UserLogin.jsp");
 	}else{	
 		String userName = session.getAttribute("userName").toString();
 		if(request.getParameter("addAddress") != null){
 			String addressLine1 = request.getParameter("addressLine1");
 			String addressLine2 = request.getParameter("addressLine2");
 			int provinceId = Integer.parseInt(request.getParameter("provinceId"));
 			DataResult<Province> getProvinceResult = provinceService.getById(provinceId);
 			if(getProvinceResult.isSuccess()){
 				Address newAddress = new Address(0,addressLine1,addressLine2,userName);
 	 			newAddress.setProvince(getProvinceResult.getData());
 	 			addAddressResult = addressService.createAddress(newAddress);
 	 			response.sendRedirect("CheckoutAddress.jsp");
 			}
 		}else{
 			addressResult = addressService.getAllByUserName(userName);
 	 		if(provinceResult.isSuccess()){
 	 			provinceList = provinceResult.getData();	
 	 		}
 	 		if(addressResult.isSuccess()){
 	 			addressList = addressResult.getData();
 	 		}
 		}
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
			<section class="h-100 h-custom" style="background-color: #eee;">
			<div class="py-5 h-100">
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col">
						<div class="card">
							<div class="card-body p-4">
									<div class="row">
									<div class="col-lg-6">
										<h5 class="mb-3">
											<a href="MainPage.jsp" class="text-body"><i
												class="fas fa-long-arrow-alt-left me-2"></i>Continue shopping</a>
										</h5>
										<div class="d-flex justify-content-between align-items-center mb-4">
											<div>
												<p class="mb-1">Please select an address for your order.</p>
											</div>
										</div>
										<%if(addressResult == null){ %>
										<%} else if(addressResult.isSuccess()){ %>
											<%if(addressList.size() > 0) {%>
												<%for(int i = 0; i < addressList.size(); i++){ %>
													<div class="card mb-3">
														<div class="card-body">
															<div class="">
																<div>
																	<div>
																		<h5 class="mb-0"><span class="text-primary">City : </span><%=addressList.get(i).getProvince().getName()%></h5>
																	</div>
																</div>											
																<div>
																	<div>
																		<h5 class="mb-0"><span class="text-primary">Address Line 1 : </span><%=addressList.get(i).getAddressLine1()%></h5>
																	</div>
																</div>
																<div>
																	<div>
																		<h5 class="mb-0"><span class="text-primary">Address Line 2 : </span><%=addressList.get(i).getAddressLine2()%></h5>
																	</div>
																</div>
																<div class="d-flex align-items-end flex-column">
																	<a type="submit" href="CheckoutComplete.jsp?addressId=<%=addressList.get(i).getId()%>"
																	class="btn btn-warning btn-block btn-lg ">
																		<div class="d-flex justify-content-between">
																			<span>Complete Order <i
																				class="fas fa-long-arrow-alt-right ms-2"></i></span>
																		</div>
																	</a>
																</div>
															</div>
														</div>
													</div>
												<%} %>
											<%}else{ %>
												<div class="d-flex justify-content-sm-center align-items-center" style="height: 30vh;">
												    <h3 class="text-danger text-center">You do not have an address yet. Please add an address to place an order.</h3>
												</div>
											<%} %>
										<% }else {%> 
											<div class="d-flex justify-content-sm-center align-items-center" style="height: 30vh;">
											    <h3 class="text-danger"><%=addressResult.getMessage() != null ?  addressResult.getMessage() : ""%></h3>
											</div>
										<%} %>
									</div>
									<div class="col-lg-6">
									<form action="" method="POST">
										<div class="card bg-primary text-white rounded-3">
											<div class="card-body">
												<div
													class="d-flex justify-content-between align-items-center mb-4">
													<h5 class="mb-0">New Address</h5>
												</div>
												<%if(addAddressResult != null && !addAddressResult.isSuccess()){ %>
													<p class="text-white mb-5 text-center"><%= addAddressResult.getMessage() != null ? addressResult.getMessage() : "" %></p>
												<%} %>
												<hr class="my-4">
												       <div class="col-md-6 mb-3">
											                <label for="country">Province</label>
											                <select name="provinceId" class="custom-select d-block w-100 form-control" id="country" required>
											      				<%for(int i = 0; i < provinceList.size(); i++){ %>
											      					<option value="<%=provinceList.get(i).getId()%>"><%=provinceList.get(i).getName() %></option>
											      				<%} %>
											                </select>
											                <div class="invalid-feedback">
											                  Please select a valid province.
											                </div>
											              </div>
												<div class="form-outline form-white mb-4">
													<label class="form-label" for="addressLine1">Address Line 1</label>
													<input type="text" id="addressLine1" name="addressLine1" value="" minlength="15" required
														class="form-control form-control-lg" />
												</div>
												<div class="form-outline form-white mb-4">
													<label class="form-label" for="addressLine2">Address Line 2</label>
													<input type="text" id="addressLine2" name="addressLine2" 
														class="form-control form-control-lg" />
												</div>
												<button type="submit" name="addAddress"
												class="btn btn-info btn-block btn-lg">
													<div class="d-flex justify-content-between">
														<span>Add Address <i
															class="fas fa-long-arrow-alt-right ms-2"></i></span>
													</div>
												</button>
											</div>
										</div>
									</form>
									</div>
								</div>					
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
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