package com.einzbern.storche.dao;

import android.content.Context;
import android.util.Log;

import com.einzbern.storche.entity.Course;
import com.einzbern.storche.entity.Term;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by Administrator on 2017/11/24.
 */

public class InputDao {
    private InputStream inputStream;
    private File file;
    private Workbook workbook;
    private Sheet sheet;
    private ArrayList<Course> courses;
    private Term term;
    private int course_c = 5;

    public InputDao(InputStream in){
        inputStream = in;
        try {
            workbook = Workbook.getWorkbook(inputStream);
        } catch (IOException e) {
            Log.e("fail to getWorkbook", e.getMessage());
        } catch (BiffException e) {
            Log.e("fail to getWorkBook", e.getMessage());
        }
        try {
            sheet = workbook.getSheet(0);
        }catch (Exception e){
            Log.e("fail to getSheet", e.getMessage());
        }
        courses = new ArrayList<Course>();
        term = new Term();
    }

    public InputDao(File f){
        file = f;
        try {
            workbook = Workbook.getWorkbook(file);
        } catch (IOException e) {
            Log.e("fail to getWorkbook", e.getMessage());
        } catch (BiffException e) {
            Log.e("fail to getWorkBook", e.getMessage());
        }
        try {
            sheet = workbook.getSheet(0);
        }catch (Exception e){
            Log.e("fail to getSheet", e.getMessage());
        }
        courses = new ArrayList<Course>();
        term = new Term();
    }

    public String getTestData(int R, int C){
        String s = "";
        try {
            s += sheet.getCell(C, R).getContents();
        }catch (Exception e){
            Log.e("fail to getTesData", e.getMessage());
        }
        return s;
    }

    public int getCourseData(){
        try {
            for (int i=1; i<=5; i++){
                // get day_1,2
                addCourse(i, 1, 4, Course.MORNING_CLASS1);
                // get day_3,4
                addCourse(i, 5, 8, Course.MORNING_CLASS2);
                // get day_5,6
                addCourse(i, 9, 12, Course.NOON_CLASS1);
                // get day_7,8
                addCourse(i, 13, 16, Course.NOON_CLASS2);
            }
        }catch (Exception e){
            Log.e("fail to getCourseData", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }
        return DbHelper.QUERY_SUCCESS;
    }

    public int getTermData(){
        String id = getNewTermId();
        String describe =  "大三-上";
        String startDay = "";
        String endDay = "";
        try {
            startDay = sheet.getCell(0, 18).getContents();
        }catch (Exception e){
            Log.e("fail to get term_start", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }
        try {
            endDay = sheet.getCell(0, 19).getContents();
        }catch (Exception e){
            Log.e("fail to get term_end", e.getMessage());
            return DbHelper.QUERY_FAIL;
        }
        term = new Term(id, describe, startDay, endDay);
        return DbHelper.QUERY_SUCCESS;
    }

    public int putDataToDb(Context appContext){
        CourseDao courseDao = new CourseDao(appContext);
        TermDao termDao = new TermDao(appContext);
        getCourseData();
        getTermData();
        for (int i=0; i<courses.size(); i++){
            courseDao.insertCourse(courses.get(i));
        }
        termDao.addTerm(term);
        Log.e("add to Db", "success");
        return DbHelper.QUERY_SUCCESS;
    }

    private String getNewCourseId(){
        return "c"+courses.size();
    }

    private String getNewTermId(){
        return "t001";
    }

    private boolean check_IsNotNull(String cell){
        if ((replaceBlank(cell)).equals("") || cell==null)
            return false;
        return true;
    }

    private boolean check_isNotHad(String course_name){
        for (int i=0; i<courses.size(); i++) {
            if (courses.get(i).getName().equals(course_name))
                return false;
        }
        return true;
    }

    public int[] getWeeks(String weeks){
        int[] reWeek = new int[2];
        String[] weeks1 = weeks.split("--");
        String[] weeks2;
        try {
            reWeek[0] = Integer.parseInt(weeks1[0]);
            weeks2 = weeks1[1].split("周");
            reWeek[1] = Integer.parseInt(weeks2[0]);
        }catch (Exception e){
            Log.e("fail to split course", e.getMessage());
            return null;
        }
        return reWeek;
    }

    public void updateDay_time_spot(int [] day_time_spot, String course_name){
        for (int i=0; i<courses.size(); i++){
            if (course_name.equals(courses.get(i).getName())){
                //Log.e("before_addDTS", courses.get(i).getName()+" : "+print(courses.get(i).getTime_spot()));
                courses.get(i).addDay_Time_Spot(day_time_spot);
                //Log.e("after_addDTS", print(courses.get(i).getTime_spot()));
                return;
            }
        }
    }

    public String replaceBlank(String s){
        String re = "";
        if (s!=null){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(s);
            re = m.replaceAll("");
        }
        return re;
    }

    public void addCourse(int C, int fromR, int endR, int time){
        String id;
        String course_name = "";
        ArrayList<int[]> day_time_spots;
        int[] day_time_spot = new int[3];
        int[] weeks;
        String teacher;
        if (check_IsNotNull(sheet.getCell(C, fromR).getContents())){
            try {
                course_name = sheet.getCell(C, fromR).getContents();
            }catch (Exception e){
                Log.e("fail to get courseName", e.getMessage());
            }
            day_time_spot[Course.DAY] = C;
            day_time_spot[Course.TIME] = time;
            day_time_spot[Course.SPOT] = Integer.parseInt(sheet.getCell(C, endR).getContents());
            if (check_isNotHad(course_name)){
                id = getNewCourseId();
                day_time_spots = new ArrayList<int[]>();
                day_time_spots.add(day_time_spot);
                weeks = getWeeks(sheet.getCell(C, fromR+1).getContents());
                teacher = sheet.getCell(C, fromR+2).getContents();
                courses.add(new Course(id, course_name, day_time_spots, weeks[0], weeks[1], teacher));
            }else {
                updateDay_time_spot(day_time_spot, course_name);
            }
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Term getTerm() {
        return term;
    }
}
