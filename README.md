## tn-query-mono

This is a mono-repo structure, which captures the `tn-query` libraries in a single build.

### Building

All the `tn-query` libraries are built with [Maven](https://maven.apache.org/).

Typically, the command `mvn clean install` is used, which builds and packages, runs unit and integration tests and installs the library and source jars into the local 
[Maven](https://maven.apache.org/) repository.

See the individual projects for any additional build instructions, in particular [tn-parent](./tn-parent/README.md), which details additional plugins and customizations used with 
the tn-query projects.  