package com.einzbern.storche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.einzbern.storche.activities.CoursesActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class MainActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private FloatingActionButton btnGoCourse;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGoCourse = (FloatingActionButton)findViewById(R.id.main_btnCourse);
        calendarView = (MaterialCalendarView)findViewById(R.id.materialCalendarView);
        calendarView.setTopbarVisible(false);

        setGoActivityListner();
    }

    private void setGoActivityListner(){
        btnGoCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(MainActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });
    }
}
