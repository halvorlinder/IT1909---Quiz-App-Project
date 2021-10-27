package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

public abstract class Utilities {

    /**
     * displays a modal error window to the user
     *
     * @param errorMessage the message to be displayed
     */
    public static void alertUser(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
    public static void alertUser() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Noe gikk galt.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    /**
     * @param fxmlString name of fxml file
     * @return returns the FXMl loader from a fxml file
     */
    public static FXMLLoader getFXMLLoader(String fxmlString) {
        return new FXMLLoader(App.class.getResource(fxmlString));
    }

}
