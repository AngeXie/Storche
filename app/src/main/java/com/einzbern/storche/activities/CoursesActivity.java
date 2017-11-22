package com.einzbern.storche.activities;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.einzbern.storche.R;
import com.einzbern.storche.services.DisCourseService;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/11/15.
 */

public class CoursesActivity extends AppCompatActivity {
    private DisCourseService disCourseService;
    private ServiceConnection disCourseCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            disCourseService = ((DisCourseService.LocalBinderDCS)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Intent bindDCSIntent;
    private LinearLayout ly;
    ButtonFloat buttonFloat;
    String[][] curWeekCourse;
    TextView[][] courses;
    LinearLayout[][] lyCourses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        bindDCSIntent = new Intent(this, DisCourseService.class);
        bindService(bindDCSIntent, disCourseCon, Context.BIND_AUTO_CREATE);

        /*
        courses = new TextView[5][4];
        lyCourses = new LinearLayout[5][4];
        ly = (LinearLayout)findViewById(R.id.Thursday_c2);
        buttonFloat = (ButtonFloat)findViewById(R.id.buttonFloat);
        setAddListners();
        initCourses();
        initLyCourses();
        curWeekCourse = disCourseService.getWeekCourses();
        */
    }

    private void initLyCourses(){
        lyCourses[0][0]  = (LinearLayout)findViewById(R.id.Monday_c1);
        lyCourses[0][1]  = (LinearLayout)findViewById(R.id.Monday_c2);
        lyCourses[0][2]  = (LinearLayout)findViewById(R.id.Monday_c3);
        lyCourses[0][3]  = (LinearLayout)findViewById(R.id.Monday_c4);
        lyCourses[1][0] = (LinearLayout)findViewById(R.id.Tuesday_c1);
        lyCourses[1][1] = (LinearLayout)findViewById(R.id.Tuesday_c2);
        lyCourses[1][2] = (LinearLayout)findViewById(R.id.Tuesday_c3);
        lyCourses[1][3] = (LinearLayout)findViewById(R.id.Tuesday_c4);
        lyCourses[2][0] = (LinearLayout)findViewById(R.id.Wednesday_c1);
        lyCourses[2][1] = (LinearLayout)findViewById(R.id.Wednesday_c2);
        lyCourses[2][2] = (LinearLayout)findViewById(R.id.Wednesday_c3);
        lyCourses[2][3] = (LinearLayout)findViewById(R.id.Wednesday_c4);
    }

    private void initCourses(){
        for (int i=0; i<5; i++){
            for (int j=0; j<4; j++){
                courses[i][j] = new TextView(this);
            }
        }
    }

    private void setAddListners(){
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFloat.setVisibility(VISIBLE);
            }
        });
        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CoursesActivity.this,AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }
}
