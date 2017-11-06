package drivingtest.project.com.model;

import java.util.ArrayList;

/**
 * Created by piyaponf on 11/4/2017 AD.
 */

public class Question {
    private int qid;
    private String question;
    private String image;
    private int catid;
    private int selectedChoiceId = -1;
    private ArrayList<Choice> choices;
    private int position;
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

    public int getSelectedChoiceId() {
        return selectedChoiceId;
    }

    public void setSelectedChoiceId(int selectedChoiceId) {
        this.selectedChoiceId = selectedChoiceId;
    }

    public ArrayList<Choice> getChoices() {
        if(choices==null)
            choices = new ArrayList<>();
        return choices;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
