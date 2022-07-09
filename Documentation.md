# JavaChess - Jess

## Meta-Data

### Authors

* Knöpfle, Kay (kk172)
* Greschner, Lucca (lg088)
* Knerrich, Max (mk381)
* Mehl, Benjamin (bm091)

### Project Name

JavaChess

### Repository

<https://github.com/maxknerrich/JavaChess>

<div style="page-break-after: always;"></div>

## Description

JavaChess (or short: Jess) is a PvP chess game. A session of the game is hosted by one player through the client application. The other player joins the game.

When in-game, the players can move the pieces by clicking on the pieces and afterwards clicking on the green tiles. The selfmade chess engine can calculate every legal move as well as more complicated chess specifics such as castling and en passant. It also detects when a king is in check and when a player has won ("checkmate").

The Server and Client are based on the Java-Websocket library by TooTallNate. The server can be started independently as a what we call "Dedicated Server". To do this you may just add the flag -d on application startup. Once started, the dedicated server greets you with a simple CLI. We implemented the commands list, stop, start, restart and exit. list lists all connected clients (though there will always be a maximum of two, so a dedicated server may not be used to host multiple chess sessions). stop, start and restart do what they say. Just stopping the server leaves the application running, so for actually exiting the application you must enter exit after stopping the server. Theoretically, you may also exit directly from the running server but this could lead to BindException upon the next startup (if it commences within the next 30 seconds or so... ).

Our application can be customized and configured through the generated config.json. For more information about the config refer to CONFIG.md in the repository.

### Main-Class

com.gameofjess.javachess.mainpackage.Launcher

For more information about how to run our project, check out our README within our GitHub-Repository.

### Particularities

There are a few things you need to know:

There still are some bugs with our project that we could not fix as they just occur occasionally (and sometimes may not even be our fault). You can find them in our GitHub issues.

Besides that, we have a feature to detect the user's public IPv4 address. This depends on internet connectivity and a server we are hosting. Because this poses a threat to privacy, we decided to deactivate this feature by default. If you want to try it out, you have to edit the config.json that is generated upon startup and change the value for loadPublicIPAddress to true. To further protect your privacy you may change the server used to get the ip address to another one or even your own. How to do that is described [here](https://github.com/maxknerrich/JavaChess/blob/main/CONFIG.md#HostMenuConfiguration).

When running tests you may run into problems, we describe in the chapter about testing.

#### About the game

We decided not to implement remis, so when it happens the behavior of the application is the same as if there was a checkmate.

<div style="page-break-after: always;"></div>

## UML

### Class Diagram

<img src="https://raw.githubusercontent.com/maxknerrich/JavaChess/main/Architecture/JavaChess-ClassDiagram.svg">

<div style="page-break-after: always;"></div>

### Usecase Diagram

<img src="https://raw.githubusercontent.com/maxknerrich/JavaChess/main/Architecture/JavaChess-UsecaseDiagram.svg">

<div style="page-break-after: always;"></div>

## Statements

### Architecture

#### Design pattern

We used multiple design pattern:

