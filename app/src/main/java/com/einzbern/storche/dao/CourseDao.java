package com.einzbern.storche.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.einzbern.storche.entity.Course;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/19.
 */

public class CourseDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public CourseDao(Context appContext){
        try {
            dbHelper = new DbHelper(appContext);
        }catch (Exception e){
            Log.e("Database", "db open fail !");
            Log.i("Database exception", e.getMessage());
        }
    }

    public int clearData(){
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("delete from course");
            db.execSQL("delete from course_ts");
        }catch (Exception e){
            Log.e("fail to clear c_data", e.getMessage());
            dispose();
            return DbHelper.QUERY_FAIL;
        }
        dispose();
        return DbHelper.QUERY_SUCCESS;
    }

    public int getCoursesNum(){
        db = getRDb();
        int num = 0;
        Cursor c = db.rawQuery("select count(c_id) from course", null);
        while (c.moveToNext()){
            num = c.getInt(0);
        }
        c.close();
        dispose();
        return num;
    }

    public ArrayList<Course> getAllCourses(){
        db = getRDb();
        ArrayList<Course> courses = new ArrayList<Course>();
        Course course;
        Cursor cursor_course;
        Cursor cursor_courseTS;
        cursor_course = db.rawQuery("select * from course", null);
        while (cursor_course.moveToNext()){
            course = new Course();
            course = getCourseEntity(cursor_course);
            cursor_courseTS = db.rawQuery("select * from course_ts where c_id=?", new String[]{course.getId()});
            course = addTimeSpot(cursor_courseTS, course);
            cursor_courseTS.close();
            courses.add(course);
        }
        cursor_course.close();
        dispose();
        return courses;
    }

    public int insertCourse(Course course){
        //insert into course
        db = getWDb();
        try {
            db.execSQL("insert into course(c_id, c_name, startWeek, endWeek) values(?, ?, ?, ?)",
                    new Object[]{course.getId(), course.getName(), course.getStartWeek(), course.getEndWeek()});
        }catch (Exception e){
            Log.e("db_insert_course", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }finally {
            dispose();
        }
        //insert into course_ts
        db = getWDb();
        ArrayList<int[]>day_time_spot = course.getTime_spot();
        for (int i=0; i<course.getTime_spot().size(); i++){
            try {
                db.execSQL("insert into course_ts(c_id, day, time, spot) values(?, ?, ?, ?)",
                        new Object[]{course.getId(), (day_time_spot.get(i))[Course.DAY], (day_time_spot.get(i))[Course.TIME],
                                (day_time_spot.get(i))[Course.SPOT]});
            }catch (Exception e){
                Log.e("db_insert_courseTS", e.getMessage());
                return DbHelper.QUERY_FAIL;
            }
        }
        dispose();
        return DbHelper.QUERY_SUCCESS;
    }

    public int deleteCourse(){
        return DbHelper.QUERY_SUCCESS;
    }

    public int updateCourse(Course newCourse, String oldID){
        return DbHelper.QUERY_SUCCESS;
    }

    public int courseQuery(String sql){
        try {
            db = dbHelper.getWritableDatabase();
            db.execSQL(sql);
        }catch (Exception e){
            Log.e("exc_query_fail", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }finally {
            dispose();
        }
        return DbHelper.QUERY_SUCCESS;
    }

    private Course addTimeSpot(Cursor c, Course course){
        int[] day_time_spot;
        ArrayList<int[]> daytimespot = new ArrayList<int[]>();
        while (c.moveToNext()){
            day_time_spot = new int[3];
            day_time_spot[0] = c.getInt(1);
            day_time_spot[1] = c.getInt(2);
            day_time_spot[2] = c.getInt(3);
            daytimespot.add(day_time_spot);
        }
        course.setTime_spot(daytimespot);
        return course;
    }

    private Course getCourseEntity(Cursor c){
        Course course = new Course();
        course.setId(c.getString(0));
        course.setName(c.getString(1));
        course.setStartWeek(c.getInt(2));
        course.setEndWeek(c.getInt(3));
        return course;
    }

    public void dispose(){
        db.close();
    }

    private SQLiteDatabase getWDb(){
        return dbHelper.getWritableDatabase();
    }
    private SQLiteDatabase getRDb(){
        return dbHelper.getReadableDatabase();
    }
}
