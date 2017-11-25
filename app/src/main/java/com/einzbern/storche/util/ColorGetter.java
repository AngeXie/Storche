package com.einzbern.storche.util;

import android.content.Context;

import com.einzbern.storche.R;

/**
 * Created by Administrator on 2017/11/25.
 */

public class ColorGetter {
    /**
     *  get a new color
     * @param context : applicaitonContext
     * @param curC : current color , R.color.X
     * @return a new color, R.color.X
     */
    public static int getDifColor(Context context, int curC){
        switch (curC){
            case R.color.event_color_01 :
                return R.color.event_color_02;
            case R.color.event_color_02 :
                return R.color.event_color_03;
            case R.color.event_color_03 :
                return R.color.event_color_04;
            case R.color.event_color_04 :
                return R.color.event_color_05;
            case R.color.event_color_05 :
                return R.color.event_color_01;
        }
        return R.color.event_color_01;
    }
}
