![sanwada forum service](https://github.com/whitejninja/sanwada/blob/master/doc/sanwada.png "Sanwada forum service")

Sanwada is [sinhala](https://en.wikipedia.org/wiki/Sinhalese_language) term (සංවාද  ) which literally translates into [discussion](https://translate.google.com/#auto/en/%E0%B7%83%E0%B6%82%E0%B7%80%E0%B7%8F%E0%B6%AF). In software terms **Sanwada** is forum services which enables creation/alteration/deletion of typical forum related entities such as question/posts, answers/comments, points and flags. **Sanwada** provides the forum engine that can be deployed in a microservices environment excluding the frontend for the forum engine. This gives you the room to BYOUI (Bring Your Own UI) solving the issue of inabality to decouple unpleasent UI that are glued with many popular open source and commerical forum software. 

## High level view

![hight level view](https://github.com/whitejninja/sanwada/blob/master/doc/aerialview.png "High level view")

**Sanwada** when deployed should be assisted with a frontend service and a authentication/authorization service. 

#### Building

    $ git clone https://github.com/whitejninja/sanwada.git
    $ cd sanwada
    $ gradle build

#### Testing

    $ gradle check

#### Deploying
Although any .war file can be run in a Java containered appliction server, It is recommended to deploy **Sanawada** with glassfish application server.

Start application server

    $ $GLASSFISH_INSTALL_DIR/bin/asadmin start-domain

deploy sanawada.war

    $ $GLASSFISH_INSTALL_DIR/bin/asadmin deploy build/libs/sanwada.war

#### Contributing
If you intend to contribute to **Sanwada** please make a [pull request](https://github.com/whitejninja/sanwada/pulls). We would like to inform you that as a convention we follow [Java style guide](https://google.github.io/styleguide/javaguide.html) published by Google. If you use eclipse as your IDE goto `windows -> preferences -> Java -> formatter` and import `eclipse-java-formatter.xml` at the project root which follows Google's code style.

The developer documentation is accessible at [Developer documenation](https://github/whitejninja/sanwada/doc/DeveloperDocumentation.md)
