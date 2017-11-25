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

    public String getStartDay(String id){
        db = dbHelper.getReadableDatabase();
        String startDay = "'";
        try {
            Cursor cursor = db.rawQuery("select startDay from term where id=?", new String[]{id});
            if (cursor.moveToNext())
                startDay = cursor.getString(0);
            cursor.close();
        }catch (Exception e){
            Log.e("fail to find term_start", e.getMessage());
        }
        db.close();
        return startDay;
    }

    public String getEndDay(String id){
        db = dbHelper.getReadableDatabase();
        String endDay = "'";
        try {
            Cursor cursor = db.rawQuery("select endDay from term where id=?", new String[]{id});
            if (cursor.moveToNext())
                endDay = cursor.getString(0);
            cursor.close();
        }catch (Exception e){
            Log.e("fail to find term_end", e.getMessage());
        }
        db.close();
        return endDay;
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
