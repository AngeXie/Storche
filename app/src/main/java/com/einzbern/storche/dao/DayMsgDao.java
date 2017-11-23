package com.einzbern.storche.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.einzbern.storche.entity.DayMsg;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/21.
 */

public class DayMsgDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DayMsgDao(Context appContext){
        dbHelper = new DbHelper(appContext);
    }

    public int getDayMsgNum(){
        db = dbHelper.getReadableDatabase();
        int num = 0;
        try {
            Cursor cursor = db.rawQuery("select count(id) from day_msg", null);
            while (cursor.moveToNext()){
                num = cursor.getInt(0);
            }
            cursor.close();
        }catch (Exception e){
            Log.e("fail to getDayMsgNum", e.getMessage());
        }
        dispose();
        return num;
    }

    public ArrayList<DayMsg> getAllDaysMsgs(){
        db = dbHelper.getReadableDatabase();
        ArrayList<DayMsg> dayMsgs = new ArrayList<DayMsg>();
        DayMsg dayMsg = new DayMsg();
        try {
            Cursor cursor = db.rawQuery("select * from day_msg", null);
            while (cursor.moveToNext()){
                dayMsg = getEntity(cursor);
                dayMsgs.add(dayMsg);
            }
            cursor.close();
        }catch (Exception e){
            Log.e("fail to getAllDayMsg", e.getMessage());
        }
        db.close();
        return dayMsgs;
    }

    public ArrayList<DayMsg> getDayMsg_byDate(String date){
        db = dbHelper.getReadableDatabase();
        ArrayList<DayMsg> curDayMsgs = new ArrayList<DayMsg>();
        DayMsg dayMsg = new DayMsg();
        try {
            Cursor cursor = db.rawQuery("select * from day_msg where date=?", new String[]{date});
            while (cursor.moveToNext()){
                dayMsg = getEntity(cursor);
                curDayMsgs.add(dayMsg);
            }
            cursor.close();
        }catch (Exception e){
            Log.e("fail to getMsg_byDate", e.getMessage());
        }
        db.close();
        return curDayMsgs;
    }

    public int deleteDayMsg(String id){
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("delete from day_msg where id=?", new String[]{id});
        }catch (Exception e){
            Log.e("fail to delete from msg", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }
        db.close();
        return DbHelper.QUERY_SUCCESS;
    }

    public int addDayMsg(DayMsg dayMsg){
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("insert into day_msg(id, title, date, detail) values(?, ?, ?, ?)",
                    new String[]{dayMsg.getId(), dayMsg.getTitle(), dayMsg.getDate(), dayMsg.getDetail()});
        }catch (Exception e){
            Log.e("fail to insert msg", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }finally {
            dispose();
        }
        return DbHelper.QUERY_SUCCESS;
    }

    public int updateDayMsg(String id, String whichOne, String newValue){
        return DbHelper.QUERY_SUCCESS;
    }

    private DayMsg getEntity(Cursor cursor){
        DayMsg dayMsg = new DayMsg();
        try {
            dayMsg.setId(cursor.getString(0));
            dayMsg.setTitle(cursor.getString(1));
            dayMsg.setDate(cursor.getString(2));
            dayMsg.setDetail(cursor.getString(3));
        }catch (Exception e){
            Log.e("fail to getDayMsgEntity", e.getMessage());
        }
        return dayMsg;
    }

    public void dispose(){
        db.close();
    }
}