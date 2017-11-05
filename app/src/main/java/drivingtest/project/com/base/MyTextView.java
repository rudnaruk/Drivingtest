package drivingtest.project.com.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by piyaponf on 11/5/2017 AD.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "MyTextView";
    private Context mcontext;
    public MyTextView(Context context) {
        super(context);
        this.mcontext = context;
        setFont();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        setFont();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
