package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Leaderboard;
import io.internal.QuizAppModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LeaderboardPersistence {
    private final ObjectMapper mapper;
    private final String basePath;

    /**
     * Inits a new LeaderboardPersistence Object
     *
     * @throws IOException
     */
    public LeaderboardPersistence() throws IOException {
        basePath = SavePaths.getBasePath() + "leaderboards/";
        mapper = createObjectMapper();
        Path path = Path.of(basePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * @return an ObjectMapper for handling leaderboards
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
     * @param reader a Reader containing a file with a Leaderboard
     * @return a Leaderboard object read from the Reader
     * @throws IOException
     */
    public Leaderboard readLeaderboard(Reader reader) throws IOException {
        return mapper.readValue(reader, Leaderboard.class);
    }

    /**
     * writes a Leaderboard object to the file
     *
     * @param leaderboard the Leaderboard to be written
     * @param writer      the Writer containing the file
     * @throws IOException
     */
    public void writeLeaderboard(Leaderboard leaderboard, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, leaderboard);
    }

    /**
     * Loads a QuizAppModule from the saved file (saveFilePath) in the user.home folder.
     *
     * @param quizName the name of the quiz
     * @return the loaded QuizAppModule
     */
    public Leaderboard loadLeaderboard(String quizName) throws IOException {
        if (!Files.exists(Path.of(basePath + quizName + ".json")))
            throw new IOException();
        try (Reader reader = new FileReader(basePath + quizName + ".json", StandardCharsets.UTF_8)) {
            return readLeaderboard(reader);
        } catch (IOException e) {
            return new Leaderboard(quizName, List.of(), 0);
        }
    }

    /**
     * Saves a Leaderboard to the saveFilePath in the user.home folder.
     *
     * @param leaderboard the quiz to save
     */
    public void saveLeaderboard(Leaderboard leaderboard) throws IOException {
        String quizName = leaderboard.getName();
        File file = new File(basePath + quizName + ".json");
        if (!file.exists()) {

            boolean junk = file.createNewFile();
        }
        try (Writer writer = new FileWriter(basePath + quizName + ".json", StandardCharsets.UTF_8)) {
            writeLeaderboard(leaderboard, writer);
        }
    }

    /**
     * deletes a leaderboard given its name
     *
     * @param quizName the name of the quiz
     * @return true if successful, false otherwise
     */
    public boolean deleteLeaderboard(String quizName) {
        File file = new File(basePath + quizName + ".json");
        return file.delete();
    }

}
