# (itemId, latitude, longitude)
# 

CREATE TABLE IF NOT EXISTS Location (
	ItemID BIGINT,
	Coordinates POINT NOT NULL
) ENGINE=MyISAM;

INSERT INTO Location (ItemID, Coordinates)
SELECT ItemID, POINT(Latitude, Longitude)
FROM Item;

CREATE SPATIAL INDEX sp_index ON Location (Coordinates);