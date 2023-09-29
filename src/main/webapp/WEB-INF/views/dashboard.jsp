<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<title>Read Share</title>
	
	<!-- for Bootstrap CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
	
	<!-- YOUR own local CSS -->
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	
	<!-- For any Bootstrap that uses JS -->
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	
	 		
	
</head>
<body>
	
	<div class="container-fluid vh-100 mt-5 custom-width align-items-center">
		<div class="form-group mt-3 d-flex justify-content-between">
			<div>
				<h1>Welcome, ${user.userName} !</h1>
				
			</div>
			<div>
				<p><a href="/logout">logout</a></p>
				
			</div> 
		</div>
		
		<div class="form-group mt-3 d-flex justify-content-between">
		<div>
			<h4>All Projects</h4>
			
		</div>
		<div>
			<p><a href="/projects/new">+ new project</a></p>
			
		</div> 
	</div>
		
		
		
		<table class="table">
	    <thead>
	        <tr>
	            <th>Project</th>
	            <th>Team Lead</th>
	            <th>Due Date</th>
	            <th>Actions</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="oneProject" items="${projectsUserNotIn}">
	    		<tr>
	    			<td><a href="/projects/${oneProject.id}"><c:out value="${oneProject.title}"></c:out></a></td>
	    			<td><c:out value="${oneProject.lead.userName}"></c:out></td>
	    			<td><fmt:formatDate value="${oneProject.due_date}" pattern="MMMM dd"/></td>
	    			<td><a href="/projects/join/${oneProject.id}/${user.id}">Join team</a></td>
	    			
	    		</tr>
	    	</c:forEach>
	    </tbody>
	    </table>
	    
	    <h4>Your Projects</h4>
		
		<table class="table">
	    <thead>
	        <tr>
	            <th>Project</th>
	            <th>Team Lead</th>
	            <th>Due Date</th>
	            <th>Actions</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="oneProject" items="${projectsUserIn}">
	    		<tr>
	    			<td><a href="/projects/${oneProject.id}"><c:out value="${oneProject.title}"></c:out></a></td>
	    			<td><c:out value="${oneProject.lead.userName}"></c:out></td>
	    			<td><fmt:formatDate value="${oneProject.due_date}" pattern="MMMM dd"/></td>
	  
	    			<td>
	    			<c:set var="isUser" value="${oneProject.lead.id == user.id}" />
	    			
	    			<c:choose>
	        			<c:when test="${isUser}">
	        				<a href="/projects/edit/${oneProject.id}">edit</a>
	        			</c:when>
	        			<c:otherwise>
	        				<a href="/projects/leave/${oneProject.id}/${user.id}">Leave team</a>
	        			</c:otherwise>
	        		</c:choose>
	    			</td>
	    			
	    		</tr>
	    	</c:forEach>
	    </tbody>
	    </table>


	</div>
    
</body>
</html>