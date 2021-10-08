package io;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Question;
import core.Quiz;

/**
 * A Jackson module for configuring JSON serialization of TodoModel instances.
 */
public class QuizAppModule extends SimpleModule {

    private static final String NAME = "QuizModule";

    /**
     * Initializes this TodoModule with appropriate serializers and deserializers.
     */
    public QuizAppModule(boolean deepTodoModelSerializer) {
        super(NAME, Version.unknownVersion());
        addSerializer(Question.class, new QuestionSerializer());
        addSerializer(Quiz.class, new QuizSerializer());
//        addDeserializer(TodoModel.class, new TodoModelDeserializer());
    }

    public QuizAppModule() {
        this(true);
    }
}
