package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Question;
import core.Quiz;
import io.constants.JsonKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class QuizDeserializer extends JsonDeserializer<Quiz> {

    private final QuestionDeserializer questionDeserializer = new QuestionDeserializer();

    @Override
    public Quiz deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    Quiz deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            JsonNode textNode = objectNode.get(JsonKeys.QUIZ_NAME);
            String name = textNode.asText();
            JsonNode creatorNode = objectNode.get(JsonKeys.QUIZ_CREATOR);
            String creator = creatorNode.asText();
            List<Question> questionList = new ArrayList<>();
            JsonNode questionsNode = objectNode.get(JsonKeys.QUIZ_QUESTIONS);
            boolean hasQuestions = questionsNode instanceof ArrayNode;
            if (hasQuestions) {
                for (JsonNode questionNode : questionsNode) {
                    Question question = questionDeserializer.deserialize(questionNode);
                    if (question != null) {
                        questionList.add(question);
                    }
                }
            }
            return new Quiz(name, questionList, creator);

        }
        return null;
    }
}
