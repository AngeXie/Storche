package com.einzbern.storche.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.einzbern.storche.R;
import com.einzbern.storche.dao.CourseDao;
import com.einzbern.storche.dao.DbHelper;
import com.einzbern.storche.entity.Course;
import com.einzbern.storche.entity.Week;
import com.einzbern.storche.view.AddWeekDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/17.
 */

public class AddCourseActivity extends AppCompatActivity {
    private TextView tv_courseWeek;
    private TextView tv_courseDT;
    private ImageView img_return;
    private com.shamanland.fab.FloatingActionButton btn_addC_confirm;
    private MaterialEditText edt_course_spot;
    private MaterialEditText edt_course_name;
    private String intetnDt;
    private CourseDao courseDao;
    private AddWeekDialog weekDialog;
    private String course_name;
    private String couse_id;
    private int startWeek;
    private int endWeek;
    private ArrayList<int[]> day_time_spots;
    private int[] day_time_spot;
    private String allMsg = "";
    private String s_startWeek;
    private String s_endWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseDao = new CourseDao(this);
        day_time_spots = new ArrayList<int[]>();
        day_time_spot = new int[3];
        img_return = (ImageView) findViewById(R.id.img_add_return_course);
        tv_courseWeek = (TextView) findViewById(R.id.dialog_courseWeeks);
        tv_courseDT = (TextView) findViewById(R.id.tv_courseDT);
        btn_addC_confirm = (com.shamanland.fab.FloatingActionButton) findViewById(R.id.addCourse_confirm);
        edt_course_spot = (MaterialEditText) findViewById(R.id.course_spot);
        edt_course_name = (MaterialEditText) findViewById(R.id.edt_course_name);
        setListners();
        showCourseDT(getIntent());
        initDialog();
    }

    private void setListners(){
        tv_courseWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                setDialogSize();
            }
        });
        btn_addC_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAddMsgIsLegal(edt_course_name.getText().toString(), edt_course_spot.getText().toString(), s_startWeek, s_endWeek)){
                    course_name = edt_course_name.getText().toString();
                    couse_id = course_name;
                    startWeek = Integer.parseInt(s_startWeek);
                    endWeek = Integer.parseInt(s_endWeek);
                    day_time_spot[Course.SPOT] = Integer.parseInt(edt_course_spot.getText().toString());
                    day_time_spots.add(day_time_spot);
                    AlertDialog.Builder cConfirmDialog = new AlertDialog.Builder(AddCourseActivity.this);
                    cConfirmDialog.setTitle("");
                    cConfirmDialog.setMessage("确认添加课程： "+course_name);
                    cConfirmDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int re = courseDao.insertCourse(new Course(couse_id, course_name, day_time_spots, startWeek, endWeek, ""));
                            String rs = re== DbHelper.QUERY_SUCCESS ? "添加成功" : "添加失败";
                            Toast.makeText(getApplicationContext(), rs, Toast.LENGTH_SHORT).show();
                            returnToCourse();
                        }
                    });
                    cConfirmDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    cConfirmDialog.show();
                }else {
                    String st = "请检查输入内容！";
                    Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToCourse();
            }
        });
    }

    private boolean checkAddMsgIsLegal(Object c_name, Object inputSpot, Object start_w, Object end_w){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (c_name != null && !c_name.equals(""))
            if (inputSpot != null && !inputSpot.equals("") && pattern.matcher(inputSpot.toString()).matches()) {
                if (start_w != null && end_w != null) {
                    if (Integer.parseInt(start_w.toString()) <= Integer.parseInt(end_w.toString())) {
                        //Log.e("islegal:", "legal");
                        return true;
                    }else {
                        tv_courseWeek.setText("请选择合法的周数！");
                    }
                }
            }
        //Log.e("isLagal", "inlegal");
        //og.e("start-end", s_startWeek+"-"+s_endWeek);
        return false;
    }

    private void showCourseDT(Intent intent){
        intetnDt = intent.getStringExtra("courseDT");
        tv_courseDT.setText(intetnDt);
        tv_courseDT.setTextSize(25);
        tv_courseDT.setTextColor(Color.parseColor("#999999"));
        getIntentDTMsg(intetnDt);
    }

    private void returnToCourse(){
        Intent intent = new Intent();
        intent.setClass(AddCourseActivity.this, CoursesActivity.class);
        startActivity(intent);
    }

    private void getIntentDTMsg(String intentDt){
        String[] DTmsgs = intentDt.split(" ");
        int day = Week.MONDAY;
        switch (DTmsgs[0]){
            case "周一": day = Week.MONDAY; break;
            case "周二": day = Week.TUESDAY; break;
            case "周三": day = Week.WEDNESDAY; break;
            case "周四": day = Week.THURSDAY; break;
            case "周五": day = Week.FIRDAY; break;
        }
        int time = 1;
        switch (DTmsgs[1]){
            case "1,2": time = Course.MORNING_CLASS1; break;
            case "3,4": time = Course.MORNING_CLASS2; break;
            case "5,6": time = Course.NOON_CLASS1; break;
            case "7,8": time = Course.NOON_CLASS2; break;
        }
        day_time_spot[Course.DAY] = day;
        day_time_spot[Course.TIME] = time;
    }

    private void getALlMsg(){
        allMsg += "name: "+course_name+"\n";
        allMsg += "spot: "+day_time_spot[Course.SPOT]+"\n";
        allMsg += "day: "+day_time_spot[Course.DAY]+"\n";
        allMsg +=  "time: "+day_time_spot[Course.TIME]+"\n";
        allMsg += "startWeek: "+s_startWeek+"\n";
        allMsg += "endWeek: "+s_endWeek+"\n";
    }

    private void initDialog(){
        weekDialog = new AddWeekDialog.Builder(AddCourseActivity.this)
                .setIGetDataListener(new AddWeekDialog.IGetDialogDate() {
                    @Override
                    public void getData(String stWeek, String edWeek) {
                        s_startWeek = stWeek;
                        s_endWeek = edWeek;
                        tv_courseWeek.setText(stWeek+" 周- "+edWeek+"周");
                        optDisWeek();
                    }
                })
                .setNegativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                })
                .setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                })
                .Build();

    }

    private void showDialog(){
        if(weekDialog != null && !weekDialog.isShowing()){
            weekDialog.show();
        }
    }

    private void dissmissDialog(){
        if(weekDialog != null && weekDialog.isShowing()){
            weekDialog.dismiss();
        }
    }

    private void setDialogSize(){
        Window dialogWindow = weekDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.6);
        //lp.height = (int) (d.heightPixels* 0.6);// 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    private void optDisWeek(){
        tv_courseWeek.setTextColor(Color.parseColor("#666666"));
        tv_courseWeek.setTextSize(25);
    }
}