* Factory for scenes - See: SceneFactory.java in org.gameofjess.javachess.gui.scenes
* Builder for the Server.java-Class - See: ServerBuilder.java in org.gameofjess.javachess.server
* A variation of the singleton design pattern (though similarities aren't the biggest):

For the config we used a singleton approach before but as we aimed for the possibility of multiple configs, we decided to implement the ConfigLoader.java (formerly: ConfigHandler.java) Class a bit differently. It puts loaded configs in a HashMap so that the process of loading the config from file is not repeated everytime.

#### Interfaces

* IScene.java in com.gameofjess.javachess.gui.scenes
  * Implemented by GameScene, HostScene and JoinScene
  * Instantiated only via SceneFactory
* Config in com.gameofjess.javachess.helper.configuration
  * Implemented by StandardConfig
  * Used to make it easier to add new configs
* Message in com.gameofjess.javachess.helper.messages
  * Implemented by ClientMessage and ServerMessage

#### Inheritance

* GameController, HostMenuController and JoinController inherit from Controller
  * This makes it easier to share methods between them (notably switchScene(type: SceneType))
* All the chess pieces from com.gameofjess.javachess.chesslogic.pieces inherit from the abstract class Piece

##### Inheritance from outside the project

* Server in org.gameofjess.javachess.server inherits from the abstract class WebSocketServer in the Java-Websocket API
* Client in org.gameofjess.javachess.client inherits from the abstract class WebSocketClient in the Java-Websocket API
* BoardPane in org.gameofjess.javachess.gui.objects extends StackPane from JavaFX
* BoardOverlay in org.gameofjess.javachess.gui.objects extends HBox from JavaFX
* BoardPane in org.gameofjess.javachess.gui.objects extends HBox from JavaFX
* CapturedPieceGrid in org.gameofjess.javachess.gui.objects extends GridPane from JavaFX
* CapturedPieceView in org.gameofjess.javachess.gui.objects extends HBox from JavaFX
* PromotionSelectView in org.gameofjess.javachess.gui.objects extends VBox from JavaFX

#### Further information about the project structure

* org.gameofjess.javachess.chesslogic: Contains all chess logic (i.e. the model for the GUI)
* org.gameofjess.javachess.chesslogic.pieces: Contains all chess pieces (only model, not view)
* org.gameofjess.javachess.client: Contains WebSocketClient implementation and a ConnectionHandler that poses a way to interact with the Client without the ability to fiddle with internal Client stuff.
* org.gameofjess.javachess.gui: Contains everything related with GUI
* org.gameofjess.javachess.gui.objects: Contains all custom objects for the GUI
* org.gameofjess.javachess.gui.scenes: Contains all Scene-classes that allow easy access to a JavaFX-Scene and their respective controller
* org.gameofjess.javachess.helper.argumentparsing: Contains the ArgumentParses that parses the flags given on startup as seen in the README; the Option-Enum makes for an easy addition of new flags
* org.gameofjess.javachess.helper.configuration: Contains the standard config and the ConfigLoader which loads configs.
* org.gameofjess.javachess.helper.exceptions: Contains all custom exceptions
* org.gameofjess.javachess.helper.game: Contains enums that represent information needed for the GameController as well as Client and Server
* org.gameofjess.javachess.helper.messages: Contains all messages that are needed for Client and Server
* org.gameofjess.javachess.mainpackage: Contains Launcher- and Main-class. The Launcher-class is used to launch the application (GUI and dedicated server), Main-class launches the GUI application

### Clean Code

Most importantly, we tried to encapsulate our classes as much as possible. This means, we do not have any publicly accessible fields nor any public methods that return a modifiable instance. Either, they return an object that can't be modified (e.g. when using a Collection) or they give back new objects that were cloned from the original.

Secondly, we used loose coupling wherever possible. Although, we could not use loose coupling in all cases. For example, when using our own WebSocketServer-implementation (Server.class). That is due to methods we introduced that do not override the parent's method and thus would only be accessible if we casted the WebSocketServer to our own implementation - which would defeat the purpose of loose coupling.

We also kept attention not to use static methods, however there is one exception: The ArgumentParser.class is implemented staticly as it would not really make a difference if we instantiated the ArgumentParser in Launcher.class.

### Tests

We tried to write unit tests for every method we could test reasonably well. Therefor, we used the mocking library Mockito and the library Awaitility for testing concurrent processes.

We implemented negative tests in the ArgumentParserTest-class which you may find in com.gameofjess.javachess.helper.argumentparsing and in several other tests.

To check the test coverage of the project you may run the command `mvn package jacoco:report`. Afterwards, you will find a jacoco-report in the target > site > jacoco folder.

We also did some (sort of) integration testing. The Test-Class ServerClientTest tests the connection between Client and Server. Therefore, results from this tests may vary depending on your setup.

Also, there could be BindExceptions in ServerClientTest and ServerCommandListenerTest as well as ServerBuilderTest to which we haven't found a solution yet. This may be linked to [this issue](https://github.com/TooTallNate/Java-WebSocket/issues/879) within the upstream Java-Websocket library. We tried all solutions proposed in the comments to this issue but with no success. However, these BindExceptions should not affect the result of the tests in ServerCommandListenerTest and ServerBuilderTest as these do not depend on the server actually running - only ServerClientTest does.

### GUI

We have done the basic layout using FXML-Files in the resources folder.
The controllers can be found in the package com.gameofjess.javachess.gui.controller.

We have also created our own GUI elements which can be found in the package com.gameofjess.javachess.gui.objects. They are dynamically rendered in the GameController-class.

Most notable examples for these objects are the BoardPane and the BoardCell. They are used to display the board. It was particularly hard to get the BoardPane to stay square. Therefore, we used an implementation of the BoardPane we found on StackOverflow. The source can be found in the Javadoc.

We also implemented EventHandling for multiple GUI elements. Most notably in the GameController-class EventHandlers are used to make the pieces (as well as other fields on the board) clickable.

Also, our GUI is 100% responsive - there is just one bug where sometimes on linux machines running Mutter or KWin the pieces in the rightmost column of the game scene are rendered too big on startup. This is fixed by a quick resize.

### Logging

We pursued the following logging approach for the different log levels:

* *TRACE:* Everything, but specifically logging from the Java-Websocket library and logging of specific events within the chess logic
* *DEBUG:* Everything that should be needed to debug the application - not the chess logic or the server/client implementation
* *INFO:* Normal events such as application startup etc.
* *WARN:* Warnings that should not affect application behavior but should be something the user knows about.
* *ERROR:* Errors that could harm the experience but do not lead to the termination of the application. E.g. exceptions in the server thread that lead to the game being stopped.
* *FATAL:* Errors that lead to the termination of the application.

IMPORTANT: Note that the logging level in the log4j2.xml has no effect on the application logging level once the application has started. So, to adjust the logging level refer to the config. (See also: [CONFIG.md](https://github.com/maxknerrich/JavaChess/blob/main/CONFIG.md))

### Exceptions

We implemented our own exceptions for argument parsing that can be found in com.gameofjess.javachess.helper.exceptions

### UML

We tried to make the UML diagrams as complete as possible.
In case of the class diagram we don't think anything is missing, but with the Usecase diagram we intentionally decided to leave side features (i.e. the config and showing the ip address) in the host menu out for the sake of simplicity and readability.

### Threads

We used threads for testing as well as in our implementation. Notable examples are in the Controller-Class in which the Client and Server instances are started within threads as well as in the GameController-Class where we put longer lasting tasks like rendering images into Tasks.

We haven't implemented own Threads that have access to shared data but the WebSocket library uses Threads which then access our methods (e.g.: onOpen-method in Server-class), thus we needed to make sure that concurrency issues do not occur through the use of ConcurrentHashMaps for users and gameColors. 

### Streams and Lambda expressions

Streams and lambda expressions are used almost everywhere. You can find most of them in the GameController-Class and the chess logic. It is particularly hard to give concrete examples here because we have replaced every for-each loop with a stream to a) increase performance and b) make it easier to filter elements.
Especially hard workloads like rendering the piece images (which size might be ridiculously large (1024x1024)) and the calculation of all legal moves, we have implemented using parallel streams.

### Factories

As mentioned in the chapter about architecture, we have one factory we use to create our scenes. Note though that these are not JavaFX-Scene but rather wrappers from which we can get JavaFX-Scenes as well as their controllers. This makes it easier to add new Scenes because then only one new class implementing the Scene-Interface is needed.
