
return: itemId, name
search over: name, category, description
multiple keywords: OR

Item table
Category table
Attributes: 
	ItemID (string field, YES) <--- (key) so we can retrieve tuple, but also for searching
	Name (string field, YES) <--- required to return in search results.
	Description (text field, NO) <--- full text search. description too long. wastes space, so don't store.