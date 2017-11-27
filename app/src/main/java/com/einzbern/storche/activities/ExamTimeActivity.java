package com.einzbern.storche.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.einzbern.storche.R;
import com.einzbern.storche.dao.ExamDao;
import com.einzbern.storche.entity.Exam;
import com.einzbern.storche.util.CurrentDateUtils;
import com.einzbern.storche.view.AddExamDialog;
import com.shamanland.fab.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ExamTimeActivity extends AppCompatActivity{
    protected ListView lvExam;
    protected ExamDao examDao;//数据库
    protected List <Exam>ltentityExam;
    protected static Exam examEntity;
    protected SimpleAdapter adpExam;
    protected List ltExam;
    protected Map mapExam;
    protected String[] itemName;
    protected int[] itemId;
    protected int idsum = 5;
    protected FloatingActionButton fabAdd;
    protected AddExamDialog addExamDialog;//Dialog数据初始化
    protected View dialogView;
    protected LayoutInflater mInflater;
    protected List ltYear;
    private List ltMonth;
    private List ltDay;
    private List ltHour;
    private List ltMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examtime);
        lvExam = (ListView) findViewById(R.id.Exam_listExam);
        fabAdd = (FloatingActionButton) findViewById(R.id.exam_fabAdd);
        examDao = new ExamDao(getApplicationContext());
        ltentityExam = new ArrayList<>();
        examEntity = new Exam();
        ltExam = new ArrayList<>();
        mapExam = new HashMap<>();
        itemName = new String[idsum];
        itemId = new int[]{R.id.exam_tvExamName, R.id.exam_tvCountDown, R.id.exam_tvExamdate, R.id.exam_tvExamClock, R.id.exam_tvExamId};
        ltYear = new ArrayList<>();
        ltMonth = new ArrayList<>();
        ltDay = new ArrayList<>();
        ltHour = new ArrayList<>();
        ltMin = new ArrayList<>();
        initView();
    }

    private void initView(){
        initListView();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                setDialog();
            }
        });
    }

    private void initListView() {
        itemName[0] = "ExamName";
        itemName[1] = "ExamCountDown";
        itemName[2] = "ExamDate";
        itemName[3] = "ExamClock";
        itemName[4] = "ExamID";
        initAdpList();
        adpExam = new SimpleAdapter(this,ltExam, R.layout.list_item_examtime, itemName, itemId );
        lvExam.setAdapter(adpExam);
        lvExam.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                mapExam = new HashMap();
                mapExam = (Map) lvExam.getItemAtPosition(position);
                new AlertDialog.Builder(ExamTimeActivity.this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                        deleteFromLvSQL((String) mapExam.get(itemName[4]));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("是否删除？")
                        .show();
                return false;
            }
        });
    }

    /**
     * 计算倒计时
     * @param date
     * @return
     */
    private String getCountdown(String date) {
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

    private void initDialog(){
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = mInflater.inflate(R.layout.dialog_add_examtime, null);

        int year = new CurrentDateUtils().getYear();
        initTimeList(ltYear,year,2030);
        initTimeList(ltMonth, 1, 12);
        initTimeList(ltDay, 1, 31);
        initTimeList(ltHour, 1, 24);
        initTimeList(ltMin, 1, 60);
        addExamDialog = new AddExamDialog.Builder(ExamTimeActivity.this)
                .setDialogView(dialogView)
                .setltYear(ltYear)
                .setltMonth(ltMonth)
                .setltDay(ltDay)
                .setltStHour(ltHour)
                .setltStMin(ltMin)
                .setltEdHour(ltHour)
                .setltEdMin(ltMin)
                .setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checkTime(examEntity.getDate())) {
                            dissmissDialog();
                        }
                    }
                }).setNegativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                })
                .setGetDataListener(new AddExamDialog.IGetDataListener() {

                    @Override
                    public void getData(String title, String date, String startTime, String endTime) {
                        examEntity.setId(title);
                        examEntity.setName(title);
                        examEntity.setDate(date);
                        examEntity.setStartTime(startTime);
                        examEntity.setEndTime(endTime);
                        if(checkTime(date)){
                            addToLvSQL();
                        }else {
                            Toast.makeText(getApplicationContext(), "选择的时间不正确", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .Build();


    }

    private void addToLvSQL() {
        //Log.i("examEntity", "addToLvSQL: " + examEntity.getName() +"\tDate"+ examEntity.getDate());
        examDao.addExam(examEntity);
        //更新Adapter
        ltExam.clear();
        initAdpList();
        adpExam.notifyDataSetChanged();
    }

    private void deleteFromLvSQL(String id){
        examDao.deleteExam(id);
        ltExam.clear();
        initAdpList();
        adpExam.notifyDataSetChanged();
    }

    public void initAdpList(){
        ltentityExam = examDao.getAllExams();
        Exam exam;
// Log.i("ltentityExam.size", "addToLvSQL: "+ltentityExam.size());
        if(ltentityExam.size()!=0){
            lvExam.setVisibility(View.VISIBLE);

            for (int i = 0; i < ltentityExam.size(); i++) {
                exam = ltentityExam.get(i);
//Log.i("eeeee", "addToLvSQL"+ exam.getId() + exam.getName() + "*" +"*"+getCountdown(exam.getDate())+ exam.getDate() + "*" + exam.getStartTime() + " - " + exam.getEndTime());
                mapExam = new HashMap();
                mapExam.put(itemName[0], exam.getName());
                mapExam.put(itemName[1], getCountdown(exam.getDate()));
                mapExam.put(itemName[2], exam.getDate());
                mapExam.put(itemName[3], exam.getStartTime() + " : " + exam.getEndTime());
                mapExam.put(itemName[4], exam.getId());
                ltExam.add(mapExam);
            }

        }
    }

    private void initTimeList(List list, int start, int end) {
        for (int i = start; i <= end; i++){
            if(i < 10){
                list.add("0"+i);
            }else {
                list.add(String.valueOf(i));
            }
        }
    }

    private void showDialog(){
        initDialog();
        if(addExamDialog !=null && !addExamDialog.isShowing()){
            addExamDialog.show();
        }
    }

    private void dissmissDialog(){
        if(addExamDialog !=null && addExamDialog.isShowing()){
            addExamDialog.dismiss();
        }

    }

    private  void setDialog(){
        Window dialogWindow = addExamDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    private Boolean checkTime(String date){
Log.i("date", "checkTime: " +date);
        return !getCountdown(date).contains("-");
    }
}

