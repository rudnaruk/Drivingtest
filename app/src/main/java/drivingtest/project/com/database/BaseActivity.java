package drivingtest.project.com.database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by piyaponf on 11/5/2017 AD.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public String TAG = "drivingtest.project.com";
    public abstract void iniView();
    public abstract int getView();
    public abstract void onClose();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        iniView();
    }

    @Override
    protected void onDestroy() {
        onClose();
        super.onDestroy();
    }
}
