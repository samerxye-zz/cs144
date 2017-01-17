CREATE TABLE Actors (Name VARCHAR(40), Movie VARCHAR(80), Year INTEGER, Role VARCHAR(40));

LOAD DATA LOCAL INFILE '~/data/actors.csv' INTO TABLE Actors FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

select Name from Actors where Movie='Die Another Day';

delete from Actors where Movie='Die Another Day';

