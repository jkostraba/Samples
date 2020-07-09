Contact API used to learn a little about swagger.

GET

.../contacts/  list all contacts</br>
.../contact/<attrib>?<value>  search a specific contact by attribute</br>
.../contactLists/  list all of the contact lists</br>
.../contactList/name?<value>  search a specific contact list by name</br>

POST

.../contact/email?<value>  create or update a contact specified by its ID (email)</br>
.../contactList/name?<value>  create or update a contact list specified by its ID (name)</br>

DELETE

.../contact/email?<value>  delete a contact specified by its ID (email)</br>
.../contactList/name?<value>  delete a contact list specified by its ID (name)</br>


Swagger implements the means to define the schema for communicated objects.  We
could create a schema for the contact (contact.email, contact.firstName, contact.phone, etc.).
However, I don't want to limit the contact schema in the interface - I want the
contact to be schema-less, with the exception of the primary key, the email address.

