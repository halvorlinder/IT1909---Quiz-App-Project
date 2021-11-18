package ui.controllers;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import ui.User;
import ui.Utilities;

import java.io.IOException;

public class GoBackController extends BaseController {
    private InitializableController previousController;
    private Button button;
    private Parent previousRoot;


    /**
     * @param user the current user
     */
    public GoBackController(User user) {
        super(user);
    }

    /**
     * @param backButton the button that should take the user back to the previous page
     */
    public void setBackButton(Button backButton) {
        button = backButton;
        backButton.setOnAction((ae) -> goBack());
    }

    /**
     * sets info for loading the current page from the next page
     *
     * @param previousController the controller of the current page
     * @param previousRoot       the current root
     */
    public void setPreviousPageInfo(InitializableController previousController, Parent previousRoot) {
        this.previousRoot = previousRoot;
        this.previousController = previousController;
    }

    /**
     * returns to the previous page
     */
    protected void goBack() {
        try {
            previousController.initialize();
        } catch (IOException ioException) {
            Utilities.alertUser("Kunne ikke gå tilbake");
            return;
        }
        button.getScene().setRoot(previousRoot);
    }

}
