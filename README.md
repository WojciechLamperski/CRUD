
### Prerequisites:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [JDK-21](https://jdk.java.net/21/)
- [Postman](https://www.postman.com/downloads/) (Optional but useful)

-----------------

## Project Setup Guide

### 1. Install and Set Up MySQL Workbench
- Download and install **MySQL Workbench** on your device: [MySQL Workbench Download](https://dev.mysql.com/downloads/workbench/).  
- Follow the official installation instructions to complete the setup.

### 2. Connect to MySQL workbench
- Open **MySQL Workbench** and connect to your **local instance (3306)** using the root user and the password set during installation.
- *(Optional)* Create a new **user** with necessary privileges that you will use for this application.

### 3. Create a MySQL Workbench Connection
- Set up a new MySQL connection in Workbench.
- You can either:
    - Use the **custom user** created in the previous step, **or**
    - Use the **root user** for authentication.

- In my app I assume that the url to you SQL follows this structure (which it most likely does), but if it doesn't - modify it in *application.properties* and *application-test.properties*:

   ```properties
   spring.datasource.url=jdbc:mysql://${DB_HOST}${DB_PORT}/${DB_NAME}
   # ie. jdbc:mysql://localhost:3306/backend
   ```
  
### 4. Execute the Database Script
- Navigate to the `database` directory and locate the `.sql` script.
- Copy the script and execute it in **MySQL Workbench** to create a database named `"backend"` (you can use different name if you want).
- *(Optional)* If you will perform **tests**, execute the same script again with a different database name to create our test database.

### 5. Configure Environment Variables
- Create a `.env` file at the root level (same layer as `database` and `src` folders) and define the following variables (omit the test values, if you haven't created a separate db for tests):
    ```dotenv
    # Main app DB  
    DB_HOST=ie.localhost
    DB_USER=USER_YOU_HAVE_SET
    DB_PASSWORD=PASSWORD_YOU_HAVE_SET
    DB_NAME=DB_NAME_YOU_HAVE_SET
    DB_PORT=ie.:3306
    # Test DB   
    DB_TEST_HOST=ie.localhost
    DB_TEST_USER=USER_YOU_HAVE_SET
    DB_TEST_PASSWORD=PASSWORD_YOU_HAVE_SET
    DB_TEST_NAME=DB_NAME_YOU_HAVE_SET
    DB_TEST_PORT=ie.:3306
    ```
- These variables will be used by our *application.properties* & *application-test.properties* as well as in our *python script*.
### 6. Populate the database using python script
- Run your python script, in the "database" folder - *jsonToMySQL.py*. It will populate the database with the  values from *data.json*. 
- By default, the script uses the main database environment variables (`DB_HOST`, `DB_NAME`, etc.), so if you created a test database simply comment the original variables and uncomment the test variables in your python scrip and fire it for the second time.
### 7. Run the app. 🚀 
- For running the main app use:
    ```
    mvn spring-boot:run
    ```
  ###### Endpoints
  The app should start on localhost:8080, unless this port is already occupied. <br>
  You have the following URL endpoints at your disposal:
  - /api/years
    - /{yearId}
    - /{yearId}/districts
    - /{yearId}/populations
  - /api/voivodeships
    - /{voivodeshipId}
    - /{voivodeshipId}/districts
    - /{voivodeshipId}/populations
  - /api/districts
    - /{districtId}
    - /{districtId}/populations
  - /api/populations
    - /{populationId}

  <br>
  
  ###### Request Types
  All of the above work for **GET** request, but **POST**, and **PUT** don't work for nested requests. If you want to delete a record use **DELETE** request and add the ID of the record you want to delete (years/1 will delete first year).

  <br>
  
  ###### Pages, Ordering, Sorting
  You can also specify params (when doing a **GET** request) such as ordering by record, sort order, and page size, like this: <br>
  - /api/years?pageNumber=0&pageSize=1&sortBy=yearId&sortDirection=desc


- For running tests use:
    ```
    mvn clean install
    ```