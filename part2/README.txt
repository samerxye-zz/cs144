Solutions for Part B:

1) Tables

___________________________________________

Table Name: Item
Primary Key: Item
Attributes:
	ItemID
	UserID
	Name
	Currently
	Buy_Price
	First_Bid
	Number_of_Bids
	Location
	Latitude
	Longitude
	Country
	Started
	Ends
	Description
___________________________________________
___________________________________________

Table Name: Bidder
Primary Key: UserID
Attributes:
	UserID
	Rating
	Location
	Country
___________________________________________
___________________________________________

Table Name: Bid
Primary Key: ItemID, UserID, Time
Attributes:
	ItemID
	UserID
	Time
	Amount
___________________________________________
___________________________________________

Table Name: Seller
Primary Key: UserID
Attributes:
	UserID
	Rating
___________________________________________
___________________________________________

Table Name: Category
Primary Key: Category, ItemID
Attributes:
	Category
	ItemID
___________________________________________



2) non-trivial FDs

___________________________________________

Table Name: Item
FDs:
	ItemID -> SellerID, Name, Currently
, Buy_price, First_bid, Number_of_Bids, Loc
ation, Latitude, Longitude, Country, Starte
d, Ends, Description
___________________________________________
___________________________________________

Table Name: Bidder
FDs:
	UserID -> Rating, Location, Country
___________________________________________
___________________________________________

Table Name: Bid
FDs:
	ItemID, UserID, Time -> Amount
___________________________________________
___________________________________________

Table Name: Seller
FDs:
	UserID -> Rating
___________________________________________
___________________________________________

Table Name: Category
FDs:
	CategoryID, ItemID
___________________________________________

3) Yes, all our relations are in BCNF

4) Yes, all our relations are in 4NF