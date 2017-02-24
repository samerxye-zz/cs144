<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title><%= request.getAttribute("title") %></title>
		<link rel="stylesheet" type="text/css" href="./resources/itemServlet.css">
	</head>
	<body>
		<h1 id="itemInterfaceHeading">Item Interface</h1>
		<c:choose>
			<c:when test="${isValidID}">
				${result}
			</c:when>
			<c:otherwise>
				<p>Invalid ID!</p>
			</c:otherwise>
		</c:choose>
	</body>
</html>