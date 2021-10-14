package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Quiz;
import core.UserData;
import io.internal.QuizAppModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class UserPersistence {
    private final ObjectMapper mapper;
    private static final String BASE_PATH = System.getProperty("user.home") + "/QuizApp/";

    /**
     * Inits a new QuizPersistence Object
     *
     * @throws IOException
     */
    public UserPersistence() throws IOException {
        mapper = createObjectMapper();
        if (!Files.exists(Path.of(BASE_PATH))) {
            Files.createDirectory(Path.of(BASE_PATH));
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
    public UserData readUserData(Reader reader) throws IOException {
        return mapper.readValue(reader, UserData.class);
    }

    /**
     * writes a Quiz object to the file
     *
     * @param userData   the Quiz to be written
     * @param writer the Writer containing the file
     * @throws IOException
     */
    public void writeUserData(UserData userData, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, userData);
    }

    /**
     * Loads a QuizAppModule from the saved file (saveFilePath) in the user.home folder.
     *
     * @return the loaded QuizAppModule
     */
    public UserData loadUserData() throws IOException {
        try (Reader reader = new FileReader(BASE_PATH + "users.json", StandardCharsets.UTF_8)) {
            return readUserData(reader);
        }catch (IOException ioException){
            return new UserData();
        }
    }

    /**
     * Saves a Quiz to the saveFilePath in the user.home folder.
     *
     * @param userData the quiz to save
     */
    public void saveUserData(UserData userData) throws IOException {
        File file = new File(BASE_PATH + "users.json");
        if (!file.exists()) {
            boolean junk = file.createNewFile();
        }
        try (Writer writer = new FileWriter(BASE_PATH + "users.json", StandardCharsets.UTF_8)) {
            writeUserData(userData, writer);
        }
    }
    
}
