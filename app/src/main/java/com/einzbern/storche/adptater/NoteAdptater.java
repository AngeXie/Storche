package com.einzbern.storche.adptater;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.einzbern.storche.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;
import java.util.Map;


public class NoteAdptater extends BaseAdapter{
    public NoteAdptater(Context context, List<Map<String, String>> list){
        this.context = context;
        noteList = list;
        mInfater = LayoutInflater.from(context);
        mCollapsedStatus = new SparseBooleanArray();
    }
    private List<Map<String,String>> noteList;
    private Context context;
    private LayoutInflater mInfater;
    private SparseBooleanArray mCollapsedStatus;//展开折叠状态


    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        private TextView tvTitle;
        private ExpandableTextView expandableTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInfater.inflate(R.layout.list_item_note, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.note_tvTitle);
            viewHolder.expandableTextView = (ExpandableTextView) convertView.findViewById(R.id.note_tvexpand);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(noteList.get(position).get("Title"));
        viewHolder.expandableTextView.setText(noteList.get(position).get("Content"), mCollapsedStatus, position);
        return convertView;
    }

}
