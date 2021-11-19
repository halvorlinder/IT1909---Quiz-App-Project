package core;

import java.util.Objects;

/**
 * This class represents a score for a leaderboard
 */
public class Score implements Comparable<Score> {

    private final String name;
    private final int points;

    /**
     * @param name   of person who got score
     * @param points amount of correct answers
     */
    public Score(String name, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Cannot have a negative score");
        }
        this.name = name;
        this.points = points;
    }

    /**
     * @return points (correct answers)
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * @param s the score to be comared
     * @return whether or not this score is larger,
     * equal to or smaller than another score object
     */
    @Override
    public int compareTo(Score s) {
        return s.getPoints() - this.getPoints();
    }

    /**
     * @param o an object
     * @return if o is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return points == score.points;
    }

    /**
     * hashes this object based on its points value
     *
     * @return the object hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
