package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Leaderboard;
import core.Score;

import java.io.IOException;

class LeaderboardSerializer extends JsonSerializer<Leaderboard> {

    @Override
    public void serialize(Leaderboard leaderboard, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", leaderboard.getName());
        jsonGen.writeNumberField("maxScore", leaderboard.getMaxScore());
        jsonGen.writeArrayFieldStart("scores");
        for (Score score : leaderboard.getScores()) {
            jsonGen.writeObject(score);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }

}
