package com.prateek.notifyme;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.service.NotificationService;

import java.util.List;

import static com.prateek.notifyme.commons.utils.TAG;

public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {
    private List<ListViewItemDTO> listViewItemDtoList = null;

    private Context ctx = null;
    private NotificationService notifyService;

    public ListViewItemCheckboxBaseAdapter(Context ctx, List<ListViewItemDTO> listViewItemDtoList) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
        this.notifyService = new NotificationService(ctx);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }


    @Override
    public View getView(final int itemIndex, View convertView, ViewGroup viewGroup) {

        ListViewItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();

        }else
        {

            convertView = View.inflate(ctx, R.layout.activity_list_view_with_checkbox_item, null);

            Switch switchC =  convertView.findViewById(R.id.switchButton);

            switchC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                        listViewItemDtoList.get(itemIndex).setChecked(status);
                        Log.d(TAG, "onCheckedChanged: "+ status + listViewItemDtoList.get(itemIndex).getItemText());
                        notifyService.toggleApplication(listViewItemDtoList.get(itemIndex).getItemText(), status );
                    //viewHolder.getSwitchCompat().setChecked(b);
                }
            });

            TextView listItemText = (TextView) convertView.findViewById(R.id.list_view_item_text);

            viewHolder = new ListViewItemViewHolder(convertView);

            viewHolder.setSwitchW(switchC);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        ListViewItemDTO listViewItemDto = listViewItemDtoList.get(itemIndex);
        Log.d(TAG, "getView: " + listViewItemDto.getItemText() + " " + listViewItemDto.isChecked());
        viewHolder.getSwitchW().setChecked(listViewItemDto.isChecked());
        viewHolder.getItemTextView().setText(listViewItemDto.getItemText());

        Log.i(TAG, listViewItemDto.getItemText());
        Log.i(TAG, String.valueOf(listViewItemDto.isChecked()));
        return convertView;
    }

}
