package drivingtest.project.com.model;

/**
 * Created by piyaponf on 11/4/2017 AD.
 */

public class Category {
    private int catid;
    private String name;

    public Category(int catid, String name) {
        this.catid = catid;
        this.name = name;
    }

    public int getCatid() {
        return catid;
    }

    public String getName() {
        return name;
    }
}
