package drivingtest.project.com;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import drivingtest.project.com.base.BaseActivity;
import drivingtest.project.com.base.view.MyTextView;
import drivingtest.project.com.database.MyDatabase;
import drivingtest.project.com.model.Choice;
import drivingtest.project.com.model.Question;
import drivingtest.project.com.model.Score;
import drivingtest.project.com.view.MyDialog;
import drivingtest.project.com.view.TestingAdapter;

public class TestActivity extends BaseActivity implements TestingAdapter.OnDoQuestionListener {
    private String TAG = "drivingtest.project.com";
    private final int MAX_COUNT = 20;

    //default cat_id is 1
    private int cat_id = 1;


    public static final int MODE_TIME_COUNTER = 1;
    public static final int MODE_NO_TIMER = 0;

    //type pre&post-test
    public static final int TYPE_PRE_TEST = 3;
    public static final int TYPE_POST_TEST = 4;

    //default mode time counter
    private int selectedMode = MODE_TIME_COUNTER;

    //default type pretest
    private int selectedType = TYPE_PRE_TEST;

    // minutes * 60 sec * 1000
    private final int MAX_TIME_MILLISECOND = 1*60*1000;

    ArrayList<Question> questions = new ArrayList<>();

    private MyTextView tvCount;
    private MyTextView tvTimer;
    private RecyclerView mRecyclerView;
    private View viewDivider,viewTimer;
    private Button btnCalculate;
    private CountDownTimer countDownTimer;

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
        btnCalculate = (Button)findViewById(R.id.btnCalculate);
        btnCalculate.setEnabled(false);

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
        selectedType = intent.getIntExtra("test_type",selectedType);
        hideTimer();
    }
    @Override
    public int getView() {
        return R.layout.activity_test;
    }

    @Override
    public void onClose() {
        if(countDownTimer !=null) {
            countDownTimer.cancel();
        }
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
        countDownTimer = new CountDownTimer(MAX_TIME_MILLISECOND, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(getTimeCounter(millisUntilFinished));
            }

            public void onFinish() {
                tvTimer.setText(getTimeCounter(0));
                onTimeUp();
            }
        };
        countDownTimer.start();
    }


    /**
     * When Time up then calculate score
     */
    private void onTimeUp(){
        calulateScore(null);
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

    @Override
    public void onDoQuestion() {
        int count = 0;
        for(Question question : questions){
            if(question.getSelectedChoiceId()!=-1){
                count++;
            }
        }
        tvCount.setText(getString(R.string.count_format,count,questions.size()));
        if(count == questions.size()){
            testDone();
        }
    }

    /**
     * When testing done
     */
    private void testDone(){
        btnCalculate.setEnabled(true);
    }

    //when time up or user click finish
    public void calulateScore(View view) {
        if(countDownTimer !=null) {
            countDownTimer.cancel();
        }
        saveScore();
        final MyDialog myDialog = MyDialog.getInstance();
        myDialog.setOnOkClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismissDialog();
                finish();
            }
        });
        if(isPass()) {
            myDialog.showDialog(this, "Test Result", getString(R.string.your_score_pass, calculateScore()));
        }else{
            myDialog.showDialog(this, "Test Result", getString(R.string.your_score, calculateScore()));
        }
    }

    /**
     * for async-task load question from database
     */
    private class LoadQuestionsTask extends AsyncTask<Integer,Void,List<Question>>{
        @Override
        protected List<Question> doInBackground(Integer... integers) {
            MyDatabase myDatabase = new MyDatabase(getApplicationContext());
            int catID= integers[0];
            if(catID == 8){
                if(selectedMode == MODE_TIME_COUNTER) {
                    return myDatabase.getAllQuestion(50);
                }else{
                    return myDatabase.getAllQuestion(30);
                }
            }
            return myDatabase.getQuestionByCategoryId(catID,MAX_COUNT);
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            TestActivity.this.questions.addAll(questions);

            //add question to recycle view
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            TestingAdapter mTestingAdapter = new TestingAdapter(questions,getApplicationContext(),mRecyclerView);
            mTestingAdapter.setOnDoQuestionListener(TestActivity.this);
            mRecyclerView.setAdapter(mTestingAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            tvCount.setText(getString(R.string.count_format,0,questions.size()));

            //start timer after question was loaded.
            if(selectedMode == MODE_TIME_COUNTER){
                final MyDialog myDialog = MyDialog.getInstance();
                myDialog.setOnOkClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismissDialog();
                        startTesting();
                    }
                });
                myDialog.showDialog(TestActivity.this,getString(R.string.start),getString(R.string.start_text));

            }
        }
    }

    private int calculateScore(){
        int i = 0;
        for(Question question: questions){
            for(Choice choice : question.getChoices()){
                if(choice.isFlag() && choice.isChecked()){
                    i++;
                }
            }
        }
        return i;
    }
    private boolean isPass(){
        int i = 0;
        for(Question question: questions){
            for(Choice choice : question.getChoices()){
                if(choice.isFlag() && choice.isChecked()){
                    i++;
                }
            }
        }
        int total = questions.size()+1;
        int p = i*100/total;
        return p>=90;
    }

    private void saveScore(){
        SaveScoreTask saveScoreTask = new SaveScoreTask();
        saveScoreTask.execute(new Score(0,"",cat_id,calculateScore(),selectedType));
    }

    private class SaveScoreTask extends AsyncTask<Score, Void, Void> {
        @Override
        protected Void doInBackground(Score... scores) {
            MyDatabase myDatabase = new MyDatabase(getApplicationContext());
            myDatabase.saveScore(scores[0]);
            return null;
        }

    }

    }
