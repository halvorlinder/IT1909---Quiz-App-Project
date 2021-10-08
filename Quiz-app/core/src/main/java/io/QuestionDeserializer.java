package io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDeserializer extends JsonDeserializer<Question> {
    @Override
    public Question deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return deserialize(jsonParser.getCodec().readTree(jsonParser));
    }

    Question deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            JsonNode questionTextNode = objectNode.get("questionText");
            JsonNode correctAnswerNode = objectNode.get("correctAnswer");
            String questionText = questionTextNode.asText();
            int correctAnswer = correctAnswerNode.asInt();
            List<String> choiceList = new ArrayList<>();
            JsonNode choicesNode = objectNode.get("choices");
            boolean hasChoices = choicesNode instanceof ArrayNode;
            if (hasChoices) {
                for (JsonNode choiceNode : choicesNode) {
                    String choice = choiceNode.asText();
                    if (choice != null) {
                        choiceList.add(choice);
                    }
                }
            }
            return new Question(questionText, choiceList, correctAnswer);

        }
        return null;
    }
}
