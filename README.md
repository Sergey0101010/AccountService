# AccountService
Cooking recipes service.

**Stack:** Java 17, Spring boot, Spring Security, Hibernate, MySQL, Gradle.
## Description.
To access this service, registration is required, which is available to everyone on this request: `POST /api/auth/signup`. 
After registration, the user is assigned the role of `USER`,with this role, the following functions are available to him:
- Password change: `POST /api/auth/changepass` (the password must be of a certain length, differ from the old one, not be in the database of breached passwords) 
- Viewing a user's salary for a specific month `GET api/empl/payment`
User with the role `ADMINISTRATOR` can:
- View the full list of all users of the service: `GET api/admin/user`
- Delete a user by his email `DELETE api/admin/user/{userEmail}`
- Add/Remove user role `PUT api/admin/user/role`
User with the role `ACCOUNTANT` have all abilities of `USER` and additional:
- Updating employee salary data for any period: `PUT api/acct/payments`

Important events are logged in separate files using SLF4J.
