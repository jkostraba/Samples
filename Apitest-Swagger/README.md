Contact API used to learn a little about swagger.

GET

.../contacts/  list all contacts
.../contact/<attrib>?<value>  search a specific contact by attribute
.../contactLists/  list all of the contact lists
.../contactList/name?<value>  search a specific contact list by name

POST

.../contact/email?<value>  create or update a contact specified by its ID (email)
.../contactList/name?<value>  create or update a contact list specified by its ID (name)

DELETE

.../contact/email?<value>  delete a contact specified by its ID (email)
.../contactList/name?<value>  delete a contact list specified by its ID (name)


Swagger implements the means to define the schema for communicated objects.  We
could create a schema for the contact (contact.email, contact.firstName, contact.phone, etc.).
However, I don't want to limit the contact schema in the interface - I want the
contact to be schema-less, with the exception of the primary key, the email address.

