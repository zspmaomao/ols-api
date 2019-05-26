# OLS-API

## How to run the Application
```sh
$ ./gradlew bootRun111
```
> This application uses in-memory DB H2.

## How to see data in the in-memory DB
1. Once the application is running, go to http://localhost:8080/h2-console.
2. Change **JDBC URL** to **jdbc:h2:mem:ols_api**, then click the **Connect** button .  
