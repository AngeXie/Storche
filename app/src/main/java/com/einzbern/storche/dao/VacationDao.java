package com.einzbern.storche.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.einzbern.storche.entity.Vacation;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/21.
 */

public class VacationDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public VacationDao(Context appContext){
        try {
            dbHelper = new DbHelper(appContext);
        }catch (Exception e){
            Log.e("dbHelper open fail", e.getMessage());
        }
    }

    public int getVacationsNumber(){
        db =dbHelper.getReadableDatabase();
        int num = 0;
        try {
            Cursor cursor = db.rawQuery("select count(v_id) from vacation", null);
            while (cursor.moveToNext())
                num = cursor.getInt(0);
            cursor.close();
        }catch (Exception e){
            Log.e("fail to count vacations", e.getMessage());
        }
        dispose();
        return num;
    }

    public ArrayList<Vacation> getAllVacations(){
        db = dbHelper.getReadableDatabase();
        ArrayList<Vacation> vacations = new ArrayList<Vacation>();
        Vacation vacation = new Vacation();
        try {
            Cursor cursor = db.rawQuery("select * from vacation", null);
            while (cursor.moveToNext()){
                vacation = getVacationEntity(cursor);
                vacations.add(vacation);
            }
            cursor.close();
        }catch (Exception e){
            Log.e("fail to getVacationsMSG", e.getMessage());
        }finally {}
        dispose();
        return vacations;
    }

    public int insertVacation(Vacation vacation){
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("insert into vacation(v_id, v_name, startDay, endDay) values(?, ?, ?, ?)",
                    new String[]{vacation.getId(), vacation.getName(), vacation.getStartDay(), vacation.getEndDay()});
        }catch (Exception e){
            Log.e("fail to insert vacation", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }
        db.close();
        return DbHelper.QUERY_SUCCESS;
    }

    public int deletVacation(String id){
        return DbHelper.QUERY_SUCCESS;
    }

    public int updateVacation(Vacation newVacation, String oldID){
        return DbHelper.QUERY_SUCCESS;
    }

    private Vacation getVacationEntity(Cursor c){
        Vacation vacation = new Vacation();
        vacation.setId(c.getString(0).toString());
        vacation.setName(c.getString(1).toString());
        vacation.setStartDay(c.getString(2).toString());
        vacation.setEndDay(c.getString(3).toString());
        return vacation;
    }

    public void dispose(){
        db.close();
    }
}
