package com.einzbern.storche.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.einzbern.storche.R;

/**
 * Created by Administrator on 2017/11/22.
 */

public class AddNoteDialog extends Dialog{
    public AddNoteDialog(@NonNull Context context) {
        super(context);
    }

    public AddNoteDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected AddNoteDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public interface IGetDialogData{
        public void getDate(String title, String content);
    }

    public static class Builder{
        private Context mContext;
        private View dialogView;
        private OnClickListener positiveListener;
        private OnClickListener negativeListener;
        private IGetDialogData getDataListener;
        private String title;
        private String content;

        public Builder(Context getmContext){
            this.mContext = getmContext;
        }

        public Builder setPositiveListener(OnClickListener positiveListener){
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setNegativeListener(OnClickListener negativeListener){
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder setIGetDataListener(IGetDialogData getDataListener){
            this.getDataListener = getDataListener;
            return this;
        }

        public Builder setDialogView(View view){
            this.dialogView = view;
            return this;
        }

        public AddNoteDialog Build(){
            final AddNoteDialog addNoteDialog = new AddNoteDialog(mContext);
            final EditText etTitle = (EditText) dialogView.findViewById(R.id.addNote_etTitle);
            final EditText etContent = (EditText) dialogView.findViewById(R.id.addNote_etContent);
            Button btnConfirm = (Button) dialogView.findViewById(R.id.addNote_btnConfirm);
            Button btnCancel = (Button) dialogView.findViewById(R.id.addNote_btnCancel);

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getDataListener != null){
                        getDataListener.getDate(etTitle.getText().toString(), etContent.getText().toString());
                    }

                    if(positiveListener != null){
                        positiveListener.onClick(addNoteDialog, BUTTON_POSITIVE);
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(negativeListener != null){
                        negativeListener.onClick(addNoteDialog, BUTTON_NEGATIVE);
                    }
                }
            });

            addNoteDialog.setContentView(dialogView);
            return addNoteDialog;
        }
    }

}
