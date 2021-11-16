package io.internal;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.*;

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

        addSerializer(Leaderboard.class, new LeaderboardSerializer());
        addDeserializer(Leaderboard.class, new LeaderboardDeserializer());

        addSerializer(Score.class, new ScoreSerializer());
        addDeserializer(Score.class, new ScoreDeserializer());

        addSerializer(UserData.class, new UserDataSerializer());
        addDeserializer(UserData.class, new UserDataDeserializer());

        addSerializer(UserRecord.class, new UserSerializer());
        addDeserializer(UserRecord.class, new  UserDeserializer());
    }
}
