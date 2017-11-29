package com.einzbern.storche.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.einzbern.storche.dao.CourseDao;
import com.einzbern.storche.entity.Course;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/15.
 */

public class DisCourseService{
    /*
    private final IBinder iBinder = new LocalBinderDCS();
    public class LocalBinderDCS extends Binder{
        public DisCourseService getService(){
            return DisCourseService.this;
        }
    }
    @Override
    public void onCreate(){
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    */
    private int curWeek;
    public DisCourseService(){
    }

    public String[][] getWeekCourses(CourseDao courseDao, int curWeek){
        ArrayList<Course>courses = courseDao.getAllCourses();
        String[][] weekCourses = new String[5][4];
        weekCourses = initcourseMsg(weekCourses);
        String course_name = "";
        int spot, day, time, startWeek, endWeek;
        String spot_s;
        Log.e("course_size: ", ""+courses.size());
        for (int i=0; i<courses.size(); i++){
            course_name = "@"+courses.get(i).getName();
            startWeek = courses.get(i).getStartWeek();
            endWeek = courses.get(i).getEndWeek();
            //Log.e("course_name: ", i+":"+course_name);
            if (startWeek<=curWeek && curWeek<=endWeek){
                for (int j=0; j<courses.get(i).getTime_spot().size(); j++){
                    day = (courses.get(i).getTime_spot().get(j))[Course.DAY];
                    time = (courses.get(i).getTime_spot().get(j))[Course.TIME];
                    spot = (courses.get(i).getTime_spot().get(j))[Course.SPOT];
                    spot_s = spot==0 ? "体育场地" : ""+spot;
                    weekCourses[day-1][time] = course_name + "\n"+spot_s;
                }
            }
        }
        return weekCourses;
    }

    private String[][] initcourseMsg(String[][] courseMsg){
        for (int i=0; i<courseMsg.length; i++){
            for (int j=0; j<courseMsg[0].length; j++){
                courseMsg[i][j] = "";
            }
        }
        return courseMsg;
    }
}
