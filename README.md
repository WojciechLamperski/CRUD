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

- In my app I assume that the url to you SQL app is based on the db name, if it's not modify it in *application.properties* and *application-test.properties*:

   ```properties
   spring.datasource.url=jdbc:mysql://${DB_TEST_URL}/${DB_TEST_NAME}
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
    DB_URL=ie.localhost:3306
    # Test DB   
    DB_TEST_HOST=ie.localhost
    DB_TEST_USER=USER_YOU_HAVE_SET
    DB_TEST_PASSWORD=PASSWORD_YOU_HAVE_SET
    DB_TEST_NAME=DB_NAME_YOU_HAVE_SET
    DB_TEST_URL=ie.localhost:3306
    
    ```
- These variables will be used by our *application.properties* & *application-test.properties* as well as in our *python script*.
### 6. Populate the database using python script
- Run your python script, in the "database" folder - *jsonToMySQL.py*. It will populate the database with the  values from *data.json*. 
- By default, the script uses the main database environment variables (`DB_HOST`, `DB_NAME`, etc.), so if you created a test database simply comment the original variables and uncomment the test variables in your python scrip and fire it for the second time.
### 7. Run the app. 🚀 