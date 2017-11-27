package com.einzbern.storche.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.einzbern.storche.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class AddExamDialog extends Dialog {
    public AddExamDialog(@NonNull Context context) {
        super(context);
    }

    public AddExamDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected AddExamDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    //回调接口
    public interface IGetDataListener{
        public void getData(String title, String date, String startTime, String endTime);
    }

    public static class Builder{
        private Context mContext;
        private View contentView;
        private String[] time;
        private List ltYear;
        private List ltMonth;
        private List ltDay;
        private List ltStHour;
        private List ltStMin;
        private List ltEdHour;
        private List ltEdMin;
        private OnClickListener positiveListener;
        private OnClickListener negativeListener;
        private IGetDataListener getDataListener;

        public Builder(Context context){
            this.mContext = context;
        }
        public Builder setDialogView(View view){
            this.contentView = view;
            return this;
        }
        public Builder setltYear(List year){
            this.ltYear = year;
            return this;
        }
        public Builder setltDay(List ltDay){
            this.ltDay = ltDay;
            return this;
        }
        public Builder setltMonth(List ltMonth){
            this.ltMonth = ltMonth;
            return this;
        }
        public Builder setltStHour(List ltStHour){
            this.ltStHour = ltStHour;
            return this;
        }
        public Builder setltStMin(List ltStMin){
            this.ltStMin = ltStMin;
            return this;
        }
        public Builder setltEdHour(List ltEdHour){
            this.ltEdHour = ltEdHour;
            return this;
        }
        public Builder setltEdMin(List ltEdMin){
            this.ltEdMin = ltEdMin;
            return this;
        }
        public Builder setPositiveListener(OnClickListener positiveListener){
            this.positiveListener = positiveListener;
            return this;
        }
        public Builder setNegativeListener(OnClickListener negativeListener){
            this.negativeListener = negativeListener;
            return this;
        }
        public Builder setGetDataListener(IGetDataListener getDataListener){
            this.getDataListener = getDataListener;
            return this;
        }

        public AddExamDialog Build(){
            AddExamDialog addExamDialog = new AddExamDialog(mContext);
            time = new String[7];
            if(contentView != null) {
                initDialog(addExamDialog);
                addExamDialog.setContentView(contentView);
            }
            addExamDialog.setCanceledOnTouchOutside(false);
            return addExamDialog;
        }

        private void initDialog(final AddExamDialog addExamDialog) {
            time[0] = (String) ltYear.get(0);
            time[1] = (String)ltMonth.get(0);
            time[2] = (String)ltDay.get(0);
            time[3] = (String)ltStHour.get(0);
            time[4] = (String)ltStMin.get(0);
            time[5] = (String)ltEdHour.get(0);
            time[6] = (String)ltEdMin.get(0);
            final EditText etTitle = (EditText)contentView.findViewById(R.id.addNote_etTitle);
            Button btnConfirm = (Button)contentView.findViewById(R.id.addNote_btnConfirm);
            WheelView wvYear = getWheelview(R.id.addNote_wvYear);
            WheelView wvMonth = getWheelview(R.id.addNote_wvMonth);
            WheelView wvDay = getWheelview(R.id.addNote_wvDay);
            WheelView wvStartHour = getWheelview(R.id.addNote_wvStartHour);
            WheelView wvStartMin = getWheelview(R.id.addNote_wvStartMin);
            WheelView wvEndHour = getWheelview(R.id.addNote_wvEndHour);
            WheelView wvEndMin = getWheelview(R.id.addNote_wvEndMin);
            initWheelView(wvYear,ltYear ,0 );
            initWheelView(wvMonth, ltMonth,1);
            initWheelView(wvDay, ltDay,2);
            initWheelView(wvStartHour, ltStHour,3);
            initWheelView(wvStartMin, ltStMin,4);
            initWheelView(wvEndHour, ltEdHour,5);
            initWheelView(wvEndMin, ltEdMin,6);

            if(positiveListener != null){
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(getDataListener != null){
                            getDataListener.getData(etTitle.getText().toString(),
                                    time[0]+"-"+time[1]+"-" + time[2],
                                    time[3] + ":" + time[4],
                                    time[5] + ":" + time[6]);
                        }
                        if(positiveListener != null) {
                            positiveListener.onClick(addExamDialog, BUTTON_POSITIVE);
                        }
                    }
                });
            }
            Button btnCancel = (Button)contentView.findViewById(R.id.addNote_btnCancel);
            if(negativeListener != null){
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(negativeListener != null) {
                            negativeListener.onClick(addExamDialog, BUTTON_NEGATIVE);
                        }
                    }
                });
            }
        }

        private void initWheelView(WheelView wheelView, List list, int i) {
           final int j = i;
            wheelView.setItems(list);
            wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
                public void onSelected(int selectedIndex, String item) {
                   time[j] = item ;

                }
            });

        }

        private WheelView getWheelview(int wheelViewId) {
            return (WheelView)contentView.findViewById(wheelViewId);
        }

    }

}
