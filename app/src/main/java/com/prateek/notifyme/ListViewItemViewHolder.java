package com.prateek.notifyme;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

public class ListViewItemViewHolder extends RecyclerView.ViewHolder {

    private Switch switchW;

    private TextView itemTextView;

    public ListViewItemViewHolder(View itemView) {
        super(itemView);
    }

    /*public CheckBox getItemCheckbox() {
        return itemCheckbox;
    }

    public void setItemCheckbox(CheckBox itemCheckbox) {
        this.itemCheckbox = itemCheckbox;
    }*/

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
}