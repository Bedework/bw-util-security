# bw-util-security [![Build Status](https://travis-ci.org/Bedework/bw-util-security.svg)](https://travis-ci.org/Bedework/bw-util-security)

This project provides a number of security related utility classes and methods for
[Bedework](https://www.apereo.org/projects/bedework).

### Requirements

1. JDK 11
2. Maven 3

### Building Locally

> mvn clean install

### Releasing

Releases of this fork are published to Maven Central via Sonatype.

To create a release, you must have:

1. Permissions to publish to the `org.bedework` groupId.
2. `gpg` installed with a published key (release artifacts are signed).

To perform a new release:

> mvn -P bedework-dev release:clean release:prepare

When prompted, select the desired version; accept the defaults for scm tag and next development version.
When the build completes, and the changes are committed and pushed successfully, execute:

> mvn -P bedework-dev release:perform

For full details, see [Sonatype's documentation for using Maven to publish releases](http://central.sonatype.org/pages/apache-maven.html).

### Release Notes
#### 4.1.0
    * Moved from bw-util

#### 4.1.1
    * Update library versions

#### 4.1.2
    * Update library versions

#### 4.1.3
    * Update library versions

#### 4.1.4
    * Update library versions

#### 5.0.0
    * Use bedework parent
    * Update library versions

#### 5.0.1
    * Changes to util-config
    * Update library versions

#### 5.0.2
    * sonatype release failure
