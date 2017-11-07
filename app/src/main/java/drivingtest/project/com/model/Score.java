package drivingtest.project.com.model;

/**
 * Created by piyaponf on 11/7/2017 AD.
 */

public class Score {
    private int s_id;
    private String date;
    private int c_id;
    private int score;
    private int type;

    public Score(int s_id, String date, int c_id, int score, int type) {
        this.s_id = s_id;
        this.date = date;
        this.c_id = c_id;
        this.score = score;
        this.type = type;
    }

    public int getS_id() {
        return s_id;
    }

    public String getDate() {
        return date;
    }

    public int getC_id() {
        return c_id;
    }

    public int getScore() {
        return score;
    }

    public int getType() {
        return type;
    }
}
