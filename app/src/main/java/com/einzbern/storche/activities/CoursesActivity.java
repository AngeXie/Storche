package com.einzbern.storche.activities;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.einzbern.storche.MainActivity;
import com.einzbern.storche.R;
import com.einzbern.storche.dao.CourseDao;
import com.einzbern.storche.dao.DbHelper;
import com.einzbern.storche.dao.InputDao;
import com.einzbern.storche.dao.TermDao;
import com.einzbern.storche.entity.Course;
import com.einzbern.storche.entity.Term;
import com.einzbern.storche.entity.Week;
import com.einzbern.storche.services.DisCourseService;
import com.einzbern.storche.util.ColorGetter;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.githang.statusbar.StatusBarCompat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Exchanger;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

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
    private static final int EX_FILE_PICKER_RESULT = 0;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    DisCourseService disCourseService;
    ButtonRectangle btn_clearData, btn_inputData;
    ImageView img_backToIndex;
    DbHelper dbHelper;
    String[][] curWeekCourse;
    TextView[][] courses;
    LinearLayout[][] lyCourses;
    int lycourse_i, lycourse_j;
    ArrayList<TextView> addTextViews;
    ArrayList<String> intentDatas;
    boolean showAdd;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        /*
        bindDCSIntent = new Intent(this, DisCourseService.class);
        bindService(bindDCSIntent, disCourseCon, Context.BIND_AUTO_CREATE);
        */
        path = "/sdcard";
        showAdd = false;
        addTextViews = new ArrayList<TextView>();
        intentDatas = new ArrayList<String>();
        disCourseService = new DisCourseService();
        img_backToIndex = (ImageView) findViewById(R.id.img_back_to_index);
        btn_clearData = (ButtonRectangle) findViewById(R.id.btn_delete_data_course_term);
        btn_inputData = (ButtonRectangle) findViewById(R.id.btn_inputFromExcel);
        dbHelper = new DbHelper(this);
        courses = new TextView[5][4];
        lyCourses = new LinearLayout[5][4];
        curWeekCourse = new String[5][4];

        refreshActivity();
    }

    private void refreshActivity(){
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
        img_backToIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CoursesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                boolean re = (getApplicationContext()).deleteDatabase(dbHelper.DATABASE_NAME);
                String s = re?"suceess":"fail";
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                */
                AlertDialog.Builder cConfirmDialog = new AlertDialog.Builder(CoursesActivity.this);
                cConfirmDialog.setTitle("");
                cConfirmDialog.setMessage("确认清除课程表和学期的所有信息吗？");
                cConfirmDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CourseDao courseDao = new CourseDao(getApplicationContext());
                        TermDao termDao = new TermDao(getApplicationContext());
                        courseDao.clearData();
                        termDao.clearData();
                        refreshActivity();
                    }
                });
                cConfirmDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                cConfirmDialog.show();
            }
        });
        btn_inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((new CourseDao(getApplicationContext())).getCoursesNum() == 0) {
                    if ((new TermDao(getApplicationContext())).getStartDay() == null)
                        checkPermissionsAndOpenFilePicker();
                }else {
                    String s0 = "检测到已有课程表和学期信息，请先清除已有信息再进行导入操作";
                    Toast.makeText(getApplicationContext(), s0, Toast.LENGTH_LONG).show();
                }
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
                }
            }
        }
    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                requestPermissions(new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    private void openFilePicker(){
        ExFilePicker exFilePicker = new ExFilePicker();
        exFilePicker.start(this, EX_FILE_PICKER_RESULT);
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

    /*
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
    */

    private String idToStirng(int id){
        switch (id){
            case R.id.Monday_c1: return "周一 1,2";
            case R.id.Monday_c2: return "周一 3,4";
            case R.id.Monday_c3: return "周一 5,6";
            case R.id.Monday_c4: return "周一 7,8";
            case R.id.Tuesday_c1: return "周二 1,2";
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EX_FILE_PICKER_RESULT) {
            ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
            if (result != null && result.getCount() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < result.getCount(); i++) {
                    stringBuilder.append(result.getNames().get(i));
                    if (i < result.getCount() - 1) stringBuilder.append(", ");
                }
                //Log.e("path", "Count: " + result.getCount() + "\n" + "Path: " + result.getPath() + "\n" + "Selected: " + stringBuilder.toString());
                Log.e("path", getPath(result.getPath(), stringBuilder.toString()));
                AlertDialog.Builder cConfirmDialog = new AlertDialog.Builder(CoursesActivity.this);
                cConfirmDialog.setTitle("");
                cConfirmDialog.setMessage("确认路径:\n"+path);
                cConfirmDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            InputDao inputDao = new InputDao(new FileInputStream(path));
                            inputDao.putDataToDb(getApplicationContext());
                            refreshActivity();
                            String sucsMsg = "课程表已成功导入";
                            Toast.makeText(getApplicationContext(), sucsMsg, Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Log.e("fail to open excel", e.getMessage());
                            String fialMsg = "导入失败";
                            Toast.makeText(getApplicationContext(), fialMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cConfirmDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cancleMsg = "已取消导入";
                        Toast.makeText(getApplicationContext(), cancleMsg, Toast.LENGTH_SHORT).show();
                    }
                });
                cConfirmDialog.show();
            }
        }
    }

    private String getPath(String dir, String fileName){
        String[] dir_pars = dir.split("/");
        path = "/sdcard";
        for (int i=4; i<dir_pars.length; i++)
            path += "/"+dir_pars[i];
        path += "/"+fileName;
        return path;
    }
}
