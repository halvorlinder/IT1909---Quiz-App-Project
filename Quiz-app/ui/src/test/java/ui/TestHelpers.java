package ui;

import io.Paths;

import java.io.IOException;
import java.nio.file.Files;

public class TestHelpers {

    public static void deleteQuiz(String name) {
        String fileName = Paths.getBasePath() + "Quizzes/"+name+".json";
        try {
            Files.delete(java.nio.file.Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
