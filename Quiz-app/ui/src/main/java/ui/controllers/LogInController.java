package ui.controllers;

import core.User;
import core.UserData;
import core.UserRecord;
import io.UserPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.APIClientService;
import ui.Utilities;

import java.io.IOException;

public final class LogInController {
    @FXML
    private PasswordField logInPassword;
    @FXML
    private TextField logInUserName;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private TextField registerUserName;

    private UserPersistence userPersistence;
    private UserData userData;
    private final String fileName;
    private APIClientService apiClientService;
    /**
     * fetches userData
     */
    @FXML
    public void initialize() {
        apiClientService = new APIClientService();
    }

    /**
     *
     */
    public LogInController() {
        fileName = "users.json";
    }

    /**
     * @param fileName the file where users are saved
     */
    public LogInController(String fileName) {
        this.fileName = fileName;
    }

    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptLogIn(ActionEvent actionEvent) {
        try {
            UserRecord userRecord = new UserRecord(logInUserName.getText(),logInPassword.getText());
            if (userData.attemptLogIn(userRecord)){
                apiClientService.loginUser(userRecord);
                logIn(actionEvent, logInUserName.getText());
            }
            else
                Utilities.alertUser("Brukernavn eller passord er feil");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }

    }

    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptRegister(ActionEvent actionEvent) {
        //TODO create logic for registering and checking username and password
        try {
            UserRecord userRecord = new UserRecord(registerUserName.getText(),registerPassword.getText());
            if (userData.attemptRegister(userRecord)){
                apiClientService.registerUser(userRecord);
                logIn(actionEvent, registerUserName.getText());
            } else {
                Utilities.alertUser("Brukernavn er tatt");
            }
        } catch (IOException | InterruptedException ioException) {
            Utilities.alertUser("Noe gikk galt");
        }
    }

    /**
     * logs into the applicatio
     *
     * @param actionEvent
     * @param username
     * @throws IOException
     */
    private void logIn(ActionEvent actionEvent, String username) throws IOException {
        User.setUserName(username);
        final FXMLLoader loader = Utilities.getFXMLLoader("HomePage.fxml");
        // HomePageController controller = new HomePageController();
        // loader.setController(controller);
        final Parent root = loader.load();
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

}
