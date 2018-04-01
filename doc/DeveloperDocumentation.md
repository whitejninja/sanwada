![sanwada forum service](https://github.com/whitejninja/sanwada/blob/master/doc/sanwada.png "Sanwada forum service")

Sanwada is [sinhala](https://en.wikipedia.org/wiki/Sinhalese_language) term (සංවාද  ) which literally translates into [discussion](https://translate.google.com/#auto/en/%E0%B7%83%E0%B6%82%E0%B7%80%E0%B7%8F%E0%B6%AF). In software terms **Sanwada** is forum services which enables creation/alteration/deletion of typical forum related entities such as question/posts, answers/comments, points and flags. **Sanwada** provides the forum engine that can be deployed in a microservices environment excluding the frontend for the forum engine. This gives you the room to BYOUI (Bring Your Own UI) solving the issue of inabality to decouple unpleasent UI that are glued with many popular open source and commerical forum software. 

# Documentation for developers

## Building and accessing Javadoc

Go to root directory of the project and execute,

    $ gradle javadoc

Javadoc for **Sanwada** will be accessible at `build/docs/javadoc/index.html`

	$ google-chrome build/docs/javadoc/index.html


## Accessing swagger documentation

Build and deploy application

	$ gradle build
    $ $GLASSFISH_INSTALL_DIR/bin/asadmin start-domain
    $ $GLASSFISH_INSTALL_DIR/bin/asadmin deploy build/libs/sanwada.war

Once **Sanwada** is deployed swagger documentation can be accessible at `http://locahost:8080/sanwada/v1/swagger.json` via **Swagger UI**.


## Configuration

Configuration is handled by `src/main/resources/META-INF/microprofile-config.properties` file. Internally this configuration is processed via eclipse-microprofile API.

Configuration values supported are in the following table

| Property        | Value           | Example  |
|-----------------|-----------------|----------|
| config_ordinal  | Integer value to compare with rest of the confiuration files. File with the highest number takes precedense when configration is evalutated | 5999 | 
| datasource.host | String hostname name or IP address of the database server | localhost |
| datasource.port | Integer value of the port number, database server is listening on incoming connections | 27019 |


## Design 

![sequence_diagram](https://github.com/whitejninja/sanwada/blob/master/doc/sequence_diagram_v1.png "Sequence Diagram")

**Sanwada** serves it's user's forum requests via a REST API.
REST API currentlly has 2 endpoints/resources.

* QuestionResource  (synonymous to a post in a forum scenario)
* AnswerResource  (synonymous to a reply in a forum scenario)

The requests recieved from the **Foo**Resource object (Foo can be any REST resource) are then handed over to **Foo**Dataservice. **Foo**DataService implements **Foo**Dao interface. It processes the request received, applies any filters to the request and makes the request presentable to **Bar**DataSourceClient.

**DataSourceClient** interface contracts the interation between a client who wants access and execute queries on a database server. **Bar**DataSourceClient implements this contract.

**Bar**DataSourceClient is responsible for connecting to **Bar** database and executing the requested database queries. **Bar** can be MySql, MongoDB, MsSql or any other database type. **Bar** can be relational or nosql type database as database query preparation is done by the **Foo**DataService object. Currently only MongoDB supported is implemented as a datasource client.

The above sequence diagram abstracts the above mentioned interaction for GET request.



