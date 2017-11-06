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
        Intent intent = new Intent(this, TestActivity.class);
        if(i == R.id.button) {
            intent.putExtra("cat_id",1);
            intent.putExtra("time_mode", TestActivity.MODE_TIME_COUNTER);
        }else{
            intent.putExtra("cat_id",1);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMEE);
        }
        startActivity(intent);
    }
}
