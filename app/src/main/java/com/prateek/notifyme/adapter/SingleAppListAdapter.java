package com.prateek.notifyme.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.prateek.notifyme.MainActivity;
import com.prateek.notifyme.NotificationListing;
import com.prateek.notifyme.R;
import com.prateek.notifyme.SignIn;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.SingleListElement;
import com.prateek.notifyme.service.NotificationService;

import java.util.ArrayList;

import static com.prateek.notifyme.commons.utils.TAG;

public class SingleAppListAdapter extends ArrayAdapter<SingleListElement> {


    private int layoutResource;
    private ArrayList<SingleListElement> appList;
    private Context mContext;
    private static class ViewHolder {
        TextView tv_time;
        TextView tv_date;
        TextView tv_app_name;
        ImageView iv_archive;
        ImageView iv_delete;

//        TextView tv_counter;
        //ImageView info;
    }

    public SingleAppListAdapter(@NonNull Context context, int layoutResource, ArrayList<SingleListElement> applist) {
        super(context, layoutResource, applist);
        this.layoutResource = layoutResource;
        this.appList = applist;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final SingleListElement listElement = getItem(position);

        final SingleAppListAdapter.ViewHolder viewHolder;

        View result;

        if (convertView == null){
            viewHolder = new SingleAppListAdapter.ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_element_single);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date_element_single);
            viewHolder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_group_single);
            viewHolder.iv_archive = (ImageView) convertView.findViewById(R.id.iv_single_archive);
            viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_single_delete);
//            viewHolder.tv_counter = (TextView) convertView.findViewById(R.id.tv_counter_notification_single);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (SingleAppListAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.tv_app_name.setText(listElement.getAppName());
        viewHolder.tv_date.setText(listElement.getDate());
        viewHolder.tv_time.setText(listElement.getTime());
        viewHolder.iv_archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick archive at: "+position);
                NotificationService.archiveNotification(SignIn.mAuth.getCurrentUser().getEmail(), listElement.getAppID().toString(),
                        utils.getApplicationName(listElement.getAppID(), mContext), listElement.getAppName(), listElement.getSortTimeStamp());
                NotificationService.deleteNotification(listElement.getNotificationID().toString());
                NotificationListing.notificationList.remove(position);
                notifyDataSetChanged();


            }
        });

        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick delete at: "+position);
                NotificationService.deleteNotification(listElement.getNotificationID().toString());
                NotificationListing.notificationList.remove(position);
                notifyDataSetChanged();
            }
        });
//        viewHolder.tv_counter.setText(listElement.getCounter());

        return result;
    }

}
