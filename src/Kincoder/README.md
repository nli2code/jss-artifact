# Pattern Miner

## Requirements

* JDK 1.11+
* Docker & Docker-Compose
* source code of `POI 4.0.0` and `OpenJDK 1.11`
* client code of `POI 4.0.0`

## Step 1, create knowledge graph with `Docker-Compose`

### start a neo4j server

1. install `docker` and `docker-compose`
2. create a file named `docker-compose.yml`, and enter the following code:

    ```
    version: '3'
    services:
      neo4j:
        image: neo4j:latest
        restart: always
        environment:
          NEO4J_AUTH: {USERNAME}/{PASSWORD}
        cap_add:
          - SYS_RESOURCE
        ports:
          - "{HTTP_PORT}:7474"
          - "{BOLT_PORT}:7687"
        volumes:
          - {DATA_DIR}:/data
    ```
    
3. replace placeholders in `docker-compose.yml` with actual values
4. run `docker-compose up -d`

### build knowledge graph of java & poi APIs

1. pull repository from `https://github.com/nli2code/Kincode`
2. modify configurations in `kincoder/knowledge-graph-builder/src/main/java/cn/edu/pku/hcst/kincoder/kg/builder/KnowledgeGraphBuilderConfig.java` to connect the neo4j server, and specify the paths of `OpenJDK 1.11` & `POI 4.0.0`
3. run command `./gradlew knowledge-graph-builder:run` to build the knowledge graph

## Step 2, mine patterns from client code of `POI 4.0.0`

1. modify configurations in `kincoder/pattern-miner/src/main/java/cn/edu/pku/hcst/kincoder/pattern/miner/PatternMiningRunner.java:main`. There are two `TODO` comments specifying configurations need to be modified.
2. arrange your client code directory like this:

    ```
    └── clientCode
        ├── shortName0      //short name of an API
        │   ├── code0       //client code which containing that short name
        │   ├── code1
        │   ├── code2
        │   ├── code3
        │   ├── code4
        │   ├── fullName0   //one possible full name of that short name
        │   ├── fullName1   //another possible full name of that short name
        │   └── fullName2   //these are empty directories
        ├── shortName1
        │   ├── code0
        │   ├── code1
        │   ├── code2
        │   ├── fullName0
        │   ├── fullName1
    ```
3. run command `./gradlew pattern-miner:run` to mine patterns from client code
4. get your patterns in `clientCode/shortNameOfAPI/fullNameOfAPI`
