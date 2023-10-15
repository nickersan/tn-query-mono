## tn-query-mono

This is a mono-repo structure, which captures all `tn-query` projects in a single repository.

For more information about `tn-query`, see the [tn-query](./tn-query/README.md) project which defines the query concepts. 

### Projects

#### [tn-parent](./tn-parent/README.md)
Parent POM that defines the common dependencies and build features for all `tn-query` projects.

#### [tn-query](./tn-query/README.md)
The base library that defines the query structure, provides the parser for that query structure and sets up the extension points for specific predicate implementations.

#### [tn-query-java](./tn-query-java/README.md)
An implementation that constructs Java predicates that can be used in conjunction with the 
[Java Stream API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html).

#### [tn-query-jdbc](./tn-query-jdbc/README.md)
An implementation that constructs SQL predicates that can be used in conjunction with a JDBC 
[PreparedStatement](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/PreparedStatement.html).

#### [tn-query-jpa](./tn-query-jdbc/README.md)
An implementation that constructs JPA predicates that can be used in conjunction with the  
[Spring Boot JPA API](https://docs.spring.io/spring-data/jpa/docs/current/reference/html).

### Build

All the `tn-query` projects are built with [Maven](https://maven.apache.org/).

Typically, the command `mvn clean install` is used, which builds and packages, runs unit and integration tests and installs the artifacts into the local 
[Maven](https://maven.apache.org/) repository.

The [tn-parent](./tn-parent/README.md) must be build first, followed but [tn-query](./tn-query/README.md), after which, the projects that provide the specific predicate 
implementations can be built as necessary.

See the individual projects for any additional build instructions, in particular [tn-parent](./tn-parent/README.md), which details additional plugins and customizations used with 
the tn-query projects.