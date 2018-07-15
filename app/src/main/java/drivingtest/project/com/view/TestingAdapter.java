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

public class TestingAdapter extends RecyclerView.Adapter<TestingAdapter.TestingViewHolder> implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private final MyDatabase myDatabase;
    private List<Question> questions;
    private Context context;
    private OnDoQuestionListener onDoQuestionListener = null;
    public TestingAdapter(List<Question> questions, Context context, RecyclerView mRecyclerView) {
        this.questions = questions;
        this.context = context;
        this.mRecyclerView = mRecyclerView;
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
            holder.ivQuestion.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Uri.parse("file:///android_asset" + question.getImage())) // Uri of the picture
                    .into(holder.ivQuestion);
        }else{
            holder.ivQuestion.setVisibility(View.GONE);
        }
        if(question.getChoices().size() == 0){
            LoadChoiceTask task =new LoadChoiceTask();
            task.execute(question);
        }else{
            RadioButton []rvChoices = {holder.rbChoice1,holder.rbChoice2,holder.rbChoice3,holder.rbChoice4};
            int isLableChoice[] ={R.string.choice_a,R.string.choice_b,R.string.choice_c,R.string.choice_d};
            for (RadioButton radioButton: rvChoices){
                radioButton.setVisibility(View.GONE);
            }
            for(int i =0;(i< question.getChoices().size()&& i<4);i++){
                rvChoices[i].setText(String.format("%s %s",context.getString(isLableChoice[i]),question.getChoices().get(i).getName()));
                rvChoices[i].setVisibility(View.VISIBLE);
                rvChoices[i].setChecked(false);
                if(question.getChoices().get(i).isChecked()){
                    rvChoices[i].setChecked(true);
                }
                rvChoices[i].setTag(question.getChoices().get(i));
                rvChoices[i].setOnClickListener(TestingAdapter.this);
            }
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setOnDoQuestionListener(OnDoQuestionListener onDoQuestionListener) {
        this.onDoQuestionListener = onDoQuestionListener;
    }

    @Override
    public void onClick(View view) {
        Choice choice = (Choice) view.getTag();
        final Question question = findQuestionByQuestionId(choice.getQid());
        for(Choice mChoice1 : question.getChoices()){
            mChoice1.setChecked(false);
        }
        choice.setChecked(true);
        if(choice!=null && question!=null){
            question.setSelectedChoiceId(choice.getCh_id());
        }else if(question!=null){
            question.setSelectedChoiceId(-1);
        }
        if(this.onDoQuestionListener!=null){
            this.onDoQuestionListener.onDoQuestion();
        }
        mRecyclerView.post(new Runnable() {
            @Override public void run() {
                try {
                    notifyItemChanged(question.getPosition());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * for find question in quesion list by question id
     * @param qid is question id
     * @return question obj.
     */
    private Question findQuestionByQuestionId(int qid){
        Question question =null;
        for(Question mQuestion : questions){
            if(mQuestion.getQid()==qid){
                question = mQuestion;
                break;
            }
        }
        return question;
    }


    /**
     * View holder for product.
     */
    public class TestingViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;
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
            if(!choices.isEmpty()) {
                mQuestion.getChoices().addAll(choices);
                TestingAdapter.this.notifyItemChanged(mQuestion.getPosition());
            }
        }
    }
    public interface OnDoQuestionListener{
        public void onDoQuestion();
    }
}
