# Demo javaFX project
This is a simple javaFX demo project which can be used as a starting point.
Just follow the setup to download and finally run your application.

# Setup for a group
 1. Every Member: Download IntelliJ IDEA
	- (https://www.jetbrains.com/de-de/idea/download/)
 2. Every Member: Download JDK 17
	- (https://adoptium.net/)
 3. Only once for every team!: Fork this Repository
    - Rename your project in gitlab
    - Grant access to your team members
 4. Every Member: Clone the forked repository
 5. Every Member: Open the project in intelliJ IDEA
	- Make sure to enable "auto-import" if prompted
	- Specify JDK Version to 17 if prompted
 6. Every Member: Run by hitting the green run button in the top right corner (Make sure "Run javaFX" configuration is selected)
 
# Optional
Commands to run the project from terminal (You may have to install maven if the commands fail):
- Run javaFX: `mvn clean compile javafx:run`
- Run tests: `mvn clean compile test`
