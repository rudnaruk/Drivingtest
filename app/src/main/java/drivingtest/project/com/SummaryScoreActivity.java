package drivingtest.project.com;

import android.content.Intent;
import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private GraphView graph;

    @Override
    public void iniView() {
        MyDatabase myDatabase = new MyDatabase(getApplicationContext());
        selectedCategory = myDatabase.getCategoriesByCategoryId(cat_id);
        scores = (ArrayList<Score>) myDatabase.getScoreByCategoryId(cat_id);
        graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[scores.size()];
        for(int i=0;i<scores.size();i++){
            Score score = scores.get(i);
            Date d1 = stringToDate(score.getDate(),"EEE MMM d HH:mm:ss zz yyyy");
            dataPoints[i] = new DataPoint(d1, score.getScore());
        }

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(5);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(10); // only 4 because of the space

// set manual x bounds to have nice steps
//        graph.getViewport().setMinX(d1.getTime());
//        graph.getViewport().setMaxX(d11.getTime());
//        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
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

    private Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
