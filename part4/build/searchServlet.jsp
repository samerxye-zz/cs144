<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title><%= request.getAttribute("title") %></title>
		<link rel="stylesheet" type="text/css" href="./resources/searchServlet.css">
	</head>
	<body>
		<h1 id="searchInterfaceHeading">Search Interface</h1>
		<div id="queryForm">
			<form>
				<p id="inputQueryText">Query: </p>
				<input type="text" name="query" value="${query}">
				<input type="submit" value="Submit">
			</form>
		</div>
		<c:if test="${isQuery}">
		    <div id="searchInterfaceResults">
				<table id="tableOuter">
					<tr>
						<th class="tableElement">ItemID</th>
						<th class="tableElement">Name</th>
					</tr>
					<c:forEach var="result" items="${searchResult}">
						<tr>
							<td class="tableElement">${result.getItemId()}</td>
							<td class="tableElement">${result.getName()}</td>
						</tr>
					</c:forEach>
				</table>
				<div id="searchInterfacePageChanger">
					<div class="pageChanger"><img id="backPage" src="./resources/back.png" height="42" width="42"></div>
					<p id="pageNumber">${page}</p>
					<div class="pageChanger"><img id="nextPage" src="./resources/next.png" height="42" width="42"></a></div>
				</div>
				<script type="text/javascript">
					document.getElementById("backPage").onclick = function () {
						var page = '${page}';
						--page;
				        window.location.href = "/eBay/search?query="+'${query}'+"&page="+page;
				    };
				    document.getElementById("nextPage").onclick = function () {
						var page = '${page}';
						++page;
				        window.location.href = "/eBay/search?query="+'${query}'+"&page="+page;
				    };
				</script>
			</div>
		</c:if>
	</body>
</html>