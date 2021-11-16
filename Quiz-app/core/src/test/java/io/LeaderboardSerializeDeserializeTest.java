package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Leaderboard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardSerializeDeserializeTest {

    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = LeaderboardPersistence.createObjectMapper();
    }

    final static String leaderboardWithTwoScores =
            """
                    {
                      "name" : "test",
                      "maxScore" : 2,
                      "scores" : [ {
                        "name" : "oskar",
                        "points" : 2
                      }, {
                        "name" : "halvor",
                        "points" : 0
                      } ]
                    }
                                 
                     """;

    @Test
    public void testSerializers() {
        Leaderboard leaderboard = createLeaderboardWithTwoScore();
        try {
            assertEquals(leaderboardWithTwoScores.replaceAll("\\s+", ""),
                    mapper.writeValueAsString(leaderboard));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeserializers() {
        try {
            Leaderboard leaderboard = mapper.readValue(leaderboardWithTwoScores, Leaderboard.class);
            checkLeaderboard(leaderboard, createLeaderboardWithTwoScore());
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }
}
