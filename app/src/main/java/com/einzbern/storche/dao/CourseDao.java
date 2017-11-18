package com.einzbern.storche.dao;

import com.einzbern.storche.entities.Course;
import com.einzbern.storche.entities.Week;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/15.
 */

public class CourseDao {
    public CourseDao() {}

    public Course getCourse() {
        Course course = new Course();
        course.setName("高等数学");
        ArrayList<Integer> weeks = new ArrayList<Integer>();
        weeks.add(1);
        weeks.add(2);
        weeks.add(3);
        weeks.add(4);
        weeks.add(5);
        weeks.add(6);
        weeks.add(7);
        course.setWeeks(weeks);
        ArrayList<String[]> pot_pos = new ArrayList<String[]>();
        String[] pot_p1 = {""+ Week.MONDAY, ""+Course.MORNING_CLASS1, "4301"};
        String[] pot_p2 = {""+ Week.FIRDAY, ""+Course.NOON_CLASS1, "1204"};
        pot_pos.add(pot_p1);
        pot_pos.add(pot_p2);
        course.setTime_spot(pot_pos);
        return course;
    }
}
