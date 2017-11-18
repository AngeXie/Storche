package com.einzbern.storche.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alamkanak.weekview.WeekView;
import com.einzbern.storche.entities.Course;

/**
 * Created by Administrator on 2017/11/15.
 */

public class DisCourseService extends Service {
    private final IBinder iBinder = new LocalBinderDCS();
    public class LocalBinderDCS extends Binder{
        public DisCourseService getService(){
            return DisCourseService.this;
        }
    }
    private String[][] weekCourses;
    @Override
    public void onCreate(){
        weekCourses = new String [5][4];
        initWeekCourses();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private void initWeekCourses(){
        for (int i=0; i<5; i++){
            for (int j=0; j<4; j++){
                weekCourses[i][j] = null;
            }
        }
    }

    private String[][] getWeekCourses(){
        weekCourses[0][0] = "数据结构\n1204";
        weekCourses[0][3] = "线性代数\n2205";
        weekCourses[1][2] = "高等数学\n4306";
        return weekCourses;
    }
}
