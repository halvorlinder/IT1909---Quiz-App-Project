package ui;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public abstract class ModalWindowUtility {

    public static void alertUser(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
