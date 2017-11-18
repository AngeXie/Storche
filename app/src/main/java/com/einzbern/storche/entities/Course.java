package com.einzbern.storche.entities;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/15.
 */

public class Course {
    public final static int MORNING_CLASS1 = 1;
    public final static int MORNING_CLASS2 = 2;
    public final static int NOON_CLASS1 = 3;
    public final static int NOON_CLASS2 = 4;
    private String name;
    private ArrayList<String[]> time_spot;
    private ArrayList<Integer> weeks;

    public Course() {}

    public Course(String name, ArrayList<String[]> time_spot, ArrayList<Integer> weeks) {
        this.name = name;
        this.time_spot = time_spot;
        this.weeks = weeks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String[]> getTime_spot() {
        return time_spot;
    }

    public void setTime_spot(ArrayList<String[]> time_spot) {
        this.time_spot = time_spot;
    }

    public ArrayList<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<Integer> weeks) {
        this.weeks = weeks;
    }
}
