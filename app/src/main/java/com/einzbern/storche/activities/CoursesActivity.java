package com.einzbern.storche.activities;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.einzbern.storche.R;
import com.einzbern.storche.dao.CourseDao;
import com.einzbern.storche.dao.DbHelper;
import com.einzbern.storche.dao.InputDao;
import com.einzbern.storche.entity.Course;
import com.einzbern.storche.entity.Week;
import com.einzbern.storche.services.DisCourseService;
import com.einzbern.storche.util.ColorGetter;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/11/15.
 */

public class CoursesActivity extends AppCompatActivity {
    /*
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
    */
    DisCourseService disCourseService;
    ButtonRectangle btn_getData, btn_deleteDb, btn_showName;
    DbHelper dbHelper;
    String[][] curWeekCourse;
    TextView[][] courses;
    LinearLayout[][] lyCourses;
    int lycourse_i, lycourse_j;
    ArrayList<TextView> addTextViews;
    ArrayList<String> intentDatas;
    boolean showAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        /*
        bindDCSIntent = new Intent(this, DisCourseService.class);
        bindService(bindDCSIntent, disCourseCon, Context.BIND_AUTO_CREATE);
        */
        showAdd = false;
        addTextViews = new ArrayList<TextView>();
        intentDatas = new ArrayList<String>();
        disCourseService = new DisCourseService();
        btn_getData = (ButtonRectangle) findViewById(R.id.btn_getData);
        btn_deleteDb = (ButtonRectangle) findViewById(R.id.btn_deleteDB);
        btn_showName = (ButtonRectangle) findViewById(R.id.btn_showName);
        dbHelper = new DbHelper(this);
        courses = new TextView[5][4];
        lyCourses = new LinearLayout[5][4];
        curWeekCourse = new String[5][4];
        //curWeekCourse = disCourseService.getWeekCourses(new CourseDao(this));

