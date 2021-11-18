package ui.controllers;

import core.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.APIClientService;
import ui.User;
import ui.Utilities;

import java.io.IOException;

public final class LoginPageController {
    @FXML
    private PasswordField logInPassword;
    @FXML
    private TextField logInUserName;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private TextField registerUserName;

    private APIClientService apiClientService;

    /**
     * fetches userData
     */
    @FXML
    public void initialize() {
        apiClientService = new APIClientService();
    }


    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptLogIn(ActionEvent actionEvent) {
        try {
            UserRecord userRecord = new UserRecord(logInUserName.getText(), logInPassword.getText());
            String accessToken = apiClientService.loginUser(userRecord);
            logIn(actionEvent, logInUserName.getText(), accessToken);
        } catch (IOException ignored) {

        }

    }

    /**
     * attempts to log a user into the application
     *
     * @param actionEvent the actionEvent
     */
    @FXML
    public void attemptRegister(ActionEvent actionEvent) {
        if(registerUserName.getText().isEmpty())
            Utilities.alertUser("Brukernavn kan ikke være tomt");
        else if(registerPassword.getText().isEmpty())
            Utilities.alertUser("Passord kan ikke være tomt");
        try {
            UserRecord userRecord = new UserRecord(registerUserName.getText(), registerPassword.getText());
            String accessToken = apiClientService.registerUser(userRecord);
            logIn(actionEvent, registerUserName.getText(), accessToken);
        } catch (IOException ignored) {
        }
    }

    /**
     * logs into the applicatio
     *
     * @param actionEvent
     * @param username
     * @param token       the auth token
     * @throws IOException
     */
    private void logIn(ActionEvent actionEvent, String username, String token) throws IOException {
        final FXMLLoader loader = Utilities.getFXMLLoader("HomePage.fxml");
        HomePageController controller = new HomePageController(new User(username, token));
        loader.setController(controller);
        final Parent root = loader.load();
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

}
