package drivingtest.project.com;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import drivingtest.project.com.base.MyTextView;
import drivingtest.project.com.base.BaseActivity;
import drivingtest.project.com.database.MyDatabase;
import drivingtest.project.com.model.Category;

public class TestActivity extends BaseActivity {
    private String TAG = "drivingtest.project.com";
    private final int MAX_COUNT = 50;

    // minutes * 60 sec * 1000
    private final int MAX_TIME_MILLISECOND = 1*60*1000;

    MyTextView tvCount;
    MyTextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void iniView() {
        tvTimer = findViewById(R.id.tvTimer);
        tvCount = findViewById(R.id.tvCount);

        tvCount.setText(getString(R.string.count_format,0,MAX_COUNT));
        tvTimer.setText(getTimeCounter(MAX_TIME_MILLISECOND));

        // start Test
        startTesting();
        MyDatabase myDatabase = new MyDatabase(this);
        for(Category cat :myDatabase.getCategories()){
            Log.d(TAG,cat.getName() + " ("+cat.getCatid()+")");
        }
    }

    @Override
    public int getView() {
        return R.layout.activity_test;
    }

    @Override
    public void onClose() {

    }

    /**
     * When Start Testing and time counter start
     */
    private void startTesting(){
        new CountDownTimer(MAX_TIME_MILLISECOND, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(getTimeCounter(millisUntilFinished));
            }

            public void onFinish() {
                tvTimer.setText(getTimeCounter(0));
                onTimeUp();
            }
        }.start();
    }


    /**
     * When Time up then calculate score
     */
    private void onTimeUp(){

    }

    /**
     * for convert time milliseconds to string mm:ss format
     * @param millis
     * @return String mm:ss format
     */
    public String getTimeCounter(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        return String.format("%s:%s %s",intToTwoDigitsString(minutes),intToTwoDigitsString(seconds), getText(R.string.seconds));
    }


    /**
     * @param number
     * @return 2 digits String of number
     */
    public String intToTwoDigitsString(int number){
        if(Math.abs(number)<10){
            return "0"+number;
        }else{
            return ""+number;
        }
    }
}
