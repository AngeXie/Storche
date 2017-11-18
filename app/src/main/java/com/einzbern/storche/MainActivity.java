package com.einzbern.storche;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.einzbern.storche.activities.CoursesActivity;

public class MainActivity extends AppCompatActivity {
    private Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skipBtn = (Button)findViewById(R.id.button);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CoursesActivity.class);
                startActivity(intent);
            }
        });
    }
}
