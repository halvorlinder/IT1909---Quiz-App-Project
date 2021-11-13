package core;

public class Score implements Comparable<Score> {

    private final String name;
    private final int points;

    public Score(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Score s) {
        return s.getPoints() - this.getPoints();
    }
}
