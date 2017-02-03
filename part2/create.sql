CREATE TABLE Item (
	ItemID BIGINT,
	UserID VARCHAR(500),
	Name VARCHAR(500),
	Currently DECIMAL(8,2),
	Buy_Price DECIMAL(8,2),
	First_Bid DECIMAL(8,2),
	Number_of_Bids INT,
	Location VARCHAR(500),
	Latitude DOUBLE, 
	Longitude DOUBLE, 
	Country VARCHAR(500), 
	Started TIMESTAMP,
	Ends TIMESTAMP,
	Description VARCHAR(4000),
	PRIMARY KEY(ItemID)
);

CREATE TABLE Bidder (
	UserID VARCHAR(500),
	Rating INT,
	Location VARCHAR(500),
	Country VARCHAR(500),
	PRIMARY KEY(UserID)
);

CREATE TABLE Bid (
	ItemID BIGINT,
	UserID VARCHAR(500),
	Time TIMESTAMP, ###????
	Amount DECIMAL(8,2),
	PRIMARY KEY(ItemID, UserID, Time)
);

CREATE TABLE Seller (
	UserID VARCHAR(500),
	Rating INT,
	PRIMARY KEY(UserID)
);

CREATE TABLE Category (
	Category VARCHAR(500),
	ItemID BIGINT,
	PRIMARY KEY(Category, ItemID)
);