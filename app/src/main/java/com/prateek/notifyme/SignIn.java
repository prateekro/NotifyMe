package com.prateek.notifyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {

    //Declarations
    private Button bt_sign_in, bt_sign_up;
    private EditText et_username, et_password, et_confirm_pass;
    private TextView tv_guest, tv_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Definitions
        bt_sign_in = (Button) findViewById(R.id.bt_signin);
        bt_sign_up = (Button) findViewById(R.id.bt_signup);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_pass = (EditText) findViewById(R.id.et_confirm_pass);

        tv_guest = (TextView) findViewById(R.id.tv_guest_signin);
        tv_banner = (TextView) findViewById(R.id.tv_banner);

        tv_banner.setText(R.string.sign_in);
        et_confirm_pass.setVisibility(View.INVISIBLE);

        View.OnClickListener btListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case  R.id.bt_signin: {
                        // goto MainActivity

                        if (et_confirm_pass.getVisibility() == View.VISIBLE) {

                            tv_banner.setText(R.string.sign_in);
                            et_confirm_pass.setVisibility(View.INVISIBLE);
                        }else{

                            startActivity(new Intent(SignIn.this, MainActivity.class));
                        }
                        break;
                    }
                    case  R.id.tv_guest_signin: {
                        // goto MainActivity

                        startActivity(new Intent(SignIn.this, MainActivity.class));
                        break;
                    }

                    case R.id.bt_signup: {
                        // change UI to SignUp

                        if (et_confirm_pass.getVisibility() == View.VISIBLE){
                            //Call service to signUp and (default login?) and take to MainActivity

                            startActivity(new Intent(SignIn.this, MainActivity.class));
                        }else{

                            tv_banner.setText(R.string.sign_up);
                            et_confirm_pass.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            }
        };

        tv_guest.setOnClickListener(btListener);

        bt_sign_in.setOnClickListener(btListener);
        bt_sign_up.setOnClickListener(btListener);
    }

    @Override
    public void onBackPressed() {
        if (et_confirm_pass.getVisibility() == View.VISIBLE) {
            tv_banner.setText(R.string.sign_in);
            et_confirm_pass.setVisibility(View.INVISIBLE);
        }else{
            super.onBackPressed();
            finish();
        }

    }
}
