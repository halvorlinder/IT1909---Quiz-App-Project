package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leaderboard {

    private final String name;
    private final List<Score> scores;
    private final int maxScore;

    /**
     * @param quizName the name of the quiz relating to this Leaderboard
     * @param scores   list of all recordes scores for this quiz
     * @param maxScore the highest score you can get on the quiz
     */
    public Leaderboard(String quizName, List<Score> scores, int maxScore) {
        this.name = quizName;
        this.scores = new ArrayList<>(scores);
        this.maxScore = maxScore;
    }

    public Leaderboard(String quizName, int maxScore) {
        this(quizName, new ArrayList<>(), maxScore);
    }

    /**
     * @return list of scores
     */
    public List<Score> getScores() {
        List<Score> copy_scores = new ArrayList<>(scores);
        return copy_scores;
    }

    public List<Score> getSortedScores() {
        List<Score> copy_scores = new ArrayList<>(scores);
        Collections.sort(copy_scores);
        return copy_scores;
    }

    public String getName() {
        return name;
    }

    public void addScore(Score score) {
        scores.add(score);
    }

    /**
     * @return number of scores
     */
    public int getScoreLength() {
        return scores.size();
    }

    /**
     * @return the max score of the quiz
     */
    public int getMaxScore() {
        return maxScore;
    }
}
