# ARG
To make this program run in new computers. You have to first check whether dbcreate in DataSource.groovy is set to create or not, if its in "create" then it's fine, 
else change it to "create"
After the program is executed once, change dbcreate back to "update". 
For new user, after creating your account, you have to change the value of "role" in User table in the local database, it will by default be set to "user",
you have to change it to "admin" in order to add Teachers and Subjects.
