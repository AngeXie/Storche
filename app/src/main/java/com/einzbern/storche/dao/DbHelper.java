package com.einzbern.storche.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/11/19.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int QUERY_SUCCESS = 1;
    public static final int QUERY_FAIL = 0;
    public static final String DATABASE_NAME = "db_storche";
    public static final int DATABASE_VERSION = 1;
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table course
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS course(" +
                            "c_id varchar(20) primary key, " +
                            "c_name varchar(20), " +
                            "startWeek integer, " +
                            "endWeek integer)");
        }catch (Exception e){
            Log.e("create_table", "course fail\n"+e.getMessage());
        }
        //create table course-ts
        try{
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS course_ts(" +
                            "c_id varchar(20), " +
                            "day integer, " +
                            "time integer, " +
                            "spot integer)");
        }catch (Exception e){
            Log.e("create_table", "course_ts fail\n"+e.getMessage());
        }
        //create table vacation
        try{
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS vacation(" +
                            "v_id varchar(20) primary key, " +
                            "v_name varchar(20), " +
                            "startDay varchar(20), " +
                            "endDay varchar(20))");
        }catch (Exception e){
            Log.e("create_table", "vacation fail\n"+e.getMessage());
        }
        //create table dayMsg
        try{
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS day_msg(" +
                            "id varchar(20) primary key, " +
                            "title varchar(20), " +
                            "date varchar(20), " +
                            "detail text)");
        }catch (Exception e){
            Log.e("create_table", "dayMsg fail\n"+e.getMessage());
        }
        //create table exam
        try{
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS exam(" +
                            "id varchar(20) primary key, " +
                            "name varchar(30), " +
                            "date varchar(20), " +
                            "startTime varchar(20), " +
                            "endTime varchar(20))");
        }catch (Exception e){
            Log.e("create_table", "exam fail\n"+e.getMessage());
        }
        //create table term
        try{
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS term(" +
                            "id varchar(20) primary key, " +
                            "describe varchar(20), " +
                            "startDay varchar(20), " +
                            "endDay varchar(20))");
        }catch (Exception e){
            Log.e("create_table", "term fail\n"+e.getMessage());
        }
        initData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initData(SQLiteDatabase db){
        String sql_initTerm = "insert into term(id, describe, startDay, endDay) values('t01', '大三-上', '2017-09-01', '2018-02-01')";
        try {
            db.execSQL(sql_initTerm);
        }catch (Exception e){
            Log.e("fail to initTerm", e.getMessage());
        }
    }
}
