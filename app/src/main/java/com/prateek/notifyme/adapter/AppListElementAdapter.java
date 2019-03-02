package com.prateek.notifyme.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prateek.notifyme.R;
import com.prateek.notifyme.elements.ListElement;

import java.util.ArrayList;

//https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view :: Read : three_horizontal_text_views_layout

//https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
public class AppListElementAdapter extends ArrayAdapter<ListElement> {

    private int layoutResource;
    private ArrayList<ListElement> appList;
    private Context mContext;

    private static class ViewHolder {
        TextView tv_time;
        TextView tv_date;
        TextView tv_app_name;
        TextView tv_counter;
        //ImageView info;
    }

    public AppListElementAdapter(@NonNull Context context, int layoutResource, ArrayList<ListElement> applist) {
        super(context, layoutResource, applist);
        this.layoutResource = layoutResource;
        this.appList = applist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ListElement listElement = getItem(position);

        ViewHolder viewHolder;

        View result;

        if (convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_element);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date_element);
            viewHolder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_group);
            viewHolder.tv_counter = (TextView) convertView.findViewById(R.id.tv_counter_notification);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.tv_app_name.setText(listElement.getAppName());
        viewHolder.tv_date.setText(listElement.getDate());
        viewHolder.tv_time.setText(listElement.getTime());
        viewHolder.tv_counter.setText(listElement.getCounter());

        return convertView;
    }

}
