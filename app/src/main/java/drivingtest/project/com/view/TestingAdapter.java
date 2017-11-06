package drivingtest.project.com.view;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import drivingtest.project.com.R;
import drivingtest.project.com.database.MyDatabase;
import drivingtest.project.com.model.Choice;
import drivingtest.project.com.model.Question;

/**
 * Created by piyaponf on 11/5/2017 AD.
 */

public class TestingAdapter extends RecyclerView.Adapter<TestingAdapter.TestingViewHolder>{

    private final MyDatabase myDatabase;
    private List<Question> questions;
    private Context context;
    public TestingAdapter(List<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
        myDatabase = new MyDatabase(context);
    }

    @Override
    public TestingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_testing, parent, false);
        return new TestingAdapter.TestingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestingViewHolder holder, int position) {
        Question question = questions.get(position);
        question.setPosition(position);
        holder.tvQuestion.setText(String.format("%d. %s",(position+1),question.getQuestion()));
        if(question.getImage()!=null) {
            Glide.with(context)
                    .load(Uri.parse("file:///android_asset" + question.getImage())) // Uri of the picture
                    .into(holder.ivQuestion);
        }
        if(question.getChoices().size() == 0){
            LoadChoiceTask task =new LoadChoiceTask();
            task.execute(question);
        }else{
            RadioButton []rvChoices = {holder.rbChoice1,holder.rbChoice2,holder.rbChoice3,holder.rbChoice4};
            for (RadioButton radioButton: rvChoices){
                radioButton.setVisibility(View.GONE);
            }
            for(int i =0;(i< question.getChoices().size()&& i<4);i++){
                rvChoices[i].setText(question.getChoices().get(i).getName());
                rvChoices[i].setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    /**
     * View holder for product.
     */
    public class TestingViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;
        public RadioGroup radioGroup;
        public ImageView ivQuestion;
        public RadioButton rbChoice1;
        public RadioButton rbChoice2;
        public RadioButton rbChoice3;
        public RadioButton rbChoice4;

        /**
         * Constructor.
         *
         * @param view the view.
         */
        public TestingViewHolder(View view) {

            super(view);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            ivQuestion = view.findViewById(R.id.ivQuestion);
            radioGroup = view.findViewById(R.id.rgChoice);
            rbChoice1 = view.findViewById(R.id.radioButton1);
            rbChoice2 = view.findViewById(R.id.radioButton2);
            rbChoice3 = view.findViewById(R.id.radioButton3);
            rbChoice4 = view.findViewById(R.id.radioButton4);
        }
    }

    public class LoadChoiceTask extends AsyncTask<Question,Void,List<Choice>>{
        Question mQuestion;
        @Override
        protected List<Choice> doInBackground(Question... questions) {
            mQuestion = questions[0];
            if(mQuestion==null){
                return null;
            }
            return myDatabase.getChoiceByQuestionId(mQuestion.getQid());
        }

        @Override
        protected void onPostExecute(List<Choice> choices) {
            mQuestion.getChoices().addAll(choices);
            TestingAdapter.this.notifyItemChanged(mQuestion.getPosition());
        }
    }
}
