package drivingtest.project.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String TAG = "drivingtest.project.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goTesting(View view) {
        int i = view.getId();
        if(i == R.id.button) {
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",1);
            intent.putExtra("test_type",TestActivity.TYPE_PRE_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_TIME_COUNTER);
            startActivity(intent);
        }else if(i == R.id.button1){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",1);
            intent.putExtra("test_type",TestActivity.TYPE_POST_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMEE);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            startActivity(intent);
        }
    }
}
