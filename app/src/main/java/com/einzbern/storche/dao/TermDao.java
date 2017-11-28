package com.einzbern.storche.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.einzbern.storche.entity.Term;


/**
 * Created by Administrator on 2017/11/22.
 */

public class TermDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public TermDao(Context appContext){
        try {
            dbHelper = new DbHelper(appContext);
        }catch (Exception e){
            Log.e("fail to get db_termDao", e.getMessage());
        }
    }

    public int addTerm(Term term){
        db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("insert into term(id, describe, startDay, endDay) values(?, ?, ?, ?)",
                    new String[]{term.getId(), term.getDescribe(), term.getStartDay(), term.getEndDay()});
        }catch (Exception e){
            Log.e("fail to addTerm", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }
        db.close();
        return DbHelper.QUERY_SUCCESS;
    }

    public int clearData(){
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("delete from term");
        }catch (Exception e){
            Log.e("fail to clear term data", e.getMessage());
            db.close();
            return DbHelper.QUERY_FAIL;
        }
        db.close();
        return DbHelper.QUERY_SUCCESS;
    }

    public String getStartDay(){
        db = dbHelper.getReadableDatabase();
        String startDay = null;
        try {
            Cursor cursor = db.rawQuery("select startDay from term", null);
            if (cursor.moveToNext())
                startDay = formatS(cursor.getString(0));
            cursor.close();
        }catch (Exception e){
            db.close();
            Log.e("fail to find term_start", e.getMessage());
            return null;
        }
        db.close();
        return startDay;
    }

    public String getEndDay(){
        db = dbHelper.getReadableDatabase();
        String endDay = null;
        try {
            Cursor cursor = db.rawQuery("select endDay from term", null);
            if (cursor.moveToNext())
                endDay = formatS(cursor.getString(0));
            cursor.close();
        }catch (Exception e){
            db.close();
            Log.e("fail to find term_end", e.getMessage());
            return null;
        }
        db.close();
        return endDay;
    }

    private String formatS(String s){
        return s.split("：")[s.split("：").length-1];
    }

    public Term getEntity(Cursor c){
        Term term = new Term();
        term.setId(c.getString(0));
        term.setDescribe(c.getString(1));
        term.setStartDay(c.getString(2));
        term.setEndDay(c.getString(3));
        return term;
    }

    public void dispose(){
        db.close();
    }
}
