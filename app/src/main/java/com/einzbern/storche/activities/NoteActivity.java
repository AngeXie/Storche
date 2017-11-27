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
import android.widget.TextView;

import com.einzbern.storche.R;
import com.einzbern.storche.adptater.NoteAdptater;
import com.einzbern.storche.dao.DayMsgDao;
import com.einzbern.storche.entity.DayMsg;
import com.einzbern.storche.entity.Exam;
import com.einzbern.storche.util.CurrentDateUtils;
import com.einzbern.storche.view.AddNoteDialog;
import com.shamanland.fab.FloatingActionButton;

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
    protected DayMsgDao noteDao;
    protected List<DayMsg> entityList;//存放数据库返回结果
    protected List noteList;//适配器List
    protected Map mapNote;//适配器List的每一项
    protected static DayMsg noteEntity;
    //ListView 数据
    protected ArrayList<String> itemkey;
    protected NoteAdptater noteLAdapter;
    protected FloatingActionButton fbnAdd;
    protected LayoutInflater mInflater;
    //dialog
    protected View dialogView;
    protected AddNoteDialog noteDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        lvNote = (ListView) findViewById(R.id.note_lvNote);
        tv_curDate = (TextView) findViewById(R.id.note_tvCurDate);
        tv_weekDay = (TextView) findViewById(R.id.note_tvWeekDay);
        fbnAdd = (FloatingActionButton) findViewById(R.id.note_fbnAddNote);

        currentDateUtils = new CurrentDateUtils();
        noteDao = new DayMsgDao(NoteActivity.this);
        entityList = new ArrayList<>();
        entityList = new ArrayList<DayMsg>();
        noteList = new ArrayList<>();
        mapNote = new HashMap<>();
        noteEntity = new DayMsg();
        itemkey = new ArrayList<>();
        initView();
    }

    private void initView() {
        initListView();
        tv_curDate.setText(currentDateUtils.getYear() + "."
                + currentDateUtils.getMonth() + "."+ "."
                + currentDateUtils.getDay() );

        fbnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoteDialog();
                setDialogSize();
            }
        });
    }


    private void initDialog() {
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = mInflater.inflate(R.layout.dialog_add_note, null);

        noteDialog = new AddNoteDialog.Builder(NoteActivity.this)
                .setDialogView(dialogView)
                .setPositiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dissmissNoteDialog();
                    }
                })
                .setNegativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissNoteDialog();
                    }
                })
                .setIGetDataListener(new AddNoteDialog.IGetDialogData() {
                    @Override
                    public void getDate(String title, String content) {
                        noteEntity.setId(title);
                        noteEntity.setTitle(title);
                        noteEntity.setDetail(content);
                        addOneItem();
                    }
                })
                .Build();
    }

    private void setDialogSize(){
        Window dialogWindow = noteDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        lp.height = (int) (d.heightPixels* 0.6);// 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
    }

    private void dissmissNoteDialog() {
        if(noteDialog != null && noteDialog.isShowing()){
            noteDialog.dismiss();
        }
    }

    private void showNoteDialog() {
        initDialog();
        if(noteDialog != null && !noteDialog.isShowing()){
            noteDialog.show();
        }
    }

    private void initListView() {

        itemkey.add("Title");
        itemkey.add("Content");
        noteList = setadapterList();
        noteLAdapter = new NoteAdptater(this,noteList);
        lvNote.setAdapter(noteLAdapter);
        lvNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mapNote = new HashMap();
                mapNote = (Map) lvNote.getItemAtPosition(position);
                new AlertDialog.Builder(NoteActivity.this)
                        .setMessage("确定删除")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteOneItem((String) mapNote.get(itemkey.get(0)));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });
    }

    private List setadapterList() {
        entityList = noteDao.getAllDaysMsgs();
        DayMsg note;
        if(entityList.size() != 0){
            for(int i = 0; i < entityList.size(); i++){
                note = entityList.get(i);
                mapNote = new HashMap<>();
                mapNote.put(itemkey.get(0), note.getTitle());
                mapNote.put(itemkey.get(1), note.getDetail());
                noteList.add(mapNote);
            }
        }
        return noteList;
    }

    private void deleteOneItem(String id){
        noteDao.deleteDayMsg(id);
        noteList.clear();
        setadapterList();
        noteLAdapter.notifyDataSetChanged();
    }

    private void addOneItem(){
        Log.i("entity", "addOneItem: " + noteEntity.getId() + "*" + noteEntity.getTitle() + "*" + noteEntity.getDetail());
        noteDao.addDayMsg(noteEntity);
        noteList.clear();
        setadapterList();
        noteLAdapter.notifyDataSetChanged();
    }

}
