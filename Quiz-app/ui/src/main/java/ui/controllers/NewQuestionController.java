package ui.controllers;

import core.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import ui.APIClientService;
import ui.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class NewQuestionController {

    @FXML
    private Label headline;

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
    private Question preFilledQuestion;
    private int questionId;
    private boolean editMode;
    private List<TextField> listOfTextFields;
    private List<RadioButton> listOfRadioButtons;
    private String quizName;
    private APIClientService apiClientService;
    // App.setRoot needs to be completed
    // All FXML files need to be created and named accordingly

    /**
     * @param quizName
     */
    public NewQuestionController(String quizName) {
        this.quizName = quizName;
    }

    /**
     * initializes the page with pre-filled information and in edit mode
     *
     * @param quizName
     * @param questionId
     * @param question
     */
    public NewQuestionController(String quizName, int questionId, Question question) {
        this(quizName);
        editMode = true;
        preFilledQuestion = question;
        this.questionId = questionId;
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
    public void initialize() throws IOException, InterruptedException {
        apiClientService = new APIClientService();
        headline.setText(quizName);
        listOfTextFields = List.of(choice1, choice2, choice3, choice4);
        listOfRadioButtons = List.of(radioButton1, radioButton2, radioButton3, radioButton4);
        if (!editMode) {
            listOfRadioButtons.forEach(radio -> radio.setOnAction(ae -> submitButton.setDisable(false)));
            submitButton.setDisable(true);
        } else {
            questionText.setText(preFilledQuestion.getQuestion());
            for (int i = 0; i < listOfTextFields.size(); i++) {
                listOfTextFields.get(i).setText(preFilledQuestion.getChoice(i));
            }
            listOfRadioButtons.get(preFilledQuestion.getAnswer()).setSelected(true);
        }

    }

    /**
     * submits a question from the UI
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void submitQuestion(ActionEvent actionEvent) throws IOException, InterruptedException {
        //Takes you back to the home page

        if (questionText.getText().isEmpty()) {
            Utilities.alertUser("Du må skrive inn et spørsmål");
            return;
        }
        for (TextField textField : listOfTextFields) {
            if (textField.getText().isEmpty()) {
                Utilities.alertUser("Du må fylle ut alle feltene!");
                return;
            }
        }
        question = new Question(questionText.getText()
                .replaceAll("\n", " ")
                .replaceAll("\\$", " "), getListOfAnswers(), getCheckedId());
        if (editMode)
            apiClientService.putQuestion(quizName, questionId, question);
        else
            apiClientService.addQuestion(quizName, question);
        ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
        // apiClientService.deleteLeaderboard(quizName);
        // apiClientService.postLeaderboard(new Leaderboard(quizName,
        // apiClientService.getQuiz(quizName).getQuizLength()));

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
