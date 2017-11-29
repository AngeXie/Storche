package com.einzbern.storche;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.einzbern.storche.activities.CoursesActivity;
import com.einzbern.storche.activities.NoteActivity;
import com.einzbern.storche.activities.ExamTimeActivity;
import com.einzbern.storche.dao.ExamDao;
import com.einzbern.storche.dao.TermDao;
import com.einzbern.storche.entity.Exam;
import com.einzbern.storche.util.CurrentDateUtils;
import com.einzbern.storche.util.GetweekUtil;
import com.einzbern.storche.util.StrToDateUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private FloatingActionButton btnGoCourse;
    private FloatingActionButton btnGoNote;
    private TextView tvExamTime;
    private TextView tvCurWeek;
    private LinearLayout wigetExam;
    private TermDao termDao;
    private ExamDao examDao;
    private StrToDateUtil strToDateUtil;
    Intent intent;
    private Date startDate; //开学日期
    private Date vocationDate;
    private String examTime;
    private String termWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strToDateUtil = new StrToDateUtil();
        termDao = new TermDao(MainActivity.this);
        examDao = new ExamDao(MainActivity.this);
        btnGoCourse = (FloatingActionButton)findViewById(R.id.main_btnCourse);
        btnGoNote = (FloatingActionButton)findViewById(R.id.main_btnNote);
        wigetExam = (LinearLayout) findViewById(R.id.main_Exam);
        calendarView = (MaterialCalendarView)findViewById(R.id.materialCalendarView);
        tvExamTime = (TextView)findViewById(R.id.tv_main_TestTime);
        tvCurWeek = (TextView)findViewById(R.id.tv_main_curWeek);
        bindListener();
        initView();
    }

    private void bindListener(){
        btnGoCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(MainActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });
        btnGoNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setClass(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
        wigetExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setClass(MainActivity.this, ExamTimeActivity.class);
                startActivity(intent);
            }
        });

    }
    /**
     * 头部文字日期正确显示及日历界面设置
     */
    private void initView(){
        Date cur = new Date(System.currentTimeMillis());
        calendarView.setCurrentDate(cur);
        calendarView.setSelectedDate(cur);
        calendarView.setTopbarVisible(true);
        CalendarDay curDate = calendarView.getSelectedDate();
        initData();
        initCalenderView();
        if(checkSqlNotNull()){
            Log.e("start",startDate.toString());
            int i_week = new GetweekUtil(startDate).getWeekNum();
            if (i_week != 0) {
                termWeek = "第" + i_week +"周";
            }
            Log.e("i_week", ""+i_week);
        }else {
            termWeek = "";
        }
        refreshHeader(curDate.getYear(), curDate.getMonth() + 1, curDate.getDay(), termWeek);
        initExam();
    }

    private  void initData(){
        String start;
        String end;
        if(checkSqlNotNull()) {
            start = termDao.getStartDay();
           end = termDao.getEndDay();
            startDate = strToDateUtil.getDate(start);
            vocationDate = strToDateUtil.getDate(end);

        }
    }

    private void initExam() {
        TextView des1 = (TextView)findViewById(R.id.tv_main_examdis1);
        TextView des2 = (TextView)findViewById(R.id.tv_main_examdis2);
        TextView tvExamName = (TextView)findViewById(R.id.tv_main_textName);
        if(checkExamNotNull()) {
            Exam examEntity = examDao.getAllExams().get(0);
            examTime = getCountdown(examEntity.getDate());

            tvExamName.setText(examEntity.getName());
            des1.setText("距离");
            des2.setText("还有");
            tvExamTime.setText(examTime);
        }else {
            tvExamName.setText("");
            tvExamTime.setText("");
            des1.setText("");
            des2.setText("最近没有考试哦");
        }
    }

    private  void initCalenderView(){
        if(checkSqlNotNull()) {
            calendarView.addDecorators(new vocationDecorator(), new startDecorator());
            calendarView.invalidateDecorators();
        }
    }

    /**
     * 更新头部文字日期
     * @param year
     * @param month
     * @param day
     */
    private void refreshHeader(int year, int month, int day, String week) {

        TextView tvDay = (TextView)findViewById(R.id.tv_main_curDay);
        TextView tvMonth = (TextView)findViewById(R.id.tv_main_curMonth);
        TextView tvYear = (TextView)findViewById(R.id.tv_main_curYear);
        TextView tvWeek = (TextView)findViewById(R.id.tv_main_curWeek);
        tvDay.setText("月" + day + "日");
        tvMonth.setText("" + month);
        tvYear.setText(""+ year);
        tvWeek.setText(week);
    }


    private String getCountdown(String date) {
        if(date == null){
            return null;
        }
        long days = 0;
        CurrentDateUtils util = new CurrentDateUtils();
        String curDate = util.getYear()+"-"+util.getMonth()+"-"+util.getDay();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date begin = format.parse(curDate);
            Date end = format.parse(date);
            days = (end.getTime()-begin.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days + "天";
    }

    private Boolean checkSqlNotNull(){
        if(termDao.getEndDay() != null && termDao.getStartDay() != null){
            return true;
        }
        return false;
    }

    private class vocationDecorator implements DayViewDecorator{

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            // Log.i("sssssss", "shouldDecorate: " + day.getDate().toString() + " vacation :" + vocationDate.toString() );
            return day.getDate().equals(vocationDate);
        }

        @Override
        public void decorate(DayViewFacade view) {

            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.pic_vacation));
        }
    }

    private class startDecorator implements DayViewDecorator{

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.getDate().equals(startDate);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.pic_start));
        }
    }

    private boolean checkExamNotNull(){
        // Log.i("nullll", "checkExamNotNull: " + examDao.getAllExams().size());
        if(examDao.getAllExams().size() != 0){
            return true;
        }
        else return false;
    }

    @Override
    public void onResume(){
        initView();
        super.onResume();
        // Log.i("backto", "onResume: " + "返回");

    }
}
