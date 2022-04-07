package mainpackage;

/**
 * This class is needed to fix the issue with starting JavaFX Applications pointed out here:
 * https://stackoverflow.com/questions/52144931/how-to-add-javafx-runtime-to-eclipse-in-java-11
 */
public class Launcher {
    /**
     * Start the application
     *
     */
    public static void main(String[] args){
        Main.main(args);
    }
}
