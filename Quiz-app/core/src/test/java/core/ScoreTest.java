package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    private Score score;

    @Test
    public void testInvalidConstructor(){
        assertThrows(IllegalArgumentException.class, () -> new Score("test", -1));
    }

    @Test
    public void testValidConstructor(){
        assertDoesNotThrow(() -> score = new Score("test", 1));
        assertEquals("test", score.getName());
        assertEquals(1, score.getPoints());
    }

    @Test
    public void compareScores() {
        Score largeScore = new Score("test", 5);
        Score smallScore = new Score("test", 1);
        Score smallScore2 = new Score("notTest", 1);
        assertEquals(4, smallScore.compareTo(largeScore));
        assertTrue(smallScore.equals(smallScore2));
    }
}
