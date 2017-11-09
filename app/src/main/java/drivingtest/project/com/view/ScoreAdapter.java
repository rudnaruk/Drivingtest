package drivingtest.project.com.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import drivingtest.project.com.R;
import drivingtest.project.com.TestActivity;
import drivingtest.project.com.base.view.MyTextView;
import drivingtest.project.com.model.Score;

/**
 * Created by piyaponf on 11/9/2017 AD.
 */

public class ScoreAdapter extends ArrayAdapter<Score> {
    Context context;
    int cat_id;
    List<Score> scores;
    public ScoreAdapter(@NonNull Context context, int resource, @NonNull List<Score> objects,int cat_id) {
        super(context, resource, objects);
        this.context = context;
        this.cat_id = cat_id;
        this.scores = objects;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Nullable
    @Override
    public Score getItem(int position) {
        return scores.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_score,null);
            MyTextView tvNo = (MyTextView) convertView.findViewById(R.id.tvNo);
            MyTextView tvScore = (MyTextView) convertView.findViewById(R.id.tvScore);
            MyTextView tvDate = (MyTextView) convertView.findViewById(R.id.tvDate);
            MyTextView tvType = (MyTextView) convertView.findViewById(R.id.tvType);
            ViewHolder viewHolder = new ViewHolder(tvScore,tvType,tvDate,tvNo);
            convertView.setTag(viewHolder);
        }
        Score mScore = getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvNo.setText(""+(position+1));
        viewHolder.tvScore.setText(""+mScore.getScore());
        viewHolder.tvDate.setText(mScore.getDate());
        if(mScore.getType()== TestActivity.TYPE_PRE_TEST && cat_id<=5) {
            viewHolder.tvType.setText(context.getText(R.string.pre));
        }else if( mScore.getType()== TestActivity.TYPE_POST_TEST && cat_id<=5){
            viewHolder.tvType.setText(context.getText(R.string.post));
        }else{
            viewHolder.tvType.setText("-");
        }

        return convertView;
    }

    private class ViewHolder{
        MyTextView tvScore;
        MyTextView tvType;
        MyTextView tvDate;
        MyTextView tvNo;

        public ViewHolder(MyTextView tvScore, MyTextView tvType, MyTextView tvDate, MyTextView tvNo) {
            this.tvScore = tvScore;
            this.tvType = tvType;
            this.tvDate = tvDate;
            this.tvNo = tvNo;
        }
    }

    private Date stringToDate(String aDate, String aFormat) {
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
