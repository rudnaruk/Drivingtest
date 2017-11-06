package drivingtest.project.com;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import drivingtest.project.com.base.BaseActivity;
import drivingtest.project.com.base.view.MyTextView;
import drivingtest.project.com.database.MyDatabase;
import drivingtest.project.com.model.Question;
import drivingtest.project.com.view.TestingAdapter;

public class TestActivity extends BaseActivity {
    private String TAG = "drivingtest.project.com";
    private final int MAX_COUNT = 50;

    //default cat_id is 1
    private int cat_id = 1;

    public static final int MODE_TIME_COUNTER = 1;
    public static final int MODE_NO_TIMEE = 0;

    //default mode time counter
    private int selectedMode = MODE_TIME_COUNTER;


    // minutes * 60 sec * 1000
    private final int MAX_TIME_MILLISECOND = 1*60*1000;

    ArrayList<Question> questions = new ArrayList<>();

    MyTextView tvCount;
    MyTextView tvTimer;
    RecyclerView mRecyclerView;
    View viewDivider,viewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void iniView() {

        tvTimer = findViewById(R.id.tvTimer);
        tvCount = findViewById(R.id.tvCount);
        mRecyclerView = (RecyclerView)findViewById(R.id.viewPager);
        viewDivider = findViewById(R.id.viewDivider);
        viewTimer = findViewById(R.id.viewTimer);
        tvCount.setText(getString(R.string.count_format,0,MAX_COUNT));
        tvTimer.setText(getTimeCounter(MAX_TIME_MILLISECOND));

        setUpMode();
        //load question by cat id
        LoadQuestionsTask loadQuestionsTask = new LoadQuestionsTask();
        loadQuestionsTask.execute(cat_id);

    }

    //get mode and category id from intent
    private void setUpMode(){
        Intent intent = getIntent();
        cat_id = intent.getIntExtra("cat_id",cat_id);
        selectedMode = intent.getIntExtra("time_mode",selectedMode);
        hideTimer();
    }
    @Override
    public int getView() {
        return R.layout.activity_test;
    }

    @Override
    public void onClose() {

    }
    /**
     * for hide view time counter and divider
     */
    private void hideTimer(){
        viewDivider.setVisibility(View.GONE);
        viewTimer.setVisibility(View.GONE);
    }

    /**
     * for show view time counter and divider
     */
    private void showTimer(){
        viewDivider.setVisibility(View.VISIBLE);
        viewTimer.setVisibility(View.VISIBLE);
    }

    /**
     * When Start Testing and time counter start
     */
    private void startTesting(){
        showTimer();
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

    /**
     * for async-task load question from database
     */
    private class LoadQuestionsTask extends AsyncTask<Integer,Void,List<Question>>{
        @Override
        protected List<Question> doInBackground(Integer... integers) {
            MyDatabase myDatabase = new MyDatabase(getApplicationContext());
            return myDatabase.getQuestionByCategoryId(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            TestActivity.this.questions.addAll(questions);

            //add question to recycle view
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            TestingAdapter mTestingAdapter = new TestingAdapter(questions,getApplicationContext(),mRecyclerView);
            mRecyclerView.setAdapter(mTestingAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            tvCount.setText(getString(R.string.count_format,0,questions.size()));

            //start timer after question was loaded.
            if(selectedMode == MODE_TIME_COUNTER){
                startTesting();
            }
        }
    }
}
