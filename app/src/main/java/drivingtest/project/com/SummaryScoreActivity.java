package drivingtest.project.com;

import android.content.Intent;

import java.util.ArrayList;

import drivingtest.project.com.base.BaseActivity;
import drivingtest.project.com.database.MyDatabase;
import drivingtest.project.com.model.Category;
import drivingtest.project.com.model.Score;

/**
 * Created by piyaponf on 11/7/2017 AD.
 */

public class SummaryScoreActivity extends BaseActivity{
    //default cat_id is 1
    private int cat_id = 1;
    private Category selectedCategory;
    private ArrayList<Score> scores;
    @Override
    public void iniView() {
        MyDatabase myDatabase = new MyDatabase(getApplicationContext());
        selectedCategory = myDatabase.getCategoriesByCategoryId(cat_id);
        scores = (ArrayList<Score>) myDatabase.getScoreByCategoryId(cat_id);
    }

    //get mode and category id from intent
    private void setUpMode(){
        Intent intent = getIntent();
        cat_id = intent.getIntExtra("cat_id",cat_id);
    }

    @Override
    public int getView() {
        return R.layout.activity_summary_score;
    }

    @Override
    public void onClose() {

    }
}
