package ui.controllers;

import core.Question;
import core.Quiz;
import io.QuizPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ui.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class NewQuestionController {


    @FXML
    private TextField questionText;

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
    private Button backButton;

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
    private String quizName;

    // App.setRoot needs to be completed
    // All FXML files need to be created and named accordingly

    /**
     *
     * @param quizName
     */
    public NewQuestionController(String quizName) {
        this.quizName = quizName;
    }

    /**
     * Sets paramet to default quiz
     */
    public NewQuestionController() {
        this.quizName = "quiz101";
    }

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
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void submitQuestion(ActionEvent actionEvent) throws IOException { //Takes you back to the home page

        for (TextField textField : listOfTextFields) {
            if (textField.getText().isEmpty()) {
                Utilities.alertUser(
                        "You have to fill out all the options before you can submit!");
                throw new IllegalStateException(
                        "You have to fill out all the options before you can submit!");
            }
        }
        if (questionText.getText().isEmpty()) {
            Utilities.alertUser("Du må skrive inn et spørsmål");
            throw new IllegalStateException("Du må skrive inn et spørsmål");
        }
        question = new Question(questionText.getText()
                .replaceAll("\n", " ")
                .replaceAll("\\$", " "), getListOfAnswers(), getCheckedId());
        QuizPersistence quizPersistence = new QuizPersistence();
        Quiz quiz = quizPersistence.loadQuiz(quizName);
        quiz.addQuestion(question);
        quizPersistence.saveQuiz(quiz);
        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());

    }

    /**
     * @return id of the checked button
     */
    public int getCheckedId() {
        return radioButton.getToggles().indexOf(radioButton.getSelectedToggle());
    }

    /**
     * @return list of choices provided by the user
     */
    public List<String> getListOfAnswers() {
        return listOfTextFields.stream().map(field -> field.getText().replace('\n', ' ')).collect(Collectors.toList());
    }

    public String getQuestion() {
        return questionText.getText();
    }
    /**
     * Sets the current root to be the home page
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void showHomePage(ActionEvent actionEvent) throws IOException { // Switch scene to HomePage

        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }

}
