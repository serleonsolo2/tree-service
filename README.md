# Interview Development Task (Tree Service)

### How to
#### Start docker-compose dependencies:
```bash
   docker-compose up --build
   ```

#### Build Project:
`./gradlew clean build`

#### Start Tests:
`./gradlew test`

#### Generate Jooq:
`./gradlew jooqCodegen`

#### Swagger Document
http://localhost:8083/api/swagger-ui.html

#### Postman collection:
postman/Tree.postman_collection.json

### Solution Description
Performance optimization for the large trees (splitted for the external service and UI):

#### External services
As any depth limitations can be not acceptable by the external clients and typical recursive cte with tall trees 
can cause performance degradation, we use ltree technique https://www.postgresql.org/docs/current/ltree.html
Ltrees by using special path field and GIST index can significantly optimize the query cost. 
Ltrees approach adds overhead to calculate the path for the creation operation.
(update operations can be more complicated, currently out of scope)
Additionally we use kotlin coroutines and flat structure as we expect payload can be very large for real DTOs. 

#### UI
We use normal recursive postgres CTE but limit the maximum depth.
Main assumption is that UI should work lazily and does not need all information at once, so we can minimize the payload size. 
Additionally, we use more convenient nested format to minimize transformations on FE side.

#### Consistency
When creating an edge we dont check consistency as it can be rather expensive operation, we
should validate consistency when getting the subtrees instead (currently out of scope)

#### Other Potential Improvements
* GraphQL and filters - payload can be minimized by using additional filters
* Paging for max children - potentially we can also implement paging for the maximum children if needed
* Use graph databases to store data in native format instead of Postgres
* Detect invalid trees, cycles
* Cloud deployment, security etc

### Technical specification:
* Kotlin
* Spring Boot
* Gradle
* Postgres
* Jooq (with code generation and ltree extension)
* Flyway
* Kotlin Coroutines
* Swagger
* Spring Boot Tests & Junit5
