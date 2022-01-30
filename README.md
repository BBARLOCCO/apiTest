# Test Rest API

 - Spring boot application, checkout [HELP.md](./HELP.md) for details

## Configuration properties

 - The configuration properties are in src/main/resources/application*.yaml files.
 - Setup SPRING.PROFILES.ACTIVE environment variable to "prod" (without quotes) for production environment.

## Auth0 authentication (oauth2)
 - We're using auth0 provider, to config your account provide following environment variables;
   - auth0Audience
   - auth0Domain
   - auth0Connection
   - auth0ManagementToken
   - auth0ClientId
   - auth0ClientSecret

## CI/CD

Project has been setup to use Travis-ci and Heroku. 
Use https://brunos-crm-api-test.herokuapp.com/ for live api access


## H2 Database
 
 - Project comes configured to a H2 in-mem database, it will be cleared after each reboot.
 - You can setup databaseUsername, databaseUrl and databaseSecret environment variables for other SQL databases (Tested in PostgreSql). 

## Run self-contained server

 - Run `mvn (or ./mvnw in Windows) install` for dependencies installation.
 - Run `mvn spring-boot:run`, application will start listening to 8080 port.
 - You can also run it from your prefered IDE.

## Use Docker! 

 - Run  `./mvn package` for .jar creation
 - Run `docker build ./ -t apitest` for docker image creation (tag it as you prefer)
 - Start the server: `docker run -p 8080:8080 --env-file environment.list apitest`  
 
#### fill the environment.list variables first!  
 

## Use Postman for full api examples and doc:

https://www.getpostman.com/collections/6c5154528ad6968a2e02