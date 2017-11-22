package com.einzbern.storche;

import android.content.Intent;
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
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private FloatingActionButton btnGoCourse;
    private FloatingActionButton btnGoNote;
    private LinearLayout wigetExam;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGoCourse = (FloatingActionButton)findViewById(R.id.main_btnCourse);
        btnGoNote = (FloatingActionButton)findViewById(R.id.main_btnNote);
        wigetExam = (LinearLayout) findViewById(R.id.main_Exam);
        calendarView = (MaterialCalendarView)findViewById(R.id.materialCalendarView);
        bindListener();
        initView();
    }


    private void bindListener(){
        onDateChanged();
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
        refreshHeader(curDate.getYear(), curDate.getMonth() + 1, curDate.getDay());
    }

    /**
     * 更新头部文字日期
     * @param year
     * @param month
     * @param day
     */
    private void refreshHeader(int year, int month, int day) {
        int week = 12;
        TextView tvDay = (TextView)findViewById(R.id.tv_main_curDay);
        TextView tvMonth = (TextView)findViewById(R.id.tv_main_curMonth);
        TextView tvYear = (TextView)findViewById(R.id.tv_main_curYear);
        TextView tvWeek = (TextView)findViewById(R.id.tv_main_curWeek);
        tvDay.setText("月" + day + "日");
        tvMonth.setText("" + month);
        tvYear.setText(""+ year);
        tvWeek.setText("第" + week + "周");
    }

    /**
     * when Calender is clicked
     */
    private void onDateChanged() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if(selected){
                    //refreshHeader(date.getYear(), date.getMonth()+1, date.getDay());//+1因为框架有Bug
                }
            }
        });



    }


    /**
     * Jump to page
     */
    private void setGoActivityListner(){



    }
}
