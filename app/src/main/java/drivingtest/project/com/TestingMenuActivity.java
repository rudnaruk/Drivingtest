package drivingtest.project.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TestingMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_menu);
    }

    public void btnClick(View view) {
        int i = view.getId();
        if(i==R.id.btnMenu1){
            Intent intent = new Intent(this, PosttestMenuActivity.class);
            startActivity(intent);
        }else if(i==R.id.btnMenu2){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",8);
            intent.putExtra("test_type",TestActivity.TYPE_PRE_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMER);
            startActivity(intent);
        }else if(i==R.id.btnMenu3){
            Intent intent = new Intent(this, TestingSubMenuActivity.class);
            startActivity(intent);
        }else if(i==R.id.btnMenu4){
            Intent intent = new Intent(this, SummaryMenuActivity.class);
            startActivity(intent);
        }
    }
}
