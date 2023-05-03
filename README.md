# Bookstore
This project implements a proof-of-concept of a bookstore backend. 

## APIs
It provides APIs to:
- Create a book (**POST**)
- Update a book (**PUT**)
- Find books by title/author (**GET** with path variables)
- Delete a book (**DELETE**)

##Roles
The User role has permissions to create, update and find books. The Admin role has permissions to delete a book.

## Security
The user and admin roles are authorized by basic authentication, which is not considered secure. Their passwords are randomly generated on application startup.

## Testing
API tests were done using Postman. The application was locally connected to MySQL. Please contact the developer for a Postman testing collection.

## Known issues
Currently there is no support for two authors with the same name - names are used as an id field. Inserting an author with the same name will overwrite the existing author.

## Extensions
- Switch to a more secure form of authentication
- Use a composite key for authors based on both name and id, allowing authors with identical names


