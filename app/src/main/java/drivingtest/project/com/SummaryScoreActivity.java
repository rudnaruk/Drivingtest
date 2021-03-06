package drivingtest.project.com;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import drivingtest.project.com.base.BaseActivity;
import drivingtest.project.com.base.view.MyTextView;
import drivingtest.project.com.database.MyDatabase;
import drivingtest.project.com.model.Category;
import drivingtest.project.com.model.Score;
import drivingtest.project.com.view.ScoreAdapter;

/**
 * Created by piyaponf on 11/7/2017 AD.
 */

public class SummaryScoreActivity extends BaseActivity{
    //default cat_id is 1
    private int cat_id = 1;
    private Category selectedCategory;
    private ArrayList<Score> scores;
    private GraphView graph;
    private MyTextView tvCatName;
    private View viewDetail;
    private ListView lv;
    private NestedScrollView nestedScrollView;

    @Override
    public void iniView() {
        setUpMode();
        tvCatName = (MyTextView) findViewById(R.id.tvCatName);
        MyDatabase myDatabase = new MyDatabase(getApplicationContext());
        selectedCategory = myDatabase.getCategoriesByCategoryId(cat_id);
        tvCatName.setText(selectedCategory.getName());
        scores = (ArrayList<Score>) myDatabase.getScoreByCategoryId(cat_id);
        graph = (GraphView) findViewById(R.id.graph);
        viewDetail = findViewById(R.id.viewDetail);
        lv = (ListView)findViewById(R.id.itemlv);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.itemlv).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        lv.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        if(cat_id >5){
            viewDetail.setVisibility(View.GONE);
        }
        final DataPoint[] dataPoints = new DataPoint[scores.size()+1];
        for(int i=0;i<scores.size();i++){
            Score score = scores.get(i);
//          Date d1 = stringToDate(score.getDate(),"yyyy-MM-dd HH:mm:ss");
            dataPoints[i] = new DataPoint(i+1, score.getScore());
        }
        dataPoints[scores.size()] = new DataPoint(scores.size()+1, 0);

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(cat_id>5){
                    return Color.rgb(16,200, 72);
                }
                for(int i=0;i<scores.size();i++) {
                    DataPoint dataPoint = dataPoints[i];
                    if (data == dataPoint && scores.get(i).getType()==TestActivity.TYPE_PRE_TEST) {
                        return Color.rgb(238,128, 44);
                    }
                }
                return Color.rgb(255,64, 129);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

//        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(scores.size()); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(scores.size()+1);
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(true);

        ScoreAdapter scoreAdapter = new ScoreAdapter(this,0,scores,cat_id);
        LayoutInflater inflater = getLayoutInflater();

        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_table_score_layout, lv, false);
        lv.addHeaderView(header, null, false);
        lv.setAdapter(scoreAdapter);
    }

    //get mode and category id from intent
    private void setUpMode(){
        Intent intent = getIntent();
        cat_id = intent.getIntExtra("cat_id",cat_id);
    }

    private Date getBoundDate(Date dtStartDate,int added){
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy HH:mm");
        Calendar c = Calendar.getInstance();
        c.setTime(dtStartDate);
        c.add(Calendar.MINUTE, added);  // number of days to add
        return new Date(c.getTimeInMillis());
    }
    @Override
    public int getView() {
        return R.layout.activity_summary_score;
    }

    @Override
    public void onClose() {

    }

    private Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
