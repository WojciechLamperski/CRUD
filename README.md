## To see the app in production access this link:
[52.58.98.128:8080/swagger-ui/index.html](http://52.58.98.128:8080/swagger-ui/index.html)
<br/> You can then test it in Postman

### About the project:
This application is a demo CRUD REST API built in Java, and Spring Boot. 
The application allows users to access, and modify records regarding population 
in various polish districts and voivodeships via the use of endpoints 
(albeit in production only GET endpoints are exposed). 
The app uses H2 as its database, is hosted in AWS EC2, and implements CI/CD with GitHub Actions.

-----------------

## Prerequisites for running the app locally:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [JDK-21](https://jdk.java.net/21/)
- [Postman](https://www.postman.com/downloads/) (Optional but useful)


### Project Setup Guide 🚀

- For running the main app use (in root directory):
    ```
  mvn clean install
    mvn spring-boot:run
    ```
  The app will populate the h2 database with data from resources/data.json file.
  <br/><br/>

- There is an option to run the app profiled to only expose GET endpoints:
   ```
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
   ```
  ###### Endpoints
  The app should start on localhost:8080, unless this port is already occupied. <br>
  You can see all the endpoints by visiting the following URL: <br/>
  [localhost:8080/swagger-ui/index.html](localhost:8080/swagger-ui/index.html)
  <br>
  If you run the app with **-Dspring-boot.run.profiles=prod** you will only expose GET endpoints, which will be reflected in your swagger-ui page.
  ```
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
  ```
  
  ###### Pages, Ordering, Sorting
  You can also specify params (when doing a **GET** request) such as ordering by record, sort order, and page size, like this: <br>
  - /api/years?pageNumber=0&pageSize=1&sortBy=yearId&sortDirection=desc


- For running tests use:
    ```
    mvn clean install
    ```