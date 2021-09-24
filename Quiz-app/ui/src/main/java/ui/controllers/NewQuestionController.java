package ui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import core.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.App;

public class NewQuestionController {


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
    private ToggleGroup options;

    private Question question;
    private List<TextField> listOfTextFields;
    private List<RadioButton> listOfRadioButtons;
    private List<String> listOfAnswers;

    // App.setRoot needs to be completed
    // All FXML files need to be created and named accordingly

    public void initialize(){
        listOfTextFields = List.of(choice1,choice2,choice3,choice4);
        listOfAnswers = listOfTextFields.stream().map(textField -> textField.getText()).collect(Collectors.toList());
        listOfRadioButtons = List.of(radioButton1, radioButton2, radioButton3, radioButton4);
        listOfRadioButtons.forEach(radio -> radio.setOnAction(ae -> submitButton.setDisable(false)));

        submitButton.setDisable(true);

    }

    @FXML
    public void submitQuestion() throws IOException { //Takes you back to the home page

        for (TextField textField : listOfTextFields) {
            if(!checkIfTextfieldEmpty(textField)){
                throw new IllegalStateException("You have to fill out all the options before you can submit!");
            }
        }
        if(!checkIfTextAreaEmpty(questionText)){
            throw new IllegalStateException("Du må skrive inn et spørsmål");
        }
        question = new Question(getTextFromTA(questionText),getListOfAnswers(),getCheckedId());
        App.setRoot("HomePage.FXML");
    }

    private boolean checkIfTextfieldEmpty(TextField textfield) {
        return !textfield.getText().isEmpty();
    }
        
    private boolean checkIfTextAreaEmpty(TextArea question){
        return !question.getText().isEmpty();
        }

    public String getTextFromTF(TextField textfield){
        return textfield.getText();
    }

    public String getTextFromTA(TextArea textArea){
        return textArea.getText();
    }

    public int getCheckedId(){
        return options.getToggles().indexOf(options.getSelectedToggle());
    }

    public List<String> getListOfAnswers(){
        return listOfAnswers;
    }

}