        initLyCourses();
        clearAllCourseView();
        curWeekCourse = disCourseService.getWeekCourses(new CourseDao(getApplicationContext()));
        addAllCourseView();
        setAddListners();
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
        lyCourses[3][0] = (LinearLayout)findViewById(R.id.Thursday_c1);
        lyCourses[3][1] = (LinearLayout)findViewById(R.id.Thursday_c2);
        lyCourses[3][2] = (LinearLayout)findViewById(R.id.Thursday_c3);
        lyCourses[3][3] = (LinearLayout)findViewById(R.id.Thursday_c4);
        lyCourses[4][0] = (LinearLayout)findViewById(R.id.Friday_c1);
        lyCourses[4][1] = (LinearLayout)findViewById(R.id.Friday_c2);
        lyCourses[4][2] = (LinearLayout)findViewById(R.id.Friday_c3);
        lyCourses[4][3] = (LinearLayout)findViewById(R.id.Friday_c4);
    }

    private void clearAllCourseView(){
        for (int i=0; i<5; i++){
            for (int j=0; j<4; j++){
                lyCourses[i][j].removeAllViews();
            }
        }
    }

    private void addAllCourseView(){
        TextView textView;
        LinearLayout.LayoutParams params;
        int color = R.color.event_color_01;
        for (int i=0; i<5; i++){
            for (int j=0; j<4; j++){
                lyCourses[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.e("whichClick", idToStirng(v.getId()));
                        if (showAdd){
                            Log.e("close addTV", "true");
                            for (int i=0; i<addTextViews.size(); i++){
                                addTextViews.get(i).setVisibility(View.INVISIBLE);
                            }
                            showAdd = false;
                        }
                    }
                });
                if (!curWeekCourse[i][j].equals("")){
                    params = (LinearLayout.LayoutParams) lyCourses[i][j].getLayoutParams();
                    color = ColorGetter.getDifColor(this, color);
                    textView = new TextView(this);
                    textView.setLayoutParams(params);
                    textView.setText(curWeekCourse[i][j]);
                    textView.setTextColor(Color.parseColor("#ffffff"));
                    textView.setTextSize(16);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(this.getResources().getColor(color));
                    lyCourses[i][j].addView(textView);
                }
            }
        }
    }

    private void setAddListners(){
        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputDao inputDao = new InputDao(getApplicationContext().getAssets().open("class-scheule.xls"));
                    inputDao.putDataToDb(getApplicationContext());
                } catch (IOException e) {
                    Log.e("fail to open excel", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        btn_deleteDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean re = (getApplicationContext()).deleteDatabase(dbHelper.DATABASE_NAME);
                String s = re?"suceess":"fail";
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        btn_showName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("data", getAllCourseMsg(new CourseDao(getApplicationContext())));
            }
        });
        for (lycourse_i=0; lycourse_i<5; lycourse_i++){
            for (lycourse_j=0; lycourse_j<4; lycourse_j++){
                if (lyCourses[lycourse_i][lycourse_j].getChildCount() == 0){
                    setLyListener(lyCourses[lycourse_i][lycourse_j]);
                    final TextView textView = (TextView) lyCourses[lycourse_i][lycourse_j].getChildAt(0);
                    addTextViews.add(textView);
                    lyCourses[lycourse_i][lycourse_j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (showAdd){
                                //Log.e("close addTV", "true");
                                for (int i=0; i<addTextViews.size(); i++){
                                    addTextViews.get(i).setVisibility(View.INVISIBLE);
                                }
                                showAdd = false;
                            }else {
                                textView.setVisibility(VISIBLE);
                                showAdd = true;
                            }
                        }
                    });
                    //Log.e("index", "i: "+lycourse_i+" j: "+lycourse_j);
                }
            }
        }
    }

    private void setLyListener(LinearLayout ly){
        TextView textView = new TextView(this);
        final String data = idToStirng(ly.getId());
        textView.setText("+");
        textView.setTextSize(50);
        textView.setTextColor(Color.parseColor("#cccccc"));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddTVListener(data);
            }
        });
        textView.setVisibility(View.INVISIBLE);
        ly.addView(textView);
    }

    private void setAddTVListener(String data){
        Intent intent = new Intent();
        intent.putExtra("courseDT", data);
        intent.setClass(CoursesActivity.this, AddCourseActivity.class);
        startActivity(intent);
    }

    private String getAllCourseMsg(CourseDao courseDao){
        ArrayList<Course> courses = courseDao.getAllCourses();
        String result = "";
        for (int i=0; i<courses.size(); i++){
            result += "name: ";
            result += (courses.get(i)).getName();
            result += "\n";
            result += getDTSMsg(courses.get(i).getTime_spot());
            result += (courses.get(i)).getStartWeek();
            result += "\n";
            result += (courses.get(i)).getEndWeek();
            result += "\n";
        }
        return result;
    }

    private String getDTSMsg(ArrayList<int[]>dts){
        String s = "";
        for (int i=0; i<dts.size(); i++){
            s += (dts.get(i))[0]+"-";
            s += (dts.get(i))[1]+"-";
            s += (dts.get(i))[2]+"\n";
        }
        return s;
    }

    private String idToStirng(int id){
        switch (id){
            case R.id.Monday_c1: return "周一 1,2";
            case R.id.Monday_c2: return "周一 3,4";
            case R.id.Monday_c3: return "周一 5,6";
            case R.id.Monday_c4: return "周一 7,8";
            case R.id.Tuesday_c1: return "周二-1,2";
            case R.id.Tuesday_c2: return "周二 3,4";
            case R.id.Tuesday_c3: return "周二 5,6";
            case R.id.Tuesday_c4: return "周二 7,8";
            case R.id.Wednesday_c1: return "周三 1,2";
            case R.id.Wednesday_c2: return "周三 3,4";
            case R.id.Wednesday_c3: return "周三 5,6";
            case R.id.Wednesday_c4: return "周三 7,8";
            case R.id.Thursday_c1: return "周四 1,2";
            case R.id.Thursday_c2: return "周四 3,4";
            case R.id.Thursday_c3: return "周四 5,6";
            case R.id.Thursday_c4: return "周四 7,8";
            case R.id.Friday_c1: return "周五 1,2";
            case R.id.Friday_c2: return "周五 3,4";
            case R.id.Friday_c3: return "周五 5,6";
            case R.id.Friday_c4: return "周五 7,8";
        }
        return "Mon-c1";
    }
}
