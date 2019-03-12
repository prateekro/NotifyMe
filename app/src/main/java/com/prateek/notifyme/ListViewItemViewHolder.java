package com.prateek.notifyme;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class ListViewItemViewHolder extends RecyclerView.ViewHolder {

    private Switch switchW;

    private TextView itemTextView;

    private RadioGroup priority;

    public ListViewItemViewHolder(View itemView) {
        super(itemView);
    }

    public Switch getSwitchW() {
        return switchW;
    }

    public void setSwitchW(Switch switchW) {
        this.switchW = switchW;
    }

    public TextView getItemTextView() {
        return itemTextView;
    }

    public void setItemTextView(TextView itemTextView) {
        this.itemTextView = itemTextView;
    }

    public RadioGroup getPriority() {
        return priority;
    }

    public void setPriority(RadioGroup priority) {
        this.priority = priority;
    }
}