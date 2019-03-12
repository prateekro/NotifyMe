package com.prateek.notifyme.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prateek.notifyme.MainActivity;
import com.prateek.notifyme.R;
import com.prateek.notifyme.elements.ListElement;

import java.util.ArrayList;
import java.util.Objects;

import static com.prateek.notifyme.commons.utils.TAG;
import static com.prateek.notifyme.commons.utils.getAppIcon;

//https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view :: Read : three_horizontal_text_views_layout

//https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
public class AppListElementAdapter extends ArrayAdapter<ListElement>{

    private int layoutResource;
    private ArrayList<ListElement> appList;
    private Context mContext;
    private static final int DEFAULT_THRESHOLD = 128;
    private int lastAction = -1;

    private static class ViewHolder {
        ImageView iv_app_icon;
        TextView tv_time;
        TextView tv_date;
        TextView tv_app_name;
        TextView tv_counter;
        ConstraintLayout container;
        //ImageView info;
    }

    public AppListElementAdapter(@NonNull Context context, int layoutResource, ArrayList<ListElement> applist) {
        super(context, layoutResource, applist);
        this.layoutResource = layoutResource;
        this.appList = applist;
        this.mContext = context;
    }

// // TODO - (A) - For animate up down animation - Future todo
//    private int lastPosition = -1; //

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

            viewHolder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_element);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date_element);
            viewHolder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_group);
            viewHolder.tv_counter = (TextView) convertView.findViewById(R.id.tv_counter_notification);
            viewHolder.container = (ConstraintLayout) convertView.findViewById(R.id.container);

            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
// // TODO - (A) - For animate up down animation - Future todo
//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ?
//                R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;


        viewHolder.iv_app_icon.setImageDrawable(getAppIcon(mContext, listElement.getAppID().toString()));
        //listElement.getAppName());
        viewHolder.tv_app_name.setText(listElement.getAppName());
        viewHolder.tv_date.setText(listElement.getDate());
        viewHolder.tv_time.setText(listElement.getTime());
        viewHolder.tv_counter.setText(Objects.equals(listElement.getCounter(), "0") ? "" : listElement.getCounter());

        return result;
    }

}
