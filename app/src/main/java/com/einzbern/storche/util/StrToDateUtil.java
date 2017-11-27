package com.einzbern.storche.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/26.
 */

public class StrToDateUtil {
    String strDate;
    Date date;
     public Date getDate(String strDate){
        this.strDate = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }
}
