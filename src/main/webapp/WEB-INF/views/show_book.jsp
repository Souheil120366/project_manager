<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
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
	
	<div class="container-fluid vh-100 mt-5 custom-width  align-items-center">
		<div class="form-group mt-3 d-flex justify-content-between align-items-center">
			<h1>${book.title}</h1>
			<p><a href="/books">back to the shelves</a></p>
        </div> 
        <c:set var="isUser" value="${book.user.id == user.id}" />
        
        <c:choose>
    		<c:when test="${isUser}">
    			<span>You read </span>
    			<span><c:out value="${book.title}"/> by </span>
    			<span><c:out value="${book.author}"/>.</span>
    			<p>Here are your thoughts:</p>
    		</c:when>
    		<c:otherwise>
    			<span><c:out value="${book.user.userName}"/> read </span>
    			<span><c:out value="${book.title}"/> by </span>
    			<span><c:out value="${book.author}"/>.</span>
    			<p>Here are <c:out value="${book.user.userName}"/>'s thoughts:</p>
            </c:otherwise>
        </c:choose>
        
        
        <hr class="my-4">
        <p><c:out value="${book.my_thoughts}"/></p>
        <hr class="my-4"> 
        
        
        <div class="d-flex justify-content-end">
        	
        	<c:set var="bookId" value="${book.id}" />
        	
        
            <c:choose>
            	<c:when test="${isUser}">
            		<a href="/books/${bookId}/edit" class="btn btn-primary">
            			<c:out value="Edit" />
            		</a>
                	<p>&nbsp;&nbsp;</p>
                	<a href="/delete/${bookId}" class="btn btn-danger">
                    	<c:out value="Delete" />
                    </a>
                </c:when>
            </c:choose>
        </div> 
		
    </div>
    
    
</body>
</html>