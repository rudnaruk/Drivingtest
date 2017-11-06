package drivingtest.project.com.base.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by piyaponf on 11/6/2017 AD.
 */

public class MyRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    private static final String TAG = "MyRadioButton";
    private Context mcontext;
    public MyRadioButton(Context context) {
        super(context);
        this.mcontext = context;
        setFont();
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        setFont();
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mcontext = context;
        setFont();
    }

    private void setFont(){
        int style = getTypeface().getStyle();
        Typeface type = null;
        if(style == Typeface.BOLD){
            type = Typeface.createFromAsset(mcontext.getAssets(),"font/Quark-Bold.otf");
        }else{
            type = Typeface.createFromAsset(mcontext.getAssets(),"font/Quark-Light.otf");
        }
        setTypeface(type);
    }
}
