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
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.einzbern.storche.R;
import com.einzbern.storche.entities.Week;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        bindDCSIntent = new Intent(this, DisCourseService.class);
        bindService(bindDCSIntent, disCourseCon, Context.BIND_AUTO_CREATE);

        ly = (LinearLayout)findViewById(R.id.Thursday_c2);
        buttonFloat = (ButtonFloat)findViewById(R.id.buttonFloat);
        setListners();
    }

    private void setListners(){
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
        });}
}
