package com.einzbern.storche.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.einzbern.storche.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Administrator on 2017/11/17.
 */

public class AddCourseActivity extends AppCompatActivity {
    private TextView courseName;
    private TextView courseWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseName = (TextView) findViewById(R.id.dialog_courseNums);
        courseWeek = (TextView) findViewById(R.id.dialog_courseWeeks);
        setLisners();
    }

    private void setLisners(){
        courseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cNumDialog = new AlertDialog.Builder(AddCourseActivity.this);
                cNumDialog.setTitle("选择上课节数");
                cNumDialog.setMessage("上课节数选项");
                cNumDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                cNumDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                cNumDialog.show();
            }
        });
        courseWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cNumDialog = new AlertDialog.Builder(AddCourseActivity.this);
                cNumDialog.setTitle("选择上课周数");
                cNumDialog.setMessage("上课周数选项");
                cNumDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                cNumDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                cNumDialog.show();
            }
        });
    }
}
