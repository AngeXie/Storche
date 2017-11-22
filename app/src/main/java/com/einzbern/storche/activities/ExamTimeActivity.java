package com.einzbern.storche.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.einzbern.storche.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/19.
 */

public class ExamTimeActivity extends AppCompatActivity{
    protected ListView lvExam;
    protected SimpleAdapter adpExam;
    protected List ltExam;
    protected Map mapExam;
    protected String[] itemName;
    protected int[] itemId;
    protected int idsum = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examtime);
        lvExam = (ListView) findViewById(R.id.Exam_listExam);
        ltExam = new ArrayList<>();
        mapExam = new HashMap<>();
        itemName = new String[idsum];
        itemId = new int[]{R.id.exam_tvExamName, R.id.exam_tvExamTime, R.id.exam_tvExamdate, R.id.exam_tvExamClock};
        initListView();


    }

    private void initListView() {
        /**id为
         *exam_tvExamName
         exam_tvExamTime
         exam_tvExamdate
         exam_tvExamClock
         */

        itemName[0] = "ExamName";
        itemName[1] = "ExamTime";
        itemName[2] = "ExamDate";
        itemName[3] = "ExamClock";

        mapExam.put(itemName[0], "软件工程");
        mapExam.put(itemName[1], "4周");
        mapExam.put(itemName[2], "2017.08.12");
        mapExam.put(itemName[3], "am 8:00 - 9:00");
        ltExam.add(mapExam);
        adpExam = new SimpleAdapter(this,ltExam, R.layout.list_item_examtime, itemName, itemId );
        lvExam.setAdapter(adpExam);
    }


}

