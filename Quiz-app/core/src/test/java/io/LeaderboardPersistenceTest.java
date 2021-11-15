package io;

import core.Leaderboard;
import core.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardPersistenceTest {

        private LeaderboardPersistence leaderboardPersistence;

        @BeforeEach
        public void setup() throws IOException {
            leaderboardPersistence = new LeaderboardPersistence();
        }

        @Test
        public void testSerializersDeserializers() {
            Leaderboard leaderboard1 = TestHelpers.createLeaderboardWithTwoScore();
            try {
                StringWriter writer = new StringWriter();
                leaderboardPersistence.writeLeaderboard(leaderboard1, writer);
                String json = writer.toString();
                Leaderboard leaderboard2 = leaderboardPersistence.readLeaderboard(new StringReader(json));
                TestHelpers.checkLeaderboard(leaderboard1, leaderboard2);

            } catch (IOException e) {
                fail(e.getMessage());
            }
        }
}
