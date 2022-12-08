package com.rivkoch.passwordwithconditions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Toast.makeText(SuccessActivity.this, "Invitation successfully unlocked", Toast.LENGTH_LONG).show();
    }


}