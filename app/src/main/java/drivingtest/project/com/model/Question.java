package drivingtest.project.com.model;

/**
 * Created by piyaponf on 11/4/2017 AD.
 */

public class Question {
    private int qid;
    private String question;
    private String image;
    private int catid;

    public Question(int qid, String question, String image, int catid) {
        this.qid = qid;
        this.question = question;
        this.image = image;
        this.catid = catid;
    }

    public int getQid() {
        return qid;
    }

    public String getQuestion() {
        return question;
    }

    public String getImage() {
        return image;
    }

    public int getCatid() {
        return catid;
    }
}
