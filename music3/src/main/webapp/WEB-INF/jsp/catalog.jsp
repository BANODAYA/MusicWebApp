<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  
  <head>
    
    <title>Catalog -- MusicStore</title>
    
  </head>
  
  <body>
    
	<h1>Catalog</h1>
	<h2>To get more information, Choose one of the following product</h2>

	<ol>
		<c:forEach items="${products}" var="product">
			<c:url value="product.html" var="productUrl">
			<c:param name="productCode" value="${product.code}" />
			</c:url>
			<li><a href="${productUrl}">${product.description} </a></li>
		</c:forEach>
	</ol>



	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<c:url var="cartURL" value="cart.html" />
	<ul>
		<li><a href="${userWelcomeUrl}">User Home </a></li>
		<li><a HREF="${cartURL}"> Cart View</a></li>
	</ul>

</body>
</html>
