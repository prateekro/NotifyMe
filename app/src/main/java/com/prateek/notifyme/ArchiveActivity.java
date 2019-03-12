package com.prateek.notifyme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prateek.notifyme.adapter.ArchiveElementAdapter;
import com.prateek.notifyme.commons.utils;
import com.prateek.notifyme.elements.ArchiveListElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class ArchiveActivity extends AppCompatActivity {

    ArrayList<ArchiveListElement> archiveList;
    private ArchiveElementAdapter mArchiveElementAdapter;
    private ListView lv_listing;
    private TextView tv_appname;
    private ImageView iv_appIcon;


    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("notifications").child(SignIn.mAuth.getCurrentUser().getEmail().replace(".",""));

    }

    @Override
    protected void onStart() {
        super.onStart();

        archiveList = new ArrayList<ArchiveListElement>();
        lv_listing = (ListView) findViewById(R.id.lv_listing);
        tv_appname = (TextView) findViewById(R.id.tv_listing);
        iv_appIcon = (ImageView) findViewById(R.id.iv_listing);

        mArchiveElementAdapter = new ArchiveElementAdapter(this, R.layout.archive_element, archiveList);
        lv_listing.setAdapter(mArchiveElementAdapter);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.rv_layout_animation);
        lv_listing.setLayoutAnimation(controller);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                NotificationBean notification = dataSnapshot.getValue(NotificationBean.class);
//                HashMap map = dataSnapshot.getValue(HashMap.class);
                if(dataSnapshot.hasChildren()){
                    Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                    while (iter.hasNext()){
                        DataSnapshot snap = iter.next();
                        String nodId = snap.getKey();

                        String txt = (String) snap.child("txt").getValue();
                        String appname = (String) snap.child("appname").getValue();
                        String appid = (String) snap.child("appid").getValue();
                        String timestamp = (String) snap.child("timestamp").getValue().toString();

                        if (txt == null || appname == null || appid == null || timestamp == null)
                            continue;
                        archiveList.add(new ArchiveListElement(utils.getTimeAgo(new Date(timestamp)), utils.getTimeToDate(new Date(timestamp)), appname, txt, timestamp.toString(), appid.toString(), 0));

                        //received results
                        Log.d(utils.TAG, "onDataChange: "+txt+appname+appid+timestamp);
                    }
                    mArchiveElementAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("^^^ ERROR: "+ databaseError.toString());
            }
        };
        mPostReference.addValueEventListener(postListener);
        mPostListener = postListener;

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }

    }
}
