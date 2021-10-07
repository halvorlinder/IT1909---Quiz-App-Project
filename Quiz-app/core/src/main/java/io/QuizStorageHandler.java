package io;

import core.Question;
import core.Quiz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Handles reading and writing quizzes
 */
public class QuizStorageHandler {
    private final File file;

    /**
     * @param quizName the name of the quiz file to be handled
     * @throws IOException
     */
    public QuizStorageHandler(String quizName) throws IOException {
        file = new File(System.getProperty("user.home") + "/QuizApp/" + quizName + ".txt");
        if (!file.exists()) {
            boolean createFolders = file.getParentFile().mkdirs();
            boolean createFile = file.createNewFile();
            if (createFile && createFolders)
                System.out.println("Created a new file named " + quizName);
        }
    }

    /**
     * writes a question to the quiz of the object
     *
     * @param question the question to be written
     */
    public void writeQuestion(Question question) throws IOException {
        Quiz quiz = getQuiz();
        try (FileWriter fileWriter = new FileWriter(file)) {
            System.out.println(Arrays.toString(question.getChoices()));
            quiz.addQuestion(question);
            for (Question q : quiz.getQuestions()) {
                fileWriter.write("%s$%s$%s$%s$%s$%s\n".formatted(q.getQuestion(),
                        q.getChoice(0),
                        q.getChoice(1),
                        q.getChoice(2),
                        q.getChoice(3),
                        q.getAnswer()));
            }
            fileWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * parses a question from a csv-string
     *
     * @param questionString the string to be parsed
     * @return the Question object parsed from the string
     */
    private Question parseQuestion(String questionString) {
        String[] data = questionString.split("\\$");
        return new Question(data[0], List.of(data[1], data[2], data[3], data[4]), Integer.parseInt(data[5]));
    }

    /**
     * @return the quiz parsed from the file
     * @throws IOException
     */
    public Quiz getQuiz() throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            List<Question> questions = new ArrayList<>();
            while (scanner.hasNextLine()) {
                questions.add(parseQuestion(scanner.nextLine()));
            }
            return new Quiz(questions);
        }
    }
}
