# Food Log Rest API

 - Spring boot application, checkout [HELP.md](./HELP.md) for details

## Configuration properties

 - The configuration properties are in src/main/resources/applitation*.yaml files.
 - Setup SPRING.PROFILES.ACTIVE environment variable to "prod" (without quotes) for production environment.
 - Setup nutritionix apikey and appId for nutritionix credentials.
 - For production environment **change foodLog.secretKey value** , is the salt for JWT tokens.

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
 - Start the server: `docker run -p 8080:8080 apitest` 



## Initial DATA

 - Default data will be migrated on startup, including 3 users:
   - user@test.com, with several food entries.
   - user2@test.com, without food entries.
   - admin@test.com, with several food entries and admin role.