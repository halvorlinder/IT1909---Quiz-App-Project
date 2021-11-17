package ui.controllers;

import core.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.APIClientService;
import ui.User;
import ui.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class NewQuestionPageController extends GoBackController implements InitializableController {

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

    /**
     * @param quizName the name of the quiz
     * @param user     the current user
     */
    public NewQuestionPageController(String quizName, User user) {
        super(user);
        this.quizName = quizName;
    }

    /**
     * initializes the page with pre-filled information and in edit mode
     *
     * @param quizName   the name of the quiz
     * @param questionId the index of the question
     * @param question   the new question
     * @param user       the current user
     */
    public NewQuestionPageController(String quizName, int questionId, Question question, User user) {
        this(quizName, user);
        editMode = true;
        preFilledQuestion = question;
        this.questionId = questionId;
    }

    /**
     * initializes the controller
     */
    @Override
    @FXML
    public void initialize() {
        setBackButton(backButton);
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
        goBack();
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


}
