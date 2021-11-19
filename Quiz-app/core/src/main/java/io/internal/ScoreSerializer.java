package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Score;

import java.io.IOException;

class ScoreSerializer extends JsonSerializer<Score> {

    @Override
    public void serialize(Score score, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", score.getName());
        jsonGen.writeNumberField("points", score.getPoints());
        jsonGen.writeEndObject();
    }

}
