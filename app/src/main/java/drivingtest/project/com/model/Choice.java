package drivingtest.project.com.model;

/**
 * Created by piyaponf on 11/4/2017 AD.
 */

public class Choice {
    private int ch_id;
    private String name;
    private boolean flag;
    private int qid;
    private boolean isChecked = false;
    public Choice(int ch_id, String name, boolean flag, int qid) {
        this.ch_id = ch_id;
        this.name = name;
        this.flag = flag;
        this.qid = qid;
    }

    public int getCh_id() {
        return ch_id;
    }

    public String getName() {
        return name;
    }

    public boolean isFlag() {
        return flag;
    }

    public int getQid() {
        return qid;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
