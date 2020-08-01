<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  
  <head>
    
    <title> Cart -- MusicStore</title>
    
  </head>
  
  <body>
    
	<h1>Music Cart</h1>

	<ul>
		<c:forEach items="${cart.items}" var="item">
			<li>Product Id: ${item.productId}</li>
			<li>Product Quantity: ${item.quantity}</li>
		</c:forEach>
	</ul>

	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="checkoutURL" value="checkout.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<ul>
		<li><a href="${checkoutURL}">Checkout</a></li>
		<li><a href="${catalogURL}"> Catalog Browsing</a></li>
		<li><a href="${userWelcomeUrl}">User Home </a></li>
	</ul>

  </body>
  
</html>
