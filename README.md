# HANWUPlatform : Human and nature water use platform

## How to use ?

###### step 1. make sure application.properties file path in pom.xml

###### step 2. install maven plugin in your computer

###### step 3. make sure your computer has installed mysql services and start services

###### step 4. configure configuration/application.properties

###### step 5. configure configuration/database.properties

###### step 6. configure configuration/simulation.properties

###### step 7. database migrations:
		  mvn db-migrator:create         
		  mvn db-migrator:migrate
		  
###### step 8. Build program:
          mvn clean install
          
###### step 9. Instrumentation(IDE):
          mvn process-classes  
          
          (or) mvn activejdbc-instrumentation:instrument
        

			