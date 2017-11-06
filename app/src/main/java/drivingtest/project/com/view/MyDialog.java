package drivingtest.project.com.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import drivingtest.project.com.R;
import drivingtest.project.com.base.view.MyTextView;

/**
 * Created by piyaponf on 11/6/2017 AD.
 */

public class MyDialog {
    AlertDialog alertDialog;
    View.OnClickListener onOkClickListener;
    private static MyDialog myDialog = new MyDialog();
    public static MyDialog getInstance(){
        return myDialog;
    }

    private MyDialog() {
    }

    public void showDialog(Context context, String msg){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialogBuilder.setView(dialogView);
        MyTextView tv = (MyTextView) dialogView.findViewById(R.id.myTextView);
        Button btn =(Button) dialogView.findViewById(R.id.btnOk);
        View divider  =dialogView.findViewById(R.id.layoutDivider);
        MyTextView tvTitle = (MyTextView) dialogView.findViewById(R.id.myTextViewTitle);
        divider.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        if(onOkClickListener!=null) {
            btn.setOnClickListener(onOkClickListener);
        }
        tv.setText(msg);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void showDialog(Context context,String title, String msg){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialogBuilder.setView(dialogView);
        MyTextView tv = (MyTextView) dialogView.findViewById(R.id.myTextView);
        Button btn =(Button) dialogView.findViewById(R.id.btnOk);
        View divider  =dialogView.findViewById(R.id.layoutDivider);
        MyTextView tvTitle = (MyTextView) dialogView.findViewById(R.id.myTextViewTitle);
        divider.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);

        if(onOkClickListener!=null) {
            btn.setOnClickListener(onOkClickListener);
        }
        tvTitle.setText(title);
        tv.setText(msg);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void dismissDialog(){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }

    public void setOnOkClickListener(View.OnClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }
}
