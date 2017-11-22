package com.einzbern.storche.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.einzbern.storche.entity.Exam;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/11/22.
 */

public class ExamDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public ExamDao(Context appContext){
        try {
            dbHelper = new DbHelper(appContext);
        }catch (Exception e){
            Log.e("fail to get db_examDao", e.getMessage());
        }
    }

    public int getExamsNum(){
        db = dbHelper.getReadableDatabase();
        int number = 0;
        try {
            Cursor cursor = db.rawQuery("select count(id) from exam", null);
            while (cursor.moveToNext())
                number = cursor.getInt(0);
            cursor.close();
        }catch (Exception e){
            Log.e("fail to get exam number", e.getMessage());
        }
        db.close();
        return number;
    }

    public ArrayList<Exam> getAllExams(){
        db = dbHelper.getReadableDatabase();
        ArrayList<Exam> exams = new ArrayList<Exam>();
        Exam exam = new Exam();
        try {
            Cursor cursor = db.rawQuery("select * from exam", null);
            while (cursor.moveToNext()){
                exam = getEntity(cursor);
                exams.add(exam);
            }
            cursor.close();
        }catch (Exception e){
            Log.e("fail to getAllExams", e.getMessage());
        }
        dispose();
        return exams;
    }

    public int addExam(Exam exam){
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("insert into exam(id, name, date, startTime, endTime) values(?, ?, ?, ?, ?)",
                    new String[]{exam.getId(), exam.getName(), exam.getDate(), exam.getStartTime(), exam.getEndTime()});
            //Log.i("after insert", "*");
        }catch (Exception e){
            Log.e("fail to insert exam", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }finally {
            dispose();
        }
        return DbHelper.QUERY_SUCCESS;
    }

    public int deleteExam(String id){return DbHelper.QUERY_SUCCESS;}

    public int updateExam(String id, String whichValue, String newValue){return DbHelper.QUERY_SUCCESS;}

    public void dispose(){
        db.close();
    }
    private Exam getEntity(Cursor c){
        Exam exam = new Exam();
        exam.setId(c.getString(0));
        exam.setName(c.getString(1));
        exam.setDate(c.getString(2));
        exam.setStartTime(c.getString(3));
        exam.setEndTime(c.getString(4));
        return exam;
    }
}
