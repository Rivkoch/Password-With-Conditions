package com.rivkoch.passwordwithconditions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String PASSWORD = "Radcliffe%98%"; // passwordexample

    private Button enter_BTN;
    private EditText password_EDT;
    private int batteryPercentageFirst;
    private int batteryPercentageSecond;
    private TextView battery_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();



        setListeners();
    }


    private void setListeners() {
        enter_BTN.setOnClickListener(v->{


        });


    }

    private int getBatteryPercentage() {
        int percentage = 0;
        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            battery_TV.setText("Battery Percentage is "+percentage+" %");
        }
        return percentage;
    }

    private void checkIfPasswordCorrect(String str) {

    }


    private void findViews() {
        enter_BTN = findViewById(R.id.enter_BTN);
        password_EDT = findViewById(R.id.password_EDT);
        battery_TV = findViewById(R.id.battery_TV);
    }
}