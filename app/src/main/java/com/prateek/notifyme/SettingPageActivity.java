package com.prateek.notifyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SettingPageActivity extends AppCompatActivity {

    List<String> dataForSetting = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        init();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, dataForSetting);
        ListView listView = findViewById(R.id.settingDataView);
        listView.setAdapter(adapter);
}

    private void init() {
        dataForSetting.add("app configure");
        dataForSetting.add("app configure");
        dataForSetting.add("app configure");
        dataForSetting.add("app configure");
    }

}
