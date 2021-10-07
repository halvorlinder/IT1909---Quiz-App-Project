package ui.controllers;

import core.Question;
import io.QuizStorageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.App;
import ui.ModalWindowUtility;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class NewQuestionController {


    @FXML
    private TextArea questionText;

    @FXML
    private TextField choice1;

    @FXML
    private TextField choice2;

    @FXML
    private TextField choice3;

    @FXML
    private TextField choice4;

    @FXML
    private Button submitButton;

    @FXML
    private RadioButton radioButton1;

    @FXML
    private RadioButton radioButton2;

    @FXML
    private RadioButton radioButton3;

    @FXML
    private RadioButton radioButton4;

    @FXML
    private ToggleGroup radioButton;

    private Question question;
    private List<TextField> listOfTextFields;
    private List<RadioButton> listOfRadioButtons;
    private List<String> listOfAnswers;

    // App.setRoot needs to be completed
    // All FXML files need to be created and named accordingly

    /**
     * initializes the controller
     */
    public void initialize() {
        listOfTextFields = List.of(choice1, choice2, choice3, choice4);
        listOfRadioButtons = List.of(radioButton1, radioButton2, radioButton3, radioButton4);
        listOfRadioButtons.forEach(radio -> radio.setOnAction(ae -> submitButton.setDisable(false)));

        submitButton.setDisable(true);
    }

    /**
     * submits a question from the UI
     * @throws IOException
     */
    @FXML
    public void submitQuestion() throws IOException { //Takes you back to the home page

        for (TextField textField : listOfTextFields) {
            if (textField.getText().isEmpty()) {
                ModalWindowUtility.alertUser(
                        "You have to fill out all the options before you can submit!");
                throw new IllegalStateException(
                        "You have to fill out all the options before you can submit!");
            }
        }
        if (questionText.getText().isEmpty()) {
            ModalWindowUtility.alertUser("Du må skrive inn et spørsmål");
            throw new IllegalStateException("Du må skrive inn et spørsmål");
        }
        question = new Question(questionText.getText()
                .replaceAll("\n", " ")
                .replaceAll("\\$", " "), getListOfAnswers(), getCheckedId());
        QuizStorageHandler handler = new QuizStorageHandler("quiz101");
        handler.writeQuestion(question);
        App.setRoot("HomePage.fxml");
    }

    /**
     *
     * @return id of the checked button
     */
    public int getCheckedId() {
        return radioButton.getToggles().indexOf(radioButton.getSelectedToggle());
    }

    /**
     *
     * @return list of choices provided by the user
     */
    public List<String> getListOfAnswers() {
        return listOfTextFields.stream().map(field -> field.getText().replace('\n', ' ')).collect(Collectors.toList());
    }

}
