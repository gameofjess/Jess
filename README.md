# JavaChess

[![Java CI with Maven](https://github.com/maxknerrich/JavaChess/actions/workflows/maven.yml/badge.svg)](https://github.com/maxknerrich/JavaChess/actions/workflows/maven.yml)

## About

This repository will be used for a uni project for the module "Software Development 2" in the course "Medieninformatik", second semester.

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

`mvn package`

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

## Dependencies

This project uses the java-websocket library made by TooTallNate and contributors which can be found here: [Java-Websocket Repository](https://github.com/TooTallNate/java-websocket)
It also uses the [log4j logging framework](https://logging.apache.org/log4j/2.x/index.html) and the [JUnit unit test framework](https://junit.org/junit5/) as well as [JavaFX](https://openjfx.io/) and [Gson](https://github.com/google/gson) for Object Serialization.

## Copyright

JavaChess (c) 2022 [Max Knerrich](https://github.com/maxknerrich), [Kay Knöpfle](https://github.com/joystick01), 
[Benjamin Mehl](https://github.com/BenniBM), [Lucca Greschner](https://github.com/Uggah)

SPDX-License-Identifier: GPL-3.0
