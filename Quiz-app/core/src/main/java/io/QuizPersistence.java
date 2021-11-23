package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Quiz;
import io.internal.QuizAppModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class QuizPersistence {
    private final ObjectMapper mapper;
    private final String basePath;

    /**
     * Inits a new QuizPersistence Object
     *
     * @throws IOException
     */
    public QuizPersistence() throws IOException {
        basePath = SavePaths.getBasePath() + "Quizzes/";
        mapper = createObjectMapper();
        Path path = Path.of(basePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * @return an ObjectMapper for handling quizzes
     */
    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(createJacksonModule());
    }

    /**
     * @return a new QuizAppModule
     */
    public static SimpleModule createJacksonModule() {
        return new QuizAppModule();
    }

    /**
     * @param reader a Reader containing a file with a Quiz
     * @return a Quiz object read from the Reader
     * @throws IOException
     */
    public Quiz readQuiz(Reader reader) throws IOException {
        return mapper.readValue(reader, Quiz.class);
    }

    /**
     * writes a Quiz object to the file
     *
     * @param quiz   the Quiz to be written
     * @param writer the Writer containing the file
     * @throws IOException
     */
    public void writeQuiz(Quiz quiz, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, quiz);
    }

    /**
     * Loads a quiz from the saved file (saveFilePath) in the user.home folder.
     *
     * @param quizName the name of the quiz
     * @return the quiz with the given name
     */
    public Quiz loadQuiz(String quizName) throws IOException {
        if (!Files.exists(Path.of(basePath + quizName + ".json")))
            throw new FileNotFoundException();
        try (Reader reader = new FileReader(basePath + quizName + ".json", StandardCharsets.UTF_8)) {
            return readQuiz(reader);
        }
    }

    /**
     * Saves a Quiz to the saveFilePath in the user.home folder.
     *
     * @param quiz the quiz to save
     */
    public void saveQuiz(Quiz quiz) throws IOException {
        String quizName = quiz.getName();
        File file = new File(basePath + quizName + ".json");
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException("Failed to create file");
        }
        try (Writer writer = new FileWriter(basePath + quizName + ".json", StandardCharsets.UTF_8)) {
            writeQuiz(quiz, writer);
        }
    }

    /**
     * @return a list containing all available quiz names
     */
    public List<String> getListOfQuizNames() throws IOException {
        List<String> listOfFileNames = new ArrayList<>();
        File directory = new File(basePath);
        if (!directory.exists())
            Files.createDirectories(Path.of(basePath));
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files)
            if (file.isFile())
                listOfFileNames.add(file.getName().replace(".json", ""));

        return listOfFileNames;
    }

    /**
     * deletes a quiz given its name
     *
     * @param quizName the name of the quiz
     */
    public void deleteQuiz(String quizName) throws IOException {
        File file = new File(basePath + quizName + ".json");
        if (!file.exists())
            throw new FileNotFoundException();
        if (!file.delete())
            throw new IOException("Failed to delete file");
    }

}
