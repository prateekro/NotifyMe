package com.prateek.notifyme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                routeToRightPage(name);
            }
        });
    }

    // method to rate the app
    public void rateMe() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.whatsapp")));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + "com.whatsapp")));
        }
    }

    private void routeToRightPage(String name) {

        switch (name) {
            case "App Configure":
                startActivity(new Intent(SettingPageActivity.this, SubSettingPageActivity.class));
                break;
            case "Rate my app":
                rateMe();
                break;
            case "Share":
                invokeShare();
                break;
            case "Version":
                Toast.makeText(getApplicationContext(), "Version 0.0.1 SNAPSHOT ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void invokeShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String body = "Share this app, make it great again !!";
        String shareSub = "Wanna share this app";
        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share Using"));
    }

    private void init() {
        dataForSetting.add("App Configure");
        dataForSetting.add("Rate my app");
        dataForSetting.add("Share");
        dataForSetting.add("Version");
    }
}
