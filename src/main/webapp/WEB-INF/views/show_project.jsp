<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>Project Details</title>
	
	<!-- for Bootstrap CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	
	<!-- YOUR own local CSS -->
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	
	<!-- For any Bootstrap that uses JS -->
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	
	
</head>
<body>
	
	<div class="container-fluid vh-100 mt-5 custom-width  align-items-center">
		<div class="form-group mt-3 mb-4 d-flex justify-content-between align-items-center">
			<h1>Project Details</h1>
			<p><a href="/dashboard">Back to Dashboard</a></p>
        </div> 
        
        <div class="form-group d-flex align-items-center">
			<p class="col-md-3">Project: </p>
			<p>${project.title}</p>
	    </div>
	    <br>
	    
	    <div class="form-group d-flex align-items-center">
			<p class="col-md-3">Description: </p>
			<p>${project.description}</p>
		</div>
		<br>
        
		 <div class="form-group d-flex align-items-center">
			<p class="col-md-3">Due Date: </p>
			<p><fmt:formatDate value="${project.due_date}" pattern="MM/dd/yyyy"/></p>
	    </div>
	    <br>
        
	    <c:set var="isUser" value="${project.lead.id == user.id}" />
		
		<c:choose>
			<c:when test="${isUser}">
				<div class="d-flex justify-content-end">
	        
					<a href="/delete/${project.id}" class="btn btn-danger">
                 		<c:out value="Delete" />
                 	</a>
                
                </div> 
			</c:when>
		</c:choose>
        
		
    </div>
    
    
</body>
</html>