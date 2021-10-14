package io;

import core.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.fail;

public class QuizPersistenceTest {

    private QuizPersistence quizPersistence;

    @BeforeEach
    public void setup() throws IOException {
        quizPersistence = new QuizPersistence();
    }

    @Test
    public void testSerializersDeserializers() {
        Quiz quiz = TestHelpers.createQuizWithTwoQuestions();
        try {
            StringWriter writer = new StringWriter();
            quizPersistence.writeQuiz(quiz, writer);
            String json = writer.toString();
            Quiz quiz2 = quizPersistence.readQuiz(new StringReader(json));
            TestHelpers.checkQuiz(quiz, quiz2);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}

