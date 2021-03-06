package drivingtest.project.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import drivingtest.project.com.model.Category;
import drivingtest.project.com.model.Choice;
import drivingtest.project.com.model.Question;
import drivingtest.project.com.model.Score;

/**
 * Created by piyaponf on 11/4/2017 AD.
 */

public class MyDatabase {

    private static final String TABLE_QUESTIONS = "QUESTIONS";
    private static final String TABLE_CATEGORIES = "CATEGORIES";
    private static final String TABLE_CHOICE = "CHOICE";
    private static final String TABLE_SCORE = "SCORE";
    public static final String ALL_TABLES [] = {TABLE_QUESTIONS,TABLE_CATEGORIES,TABLE_CHOICE,TABLE_SCORE};
    private static final int VERSION = 1;
    private DatabaseHandler databaseHandler;

    public MyDatabase(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }

    /**
     * get all category
     * @return list of category
     */
    public List<Category> getCategories(){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Category> cats = new ArrayList<>();
        String query = String.format("SELECT * FROM %s",TABLE_CATEGORIES);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1));
                cats.add(category);
            } while (cursor.moveToNext());
        }
        db.close();
        return cats;
    }
    /**
     * get a category object
     * @return  a category object
     */
    public Category getCategoriesByCategoryId(int c_id){
        SQLiteDatabase db = databaseHandler.openDatabase();
        String query = String.format("SELECT * FROM %s WHERE c_id=%d",TABLE_CATEGORIES,c_id);
        Category category =null;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                category = new Category(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1));
            } while (cursor.moveToNext());
        }
        db.close();
        return category;
    }

    /**
     * get all question
     * @return list of question
     */
    public List<Question> getAllQuestion(int count){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Question> questions = new ArrayList<>();

        String query = "SELECT * FROM(\n" +
                "SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=1 ORDER BY RANDOM() LIMIT 0,10)\n" +
                "UNION SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=2 ORDER BY RANDOM() LIMIT 0,10)\n" +
                "UNION SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=3 ORDER BY RANDOM() LIMIT 0,10)\n" +
                "UNION SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=4 ORDER BY RANDOM() LIMIT 0,10)\n" +
                "UNION SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=5 ORDER BY RANDOM() LIMIT 0,10)\n" +
                "UNION SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=6 ORDER BY RANDOM() LIMIT 0,10)\n" +
                "UNION SELECT * FROM (SELECT * FROM QUESTIONS WHERE c_id=7 ORDER BY RANDOM() LIMIT 0,10)\n" +
                ")ORDER BY RANDOM()\n"+
                "LIMIT 0,"+count;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        db.close();
        return questions;
    }

    /**
     * get list question by category id
     * @param cid is category id
     * @return list of question
     */
    public List<Question> getQuestionByCategoryId(int cid,int max){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Question> questions = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE c_id=%d ORDER BY RANDOM() LIMIT 0,%d",TABLE_QUESTIONS,cid,max);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        db.close();
        return questions;
    }

    /**
     * get list of choices by question id
     * @param qid is question id
     * @return List of choice
     */
    public List<Choice> getChoiceByQuestionId(int qid){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Choice> choices = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE q_id=%d",TABLE_CHOICE,qid);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Choice choice = new Choice(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2).equals("1"),
                        Integer.parseInt(cursor.getString(3)));
                choices.add(choice);
            } while (cursor.moveToNext());
        }
        db.close();
        return choices;
    }

    /**
     * get all score by category id
     * @param cid is category id
     * @return list of score
     */
    public List<Score> getScoreByCategoryId(int cid){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Score> scores = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE c_id=%d ORDER BY date",TABLE_SCORE,cid);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4))
                );
                scores.add(score);
            } while (cursor.moveToNext());
        }
        db.close();
        return scores;
    }

    /**
     * for save score to DB
     * @param score
     */
    public void saveScore(Score score){
        SQLiteDatabase db = databaseHandler.openDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("c_id", score.getC_id());
        insertValues.put("score", score.getScore());
        insertValues.put("type", score.getType());
        db.insert(TABLE_SCORE, null, insertValues);
        db.close();
    }

}
