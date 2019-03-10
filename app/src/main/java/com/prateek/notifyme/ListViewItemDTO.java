package com.prateek.notifyme;

import java.util.List;

public class ListViewItemDTO {

    private boolean checked = false;

    private String itemText = "";

    public ListViewItemDTO(boolean checked, String itemText) {
        this.checked = checked;
        this.itemText = itemText;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
