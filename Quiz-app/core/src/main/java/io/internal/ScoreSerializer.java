package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Score;
import io.constants.JsonKeys;

import java.io.IOException;

/**
 * This class serializes a Score object into a JSON file
 */
class ScoreSerializer extends JsonSerializer<Score> {

    @Override
    public void serialize(Score score, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField(JsonKeys.SCORE_NAME, score.getName());
        jsonGen.writeNumberField(JsonKeys.SCORE_POINTS, score.getPoints());
        jsonGen.writeEndObject();
    }

}
