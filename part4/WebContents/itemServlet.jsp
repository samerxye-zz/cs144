<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title><%= request.getAttribute("title") %></title>
		<link rel="stylesheet" type="text/css" href="./resources/main.css">
		<link rel="stylesheet" type="text/css" href="./resources/itemServlet.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="http://stevenlevithan.com/assets/misc/date.format.js"></script>
		<script src="http://underscorejs.org/underscore-min.js"></script>
	</head>
	<body>
		<h1 id="itemInterfaceHeading">Item Interface</h1>
		<c:choose>
			<c:when test="${isValidID}">
				<div id="itemDisplayDiv">
					<table class="tableOuter">
						<tr class="tableColumnHeadingRow">
							<th class="tableElement">Attribute</th>
							<th class="tableElement">Value</th>
						</tr>
						<tr>
							<td class="tableElement">ItemID</td>
							<td class="tableElement" id="ItemID"></td>
						</tr>
						<tr>
							<td class="tableElement">Name</td>
							<td class="tableElement" id="Name"></td>
						</tr>
						<tr>
							<td class="tableElement">Currently</td>
							<td class="tableElement" id="Currently"></td>
						</tr>
						<tr id="Buy_Price_Row">
							<td class="tableElement">Buy Price</td>
							<td class="tableElement" id="Buy_Price"></td>
						</tr>
						<tr>
							<td class="tableElement">First Bid</td>
							<td class="tableElement" id="First_Bid"></td>
						</tr>
						<tr>
							<td class="tableElement">Country</td>
							<td class="tableElement" id="Country"></td>
						</tr>
						<tr>
							<td class="tableElement">Started</td>
							<td class="tableElement" id="Started"></td>
						</tr>
						<tr>
							<td class="tableElement">Ends</td>
							<td class="tableElement" id="Ends"></td>
						</tr>
						<tr>
							<td class="tableElement">UserID</td>
							<td class="tableElement" id="UserID"></td>
						</tr>
						<tr>
							<td class="tableElement">Rating</td>
							<td class="tableElement" id="Rating"></td>
						</tr>
						<tr>
							<td class="tableElement">Description</td>
							<td class="tableElement" id="Description"></td>
						</tr>
					</table>
				</div>
				<div id="categoryDisplay">
					
				</div>
				<div id="locationDisplay">
					<h1 id="locationDisplayHeading">Location</h1>
					<table>
						<tr class="tableColumnHeadingRow">
							<th class="tableElement">Attribute</th>
							<th class="tableElement">Value</th>
						</tr>
						<tr>
							<td class="tableElement">Location</td>
							<td class="tableElement" id="Location"></td>
						</tr>
						<tr id="LatitudeRow">
							<td class="tableElement">Latitude</td>
							<td class="tableElement" id="Latitude"></td>
						</tr>
						<tr id="LongitudeRow">
							<td class="tableElement">Longitude</td>
							<td class="tableElement" id="Longitude"></td>
						</tr>
					</table>
				</div>
				<div id="bidsDisplay">
					<h1 id="bidsDisplayHeading">Bids</h3>
					<h3 id="numberOfBidsText">Number of Bids: <p id="numberOfBidsReplace"></p></h1>
					<p id="topBidSchema">->Top Bid<-</p>
					<div id="bidsDisplayInnerDiv"></div>
				</div>
				<script type="text/javascript">
					var timeFormatOptions = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric' };
					var xmlDoc = $.parseXML(`${result}`);
					var $xml = $(xmlDoc);

					var name = $xml.find("Name").text();
					$("td#Name").text(name);

					var itemId = $xml.find("Item").attr("ItemID");
					$("td#ItemID").text(itemId);

					//category
					var categories = new Array();
					var $categories = $xml.find("Category").each(function() {
						categories.push($(this).text());
					});
					var categorySkelHTML = `<table><tr class="tableColumnHeadingRow"><th class="tableElement">Category</th></tr>`;
					$.each(categories, function(index, value) {
						categorySkelHTML+=`<tr><td class="tableElement">`+value+`</td></tr>`;
					});
					categorySkelHTML+=`</table>`;
					$("div#categoryDisplay").html(categorySkelHTML);

					var currently = $xml.find("Currently").text();
					$("td#Currently").text(currently);

					var $buy_price = $xml.find("Buy_Price");
					if($buy_price.length !== 0) {
						//exists
						$("td#Buy_Price").text($buy_price.text());
					} else {
						//does not exist
						$("tr#Buy_Price_Row").hide();
					}

					var first_bid = $xml.find("First_Bid").text();
					$("td#First_Bid").text(first_bid);

					var number_of_bids = $xml.find("Number_of_Bids").text();
					$("p#numberOfBidsReplace").text(number_of_bids);

					var $bids = $xml.find("Bids");
					//bid
					if(number_of_bids === "0") {
						$("div#bidsDisplayInnerDiv").html("This item has no bids!");
					} else {
						var bidsSkelHTML = `<div class="tableColumnHeadingRow bid" id="bidsTableHeadingRow"><span id="bidsAmountColumn">Amount</span><span id="bidsUserIDRatingColumn">UserID/Rating</span><span id="bidsTimeColumn">Time</span><span id="bidsLocationCountryColumn">Location/Country</span></div>`;
						var arrayof$bid = new Array();
						var $bid = $bids.find("Bid").each(function() {
							arrayof$bid.push($(this));
						});
						arrayof$bid.sort(function(a,b){
							var bAmount = b.find("Amount").text().substr(1);
							var aAmount = a.find("Amount").text().substr(1);
							return bAmount - aAmount;
						});
						$.each(arrayof$bid, function(index, value) {
							var time = value.find("Time").text();
							if(index === 0) {
								bidsSkelHTML += `<div class="bid" id="topBid"><div class="bidAmount">`+value.find("Amount").text()+`</div><div class="bidUserIDRating"><div class="bidUserID">`+value.find("Bidder").attr("UserID")+`</div><div class="bidRating">`+value.find("Bidder").attr("Rating")+`</div></div><div class="bidTime">`+(new Date(Date.parse(time))).toLocaleDateString("en-US", timeFormatOptions)+`</div><div class="bidLocCountry"><div class="bidLocation">`+value.find("Location").text()+`</div><div class="bidCountry">`+value.find("Country").text()+`</div></div></div>`;
							} else {
								bidsSkelHTML += `<div class="bid"><div class="bidAmount">`+value.find("Amount").text()+`</div><div class="bidUserIDRating"><div class="bidUserID">`+value.find("Bidder").attr("UserID")+`</div><div class="bidRating">`+value.find("Bidder").attr("Rating")+`</div></div><div class="bidTime">`+(new Date(Date.parse(time))).toLocaleDateString("en-US", timeFormatOptions)+`</div><div class="bidLocCountry"><div class="bidLocation">`+value.find("Location").text()+`</div><div class="bidCountry">`+value.find("Country").text()+`</div></div></div>`;
							}
						});
						$("div#bidsDisplayInnerDiv").html(bidsSkelHTML);
						$("div#topBid").on("click", function () {
							$("p#topBidSchema").slideToggle( "fast", function() {
								// Animation complete.
							});
						});
					}

					var $loc = $xml.find("Location");
					var loc = $loc.text();
					$("td#Location").text(loc);
					var latitude = $loc.attr("Latitude");
					if(!_.isUndefined(latitude)) {
						$("td#Latitude").text(latitude);
						var longitude = $loc.attr("Longitude");
						$("td#Longitude").text(longitude);
					}
					else {
						$("tr#LongitudeRow").hide();
						$("tr#LatitudeRow").hide();
					}

					var country = $xml.find("Country").text();
					$("td#Country").text(country);

					var starts = $xml.find("Started").text();
					var startsFormatted = (new Date(Date.parse(starts))).toLocaleDateString("en-US", timeFormatOptions);
					$("td#Started").text(startsFormatted);
					var ends = $xml.find("Ends").text();
					var endsFormatted =(new Date(Date.parse(ends))).toLocaleDateString("en-US", timeFormatOptions);
					$("td#Ends").text(endsFormatted);

					var $Seller = $xml.find("Seller");
					var userId = $Seller.attr("UserID");
					$("td#UserID").text(userId);
					var rating = $Seller.attr("Rating");
					$("td#Rating").text(rating);

					var description = $xml.find("Description").text();
					$("td#Description").text(description);
				</script>
			</c:when>
			<c:otherwise>
				<p>Invalid ID!</p>
			</c:otherwise>
		</c:choose>
	</body>
</html>