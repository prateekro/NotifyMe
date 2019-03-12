package com.prateek.notifyme;

import java.util.List;

public class ListViewItemDTO {

    private boolean checked = false;

    private String itemText = "";

    private Priority priority;

    public ListViewItemDTO(boolean checked, String itemText, Priority priority) {
        this.checked = checked;
        this.itemText = itemText;
        this.priority = priority;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
