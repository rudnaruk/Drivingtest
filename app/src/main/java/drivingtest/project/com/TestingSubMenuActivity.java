package drivingtest.project.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TestingSubMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_sub_menu);
    }

    public void btnClick(View view) {
        int i = view.getId();
        if(i==R.id.btnSubMenu1){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",6);
            intent.putExtra("test_type",TestActivity.TYPE_PRE_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_TIME_COUNTER);
            startActivity(intent);
        }else if(i==R.id.btnSubMenu2){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",7);
            intent.putExtra("test_type",TestActivity.TYPE_PRE_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_TIME_COUNTER);
            startActivity(intent);
        }
    }
}
