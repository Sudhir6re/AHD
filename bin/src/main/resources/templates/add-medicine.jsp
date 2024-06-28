<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Add Stock |</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="/JS/jquery-validate.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
  <script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <style>
    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {height: 1500px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height: auto;} 
    }
  </style>
</head>
<body>

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <h4>Sahara Medicals</h4>
      <ul class="nav nav-pills nav-stacked">
        <li><a href='<c:url value="/home"></c:url>'>Home</a></li>
        <li><a href='<c:url value="/addMedicine"></c:url>'>Add Medicine</a></li>
      </ul><br>
    </div>

    <div class="col-sm-9">
      <h4><small>Medical Store Stock & Inventory Management System</small></h4>
      <hr>
      <h3>Add New Medicine</h3>
      
      	<div class="container">
		
		<c:url var="addMedicineVar" value="/saveMedicine" />
		<div class="container">
		  <form:form method="POST" action="${addMedicineVar}" modelAttribute="medicineModelAttribute" id="addMedicineForm" class="form-horizontal">
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="medtype">Select Medicine Type:</label>
		      <div class="col-sm-10" style="width:20%">
		        <form:select class="form-control" path="medicineType" id="medicineType" name="medicineType">
					<option value="">---- Please Select ----</option>
					<c:forEach items = "${lstMedicineType}" var="medTypeVar" >
						<option value="${medTypeVar.id}" >${medTypeVar.medicinetypenamefullname}</option>
					</c:forEach>
				</form:select>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="prdname">Name Of Medicine:</label>
		      <div class="col-sm-10">
		        <form:input id="medicineName" path="medicineName" type="text"/>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="rate">Medicine Rate:</label>
		      <div class="col-sm-10">          
		        <form:input id="medicineRate" path="medicineRate" type="text"></form:input>
		      </div>
		    </div>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="quantity">Medicine Quantity:</label>
		      <div class="col-sm-10">          
		        <form:input id="medicineQuantity" path="medicineQuantity" type="text"></form:input>
		      </div>
		    </div>
		    <div class="form-group">        
		      <div class="col-sm-offset-2 col-sm-10">
		        <button type="submit" class="btn btn-default">Save</button>
		        <button type="button" class="btn btn-default" id="btnBack">Back</button>
		      </div>
		    </div>
		  </form:form>
	</div>

	<c:url var="goBack" value="/home"/>
	<form:form method="post" action="${goBack}" id="backForm"> 
	</form:form> 
	
	<script type="text/javascript" src="<c:url value="/JS/add-medicine.js" />"></script>
		
		</div>
      <br><br>
    </div>
  </div>
</div>

<footer class="container-fluid">
  <p>Footer Text</p>
</footer>

</body>
</html>
