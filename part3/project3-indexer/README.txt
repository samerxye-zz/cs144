
return: itemId, name
search over: name, category, description
multiple keywords: OR

Item table
Category table
Attributes: 
	ItemID (string field, YES) <--- (key) so we can retrieve tuple, but also for searching
	Category (string field, YES) <--- search
	Description (text field, NO) <--- search. description too long, so don't store.

Document:
Field (string field): ItemID
Field (text field):   Full-text index... ItemID + Category(s) + Description