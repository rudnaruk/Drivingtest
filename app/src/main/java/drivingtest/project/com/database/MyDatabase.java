package drivingtest.project.com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import drivingtest.project.com.model.Category;
import drivingtest.project.com.model.Choice;
import drivingtest.project.com.model.Question;

/**
 * Created by piyaponf on 11/4/2017 AD.
 */

public class MyDatabase {

    private static final String TABLE_QUESTIONS = "QUESTIONS";
    private static final String TABLE_CATEGORIES = "CATEGORIES";
    private static final String TABLE_CHOICE = "CHOICE";
    public static final String ALL_TABLES [] = {TABLE_QUESTIONS,TABLE_CATEGORIES,TABLE_CHOICE};
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
     * get all question
     * @return list of question
     */
    public List<Question> getAllQuestion(){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Question> questions = new ArrayList<>();
        String query = String.format("SELECT * FROM %s",TABLE_QUESTIONS);
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
    public List<Question> getQuestionByCategoryId(int cid){
        SQLiteDatabase db = databaseHandler.openDatabase();
        List<Question> questions = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE c_id=%d",TABLE_QUESTIONS,cid);
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
}
