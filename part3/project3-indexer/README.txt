Index design:

- One inverted index
- Each item in the Item table, identified by its ItemID, is a document in the inverted index
- Each document has 3 fields:
	1. ItemID (StringField, Field.Store.YES): ItemID is necessary to retrieve the tuple from the database later on. It's also required in the search result.
	2. Name (StringField, Field.Store.YES): Name is required in the search result.
	3. Content (TextField, Field.Store.NO): Concatenation of an Item's name, categories, and description. This field will be used to search matches on. The field is not stored because the info will not be required in the search result. It will also waste storage space.