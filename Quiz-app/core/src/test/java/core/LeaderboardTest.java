package core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {

    private Leaderboard leaderboard;
    private final String quizName = "test";
    private final String username = "user";

    /**
     * Test different valid constructors
     */
    @Test
    public void testValidConstructor() {
        int maxScore = 3;
        assertDoesNotThrow(() -> leaderboard = new Leaderboard(quizName, maxScore));
        assertEquals(maxScore, leaderboard.getMaxScore());
        assertEquals(quizName, leaderboard.getQuizName());
        assertEquals(0, leaderboard.getScoreLength());
    }

    /**
     * Test an invalid constructor
     */
    @Test
    public void testInvalidConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Leaderboard(quizName, -1));
    }

    /**
     * Test adding scores to a leaderboard
     */
    @Test
    public void testAddScore() {
        leaderboard = new Leaderboard(quizName, 3);
        assertEquals(0, leaderboard.getScoreLength());
        leaderboard.addScore(new Score(username, 1));
        assertEquals(1, leaderboard.getScoreLength());
    }

    /**
     * Test sorting multiple scores
     */
    @Test
    public void testSortScores() {
        int maxScore = 10;
        List<Score> scores = new ArrayList<>();
        List<Score> sortedScores = new ArrayList<>();
        for(int i=0; i < maxScore; i++){
            scores.add(new Score(username, i));
            sortedScores.add(new Score(username, maxScore - 1 - i));
        }
        Leaderboard leaderboard = new Leaderboard("testQ", scores, maxScore);
        List<Score> actualScores = leaderboard.getScores();
        List<Score> actualSortedScores = leaderboard.getSortedScores();
        for(int i=0; i < maxScore; i++){
            assertEquals(scores.get(i), actualScores.get(i));
            assertEquals(sortedScores.get(i).getPoints(), actualSortedScores.get(i).getPoints());
        }
    }
}
