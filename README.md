Commands in the main directory of project to run the app:
1. mvn clean package
2. docker build -t selfcloud-demo:latest
3. docker run -p 8090:8090 --env=GOOGLE_CLIENT_ID=<Your google client id> --env=GOOGLE_CLIENT_SECRET=<Your google client secret> --env=SPRING_MAIL_USERNAME=<Your mail address> --env=SPRING_MAIL_PASSWORD=<Your app password> selfcloud-demo:latest

The login page is available at http://localhost:8090/login
The Swagger-ui is available at http://localhost:8090/swagger-ui

The default users (username:password):
1. user:user with role USER (READ_ORDER)
2. mod:mod with role MODERATOR (READ_ORDER, CREATE_ORDER)
3. admin:admin with role ADMIN (READ_ORDER, CREATE_ORDER, UPDATE_ORDER, DELETE_ORDER, CREATE_USER, DELETE_USER, GRAND_AUTHORITY, REVOKE_AUTHORITY)
