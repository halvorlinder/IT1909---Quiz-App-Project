package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    private Score score;
    private final String username = "user";

    /**
     * Test constructor with negative score
     */
    @Test
    public void testInvalidConstructor(){
        assertThrows(IllegalArgumentException.class, () -> new Score(username, -1));
    }

    /**
     * Test constructor with valid arguments
     */
    @Test
    public void testValidConstructor(){
        assertDoesNotThrow(() -> score = new Score(username, 1));
        assertEquals(username, score.getName());
        assertEquals(1, score.getPoints());
    }

    /**
     * Test comparing to score elements
     */
    @Test
    public void testCompareScores() {
        Score largeScore = new Score(username, 5);
        Score smallScore = new Score(username, 1);
        Score smallScore2 = new Score("notTest", 1);
        assertEquals(4, smallScore.compareTo(largeScore));
        assertEquals(smallScore, smallScore2);
    }
}
