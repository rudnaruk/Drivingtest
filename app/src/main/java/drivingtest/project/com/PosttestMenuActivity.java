package drivingtest.project.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PosttestMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posttest_menu);
    }

    public void btnClick(View view) {
        int i = view.getId();
        if(i==R.id.btnPostMenu1){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",1);
            intent.putExtra("test_type",TestActivity.TYPE_POST_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMER);
            startActivity(intent);
        }else if(i==R.id.btnPostMenu2){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",2);
            intent.putExtra("test_type",TestActivity.TYPE_POST_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMER);
            startActivity(intent);
        }else if(i==R.id.btnPostMenu3){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",3);
            intent.putExtra("test_type",TestActivity.TYPE_POST_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMER);
            startActivity(intent);
        }else if(i==R.id.btnPostMenu4){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",4);
            intent.putExtra("test_type",TestActivity.TYPE_POST_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMER);
            startActivity(intent);
        }else if(i==R.id.btnPostMenu5){
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("cat_id",5);
            intent.putExtra("test_type",TestActivity.TYPE_POST_TEST);
            intent.putExtra("time_mode", TestActivity.MODE_NO_TIMER);
            startActivity(intent);
        }
    }
}
