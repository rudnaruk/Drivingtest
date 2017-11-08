package drivingtest.project.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SummaryMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_menu);
    }

    public void btnClick(View view) {
        int i = view.getId();
        if(i==R.id.btnSumMenu1){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",1);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu2){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",2);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu3){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",3);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu4){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",4);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu5){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",5);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu6){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",6);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu7){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",7);
            startActivity(intent);
        }else if(i==R.id.btnSumMenu8){
            Intent intent = new Intent(this, SummaryScoreActivity.class);
            intent.putExtra("cat_id",8);
            startActivity(intent);
        }
    }
}
