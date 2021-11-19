package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Score;

import java.io.IOException;

/**
 * This class deserializes a JSON file to a Score object
 */
class ScoreDeserializer extends JsonDeserializer<Score> {

    @Override
    public Score deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserialize(jsonParser.getCodec().readTree(jsonParser));
    }

    Score deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            JsonNode nameTextNode = objectNode.get("name");
            JsonNode correctAnswerNode = objectNode.get("points");
            String name = nameTextNode.asText();
            int points = correctAnswerNode.asInt();
            return new Score(name, points);
        }
        return null;
    }
}