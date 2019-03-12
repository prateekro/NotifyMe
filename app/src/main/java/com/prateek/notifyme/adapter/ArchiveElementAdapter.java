package com.prateek.notifyme.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prateek.notifyme.R;
import com.prateek.notifyme.elements.ArchiveListElement;

import java.util.ArrayList;

import static com.prateek.notifyme.commons.utils.getAppIcon;

public class ArchiveElementAdapter extends ArrayAdapter<ArchiveListElement> {
    private int layoutResource;
    private ArrayList<ArchiveListElement> appList;
    private Context mContext;

    public ArchiveElementAdapter(@NonNull Context context, int layoutResource, ArrayList<ArchiveListElement> applist) {
        super(context, layoutResource, applist);
        this.layoutResource = layoutResource;
        this.appList = applist;
        this.mContext = context;
    }

    private static class ViewHolder {
        ImageView iv_appIcon;
        TextView tv_app_name;
        TextView tv_app_text_extra;
        TextView tv_time;
        TextView tv_date;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ArchiveListElement listElement = getItem(position);

        final ArchiveElementAdapter.ViewHolder viewHolder;

        View result;

        if (convertView == null){
            viewHolder = new ArchiveElementAdapter.ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder.iv_appIcon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
            viewHolder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_group);
            viewHolder.tv_app_text_extra = (TextView) convertView.findViewById(R.id.tv_app_text);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_element);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date_element);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ArchiveElementAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.iv_appIcon.setImageDrawable(getAppIcon(mContext, listElement.getAppID()));
        viewHolder.tv_app_name.setText(listElement.getAppName());
        viewHolder.tv_app_text_extra.setText(listElement.getAppTextExtra());
        viewHolder.tv_time.setText(listElement.getTime());
        viewHolder.tv_date.setText(listElement.getDate());

        return result;
    }

}
