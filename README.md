# JavaChess

[![Java CI with Maven](https://github.com/maxknerrich/JavaChess/actions/workflows/maven.yml/badge.svg)](https://github.com/maxknerrich/JavaChess/actions/workflows/maven.yml)

## About

Jess (short for JavaChess) is a chess game written in Java. It was made for a uni project for the module "Software Development 2" in the second semester of the course "BSc Medieninformatik" (engl.: "Computer Science and Media") at [Hochschule der Medien / Stuttgart Media University](https://www.hdm-stuttgart.de).

Screenshots can be found [here](Screenshots.md).

## How to build and use

### Dependencies

For building this project you need to have maven and java installed. Depending on your operating system, steps may vary. Therefore, the below installation instructions might not apply.

Debian:

`$ sudo apt update && sudo apt install -y maven openjdk-17-jre`

Other debian-based distributions may be similar, but that's untested.

Fedora:

```
$ sudo dnf install -y maven java-17
$ sudo dnf install -y maven-openjdk-17 --allowerasing
```

### The building process

After cloning the repository you may run the following command within the project's folder:

`mvn package -DskipTests`

After the process of building, you should find the built jar-file in the folder target.
When the project proceeds to usuable state, we'll also provide prebuilt jars.

### How to use

To start the client application you may run the following command:

`java -jar JavaChess-0.1.jar`

However, if you intend to host a dedicated server there are a few advanced features.

- `-s`: Start a dedicated server.
- `-p <port>`: Choose a port for the server to listen on.
- `-H <hostname>`: Choose a hostname for the server to listen on.

When using a dedicated server there are commands you can run to monitor the running server and manipulate its operation:

- `stop`: Stop the server.
- `start`: Start the server after it has been stopped.
- `restart`: Restart the server.
- `list`: Show the players' name.
- `exit`: Exit the program. We advice to only run this command when the server is already stopped.

## How to generate javadoc documentation

This program will be fully documented using javadoc. To generate the javadoc-files you may navigate to the project
folder and run the following command:

`mvn javadoc:javadoc`

Using the optional flag `-Dshow=private` will add information about private methods to the generated javadoc-files.

## Configuration

Configuration is described here: [CONFIG.md](https://github.com/maxknerrich/JavaChess/blob/main/CONFIG.md)

## Dependencies

### Runtime dependencies

|Dependency|Creator|License|
|:--:|:--:|:--:|
|[Java-WebSocket](https://github.com/TooTallNate/java-websocket)|[TooTallNate and contributors](https://github.com/TooTallNate)|[MIT License](https://github.com/TooTallNate/Java-WebSocket/blob/master/LICENSE)|
|[JavaFX](https://openjfx.io/)|[Oracle / OpenJDK-Community](https://github.com/openjdk)|[GPLv2](https://github.com/openjdk/jfx/blob/master/LICENSE), [Classpath Exception](https://github.com/openjdk/jfx/blob/master/ADDITIONAL_LICENSE_INFO)|
|[gson](https://github.com/google/gson)|[Google](https://opensource.google)|[Apache License 2.0](https://github.com/google/gson/blob/master/LICENSE)|
|[log4j2](https://github.com/apache/logging-log4j2)|[The Apache Software Foundation](https://www.apache.org/)|[Apache License 2.0](https://github.com/apache/logging-log4j2/blob/release-2.x/LICENSE.txt)|
|[Apache Commons Collections](https://github.com/apache/commons-collections)|[The Apache Software Foundation](https://www.apache.org/)|[Apache License 2.0](https://github.com/apache/commons-collections/blob/master/LICENSE.txt)|

### Testing dependencies

|Dependency|Creator|License|
|:--:|:--:|:--:|
|[JUnit Framework](https://github.com/junit-team/junit5/)|[JUnit-Team](https://junit.org/junit5/)|[Eclipse Public License v2.0](https://github.com/junit-team/junit5/blob/main/LICENSE.md)|
|[Mockito Framework](https://github.com/mockito/mockito)|[Szczepan Faber and contributors](https://site.mockito.org/)|[MIT License](https://github.com/mockito/mockito/blob/main/LICENSE)
|[Awaitility](https://github.com/awaitility/awaitility)|[Johan Haleby and contributors](http://www.awaitility.org/)|[Apache License 2.0](https://github.com/awaitility/awaitility/blob/master/LICENSE)|

### Resources

|Dependency|Creator|License|
|:--:|:--:|:--:|
|[Chess Pieces](https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces)|[Colin M.L. Burnett](https://commons.wikimedia.org/wiki/User:Cburnett)|[GPLv2+](https://www.gnu.org/licenses/gpl-2.0.txt)|

## Copyright

JavaChess (c) 2022 [Max Knerrich](https://github.com/maxknerrich), [Kay Knöpfle](https://github.com/joystick01), 
[Benjamin Mehl](https://github.com/BenniBM), [Lucca Greschner](https://github.com/Uggah)

SPDX-License-Identifier: GPL-3.0
