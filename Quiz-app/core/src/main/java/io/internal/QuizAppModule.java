package io.internal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Question;
import core.Quiz;

/**
 * A Jackson module for configuring JSON serialization of QuizAppModule instances.
 */
public class QuizAppModule extends SimpleModule {

    private static final String NAME = "QuizModule";

    /**
     * Initializes this QuizAppModule with appropriate serializers and deserializers.
     */
    public QuizAppModule() {
        super(NAME, Version.unknownVersion());
        addSerializer(Question.class, new QuestionSerializer());
        addDeserializer(Question.class, new QuestionDeserializer());

        addSerializer(Quiz.class, new QuizSerializer());
        addDeserializer(Quiz.class, new QuizDeserializer());
    }
}
