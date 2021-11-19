package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a leaderboard for a quiz
 */
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
        if (maxScore < 0) {
            throw new IllegalArgumentException("Cannot have a negative max score");
        }
        this.name = quizName;
        this.scores = new ArrayList<>(scores);
        this.maxScore = maxScore;
    }

    /**
     * @param quizName the name of the quiz relating to this Leaderboard
     * @param maxScore the highest score you can get on the quiz
     */
    public Leaderboard(String quizName, int maxScore) {
        this(quizName, new ArrayList<>(), maxScore);
    }

    /**
     * @return list of scores
     */
    public List<Score> getScores() {
        return new ArrayList<>(scores);
    }

    /**
     * function that sorts the score objects by their points
     *
     * @return a list of the scores sorted (in descending order)
     */
    public List<Score> getSortedScores() {
        List<Score> copyScores = new ArrayList<>(scores);
        Collections.sort(copyScores);
        return copyScores;
    }

    /**
     * @return the name of the quiz
     */
    public String getName() {
        return name;
    }

    /**
     * @param score the score object to be added
     */
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
