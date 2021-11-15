package core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {

    private Leaderboard leaderboard;

    @Test
    public void testValidConstructor() {
        assertDoesNotThrow(() -> leaderboard = new Leaderboard("test", 3));
        assertEquals(3, leaderboard.getMaxScore());
        assertEquals("test", leaderboard.getName());
        assertEquals(0, leaderboard.getScoreLength());
    }

    @Test
    public void testUnvalidConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Leaderboard("test", -1));
    }

    @Test
    public void testAddScore() {
        leaderboard = new Leaderboard("test", 3);
        assertEquals(0, leaderboard.getScoreLength());
        leaderboard.addScore(new Score("test", 1));
        assertEquals(1, leaderboard.getScoreLength());
    }

    @Test
    public void testSortScores() {
        List<Score> scores = new ArrayList<>();
        List<Score> sortedScores = new ArrayList<>();
        for(int i=0; i < 10; i++){
            scores.add(new Score("test", i));
            sortedScores.add(new Score("test", 9 - i));
        }
        Leaderboard leaderboard = new Leaderboard("testQ", scores, 10);
        List<Score> actualScores = leaderboard.getScores();
        List<Score> actualSortedScores = leaderboard.getSortedScores();
        for(int i=0; i < 10; i++){
            assertEquals(scores.get(i), actualScores.get(i));
            assertEquals(sortedScores.get(i).getPoints(), actualSortedScores.get(i).getPoints());
        }
    }


}
