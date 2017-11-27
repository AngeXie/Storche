package com.einzbern.storche.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



public class GetweekUtil {
    /**
     * 当前日期与给定日期相隔周数 "yyyy-MM-dd"
     * 返回 int 值 周数
     */
    public int weekNum;
    Calendar endCalendar;
    Date endDate;

    Calendar startCalendar;
    Date startDate;
    public GetweekUtil(String startDate){//date "yyyy-MM-dd"
        this.startDate = toDate(startDate);
        startCalendar = Calendar.getInstance();
        startCalendar.setTime(this.startDate);

        this.endDate = new Date(System.currentTimeMillis());
        endCalendar = Calendar.getInstance();
        endCalendar.setTime(this.endDate);
        weekNum = 0;

    }
    public GetweekUtil(Date startDate){
        this.startDate = startDate;
        startCalendar = Calendar.getInstance();
        startCalendar.setTime(this.startDate);

        this.endDate = new Date(System.currentTimeMillis());
        endCalendar = Calendar.getInstance();
        endCalendar.setTime(this.endDate);
        weekNum = 0;
    }



    private Date toDate(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private int getExtraDays(Calendar calendar){
        int extraDays = 0;
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        switch (weekDay){
            case 1: extraDays = 6;break;
            case 2: extraDays = 0;break;
            case 3: extraDays = 1;break;
            case 4: extraDays = 2;break;
            case 5: extraDays = 3;break;
            case 6: extraDays = 4;break;
            case 7: extraDays = 5;break;
            default:break;
        }
        return extraDays;
    }

    public int getWeekNum() {
        weekNum = 0;
        long days = 0;
        days = (endDate.getTime() - startDate.getTime())/(24*60*60*1000) + getExtraDays(startCalendar) +7 - getExtraDays(endCalendar);
        System.out.println("days:" +days);
        weekNum = (int) (days/7);
        return weekNum;
    }


}
