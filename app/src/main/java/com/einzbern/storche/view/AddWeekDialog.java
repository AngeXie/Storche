package com.einzbern.storche.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.einzbern.storche.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public class AddWeekDialog extends Dialog {
    public AddWeekDialog(@NonNull Context context) {
        super(context);
    }

    public AddWeekDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected AddWeekDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public interface IGetDialogDate{
        public void getData(String startWeek, String endWeek);
    }

    public static class Builder{
        private Context mContext;
        private View dialogView;
        private String startWeek;
        private String endWeek;
        private DialogInterface.OnClickListener positiveListener;
        private DialogInterface.OnClickListener negativeListener;
        private IGetDialogDate getDataListener;

        public Builder(Context context){
            this.mContext = context;
        }

        public Builder setPositiveListener(DialogInterface.OnClickListener positiveListener){
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setNegativeListener(DialogInterface.OnClickListener negativeListener){
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder setIGetDataListener(IGetDialogDate getDataListener){
            this.getDataListener = getDataListener;
            return this;
        }



        public AddWeekDialog Build(){
            startWeek = "1";
            endWeek = "1";
            final AddWeekDialog weekDialog = new AddWeekDialog(mContext);
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialogView = mInflater.inflate(R.layout.dialog_add_week, null);
            WheelView wvStart = (WheelView) dialogView.findViewById(R.id.addWeek_wvStWeek);
            WheelView wvEnd = (WheelView) dialogView.findViewById(R.id.addWeek_wvEdWeek);
            Button btnConfirm = (Button)dialogView.findViewById(R.id.addWeek_btnConfirm);
            Button btnCancel = (Button)dialogView.findViewById(R.id.addWeek_btnCancel);

            wvStart.setItems(getWeekList());
            wvEnd.setItems(getWeekList());

            wvStart.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
                @Override
               public void onSelected(int selectedIndex, String item){
                    startWeek = item;
                }
            });

            wvEnd.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
                @Override
                public void onSelected(int selectedIndex, String item){
                    endWeek = item;
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getDataListener != null){
                        getDataListener.getData(startWeek, endWeek);
                    }
                    if(positiveListener != null){
                        positiveListener.onClick(weekDialog, BUTTON_POSITIVE);
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(negativeListener != null){
                        negativeListener.onClick(weekDialog, BUTTON_NEGATIVE);
                    }
                }
            });

            weekDialog.setContentView(dialogView);
            return weekDialog;
        }

        private List<String> getWeekList() {
            List weekList = new ArrayList<>();
            for (int i = 1; i <= 25; i++) {
                weekList.add(String.valueOf(i));
            }
            return weekList;
        }
    }
}
