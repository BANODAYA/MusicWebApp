<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  
  <head>
    
    <title>Product Details -- MusicStore</title>
    
  </head>
  
  <body>
    
	<h1>Product Details</h1>
	<h2>${product.description}</h2>

	<c:url var="listenURL" value="${listenNextUrl}" />
	<c:url var="addToCartUrl" value="cart.html">
		<c:param name="addItem" value="true" />
	</c:url>

	<ul>
		<li>Product Code: ${product.code}</li>
		<li>Price: ${product.price}</li>
		<li><a href="${listenURL}">Listen to samples</a></li>
		<li><a href="${addToCartUrl}">Add to Cart</a></li>
	</ul>

	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="cartUrl" value="cart.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<ul>
		<li><a href="${catalogURL}">Browse Catalog </a></li>
		<li><a HREF="${cartURL}">View Cart</a></li>
		<li><a href="${userWelcomeUrl}">User Home </a></li>
	</ul>

  </body>
  
</html>
