## tn-parent

Defines the common dependencies and build features for all `tn-query` projects.

### Features
In addition to the usual features provided by Maven, `tn-parent` adds the following:

* switches the Java compiler to Java 17.
* attaches source and test jars that will be installed/deployed to the Maven repository when the relevant `install` and `deploy` goals are used.
* integration test separation via a `src/it` directory, so that fast running unit tests can be run in isolation to typically slower integration tests.
* [JaCoCo](https://www.jacoco.org/index.html) coverage reports, which are generated with every `test` run.
* [Pitest](https://pitest.org/) mutation tests, that can be optionally run by adding `-Dpitest` to the Maven command.

### Build

Typically, the command `mvn clean install` is used, which builds and packages, runs unit and integration tests and installs the artifacts into the local
[Maven](https://maven.apache.org/) repository.

