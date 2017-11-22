package com.einzbern.storche.util;



import java.util.Calendar;
import java.util.TimeZone;
public class CurrentDateUtils {
    private int year;
    private int month;
    private int day;
    private String weekDay;
    private Calendar calendar;
    private String date;
    public CurrentDateUtils(){
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        weekDay = formatWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
    }

    private String formatWeekDay(int dayOfWeek) {
        String weekDay ;
        switch (dayOfWeek){
            case 1:
                weekDay = "周日";
                break;
            case 2:
                weekDay = "周一";
                break;
            case 3:
                weekDay = "周二";
                break;
            case 4:
                weekDay = "周三";
                break;
            case 5:
                weekDay = "周四";
                break;
            case 6:
                weekDay = "周五";
                break;
            case 7:
                weekDay = "周六";
                break;
            default:
                weekDay = "";
                break;
        }
        return weekDay;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}

