package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

    @FXML
    private void sayHello(ActionEvent event) {
        System.out.println("Hello!");
    }
}
