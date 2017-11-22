package com.einzbern.storche.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.einzbern.storche.R;
import com.einzbern.storche.adptater.NoteAdptater;
import com.einzbern.storche.util.CurrentDateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/11/19.
 */

public class NoteActivity extends AppCompatActivity {
    protected ListView lvNote;
    protected TextView tv_curDate;
    protected TextView tv_weekDay;
    protected CurrentDateUtils currentDateUtils;
    //ListView 数据
    protected List ltNote;
    protected Map mapNote;
    protected NoteAdptater noteLAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        lvNote = (ListView) findViewById(R.id.note_lvNote);
        tv_curDate = (TextView) findViewById(R.id.note_tvCurDate);
        tv_weekDay = (TextView) findViewById(R.id.note_tvWeekDay);
        currentDateUtils = new CurrentDateUtils();
        mapNote = new HashMap<>();
        initListView();


    }

    private List<Map<String, String>> getData(){
        List list = new ArrayList<Map<String, String>>();
        mapNote.put("Title", "今日记事");
        mapNote.put("Content", "The story of a Chinese couple at Oxford University who first met each other as deskmate in middle school and recently married each other has gone viral, southcn.com reported.ty in a maps app to show an address. This model provides multiple entry points for a single app and allows any app to behave as a user's \"default\" The story of a Chinese couple at Oxford University who first met each other as deskmate in middle school and recentlfor an action that other apps may invoke.");
        list.add(mapNote);
        mapNote.put("Title", "今日记事");
        mapNote.put("Content", "The story of a Chinese couple at Oxford University who first met each other as deskmate in middle school and recently married each other has gone viral, southcn.com reported.ty in a maps app to show an address. This model provides multiple entry points for a single app and allows any app to behave as a user's \"default\" The story of a Chinese couple at Oxford University who first met each other as deskmate in middle school and recentlfor an action that other apps may invoke.");
        list.add(mapNote);
        return list;
    }

    private void initListView() {
        ltNote = getData();
        noteLAdapter = new NoteAdptater(this,ltNote);
        lvNote.setAdapter(noteLAdapter);
        tv_curDate.setText(currentDateUtils.getYear() + "."
                + currentDateUtils.getMonth() + "."+ "."
                + currentDateUtils.getDay() );
        tv_weekDay.setText(currentDateUtils.getWeekDay());
    }

}
