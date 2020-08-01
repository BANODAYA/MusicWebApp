<!DOCTYPE HTML>

<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  
  <head>
    
    <title>Music Store :: Listen</title>
    
  </head>
  
  <body>
    
	<h1>Listen to Tracks from ${product.description}</h1>
	<h2>Choose an mp3 to listen to.</h2>


	<table>
		<tr>
			<td><b>Track Title</b></td>
			<td><b>Click to Listen</b></td>
		</tr>
		<c:forEach items="${tracks}" var="track">
			<tr>
				<td><b>${track.title}</b></td>
				<td>
				  <audio controls>
				    <source src="/sound/${product.code}/${track.sampleFilename}" />
				  </audio>
				</td>
			</tr>
		</c:forEach>
	</table>
	<c:url var="productUrl" value="product.html">
	</c:url>
	<c:url var="addToCartUrl" value="cart.html">
		<c:param name="addItem" value="true" />
	</c:url>
	<a href="${addToCartUrl}">Add this CD to Cart</a>

	<c:url var="catalogUrl" value="catalog.html" />
	<c:url var="cartUrl" value="cart.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />

	<ul>
		<li><a HREF="${productUrl}">Back to Product Page</a></li>
		<li><a href="${catalogUrl}">Browse Catalog </a></li>
		<li><a HREF="${cartUrl}">View Cart</a></li>
		<li><a href="${userWelcomeUrl}">User Home </a></li>
	</UL>

  </body>
  
</html>
