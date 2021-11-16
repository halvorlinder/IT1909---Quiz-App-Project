package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Leaderboard;
import core.Score;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class LeaderboardDeserializer extends JsonDeserializer<Leaderboard> {

    private final ScoreDeserializer scoreDeserializer = new ScoreDeserializer();

    @Override
    public Leaderboard deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    Leaderboard deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            JsonNode textNode = objectNode.get("name");
            JsonNode maxScoreNode = objectNode.get("maxScore");
            String name = textNode.asText();
            int maxScore = maxScoreNode.asInt();
            List<Score> scoreList = new ArrayList<>();
            JsonNode scoresNode = objectNode.get("scores");
            boolean hasQuestions = scoresNode instanceof ArrayNode;
            if (hasQuestions) {
                for (JsonNode scoreNode : scoresNode) {
                    Score score = scoreDeserializer.deserialize(scoreNode);
                    if (score != null) {
                        scoreList.add(score);
                    }
                }
            }
            return new Leaderboard(name, scoreList, maxScore);

        }
        return null;
    }
}
