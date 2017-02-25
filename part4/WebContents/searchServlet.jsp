<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title><%= request.getAttribute("title") %></title>
		<link rel="stylesheet" type="text/css" href="./resources/main.css">
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
		<c:choose>
			<c:when test="${isInvalidPage}">
				<h4 id="searchServletMsg">Invalid Page Requested.</h4>
			</c:when>
			<c:when test="${isNoResults}">
				<h4 id="searchServletMsg">Sorry, no more results were found. Try another query!</h4>
			</c:when>
			<c:when test="${isQuery}">
				<div id="searchInterfaceResults">
						<table id="tableOuter">
							<tr id="tableColumnHeadingRow">
								<th class="tableElement">ItemID</th>
								<th class="tableElement">Name</th>
							</tr>
							<c:forEach var="result" items="${searchResult}">
								<tr id="searchResultTableRow${result.getItemId()}">
									<td class="tableElement">${result.getItemId()}</td>
									<td class="tableElement">${result.getName()}</td>
								</tr>
								<script type="text/javascript">
									document.getElementById("searchResultTableRow${result.getItemId()}").onclick = function () {
										window.location.href = "/eBay/item?id="+'${result.getItemId()}';
									}
								</script>
							</c:forEach>
						</table>
						<div id="searchInterfacePageChanger">
							<c:if test="${!isPageOne}">
								<div class="pageChanger"><img id="backPage" src="./resources/back.png" height="42" width="42"></div>
							</c:if>
							<p id="pageNumber">${page}</p>
							<c:if test="${!isPageLast}">
								<div class="pageChanger"><img id="nextPage" src="./resources/next.png" height="42" width="42"></a></div>
							</c:if>
						</div>
						<c:if test="${!isPageOne}">
							<script type="text/javascript">
								document.getElementById("backPage").onclick = function () {
									var page = '${page}';
									--page;
							        window.location.href = "/eBay/search?query="+'${query}'+"&page="+page;
							    };
							</script>
						</c:if>
						<c:if test="${!isPageLast}">
							<script type="text/javascript">
							    document.getElementById("nextPage").onclick = function () {
									var page = '${page}';
									++page;
							        window.location.href = "/eBay/search?query="+'${query}'+"&page="+page;
							    };
							</script>
						</c:if>
					</div>
			</c:when>
			<c:otherwise>
				<h4 class="searchServletMsg">Enter a search query above to begin searching!</h4>
			</c:otherwise>
		</c:choose>
	</body>
</html>